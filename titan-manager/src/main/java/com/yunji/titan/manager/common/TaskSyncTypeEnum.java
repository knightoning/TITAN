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
package com.yunji.titan.manager.common;

/**
 * @desc 自动压测任务枚举
 *
 * @author liuliang
 *
 */
public enum TaskSyncTypeEnum {

	/**
	 * 更新
	 */
	UPDATE(1,"更新"),
	/**
	 * 删除
	 */
	DELETE(2,"删除");
	
	/**
	 * 值
	 */
	public int value;
	/**
	 * 描述
	 */
	public String desc;
	
	private TaskSyncTypeEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	public int getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}
}
