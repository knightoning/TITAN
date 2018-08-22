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

import java.util.Date;

/**
 * @desc 自动压测任务表实体,对应表[t_automatic_task]
 *
 * @author liuliang
 *
 */
public class AutomaticTask {
	/**
	 * 主键ID
	 */
	private Long automaticTaskId;
	/**
	 * 场景ID
	 */
	private Long sceneId;
	/**
	 * 场景名称
	 */
	private String sceneName;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 压测次数
	 */
	private Integer pressureTimes;
	/**
	 * 记录创建时间
	 */
	private Long createTime;
	public Long getAutomaticTaskId() {
		return automaticTaskId;
	}
	public void setAutomaticTaskId(Long automaticTaskId) {
		this.automaticTaskId = automaticTaskId;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Integer getPressureTimes() {
		return pressureTimes;
	}
	public void setPressureTimes(Integer pressureTimes) {
		this.pressureTimes = pressureTimes;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
}
