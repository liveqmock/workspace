package com.yazuo.api.service.account.vo.authority;

import net.sf.json.JSONObject;

public class AuthorityVo implements java.io.Serializable {
	private Integer authorityId;
	private String authorityName;
	private String authorityDesc;
	private Integer productId;
	private String authorityCode;

	public Integer getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getAuthorityDesc() {
		return authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	public String toString() {
		try {
			return JSONObject.fromObject(this).toString();
		} catch (Exception e) {
			return null;
		}
	}
}
