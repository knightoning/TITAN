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
package com.yunji.titan.manager.utils.timertask;

/**
 * 分布式定时任务
 * 
 * @author gaoxianglong
 */
public interface DistributedTimerTask {
	/**
	 * 添加分布式定时任务
	 * 
	 * @author gaoxianglong
	 * 
	 * @param taskName
	 *            任务名称
	 * 
	 * @exception Exception
	 * 
	 * @return boolean 任务添加结果
	 */
	public boolean addTask(String taskName);

	/**
	 * 删除分布式定时任务
	 * 
	 * @author gaoxianglong
	 * 
	 * @param taskName
	 *            任务名称
	 * 
	 * @exception Exception
	 * 
	 * @return boolean 任务删除结果
	 */
	public boolean deleteTask(String taskName);
}
