package com.yazuo.superwifi.sms.service.impl;


import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yazuo.superwifi.exception.BussinessException;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.merchant.vo.AwardRule;
import com.yazuo.superwifi.sms.service.MessageInfoService;
import com.yazuo.superwifi.sms.vo.MessageInfo;
import com.yazuo.superwifi.util.DateJsonValueProcessor;
import com.yazuo.superwifi.util.HttpUtil;


@Service("messageInfoServiceImpl")
public class MessageInfoServiceImpl implements MessageInfoService
{
    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Value("#{propertiesReader['addOrUpdateAwardRuleInfo']}")
    private String addOrUpdateAwardRuleInfo;

    @Value("#{propertiesReader['saveOrUpdateActiveSmsTemplate']}")
    private String saveOrUpdateActiveSmsTemplate;

    @Value("#{propertiesReader['deleteAwardRule']}")
    private String deleteAwardRule;

    @Value("#{propertiesReader['crmApiKey']}")
    private String crmApiKey;

    @Resource(name = "merchantServiceImpl")
    private MerchantService merchantService;

    public void addMessageInfo(MessageInfo messageInfo)
        throws Exception
    {
        Boolean isSendMessage = messageInfo.getIsSendMessage();
        Assert.notNull(isSendMessage, "是否发送短信标识不能为空");
        String id = messageInfo.getId();
        if (isSendMessage)// 需要发送短信
        {
            Assert.notNull(messageInfo.getContent(), "短信内容不能为空");
            Assert.hasText(messageInfo.getContent(), "短信内容不能为空");
            if (StringUtils.isEmpty(id))// 添加短信规则
            {
                // 添加crm短信营销
                Map<String, Object> result = addSmsRule(messageInfo.getBrandId(), messageInfo.getContent());
                Integer awardRuleId = (Integer)result.get("awardRuleId");
                Integer activeSmsTemplateId = (Integer)result.get("activeSmsTemplateId");

                // 添加短信设置规则
                messageInfo.setId(null);
                messageInfo.setAwardRuleId(awardRuleId);
                messageInfo.setActiveSmsTemplateId(activeSmsTemplateId);
                messageInfo.setInsertTime(new Date());
                messageInfo.setLastUpdateTime(new Date());
                mongoTemplate.insert(messageInfo);
            }
            else
            {// 修改短信规则
                Query query = new Query(Criteria.where("_id").is(id));
                MessageInfo info = mongoTemplate.findOne(query, MessageInfo.class);
                Update update = null;
                if (null != info.getAwardRuleId())// crm已有营销规则与短信模板
                {
                    updateSmsRule(messageInfo.getContent(), info.getActiveSmsTemplateId(), info.getAwardRuleId(),
                        messageInfo.getBrandId());
                    update = Update.update("lastUpdateTime", new Date()).set("isSendMessage",
                        messageInfo.getIsSendMessage()).set("merchantId", messageInfo.getMerchantId()).set("content",
                        messageInfo.getContent()).set("counts", messageInfo.getCounts()).set("length",
                        messageInfo.getLength());
                }
                else
                {// crm未添加营销规则与短信模板
                    Map<String, Object> result = addSmsRule(messageInfo.getBrandId(), messageInfo.getContent());
                    Integer awardRuleId = (Integer)result.get("awardRuleId");
                    Integer activeSmsTemplateId = (Integer)result.get("activeSmsTemplateId");

                    update = Update.update("lastUpdateTime", new Date()).set("isSendMessage",
                        messageInfo.getIsSendMessage()).set("merchantId", messageInfo.getMerchantId()).set("content",
                        messageInfo.getContent()).set("counts", messageInfo.getCounts()).set("length",
                        messageInfo.getLength()).set("awardRuleId", awardRuleId).set("activeSmsTemplateId",
                        activeSmsTemplateId);
                }

                mongoTemplate.updateFirst(query, update, MessageInfo.class);
            }
        }
        else
        {// 不需要发送短信
            if (StringUtils.isEmpty(id))
            {
                messageInfo.setId(null);
                messageInfo.setInsertTime(new Date());
                messageInfo.setLastUpdateTime(new Date());
                mongoTemplate.insert(messageInfo);
            }
            else
            {
                Query query = new Query(Criteria.where("_id").is(id));
                MessageInfo info = mongoTemplate.findOne(query, MessageInfo.class);

                // 删除crm营销短信
                deleteSmsRule(info.getAwardRuleId());

                // 修改短信设置规则
                Update update = Update.update("lastUpdateTime", new Date()).set("isSendMessage",
                    messageInfo.getIsSendMessage()).set("merchantId", messageInfo.getMerchantId()).set("content", "").set(
                    "counts", 0).set("length", 0).set("awardRuleId", null).set("activeSmsTemplateId", null);
                mongoTemplate.updateFirst(query, update, MessageInfo.class);
            }
        }
    }

    @Override
    public MessageInfo getMessageInfo(Map<String, Object> map)
        throws Exception
    {
        Integer brandId = (Integer)map.get("brandId");
        MessageInfo messageInfo = mongoTemplate.findOne(new Query(Criteria.where("brandId").is(brandId)),
            MessageInfo.class);
        if (null == messageInfo)
        {
            messageInfo = new MessageInfo();
            messageInfo.setId("");
            messageInfo.setIsSendMessage(false);
            messageInfo.setContent("");
        }
        return messageInfo;
    }

    private Map<String, Object> addSmsRule(Integer brandId, String content)
        throws Exception
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date activeEnd = sdf.parse("9999-12-12");
        AwardRule awardRule = new AwardRule();

        awardRule.setLowerLimit(new BigDecimal(0));
        awardRule.setUpperLimit(new BigDecimal(10000000));
        awardRule.setAwardAmount(new BigDecimal(0));
        awardRule.setTransCode("0000");
        awardRule.setMerchantId(brandId);
        awardRule.setScope(1);
        awardRule.setAwardType(8);
        awardRule.setAwardAmountType(1);
        awardRule.setAgainFlag(true);
        awardRule.setReuseFlag(1);
        awardRule.setActiveBegin(new Date());
        awardRule.setActiveEnd(activeEnd);
        awardRule.setSourceLimit("13,14");
        awardRule.setMembershipGrade("1");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("awardRule", awardRule);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor()
        {
            public Object getDefaultValue(Class type)
            {
                return null;
            }
        });
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
        List<NameValuePair> nvps1 = new ArrayList<NameValuePair>();
        nvps1.add(new BasicNameValuePair("ciphertext", URLEncoder.encode(
            JSONObject.fromObject(map, jsonConfig).toString(), "utf-8")));
        nvps1.add(new BasicNameValuePair("key", crmApiKey));
        JSONObject awardRuleInfo = HttpUtil.httpPostForJson(nvps1, addOrUpdateAwardRuleInfo);
        boolean success = awardRuleInfo.getJSONObject("data").getJSONObject("result").getBoolean("success");
        if (!success)
        {
            throw new BussinessException("营销创建失败");
        }
        Integer awardRuleId = awardRuleInfo.getJSONObject("data").getJSONObject("result").getInt("id");
        JSONObject jo = new JSONObject();
        jo.accumulate("merchantId", brandId);
        jo.accumulate("awardRuleId", awardRuleId);
        jo.accumulate("messageContent", content);
        List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();
        nvps2.add(new BasicNameValuePair("ciphertext", URLEncoder.encode(jo.toString(), "utf-8")));
        nvps2.add(new BasicNameValuePair("key", crmApiKey));
        JSONObject smsTemplateJson = HttpUtil.httpPostForJson(nvps2, saveOrUpdateActiveSmsTemplate);
        boolean flag = smsTemplateJson.getJSONObject("data").getJSONObject("result").getBoolean("success");
        if (!flag)
        {
            throw new BussinessException("营销创建失败");
        }
        Integer activeSmsTemplateId = smsTemplateJson.getJSONObject("data").getJSONObject("result").getInt("id");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("awardRuleId", awardRuleId);
        result.put("activeSmsTemplateId", activeSmsTemplateId);
        return result;
    }

    private void deleteSmsRule(Integer awardRuleId)
        throws Exception
    {
        List<NameValuePair> nvps1 = new ArrayList<NameValuePair>();
        nvps1.add(new BasicNameValuePair("ciphertext", "{awardRuleId:" + awardRuleId + "}"));
        nvps1.add(new BasicNameValuePair("publicKey", crmApiKey));

        JSONObject result = HttpUtil.httpPostForJson(nvps1, deleteAwardRule);
        boolean flag = result.getJSONObject("data").getJSONObject("result").getBoolean("success");
        if (!flag)
        {
            throw new BussinessException("删除短信设置失败");
        }
    }

    private void updateSmsRule(String content, Integer smsTemplateId, Integer awardRuleId, Integer brandId)
        throws Exception
    {
        JSONObject jo = new JSONObject();
        jo.accumulate("id", smsTemplateId);
        jo.accumulate("merchantId", brandId);
        jo.accumulate("awardRuleId", awardRuleId);
        jo.accumulate("messageContent", content);
        List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();
        nvps2.add(new BasicNameValuePair("ciphertext", URLEncoder.encode(jo.toString(), "utf-8")));
        nvps2.add(new BasicNameValuePair("key", crmApiKey));
        JSONObject smsTemplateJson = HttpUtil.httpPostForJson(nvps2, saveOrUpdateActiveSmsTemplate);
        boolean flag = smsTemplateJson.getJSONObject("data").getJSONObject("result").getBoolean("success");
        if (!flag)
        {
            throw new BussinessException("修改短信规则失败");
        }
    }

}
