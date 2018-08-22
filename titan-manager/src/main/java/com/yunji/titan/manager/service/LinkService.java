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

import com.yunji.titan.manager.bo.LinkBO;
import com.yunji.titan.manager.entity.Link;

/**
 * 链路表Service接口
 *
 * @author liuliang
 *
 */
public interface LinkService {
	
	/**
     * 查询链路总数量
     *
     * @author liuliang
     *
     * @return int 链路总数量
     * @throws Exception
     */
    int getLinkCount() throws Exception;
    
    /**
	 * 查询符合条件的记录总数
	 *
	 * @author liuliang
	 *
	 * @param linkName 链路名
	 * @return int 符合条件的记录总数
	 * @throws Exception
	 */
	int getLinkCount(String linkName) throws Exception;

	/**
	 * 分页查询所有链路列表
	 * 
	 * @author liuliang
	 *
	 * @param linkName
	 * @param pageIndex
	 * @param pageSize
	 * @return List<LinkBO>
	 * @throws Exception
	 */
	List<LinkBO> getLinkList(String linkName,int pageIndex, int pageSize) throws Exception;

	/**
	 * 增加链路记录
	 *
	 * @author liuliang
	 *
	 * @param linkBO 链路参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int addLink(LinkBO linkBO) throws Exception;

	/**
	 * 更新链路记录
	 *
	 * @author liuliang
	 *
	 * @param linkBO 链路参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int updateLink(LinkBO linkBO) throws Exception;

	/**
	 * 删除链路记录
	 *
	 * @author liuliang
	 *
	 * @param idList 链路ID(多个ID以英文","隔开)
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int removeLink(String idList) throws Exception;

	/**
	 * 根据ID查询链路详情
	 *
	 * @author liuliang
	 *
	 * @param linkId 链路ID
	 * @return Link 链路实体
	 * @throws Exception
	 */
	Link getLink(long linkId) throws Exception;

	/**
	 * 根据链路ID查询链路列表
	 * 
	 * @author liuliang
	 *
	 * @param ids ids 链路ID (多个ID以英文","隔开)
	 * @return
	 * @throws Exception
	 */
	List<Link> getLinkListByIds(String ids) throws Exception;

	/**
	 * 删除链路并更新链路相关的场景
	 *
	 * @author liuliang
	 *
	 * @param linkId 链路ID
	 * @param sceneCount 包含该链路ID的场景数
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int removeLinkAndUpdateScene(long linkId,int sceneCount) throws Exception;
}
