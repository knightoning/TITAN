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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunji.titan.manager.bo.ActionPerformanceBO;
import com.yunji.titan.manager.bo.OperateBO;
import com.yunji.titan.manager.common.Groups;
import com.yunji.titan.manager.common.OperateTypeConstant;
import com.yunji.titan.manager.common.SceneStatusEnum;
import com.yunji.titan.manager.result.Result;
import com.yunji.titan.manager.service.AgentService;
import com.yunji.titan.manager.service.LinkService;
import com.yunji.titan.manager.service.OperateService;
import com.yunji.titan.manager.service.SceneService;
import com.yunji.titan.manager.utils.ResultUtil;
import com.yunji.titan.manager.utils.ValidatorUtil;
import com.yunji.titan.task.facade.TaskFacade;
import com.yunji.titan.utils.ErrorCode;
import com.yunji.titan.utils.TaskIssuedBean;

/**
 * @desc 操作Controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/operate")
public class OperateController {
	
	private Logger logger = LoggerFactory.getLogger(OperateController.class);
	
	/**
	 * 场景服务
	 */
	@Resource
	private SceneService sceneService;
	/**
	 * 链路服务
	 */
	@Resource
	private LinkService linkService;
	/**
	 * Agent命令执行服务
	 */
	@Resource
	private AgentService agentService;
	/**
	 * 操作服务
	 */
	@Resource
	private OperateService operateService;
	
	@Resource
	private TaskFacade taskFacade;
	
	/**
	 * Agent命令脚本路径
	 */
	@Value("${start.agent.path}") 
	private String startAgentPath;
	@Value("${stop.agent.path}") 
	private String stopAgentPath;
	@Value("${restart.agent.path}") 
	private String restartAgentPath;
	
	/**
	 * @desc 操作压力测试
	 *
	 * @author liuliang
	 *
	 * @param operateBO
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/performanceTest")
	@ResponseBody
	public Result performanceTest(@Validated(Groups.Query.class) OperateBO operateBO,BindingResult br){	
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		//执行任务
		com.yunji.titan.utils.Result<?> operateResult = null;
		if(OperateTypeConstant.START == operateBO.getOperateType()) {
			ActionPerformanceBO ap = null;
			try {
				ap = operateService.getActionParamer(operateBO.getSceneId());
				if(null == ap) {
					return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,"查询待压测参数失败",result);
				}
			} catch (Exception e) {
				logger.error("查询待压测参数异常,params:{}",operateBO.toString(),e);
				return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,"查询待压测参数异常",result);
			}
			TaskIssuedBean taskIssuedBean = new TaskIssuedBean();
			operateService.copyBeanProperties(ap, taskIssuedBean);
			operateResult = taskFacade.startPerformanceTest(taskIssuedBean);
		}else if(OperateTypeConstant.STOP == operateBO.getOperateType()){
			operateResult = taskFacade.stopPerformanceTest(operateBO.getSceneId().intValue());
		}
		if(operateResult.isSuccess()) {
			//更新DB
			try {
				int sceneStatus = SceneStatusEnum.STARTING.value;
				if(OperateTypeConstant.STOP == operateBO.getOperateType()) {
					sceneStatus = SceneStatusEnum.STOPPING.value;
				}
				int updateResult = sceneService.updateSceneStatus(operateBO.getSceneId(),sceneStatus);
				if(0 == updateResult) {
					logger.error("执行压测命令成功,更新DB失败,params:{}",operateBO.toString());
				}
			} catch (Exception e) {
				logger.error("执行压测命令成功,更新DB异常,params:{}",operateBO.toString());
				return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,"停止压测成功,更新DB异常",result);
			}
			return ResultUtil.success(result);
		}else {
			//失败返回
			return ResultUtil.fail(operateResult.getErrorCode(),operateResult.getErrorMsg(),result);
		}
	}
	
	/**
	 * @desc 操作Agent节点
	 *
	 * @author liuliang
	 *
	 * @param operateBO
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/agent")
	@ResponseBody
	public Result agent(@Validated(Groups.Update.class) OperateBO operateBO,BindingResult br){
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		//执行命令
		String commandPath = null;
		switch (operateBO.getOperateType()) {
			case OperateTypeConstant.START:		
				commandPath = startAgentPath;
				break;
			case OperateTypeConstant.STOP:		
				commandPath = stopAgentPath;
				break;
			case OperateTypeConstant.RESTART:
				commandPath = restartAgentPath;
				break;  
			default:
				logger.error("操作Agent,操作类型错误,params:{}",operateBO.toString());
				break;
		}
		if(StringUtils.isNotBlank(commandPath)){
			try {
				String commandResult = agentService.executeCommand(commandPath);
				logger.info("执行agent命令结果,commandResult:{}",commandResult);
				return ResultUtil.success(result);
			} catch (Exception e) {
				logger.error("执行shell命令异常,params:{}",operateBO.toString(),e);
				return ResultUtil.fail(ErrorCode.FAIL,"执行命令异常",result);
			}
		}else{
			return ResultUtil.fail(ErrorCode.FAIL,"操作类型错误",result);
		}
	}
	
}
