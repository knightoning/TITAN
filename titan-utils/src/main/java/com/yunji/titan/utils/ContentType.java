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

/**
 * 内容类型
 * 
 * @author gaoxianglong
 */
public enum ContentType {
	/* ssss */
	APPLICATION_JSON("application/json"), APPLICATION_XML("application/xml"), TEXT_XML("text/xml"), TEXT_HTML(
			"text/html"), APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");
	public String type = null;

	ContentType(String type) {
		this.type = type;
	}
}