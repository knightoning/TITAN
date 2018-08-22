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
import javax.servlet.http.HttpServletRequest;

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

import com.yunji.titan.manager.bo.PageRequestBO;
import com.yunji.titan.manager.bo.Pager;
import com.yunji.titan.manager.bo.SceneBO;
import com.yunji.titan.manager.bo.SceneDetailBO;
import com.yunji.titan.manager.common.Groups;
import com.yunji.titan.manager.common.SceneStatusEnum;
import com.yunji.titan.manager.entity.AutomaticTask;
import com.yunji.titan.manager.entity.Link;
import com.yunji.titan.manager.entity.Scene;
import com.yunji.titan.manager.result.ComponentResult;
import com.yunji.titan.manager.result.Result;
import com.yunji.titan.manager.service.AutomaticTaskService;
import com.yunji.titan.manager.service.LinkService;
import com.yunji.titan.manager.service.SceneService;
import com.yunji.titan.manager.utils.ResultUtil;
import com.yunji.titan.manager.utils.ValidatorUtil;
import com.yunji.titan.utils.ErrorCode;

/**
 * @desc 场景管理Controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/scene")
public class SceneController {

	private Logger logger = LoggerFactory.getLogger(SceneController.class);
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
	 * 自动化任务服务
	 */
	@Resource
	private AutomaticTaskService automaticTaskService;
	
	/**
	 * @desc 分页获取场景数据
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ComponentResult<Pager<SceneBO>>
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ComponentResult<Pager<SceneBO>> list(@Validated PageRequestBO pr,BindingResult br) {
		ComponentResult<Pager<SceneBO>> componentResult = new ComponentResult<Pager<SceneBO>>();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, componentResult);
		}
		//查询
		try {
			int totalCount = sceneService.getSceneCount(pr.getFilterCondition());
			List<SceneBO> records = sceneService.getSceneList(pr.getFilterCondition(),pr.getPageIndex(),pr.getPageSize());
			if(null != records) {
				for(SceneBO sceneBO:records){
					AutomaticTask automaticTask = automaticTaskService.getDataDetailBySceneId(sceneBO.getSceneId());
					if(null != automaticTask){
						sceneBO.setIsAutomatic(1);
					}
				}
			}
			componentResult.setData(new Pager<SceneBO>(totalCount, records));
			return ResultUtil.success(componentResult);
		} catch (Exception e) {
			logger.error("分页获取数据异常,pageRequestBO:{}",pr.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,componentResult);
	}
	
	/**
	 * @desc 新增场景
	 *
	 * @author liuliang
	 *
	 * @param sceneBO 场景BO
	 * @return Result
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Result add(@Validated(Groups.Add.class) SceneBO sceneBO,BindingResult br){
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		long totalRequest = sceneBO.getTotalRequest();
		long concurrentUser = sceneBO.getConcurrentUser();
		long useAgent = sceneBO.getUseAgent();
		long concurrentStart = sceneBO.getConcurrentStart();
		if( (totalRequest < concurrentUser) || (concurrentUser < useAgent) 
				|| (0 != (totalRequest % concurrentUser)) || (0 != (concurrentUser % useAgent))){
			return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,"总请求数、并发用户数、agent数倍数关系错误",result);
		}
		if(concurrentStart > concurrentUser){
			return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,"起始并发用户数不能大于并发用户数",result);
		}
		//新增
		try {
			int addResult = sceneService.addScene(sceneBO);
			if(0 < addResult) {
				return ResultUtil.success(result);
			}
		} catch(DuplicateKeyException de){
			logger.error("新增数据异常,场景名重复,params:{}",sceneBO.toString(),de);
			return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,"场景名已存在",result);
		} catch (Exception e) {
			logger.error("新增数据异常,params:{}",sceneBO.toString(),e);
		}
		//返回失败结果
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 删除场景
	 *
	 * @author liuliang
	 *
	 * @param ids 场景ID
	 * @return Result
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Result del(@RequestParam("ids") String ids){
		Result result = new Result();
		//参数校验
		if(StringUtils.isBlank(ids)){
			logger.warn("删除数据,参数错误,ids:{}",ids);
			return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,result);
		}
		//删除
		String[] idsArray = ids.split("\\,");
		int idsLength = idsArray.length;
		int failNum = 0;
		//逐个ID处理
		for(int i=0; i<idsLength; i++){
			long sceneId = Long.parseLong(idsArray[i]);
			int delResult = 0;
			try {
				delResult = sceneService.removeSceneAndUpdateRelatedData(sceneId);
			} catch (Exception e) {
				logger.error("删除数据异常,sceneId:{}",sceneId,e);
			}
			if(0 == delResult) {
				failNum++;
			}
		}
		if(0 == failNum) {
			return ResultUtil.success(result);
		}else {
			//TODO
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 更新场景
	 *
	 * @author liuliang
	 *
	 * @param sceneBO 场景BO
	 * @return Result
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Result update(@Validated(Groups.Update.class) SceneBO sceneBO,BindingResult br){
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		long totalRequest = sceneBO.getTotalRequest();
		long concurrentUser = sceneBO.getConcurrentUser();
		long useAgent = sceneBO.getUseAgent();
		long concurrentStart = sceneBO.getConcurrentStart();
		if( (totalRequest < concurrentUser) || (concurrentUser < useAgent) 
				|| (0 != (totalRequest % concurrentUser)) || (0 != (concurrentUser % useAgent))){
			return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,"总请求数、并发用户数、agent数倍数关系错误",result);
		}
		if(concurrentStart > concurrentUser){
			return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,"起始并发用户数不能大于并发用户数",result);
		}
		//更新
		try {
			int updateResult = sceneService.updateScene(sceneBO);
			if(0 < updateResult) {
				return ResultUtil.success(result);
			} else {
				Scene scene = sceneService.getScene(sceneBO.getSceneId());
				if((null != scene) && (SceneStatusEnum.NOT_START.value != scene.getSceneStatus())){
					String errMsg = SceneStatusEnum.getDesc(scene.getSceneStatus()) + ",不可修改";
					return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,errMsg,result);
				}
			}
		} catch(DuplicateKeyException de){
			logger.error("更新数据异常,场景名重复,params:{}",sceneBO.toString(),de);
			return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,"场景名已存在",result);
		} catch (Exception e) {
			logger.error("更新数据异常,params:{}",sceneBO.toString(),e);
		}
		//返回失败结果
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 查询场景详情
	 *
	 * @author liuliang
	 *
	 * @param sceneId 场景ID
	 * @return ComponentResult<SceneDetailBO>
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public ComponentResult<SceneDetailBO> get(@Validated(Groups.Query.class) SceneBO sceneBO,BindingResult br) {
		ComponentResult<SceneDetailBO> componentResult = new ComponentResult<SceneDetailBO>();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, componentResult);
		}
		//查询
		try {
			Scene scene = sceneService.getScene(sceneBO.getSceneId());
			if(null != scene){
				String containLinkid = scene.getContainLinkid();
				List<Link> linkList = linkService.getLinkListByIds(containLinkid);
				
				componentResult.setData(new SceneDetailBO(scene, linkList));
				return ResultUtil.success(componentResult);
			}
		} catch (Exception e) {
			logger.error("查询数据详情异常,params:{}",sceneBO.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,componentResult);
	}
	
	/**
	 * @desc 强制重置所有的场景状态为原始状态
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @return Result
	 */
	@RequestMapping(value = "/reset")
	@ResponseBody
	public Result reset(HttpServletRequest request){
		Result result = new Result();
		try {
			sceneService.resetSceneStatus();
			return ResultUtil.success(result);
		} catch (Exception e) {
			logger.error("强制重置所有的场景状态为原始状态异常",e);
		}
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
}
