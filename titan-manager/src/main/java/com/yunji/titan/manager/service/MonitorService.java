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

import java.util.List;

import com.yunji.titan.manager.entity.Monitor;

/**
 * 机器资源情况service接口
 *
 * @author liuliang
 *
 */
public interface MonitorService {

	/**
	 * 查询指定条数记录
	 *
	 * @author liuliang
	 *
	 * @param size 记录数
	 * @return List<Monitor>
	 * @throws Exception
	 */
	List<Monitor> getMonitorList(int size) throws Exception;
	
	/**
	 * 查询指定条数记录(筛选去除符合时间范围的数据)
	 *
	 * @author liuliang
	 *
	 * @param size 记录数
	 * @return List<Monitor>
	 * @throws Exception
	 */
	List<Monitor> getMonitorFilterList(int size) throws Exception;

	/**
	 * 压测报告详情页 图表数据
	 * 
	 * @author liuliang
	 *
	 * @param ips
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	List<Monitor> getReportDetailMonitorData(String ips) throws Exception;

	/**
	 * 压测报告详情页 图表数据 （时间区间）
	 *
	 * @author liuliang
	 *
	 * @param ips
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	List<Monitor> getReportDetailMonitorData(String ips, long startTime, long endTime) throws Exception;
}
