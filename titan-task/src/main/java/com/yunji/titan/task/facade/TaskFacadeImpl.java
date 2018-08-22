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

import javax.annotation.Resource;

import com.yunji.titan.task.template.GetAgentsHostAddress;
import com.yunji.titan.task.template.PullTask;
import com.yunji.titan.task.template.StartPerformanceTest;
import com.yunji.titan.task.template.StopPerformanceTest;
import com.yunji.titan.utils.Result;
import com.yunji.titan.utils.TaskIssuedBean;

/**
 * Task服务外观类实现
 * 
 * @author gaoxianglong
 */
public class TaskFacadeImpl implements TaskFacade {
	@Resource(name = "startPerformanceTest")
	private StartPerformanceTest startPerformanceTest;

	@Resource(name = "stopPerformanceTest")
	private StopPerformanceTest stopPerformanceTest;

	@Resource(name = "pullTask")
	private PullTask pullTask;

	@Resource(name = "getAgentsHostAddress")
	private GetAgentsHostAddress getAgentsHostAddress;

	@Override
	public Result<?> startPerformanceTest(TaskIssuedBean taskIssuedBean) {
		return startPerformanceTest.getResult(taskIssuedBean);
	}

	@Override
	public Result<?> stopPerformanceTest(Integer senceId) {
		return stopPerformanceTest.getResult(senceId);
	}

	@Override
	public Result<String> pullTask(String zNode) {
		return pullTask.getResult(zNode);
	}

	@Override
	public Result<String> getAgentsHostAddress() {
		return getAgentsHostAddress.getResult(null);
	}
}