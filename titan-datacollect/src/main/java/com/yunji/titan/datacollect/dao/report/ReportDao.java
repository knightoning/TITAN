/*
 * Copyright 2015-2101 yunjiweidian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yunji.titan.datacollect.dao.report;

import java.sql.SQLException;

import com.yunji.titan.datacollect.bean.po.ReportPO;

/**
 * 数据上报DAO接口
 * 
 * @author gaoxianglong
 */
public interface ReportDao {
	/**
	 * 新增压测结果信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @param reportPO
	 *            报表实体Bean
	 * 
	 * @return void
	 */
	public void insertReport(ReportPO reportPO);

	/**
	 * 更改场景状态
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sceneId
	 *            场景ID
	 * 
	 * @param sceneStatus
	 *            场景状态（0：未开始 ，1：进行中）
	 * 
	 * @return void
	 */
	public void updateScene(int sceneId, int sceneStatus);
}