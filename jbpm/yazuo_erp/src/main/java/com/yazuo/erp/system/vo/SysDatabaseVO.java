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

package com.yazuo.erp.system.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class SysDatabaseVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "资料库";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_APPLY_CROWD_TYPE = "适用人群";
	public static final String ALIAS_DATA_TYPE = "资料类型";
	public static final String ALIAS_ATTACHMENT_ID = "附件ID";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_APPLY_CROWD_TYPE = "applyCrowdType";
	public static final String COLUMN_DATA_TYPE = "dataType";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.String title; //"标题";
	private java.lang.String description; //"描述";
	private String[] applyCrowdType; //"适用人群";
	private java.lang.String dataType; //"资料类型";
	private java.lang.Integer attachmentId; //"附件ID";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END

	public SysDatabaseVO(){
	}

	public SysDatabaseVO(
		java.lang.Integer id
	){
		this.id = id;
	}
	
	private SysAttachmentVO sysAttachment;
	
	public void setSysAttachment(SysAttachmentVO sysAttachment){
		this.sysAttachment = sysAttachment;
	}
	
	public SysAttachmentVO getSysAttachment() {
		return sysAttachment;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setApplyCrowdType(String[] value) {
		this.applyCrowdType = value;
	}
	
	public String[] getApplyCrowdType() {
		return this.applyCrowdType;
	}
	public void setDataType(java.lang.String value) {
		this.dataType = value;
	}
	
	public java.lang.String getDataType() {
		return this.dataType;
	}
	public void setAttachmentId(java.lang.Integer value) {
		this.attachmentId = value;
	}
	
	public java.lang.Integer getAttachmentId() {
		return this.attachmentId;
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
			.append("Title",getTitle())
			.append("Description",getDescription())
			.append("ApplyCrowdType",getApplyCrowdType())
			.append("DataType",getDataType())
			.append("AttachmentId",getAttachmentId())
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
		if(obj instanceof SysDatabaseVO == false) return false;
		if(this == obj) return true;
		SysDatabaseVO other = (SysDatabaseVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

