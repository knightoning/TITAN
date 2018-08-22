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

import java.io.Serializable;

/**
 * RPC结果信息返回
 * 
 * @author gaoxianglong
 */
public class Result<T> implements Serializable{
	private static final long serialVersionUID = 12505436322825303L;
	private boolean success;
	private int errorCode;
	private String errorMsg;
	private T data;

	public Result<T> getResult(boolean success, ErrorCode errorCodeEnum) {
		return getResult(success, errorCodeEnum, null);
	}

	public Result<T> getResult(boolean success, ErrorCode errorCodeEnum, T data) {
		this.success = success;
		this.errorCode = errorCodeEnum.code;
		this.errorMsg = errorCodeEnum.errorMsg;
		this.data = data;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [success=" + success + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", data=" + data
				+ "]";
	}
}