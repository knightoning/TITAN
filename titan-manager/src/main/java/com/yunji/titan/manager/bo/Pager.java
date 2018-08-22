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
package com.yunji.titan.manager.bo;

import java.util.List;

/**
 * @desc 分页查询基础响应参数BO
 * 
 * @author liuliang
 *
 */
public class Pager<T> {

	/**
	 * 符合条件的总记录数
	 */
	private int totalCount;
	
	/**
	 * 记录
	 */
	private List<T>  records;
			 
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	
	public Pager() {}
	public Pager(int totalCount, List<T> records) {
		super();
		this.totalCount = totalCount;
		this.records = records;
	}
	
}
