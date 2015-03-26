/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author erp team
 * @date 
 */
public class SysMailTemplateVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "邮件模板";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MAIL_TEMPLATE_TYPE = "模板类型";
	public static final String ALIAS_NAME = "模板名称";
	public static final String ALIAS_TITLE = "邮件标题";
	public static final String ALIAS_CONTENT = "邮件内容";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_IS_SEND_SMS = "是否发送短信";
	public static final String ALIAS_SMS_CONTENT = "smsContent";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MAIL_TEMPLATE_TYPE = "mailTemplateType";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_IS_SEND_SMS = "isSendSms";
	public static final String COLUMN_SMS_CONTENT = "smsContent";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.String mailTemplateType; //"模板类型";
	private java.lang.String name; //"模板名称";
	private java.lang.String title; //"邮件标题";
	private java.lang.String content; //"邮件内容";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.String isSendSMS; //"是否发送短信 0:不发送,1:发送";
	private java.lang.String smsContent; //"smsContent";//columns END

	public java.lang.String getIsSendSMS() {
		return isSendSMS;
	}

	public void setIsSendSMS(java.lang.String isSendSMS) {
		this.isSendSMS = isSendSMS;
	}

	public SysMailTemplateVO(){
	}

	public SysMailTemplateVO(
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
	public void setMailTemplateType(java.lang.String value) {
		this.mailTemplateType = value;
	}
	
	public java.lang.String getMailTemplateType() {
		return this.mailTemplateType;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
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
		

	public void setSmsContent(java.lang.String value) {
		this.smsContent = value;
	}
	
	public java.lang.String getSmsContent() {
		return this.smsContent;
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
			.append("MailTemplateType",getMailTemplateType())
			.append("Name",getName())
			.append("Title",getTitle())
			.append("Content",getContent())
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
		if(obj instanceof SysMailTemplateVO == false) return false;
		if(this == obj) return true;
		SysMailTemplateVO other = (SysMailTemplateVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

