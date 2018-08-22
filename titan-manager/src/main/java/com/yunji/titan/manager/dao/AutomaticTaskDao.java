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

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunji.titan.manager.bo.AutomaticTaskBO;
import com.yunji.titan.manager.entity.AutomaticTask;
import com.yunji.titan.manager.entity.mapper.AutomaticTaskMapper;

/**
 * @desc 自动压测任务表Dao
 *
 * @author liuliang
 *
 */
@Repository
public class AutomaticTaskDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private AutomaticTaskMapper automaticTaskMapper;

	/**
	 * @desc 查询自动压测任务总数量
	 *
	 * @author liuliang
	 *
	 * @return
	 * @throws Exception
	 */
	public int queryAutomaticTaskCount() throws Exception {
		final String sql = "SELECT count(*) FROM t_automatic_task";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	/**
     * @desc 分页查询所有自动压测任务
     *
     * @author liuliang
     *
     * @param pageIndex 当前页数
     * @param pageSize 每页记录条数
     * @return List<AutomaticTask>
     */
    public List<AutomaticTask> queryAutomaticTaskByPage(int pageIndex,int pageSize) throws Exception{
    	int offset =  pageIndex * pageSize;
    	final String sql = "SELECT * FROM t_automatic_task ORDER BY start_time DESC limit ?,?";
        return jdbcTemplate.query(sql,new Object[]{offset,pageSize},automaticTaskMapper);
    }

    /**
	 * @desc 增加记录
	 *
	 * @author liuliang
	 *
	 * @param automaticTaskBO 参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	public int add(AutomaticTaskBO automaticTaskBO) throws Exception{
		String sql = "INSERT INTO t_automatic_task(scene_id,scene_name,start_time,pressure_times,create_time) VALUES(?,?,?,?,?)";
		return jdbcTemplate.update(sql,new Object[]{automaticTaskBO.getSceneId(),automaticTaskBO.getSceneName(),
				automaticTaskBO.getStartTime(),automaticTaskBO.getPressureTimes(),System.currentTimeMillis()});
	}

	public AutomaticTask queryDataDetail(long id) throws Exception{
		String sql = "SELECT * FROM t_automatic_task WHERE automatic_task_id = ?";
		List<AutomaticTask> dataList = jdbcTemplate.query(sql,new Object[]{id},automaticTaskMapper);
		if((null != dataList) && (0 < dataList.size())){
			return dataList.get(0);
		}else{
			return null;
		}
	}

	public AutomaticTask queryDataDetailBySceneId(long sceneId) throws Exception{
		String sql = "SELECT * FROM t_automatic_task WHERE scene_id = ?";
		List<AutomaticTask> dataList = jdbcTemplate.query(sql,new Object[]{sceneId},automaticTaskMapper);
		if((null != dataList) && (0 < dataList.size())){
			return dataList.get(0);
		}else{
			return null;
		}
	}

	public int del(long id)  throws Exception {
		String sql = "DELETE FROM t_automatic_task WHERE automatic_task_id = ?";
		return jdbcTemplate.update(sql,new Object[]{id});
	}

	public int update(AutomaticTaskBO automaticTaskBO) throws Exception{
		String sql = "UPDATE t_automatic_task SET start_time = ?,pressure_times = ? WHERE automatic_task_id = ?";
		return jdbcTemplate.update(sql,new Object[]{automaticTaskBO.getStartTime(),automaticTaskBO.getPressureTimes(),automaticTaskBO.getAutomaticTaskId()});
	}

	/**
	 * @desc 根据场景ID删除定时任务
	 *
	 * @author liuliang
	 *
	 * @param sceneId
	 */
	public int delBySceneId(long sceneId) throws Exception{
		String sql = "DELETE FROM t_automatic_task WHERE scene_id = ?";
		return jdbcTemplate.update(sql,new Object[]{sceneId});
	}
}
