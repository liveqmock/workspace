package com.yazuo.superwifi.sms.service.impl;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.superwifi.sms.service.SMSSender;
import com.yazuo.superwifi.sms.service.SmsLogService;
import com.yazuo.superwifi.sms.vo.SmsLog;
import com.yazuo.superwifi.util.HttpUtil;
import com.yazuo.superwifi.util.SendMessage;


@Service("SMSSenderImpl")
public class SMSSenderImpl implements SMSSender
{
    @Resource(name = "smsLogServiceImpl")
    private SmsLogService smsLogServiceImpl;

    private static final Logger LOG = Logger.getLogger(SMSSenderImpl.class);

    @Value("#{propertiesReader['smsUrl']}") 
    String smsUrl;
    
    @Value("#{propertiesReader['smsMarketUrl']}") 
    String smsMarketUrl;
    
    @Value("#{propertiesReader['crmApiKey']}")
    private String crmApiKey;

    /**
     * <p> Description:发送短信 </p>
     */
    public void sendMessage(String content, String mobileNumber,Integer brandId,Integer messType)
        throws Exception
    {
        SmsLog smsLog = new SmsLog();
        smsLog.setMobileNumber(mobileNumber);
        smsLog.setContent(content);
        smsLog.setBrandId(brandId);
        smsLog.setInsertTime(new Date());
        double smsLength = content.length();
        // 现有短信机制可以认为70个字为一条
        double standardSmsLength = 70d;
        smsLog.setSmsLength(smsLength);
        smsLog.setSmsCount((int)Math.ceil(smsLength / standardSmsLength));
        smsLog.setSmsType(messType);
        smsLog.setIsSuccess(false);

        content = URLEncoder.encode(content, "UTF-8");
        String jsonpar = "{\"mobile\":\"" + mobileNumber + "\",\"content\":\"" + content + "\",\"merchant_id\":\""
                         + SendMessage.MERCHANT_ID + "\",\"source\":\"" + SendMessage.SOURCE + "\"}";

        String jsonre = "";
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("ciphertext", jsonpar));
        nvps.add(new BasicNameValuePair("key", SendMessage.KEY.toString()));
        if(messType.intValue() == SendMessage.SMS_TYPE){
            jsonre = HttpUtil.httpPostForString(nvps, smsUrl);
        }else if(messType.intValue() == SendMessage.SMSMARKETURL_TYPE){
            jsonre = HttpUtil.httpPostForString(nvps, smsMarketUrl);
        }else{
            LOG.info("未知的短信类型");
        }

        LOG.info("超级wifi调用发送短信接口返回jsonre=" + jsonre);
        if (!StringUtils.isBlank(jsonre))
        {
            JSONObject requestObject = JSONObject.fromObject(jsonre);
            String data = URLDecoder.decode(((JSONObject)requestObject.get("data")).get("result").toString(), "UTF-8");
            LOG.info("超级wifi调用发送短信返回" + data);
            JSONObject jsondata = JSONObject.fromObject(data);

            if (jsondata == null)
            {
                LOG.info("手机号：" + mobileNumber + "；短信发送失败,返回jsondata为空");
                
                smsLog.setIsSuccess(false);
                LOG.info(content);
                smsLogServiceImpl.addSmsLOg(smsLog);
            }
            else if (jsondata.getBoolean("success"))
            {
                LOG.info("手机号：" + mobileNumber + "；短信发送成功");
                smsLog.setIsSuccess(true);
                LOG.info(content);
                smsLogServiceImpl.addSmsLOg(smsLog);
            }
            else
            {
                LOG.info("手机号：" + mobileNumber + "；短信发送失败");
                smsLog.setIsSuccess(false);
                //保存短信
                LOG.info(content);
                smsLogServiceImpl.addSmsLOg(smsLog);
            }
        }
        else
        {
            LOG.info("超级wifi调用发短信接口返回jsonre为空");
            smsLog.setIsSuccess(false);
            LOG.info(content);
            smsLogServiceImpl.addSmsLOg(smsLog);
        }
    }
}
