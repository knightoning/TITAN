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
package com.yunji.titan.datacollect.bean.po;

/**
 * 报表实体Bean,对应数据库表[t_report]
 *
 * @author gaoxiangong
 */
public class ReportPO {
	private String reportName;
	private int sceneId;
	private String sceneName;
	private long startTime;
	private long endTime;
	/**
	 * 期待吞吐量
	 */
	private int expectTps;
	/**
	 * 实际吞吐量
	 */
	private int actualTps;
	/**
	 * 总并发请求数
	 */
	private long totalRequest;
	private int successRequest;
	private int businessSuccessRequest;
	/**
	 * 并发用户数
	 */
	private int concurrentUser;
	/**
	 * 用户平均请求等待时间
	 */
	private long userWaittime;
	/**
	 * 服务器平均请求等待时间
	 */
	private long serverWaittime;
	/**
	 * 测试报告结论（0：合格 1：不合格）
	 */
	private int conclusion;
	private long createTime;

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
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

	public int getExpectTps() {
		return expectTps;
	}

	public void setExpectTps(int expectTps) {
		this.expectTps = expectTps;
	}

	public int getActualTps() {
		return actualTps;
	}

	public void setActualTps(int actualTps) {
		this.actualTps = actualTps;
	}

	public long getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(long totalRequest) {
		this.totalRequest = totalRequest;
	}

	public int getSuccessRequest() {
		return successRequest;
	}

	public void setSuccessRequest(int successRequest) {
		this.successRequest = successRequest;
	}

	public int getBusinessSuccessRequest() {
		return businessSuccessRequest;
	}

	public void setBusinessSuccessRequest(int businessSuccessRequest) {
		this.businessSuccessRequest = businessSuccessRequest;
	}

	public int getConcurrentUser() {
		return concurrentUser;
	}

	public void setConcurrentUser(int concurrentUser) {
		this.concurrentUser = concurrentUser;
	}

	public long getUserWaittime() {
		return userWaittime;
	}

	public void setUserWaittime(long userWaittime) {
		this.userWaittime = userWaittime;
	}

	public long getServerWaittime() {
		return serverWaittime;
	}

	public void setServerWaittime(long serverWaittime) {
		this.serverWaittime = serverWaittime;
	}

	public int getConclusion() {
		return conclusion;
	}

	public void setConclusion(int conclusion) {
		this.conclusion = conclusion;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ReportPO [reportName=" + reportName + ", sceneid=" + sceneId + ", sceneName=" + sceneName
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", expectTps=" + expectTps + ", actualTps="
				+ actualTps + ", totalRequest=" + totalRequest + ", successRequest=" + successRequest
				+ ", businessSuccessRequest=" + businessSuccessRequest + ", concurrentUser=" + concurrentUser
				+ ", userWaittime=" + userWaittime + ", serverWaittime=" + serverWaittime + ", conclusion=" + conclusion
				+ ", createTime=" + createTime + "]";
	}
}