package com.yazuo.weixin.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yazuo.weixin.vo.Message;

public class XmlUtil {

	static Document document = null;
	static Element rootElm = null;

	public XmlUtil() {
		document = DocumentHelper.createDocument();
		rootElm = document.addElement("xml");// 创建根节点
	}

	public XmlUtil(String xmlString) {
		try {
			document = DocumentHelper.parseText(xmlString);
			rootElm = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public String getXmlString() {
		return document.asXML();
	}

	public Element setElementText(String elementName, String elementText) {
		Element e = rootElm.addElement(elementName);
		e.setText(elementText);
		return e;
	}

	public Element setElementCData(String elementName, String elementText) {
		Element e = rootElm.addElement(elementName);
		e.addCDATA(elementText);
		return e;
	}

	public String getElementText(String elementName) {
		return rootElm.elementText(elementName);
	}

	public static Message convertString2Message(String xmlString, Logger log) {
		Message message = new Message();
		Class<? extends Message> messageClass = message.getClass();
		Document document = null;
		Element rootElm = null;
		try {
			document = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
		}
		rootElm = document.getRootElement();

		for (Iterator it = rootElm.elementIterator(); it.hasNext();) {
			Element e = (Element) it.next();
			Method m = null;
			if (e.getName().equals("Location_X")
					|| e.getName().equals("Location_Y")) {
				try {
					m = messageClass.getDeclaredMethod("set" + e.getName(),
							Double.class);
					m.invoke(message, Double.valueOf(e.getText()));
				} catch (NumberFormatException e1) {
					log.error(e1.getMessage());
				} catch (SecurityException e1) {
					log.error(e1.getMessage());
				} catch (IllegalArgumentException e1) {
					log.error(e1.getMessage());
				} catch (NoSuchMethodException e1) {
					log.error(e1.getMessage());
				} catch (IllegalAccessException e1) {
					log.error(e1.getMessage());
				} catch (InvocationTargetException e1) {
					log.error(e1.getMessage());
				}
			} else {
				try {
					m = messageClass.getDeclaredMethod("set" + e.getName(),
							String.class);
					m.invoke(message, e.getText());
				} catch (SecurityException e1) {
					log.error(e1.getMessage());
				} catch (IllegalArgumentException e1) {
					log.error(e1.getMessage());
				} catch (NoSuchMethodException e1) {
					log.error(e1.getMessage());
				} catch (IllegalAccessException e1) {
					log.error(e1.getMessage());
				} catch (InvocationTargetException e1) {
					log.error(e1.getMessage());
				}
			}

		}

		return message;
	}

	public static String convertMessage2String(Message messageOut) {
		if (messageOut.getMsgType().equals(Message.MSG_TYPE_TEXT)) {
			return getStringFromTextMSG(messageOut);
		} else if (messageOut.getMsgType().equals(Message.MSG_TYPE_NEWS)) {
			return getStringFromNewsMSG(messageOut);
		} else if (messageOut.getMsgType().equals(Message.MSG_TYPE_EVENT)) {
			return getStringFromNewsMSG(messageOut);
		} else if (messageOut.getMsgType().equals(Message.MSG_TYPE_LOCATION)) {
			return getStringFromLocationMSG(messageOut);
		}
		return null;
	}

	public static String getStringFromTextMSG(Message messageOut) {
		if (messageOut.getContent().equals("null")) {
			return "什么都不回复";
		}
		document = DocumentHelper.createDocument();
		rootElm = document.addElement("xml");
		rootElm.addElement("ToUserName").addCDATA(messageOut.getToUserName());
		rootElm.addElement("FromUserName").addCDATA(
				messageOut.getFromUserName());
		rootElm.addElement("CreateTime").setText(messageOut.getCreateTime());
		rootElm.addElement("MsgType").addCDATA(Message.MSG_TYPE_TEXT);
		rootElm.addElement("Content").addCDATA(messageOut.getContent());
		rootElm.addElement("FuncFlag").setText("0");
		return document.asXML();
	}

	public static String getStringFromEventMSG(Message messageOut) {
		document = DocumentHelper.createDocument();
		rootElm = document.addElement("xml");
		rootElm.addElement("ToUserName").addCDATA(messageOut.getToUserName());
		rootElm.addElement("FromUserName").addCDATA(
				messageOut.getFromUserName());
		rootElm.addElement("Title").addCDATA(messageOut.getTitle());
		rootElm.addElement("CreateTime").setText(messageOut.getCreateTime());
		rootElm.addElement("MsgType").addCDATA(Message.MSG_TYPE_NEWS);
		rootElm.addElement("ArticleCount").setText(
				String.valueOf(messageOut.getArticles().size()));
		Element articlesElement = rootElm.addElement("Articles");
		for (Message m : messageOut.getArticles()) {

			Element e = articlesElement.addElement("item");
			e.addElement("Title").addCDATA(m.getTitle());
			e.addElement("Description").addCDATA(m.getDescription());
			e.addElement("PicUrl").addCDATA(m.getPicUrl());
			e.addElement("Url").addCDATA(m.getUrl());
		}
		rootElm.addElement("FuncFlag").setText("1");
		return document.asXML();
	}

	public static String getStringFromNewsMSG(Message messageOut) {
		document = DocumentHelper.createDocument();
		rootElm = document.addElement("xml");
		rootElm.addElement("ToUserName").addCDATA(messageOut.getToUserName());
		rootElm.addElement("FromUserName").addCDATA(
				messageOut.getFromUserName());
		rootElm.addElement("CreateTime").setText(messageOut.getCreateTime());
		rootElm.addElement("MsgType").addCDATA(Message.MSG_TYPE_NEWS);
		rootElm.addElement("ArticleCount").setText(
				String.valueOf(messageOut.getArticles().size()));
		Element articlesElement = rootElm.addElement("Articles");
		for (Message m : messageOut.getArticles()) {

			Element e = articlesElement.addElement("item");
			e.addElement("Title").addCDATA(m.getTitle());
			e.addElement("Description").addCDATA(m.getDescription());
			e.addElement("PicUrl").addCDATA(m.getPicUrl());
			e.addElement("Url").addCDATA(m.getUrl());
		}
		rootElm.addElement("FuncFlag").setText("1");
		return document.asXML();
	}

	public static String getStringFromLocationMSG(Message messageOut) {
		document = DocumentHelper.createDocument();
		rootElm = document.addElement("xml");
		rootElm.addElement("ToUserName").addCDATA(messageOut.getToUserName());
		rootElm.addElement("FromUserName").addCDATA(
				messageOut.getFromUserName());
		rootElm.addElement("CreateTime").setText(messageOut.getCreateTime());
		rootElm.addElement("MsgType").addCDATA(Message.MSG_TYPE_LOCATION);
		rootElm.addElement("Location_X").setText(
				messageOut.getLocation_X().toString());
		rootElm.addElement("Location_Y").setText(
				messageOut.getLocation_Y().toString());
		rootElm.addElement("Scale").setText(messageOut.getScale());
		rootElm.addElement("Label").addCDATA(messageOut.getLabel());
		rootElm.addElement("MsgId").setText(messageOut.getMsgId());
		return document.asXML();
	}
}
