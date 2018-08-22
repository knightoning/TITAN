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

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yunji.titan.datacollect.bean.po.ReportPO;
import com.yunji.titan.datacollect.dao.report.MonitorDao;
import com.yunji.titan.datacollect.dao.report.ReportDao;
import com.yunji.titan.datacollect.utils.ResultStatistics;
import com.yunji.titan.utils.MonitorBean;
import com.yunji.titan.utils.ResultBean;

/**
 * 接受agent的压测数据并上报到数据库中落盘
 * 
 * @author gaoxianglong
 */
@Service
public class UploadData {
	@Resource
	private ReportDao reportDao;
	@Resource
	private MonitorDao monitorDao;
	private static Logger log = LoggerFactory.getLogger(UploadData.class);

	/**
	 * 执行数据上报
	 * 
	 * @author gaoxianglong
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void upload(String taskId, Map<String, CountDownLatch> countDownLatchMap,
			Map<String, List<ResultBean>> resultMap) throws Exception {
		CountDownLatch latch = countDownLatchMap.get(taskId);
		try {
			/* 等待指定场景的压测数据 */
			latch.await();
			List<ResultBean> results = resultMap.get(taskId);
			/* 统计压测结果 */
			ReportPO reportPO = ResultStatistics.result(results);
			if (null != reportPO) {
				/* 新增压测结果信息 */
				reportDao.insertReport(reportPO);
				/* 更改场景状态为未开始 */
				reportDao.updateScene(reportPO.getSceneId(), 0);
				log.info("senceId为[" + reportPO.getSceneId() + "]的压测结果已经收集完成并成功上报");
			}
		} finally {
			/* 资源回收 */
			countDownLatchMap.remove(taskId);
			resultMap.remove(taskId);
		}
	}

	/**
	 * @desc 数据上报-monitor
	 *
	 * @author liuliang
	 *
	 * @param monitorBean
	 */
	public void uploadMontor(MonitorBean monitorBean) {
		try {
			int result = monitorDao.insert(monitorBean);
			if (1 != result) {
				log.error("monitor数据上报失败,monitorBean:{}", JSON.toJSONString(monitorBean));
			}
		} catch (Exception e) {
			log.error("monitor数据上报异常,monitorBean:{}", JSON.toJSONString(monitorBean), e);
		}
	}
}