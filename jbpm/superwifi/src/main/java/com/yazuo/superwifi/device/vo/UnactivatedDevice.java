/*
 * 文件名：UnactivatedDevice.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2015年1月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.device.vo;


import java.util.Date;


public class UnactivatedDevice
{
    /**
     * 主键
     */
    private String id;

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
    
    private String mac;
    
    
    /**
     * 激活状态
     */
    public static final Integer STATUS_ACTIVATED=0;
    /**
     * 释放状态
     */
    public static final Integer STATUS_RELEASE=1;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    public String getMac()
    {
        return mac;
    }

    public void setMac(String mac)
    {
        this.mac = mac;
    }
    
}
