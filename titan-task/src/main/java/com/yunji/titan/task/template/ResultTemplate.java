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
package com.yunji.titan.task.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yunji.titan.task.exception.PerformanceTestException;
import com.yunji.titan.task.exception.PullTaskException;
import com.yunji.titan.task.exception.ResourceException;
import com.yunji.titan.task.exception.StartPerformanceTestException;
import com.yunji.titan.task.exception.StopPerformanceTestException;
import com.yunji.titan.task.exception.TaskException;
import com.yunji.titan.utils.ErrorCode;
import com.yunji.titan.utils.Result;

/**
 * RPC接口出参模板类
 * 
 * @author gaoxianglong
 */
public interface ResultTemplate<T, E> {
	Logger log = LoggerFactory.getLogger(ResultTemplate.class);

	/**
	 * 封装RPC接口出参
	 * 
	 * @author gaoxianglong
	 * 
	 * @param param
	 * 
	 * @return Result<T>
	 */
	default Result<T> getResult(E param) {
		Result<T> result = new Result<>();
		try {
			result = invoke(param);
		} catch (ResourceException e) {
			result.getResult(false, e.getErrorCode());
			log.error("error", e);
		} catch (StartPerformanceTestException e) {
			result.getResult(false, e.getErrorCode());
			log.error("error", e);
		} catch (StopPerformanceTestException e) {
			result.getResult(false, e.getErrorCode());
			log.error("error", e);
		} catch (PullTaskException e) {
			result.getResult(false, e.getErrorCode());
			log.error("error", e);
		} catch (PerformanceTestException e) {
			result.getResult(false, ErrorCode.FAIL);
			log.error("error", e);
		} catch (TaskException e) {
			result.getResult(false, ErrorCode.FAIL);
			log.error("error", e);
		} catch (Exception e) {
			result.getResult(false, ErrorCode.FAIL);
			log.error("error", e);
		} catch (Throwable e) {
			result.getResult(false, ErrorCode.FAIL);
			log.error("error", e);
		}
		return result;
	}

	/**
	 * 执行目标方法调用
	 * 
	 * @author gaoxianglong
	 * 
	 * @param param
	 * 
	 * @return Result<T>
	 */
	public abstract Result<T> invoke(E param);
}
