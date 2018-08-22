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

/**
 * RPC接口出参模板类实现,下发压测指令后,agent领取压测任务
 * 
 * @author gaoxianglong
 */
@Component("pullTask")
public class PullTask implements ResultTemplate<String, String> {
	@Resource
	private TaskService taskService;

	@Override
	public Result<String> invoke(String zNode) {
		String taskInfo = taskService.pullTask(zNode);
		return new Result<String>().getResult(true, ErrorCode.PULL_TASK_SUCCESS, taskInfo);
	}
}
