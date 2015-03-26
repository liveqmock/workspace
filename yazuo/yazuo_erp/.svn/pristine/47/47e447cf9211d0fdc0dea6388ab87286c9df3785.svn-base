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


public class TraCourseCoursewareVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraCourseCourseware";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_COURSE_ID = "课程ID";
	public static final String ALIAS_COURSEWARE_ID = "课件ID";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_IS_REQUIRED = "是否必修";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.Integer courseId;
	private java.lang.Integer coursewareId;
	private java.lang.Integer sort;
	private java.lang.String isRequired;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private Date insertTime;
	private java.lang.Integer updateBy;
	private Date updateTime;
	//columns END

	public TraCourseCoursewareVO(){
	}

	public TraCourseCoursewareVO(
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
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setIsRequired(java.lang.String value) {
		this.isRequired = value;
	}
	
	public java.lang.String getIsRequired() {
		return this.isRequired;
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

    public Integer getInsertBy() {
        return insertBy;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CourseId",getCourseId())
			.append("CoursewareId",getCoursewareId())
			.append("Sort",getSort())
			.append("IsRequired",getIsRequired())
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
		if(obj instanceof TraCourseCoursewareVO == false) return false;
		if(this == obj) return true;
		TraCourseCoursewareVO other = (TraCourseCoursewareVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

