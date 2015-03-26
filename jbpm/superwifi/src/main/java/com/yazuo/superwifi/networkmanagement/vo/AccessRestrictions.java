/*
 * 文件名：AccessRestrictions.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年12月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.networkmanagement.vo;

import java.util.Date;

public class AccessRestrictions
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
     * 类型 1网站  2 APP
     */
    private Integer type;
    /**
     * 值：网站是一个url  APP是一个或者多个url
     */
//    private Object values;
    private String url;
    /**
     * 插入时间
     */
    private Date insertTime;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
    /**
     * 状态  0删除  1正常
     */
    private Integer status;
    /**
     * APP 名字
     */
    private String name;
    /**
     * 软删除状态
     */
    public static final Integer STATUS_DELETE=0;
    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL=1;
    /**
     * 网站
     */
    public static final Integer TYPE_WEB=1;
    /**
     * APP
     */
    public static final Integer TYPE_APP=2;
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
    public Integer getType()
    {
        return type;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getStatus()
    {
        return status;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
}
