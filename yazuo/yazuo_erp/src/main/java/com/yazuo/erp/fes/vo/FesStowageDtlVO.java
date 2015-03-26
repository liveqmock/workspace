/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author erp team
 * @date 
 */
public class FesStowageDtlVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "配载明细";
	public static final String ALIAS_ID = "配载明细ID";
	public static final String ALIAS_STOWAGE_ID = "配载信息ID";
	public static final String ALIAS_CATEGORY = "品类";
	public static final String ALIAS_AMOUNT = "数量";
	public static final String ALIAS_UNIT_TYPE = "单位";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_STOWAGE_ID = "stowageId";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_UNIT_TYPE = "unitType";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";

	//columns START
	private java.lang.Integer id; //"配载明细ID";
	private java.lang.Integer stowageId; //"配载信息ID";
	private java.lang.String category; //"品类";
	private java.math.BigDecimal amount; //"数量";
	private java.lang.String unitType; //"单位";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	//columns END
	private java.lang.Integer processId; //"流程ID";
	private java.lang.String logisticsCompany; //"物流公司";
	private java.lang.String logisticsNum; //"物流单号";
	
	public java.lang.Integer getProcessId() {
		return processId;
	}

	public void setProcessId(java.lang.Integer processId) {
		this.processId = processId;
	}

	public java.lang.String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(java.lang.String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public java.lang.String getLogisticsNum() {
		return logisticsNum;
	}

	public void setLogisticsNum(java.lang.String logisticsNum) {
		this.logisticsNum = logisticsNum;
	}

	public FesStowageDtlVO(){
	}

	public FesStowageDtlVO(
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
	public void setStowageId(java.lang.Integer value) {
		this.stowageId = value;
	}
	
	public java.lang.Integer getStowageId() {
		return this.stowageId;
	}
	public void setCategory(java.lang.String value) {
		this.category = value;
	}
	
	public java.lang.String getCategory() {
		return this.category;
	}
	public void setAmount(java.math.BigDecimal value) {
		this.amount = value;
	}
	
	public java.math.BigDecimal getAmount() {
		return this.amount;
	}
	public void setUnitType(java.lang.String value) {
		this.unitType = value;
	}
	
	public java.lang.String getUnitType() {
		return this.unitType;
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

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("StowageId",getStowageId())
			.append("Category",getCategory())
			.append("Amount",getAmount())
			.append("UnitType",getUnitType())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof FesStowageDtlVO == false) return false;
		if(this == obj) return true;
		FesStowageDtlVO other = (FesStowageDtlVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

