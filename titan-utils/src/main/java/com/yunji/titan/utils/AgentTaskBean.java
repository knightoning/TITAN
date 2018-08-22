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
package com.yunji.titan.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 压测任务Bean
 * 
 * @author gaoxianglong
 */
public class AgentTaskBean {
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
	private Map<String, List<String>> params = new ConcurrentHashMap<>();
	private Map<String, ContentType> contentTypes = new ConcurrentHashMap<>();
	private Map<String, String> charsets = new ConcurrentHashMap<>();

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

	public int getInitConcurrentUsersSize() {
		return initConcurrentUsersSize;
	}

	public void setInitConcurrentUsersSize(int initConcurrentUsersSize) {
		this.initConcurrentUsersSize = initConcurrentUsersSize;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSenceName() {
		return senceName;
	}

	public void setSenceName(String senceName) {
		this.senceName = senceName;
	}

	public long getContinuedTime() {
		return continuedTime;
	}

	public void setContinuedTime(long continuedTime) {
		this.continuedTime = continuedTime;
	}

	public int getExpectThroughput() {
		return expectThroughput;
	}

	public void setExpectThroughput(int expectThroughput) {
		this.expectThroughput = expectThroughput;
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

	public Map<String, List<String>> getParams() {
		return params;
	}

	public void setParams(Map<String, List<String>> params) {
		this.params = params;
	}
}