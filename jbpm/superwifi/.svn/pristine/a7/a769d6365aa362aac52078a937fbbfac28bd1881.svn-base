package com.yazuo.api.service.account.vo;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1313208188754923737L;
	// 用户名
	private String userInfoName;
	// 用户手机号， 登录名
	private String mobile;
	// 商户ID
	private int merchantId;
	// 产品的权限ID
	private List<Integer> authorityIdList;
	// 产品ID
	private int productId;
	// 用户ＩＤ，　ID存在为更新
	private int userInfoId;
	// 角色ID
	private int roleId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public List<Integer> getAuthorityIdList() {
		return authorityIdList;
	}

	public void setAuthorityIdList(List<Integer> authorityIdList) {
		this.authorityIdList = authorityIdList;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(int userInfoId) {
		this.userInfoId = userInfoId;
	}

	@Override
	public String toString() {
		try {
			return JSONObject.fromObject(this).toString();
		} catch (Exception e) {
			return null;
		}
	}

}
