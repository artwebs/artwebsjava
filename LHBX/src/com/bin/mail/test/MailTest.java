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
	      mailInfo.setPassword("");//������������   
	      mailInfo.setFromAddress("artwebs@163.com");   
	      mailInfo.setToAddress("artwebs@126.com");   
	      mailInfo.setSubject("�����������");   
	      mailInfo.setContent("������������");   
	         //�������Ҫ�������ʼ�  
	      SimpleMailSender sms = new SimpleMailSender();  
	      boolean tflag=sms.sendTextMail(mailInfo);//���������ʽ   
	      Assert.assertEquals(true, tflag);
	      boolean hflag=sms.sendHtmlMail(mailInfo);//����html��ʽ  
	      Assert.assertEquals(true, hflag);
	}
}
