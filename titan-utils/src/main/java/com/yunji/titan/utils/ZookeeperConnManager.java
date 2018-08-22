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
package com.yunji.titan.utils;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zookeeper连接管理类
 * 
 * @author gaoxianglong
 */
public class ZookeeperConnManager {
	private ZooKeeper zkClient;
	private String zkAddress;
	private int zkSessionTimeout = 5000;
	private CountDownLatch countDownLatch;
	private Logger logger = LoggerFactory.getLogger(ZookeeperConnManager.class);

	public ZookeeperConnManager() {
		countDownLatch = new CountDownLatch(1);
	}

	/**
	 * 连接zookeeper
	 * 
	 * @author gaoxianglong
	 */
	public void init() {
		try {
			zkClient = new ZooKeeper(zkAddress, zkSessionTimeout, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					KeeperState state = event.getState();
					switch (state) {
					case SyncConnected:
						countDownLatch.countDown();
						logger.info("connection zookeeper success");
						break;
					case Disconnected:
						logger.warn("zookeeper connection is disconnected");
						break;
					case Expired:
						logger.error("zookeeper session expired");
						break;
					case AuthFailed:
						logger.error("authentication failure");
						break;
					default:
						break;
					}
				}
			});
			countDownLatch.await();
		} catch (Exception e) {
			logger.error("error", e);
		}
	}

	public String getZkAddress() {
		return zkAddress;
	}

	public void setZkAddress(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	public int getZkSessionTimeout() {
		return zkSessionTimeout;
	}

	public void setZkSessionTimeout(int zkSessionTimeout) {
		this.zkSessionTimeout = zkSessionTimeout;
	}

	public ZooKeeper getZkClient() {
		return zkClient;
	}
}