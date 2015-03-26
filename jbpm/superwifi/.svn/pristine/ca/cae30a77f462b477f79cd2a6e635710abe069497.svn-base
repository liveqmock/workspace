/*
 * 文件名：Merchant.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年7月31日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.merchant.vo;


import java.util.Date;
import java.util.List;

import com.yazuo.superwifi.device.vo.DeviceInfo;


public class MerchantInfo implements Cloneable
{
    private Integer merchantId;

    private String merchantName;

    private Boolean isFaceShop;

    private Integer parent;

    private Integer status;

    private Integer brandId;

    /**
     * 此处mac码list，只负责在前端展示时抛出使用，不存入数据库 在deviceinfo中查出该商户的所有mac集合然后放入到此处抛出去给前台展示
     */
    private List<DeviceInfo> deviceInfoList;

    private String parentName;

    private Date insertTime;

    private Date lastUpdateTime;

    /**
     * portal图片路径
     */
    private String portalPic;

    /**
     * 连接类型 1浏览器 2微信 3浏览器+微信
     */
    private Integer connectType;

    /**
     * 芝麻库内唯一标示，暂不用关心
     */
    private Integer shopId;

    /**
     * 驻店时长
     */
    private Integer time;

    /**
     * 主管密码
     */
    private String adminPassWord;

    /**
     * 开通表单时设置的最大权限的人，老板/店长姓名
     */
    private String bossName;

    /**
     * 开通表单时设置的最大权限的人，老板/店长姓名
     */
    private String bossMobile;

    /**
     * 是否发送会员增量统计
     */
    private Boolean isSendMemStatic;

    /**
     * 发送会员增量统计的手机号
     */
    private String sendMemStatisMobile;

    /**
     * 商户来源 1-CRM商户，2-非CRM商户
     */
    private Integer source;
    /**
     * 芝麻后台用户名
     */
    private String zhimaUsername;
    /**
     * 芝麻后台密码
     */
    private String zhimaPassword;
    /**
     * 操作人
     */
    private String operatorId;
    
    /**
     * 操作人
     */
    private String operatorMobileNumber;
    /**
     * 运营平台中的用户ID
     */
    private Integer userId;
    
    /**
     * 是否开通短信验证码  
     * true  需要正确的验证码   false  随便填写即可不做校验
     */
    private Boolean isPassWordCheck;
    
    /**
     * 商户类型，1回头宝商户，2全会员营销商户
     */

    private Integer merchantType;
    /**
     * 软删除状态
     */
    public static final Integer STATUS_DELETE = 0;

    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL = 1;

    public static final String CONNECTTYPE_WEIXIN = "微信连接";

    public static final String CONNECTTYPE_WEB = "网页连接";

    public static final String CONNECTTYPE_WEIXINADDWEB = "微信和网页连接";

    /**
     * 商户来源 1-CRM商户
     */
    public static final Integer SOURCE_CRM = 1;

    /**
     * 商户来源 2-非CRM商户(独立商户)
     */
    public static final Integer SOURCE_ALONE = 2;
    
    /**
     * 商户来源3-非CRM商户(独立商户)
     */
    public static final Integer SOURCE_TRIAL = 3;

    public Integer getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public Boolean getIsFaceShop()
    {
        return isFaceShop;
    }

    public void setIsFaceShop(Boolean isFaceShop)
    {
        this.isFaceShop = isFaceShop;
    }

    public Integer getParent()
    {
        return parent;
    }

    public void setParent(Integer parent)
    {
        this.parent = parent;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getBrandId()
    {
        return brandId;
    }

    public void setBrandId(Integer brandId)
    {
        this.brandId = brandId;
    }



    public Integer getShopId()
    {
        return shopId;
    }

    public void setShopId(Integer shopId)
    {
        this.shopId = shopId;
    }

    public List<DeviceInfo> getDeviceInfoList()
    {
        return deviceInfoList;
    }

    public void setDeviceInfoList(List<DeviceInfo> deviceInfoList)
    {
        this.deviceInfoList = deviceInfoList;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public Integer getConnectType()
    {
        return connectType;
    }

    public void setConnectType(Integer connectType)
    {
        this.connectType = connectType;
    }

    public Integer getTime()
    {
        return time;
    }

    public void setTime(Integer time)
    {
        this.time = time;
    }

    public String getAdminPassWord()
    {
        return adminPassWord;
    }

    public void setAdminPassWord(String adminPassWord)
    {
        this.adminPassWord = adminPassWord;
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

    public String getPortalPic()
    {
        return portalPic;
    }

    public void setPortalPic(String portalPic)
    {
        this.portalPic = portalPic;
    }

    public Boolean getIsSendMemStatic()
    {
        return isSendMemStatic;
    }

    public void setIsSendMemStatic(Boolean isSendMemStatic)
    {
        this.isSendMemStatic = isSendMemStatic;
    }

    public String getSendMemStatisMobile()
    {
        return sendMemStatisMobile;
    }

    public void setSendMemStatisMobile(String sendMemStatisMobile)
    {
        this.sendMemStatisMobile = sendMemStatisMobile;
    }

    public String getBossName()
    {
        return bossName;
    }

    public void setBossName(String bossName)
    {
        this.bossName = bossName;
    }

    public String getBossMobile()
    {
        return bossMobile;
    }

    public void setBossMobile(String bossMobile)
    {
        this.bossMobile = bossMobile;
    }

    public Integer getSource()
    {
        return source;
    }

    public void setSource(Integer source)
    {
        this.source = source;
    }

    @Override
    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public String getZhimaUsername()
    {
        return zhimaUsername;
    }

    public void setZhimaUsername(String zhimaUsername)
    {
        this.zhimaUsername = zhimaUsername;
    }

    public String getZhimaPassword()
    {
        return zhimaPassword;
    }

    public void setZhimaPassword(String zhimaPassword)
    {
        this.zhimaPassword = zhimaPassword;
    }

    public String getOperatorId()
    {
        return operatorId;
    }

    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }

    public String getOperatorMobileNumber()
    {
        return operatorMobileNumber;
    }

    public void setOperatorMobileNumber(String operatorMobileNumber)
    {
        this.operatorMobileNumber = operatorMobileNumber;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

	public Boolean getIsPassWordCheck() {
		return isPassWordCheck;
	}

	public void setIsPassWordCheck(Boolean isPassWordCheck) {
		this.isPassWordCheck = isPassWordCheck;
	}

	public Integer getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
	}

}
