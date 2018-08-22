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
package com.yunji.titan.task.exception;

import com.yunji.titan.utils.ErrorCode;

/**
 * Agent领取任务异常
 * 
 * @author gaoxianglong
 */
public class PullTaskException extends TaskException {
	private static final long serialVersionUID = -1771049149303844212L;
	private ErrorCode errorCode;

	public PullTaskException(String msg) {
		super(msg);
	}

	public PullTaskException(String msg, ErrorCode errorCode) {
		super(msg);
		this.setErrorCode(errorCode);
	}

	private void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}