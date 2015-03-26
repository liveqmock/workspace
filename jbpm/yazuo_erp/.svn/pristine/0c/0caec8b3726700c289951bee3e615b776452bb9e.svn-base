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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.fes.vo.FesMarketingActivityVO;
import com.yazuo.erp.fes.vo.FesPlanAttachmentVO;
import com.yazuo.erp.fes.vo.FesProcessAttachmentVO;

/**
 * @author erp team
 * @date 
 */
public class SysAttachmentVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "附件";
	public static final String ALIAS_ID = "附件ID";
	public static final String ALIAS_ATTACHMENT_NAME = "附件名称";
	public static final String ALIAS_ORIGINAL_FILE_NAME = "原始文件名";
	public static final String ALIAS_ATTACHMENT_TYPE = "附件类型";
	public static final String ALIAS_ATTACHMENT_SIZE = "附件大小";
	public static final String ALIAS_ATTACHMENT_PATH = "附件路径";
	public static final String ALIAS_ATTACHMENT_SUFFIX = "附件后缀";
	public static final String ALIAS_MODULE_TYPE = "模块类型";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_IS_DOWNLOADABLE = "是否可下载";
	public static final String ALIAS_HOURS = "时长";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_ATTACHMENT_NAME = "attachmentName";
	public static final String COLUMN_ORIGINAL_FILE_NAME = "originalFileName";
	public static final String COLUMN_ATTACHMENT_TYPE = "attachmentType";
	public static final String COLUMN_ATTACHMENT_SIZE = "attachmentSize";
	public static final String COLUMN_ATTACHMENT_PATH = "attachmentPath";
	public static final String COLUMN_ATTACHMENT_SUFFIX = "attachmentSuffix";
	public static final String COLUMN_MODULE_TYPE = "moduleType";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_IS_DOWNLOADABLE = "isDownloadable";
	public static final String COLUMN_HOURS = "hours";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"附件ID";
	private java.lang.String attachmentName; //"附件名称";
	private java.lang.String originalFileName; //"原始文件名";
	private java.lang.String attachmentType; //"附件类型";
	private java.lang.String attachmentSize; //"附件大小";
	private java.lang.String attachmentPath; //"附件路径";
	private java.lang.String attachmentSuffix; //"附件后缀";
	private java.lang.String moduleType; //"模块类型";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String isDownloadable; //"是否可下载";
	private java.math.BigDecimal hours; //"时长";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	
	private BigDecimal attachmentSizeKB; //"附件大小，单位KB";
	private String merchantName;
	private String fileFullPath; //文件全路径， 不包含主机名和端口
	private String userName; //操作人
	//columns END
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	/*
	 * self-defined attribute start.
	 */
	//关联系统附件表
	private String processAttachmentType;
	
	/*
	 * self-defined attribute end.
	 */

	public String getProcessAttachmentType() {
		return processAttachmentType;
	}

	public BigDecimal getAttachmentSizeKB() {
		return attachmentSizeKB;
	}

	public void setAttachmentSizeKB(BigDecimal attachmentSizeKB) {
		this.attachmentSizeKB = attachmentSizeKB;
	}

	public void setProcessAttachmentType(String processAttachmentType) {
		this.processAttachmentType = processAttachmentType;
	}

	public SysAttachmentVO(){
	}

	public SysAttachmentVO(
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
	public void setAttachmentName(java.lang.String value) {
		this.attachmentName = value;
	}
	
	public java.lang.String getAttachmentName() {
		return this.attachmentName;
	}
	public void setOriginalFileName(java.lang.String value) {
		this.originalFileName = value;
	}
	
	public java.lang.String getOriginalFileName() {
		return this.originalFileName;
	}
	public void setAttachmentType(java.lang.String value) {
		this.attachmentType = value;
	}
	
	public java.lang.String getAttachmentType() {
		return this.attachmentType;
	}
	public void setAttachmentSize(java.lang.String value) {
		this.attachmentSize = value;
	}
	
	public java.lang.String getAttachmentSize() {
		return this.attachmentSize;
	}
	public void setAttachmentPath(java.lang.String value) {
		this.attachmentPath = value;
	}
	
	public java.lang.String getAttachmentPath() {
		return this.attachmentPath;
	}
	public void setAttachmentSuffix(java.lang.String value) {
		this.attachmentSuffix = value;
	}
	
	public java.lang.String getAttachmentSuffix() {
		return this.attachmentSuffix;
	}
	public void setModuleType(java.lang.String value) {
		this.moduleType = value;
	}
	
	public java.lang.String getModuleType() {
		return this.moduleType;
	}
	public void setIsEnable(java.lang.String value) {
		this.isEnable = value;
	}
	
	public java.lang.String getIsEnable() {
		return this.isEnable;
	}
	public void setIsDownloadable(java.lang.String value) {
		this.isDownloadable = value;
	}
	
	public java.lang.String getIsDownloadable() {
		return this.isDownloadable;
	}
	public void setHours(java.math.BigDecimal value) {
		this.hours = value;
	}
	
	public java.math.BigDecimal getHours() {
		return this.hours;
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
	
	private Set fesMarketingActivitys = new HashSet(0);
	public void setFesMarketingActivitys(Set<FesMarketingActivityVO> fesMarketingActivity){
		this.fesMarketingActivitys = fesMarketingActivity;
	}
	
	public Set<FesMarketingActivityVO> getFesMarketingActivitys() {
		return fesMarketingActivitys;
	}
	
	private Set fesProcessAttachments = new HashSet(0);
	public void setFesProcessAttachments(Set<FesProcessAttachmentVO> fesProcessAttachment){
		this.fesProcessAttachments = fesProcessAttachment;
	}
	
	public Set<FesProcessAttachmentVO> getFesProcessAttachments() {
		return fesProcessAttachments;
	}
	
	private Set fesPlanAttachments = new HashSet(0);
	public void setFesPlanAttachments(Set<FesPlanAttachmentVO> fesPlanAttachment){
		this.fesPlanAttachments = fesPlanAttachment;
	}
	
	public Set<FesPlanAttachmentVO> getFesPlanAttachments() {
		return fesPlanAttachments;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AttachmentName",getAttachmentName())
			.append("OriginalFileName",getOriginalFileName())
			.append("AttachmentType",getAttachmentType())
			.append("AttachmentSize",getAttachmentSize())
			.append("AttachmentPath",getAttachmentPath())
			.append("AttachmentSuffix",getAttachmentSuffix())
			.append("ModuleType",getModuleType())
			.append("IsEnable",getIsEnable())
			.append("IsDownloadable",getIsDownloadable())
			.append("Hours",getHours())
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
		if(obj instanceof SysAttachmentVO == false) return false;
		if(this == obj) return true;
		SysAttachmentVO other = (SysAttachmentVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

