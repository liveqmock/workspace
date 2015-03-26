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


public class TraExamOptionVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraExamOption";
	public static final String ALIAS_ID = "选项ID";
	public static final String ALIAS_QUESTION_ID = "试题ID";
	public static final String ALIAS_OPTION_CONTENT = "选项内容";
	public static final String ALIAS_ATTACHMENT_ID = "附件ID";
	public static final String ALIAS_REF_ANSWER = "参考答案";
	public static final String ALIAS_IS_ANSWER = "是否答案";
	public static final String ALIAS_IS_SELECTED = "是否选择";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.Integer examDtlId;
	private java.lang.String optionContent;
	private java.lang.Integer attachmentId;
	private java.lang.String refAnswer;
	private java.lang.String isAnswer;
	private java.lang.String isSelected;
	private java.lang.Integer insertBy;
	private Date insertTime;
	private java.lang.Integer updateBy;
	private Date updateTime;
	//columns END

	public TraExamOptionVO(){
	}

	public TraExamOptionVO(
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
	public void setExamDtlId(java.lang.Integer value) {
		this.examDtlId = value;
	}
	
	public java.lang.Integer getExamDtlId() {
		return this.examDtlId;
	}
	public void setOptionContent(java.lang.String value) {
		this.optionContent = value;
	}
	
	public java.lang.String getOptionContent() {
		return this.optionContent;
	}
	public void setAttachmentId(java.lang.Integer value) {
		this.attachmentId = value;
	}
	
	public java.lang.Integer getAttachmentId() {
		return this.attachmentId;
	}
	public void setRefAnswer(java.lang.String value) {
		this.refAnswer = value;
	}
	
	public java.lang.String getRefAnswer() {
		return this.refAnswer;
	}
	public void setIsAnswer(java.lang.String value) {
		this.isAnswer = value;
	}
	
	public java.lang.String getIsAnswer() {
		return this.isAnswer;
	}
	public void setIsSelected(java.lang.String value) {
		this.isSelected = value;
	}
	
	public java.lang.String getIsSelected() {
		return this.isSelected;
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
			.append("ExamDtlId", getExamDtlId())
			.append("OptionContent",getOptionContent())
			.append("AttachmentId",getAttachmentId())
			.append("RefAnswer",getRefAnswer())
			.append("IsAnswer",getIsAnswer())
			.append("IsSelected",getIsSelected())
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
		if(obj instanceof TraExamOptionVO == false) return false;
		if(this == obj) return true;
		TraExamOptionVO other = (TraExamOptionVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

