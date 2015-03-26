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
public class FesProcessAttachmentVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "上线流程-附件关系表";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_PROCESS_ID = "流程ID";
	public static final String ALIAS_ATTACHMENT_ID = "附件ID";
	public static final String ALIAS_PROCESS_ATTACHMENT_TYPE = "上线流程附件类型";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PROCESS_ID = "processId";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_PROCESS_ATTACHMENT_TYPE = "processAttachmentType";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer processId; //"流程ID";
	private java.lang.Integer attachmentId; //"附件ID";
	private java.lang.String processAttachmentType; //"上线流程附件类型";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.String nonProcessAttachmentType; //"上线流程附件类型 作为 不等于条件输入";
	private java.lang.String[] processAttachmentTypes; //"数组类型，用于判断只要存在一个卡样确认单，则更改状态";
	//columns END

	public java.lang.String[] getProcessAttachmentTypes() {
		return processAttachmentTypes;
	}

	public void setProcessAttachmentTypes(java.lang.String[] processAttachmentTypes) {
		this.processAttachmentTypes = processAttachmentTypes;
	}

	public java.lang.String getNonProcessAttachmentType() {
		return nonProcessAttachmentType;
	}

	public void setNonProcessAttachmentType(java.lang.String nonProcessAttachmentType) {
		this.nonProcessAttachmentType = nonProcessAttachmentType;
	}

	public FesProcessAttachmentVO(){
	}

	public FesProcessAttachmentVO(
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
	public void setProcessId(java.lang.Integer value) {
		this.processId = value;
	}
	
	public java.lang.Integer getProcessId() {
		return this.processId;
	}
	public void setAttachmentId(java.lang.Integer value) {
		this.attachmentId = value;
	}
	
	public java.lang.Integer getAttachmentId() {
		return this.attachmentId;
	}
	public void setProcessAttachmentType(java.lang.String value) {
		this.processAttachmentType = value;
	}
	
	public java.lang.String getProcessAttachmentType() {
		return this.processAttachmentType;
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
	
	private FesOnlineProcessVO fesOnlineProcess;
	
	public void setFesOnlineProcess(FesOnlineProcessVO fesOnlineProcess){
		this.fesOnlineProcess = fesOnlineProcess;
	}
	
	public FesOnlineProcessVO getFesOnlineProcess() {
		return fesOnlineProcess;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProcessId",getProcessId())
			.append("AttachmentId",getAttachmentId())
			.append("ProcessAttachmentType",getProcessAttachmentType())
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
		if(obj instanceof FesProcessAttachmentVO == false) return false;
		if(this == obj) return true;
		FesProcessAttachmentVO other = (FesProcessAttachmentVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

