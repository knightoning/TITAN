package com.yunji.titan.test.agent.utils;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.yunji.titan.agent.utils.PropertyResolver;
import com.yunji.titan.utils.RequestType;

public class PropertyResolverTest {
	public @Test void testResolver() {
		String inParam = "?name=JohnGao&${password}&${token}&${age}&test=123&${sex}";
		String outParam = "{'password':123456,'token':'zaxswed'}";
		System.out.println(PropertyResolver.resolver(inParam, outParam, RequestType.GET));

		inParam = "?name=JohnGao&test=123";
		outParam = "{'password':123456,'token':'zaxswed'}";
		System.out.println(PropertyResolver.resolver(inParam, outParam, RequestType.GET));

		inParam = null;
		outParam = "{'password':123456,'token':'zaxswed'}";
		System.out.println(PropertyResolver.resolver(inParam, outParam, RequestType.GET));

		inParam = "?name=JohnGao&test=123";
		outParam = null;
		System.out.println(PropertyResolver.resolver(inParam, outParam, RequestType.GET));

		inParam = "{'name':'JohnGao',${password},${token},${sex}}";
		outParam = "{'password':{'k':'v'},'token':'zaxswed'}";
		System.out.println(PropertyResolver.resolver(inParam, outParam, RequestType.POST));
		JSONObject jsonObj = JSONObject.parseObject(PropertyResolver.resolver(inParam, outParam, RequestType.POST));
		System.out.println(jsonObj.get("password"));

		long startTime = System.currentTimeMillis();
		int num = 1000000;
		for (int i = 0; i < num; i++) {
			PropertyResolver.resolver(inParam, outParam, RequestType.POST);
		}
		System.out.println("TPS:" + (num / ((System.currentTimeMillis() - startTime) / 1000)) + "/s");
	}
}
