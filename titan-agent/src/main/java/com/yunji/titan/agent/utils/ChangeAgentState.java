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
package com.yunji.titan.agent.utils;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import com.yunji.titan.agent.state.AgentState;
import com.yunji.titan.agent.state.AgentStateContext;

/**
 * 更改Agent状态工具类
 * 
 * @author gaoxianglong
 */
public class ChangeAgentState {
	private static Logger log = Logger.getLogger(ChangeAgentState.class);

	/**
	 * Agent自身状态变更
	 * 
	 * @author gaoxianglong
	 * 
	 * @param agentStateContext
	 * 
	 * @param state
	 * 
	 * @param zkClient
	 * 
	 * @param nodePath
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public static void change(AgentStateContext agentStateContext, AgentState state, ZooKeeper zkClient,
			String nodePath) throws KeeperException, InterruptedException {
		agentStateContext.setState(state);
		Stat stat = agentStateContext.opretion(zkClient, nodePath);
		if (null != stat) {
			log.info("zNode为" + nodePath + "的当前状态已经更新为" + agentStateContext.getStateInfo());
		}
	}
}