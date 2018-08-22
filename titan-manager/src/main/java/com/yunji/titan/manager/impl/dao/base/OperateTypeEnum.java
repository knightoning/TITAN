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
 * @desc SQL操作类型枚举
 *
 * @author liuliang
 *
 */
public enum OperateTypeEnum {
	/**
	 * 增
	 */
	INSERT,  
	/**
	 * 增 Criteria入参
	 */
	INSERT_C,  
	/**
	 * 删
	 */
	DELETE,	 
	/**
	 * 删  Criteria入参
	 */
	DELETE_C,	 
	/**
	 * 改
	 */
	UPDATE,  
	/**
	 * 改  Criteria入参
	 */
	UPDATE_C,  
	/**
	 * 查 Criteria入参
	 */
	QUERY,	 
	/**
	 * 查
	 */
	QUERY_C,	 
	/**
	 * 统计
	 */
	COUNT, 
	/**
	 * 统计 Criteria入参
	 */
	COUNT_C; 
}
