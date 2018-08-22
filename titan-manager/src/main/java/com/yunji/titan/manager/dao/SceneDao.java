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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunji.titan.manager.bo.SceneBO;
import com.yunji.titan.manager.entity.Scene;
import com.yunji.titan.manager.entity.mapper.SceneMapper;

/**
 * @desc 场景表Dao
 *
 * @author liuliang
 *
 */
@Repository
public class SceneDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
	@Resource
	private SceneMapper sceneMapper;
    
	/**
     * @desc 查询场景总数量
     *
     * @author liuliang
     *
     * @return int 场景总数量
     * @throws Exception
     */
    public int querySceneCount() throws Exception {
        final String sql = "SELECT count(*) FROM t_scene";
        return jdbcTemplate.queryForObject(sql,Integer.class);
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
	public int queryLinkCount(String scenekName) throws Exception {
		final String sql = "SELECT count(*) FROM t_scene WHERE scene_name LIKE ?";
		scenekName = "%" + scenekName + "%";
	    return jdbcTemplate.queryForObject(sql,new Object[]{scenekName},Integer.class);
	}


	/**
	 * @desc 分页查询所有场景列表
	 *
	 * @author liuliang
	 * @param scenekName 
	 *
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<Scene> 场景实体集合
	 * @throws Exception
	 */
	public List<Scene> querySceneByPage(int pageIndex, int pageSize) throws Exception {
		int offset =  pageIndex * pageSize;
    	final String sql = "SELECT * FROM t_scene ORDER BY create_time DESC limit ?,?";
    	List<Scene> sceneList = jdbcTemplate.query(sql,new Object[]{offset,pageSize},sceneMapper);
        return this.updateSceneContainLinkid(sceneList);
	}

	/**
	 * @desc 分页查询所有场景列表
	 *
	 * @author liuliang
	 * @param scenekName 
	 *
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<Scene> 场景实体集合
	 * @throws Exception
	 */
	public List<Scene> querySceneByPage(String scenekName, int pageIndex,int pageSize) throws Exception {
		int offset =  pageIndex * pageSize;
		scenekName = "%" + scenekName + "%";
    	final String sql = "SELECT * FROM t_scene WHERE scene_name LIKE ? ORDER BY create_time DESC limit ?,?";
    	List<Scene> sceneList = jdbcTemplate.query(sql,new Object[]{scenekName,offset,pageSize},sceneMapper);
        return this.updateSceneContainLinkid(sceneList); 
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
	public int addScene(SceneBO sceneBO) throws Exception {
		String containLinkid = "," + sceneBO.getContainLinkid() + ",";
		String sql = "INSERT INTO t_scene(scene_name,duration_hour,duration_min,duration_sec,concurrent_user,concurrent_start,total_request,expect_tps,contain_linkid,link_relation,use_agent,scene_status,create_time,modify_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql,new Object[]{sceneBO.getSceneName().trim(),sceneBO.getDurationHour(),sceneBO.getDurationMin(),sceneBO.getDurationSec(),
				sceneBO.getConcurrentUser(),sceneBO.getConcurrentStart(),sceneBO.getTotalRequest(),sceneBO.getExpectTps(),containLinkid,
				sceneBO.getLinkRelation(),sceneBO.getUseAgent(),sceneBO.getSceneStatus(),System.currentTimeMillis(),System.currentTimeMillis()});
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
	public int updateScene(Scene scene) throws Exception {
		if(null == scene.getSceneStatus()) {
			scene.setSceneStatus(0);
		}
		String containLinkid = "," + scene.getContainLinkid() + ",";
		String sql = "UPDATE t_scene SET duration_hour = ?,duration_min = ?,duration_sec = ?,concurrent_user = ?,concurrent_start = ?,total_request = ?,expect_tps = ?,contain_linkid = ?,link_relation = ?,use_agent = ?,scene_status = ?,modify_time = ? WHERE scene_id = ? AND scene_status = 0";
		return jdbcTemplate.update(sql,new Object[]{scene.getDurationHour(),scene.getDurationMin(),scene.getDurationSec(),
				scene.getConcurrentUser(),scene.getConcurrentStart(),scene.getTotalRequest(),scene.getExpectTps(),containLinkid,
				scene.getLinkRelation(),scene.getUseAgent(),scene.getSceneStatus(),System.currentTimeMillis(),scene.getSceneId()});
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
	public int removeScene(String idList) throws Exception {
		String sql = "DELETE FROM t_scene WHERE scene_id IN (" + idList + ")";
		return jdbcTemplate.update(sql);
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
	public Scene getScene(long sceneId) throws Exception {
		String sql = "SELECT * FROM t_scene WHERE scene_id = ?";
		List<Scene> dataList = jdbcTemplate.query(sql,new Object[]{sceneId},sceneMapper);
		if((null != dataList) && (0 < dataList.size())){
			return this.updateSceneContainLinkid(dataList.get(0));
		}else{
			return null;
		}
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
	public int updateScene(long sceneId, int sceneStatus) throws Exception {
		String sql = "UPDATE t_scene SET scene_status = ?  WHERE scene_id = ?";
		return jdbcTemplate.update(sql,new Object[]{sceneStatus,sceneId});
	}

	/**
	 * @desc 根据linkId查询场景列表
	 * 
	 * @param linkId 链路ID 
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<Scene> 符合条件的场景列表
	 */
	public List<Scene> getSceneListByLinkId(long linkId, int pageIndex,int pageSize) throws Exception {
		int offset =  pageIndex * pageSize;
		 //加","用于处理大编号包含小编号的情况
		String linkIdTemp = "%," + linkId + ",%"; 
    	final String sql = "SELECT * FROM t_scene WHERE contain_linkid LIKE ? ORDER BY create_time DESC limit ?,?";
    	List<Scene> sceneList = jdbcTemplate.query(sql,new Object[]{linkIdTemp,offset,pageSize},sceneMapper);
        return this.updateSceneContainLinkid(sceneList); 
	}
	
	/**
	 * @desc 根据linkId查询包含该ID的场景数
	 * 
	 * @param linkId 链路ID 
	 * @return int 符合条件的记录数
	 * @throws Exception
	 */
	public int getSceneCountByLinkId(String linkId) throws Exception {
		final String sql = "SELECT count(*) FROM t_scene WHERE contain_linkid LIKE ?";
		linkId = "%," + linkId + ",%";  
	    return jdbcTemplate.queryForObject(sql,new Object[]{linkId},Integer.class);
	}
	
	/**
	 * @desc 根据linkId删除包含该ID的场景
	 * 
	 * @param linkId 链路ID 
	 * @return 受影响的记录数
	 * @throws Exception
	 */
	public int removeSceneByLinkId(long linkId) throws Exception {
		String sql = "DELETE FROM t_scene WHERE contain_linkid LIKE ?";
		String linkIdTemp = "%," + linkId + ",%";  
		return jdbcTemplate.update(sql,new Object[]{linkIdTemp});
	}
	
	/**
	 * @desc 设置Scene的ContainLinkid字段
	 * 
	 * @param sceneList 场景实体集合
	 * @return List<Scene>
	 * @throws Exception
	 */
	private List<Scene> updateSceneContainLinkid(List<Scene> sceneList) throws Exception {
		List<Scene> returnSceneList = new ArrayList<Scene>();
		if((null != sceneList) && (0 < sceneList.size())){
    		for(Scene scene:sceneList){
    			returnSceneList.add(this.updateSceneContainLinkid(scene));
    		}
    		//修改后返回
    		return returnSceneList;
    	}
		return sceneList;
	}
	
	/**
	 * @desc 设置Scene的ContainLinkid字段
	 * 
	 * @param scene 场景实体
	 * @return Scene
	 * @throws Exception
	 */
	private Scene updateSceneContainLinkid(Scene scene) throws Exception {
		if(null != scene){
			String containLinkid = scene.getContainLinkid();
			if(StringUtils.isNotBlank(containLinkid)){
				scene.setContainLinkid(containLinkid.substring(1,containLinkid.length()-1));
				return scene;
			}
		}
		return scene;
	}

	/**
	 * @desc 更新所有场景状态
	 *
	 * @author liuliang
	 *
	 * @param sceneStatus
	 * @return int
	 */
	public int updateStatusAll(int sceneStatus)  throws Exception {
		String sql = "UPDATE t_scene SET scene_status = ?";
		return jdbcTemplate.update(sql,new Object[]{sceneStatus});
	}

	/**
	 * @desc 根据场景名称查询场景
	 *
	 * @author liuliang
	 *
	 * @param sceneName
	 * @return
	 */
	public Scene getSceneByName(String sceneName)  throws Exception {
		final String sql = "SELECT * FROM t_scene WHERE scene_name = ?";
	    List<Scene> dataList = jdbcTemplate.query(sql,new Object[]{sceneName},sceneMapper);
	    if((null != dataList) && (0 < dataList.size())){
			return dataList.get(0);
		}else{
			return null;
		}
	}

}
