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
public class MerchantProductVO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "MerchantProduct";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_PRODUCT_ID = "productId";
	public static final String ALIAS_MERCHANT_ID = "merchantId";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PRODUCT_ID = "productId";
	public static final String COLUMN_MERCHANT_ID = "merchantId";

	// columns START
	private java.lang.Long id; // "id";
	private java.lang.Integer productId; // "productId";
	private java.lang.Integer merchantId; // "merchantId";
	private java.lang.String productName; // "merchantId";

	// columns END

	public MerchantProductVO() {
	}

	public MerchantProductVO(java.lang.Long id) {
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setProductId(java.lang.Integer value) {
		this.productId = value;
	}

	public java.lang.Integer getProductId() {
		return this.productId;
	}

	public void setMerchantId(java.lang.Integer value) {
		this.merchantId = value;
	}

	public java.lang.Integer getMerchantId() {
		return this.merchantId;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	private String sortColumns;

	public String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId())
				.append("ProductId", getProductId()).append("MerchantId", getMerchantId()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof MerchantProductVO == false)
			return false;
		if (this == obj)
			return true;
		MerchantProductVO other = (MerchantProductVO) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
