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
package com.yunji.titan.manager.common;

import com.yunji.titan.utils.ContentType;

/**
 * @desc 压测请求  内容类型枚举类
 *
 * @author liuliang
 *
 */
public enum ContentTypeEnum {

	/**
	 * application/json
	 */
	APP_JSON(0,"application/json"),
	/**
	 * application/xml
	 */
	APP_XML(1,"application/xml"),
	/**
	 * text/xml
	 */
	TEXT_XML(2,"text/xml"),
	/**
	 * text/html
	 */
	TEXT_HTML(3,"text/html"),
	/**
	 * application/x-www-form-urlencoded
	 */
	APP_FORM(4,"application/x-www-form-urlencoded");
	
	
	/**
	 * @desc 根据编码获取值
	 *
	 * @author liuliang
	 *
	 * @param code DB中的编码
	 * @return ContentType 内容枚举类型
	 */
	public static ContentType getContentType(int code){
		switch (code) {
			case 0:
				return ContentType.APPLICATION_JSON;
			case 1:
				return ContentType.APPLICATION_XML;
			case 2:
				return ContentType.TEXT_XML;
			case 3:
				return ContentType.TEXT_HTML;
			case 4:
				return ContentType.APPLICATION_X_WWW_FORM_URLENCODED;
			default:
				break;
		}
		return null;
	}
	
	private ContentTypeEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}
	/**
	 * DB存的值
	 */
	public int code;
	/**
	 * 字符编码
	 */
	public String value;  	
	public int getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}
}
