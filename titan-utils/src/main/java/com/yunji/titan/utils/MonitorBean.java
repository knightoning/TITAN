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
 * @desc monitor数据上报bean
 *
 * @author liuliang
 *
 */
public class MonitorBean{

	/**
	 * 服务机器类型（0：agent机器  1：目标机器）
	 */
	private Integer serverType;
	/**
	 * 机器IP
	 */
	private String ip;
	/**
	 * CPU使用率
	 */
	private Double cpuUsage;
	/**
	 * 内存使用率
	 */
	private Double memoryUsage;
	/**
	 * 磁盘IOPS
	 */
	private Double iops;
	/**
	 * 记录创建时间
	 */
	private Long createTime;
	public Integer getServerType() {
		return serverType;
	}
	public void setServerType(Integer serverType) {
		this.serverType = serverType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Double getCpuUsage() {
		return cpuUsage;
	}
	public void setCpuUsage(Double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	public Double getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(Double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	public Double getIops() {
		return iops;
	}
	public void setIops(Double iops) {
		this.iops = iops;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "MonitorBean [serverType=" + serverType + ", ip=" + ip + ", cpuUsage=" + cpuUsage + ", memoryUsage="
				+ memoryUsage + ", iops=" + iops + ", createTime=" + createTime + "]";
	}
}
