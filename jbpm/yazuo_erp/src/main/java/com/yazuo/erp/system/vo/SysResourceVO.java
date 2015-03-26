/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.system.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class SysResourceVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SysResource";
	public static final String ALIAS_ID = "主键ID";
	public static final String ALIAS_PARENT_ID = "上级ID";
	public static final String ALIAS_TREE_CODE = "树状编码";
	public static final String ALIAS_PARENT_TREE_CODE = "上级编码";
	public static final String ALIAS_RESOURCE_TYPE = "类型";
	public static final String ALIAS_RESOURCE_NAME = "名称";
	public static final String ALIAS_RESOURCE_URL = "地址";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.Integer parentId;
	private java.lang.String treeCode;
	private java.lang.String parentTreeCode;
	private java.lang.String resourceType;
	private java.lang.String resourceName;
	private java.lang.String resourceUrl;
	private java.lang.Integer sort;
	private java.lang.String remark;
	private java.lang.String isVisible;
	private java.lang.Integer insertBy;
	private java.util.Date insertTime;
	private java.lang.Integer updateBy;
	private java.util.Date updateTime;
	private java.lang.Integer pageNumber;
	private java.lang.Integer pageSize;
	//columns END
	private String isSelected;

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
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

	public SysResourceVO(){
	}

	public SysResourceVO(
		java.lang.Integer id
	){
		this.id = id;
	}

	public java.lang.String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(java.lang.String isVisible) {
		this.isVisible = isVisible;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
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
	public void setResourceType(java.lang.String value) {
		this.resourceType = value;
	}
	
	public java.lang.String getResourceType() {
		return this.resourceType;
	}
	public void setResourceName(java.lang.String value) {
		this.resourceName = value;
	}
	
	public java.lang.String getResourceName() {
		return this.resourceName;
	}
	public void setResourceUrl(java.lang.String value) {
		this.resourceUrl = value;
	}
	
	public java.lang.String getResourceUrl() {
		return this.resourceUrl;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
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
			.append("ParentId",getParentId())
			.append("TreeCode",getTreeCode())
			.append("ParentTreeCode",getParentTreeCode())
			.append("ResourceType",getResourceType())
			.append("ResourceName",getResourceName())
			.append("ResourceUrl",getResourceUrl())
			.append("Sort",getSort())
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
		if(obj instanceof SysResourceVO == false) return false;
		if(this == obj) return true;
		SysResourceVO other = (SysResourceVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

