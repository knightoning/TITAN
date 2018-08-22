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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunji.titan.manager.bo.MonitorSetBO;
import com.yunji.titan.manager.bo.Pager;
import com.yunji.titan.manager.bo.PageRequestBO;
import com.yunji.titan.manager.bo.ReportBO;
import com.yunji.titan.manager.common.Groups;
import com.yunji.titan.manager.entity.Monitor;
import com.yunji.titan.manager.entity.Scene;
import com.yunji.titan.manager.result.ComponentResult;
import com.yunji.titan.manager.result.Result;
import com.yunji.titan.manager.service.MonitorSetService;
import com.yunji.titan.manager.service.SceneService;
import com.yunji.titan.manager.utils.ResultUtil;
import com.yunji.titan.manager.utils.ValidatorUtil;
import com.yunji.titan.utils.ErrorCode;

/**
 * @desc 监控管理Controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/monitor")
public class MonitorSetController {

	private Logger logger = LoggerFactory.getLogger(MonitorSetController.class);
	
	@Resource
	private MonitorSetService monitorSetService;
	/**
	 * 场景服务
	 */
	@Resource
	private SceneService sceneService;
	
	/**
	 * @desc 分页列表查询
	 *
	 * @author liuliang
	 *
	 * @param pr
	 * @return ComponentResult<Pager<MonitorSetBO>>
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ComponentResult<Pager<MonitorSetBO>> list(@Validated PageRequestBO pr,BindingResult br) {
		ComponentResult<Pager<MonitorSetBO>> componentResult = new ComponentResult<Pager<MonitorSetBO>>();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, componentResult);
		}
		//查询
		try {
			int totalCount = monitorSetService.getCount();
			List<MonitorSetBO> records = monitorSetService.getDataList(pr.getPageIndex(),pr.getPageSize());
			
			componentResult.setData(new Pager<MonitorSetBO>(totalCount, records));
			return ResultUtil.success(componentResult);
		} catch (Exception e) {
			logger.error("分页获取数据异常,params:{}",pr.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,componentResult);
	}
	
	/**
	 * @desc 新增
	 *
	 * @author liuliang
	 *
	 * @param monitorSetBO
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Result add(@Validated(Groups.Add.class) MonitorSetBO monitorSetBO,BindingResult br) {
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		//新增
		try {
			Scene scene = sceneService.getSceneByName(monitorSetBO.getSceneName());
			if(null == scene) {
				return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,"场景名不存在",result);
			}
			monitorSetBO.setSceneId(scene.getSceneId());
			int addResult = monitorSetService.add(monitorSetBO);
			if(0 < addResult) {
				return ResultUtil.success(result);
			}
		} catch (DuplicateKeyException de) {
			logger.error("新增异常,唯一索引重复,params:{}", monitorSetBO.toString(), de);
			return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,"场景名重复",result);
		} catch (Exception e) {
			logger.error("新增数据异常,params:{}",monitorSetBO.toString(),e);
		}
		//返回失败结果
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 删除
	 *
	 * @author liuliang
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Result del(@RequestParam("ids") String ids) {
		Result result = new Result();
		//参数校验
		if(StringUtils.isBlank(ids)){
			logger.warn("删除数据,参数错误,ids:{}",ids);
			return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,result);
		}
		//删除
		try {
			int delResult = monitorSetService.del(ids);
			if(0 < delResult) {
				return ResultUtil.success(result);
			}
		} catch (Exception e) {
			logger.error("删除数据异常,ids:{}",ids,e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}

	/**
	 * @desc 更新
	 *
	 * @author liuliang
	 *
	 * @param monitorSetBO
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Result update(@Validated(Groups.Update.class) MonitorSetBO monitorSetBO,BindingResult br) {
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		//更新
		try {
			Scene scene = sceneService.getSceneByName(monitorSetBO.getSceneName());
			if(null == scene) {
				return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,"场景名不存在",result);
			}
			int updateResult = monitorSetService.update(monitorSetBO);
			if(0 < updateResult) {
				return ResultUtil.success(result);
			}
		} catch (DuplicateKeyException de) {
			logger.error("更新异常,唯一索引重复,params:{}", monitorSetBO.toString(), de);
			return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,"场景名重复",result);
		} catch (Exception e) {
			logger.error("更新数据异常,params:{}",monitorSetBO.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 压测报告详情页 图表数据
	 *
	 * @author liuliang
	 *
	 * @param reportBO
	 * @return
	 */
	@RequestMapping(value = "/chartdetail")
	@ResponseBody
	public ComponentResult<Object> chartdetail(@Validated(Groups.Query.class) ReportBO reportBO,BindingResult br) {
		ComponentResult<Object> componentResult = new ComponentResult<Object>();
		try {
			Map<String,List<Monitor>> map = monitorSetService.getReportDetailMonitorData(reportBO);
			componentResult.setData(map);
			return ResultUtil.success(componentResult);
		} catch (Exception e) {
			logger.error("压测报告详情,查询监控数据DB异常",e);
		}
		return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,componentResult);
	}
}
