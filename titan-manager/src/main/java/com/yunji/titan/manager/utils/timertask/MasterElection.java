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
import com.yunji.titan.utils.NodePath;
import com.yunji.titan.utils.ZookeeperConnManager;

/**
 * 如果能够获取到分布式锁,则意味着可以执行任务调度
 * 
 * @author gaoxianglong
 */
@Service
public class MasterElection implements Watcher {
	private ZooKeeper zkClient;
	@Resource
	private ZookeeperConnManager zookeeperConnManager;
	private String nodePath = NodePath.TIMETASK_LOCK_NODEPATH;
	protected static boolean IS_MASTER = false;
	private Logger log = LoggerFactory.getLogger(MasterElection.class);

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
				zkClient.create(nodePath, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
				log.info("mode --> 成功获取到分布式锁,可执行定时任务调度");
				IS_MASTER = true;
			} else {
				zkClient.exists(nodePath, this);
			}
		} catch (KeeperException e) {
			try {
				/* 如果节点已经存在,则监听 */
				zkClient.exists(nodePath, this);
			} catch (Exception e1) {
				log.error("error", e1);
			}
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	@Override
	public void process(WatchedEvent event) {
		try {
			/* 重新注册节点 */
			zkClient.exists(nodePath, this);
			EventType eventType = event.getType();
			switch (eventType) {
			case NodeDeleted:
				createNode();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			log.error("error", e);
		}
	}
}