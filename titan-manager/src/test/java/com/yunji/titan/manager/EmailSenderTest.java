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
package com.yunji.titan.manager;

import javax.annotation.Resource;

import org.junit.Test;

import com.yunji.titan.manager.test.BaseJunit4Test;
import com.yunji.titan.manager.utils.EmailSender;

/**
 * @desc
 *
 * @author liuliang
 *
 */
public class EmailSenderTest extends BaseJunit4Test{

	@Resource
	private EmailSender emailSender;
	
	@Test
	public void sendEmailTest() {
		System.out.println("-------start------");
		
//		boolean result = EmailUtil.sendHtml("HaHa邮件名", "测试邮件<div>,这里是html内容1111111111<br>222222</div>", "liul@yunjiweidian.com", null);
		boolean result = emailSender.sendHtml("HaHa邮件名", "测试邮件<div>,这里是html内容1111111111<br>222222</div>", "liul@yunjiweidian.com", null);
		System.out.println("result:" + result);
		
		System.out.println("------end-------");
	}
}
