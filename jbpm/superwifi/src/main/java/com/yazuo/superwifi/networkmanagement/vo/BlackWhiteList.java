/*
 * 文件名：BlackWhiteList.java
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
import java.util.List;


public class BlackWhiteList
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
     * 手机号
     */
    private String phoneNum;
    
    /**
     * mac
     */
    private String mac;
    
    /**
     * 店员名店来源，1同步，2超时自动踢出
     */
    private Integer source;

    /**
     * 类型 1 黑名单 2 白名单
     */
    private Integer type;

    /**
     * maclist
     */
    private List<String> macList;
    
    /**
     * 软删除状态
     */
    public static final Integer STATUS_DELETE=0;
    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL=1;

    /**
     * 黑名单
     */
    public static final Integer TYPE_BLACK=1;
    /**
     * 白名单
     */
    public static final Integer TYPE_WHITE=2;
    
    public static final Integer SOURCE_FACESHOP=1;
    
    public static final Integer SOURCE_TIMEOUT=2;
    public String getId()
    {
        return id;
    }

    public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
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

	public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public List<String> getMacList()
    {
        return macList;
    }

    public void setMacList(List<String> macList)
    {
        this.macList = macList;
    }

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
}
