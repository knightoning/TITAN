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

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
/**
 * 分布式定时任务
 * 
 * @author gaoxianglong
 */
@Service("distributedTimerTask")
public class DistributedTimerTaskImpl implements DistributedTimerTask {
	@Resource
	private TimerTaskListener timerTaskListener;

	@Override
	public boolean addTask(String taskName) {
		return notifyTask(taskName, new String("taskName=" + taskName + "\n" + "type=true"));
	}

	@Override
	public boolean deleteTask(String taskName) {
		return notifyTask(taskName, new String("taskName=" + taskName + "\n" + "type=false"));
	}

	/**
	 * 通知其他节点任务发生变化需要重新更新本地
	 * 
	 * @author gaoxianglong
	 */
	private boolean notifyTask(String taskName, String task) {
		boolean result = false;
		if (StringUtils.isEmpty(taskName)) {
			return result;
		}
		return timerTaskListener.synTask(task.getBytes());
	}
}