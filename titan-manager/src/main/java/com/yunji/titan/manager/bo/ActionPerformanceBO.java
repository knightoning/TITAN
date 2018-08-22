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

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.yunji.titan.utils.ContentType;
import com.yunji.titan.utils.ProtocolType;
import com.yunji.titan.utils.RequestType;

/**
 * @desc 执行压压测参数BO
 *
 * @author liuliang
 *
 */
public class ActionPerformanceBO implements Serializable {

	private static final long serialVersionUID = 8450751422421751510L;
	/**
	 * 场景ID
	 */
	private Long sceneId;
	/**
	 * 场景名称
	 */
	private String sceneName;
	/**
	 * 起步量级
	 */
	private Integer initConcurrentUsersSize;
	/**
	 * 并发用户数
	 */
	private Integer concurrentUsersSize;
	/**
	 * 总任务数
	 */
	private Long taskSize;
	/**
	 * 申请压测agent节点数
	 */
	private Integer agentSize;
	/**
	 * 期待吞吐量
	 */
	private Integer expectThroughput;
	/**
	 * 期待持续时间
	 */
	private Integer continuedTime;
	/**
	 * 时间单位
	 */
	private TimeUnit timeUnit;
	/**
	 * 协议类型
	 */
	private Map<String, ProtocolType> protocolTypes;
	/**
	 * 请求类型
	 */
	private Map<String, RequestType> requestTypes;
	/**
	 * 需要执行全链路压测的目标URL
	 */
	private List<String> urls;
	/**
	 * 每一个URL对应的动态参数
	 */
	private Map<String, File> params;
	/**
	 * 内容类型
	 */
	private Map<String, ContentType> contentTypes;
	/**
	 * 编码格式
	 */
	private Map<String, String> charsets;

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

	public Integer getInitConcurrentUsersSize() {
		return initConcurrentUsersSize;
	}

	public void setInitConcurrentUsersSize(Integer initConcurrentUsersSize) {
		this.initConcurrentUsersSize = initConcurrentUsersSize;
	}

	public Integer getConcurrentUsersSize() {
		return concurrentUsersSize;
	}

	public void setConcurrentUsersSize(Integer concurrentUsersSize) {
		this.concurrentUsersSize = concurrentUsersSize;
	}

	public Long getTaskSize() {
		return taskSize;
	}

	public void setTaskSize(Long taskSize) {
		this.taskSize = taskSize;
	}

	public Integer getAgentSize() {
		return agentSize;
	}

	public void setAgentSize(Integer agentSize) {
		this.agentSize = agentSize;
	}

	public Integer getExpectThroughput() {
		return expectThroughput;
	}

	public void setExpectThroughput(Integer expectThroughput) {
		this.expectThroughput = expectThroughput;
	}

	public Integer getContinuedTime() {
		return continuedTime;
	}

	public void setContinuedTime(Integer continuedTime) {
		this.continuedTime = continuedTime;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Map<String, ProtocolType> getProtocolTypes() {
		return protocolTypes;
	}

	public void setProtocolTypes(Map<String, ProtocolType> protocolTypes) {
		this.protocolTypes = protocolTypes;
	}

	public Map<String, RequestType> getRequestTypes() {
		return requestTypes;
	}

	public void setRequestTypes(Map<String, RequestType> requestTypes) {
		this.requestTypes = requestTypes;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public Map<String, File> getParams() {
		return params;
	}

	public void setParams(Map<String, File> params) {
		this.params = params;
	}

	public Map<String, ContentType> getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(Map<String, ContentType> contentTypes) {
		this.contentTypes = contentTypes;
	}

	public Map<String, String> getCharsets() {
		return charsets;
	}

	public void setCharsets(Map<String, String> charsets) {
		this.charsets = charsets;
	}

	@Override
	public String toString() {
		return "ActionPerformanceBO [sceneId=" + sceneId + ", sceneName=" + sceneName + ", initConcurrentUsersSize="
				+ initConcurrentUsersSize + ", concurrentUsersSize=" + concurrentUsersSize + ", taskSize=" + taskSize
				+ ", agentSize=" + agentSize + ", expectThroughput=" + expectThroughput + ", continuedTime="
				+ continuedTime + ", timeUnit=" + timeUnit + ", protocolTypes=" + protocolTypes + ", requestTypes="
				+ requestTypes + ", urls=" + urls + ", params=" + params + ", contentTypes=" + contentTypes
				+ ", charsets=" + charsets + "]";
	}

}
