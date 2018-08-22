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
package com.yunji.titan.agent;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.yunji.titan.agent.config.HttpConnectionManager;

/**
 * 定时清理空闲和过期的http连接
 * 
 * @author gaoxianglong
 */
@Service
public class IdleConnectionEvictor {
	@Resource
	private HttpConnectionManager httpConnectionManager;
	private Logger log = LoggerFactory.getLogger(IdleConnectionEvictor.class);

	@PostConstruct
	public void init() {
		/* 定时清理所有空闲的http连接 */
		httpConnectionManager.getPoolConnManager().closeIdleConnections(5, TimeUnit.SECONDS);
		log.info("定时清理空闲和过期的http连接任务启动...");
	}

	@Scheduled(cron = "0/5 * * * * ?")
	public void connectionEvictor() {
		/* 定时关闭所有过期的http连接 */
		httpConnectionManager.getPoolConnManager().closeExpiredConnections();
	}
}