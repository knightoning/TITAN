/*
 * Copyright 2015-2101 yunjiweidian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yunji.titan.datacollect;

import javax.annotation.Resource;

import org.apache.zookeeper.CreateMode;
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
 * Master选举
 * 
 * @author gaoxianglong
 */
@Service
public class MasterElection implements Watcher {
	@Resource
	private ZookeeperConnManager zookeeperConnManager;
	@Resource
	private SubscribeMessage subscribeMessage;
	private final String nodePath = NodePath.DATACOLLECT_LOCK_NODEPATH;
	private Logger log = LoggerFactory.getLogger(MasterElection.class);

	/**
	 * 创建临时节点
	 * 
	 * @author gaoxianglong
	 * 
	 * @exception Exception
	 * 
	 * @return void
	 */
	public void election() {
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		try {
			/* 验证临时节点是否已经被创建 */
			if (null == zkClient.exists(nodePath, false)) {
				zkClient.create(nodePath, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
				log.info("titan-datacollect启动成功...");
				subscribeMessage.getMsg();
				/* 注册节点 */
				zkClient.exists(nodePath, this);
			} else {
				/* 如果节点已经存在，则监听 */
				zkClient.exists(nodePath, this);
				log.info("mode --> slave");
			}
		} catch (Exception e) {
			try {
				/* 如果节点已经存在，则监听 */
				zkClient.exists(nodePath, this);
				log.info("mode --> slave");
			} catch (Exception e1) {
				log.error("error", e1);
			}
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
			case NodeDeleted:
				election();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			log.error("error", e);
		}
	}
}