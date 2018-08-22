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
package com.yunji.titan.manager.impl.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.yunji.titan.manager.dao.BaseDao;
import com.yunji.titan.manager.impl.dao.base.Criteria;
import com.yunji.titan.manager.impl.dao.base.DefaultNameHandler;
import com.yunji.titan.manager.impl.dao.base.NameHandler;
import com.yunji.titan.manager.impl.dao.base.OperateTypeEnum;
import com.yunji.titan.manager.impl.dao.base.Pager;
import com.yunji.titan.manager.impl.dao.base.SqlAssembleTools;
import com.yunji.titan.manager.impl.dao.base.SqlBean;
/**
 * 基础dao实现
 *
 * @author liuliang
 *
 */
@Repository
public class BaseDaoImpl implements BaseDao{

    @Resource
	protected JdbcTemplate jdbcTemplate;
    protected NameHandler nameHandler;
    
    /**
     * 默认表名
     * 
     * @author liuliang
     *
     * @return
     */
    protected NameHandler getNameHandler() {  
        if (null == nameHandler) {  
        	nameHandler = new DefaultNameHandler();
        }  
        return nameHandler;  
    }  
    /**
     * 获取 RowMapper
     * 
     * @author liuliang
     *
     * @param clazz
     * @return
     */
    protected <T> RowMapper<T> getRowMapper(Class<T> clazz) {  
    	return BeanPropertyRowMapper.newInstance(clazz);
    }  
	
	@Override
	public int queryCount(Class<?> clazz) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(clazz,this.getNameHandler(),OperateTypeEnum.COUNT);
		return jdbcTemplate.queryForObject(sqlBean.getSql(), Integer.class);
	}
	@Override
	public int queryCount(Criteria criteria) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(criteria,this.getNameHandler(),OperateTypeEnum.COUNT_C);
		return jdbcTemplate.queryForObject(sqlBean.getSql(),sqlBean.getParams().toArray(), Integer.class);
	}
	
	@Override
	public int insert(Object entity) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(entity,this.getNameHandler(),OperateTypeEnum.INSERT);
		return jdbcTemplate.update(sqlBean.getSql(),sqlBean.getParams().toArray());
	}

	@Override
	public int delete(Class<?> clazz, Long id) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(clazz,String.valueOf(id),this.getNameHandler(),OperateTypeEnum.DELETE);
		return jdbcTemplate.update(sqlBean.getSql(),sqlBean.getParams().toArray());
	}
	
	@Override
	public int delete(Criteria criteria) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(criteria,this.getNameHandler(),OperateTypeEnum.DELETE_C);
		return jdbcTemplate.update(sqlBean.getSql(),sqlBean.getParams().toArray());
	}

	@Override
	public int update(Object entity) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(entity,this.getNameHandler(),OperateTypeEnum.UPDATE);
		return jdbcTemplate.update(sqlBean.getSql(),sqlBean.getParams().toArray());
	}

	@Override
	public <T> T query(Class<T> clazz, Long id) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(clazz,String.valueOf(id),this.getNameHandler(),OperateTypeEnum.QUERY);
		List<T> list = jdbcTemplate.query(sqlBean.getSql(),sqlBean.getParams().toArray(),this.getRowMapper(clazz));
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T query(Criteria criteria) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(criteria,this.getNameHandler(),OperateTypeEnum.QUERY_C);
		List<?> list = jdbcTemplate.query(sqlBean.getSql(),sqlBean.getParams().toArray(),this.getRowMapper(criteria.getClazz()));
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return (T) list.get(0);
	}

	@Override
	public <T> List<T> queryList(Class<T> clazz,Pager pager) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(clazz,pager,this.getNameHandler(),OperateTypeEnum.QUERY);
		return jdbcTemplate.query(sqlBean.getSql(),sqlBean.getParams().toArray(),this.getRowMapper(clazz));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryList(Criteria criteria, Pager pager) throws Exception{
		SqlBean sqlBean = SqlAssembleTools.buildSQL(criteria,pager,this.getNameHandler(),OperateTypeEnum.QUERY_C);
		List<?> list =  jdbcTemplate.query(sqlBean.getSql(),sqlBean.getParams().toArray(),this.getRowMapper(criteria.getClazz()));
		return (List<T>) list;
	}

}
