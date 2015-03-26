package com.yazuo.api.service.account.vo.userInfo;

import net.sf.json.JSONObject;

public class UserInfoVo implements java.io.Serializable {
	private Integer userInfoId;
	private String userInfoName;
	private String mobile;
	private String email;
	private Boolean availableFlag;
	private Integer merchantId;
	private String terminalNo;
	private Integer tagId;
	private Long tempPwdExpireTime;

	public Integer getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(Integer userInfoId) {
		this.userInfoId = userInfoId;
	}

	public String getUserInfoName() {
		return userInfoName;
	}

	public void setUserInfoName(String userInfoName) {
		this.userInfoName = userInfoName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAvailableFlag() {
		return availableFlag;
	}

	public void setAvailableFlag(Boolean availableFlag) {
		this.availableFlag = availableFlag;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Long getTempPwdExpireTime() {
		return tempPwdExpireTime;
	}

	public void setTempPwdExpireTime(Long tempPwdExpireTime) {
		this.tempPwdExpireTime = tempPwdExpireTime;
	}

	public String toString() {
		try {
			return JSONObject.fromObject(this).toString();
		} catch (Exception e) {
			return null;
		}
	}
}
