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
package com.yunji.titan.manager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.yunji.titan.manager.bo.ReportBO;
import com.yunji.titan.manager.service.ReportService;

/**
 * @desc 测试报告测试
 *
 * @author liuliang
 *
 */
public class ReportServiceTest {


	ReportService reportService = null;
    GenericXmlApplicationContext context = new GenericXmlApplicationContext();

    @Before
    public void init() {
        System.setProperty("config_env", "local");
        String root;
        String separator;
        String spring;

        root = System.getProperty("gateway.dir");
        separator = System.getProperty("file.separator");
        spring = "file:" + root + separator + "src/main/resources/*.xml";
        // context.setValidating(false);
        context.load("classpath*:/*.xml", spring);
        context.refresh();
        context.start();

        reportService = (ReportService) context.getBean("reportServiceImpl");

    }

    @After
    public void close() {
        context.close();
    }
    
    /**
     * @desc 添加测试报告 测试
     *
     * @author liuliang
     *
     */
    @Test
    public void addReportTest(){
    	System.out.println("---开始-----");
    	ReportBO reportBO = new ReportBO();
    	reportBO.setReportName("测试名10333333");
    	reportBO.setSceneId(1222222L);
    	reportBO.setSceneName("场景名1033333");
    	reportBO.setStartTime(System.currentTimeMillis());
    	reportBO.setEndTime(System.currentTimeMillis());
    	reportBO.setExpectTps(2000);
    	
    	reportBO.setActualTps(100);
    	reportBO.setTotalRequest(33333L);
    	reportBO.setSuccessRequest(2222L);
    	reportBO.setConcurrentUser(300L);
    	reportBO.setUserWaittime(400L);
    	reportBO.setServerWaittime(600L);
    	
    	reportBO.setConclusion(1);
    	reportBO.setCreateTime(System.currentTimeMillis());
    	try {
			int result = reportService.addReport(reportBO);
			
			System.out.println("result:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	System.out.println("---结束-----");
    }
    
    public static void main(String[] args) {
		
    	System.out.println(String.valueOf(System.currentTimeMillis()).length());
	}
}
