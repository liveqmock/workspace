package com.yazuo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName CommonUtil
 * @Description
 * @author sundongfeng@yazuo.com
 * @date 2014-7-29 下午2:08:38
 * @version 1.0
 */
public class CommonUtil {
	private static final Log log = LogFactory.getLog(CommonUtil.class);

	public static String postMessage(String url) {
		String result = "";
		try {
			// 服务调用
			HttpClient httpclient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
			HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
			String urlpath = url;
			log.info("发送请求：" + urlpath);
			HttpGet httpGet = new HttpGet(urlpath);
			HttpResponse response;
			response = httpclient.execute(httpGet);

			// 结果处理
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				log.info("请求响应结果：" + result);
			}
		} catch (Exception e) {
			log.error("请求服务调用失败.", e);
			log.error(e.getMessage());
		}
		return result;
	}

	public static String getDateBeforeOrAfterDays(String calendarUrl, Date date, Integer days) {
		String result = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String beginTime = format.format(date);
		String endTime = null;
		try {
			// 服务调用
			HttpClient httpclient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
			HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
			String urlpath = calendarUrl + "?date=" + beginTime + "&days=" + days;

			log.info("发送请求：" + urlpath);
			HttpGet httpGet = new HttpGet(urlpath);
			HttpResponse response;
			response = httpclient.execute(httpGet);

			// 结果处理
			if (response.getStatusLine().getStatusCode() == 200) {
				log.info("请求成功");
				result = EntityUtils.toString(response.getEntity());
				JSONObject object = JSONObject.fromObject(result);
				Long time = (Long) object.get("data");
				endTime = format.format(time);

			}
		} catch (Exception e) {
			log.error("请求服务调用失败.", e);
			log.error(e.getMessage());
		}
		return endTime;
	}
}
