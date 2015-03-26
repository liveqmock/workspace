package com.yazuo.util;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil {
	static Logger log = Logger.getLogger(HttpUtil.class);

	
	public static String httpPostForString(List<NameValuePair> nvps, String url)
			throws Exception {
		StringBuffer logString=new StringBuffer("url=" + url);
		for (NameValuePair nvp : nvps) {
			logString.append("\n" + nvp.getName() + "：" + nvp.getValue());
		}
		log.info(logString.toString());
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			log.info("httpPost调用"+url+"返回\n\nrequestObject="
					+ result);
			return result;
		} else {
			log.error("httpPost调用失败response.getStatusLine().getStatusCode()="
					+ response.getStatusLine().getStatusCode());
			throw new Exception(
					"httpPost调用失败response.getStatusLine().getStatusCode()="
							+ response.getStatusLine().getStatusCode());
		}
	}
	
	public static JSONObject httpPostForJson(List<NameValuePair> nvps, String url)
			throws Exception {
		StringBuffer logString=new StringBuffer("url=" + url);
		for (NameValuePair nvp : nvps) {
			logString.append("\n" + nvp.getName() + "：" + nvp.getValue());
		}
		log.info(logString.toString());
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);
			log.info("httpPost调用"+url+"返回\n\nrequestObject="
					+ requestObject.toString());
			return requestObject;
		} else {
			log.error("httpPost调用失败response.getStatusLine().getStatusCode()="
					+ response.getStatusLine().getStatusCode());
			throw new Exception(
					"httpPost调用失败response.getStatusLine().getStatusCode()="
							+ response.getStatusLine().getStatusCode());
		}
	}
}
