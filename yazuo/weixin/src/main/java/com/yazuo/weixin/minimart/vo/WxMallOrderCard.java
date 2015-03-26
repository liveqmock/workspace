package com.yazuo.weixin.minimart.vo;

/**
* @ClassName WxMallOrderCard
* @Description 订单绑卡
* @author sundongfeng@yazuo.com
* @date 2014-10-22 下午6:13:56
* @version 1.0
*/
public class WxMallOrderCard {
	 private Integer id;
	 private String outTradeNo	;
	 private String cardNo;
	 private Integer cardStatus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}
	 
}
