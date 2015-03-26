/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class TraTeacherStudentVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraTeacherStudent";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_TEACHER_ID = "老师ID";
	public static final String ALIAS_STUDENT_ID = "学生ID";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.Integer teacherId;
	private java.lang.Integer studentId;
	private java.lang.String isEnable;
	private java.lang.Integer insertBy;
	private Date insertTime;
	//columns END

	public TraTeacherStudentVO(){
	}

	public TraTeacherStudentVO(
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
	public void setTeacherId(java.lang.Integer value) {
		this.teacherId = value;
	}
	
	public java.lang.Integer getTeacherId() {
		return this.teacherId;
	}
	public void setStudentId(java.lang.Integer value) {
		this.studentId = value;
	}
	
	public java.lang.Integer getStudentId() {
		return this.studentId;
	}
	public void setIsEnable(java.lang.String value) {
		this.isEnable = value;
	}
	
	public java.lang.String getIsEnable() {
		return this.isEnable;
	}
	public void setInsertBy(java.lang.Integer value) {
		this.insertBy = value;
	}
	
	public java.lang.Integer getInsertBy() {
		return this.insertBy;
	}

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TeacherId",getTeacherId())
			.append("StudentId",getStudentId())
			.append("IsEnable",getIsEnable())
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
		if(obj instanceof TraTeacherStudentVO == false) return false;
		if(this == obj) return true;
		TraTeacherStudentVO other = (TraTeacherStudentVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

