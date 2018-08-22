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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunji.titan.manager.bo.ReportBO;
import com.yunji.titan.manager.entity.Report;
import com.yunji.titan.manager.entity.mapper.ReportMapper;

/**
 * @desc 测试报告表Dao
 *
 * @author liuliang
 *
 */
@Repository
public class ReportDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    
    @Resource
    private ReportMapper reportMapper;
    /**
     * @desc 查询测试报告总数量
     *
     * @author liuliang
     *
     * @return int 测试报告总数量
     * @throws Exception
     */
    public int queryReportCount() throws Exception {
        final String sql = "SELECT count(*) FROM t_report";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    /**
	 * @desc 查询符合条件的报告数量
	 *
	 * @author liuliang
	 *
	 * @param reportName
	 * @return int 总数量
	 */
	public int queryReportCount(String reportName) throws Exception {
		final String sql = "SELECT count(*) FROM t_report WHERE report_name LIKE ?";
		reportName = "%" + reportName + "%";
	    return jdbcTemplate.queryForObject(sql,new Object[]{reportName},Integer.class);
	}

	/**
	 * @desc 分页查询测试报告列表
	 *
	 * @author liuliang
	 *
	 * @param reportName 报告名称
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<Report>
	 */
	public List<Report> queryReportByPage(int pageIndex, int pageSize) throws Exception {
		int offset =  pageIndex * pageSize;
    	final String sql = "SELECT * FROM t_report ORDER BY create_time DESC limit ?,?";
        return jdbcTemplate.query(sql,new Object[]{offset,pageSize},reportMapper);
	}

	/**
	 * @desc 分页查询测试报告列表(带过滤条件)
	 *
	 * @author liuliang
	 *
	 * @param reportName 报告名称
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return List<Report>
	 */
	public List<Report> queryReportByPage(String reportName, int pageIndex,
			int pageSize) throws Exception {
		int offset =  pageIndex * pageSize;
		reportName = "%" + reportName + "%";
    	final String sql = "SELECT * FROM t_report WHERE report_name LIKE ? ORDER BY create_time DESC limit ?,?";
        return jdbcTemplate.query(sql,new Object[]{reportName,offset,pageSize},reportMapper);
	}

	/**
	 * @desc 根据测试报告ID查询详情列表
	 *
	 * @author liuliang
	 *
	 * @param idList 测试报告ID ,多个ID,以英文','隔开
	 * @return List<Report> 测试报告详情集合
	 */
	public List<Report> queryReportListById(String idList) throws Exception {
    	final String sql = "SELECT * FROM t_report WHERE report_id IN (" + idList + ")";
        return jdbcTemplate.query(sql,reportMapper);
	}

	/**
	 * @desc 删除测试报告
	 *
	 * @author liuliang
	 *
	 * @param ids  测试报告ID(多个ID以英文","隔开)
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	public int removeReport(String ids) throws Exception {
		String sql = "DELETE FROM t_report WHERE report_id IN (" + ids + ")";
		return jdbcTemplate.update(sql);
	}

	/**
	 * @desc 增加测试报告记录
	 *
	 * @author liuliang
	 *
	 * @param reportBO 测试报告参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	public int addReport(ReportBO reportBO) throws Exception {
		String sql = "INSERT INTO t_report(report_name,scene_id,scene_name,start_time,end_time,expect_tps,actual_tps,total_request,success_request,business_success_request,concurrent_user,user_waittime,server_waittime,conclusion,create_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql,new Object[]{reportBO.getReportName(),reportBO.getSceneId(),reportBO.getSceneName(),reportBO.getStartTime(),reportBO.getEndTime(),
				reportBO.getExpectTps(),reportBO.getActualTps(),reportBO.getTotalRequest(),reportBO.getSuccessRequest(),reportBO.getBusinessSuccessRequest(),reportBO.getConcurrentUser(),
				reportBO.getUserWaittime(),reportBO.getServerWaittime(),reportBO.getConclusion(),reportBO.getCreateTime()});
	}

	/**
	 * @desc 删除指定时间之前的数据
	 *
	 * @author liuliang
	 *
	 * @param time
	 * @return
	 */
	public int delBreforeTime(Long time) throws Exception{
		String sql = "DELETE FROM t_report WHERE create_time < ?";
		return jdbcTemplate.update(sql,new Object[] {time});
	}

}
