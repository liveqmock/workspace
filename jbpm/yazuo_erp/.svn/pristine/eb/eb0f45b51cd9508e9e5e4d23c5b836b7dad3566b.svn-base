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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.system.vo.SysAttachmentVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class FesPlanAttachmentVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "工作计划-附件关系表";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_PLAN_ID = "工作计划ID";
	public static final String ALIAS_ATTACHMENT_ID = "附件ID";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PLAN_ID = "planId";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer planId; //"工作计划ID";
	private java.lang.Integer attachmentId; //"附件ID";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	//columns END

	public FesPlanAttachmentVO(){
	}

	public FesPlanAttachmentVO(
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
	public void setPlanId(java.lang.Integer value) {
		this.planId = value;
	}
	
	public java.lang.Integer getPlanId() {
		return this.planId;
	}
	public void setAttachmentId(java.lang.Integer value) {
		this.attachmentId = value;
	}
	
	public java.lang.Integer getAttachmentId() {
		return this.attachmentId;
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

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
	
	private SysAttachmentVO sysAttachment;
	
	public void setSysAttachment(SysAttachmentVO sysAttachment){
		this.sysAttachment = sysAttachment;
	}
	
	public SysAttachmentVO getSysAttachment() {
		return sysAttachment;
	}
	
	private FesPlanVO fesPlan;
	
	public void setFesPlan(FesPlanVO fesPlan){
		this.fesPlan = fesPlan;
	}
	
	public FesPlanVO getFesPlan() {
		return fesPlan;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("PlanId",getPlanId())
			.append("AttachmentId",getAttachmentId())
			.append("Remark",getRemark())
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
		if(obj instanceof FesPlanAttachmentVO == false) return false;
		if(this == obj) return true;
		FesPlanAttachmentVO other = (FesPlanAttachmentVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

