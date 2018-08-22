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
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.yunji.titan.manager.common.Groups;

/**
 * @desc 自动压测BO
 *
 * @author liuliang
 *
 */
public class AutomaticTaskBO implements Serializable{
	private static final long serialVersionUID = -1163866159102834279L;
	/**
	 * 主键ID
	 */
	@NotNull(groups = {Groups.Update.class,Groups.Query.class},message = "自动压测任务ID不能为空")
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
	@NotNull(groups = {Groups.Add.class,Groups.Update.class},message = "开始时间不能为空")
	private Date startTime;
	/**
	 * 压测次数
	 */
	@NotNull(groups = {Groups.Add.class,Groups.Update.class},message = "压测次数不能为空")
	private Integer pressureTimes;
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
	
	@Override
	public String toString() {
		return "AutomaticTaskBO [automaticTaskId=" + automaticTaskId + ", sceneId=" + sceneId + ", sceneName="
				+ sceneName + ", startTime=" + startTime + ", pressureTimes=" + pressureTimes + "]";
	}
}
