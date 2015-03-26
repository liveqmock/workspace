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

package com.yazuo.erp.bes.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class BesFormerUserMerchantVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "老用户-商户关系表";
	public static final String ALIAS_ID = "老用户商户关系表ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_BEGIN_TIME = "服务开始时间";
	public static final String ALIAS_END_TIME = "服务结束时间";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_USER_ID = "userId";
	public static final String COLUMN_BEGIN_TIME = "beginTime";
	public static final String COLUMN_END_TIME = "endTime";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"老用户商户关系表ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.Integer userId; //"用户ID";
	private java.util.Date beginTime; //"服务开始时间";
	private java.util.Date endTime; //"服务结束时间";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END

	public BesFormerUserMerchantVO(){
	}

	public BesFormerUserMerchantVO(
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
	
	public java.lang.Integer getMerchantId() {
		return this.merchantId;
	}
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setBeginTime(java.util.Date value) {
		this.beginTime = value;
	}
	
	public java.util.Date getBeginTime() {
		return this.beginTime;
	}
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
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
	
	private SysUserVO sysUser;
	
	public void setSysUser(SysUserVO sysUser){
		this.sysUser = sysUser;
	}
	
	public SysUserVO getSysUser() {
		return sysUser;
	}
	
	private SynMerchantVO synMerchant;
	
	public void setSynMerchant(SynMerchantVO synMerchant){
		this.synMerchant = synMerchant;
	}
	
	public SynMerchantVO getSynMerchant() {
		return synMerchant;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("MerchantId",getMerchantId())
			.append("UserId",getUserId())
			.append("BeginTime",getBeginTime())
			.append("EndTime",getEndTime())
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
		if(obj instanceof BesFormerUserMerchantVO == false) return false;
		if(this == obj) return true;
		BesFormerUserMerchantVO other = (BesFormerUserMerchantVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

