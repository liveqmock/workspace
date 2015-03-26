package com.yazuo.weixin.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpClientCommonSSL {

	private static final Log log = LogFactory.getLog("weixin");

	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * 调用其他服务[post]接口
	 * 
	 */
	public static String commonPostStream(String url, String json) {

		String result = "";

		try {
			SSLContext sc = SSLContext.getInstance("TLSv1");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console
					.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setConnectTimeout(8000); // 用不超时，除非服务端主动断开连接
			conn.setReadTimeout(10000); // 读取数据超时时间，用不超时，除非服务器主动断开连接
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			//System.out.println(json);
			out.write(json.getBytes("UTF-8"));
			out.flush();
			out.close(); // flush and close
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));// 设置编码,否则中文乱码
			StringBuffer sb = new StringBuffer();
			while ((result = reader.readLine()) != null) {
				sb.append(result);
			}
			// System.out.println(sb.toString());
			reader.close();
			conn.disconnect();
			return sb.toString();

		} catch (Exception e) {
			log.error("连接错误", e);
		}
		return result;
	}

}
