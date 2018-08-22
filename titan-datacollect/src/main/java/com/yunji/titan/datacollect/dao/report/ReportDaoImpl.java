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

import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.yunji.titan.datacollect.bean.po.ReportPO;

/**
 * 数据上报DAO接口实现
 * 
 * @author gaoxianglong
 */
@Repository
public class ReportDaoImpl implements ReportDao {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertReport(ReportPO reportPO) {
		String sql = "INSERT INTO t_report(report_name,scene_id,scene_name,start_time,end_time,expect_tps,actual_tps,"
				+ "total_request,success_request,business_success_request,concurrent_user,user_waittime,server_waittime,conclusion,create_time) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,
				new Object[] { reportPO.getReportName(), reportPO.getSceneId(), reportPO.getSceneName(),
						reportPO.getStartTime(), reportPO.getEndTime(), reportPO.getExpectTps(),
						reportPO.getActualTps(), reportPO.getTotalRequest(), reportPO.getSuccessRequest(),
						reportPO.getBusinessSuccessRequest(), reportPO.getConcurrentUser(),
						reportPO.getUserWaittime(), reportPO.getServerWaittime(), reportPO.getConclusion(),
						reportPO.getCreateTime() });
	}

	@Override
	public void updateScene(int sceneId, int sceneStatus) {
		String sql = "UPDATE t_scene SET scene_status = ? WHERE scene_id = ?";
		jdbcTemplate.update(sql, new Object[] { sceneStatus, sceneId });
	}
}