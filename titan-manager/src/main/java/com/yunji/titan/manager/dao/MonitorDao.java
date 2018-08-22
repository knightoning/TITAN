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
package com.yunji.titan.manager.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yunji.titan.manager.entity.Monitor;

/**
 * @desc 机器资源情况Dao
 *
 * @author liuliang
 *
 */
@Repository
public class MonitorDao {

	  @Resource
	  private JdbcTemplate jdbcTemplate;
	  
	  /**
	   * @desc 查询指定条数记录
	   *
	   * @author liuliang
	   *
	   * @param size 记录数
	   * @return List<Monitor>
	   * @throws Exception
	   */
	  public List<Monitor> queryList(int size) throws Exception{
		  String sql = "(SELECT * FROM t_monitor WHERE ip = (SELECT b.ip FROM(SELECT a.ip FROM t_monitor a ORDER BY a.create_time DESC) b GROUP BY b.ip LIMIT 0,1) ORDER BY create_time DESC LIMIT ?)"
					  +"UNION ALL"
					  +"(SELECT * FROM t_monitor WHERE ip = (SELECT b.ip FROM(SELECT a.ip FROM t_monitor a ORDER BY a.create_time DESC) b GROUP BY b.ip LIMIT 1,1) ORDER BY create_time DESC LIMIT ?)";
		  return jdbcTemplate.query(sql,new Object[]{size,size},BeanPropertyRowMapper.newInstance(Monitor.class));
	  }
	  
	  /**
	   * @desc 插入
	   *
	   * @author liuliang
	   *
	   * @param monitor
	   * @return int 受影响的记录数
	   * @throws Exception
	   */
	  public int insert(Monitor monitor) throws Exception{
		  String sql = "INSERT INTO t_monitor(ip,cpu_usage,memory_usage,iops,create_time) VALUES(?,?,?,?,?)";
		  return jdbcTemplate.update(sql, new Object[]{monitor.getIp(),monitor.getCpuUsage(),monitor.getMemoryUsage(),monitor.getIops(),System.currentTimeMillis()});
	  }

	/**
	 * @desc 查询指定条数的IP集合
	 *
	 * @author liuliang
	 *
	 * @param serverType 服务类型 0：agent 1:目标机器
	 * @param size 记录数
	 * @return List<String> IP集合
	 */
	public List<String> queryIPList(int serverType,int size) {
		String sql = "SELECT ip FROM(SELECT * FROM t_monitor WHERE server_type = ? ORDER BY create_time DESC) b GROUP BY ip ORDER BY create_time DESC LIMIT ?";
		return jdbcTemplate.queryForList(sql,new Object[]{serverType,size},String.class);
	}

	/**
	 * @desc 根据IP查询指定条数数据
	 *
	 * @author liuliang
	 *
	 * @param ip
	 * @param size
	 * @return
	 */
	public List<Monitor> queryList(String ip, int size) {
		String sql = "SELECT * FROM t_monitor WHERE ip = ? ORDER BY create_time DESC LIMIT ?";
		return jdbcTemplate.query(sql,new Object[]{ip,size},BeanPropertyRowMapper.newInstance(Monitor.class));
	}

	/**
	 * @desc 根据IP查询数据集合
	 *
	 * @author liuliang
	 *
	 * @param serverType 服务类型 0：agent 1:目标机器
	 * @param ips
	 * @return
	 */
	public List<Monitor> queryListByIP(int serverType,String ips) throws Exception{
		String[] arr = ips.split("\\,");
		StringBuilder sb = new StringBuilder();
		for(int i=0,length = arr.length;i<length;i++) {
			sb.append("'").append(arr[i]).append("'");
			if((i+1) != length) {
				sb.append(",");
			}
		}
		String sql = "SELECT * FROM t_monitor WHERE server_type = " + serverType + " AND ip IN (" + sb.toString() + ")";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Monitor.class));
	}

	/**
	 * @desc
	 *
	 * @author liuliang
	 *
	 * @param i
	 * @param ips
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Monitor> queryListByIP(int serverType, String ips, long startTime, long endTime) throws Exception{
		String[] arr = ips.split("\\,");
		StringBuilder sb = new StringBuilder();
		for(int i=0,length = arr.length;i<length;i++) {
			sb.append("'").append(arr[i]).append("'");
			if((i+1) != length) {
				sb.append(",");
			}
		}
		String sql = "SELECT * FROM t_monitor WHERE server_type = " + serverType + " AND ip IN (" + sb.toString() + ") AND create_time >= " + startTime+ " AND create_time <= " + endTime + " ORDER BY create_time DESC";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Monitor.class));
	}

	/**
	 * @desc 删除指定时间之前的数据
	 * 
	 * @author liuliang
	 *
	 * @param serverType 机器类型
	 * @param time 时间戳
	 * @return int  受影响的记录数
	 */
	public int delBreforeTime(int serverType,Long time) throws Exception{
		String sql = "DELETE FROM t_monitor WHERE server_type = ? AND create_time < ?";
		return jdbcTemplate.update(sql,new Object[] {serverType,time});
	}
}
