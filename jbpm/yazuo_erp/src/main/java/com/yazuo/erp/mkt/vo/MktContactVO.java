/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;

import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class MktContactVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "通讯录";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_NAME = "姓名";
	public static final String ALIAS_GENDER_TYPE = "性别";
	public static final String ALIAS_BIRTHDAY = "生日";
	public static final String ALIAS_POSITION = "职位";
	public static final String ALIAS_MOBILE_PHONE = "手机";
	public static final String ALIAS_TELEPHONE = "座机";
	public static final String ALIAS_ROLE_TYPE = "角色类型";
	public static final String ALIAS_MAIL = "邮件";
	public static final String ALIAS_MICRO_MESSAGE = "微信";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_GENDER_TYPE = "genderType";
	public static final String COLUMN_BIRTHDAY = "birthday";
	public static final String COLUMN_POSITION = "position";
	public static final String COLUMN_MOBILE_PHONE = "mobilePhone";
	public static final String COLUMN_TELEPHONE = "telephone";
	public static final String COLUMN_ROLE_TYPE = "roleType";
	public static final String COLUMN_MAIL = "mail";
	public static final String COLUMN_MICRO_MESSAGE = "microMessage";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String name; //"姓名";
	private java.lang.String genderType; //"性别";
	private java.util.Date birthday; //"生日";
	private java.lang.String position; //"职位";
	private java.lang.String mobilePhone; //"手机";
	private java.lang.String telephone; //"座机";
	private java.lang.String roleType; //"角色类型";
	private java.lang.String mail; //"邮件";
	private java.lang.String microMessage; //"微信";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	
	private String merchantName; // 商户名称
	private String moduleType; // 模块类型
	private Map<String, Object> dicRole;//联系人角色
	//columns END

	public Map<String, Object> getDicRole() {
		return dicRole;
	}

	public void setDicRole(Map<String, Object> dicRole) {
		this.dicRole = dicRole;
	}

	public MktContactVO(){
	}

	public MktContactVO(
		java.lang.Integer id
	){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setMerchantId(java.lang.Integer value) {
		this.merchantId = value;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public java.lang.Integer getMerchantId() {
		return this.merchantId;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setGenderType(java.lang.String value) {
		this.genderType = value;
	}
	
	public java.lang.String getGenderType() {
		return this.genderType;
	}
	public void setBirthday(java.util.Date value) {
		this.birthday = value;
	}
	
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	public void setPosition(java.lang.String value) {
		this.position = value;
	}
	
	public java.lang.String getPosition() {
		return this.position;
	}
	public void setMobilePhone(java.lang.String value) {
		this.mobilePhone = value;
	}
	
	public java.lang.String getMobilePhone() {
		return this.mobilePhone;
	}
	public void setTelephone(java.lang.String value) {
		this.telephone = value;
	}
	
	public java.lang.String getTelephone() {
		return this.telephone;
	}
	public void setRoleType(java.lang.String value) {
		this.roleType = value;
	}
	
	public java.lang.String getRoleType() {
		return this.roleType;
	}
	public void setMail(java.lang.String value) {
		this.mail = value;
	}
	
	public java.lang.String getMail() {
		return this.mail;
	}
	public void setMicroMessage(java.lang.String value) {
		this.microMessage = value;
	}
	
	public java.lang.String getMicroMessage() {
		return this.microMessage;
	}
	public void setIsEnable(java.lang.String value) {
		this.isEnable = value;
	}
	
	public java.lang.String getIsEnable() {
		return this.isEnable;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setInsertBy(java.lang.Integer value) {
		this.insertBy = value;
	}
	
	public java.lang.Integer getInsertBy() {
		return this.insertBy;
	}
	public void setInsertTime(java.util.Date value) {
		this.insertTime = value;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateBy(java.lang.Integer value) {
		this.updateBy = value;
	}
	
	public java.lang.Integer getUpdateBy() {
		return this.updateBy;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
	

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("MerchantId",getMerchantId())
			.append("Name",getName())
			.append("GenderType",getGenderType())
			.append("Birthday",getBirthday())
			.append("Position",getPosition())
			.append("MobilePhone",getMobilePhone())
			.append("Telephone",getTelephone())
			.append("RoleType",getRoleType())
			.append("Mail",getMail())
			.append("MicroMessage",getMicroMessage())
			.append("IsEnable",getIsEnable())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MktContactVO == false) return false;
		if(this == obj) return true;
		MktContactVO other = (MktContactVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

