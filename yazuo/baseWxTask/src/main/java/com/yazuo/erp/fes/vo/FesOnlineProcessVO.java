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

/**
 * @Description TODO
 * @author erp team
 * @date
 */
public class FesOnlineProcessVO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
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

	// columns START
	private java.lang.Integer id; // "流程ID";
	private java.lang.Integer programId; // "上线计划ID";
	private java.lang.Integer stepId; // "步骤ID";
	private java.util.Date planEndTime; // "计划完成时间";
	private java.util.Date endTime; // "实际完成时间";
	private java.lang.String onlineProcessStatus; // "状态";
	private java.lang.String remark; // "备注";
	private java.lang.Integer insertBy; // "创建人";
	private java.util.Date insertTime; // "创建时间";
	private java.lang.Integer updateBy; // "最后修改人";
	private java.util.Date updateTime; // "最后修改时间";
	// columns END

	// 计划上线时间是否延期，根据 planEndTime + n个工作日算出
	private boolean planEndTimeStatus;

	public boolean isPlanEndTimeStatus() {
		return planEndTimeStatus;
	}

	public void setPlanEndTimeStatus(boolean planEndTimeStatus) {
		this.planEndTimeStatus = planEndTimeStatus;
	}

	// self-defined attribute end.

	public FesOnlineProcessVO() {
	}

	public FesOnlineProcessVO(java.lang.Integer id) {
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

	private FesOnlineProgramVO fesOnlineProgram;

	public void setFesOnlineProgram(FesOnlineProgramVO fesOnlineProgram) {
		this.fesOnlineProgram = fesOnlineProgram;
	}

	public FesOnlineProgramVO getFesOnlineProgram() {
		return fesOnlineProgram;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId())
				.append("ProgramId", getProgramId()).append("StepId", getStepId()).append("PlanEndTime", getPlanEndTime())
				.append("EndTime", getEndTime()).append("OnlineProcessStatus", getOnlineProcessStatus())
				.append("Remark", getRemark()).append("InsertBy", getInsertBy()).append("InsertTime", getInsertTime())
				.append("UpdateBy", getUpdateBy()).append("UpdateTime", getUpdateTime()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof FesOnlineProcessVO == false)
			return false;
		if (this == obj)
			return true;
		FesOnlineProcessVO other = (FesOnlineProcessVO) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
