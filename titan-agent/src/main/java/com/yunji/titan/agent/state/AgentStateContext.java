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
package com.yunji.titan.agent.state;

import javax.annotation.PostConstruct;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

/**
 * Agent状态上下文
 * 
 * @author gaoxianglong
 */
@Component
public class AgentStateContext {
	private AgentState agentState;
	private String stateInfo;

	public String getStateInfo() {
		return stateInfo;
	}

	private void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	/**
	 * 更改状态
	 * 
	 * @author gaoxianglong
	 */
	public void setState(AgentState agentState) {
		this.agentState = agentState;
		if (agentState instanceof FreeState) {
			this.setStateInfo("空闲状态");
		} else if (agentState instanceof StopState) {
			this.setStateInfo("暂停状态");
		} else if (agentState instanceof BusynessState) {
			this.setStateInfo("忙碌状态");
		}
	}

	public Stat opretion(ZooKeeper zkClient, String nodePath) throws KeeperException, InterruptedException {
		return agentState.opretion(zkClient, nodePath);
	}
}