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
package com.yunji.titan.task.service.assign;

import com.yunji.titan.utils.TaskIssuedBean;

/**
 * 任务编排和任务下发接口
 * 
 * @author gaoxianglong
 */
public interface TaskService {
	/**
	 * 启动压测
	 * 
	 * @author gaoxianglong
	 * 
	 * @param taskIssuedBean
	 * 
	 * @return void
	 */
	public void startPerformanceTest(TaskIssuedBean taskIssuedBean);

	/**
	 * 给指定场景下所有正在执行压测任务的agent发出interrupted信号,但压测任务并不会立即停止
	 *
	 * @author gaoxianglong
	 * 
	 * @param senceId
	 *            场景ID
	 * 
	 * @return void
	 */
	public void stopPerformanceTest(Integer senceId);

	/**
	 * 下发压测指令后,agent领取压测任务
	 * 
	 * @author gaoxianglong
	 * 
	 * @param zNode
	 *            agent的zNode
	 * 
	 * @return String
	 */
	public String pullTask(String zNode);
}