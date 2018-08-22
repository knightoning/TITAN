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
package com.yunji.titan.manager.service;

import com.yunji.titan.manager.bo.ActionPerformanceBO;
import com.yunji.titan.utils.TaskIssuedBean;

/**
 * 操作Service接口
 *
 * @author liuliang
 *
 */
public interface OperateService {

	/**
	 * 获取执行压测操作参数
	 *
	 * @author liuliang
	 *
	 * @param sceneId 场景ID
	 * @return ActionPerformanceBO
	 * @throws Exception
	 */
	ActionPerformanceBO getActionParamer(long sceneId) throws Exception;

	/**
	 * 自动化压测任务
	 * 
	 * @author liuliang
	 *
	 * @param sceneId
	 * @param executeNum
	 * @throws Exception
	 */
	void doAutomaticTask(long sceneId,int executeNum) throws Exception;
	
	/**
	 * 压测参数bean转换
	 *
	 * @author liuliang
	 *
	 * @param ap
	 * @param tb
	 */
	void copyBeanProperties(ActionPerformanceBO ap, TaskIssuedBean tb);
}
