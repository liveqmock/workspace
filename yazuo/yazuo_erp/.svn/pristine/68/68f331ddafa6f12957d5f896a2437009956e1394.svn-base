/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.EmailVO;
import com.yazuo.erp.fes.controller.FesOnlineProcessController;

/**
 * @Description 包含发邮件相关逻辑
 * @author song
 * @date 2014-9-18 下午4:14:55
 */
@SuppressWarnings("unused")
@Component
public class EmailUtil {

	@Resource  private JavaMailSenderImpl mailSender;
	private static final Log LOG = LogFactory.getLog(EmailUtil.class);
	/**
	 * @Description 第十歩项目上线的时候发送邮件方法/发送月报的时候发邮件
	 * 
	 * @param sendAddress 邮件地址中显示的发送者
	 * @param context 邮件的正文
	 * @param withHtml 是否包含html
	 * @param to 收件人地址
	 * @return flag: 如果有大于一个邮件发送成功，返回true
	 */
	//comments this line, solve this issue by try{}catch{}
 	//@Transactional(noRollbackForClassName={"RuntimeException","UnexpectedRollbackException"}) 
	public Boolean sendEmailAfterMerchantOnline(EmailVO emailVO){
		String sendAddress = emailVO.getSendAddress();
		String subject = emailVO.getSubject();
		String context = emailVO.getContext();
		String[] to = emailVO.getTo();
		boolean flag = false;
		MimeMessage message = mailSender.createMimeMessage();
		Session session = mailSender.getSession();
		session.setDebug(true);
		// use the true flag to indicate you need a multipart message
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setFrom(Constant.Email.from, sendAddress);
			LOG.info("Email.from  "+Constant.Email.from);
			// use the true flag to indicate the text included is HTML
//			helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);
			helper.setText(context, true);
			/*
			 页面要显示图片的情况特殊处理
			// let's include the infamous windows Sample file (this time copied to c:/)
			FileSystemResource res = new FileSystemResource(new File("c:/Sample.jpg"));
			helper.addInline("identifier1234", res);
			 */
			mailSender.send(message);
			flag = true; 
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public Boolean testThrowException() throws IOException{
		FileUtils.copyDirectory(new File("dd"), new File(""));
		return true;
	}
}
