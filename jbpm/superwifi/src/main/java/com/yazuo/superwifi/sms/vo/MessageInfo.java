package com.yazuo.superwifi.sms.vo;


import java.util.Date;


public class MessageInfo
{
    /**
     * 主键、
     */
    private String id;

    /**
     * 门店ID
     */
    private Integer merchantId;

    /**
     * 品牌ID
     */
    private Integer brandId;

    /**
     * 是否发送短信
     */
    private Boolean isSendMessage;

    /**
     * 短信类型，1连接WiFi推送短信，2过客短信
     */
    private Integer messageType;

    /**
     * 插入时间
     */
    private Date insertTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 长度
     */
    private Integer length;

    /**
     * 条数
     */
    private Integer counts;

    /**
     * 连接WiFi推送短信
     */
    public static final Integer MESSAGE_WIFISEND = 0;

    /**
     * 过客短信
     */
    public static final Integer MESSAGE_GUOKE = 1;

    private Integer awardRuleId;

    private Integer activeSmsTemplateId;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId)
    {
        this.merchantId = merchantId;
    }

    public Integer getBrandId()
    {
        return brandId;
    }

    public void setBrandId(Integer brandId)
    {
        this.brandId = brandId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Integer getLength()
    {
        return length;
    }

    public void setLength(Integer length)
    {
        this.length = length;
    }

    public Integer getCounts()
    {
        return counts;
    }

    public void setCounts(Integer counts)
    {
        this.counts = counts;
    }

    public Boolean getIsSendMessage()
    {
        return isSendMessage;
    }

    public void setIsSendMessage(Boolean isSendMessage)
    {
        this.isSendMessage = isSendMessage;
    }

    public Integer getMessageType()
    {
        return messageType;
    }

    public void setMessageType(Integer messageType)
    {
        this.messageType = messageType;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }

    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getAwardRuleId()
    {
        return awardRuleId;
    }

    public void setAwardRuleId(Integer awardRuleId)
    {
        this.awardRuleId = awardRuleId;
    }

    public Integer getActiveSmsTemplateId()
    {
        return activeSmsTemplateId;
    }

    public void setActiveSmsTemplateId(Integer activeSmsTemplateId)
    {
        this.activeSmsTemplateId = activeSmsTemplateId;
    }

}
