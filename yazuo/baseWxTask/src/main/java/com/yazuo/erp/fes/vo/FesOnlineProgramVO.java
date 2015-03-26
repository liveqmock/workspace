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

package com.yazuo.erp.fes.vo;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @Description TODO
 * @author erp team
 * @date
 */
public class FesOnlineProgramVO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "上线计划";
	public static final String ALIAS_ID = "上线计划ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_BEGIN_TIME = "启动时间";
	public static final String ALIAS_PLAN_ONLINE_TIME = "计划上线时间";
	public static final String ALIAS_ONLINE_TIME = "实际上线时间";
	public static final String ALIAS_ONLINE_PROGRAM_STATUS = "状态";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_BEGIN_TIME = "beginTime";
	public static final String COLUMN_PLAN_ONLINE_TIME = "planOnlineTime";
	public static final String COLUMN_ONLINE_TIME = "onlineTime";
	public static final String COLUMN_ONLINE_PROGRAM_STATUS = "onlineProgramStatus";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	// columns START
	private java.lang.Integer id; // "上线计划ID";
	private java.lang.Integer merchantId; // "商户ID";
	private java.util.Date beginTime; // "启动时间";
	private java.util.Date planOnlineTime; // "计划上线时间";
	private java.util.Date onlineTime; // "实际上线时间";
	private java.lang.String onlineProgramStatus; // "状态";
	private java.lang.String remark; // "备注";
	private java.lang.Integer insertBy; // "创建人";
	private java.util.Date insertTime; // "创建时间";
	private java.lang.Integer updateBy; // "最后修改人";
	private java.util.Date updateTime; // "最后修改时间";
	private java.lang.String merchantName;// 商户名称

	// columns END

	public FesOnlineProgramVO() {
	}

	public FesOnlineProgramVO(java.lang.Integer id) {
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

	public void setBeginTime(java.util.Date value) {
		this.beginTime = value;
	}

	public java.util.Date getBeginTime() {
		return this.beginTime;
	}

	public void setPlanOnlineTime(java.util.Date value) {
		this.planOnlineTime = value;
	}

	public java.util.Date getPlanOnlineTime() {
		return this.planOnlineTime;
	}

	public void setOnlineTime(java.util.Date value) {
		this.onlineTime = value;
	}

	public java.util.Date getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineProgramStatus(java.lang.String value) {
		this.onlineProgramStatus = value;
	}

	public java.lang.String getOnlineProgramStatus() {
		return this.onlineProgramStatus;
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

	public java.lang.String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(java.lang.String merchantName) {
		this.merchantName = merchantName;
	}

	private String sortColumns;

	public String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	private Set fesOnlineProcesss = new HashSet(0);

	public void setFesOnlineProcesss(Set<FesOnlineProcessVO> fesOnlineProcess) {
		this.fesOnlineProcesss = fesOnlineProcess;
	}

	public Set<FesOnlineProcessVO> getFesOnlineProcesss() {
		return fesOnlineProcesss;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId())
				.append("MerchantId", getMerchantId()).append("BeginTime", getBeginTime())
				.append("PlanOnlineTime", getPlanOnlineTime()).append("OnlineTime", getOnlineTime())
				.append("OnlineProgramStatus", getOnlineProgramStatus()).append("Remark", getRemark())
				.append("InsertBy", getInsertBy()).append("InsertTime", getInsertTime()).append("UpdateBy", getUpdateBy())
				.append("UpdateTime", getUpdateTime()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof FesOnlineProgramVO == false)
			return false;
		if (this == obj)
			return true;
		FesOnlineProgramVO other = (FesOnlineProgramVO) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
