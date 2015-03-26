/**
 * @Description 基于HttpClient的工具类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.util;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

/**
 * @Description 基于HttpClient的工具类
 * @date 2014-8-11 上午10:59:38
 */
public class HttpClientUtil {

	static Log log = LogFactory.getLog(HttpClientUtil.class);

	/**
	 * @Description 执行请求，返回执行结果
	 * @param url
	 *            待请求的URL
	 * @return
	 */
	public static String execRequestAndGetResponse(String url) {
		HttpPost httpPost = new HttpPost(url);
		String resultStr = null;// 响应
		try {
			HttpClient httpClient = new DefaultHttpClient();
			long beginning = System.currentTimeMillis();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
			HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
			long end = System.currentTimeMillis();
			log.info("request：" + url + "，用时:" + (end - beginning) + "ms");
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				resultStr = EntityUtils.toString(httpResponse.getEntity());
				log.info("response：" + resultStr);
			} else {
				log.error("返回状态码不是200，状态码：" + httpResponse.getStatusLine().getStatusCode() + "，URL：" + url);
			}
		} catch (IOException e) {
			log.error("请求错误，请求URL：" + url);
			e.printStackTrace();
			httpPost.abort();
		} finally {
			httpPost.releaseConnection();
		}
		return resultStr;
	}

}
