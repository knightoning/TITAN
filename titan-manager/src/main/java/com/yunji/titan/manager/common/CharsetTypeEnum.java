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

/**
 * @desc 压测请求 字符编码 枚举类
 *
 * @author liuliang
 *
 */
public enum CharsetTypeEnum {
	/**
	 * UTF-8编码
	 */
	UTF_8(0,"UTF-8"),
	/**
	 * ISO-8859-1编码
	 */
	ISO_8859_1(1,"ISO-8859-1"),
	/**
	 * US-ASCII编码
	 */
	US_ASCII(2,"US-ASCII"),
	/**
	 * UTF-16编码
	 */
	UTF_16(3,"UTF-16"),
	/**
	 * UTF-16LE编码
	 */
	UTF_16LE(4,"UTF-16LE"),
	/**
	 * UTF-16BE编码
	 */
	UTF_16BE(5,"UTF-16BE");
	
	/**
	 * @desc 根据编码获取值
	 *
	 * @author liuliang
	 *
	 * @param code DB中的编码
	 * @return  字符编码 值
	 */
	public static String getValue(int code){
		for(CharsetTypeEnum c:CharsetTypeEnum.values()){
			if(code == c.getCode()){
				return c.getValue();
			}
		}
		return null;
	}
	
	private CharsetTypeEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * DB存的编码
	 */
	public int code;	
	/**
	 * 值
	 */
	public String value;	
	public int getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}
}
