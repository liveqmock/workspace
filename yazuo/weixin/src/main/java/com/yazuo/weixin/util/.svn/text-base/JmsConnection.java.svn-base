package com.yazuo.weixin.util;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsConnection {
	private static Logger logger= LoggerFactory.getLogger(JmsConnection.class);
	private static ConnectionFactory connectionFactory = null;

	
	public static Connection getConnection(String url,boolean isSerializable){
		if(connectionFactory==null){
			connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD,
					url);
		}
		Connection connection=null;
		try {
			connection = connectionFactory.createConnection();
			((ActiveMQConnection) connection)
					.setObjectMessageSerializationDefered(isSerializable);
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("初始化Active MQ失败，请检查Active MQ是否已经启动！", e);
		}
		return connection;
	}
	
	public static boolean testActiveMQ(String url,boolean isSerializable) throws JMSException{
		Connection conn=getConnection(url,isSerializable);
		if(conn!=null){
			return true;
		}
		else{
			return false;
		}
	}
}
