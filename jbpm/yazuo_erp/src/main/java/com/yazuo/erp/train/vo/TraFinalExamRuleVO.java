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

public class TraFinalExamRuleVO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "TraFinalExamRule";
	public static final String ALIAS_ID = "规则ID";
	public static final String ALIAS_COURSE_ID = "课程ID";
	public static final String ALIAS_TIME_LIMIT = "考试限时";
	public static final String ALIAS_TOTAL = "增加考题数量";
	public static final String ALIAS_SCORE = "每题分值";
	public static final String ALIAS_FINAL_EXAM_RULE_TYPE = "组卷规则类型";
	public static final String ALIAS_PASS_SCORE = "分数线";
	public static final String ALIAS_INTERVIEW_SCORES = "面试分数线";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	// columns START
	private java.lang.Integer id;
	private java.lang.Integer courseId;
	private java.lang.String timeLimit;
	private BigDecimal total;
	private BigDecimal score;
	private java.lang.String finalExamRuleType;
	private BigDecimal passScore;
	private BigDecimal interviewScores;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private java.lang.String insertTime;
	private java.lang.Integer updateBy;
	private Date updateTime;

	// columns END

	public TraFinalExamRuleVO() {
	}

	public TraFinalExamRuleVO(java.lang.Integer id) {
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

	public void setTimeLimit(java.lang.String value) {
		this.timeLimit = value;
	}

	public java.lang.String getTimeLimit() {
		return this.timeLimit;
	}

	public void setTotal(BigDecimal value) {
		this.total = value;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setScore(BigDecimal value) {
		this.score = value;
	}

	public BigDecimal getScore() {
		return this.score;
	}

	public void setFinalExamRuleType(java.lang.String value) {
		this.finalExamRuleType = value;
	}

	public java.lang.String getFinalExamRuleType() {
		return this.finalExamRuleType;
	}

	public void setPassScore(BigDecimal value) {
		this.passScore = value;
	}

	public BigDecimal getPassScore() {
		return this.passScore;
	}

	public BigDecimal getInterviewScores() {
		return interviewScores;
	}

	public void setInterviewScores(BigDecimal interviewScores) {
		this.interviewScores = interviewScores;
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

	public void setInsertTime(java.lang.String value) {
		this.insertTime = value;
	}

	public java.lang.String getInsertTime() {
		return this.insertTime;
	}

	public void setUpdateBy(java.lang.Integer value) {
		this.updateBy = value;
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

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId()).append("CourseId", getCourseId())
				.append("TimeLimit", getTimeLimit()).append("Total", getTotal()).append("Score", getScore())
				.append("FinalExamRuleType", getFinalExamRuleType()).append("PassScore", getPassScore())
				.append("interviewScores", getInterviewScores()).append("Remark", getRemark()).append("InsertBy", getInsertBy())
				.append("InsertTime", getInsertTime()).append("UpdateBy", getUpdateBy()).append("UpdateTime", getUpdateTime())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof TraFinalExamRuleVO == false)
			return false;
		if (this == obj)
			return true;
		TraFinalExamRuleVO other = (TraFinalExamRuleVO) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
