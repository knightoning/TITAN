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
package com.yunji.titan.task.facade;

import com.yunji.titan.utils.Result;
import com.yunji.titan.utils.TaskIssuedBean;

/**
 * Task服务外观类,将需要对外提供的接口进行统一封装
 * 
 * @author gaoxianglong
 */
public interface TaskFacade {
	/**
	 * 启动压测
	 * 
	 * @author gaoxianglong
	 * 
	 * @param taskIssuedBean
	 *            任务下发Bean
	 * 
	 * @return Result
	 */
	public Result<?> startPerformanceTest(TaskIssuedBean taskIssuedBean);

	/**
	 * 停止压测
	 * 
	 * @author gaoxianglong
	 * 
	 * @param senceId
	 *            场景ID
	 * 
	 * @return Result
	 */
	public Result<?> stopPerformanceTest(Integer senceId);

	/**
	 * agent领取压测任务
	 * 
	 * @author gaoxianglong
	 * 
	 * @param zNode
	 *            agent的zNode
	 * 
	 * @return Result<String>
	 */
	public Result<String> pullTask(String zNode);

	/**
	 * 获取注册中心所有的agent的hostAddress信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @return Result<String>
	 */
	public Result<String> getAgentsHostAddress();
}