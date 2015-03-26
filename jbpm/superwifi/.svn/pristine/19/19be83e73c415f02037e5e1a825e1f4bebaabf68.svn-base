package com.yazuo.api.service.account.vo.role;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 返回角色对象
 * 
 * @author libin
 * 
 */
public class RoleVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3238870903926117981L;
	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private Boolean commonFlag;
	private Integer merchantId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Boolean getCommonFlag() {
		return commonFlag;
	}

	public void setCommonFlag(Boolean commonFlag) {
		this.commonFlag = commonFlag;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String toString() {
		try {
			return JSONObject.fromObject(this).toString();
		} catch (Exception e) {
			return null;
		}
	}
}
