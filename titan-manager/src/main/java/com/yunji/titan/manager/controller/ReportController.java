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
package com.yunji.titan.manager.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunji.titan.manager.bo.Pager;
import com.yunji.titan.manager.bo.PageRequestBO;
import com.yunji.titan.manager.bo.ReportBO;
import com.yunji.titan.manager.entity.Report;
import com.yunji.titan.manager.result.ComponentResult;
import com.yunji.titan.manager.result.Result;
import com.yunji.titan.manager.service.ReportService;
import com.yunji.titan.manager.utils.EmailSender;
import com.yunji.titan.manager.utils.ResultUtil;
import com.yunji.titan.manager.utils.TitanDateUtil;
import com.yunji.titan.manager.utils.ValidatorUtil;
import com.yunji.titan.utils.ErrorCode;

/**
 * @desc 测试报告管理Controller
 *
 * @author liuliang
 *
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController {

	private Logger logger = LoggerFactory.getLogger(ReportController.class);
	/**
	 * 测试报告服务
	 */
	@Resource
	private ReportService reportService;
	/**
	 * 邮件发送
	 */
	@Resource
	private EmailSender emailSender;
	/**
	 * 邮件主题
	 */
	@Value("${mail.subject}")
	private String mailSubject; 
	/**
	 * 邮件收件人
	 */
	@Value("${mail.receive.address}")
	private String mailReceiveAddress; 
	/**
	 * 邮件抄送人
	 */
	@Value("${mail.cc.receive.address}")
	private String mailCcReceiveAddress;
	/**
	 * 项目根目录
	 */
	@Value("${base.path}")
	private String basePath;
	
	/**
	 * @desc 分页获取测试报告数据
	 *
	 * @author liuliang
	 *
	 * @param pr
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ComponentResult<Pager<ReportBO>> list(@Validated PageRequestBO pr,BindingResult br) {
		ComponentResult<Pager<ReportBO>> componentResult = new ComponentResult<Pager<ReportBO>>();
		//参数校验
		Result validateResult = ValidatorUtil.getValidResult(br);
		if(ResultUtil.isfail(validateResult)) {
			return ResultUtil.copy(validateResult, componentResult);
		}
		//查询
		try {
			int totalCount = reportService.getReportCount(pr.getFilterCondition());
			List<ReportBO> records = reportService.getReportList(pr.getFilterCondition(),pr.getPageIndex(),pr.getPageSize());
			if(null != records) {
				String durationTime = "";
				long durationTimeTemp = 0; 
				double businessSuccessRate = 0.0D;
				double httpSuccessRate = 0.0D;
				long peakThroughput = 0;
				String peakDurationTime = "";
				DecimalFormat df = new DecimalFormat("#.00");  
				for(ReportBO reportBO : records){
					durationTimeTemp = (long)Math.ceil((reportBO.getEndTime() - reportBO.getStartTime())/1000.0);
					
					durationTime = TitanDateUtil.getDescBySecond(durationTimeTemp);
					businessSuccessRate = Double.parseDouble(df.format(reportBO.getBusinessSuccessRequest() / (double)reportBO.getTotalRequest() * 100.00));
					httpSuccessRate = Double.parseDouble(df.format(reportBO.getSuccessRequest() / (double)reportBO.getTotalRequest() * 100.00));
					peakThroughput = (long) Math.ceil((reportBO.getTotalRequest() * 0.8)/(durationTimeTemp * 0.2));
					peakDurationTime = TitanDateUtil.getDescBySecond((long)(durationTimeTemp * 0.2));
					
					reportBO.setDurationTime(durationTime);
					reportBO.setBusinessSuccessRate(businessSuccessRate);
					reportBO.setHttpSuccessRate(httpSuccessRate);
					reportBO.setPeakThroughput(peakThroughput);
					reportBO.setPeakDurationTime(peakDurationTime);
				}
			}
			
			componentResult.setData(new Pager<ReportBO>(totalCount, records));
			return ResultUtil.success(componentResult);
		} catch (Exception e) {
			logger.error("分页获取数据异常,params:{}",pr.toString(),e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.QUERY_DB_ERROR,componentResult);
	}
	
	/**
	 * @desc 删除
	 *
	 * @author liuliang
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Result del(@RequestParam("ids") String ids) {
		Result result = new Result();
		//参数校验
		if(StringUtils.isBlank(ids)){
			logger.warn("删除数据,参数错误,ids:{}",ids);
			return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,result);
		}
		//删除
		try {
			int delResult = reportService.removeReport(ids);
			if(0 < delResult) {
				return ResultUtil.success(result);
			}
		} catch (Exception e) {
			logger.error("删除数据异常,ids:{}",ids,e);
		}
		//失败返回
		return ResultUtil.fail(ErrorCode.UPDATE_DB_ERROR,result);
	}
	
	/**
	 * @desc 发送测试报告邮件
	 *
	 * @author liuliang
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/sendReportEmail")
	@ResponseBody
	public Result sendReportEmail(@RequestParam("ids") String ids){
		Result result = new Result();
		//参数校验
		if(StringUtils.isBlank(ids)){
			logger.warn("删除数据,参数错误,ids:{}",ids);
			return ResultUtil.fail(ErrorCode.REQUEST_PARA_ERROR,result);
		}
		//发送
		try {
			List<Report> reportList = reportService.getReportListById(ids);
			if((null != reportList) && (0 < reportList.size())){
				boolean sendResult = this.sendEmailContent(reportList); 
				if(sendResult){
					return ResultUtil.success(result);
				}
			}else {
				logger.error("发送测试报告邮件,查询测试报告记录为空,ids:{}",ids);
			}
		} catch (Exception e) {
			logger.error("发送压测报告邮件异常,ids:{}",ids,e);
		}
		//失败返回
		return ResultUtil.fail(result);
	}

	/**
	 * @desc 发送邮件
	 *
	 * @author liuliang
	 *
	 * @param reportList 待发送的数据
	 * @return String 发送结果
	 */
	private boolean sendEmailContent(List<Report> reportList) throws Exception{
		String durationTime = "";
		long durationTimeTemp = 0; 
		double businessSuccessRate = 0.0D;
		double httpSuccessRate = 0.0D;
		long peakThroughput = 0;
		String peakDurationTime = "";
		DecimalFormat df = new DecimalFormat("#.00");  
		
		StringBuilder sb = new StringBuilder();
		//1、逐条组装
		for(Report report:reportList){
			durationTimeTemp = (long)Math.ceil((report.getEndTime() - report.getStartTime())/1000.0); 
			durationTime = TitanDateUtil.getDescBySecond(durationTimeTemp);
			businessSuccessRate = Double.parseDouble(df.format(report.getBusinessSuccessRequest() / (double)report.getTotalRequest() * 100.00));
			httpSuccessRate = Double.parseDouble(df.format(report.getSuccessRequest() / (double)report.getTotalRequest() * 100.00));
			peakThroughput = (long) Math.ceil((report.getTotalRequest() * 0.8)/(durationTimeTemp * 0.2));
			peakDurationTime = TitanDateUtil.getDescBySecond((long)(durationTimeTemp * 0.2));
			
			sb.append("<div style='width: 880px;min-height: 360px;border: 1px solid #EDEDED;margin-bottom: 5px;position: relative;'>");
			sb.append("<img style='left:700px; top:10px;position: absolute;' width='135px;' height='95px;' src='" + this.getConclusionDesc(report.getConclusion()) + "'/>");
			sb.append("<h3 style='margin-left: 10px;'>" + report.getReportName() + "</h3>");
			sb.append("<table border='1px' bordercolor='#C6C5C6' style='border-collapse:collapse;width: 860px;margin-left: 10px;text-align: center;'>");
			sb.append("<thead>");
			sb.append("		<tr style='background-color: #F1F1F1;height: 40px;'>");
			sb.append("			<th>并发用户数</th><th>总并发请求数</th><th>HTTP200成功请求数</th><th>业务成功请求数</th><th>HTTP200成功率</th>");
			sb.append("		</tr>");
			sb.append("	</thead>");
			sb.append("	<tbody>");
			sb.append("		<tr style='height: 40px;'>");
			sb.append("			<td>" + report.getConcurrentUser() + "</td>");
			sb.append("			<td>" + report.getTotalRequest() + "</td>");
			sb.append("			<td>" + report.getSuccessRequest() + "</td>");
			sb.append("			<td>" + report.getBusinessSuccessRequest()+ "</td>");
			sb.append("		    <td>" + httpSuccessRate + "%</td>");
			sb.append("		</tr>");
			sb.append("	</tbody>");
			sb.append("	<thead>");
			sb.append("		<tr style='background-color: #F1F1F1;height: 40px;'>");
			sb.append("		<th>业务成功率</th><th>期待吞吐量/s</th><th>实际吞吐量/s</th><th>用户平均请求等待时间/ms</th><th>服务器平均请求等待时间/ms</th>");
			sb.append("		</tr>");
			sb.append("		</thead>");
			sb.append("		<tbody>");
			sb.append("		<tr style='height: 40px;'>");
			sb.append("			<td>" + businessSuccessRate  + "%</td>");
			sb.append("			<td>" + report.getExpectTps() + "</td>");
			sb.append("			<td>" + report.getActualTps() + "</td>");
			sb.append("			<td>" + report.getUserWaittime() + "</td>");
			sb.append("			<td>" + report.getServerWaittime() + "</td>"); 
			sb.append("		</tr>");
			sb.append("	</tbody>");
			sb.append("	<thead>");
			sb.append("		<tr style='background-color: #F1F1F1;height: 40px;'>");
			sb.append("		<th>峰值吞吐量/s</th><th>峰值持续时间</th><th>开始时间</th><th>结束时间</th><th>持续时间</th>");
			sb.append("		</tr>");
			sb.append("		</thead>");
			sb.append("		<tbody>");
			sb.append("		<tr style='height: 40px;'>");
			sb.append("			<td>" + peakThroughput  + "</td>");
			sb.append("			<td>" + peakDurationTime + "</td>");
			sb.append("			<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(report.getStartTime()) + "</td>");
			sb.append("			<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(report.getEndTime()) + "</td>");
			sb.append("			<td>" + durationTime + "</td>"); 
			sb.append("		</tr>");
			sb.append("		</tbody>");
			sb.append("	</table>");
			sb.append("</div>");
		}
		//2、发送
		boolean sendResult = emailSender.sendHtml(mailSubject, sb.toString(), mailReceiveAddress, mailCcReceiveAddress);
		
		//3、返回发送结果
		return sendResult;
	}
	/**
	 * @desc 获取压测结果描述
	 *
	 * @author liuliang
	 *
	 * @param conclusion  压测结果值
	 * @return String 压测结果描述
	 */
	private String getConclusionDesc(int conclusion){
		String conclusionImgUrl = basePath;
		if(0 == conclusion){
			conclusionImgUrl += "images/well.png";
		}else{
			conclusionImgUrl +=  "images/bad.png";
		}
		return conclusionImgUrl;
	}
}
