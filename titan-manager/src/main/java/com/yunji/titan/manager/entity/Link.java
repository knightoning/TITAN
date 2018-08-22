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
 * @desc 链路表实体,对应表[t_link]
 *
 * @author liuliang
 *
 */
public class Link {
	/**
	 * 主键自增ID
	 */
	private Long linkId;
	/**
	 * 链路名
	 */
	private String linkName;
	/**
	 * 协议类型（0：http、1：https）
	 */
	private Integer protocolType;
	/**
	 * 压测URL
	 */
	private String stresstestUrl;
	/**
	 * 请求类型（0：get:1：post）
	 */
	private Integer requestType;
	/**
	 * 内容类型
	 */
	private Integer contentType;
	/**
	 * 字符编码
	 */
    private Integer charsetType;
	/**
	 * 压测文件路径
	 */
	private String testfilePath;
	/**
	 * 记录创建时间
	 */
	private Long createTime;
	/**
	 * 记录最后修改时间
	 */
	private Long modifyTime;
	public Long getLinkId() {
		return linkId;
	}
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public Integer getProtocolType() {
		return protocolType;
	}
	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}
	public String getStresstestUrl() {
		return stresstestUrl;
	}
	public void setStresstestUrl(String stresstestUrl) {
		this.stresstestUrl = stresstestUrl;
	}
	public Integer getRequestType() {
		return requestType;
	}
	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public Integer getCharsetType() {
		return charsetType;
	}
	public void setCharsetType(Integer charsetType) {
		this.charsetType = charsetType;
	}
	public String getTestfilePath() {
		return testfilePath;
	}
	public void setTestfilePath(String testfilePath) {
		this.testfilePath = testfilePath;
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
	
}