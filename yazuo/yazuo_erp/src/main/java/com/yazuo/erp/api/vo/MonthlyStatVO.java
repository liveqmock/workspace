package com.yazuo.erp.api.vo;

/**
 * 月度会员统计数据
 */
public class MonthlyStatVO {
    private Integer newMember;//新增会员
    private Integer fansMember;//新增粉丝会员
    private Integer integralMember;//新增积分会员
    private Integer storeMember;//新增积分会员
    private Double  storePay;//新增储值
//    private Double storeBalance;//储值沉淀
    private Double realMarketingIncome;//营销收入

    public Integer getNewMember() {
        return newMember;
    }

    public void setNewMember(Integer newMember) {
        this.newMember = newMember;
    }

    public Integer getFansMember() {
        return fansMember;
    }

    public void setFansMember(Integer fansMember) {
        this.fansMember = fansMember;
    }

    public Integer getIntegralMember() {
        return integralMember;
    }

    public void setIntegralMember(Integer integralMember) {
        this.integralMember = integralMember;
    }

    public Integer getStoreMember() {
        return storeMember;
    }

    public void setStoreMember(Integer storeMember) {
        this.storeMember = storeMember;
    }

    public Double getStorePay() {
        return storePay;
    }

    public void setStorePay(Double storePay) {
        this.storePay = storePay;
    }

    public Double getRealMarketingIncome() {
        return realMarketingIncome;
    }

    public void setRealMarketingIncome(Double realMarketingIncome) {
        this.realMarketingIncome = realMarketingIncome;
    }
}
