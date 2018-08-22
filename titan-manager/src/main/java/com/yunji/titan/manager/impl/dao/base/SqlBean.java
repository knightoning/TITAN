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

import java.util.List;
/**
 * SQL组装bean
 *
 * @author liuliang
 *
 */
public class SqlBean {

	/**
	 * sql语句
	 */
	private String sql;
	/**
	 * 参数,与sql属性中的问号按顺序对应
	 */
	private List<Object> params;
	
	public SqlBean() {
		super();
	}
	
	public SqlBean(String sql, List<Object> params) {
		super();
		this.sql = sql;
		this.params = params;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<Object> getParams() {
		return params;
	}
	public void setParams(List<Object> params) {
		this.params = params;
	}
}
