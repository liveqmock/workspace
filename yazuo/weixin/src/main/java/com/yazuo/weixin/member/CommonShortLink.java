package com.yazuo.weixin.member;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

public class CommonShortLink {
	private static final Log logger = LogFactory.getLog("weixin");
	
	private static String getHttpClientShortUrl(String url_long){
		String shortUrl ="";
		//获得httpclient对象
		PoolingClientConnectionManager connectionManager=new PoolingClientConnectionManager();;
		HttpClient httpclient = new DefaultHttpClient(connectionManager);
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,6000);//请求超时设置时间
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,6000);//读取超时设置时间
		 
		//获得HttpGet对象
		HttpGet httpGet = null;
		httpGet = new HttpGet(url_long);
		//发送请求
		HttpResponse response;
		try {
			response = httpclient.execute(httpGet);
			shortUrl = getShortUrl(response);
			logger.info("第一次请求：短链接shortUrl="+shortUrl);
//			mFeilds.put("shortUrl", shortUrl);
		} catch (Exception e) {
			//e.printStackTrace();
			//网址放全称不放简称
//			mFeilds.put("shortUrl", url_long_old);
//			mFeilds.put("shortUrl", "");
			//20140313 zp 如果报错，则第二次请求
			logger.info("第二次请求：请求到数据，则再次发起请求--开始");
			try {
				response = httpclient.execute(httpGet);
				String shortUrl2 = getShortUrl(response);
				shortUrl=shortUrl2;
//				mFeilds.put("shortUrl", shortUrl2);
				logger.info("第二次请求：短链接shortUrl2="+shortUrl2);
			}catch (Exception e2) {
//				mFeilds.put("shortUrl", "");
				logger.info("第二次请求：捕获异常网址放全称不放简称,捕获异常1="+e2.getMessage());
			} 
			logger.info("捕获异常网址放全称不放简称,捕获异常="+e.getMessage());
		} 
		return shortUrl.toString();
	}
	
	private static String getShortUrl(HttpResponse response) throws Exception{
		StringBuffer shortUrl = new StringBuffer();
		shortUrl.append(" ");
		if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
			String result=EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);
			JSONArray ja = new JSONArray();
			ja = requestObject.getJSONArray("urls");
			if(ja!=null&&ja.size()>0){
				requestObject = JSONObject.fromObject(ja.get(0));
				if(requestObject.has("url_short")&&requestObject.get("url_short")!=null){
//					mFeilds.put("shortUrl", requestObject.get("url_short"));//短链接
					shortUrl.append(requestObject.get("url_short"));
				}else{
					//url_short中没有值,网址放全称不放简称
//					mFeilds.put("shortUrl", url_long);
//					mFeilds.put("shortUrl", "");
					shortUrl.append("");
				}
			}else{
				//urls中没有记录,说明没有生成短链接,网址放全称不放简称
//				mFeilds.put("shortUrl", url_long_old);
//				mFeilds.put("shortUrl", "");
				shortUrl.append("");
				logger.info("urls中没有记录,说明没有生成短链接,网址放全称不放简称");
			}
		}else{
			//如果状态码不等于200,不是正常返回,网址放全称不放简称
//			mFeilds.put("shortUrl", url_long_old);
//			mFeilds.put("shortUrl", "");
			shortUrl.append("");
			logger.info("状态码不等于200,不是正常返回,网址放全称不放简称");
		}
		shortUrl.append(" ");//2014-07-29 zp 加空格
		return shortUrl.toString();
	}
	
	
	/**url是需要转化成短链的url*/
	public static String getShortLink(String url) {
		ResourceBundle bundle = ResourceBundle.getBundle("image-config");
		String urlLong = bundle.getString("url_long"); // 生成短连接的接口
		// 将参数url转码
		String tempUrl = "";
		try {
			tempUrl = URLEncoder.encode(url,"UTF-8");//多参数时必须用URLEncoder转码否则weibo api不识别多参数
			logger.info("URLEncoder编码后的值："+tempUrl);
		} catch (UnsupportedEncodingException e) {
			logger.error("shortUrl通过URLEncoder转码错误");
		}
		StringBuilder sb = new StringBuilder(urlLong); // 调用weibo生成短链接
		sb.append(tempUrl); // 参数
		return getHttpClientShortUrl(sb.toString());
	}
	
	
	public static void main (String [] args) {
		String temp_url = "http://124.42.38.70/yazuo-weixin/merchantWifi/pushPage.do?brandId=15&weixinId=ooj7DjqAPLQVv6SXdYkdo4Do1MVU";
		try {
			temp_url =  URLEncoder.encode(temp_url,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String url = "http://api.weibo.com/2/short_url/shorten.json?source=1751316174&url_long="+temp_url;
		CommonShortLink shortLink = new CommonShortLink();
		String shortUrl = shortLink.getHttpClientShortUrl(url);
		System.out.println("---短链接--------"+shortUrl);
//		try {
//			shortUrl = URLEncoder.encode(shortUrl,"UTF-8");//多参数时必须用URLEncoder转码否则api不识别多参数
//			System.out.println("---短链接--------"+shortUrl);
//		} catch (UnsupportedEncodingException e) {
//			logger.error("url_long_temp通过URLEncoder转码错误");
//			//应该做处理,短链生成失败的时候应该程序继续发短信还是说就不再发送短信,下面有处理就是调用api没返回短链时写全连接
//		}

	}
}
