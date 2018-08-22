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
/**
 *  默认名称获取实现
 * 
 * @author liuliang
 *
 */
public class DefaultNameHandler implements NameHandler{

	/**
	 * 表名前缀
	 */
	private static final String TABLE_PREFIX = "t_";
	 /**
	  * 主键后缀
	  */
    private static final String PK_SUFFIX = "_id";
	
    
	@Override
	public String getTableName(Class<?> clazz) {
		return TABLE_PREFIX + getUnderscoreName(clazz.getSimpleName()); 
	}

	@Override
	public String getPrimaryKeyName(Class<?> clazz) {
		return getUnderscoreName(clazz.getSimpleName()) + PK_SUFFIX;
	}
	
	@Override
	public String getColumnName(String fieldName) {
		return getUnderscoreName(fieldName); 
	}

	private String getUnderscoreName(String name){
		if((null == name) || ("" == name)){
			return name;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(name.substring(0, 1).toLowerCase());
		for (int i = 1; i < name.length(); i++) {
			String s = name.substring(i, i + 1);
			String slc = s.toLowerCase();
			if (!s.equals(slc)) {
				sb.append("_").append(slc);
			}else {
				sb.append(s);
			}
		}
		return sb.toString();
	}
}
