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
package com.yunji.titan.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * RcoketMQ数据源
 * 
 * @author gaoxianglong
 */
public class RocketMqDataSource {
	private String rocketTopic;
	private String namesrvAddr;
	private DefaultMqFactory defaultMQFactory;
	private DefaultMQProducer producer;
	private DefaultMQPushConsumer consumer;
	private Logger log = LoggerFactory.getLogger(RocketMqDataSource.class);

	public String getRocketTopic() {
		return rocketTopic;
	}

	public void setRocketTopic(String rocketTopic) {
		this.rocketTopic = rocketTopic;
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	/**
	 * 初始化Producer
	 * 
	 * @author gaoxianglong
	 */
	public void initProducer() {
		if (null != producer) {
			producer.shutdown();
		}
		defaultMQFactory = new DefaultMqFactory();
		defaultMQFactory.setNamesrvAddr(namesrvAddr);
		producer = defaultMQFactory.getDefaultMQProducer("titan-agent");
		try {
			producer.start();
		} catch (MQClientException e) {
			log.error("error", e);
		}
		log.info("RocketMQ Producer初始化完成...");
	}

	/**
	 * 初始化Consumer
	 * 
	 * @author gaoxianglong
	 */
	public void initConsumer() {
		defaultMQFactory = new DefaultMqFactory();
		defaultMQFactory.setNamesrvAddr(namesrvAddr);
		consumer = defaultMQFactory.getDefaultMQPushConsumer("titan-datacollect-" + rocketTopic);
		/* 设置MQ的工作线程数 */
		consumer.setConsumeThreadMax(100);
		consumer.setConsumeThreadMin(50);
		consumer.setMessageModel(MessageModel.CLUSTERING);
	}

	public DefaultMQProducer getProducer() {
		return producer;
	}

	public DefaultMQPushConsumer getConsumer() {
		return consumer;
	}
}