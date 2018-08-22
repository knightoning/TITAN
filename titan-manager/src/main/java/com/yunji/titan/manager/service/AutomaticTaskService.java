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
package com.yunji.titan.manager.service;

import java.util.List;

import com.yunji.titan.manager.bo.AutomaticTaskBO;
import com.yunji.titan.manager.entity.AutomaticTask;

/**
 * 压测任务表Service接口
 *
 * @author liuliang
 *
 */
public interface AutomaticTaskService {

	/**
     * 查询总数量
     *
     * @author liuliang
     *
     * @return int 总数量
     * @throws Exception
     */
    int getCount() throws Exception;
    
	/**
	 * 分页查询所有数据列表
	 *
	 * @author liuliang
	 *
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<AutomaticTask> 数据集合
	 * @throws Exception
	 */
	List<AutomaticTaskBO> getDataList(int pageIndex, int pageSize) throws Exception;
	
	/**
	 * 增加记录
	 *
	 * @author liuliang
	 *
	 * @param automaticTaskBO 参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int add(AutomaticTaskBO automaticTaskBO) throws Exception;

	/**
	 * 查询详情(根据主键)
	 * 
	 * @author liuliang
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AutomaticTask getDataDetail(long id) throws Exception;

	/**
	 * 查询详情(根据 sceneId)
	 * 
	 * @author liuliang
	 *
	 * @param sceneId
	 * @return
	 * @throws Exception
	 */
	AutomaticTask getDataDetailBySceneId(long sceneId) throws Exception;

	/**
	 * 删除
	 * 
	 * @author liuliang
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int del(long id) throws Exception;

	/**
	 * 更新
	 * 
	 * @author liuliang
	 *
	 * @param automaticTaskBO
	 * @return
	 * @throws Exception
	 */
	int update(AutomaticTaskBO automaticTaskBO) throws Exception;
}
