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
package com.yunji.titan.manager.dao;

import java.util.List;

import com.yunji.titan.manager.impl.dao.base.Criteria;
import com.yunji.titan.manager.impl.dao.base.Pager;
/**
 * 
 * 基础dao
 *
 * @author liuliang
 *
 */
public interface BaseDao {

	/**
	 * 查记录数-总数
	 *
	 * @author liuliang
	 *
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	int queryCount(Class<?> clazz) throws Exception;
	
	/**
	 * 查记录数-满足指定条件的
	 *
	 * @author liuliang
	 *
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	int queryCount(Criteria criteria) throws Exception;
	
	/**
	 * 增
	 *
	 * @author liuliang
	 *
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	int insert(Object entity) throws Exception;
	
	/**
	 * 删
	 *
	 * @author liuliang
	 *
	 * @param clazz
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int delete(Class<?> clazz, Long id) throws Exception;
	
	/**
	 * 删  根据指定条件
	 *
	 * @author liuliang
	 *
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	int delete(Criteria criteria) throws Exception;
	
	/**
	 * 改
	 *
	 * @author liuliang
	 *
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	int update(Object entity) throws Exception;
	
	/**
	 * 查-主键
	 *
	 * @author liuliang
	 *
	 * @param clazz
	 * @param id
	 * @return
	 * @throws Exception
	 */
	<T> T query(Class<T> clazz, Long id) throws Exception;
	
	/**
	 * 查-指定条件
	 *
	 * @author liuliang
	 *
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
    <T> T query(Criteria criteria) throws Exception;
	
	/**
	 * 分页查询
	 *
	 * @author liuliang
	 *
	 * @param clazz
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	<T> List<T> queryList(Class<T> clazz,Pager pager) throws Exception;
	
	/**
	 * 分页查询
	 *
	 * @author liuliang
	 *
	 * @param criteria
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	<T> List<T> queryList(Criteria criteria,Pager pager) throws Exception;
}
