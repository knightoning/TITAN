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

import java.io.File;
import java.util.List;

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

import com.yunji.titan.manager.bo.LinkBO;
import com.yunji.titan.manager.bo.PageRequestBO;
import com.yunji.titan.manager.bo.Pager;
import com.yunji.titan.manager.common.Groups;
import com.yunji.titan.manager.entity.Link;
import com.yunji.titan.manager.result.ComponentResult;
import com.yunji.titan.manager.result.Result;
import com.yunji.titan.manager.service.LinkService;
import com.yunji.titan.manager.service.SceneService;
import com.yunji.titan.manager.utils.ResultUtil;
import com.yunji.titan.manager.utils.ValidatorUtil;
import com.yunji.titan.utils.ErrorCode;
import com.yunji.titan.utils.ftp.FtpUtils;

/**
 * @desc 链路管理Controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/link")
public class LinkController {

	private Logger logger = LoggerFactory.getLogger(LinkController.class);
	/**
	 * 链路服务
	 */
	@Resource
	private LinkService linkService;
	/**
	 * 场景服务
	 */
	@Resource
	private SceneService sceneService;
	/**
	 * ftp工具类
	 */
	@Resource
	private FtpUtils ftpUtils;
	
	/**
	 * @desc 分页获取链路数据
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ComponentResult<Pager<LinkBO>>
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ComponentResult<Pager<LinkBO>> list(@Validated PageRequestBO pr,BindingResult br) {
		ComponentResult<Pager<LinkBO>> componentResult = new ComponentResult<Pager<LinkBO>>();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, componentResult);
		}
		//查询
		try {
			int totalCount = linkService.getLinkCount(pr.getFilterCondition());
			List<LinkBO> records = linkService.getLinkList(pr.getFilterCondition(),pr.getPageIndex(),pr.getPageSize());
			
			componentResult.setData(new Pager<LinkBO>(totalCount, records));
			return ResultUtil.success(componentResult);
		} catch (Exception e) {
			logger.error("分页获取数据异常,params:{}",pr.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,componentResult);
	}
	
	/**
	 * @desc 新增链路
	 *
	 * @author liuliang
	 *
	 * @param linkBO 链路BO
	 * @return Result
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Result add(@Validated(Groups.Add.class) LinkBO linkBO,BindingResult br){
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		//新增
		try {
			int addResult = linkService.addLink(linkBO);
			if(0 < addResult) {
				return ResultUtil.success(result);
			}
		} catch(DuplicateKeyException de){
			logger.error("新增数据异常,链路名重复,params:{}",linkBO.toString(),de);
			return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,"链路名已存在",result);
		} catch (Exception e) {
			logger.error("新增数据异常,params:{}",linkBO.toString(),e);
		}
		//返回失败结果
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 删除链路
	 *
	 * @author liuliang
	 *
	 * @param ids 链路ID
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
		//逐个链路ID处理
		for(int i=0; i<idsLength ; i++){
			long linkId = Long.parseLong(idsArray[i]);
			int delResult = 0;
			//查询要删除的id是否有场景用到过
			try {
				int sceneCount = sceneService.getSceneCountByLinkId(linkId);
				if(0 < sceneCount){
					delResult = linkService.removeLinkAndUpdateScene(linkId,sceneCount);
				}else{
					delResult = linkService.removeLink(String.valueOf(linkId));
				}
			} catch (Exception e) {
				logger.error("删除数据异常,linkId:{}",linkId,e);
			}
			if(0 == delResult) {
				failNum++;
			}
		}
		//返回处理结果
		if(0 == failNum) {
			return ResultUtil.success(result);
		} else {
			//TODO
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 更新链路
	 *
	 * @author liuliang
	 *
	 * @param linkBO 链路BO
	 * @return Result
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Result update(@Validated(Groups.Update.class) LinkBO linkBO,BindingResult br){
		Result result = new Result();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, result);
		}
		//更新
		try {
			//查原数据
			Link link = linkService.getLink(linkBO.getLinkId());
			String oldtestFileName = link.getTestfilePath();
			//更新DB
			int updateResult = linkService.updateLink(linkBO);
			if(0 < updateResult) {
				//若存在旧的压测文件,则删除
				if(StringUtils.isNotBlank(oldtestFileName) && (!oldtestFileName.equals(linkBO.getTestfilePath()))){
					boolean delResult = ftpUtils.deleteFile(new File(oldtestFileName));
					if(!delResult){
						logger.error("删除旧压测文件失败,linkId:{},filename:{}",linkBO.getLinkId(),oldtestFileName);
					}
				}
				return ResultUtil.success(result);
			}
		} catch(DuplicateKeyException de){
			logger.error("更新数据异常,链路名重复,linkBO:{}",linkBO.toString(),de);
			return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,"链路名已存在",result);
		}catch (Exception e) {
			logger.error("更新数据异常,params:{}",linkBO.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 查询链路详情
	 *
	 * @author liuliang
	 *
	 * @param id 链路ID
	 * @return ComponentResult<Link>
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public ComponentResult<Link> get(@Validated(Groups.Query.class) LinkBO linkBO,BindingResult br) {
		ComponentResult<Link> componentResult = new ComponentResult<Link>();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, componentResult);
		}
		//查询
		try {
			Link link = linkService.getLink(linkBO.getLinkId());
			if(null != link) {
				componentResult.setData(link);
				return ResultUtil.success(componentResult);
			}
		} catch (Exception e) {
			logger.error("查询数据详情异常,params:{}",linkBO.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,componentResult);
	}
	
}