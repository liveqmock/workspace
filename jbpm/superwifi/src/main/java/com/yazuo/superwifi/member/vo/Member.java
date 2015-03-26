package com.yazuo.superwifi.member.vo;


import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;


public class Member
{
    /**
     * 主键
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
     * 状态 0删除 1正常
     */
    private Integer status;

    /**
     * 插入时间
     */
    private Date insertTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * CRM id
     */
    private String crmMemberId;

    /**
     * 终端mac码
     */
    @Indexed
    private String mac;

    private String phoneNumber;

    /**
     * 软删除状态
     */
    public static final Integer STATUS_DELETE = 0;

    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL = 1;

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

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
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

    public String getCrmMemberId()
    {
        return crmMemberId;
    }

    public void setCrmMemberId(String crmMemberId)
    {
        this.crmMemberId = crmMemberId;
    }

    public String getMac()
    {
        return mac;
    }

    public void setMac(String mac)
    {
        this.mac = mac;
    }

    /**
     * @return Returns the phoneNumber.
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            The phoneNumber to set.
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

}
