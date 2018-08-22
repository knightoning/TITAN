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

/**
 * 错误码枚举
 * 
 * @author gaoxianglong
 */
public enum ErrorCode {
	/* 启动压测成功 */
	START_PERFORMANCE_TEST_SUCCESS(100, "启动压测成功"),
	/* 启动压测失败 */
	START_PERFORMANCE_TEST_FAIL(101, "启动压测失败"),
	/* 可用agent资源不足 */
	FREE_AGENT_lACK(102,"可用agent资源不足"), 
	INTERRUPTED_SUCCESS(103, "压测任务中断成功"),
	INTERRUPTED_FAIL(104, "压测任务中断失败"),
	SUCCESS(1,"操作成功"),
	FAIL(301,"操作失败"),
	UNKNOWN_ERROR(302,"未知错误"),
	REQUEST_PARA_ERROR(303,"请求参数错误"),
	QUERY_DB_ERROR(304,"查询DB失败"),
	UPDATE_DB_ERROR(305,"更新DB失败"),
	LOGIN_ERROR(306,"用户名或密码错误"),
	HAVE_RELATED_RECORD(307,"存在关联记录"),
	PULL_TASK_SUCCESS(308,"领取任务成功"),
	PULL_TASK_FAIL(309,"领取任务失败"),
	SCENE_TYPE_FAIL(105,"场景状态不能满足当前操作"),
	STOP_PERFORMANCE_TEST_SUCCESS(106, "停止压测成功"),
	STOP_PERFORMANCE_TEST_FAIL(107, "停止压测失败"),
	GET_TASK_STOCK_FAIL(108,"场景状态不能满足当前操作"),
	GET_AGENT_HOST_ADDRESS_SUCCESS(109,"获取注册中心所有的agent的hostAddress信息成功"),
	GET_AGENT_HOST_ADDRESS_FAILS(110,"获取注册中心所有的agent的hostAddress信息失败"),
	AGENT_TYPE_ERROR(111,"agent状态不对");
	
	public int code;
	public String errorMsg;

	ErrorCode(int code, String errorInfo) {
		this.code = code;
		this.errorMsg = errorInfo;
	}
}