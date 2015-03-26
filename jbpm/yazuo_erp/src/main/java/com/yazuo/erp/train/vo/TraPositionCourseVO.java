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

package com.yazuo.erp.train.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.train.vo.*;
import com.yazuo.erp.train.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class TraPositionCourseVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "职位-课程关系表";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_POSITION_ID = "职位ID";
	public static final String ALIAS_COURSE_ID = "课程ID";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_POSITION_ID = "positionId";
	public static final String COLUMN_COURSE_ID = "courseId";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer positionId; //"职位ID";
	private java.lang.Integer courseId; //"课程ID";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	//columns END

	public TraPositionCourseVO(){
	}

	public TraPositionCourseVO(
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
	public void setPositionId(java.lang.Integer value) {
		this.positionId = value;
	}
	
	public java.lang.Integer getPositionId() {
		return this.positionId;
	}
	public void setCourseId(java.lang.Integer value) {
		this.courseId = value;
	}
	
	public java.lang.Integer getCourseId() {
		return this.courseId;
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
			.append("PositionId",getPositionId())
			.append("CourseId",getCourseId())
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
		if(obj instanceof TraPositionCourseVO == false) return false;
		if(this == obj) return true;
		TraPositionCourseVO other = (TraPositionCourseVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

