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
 * Znode路径信息
 * 
 * @author gaoxianglong
 */
public class NodePath {
	public static String ROOT_NODEPATH = "/titan";
	public static String TIMETASK_LOCK_NODEPATH = ROOT_NODEPATH + "/timerTaskLock";
	public static String TIMETASK_NODEPATH = ROOT_NODEPATH + "/timerTask";
	public static String AGENT_LOCK_NODEPATH = ROOT_NODEPATH + "/taskLock";
	public static String DATACOLLECT_LOCK_NODEPATH = ROOT_NODEPATH + "/datacollectLock";
}
