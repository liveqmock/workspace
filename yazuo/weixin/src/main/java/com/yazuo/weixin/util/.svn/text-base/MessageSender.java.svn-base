package com.yazuo.weixin.util;

import java.io.Serializable;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yazuo.message.model.SendMessage;

public class MessageSender {
	private static Logger logger= LoggerFactory.getLogger(MessageSender.class);
	private Connection connection = null;
	private String destination;
	private Session session;
	private MessageProducer producer;
	/**
	 * 消息生产者
	 * @param connection 消息通道
	 * @param destination 消息目的队列
	 */
	public MessageSender(Connection connection,String destination){
		this.connection=connection;
		this.destination=destination;
		try {
			this.session=this.connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
			producer=this.session.createProducer(session.createQueue(this.destination));
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("消息生产者创建失败！",e);
		}
	}
	
	/**
	 * 消息生产者
	 * @param url Active MQ 服务器url
	 * @param isSerializable 发送的消息是否支持序列化（发送对象消息时，需要设置为true）
	 * @param destination 消息目的队列名称
	 */
	public MessageSender(String url,boolean isSerializable,String destination){
		this.connection=JmsConnection.getConnection(url, isSerializable);
		this.destination=destination;
		try {
			this.session=this.connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
			producer=this.session.createProducer(session.createQueue(this.destination));
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("消息生产者创建失败！",e);
		}
	}
	
	/**
	 * 发送文本消息
	 * @param msg 文本消息
	 */
	public void send(String msg){
		try {
			Message message=this.session.createTextMessage(msg);
			producer.send(message);
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("消息字符串发送失败！",e);
		}
		finally{
			close();
		}
	}
	
	/**
	 * 发送对象消息
	 * @param msgObj SendMessage对象
	 */
	public void send(Object msgObj){
		try {
			Message message=this.session.createObjectMessage((Serializable)msgObj);
			producer.send(message);
			session.commit();
		} catch (JMSException e) {
			logger.error("消息对象发送失败！",e);
		}
		finally{
			close();
		}
	}
	
	/**
	 * 发送Map信息
	 * @param msgMap 信息Map
	 */
	public void send(Map<String,String> msgMap){
		try {
			MapMessage mMap=session.createMapMessage();
			for(String k:msgMap.keySet()){
				mMap.setString(k, msgMap.get(k));
			}
			producer.send(mMap);
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("消息Map发送失败！",e);
		}
		finally{
			close();
		}
	}
	
	private void close(){
		try {
			session.close();
			connection.close();
			connection=null;
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("消息生产者关闭失败！",e);
		}
	}
	
	public void testSend() throws JMSException{
		Message message=this.session.createTextMessage("test");
		producer.send(message);
	}
	
	public static void sendSMS(String content,String phoneNo,String smsAddress,String smsUsername,Integer brandId) throws JMSException {
			MessageSender sender=new MessageSender(smsAddress, true,smsUsername);
			SendMessage smessage=new SendMessage();
			smessage.setContent(content);
			smessage.setLevel(8);
			smessage.setMerchantId(brandId);
			smessage.setMobile(phoneNo);
			smessage.setSource(content);
			sender.send(smessage);
//		}
	}
	
}
