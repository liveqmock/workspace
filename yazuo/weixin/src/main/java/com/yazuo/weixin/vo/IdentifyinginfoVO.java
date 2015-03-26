package com.yazuo.weixin.vo;

import java.sql.Timestamp;

public class IdentifyinginfoVO {
	private String weixinId;
	private String identifyingCode;
	private Timestamp identifyingTime;
	private String phoneNo;
	private Integer brandId;
	public String getWeixinId() {
		return weixinId;
	}
	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}
	public String getIdentifyingCode() {
		return identifyingCode;
	}
	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}
	public Timestamp getIdentifyingTime() {
		return identifyingTime;
	}
	public void setIdentifyingTime(Timestamp identifyingTime) {
		this.identifyingTime = identifyingTime;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	
}
