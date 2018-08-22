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
import java.util.List;

import com.yunji.titan.manager.entity.Link;
import com.yunji.titan.manager.entity.Scene;

/**
 * @desc 查询场景详情响应参数BO
 *
 * @author liuliang
 *
 */
public class SceneDetailBO implements Serializable {

	private static final long serialVersionUID = -7727253914369267173L;

	/**
	 * 场景基本信息
	 */
	private Scene scene;
	/**
	 * 场景包含的链路实体集合
	 */
	private List<Link> linkList;

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public List<Link> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<Link> linkList) {
		this.linkList = linkList;
	}

	public SceneDetailBO() {
	}

	public SceneDetailBO(Scene scene, List<Link> linkList) {
		super();
		this.scene = scene;
		this.linkList = linkList;
	}

	@Override
	public String toString() {
		return "SceneDetailBO [scene=" + scene + ", linkList=" + linkList + "]";
	}

}
