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

/**
 * @desc 日期工具类
 *
 * @author liuliang
 *
 */
public class TitanDateUtil {

	/**
	 * @desc 根据毫秒数获取转换为时、分、秒后的描述
	 *
	 * @author liuliang
	 *
	 * @param second 秒
	 * @return String
	 */
	public static String getDescBySecond(long seconds){
		String timeStr = seconds + "秒";    
        if (seconds > 60) {    
            long second = seconds % 60;    
            long min = seconds / 60;    
            timeStr = min + "分" + second + "秒";    
            if (min > 60) {    
                min = (seconds / 60) % 60;    
                long hour = (seconds / 60) / 60;    
                timeStr = hour + "小时" + min + "分" + second + "秒";    
            }    
        }    
        return timeStr;    
	}
}
