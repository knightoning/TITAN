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
 * 名称获取接口 
 *
 * @author liuliang
 *
 */
public interface NameHandler {

	/**
     * 根据实体名获取表名
     *
     * @param clazz
     * @return
     */
    public String getTableName(Class<?> clazz);
    /**
     * 根据表名获取主键名
     * 
     * @param clazz
     * @return
     */
    public String getPrimaryKeyName(Class<?> clazz);
    /**
     * 根据属性名获取列名
     *
     * @param fieldName
     * @return
     */
    public String getColumnName(String fieldName);
}
