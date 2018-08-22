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

import com.yunji.titan.manager.bo.ReportBO;
import com.yunji.titan.manager.dao.ReportDao;
import com.yunji.titan.manager.entity.Report;
import com.yunji.titan.manager.service.ReportService;

/**
 * @desc 测试报告表Service实现类
 *
 * @author liuliang
 *
 */
@Service
public class ReportServiceImpl implements ReportService{

	@Resource
	private ReportDao reportDao;

	/**
     * @desc 查询测试报告总数量
     *
     * @author liuliang
     *
     * @return int 测试报告总数量
     * @throws Exception
     */
	@Override
	public int getReportCount() throws Exception {
		return reportDao.queryReportCount();
	}

	/**
	 * @desc 查询符合条件的报告数量
	 *
	 * @author liuliang
	 *
	 * @param reportName
	 * @return int 总数量
	 */
	@Override
	public int getReportCount(String reportName) throws Exception {
		if(StringUtils.isBlank(reportName)){
			return this.getReportCount();
		}else{
			return reportDao.queryReportCount(reportName);
		}
	}

	/**
	 * @desc 分页查询所有测试报告列表
	 *
	 * @author liuliang
	 *
	 * @param reportName 报告名称
	 * @param pageIndex 当前页
	 * @param pageSize 每页条数
	 * @return
	 */
	@Override
	public List<ReportBO> getReportList(String reportName, int pageIndex, int pageSize) throws Exception {
		//1、查询
		List<Report> reportList = null;
		if(StringUtils.isBlank(reportName)){
			reportList = reportDao.queryReportByPage(pageIndex, pageSize);
		}else{
			reportList = reportDao.queryReportByPage(reportName,pageIndex, pageSize);
		}
		//2、转换
		List<ReportBO> reportBOList = new ArrayList<ReportBO>();
		if((null != reportList) && (0 < reportList.size())){
			ReportBO reportBO = null;
			for(Report report:reportList){
				reportBO = new ReportBO();
				BeanUtils.copyProperties(report, reportBO);
				reportBOList.add(reportBO);
			}
		}
		//3、返回
		return reportBOList;
	}

	/**
	 * @desc 根据测试报告ID查询详情列表
	 *
	 * @author liuliang
	 *
	 * @param idList 测试报告ID ,多个ID,以英文','隔开
	 * @return List<Report> 测试报告详情集合
	 */
	@Override
	public List<Report> getReportListById(String idList) throws Exception {
		return reportDao.queryReportListById(idList);
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
	@Override
	public int removeReport(String ids) throws Exception {
		return reportDao.removeReport(ids);
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
	@Override
	public int addReport(ReportBO reportBO) throws Exception {
		return reportDao.addReport(reportBO);
	}

	/**
	 * @desc   删除指定时间之前的数据
	 *
	 * @author liuliang
	 *
	 * @param breforeTime 多久之前(单位：s) 
	 * @return  int 受影响的记录数
	 */
	@Override
	public int delByTime(Long breforeTime) throws Exception {
		//计算当前系统时间breforeTime秒的时间
		Long time = System.currentTimeMillis() - breforeTime * 1000;
		return reportDao.delBreforeTime(time);
	}
}
