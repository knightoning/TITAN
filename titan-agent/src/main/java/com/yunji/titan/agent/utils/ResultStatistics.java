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
package com.yunji.titan.agent.utils;

import com.alibaba.fastjson.JSON;
import com.yunji.titan.utils.AgentTaskBean;
import com.yunji.titan.utils.ResultBean;

/**
 * 压测结果统计
 * 
 * @author gaoxianglong
 */
public class ResultStatistics {
	public static String result(long startTime, long endTime, int httpSuccessNum, int serviceSuccessNum,
			AgentTaskBean agentTaskBean) {
		ResultBean resultBean = new ResultBean();
		resultBean.setTaskId(agentTaskBean.getTaskId());
		resultBean.setAgentSize(agentTaskBean.getAgentSize());
		resultBean.setStartTime(startTime);
		resultBean.setExpectThroughput(agentTaskBean.getExpectThroughput());
		resultBean.setEndTime(endTime);
		resultBean.setConcurrentUsers(agentTaskBean.getConcurrentUsersSize());
		resultBean.setSenceId(agentTaskBean.getSenceId());
		resultBean.setSenceName(agentTaskBean.getSenceName());
		resultBean.setTaskSize(agentTaskBean.getTaskSize());
		resultBean.setHttpSuccessNum(httpSuccessNum);
		resultBean.setServiceSuccessNum(serviceSuccessNum);
		return JSON.toJSONString(resultBean);
	}
}