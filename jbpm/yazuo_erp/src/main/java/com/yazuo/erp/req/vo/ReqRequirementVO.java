/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.req.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date 
 */
public class ReqRequirementVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "需求";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_NAME = "需求名称";
	public static final String ALIAS_REQUIREMENT_PLAN_TYPE = "需求计划类型";
	public static final String ALIAS_PLAN_TIME = "计划时间";
	public static final String ALIAS_IS_REQUIREMENTS_REVIEW = "是否通过需求评审";
	public static final String ALIAS_REQUIREMENTS_REVIEW_TIME = "通过需求评审时间";
	public static final String ALIAS_IS_PRODUCT_REVIEW = "是否通过产品评审";
	public static final String ALIAS_PRODUCT_REVIEW_TIME = "通过产品评审时间";
	public static final String ALIAS_DEVELOPMENT_TIME = "研发开发时间";
	public static final String ALIAS_PLAN_END_TIME = "预计完成时间";
	public static final String ALIAS_LEADER = "开发负责人";
	public static final String ALIAS_REQUIREMENT_STATUS = "需求状态";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_PRIORITY = "priority";
	public static final String ALIAS_REQ_SOURCE = "reqSource";

	//columns START
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.String requirementPlanType;
	private java.util.Date planTime;
	private java.lang.String isRequirementsReview;
	private java.util.Date requirementsReviewTime;
	private java.lang.String isProductReview;
	private java.util.Date productReviewTime;
	private java.util.Date developmentTime;
	private java.util.Date planEndTime;
	private java.lang.String leader;
	private java.lang.String requirementStatus;
	private java.lang.String requirementStatusVal;
	private java.lang.String requirementPlanTypeVal;
	private java.lang.String isEnable;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private java.util.Date insertTime;
	private java.lang.Integer updateBy;
	private java.util.Date updateTime;
	private java.lang.String requirementsReviewOpinion; //"需求评审意见";
	private java.lang.String productReviewOpinion; //"产品评审意见";
	//others
	private java.util.Date startPlanTime;
	private java.util.Date endPlanTime;
	private java.util.Date onlineTime;
	private java.lang.String priority; //"priority";
	private java.lang.String reqSource; //"reqSource";
	private String operator;//操作人
	private String reqSourceText;//来源 显示文本
	
	public String getReqSourceText() {
		return reqSourceText;
	}
	public void setReqSourceText(String reqSourceText) {
		this.reqSourceText = reqSourceText;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public java.lang.String getPriority() {
		return priority;
	}

	public void setPriority(java.lang.String priority) {
		this.priority = priority;
	}

	public java.lang.String getReqSource() {
		return reqSource;
	}

	public void setReqSource(java.lang.String reqSource) {
		this.reqSource = reqSource;
	}

	public java.util.Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(java.util.Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public java.util.Date getStartPlanTime() {
		return startPlanTime;
	}

	public void setStartPlanTime(java.util.Date startPlanTime) {
		this.startPlanTime = startPlanTime;
	}

	public java.util.Date getEndPlanTime() {
		return endPlanTime;
	}

	public void setEndPlanTime(java.util.Date endPlanTime) {
		this.endPlanTime = endPlanTime;
	}

	private java.lang.Integer pageNumber;
	private java.lang.Integer pageSize;
	
	//前台参数
	private List projectIds;
	private String planType;
	private String projectStatus;
	

	public java.lang.String getRequirementsReviewOpinion() {
		return requirementsReviewOpinion;
	}

	public void setRequirementsReviewOpinion(java.lang.String requirementsReviewOpinion) {
		this.requirementsReviewOpinion = requirementsReviewOpinion;
	}

	public java.lang.String getProductReviewOpinion() {
		return productReviewOpinion;
	}

	public void setProductReviewOpinion(java.lang.String productReviewOpinion) {
		this.productReviewOpinion = productReviewOpinion;
	}

	public java.lang.String getRequirementStatusVal() {
		return requirementStatusVal;
	}

	public void setRequirementStatusVal(java.lang.String requirementStatusVal) {
		this.requirementStatusVal = requirementStatusVal;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	private Set reqProjects = new HashSet(0);
	
	//columns END

	public Set getReqProjects() {
		return reqProjects;
	}

	public void setReqProjects(Set reqProjects) {
		this.reqProjects = reqProjects;
	}

	public List getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List projectIds) {
		this.projectIds = projectIds;
	}

	public java.lang.Integer getPageNumber() {
		return pageNumber;
	}

	public java.lang.String getRequirementPlanTypeVal() {
		return requirementPlanTypeVal;
	}

	public void setRequirementPlanTypeVal(java.lang.String requirementPlanTypeVal) {
		this.requirementPlanTypeVal = requirementPlanTypeVal;
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

	public ReqRequirementVO(){
	}

	public ReqRequirementVO(
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
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setRequirementPlanType(java.lang.String value) {
		this.requirementPlanType = value;
	}
	
	public java.lang.String getRequirementPlanType() {
		return this.requirementPlanType;
	}
	public void setPlanTime(java.util.Date value) {
		this.planTime = value;
	}
	
	public java.util.Date getPlanTime() {
		return this.planTime;
	}
	public void setIsRequirementsReview(java.lang.String value) {
		this.isRequirementsReview = value;
	}
	
	public java.lang.String getIsRequirementsReview() {
		return this.isRequirementsReview;
	}
	public void setRequirementsReviewTime(java.util.Date value) {
		this.requirementsReviewTime = value;
	}
	
	public java.util.Date getRequirementsReviewTime() {
		return this.requirementsReviewTime;
	}
	public void setIsProductReview(java.lang.String value) {
		this.isProductReview = value;
	}
	
	public java.lang.String getIsProductReview() {
		return this.isProductReview;
	}
	public void setProductReviewTime(java.util.Date value) {
		this.productReviewTime = value;
	}
	
	public java.util.Date getProductReviewTime() {
		return this.productReviewTime;
	}
	public void setDevelopmentTime(java.util.Date value) {
		this.developmentTime = value;
	}
	
	public java.util.Date getDevelopmentTime() {
		return this.developmentTime;
	}
	public void setPlanEndTime(java.util.Date value) {
		this.planEndTime = value;
	}
	
	public java.util.Date getPlanEndTime() {
		return this.planEndTime;
	}
	public void setLeader(java.lang.String value) {
		this.leader = value;
	}
	
	public java.lang.String getLeader() {
		return this.leader;
	}
	public void setRequirementStatus(java.lang.String value) {
		this.requirementStatus = value;
	}
	
	public java.lang.String getRequirementStatus() {
		return this.requirementStatus;
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
	
	private Set reqProjectRequirements = new HashSet(0);
	public void setReqProjectRequirements(Set<ReqProjectRequirementVO> reqProjectRequirement){
		this.reqProjectRequirements = reqProjectRequirement;
	}
	
	public Set<ReqProjectRequirementVO> getReqProjectRequirements() {
		return reqProjectRequirements;
	}
	
	private SysUserVO sysUser;
	
	public void setSysUser(SysUserVO sysUser){
		this.sysUser = sysUser;
	}
	
	public SysUserVO getSysUser() {
		return sysUser;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("RequirementPlanType",getRequirementPlanType())
			.append("PlanTime",getPlanTime())
			.append("IsRequirementsReview",getIsRequirementsReview())
			.append("RequirementsReviewTime",getRequirementsReviewTime())
			.append("IsProductReview",getIsProductReview())
			.append("ProductReviewTime",getProductReviewTime())
			.append("DevelopmentTime",getDevelopmentTime())
			.append("PlanEndTime",getPlanEndTime())
			.append("Leader",getLeader())
			.append("RequirementStatus",getRequirementStatus())
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
		if(obj instanceof ReqRequirementVO == false) return false;
		if(this == obj) return true;
		ReqRequirementVO other = (ReqRequirementVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

