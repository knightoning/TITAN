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

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yunji.titan.manager.entity.Monitor;
import com.yunji.titan.manager.service.MonitorService;
import com.yunji.titan.manager.test.BaseJunit4Test;

/**
 * @desc
 *
 * @author liuliang
 *
 */
public class MonitorServiceTest  extends BaseJunit4Test{
	
	@Resource
	private MonitorService monitorService;
	
	@Test
	public void getMonitorFilterListTest() {
		try {
			List<Monitor> list =  monitorService.getMonitorFilterList(10);
			
			System.out.println("-----------");
			System.out.println(list.size());
			for(Monitor m:list) {
				System.out.println(JSON.toJSONString(m)) ;
			}
			System.out.println("-------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
