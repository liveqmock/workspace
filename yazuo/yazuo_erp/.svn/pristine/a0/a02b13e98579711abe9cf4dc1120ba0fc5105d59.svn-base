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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class FesMaterialRequestDtlVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "设计需求单明细";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MATERIAL_REQUEST_ID = "设计需求单ID";
	public static final String ALIAS_MATERIAL_REQUEST_TYPE = "设计需求类型";
	public static final String ALIAS_SPECIFICATION_TYPE = "设计规格类型";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MATERIAL_REQUEST_ID = "materialRequestId";
	public static final String COLUMN_MATERIAL_REQUEST_TYPE = "materialRequestType";
	public static final String COLUMN_SPECIFICATION_TYPE = "specificationType";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer materialRequestId; //"设计需求单ID";
	private java.lang.String materialRequestType; //"设计需求类型";
	private java.lang.String specificationType; //"设计规格类型";
	private java.lang.String description; //"描述";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	
	private java.lang.String specificationText; //"设计规格类型 文本";
	
	
	public java.lang.String getSpecificationText() {
		return specificationText;
	}

	public void setSpecificationText(java.lang.String specificationText) {
		this.specificationText = specificationText;
	}

	public FesMaterialRequestDtlVO(){
	}

	public FesMaterialRequestDtlVO(
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
	public void setMaterialRequestId(java.lang.Integer value) {
		this.materialRequestId = value;
	}
	
	public java.lang.Integer getMaterialRequestId() {
		return this.materialRequestId;
	}
	public void setMaterialRequestType(java.lang.String value) {
		this.materialRequestType = value;
	}
	
	public java.lang.String getMaterialRequestType() {
		return this.materialRequestType;
	}
	public void setSpecificationType(java.lang.String value) {
		this.specificationType = value;
	}
	
	public java.lang.String getSpecificationType() {
		return this.specificationType;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
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
			.append("MaterialRequestId",getMaterialRequestId())
			.append("MaterialRequestType",getMaterialRequestType())
			.append("SpecificationType",getSpecificationType())
			.append("Description",getDescription())
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
		if(obj instanceof FesMaterialRequestDtlVO == false) return false;
		if(this == obj) return true;
		FesMaterialRequestDtlVO other = (FesMaterialRequestDtlVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

