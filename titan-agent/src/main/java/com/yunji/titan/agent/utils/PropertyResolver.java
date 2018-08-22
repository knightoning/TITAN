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

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.yunji.titan.agent.interpreter.param.Token;
import com.yunji.titan.utils.RequestType;

/**
 * 占位符${}解析器
 * 
 * @author gaoxianglong
 */
public class PropertyResolver {
	public static String resolver(String inParam, String outParam, RequestType type) {
		if (StringUtils.isEmpty(inParam) || StringUtils.isEmpty(outParam)) {
			return inParam;
		}
		List<String> keys = new ArrayList<>();
		String tragetParam = inParam;
		while (-1 != tragetParam.indexOf(Token.DOLLAR + Token.OPEN_BRACE)) {
			/* 计算${开始索引 */
			int startIndex = tragetParam.indexOf(Token.DOLLAR + Token.OPEN_BRACE) + 2;
			/* 计算}结束索引 */
			int endIndex = tragetParam.substring(startIndex).indexOf(Token.CLOSE_BRACE);
			keys.add(tragetParam.substring(startIndex, startIndex + endIndex));
			tragetParam = tragetParam.substring(startIndex);
		}
		if (keys.isEmpty()) {
			return inParam;
		}
		JSONObject jsonObj = JSONObject.parseObject(outParam);
		StringBuffer targetBuf = new StringBuffer();
		StringBuffer replacementBuf = new StringBuffer();
		for (String key : keys) {
			try {
				targetBuf.append(Token.DOLLAR);
				targetBuf.append(Token.OPEN_BRACE);
				targetBuf.append(key);
				targetBuf.append(Token.CLOSE_BRACE);
				Object obj = jsonObj.get(key);
				if (!StringUtils.isEmpty(obj)) {
					if (type == RequestType.GET) {
						replacementBuf.append(key);
						replacementBuf.append(Token.EQUAL);
						replacementBuf.append(obj.toString());
					} else if (type == RequestType.POST) {
						replacementBuf.append(Token.APOSTROPHE);
						replacementBuf.append(key);
						replacementBuf.append(Token.APOSTROPHE);
						replacementBuf.append(Token.COLON);
						replacementBuf.append(Token.APOSTROPHE);
						replacementBuf.append(obj.toString());
						replacementBuf.append(Token.APOSTROPHE);
					}
					inParam = inParam.replace(targetBuf.toString(), replacementBuf.toString());
				} else {
					if (type == RequestType.GET) {
						targetBuf.insert(0, Token.AMPERSAND);
					} else if (type == RequestType.POST) {
						targetBuf.insert(0, Token.COMMA);
					}
					inParam = inParam.replace(targetBuf.toString(), "");
				}
			} finally {
				targetBuf.setLength(0);
				replacementBuf.setLength(0);
			}
		}
		return inParam;
	}
}