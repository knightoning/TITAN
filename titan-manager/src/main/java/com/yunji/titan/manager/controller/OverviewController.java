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
package com.yunji.titan.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yunji.titan.manager.bo.AgentInfoBO;
import com.yunji.titan.manager.bo.OverviewResultBO;
import com.yunji.titan.manager.entity.Monitor;
import com.yunji.titan.manager.result.ComponentResult;
import com.yunji.titan.manager.service.LinkService;
import com.yunji.titan.manager.service.MonitorService;
import com.yunji.titan.manager.service.ReportService;
import com.yunji.titan.manager.service.SceneService;
import com.yunji.titan.manager.utils.ResultUtil;
import com.yunji.titan.task.facade.TaskFacade;
import com.yunji.titan.utils.ErrorCode;

/**
 * @desc 概览页相关数据Controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/overview")
public class OverviewController {

	private Logger logger = LoggerFactory.getLogger(OverviewController.class);
	/**
	 * 链路服务接口
	 */
	@Resource
	private LinkService linkService;
	/**
	 * 场景服务接口
	 */
	@Resource
	private SceneService sceneService;
	/**
	 * 测试报告服务接口
	 */
	@Resource
	private ReportService reportService;
	/**
	 * monitor信息服务接口
	 */
	@Resource
	private MonitorService monitorService;
	/**
	 * agentchart数据点数量
	 */
	@Value("${agent.chart.datanum}")
	private int agentChartDatanum;
	
	@Resource
	private TaskFacade taskFacade;
	
	/**
	 * @desc 获取概览页数据
	 *
	 * @author liuliang
	 *
	 * @return
	 */
	@RequestMapping(value = "/getOverviewData")
	@ResponseBody
	public ComponentResult<OverviewResultBO> getOverviewData(){
		ComponentResult<OverviewResultBO> componentResult = new ComponentResult<OverviewResultBO>();
		OverviewResultBO data = new OverviewResultBO();
		//1、查当前agent状态
		int totalNodesNum = 0, availableNodesNum = 0;
		List<String> availableNodesIPList = new ArrayList<String>();
		List<String> usedNodesIPList = new ArrayList<String>();
		com.yunji.titan.utils.Result<String> result = taskFacade.getAgentsHostAddress();
		if(result.isSuccess()) {
			AgentInfoBO agentInfoBO = JSON.parseObject(result.getData(), AgentInfoBO.class);
			if(null != agentInfoBO.getFreeAgents()) {
				availableNodesIPList = agentInfoBO.getFreeAgents();
				availableNodesNum = availableNodesIPList.size();
			}
			if(null != agentInfoBO.getBusynessAgents()) {
				usedNodesIPList = agentInfoBO.getBusynessAgents();
			}
			totalNodesNum = availableNodesNum + usedNodesIPList.size();
		}
		data.setTotalNodesNum(totalNodesNum);
		data.setAvailableNodesNum(availableNodesNum);
		data.setAvailableNodesIPList(availableNodesIPList);
		data.setUsedNodesIPList(usedNodesIPList);
		//2、查数据统计
		try {
			int totalLinkNum = linkService.getLinkCount();
			int totalSceneNum = sceneService.getSceneCount();
			int totalReportNum = reportService.getReportCount();
			data.setTotalLinkNum(totalLinkNum);
			data.setTotalSceneNum(totalSceneNum);
			data.setTotalReportNum(totalReportNum);
		} catch (Exception e) {
			logger.error("查概览页统计数据异常",e);
		}
		//3、返回
		componentResult.setData(data);
		return ResultUtil.success(componentResult);
	}
	
	/**
	 * @desc 获取agent节点的monitor信息
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @return BaseResponseBO
	 */
	@RequestMapping(value = "/monitor")
	@ResponseBody
	public ComponentResult<List<Monitor>> getMonitorList(HttpServletRequest request) {
		ComponentResult<List<Monitor>> componentResult = new ComponentResult<List<Monitor>>();
		try {
			List<Monitor> list = monitorService.getMonitorFilterList(agentChartDatanum);
			
			componentResult.setData(list);
			return ResultUtil.success(componentResult);
		} catch (Exception e) {
			logger.error("概览页monitor,查询DB异常",e);
		}
		return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,componentResult);
	}
}
