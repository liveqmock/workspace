/**
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

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;
import com.yazuo.erp.system.vo.SysAttachmentVO;

/**
 * @author erp team
 * @date 
 */
public class FesMaterialRequestVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "设计需求单";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_PROCESS_ID = "流程ID";
	public static final String ALIAS_ACCESS_ID = "对接人";
	public static final String ALIAS_DESCRIPTION = "物料文案";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PROCESS_ID = "processId";
	public static final String COLUMN_ACCESS_ID = "accessId";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer processId; //"流程ID";
	private java.lang.Integer accessId; //"对接人";
	private java.lang.String description; //"物料文案";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.Integer attachmentId; //"附件ID";
	private SysAttachmentVO sysAttachmentVO; //"附件信息";
	//columns END

	public SysAttachmentVO getSysAttachmentVO() {
		return sysAttachmentVO;
	}

	public void setSysAttachmentVO(SysAttachmentVO sysAttachmentVO) {
		this.sysAttachmentVO = sysAttachmentVO;
	}


	private List<FesMaterialRequestDtlVO> fesMaterialRequestDtlVOs;//设计需求单明细
	private java.lang.String onlineProcessStatus; //"流程状态";
	
	public List<FesMaterialRequestDtlVO> getFesMaterialRequestDtlVOs() {
		return fesMaterialRequestDtlVOs;
	}

	public void setFesMaterialRequestDtlVOs(List<FesMaterialRequestDtlVO> fesMaterialRequestDtlVOs) {
		this.fesMaterialRequestDtlVOs = fesMaterialRequestDtlVOs;
	}

	public java.lang.String getOnlineProcessStatus() {
		return onlineProcessStatus;
	}

	public void setOnlineProcessStatus(java.lang.String onlineProcessStatus) {
		this.onlineProcessStatus = onlineProcessStatus;
	}

	public FesMaterialRequestVO(){
	}

	public FesMaterialRequestVO(
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
		
	public void setAccessId(java.lang.Integer value) {
		this.accessId = value;
	}
	
	public java.lang.Integer getAccessId() {
		return this.accessId;
	}
		
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
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
		
	public void setAttachmentId(java.lang.Integer value) {
		this.attachmentId = value;
	}
	
	public java.lang.Integer getAttachmentId() {
		return this.attachmentId;
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
			.append("ProcessId",getProcessId())
			.append("AccessId",getAccessId())
			.append("Description",getDescription())
			.append("IsEnable",getIsEnable())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.append("AttachmentId",getAttachmentId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof FesMaterialRequestVO == false) return false;
		if(this == obj) return true;
		FesMaterialRequestVO other = (FesMaterialRequestVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

