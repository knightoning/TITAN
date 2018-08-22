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
package com.yunji.titan.manager.impl.dao.base;

/**
 * @desc SQL操作符
 *
 * @author liuliang
 *
 */
public class Operator {

	/**
	 * 模糊查询
	 */
	public static String LIKE = "LIKE";
	/**
	 * 等于
	 */
	public static String EQ = "=";
	/**
	 * 不等于
	 */
	public static String NE = "<>";
	/**
	 * 大于
	 */
	public static String GT = ">";
	/**
	 * 小于
	 */
	public static String LT = "<";
	
	/**
	 * 大于等于
	 */
	public static String GE = ">=";
	
	/**
	 * 小于等于
	 */
	public static String LE = "<=";
}
