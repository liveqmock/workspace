package com.yazuo.weixin.vo;

/**
* @ClassName WxPayDeliverStateVo
* @Description 发货状态vo类
* @author sundongfeng@yazuo.com
* @date 2014-6-20 下午2:37:12
* @version 1.0
*/
public class WxPayDeliverStateVo {
	private String mobile;
	private String app_id;
	private String open_id;
	private String transaction_id;
	private String out_trade_no;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
}
