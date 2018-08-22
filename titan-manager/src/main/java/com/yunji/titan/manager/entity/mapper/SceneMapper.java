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

import com.yunji.titan.manager.entity.Scene;

/**
 * @desc 场景表实体映射Mapper
 *
 * @author liuliang
 *
 */
@Component
public class SceneMapper implements RowMapper<Scene>{

	@Override
	public Scene mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Scene scene = new Scene();
		scene.setSceneId(rs.getLong("scene_id"));
		scene.setSceneName(rs.getString("scene_name"));
		scene.setDurationHour(rs.getInt("duration_hour"));
		scene.setDurationMin(rs.getInt("duration_min"));
		scene.setDurationSec(rs.getInt("duration_sec"));
		
		scene.setConcurrentUser(rs.getInt("concurrent_user"));
		scene.setTotalRequest(rs.getLong("total_request"));
		scene.setExpectTps(rs.getInt("expect_tps"));
		scene.setContainLinkid(rs.getString("contain_linkid"));
		scene.setLinkRelation(rs.getString("link_relation"));
		
		scene.setUseAgent(rs.getInt("use_agent"));
		scene.setSceneStatus(rs.getInt("scene_status"));
		scene.setCreateTime(rs.getLong("create_time"));
		scene.setModifyTime(rs.getLong("modify_time"));
		scene.setConcurrentStart(rs.getInt("concurrent_start"));
		return scene;
	}

}
