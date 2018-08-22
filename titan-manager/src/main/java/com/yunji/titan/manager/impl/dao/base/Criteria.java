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
 * @desc SQL组装对象
 *
 * @author liuliang
 *
 */
public class Criteria {

	/**
	 * 操作的类
	 */
	private Class<?> clazz;
	/**
	 * 字段名
	 */
	private String[] fieldNames;
	/**
	 * 运算符
	 */
	private String[] operator;
	/**
	 * 衔接符
	 */
	private String[] connector;
	/**
	 * 字段值
	 */
	private Object[] fieldValues;

	public Criteria(){}
	public Criteria(Class<?> clazz, String[] fieldNames, String[] operator,
			Object[] fieldValues) {
		super();
		this.clazz = clazz;
		this.fieldNames = fieldNames;
		this.operator = operator;
		this.fieldValues = fieldValues;
	}
	public Criteria(Class<?> clazz, String[] fieldNames, String[] operator,String[] connector,
			Object[] fieldValues) {
		super();
		this.clazz = clazz;
		this.fieldNames = fieldNames;
		this.operator = operator;
		this.connector = connector;
		this.fieldValues = fieldValues;
	}

	/**
	 * 一个参数查询条件
	 * 
	 * @author liuliang
	 *
	 * @param clazz
	 * @param fieldName
	 * @param operator
	 * @param fieldValue
	 * @return
	 */
	public static Criteria create(Class<?> clazz,String fieldName,String operator,Object fieldValue){
		return new Criteria(clazz,new String[]{fieldName},new String[]{operator},new Object[]{fieldValue});
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	public String[] getFieldNames() {
		return fieldNames;
	}
	public String[] getOperator() {
		return operator;
	}
	public Object[] getFieldValues() {
		return fieldValues;
	}
	public String[] getConnector() {
		return connector;
	}
	public void setConnector(String[] connector) {
		this.connector = connector;
	}

}
