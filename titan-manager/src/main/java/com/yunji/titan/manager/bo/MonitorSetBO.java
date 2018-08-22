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

import org.hibernate.validator.constraints.NotBlank;

import com.yunji.titan.manager.common.Groups;

/**
 * @desc 监控集BO
 *
 * @author liuliang
 *
 */
public class MonitorSetBO implements Serializable {
	private static final long serialVersionUID = 2485288312877404356L;
	/**
	 * 主键ID
	 */
	@NotNull(groups = {Groups.Update.class,Groups.Query.class},message = "监控集ID不能为空")
	private Long monitorSetId;
	/**
	 * 场景ID
	 */
	private Long sceneId;
	/**
	 * 场景名称
	 */
	@NotBlank(groups = {Groups.Add.class,Groups.Update.class},message = "场景名称不能为空")
	private String sceneName;
	/**
	 * 内网IP
	 */
	@NotBlank(groups = {Groups.Add.class,Groups.Update.class},message = "内网IP不能为空")
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

	@Override
	public String toString() {
		return "MonitorSetBO [monitorSetId=" + monitorSetId + ", sceneId=" + sceneId + ", sceneName=" + sceneName
				+ ", intranetIp=" + intranetIp + ", createTime=" + createTime + "]";
	}

}
