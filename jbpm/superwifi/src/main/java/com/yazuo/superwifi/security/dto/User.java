package com.yazuo.superwifi.security.dto;


import java.util.Date;


public class User
{
    private Integer id;

    private String userName;

    private String password;

    private Boolean status;

    private Date insertTime;

    private String mobileNumber;

    private Integer brandId;

    private Integer merchantId;

    private String tempPwd;
    
    private String cipherPasswd;
    
    private long tempPwdExpireTime;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return Returns the status.
     */
    public Boolean getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(Boolean status)
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

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber == null ? null : mobileNumber.trim();
    }

    public Integer getBrandId()
    {
        return brandId;
    }

    public void setBrandId(Integer brandId)
    {
        this.brandId = brandId;
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
     * @return Returns the tempPwd.
     */
    public String getTempPwd()
    {
        return tempPwd;
    }

    /**
     * @param tempPwd
     *            The tempPwd to set.
     */
    public void setTempPwd(String tempPwd)
    {
        this.tempPwd = tempPwd;
    }

    /**
     * @return Returns the cipherPasswd.
     */
    public String getCipherPasswd()
    {
        return cipherPasswd;
    }

    /**
     * @param cipherPasswd The cipherPasswd to set.
     */
    public void setCipherPasswd(String cipherPasswd)
    {
        this.cipherPasswd = cipherPasswd;
    }

    /**
     * @return Returns the tempPwdExpireTime.
     */
    public long getTempPwdExpireTime()
    {
        return tempPwdExpireTime;
    }

    /**
     * @param tempPwdExpireTime The tempPwdExpireTime to set.
     */
    public void setTempPwdExpireTime(long tempPwdExpireTime)
    {
        this.tempPwdExpireTime = tempPwdExpireTime;
    }

}