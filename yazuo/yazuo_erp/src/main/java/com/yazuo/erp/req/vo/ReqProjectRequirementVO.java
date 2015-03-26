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

package com.yazuo.erp.req.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.req.vo.*;
import com.yazuo.erp.req.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class ReqProjectRequirementVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "项目-需求关系表";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_PROJECT_ID = "项目ID";
	public static final String ALIAS_REQUIREMENT_ID = "需求ID";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PROJECT_ID = "projectId";
	public static final String COLUMN_REQUIREMENT_ID = "requirementId";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";

	//columns START
	private java.lang.Integer id;
	private java.lang.Integer projectId;
	private java.lang.Integer requirementId;
	private java.lang.Integer insertBy;
	private java.util.Date insertTime;
	//columns END

	public ReqProjectRequirementVO(){
	}

	public ReqProjectRequirementVO(
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
	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}
	
	public java.lang.Integer getProjectId() {
		return this.projectId;
	}
	public void setRequirementId(java.lang.Integer value) {
		this.requirementId = value;
	}
	
	public java.lang.Integer getRequirementId() {
		return this.requirementId;
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
	
	private ReqProjectVO reqProject;
	
	public void setReqProject(ReqProjectVO reqProject){
		this.reqProject = reqProject;
	}
	
	public ReqProjectVO getReqProject() {
		return reqProject;
	}
	
	private ReqRequirementVO reqRequirement;
	
	public void setReqRequirement(ReqRequirementVO reqRequirement){
		this.reqRequirement = reqRequirement;
	}
	
	public ReqRequirementVO getReqRequirement() {
		return reqRequirement;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProjectId",getProjectId())
			.append("RequirementId",getRequirementId())
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
		if(obj instanceof ReqProjectRequirementVO == false) return false;
		if(this == obj) return true;
		ReqProjectRequirementVO other = (ReqProjectRequirementVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

