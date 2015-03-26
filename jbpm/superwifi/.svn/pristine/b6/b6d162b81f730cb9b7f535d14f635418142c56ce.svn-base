package com.yazuo.api.service.account.vo;

import java.io.Serializable;

import net.sf.json.JSONObject;

import com.yazuo.api.service.account.vo.userInfo.UserInfoVo;

public class LoginUser implements Serializable {
	private int code;
	private String desc;
	private UserInfoVo userInfoVo;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public UserInfoVo getUserInfoVo() {
		return userInfoVo;
	}

	public void setUserInfoVo(UserInfoVo userInfoVo) {
		this.userInfoVo = userInfoVo;
	}

	public String toString() {
		try {
			return JSONObject.fromObject(this).toString();
		} catch (Exception e) {
			return null;
		}
	}

}
