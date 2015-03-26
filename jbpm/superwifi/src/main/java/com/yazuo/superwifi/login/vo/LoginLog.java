/*
 * 文件名：LoginLog.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年12月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.login.vo;

import java.sql.Timestamp;


public class LoginLog
{
    /**
     * 主键、存放mac码
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
     * 插入时间
     */
    private Timestamp insertTime;

    /**
     * 手机号
     */
    private String mobileNumber;

    /**
     * 设备mac
     */
    private String device_mac;

    /**
     * 会员mac
     */
    private String member_mac;

    private String url;
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

    public Timestamp getInsertTime()
    {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime)
    {
        this.insertTime = insertTime;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getDevice_mac()
    {
        return device_mac;
    }

    public void setDevice_mac(String device_mac)
    {
        this.device_mac = device_mac;
    }

    public String getMember_mac()
    {
        return member_mac;
    }

    public void setMember_mac(String member_mac)
    {
        this.member_mac = member_mac;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    
}
