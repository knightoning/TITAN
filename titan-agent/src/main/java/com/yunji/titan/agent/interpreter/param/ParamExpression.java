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
package com.yunji.titan.agent.interpreter.param;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * 参数解释器
 * 
 * @author gaoxianglong
 */
@Component("paramExpression")
public class ParamExpression implements AbstractExpression {
	@Override
	public String get(ParamContext context) {
		String param = context.getParams();
		if (StringUtils.isEmpty(param)) {
			return null;
		}
		String[] params = param.split(Token.SEPARATOR);
		if (1 < params.length) {
			param = params[0];
			context.setParams(params[1]);
		} else {
			try {
				/* 验证是否是header信息 */
				Object header = JSONObject.parseObject(param).get(Token.HEADER);
				if (null != header) {
					context.setParams(param);
					param = null;
				}
			} catch (Exception e) {
				context.setParams(null);
			}
		}
		return param;
	}
}