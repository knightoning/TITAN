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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yunji.titan.manager.service.AgentService;

/**
 * @desc 操作agent节点service接口实现类
 *
 * @author liuliang
 *
 */
@Service
public class AgentServiceImpl implements AgentService{

	private Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);
	
	/**
	 * @desc 执行shell脚本
	 *
	 * @author liuliang
	 *
	 * @param commandPath 脚本路径
	 * @return
	 */
	@Override
	public String executeCommand(String commandPath) throws Exception{
		logger.info("执行命令,command：{}",commandPath);
		String commondRealPath = Thread.currentThread().getContextClassLoader().getResource(commandPath).getPath();
		InputStreamReader stdISR = null;  
        InputStreamReader errISR = null;  
        Process process = null;  
        try {  
            process = Runtime.getRuntime().exec(commondRealPath);  
            process.waitFor();  
            String line = null;  
  
            stdISR = new InputStreamReader(process.getInputStream());  
            BufferedReader stdBR = new BufferedReader(stdISR);  
            while ((line = stdBR.readLine()) != null) {  
            	logger.info("STD line:" + line);  
            }  
  
            errISR = new InputStreamReader(process.getErrorStream());  
            BufferedReader errBR = new BufferedReader(errISR);  
            while ((line = errBR.readLine()) != null) {  
            	logger.error("ERR line:" + line);  
            }  
        } catch (IOException | InterruptedException e) {  
            e.printStackTrace();  
            throw e;
        } finally {  
            try {  
                if (stdISR != null) {  
                    stdISR.close();  
                }  
                if (errISR != null) {  
                    errISR.close();  
                }  
                if (process != null) {  
                    process.destroy();  
                }  
            } catch (IOException e) {  
            	logger.error("正式执行命令有IO异常,commandPath:{}",commandPath,e);
            }  
        }  
		return "a";
	}
}
