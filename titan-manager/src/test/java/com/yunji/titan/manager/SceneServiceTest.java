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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.yunji.titan.manager.entity.Scene;
import com.yunji.titan.manager.service.SceneService;

/**
 * @desc 场景
 *
 * @author liuliang
 *
 */
public class SceneServiceTest {

	SceneService sceneService = null;
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

        sceneService = (SceneService) context.getBean("sceneServiceImpl");

    }

    @After
    public void close() {
        context.close();
    }
    
    /**
     * @desc 更新场景状态测试
     *
     * @author liuliang
     *
     */
    @Test
    public void updateSceneStatusTest(){
    	System.out.println("--开始-------");
    	try {
			int result = sceneService.updateSceneStatus(23, 1);
			
			System.out.println("result:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 根据linkId查询场景列表 测试
     */
    @Test
    public void getSceneListByLinkIdTest(){
    	System.out.println("------开始-------");
    	List<Scene> list;
		try {
			list = sceneService.getSceneListByLinkId(5, 0, 10);
			System.out.println(JSON.toJSONString(list));
			
			list = sceneService.getSceneListByLinkId(6, 0, 10);
			System.out.println(JSON.toJSONString(list));
			
			list = sceneService.getSceneListByLinkId(1, 0, 10);
			System.out.println(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("------结束-------");
    }
    
    /**
     * 查询测试
     */
    @Test
    public void getSceneTest(){
    	System.out.println("------开始-------");
    	try {
    		Scene scene = sceneService.getScene(3);
    		System.out.println(JSON.toJSONString(scene));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println("------结束-------");
    }
}
