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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yunji.titan.manager.common.Groups;

/**
 * @desc 场景数据BO
 *
 * @author liuliang
 *
 */
public class SceneBO implements Serializable{

	private static final long serialVersionUID = 9001068486883624735L;
	/**
	 * 主键自增ID
	 */
	@NotNull(groups = {Groups.Update.class,Groups.Query.class},message = "场景ID不能为空")
	private Long sceneId;
	/**
	 * 场景名称
	 */
	@NotBlank(groups = {Groups.Add.class,Groups.Update.class},message = "场景名称不能为空")
	private String sceneName;
	/**
	 * 持续时间-时
	 */
	private Integer durationHour;
	/**
	 * 持续时间-分
	 */
	private Integer durationMin;
	/**
	 * 持续时间-秒
	 */
	private Integer durationSec;
	/**
	 * 并发用户数
	 */
	@Min(1)
	private Integer concurrentUser;
	/**
	 * 起步并发用户数
	 */
	private Integer concurrentStart;
	/**
	 * 总并发请求数
	 */
	private Long totalRequest;
	/**
	 * 期待吞吐量
	 */
	private Integer expectTps;
	/**
	 * 包含的链路ID，多个链路ID以英文","隔开
	 */
	@NotBlank(groups = {Groups.Add.class,Groups.Update.class},message = "包含的链路ID不能为空")
	private String containLinkid;
	/**
	 * 串行链路关系，按链路顺序，链路ID以英文","隔开
	 */
	@NotBlank(groups = {Groups.Add.class,Groups.Update.class},message = "链路关系不能为空")
	private String linkRelation;
	/**
	 * 使用的agent数
	 */
	private Integer useAgent;
	/**
	 * 状态（0：未开始 ，1：进行中）
	 */
	private Integer sceneStatus;
	/**
	 * 记录创建时间
	 */
	private Long createTime;
	/**
	 * 记录最后修改时间
	 */
	private Long modifyTime;
	/**
	 * 是否有自动测试任务(0:没有  1：有)
	 */
	private Integer isAutomatic;
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
	public Integer getDurationHour() {
		return durationHour;
	}
	public void setDurationHour(Integer durationHour) {
		this.durationHour = durationHour;
	}
	public Integer getDurationMin() {
		return durationMin;
	}
	public void setDurationMin(Integer durationMin) {
		this.durationMin = durationMin;
	}
	public Integer getDurationSec() {
		return durationSec;
	}
	public void setDurationSec(Integer durationSec) {
		this.durationSec = durationSec;
	}
	public Integer getConcurrentUser() {
		return concurrentUser;
	}
	public void setConcurrentUser(Integer concurrentUser) {
		this.concurrentUser = concurrentUser;
	}
	public Integer getConcurrentStart() {
		return concurrentStart;
	}
	public void setConcurrentStart(Integer concurrentStart) {
		this.concurrentStart = concurrentStart;
	}
	public Long getTotalRequest() {
		return totalRequest;
	}
	public void setTotalRequest(Long totalRequest) {
		this.totalRequest = totalRequest;
	}
	public Integer getExpectTps() {
		return expectTps;
	}
	public void setExpectTps(Integer expectTps) {
		this.expectTps = expectTps;
	}
	public String getContainLinkid() {
		return containLinkid;
	}
	public void setContainLinkid(String containLinkid) {
		this.containLinkid = containLinkid;
	}
	public String getLinkRelation() {
		return linkRelation;
	}
	public void setLinkRelation(String linkRelation) {
		this.linkRelation = linkRelation;
	}
	public Integer getUseAgent() {
		return useAgent;
	}
	public void setUseAgent(Integer useAgent) {
		this.useAgent = useAgent;
	}
	public Integer getSceneStatus() {
		return sceneStatus;
	}
	public void setSceneStatus(Integer sceneStatus) {
		this.sceneStatus = sceneStatus;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getIsAutomatic() {
		return isAutomatic;
	}
	public void setIsAutomatic(Integer isAutomatic) {
		this.isAutomatic = isAutomatic;
	}
	@Override
	public String toString() {
		return "SceneBO [sceneId=" + sceneId + ", sceneName=" + sceneName + ", durationHour=" + durationHour
				+ ", durationMin=" + durationMin + ", durationSec=" + durationSec + ", concurrentUser=" + concurrentUser
				+ ", concurrentStart=" + concurrentStart + ", totalRequest=" + totalRequest + ", expectTps=" + expectTps
				+ ", containLinkid=" + containLinkid + ", linkRelation=" + linkRelation + ", useAgent=" + useAgent
				+ ", sceneStatus=" + sceneStatus + ", createTime=" + createTime + ", modifyTime=" + modifyTime
				+ ", isAutomatic=" + isAutomatic + "]";
	}
}