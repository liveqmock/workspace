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


public class TraWrongQuestionVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraWrongQuestion";
	public static final String ALIAS_ID = "错题ID";
	public static final String ALIAS_STUDENT_ID = "学生ID";
	public static final String ALIAS_PAPER_ID = "试卷ID";
	public static final String ALIAS_QUESTION_ID = "试题ID";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.Integer studentId;
	private java.lang.Integer paperId;
	private java.lang.Integer questionId;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private Date insertTime;
	private java.lang.Integer updateBy;
	private Date updateTime;
	//columns END

	public TraWrongQuestionVO(){
	}

	public TraWrongQuestionVO(
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
	public void setPaperId(java.lang.Integer value) {
		this.paperId = value;
	}
	
	public java.lang.Integer getPaperId() {
		return this.paperId;
	}
	public void setQuestionId(java.lang.Integer value) {
		this.questionId = value;
	}
	
	public java.lang.Integer getQuestionId() {
		return this.questionId;
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
			.append("StudentId",getStudentId())
			.append("PaperId",getPaperId())
			.append("QuestionId",getQuestionId())
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
		if(obj instanceof TraWrongQuestionVO == false) return false;
		if(this == obj) return true;
		TraWrongQuestionVO other = (TraWrongQuestionVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

