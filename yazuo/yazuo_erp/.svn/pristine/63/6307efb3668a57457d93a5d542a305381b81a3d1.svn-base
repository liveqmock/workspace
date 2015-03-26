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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class TraQuestionVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraQuestion";
	public static final String ALIAS_ID = "试题ID";
	public static final String ALIAS_COURSEWARE_ID = "课件ID";
	public static final String ALIAS_QUESTION_TYPE = "试题类型";
	public static final String ALIAS_QUESTION = "题干";
	public static final String ALIAS_SCORE = "分值";
	public static final String ALIAS_IS_SYSTEM_DETERMINE = "是否系统判分";
	public static final String ALIAS_PPT_ID = "PPT的ID";
	public static final String ALIAS_URL = "URL地址";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.Integer coursewareId;
	private java.lang.String questionType;
	private java.lang.String question;
	private BigDecimal score;
	private java.lang.String isSystemDetermine;
	private java.lang.Integer pptId;
	private java.lang.String url;
	private java.lang.String isEnable;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private Date insertTime;
	private java.lang.Integer updateBy;
	private Date updateTime;
	//columns END

	public TraQuestionVO(){
	}

	public TraQuestionVO(
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
	public void setQuestionType(java.lang.String value) {
		this.questionType = value;
	}
	
	public java.lang.String getQuestionType() {
		return this.questionType;
	}
	public void setQuestion(java.lang.String value) {
		this.question = value;
	}
	
	public java.lang.String getQuestion() {
		return this.question;
	}
	public void setScore(BigDecimal value) {
		this.score = value;
	}
	
	public BigDecimal getScore() {
		return this.score;
	}
	public void setIsSystemDetermine(java.lang.String value) {
		this.isSystemDetermine = value;
	}
	
	public java.lang.String getIsSystemDetermine() {
		return this.isSystemDetermine;
	}
	public void setPptId(java.lang.Integer value) {
		this.pptId = value;
	}
	
	public java.lang.Integer getPptId() {
		return this.pptId;
	}
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setIsEnable(java.lang.String value) {
		this.isEnable = value;
	}
	
	public java.lang.String getIsEnable() {
		return this.isEnable;
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

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CoursewareId",getCoursewareId())
			.append("QuestionType",getQuestionType())
			.append("Question",getQuestion())
			.append("Score",getScore())
			.append("IsSystemDetermine",getIsSystemDetermine())
			.append("PptId",getPptId())
			.append("Url",getUrl())
			.append("IsEnable",getIsEnable())
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
		if(obj instanceof TraQuestionVO == false) return false;
		if(this == obj) return true;
		TraQuestionVO other = (TraQuestionVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

