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
package com.yunji.titan.utils;

/**
 * URL特殊字符传唤处理
 * 
 * @author gaoxianglong
 */
public class UrlEncoder {
	/**
	 * 对url请求进行特殊字符转换
	 * 
	 * @author gaoxianglong
	 * 
	 * @return String 转换后的URL
	 */
	public static String encode(String url) {
		return url.replaceAll("\\|", "%7C").replaceAll("\\{", "%7b").replaceAll("\\}", "%7d");
	}
}