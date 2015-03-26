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

package com.yazuo.erp.fes.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;
import com.yazuo.erp.system.vo.*;
import  com.yazuo.erp.syn.vo.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class FesPlanVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "工作计划";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_PLAN_ITEM_TYPE = "事项类型";
	public static final String ALIAS_COMMUNICATION_FORM_TYPE = "沟通方式";
	public static final String ALIAS_DESCRIPTION = "说明";
	public static final String ALIAS_START_TIME = "开始时间";
	public static final String ALIAS_END_TIME = "结束时间";
	public static final String ALIAS_EXPLANATION = "情况说明";
	public static final String ALIAS_IS_REMIND = "是否提醒";
	public static final String ALIAS_REMIND_TIME = "提醒时间";
	public static final String ALIAS_IS_SEND_SMS = "是否发送短信";
	public static final String ALIAS_SPONSOR = "发起人";
	public static final String ALIAS_PLANS_SOURCE = "来源";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_CONTACT_ID = "联系人ID";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_USER_ID = "userId";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_PLAN_ITEM_TYPE = "planItemType";
	public static final String COLUMN_COMMUNICATION_FORM_TYPE = "communicationFormType";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_START_TIME = "startTime";
	public static final String COLUMN_END_TIME = "endTime";
	public static final String COLUMN_EXPLANATION = "explanation";
	public static final String COLUMN_IS_REMIND = "isRemind";
	public static final String COLUMN_REMIND_TIME = "remindTime";
	public static final String COLUMN_IS_SEND_SMS = "isSendSms";
	public static final String COLUMN_SPONSOR = "sponsor";
	public static final String COLUMN_PLANS_SOURCE = "plansSource";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_CONTACT_ID = "contactId";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer userId; //"用户ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String title; //"标题";
	private java.lang.String planItemType; //"事项类型";
	private java.lang.String communicationFormType; //"沟通方式";
	private java.lang.String description; //"说明";
	private java.util.Date startTime; //"开始时间";
	private java.util.Date endTime; //"结束时间";
	private java.lang.String explanation; //"情况说明";
	private java.lang.Boolean isRemind; //"是否提醒";
	private java.util.Date remindTime; //"提醒时间";
	private java.lang.Boolean isSendSms; //"是否发送短信";
	private java.lang.Integer sponsor; //"发起人";
	private java.lang.String plansSource; //"来源";
	private java.lang.String status; //"状态";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.Integer contactId; //"联系人ID";
	//columns END
	//others
	private Date fesBeginTime;
	private Date fesEndTime;
	
	public Date getFesBeginTime() {
		return fesBeginTime;
	}

	public void setFesBeginTime(Date fesBeginTime) {
		this.fesBeginTime = fesBeginTime;
	}

	public Date getFesEndTime() {
		return fesEndTime;
	}

	public void setFesEndTime(Date fesEndTime) {
		this.fesEndTime = fesEndTime;
	}

	private String nonStatus; //状态不等于
	
	public String getNonStatus() {
		return nonStatus;
	}

	public void setNonStatus(String nonStatus) {
		this.nonStatus = nonStatus;
	}

	private Set fesPlanAttachments = new HashSet(0);
	public void setFesPlanAttachments(Set<FesPlanAttachmentVO> fesPlanAttachment){
		this.fesPlanAttachments = fesPlanAttachment;
	}
	
	public Set<FesPlanAttachmentVO> getFesPlanAttachments() {
		return fesPlanAttachments;
	}
	
	private SysUserVO sysUser;
	
	public void setSysUser(SysUserVO sysUser){
		this.sysUser = sysUser;
	}
	
	public SysUserVO getSysUser() {
		return sysUser;
	}
	
	private SynMerchantVO synMerchant;
	
	public void setSynMerchant(SynMerchantVO synMerchant){
		this.synMerchant = synMerchant;
	}
	
	public SynMerchantVO getSynMerchant() {
		return synMerchant;
	}

	public FesPlanVO(){
	}

	public FesPlanVO(
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
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setMerchantId(java.lang.Integer value) {
		this.merchantId = value;
	}
	
	public java.lang.Integer getMerchantId() {
		return this.merchantId;
	}
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setPlanItemType(java.lang.String value) {
		this.planItemType = value;
	}
	
	public java.lang.String getPlanItemType() {
		return this.planItemType;
	}
	public void setCommunicationFormType(java.lang.String value) {
		this.communicationFormType = value;
	}
	
	public java.lang.String getCommunicationFormType() {
		return this.communicationFormType;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setExplanation(java.lang.String value) {
		this.explanation = value;
	}
	
	public java.lang.String getExplanation() {
		return this.explanation;
	}
	public void setIsRemind(java.lang.Boolean value) {
		this.isRemind = value;
	}
	
	public java.lang.Boolean getIsRemind() {
		return this.isRemind;
	}
	public void setRemindTime(java.util.Date value) {
		this.remindTime = value;
	}
	
	public java.util.Date getRemindTime() {
		return this.remindTime;
	}
	public void setIsSendSms(java.lang.Boolean value) {
		this.isSendSms = value;
	}
	
	public java.lang.Boolean getIsSendSms() {
		return this.isSendSms;
	}
	public void setSponsor(java.lang.Integer value) {
		this.sponsor = value;
	}
	
	public java.lang.Integer getSponsor() {
		return this.sponsor;
	}
	public void setPlansSource(java.lang.String value) {
		this.plansSource = value;
	}
	
	public java.lang.String getPlansSource() {
		return this.plansSource;
	}
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	public java.lang.String getStatus() {
		return this.status;
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
	public void setContactId(java.lang.Integer value) {
		this.contactId = value;
	}
	
	public java.lang.Integer getContactId() {
		return this.contactId;
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
			.append("UserId",getUserId())
			.append("MerchantId",getMerchantId())
			.append("Title",getTitle())
			.append("PlanItemType",getPlanItemType())
			.append("CommunicationFormType",getCommunicationFormType())
			.append("Description",getDescription())
			.append("StartTime",getStartTime())
			.append("EndTime",getEndTime())
			.append("Explanation",getExplanation())
			.append("IsRemind",getIsRemind())
			.append("RemindTime",getRemindTime())
			.append("IsSendSms",getIsSendSms())
			.append("Sponsor",getSponsor())
			.append("PlansSource",getPlansSource())
			.append("Status",getStatus())
			.append("IsEnable",getIsEnable())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.append("ContactId",getContactId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof FesPlanVO == false) return false;
		if(this == obj) return true;
		FesPlanVO other = (FesPlanVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

