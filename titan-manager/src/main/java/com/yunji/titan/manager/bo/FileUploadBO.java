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

/**
 * @desc 文件上传结果响应参数
 *
 * @author liuliang
 *
 */
public class FileUploadBO{

	/**
	 * 文件名
	 */
	private String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public FileUploadBO() {
	}

	public FileUploadBO(String filename) {
		super();
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "FileUploadBO [filename=" + filename + "]";
	}
}
