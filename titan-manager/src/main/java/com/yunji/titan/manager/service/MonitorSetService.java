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
import java.util.Map;

import com.yunji.titan.manager.bo.MonitorSetBO;
import com.yunji.titan.manager.bo.ReportBO;
import com.yunji.titan.manager.entity.Monitor;

/**
 * 监控集service接口
 *
 * @author liuliang
 *
 */
public interface MonitorSetService {

	/**
	 * 查询总记录数
	 *
	 * @author liuliang
	 *
	 * @return
	 * @throws Exception
	 */
	int getCount() throws Exception;

	/**
	 * 分页查询数据列表
	 *
	 * @author liuliang
	 *
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	List<MonitorSetBO> getDataList(int pageIndex, int pageSize) throws Exception;

	/**
	 * 删除数据
	 *
	 * @author liuliang
	 *
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	int del(String ids) throws Exception;

	/**
	 * 新增
	 *
	 * @author liuliang
	 *
	 * @param monitorSetBO
	 * @return
	 * @throws Exception
	 */
	int add(MonitorSetBO monitorSetBO) throws Exception;

	/**
	 * 修改
	 *
	 * @author liuliang
	 *
	 * @param monitorSetBO
	 * @return
	 * @throws Exception
	 */
	int update(MonitorSetBO monitorSetBO) throws Exception;

	/**
	 * 压测报告详情页 图表数据
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param reportBO 
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<Monitor>> getReportDetailMonitorData(ReportBO reportBO) throws Exception;

	/**
	 * 删除指定时间之前的monitor agent数据
	 *
	 * @author liuliang
	 *
	 * @param breforeTime 多久之前(单位：s)
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int delAgentMonitorByTime(Long breforeTime) throws Exception;

	/**
	 * 删除指定时间之前的monitor agent数据
	 *
	 * @author liuliang
	 *
	 * @param breforeTime 多久之前(单位：s)
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int delTargetMonitorByTime(Long breforeTime)  throws Exception;

}
