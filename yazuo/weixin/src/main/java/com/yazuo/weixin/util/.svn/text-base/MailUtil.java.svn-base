package com.yazuo.weixin.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
* @ClassName MailUtil
* @Description 发邮件工具类
* @author sundongfeng@yazuo.com
* @date 2014-6-18 上午8:51:07
* @version 1.0
*/
public class MailUtil {
	private static String host = "mail.yazuo.com";
	private static String from = "webadmin@yazuo.com";
	private static String username = "webadmin@yazuo.com";
	private static String password = "123.yazuo";

	/**
	 * 发送文本消息
	 * 
	 * @param emailTo
	 * @param title
	 * @param textContent
	 */
	public static void sendMail(List<String> mailTos, String title, String textContent) {

		Properties props = new Properties();
		// Setup mail server
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		// Get session
		Session session = Session.getDefaultInstance(props);
		// watch the mail commands go by to the mail server
		session.setDebug(true);
		// Define message
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			List<InternetAddress> addresses = new ArrayList();
			for (String mailTo : mailTos) {
				addresses.add(new InternetAddress(mailTo));
			}
			message.addRecipients(Message.RecipientType.TO,
					addresses.toArray(new InternetAddress[addresses.size()]));
			message.setSubject(title);
			Multipart mp = new MimeMultipart();
			message.setSubject(title);

			MimeBodyPart mbpText = new MimeBodyPart();
			mbpText.setText(textContent);
			mp.addBodyPart(mbpText);
			message.setContent(mp);
			message.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送文本消息
	 * 
	 * @param emailTo
	 * @param title
	 * @param textContent
	 */
	public static void sendMailOneByOne(List<String> mailTos, String title,
			String textContent) {

		Properties props = new Properties();
		// Setup mail server
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		// Get session
		Session session = Session.getDefaultInstance(props);
		// watch the mail commands go by to the mail server
		session.setDebug(true);
		try {

			for (String mailTo : mailTos) {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(mailTo));
				message.setSubject(title);
				Multipart mp = new MimeMultipart();
				MimeBodyPart mbpText = new MimeBodyPart();
				mbpText.setText(textContent);
				mp.addBodyPart(mbpText);
				message.setContent(mp);
				message.saveChanges();
				Transport transport = session.getTransport("smtp");
				transport.connect(host, username, password);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			}

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void sendFileMail(List<String> mailTos, String title, String content,
			File file) {
		Properties props = new Properties();
		// Setup mail server
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		// Get session
		Session session = Session.getDefaultInstance(props);
		// watch the mail commands go by to the mail server
		session.setDebug(true);
		// Define message
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			List<InternetAddress> addresses = new ArrayList();
			for (String mailTo : mailTos) {
				addresses.add(new InternetAddress(mailTo));
			}
			message.addRecipients(Message.RecipientType.TO,
					addresses.toArray(new InternetAddress[addresses.size()]));
			message.setSubject(title);
			Multipart mp = new MimeMultipart();

			// 文本
			MimeBodyPart mbpText = new MimeBodyPart();
			mbpText.setText(content);
			mp.addBodyPart(mbpText);

			// 附件
			MimeBodyPart mbpFile = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(file);
			mbpFile.setDataHandler(new DataHandler(fds));
			mbpFile.setFileName(MimeUtility.encodeText(file.getName()));
			mp.addBodyPart(mbpFile);

			message.setContent(mp);
			message.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	public static void sendFileMailOneByOne(List<String> mailTos, String title,
			String content, File file) {
		String host = "mail.yazuo.com";
		String from = "webadmin@yazuo.com";
		String username = "webadmin@yazuo.com";
		String password = "123.yazuo";
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		try {
			for (String mailTo : mailTos) {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(mailTo));
				message.setSubject(title);
				Multipart mp = new MimeMultipart();
				MimeBodyPart mbpText = new MimeBodyPart();
				mbpText.setText(content);
				mp.addBodyPart(mbpText);
				MimeBodyPart mbpFile = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(file);
				mbpFile.setDataHandler(new DataHandler(fds));
				mbpFile.setFileName(MimeUtility.encodeText(file.getName()));
				mp.addBodyPart(mbpFile);

				message.setContent(mp);
				message.saveChanges();
				Transport transport = session.getTransport("smtp");
				transport.connect(host, username, password);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
//		new MailService().sendMail(new String[] { "libin@yazuo.com",
//				"younglibin@163.com" }, "hello", "hello world\r\n测试换行");
//		new MailService().sendMailOneByOne(new String[] { "libin@yazuo.com",
//				"younglibin@163.com" }, "hello", "hello world libin\r\n测试换行");
		// new MailService().sendFileMail(new String[] { "libin@yazuo.com",
		// "younglibin@163.com" }, "test", "test001", new File(
		// "F:/医疗报销/易才医疗报销申请单.doc"));
		// new MailService().sendFileMailOneByOne(new String[] {
		// "libin@yazuo.com", "younglibin@163.com" }, "test2", "test002",
		// new File("F:/医疗报销/易才医疗报销申请单-模板.doc"));
	}

}
