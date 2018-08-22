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
package com.yunji.titan.manager.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yunji.titan.manager.entity.Report;

/**
 * @desc 测试报告表实体映射Mapper
 *
 * @author liuliang
 *
 */
@Component
public class ReportMapper implements RowMapper<Report>{

	@Override
	public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
		Report report = new Report();
		report.setReportId(rs.getLong("report_id"));
		report.setReportName(rs.getString("report_name"));
		report.setSceneId(rs.getLong("scene_id"));
		report.setSceneName(rs.getString("scene_name"));
		report.setStartTime(rs.getLong("start_time"));
		
		report.setEndTime(rs.getLong("end_time"));
		report.setExpectTps(rs.getInt("expect_tps"));
		report.setActualTps(rs.getInt("actual_tps"));
		report.setTotalRequest(rs.getLong("total_request"));
		report.setSuccessRequest(rs.getLong("success_request"));
		
		report.setBusinessSuccessRequest(rs.getLong("business_success_request"));
		report.setConcurrentUser(rs.getLong("concurrent_user"));
		report.setUserWaittime(rs.getLong("user_waittime"));
		report.setServerWaittime(rs.getLong("server_waittime"));
		report.setConclusion(rs.getInt("conclusion"));
		report.setCreateTime(rs.getLong("create_time"));
		return report;
	}

}
