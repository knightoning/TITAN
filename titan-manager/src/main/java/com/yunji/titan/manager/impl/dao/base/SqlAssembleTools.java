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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc SQL组装工具类
 *
 * @author liuliang
 *
 */
public class SqlAssembleTools {

	private static Logger logger = LoggerFactory.getLogger(SqlAssembleTools.class);

	public static SqlBean buildSQL(Class<?> clazz,NameHandler nameHandler,OperateTypeEnum operateType){
		return buildSQL(clazz,null,null,null,null,nameHandler,operateType);
	}
	public static SqlBean buildSQL(Object entity,NameHandler nameHandler,OperateTypeEnum operateType){
		return buildSQL(null,entity,null,null,null,nameHandler,operateType);
	}
	public static SqlBean buildSQL(Class<?> clazz,String primaryKey,NameHandler nameHandler,OperateTypeEnum operateType){
		return buildSQL(clazz,null,primaryKey,null,null,nameHandler,operateType);
	}
	public static SqlBean buildSQL(Criteria criteria,NameHandler nameHandler,OperateTypeEnum operateType){
		return buildSQL(null,null,null,criteria,null,nameHandler,operateType);
	}
	public static SqlBean buildSQL(Class<?> clazz,Pager pager,NameHandler nameHandler,OperateTypeEnum operateType){
		return buildSQL(clazz,null,null,null,pager,nameHandler,operateType);
	}
	public static SqlBean buildSQL(Criteria criteria,Pager pager,NameHandler nameHandler,OperateTypeEnum operateType){
		return buildSQL(null,null,null,criteria,pager,nameHandler,operateType);
	}
	
	/**
	 * @desc 创建SQL
	 *
	 * @author liuliang
	 *
	 * @param clazz
	 * @param nameHandler
	 * @param operateType sql类型
	 * @return SQLBean
	 */
	private static SqlBean buildSQL(Class<?> clazz,Object object,String primaryKey,Criteria criteria,Pager pager,NameHandler nameHandler,OperateTypeEnum operateType){
		SqlBean sqlBean = null;
		switch (operateType) {
			case COUNT:
				sqlBean = buildCountSql(clazz,nameHandler);
				break;
			case COUNT_C:
				sqlBean = buildCountSql(criteria,nameHandler);
				break;
			case INSERT:
				sqlBean = buildInsertSql(object,nameHandler);
				break;
			case DELETE:
				sqlBean = buildDeleteSql(clazz,primaryKey,nameHandler);
				break;
			case DELETE_C:
				sqlBean = buildDeleteSql(criteria,nameHandler);
				break;
			case UPDATE:
				sqlBean = buildUpdateSql(object,nameHandler);
				break;
			case QUERY:
				sqlBean = buildQuerySql(clazz,pager,primaryKey,nameHandler);
				break;
			case QUERY_C:
				sqlBean = buildQuerySql(criteria,pager,nameHandler);
				break;
			default:
				logger.error("创建SQL语句错误,operateType不匹配,operateType：{}",operateType);
				break;
		}
		return sqlBean;
	}
	
	/**
	 * 查询
	 *
	 * @author liuliang
	 *
	 * @param clazz
	 * @param pager
	 * @param primaryKey
	 * @param nameHandler
	 * @return
	 */
	private static SqlBean buildQuerySql(Class<?> clazz,Pager pager, String primaryKey,NameHandler nameHandler) {
		String tableName = nameHandler.getTableName(clazz);
		String pkName = nameHandler.getPrimaryKeyName(clazz);
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(tableName);
		if(StringUtils.isNotBlank(primaryKey)){
			sql.append(" WHERE ").append(pkName).append(" = ?");
			params.add(primaryKey);
		}
		if(null != pager){
			sql.append(" ORDER BY create_time DESC LIMIT ?,?");
			params.add(pager.getOffset());
			params.add(pager.getPageSize());
		}
		return new SqlBean(sql.toString(),params);
	}
	
	/**
	 * 更新
	 * 
	 * @author liuliang
	 *
	 * @param entity
	 * @param nameHandler
	 * @return
	 */
	private static SqlBean buildUpdateSql(Object entity, NameHandler nameHandler) {
		Class<?> clazz = entity.getClass();
		String tableName = nameHandler.getTableName(clazz);
		String pkName = nameHandler.getPrimaryKeyName(clazz);
		Object pkValue = null;
		
		Map<String,Object> fieldMap = BeanInfoUtil.getBeanInfo(entity);
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder("UPDATE ");
		sql.append(tableName).append(" SET ");
		for(Map.Entry<String,Object> entry:fieldMap.entrySet()){
			 //值为null,则表示不更新改字段;若需清空字段,可以设值为：空字符串""
			if(null == entry.getValue()){ 
				continue;
			}
			String columnName = nameHandler.getColumnName(entry.getKey());
			//主键最后添加
			if(pkName.equals(columnName)){  
				pkValue = entry.getValue();
				continue;
			}
			sql.append(columnName).append(" = ?,");
			params.add(entry.getValue());
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" WHERE ").append(pkName).append(" = ?");
		params.add(pkValue);
		
		return  new SqlBean(sql.toString(), params);
	}
	
	/**
	 * 删除
	 * 
	 * @author liuliang
	 *
	 * @param clazz
	 * @param primaryKey
	 * @param nameHandler
	 * @return
	 */
	private static SqlBean buildDeleteSql(Class<?> clazz,String primaryKey,NameHandler nameHandler) {
		String tableName = nameHandler.getTableName(clazz);
		String pkName = nameHandler.getPrimaryKeyName(clazz);
		
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append(tableName).append(" WHERE ").append(pkName).append(" = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(primaryKey);
		return new SqlBean(sql.toString(),params);
	}
	
	/**
	 * 插入 
	 * 
	 * @author liuliang
	 *
	 * @param entity
	 * @param nameHandler
	 * @return
	 */
	private static SqlBean buildInsertSql(Object entity,NameHandler nameHandler) {
		Class<?> clazz = entity.getClass();
		String tableName = nameHandler.getTableName(clazz);
        String pkName = nameHandler.getPrimaryKeyName(clazz);
        
		Map<String,Object> fieldMap = BeanInfoUtil.getBeanInfo(entity);
		List<Object> params = new ArrayList<Object>();
		StringBuilder placeholder = new StringBuilder();
		
		StringBuilder sql = new StringBuilder("INSERT INTO ");
		sql.append(tableName).append("(");
		for(Map.Entry<String,Object> entry:fieldMap.entrySet()){
			String columnName = nameHandler.getColumnName(entry.getKey());
			 //不传主键值,则需主键自增
			if(pkName.equals(columnName)){ 
				if(null == entry.getValue()){
					continue;
				}
			}
			sql.append(columnName).append(",");
			placeholder.append("?,");
			params.add(entry.getValue());
		}
		sql.deleteCharAt(sql.length() - 1).append(") VALUES ");
		sql.append("(").append(placeholder.deleteCharAt(placeholder.length() - 1)).append(")");
		return new SqlBean(sql.toString(),params);
	}

	/**
	 * cout SQL 组建
	 * 
	 * @author liuliang
	 *
	 * @param clazz
	 * @param nameHandler
	 * @return
	 */
	private static SqlBean buildCountSql(Class<?> clazz,NameHandler nameHandler){
		String tableName = nameHandler.getTableName(clazz);
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");
		sql.append(tableName);
		return new SqlBean(sql.toString(),null);
	}
	
	/**
	 * cout SQL 组建  Criteria
	 * 
	 * @author liuliang
	 *
	 * @param criteria
	 * @param nameHandler
	 * @return
	 */
	private static SqlBean buildCountSql(Criteria criteria,NameHandler nameHandler){
		List<Object> params = new ArrayList<Object>();
		String tableName = nameHandler.getTableName(criteria.getClazz());
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");
		sql.append(tableName).append(" WHERE ");
		
		assembleCriteria(criteria,sql,params,nameHandler);
		
		return new SqlBean(sql.toString(),params);
	}
	
	/**
	 * 删除 条件删
	 * 
	 * @author liuliang
	 *
	 * @param criteria
	 * @param nameHandler
	 * @return
	 */
	private static SqlBean buildDeleteSql(Criteria criteria,NameHandler nameHandler){
		List<Object> params = new ArrayList<Object>();
		String tableName = nameHandler.getTableName(criteria.getClazz());
		StringBuilder sql = new StringBuilder("DELETE FROM ");
		sql.append(tableName).append(" WHERE ");
		
		assembleCriteria(criteria,sql,params,nameHandler);
		
		return new SqlBean(sql.toString(),params);
	}
	
	/**
	 * 条件查
	 * 
	 * @author liuliang
	 *
	 * @param criteria
	 * @param pager
	 * @param nameHandler
	 * @return
	 */
	private static SqlBean buildQuerySql(Criteria criteria, Pager pager,NameHandler nameHandler) {
		List<Object> params = new ArrayList<Object>();
		
		String tableName = nameHandler.getTableName(criteria.getClazz());
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(tableName).append(" WHERE ");
		
		assembleCriteria(criteria,sql,params,nameHandler);
		
		if(null != pager){
			sql.append(" ORDER BY create_time DESC LIMIT ?,?");
			params.add(pager.getOffset());
			params.add(pager.getPageSize());
		}
		return new SqlBean(sql.toString(),params);
	}
	
	/**
	 * 组装Criteria参数
	 * 
	 * @author liuliang
	 *
	 * @param criteria
	 * @param sql
	 * @param params
	 * @param nameHandler
	 */
	private static void assembleCriteria(Criteria criteria,StringBuilder sql,List<Object> params,NameHandler nameHandler){
		String[] fieldNames = criteria.getFieldNames();
		for(int i=0,length=fieldNames.length;i<length;i++){
			String columnName = nameHandler.getColumnName(fieldNames[i]);
			sql.append(columnName).append(" ").append(criteria.getOperator()[i]).append(" ").append("?");
			if(null != criteria.getConnector()){
				if(i < criteria.getConnector().length){
					sql.append(" ").append(criteria.getConnector()[i]).append(" ");
				}
			}
			params.add(criteria.getFieldValues()[i]);
		}
	}
}
