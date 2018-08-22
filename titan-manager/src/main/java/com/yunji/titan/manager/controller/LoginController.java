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
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunji.titan.manager.bo.LoginResultBO;
import com.yunji.titan.manager.bo.UserInfoBO;
import com.yunji.titan.manager.common.TitanConstant;
import com.yunji.titan.manager.result.ComponentResult;
import com.yunji.titan.manager.result.Result;
import com.yunji.titan.manager.utils.CookieUtil;
import com.yunji.titan.manager.utils.ResultUtil;
import com.yunji.titan.manager.utils.TicketBuilderUtil;
import com.yunji.titan.manager.utils.ValidatorUtil;
import com.yunji.titan.utils.ErrorCode;

import redis.clients.jedis.JedisCluster;

/**
 * @desc 登录controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/user")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 用户账号、密码
	 */
	@Value("${titan.manager.user}") 
	private String titanManagerUser;
	@Value("${ticket.expire.time}")
	private int ticketExpireTime;
	/**
	 * jedis
	 */
	@Resource
	private JedisCluster jedisCluster;
	
	/**
	 * @desc 登录
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param response
	 * @return LoginResponseBO 登录结果BO
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public ComponentResult<LoginResultBO> login(@Validated UserInfoBO userInfoBO,BindingResult br) {
		ComponentResult<LoginResultBO> componentResult = new ComponentResult<LoginResultBO>();
		//1、参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, componentResult);
		}
		//2、登录
		String username = userInfoBO.getUsername();
		String password = userInfoBO.getPassword();
    	String[] userArray = titanManagerUser.split("\\|");
    	for(int i=0,userArraySize=userArray.length ; i<userArraySize ; i++){
    		String[] tempUser = userArray[i].split("\\,");
    		if(username.equals(tempUser[0]) && password.equals(tempUser[1])){
    			String ticket = TicketBuilderUtil.getTicket(userInfoBO.getUsername());
    			//设置ticket到缓存中
    			if(-1 == ticketExpireTime){
    				jedisCluster.set(TitanConstant.TICKET_PREFIX + ticket,username);
    			}else{
    				jedisCluster.setex(TitanConstant.TICKET_PREFIX + ticket,ticketExpireTime, username);
    			}
    			//3、返回登录成功
    			componentResult.setData(new LoginResultBO(ticket));
    			ResultUtil.success(componentResult);
    			return componentResult;
    		}
    	}
		//4、失败返回
		return ResultUtil.fail(ErrorCode.LOGIN_ERROR,componentResult);
	}
	
	/**
	 * @desc 退出系统
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public Result logout(HttpServletRequest request){
		Result result = new Result();
		try {
			String ticket = CookieUtil.getCookieValueByName(request,TitanConstant.TICKET_PREFIX);
			if(StringUtils.isNotBlank(ticket)){
				jedisCluster.del(TitanConstant.TICKET_PREFIX + ticket);
				return ResultUtil.success(result);
			}else {
				logger.error("ticket为空,ticket:{}",ticket);
				return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,result);
			}
		} catch (Exception e) {
			logger.error("退出titan异常",e);
		}
		return ResultUtil.fail(result);
	}
}
