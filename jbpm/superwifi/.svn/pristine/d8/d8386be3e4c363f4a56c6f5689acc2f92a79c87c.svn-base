package com.yazuo.api.service.merchant;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 门店信息
 * 
 * @author libin
 * 
 */
public class MerchantVo implements Serializable {
    // 门店ID
    private Integer merchantId;
    // 门店名称
    private String merchantName;
    // 老板名字
    private String bossName;
    // 老板电话
    private String bossMobile;
    // 集团ID
    private Integer brandId;

    private Integer parentId;

    /**
     * 备注， 最长100
     */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossMobile() {
        return bossMobile;
    }

    public void setBossMobile(String bossMobile) {
        this.bossMobile = bossMobile;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
