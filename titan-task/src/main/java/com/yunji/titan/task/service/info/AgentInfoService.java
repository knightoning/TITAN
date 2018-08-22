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

import java.util.List;

/**
 * 注册中心agent状态信息接口
 * 
 * @author gaoxianglong
 */
public interface AgentInfoService {
	/**
	 * 获取注册中心内当前的agent总节点数
	 * 
	 * @author gaoxianglong
	 * 
	 * @return int
	 */
	public int getAgentSize();

	/**
	 * 获取注册中心内当前空闲的agent节点数
	 * 
	 * @author gaoxianglong
	 * 
	 * @return int
	 */
	public int getFreeAgentSize();

	/**
	 * 获取注册中心内当前空闲的agent的znode信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @return List<String>
	 */
	public List<String> getFreeAgents();

	/**
	 * 获取注册中心所有的agent的hostAddress信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @return String agent信息bean
	 */
	public String getAgentsHostAddress();
}