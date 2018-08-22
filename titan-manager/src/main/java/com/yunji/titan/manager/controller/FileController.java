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
package com.yunji.titan.manager.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yunji.titan.manager.bo.FileUploadBO;
import com.yunji.titan.manager.result.ComponentResult;
import com.yunji.titan.manager.utils.ResultUtil;
import com.yunji.titan.utils.ErrorCode;
import com.yunji.titan.utils.ftp.FtpUtils;

/**
 * @desc 文件操作Controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

	private Logger logger = LoggerFactory.getLogger(FileController.class);

	@Value("${pressure.data.filepath}")
	private String pressureDataFilepath;

	/**
	 * ftp工具类
	 */
	@Resource
	private FtpUtils ftpUtils;
	
	/**
	 * 压测文件上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/fileUpload")
	@ResponseBody
	public ComponentResult<FileUploadBO> fileUpload(HttpServletRequest request,HttpServletResponse response) {
		ComponentResult<FileUploadBO> componentResult = new ComponentResult<FileUploadBO>();
		//1、获取上传参数
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = multiRequest.getFileNames();
			while (iterator.hasNext()) {
				MultipartFile file = multiRequest.getFile(iterator.next().toString());
				if (null != file) {
					//2、上传
					String filename = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + ".xls";
					try {
						File targetFile = new File(pressureDataFilepath + filename);
						file.transferTo(targetFile);
						//3、将本地文件上传到FTP
						boolean uploadResult = ftpUtils.uploadFile(targetFile);
						if(uploadResult){
							componentResult.setData(new FileUploadBO(filename));
							return ResultUtil.success(componentResult);
						}else{
							logger.error("文件上传FTP失败,filename:{}",filename);
							return ResultUtil.fail(ErrorCode.FAIL,"文件上传FTP失败",componentResult);
						}
					} catch (IllegalStateException | IOException e) {
						logger.error("上传文件异常,filename->" + filename,e);
					} 
				}else{
					logger.error("压测文件上传,file为NULL");
				}
			}
		}else{
			logger.error("压测文件上传,request校验失败");
		}
		//3、返回
		return ResultUtil.fail(componentResult);
	}
	
	/**
	 * @desc 下载压测数据文件
	 *
	 * @author liuliang
	 *
	 * @param filename 文件名
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/fileDownload")
	public ResponseEntity<byte[]>  fileDownload(@RequestParam("filename") String filename,HttpServletRequest request,HttpServletResponse response) {
		//1、从ftp下载
		byte[] fileContent = ftpUtils.downloadFile(filename);
		HttpHeaders headers = new HttpHeaders();  
		if(null != fileContent){
			//2、成功返回
			try {
	        	headers.setContentDispositionFormData("attachment", new String(filename.getBytes("UTF-8"),"iso-8859-1")); 
	        	headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        	return new ResponseEntity<byte[]>(fileContent,headers, HttpStatus.CREATED);
			} catch (Exception e) {
				logger.error("下载压测数据文件异常,filename:{}",filename,e);
			}
		}
		//下载失败返回
		logger.warn("从ftp下载压测文件失败,filename:{}",filename);
		headers.add("Content-Disposition","attachment;filename=error.log");  
	    return new ResponseEntity<byte[]>(("从FTP服务器下载压测参数文件失败,filename:"+ filename).getBytes(),headers,HttpStatus.OK); 
	}
}
