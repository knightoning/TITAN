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
package com.yunji.titan.manager.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yunji.titan.manager.entity.AutomaticTask;

/**
 * @desc 自动压测任务表实体映射Mapper
 *
 * @author liuliang
 *
 */
@Component
public class AutomaticTaskMapper implements RowMapper<AutomaticTask>{

	@Override
	public AutomaticTask mapRow(ResultSet rs, int rowNum) throws SQLException {
		AutomaticTask task = new AutomaticTask();
		task.setAutomaticTaskId(rs.getLong("automatic_task_id"));
		task.setSceneId(rs.getLong("scene_id"));
		task.setSceneName(rs.getString("scene_name"));
		task.setStartTime(rs.getTime("start_time"));
		task.setPressureTimes(rs.getInt("pressure_times"));
		task.setCreateTime(rs.getLong("create_time"));
		return task;
	}

}
