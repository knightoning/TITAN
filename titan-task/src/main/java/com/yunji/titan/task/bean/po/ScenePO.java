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
package com.yunji.titan.task.bean.po;

/**
 * 压测场景EntityBean,对饮数据库表[r_scene]
 * 
 * @author gaoxianglong
 */
public class ScenePO {
	private Integer sceneStatus;

	public Integer getSceneStatus() {
		return sceneStatus;
	}

	public void setSceneStatus(Integer sceneStatus) {
		this.sceneStatus = sceneStatus;
	}

	@Override
	public String toString() {
		return "ScenePO [sceneStatus=" + sceneStatus + "]";
	}
}