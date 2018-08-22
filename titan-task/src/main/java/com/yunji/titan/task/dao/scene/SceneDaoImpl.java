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
package com.yunji.titan.task.dao.scene;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.yunji.titan.task.bean.po.SceneMapper;
import com.yunji.titan.task.bean.po.ScenePO;

/**
 * 压测场景Dao实现
 * 
 * @author gaoxianglong
 */
@Repository
public class SceneDaoImpl implements SceneDao {
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private SceneMapper sceneMapper;

	@Override
	public List<ScenePO> getSceneStatus(int sceneID) {
		String sql = "SELECT scene_status FROM t_scene WHERE scene_id = ?";
		return jdbcTemplate.query(sql, new Object[] { sceneID }, sceneMapper);
	}
}