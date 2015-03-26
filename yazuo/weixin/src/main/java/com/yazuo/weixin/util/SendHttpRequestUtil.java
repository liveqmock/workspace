package com.yazuo.weixin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SendHttpRequestUtil {
	public static String sendHttpRequest(String spec) {
		URL url = null;
		URLConnection urlConnection = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		String result = "";
		try {
			url = new URL(spec);
			urlConnection = url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setConnectTimeout(8000); // 用不超时，除非服务端主动断开连接
			urlConnection.setReadTimeout(10000); // 读取数据超时时间，用不超时，除非服务器主动断开连接
			inputStream = urlConnection.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			String currentLine = "";

			while ((currentLine = bufferedReader.readLine()) != null) {
				result += currentLine;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				if (inputStream != null)
					inputStream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
