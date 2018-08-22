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
package com.yunji.titan.agent.watch.registry;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.Properties;
import java.util.Scanner;

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
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yunji.titan.agent.RequestHandler;
import com.yunji.titan.agent.state.AgentStateContext;
import com.yunji.titan.agent.state.FreeState;
import com.yunji.titan.agent.utils.ChangeAgentState;
import com.yunji.titan.task.facade.TaskFacade;
import com.yunji.titan.utils.AgentTaskBean;
import com.yunji.titan.utils.AgentType;
import com.yunji.titan.utils.NodePath;
import com.yunji.titan.utils.Result;
import com.yunji.titan.utils.ZookeeperConnManager;
import com.yunji.titan.utils.ftp.FtpUtils;

/**
 * 向注册中心注册agent节点,并监听TaskService的任务下发
 * 
 * @author gaoxianglong
 */
@Component
public class RegisterAgent implements Watcher {
	private String nodePath = NodePath.ROOT_NODEPATH;
	@Resource
	private TaskFacade taskFacade;
	@Resource
	private ZookeeperConnManager zookeeperConnManager;
	@Resource
	private RequestHandler requestHandler;
	@Resource
	private FtpUtils ftpUtils;
	@Resource
	private AgentStateContext agentStateContext;
	private Logger log = LoggerFactory.getLogger(RegisterAgent.class);

	/**
	 * 注册agent节点
	 *
	 * @author gaoxianglong
	 */
	@PostConstruct
	public void register() {
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		try {
			/* 如果根节点不存在则创建 */
			if (null == zkClient.exists(nodePath, false)) {
				zkClient.create(nodePath, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			/* 创建临时节点 */
			nodePath = zkClient.create(nodePath + "/agent-", AgentType.FREE, Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL_SEQUENTIAL);
			log.info("成功注册agent节点-->" + nodePath);
			/* 注册节点 */
			zkClient.exists(nodePath, this);
		} catch (KeeperException | InterruptedException e) {
			log.error("error", e);
		}
	}

	@Override
	public void process(WatchedEvent event) {
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		try {
			/* 重新注册节点 */
			zkClient.exists(nodePath, this);
			EventType eventType = event.getType();
			switch (eventType) {
			case NodeDataChanged:
				Properties properties = new Properties();
				properties.load(new StringReader(new String(zkClient.getData(nodePath, false, null))));
				Boolean task = Boolean.parseBoolean(properties.getProperty("task"));
				Boolean interrupted = Boolean.parseBoolean(properties.getProperty("interrupted"));
				if (null != task && null != interrupted) {
					if (interrupted) {
						requestHandler.interruptedTask();
					} else if (task) {
						new Thread(() -> {
							/* 领取taskService下发的任务 */
							Result<String> result = taskFacade.pullTask(nodePath);
							if (!result.isSuccess()) {
								log.warn("领取任务失败,errorCode->" + result.getErrorCode() + "errorMsg->"
										+ result.getErrorMsg());
								try {
									/* 领取任务失败后,恢复agent状态为空闲 */
									ChangeAgentState.change(agentStateContext, new FreeState(),
											zookeeperConnManager.getZkClient(), nodePath);
									log.warn("获取压测脚本失败,则恢复agent状态为空闲");
								} catch (Exception e) {
									log.info("error", e);
								}
								return;
							}
							File ftpFile = new File(result.getData());
							/* 从FTP中获取脚本 */
							boolean downloadResult = ftpUtils.downloadFile(ftpFile);
							try {
								if (downloadResult) {
									File localFile = new File(
											System.getProperty("user.home") + "/" + ftpFile.getName());
									try (Scanner scan = new Scanner(
											new BufferedInputStream(new FileInputStream(localFile)), "UTF-8")) {
										StringBuffer strBuffer = new StringBuffer();
										while (scan.hasNextLine()) {
											strBuffer.append(scan.nextLine());
										}
										String taskInfo = strBuffer.toString();
										log.info("收到压测任务信息-->" + taskInfo);
										AgentTaskBean agentTaskBean = JSONObject.parseObject(taskInfo,
												AgentTaskBean.class);
										requestHandler.handler(nodePath, agentTaskBean);
									} catch (Exception e) {
										log.error("error", e);
									} finally {
										if (localFile.exists()) {
											if (localFile.delete()) {
												log.info("从FTP下载的源文件-->" + localFile.getPath() + "已经成功从本地删除");
											}
										}
									}
								} else {
									try {
										/*
										 * 获取压测脚本失败,则恢复agent状态为空闲,由于压测脚本获取失败,
										 * 无法获取taskid,所以需要手动修改场景状态
										 */
										ChangeAgentState.change(agentStateContext, new FreeState(),
												zookeeperConnManager.getZkClient(), nodePath);
										log.warn("获取压测脚本失败,则恢复agent状态为空闲");
									} catch (Exception e) {
										log.info("error", e);
									}
								}
							} finally {
								ftpUtils.deleteFile(ftpFile);
							}
						}).start();
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