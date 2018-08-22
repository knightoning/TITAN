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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化工具类
 * 
 * @author gaoxianglong
 */
public class SerializableUtils {
	private static Logger log = LoggerFactory.getLogger(SerializableUtils.class);

	/**
	 * 将POJO执行序列化操作
	 * 
	 * @author gaoxianglong
	 */
	public static <T> byte[] toByteArray(T t) {
		byte[] value = null;
		if (null == t) {
			return value;
		}
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				ObjectOutputStream outObj = new ObjectOutputStream(out)) {
			outObj.writeObject(t);
			value = out.toByteArray();
		} catch (Exception e) {
			log.error("序列化失败", e);
		}
		return value;
	}

	/**
	 * 反序列化操作
	 * 
	 * @author gaoxianglong
	 */
	public static <T> T toObject(byte[] value) {
		T t = null;
		if (null == value) {
			return t;
		}
		try (ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(value))) {
			t = (T) inObj.readObject();
		} catch (Exception e) {
			log.error("反序列化失败", e);
		}
		return t;
	}
}
