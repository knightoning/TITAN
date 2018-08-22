package com.yunji.titan.test.agent.interpreter;

import org.junit.Test;

import com.yunji.titan.agent.interpreter.param.AbstractExpression;
import com.yunji.titan.agent.interpreter.param.HeaderExpression;
import com.yunji.titan.agent.interpreter.param.ParamContext;
import com.yunji.titan.agent.interpreter.param.ParamExpression;

import junit.framework.Assert;

public class ExpressionTest {
	public @Test void testA() {
		AbstractExpression headerExpression = new HeaderExpression();
		AbstractExpression paramExpression = new ParamExpression();

		ParamContext context = new ParamContext();
		context.setParams(
				"?fullcoudivonId=115&shodivId=123&adivdivCont=0&isNewVersion=1%%%{'header':{'Cookie':'ticket_123','testHeader':'test_321'}}");
		Assert.assertEquals("?fullcoudivonId=115&shodivId=123&adivdivCont=0&isNewVersion=1",
				paramExpression.get(context));
		Assert.assertEquals("{'header':{'Cookie':'ticket_123','testHeader':'test_321'}}",
				headerExpression.get(context));

		context.setParams("?fullcoudivonId=115&shodivId=123&adivdivCont=0&isNewVersion=1");
		Assert.assertEquals("?fullcoudivonId=115&shodivId=123&adivdivCont=0&isNewVersion=1",
				paramExpression.get(context));
		Assert.assertEquals(null, headerExpression.get(context));

		context.setParams("{'header':{'Cookie':'ticket_123','testHeader':'test_321'}}");
		Assert.assertEquals(null, paramExpression.get(context));
		Assert.assertEquals("{'header':{'Cookie':'ticket_123','testHeader':'test_321'}}",
				headerExpression.get(context));

		context.setParams(null);
		Assert.assertEquals(null, paramExpression.get(context));
		Assert.assertEquals(null, headerExpression.get(context));
	}
}
