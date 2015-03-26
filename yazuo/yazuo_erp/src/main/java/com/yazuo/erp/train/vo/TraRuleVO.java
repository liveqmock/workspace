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
public class TraRuleVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraRule";
	public static final String ALIAS_ID = "规则ID";
	public static final String ALIAS_COURSEWARE_ID = "课件ID";
	public static final String ALIAS_PAPER_TYPE = "考卷类型";
	public static final String ALIAS_IS_SHORT_ANSWER = "是否开启问答题";
	public static final String ALIAS_TIME_LIMIT = "考试限时";
	public static final String ALIAS_TOTAL = "考题数量";
	public static final String ALIAS_SCORE = "每题分值";
	public static final String ALIAS_RULE_TYPE = "组卷规则类型";
	public static final String ALIAS_PASSING_SCORE = "分数线";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_IS_TEST = "老学员是否考试";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_COURSEWARE_ID = "coursewareId";
	public static final String COLUMN_PAPER_TYPE = "paperType";
	public static final String COLUMN_IS_SHORT_ANSWER = "isShortAnswer";
	public static final String COLUMN_TIME_LIMIT = "timeLimit";
	public static final String COLUMN_TOTAL = "total";
	public static final String COLUMN_SCORE = "score";
	public static final String COLUMN_RULE_TYPE = "ruleType";
	public static final String COLUMN_PASSING_SCORE = "passingScore";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_IS_TEST = "isTest";

	//columns START
	private java.lang.Integer id; //"规则ID";
	private java.lang.Integer coursewareId; //"课件ID";
	private java.lang.String paperType; //"考卷类型";
	private java.lang.String isShortAnswer; //"是否开启问答题";
	private java.lang.String timeLimit; //"考试限时";
	private java.math.BigDecimal total; //"考题数量";
	private java.math.BigDecimal score; //"每题分值";
	private java.lang.String ruleType; //"组卷规则类型";
	private java.math.BigDecimal passingScore; //"分数线";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.String isTest; //"老学员是否考试";
	//columns END

	public TraRuleVO(){
	}

	public TraRuleVO(
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
	public void setPaperType(java.lang.String value) {
		this.paperType = value;
	}
	
	public java.lang.String getPaperType() {
		return this.paperType;
	}
	public void setIsShortAnswer(java.lang.String value) {
		this.isShortAnswer = value;
	}
	
	public java.lang.String getIsShortAnswer() {
		return this.isShortAnswer;
	}
	public void setTimeLimit(java.lang.String value) {
		this.timeLimit = value;
	}
	
	public java.lang.String getTimeLimit() {
		return this.timeLimit;
	}
	public void setTotal(java.math.BigDecimal value) {
		this.total = value;
	}
	
	public java.math.BigDecimal getTotal() {
		return this.total;
	}
	public void setScore(java.math.BigDecimal value) {
		this.score = value;
	}
	
	public java.math.BigDecimal getScore() {
		return this.score;
	}
	public void setRuleType(java.lang.String value) {
		this.ruleType = value;
	}
	
	public java.lang.String getRuleType() {
		return this.ruleType;
	}
	public void setPassingScore(java.math.BigDecimal value) {
		this.passingScore = value;
	}
	
	public java.math.BigDecimal getPassingScore() {
		return this.passingScore;
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
	public void setIsTest(java.lang.String value) {
		this.isTest = value;
	}
	
	public java.lang.String getIsTest() {
		return this.isTest;
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
			.append("CoursewareId",getCoursewareId())
			.append("PaperType",getPaperType())
			.append("IsShortAnswer",getIsShortAnswer())
			.append("TimeLimit",getTimeLimit())
			.append("Total",getTotal())
			.append("Score",getScore())
			.append("RuleType",getRuleType())
			.append("PassingScore",getPassingScore())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.append("IsTest",getIsTest())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TraRuleVO == false) return false;
		if(this == obj) return true;
		TraRuleVO other = (TraRuleVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

