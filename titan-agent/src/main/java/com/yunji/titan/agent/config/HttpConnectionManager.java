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
package com.yunji.titan.agent.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP/HTTPS连接池管理类
 * 
 * @author gaoxianglong
 */
public class HttpConnectionManager {
	private PoolingHttpClientConnectionManager poolConnManager = null;
	private CloseableHttpClient httpClient;
	private RequestConfig requestConfig;
	/**
	 * 最大连接数
	 */
	private int maxTotal = 2000;
	/**
	 * 最大路由
	 */
	private int defaultMaxPerRoute = 2000;
	private int socketTimeout = 5000;
	private int connectTimeout = 5000;
	private int connectionRequestTimeout = 5000;
	private Logger log = LoggerFactory.getLogger(HttpConnectionManager.class);

	public void init() {
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			/* 配置同时支持 HTTP 和 HTPPS */
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
			/* 初始化连接管理器 */
			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			poolConnManager.setMaxTotal(maxTotal);
			poolConnManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
			requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
					.setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
			httpClient = getConnection();
			log.info("HttpConnectionManager初始化完成...");
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	private CloseableHttpClient getConnection() {
		CloseableHttpClient httpClient = HttpClients.custom()
				/* 设置连接池管理 */
				.setConnectionManager(poolConnManager)
				/* 设置请求配置 */
				.setDefaultRequestConfig(requestConfig)
				/* 设置重试次数 */
				.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
		if (poolConnManager != null && poolConnManager.getTotalStats() != null) {
			log.info("now client pool " + poolConnManager.getTotalStats().toString());
		}
		return httpClient;
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}

	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	public PoolingHttpClientConnectionManager getPoolConnManager() {
		return poolConnManager;
	}
}