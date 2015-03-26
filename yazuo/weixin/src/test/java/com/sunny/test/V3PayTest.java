package com.sunny.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.poifs.filesystem.EntryUtils;
import org.junit.Test;

import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.MD5Util;

public class V3PayTest {
	
	@Test
	public void testRefund() throws Exception{
		String url="https://api.mch.weixin.qq.com/secapi/pay/refund";
		String appid ="wxda8f17411ad516ef";
		String mch_id="10063341";
		String nonce_str =CommonUtil.CreateNoncestr();
		String transaction_id ="1004510493201502270022286505";
		String out_refund_no ="tk150227114436B3bx4PdQzQr3Qf02";
		String out_trade_no ="150227114436B3bx4PdQzQr3Qf02";
		String total_fee ="100";
		String refund_fee ="100";
		String op_user_id =mch_id;
		SortedMap<String, String> map = new TreeMap<String,String>();
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", nonce_str);
		map.put("transaction_id", transaction_id);
		map.put("out_refund_no", out_refund_no);
		map.put("total_fee", total_fee);
		map.put("refund_fee", refund_fee);
		map.put("op_user_id", op_user_id);
		String sign = createSign(map,"");
		map.put("sign", sign);
		String xml=CommonUtil.ArrayToXml(map);
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("E:/"+mch_id+".p12"));
        try {
            keyStore.load(instream, mch_id.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mch_id.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {

            
            HttpPost post= new HttpPost(url);
            post.setEntity(new StringEntity(xml, "UTF-8"));
            HttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    System.out.println(new String(EntityUtils.toString(entity).getBytes("UTF-8")));
                }
                EntityUtils.consume(entity);
            } finally {
            	httpclient.close();
            }
        } finally {
            httpclient.close();
        }
	}
	
	public String createSign(SortedMap<String, String> packageParams,String apiKey){
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=sDGYszkxbbBMai7G8Vap7FL4gjwNXhvz");
		String sign = MD5Util.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
		return sign;
	}
}
