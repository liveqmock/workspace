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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class SysProductOperationVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "产品运营";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_TITLE = "营销标题";
	public static final String ALIAS_PRODUCT_TYPE = "所属产品";
	public static final String ALIAS_PROMOTION_PLATFORM = "推广平台";
	public static final String ALIAS_CONTENT = "运营内容";
	public static final String ALIAS_AMOUNT = "需求量";
	public static final String ALIAS_BEGIN_TIME = "开始时间";
	public static final String ALIAS_END_TIME = "结束时间";
	public static final String ALIAS_IS_OPEN = "是否开启推广";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_PRODUCT_TYPE = "productType";
	public static final String COLUMN_PROMOTION_PLATFORM = "promotionPlatform";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_BEGIN_TIME = "beginTime";
	public static final String COLUMN_END_TIME = "endTime";
	public static final String COLUMN_IS_OPEN = "isOpen";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.String title; //"营销标题";
	private String[]  productType; //"所属产品";
	private String[]  promotionPlatform; //"推广平台";
	private java.lang.String content; //"运营内容";
	private java.lang.Integer amount; //"需求量";
	private java.util.Date beginTime; //"开始时间";
	private java.util.Date endTime; //"结束时间";
	private java.lang.String isOpen; //"是否开启推广";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END
	private java.lang.Integer pageNumber;
	private java.lang.Integer pageSize;
	private String inputPromotionPlatform;//参数 推广平台
	private String filterByCurrentDate = null; //是否选择按当前日期过滤
	private Integer userId = null;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	private List<SysDictionaryVO> dicProductTypes;
	private List<SysDictionaryVO> dicPlatforms;

	public List<SysDictionaryVO> getDicProductTypes() {
		return dicProductTypes;
	}

	public void setDicProductTypes(List<SysDictionaryVO> dicProductTypes) {
		this.dicProductTypes = dicProductTypes;
	}

	public List<SysDictionaryVO> getDicPlatforms() {
		return dicPlatforms;
	}

	public void setDicPlatforms(List<SysDictionaryVO> dicPlatforms) {
		this.dicPlatforms = dicPlatforms;
	}

	public String getFilterByCurrentDate() {
		return filterByCurrentDate;
	}

	public void setFilterByCurrentDate(String filterByCurrentDate) {
		this.filterByCurrentDate = filterByCurrentDate;
	}

	public String getInputPromotionPlatform() {
		return inputPromotionPlatform;
	}

	public void setInputPromotionPlatform(String inputPromotionPlatform) {
		this.inputPromotionPlatform = inputPromotionPlatform;
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

	public SysProductOperationVO(){
	}

	public SysProductOperationVO(
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
	public void setAmount(java.lang.Integer value) {
		this.amount = value;
	}
	
	public java.lang.Integer getAmount() {
		return this.amount;
	}
	public void setBeginTime(java.util.Date value) {
		this.beginTime = value;
	}
	
	public java.util.Date getBeginTime() {
		return this.beginTime;
	}
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setIsOpen(java.lang.String value) {
		this.isOpen = value;
	}
	
	public java.lang.String getIsOpen() {
		return this.isOpen;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Title",getTitle())
			.append("ProductType",getProductType())
			.append("PromotionPlatform",getPromotionPlatform())
			.append("Content",getContent())
			.append("Amount",getAmount())
			.append("BeginTime",getBeginTime())
			.append("EndTime",getEndTime())
			.append("IsOpen",getIsOpen())
			.append("IsEnable",getIsEnable())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public String[] getProductType() {
		return productType;
	}

	public void setProductType(String[] productType) {
		this.productType = productType;
	}

	public String[] getPromotionPlatform() {
		return promotionPlatform;
	}

	public void setPromotionPlatform(String[] promotionPlatform) {
		this.promotionPlatform = promotionPlatform;
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SysProductOperationVO == false) return false;
		if(this == obj) return true;
		SysProductOperationVO other = (SysProductOperationVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

