package com.yazuo.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class UrlUtil {
	public static String  getShotUrl(String url) {
		try {
			url = URLEncoder.encode(url,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String url_long = "http://api.weibo.com/2/short_url/shorten.json?" +"source=1751316174&url_long="+url;//短链接获取
		//调用方式一开始
	    HttpClient httpclient = new DefaultHttpClient();
	    //获得HttpGet对象
        HttpGet httpGet = new HttpGet(url_long);
        //发送请求
        HttpResponse response;
		try {
			response = httpclient.execute(httpGet);
			if(response.getStatusLine().getStatusCode()==200){
				String result=EntityUtils.toString(response.getEntity());
				JSONObject requestObject = JSONObject.fromObject(result);
				JSONArray ja = new JSONArray();
				ja = requestObject.getJSONArray("urls");
				if(ja!=null&&ja.size()>0){
					requestObject = JSONObject.fromObject(ja.get(0));
					if(requestObject.has("url_short")&&requestObject.get("url_short")!=null){
						return (String)requestObject.get("url_short");
					}else{
						return "url_short error";
					}
				}else{
					return "url_short error";
				}
			}else{
				return "url_short error";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "url_short error";
		} catch (IOException e) {
			e.printStackTrace();
			return "url_short error";
		}
	}
	public static void main(String[] args) {
		System.out.println(getShotUrl("http://open.weibo.com/wiki/Short_url/shorten"));;
	}
}
