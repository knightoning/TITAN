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

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

/**
 * 可以从 DefaultMQFactory中获取producter和comsunmer实例
 * 
 * @author gaoxianglong
 */
public class DefaultMqFactory {
	private String namesrvAddr;

	/**
	 * 获取DefaultMQProducer实例
	 * 
	 * @author gaoxianglong
	 * 
	 * @param producerGroup
	 * 
	 * @return DefaultMQProducer
	 */
	public DefaultMQProducer getDefaultMQProducer(String producerGroup) {
		DefaultMQProducer defaultMQProducer = new DefaultMQProducer(producerGroup);
		defaultMQProducer.setNamesrvAddr(namesrvAddr);
		return defaultMQProducer;
	}

	/**
	 * 获取DefaultMQPushConsumer实例
	 * 
	 * @author gaoxianglong
	 * 
	 * @param producerGroup
	 * 
	 * @return DefaultMQProducer
	 */
	public DefaultMQPushConsumer getDefaultMQPushConsumer(String producerGroup) {
		DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(producerGroup);
		defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
		return defaultMQPushConsumer;
	}

	/**
	 * 获取DefaultMQPullConsumer实例
	 * 
	 * @author gaoxianglong
	 * 
	 * @param producerGroup
	 * 
	 * @return DefaultMQProducer
	 */
	public DefaultMQPullConsumer getDefaultMQPullConsumer(String producerGroup) {
		DefaultMQPullConsumer defaultMQPullConsumer = new DefaultMQPullConsumer(producerGroup);
		defaultMQPullConsumer.setNamesrvAddr(namesrvAddr);
		return defaultMQPullConsumer;
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}
}