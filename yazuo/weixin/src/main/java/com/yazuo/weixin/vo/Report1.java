package com.yazuo.weixin.vo;

import java.math.BigDecimal;

public class Report1 {

	
	//{"message":"","data":{"info":{"new_nember":195307,"new_card":196980,"cash_consume_amount":5.6972033918E8,"integral_consume":1.941686854E7,
	//"store_consume":5.382230705E7,"coupon_quantity":148261,"coupon_quantity_amount":4673056.3,
	//"marketing_income":4.2879536514E8,"storage_recharge":5.043354437E7,"store_reward":2325239.26,
	//"new_integral":5.052078092E7}},"code":1,"success":true}
	private String message;
	private Data data;
	private String code;
	private Boolean success;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
}

class Data{
	private Info info;

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
	
}
class Info{
	private BigDecimal new_nember;
	private BigDecimal new_card;
	private BigDecimal cash_consume_amount;
	private BigDecimal integral_consume;
	private BigDecimal store_consume;
	private BigDecimal coupon_quantity;
	private BigDecimal coupon_quantity_amount;
	private BigDecimal marketing_income;
	private BigDecimal storage_recharge;
	private BigDecimal store_reward;
	private BigDecimal new_integral;
	public BigDecimal getNew_nember() {
		return new_nember;
	}
	public void setNew_nember(BigDecimal new_nember) {
		this.new_nember = new_nember;
	}
	public BigDecimal getNew_card() {
		return new_card;
	}
	public void setNew_card(BigDecimal new_card) {
		this.new_card = new_card;
	}
	public BigDecimal getCash_consume_amount() {
		return cash_consume_amount;
	}
	public void setCash_consume_amount(BigDecimal cash_consume_amount) {
		this.cash_consume_amount = cash_consume_amount;
	}
	public BigDecimal getIntegral_consume() {
		return integral_consume;
	}
	public void setIntegral_consume(BigDecimal integral_consume) {
		this.integral_consume = integral_consume;
	}
	public BigDecimal getStore_consume() {
		return store_consume;
	}
	public void setStore_consume(BigDecimal store_consume) {
		this.store_consume = store_consume;
	}
	public BigDecimal getCoupon_quantity() {
		return coupon_quantity;
	}
	public void setCoupon_quantity(BigDecimal coupon_quantity) {
		this.coupon_quantity = coupon_quantity;
	}
	public BigDecimal getCoupon_quantity_amount() {
		return coupon_quantity_amount;
	}
	public void setCoupon_quantity_amount(BigDecimal coupon_quantity_amount) {
		this.coupon_quantity_amount = coupon_quantity_amount;
	}
	public BigDecimal getMarketing_income() {
		return marketing_income;
	}
	public void setMarketing_income(BigDecimal marketing_income) {
		this.marketing_income = marketing_income;
	}
	public BigDecimal getStorage_recharge() {
		return storage_recharge;
	}
	public void setStorage_recharge(BigDecimal storage_recharge) {
		this.storage_recharge = storage_recharge;
	}
	public BigDecimal getStore_reward() {
		return store_reward;
	}
	public void setStore_reward(BigDecimal store_reward) {
		this.store_reward = store_reward;
	}
	public BigDecimal getNew_integral() {
		return new_integral;
	}
	public void setNew_integral(BigDecimal new_integral) {
		this.new_integral = new_integral;
	}
	
}