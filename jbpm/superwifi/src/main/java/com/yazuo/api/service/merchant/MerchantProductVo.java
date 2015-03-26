/*
 * 文件名：MerchantProductVo.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2015年1月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.api.service.merchant;


public class MerchantProductVo implements java.io.Serializable {
    private Long id;
    private Integer productId;
    private Integer merchantId;
    private java.sql.Timestamp beginTime;
    private java.sql.Timestamp endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public java.sql.Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(java.sql.Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public java.sql.Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(java.sql.Timestamp endTime) {
        this.endTime = endTime;
    }
}
