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
 * agent状态
 * 
 * @author gaoxianglong
 */
public class AgentType {
	/**
	 * 空闲状态
	 */
	public static final byte[] FREE = ("task=false" + "\n" + "interrupted=false" + "\n" + "hostAddress="
			+ Localhost.getHostAddress()).getBytes();

	/**
	 * 忙碌状态
	 */
	public static final byte[] BUSYNESS = ("task=true" + "\n" + "interrupted=false" + "\n" + "hostAddress="
			+ Localhost.getHostAddress()).getBytes();

	/**
	 * 暂停状态
	 */
	public static final byte[] STOP = ("task=true" + "\n" + "interrupted=true" + "\n" + "hostAddress="
			+ Localhost.getHostAddress()).getBytes();
}