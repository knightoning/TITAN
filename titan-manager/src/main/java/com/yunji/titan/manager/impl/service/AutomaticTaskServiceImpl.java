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
package com.yunji.titan.manager.impl.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yunji.titan.manager.bo.AutomaticTaskBO;
import com.yunji.titan.manager.dao.AutomaticTaskDao;
import com.yunji.titan.manager.entity.AutomaticTask;
import com.yunji.titan.manager.service.AutomaticTaskService;

/**
 * @desc 压测任务表Service接口实现类
 *
 * @author liuliang
 *
 */
@Service
public class AutomaticTaskServiceImpl implements AutomaticTaskService{

	@Resource
	private AutomaticTaskDao automaticTaskDao;

	/**
     * 查询总数量
     *
     * @author liuliang
     *
     * @return int 总数量
     * @throws Exception
     */
	@Override
	public int getCount() throws Exception {
		return automaticTaskDao.queryAutomaticTaskCount();
	}

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
	@Override
	public List<AutomaticTaskBO> getDataList(int pageIndex, int pageSize)throws Exception {
		List<AutomaticTask> list = automaticTaskDao.queryAutomaticTaskByPage(pageIndex, pageSize);
		List<AutomaticTaskBO> automaticTaskBOList = new ArrayList<AutomaticTaskBO>();
		if((null != list) && (0 < list.size())){
			AutomaticTaskBO automaticTaskBO = null;
			for(AutomaticTask automaticTask:list){
				automaticTaskBO = new AutomaticTaskBO();
				BeanUtils.copyProperties(automaticTask, automaticTaskBO);
				automaticTaskBOList.add(automaticTaskBO);
			}
		}
		return automaticTaskBOList;
	}

	/**
	 * 增加记录
	 *
	 * @author liuliang
	 *
	 * @param automaticTaskBO 参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	@Override
	public int add(AutomaticTaskBO automaticTaskBO) throws Exception {
		return automaticTaskDao.add(automaticTaskBO);
	}

	/**
	 * 查询详情(根据主键)
	 * 
	 * @author liuliang
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public AutomaticTask getDataDetail(long id) throws Exception{
		return automaticTaskDao.queryDataDetail(id);
	}

	/**
	 * 查询详情(根据 sceneId)
	 * 
	 * @author liuliang
	 *
	 * @param sceneId
	 * @return
	 * @throws Exception
	 */
	@Override
	public AutomaticTask getDataDetailBySceneId(long sceneId) throws Exception {
		return automaticTaskDao.queryDataDetailBySceneId(sceneId);
	}

	/**
	 * 删除
	 * 
	 * @author liuliang
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public int del(long id) throws Exception {
		return automaticTaskDao.del(id);
	}

	/**
	 * 更新
	 * 
	 * @author liuliang
	 *
	 * @param automaticTaskBO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int update(AutomaticTaskBO automaticTaskBO) throws Exception {
		return automaticTaskDao.update(automaticTaskBO);
	}
}
