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

import com.yunji.titan.manager.bo.SceneBO;
import com.yunji.titan.manager.entity.Scene;

/**
 * 场景表Service接口
 *
 * @author liuliang
 *
 */
public interface SceneService {
	
	/**
     * 查询场景总数量
     *
     * @author liuliang
     *
     * @return int 场景总数量
     * @throws Exception
     */
    int getSceneCount() throws Exception;

	/**
	 * 查询符合条件的记录总数
	 *
	 * @author liuliang
	 *
	 * @param scenekName 场景名
	 * @return int 符合条件的记录总数
	 * @throws Exception
	 */
	int getSceneCount(String scenekName) throws Exception ;

	/**
	 * 分页查询所有场景列表
	 *
	 * @author liuliang
	 * @param scenekName 
	 *
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<SceneBO> 场景BO集合
	 * @throws Exception
	 */
	List<SceneBO> getSceneList(String scenekName, int pageIndex, int pageSize) throws Exception ;

	/**
	 * 增加场景记录
	 *
	 * @author liuliang
	 *
	 * @param sceneBO 场景参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int addScene(SceneBO sceneBO) throws Exception ;

	/**
	 * 更新场景记录
	 *
	 * @author liuliang
	 *
	 * @param sceneBO 场景参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int updateScene(SceneBO sceneBO) throws Exception ;

	/**
	 * 删除场景记录
	 *
	 * @author liuliang
	 *
	 * @param idList  idList 链路ID(多个ID以英文","隔开)
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int removeScene(String idList) throws Exception ;

	/**
	 * 根据ID查询场景详情
	 *
	 * @author liuliang
	 *
	 * @param sceneId 场景ID
	 * @return Scene 场景实体
	 * @throws Exception
	 */
	Scene getScene(long sceneId) throws Exception ;
	
	/**
	 * 根据场景ID更新场景状态
	 *
	 * @author liuliang
	 *
	 * @param sceneId 场景ID
	 * @param sceneStatus 场景转态
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int updateSceneStatus(long sceneId,int sceneStatus) throws Exception ;

	/**
	 * 根据linkId查询场景列表
	 * 
	 * @param linkId 链路ID 
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<Scene> 符合条件的场景列表
	 * @throws Exception
	 */
	List<Scene> getSceneListByLinkId(long linkId,int pageIndex,int pageSize) throws Exception ;

	/**
	 * 根据linkId查询包含该ID的场景数
	 * 
	 * @param linkId 链路ID 
	 * @return int 符合条件的记录数
	 * @throws Exception
	 */
	int getSceneCountByLinkId(long linkId) throws Exception ;

	/**
	 * 更新场景状态为原始状态
	 *
	 * @author liuliang
	 *
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int resetSceneStatus() throws Exception ;

	/**
	 * 根据场景名称查询场景
	 * 
	 * @author liuliang
	 *
	 * @param sceneName
	 * @return
	 * @throws Exception
	 */
	Scene getSceneByName(String sceneName) throws Exception ;

	/**
	 * 删除场景并更新相关连数据
	 *
	 * @author liuliang
	 *
	 * @param sceneId
	 * @return
	 * @throws Exception
	 */
	int removeSceneAndUpdateRelatedData(long sceneId) throws Exception ;
}
