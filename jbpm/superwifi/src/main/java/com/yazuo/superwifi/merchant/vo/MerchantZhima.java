/*
 * 文件名：MerchantZhima.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年7月31日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.merchant.vo;

public class MerchantZhima
{
private Integer merchantId;
private String zhimaId;
private Integer brandId;
private Integer status;
/**
 * 正常状态
 */
public static final Integer STATUS_NORMAL=1;
/**
 * 删除状态
 */
public static final Integer STATUS_DELETE=0;
public Integer getMerchantId()
{
    return merchantId;
}
public void setMerchantId(Integer merchantId)
{
    this.merchantId = merchantId;
}
public String getZhimaId()
{
    return zhimaId;
}
public void setZhimaId(String zhimaId)
{
    this.zhimaId = zhimaId;
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

}
