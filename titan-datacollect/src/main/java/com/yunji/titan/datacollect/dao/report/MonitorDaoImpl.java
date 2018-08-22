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
package com.yunji.titan.datacollect.dao.report;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunji.titan.utils.MonitorBean;

/**
 * @desc 机器资源情况Dao实现类
 *
 * @author liuliang
 *
 */
@Repository
public class MonitorDaoImpl implements MonitorDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * @desc 插入记录
	 *
	 * @author liuliang
	 *
	 * @param monitorBean
	 * @return int 受影响的记录数
	 * @throws Exception
	 */
	@Override
	public int insert(MonitorBean monitorBean) throws Exception {
		String sql = "INSERT INTO t_monitor(server_type,ip,cpu_usage,memory_usage,iops,create_time) VALUES(?,?,?,?,?,?)";
		return jdbcTemplate.update(sql,
				new Object[] { monitorBean.getServerType(), monitorBean.getIp(), monitorBean.getCpuUsage(),
						monitorBean.getMemoryUsage(), monitorBean.getIops(), monitorBean.getCreateTime() });

	}

}
