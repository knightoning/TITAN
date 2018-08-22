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
package com.yunji.titan.task.watch;

import java.util.List;
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
import com.yunji.titan.utils.NodePath;
import com.yunji.titan.utils.ZookeeperConnManager;

/**
 * 监听注册中心agent的变化
 * 
 * @author gaoxianglong
 */
@Component
public class WatchAgents implements Watcher {
	@Resource
	private ZookeeperConnManager zookeeperConnManager;
	private String nodePath = NodePath.ROOT_NODEPATH;
	private Logger log = LoggerFactory.getLogger(WatchAgents.class);

	@PostConstruct
	public void init() {
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		try {
			/* 如果根节点不存在则创建 */
			if (null == zkClient.exists(nodePath, false)) {
				zkClient.create(nodePath, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			/* 注册节点 */
			zkClient.getChildren(nodePath, this);
		} catch (KeeperException | InterruptedException e) {
			log.error("error", e);
		}
	}

	@Override
	public void process(WatchedEvent event) {
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		try {
			/* 重新注册节点 */
			List<String> childrens = zkClient.getChildren(nodePath, this);
			EventType eventType = event.getType();
			switch (eventType) {
			case NodeChildrenChanged:
				log.info("当前注册中心内的成功注册的agent数量-->"
						+ childrens.stream().filter(children -> children.startsWith("agent")).count());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			log.error("error", e);
		}
	}
}