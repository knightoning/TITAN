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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yunji.titan.manager.bo.SceneBO;
import com.yunji.titan.manager.common.SceneStatusEnum;
import com.yunji.titan.manager.dao.AutomaticTaskDao;
import com.yunji.titan.manager.dao.MonitorSetDao;
import com.yunji.titan.manager.dao.SceneDao;
import com.yunji.titan.manager.entity.Scene;
import com.yunji.titan.manager.service.SceneService;

/**
 * @desc 场景表Service实现类
 *
 * @author liuliang
 *
 */
@Service
public class SceneServiceImpl implements SceneService{

	@Resource
	private SceneDao sceneDao;
	@Resource
	private AutomaticTaskDao automaticTaskDao;
	@Resource
	private MonitorSetDao monitorSetDao;
	/**
     * @desc 查询场景总数量
     *
     * @author liuliang
     *
     * @return int 场景总数量
     * @throws Exception
     */
	@Override
	public int getSceneCount() throws Exception {
		return sceneDao.querySceneCount();
	}

	/**
	 * @desc 查询符合条件的记录总数
	 *
	 * @author liuliang
	 *
	 * @param scenekName 场景名
	 * @return int 符合条件的记录总数
	 * @throws Exception
	 */
	@Override
	public int getSceneCount(String scenekName) throws Exception {
		if(StringUtils.isBlank(scenekName)){
			return this.getSceneCount();
		}else{
			return sceneDao.queryLinkCount(scenekName);
		}
	}

	/**
	 * @desc 分页查询所有场景列表
	 *
	 * @author liuliang
	 * @param scenekName 
	 *
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<SceneBO> 场景BO集合
	 * @throws Exception
	 */
	@Override
	public List<SceneBO> getSceneList(String scenekName,int pageIndex, int pageSize) throws Exception {
		//1、查询
		List<Scene> sceneList = null;
		if(StringUtils.isBlank(scenekName)){
			sceneList = sceneDao.querySceneByPage(pageIndex, pageSize);
		}else{
			sceneList = sceneDao.querySceneByPage(scenekName,pageIndex, pageSize);
		}
		//2、转换
		List<SceneBO> sceneBOList = new ArrayList<SceneBO>();
		if((null != sceneList) && (0 < sceneList.size())){
			SceneBO sceneBO = null;
			for(Scene scene:sceneList){
				sceneBO = new SceneBO();
				BeanUtils.copyProperties(scene, sceneBO);
				sceneBOList.add(sceneBO);
			}
		}
		//3、返回
		return sceneBOList;
	}

	/**
	 * @desc 增加场景记录
	 *
	 * @author liuliang
	 *
	 * @param sceneBO 场景参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	@Override
	public int addScene(SceneBO sceneBO) throws Exception {
		sceneBO.setSceneStatus(SceneStatusEnum.NOT_START.value);
		return sceneDao.addScene(sceneBO);
	}

	/**
	 * @desc 更新场景记录
	 *
	 * @author liuliang
	 *
	 * @param sceneBO 场景参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	@Override
	public int updateScene(SceneBO sceneBO) throws Exception {
		Scene scene = new Scene();
		BeanUtils.copyProperties(sceneBO, scene);
		return sceneDao.updateScene(scene);
	}

	/**
	 * @desc 删除场景记录
	 *
	 * @author liuliang
	 *
	 * @param idList  idList 链路ID(多个ID以英文","隔开)
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	@Override
	public int removeScene(String idList) throws Exception {
		return sceneDao.removeScene(idList);
	}

	/**
	 * @desc 根据ID查询场景详情
	 *
	 * @author liuliang
	 *
	 * @param sceneId 场景ID
	 * @return Scene 场景实体
	 * @throws Exception
	 */
	@Override
	public Scene getScene(long sceneId) throws Exception {
		return sceneDao.getScene(sceneId);
	}

	/**
	 * @desc 根据场景ID更新场景状态
	 *
	 * @author liuliang
	 *
	 * @param sceneId 场景ID
	 * @param sceneStatus 场景转态
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	@Override
	public int updateSceneStatus(long sceneId, int sceneStatus) throws Exception {
		return sceneDao.updateScene(sceneId,sceneStatus);
	}

	/**
	 * 根据linkId查询场景列表
	 * 
	 * @param linkId 链路ID 
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<Scene> 符合条件的场景列表
	 */
	@Override
	public List<Scene> getSceneListByLinkId(long linkId, int pageIndex,int pageSize) throws Exception {
		return sceneDao.getSceneListByLinkId(linkId,pageIndex, pageSize);
	}

	/**
	 * 根据linkId查询包含该ID的场景数
	 * 
	 * @param linkId 链路ID 
	 * @return int 符合条件的记录数
	 * @throws Exception
	 */
	@Override
	public int getSceneCountByLinkId(long linkId) throws Exception {
		return sceneDao.getSceneCountByLinkId(String.valueOf(linkId));
	}

	/**
	 * @desc 更新场景状态为原始状态
	 *
	 * @author liuliang
	 *
	 * @return int 受影响的记录数
	 */
	@Override
	public int resetSceneStatus() throws Exception {
		return sceneDao.updateStatusAll(SceneStatusEnum.NOT_START.value);
	}

	/**
	 * @desc 根据场景名称查询场景
	 *
	 * @author liuliang
	 *
	 * @param sceneName
	 */
	@Override
	public Scene getSceneByName(String sceneName) throws Exception {
		return sceneDao.getSceneByName(sceneName);
	}

	/**
	 * @desc 删除场景并更新相关连数据
	 *
	 * @author liuliang
	 *
	 * @param sceneId
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT, timeout = 5)
	public int removeSceneAndUpdateRelatedData(long sceneId) throws Exception {
		//1、删场景
		int result = sceneDao.removeScene(String.valueOf(sceneId));
		//2、删定时任务
		automaticTaskDao.delBySceneId(sceneId);
		//3、删监控集
		monitorSetDao.delBySceneId(sceneId);
		return result;
	}
}
