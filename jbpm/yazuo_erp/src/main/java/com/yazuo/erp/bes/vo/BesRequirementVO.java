/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.bes.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date 
 */
public class BesRequirementVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "后端需求";
	public static final String ALIAS_ID = "后端需求ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_STORE_ID = "门店/域ID";
	public static final String ALIAS_CONTACT_CAT = "联系人分类";
	public static final String ALIAS_CONTACT_ID = "联系人ID";
	public static final String ALIAS_SOURCE_CAT = "来源分类";
	public static final String ALIAS_SOURCE_CONTENT = "来源";
	public static final String ALIAS_RESOURCE_REMARK = "需求类型";
	public static final String ALIAS_RESOURCE_EXTRA_REMARK = "其他需求类型";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_CONTENT = "内容";
	public static final String ALIAS_ATTACHMENT_ID = "附件ID";
	public static final String ALIAS_OPERATION_LOG_IDS = "流水IDs";
	public static final String ALIAS_HANDLER_ID = "处理人ID";
	public static final String ALIAS_HANDLED_TIME = "处理时间";
	public static final String ALIAS_CONMENTS = "放弃原因";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_STORE_ID = "storeId";
	public static final String COLUMN_CONTACT_CAT = "contactCat";
	public static final String COLUMN_CONTACT_ID = "contactId";
	public static final String COLUMN_SOURCE_CAT = "sourceCat";
	public static final String COLUMN_SOURCE_CONTENT = "sourceContent";
	public static final String COLUMN_RESOURCE_REMARK = "resourceRemark";
	public static final String COLUMN_RESOURCE_EXTRA_REMARK = "resourceExtraRemark";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_OPERATION_LOG_IDS = "operationLogIds";
	public static final String COLUMN_HANDLER_ID = "handlerId";
	public static final String COLUMN_HANDLED_TIME = "handledTime";
	public static final String COLUMN_CONMENTS = "conments";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"后端需求ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.Integer storeId; //"门店/域ID";
	private java.lang.String contactCat; //"联系人分类";
	private java.lang.Integer contactId; //"联系人ID";
	private java.lang.String sourceCat; //"来源分类";
	private java.lang.String sourceContent; //"来源";
	private java.lang.String resourceRemark; //"需求类型";
	private java.lang.String resourceExtraRemark; //"其他需求类型";
	private java.lang.String title; //"标题";
	private java.lang.String content; //"内容";
	private java.lang.Integer attachmentId; //"附件ID";
	private Integer[] operationLogIds; //"流水IDs";
	private java.lang.Integer handlerId; //"处理人ID";
	private java.util.Date handledTime; //"处理时间";
	private java.lang.String conments; //"放弃原因";
	private java.lang.String status; //"状态";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END
	//others
	private java.lang.Integer pageNumber;
	private java.lang.Integer pageSize;
	
	private List<Map<String, Object>> dicSourceCat;//来源分类
	private List<Map<String, Object>> dicSourceContent;//来源内容
	private List<Map<String, Object>> dicHandler;//处理人
	private List<Map<String, Object>> dicTelConnectStatus;//回访电话接听状态
	
	private Map<String, Object> dicRowSourceCat;//来源分类
	private Map<String, Object> dicRowSourceContent;//来源内容
	private Map<String, Object> dicRowContactCat;//联系人分类
	private Map<String, Object> dicRowHandler;//处理人
	private Map<String, Object> dicRowTelConnectStatus;//回访电话接听状态
	private Map<String, Object> dicRowReqStatus;//需求状态  已回访/未回访等
	private String resourceName;//需求类型
	private String resourceExtraName; //"其他需求类型";
	private Date startTime; //前端过滤条件开始时间
	private Date endTime; //前端过滤条件结束时间

	private java.lang.Integer[] ids; //"后端需求IDs> 批量保存前端参数";
	private List<Map<String, Object>> operstionLogs;//操作流水
	private String merchantName;//商户名称
	private String fesUserName;//前端负责人
	private String reVisitedText = "未回访";//是否已回访
	private String visiteButtonName; //回访按钮
	private String documentType;//文档类型
	private Integer documentId;//文档ID
	private String takingSkillResource; //"话术资源";
	private Integer btnFlag; //标识放弃或完成 [1: 放弃 2: 完成]
	private Integer baseUserId;//管辖范围过滤条件 会话中的用户ID

	public Integer getBaseUserId() {
		return baseUserId;
	}

	public void setBaseUserId(Integer baseUserId) {
		this.baseUserId = baseUserId;
	}

	public String getResourceExtraName() {
		return resourceExtraName;
	}

	public void setResourceExtraName(String resourceExtraName) {
		this.resourceExtraName = resourceExtraName;
	}

	public Integer getBtnFlag() {
		return btnFlag;
	}

	public void setBtnFlag(Integer btnFlag) {
		this.btnFlag = btnFlag;
	}

	public String getTakingSkillResource() {
		return takingSkillResource;
	}

	public void setTakingSkillResource(String takingSkillResource) {
		this.takingSkillResource = takingSkillResource;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getVisiteButtonName() {
		return visiteButtonName;
	}

	public void setVisiteButtonName(String visiteButtonName) {
		this.visiteButtonName = visiteButtonName;
	}

	public String getReVisitedText() {
		return reVisitedText;
	}

	public void setReVisitedText(String reVisitedText) {
		this.reVisitedText = reVisitedText;
	}

	public Map<String, Object> getDicRowReqStatus() {
		return dicRowReqStatus;
	}

	public void setDicRowReqStatus(Map<String, Object> dicRowReqStatus) {
		this.dicRowReqStatus = dicRowReqStatus;
	}

	public String getFesUserName() {
		return fesUserName;
	}

	public void setFesUserName(String fesUserName) {
		this.fesUserName = fesUserName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public List<Map<String, Object>> getOperstionLogs() {
		return operstionLogs;
	}

	public void setOperstionLogs(List<Map<String, Object>> operstionLogs) {
		this.operstionLogs = operstionLogs;
	}

	public java.lang.Integer[] getIds() {
		return ids;
	}

	public void setIds(java.lang.Integer[] ids) {
		this.ids = ids;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BesRequirementVO(){
	}

	public Map<String, Object> getDicRowContactCat() {
		return dicRowContactCat;
	}

	public void setDicRowContactCat(Map<String, Object> dicRowContactCat) {
		this.dicRowContactCat = dicRowContactCat;
	}
	public Map<String, Object> getDicRowSourceCat() {
		return dicRowSourceCat;
	}

	public void setDicRowSourceCat(Map<String, Object> dicRowSourceCat) {
		this.dicRowSourceCat = dicRowSourceCat;
	}

	public Map<String, Object> getDicRowSourceContent() {
		return dicRowSourceContent;
	}

	public void setDicRowSourceContent(Map<String, Object> dicRowSourceContent) {
		this.dicRowSourceContent = dicRowSourceContent;
	}

	public Map<String, Object> getDicRowHandler() {
		return dicRowHandler;
	}

	public void setDicRowHandler(Map<String, Object> dicRowHandler) {
		this.dicRowHandler = dicRowHandler;
	}

	public Map<String, Object> getDicRowTelConnectStatus() {
		return dicRowTelConnectStatus;
	}

	public void setDicRowTelConnectStatus(Map<String, Object> dicRowTelConnectStatus) {
		this.dicRowTelConnectStatus = dicRowTelConnectStatus;
	}

	public java.lang.Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(java.lang.Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public java.lang.Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(java.lang.Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<Map<String, Object>> getDicSourceCat() {
		return dicSourceCat;
	}

	public void setDicSourceCat(List<Map<String, Object>> dicSourceCat) {
		this.dicSourceCat = dicSourceCat;
	}

	public List<Map<String, Object>> getDicSourceContent() {
		return dicSourceContent;
	}

	public void setDicSourceContent(List<Map<String, Object>> dicSourceContent) {
		this.dicSourceContent = dicSourceContent;
	}

	public List<Map<String, Object>> getDicHandler() {
		return dicHandler;
	}

	public void setDicHandler(List<Map<String, Object>> dicHandler) {
		this.dicHandler = dicHandler;
	}

	public List<Map<String, Object>> getDicTelConnectStatus() {
		return dicTelConnectStatus;
	}

	public void setDicTelConnectStatus(List<Map<String, Object>> dicTelConnectStatus) {
		this.dicTelConnectStatus = dicTelConnectStatus;
	}

	public BesRequirementVO(
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
	public void setStoreId(java.lang.Integer value) {
		this.storeId = value;
	}
	
	public java.lang.Integer getStoreId() {
		return this.storeId;
	}
	public void setContactCat(java.lang.String value) {
		this.contactCat = value;
	}
	
	public java.lang.String getContactCat() {
		return this.contactCat;
	}
	public void setContactId(java.lang.Integer value) {
		this.contactId = value;
	}
	
	public java.lang.Integer getContactId() {
		return this.contactId;
	}
	public void setSourceCat(java.lang.String value) {
		this.sourceCat = value;
	}
	
	public java.lang.String getSourceCat() {
		return this.sourceCat;
	}
	public void setSourceContent(java.lang.String value) {
		this.sourceContent = value;
	}
	
	public java.lang.String getSourceContent() {
		return this.sourceContent;
	}
	public void setResourceRemark(java.lang.String value) {
		this.resourceRemark = value;
	}
	
	public java.lang.String getResourceRemark() {
		return this.resourceRemark;
	}
	public void setResourceExtraRemark(java.lang.String value) {
		this.resourceExtraRemark = value;
	}
	
	public java.lang.String getResourceExtraRemark() {
		return this.resourceExtraRemark;
	}
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setAttachmentId(java.lang.Integer value) {
		this.attachmentId = value;
	}
	
	public java.lang.Integer getAttachmentId() {
		return this.attachmentId;
	}

	public Integer[] getOperationLogIds() {
		return operationLogIds;
	}

	public void setOperationLogIds(Integer[] operationLogIds) {
		this.operationLogIds = operationLogIds;
	}

	public void setHandlerId(java.lang.Integer value) {
		this.handlerId = value;
	}
	
	public java.lang.Integer getHandlerId() {
		return this.handlerId;
	}
	public void setHandledTime(java.util.Date value) {
		this.handledTime = value;
	}
	
	public java.util.Date getHandledTime() {
		return this.handledTime;
	}
	public void setConments(java.lang.String value) {
		this.conments = value;
	}
	
	public java.lang.String getConments() {
		return this.conments;
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

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
	
	private Set besCallRecords = null;
	public void setBesCallRecords(Set<BesCallRecordVO> besCallRecord){
		this.besCallRecords = besCallRecord;
	}
	
	public Set<BesCallRecordVO> getBesCallRecords() {
		return besCallRecords;
	}
	
	private SysAttachmentVO sysAttachment;
	
	public void setSysAttachment(SysAttachmentVO sysAttachment){
		this.sysAttachment = sysAttachment;
	}
	
	public SysAttachmentVO getSysAttachment() {
		return sysAttachment;
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
	
	private MktContactVO mktContact;
	
	public void setMktContact(MktContactVO mktContact){
		this.mktContact = mktContact;
	}
	
	public MktContactVO getMktContact() {
		return mktContact;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("MerchantId",getMerchantId())
			.append("StoreId",getStoreId())
			.append("ContactCat",getContactCat())
			.append("ContactId",getContactId())
			.append("SourceCat",getSourceCat())
			.append("SourceContent",getSourceContent())
			.append("ResourceRemark",getResourceRemark())
			.append("ResourceExtraRemark",getResourceExtraRemark())
			.append("Title",getTitle())
			.append("Content",getContent())
			.append("AttachmentId",getAttachmentId())
			.append("OperationLogIds",getOperationLogIds())
			.append("HandlerId",getHandlerId())
			.append("HandledTime",getHandledTime())
			.append("Conments",getConments())
			.append("Status",getStatus())
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
		if(obj instanceof BesRequirementVO == false) return false;
		if(this == obj) return true;
		BesRequirementVO other = (BesRequirementVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

