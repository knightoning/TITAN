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

import javax.annotation.Resource;

import org.junit.Test;

import com.yunji.titan.manager.dao.MonitorDao;
import com.yunji.titan.manager.test.BaseJunit4Test;

/**
 * @desc
 *
 * @author liuliang
 *
 */
public class MonitorDaoTest extends BaseJunit4Test{

	@Resource
	private MonitorDao monitorDao;
	
	@Test
	public void queryListTest(){
		System.out.println("-----1---");
		try {
			System.out.println(monitorDao.queryList(10));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-----2---");
	}
}
