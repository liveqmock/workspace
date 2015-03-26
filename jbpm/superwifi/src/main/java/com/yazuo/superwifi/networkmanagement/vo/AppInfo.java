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
import java.util.List;

public class AppInfo
{
    /**
     * 主键、存放mac码
     */
    private String id;
    /**
     * app连接地址
     */
    private List<String> urlList;
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
   
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
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
	public List<String> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
    
}
