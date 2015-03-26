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
public class TraLearningProgressVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraLearningProgress";
	public static final String ALIAS_ID = "进度ID";
	public static final String ALIAS_STUDENT_ID = "学生ID";
	public static final String ALIAS_COURSE_ID = "课程ID";
	public static final String ALIAS_COURSEWARE_ID = "课件ID";
	public static final String ALIAS_COURSE_STATUS = "课程状态";
	public static final String ALIAS_COURSEWARE_STATUS = "课件状态";
	public static final String ALIAS_COURSE_END_TIME = "课程截止时间";
	public static final String ALIAS_COURSEWARE_END_TIME = "课件截止时间";
	public static final String ALIAS_PROGRESS_TYPE = "学习进度";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_LEARNING_PROGRESS_TYPE = "learningProgressType";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_STUDENT_ID = "studentId";
	public static final String COLUMN_COURSE_ID = "courseId";
	public static final String COLUMN_COURSEWARE_ID = "coursewareId";
	public static final String COLUMN_COURSE_STATUS = "courseStatus";
	public static final String COLUMN_COURSEWARE_STATUS = "coursewareStatus";
	public static final String COLUMN_COURSE_END_TIME = "courseEndTime";
	public static final String COLUMN_COURSEWARE_END_TIME = "coursewareEndTime";
	public static final String COLUMN_PROGRESS_TYPE = "progressType";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_LEARNING_PROGRESS_TYPE = "learningProgressType";

	//columns START
	private java.lang.Integer id; //"进度ID";
	private java.lang.Integer studentId; //"学生ID";
	private java.lang.Integer courseId; //"课程ID";
	private java.lang.Integer coursewareId; //"课件ID";
	private java.lang.String courseStatus; //"课程状态";
	private java.lang.String coursewareStatus; //"课件状态";
	private java.util.Date courseEndTime; //"课程截止时间";
	private java.util.Date coursewareEndTime; //"课件截止时间";
	private java.lang.String progressType; //"学习进度";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.String learningProgressType; //"learningProgressType";
	//columns END

	public TraLearningProgressVO(){
	}

	public TraLearningProgressVO(
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
	public void setStudentId(java.lang.Integer value) {
		this.studentId = value;
	}
	
	public java.lang.Integer getStudentId() {
		return this.studentId;
	}
	public void setCourseId(java.lang.Integer value) {
		this.courseId = value;
	}
	
	public java.lang.Integer getCourseId() {
		return this.courseId;
	}
	public void setCoursewareId(java.lang.Integer value) {
		this.coursewareId = value;
	}
	
	public java.lang.Integer getCoursewareId() {
		return this.coursewareId;
	}
	public void setCourseStatus(java.lang.String value) {
		this.courseStatus = value;
	}
	
	public java.lang.String getCourseStatus() {
		return this.courseStatus;
	}
	public void setCoursewareStatus(java.lang.String value) {
		this.coursewareStatus = value;
	}
	
	public java.lang.String getCoursewareStatus() {
		return this.coursewareStatus;
	}
	public void setCourseEndTime(java.util.Date value) {
		this.courseEndTime = value;
	}
	
	public java.util.Date getCourseEndTime() {
		return this.courseEndTime;
	}
	public void setCoursewareEndTime(java.util.Date value) {
		this.coursewareEndTime = value;
	}
	
	public java.util.Date getCoursewareEndTime() {
		return this.coursewareEndTime;
	}
	public void setProgressType(java.lang.String value) {
		this.progressType = value;
	}
	
	public java.lang.String getProgressType() {
		return this.progressType;
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
	public void setLearningProgressType(java.lang.String value) {
		this.learningProgressType = value;
	}
	
	public java.lang.String getLearningProgressType() {
		return this.learningProgressType;
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
			.append("StudentId",getStudentId())
			.append("CourseId",getCourseId())
			.append("CoursewareId",getCoursewareId())
			.append("CourseStatus",getCourseStatus())
			.append("CoursewareStatus",getCoursewareStatus())
			.append("CourseEndTime",getCourseEndTime())
			.append("CoursewareEndTime",getCoursewareEndTime())
			.append("ProgressType",getProgressType())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.append("LearningProgressType",getLearningProgressType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TraLearningProgressVO == false) return false;
		if(this == obj) return true;
		TraLearningProgressVO other = (TraLearningProgressVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

