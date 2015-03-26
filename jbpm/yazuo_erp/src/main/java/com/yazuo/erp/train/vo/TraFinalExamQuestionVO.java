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


public class TraFinalExamQuestionVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraFinalExamQuestion";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_RULE_ID = "规则ID";
	public static final String ALIAS_QUESTION_ID = "试题ID";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.Integer ruleId;
	private java.lang.Integer questionId;
	private java.lang.Integer insertBy;
	private Date insertTime;
	//columns END

	public TraFinalExamQuestionVO(){
	}

	public TraFinalExamQuestionVO(
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
	public void setRuleId(java.lang.Integer value) {
		this.ruleId = value;
	}
	
	public java.lang.Integer getRuleId() {
		return this.ruleId;
	}
	public void setQuestionId(java.lang.Integer value) {
		this.questionId = value;
	}
	
	public java.lang.Integer getQuestionId() {
		return this.questionId;
	}
	public void setInsertBy(java.lang.Integer value) {
		this.insertBy = value;
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
			.append("RuleId",getRuleId())
			.append("QuestionId",getQuestionId())
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
		if(obj instanceof TraFinalExamQuestionVO == false) return false;
		if(this == obj) return true;
		TraFinalExamQuestionVO other = (TraFinalExamQuestionVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

