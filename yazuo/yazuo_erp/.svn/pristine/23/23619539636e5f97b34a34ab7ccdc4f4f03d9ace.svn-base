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

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class FesStepVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "上线前步骤";
	public static final String ALIAS_ID = "步骤ID";
	public static final String ALIAS_STEP_NUM = "步骤编码";
	public static final String ALIAS_STEP_NAME = "步骤名称";
	public static final String ALIAS_TIP = "提示语";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_STEP_NUM = "stepNum";
	public static final String COLUMN_STEP_NAME = "stepName";
	public static final String COLUMN_TIP = "tip";
	public static final String COLUMN_SORT = "sort";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"步骤ID";
	private java.lang.String stepNum; //"步骤编码";
	private java.lang.String stepName; //"步骤名称";
	private java.lang.String tip; //"提示语";
	private java.lang.Integer sort; //"排序";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	
	private Integer mainPageStatus;//自定义流程状态
	//columns END

	public Integer getMainPageStatus() {
		return mainPageStatus;
	}

	public void setMainPageStatus(Integer mainPageStatus) {
		this.mainPageStatus = mainPageStatus;
	}

	public FesStepVO(){
	}

	public FesStepVO(
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
	public void setStepNum(java.lang.String value) {
		this.stepNum = value;
	}
	
	public java.lang.String getStepNum() {
		return this.stepNum;
	}
	public void setStepName(java.lang.String value) {
		this.stepName = value;
	}
	
	public java.lang.String getStepName() {
		return this.stepName;
	}
	public void setTip(java.lang.String value) {
		this.tip = value;
	}
	
	public java.lang.String getTip() {
		return this.tip;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
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
	
	private Set fesOnlineProcesss = new HashSet(0);
	public void setFesOnlineProcesss(Set<FesOnlineProcessVO> fesOnlineProcess){
		this.fesOnlineProcesss = fesOnlineProcess;
	}
	
	public Set<FesOnlineProcessVO> getFesOnlineProcesss() {
		return fesOnlineProcesss;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("StepNum",getStepNum())
			.append("StepName",getStepName())
			.append("Tip",getTip())
			.append("Sort",getSort())
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
		if(obj instanceof FesStepVO == false) return false;
		if(this == obj) return true;
		FesStepVO other = (FesStepVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

