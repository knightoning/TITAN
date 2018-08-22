/*
 * Copyright 2015-2101 yunjiweidian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yunji.titan.datacollect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * titan-datacollect启动函数
 * 
 * @author gaoxianglong
 */
public class Main {
	private static ApplicationContext context;
	private static Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		new Main();
	}

	Main() {
		init();
	}

	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:*-context.xml");
		MasterElection masterElection = context.getBean("masterElection", MasterElection.class);
		/* Master选举 */
		masterElection.election();
		try {
			synchronized (this) {
				this.wait();
			}
		} catch (Exception e) {
			log.error("error", e);
		}
	}
}