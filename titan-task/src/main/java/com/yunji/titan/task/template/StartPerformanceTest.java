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
package com.yunji.titan.task.template;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.yunji.titan.task.service.assign.TaskService;
import com.yunji.titan.utils.ErrorCode;
import com.yunji.titan.utils.Result;
import com.yunji.titan.utils.TaskIssuedBean;

/**
 * RPC接口出参模板类实现,启动压测
 * 
 * @author gaoxianglong
 */
@Component("startPerformanceTest")
public class StartPerformanceTest implements ResultTemplate<Object, TaskIssuedBean> {
	@Resource
	private TaskService taskService;

	@Override
	public Result<Object> invoke(TaskIssuedBean taskIssuedBean) {
		taskService.startPerformanceTest(taskIssuedBean);
		return new Result<Object>().getResult(true, ErrorCode.START_PERFORMANCE_TEST_SUCCESS);
	}
}