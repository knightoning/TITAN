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
package com.yunji.titan.agent.stresstest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yunji.titan.agent.bean.bo.OutParamBO;
import com.yunji.titan.utils.ContentType;

/**
 * 压测引擎核心接口
 * 
 * @author gaoxianglong
 */
public interface Stresstest {
	public String CHAR_SET = "UTF-8";
	public Logger log = LoggerFactory.getLogger(Stresstest.class);

	/**
	 * 执行压测
	 * 
	 * @author gaoxianglong
	 * 
	 * @param url
	 *            需要进行压测的URL
	 * 
	 * @param outParam
	 *            上一个接口的出参
	 * 
	 * @param param
	 *            表单参数或者JSON参数
	 * 
	 * @param contentType
	 *            内容类型
	 * 
	 * @param charset
	 *            编码格式
	 * 
	 * @exception Exception,IOException
	 * 
	 * @return OutParamBO 压测结果,返回业务code为0时则成功
	 */
	public OutParamBO runStresstest(String url, String outParam, String param, ContentType contentType, String charset);

	/**
	 * 获取执行结果
	 * 
	 * @author gaoxianglong
	 * 
	 * @param httpResponse
	 * 
	 * @param entity
	 * 
	 * @exception Exception
	 * 
	 * @return OutParamBean
	 */
	public default OutParamBO getResult(CloseableHttpResponse httpResponse, HttpEntity entity) {
		OutParamBO outParamBO = new OutParamBO();
		int httpCode = 200;
		if (httpCode == httpResponse.getStatusLine().getStatusCode()) {
			outParamBO.setErrorCode(httpCode);
			try {
				JSONObject obj = JSONObject.parseObject(EntityUtils.toString(entity, CHAR_SET));
				Object errorCode = obj.get("errorCode");
				Object data = obj.get("data");
				if (null != errorCode) {
					outParamBO.setErrorCode(Integer.parseInt(errorCode.toString()));
				}
				if (null != data) {
					outParamBO.setData(data.toString());
				}
			} catch (Exception e) {
				log.debug("返回的并非是JSON");
			}
		}
		return outParamBO;
	}
}