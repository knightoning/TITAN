/*
 * Copyright (C) 2015-2020 yunjiweidian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.yunji.titan.manager.utils.timertask;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yunji.titan.manager.entity.AutomaticTask;
import com.yunji.titan.manager.service.AutomaticTaskService;
import com.yunji.titan.manager.service.OperateService;
import com.yunji.titan.utils.NodePath;
import com.yunji.titan.utils.ZookeeperConnManager;
/**
 * 自动压测任务监听
 *
 * @author liuliang
 *
 */
@Service
public class TimerTaskListener implements Watcher {
	private ZooKeeper zkClient;
	@Resource
	private ZookeeperConnManager zookeeperConnManager;
	private String nodePath = NodePath.TIMETASK_NODEPATH;
	private Logger log = LoggerFactory.getLogger(TimerTaskListener.class);

	/**
	 * 自动压测任务服务
	 */
	@Resource
	private AutomaticTaskService automaticTaskService;

	/**
	 * 操作服务
	 */
	@Resource
	private OperateService operateService;

	public @PostConstruct void init() {
		zkClient = zookeeperConnManager.getZkClient();
		createNode();
	}

	/**
	 * 创建临时节点
	 * 
	 * @author gaoxianglong
	 * 
	 * @exception Exception
	 * 
	 * @return void
	 */
	private void createNode() {
		try {
			/* 验证临时节点是否已经被创建 */
			if (null == zkClient.exists(nodePath, false)) {
				zkClient.create(nodePath, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			zkClient.exists(nodePath, this);
		} catch (KeeperException e) {
			try {
				zkClient.exists(nodePath, this);
			} catch (Exception e1) {
				log.error("error", e1);
			}
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	/**
	 * 将任务同步给所有的子节点
	 * 
	 * @author gaoxianglong
	 */
	protected boolean synTask(byte[] task) {
		boolean result = false;
		try {
			result = null != zkClient.setData(nodePath, task, -1) ? true : result;
		} catch (Exception e) {
			log.error("error", e);
		}
		return result;
	}

	@Override
	public void process(WatchedEvent event) {
		try {
			/* 重新注册节点 */
			zkClient.exists(nodePath, this);
			EventType eventType = event.getType();
			switch (eventType) {
			case NodeDataChanged:
				byte[] value = zkClient.getData(nodePath, false, null);
				if (null != value) {
					Properties properties = new Properties();
					properties.load(new StringReader(new String(value)));
					Map<String, TaskInfoBean> tasksMap = TaskExecutor.tasksMap;
					synchronized (tasksMap) {
						final String taskName = properties.getProperty("taskName");
						log.info("探测到定时任务-->" + taskName + "发生更新,准备重置本地定时任务");
						if (properties.getProperty("type").equals("true")) { 
							// 新增、修改
							AutomaticTask automaticTask = automaticTaskService
									.getDataDetailBySceneId(Long.parseLong(taskName));
							String time = new SimpleDateFormat("HH:mm:ss").format(automaticTask.getStartTime());
							/* 执行次数 */
							final int executeNum = automaticTask.getPressureTimes();
							TaskInfoBean taskInfoBean = new TaskInfoBean();
							taskInfoBean.setTime(time);
							taskInfoBean.setTask(new Runnable() {
								@Override
								public void run() {
									try {
										operateService.doAutomaticTask(Long.parseLong(taskName), executeNum);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
							tasksMap.put(taskName, taskInfoBean);
						} else { // 删除
							tasksMap.remove(taskName);
						}
					}
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			log.error("error", e);
		}
	}
}