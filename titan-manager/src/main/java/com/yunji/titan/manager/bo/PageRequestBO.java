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

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @desc 分页查询请求参数BO
 *
 * @author liuliang
 *
 */
public class PageRequestBO implements Serializable{

	private static final long serialVersionUID = -6419653684529269165L;

	/**
	 * 当前页数
	 */
	@NotNull(message = "当前页数不能为空")
	private Integer pageIndex;
	/**
	 * 每页记录数
	 */
	@NotNull(message = "每页记录数不能为空")
	private Integer pageSize;
	/**
	 * 过滤条件(链路名称、场景名称、报告名称)
	 */
	private String filterCondition;
	
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getFilterCondition() {
		return filterCondition;
	}
	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}
	
	@Override
	public String toString() {
		return "PageRequestBO [pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", filterCondition="
				+ filterCondition + "]";
	}
}
