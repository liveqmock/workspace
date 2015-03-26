
package com.yazuo.erp.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.system.vo.SendMessage;
import com.yazuo.erp.system.vo.SmsLog;
import com.yazuo.util.DateUtil;
import com.yazuo.util.HttpUtil;
@Repository("sendMessageVoid")
public class SendMessageVoid 
{
	private static final Log LOG = LogFactory.getLog(SendMessageVoid.class);
	private static final Log LOG_PWD = LogFactory.getLog("userpassword");
	private static final Log LOG_SMS = LogFactory.getLog("sms");
	
    @Value("${smsUrl}")
    private String smsUrl;
    /**
     * <p>
     * Description:发送短信
     * </p>
     */
    @Async //发短信改成异步的方式， 不阻塞正常的流程
    public void sendMessage(String content, String mobileNumber, Log log) {
		//记录发送短信的内容
		log.info( DateUtil.getDate()+ ";手机号："+mobileNumber+";" +content);
    	this.sendMessage(content, mobileNumber);
    }
	/**
	 * <p>
	 * Description:发送短信
	 * </p>
	 */
	public void sendMessage(String content, String mobileNumber) {
		SmsLog smsLog=new SmsLog();
		smsLog.setMobileNumber(mobileNumber);
		smsLog.setContent(content);
		
		try {
			content=URLEncoder.encode(content,"UTF-8");
		} catch (UnsupportedEncodingException e2) {
			LOG.error(e2);
			e2.printStackTrace();
		} catch (Exception e2) {
				LOG.error(e2);
				e2.printStackTrace();
			}
		String jsonpar = "{\"mobile\":\"" + mobileNumber + "\",\"content\":\""
				+ content + "\",\"merchant_id\":\"" + SendMessage.MERCHANT_ID
				+ "\",\"source\":\"" + SendMessage.SOURCE + "\"}";

		
		String jsonre="";
		try {
			List<NameValuePair> nvps=new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("ciphertext",jsonpar));
			nvps.add(new BasicNameValuePair("key",SendMessage.KEY.toString()));
			jsonre = HttpUtil.httpPostForString(nvps, smsUrl);
		} catch (Exception e1) {
			LOG.error(e1);
			e1.printStackTrace();
		}

		LOG.info("Erp调用发送短信接口返回jsonre=" + jsonre);
		if (!StringUtils.isBlank(jsonre)) {
			JSONObject requestObject = JSONObject.fromObject(jsonre);
			try {
				String data = URLDecoder.decode(((JSONObject) requestObject
						.get("data")).get("result").toString(), "UTF-8");
				LOG.info("Erp调用发送短信返回" + data);
				JSONObject jsondata = JSONObject.fromObject(data);

				if (jsondata == null) {
					LOG.info("手机号：" + mobileNumber + "；短信发送失败,返回jsondata为空");
				} else if (jsondata.getBoolean("success")) {
					LOG.info("手机号：" + mobileNumber + "；短信发送成功");
				} else {
					LOG.info("手机号：" + mobileNumber + "；短信发送失败");
				}

			} catch (UnsupportedEncodingException e) {
				LOG.error("手机号：" + mobileNumber + "；短信发送失败", e);
			}

		} else {
			LOG.info("Erp调用发短信接口返回jsonre为空");
		}
	}
}
