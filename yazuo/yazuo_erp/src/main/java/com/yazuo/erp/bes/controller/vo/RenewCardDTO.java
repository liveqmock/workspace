package com.yazuo.erp.bes.controller.vo;

/**
 * 续卡搜索框
 */
public class RenewCardDTO {
    String merchantName;
    int availDateFrom;
    int availDateTo;
    String merchantStatus;
    String cardType;//卡分类：实现或非实体
    Integer pageSize;
    Integer pageNumber;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getAvailDateFrom() {
        return availDateFrom;
    }

    public void setAvailDateFrom(int availDateFrom) {
        this.availDateFrom = availDateFrom;
    }

    public int getAvailDateTo() {
        return availDateTo;
    }

    public void setAvailDateTo(int availDateTo) {
        this.availDateTo = availDateTo;
    }

    public String getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(String merchantStatus) {
        this.merchantStatus = merchantStatus;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "RenewCardDTO{" +
                "merchantName='" + merchantName + '\'' +
                ", availDateFrom=" + availDateFrom +
                ", availDateTo=" + availDateTo +
                ", merchantStatus='" + merchantStatus + '\'' +
                ", cardType='" + cardType + '\'' +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
