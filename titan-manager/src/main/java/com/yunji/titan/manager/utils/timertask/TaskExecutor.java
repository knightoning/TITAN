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
package com.yunji.titan.manager.utils.timertask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.yunji.titan.utils.ThreadPoolManager;
/**
 * 自动压测任务
 *
 * @author liuliang
 *
 */
@Service
public class TaskExecutor {
	private SimpleDateFormat dateFormat;
	protected static Map<String, TaskInfoBean> tasksMap;
	@Resource
	private ThreadPoolManager tpManager;
	private Logger log = LoggerFactory.getLogger(TaskExecutor.class);

	public TaskExecutor() {
		dateFormat = new SimpleDateFormat("HH:mm:ss");
		tasksMap = new HashMap<>();
	}

	private @PostConstruct void execute() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					/* 只有拿到锁后才允许执行任务调度 */
					if (MasterElection.IS_MASTER) {
						synchronized (tasksMap) {
							Set<String> taskNames = tasksMap.keySet();
							for (String taskName : taskNames) {
								TaskInfoBean taskinfoBean = tasksMap.get(taskName);
								if (taskinfoBean.getTime().equals(dateFormat.format(new Date()))) {
									tpManager.getThreadPool().execute(taskinfoBean.getTask());
								}
							}
						}
					}
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						log.error("error", e);
					}
				}
			}
		}.start();
	}
}