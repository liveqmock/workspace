/*
 * 文件名：DailyMerchantSummary.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014-7-9
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.external.statictics.vo;

import java.math.BigDecimal;

public class MonthMerchantSummary {
	private Integer merchantId;
	private Integer newMember;// 新增会员
	private Integer newCard;// 新增卡
	private Integer fansMember;// 新增粉丝会员
	private Integer integralMember;// 新增积分会员
	private Integer storeMember;// 新增储值会员
	private BigDecimal integralMemberRate;
	private Integer realcard;// 新增售卡量
	private BigDecimal cardIncome;// 售卡卡收益
	private BigDecimal storePay;// 新增储值充值
	private BigDecimal storeConsume;// 储值消费
	private BigDecimal integralPay;// 积分充值
	private BigDecimal integralConsume;// 积分消费
	private BigDecimal storeDoposit;//
	private BigDecimal cash;// 现金消费
	private BigDecimal couponAmount;// 新增券消费
	private Integer couponQuantity;// 券消费数量
	private BigDecimal cashIncome;// 现金收益
	private BigDecimal marketingIncome;// 营销收益
	private Integer evaluateQuantity;// 评价笔数
	private Integer evaluateSms;// 评价下发短信数
	private Integer evaluateScore;// 会员评价总分
	private Integer wellEvaluateQuantity;// 好评数
	private Integer middleEvaluateQuantity;// 中评数
	private Integer poorEvaluateQuantity;// 差评数
	private Integer transQuantity;// 会员交易量
	private Integer oldTransQuantity;// 老会员交易笔数
	private Integer firstConsumeNum;
	private Integer secondConsumeNum;
	private Integer thirdConsumeNum;
	private Integer brandId;// 品牌ID

	private Integer yearMember;// 年度会员数
	private BigDecimal totalConsume;// 会员总消费
	private BigDecimal yearCardInconsume;// 年度售卡收益累计
	private BigDecimal yearMarketingConsume;// 营销年度收益累计
	private BigDecimal storeBalance;// 储值沉淀
	private BigDecimal consumedAvg;// 会员桌均消费
	private BigDecimal consumedRate;// 会员活跃度
	private BigDecimal evaluateWellRate;// 会员满意度
	private BigDecimal evaluateRate;// 会员评价率

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getNewMember() {
		return newMember;
	}

	public void setNewMember(Integer newMember) {
		this.newMember = newMember;
	}

	public Integer getNewCard() {
		return newCard;
	}

	public void setNewCard(Integer newCard) {
		this.newCard = newCard;
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

	public Integer getRealcard() {
		return realcard;
	}

	public void setRealcard(Integer realcard) {
		this.realcard = realcard;
	}

	public BigDecimal getCardIncome() {
		return cardIncome;
	}

	public void setCardIncome(BigDecimal cardIncome) {
		this.cardIncome = cardIncome;
	}

	public BigDecimal getStorePay() {
		return storePay;
	}

	public void setStorePay(BigDecimal storePay) {
		this.storePay = storePay;
	}

	public BigDecimal getStoreConsume() {
		return storeConsume;
	}

	public void setStoreConsume(BigDecimal storeConsume) {
		this.storeConsume = storeConsume;
	}

	public BigDecimal getIntegralPay() {
		return integralPay;
	}

	public void setIntegralPay(BigDecimal integralPay) {
		this.integralPay = integralPay;
	}

	public BigDecimal getIntegralConsume() {
		return integralConsume;
	}

	public void setIntegralConsume(BigDecimal integralConsume) {
		this.integralConsume = integralConsume;
	}

	public BigDecimal getStoreDoposit() {
		return storeDoposit;
	}

	public void setStoreDoposit(BigDecimal storeDoposit) {
		this.storeDoposit = storeDoposit;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Integer getCouponQuantity() {
		return couponQuantity;
	}

	public void setCouponQuantity(Integer couponQuantity) {
		this.couponQuantity = couponQuantity;
	}

	public BigDecimal getCashIncome() {
		return cashIncome;
	}

	public void setCashIncome(BigDecimal cashIncome) {
		this.cashIncome = cashIncome;
	}

	public BigDecimal getMarketingIncome() {
		return marketingIncome;
	}

	public void setMarketingIncome(BigDecimal marketingIncome) {
		this.marketingIncome = marketingIncome;
	}

	public Integer getEvaluateQuantity() {
		return evaluateQuantity;
	}

	public void setEvaluateQuantity(Integer evaluateQuantity) {
		this.evaluateQuantity = evaluateQuantity;
	}

	public Integer getEvaluateSms() {
		return evaluateSms;
	}

	public void setEvaluateSms(Integer evaluateSms) {
		this.evaluateSms = evaluateSms;
	}

	public Integer getEvaluateScore() {
		return evaluateScore;
	}

	public void setEvaluateScore(Integer evaluateScore) {
		this.evaluateScore = evaluateScore;
	}

	public Integer getWellEvaluateQuantity() {
		return wellEvaluateQuantity;
	}

	public void setWellEvaluateQuantity(Integer wellEvaluateQuantity) {
		this.wellEvaluateQuantity = wellEvaluateQuantity;
	}

	public Integer getMiddleEvaluateQuantity() {
		return middleEvaluateQuantity;
	}

	public void setMiddleEvaluateQuantity(Integer middleEvaluateQuantity) {
		this.middleEvaluateQuantity = middleEvaluateQuantity;
	}

	public Integer getPoorEvaluateQuantity() {
		return poorEvaluateQuantity;
	}

	public void setPoorEvaluateQuantity(Integer poorEvaluateQuantity) {
		this.poorEvaluateQuantity = poorEvaluateQuantity;
	}

	public Integer getTransQuantity() {
		return transQuantity;
	}

	public void setTransQuantity(Integer transQuantity) {
		this.transQuantity = transQuantity;
	}

	public Integer getOldTransQuantity() {
		return oldTransQuantity;
	}

	public void setOldTransQuantity(Integer oldTransQuantity) {
		this.oldTransQuantity = oldTransQuantity;
	}

	public Integer getFirstConsumeNum() {
		return firstConsumeNum;
	}

	public void setFirstConsumeNum(Integer firstConsumeNum) {
		this.firstConsumeNum = firstConsumeNum;
	}

	public Integer getSecondConsumeNum() {
		return secondConsumeNum;
	}

	public void setSecondConsumeNum(Integer secondConsumeNum) {
		this.secondConsumeNum = secondConsumeNum;
	}

	public Integer getThirdConsumeNum() {
		return thirdConsumeNum;
	}

	public void setThirdConsumeNum(Integer thirdConsumeNum) {
		this.thirdConsumeNum = thirdConsumeNum;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getYearMember() {
		return yearMember;
	}

	public void setYearMember(Integer yearMember) {
		this.yearMember = yearMember;
	}

	public BigDecimal getTotalConsume() {
		return totalConsume;
	}

	public void setTotalConsume(BigDecimal totalConsume) {
		this.totalConsume = totalConsume;
	}

	public BigDecimal getYearCardInconsume() {
		return yearCardInconsume;
	}

	public void setYearCardInconsume(BigDecimal yearCardInconsume) {
		this.yearCardInconsume = yearCardInconsume;
	}

	public BigDecimal getYearMarketingConsume() {
		return yearMarketingConsume;
	}

	public void setYearMarketingConsume(BigDecimal yearMarketingConsume) {
		this.yearMarketingConsume = yearMarketingConsume;
	}

	public BigDecimal getStoreBalance() {
		return storeBalance;
	}

	public void setStoreBalance(BigDecimal storeBalance) {
		this.storeBalance = storeBalance;
	}

	public BigDecimal getConsumedAvg() {
		return consumedAvg;
	}

	public void setConsumedAvg(BigDecimal consumedAvg) {
		this.consumedAvg = consumedAvg;
	}

	public BigDecimal getConsumedRate() {
		return consumedRate;
	}

	public void setConsumedRate(BigDecimal consumedRate) {
		this.consumedRate = consumedRate;
	}

	public BigDecimal getEvaluateWellRate() {
		return evaluateWellRate;
	}

	public void setEvaluateWellRate(BigDecimal evaluateWellRate) {
		this.evaluateWellRate = evaluateWellRate;
	}

	public BigDecimal getEvaluateRate() {
		return evaluateRate;
	}

	public void setEvaluateRate(BigDecimal evaluateRate) {
		this.evaluateRate = evaluateRate;
	}

	public BigDecimal getIntegralMemberRate() {
		return integralMemberRate;
	}

	public void setIntegralMemberRate(BigDecimal integralMemberRate) {
		this.integralMemberRate = integralMemberRate;
	}

}
