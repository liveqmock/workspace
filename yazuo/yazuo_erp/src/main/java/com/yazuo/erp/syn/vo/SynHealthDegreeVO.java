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

package com.yazuo.erp.syn.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.syn.vo.*;
import com.yazuo.erp.syn.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class SynHealthDegreeVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "健康度";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_TARGET_TYPE = "指标类型";
	public static final String ALIAS_TARGET_VALUE = "目标值";
	public static final String ALIAS_ACTUAL_VALUE = "实际值";
	public static final String ALIAS_HEALTH_DEGREE = "健康度";
	public static final String ALIAS_REPORT_TIME = "时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_TARGET_TYPE = "targetType";
	public static final String COLUMN_TARGET_VALUE = "targetValue";
	public static final String COLUMN_ACTUAL_VALUE = "actualValue";
	public static final String COLUMN_HEALTH_DEGREE = "healthDegree";
	public static final String COLUMN_REPORT_TIME = "reportTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String targetType; //"指标类型";
	private java.math.BigDecimal targetValue; //"目标值";
	private java.math.BigDecimal actualValue; //"实际值";
	private java.math.BigDecimal healthDegree; //"健康度";
	private java.util.Date reportTime; //"时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END

	private String targetTypeText = null; // 目标类型显示文字
	
	public String getTargetTypeText() {
		return targetTypeText;
	}

	public void setTargetTypeText(String targetTypeText) {
		this.targetTypeText = targetTypeText;
	}
	public SynHealthDegreeVO(){
	}

	public SynHealthDegreeVO(
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
	public void setTargetType(java.lang.String value) {
		this.targetType = value;
	}
	
	public java.lang.String getTargetType() {
		return this.targetType;
	}
	public void setTargetValue(java.math.BigDecimal value) {
		this.targetValue = value;
	}
	
	public java.math.BigDecimal getTargetValue() {
		return this.targetValue;
	}
	public void setActualValue(java.math.BigDecimal value) {
		this.actualValue = value;
	}
	
	public java.math.BigDecimal getActualValue() {
		return this.actualValue;
	}
	public void setHealthDegree(java.math.BigDecimal value) {
		this.healthDegree = value;
	}
	
	public java.math.BigDecimal getHealthDegree() {
		return this.healthDegree;
	}
	public void setReportTime(java.util.Date value) {
		this.reportTime = value;
	}
	
	public java.util.Date getReportTime() {
		return this.reportTime;
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
			.append("TargetType",getTargetType())
			.append("TargetValue",getTargetValue())
			.append("ActualValue",getActualValue())
			.append("HealthDegree",getHealthDegree())
			.append("ReportTime",getReportTime())
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
		if(obj instanceof SynHealthDegreeVO == false) return false;
		if(this == obj) return true;
		SynHealthDegreeVO other = (SynHealthDegreeVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

