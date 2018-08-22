package com.yunji.titan.test.agent;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.springframework.util.StringUtils;
import com.yunji.titan.agent.config.HttpConnectionManager;
import com.yunji.titan.agent.utils.ParamUtils;
import com.yunji.titan.utils.ContentType;
import com.yunji.titan.utils.UrlEncoder;

/**
 * 测试类
 *
 * @author gaoxianglong
 */
public class GetRequestPerformanceTestTest {
	public @Test void testGetRequest() {
		HttpConnectionManager manager = new HttpConnectionManager();
		manager.init();
		String url = "http://localhost:8080/web-test/user/login.do";
		if (!StringUtils.isEmpty(url)) {
			HttpEntity entity = null;
			CloseableHttpResponse httpResponse = null;
			String headers = null;
			try {
				CloseableHttpClient httpClient = manager.getHttpClient();
				if (null != httpClient) {
					HttpPost request = new HttpPost(UrlEncoder.encode(url));
					entity = new StringEntity("name=JohnGao&age=18", "UTF-8");
					/* 设置请求头 */
					ParamUtils.setHeader(request, headers, ContentType.APPLICATION_X_WWW_FORM_URLENCODED, "UTF-8");
					request.setEntity(entity);
					httpResponse = httpClient.execute(request);
					entity = httpResponse.getEntity();
					System.out.println(entity);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != entity) {
						entity.getContent().close();
					}
					if (null != httpResponse) {
						httpResponse.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}