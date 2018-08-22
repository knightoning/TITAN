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
package com.yunji.titan.manager.entity;

/**
 * @desc 监控集实体，对应表[t_monitor_set]
 *
 * @author liuliang
 *
 */
public class MonitorSet {
	/**
	 * 主键ID
	 */
	 private Long monitorSetId;
	 /**
	  * 场景ID
	  */
	 private Long sceneId;
	 /**
	  * 场景名称
	  */
	 private String sceneName;
	 /**
	  * 内网IP
	  */
	 private String intranetIp;
	 /**
	  * 记录创建时间
	  */
	 private Long createTime;
	public Long getMonitorSetId() {
		return monitorSetId;
	}
	public void setMonitorSetId(Long monitorSetId) {
		this.monitorSetId = monitorSetId;
	}
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
	public String getSceneName() {
		return sceneName;
	}
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	public String getIntranetIp() {
		return intranetIp;
	}
	public void setIntranetIp(String intranetIp) {
		this.intranetIp = intranetIp;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	 
}
