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
package com.yunji.titan.manager.impl.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yunji.titan.manager.dao.MonitorDao;
import com.yunji.titan.manager.entity.Monitor;
import com.yunji.titan.manager.service.MonitorService;

/**
 * @desc  机器资源情况service接口实现类
 *
 * @author liuliang
 *
 */
@Service
public class MonitorServiceImpl implements MonitorService{

	@Resource
	private MonitorDao monitorDao;
	/**
	 * @desc 查询指定条数记录
	 *
	 * @author liuliang
	 *
	 * @param size 记录数
	 * @return List<Monitor>
	 */
	@Override
	public List<Monitor> getMonitorList(int size) throws Exception {
		return monitorDao.queryList(size);
	}
	
	/**
	 * @desc 查询指定条数记录(筛选去除符合时间范围的数据)
	 *
	 * @author liuliang
	 *
	 * @param size 记录数
	 * @return List<Monitor>
	 */
	@Override
	public List<Monitor> getMonitorFilterList(int size) throws Exception {
		List<Monitor> returnList = new ArrayList<Monitor>();
		//1、查询符合条件的IP集合
		List<String> ipList = monitorDao.queryIPList(0,1);
		if((null != ipList) && (0 < ipList.size())) {
			//2.1、获取数据集合
			List<Monitor> dataList1 = new ArrayList<Monitor>();
			if(StringUtils.isNotBlank(ipList.get(0))) {
				dataList1 = monitorDao.queryList(ipList.get(0),size);
				if((null != dataList1) && (0 < dataList1.size())) {
					returnList.addAll(dataList1);
				}
			}
		}
		//3、返回
		return returnList;
	}

	/**
	 * @desc 压测报告详情页 图表数据
	 *
	 * @author liuliang
	 *
	 * @param ips
	 */
	@Override
	public List<Monitor> getReportDetailMonitorData(String ips) throws Exception {
		return  monitorDao.queryListByIP(1,ips);
	}

	/**
	 * @desc 压测报告详情页 图表数据 （时间区间）
	 *
	 * @author liuliang
	 *
	 * @param ips
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public List<Monitor> getReportDetailMonitorData(String ips, long startTime, long endTime) throws Exception {
		return  monitorDao.queryListByIP(1,ips,startTime,endTime);
	}
	
}
