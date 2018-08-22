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
package com.yunji.titan.manager.job;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yunji.titan.manager.service.MonitorSetService;
import com.yunji.titan.manager.service.ReportService;

/**
 * @desc 定时清除监控数据
 *
 * @author liuliang
 *
 */
@Component
public class MonitorTask {

	private Logger logger = LoggerFactory.getLogger(MonitorTask.class);
	
	@Resource
	private MonitorSetService monitorSetService;
	
	/**
	 * 测试报告服务
	 */
	@Resource
	private ReportService reportService;
	
	@Value("${monitor.agent.del.time}")
	private Long monitorAgentDelTime;
	@Value("${monitor.target.del.time}")
	private Long monitorTargetDelTime;
	
	/**
	 * @desc 清除agent机器数据,每隔1小时1次
	 *
	 * @author liuliang
	 *
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
    public void delAgentJob() {
		try {
			int result = monitorSetService.delAgentMonitorByTime(monitorAgentDelTime);
			logger.error("定时清除monitor agent记录数:{}",result);
		} catch (Exception e) {
			logger.error("定时清除monitor agent数据异常",e);
		}
    }
	
	/**
	 * @desc 清除target机器数据，每天晚上12点1次
	 *
	 * @author liuliang
	 *
	 */
	@Scheduled(cron = "0 0 0 1/1 * ?")
    public void delReportDataJob() {
		try {
			//1、删除测试报告
			int s1 = reportService.delByTime(monitorTargetDelTime);
			logger.error("定时清除report记录数:{}",s1);
			
			//2、删除target监控数据
			int result = monitorSetService.delTargetMonitorByTime(monitorTargetDelTime);
			logger.error("定时清除monitor target记录数:{}",result);
		} catch (Exception e) {
			logger.error("定时清除monitor target数据异常",e);
		}
    }
}
