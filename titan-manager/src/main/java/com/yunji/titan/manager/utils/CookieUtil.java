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
package com.yunji.titan.manager.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * @desc Cookie操作工具类
 *
 * @author liuliang
 *
 */
public class CookieUtil {

	/**
	 * @desc 获取cookie中指定key的值
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param name cookie中的key
	 * @return String
	 */
	public static String getCookieValueByName(HttpServletRequest request, String name) {
		Cookie cookie = getCookieByName(request, name);
		if (cookie != null && StringUtils.isNotBlank(cookie.getValue())) {
			return cookie.getValue();
		} else {
			return null;
		}
	}
	
	/**
	 * @desc 获取cookie对象
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @param name
	 * @return Cookie
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}
	/**
	 * @desc 获取cookie中的数据，并返回map
	 *
	 * @author liuliang
	 *
	 * @param request
	 * @return Map<String, Cookie>
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>(16);
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

}
