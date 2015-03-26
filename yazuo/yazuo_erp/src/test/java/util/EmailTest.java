/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.EmailVO;
import com.yazuo.erp.system.dao.SysWhiteListDao;
import com.yazuo.erp.system.service.SysWhiteListService;
import com.yazuo.erp.system.vo.SysProductOperationVO;
import com.yazuo.erp.system.vo.SysWhiteListVO;
import com.yazuo.util.EmailUtil;

/**
 * @author song
 * @date 2014-9-18 下午2:58:22
 */
@SuppressWarnings("unused")
public class EmailTest extends JUnitDaoBaseWithTrans {
	
	@Resource  	private JavaMailSenderImpl mailSender;
	@Resource  	private SimpleMailMessage templateMessage;
	@Resource  	private EmailUtil emailUtil;
	
	@Resource  	private SysWhiteListDao sysWhiteListDao;
	@Resource  	private SysWhiteListService sysWhiteListService;

	private static String host = "mail.yazuo.com";
	private static String from = "webadmin@yazuo.com";
	private static String username = "webadmin@yazuo.com";
	private static String password = "123.yazuo";
	private static String mailTo = "songfuyu@yazuo.com";
	
	@Test 
	@SuppressWarnings("restriction")
	public void testClassLoader(){
		 URL[] urls=sun.misc.Launcher.getBootstrapClassPath().getURLs();
		   for (int i = 0; i < urls.length; i++) {
			   URL url = urls[i];
		     logger.info(url.toExternalForm());
		   }
		   System.out.println(System.getProperty("java.ext.dirs"));
		   ClassLoader extensionClassloader=ClassLoader.getSystemClassLoader().getParent();
		   System.out.println("the parent of extension classloader : "+extensionClassloader.getParent());
		   
	}
	
	@Test 
	public void testConvertVO(){
		String jsonString = this.getJsonString(new SysProductOperationVO());
		System.out.println(jsonString);
	}
	
	/**
	 * 测试 期望： 当email发送失败的时候业务不回滚
	 */
	@Test
	@Rollback(false) 
	public void updateOnlineStatusAndSendEmail() {
		SysWhiteListVO sysWhiteListVO = this.sysWhiteListService.getSysWhiteListById(555);
		sysWhiteListVO.setInsertTime(new Date());
		sysWhiteListService.updateSysWhiteList(sysWhiteListVO);
	}
	/**
	 * test not rollback when throw specific exception
	 * 可以做到
	 */
	@Test
	@Rollback(false)
	@Transactional(noRollbackForClassName={"RuntimeException","Exception", "IOException"}) 
	public void doNotRollbackWhenException(){
		try {
			emailUtil.testThrowException();
			
			logger.info("虽然有异常，但还是执行了");
			SysWhiteListVO sysWhiteListVO = this.sysWhiteListDao.getSysWhiteListById(28);
			sysWhiteListVO.setInsertTime(new Date());
			sysWhiteListDao.updateSysWhiteList(sysWhiteListVO);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 第十歩项目上线的时候发送邮件方法
	 */
	@Test
	public void sendEmailAfterMerchantOnline() {
		String context = "<div class=\"app-inner-l fn-left\"><a class=\"logo-l\" href=\"http://localhost:8080/yazuo_erp/\">雅座ERP</a></div>";
		String[] tos = new String[]{ "mayuliang@yazuo.com"};
		String subject = "O(∩_∩)O哈哈~";
		EmailVO email = new EmailVO();
		email.setSubject(subject);
		email.setSendAddress("admin");
		email.setContext(context);
		email.setTo(tos);
		Boolean sendEmailAfterMerchantOnline = emailUtil.sendEmailAfterMerchantOnline(email);
		logger.info(sendEmailAfterMerchantOnline);
	}
	
	/*********************************************************************************
	 			* 以下是单纯的发送email方法测试  
	/*********************************************************************************
	/**
	 * 用 MimeMessagePreparator 发送email
	 */
	@Test
	public void testMimeMessagePreparator() {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
				mimeMessage.setFrom(new InternetAddress(from));
				mimeMessage.setText("Dear , thank you for placing order. Your order number is ");
			}
		};
		try {
			this.mailSender.send(preparator);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}
	/**
	 * 用 MimeMessageHelper 发送Email
	 */
	@Test
	public void testMimeMessageHelper() throws MessagingException, UnsupportedEncodingException {
//		this.mailSender.setUsername("songfuyu@yazuo.com");
//		this.mailSender.setPassword("我的密码");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(mailTo);
		//邮件由系统转发，显示的是指定的名字
		helper.setFrom("yazuoservice@yazuo.com", "admin"); 
		helper.setText("Thank you for ordering!");
		mailSender.send(message);
	}
	
	/**
	 * Sending with inline resources
	 */
	@Test
	public void testMimeMessageHelper1() throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(mailTo);
		helper.setFrom(from);
		// use the true flag to indicate the text included is HTML
		helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);
		// let's include the infamous windows Sample file (this time copied to c:/)
		FileSystemResource res = new FileSystemResource(new File("c:/Sample.jpg"));
		helper.addInline("identifier1234", res);
		mailSender.send(message);
	}
	/**
	 * Sending attachments 
	 */
	@Test
	public void testMimeMessageHelper2() throws MessagingException {
		
		MimeMessage message = mailSender.createMimeMessage();
		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(mailTo);
		helper.setFrom(from);
		helper.setText("Check out this image!");
		// let's attach the infamous windows Sample file (this time copied to c:/)
		FileSystemResource file = new FileSystemResource(new File("c:/Sample.jpg"));
		helper.addAttachment("Sample.jpg", file); //附件的名字
		mailSender.send(message);
	}
	
}
