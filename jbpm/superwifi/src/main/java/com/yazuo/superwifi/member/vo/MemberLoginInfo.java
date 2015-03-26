package com.yazuo.superwifi.member.vo;


import java.util.Date;


public class MemberLoginInfo
{
    /**
     * 主键
     */
    private String id;

    /**
     * 用户设备mac码
     */
    private String pcmac;

    /**
     * 门店ID
     */
    private Integer merchantId;

    /**
     * 品牌ID
     */
    private Integer brandId;

    /**
     * wifi设备mac码
     */
    private String rmac;

    /**
     * ssid名称
     */
    private String ssid;

    /**
     * 登陆时间
     */
    private Date loginTime;
    /**
     * request头信息
     */
    private String userAgent;
    
    /**
     * @return Returns the id.
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return Returns the pcmac.
     */
    public String getPcmac()
    {
        return pcmac;
    }

    /**
     * @param pcmac
     *            The pcmac to set.
     */
    public void setPcmac(String pcmac)
    {
        this.pcmac = pcmac;
    }

    /**
     * @return Returns the merchantId.
     */
    public Integer getMerchantId()
    {
        return merchantId;
    }

    /**
     * @param merchantId
     *            The merchantId to set.
     */
    public void setMerchantId(Integer merchantId)
    {
        this.merchantId = merchantId;
    }

    /**
     * @return Returns the brandId.
     */
    public Integer getBrandId()
    {
        return brandId;
    }

    /**
     * @param brandId
     *            The brandId to set.
     */
    public void setBrandId(Integer brandId)
    {
        this.brandId = brandId;
    }

    /**
     * @return Returns the rmac.
     */
    public String getRmac()
    {
        return rmac;
    }

    /**
     * @param rmac
     *            The rmac to set.
     */
    public void setRmac(String rmac)
    {
        this.rmac = rmac;
    }

    /**
     * @return Returns the ssid.
     */
    public String getSsid()
    {
        return ssid;
    }

    /**
     * @param ssid
     *            The ssid to set.
     */
    public void setSsid(String ssid)
    {
        this.ssid = ssid;
    }

    /**
     * @return Returns the loginTime.
     */
    public Date getLoginTime()
    {
        return loginTime;
    }

    /**
     * @param loginTime
     *            The loginTime to set.
     */
    public void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }

    public String getUserAgent()
    {
        return userAgent;
    }

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

}
