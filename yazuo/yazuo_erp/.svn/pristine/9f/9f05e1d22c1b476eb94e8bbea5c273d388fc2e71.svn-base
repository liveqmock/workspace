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

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.vo.SysAttachmentVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class FesMarketingActivityVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "营销活动";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_ACTIVITY_TYPE = "营销活动类型";
	public static final String ALIAS_ACTIVITY_TITLE = "标题";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_ATTACHMENT_ID = "附件ID";
	public static final String ALIAS_APPLICANT_BY = "申请人";
	public static final String ALIAS_APPLICATION_TIME = "申请时间";
	public static final String ALIAS_REASON = "关闭原因";
	public static final String ALIAS_MARKETING_ACTIVITY_STATUS = "活动状态";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_ACTIVITY_TYPE = "activityType";
	public static final String COLUMN_ACTIVITY_TITLE = "activityTitle";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_APPLICANT_BY = "applicantBy";
	public static final String COLUMN_APPLICATION_TIME = "applicationTime";
	public static final String COLUMN_REASON = "reason";
	public static final String COLUMN_MARKETING_ACTIVITY_STATUS = "marketingActivityStatus";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String activityType; //"营销活动类型";
	private java.lang.String activityTitle; //"标题";
	private java.lang.String description; //"描述";
	private java.lang.Integer attachmentId; //"附件ID";
	private java.lang.Integer applicantBy; //"申请人";
	private java.util.Date applicationTime; //"申请时间";
	private java.lang.String reason; //"关闭原因";
	private java.lang.String marketingActivityStatus; //"活动状态";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	
	private String activityTypeName; //营销活动类型名称
	private String merchantName; // 商户名称
	private String filePath; // 文件下载路径
	private String attachmentName; // 文件现在名称
	private String originalFileName; // 文件原始名称
	private String fileSize; // 文件大小
	private String applicantByName; // 申请人姓名
	
	private SysAttachmentVO sysAttachmentVO; //附件相关
	private List<Object> listAttachmentAndOperateLog;//活动流水和附件流水

	public List<Object> getListAttachmentAndOperateLog() {
		return listAttachmentAndOperateLog;
	}

	public void setListAttachmentAndOperateLog(List<Object> listAttachmentAndOperateLog) {
		this.listAttachmentAndOperateLog = listAttachmentAndOperateLog;
	}

	public SysAttachmentVO getSysAttachmentVO() {
		return sysAttachmentVO;
	}

	public void setSysAttachmentVO(SysAttachmentVO sysAttachmentVO) {
		this.sysAttachmentVO = sysAttachmentVO;
	}

	public FesMarketingActivityVO(){
	}

	public FesMarketingActivityVO(
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
	public void setActivityType(java.lang.String value) {
		this.activityType = value;
	}
	
	public java.lang.String getActivityType() {
		return this.activityType;
	}
	public void setActivityTitle(java.lang.String value) {
		this.activityTitle = value;
	}
	
	public java.lang.String getActivityTitle() {
		return this.activityTitle;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setAttachmentId(java.lang.Integer value) {
		this.attachmentId = value;
	}
	
	public java.lang.Integer getAttachmentId() {
		return this.attachmentId;
	}
	public void setApplicantBy(java.lang.Integer value) {
		this.applicantBy = value;
	}
	
	public java.lang.Integer getApplicantBy() {
		return this.applicantBy;
	}
	public void setApplicationTime(java.util.Date value) {
		this.applicationTime = value;
	}
	
	public java.util.Date getApplicationTime() {
		return this.applicationTime;
	}
	public void setReason(java.lang.String value) {
		this.reason = value;
	}
	
	public java.lang.String getReason() {
		return this.reason;
	}
	public void setMarketingActivityStatus(java.lang.String value) {
		this.marketingActivityStatus = value;
	}
	
	public java.lang.String getMarketingActivityStatus() {
		return this.marketingActivityStatus;
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
	
	private SynMerchantVO synMerchant;
	
	public void setSynMerchant(SynMerchantVO synMerchant){
		this.synMerchant = synMerchant;
	}
	
	public SynMerchantVO getSynMerchant() {
		return synMerchant;
	}
	
	public String getActivityTypeName() {
		return activityTypeName;
	}

	public void setActivityTypeName(String activityTypeName) {
		this.activityTypeName = activityTypeName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getApplicantByName() {
		return applicantByName;
	}

	public void setApplicantByName(String applicantByName) {
		this.applicantByName = applicantByName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("MerchantId",getMerchantId())
			.append("ActivityType",getActivityType())
			.append("ActivityTitle",getActivityTitle())
			.append("Description",getDescription())
			.append("AttachmentId",getAttachmentId())
			.append("ApplicantBy",getApplicantBy())
			.append("ApplicationTime",getApplicationTime())
			.append("Reason",getReason())
			.append("MarketingActivityStatus",getMarketingActivityStatus())
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
		if(obj instanceof FesMarketingActivityVO == false) return false;
		if(this == obj) return true;
		FesMarketingActivityVO other = (FesMarketingActivityVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

