/*
 * 文件名：BrandInfo.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年12月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.merchant.vo;

import java.util.Date;
import java.util.List;


public class HomePage
{
    /**
     * 主键
     */
    private String id;

    /**
     * 品牌ID
     */
    private Integer brandId;
    /**
     * 门店id
     */
    private Integer merchantId;

    /**
     * 插入时间
     */
    private Date insertTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 自定义的会员中心
     */
    private String html;
    
    /**
     * 选择卡类型的id
     */
    private List<Integer> cardTypeIds;
    /**
     * 连接到会员中心的方式  1 微信的会员中心  2自定义主页方案  3会员卡推荐页面
     */
    private Integer memberCenterType;
    /**
     * 连接到会员中心方式的名称，不存数据库 只做展示使用
     */
    private String memberCenterName;
    
    private Boolean isCrmMerchant;
  
    public static final String MEMBERCENTER="连接至会员中心";
    public static final String MYSETHOMEPAGE="自定义主页方案";
    public static final String MEMBERCARD_RECOMMEND="会员卡推荐页面";
    
    public static final Integer MEMBERCENTER_VALUE=1;
    public static final Integer MYSETHOMEPAGE_VALUE=2;
    public static final Integer MEMBERCARD_RECOMMEND_VALUE=3;
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getBrandId()
    {
        return brandId;
    }

    public void setBrandId(Integer brandId)
    {
        this.brandId = brandId;
    }

    public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
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

	public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
    }

    public Integer getMemberCenterType()
    {
        return memberCenterType;
    }

    public void setMemberCenterType(Integer memberCenterType)
    {
        this.memberCenterType = memberCenterType;
    }

	public String getMemberCenterName() {
		return memberCenterName;
	}

	public void setMemberCenterName(String memberCenterName) {
		this.memberCenterName = memberCenterName;
	}

    public List<Integer> getCardTypeIds()
    {
        return cardTypeIds;
    }

    public void setCardTypeIds(List<Integer> cardTypeIds)
    {
        this.cardTypeIds = cardTypeIds;
    }

    /**
     * @return Returns the isCrmMerchant.
     */
    public Boolean getIsCrmMerchant()
    {
        return isCrmMerchant;
    }

    /**
     * @param isCrmMerchant The isCrmMerchant to set.
     */
    public void setIsCrmMerchant(Boolean isCrmMerchant)
    {
        this.isCrmMerchant = isCrmMerchant;
    }
    
}
