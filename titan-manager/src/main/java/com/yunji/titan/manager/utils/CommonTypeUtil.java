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

import com.yunji.titan.utils.ProtocolType;
import com.yunji.titan.utils.RequestType;

/**
 * @desc 通用类型转换工具类
 *
 * @author liuliang
 *
 */
public class CommonTypeUtil {

	/**
	 * @desc 根据协议值获取协议枚举类型
	 *
	 * @author liuliang
	 *
	 * @param value 协议值（0：http 1:https）
	 * @return ProtocolType 协议类型 枚举,默认返回HTTP
	 */
	public static ProtocolType getProtocolType(int value){
		switch (value) {
			case 0:
				return ProtocolType.HTTP;
			case 1:
				return ProtocolType.HTTPS;
			default:
				return ProtocolType.HTTP;
		}
	}
	
	/**
	 * @desc 根据请求类型值获取请求类型
	 *
	 * @author liuliang
	 *
	 * @param value 类型值（0：get 1:post）
	 * @return 请求类型枚举 ,默认返回 GET
	 */
	public static RequestType getRequestType(int value){
		switch (value) {
			case 0:
				return RequestType.GET;
			case 1:
				return RequestType.POST;
			default:
				return RequestType.GET;
		}
	}
}
