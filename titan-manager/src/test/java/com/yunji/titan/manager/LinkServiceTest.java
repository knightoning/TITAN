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

import com.yunji.titan.manager.service.LinkService;
import com.yunji.titan.manager.service.SceneService;

/**
 * @desc 场景
 *
 * @author liuliang
 *
 */
public class LinkServiceTest {

	LinkService linkService = null;
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

        linkService = (LinkService) context.getBean("linkServiceImpl");
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
    public void addSceneTest(){
    	System.out.println("--------");
    	System.out.println("---------");
    }
    
    /**
     * @desc 删除链路测试
     *
     * @author liuliang
     *
     */
    @Test
    public void removeLinkAndUpdateSceneTest(){
    	System.out.println("----开始----");
    	long linkId = 110;
    	int sceneCount = 0;
    	int result = 0;
		try {
			sceneCount = sceneService.getSceneCountByLinkId(linkId);
			result = linkService.removeLinkAndUpdateScene(linkId, sceneCount);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sceneCount + ":" + result);
    	System.out.println("-----结束----");
    }
}
