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
package com.yunji.titan.manager.impl.dao.base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc 获取对象信息工具类
 *
 * @author liuliang
 *
 */
public class BeanInfoUtil {
	 private static Logger logger = LoggerFactory.getLogger(BeanInfoUtil.class);
	 
	 private static Map<Class<?>, BeanInfo> methodCache = Collections.synchronizedMap(new WeakHashMap<Class<?>, BeanInfo>());
	 
	/**
	 * @desc 根据对象获取对象所有属性、属性值
	 *
	 * @author liuliang
	 *
	 * @param entity 对象
	 * @return Map<String,Object> [key:属性名   value:属性名对应的值]
	 */
	public static Map<String,Object> getBeanInfo(Object entity){
		Map<String,Object> map = new HashMap<String, Object>(16);
		try {
			Class<?> clazz = entity.getClass();
			BeanInfo beanInfo = null;
			if(null == methodCache.get(clazz)){
				Class<?> superClazz =clazz.getSuperclass();
				beanInfo = Introspector.getBeanInfo(clazz, superClazz);
				methodCache.put(clazz, beanInfo);
				
				Introspector.flushFromCaches(clazz);
				if(null != superClazz){
					Introspector.flushFromCaches(superClazz);
				}
			}else{
				beanInfo = methodCache.get(clazz);
			}
			
			PropertyDescriptor[] pdArr = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd:pdArr){
				String fieldName = pd.getName();
				Object fieldValue = getFieldValue(pd.getReadMethod(),entity);
				map.put(fieldName, fieldValue);
			}
		} catch (IntrospectionException e) {
			logger.error("获取对象信息异常,entity:{}",entity.getClass(),e);
		}
		return map;
	}
	/**
	 * 获取属性值
	 *
	 * @author liuliang
	 *
	 * @param readMethod
	 * @param entity
	 * @return
	 */
	private static Object getFieldValue(Method readMethod, Object entity){
		try {
			if(!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())){
				readMethod.setAccessible(true);
			}
			return readMethod.invoke(entity);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
}
