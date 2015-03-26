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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yazuo.erp.base.EmailVO;
import com.yazuo.erp.mkt.vo.MktBrandInterviewVO;
import com.yazuo.erp.mkt.vo.MktShopSurveyVO;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysDictionaryVO;
import com.yazuo.erp.system.vo.SysDocumentVO;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.vo.TradeCardtypeVO;
import com.yazuo.erp.trade.vo.TradeMessageTemplateVO;

/**
 * @Description 上线流程VO
 * @author erp team
 * @date 
 */
@JsonIgnoreProperties({ "insertTime", FesOnlineProcessVO.COLUMN_UPDATE_TIME})
public class FesOnlineProcessVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "上线流程";
	public static final String ALIAS_ID = "流程ID";
	public static final String ALIAS_PROGRAM_ID = "上线计划ID";
	public static final String ALIAS_STEP_ID = "步骤ID";
	public static final String ALIAS_PLAN_END_TIME = "计划完成时间";
	public static final String ALIAS_END_TIME = "实际完成时间";
	public static final String ALIAS_ONLINE_PROCESS_STATUS = "状态";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PROGRAM_ID = "programId";
	public static final String COLUMN_STEP_ID = "stepId";
	public static final String COLUMN_PLAN_END_TIME = "planEndTime";
	public static final String COLUMN_END_TIME = "endTime";
	public static final String COLUMN_ONLINE_PROCESS_STATUS = "onlineProcessStatus";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"流程ID";
	private java.lang.Integer processId; //"流程ID";
	private java.lang.Integer programId; //"上线计划ID";
	private java.lang.Integer stepId; //"步骤ID";
	private java.util.Date planEndTime; //"计划完成时间";
	private java.util.Date endTime; //"实际完成时间";
	private java.lang.String onlineProcessStatus; //"状态";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	
	//columns END
	
	/*
	 * self-defined attribute start.
	 */
	//for attribute 'onlineProcessStatus'
	private SysDictionaryVO sysDictionary;
	//计划上线时间是否延期，根据 planEndTime + n个工作日算出
	private boolean planEndTimeStatus;
	//判断上线流程的总状态
	private boolean onlineFinalConfirmStatus;
	//关联系统附件表
	private List<SysAttachmentVO> fesSysAttachments;
	//资源的英文描述
	private String resourceRemark = null;
	//具体每一个步骤对应的文件路径，处理附件的时候用
	private String fileConfigPath; 
	//流水表中的用户
	private Integer userId;
	//操作日志
	private List<SysOperationLogVO> sysOperationLogVOs =  new LinkedList<SysOperationLogVO>();//操作流水
	private List<TradeCardtypeVO> tradeCardtypes = null;
	private List<TradeMessageTemplateVO> tradeMessageTemplates = null;
	//商户id，前台传过来
	private Integer merchantId;
	//删除附件是否可用[目前只针对步骤3]
	private Boolean delAvailable;
	//设备配送的时候操作流水使用
	private FesStowageVO fesStowageVO;
	//步骤号
	private String stepNum;
	//前端负责人
	private List<SysUserVO> listUsers;
	//附件和操作流水 合成一个对象，为了排序
	private List<Object> listAttachmentAndOperateLog;
	//邮件相关
	private EmailVO email;
	//回访单
	private List<SysDocumentVO> listSysDocumentVO;
	//回访单的状态
	private String visiterState;
	
	private String onlineStatuesForUI;//为前端区分上线前0/上线后1
	List<FesMaterialRequestVO> fesMaterialRequests; //需求相关
	List<FesMarketingActivityVO> fesMarketingActivitys;//上线后流程， 营销活动
	private Date planReciveCardTime;//预计出卡时间
	//columns END

	public Date getPlanReciveCardTime() {
		return planReciveCardTime;
	}

	public void setPlanReciveCardTime(Date planReciveCardTime) {
		this.planReciveCardTime = planReciveCardTime;
	}

	public List<FesMarketingActivityVO> getFesMarketingActivitys() {
		return fesMarketingActivitys;
	}

	public void setFesMarketingActivitys(List<FesMarketingActivityVO> fesMarketingActivitys) {
		this.fesMarketingActivitys = fesMarketingActivitys;
	}

	public List<FesMaterialRequestVO> getFesMaterialRequests() {
		return fesMaterialRequests;
	}

	public void setFesMaterialRequests(List<FesMaterialRequestVO> fesMaterialRequests) {
		this.fesMaterialRequests = fesMaterialRequests;
	}

	public String getOnlineStatuesForUI() {
		return onlineStatuesForUI;
	}

	public void setOnlineStatuesForUI(String onlineStatuesForUI) {
		this.onlineStatuesForUI = onlineStatuesForUI;
	}
	/*
	 * self-defined attribute end.
	 */

	public String getVisiterState() {
		return visiterState;
	}

	public void setVisiterState(String visiterState) {
		this.visiterState = visiterState;
	}

	public List<SysDocumentVO> getListSysDocumentVO() {
		return listSysDocumentVO;
	}

	public void setListSysDocumentVO(List<SysDocumentVO> listSysDocumentVO) {
		this.listSysDocumentVO = listSysDocumentVO;
	}

	public EmailVO getEmail() {
		return email;
	}

	public void setEmail(EmailVO email) {
		this.email = email;
	}

	public List<Object> getListAttachmentAndOperateLog() {
		return listAttachmentAndOperateLog;
	}

	public void setListAttachmentAndOperateLog(List<Object> listAttachmentAndOperateLog) {
		this.listAttachmentAndOperateLog = listAttachmentAndOperateLog;
	}

	public List<SysUserVO> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<SysUserVO> listUsers) {
		this.listUsers = listUsers;
	}
	public List<TradeMessageTemplateVO> getTradeMessageTemplates() {
		return tradeMessageTemplates;
	}

	public void setTradeMessageTemplates(List<TradeMessageTemplateVO> tradeMessageTemplates) {
		this.tradeMessageTemplates = tradeMessageTemplates;
	}
	public List<TradeCardtypeVO> getTradeCardtypes() {
		return tradeCardtypes;
	}

	public void setTradeCardtypes(List<TradeCardtypeVO> tradeCardtypes) {
		this.tradeCardtypes = tradeCardtypes;
	}

	public java.lang.Integer getProcessId() {
		return processId;
	}

	public void setProcessId(java.lang.Integer processId) {
		this.processId = processId;
	}

	public String getStepNum() {
		return stepNum;
	}

	public void setStepNum(String stepNum) {
		this.stepNum = stepNum;
	}

	public FesStowageVO getFesStowageVO() {
		return fesStowageVO;
	}

	public void setFesStowageVO(FesStowageVO fesStowageVO) {
		this.fesStowageVO = fesStowageVO;
	}

	public Boolean getDelAvailable() {
		return delAvailable;
	}

	public void setDelAvailable(Boolean delAvailable) {
		this.delAvailable = delAvailable;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<SysOperationLogVO> getSysOperationLogVOs() {
		return sysOperationLogVOs;
	}

	public void setSysOperationLogVOs(List<SysOperationLogVO> sysOperationLogVOs) {
		this.sysOperationLogVOs = sysOperationLogVOs;
	}
	public String getFileConfigPath() {
		return fileConfigPath;
	}

	public void setFileConfigPath(String stepNum) {
		this.fileConfigPath = stepNum;
	}
	public String getResourceRemark() {
		return resourceRemark;
	}

	public void setResourceRemark(String resourceRemark) {
		this.resourceRemark = resourceRemark;
	}

	public List<SysAttachmentVO> getFesSysAttachments() {
		return fesSysAttachments;
	}

	public void setFesSysAttachments(List<SysAttachmentVO> fesSysAttachments) {
		this.fesSysAttachments = fesSysAttachments;
	}
	public boolean isOnlineFinalConfirmStatus() {
		return onlineFinalConfirmStatus;
	}

	public void setOnlineFinalConfirmStatus(boolean onlineFinalConfirmStatus) {
		this.onlineFinalConfirmStatus = onlineFinalConfirmStatus;
	}

	public boolean isPlanEndTimeStatus() {
		return planEndTimeStatus;
	}

	public void setPlanEndTimeStatus(boolean planEndTimeStatus) {
		this.planEndTimeStatus = planEndTimeStatus;
	}

	public SysDictionaryVO getSysDictionary() {
		return sysDictionary;
	}

	public void setSysDictionary(SysDictionaryVO sysDictionary) {
		this.sysDictionary = sysDictionary;
	}
	
	public FesOnlineProcessVO(){
	}

	public FesOnlineProcessVO(
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
	public void setProgramId(java.lang.Integer value) {
		this.programId = value;
	}
	
	public java.lang.Integer getProgramId() {
		return this.programId;
	}
	public void setStepId(java.lang.Integer value) {
		this.stepId = value;
	}
	
	public java.lang.Integer getStepId() {
		return this.stepId;
	}
	public void setPlanEndTime(java.util.Date value) {
		this.planEndTime = value;
	}
	
	public java.util.Date getPlanEndTime() {
		return this.planEndTime;
	}
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setOnlineProcessStatus(java.lang.String value) {
		this.onlineProcessStatus = value;
	}
	
	public java.lang.String getOnlineProcessStatus() {
		return this.onlineProcessStatus;
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
	
	private List fesTrainPlans = null;
	private List<MktBrandInterviewVO> mktBrandInterViews = new ArrayList<MktBrandInterviewVO>();
	private List<MktShopSurveyVO> mktShopSurveys = new ArrayList<MktShopSurveyVO>();
	
	public List<MktBrandInterviewVO> getMktBrandInterViews() {
		return mktBrandInterViews;
	}

	public void setMktBrandInterViews(List<MktBrandInterviewVO> mktBrandInterViews) {
		this.mktBrandInterViews = mktBrandInterViews;
	}

	public List<MktShopSurveyVO> getMktShopSurveys() {
		return mktShopSurveys;
	}

	public void setMktShopSurveys(List<MktShopSurveyVO> mktShopSurveys) {
		this.mktShopSurveys = mktShopSurveys;
	}

	public void setFesTrainPlans(List<FesTrainPlanVO> fesTrainPlan){
		this.fesTrainPlans = fesTrainPlan;
	}
	
	public List<FesTrainPlanVO> getFesTrainPlans() {
		return fesTrainPlans;
	}
	
	private List fesRemarks = null;
	public void setFesRemarks(List<FesRemarkVO> fesRemark){
		this.fesRemarks = fesRemark;
	}
	
	public List<FesRemarkVO> getFesRemarks() {
		return fesRemarks;
	}
	
	private List fesOpenCardApplications = null;
	public void setFesOpenCardApplications(List<FesOpenCardApplicationVO> fesOpenCardApplication){
		this.fesOpenCardApplications = fesOpenCardApplication;
	}
	
	public List<FesOpenCardApplicationVO> getFesOpenCardApplications() {
		return fesOpenCardApplications;
	}
	
	private List fesStowages = null;
	public void setFesStowages(List<FesStowageVO> fesStowage){
		this.fesStowages = fesStowage;
	}
	
	public List<FesStowageVO> getFesStowages() {
		return fesStowages;
	}
	
	private Set fesProcessAttachments = new HashSet(0);
	public void setFesProcessAttachments(Set<FesProcessAttachmentVO> fesProcessAttachment){
		this.fesProcessAttachments = fesProcessAttachment;
	}
	
	public Set<FesProcessAttachmentVO> getFesProcessAttachments() {
		return fesProcessAttachments;
	}
	
	private FesOnlineProgramVO fesOnlineProgram;
	
	public void setFesOnlineProgram(FesOnlineProgramVO fesOnlineProgram){
		this.fesOnlineProgram = fesOnlineProgram;
	}
	
	public FesOnlineProgramVO getFesOnlineProgram() {
		return fesOnlineProgram;
	}
	
	private FesStepVO fesStep;
	
	public void setFesStep(FesStepVO fesStep){
		this.fesStep = fesStep;
	}
	
	public FesStepVO getFesStep() {
		return fesStep;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProgramId",getProgramId())
			.append("StepId",getStepId())
			.append("PlanEndTime",getPlanEndTime())
			.append("EndTime",getEndTime())
			.append("OnlineProcessStatus",getOnlineProcessStatus())
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
		if(obj instanceof FesOnlineProcessVO == false) return false;
		if(this == obj) return true;
		FesOnlineProcessVO other = (FesOnlineProcessVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

