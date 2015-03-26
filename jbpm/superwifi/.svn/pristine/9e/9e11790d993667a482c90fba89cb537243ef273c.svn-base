/*
 * 文件名：DeviceInfo.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年9月2日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.device.vo;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class DeviceInfo
{
    /**
     * 主键
     */
    private String id;
    /**
     * 存放mac码
     */
    @Indexed
    private String mac;
    /**
     * 门店ID
     */
    private Integer merchantId;
    /**
     * 品牌ID
     */
    private Integer brandId;
    /**
     * 状态  0删除  1正常
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
     * 引用deviceSSid 的名称
     */
    //@DBRef
    private List<DeviceSSID> devSSID;
   
    /**
     * 软删除状态
     */
    public static final Integer STATUS_DELETE=0;
    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL=1;
    /**
     * 浏览器
     */
    public static final Integer CONNECTTYPE_BROWSER=1;
    /**
     * 微信
     */
    public static final Integer CONNECTTYPE_WECHAT=2;
    /**
     * 浏览器和微信
     */
    public static final Integer CONNECTTYPE_BROWSERANDWECHAT=3;
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
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
    public Date getInsertTime()throws Exception
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
	public List<DeviceSSID> getDevSSID() {
		return devSSID;
	}
	public void setDevSSID(List<DeviceSSID> devSSID) {
		this.devSSID = devSSID;
	}
}
