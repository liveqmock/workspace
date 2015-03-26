/**
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
 * @author erp team
 * @date 
 */
public class SysGroupVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SysGroup";
	public static final String ALIAS_ID = "组ID";
	public static final String ALIAS_GROUP_NAME = "名称";
	public static final String ALIAS_PARENT_ID = "上级组ID";
	public static final String ALIAS_TREE_CODE = "树状编码";
	public static final String ALIAS_PARENT_TREE_CODE = "上级编码";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.String groupName;
	private java.lang.Integer parentId;
	private java.lang.String treeCode;
	private java.lang.String parentTreeCode;
	private java.lang.String isEnable;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private java.util.Date insertTime;
	private java.lang.Integer updateBy;
	private java.util.Date updateTime;
	private java.lang.Integer isLeaf;//新添加的字段， 为1 or 0
	//columns END

	private java.util.Date insertTimeBegin;
	private java.util.Date insertTimeEnd;
	private java.util.Date updateTimeBegin;
	private java.util.Date updateTimeEnd;
	private String sortColumns;
	private Integer groupId;
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getSortColumns() {
		return sortColumns;
	}
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}


	public java.util.Date getInsertTimeBegin() {
		return insertTimeBegin;
	}


	public void setInsertTimeBegin(java.util.Date insertTimeBegin) {
		this.insertTimeBegin = insertTimeBegin;
	}


	public java.util.Date getInsertTimeEnd() {
		return insertTimeEnd;
	}


	public void setInsertTimeEnd(java.util.Date insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}


	public java.util.Date getUpdateTimeBegin() {
		return updateTimeBegin;
	}


	public void setUpdateTimeBegin(java.util.Date updateTimeBegin) {
		this.updateTimeBegin = updateTimeBegin;
	}


	public java.util.Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}


	public void setUpdateTimeEnd(java.util.Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}


	public SysGroupVO(){
	}


	public java.lang.Integer getIsLeaf() {
		return isLeaf;
	}


	public void setIsLeaf(java.lang.Integer isLeaf) {
		this.isLeaf = isLeaf;
	}


	public SysGroupVO(
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
	public void setGroupName(java.lang.String value) {
		this.groupName = value;
	}
	
	public java.lang.String getGroupName() {
		return this.groupName;
	}
	public void setParentId(java.lang.Integer value) {
		this.parentId = value;
	}
	
	public java.lang.Integer getParentId() {
		return this.parentId;
	}
	public void setTreeCode(java.lang.String value) {
		this.treeCode = value;
	}
	
	public java.lang.String getTreeCode() {
		return this.treeCode;
	}
	public void setParentTreeCode(java.lang.String value) {
		this.parentTreeCode = value;
	}
	
	public java.lang.String getParentTreeCode() {
		return this.parentTreeCode;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("GroupName",getGroupName())
			.append("ParentId",getParentId())
			.append("TreeCode",getTreeCode())
			.append("ParentTreeCode",getParentTreeCode())
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
		if(obj instanceof SysGroupVO == false) return false;
		if(this == obj) return true;
		SysGroupVO other = (SysGroupVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

