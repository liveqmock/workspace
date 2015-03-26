package com.yazuo.weixin.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class CreateButton {

	Logger log = Logger.getLogger(this.getClass());

	public String create(String json, String access_token) throws Exception {
		log.info("access_token==" + access_token);

		URL postUrl = new URL(
				"https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
						+ access_token);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type",
				"text/html; charset=UTF-8");
		connection.connect();
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());
		System.out.println(json);
		out.write(json.getBytes("UTF-8"));
		out.flush();
		out.close(); // flush and close
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));// 设置编码,否则中文乱码
		String result = "";
		StringBuffer sb = new StringBuffer();
		while ((result = reader.readLine()) != null) {
			sb.append(result);
		}
		System.out.println(sb.toString());
		reader.close();
		connection.disconnect();
		return sb.toString();
	}
}
