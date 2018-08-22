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
package com.yunji.titan.task.service.assign;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.yunji.titan.agent.state.AgentStateContext;
import com.yunji.titan.agent.state.BusynessState;
import com.yunji.titan.agent.state.StopState;
import com.yunji.titan.agent.utils.ChangeAgentState;
import com.yunji.titan.task.bean.po.ScenePO;
import com.yunji.titan.task.dao.scene.SceneDao;
import com.yunji.titan.task.exception.PullTaskException;
import com.yunji.titan.task.exception.ResourceException;
import com.yunji.titan.task.exception.StartPerformanceTestException;
import com.yunji.titan.task.exception.StopPerformanceTestException;
import com.yunji.titan.task.service.info.AgentInfoService;
import com.yunji.titan.task.watch.WatchAgents;
import com.yunji.titan.utils.AgentTaskBean;
import com.yunji.titan.utils.ContentType;
import com.yunji.titan.utils.ErrorCode;
import com.yunji.titan.utils.NodePath;
import com.yunji.titan.utils.ProtocolType;
import com.yunji.titan.utils.RequestType;
import com.yunji.titan.utils.TaskIssuedBean;
import com.yunji.titan.utils.ZookeeperConnManager;
import com.yunji.titan.utils.ftp.FtpUtils;
import redis.clients.jedis.JedisCluster;

/**
 * 任务编排和任务下发接口实现
 * 
 * @author gaoxianglong
 */
@Service
public class TaskServiceImpl implements TaskService {
	private String nodePath = NodePath.AGENT_LOCK_NODEPATH;
	@Resource
	private ZookeeperConnManager zookeeperConnManager;
	@Resource
	private AgentInfoService agentInfoService;
	@Resource
	private SceneDao sceneDao;
	@Resource
	private JedisCluster jedisCluster;
	@Resource
	private WatchAgents watchAgents;
	@Resource
	private FtpUtils ftpUtils;
	@Resource
	private AgentStateContext agentStateContext;
	private static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Override
	public void startPerformanceTest(TaskIssuedBean taskIssuedBean) {
		/* 获取当前场景状态,如果指定场景的状态非0则不允许执行压测 */
		if (!checkStatus(0, taskIssuedBean.getSenceId())) {
			throw new ResourceException("场景状态非空闲不允许启动压测", ErrorCode.SCENE_TYPE_FAIL);
		}
		ZooKeeper zkClient = zookeeperConnManager.getZkClient();
		/* 通过分布式锁来确保获取agent数时的一致性 */
		log.info("正在尝试获取任务分布式锁");
		while (true) {
			try {
				Stat stat = zkClient.exists(nodePath, false);
				if (null == stat) {
					zkClient.create(nodePath, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
					log.info("成功获取到分布式锁");
					/* 执行任务编排和任务下发 */
					taskIssued(taskIssuedBean, zkClient);
					zkClient.delete(nodePath, -1);
					break;
				}
			} catch (KeeperException | InterruptedException e) {
				throw new ResourceException("无法获取到任务锁,无法启动压测任务", ErrorCode.GET_TASK_STOCK_FAIL);
			}
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				log.error("error", e);
			}
		}
	}

	/**
	 * 执行任务下发
	 * 
	 * @author gaoxianglong
	 */
	private void taskIssued(TaskIssuedBean taskIssuedBean, ZooKeeper zkClient) {
		/* 获取当前注册中心中内可用的agent数,如果agent数不足则不允许执行压测 */
		int freeAgentSize = agentInfoService.getFreeAgentSize();
		if (freeAgentSize < taskIssuedBean.getAgentSize()) {
			throw new ResourceException("可用的agent节点数不足", ErrorCode.FREE_AGENT_lACK);
		}
		/* 获取当前注册中心内可用agent的znode地址 */
		List<String> freeAgents = agentInfoService.getFreeAgents();
		/* 开始任务编排 */
		Map<String, AgentTaskBean> taskMap = missionSchedule(freeAgents, taskIssuedBean.getAgentSize(),
				taskIssuedBean.getUrls(), downLoadFiles(taskIssuedBean.getParams()), taskIssuedBean.getRequestTypes(),
				taskIssuedBean.getProtocolTypes(), taskIssuedBean.getContentTypes(), taskIssuedBean.getCharsets());
		/* 为每一个场景的压测任务分配一个唯一的任务ID */
		String taskId = UUID.randomUUID().toString();
		/* 通过并行流方式加速任务下发,加速压测预热过程 */
		taskMap.keySet().parallelStream().forEach(znode -> {
			AgentTaskBean taskBean = taskMap.get(znode);
			taskBean.setTaskId(taskId);
			/* 每个agent分配的起步量级并发用户数 */
			taskBean.setInitConcurrentUsersSize(
					taskIssuedBean.getInitConcurrentUsersSize() / taskIssuedBean.getAgentSize());
			/* 每个agent分配的并发用户数 */
			taskBean.setConcurrentUsersSize(taskIssuedBean.getConcurrentUsersSize() / taskIssuedBean.getAgentSize());
			/* 每个并发用户分配的并发任务数 */
			taskBean.setTaskSize(taskIssuedBean.getTaskSize() / taskIssuedBean.getConcurrentUsersSize());
			taskBean.setSenceId(taskIssuedBean.getSenceId());
			taskBean.setSenceName(taskIssuedBean.getSenceName());
			taskBean.setAgentSize(taskIssuedBean.getAgentSize());
			taskBean.setExpectThroughput(taskIssuedBean.getExpectThroughput());
			if (0 != taskIssuedBean.getContinuedTime() && null != taskIssuedBean.getTimeUnit()) {
				taskBean.setContinuedTime(taskIssuedBean.getTimeUnit().toSeconds(taskIssuedBean.getContinuedTime()));
			}
			String taskInfo = JSON.toJSONString(taskBean);
			log.info("znode-->" + znode + "的任务信息-->" + taskInfo);
			/* 将agent对应的任务信息上传至ftp等待任务下发 */
			final String filePath = znode + ".json";
			if (ftpUtils.uploadFile(new File(filePath), taskInfo.getBytes())) {
				/* 将每一个agent的FTP脚本文件路径存储至Redis */
				jedisCluster.set(znode, filePath);
				try {
					/* 通知注册中心内指定的agent领取压测任务 */
					ChangeAgentState.change(agentStateContext, new BusynessState(), zkClient, znode);
				} catch (Exception e) {
					log.error("error", e);
					throw new StartPerformanceTestException("下发压测任务失败", ErrorCode.START_PERFORMANCE_TEST_FAIL);
				}
			}
		});
		Set<String> znodes = taskMap.keySet();
		/* 将执行某场景压测的所有agent的znode信息存储到redis,便于后续暂停某个场景时使用 */
		jedisCluster.sadd(String.valueOf(taskIssuedBean.getSenceId()), znodes.toArray(new String[znodes.size()]));
	}

	/**
	 * 从FTP服务器上下载指定的文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @param paths
	 *            每一个url对应的FTP文件路径
	 * 
	 * @exception Exception
	 * 
	 * @return Map<String, List<String>> 每一个URL对应的动态参数
	 */
	private Map<String, List<String>> downLoadFiles(Map<String, File> paths) {
		Map<String, List<String>> params = new HashMap<String, List<String>>(16);
		if (null == paths || paths.isEmpty()) {
			return null;
		}
		paths.forEach((url, ftpFile) -> {
			if (null == ftpFile) {
				return;
			}
			/* 从FTP进行下载 */
			boolean result = ftpUtils.downloadFile(ftpFile);
			if (result) {
				File localFile = new File(System.getProperty("user.home") + "/" + ftpFile.getName());
				if (localFile.exists()) {
					List<String> param = new ArrayList<String>();
					try (HSSFWorkbook book = new HSSFWorkbook(
							new BufferedInputStream(new FileInputStream(localFile.getPath())))) {
						HSSFSheet sheet = book.getSheetAt(0);
						/* 返回参数文件行数 */
						for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
							HSSFRow sheetRow = sheet.getRow(i);
							if (null != sheetRow) {
								HSSFCell cell = sheetRow.getCell(0);
								if (null != cell) {
									// cell.setCellType(Cell.CELL_TYPE_STRING);
									cell.setCellType(CellType.STRING);
									param.add(String.valueOf(cell.getRichStringCellValue()));
								}
							}
						}
						params.put(url, param);
					} catch (FileNotFoundException e) {
						log.error("error", e);
					} catch (IOException e) {
						log.error("error", e);
					} finally {
						if (localFile.delete()) {
							log.info("从FTP下载的源文件-->" + localFile.getPath() + "已经成功从本地删除");
						}
					}
				}
			} else {
				log.error("无法从FTP下载目标文件-->" + ftpFile.getPath());
			}
		});
		return params;
	}

	@Override
	public String pullTask(String zNode) {
		if (!jedisCluster.exists(zNode)) {
			throw new PullTaskException("领取任务失败", ErrorCode.PULL_TASK_FAIL);
		}
		String taskInfo = jedisCluster.get(zNode);
		jedisCluster.del(zNode);
		return taskInfo;
	}

	/**
	 * 压测任务编排,包括调度指定的agent参与压测、agent分配动态参数等
	 * 
	 * @author gaoxianglong
	 * 
	 * @param znodes
	 *            可用的agent节点集合
	 * 
	 * @param agentSize
	 *            申请压测agent节点数
	 * 
	 * @param urls
	 *            需要执行全链路压测的目标URL
	 * 
	 * @param params
	 *            每一个URL对应的动态参数
	 * 
	 * @param requestTypes
	 *            请求类型
	 * 
	 * @param protocolTypes
	 *            请求协议
	 * 
	 * @throws Exception
	 * 
	 * @return Map<String, TaskBean>
	 */
	private Map<String, AgentTaskBean> missionSchedule(List<String> znodes, int agentSize, List<String> urls,
			Map<String, List<String>> params, Map<String, RequestType> requestTypes,
			Map<String, ProtocolType> protocolTypes, Map<String, ContentType> contentTypes,
			Map<String, String> charsets) {
		Map<String, AgentTaskBean> taskMap = new ConcurrentHashMap<String, AgentTaskBean>(16);
		/* 定义目标agent分配动态参数的开始索引 */
		Map<String, Integer> startIndex = new ConcurrentHashMap<String, Integer>(16);
		/* 定义目标agent分配动态参数的结束索引 */
		Map<String, Integer> endIndex = new ConcurrentHashMap<String, Integer>(16);
		for (int i = 0; i < agentSize; i++) {
			AgentTaskBean taskBean = new AgentTaskBean();
			/* 通过并行流加速压测任务编排 */
			urls.parallelStream().forEach(url -> {
				if (null != params) {
					/* 获取manager下发的一个url包含的完整动态参数集合 */
					List<String> param = params.get(url);
					if (null != param && (!param.isEmpty())) {
						if (param.size() >= agentSize) {
							/* 每一个agent预计分配的动态参数数量 */
							int agentParamsize = 0 == (param.size() % agentSize) ? param.size() / agentSize
									: param.size() / agentSize + 1;
							/* 初始化索引 */
							if (!startIndex.containsKey(url)) {
								startIndex.put(url, 0);
							}
							if (!endIndex.containsKey(url)) {
								endIndex.put(url, agentParamsize);
							}
							/* 为每个agent分配相应的动态参数 */
							taskBean.getParams().put(url,
									paramAllocation(param, startIndex.get(url), endIndex.get(url)));
							/* 更新索引的起始位置 */
							startIndex.put(url, endIndex.get(url));
							endIndex.put(url, endIndex.get(url) + agentParamsize);
						} else {
							taskBean.getParams().put(url, param);
						}
					}
				}
				taskBean.getUrls().add(url);
				taskBean.getRequestTypes().put(url, requestTypes.get(url));
				taskBean.getProtocolTypes().put(url, protocolTypes.get(url));
				taskBean.getContentTypes().put(url, contentTypes.get(url));
				taskBean.getCharsets().put(url, charsets.get(url));
			});
			taskMap.put(znodes.get(i), taskBean);
		}
		return taskMap;
	}

	@Override
	public void stopPerformanceTest(Integer senceId) {
		/* 获取当前场景状态,如果指定场景的状态非1则不允许停止压测 */
		if (!checkStatus(1, senceId)) {
			throw new ResourceException("场景状态非启动中不允许停止压测", ErrorCode.SCENE_TYPE_FAIL);
		}
		String key = String.valueOf(senceId);
		if (!jedisCluster.exists(key)) {
			throw new StopPerformanceTestException("redis中不包含场景ID[" + senceId + "]的相关znode节点",
					ErrorCode.STOP_PERFORMANCE_TEST_FAIL);
		}
		Set<String> znodes = jedisCluster.smembers(key);
		if (!znodes.isEmpty()) {
			ZooKeeper zkClient = zookeeperConnManager.getZkClient();
			try {
				/* 通过并行流加速暂停任务下发 */
				znodes.parallelStream().forEach(znode -> {
					try {
						String value = new String(zkClient.getData(znode, false, null));
						if (null != value) {
							Properties properties = new Properties();
							properties.load(new StringReader(value));
							String agentStateKey = "task";
							String interruptedKey = "interrupted";
							/* 忙碌状态才可停止task=true\interrupted=false */
							if (Boolean.parseBoolean(properties.getProperty(agentStateKey))
									&& !Boolean.parseBoolean(properties.getProperty(interruptedKey))) {
								ChangeAgentState.change(agentStateContext, new StopState(), zkClient, znode);
							} else {
								throw new ResourceException("agent状态不对", ErrorCode.AGENT_TYPE_ERROR);
							}
						}
					} catch (Exception e) {
						log.error("error", e);
						throw new StopPerformanceTestException("停止压测失败", ErrorCode.STOP_PERFORMANCE_TEST_FAIL);
					}
				});
			} finally {
				jedisCluster.del(key);
			}
		}
	}

	/**
	 * 状态检查
	 * 
	 * @author gaoxianglong
	 */
	private boolean checkStatus(int status, int senceId) {
		boolean result = false;
		List<ScenePO> scenePOs = sceneDao.getSceneStatus(senceId);
		if (!scenePOs.isEmpty()) {
			int sceneStatus = scenePOs.get(0).getSceneStatus();
			if (status == sceneStatus) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 为每一个agent分配的相应数量的动态参数
	 * 
	 * @author gaoxianglong
	 * 
	 * @param params
	 *            一个url包含的完整动态参数
	 * 
	 * @param startIndex
	 *            开始索引位置
	 * 
	 * @param endIndex
	 *            结束索引位置
	 * 
	 * @return List<String> 返回指定agent可分配的动态参数
	 */
	private List<String> paramAllocation(List<String> params, int startIndex, int endIndex) {
		List<String> list = null;
		if (params.isEmpty()) {
			return list;
		}
		list = new ArrayList<String>();
		for (int i = startIndex; i < (endIndex > params.size() ? params.size() : endIndex); i++) {
			list.add(params.get(i));
		}
		return list;
	}
}
