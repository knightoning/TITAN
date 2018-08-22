/*
 * Copyright 2015-2101 yunjiweidian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yunji.titan.utils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 任务下发Bean
 * 
 * @author gaoxianglong
 */
public class TaskIssuedBean implements Serializable{
	private static final long serialVersionUID = 851937888975205301L;
	private String taskId;
	private String znode;
	private int senceId;
	private String senceName;
	private int initConcurrentUsersSize;
	private int concurrentUsersSize;
	private long taskSize;
	private int agentSize;
	private int expectThroughput;
	private long continuedTime;
	private List<String> urls = new ArrayList<String>();
	private Map<String, RequestType> requestTypes = new ConcurrentHashMap<>();
	private Map<String, ProtocolType> protocolTypes = new ConcurrentHashMap<>();
	private Map<String, File> params = new ConcurrentHashMap<>();
	private Map<String, ContentType> contentTypes = new ConcurrentHashMap<>();
	private Map<String, String> charsets = new ConcurrentHashMap<>();
	private TimeUnit timeUnit;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getZnode() {
		return znode;
	}

	public void setZnode(String znode) {
		this.znode = znode;
	}

	public int getSenceId() {
		return senceId;
	}

	public void setSenceId(int senceId) {
		this.senceId = senceId;
	}

	public String getSenceName() {
		return senceName;
	}

	public void setSenceName(String senceName) {
		this.senceName = senceName;
	}

	public int getInitConcurrentUsersSize() {
		return initConcurrentUsersSize;
	}

	public void setInitConcurrentUsersSize(int initConcurrentUsersSize) {
		this.initConcurrentUsersSize = initConcurrentUsersSize;
	}

	public int getConcurrentUsersSize() {
		return concurrentUsersSize;
	}

	public void setConcurrentUsersSize(int concurrentUsersSize) {
		this.concurrentUsersSize = concurrentUsersSize;
	}

	public long getTaskSize() {
		return taskSize;
	}

	public void setTaskSize(long taskSize) {
		this.taskSize = taskSize;
	}

	public int getAgentSize() {
		return agentSize;
	}

	public void setAgentSize(int agentSize) {
		this.agentSize = agentSize;
	}

	public int getExpectThroughput() {
		return expectThroughput;
	}

	public void setExpectThroughput(int expectThroughput) {
		this.expectThroughput = expectThroughput;
	}

	public long getContinuedTime() {
		return continuedTime;
	}

	public void setContinuedTime(long continuedTime) {
		this.continuedTime = continuedTime;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public Map<String, RequestType> getRequestTypes() {
		return requestTypes;
	}

	public void setRequestTypes(Map<String, RequestType> requestTypes) {
		this.requestTypes = requestTypes;
	}

	public Map<String, ProtocolType> getProtocolTypes() {
		return protocolTypes;
	}

	public void setProtocolTypes(Map<String, ProtocolType> protocolTypes) {
		this.protocolTypes = protocolTypes;
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

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	@Override
	public String toString() {
		return "TaskIssuedBean [taskId=" + taskId + ", znode=" + znode + ", senceId=" + senceId + ", senceName="
				+ senceName + ", initConcurrentUsersSize=" + initConcurrentUsersSize + ", concurrentUsersSize="
				+ concurrentUsersSize + ", taskSize=" + taskSize + ", agentSize=" + agentSize + ", expectThroughput="
				+ expectThroughput + ", continuedTime=" + continuedTime + ", urls=" + urls + ", requestTypes="
				+ requestTypes + ", protocolTypes=" + protocolTypes + ", params=" + params + ", contentTypes="
				+ contentTypes + ", charsets=" + charsets + ", timeUnit=" + timeUnit + "]";
	}
}