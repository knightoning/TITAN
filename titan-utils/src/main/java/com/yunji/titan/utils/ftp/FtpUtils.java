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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP工具类
 * 
 * @author gaoxianglong
 */
public class FtpUtils {
	private FtpConnManager ftpConnManager;
	private static Logger log = LoggerFactory.getLogger(FtpUtils.class);

	public FtpUtils(FtpConnManager ftpConnManager) {
		this.ftpConnManager = ftpConnManager;
	}

	/**
	 * 上传文件至FTP服务器
	 * 
	 * @author gaoxianglong
	 */
	public boolean uploadFile(File file) {
		boolean result = false;
		FTPClient ftpClient = ftpConnManager.getFTPClient();
		if (null == ftpClient || !ftpClient.isConnected()) {
			return result;
		}
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file.getPath()))) {
			boolean storeFile = ftpClient.storeFile(file.getName(), in);
			if (storeFile) {
				result = true;
				log.info("file-->" + file.getPath() + "成功上传至FTP服务器");
			}
		} catch (Exception e) {
			log.error("error", e);
		} finally {
			disconnect(ftpClient);
		}
		return result;
	}

	public boolean uploadFile(File file, byte[] value) {
		boolean result = false;
		if (null == value) {
			return result;
		}
		FTPClient ftpClient = ftpConnManager.getFTPClient();
		if (null == ftpClient || !ftpClient.isConnected()) {
			return result;
		}
		try (BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(value))) {
			boolean storeFile = ftpClient.storeFile(file.getName(), in);
			if (storeFile) {
				result = true;
				log.info("file-->" + file.getPath() + "成功上传至FTP服务器");
			}
		} catch (Exception e) {
			log.error("error", e);
		} finally {
			disconnect(ftpClient);
		}
		return result;
	}

	/**
	 * 从FTP服务器下载指定的文件至本地
	 * 
	 * @author gaoxianglong
	 */
	public boolean downloadFile(File file) {
		boolean result = false;
		FTPClient ftpClient = ftpConnManager.getFTPClient();
		if (null == ftpClient || !ftpClient.isConnected()) {
			return result;
		}
		try (BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(System.getProperty("user.home") + "/" + file.getName()))) {
			result = ftpClient.retrieveFile(file.getName(), out);
			if (result) {
				result = true;
				log.info("file-->" + file.getPath() + "成功从FTP服务器下载");
			}
		} catch (Exception e) {
			log.error("error", e);
		} finally {
			disconnect(ftpClient);
		}
		return result;
	}

	/**
	 * @desc 根据文件名下载文件
	 *
	 * @author liuliang
	 *
	 * @param filename
	 *            文件名
	 * @return boolean下载结果
	 */
	public byte[] downloadFile(String filename) {
		FTPClient ftpClient = ftpConnManager.getFTPClient();
		if (null == ftpClient || !ftpClient.isConnected()) {
			log.error("根据文件名下载文件失败,获取ftpClient失败,filename:{}", filename);
			return null;
		}
		try {
			ftpClient.enterLocalPassiveMode();
			InputStream ins = ftpClient.retrieveFileStream(new String(filename.getBytes("UTF-8"), "iso-8859-1"));
			if (null == ins) {
				return null;
			}
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			int value =100;
			while ((rc = ins.read(buff, 0, value)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] fileByte = swapStream.toByteArray();
			// ftpClient.getReply();
			return fileByte;
		} catch (IOException e) {
			log.error("根据文件名下载文件异常,filename:{}", filename, e);
		} finally {
			disconnect(ftpClient);
		}
		return null;
	}

	/**
	 * 删除服务器上指定的文件
	 * 
	 * @author gaoxianglong
	 */
	public boolean deleteFile(File file) {
		boolean result = false;
		FTPClient ftpClient = ftpConnManager.getFTPClient();
		if (null == ftpClient || !ftpClient.isConnected()) {
			return result;
		}
		try {
			result = ftpClient.deleteFile(file.getName());
			if (result) {
				result = true;
				log.info("file-->" + file.getPath() + "成功从FTP服务器删除");
			}
		} catch (Exception e) {
			log.error("error", e);
		} finally {
			disconnect(ftpClient);
		}
		return result;
	}

	/**
	 * 断开FTP连接
	 * 
	 * @author gaoxianglong
	 */
	protected static void disconnect(FTPClient ftpClient) {
		if (null != ftpClient) {
			try {
				ftpClient.disconnect();
			} catch (IOException e1) {
				log.error("断开FTP会话连接异常", e1);
			}
		}
	}
}