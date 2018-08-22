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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc 页面跳转controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/pages")
public class PageJumpController {

	/**
	 * @desc 跳转到主管理界面
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/main")
	public ModelAndView toMainPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("main");
	}
	/**
	 * @desc 跳转到概览页
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/overview")
	public ModelAndView toOverviewPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("overview");
	}
	
	/**
	 * @desc 跳转到报表列表
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/report/report_list")
	public ModelAndView toReportListPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("report/report_list");
	}
	
	/**
	 * @desc 跳转到测试报告详情页
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/report/report_detail")
	public ModelAndView toReportDetailPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("report/report_detail");
	}
	
	/**
	 * @desc 跳转到链路列表
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/pressure/link_list")
	public ModelAndView toLinkListPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("pressure/link_list");
	}
	
	/**
	 * @desc 跳转到新增链路
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/pressure/link_add")
	public ModelAndView toLinkAddPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("pressure/link_add");
	}
	
	/**
	 * @desc 跳转到场景列表
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/pressure/scene_list")
	public ModelAndView toSceneListPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("pressure/scene_list");
	}
	
	/**
	 * @desc 跳转到新增场景
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/pressure/scene_add")
	public ModelAndView toSceneAddPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("pressure/scene_add");
	}
	
	/**
	 * @desc 跳转到执行压测列表页
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/pressure/do_pressure_list")
	public ModelAndView toDoPressureListPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("pressure/do_pressure_list");
	}
	
	/**
	 * @desc 跳转到执行压测操作页
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView 
	 */
	@RequestMapping(value = "/pressure/do_pressure")
	public ModelAndView toDoPressurePage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("pressure/do_pressure");
	}
	
	/**
	 * 
	 * @desc 跳转到监控集列表页
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/monitor/monitor_list")
	public ModelAndView toMonitorListPage(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("monitor/monitor_list");
	}

}
