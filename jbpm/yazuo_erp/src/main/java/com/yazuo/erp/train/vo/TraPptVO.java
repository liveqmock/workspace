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
import java.util.List;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class TraPptVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraPpt";
	public static final String ALIAS_ID = "PPT的ID";
	public static final String ALIAS_COURSEWARE_ID = "课件ID";
	public static final String ALIAS_PPT_NAME = "标题";
	public static final String ALIAS_PPT_DESC = "描述";
	public static final String ALIAS_PPT_PATH = "路径";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.Integer coursewareId;
	private java.lang.String pptName;
	private java.lang.String pptDesc;
	private java.lang.String pptPath;
	private java.lang.Integer insertBy;
	private Date insertTime;
	private java.lang.Integer updateBy;
	private Date updateTime;
	//columns END

    private List<TraPptDtlVO> pptDtlVOs;

	public TraPptVO(){
	}

	public TraPptVO(
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
	public void setCoursewareId(java.lang.Integer value) {
		this.coursewareId = value;
	}
	
	public java.lang.Integer getCoursewareId() {
		return this.coursewareId;
	}
	public void setPptName(java.lang.String value) {
		this.pptName = value;
	}
	
	public java.lang.String getPptName() {
		return this.pptName;
	}
	public void setPptDesc(java.lang.String value) {
		this.pptDesc = value;
	}
	
	public java.lang.String getPptDesc() {
		return this.pptDesc;
	}
	public void setPptPath(java.lang.String value) {
		this.pptPath = value;
	}
	
	public java.lang.String getPptPath() {
		return this.pptPath;
	}
	public void setInsertBy(java.lang.Integer value) {
		this.insertBy = value;
	}

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getInsertBy() {
        return insertBy;
    }

    public List<TraPptDtlVO> getPptDtlVOs() {
        return pptDtlVOs;
    }

    public void setPptDtlVOs(List<TraPptDtlVO> pptDtlVOs) {
        this.pptDtlVOs = pptDtlVOs;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CoursewareId",getCoursewareId())
			.append("PptName",getPptName())
			.append("PptDesc",getPptDesc())
			.append("PptPath",getPptPath())
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
		if(obj instanceof TraPptVO == false) return false;
		if(this == obj) return true;
		TraPptVO other = (TraPptVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

