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
package com.yunji.titan.manager.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @desc
 *
 * @author liuliang
 *
 */
@Component
public class EmailSender {

	/**
	 * 发送者账号
	 */
	@Value("${email.sender.name}") private String emailSenderName;
	/**
	 * 发送者密码
	 */
	@Value("${email.sender.password}") private String emailSenderPassword;
	/**
	 * host
	 */
	private final static String MAIL_SMTP_HOST = "smtp.sina.com";
	/**
	 * port
	 */
	private final static int MAIL_SMTP_PORT = 25;
	/**
	 * @desc 发送html格式邮件
	 *
	 * @author liuliang
	 *
	 * @param mailSubject 邮件主题
	 * @param mailContent 邮件内容
	 * @param receiveAddress 接收人(多个收件人用因为','隔开)
	 * @return boolean
	 */
	public boolean sendHtml(String mailSubject, String mailContent, String receiveAddress) {
		return sendHtml(mailSubject, mailContent, receiveAddress, null);
	}
	
	/**
	 * @desc 发送html格式邮件
	 *
	 * @author liuliang
	 *
	 * @param mailSubject 邮件主题
	 * @param mailContent 邮件内容
	 * @param receiveAddress 接收人 (多个收件人用因为','隔开)
	 * @param ccReceiveAddress 抄送人 (多个收件人用因为','隔开)
	 * @return boolean
	 */
	public boolean sendHtml(String mailSubject, String mailContent, String receiveAddress, String ccReceiveAddress) {
		boolean flag = true;
		try {
			MimeMessage message = initialMessage();
			// 设置收件人
			String[] receiveAddressArray = receiveAddress.split(",");
			//群发
			int len = receiveAddressArray.length;
			Address[] to = new InternetAddress[len];
			for(int i=0; i<len; i++){
				to[i] = new InternetAddress(receiveAddressArray[i]);
			}
			message.setRecipients(RecipientType.TO, to);
			// 设置抄送人，如果有
			if (ccReceiveAddress != null && !ccReceiveAddress.trim().equals("")) { 
				String[] ccReceiveAddressArray = ccReceiveAddress.split(",");
				int cclen = ccReceiveAddressArray.length;
				Address[] ccTo = new InternetAddress[cclen];
				for(int i=0; i<cclen; i++){
					ccTo[i] = new InternetAddress(ccReceiveAddressArray[i]);
				}
				message.setRecipients(RecipientType.CC, ccTo);
			}
			// 设置邮件标题
			message.setSubject(mailSubject);
			//邮件时间
			message.setSentDate(new Date());
			BodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(mailContent, "text/html;charset=utf-8");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			message.setContent(mp);
			// 发送邮件
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
			flag = false;
		} catch (MessagingException e) {
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
	}

	/**
	 * @desc 初始化邮件通用设置
	 *
	 * @author liuliang
	 *
	 * @return MimeMessage
	 * @throws MessagingException
	 */
	private MimeMessage initialMessage() throws MessagingException{
		// 配置发送邮件的环境属性
		final Properties props = new Properties();
		// 表示SMTP发送邮件，需要进行身份验证
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", MAIL_SMTP_HOST);
		props.put("mail.smtp.port", MAIL_SMTP_PORT);
		// 发件人的账号
		props.put("mail.user", emailSenderName);
		// 访问SMTP服务时需要提供的密码
		props.put("mail.password", emailSenderPassword);
		// 构建授权信息，用于进行SMTP进行身份验证
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				//用户名、密码
				String userName = props.getProperty("mail.user");
				String password = props.getProperty("mail.password");
				return new PasswordAuthentication(userName, password);
			}
		};
		// 使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(props, authenticator);
		// 创建邮件消息
		MimeMessage message = new MimeMessage(mailSession);
		// 设置发件人
		InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
		message.setFrom(form);
		return message;
	}
	
}
