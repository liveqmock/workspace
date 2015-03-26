package com.yazuo.superwifi.merchant.vo;
import java.util.Date;

public class AwardRule implements java.io.Serializable{
	/**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -5923758892699955353L;
    private Integer id;
	private java.math.BigDecimal lowerLimit;
	private java.math.BigDecimal upperLimit;
	private java.math.BigDecimal awardAmount;
	private String transCode;
	private Integer couponTypeId;
	private Integer cardtypeId;
	private Integer merchantId;
	private Integer scope;
	private Integer awardType;
	private Integer awardAmountType;
	private Boolean againFlag;
	private Integer reuseFlag;
	private Integer consumeCouponBatch;
	private java.math.BigDecimal baseLimit;
	private String sourceLimit;
	private Integer birthdayDoubleFlag;
	private Integer birthmonthDoubleFlag;
	private Date activeBegin;
	private Date activeEnd;
	private Integer activeId;
	private String membershipGrade;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public java.math.BigDecimal getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(java.math.BigDecimal lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	
	public java.math.BigDecimal getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(java.math.BigDecimal upperLimit) {
		this.upperLimit = upperLimit;
	}
	
	public java.math.BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(java.math.BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}
	
	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	
	public Integer getCouponTypeId() {
		return couponTypeId;
	}

	public void setCouponTypeId(Integer couponTypeId) {
		this.couponTypeId = couponTypeId;
	}
	
	public Integer getCardtypeId() {
		return cardtypeId;
	}

	public void setCardtypeId(Integer cardtypeId) {
		this.cardtypeId = cardtypeId;
	}
	
	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	
	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}
	
	public Integer getAwardType() {
		return awardType;
	}

	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}
	
	public Integer getAwardAmountType() {
		return awardAmountType;
	}

	public void setAwardAmountType(Integer awardAmountType) {
		this.awardAmountType = awardAmountType;
	}
	
	public Boolean getAgainFlag() {
		return againFlag;
	}

	public void setAgainFlag(Boolean againFlag) {
		this.againFlag = againFlag;
	}
	
	public Integer getReuseFlag() {
		return reuseFlag;
	}

	public void setReuseFlag(Integer reuseFlag) {
		this.reuseFlag = reuseFlag;
	}
	
	public Integer getConsumeCouponBatch() {
		return consumeCouponBatch;
	}

	public void setConsumeCouponBatch(Integer consumeCouponBatch) {
		this.consumeCouponBatch = consumeCouponBatch;
	}
	
	public java.math.BigDecimal getBaseLimit() {
		return baseLimit;
	}

	public void setBaseLimit(java.math.BigDecimal baseLimit) {
		this.baseLimit = baseLimit;
	}
	
	public String getSourceLimit() {
		return sourceLimit;
	}

	public void setSourceLimit(String sourceLimit) {
		this.sourceLimit = sourceLimit;
	}
	
	public Integer getBirthdayDoubleFlag() {
		return birthdayDoubleFlag;
	}

	public void setBirthdayDoubleFlag(Integer birthdayDoubleFlag) {
		this.birthdayDoubleFlag = birthdayDoubleFlag;
	}
	
	public Integer getBirthmonthDoubleFlag() {
		return birthmonthDoubleFlag;
	}

	public void setBirthmonthDoubleFlag(Integer birthmonthDoubleFlag) {
		this.birthmonthDoubleFlag = birthmonthDoubleFlag;
	}
	
	public Date getActiveBegin() {
		return activeBegin;
	}

	public void setActiveBegin(Date activeBegin) {
		this.activeBegin = activeBegin;
	}
	
	public Date getActiveEnd() {
		return activeEnd;
	}

	public void setActiveEnd(Date activeEnd) {
		this.activeEnd = activeEnd;
	}
	
	public Integer getActiveId() {
		return activeId;
	}

	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}
	
	public String getMembershipGrade() {
		return membershipGrade;
	}

	public void setMembershipGrade(String membershipGrade) {
		this.membershipGrade = membershipGrade;
	}
}
