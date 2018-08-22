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
import com.yunji.titan.task.bean.po.ScenePO;

/**
 * 压测场景Dao
 * 
 * @author gaoxianglong
 */
public interface SceneDao {
	/**
	 * 根据场景ID获取场景状态
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sceneID
	 *            场景ID
	 * 
	 * @return List<ScenePO> 场景状态
	 */
	public List<ScenePO> getSceneStatus(int sceneID);
}