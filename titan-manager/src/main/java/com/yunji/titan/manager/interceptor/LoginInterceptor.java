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
package com.yunji.titan.manager.interceptor;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yunji.titan.manager.common.TitanConstant;
import com.yunji.titan.manager.utils.CookieUtil;

import redis.clients.jedis.JedisCluster;

/**
 * @desc 登录拦截器
 *
 * @author liuliang
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	/**
	 * 不需登录拦截的路径
	 */
    private List<String> uncheckUrls;
    /**
     * jedis
     */
    @Resource
	private JedisCluster jedisCluster;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg2) throws Exception {
		//1、获取请求的参数
		String requestUrl = request.getServletPath(); 
		String ticket = CookieUtil.getCookieValueByName(request,TitanConstant.TICKET_PREFIX);
		//2、验证url
		if((null != uncheckUrls) && (0 < uncheckUrls.size()) && (uncheckUrls.contains(requestUrl))){
			return true;
		}else{
			 //3、验证ticket
			String ticketInRedis = jedisCluster.get(TitanConstant.TICKET_PREFIX + ticket);
			if(StringUtils.isBlank(ticket) || StringUtils.isBlank(ticketInRedis)) {
				logger.warn("登录拦截,requestUrl:{},ticket:{},ticketInRedis:{}",requestUrl,ticket,ticketInRedis);
				PrintWriter out = response.getWriter();  
		        out.println("<html>");      
		        out.println("<script>");      
		        out.println("window.open ('"+request.getContextPath()+"/login.html','_top')");      
		        out.println("</script>");      
		        out.println("</html>"); 
			}else {
				return true;
			}
		}
		return false;
	}

	public List<String> getUncheckUrls() {
		return uncheckUrls;
	}

	public void setUncheckUrls(List<String> uncheckUrls) {
		this.uncheckUrls = uncheckUrls;
	}
}
