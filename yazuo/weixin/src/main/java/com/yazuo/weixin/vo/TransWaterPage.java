package com.yazuo.weixin.vo;

/**
* @ClassName TransWaterPage
* @Description 交易流水
* @author sundongfeng@yazuo.com
* @date 2014-8-12 上午11:27:55
* @version 1.0
*/
public class TransWaterPage {
	private Integer id;
	private Integer membershipId;
	private Integer transMerchantId;
	private String transMerchant;
	private String transMerchantName;
	private String transTime;
	private Double total;
	private Double cash;
	private Double integral;
	private Double store;
	private Double coupon;
	private String evaluateId;
	private Double score;
	private String content;
	private boolean out;//是否过期
	
	public boolean isOut() {
		return out;
	}
	public void setOut(boolean out) {
		this.out = out;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(Integer membershipId) {
		this.membershipId = membershipId;
	}
	public Integer getTransMerchantId() {
		return transMerchantId;
	}
	public void setTransMerchantId(Integer transMerchantId) {
		this.transMerchantId = transMerchantId;
	}
	public String getTransMerchant() {
		return transMerchant;
	}
	public void setTransMerchant(String transMerchant) {
		this.transMerchant = transMerchant;
	}
	public String getTransMerchantName() {
		return transMerchantName;
	}
	public void setTransMerchantName(String transMerchantName) {
		this.transMerchantName = transMerchantName;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getCash() {
		return cash;
	}
	public void setCash(Double cash) {
		this.cash = cash;
	}
	
	public Double getIntegral() {
		return integral;
	}
	public void setIntegral(Double integral) {
		if(integral==null){
			integral=0d;
		}
		this.integral = integral;
	}
	public Double getStore() {
		return store;
	}
	public void setStore(Double store) {
		this.store = store;
	}
	public Double getCoupon() {
		return coupon;
	}
	public void setCoupon(Double coupon) {
		this.coupon = coupon;
	}
	public String getEvaluateId() {
		return evaluateId;
	}
	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId==null?"":evaluateId.trim();
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		if(score==null){
			score=0d;
		}
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		if(content==null){
			content="";
		}
		this.content = content;
	}
	
	
}
