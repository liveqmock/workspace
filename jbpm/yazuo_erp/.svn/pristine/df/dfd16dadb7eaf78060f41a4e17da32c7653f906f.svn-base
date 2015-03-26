/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author erp team
 * @date 
 */
public class SysProblemCommentsVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "问题回复";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_PROBLEM_ID = "问题ID";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_SOLVERED_BY = "处理人ID";
	public static final String ALIAS_SOLVING_TIME = "处理时间";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PROBLEM_ID = "problemId";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_SOLVERED_BY = "solveredBy";
	public static final String COLUMN_SOLVING_TIME = "solvingTime";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer problemId; //"问题ID";
	private java.lang.String description; //"描述";
	private java.lang.Integer solveredBy; //"处理人ID";
	private java.util.Date solvingTime; //"处理时间";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	//columns END

	public SysProblemCommentsVO(){
	}

	public SysProblemCommentsVO(
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
		
	public void setProblemId(java.lang.Integer value) {
		this.problemId = value;
	}
	
	public java.lang.Integer getProblemId() {
		return this.problemId;
	}
		
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
		
	public void setSolveredBy(java.lang.Integer value) {
		this.solveredBy = value;
	}
	
	public java.lang.Integer getSolveredBy() {
		return this.solveredBy;
	}
		
	public void setSolvingTime(java.util.Date value) {
		this.solvingTime = value;
	}
	
	public java.util.Date getSolvingTime() {
		return this.solvingTime;
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
	
	private SysProblemVO sysProblem;
	
	public void setSysProblem(SysProblemVO sysProblem){
		this.sysProblem = sysProblem;
	}
	
	public SysProblemVO getSysProblem() {
		return sysProblem;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProblemId",getProblemId())
			.append("Description",getDescription())
			.append("SolveredBy",getSolveredBy())
			.append("SolvingTime",getSolvingTime())
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
		if(obj instanceof SysProblemCommentsVO == false) return false;
		if(this == obj) return true;
		SysProblemCommentsVO other = (SysProblemCommentsVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

