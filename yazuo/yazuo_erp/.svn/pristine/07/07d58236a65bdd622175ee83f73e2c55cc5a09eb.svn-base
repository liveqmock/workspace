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

/**
 * @author erp team
 * @date 
 */
public class SysToDoListVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "待办事项";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_PRIORITY_LEVEL_TYPE = "优先级";
	public static final String ALIAS_ITEM_TYPE = "待办事项类型";
	public static final String ALIAS_ITEM_CONTENT = "待办事项内容";
	public static final String ALIAS_ITEM_STATUS = "待办事项状态";
	public static final String ALIAS_RELATED_TYPE = "相关类型";
	public static final String ALIAS_RELATED_ID = "相关ID";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_BUSINESS_TYPE = "业务场景";
	public static final String ALIAS_STORE_ID = "门店ID";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_USER_ID = "userId";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_PRIORITY_LEVEL_TYPE = "priorityLevelType";
	public static final String COLUMN_ITEM_TYPE = "itemType";
	public static final String COLUMN_ITEM_CONTENT = "itemContent";
	public static final String COLUMN_ITEM_STATUS = "itemStatus";
	public static final String COLUMN_RELATED_TYPE = "relatedType";
	public static final String COLUMN_RELATED_ID = "relatedId";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_BUSINESS_TYPE = "businessType";
	public static final String COLUMN_STORE_ID = "storeId";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer userId; //"用户ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String priorityLevelType; //"优先级";
	private java.lang.String itemType; //"待办事项类型";
	private java.lang.String itemContent; //"待办事项内容";
	private java.lang.String itemStatus; //"待办事项状态";
	private java.lang.String relatedType; //"相关类型";
	private java.lang.Integer relatedId; //"相关ID";
	private java.lang.String remark; //"备注";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.String businessType; //"业务类型";

	private java.lang.Integer storeId; //"门店ID";
	private String resourceRemark = null;//资源的英文描述
	private String itemTypeText;//页面列表显示类型
	private String[] inputItemTypes = null;//页面搜索条件对应的显示类型 有多个
	private String[] businessTypes = null;//业务场景有多个
	private String merchantName;
	private java.lang.Integer pageNumber;
	private java.lang.Integer pageSize;
	//columns END
	public String[] getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(String[] businessTypes) {
		this.businessTypes = businessTypes;
	}

	
	public java.lang.String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(java.lang.String businessType) {
		this.businessType = businessType;
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


	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String[] getInputItemTypes() {
		return inputItemTypes;
	}

	public void setInputItemTypes(String[] inputItemTypes) {
		this.inputItemTypes = inputItemTypes;
	}

	public String getItemTypeText() {
		return itemTypeText;
	}

	public void setItemTypeText(String itemTypeText) {
		this.itemTypeText = itemTypeText;
	}

	public String getResourceRemark() {
		return resourceRemark;
	}

	public void setResourceRemark(String resourceRemark) {
		this.resourceRemark = resourceRemark;
	}

	public SysToDoListVO(){
	}

	public SysToDoListVO(
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
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setMerchantId(java.lang.Integer value) {
		this.merchantId = value;
	}
	
	public java.lang.Integer getMerchantId() {
		return this.merchantId;
	}
	public void setPriorityLevelType(java.lang.String value) {
		this.priorityLevelType = value;
	}
	
	public java.lang.String getPriorityLevelType() {
		return this.priorityLevelType;
	}
	public void setItemType(java.lang.String value) {
		this.itemType = value;
	}
	
	public java.lang.String getItemType() {
		return this.itemType;
	}
	public void setItemContent(java.lang.String value) {
		this.itemContent = value;
	}
	
	public java.lang.String getItemContent() {
		return this.itemContent;
	}
	public void setItemStatus(java.lang.String value) {
		this.itemStatus = value;
	}
	
	public java.lang.String getItemStatus() {
		return this.itemStatus;
	}
	public void setRelatedType(java.lang.String value) {
		this.relatedType = value;
	}
	
	public java.lang.String getRelatedType() {
		return this.relatedType;
	}
	public void setRelatedId(java.lang.Integer value) {
		this.relatedId = value;
	}
	
	public java.lang.Integer getRelatedId() {
		return this.relatedId;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setIsEnable(java.lang.String value) {
		this.isEnable = value;
	}
	
	public java.lang.String getIsEnable() {
		return this.isEnable;
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

	public void setStoreId(java.lang.Integer value) {
		this.storeId = value;
	}
	
	public java.lang.Integer getStoreId() {
		return this.storeId;
	}
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("MerchantId",getMerchantId())
			.append("PriorityLevelType",getPriorityLevelType())
			.append("ItemType",getItemType())
			.append("ItemContent",getItemContent())
			.append("ItemStatus",getItemStatus())
			.append("RelatedType",getRelatedType())
			.append("RelatedId",getRelatedId())
			.append("Remark",getRemark())
			.append("IsEnable",getIsEnable())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("BusinessType",getBusinessType())
			.append("UpdateTime",getUpdateTime())
			.append("StoreId",getStoreId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SysToDoListVO == false) return false;
		if(this == obj) return true;
		SysToDoListVO other = (SysToDoListVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

