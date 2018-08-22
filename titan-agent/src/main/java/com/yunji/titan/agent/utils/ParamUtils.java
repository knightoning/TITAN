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
package com.yunji.titan.agent.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yunji.titan.utils.ContentType;

/**
 * 参数相关工具类
 * 
 * @author gaoxianglong
 */
public class ParamUtils {
	private static Logger log = LoggerFactory.getLogger(ParamUtils.class);

	/**
	 * 此方法废弃,提供参数解释器AbstractExpression
	 * 
	 * @author gaoxianglong
	 */
	@Deprecated
	public static Map<String, String> resolve(String param) {
		String[] params = null;
		if (StringUtils.isEmpty(param)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>(16);
		params = param.split("(%%%)");
		if (null != params) {
			if (1 < params.length) {
				map.put("param", params[0]);
				map.put("headers", params[1]);
			} else {
				try {
					Object header = JSONObject.parseObject(params[0]).get("header");
					/* 判断参数是否是头信息 */
					map.put(null != header ? "headers" : "param", params[0]);
				} catch (Exception e) {
					map.put("param", params[0]);
					log.debug("header中不带参数");
				}
			}
		}
		return map;
	}

	/**
	 * 设置请求头
	 *
	 * @author gaoxianglong
	 */
	public static void setHeader(HttpRequestBase request, String headers, ContentType contentType, String charset) {
		if (null == request) {
			return;
		}
		request.setHeader(HTTP.CONTENT_TYPE,
				Optional.ofNullable(contentType.type).orElseGet(() -> ContentType.APPLICATION_XML.type) + ";"
						+ Optional.ofNullable(charset).orElseGet(() -> "UTF-8"));
		if (!StringUtils.isEmpty(headers)) {
			/* 设置请求头参数 */
			JSONObject jsonObj = JSONObject.parseObject(JSONObject.parseObject(headers).get("header").toString());
			jsonObj.keySet().stream().forEach(key -> request.setHeader(key, jsonObj.getString(key)));
		}
	}

	/**
	 * JSON请求合并
	 * 
	 * @author gaoxianglong
	 */
	public static String jsonCombination(String json1, String json2) {
		if (StringUtils.isEmpty(json1)) {
			return json2;
		}
		JSONObject jsonObj = null;
		try {
			jsonObj = JSONObject.parseObject(json1);
			jsonObj.putAll(JSONObject.parseObject(json2));
		} catch (Exception e) {
			log.debug("参数并非是JSON");
		}
		return null != jsonObj ? jsonObj.toJSONString() : null;
	}

	/**
	 * 将JSON转换为url参数
	 * 
	 * @author gaoxianglong
	 */
	public static String jsonToUrl(String p1, String p2) {
		StringBuffer param = new StringBuffer();
		try {
			JSONObject jsonObj = JSONObject.parseObject(p2);
			Set<String> keys = jsonObj.keySet();
			if (!keys.isEmpty()) {
				// if (StringUtils.isEmpty(p1)) {
				// param.append("?");
				// } else {
				// param.append(p1 + "&");
				// }
				param.append(StringUtils.isEmpty(p1) ? "?" : p1 + "&");
				keys.stream().forEach(key -> {
					param.append(key + "=" + jsonObj.get(key).toString());
					param.append("&");
				});
			}
		} catch (Exception e) {
			log.debug("参数并非是JSON");
		} finally {
			/* 删除最后一个符号& */
			if (!StringUtils.isEmpty(param.toString())) {
				int length = param.length();
				param.delete(length - 1, length);
			}
		}
		return param.toString();
	}
}