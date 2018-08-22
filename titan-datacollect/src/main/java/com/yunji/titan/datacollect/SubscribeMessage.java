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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.yunji.titan.utils.MonitorBean;
import com.yunji.titan.utils.ResultBean;
import com.yunji.titan.utils.ThreadPoolManager;
import com.yunji.titan.utils.config.RocketMqDataSource;

/**
 * 从消息队列中获取压测结果数据信息
 * 
 * @author gaoxianglong
 */
@Service
public class SubscribeMessage {
	@Resource
	private ThreadPoolManager threadPoolManager;
	@Resource
	private UploadData uploadData;
	@Resource(name = "operationalIndicator")
	private RocketMqDataSource operationalIndicator;
	@Resource(name = "monitorIndicator")
	private RocketMqDataSource monitorIndicator;
	private Map<String, List<ResultBean>> resultMap;
	private Map<String, CountDownLatch> countDownLatchMap;
	private Logger log = LoggerFactory.getLogger(SubscribeMessage.class);

	public SubscribeMessage() {
		countDownLatchMap = new ConcurrentHashMap<String, CountDownLatch>();
		resultMap = new ConcurrentHashMap<String, List<ResultBean>>();
	}

	protected void getMsg() {
		getOperationalIndicator();
		getMonitorIndicator();
	}

	/**
	 * 获取业务指标数据
	 * 
	 * @author gaoxianglong
	 */
	private void getOperationalIndicator() {
		DefaultMQPushConsumer consumer = operationalIndicator.getConsumer();
		try {
			consumer.subscribe(operationalIndicator.getRocketTopic(), "*");
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			MessageListenerConcurrently titanMessageListenerConcurrently = (msgs, context) -> {
				if (!msgs.isEmpty()) {
					msgs.stream().forEach(msg -> {
						final String body = new String(msg.getBody());
						try {
							log.info("id-->" + msg.getMsgId() + "\tbody-->" + body);
							ResultBean resultBean = JSONObject.parseObject(body, ResultBean.class);
							if (null != resultBean) {
								upload(resultBean.getTaskId(), resultBean);
							}
						} catch (Exception e) {
							log.error("error", e);
						}
					});
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			};
			consumer.registerMessageListener(titanMessageListenerConcurrently);
			consumer.start();
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	/**
	 * 获取业务指标数据
	 * 
	 * @author gaoxianglong
	 */
	private void getMonitorIndicator() {
		DefaultMQPushConsumer consumer = monitorIndicator.getConsumer();
		try {
			consumer.subscribe(monitorIndicator.getRocketTopic(), "*");
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			MessageListenerConcurrently titanMessageListenerConcurrently = (msgs, context) -> {
				if (!msgs.isEmpty()) {
					msgs.stream().forEach(msg -> {
						final String body = new String(msg.getBody());
						try {
							log.info("id-->" + msg.getMsgId() + "\tbody-->" + body);
							if (null != body) {
								MonitorBean monitorBean = JSONObject.parseObject(body, MonitorBean.class);
								if (null != monitorBean) {
									uploadData.uploadMontor(monitorBean);
								}
							}
						} catch (Exception e) {
							log.error("error", e);
						}
					});
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			};
			consumer.registerMessageListener(titanMessageListenerConcurrently);
			consumer.start();
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	/**
	 * 执行数据上报
	 * 
	 * @author gaoxianglong
	 */
	private synchronized void upload(final String taskId, ResultBean resultBean) {
		if (!resultMap.containsKey(taskId)) {
			log.info("taskId-->" + taskId + "\tagentSize-->" + resultBean.getAgentSize());
			final CountDownLatch latch = new CountDownLatch(resultBean.getAgentSize());
			countDownLatchMap.put(taskId, latch);
			threadPoolManager.getThreadPool().execute(() -> {
				/* 执行数据上报 */
				try {
					uploadData.upload(taskId, countDownLatchMap, resultMap);
				} catch (Exception e) {
					log.error("error", e);
				}
			});
			List<ResultBean> list = new ArrayList<ResultBean>();
			list.add(resultBean);
			resultMap.put(taskId, list);
			latch.countDown();
		} else {
			resultMap.get(taskId).add(resultBean);
			CountDownLatch latch = countDownLatchMap.get(taskId);
			latch.countDown();
		}
	}
}