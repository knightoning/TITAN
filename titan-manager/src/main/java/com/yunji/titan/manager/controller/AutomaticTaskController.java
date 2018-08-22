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

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunji.titan.manager.bo.AutomaticTaskBO;
import com.yunji.titan.manager.bo.PageRequestBO;
import com.yunji.titan.manager.bo.Pager;
import com.yunji.titan.manager.common.Groups;
import com.yunji.titan.manager.common.TaskSyncTypeEnum;
import com.yunji.titan.manager.entity.AutomaticTask;
import com.yunji.titan.manager.result.ComponentResult;
import com.yunji.titan.manager.result.Result;
import com.yunji.titan.manager.service.AutomaticTaskService;
import com.yunji.titan.manager.utils.ResultUtil;
import com.yunji.titan.manager.utils.ValidatorUtil;
import com.yunji.titan.manager.utils.timertask.DistributedTimerTask;
import com.yunji.titan.utils.ErrorCode;

/**
 * @desc 自动压测任务管理Controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/task")
public class AutomaticTaskController implements ApplicationListener<ContextRefreshedEvent>{
	private Logger logger = LoggerFactory.getLogger(AutomaticTaskController.class);

	/**
	 * 自动压测任务服务
	 */
	@Resource
	private AutomaticTaskService automaticTaskService;
	/**
	 * 定时任务服务
	 */
	@Resource
	private DistributedTimerTask distributedTimerTask;

	/**
	 * @desc 分页列表
	 *
	 * @author liuliang
	 *
	 * @param pr
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ComponentResult<Pager<AutomaticTaskBO>> list(@Validated PageRequestBO pr,BindingResult br) {
		ComponentResult<Pager<AutomaticTaskBO>> componentResult = new ComponentResult<Pager<AutomaticTaskBO>>();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, componentResult);
		}
		//查询
		try {
			int totalCount = automaticTaskService.getCount();
			List<AutomaticTaskBO> records = automaticTaskService.getDataList(pr.getPageIndex(),pr.getPageSize());
	
			componentResult.setData(new Pager<AutomaticTaskBO>(totalCount, records));
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
	 * @param automaticTaskBO
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Result add(@Validated(Groups.Add.class) AutomaticTaskBO automaticTaskBO,BindingResult br) {
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		//新增
		try {
			int addResult = automaticTaskService.add(automaticTaskBO);
			if(0 < addResult) {
				// 同步定时任务
				this.syncAutomaticTask(automaticTaskBO, TaskSyncTypeEnum.UPDATE.value);
				return ResultUtil.success(result);
			}
		} catch(DuplicateKeyException de){
			logger.error("新增数据异常,主键重复,params:{}",automaticTaskBO.toString(),de);
			return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,"主键重复",result);
		} catch (Exception e) {
			logger.error("新增数据异常,params:{}",automaticTaskBO.toString(),e);
		}
		//返回失败结果
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}

	/**
	 * @desc  删除
	 *
	 * @author liuliang
	 *
	 * @param automaticTaskBO
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Result del(@Validated(Groups.Query.class) AutomaticTaskBO automaticTaskBO,BindingResult br) {
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		//删除
		try {
			AutomaticTask automaticTask = automaticTaskService.getDataDetail(automaticTaskBO.getAutomaticTaskId());
			int delResult = automaticTaskService.del(automaticTaskBO.getAutomaticTaskId());
			if(0 < delResult) {
				// 同步定时任务
				AutomaticTaskBO obj = new AutomaticTaskBO();
				BeanUtils.copyProperties(automaticTask, obj);
				this.syncAutomaticTask(obj, TaskSyncTypeEnum.DELETE.value);
				
				return ResultUtil.success(result);
			}
		} catch (Exception e) {
			logger.error("删除数据异常,params:{}",automaticTaskBO.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 更新
	 *
	 * @author liuliang
	 *
	 * @param automaticTaskBO
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Result update(@Validated(Groups.Update.class) AutomaticTaskBO automaticTaskBO,BindingResult br) {
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		//更新
		try {
			int updateResult = automaticTaskService.update(automaticTaskBO);
			if (0 < updateResult) {
				// 同步定时任务
				this.syncAutomaticTask(automaticTaskBO, TaskSyncTypeEnum.UPDATE.value);
				return ResultUtil.success(result);
			}
		} catch (Exception e) {
			logger.error("更新数据异常,params:{}",automaticTaskBO.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 查
	 *
	 * @author liuliang
	 *
	 * @param automaticTaskBO
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public ComponentResult<AutomaticTask> get(@Validated(Groups.Query.class) AutomaticTaskBO automaticTaskBO,BindingResult br) {
		ComponentResult<AutomaticTask> componentResult = new ComponentResult<AutomaticTask>();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, componentResult);
		}
		//查询
		try {
			AutomaticTask automaticTask = automaticTaskService.getDataDetail(automaticTaskBO.getAutomaticTaskId());
			if(null != automaticTask) {
				componentResult.setData(automaticTask);
				return ResultUtil.success(componentResult);
			}
		} catch (Exception e) {
			logger.error("查询数据详情异常,params:{}",automaticTaskBO.toString(),e);
		}		
		//失败返回
		return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,componentResult);
	}

	/**
	 * @desc 同步定时任务
	 *
	 * @author liuliang
	 *
	 * @param automaticTask
	 *            任务内容
	 * @param syncType
	 *            同步类型 1：更新 2、删除
	 * @return
	 */
	private boolean syncAutomaticTask(AutomaticTaskBO automaticTaskBO, int syncType) {
		logger.info("更新自动压测任务,syncType:" + syncType + "," + automaticTaskBO.toString());
		long sceneId = automaticTaskBO.getSceneId();
		if (TaskSyncTypeEnum.UPDATE.value == syncType) { 	
			return distributedTimerTask.addTask(String.valueOf(sceneId));
		} else if (TaskSyncTypeEnum.DELETE.value == syncType) {   
			return distributedTimerTask.deleteTask(String.valueOf(sceneId));
		}
		return false;
	}

	/**
	 * @desc 启动时初始化定时压测任务
	 *
	 * @author liuliang
	 *
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("---初始化自动化压测任务----");
		 try {
			 List<AutomaticTaskBO> dataList = automaticTaskService.getDataList(0,automaticTaskService.getCount());
			 if((null != dataList) && (0 < dataList.size())){
				 for(AutomaticTaskBO automaticTaskBO:dataList){
					 this.syncAutomaticTask(automaticTaskBO,TaskSyncTypeEnum.UPDATE.value);
				 }
			 }
		 } catch (Exception e) {
			 logger.error("初始化所有自动压测任务异常",e);
		 }
	}
	 
}