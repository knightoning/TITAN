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
package com.yunji.titan.monitor.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yunji.titan.monitor.utils.MsgProvider;
import com.yunji.titan.monitor.utils.SigarUtil;
import com.yunji.titan.utils.MonitorBean;

/**
 * @desc 初始化方法
 *
 * @author liuliang
 *
 */
@Component
public class InitMonitor {

	private Logger logger = LoggerFactory.getLogger(InitMonitor.class);
	
	/**
	 * 取样间隔时间
	 */
	@Value("${get.data.time}")
	private int getDataTime;
	
	/**
	 * 机器标识 0：agent机器    1：目标机
	 */
	@Value("${machine.flag}")
	private int machineFlag;
	
	/**
	 * MQ消息发送
	 */
	@Resource
	private MsgProvider msgProvider;
	
	/**
	 * 埋点机器IOPS
	 */
	private static Double MONITOR_MACHINE_IOPS = 0.0;
	
	/**
	 * @desc 初始启动
	 *
	 * @author liuliang
	 *
	 */
	@PostConstruct
	public void init() {
		logger.info("----------------titan-monitor started...");
		new Thread(){
		    @Override
		    public void run() {
		    	doIostat();
		    }
		}.start();
		while(true) {
			//1、获取组装信息
			MonitorBean monitorBean = new MonitorBean();
			Long localTime = System.currentTimeMillis();
			try {
				monitorBean.setCreateTime(localTime);
				monitorBean.setServerType(machineFlag);
				monitorBean.setIp(SigarUtil.getHostAddress());
				monitorBean.setCpuUsage(SigarUtil.getCPUUsage());
				monitorBean.setMemoryUsage(SigarUtil.getMemoryUsage());
				monitorBean.setIops(MONITOR_MACHINE_IOPS);
				//2、信息上报
				msgProvider.sendMsg(JSON.toJSONString(monitorBean));
			} catch (Exception e) {
				logger.error("monitor数据上报异常,monitorBean:{}",JSON.toJSONString(monitorBean),e);
			}
			//休眠
			try {
				Thread.sleep(getDataTime - (System.currentTimeMillis() - localTime));
			} catch (InterruptedException e) {
				logger.error("monitor休眠异常",e);
			}
		}
	}
	/**
	 * @desc iostat命令获取IOPS,刷新全局变量
	 *
	 * @author liuliang
	 *
	 */
	private void doIostat(){
        try {    
        	String os = System.getProperty("os.name");  
        	String windowsFlag = "win";
        	if((null != os)  && os.toLowerCase().startsWith(windowsFlag)){  
        	   return;  //windows不执行
        	}  
        	
        	String command = "iostat -d -x 5";    
            Runtime r = Runtime.getRuntime();    
            Process pro = r.exec(command);    
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));    
            String line = null;    
            int count =  0;    
            while((line=in.readLine()) != null){            
                if(++count >= 4){    
	                  if((null != line) &&(line.startsWith("vda"))){
	                	  String[] temp = line.split("\\s+"); 
	                	  if(temp.length > 1){   
	                		  MONITOR_MACHINE_IOPS = Double.parseDouble(temp[3]) + Double.parseDouble(temp[4]);
	                	  }
	                  }
                }    
            }    
            in.close();    
            pro.destroy();    
        } catch (Exception e) {    
            logger.error("获取IOPS异常",e);
        }  
	}
}
