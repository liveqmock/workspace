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

package com.yazuo.erp.syn.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class SynMerchantProductVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "商户产品";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_PRODUCT_ID = "产品ID";
	public static final String ALIAS_PRODUCT_NAME = "产品名称";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_PRODUCT_ID = "productId";
	public static final String COLUMN_PRODUCT_NAME = "productName";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.Integer productId; //"产品ID";
	private java.lang.String productName; //"产品名称";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END

	public SynMerchantProductVO(){
	}

	public SynMerchantProductVO(
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
	public void setProductId(java.lang.Integer value) {
		this.productId = value;
	}
	
	public java.lang.Integer getProductId() {
		return this.productId;
	}
	public void setProductName(java.lang.String value) {
		this.productName = value;
	}
	
	public java.lang.String getProductName() {
		return this.productName;
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
			.append("MerchantId",getMerchantId())
			.append("ProductId",getProductId())
			.append("ProductName",getProductName())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SynMerchantProductVO == false) return false;
		if(this == obj) return true;
		SynMerchantProductVO other = (SynMerchantProductVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

