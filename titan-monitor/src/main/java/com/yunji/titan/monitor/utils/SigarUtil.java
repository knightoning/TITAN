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
package com.yunji.titan.monitor.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * @desc Sigar获取系统信息工具类
 *
 * @author liuliang
 *
 */
public class SigarUtil {

	private static Sigar sigar;
	
	private static Sigar getInstance() {  
        if (null == sigar) {  
            sigar = new Sigar();  
        }  
        return sigar;  
    }  
	
	/**
	 * @desc 获取cpu使用率
	 *
	 * @author liuliang
	 *
	 * @return Double 比例(保留2位小数)
	 */
	public static Double getCPUUsage() {
		double cpuUsage = 0;
		try {
			CpuPerc[] cpuList = getInstance().getCpuPercList();
			// 不管是单块CPU还是多CPU都适用
			for (int i = 0; i < cpuList.length; i++) {
				cpuUsage += cpuList[i].getCombined() * 100;
			}
			cpuUsage = NumberUtil.format(cpuUsage / cpuList.length);
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return cpuUsage;
	}
	
	/**
	 * @desc 获取内存使用率
	 *
	 * @author liuliang
	 *
	 * @return Double 比例(保留2位小数)
	 */
	public static Double getMemoryUsage() {
		double memoryUsage = 0;
		try {
			Mem mem = getInstance().getMem();
			memoryUsage = NumberUtil.format(mem.getUsedPercent());
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return memoryUsage;
	}
	
	/**
	 * @desc 获取机器IP地址
	 *
	 * @author liuliang
	 *
	 * @return String
	 */
	public static String getHostAddress() {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}  
		return inetAddress.getHostAddress();
	}
}
