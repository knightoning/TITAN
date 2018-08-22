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

import com.yunji.titan.manager.bo.ReportBO;
import com.yunji.titan.manager.entity.Report;

/**
 * 测试报告表Service接口
 *
 * @author liuliang
 *
 */
public interface ReportService {
	
	/**
     * 查询测试报告总数量
     *
     * @author liuliang
     *
     * @return int 测试报告总数量
     * @throws Exception
     */
    int getReportCount() throws Exception;

	/**
	 * 查询符合条件的报告数量
	 *
	 * @author liuliang
	 *
	 * @param reportName
	 * @return int 总数量
	 * @throws Exception
	 */
	int getReportCount(String reportName) throws Exception;

	/**
	 * 分页查询所有测试报告列表
	 *
	 * @author liuliang
	 *
	 * @param reportName 报告名称
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return
	 * @throws Exception
	 */
	List<ReportBO> getReportList(String reportName, int pageIndex, int pageSize) throws Exception;

	/**
	 * 根据测试报告ID查询详情列表
	 *
	 * @author liuliang
	 *
	 * @param idList 测试报告ID ,多个ID,以英文','隔开
	 * @return List<Report> 测试报告详情集合
	 * @throws Exception
	 */
	List<Report> getReportListById(String idList) throws Exception;

	/**
	 * 删除测试报告
	 *
	 * @author liuliang
	 *
	 * @param ids  测试报告ID(多个ID以英文","隔开)
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int removeReport(String ids) throws Exception;
	
	/**
	 * 增加测试报告记录
	 *
	 * @author liuliang
	 *
	 * @param reportBO 测试报告参数BO
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	int addReport(ReportBO reportBO) throws Exception;

	/**
	 *  删除指定时间之前的数据
	 *
	 * @author liuliang
	 *
	 * @param breforeTime 多久之前(单位：s) 
	 * @return  int 受影响的记录数
	 * @throws Exception
	 */
	int delByTime(Long breforeTime) throws Exception;

}
