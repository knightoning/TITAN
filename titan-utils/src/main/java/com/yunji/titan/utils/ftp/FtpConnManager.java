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
package com.yunji.titan.utils.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP连接管理类
 * 
 * @author gaoxianglong
 */
public class FtpConnManager {
	private int bufferSize = 1024;
	private int connectTimeout = 1000;
	private int soTimeout = 1000;
	private String hostname;
	private String userName;
	private String passWord;
	private String encoding = "UTF-8";
	private Logger log = LoggerFactory.getLogger(FtpConnManager.class);

	protected FTPClient getFTPClient() {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.setConnectTimeout(connectTimeout);
			ftpClient.connect(hostname);
			ftpClient.login(userName, passWord);
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				FtpUtils.disconnect(ftpClient);
				log.warn("FTP登陆失败,账号或者密码有误");
			} else {
				ftpClient.setSoTimeout(soTimeout);
				/* 设置缓冲区大小 */
				ftpClient.setBufferSize(bufferSize);
				/* 设置服务器编码 */
				ftpClient.setControlEncoding(encoding);
				/* 设置以二进制方式传输 */
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				/* 设置服务器目录 */
				// ftpClient.changeWorkingDirectory(directory);
				ftpClient.enterLocalPassiveMode();
				log.debug("成功连接并登录FTP服务器。。。");
			}
		} catch (Exception e) {
			FtpUtils.disconnect(ftpClient);
			log.error("error", e);
		}
		return ftpClient;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}