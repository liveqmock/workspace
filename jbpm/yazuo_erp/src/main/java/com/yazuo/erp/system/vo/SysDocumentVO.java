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
public class SysDocumentVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "填单信息";
	public static final String ALIAS_ID = "填单ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_DOCUMENT_TYPE = "填单类型";
	public static final String ALIAS_ATTACHMENT_ID = "附件ID";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_DOCUMENT_TYPE = "documentType";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"填单ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String documentType; //"填单类型";
	private java.lang.Integer attachmentId; //"附件ID";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END
	
	private SysAttachmentVO sysAttachment;
	private List<SysDocumentDtlVO> sysDocumentDtlList; 
	private String documentFilePath; //文件uri的路径， 用于前台下载
	private String onlineProcessStatus; //上线流程状态
	private java.lang.String documentTypeText; //"填单类型显示文本";
	private java.lang.String processAttachmentType; //"附件类型";

	private java.lang.String originalFileName; //"原始文件名";
	private java.lang.String optionContent; //"选项内容";
	
	public java.lang.String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(java.lang.String optionContent) {
		this.optionContent = optionContent;
	}

	public java.lang.String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(java.lang.String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public java.lang.String getProcessAttachmentType() {
		return processAttachmentType;
	}

	public void setProcessAttachmentType(java.lang.String processAttachmentType) {
		this.processAttachmentType = processAttachmentType;
	}

	public java.lang.String getDocumentTypeText() {
		return documentTypeText;
	}

	public void setDocumentTypeText(java.lang.String documentTypeText) {
		this.documentTypeText = documentTypeText;
	}

	public String getOnlineProcessStatus() {
		return onlineProcessStatus;
	}

	public void setOnlineProcessStatus(String onlineProcessStatus) {
		this.onlineProcessStatus = onlineProcessStatus;
	}

	public String getDocumentFilePath() {
		return documentFilePath;
	}

	public void setDocumentFilePath(String documentFilePath) {
		this.documentFilePath = documentFilePath;
	}

	public SysAttachmentVO getSysAttachment() {
		return sysAttachment;
	}

	public void setSysAttachment(SysAttachmentVO sysAttachment) {
		this.sysAttachment = sysAttachment;
	}

	public List<SysDocumentDtlVO> getSysDocumentDtlList() {
		return sysDocumentDtlList;
	}

	public void setSysDocumentDtlList(List<SysDocumentDtlVO> sysDocumentDtlList) {
		this.sysDocumentDtlList = sysDocumentDtlList;
	}

	public SysDocumentVO(){
	}

	public SysDocumentVO(
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
	public void setMerchantId(java.lang.Integer value) {
		this.merchantId = value;
	}
	
	public java.lang.Integer getMerchantId() {
		return this.merchantId;
	}
	public void setDocumentType(java.lang.String value) {
		this.documentType = value;
	}
	
	public java.lang.String getDocumentType() {
		return this.documentType;
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
			.append("MerchantId",getMerchantId())
			.append("DocumentType",getDocumentType())
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
		if(obj instanceof SysDocumentVO == false) return false;
		if(this == obj) return true;
		SysDocumentVO other = (SysDocumentVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

