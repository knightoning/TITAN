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
package com.yunji.titan.manager.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunji.titan.manager.bo.MonitorSetBO;
import com.yunji.titan.manager.entity.MonitorSet;

/**
 * @desc 监控集Dao
 *
 * @author liuliang
 *
 */
@Repository
public class MonitorSetDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * @desc 查总记录数
	 *
	 * @author liuliang
	 *
	 * @return
	 */
	public int getCount() {
		final String sql = "SELECT count(*) FROM t_monitor_set";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	/**
	 * @desc 查询一页数据
	 *
	 * @author liuliang
	 *
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<MonitorSet> getDataList(int pageIndex, int pageSize) {
		int offset =  pageIndex * pageSize;
    	final String sql = "SELECT * FROM t_monitor_set ORDER BY create_time DESC limit ?,?";
        return jdbcTemplate.query(sql,new Object[]{offset,pageSize},BeanPropertyRowMapper.newInstance(MonitorSet.class));
	}

	/**
	 * @desc 删除数据
	 *
	 * @author liuliang
	 *
	 * @param ids
	 * @return
	 */
	public int del(String ids) {
		String sql = "DELETE FROM t_monitor_set WHERE monitor_set_id IN (" + ids + ")";
		return jdbcTemplate.update(sql);
	}

	/**
	 * @desc 新增
	 *
	 * @author liuliang
	 *
	 * @param monitorSetBO
	 * @return
	 */
	public int add(MonitorSetBO monitorSetBO) {
		String sql = "INSERT INTO t_monitor_set(scene_id,scene_name,intranet_ip,create_time) VALUES(?,?,?,?)";
		return jdbcTemplate.update(sql,new Object[]{monitorSetBO.getSceneId(),monitorSetBO.getSceneName(),
				monitorSetBO.getIntranetIp(),System.currentTimeMillis()});

	}

	/**
	 * @desc 修改
	 *
	 * @author liuliang
	 *
	 * @param monitorSetBO
	 * @return
	 */
	public int update(MonitorSetBO monitorSetBO) {
		String sql = "UPDATE t_monitor_set SET scene_id = ?,scene_name = ?,intranet_ip = ? WHERE monitor_set_id = ?";
		return jdbcTemplate.update(sql,new Object[]{monitorSetBO.getSceneId(),monitorSetBO.getSceneName(),monitorSetBO.getIntranetIp(),monitorSetBO.getMonitorSetId()});

	}

	/**
	 * @desc 根据场景ID查询监控集
	 *
	 * @author liuliang
	 *
	 * @param sceneId 场景ID
	 * @return
	 */
	public MonitorSet query(long sceneId) throws Exception{
		String sql = "SELECT * FROM t_monitor_set WHERE scene_id = ?";
		List<MonitorSet> dataList = jdbcTemplate.query(sql,new Object[]{sceneId},BeanPropertyRowMapper.newInstance(MonitorSet.class));
		if((null != dataList) && (0 < dataList.size())){
			return dataList.get(0);
		}else{
			return null;
		}
	}

	/**
	 * @desc 根据场景ID删除数据
	 *
	 * @author liuliang
	 *
	 * @param sceneId
	 */
	public int delBySceneId(long sceneId) {
		String sql = "DELETE FROM t_monitor_set WHERE scene_id = ?";
		return jdbcTemplate.update(sql,new Object[]{sceneId});
	}
	
}
