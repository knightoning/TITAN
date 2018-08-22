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

import java.util.List;

/**
 * @desc 概览页数据响应结果BO
 *
 * @author liuliang
 *
 */
public class OverviewResultBO {

	/**
	 * 总节点数
	 */
	private Integer totalNodesNum;
	/**
	 * 可用节点数
	 */
	private Integer availableNodesNum;
	/**
	 * 总链路数
	 */
	private Integer totalLinkNum;
	/**
	 * 总场景数
	 */
	private Integer totalSceneNum;
	/**
	 * 总测试报告数
	 */
	private Integer totalReportNum;
	/**
	 * 可用节点IP集合
	 */
	private List<String> availableNodesIPList;
	/**
	 * 已用节点IP集合
	 */
	private List<String> usedNodesIPList;

	public Integer getTotalNodesNum() {
		return totalNodesNum;
	}

	public void setTotalNodesNum(Integer totalNodesNum) {
		this.totalNodesNum = totalNodesNum;
	}

	public Integer getAvailableNodesNum() {
		return availableNodesNum;
	}

	public void setAvailableNodesNum(Integer availableNodesNum) {
		this.availableNodesNum = availableNodesNum;
	}

	public Integer getTotalLinkNum() {
		return totalLinkNum;
	}

	public void setTotalLinkNum(Integer totalLinkNum) {
		this.totalLinkNum = totalLinkNum;
	}

	public Integer getTotalSceneNum() {
		return totalSceneNum;
	}

	public void setTotalSceneNum(Integer totalSceneNum) {
		this.totalSceneNum = totalSceneNum;
	}

	public Integer getTotalReportNum() {
		return totalReportNum;
	}

	public void setTotalReportNum(Integer totalReportNum) {
		this.totalReportNum = totalReportNum;
	}

	public List<String> getAvailableNodesIPList() {
		return availableNodesIPList;
	}

	public void setAvailableNodesIPList(List<String> availableNodesIPList) {
		this.availableNodesIPList = availableNodesIPList;
	}

	public List<String> getUsedNodesIPList() {
		return usedNodesIPList;
	}

	public void setUsedNodesIPList(List<String> usedNodesIPList) {
		this.usedNodesIPList = usedNodesIPList;
	}

	@Override
	public String toString() {
		return "OverviewResultBO [totalNodesNum=" + totalNodesNum + ", availableNodesNum=" + availableNodesNum
				+ ", totalLinkNum=" + totalLinkNum + ", totalSceneNum=" + totalSceneNum + ", totalReportNum="
				+ totalReportNum + ", availableNodesIPList=" + availableNodesIPList + ", usedNodesIPList="
				+ usedNodesIPList + "]";
	}

}
