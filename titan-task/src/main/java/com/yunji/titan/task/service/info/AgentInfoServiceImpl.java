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
package com.yunji.titan.task.service.info;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yunji.titan.task.bean.bo.AgentInfoBO;
import com.yunji.titan.task.exception.ResourceException;
import com.yunji.titan.task.watch.WatchAgents;
import com.yunji.titan.utils.ErrorCode;
import com.yunji.titan.utils.NodePath;
import com.yunji.titan.utils.ZookeeperConnManager;

/**
 * 注册中心agent状态信息接口实现
 * 
 * @author gaoxianglong
 */
@Service
public class AgentInfoServiceImpl implements AgentInfoService {
	@Resource
	private ZookeeperConnManager zookeeperConnManager;
	@Resource
	private WatchAgents watchAgents;
	private String nodePath = NodePath.ROOT_NODEPATH;
	private Logger log = LoggerFactory.getLogger(AgentInfoService.class);

	@Override
	public int getAgentSize() {
		int agentSize = 0;
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		if (null == zkClient) {
			return agentSize;
		}
		try {
			agentSize = agentFilter(zkClient.getChildren(nodePath, false)).size();
		} catch (KeeperException | InterruptedException e) {
			log.error("error", e);
		}
		return agentSize;
	}

	@Override
	public int getFreeAgentSize() {
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		if (null == zkClient) {
			return 0;
		}
		LongAdder freeAgentSize = new LongAdder();
		List<String> childrens = null;
		try {
			childrens = agentFilter(zkClient.getChildren(nodePath, false));
		} catch (KeeperException | InterruptedException e) {
			log.error("error", e);
		}
		childrens.stream().forEach(children -> {
			Properties properties = new Properties();
			try {
				properties.load(new StringReader(new String(zkClient.getData(nodePath + "/" + children, false, null))));
				String agentStateKey = "task";
				String agentState = "false";
				if (properties.getProperty(agentStateKey).equals(agentState)) {
					freeAgentSize.increment();
				}
			} catch (IOException | KeeperException | InterruptedException e) {
				log.error("error", e);
			}
		});
		return freeAgentSize.intValue();
	}

	@Override
	public List<String> getFreeAgents() {
		List<String> nodes = new ArrayList<String>();
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		if (null == zkClient) {
			return nodes;
		}
		List<String> childrens = null;
		try {
			childrens = agentFilter(zkClient.getChildren(nodePath, false));
		} catch (KeeperException | InterruptedException e) {
			log.error("error", e);
		}
		childrens.stream().forEach(children -> {
			try {
				Properties properties = new Properties();
				properties.load(new StringReader(new String(zkClient.getData(nodePath + "/" + children, false, null))));
				String agentStateKey = "task";
				String agentState = "false";
				if (properties.getProperty(agentStateKey).equals(agentState)) {
					nodes.add(nodePath + "/" + children);
				}
			} catch (IOException | KeeperException | InterruptedException e) {
				log.error("error", e);
			}
		});
		return nodes;
	}

	@Override
	public String getAgentsHostAddress() {
		String agentInfo = null;
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		if (null == zkClient) {
			return agentInfo;
		}
		AgentInfoBO agentInfoBO = new AgentInfoBO();
		List<String> childrens = null;
		try {
			childrens = agentFilter(zkClient.getChildren(nodePath, false));
		} catch (KeeperException | InterruptedException e) {
			throw new ResourceException(e.getMessage(), ErrorCode.GET_AGENT_HOST_ADDRESS_FAILS);
		}
		childrens.stream().forEach(children -> {
			try {
				Properties properties = new Properties();
				properties.load(new StringReader(new String(zkClient.getData(nodePath + "/" + children, false, null))));
				String hostAddress = children + "/" + properties.getProperty("hostAddress");
				String agentStateKey = "task";
				if (!Boolean.parseBoolean(properties.getProperty(agentStateKey))) {
					if (null == agentInfoBO.getFreeAgents()) {
						agentInfoBO.setFreeAgents(new ArrayList<String>());
					}
					agentInfoBO.getFreeAgents().add(hostAddress);
				} else {
					if (null == agentInfoBO.getBusynessAgents()) {
						agentInfoBO.setBusynessAgents(new ArrayList<String>());
					}
					agentInfoBO.getBusynessAgents().add(hostAddress);
				}
			} catch (IOException | KeeperException | InterruptedException e) {
				throw new ResourceException(e.getMessage(), ErrorCode.GET_AGENT_HOST_ADDRESS_FAILS);
			}
		});
		agentInfo = JSON.toJSONString(agentInfoBO);
		return agentInfo;
	}

	/**
	 * 过滤注册中心根目录下的非agent节点
	 * 
	 * @author gaoxianglong
	 */
	private List<String> agentFilter(List<String> childrens) {
		return childrens.stream().filter(children -> children.startsWith("agent")).collect(Collectors.toList());
	}
}