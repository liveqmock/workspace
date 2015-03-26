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

package com.yazuo.external.account.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @Description TODO
 * @author erp team
 * @date
 */
public class MerchantVO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "商户";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_MERCHANT_NAME = "商户名称";
	public static final String ALIAS_BRAND = "品牌名称";
	public static final String ALIAS_MERCHANT_NO = "商户编号";
	public static final String ALIAS_IS_FACE_SHOP = "是否实体店";
	public static final String ALIAS_AD_COLUMN = "广告";
	public static final String ALIAS_PROMPT_BAR = "推广";
	public static final String ALIAS_PARENT = "父商户ID";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_BRAND_SHORT_PINYIN = "集团简称";
	public static final String ALIAS_BRAND_ID = "集团id";
	public static final String ALIAS_THIRDPARTY_MERCHANT_NO = "第三方商户号";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_MERCHANT_NAME = "merchantName";
	public static final String COLUMN_BRAND = "brand";
	public static final String COLUMN_MERCHANT_NO = "merchantNo";
	public static final String COLUMN_IS_FACE_SHOP = "isFaceShop";
	public static final String COLUMN_AD_COLUMN = "adColumn";
	public static final String COLUMN_PROMPT_BAR = "promptBar";
	public static final String COLUMN_PARENT = "parent";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_BRAND_SHORT_PINYIN = "brandShortPinyin";
	public static final String COLUMN_BRAND_ID = "brandId";
	public static final String COLUMN_THIRDPARTY_MERCHANT_NO = "thirdpartyMerchantNo";

	// columns START
	private java.lang.Integer merchantId; // "商户ID";
	private java.lang.String merchantName; // "商户名称";
	private java.lang.String brand; // "品牌名称";
	private java.lang.String merchantNo; // "商户编号";
	private java.lang.Boolean isFaceShop; // "是否实体店";
	private java.lang.String adColumn; // "广告";
	private java.lang.String promptBar; // "推广";
	private java.lang.Integer parent; // "父商户ID";
	private Integer status; // "状态";
	private java.lang.String brandShortPinyin; // "集团简称";
	private java.lang.Integer brandId; // "集团id";
	private java.lang.String thirdpartyMerchantNo; // "第三方商户号";

	// columns END

	public MerchantVO() {
	}

	public MerchantVO(java.lang.Integer merchantId) {
		this.merchantId = merchantId;
	}

	public void setMerchantId(java.lang.Integer value) {
		this.merchantId = value;
	}

	public java.lang.Integer getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantName(java.lang.String value) {
		this.merchantName = value;
	}

	public java.lang.String getMerchantName() {
		return this.merchantName;
	}

	public void setBrand(java.lang.String value) {
		this.brand = value;
	}

	public java.lang.String getBrand() {
		return this.brand;
	}

	public void setMerchantNo(java.lang.String value) {
		this.merchantNo = value;
	}

	public java.lang.String getMerchantNo() {
		return this.merchantNo;
	}

	public void setIsFaceShop(java.lang.Boolean value) {
		this.isFaceShop = value;
	}

	public java.lang.Boolean getIsFaceShop() {
		return this.isFaceShop;
	}

	public void setAdColumn(java.lang.String value) {
		this.adColumn = value;
	}

	public java.lang.String getAdColumn() {
		return this.adColumn;
	}

	public void setPromptBar(java.lang.String value) {
		this.promptBar = value;
	}

	public java.lang.String getPromptBar() {
		return this.promptBar;
	}

	public void setParent(java.lang.Integer value) {
		this.parent = value;
	}

	public java.lang.Integer getParent() {
		return this.parent;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setBrandShortPinyin(java.lang.String value) {
		this.brandShortPinyin = value;
	}

	public java.lang.String getBrandShortPinyin() {
		return this.brandShortPinyin;
	}

	public void setBrandId(java.lang.Integer value) {
		this.brandId = value;
	}

	public java.lang.Integer getBrandId() {
		return this.brandId;
	}

	public void setThirdpartyMerchantNo(java.lang.String value) {
		this.thirdpartyMerchantNo = value;
	}

	public java.lang.String getThirdpartyMerchantNo() {
		return this.thirdpartyMerchantNo;
	}

	private String sortColumns;

	public String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("MerchantId", getMerchantId())
				.append("MerchantName", getMerchantName()).append("Brand", getBrand()).append("MerchantNo", getMerchantNo())
				.append("IsFaceShop", getIsFaceShop()).append("AdColumn", getAdColumn()).append("PromptBar", getPromptBar())
				.append("Parent", getParent()).append("Status", getStatus()).append("BrandShortPinyin", getBrandShortPinyin())
				.append("BrandId", getBrandId()).append("ThirdpartyMerchantNo", getThirdpartyMerchantNo()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getMerchantId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof MerchantVO == false)
			return false;
		if (this == obj)
			return true;
		MerchantVO other = (MerchantVO) obj;
		return new EqualsBuilder().append(getMerchantId(), other.getMerchantId()).isEquals();
	}
}
