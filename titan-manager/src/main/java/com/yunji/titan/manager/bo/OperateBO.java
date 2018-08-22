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

import javax.validation.constraints.NotNull;

import com.yunji.titan.manager.common.Groups;

/**
 * @desc 操作请求参数BO
 *
 * @author liuliang
 *
 */
public class OperateBO {

	/**
	 * 场景ID
	 */
	@NotNull(groups = {Groups.Query.class},message = "场景ID不能为空")
	private Long sceneId;
	/**
	 * 操作类型
	 */
	@NotNull(groups = {Groups.Update.class,Groups.Query.class},message = "操作类型不能为空")
	private Integer operateType;
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
	public Integer getOperateType() {
		return operateType;
	}
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	@Override
	public String toString() {
		return "OperateBO [sceneId=" + sceneId + ", operateType=" + operateType + "]";
	}
	
}
