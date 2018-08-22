package com.yunji.titan.manager;


import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.yunji.titan.manager.dao.BaseDao;
import com.yunji.titan.manager.entity.AutomaticTask;
import com.yunji.titan.manager.entity.Link;
import com.yunji.titan.manager.entity.Monitor;
import com.yunji.titan.manager.entity.MonitorSet;
import com.yunji.titan.manager.entity.Report;
import com.yunji.titan.manager.entity.Scene;
import com.yunji.titan.manager.impl.dao.base.Criteria;
import com.yunji.titan.manager.impl.dao.base.Operator;
import com.yunji.titan.manager.impl.dao.base.Pager;
import com.yunji.titan.manager.test.BaseJunit4Test;

/**
 * @desc 基础dao测试
 *
 * @author liuliang
 *
 */
public class BaseDaoTest extends BaseJunit4Test{

	@Resource
	BaseDao baseDao;
	
	private Link link;
	private Scene scene;
	private Report report;
	private AutomaticTask automaticTask;
	private Monitor monitor;
	private MonitorSet monitorSet;
	
	@Before
	public void before(){
		System.setProperty("config_env", "local");
		link = new Link();
		scene = new Scene();
		report = new Report();
		automaticTask = new AutomaticTask();
		monitor = new Monitor();
		monitorSet = new MonitorSet();
	}
	
	/**
	 * 查记录数
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void queryCountTest() throws Exception{
		System.out.println("link count:" + baseDao.queryCount(Link.class));
		System.out.println("scene count:" + baseDao.queryCount(Scene.class));
		System.out.println("report count:" + baseDao.queryCount(Report.class));
		System.out.println("automaticTask count:" + baseDao.queryCount(AutomaticTask.class));
		System.out.println("monitor count:" + baseDao.queryCount(Monitor.class));
		System.out.println("monitorSet count:" + baseDao.queryCount(MonitorSet.class));
	}
	
	/**
	 * 增
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void insertTest() throws Exception{
		monitor.setIp("88.88.88.88");
		monitor.setCpuUsage(10.0);
		monitor.setMemoryUsage(2.0);
		monitor.setIops(3.0);
		monitor.setCreateTime(System.currentTimeMillis());
		monitor.setServerType(0);
		int result = baseDao.insert(monitor);
		
		System.out.println("result:" + result);
	}
	
	/**
	 * 删
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void deleteTest() throws Exception{
		int result = baseDao.delete(Monitor.class, 17996L);
		System.out.println("result:" + result);
	}
	
	/**
	 * 更新
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void updateTest() throws Exception{
		monitor.setMonitorId(17997L);
		monitor.setIp("88.88.99.99");
		monitor.setCpuUsage(99.0);
		monitor.setMemoryUsage(99.0);
		monitor.setIops(99.0);
		monitor.setCreateTime(System.currentTimeMillis());
		monitor.setServerType(1);
		
		int result = baseDao.update(monitor);
		
		System.out.println(result);
	}
	
	/**
	 * 查
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void queryTest() throws Exception{
		System.out.println("----------");
		
		AutomaticTask automaticTask = baseDao.query(AutomaticTask.class, 46L);
		System.out.println(JSON.toJSONString(automaticTask));
		
		System.out.println("----------");
	}
	
	/**
	 * 分页查
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void queryListTest() throws Exception{
		List<Monitor> list = baseDao.queryList(Monitor.class,new Pager(1,2));
		System.out.println(list.size());
		System.out.println(JSON.toJSONString(list));
		
	}
	
	/**
	 * 查询符合条件的
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void queryCountByCriteriaTest() throws Exception{
		Criteria criteria = new Criteria(Monitor.class, new String[]{"ip","cpuUsage","iops"}, new String[]{"LIKE","LIKE","="},new String[]{"AND","OR"},new Object[]{"%172.16.5%","%26%",8});
		int result = baseDao.queryCount(criteria);
		
		Criteria criteria2 = new Criteria(Monitor.class, new String[]{"ip"}, new String[]{"LIKE"},new Object[]{"%172.16.5%"});
		int result2 = baseDao.queryCount(criteria2);
		
		Criteria criteria3 = Criteria.create(Monitor.class, "ip", Operator.LIKE,"%172.16.5%");
		int result3 = baseDao.queryCount(criteria3);
		
		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
	}
	
	/**
	 * 删
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void deleteByCriteriaTest() throws Exception{
		Criteria criteria = new Criteria(Monitor.class, new String[]{"ip","cpuUsage","iops"}, new String[]{"LIKE","LIKE","="},new String[]{"AND","OR"},new Object[]{"%172.16.5%","%26%",8});
		baseDao.delete(criteria);
	}
	
	/**
	 * 查
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void queryByCriteriaTest() throws Exception{
		Monitor monitor = baseDao.query(Criteria.create(Monitor.class, "ip", Operator.EQ, "172.16.4.107"));
		System.out.println(JSON.toJSONString(monitor));
		
		Link link = baseDao.query(Criteria.create(Link.class, "linkName", Operator.EQ, "测试链路"));
		System.out.println(JSON.toJSONString(link));
	}
	
	/**
	 * 查
	 * 
	 * @author liuliang
	 *
	 * @throws Exception
	 */
	@Test
	public void queryListByCriteriaTest() throws Exception{
		System.out.println("----------");
		
		List<Scene> list = baseDao.queryList(Criteria.create(Scene.class, "sceneName", Operator.LIKE, "%测试%"),Pager.create(0,2));
		System.out.println(JSON.toJSONString(list));
		System.out.println("----------");
	}
}
