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

import org.springframework.stereotype.Component;

/**
 * @desc redis操作辅助工具类
 *
 * @author liuliang
 *
 */
@Component 
public class RedisHelper {
//	@Resource
//	private RedisTemplate<String,Object> redisTemplate;
//	
//	private static RedisHelper redisHelper;
//	private static ValueOperations<String, Object> valueOperations;
//	
//	@PostConstruct  
//	public void init() {
//		redisHelper = this;
//		redisHelper.redisTemplate = this.redisTemplate;
//		valueOperations = redisHelper.redisTemplate.opsForValue();
//	}
//	
//	/**
//	 * @desc 取key值
//	 *
//	 * @author liuliang
//	 *
//	 * @param key
//	 * @return
//	 */
//	public static Object get(String key){
//		if(StringUtils.isNotBlank(key)){
//			 return valueOperations.get(key);
//		}else{
//			return null;
//		}
//	}
//	
//	/**
//	 * @desc 设key值
//	 *
//	 * @author liuliang
//	 *
//	 * @param key
//	 * @param value
//	 */
//	public static void set(String key,Object value){
//		if(StringUtils.isNotBlank(key)){
//			valueOperations.set(key,value);
//		}
//	}
//	
//	/**
//	 * @desc 设值,带过期时间
//	 *
//	 * @author liuliang
//	 *
//	 * @param key
//	 * @param value
//	 * @param ttl 过期时间（单位：秒）
//	 */
//	public static void set(String key,Object value,int ttl){
//		if(StringUtils.isNotBlank(key)){
//			valueOperations.set(key,value);
//			redisHelper.redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
//		}
//	}
//	
//	/**
//	 * @desc 删值
//	 *
//	 * @author liuliang
//	 *
//	 * @param key
//	 */
//	public static void del(String key){
//		if(StringUtils.isNotBlank(key)){
//			redisHelper.redisTemplate.delete(key);
//		}
//	}
}