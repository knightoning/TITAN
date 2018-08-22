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
 * @desc 测试报告表实体,对应表[t_report]
 *
 * @author liuliang
 *
 */
public class Report {
	/**
	 *  主键自增ID
	 */
	private Long reportId;
	/**
	 * 报告名称
	 */
	private String reportName; 
	/**
	 * 场景ID
	 */
	private Long sceneId;
	/**
	 * 场景名称
	 */
	private String sceneName; 
	/**
	 * 测试起始时间
	 */
	private Long startTime; 
	/**
	 * 测试结束时间
	 */
	private Long endTime; 
	/**
	 * 期待吞吐量
	 */
	private Integer expectTps; 
	/**
	 * 实际吞吐量
	 */
	private Integer actualTps; 
	/**
	 * 总并发请求数
	 */
	private Long totalRequest; 
	/**
	 * HTTP200成功请求数
	 */
	private Long successRequest;
	/**
	 * 业务成功请求数
	 */
	private Long businessSuccessRequest;
	/**
	 * 并发用户数
	 */
	private Long concurrentUser; 
	/**
	 * 用户平均请求等待时间（单位：毫秒）
	 */
	private Long userWaittime; 
	/**
	 * 服务器平均请求等待时间（单位：毫秒）
	 */
	private Long serverWaittime; 
	/**
	 * 测试报告结论（0：合格 1：不合格）
	 */
	private Integer conclusion;
	/**
	 * 记录创建时间
	 */
	private Long createTime;
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
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
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getExpectTps() {
		return expectTps;
	}
	public void setExpectTps(Integer expectTps) {
		this.expectTps = expectTps;
	}
	public Integer getActualTps() {
		return actualTps;
	}
	public void setActualTps(Integer actualTps) {
		this.actualTps = actualTps;
	}
	public Long getTotalRequest() {
		return totalRequest;
	}
	public void setTotalRequest(Long totalRequest) {
		this.totalRequest = totalRequest;
	}
	public Long getSuccessRequest() {
		return successRequest;
	}
	public void setSuccessRequest(Long successRequest) {
		this.successRequest = successRequest;
	}
	public Long getBusinessSuccessRequest() {
		return businessSuccessRequest;
	}
	public void setBusinessSuccessRequest(Long businessSuccessRequest) {
		this.businessSuccessRequest = businessSuccessRequest;
	}
	public Long getConcurrentUser() {
		return concurrentUser;
	}
	public void setConcurrentUser(Long concurrentUser) {
		this.concurrentUser = concurrentUser;
	}
	public Long getUserWaittime() {
		return userWaittime;
	}
	public void setUserWaittime(Long userWaittime) {
		this.userWaittime = userWaittime;
	}
	public Long getServerWaittime() {
		return serverWaittime;
	}
	public void setServerWaittime(Long serverWaittime) {
		this.serverWaittime = serverWaittime;
	}
	public Integer getConclusion() {
		return conclusion;
	}
	public void setConclusion(Integer conclusion) {
		this.conclusion = conclusion;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
}