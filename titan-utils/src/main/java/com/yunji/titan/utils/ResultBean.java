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

/**
 * 压测结果Bean
 * 
 * @author gaoxianglong
 */
public class ResultBean {
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 场景ID
	 */
	private int senceId;
	private String senceName;
	private long startTime;
	private long endTime;
	/**
	 * 持续时间
	 */
	private long continuedTime;
	/**
	 * 期待吞吐量
	 */
	private int expectThroughput;
	private int throughput;
	/**
	 * 并发用户数
	 */
	private int concurrentUsers;
	private long taskSize;
	/**
	 * 用户平均请求等待时间
	 */
	private double timePerRequestbyUser;
	/**
	 * 服务器平均请求等待时间
	 */
	private double timePerRequestbyServer;
	/**
	 * HTTP200请求成功数
	 */
	private int httpSuccessNum;
	/**
	 * 业务成功数
	 */
	private int serviceSuccessNum;
	private int agentSize;

	public int getHttpSuccessNum() {
		return httpSuccessNum;
	}

	public void setHttpSuccessNum(int httpSuccessNum) {
		this.httpSuccessNum = httpSuccessNum;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getServiceSuccessNum() {
		return serviceSuccessNum;
	}

	public void setServiceSuccessNum(int serviceSuccessNum) {
		this.serviceSuccessNum = serviceSuccessNum;
	}

	public String getSenceName() {
		return senceName;
	}

	public void setSenceName(String senceName) {
		this.senceName = senceName;
	}

	public int getAgentSize() {
		return agentSize;
	}

	public void setAgentSize(int agentSize) {
		this.agentSize = agentSize;
	}

	public int getSenceId() {
		return senceId;
	}

	public void setSenceId(int senceId) {
		this.senceId = senceId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
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

	public int getThroughput() {
		return throughput;
	}

	public void setThroughput(int throughput) {
		this.throughput = throughput;
	}

	public int getConcurrentUsers() {
		return concurrentUsers;
	}

	public void setConcurrentUsers(int concurrentUsers) {
		this.concurrentUsers = concurrentUsers;
	}

	public long getTaskSize() {
		return taskSize;
	}

	public void setTaskSize(long taskSize) {
		this.taskSize = taskSize;
	}

	public double getTimePerRequestbyUser() {
		return timePerRequestbyUser;
	}

	public void setTimePerRequestbyUser(double timePerRequestbyUser) {
		this.timePerRequestbyUser = timePerRequestbyUser;
	}

	public double getTimePerRequestbyServer() {
		return timePerRequestbyServer;
	}

	public void setTimePerRequestbyServer(double timePerRequestbyServer) {
		this.timePerRequestbyServer = timePerRequestbyServer;
	}
}