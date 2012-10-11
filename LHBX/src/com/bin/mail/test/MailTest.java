package com.bin.mail.test;

import com.bin.mail.MailSenderInfo;
import com.bin.mail.SimpleMailSender;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MailTest extends TestCase {
	public void testSend()
	{
		 MailSenderInfo mailInfo = new MailSenderInfo();   
	      mailInfo.setMailServerHost("smtp.163.com");   
	      mailInfo.setMailServerPort("25");   
	      mailInfo.setValidate(true);   
	      mailInfo.setUserName("test@163.com");   
	      mailInfo.setPassword("");//您的邮箱密码   
	      mailInfo.setFromAddress("artwebs@163.com");   
	      mailInfo.setToAddress("artwebs@126.com");   
	      mailInfo.setSubject("设置邮箱标题");   
	      mailInfo.setContent("设置邮箱内容");   
	         //这个类主要来发送邮件  
	      SimpleMailSender sms = new SimpleMailSender();  
	      boolean tflag=sms.sendTextMail(mailInfo);//发送文体格式   
	      Assert.assertEquals(true, tflag);
	      boolean hflag=sms.sendHtmlMail(mailInfo);//发送html格式  
	      Assert.assertEquals(true, hflag);
	}
}
