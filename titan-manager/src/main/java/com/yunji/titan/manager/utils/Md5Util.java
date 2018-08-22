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

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @desc MD5加密工具类
 *
 * @author liuliang
 *
 */
public class Md5Util {

	/**
	 * @desc 获取MD5加密后的字符串
	 *
	 * @author liuliang
	 *
	 * @param str 待加密的字符串
	 * @return String 加密后的字符串(若发生异常,则返回原待加密字符串)
	 */
	public static String getMD5Str(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }
}
