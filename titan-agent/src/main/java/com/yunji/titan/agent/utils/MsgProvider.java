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

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.alibaba.rocketmq.common.message.Message;
import com.yunji.titan.utils.config.RocketMqDataSource;

/**
 * 消息生产者
 * 
 * @author gaoxianglong
 */
@Component
public class MsgProvider {
	@Resource
	private RocketMqDataSource rocketMQDataSource;
	private Logger log = LoggerFactory.getLogger(MsgProvider.class);

	/**
	 * 将压测结果写入消息队列
	 * 
	 * @author gaoxianglong
	 */
	public void sendMsg(String result) {
		if (StringUtils.isEmpty(result)) {
			return;
		}
		try {
			String msgId = rocketMQDataSource.getProducer()
					.send(new Message(rocketMQDataSource.getRocketTopic(), "*", "uploadKey", result.getBytes()))
					.getMsgId();
			log.info(msgId);
		} catch (Exception e) {
			log.error("error", e);
		}
	}
}
