package com.yazuo.superwifi.util;


import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class HttpUtil
{
    static Logger log = Logger.getLogger(HttpUtil.class);

    public static String httpPostForString(List<NameValuePair> nvps, String url)
        throws Exception
    {
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        // 发送请求
        HttpResponse response=null;
        try
        {
            response = httpclient.execute(httpPost);
        }
        catch (Exception e)
        {
            log.error(url + "\nhttpPost调用失败 ",e);
            throw e;
        }
        StringBuffer logString = new StringBuffer("");
        for (NameValuePair nvp : nvps)
        {
            logString.append("\n" + nvp.getName() + "：" + nvp.getValue());
        }
        String result = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() == 200)
        {
            log.info("\nPost地址:" + url + "\n参数:\n---------------------------" + logString.toString() + "\n---------------------------\n返回值:" + result);
            return result;
        }
        else
        {
            log.error("\nPost地址:" + url + "\n参数:\n---------------------------" + logString.toString() + "\n---------------------------\n状态码:"
                + response.getStatusLine().getStatusCode() +"\n返回值:" + result);
            throw new Exception("\nPost地址:" + url + "\n参数:\n---------------------------" + logString.toString() + "\n---------------------------\n状态码:"
                + response.getStatusLine().getStatusCode() +"\n返回值:" + result);
        }
    }

    public static JSONObject httpPostForJson(List<NameValuePair> nvps, String url)
        throws Exception
    {

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        // 发送请求
        HttpResponse response;
        response = httpclient.execute(httpPost);
        StringBuffer logString = new StringBuffer("");
        for (NameValuePair nvp : nvps)
        {
            logString.append("\n" + nvp.getName() + "：" + nvp.getValue());
        }
        String result = EntityUtils.toString(response.getEntity());
        if (response.getStatusLine().getStatusCode() == 200)
        {
            
            JSONObject requestObject = JSONObject.fromObject(result);
            log.info("\nPost地址:" + url + "\n参数:\n---------------------------" + logString.toString() + "\n---------------------------\n返回值:\n" + requestObject.toString());
            return requestObject;
        }
        else
        {
            log.error("\nPost地址:" + url + "\n参数:\n---------------------------" + logString.toString() + "\n---------------------------\n状态码:"
                + response.getStatusLine().getStatusCode() +"\n返回值:" + result);
            throw new Exception("\nPost地址:" + url + "\n参数:\n---------------------------" + logString.toString() + "\n---------------------------\n状态码:"
                + response.getStatusLine().getStatusCode() +"\n返回值:" + result);
        }
    }
    
    public static void main(String[] args)
    {
        //String url="http://open.zhimatech.com/api/v1/yazuo/getEnteredCustomerCount";
        //String url="http://open.zhimatech.com/api/v1/yazuo/getTouchedCustomerCount";
        String url="http://open.zhimatech.com/api/v1/yazuo/getCustomerMacInfo";
        //String url="http://open.zhimatech.com/api/v1/yazuo/getCustomerStayedDistributsing";
        List<NameValuePair> nvps=new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("startTime", "2014-05-19"));
        nvps.add(new BasicNameValuePair("endTime", "2014-09-18"));
        nvps.add(new BasicNameValuePair("deviceMac", "048d38565058"));
        nvps.add(new BasicNameValuePair("token", "yz_t1000"));
        nvps.add(new BasicNameValuePair("duration", "10"));
        nvps.add(new BasicNameValuePair("macList", "F8:A4:5F:11:08:B3"));
//        nvps.add(new BasicNameValuePair("macList", "00:0a:f5:44:66:11"));
//        nvps.add(new BasicNameValuePair("macList", "54:5E:BD:5c:75:37"));
        try
        {
            log.info(httpPostForJson(nvps, url));
        }
        catch (Exception e)
        {
            // TODO Auto-generated   catch block
            e.printStackTrace();
        }
    }
}
