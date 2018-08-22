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

/**
 * 抽象解释器接口
 * 
 * @author gaoxianglong
 */
public interface AbstractExpression {
	/**
	 * 获取param、header等相关信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @param context
	 * 
	 * @return String
	 */
	public String get(ParamContext context);
}