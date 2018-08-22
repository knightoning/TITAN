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

import java.math.BigDecimal;

/**
 * @desc 数字格式化工具
 * 
 * @author liuliang
 *
 */
public class NumberUtil {

	/**
	 * @desc 保留2位小数(四舍五入)
	 *
	 * @author liuliang
	 *
	 * @param number
	 * @return double
	 */
	public static double format(double number){
		try {
			return new BigDecimal(number).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}
}
