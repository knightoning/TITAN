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
package com.yunji.titan.manager.utils;

import org.apache.commons.lang3.StringUtils;

import com.yunji.titan.manager.result.Result;
import com.yunji.titan.utils.ErrorCode;

/**
 * @desc 业务结果工具类
 *
 * @author liuliang
 *
 */
public class ResultUtil {

	/**
	 * @desc 成功
	 *
	 * @author liuliang
	 *
	 * @param result
	 * @return
	 */
	public static <T extends Result> T success(T result) {
		return build(true,null,ErrorCode.SUCCESS, result);
	}
	
	/**
	 * @desc 失败
	 *
	 * @author liuliang
	 *
	 * @param result
	 * @return
	 */
	public static <T extends Result> T fail(T result) {
		return build(false,null,ErrorCode.FAIL, result);
	}
	
	/**
	 * @desc 失败
	 *
	 * @author liuliang
	 *
	 * @param result
	 * @return
	 */
	public static <T extends Result> T fail(ErrorCode errorCode,T result) {
		return build(false,null,errorCode, result);
	}
	
	/**
	 * @desc 失败
	 *
	 * @author liuliang
	 *
	 * @param result
	 * @return
	 */
	public static <T extends Result> T fail(ErrorCode errorCode,String errMsg,T result) {
		return build(false,errMsg,errorCode, result);
	}
	
	/**
	 * @desc 失败
	 *
	 * @author liuliang
	 *
	 * @param result
	 * @return
	 */
	public static <T extends Result> T fail(int errorCode,String errMsg,T result) {
		return build(false,errorCode,errMsg, result);
	}
	
	/**
	 * @desc 判断是否成功
	 *
	 * @author liuliang
	 *
	 * @param result
	 * @return boolean [true:成功  ,false:失败]
	 */
	public static <T extends Result> boolean isSuccess(T result) {
		 return result == null?true:result.isSuccess();
	}
	
	/**
	 * @desc 判断是否失败
	 *
	 * @author liuliang
	 *
	 * @param result
	 * @return boolean [true:失败  ,false:成功]
	 */
	public static boolean isfail(Result result){
        return !isSuccess(result);
    }
	
	/**
	 * @desc 构建结果
	 *
	 * @author liuliang
	 *
	 * @param isSuccess 是否成功
	 * @param msg 提示信息,若为空，使用默认信息
	 * @param errorCode 结果码
	 * @param result 结果
	 * @return Result
	 */
	public static <T extends Result> T build(boolean isSuccess,String msg,ErrorCode errorCode,T result) {
		result.setSuccess(isSuccess);
		result.setErrorCode(errorCode.code);
		result.setErrorMsg(StringUtils.isBlank(msg)?errorCode.errorMsg:msg);
		return result;
	}
	
	public static <T extends Result> T build(boolean isSuccess,int errorCode,String errMsg,T result) {
		result.setSuccess(isSuccess);
		result.setErrorCode(errorCode);
		result.setErrorMsg(errMsg);
		return result;
	}
	
	public static <S extends Result,T extends Result> T copy(S source,T target){
        if(source == null || target ==null){
            return null;
        }
        target.setErrorCode(source.getErrorCode());
        target.setErrorMsg(source.getErrorMsg());
        target.setSuccess(source.isSuccess());
        return target;
    }
}
