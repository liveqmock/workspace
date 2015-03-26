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
import java.util.LinkedList;
import java.util.List;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class TraExamDtlVO implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    //alias
    public static final String TABLE_ALIAS = "TraExamDtl";
    public static final String ALIAS_ID = "详情ID";
    public static final String ALIAS_PAPER_ID = "试卷ID";
    public static final String ALIAS_QUESTION_ID = "试题ID";
    public static final String ALIAS_QUESTION_TYPE = "试题类型";
    public static final String ALIAS_CONTENT = "题干";
    public static final String ALIAS_QUESTION_SCORE = "分值";
    public static final String ALIAS_SCORE = "得分";
    public static final String ALIAS_IS_CORRECT = "是否正确";
    public static final String ALIAS_IS_SYSTEM_DETERMINE = "是否系统判分";
    public static final String ALIAS_PPT_ID = "PPT的ID";
    public static final String ALIAS_URL = "URL地址";
    public static final String ALIAS_REMARK = "备注";
    public static final String ALIAS_INSERT_BY = "创建人";
    public static final String ALIAS_INSERT_TIME = "创建时间";
    public static final String ALIAS_UPDATE_BY = "最后修改人";
    public static final String ALIAS_UPDATE_TIME = "最后修改时间";

    //columns START
    private java.lang.Integer id;
    private java.lang.Integer paperId;
    private java.lang.Integer questionId;
    private java.lang.String questionType;
    private java.lang.String content;
    private BigDecimal questionScore;
    private BigDecimal score;
    private java.lang.String isCorrect;
    private java.lang.String isSystemDetermine;
    private java.lang.Integer pptId;
    private java.lang.String url;
    private java.lang.String remark;
    private java.lang.Integer insertBy;
    private Date insertTime;
    private java.lang.Integer updateBy;
    private Date updateTime;
    //columns END
    // 添加对象属性
    private List<TraExamOptionVO> examOptionVOs;

    public TraExamDtlVO() {
    }

    public TraExamDtlVO(
            java.lang.Integer id
    ) {
        this.id = id;
    }

    public void setId(java.lang.Integer value) {
        this.id = value;
    }

    public java.lang.Integer getId() {
        return this.id;
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

    public void setQuestionType(java.lang.String value) {
        this.questionType = value;
    }

    public java.lang.String getQuestionType() {
        return this.questionType;
    }

    public void setContent(java.lang.String value) {
        this.content = value;
    }

    public java.lang.String getContent() {
        return this.content;
    }

    public void setQuestionScore(BigDecimal value) {
        this.questionScore = value;
    }

    public BigDecimal getQuestionScore() {
        return this.questionScore;
    }

    public void setScore(BigDecimal value) {
        this.score = value;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public void setIsCorrect(java.lang.String value) {
        this.isCorrect = value;
    }

    public java.lang.String getIsCorrect() {
        return this.isCorrect;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id", getId())
                .append("PaperId", getPaperId())
                .append("QuestionId", getQuestionId())
                .append("QuestionType", getQuestionType())
                .append("Content", getContent())
                .append("QuestionScore", getQuestionScore())
                .append("Score", getScore())
                .append("IsCorrect", getIsCorrect())
                .append("IsSystemDetermine", getIsSystemDetermine())
                .append("PptId", getPptId())
                .append("Url", getUrl())
                .append("Remark", getRemark())
                .append("InsertBy", getInsertBy())
                .append("InsertTime", getInsertTime())
                .append("UpdateBy", getUpdateBy())
                .append("UpdateTime", getUpdateTime())
                .toString();
    }

    public List<TraExamOptionVO> getExamOptionVOs() {
        if (this.examOptionVOs == null) {
            this.examOptionVOs = new LinkedList<TraExamOptionVO>();
        }
        return examOptionVOs;
    }

    public void setExamOptionVOs(List<TraExamOptionVO> examOptionVOs) {
        this.examOptionVOs = examOptionVOs;
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof TraExamDtlVO == false) return false;
        if (this == obj) return true;
        TraExamDtlVO other = (TraExamDtlVO) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

