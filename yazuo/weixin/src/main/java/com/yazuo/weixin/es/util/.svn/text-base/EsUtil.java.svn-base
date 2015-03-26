package com.yazuo.weixin.es.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weibo4j.org.json.JSONObject;

import com.yazuo.weixin.es.vo.EsRequestVO;

public class EsUtil {
	private static final Log log = LogFactory.getLog("weixin");
	public static JSONObject doEsRequest(EsRequestVO esRequestVO) {
		try {
			String requestCommand = esRequestVO.getUrl();
			log.info("发送请求：" + requestCommand+"\n 发送内容："+esRequestVO.getRequestJson());
			long startTime = System.currentTimeMillis();
			URL url = new URL(requestCommand);
			
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			out.write(esRequestVO.getRequestJson());
			out.flush();
			out.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line = null;
			StringBuffer content = new StringBuffer();
			while ((line = in.readLine()) != null) {// line为返回值，这就可以判断是否成功、
				content.append(line);
			}
			in.close();
			in = null;
			url = null;
			log.info("返回内容:"+content);
			log.info("耗时::" + (System.currentTimeMillis() - startTime) + "毫秒");
			JSONObject json = new JSONObject(content.toString());
			return json;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
