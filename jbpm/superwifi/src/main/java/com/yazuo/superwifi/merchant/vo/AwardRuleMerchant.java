package com.yazuo.superwifi.merchant.vo;

public class AwardRuleMerchant implements java.io.Serializable{
	/**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -8449127173386592789L;
    private Integer id;
	private String merchantNo;
	private Integer ruleId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
}
