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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yunji.titan.manager.bo.MonitorSetBO;
import com.yunji.titan.manager.bo.ReportBO;
import com.yunji.titan.manager.dao.MonitorDao;
import com.yunji.titan.manager.dao.MonitorSetDao;
import com.yunji.titan.manager.entity.Monitor;
import com.yunji.titan.manager.entity.MonitorSet;
import com.yunji.titan.manager.service.MonitorService;
import com.yunji.titan.manager.service.MonitorSetService;

/**
 * @desc 监控集service接口实现类
 *
 * @author liuliang
 *
 */
@Service
public class MonitorSetServiceImpl implements MonitorSetService{

	Logger logger = LoggerFactory.getLogger(MonitorSetServiceImpl.class);

	@Resource
	private MonitorSetDao monitorSetDao;
	
	@Resource
	private MonitorDao monitorDao;
	/**
	 * monitor信息服务接口
	 */
	@Resource
	private MonitorService monitorService;
	/**
	 * @desc 查询总记录数
	 *
	 * @author liuliang
	 *
	 * @return
	 */
	@Override
	public int getCount() throws Exception {
		return monitorSetDao.getCount();
	}

	/**
	 * @desc 分页查询数据列表
	 *
	 * @author liuliang
	 *
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<MonitorSetBO> getDataList(int pageIndex, int pageSize) throws Exception {
		List<MonitorSet> list = monitorSetDao.getDataList(pageIndex, pageSize);
		List<MonitorSetBO> boList = new ArrayList<MonitorSetBO>();
		if((null != list) && (0 < list.size())){
			MonitorSetBO monitorSetBO = null;
			for(MonitorSet monitorSet:list){
				monitorSetBO = new MonitorSetBO();
				BeanUtils.copyProperties(monitorSet, monitorSetBO);
				boList.add(monitorSetBO);
			}
		}
		return boList;
	}

	/**
	 * @desc 删除数据
	 *
	 * @author liuliang
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public int del(String ids) throws Exception {
		return monitorSetDao.del(ids);
	}

	/**
	 * @desc 新增
	 *
	 * @author liuliang
	 *
	 * @param monitorSetBO
	 * @return
	 */
	@Override
	public int add(MonitorSetBO monitorSetBO) throws Exception {
		return monitorSetDao.add(monitorSetBO);
	}

	/**
	 * @desc 修改
	 *
	 * @author liuliang
	 *
	 * @param monitorSetBO
	 * @return
	 */
	@Override
	public int update(MonitorSetBO monitorSetBO) throws Exception {
		return monitorSetDao.update(monitorSetBO);
	}
	/**
	 * @desc 压测报告详情页 图表数据
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param sceneId 场景ID
	 * @return
	 */
	@Override
	public Map<String,List<Monitor>> getReportDetailMonitorData(ReportBO reportBO) throws Exception {
		Map<String,List<Monitor>> returnMap = new HashMap<String,List<Monitor>>(16);
		//1、查监控集
		MonitorSet monitorSet = monitorSetDao.query(reportBO.getSceneId());
		if(null != monitorSet) {
			String ips = monitorSet.getIntranetIp();
			List<Monitor> tempList = monitorService.getReportDetailMonitorData(ips,reportBO.getStartTime(),reportBO.getEndTime());
			
			if((null != tempList) && (0 < tempList.size())) {
				List<Monitor> list1 = new ArrayList<Monitor>();
				List<Monitor> list2 = new ArrayList<Monitor>();
				List<Monitor> list3 = new ArrayList<Monitor>();
				String ip1 = null;
				String ip2 = null;
				String ip3 = null;
				String[] ipArr = ips.split("\\,");
				if(1 == ipArr.length) {
					ip1 = ipArr[0];
				}else if(2 == ipArr.length) {
					ip1 = ipArr[0];
					ip2 = ipArr[1];
				}else if(3 == ipArr.length){
					ip1 = ipArr[0];
					ip2 = ipArr[1];
					ip3 = ipArr[2];
				}
				for(Monitor m:tempList) {
					String mIP = m.getIp();
					if(mIP.equals(ip1)) {
						list1.add(m);
					}else if(mIP.equals(ip2)) {
						list2.add(m);
					}else if(mIP.equals(ip3)) {
						list3.add(m);
					}
				}
				if((null != ip1) && (0 < list1.size())) {
					returnMap.put(ip1, list1);
				}
				if((null != ip2) && (0 < list2.size())) {
					returnMap.put(ip2, list2);
				}
				if((null != ip2) && (0 < list3.size())) {
					returnMap.put(ip3, list3);
				}
			}
		}
		//返回	
		return returnMap;
	}

	/**
	 * @desc 删除指定时间之前的monitor agent数据
	 *
	 * @author liuliang
	 *
	 * @param breforeTime 多久之前(单位：s)
	 * @return int 受影响的记录数
	 */
	@Override
	public int delAgentMonitorByTime(Long breforeTime) throws Exception {
		//计算当前系统时间breforeTime秒的时间
		Long time = System.currentTimeMillis() - breforeTime * 1000;
		return monitorDao.delBreforeTime(0,time);
	}

	/**
	 * @desc 删除指定时间之前的monitor agent数据
	 *
	 * @author liuliang
	 *
	 * @param breforeTime 多久之前(单位：s)
	 * @return int 受影响的记录数
	 */
	@Override
	public int delTargetMonitorByTime(Long breforeTime) throws Exception {
		//计算当前系统时间breforeTime秒的时间
		Long time = System.currentTimeMillis() - breforeTime * 1000;
		return monitorDao.delBreforeTime(1,time);
	}
	
}
