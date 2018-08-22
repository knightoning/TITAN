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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.yunji.titan.manager.result.Result;
import com.yunji.titan.utils.ErrorCode;

/**
 * @desc 参数校验工具类
 *
 * @author liuliang
 *
 */
public class ValidatorUtil {
	private static Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);
	/**
	 * @desc 根据BindingResult获取数据校验信息
	 *
	 * @author liuliang
	 *
	 * @param br
	 * @return
	 */
	public static Result getValidResult(BindingResult br){
    	if(!br.hasErrors()){
    		 return ResultUtil.success(new Result());
    	}else{
    		List<FieldError> list = br.getFieldErrors();
    		StringBuilder sb = new StringBuilder();
    		for (FieldError e: list) {
    			sb.append(e.getField()).append(":").append(e.getDefaultMessage()).append(";");
    		}
    		String errorMessage = StringUtils.removeEnd(sb.toString(),";");
    		logger.error("参数异常,errorMessage:{}",errorMessage);
    		return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,errorMessage,new Result());
    	}
    }
}
