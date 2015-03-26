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

package com.yazuo.erp.minierp.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @Description TODO
 * @author erp team
 * @date
 */
public class BrandsVO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "Brands";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_CONTRACT_DATE = "contractDate";
	public static final String ALIAS_CONTRACT_TYPE = "contractType";
	public static final String ALIAS_ONLINE_DATE = "onlineDate";
	public static final String ALIAS_EXPIRATION_DATE = "expirationDate";
	public static final String ALIAS_SALE = "sale";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_CRM_ID = "crmId";
	public static final String ALIAS_BRAND = "brand";
	public static final String ALIAS_SHOP_COUNT = "shopCount";
	public static final String ALIAS_SUM_HEALTH = "sumHealth";
	public static final String ALIAS_CREATED_AT = "createdAt";
	public static final String ALIAS_UPDATED_AT = "updatedAt";
	public static final String ALIAS_LOGO_FILE_NAME = "logoFileName";
	public static final String ALIAS_LOGO_CONTENT_TYPE = "logoContentType";
	public static final String ALIAS_LOGO_FILE_SIZE = "logoFileSize";
	public static final String ALIAS_LOGO_UPDATED_AT = "logoUpdatedAt";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CONTRACT_DATE = "contractDate";
	public static final String COLUMN_CONTRACT_TYPE = "contractType";
	public static final String COLUMN_ONLINE_DATE = "onlineDate";
	public static final String COLUMN_EXPIRATION_DATE = "expirationDate";
	public static final String COLUMN_SALE = "sale";
	public static final String COLUMN_USER_ID = "userId";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_CRM_ID = "crmId";
	public static final String COLUMN_BRAND = "brand";
	public static final String COLUMN_SHOP_COUNT = "shopCount";
	public static final String COLUMN_SUM_HEALTH = "sumHealth";
	public static final String COLUMN_CREATED_AT = "createdAt";
	public static final String COLUMN_UPDATED_AT = "updatedAt";
	public static final String COLUMN_LOGO_FILE_NAME = "logoFileName";
	public static final String COLUMN_LOGO_CONTENT_TYPE = "logoContentType";
	public static final String COLUMN_LOGO_FILE_SIZE = "logoFileSize";
	public static final String COLUMN_LOGO_UPDATED_AT = "logoUpdatedAt";

	// columns START
	private java.lang.Integer id; // "id";
	private java.lang.String name; // "name";
	private java.util.Date contractDate; // "contractDate";
	private java.lang.String contractType; // "contractType";
	private java.util.Date onlineDate; // "onlineDate";
	private java.util.Date expirationDate; // "expirationDate";
	private java.lang.String sale; // "sale";
	private java.lang.Integer userId; // "userId";
	private java.lang.Integer status; // "status";
	private java.lang.Integer crmId; // "crmId";
	private java.lang.String brand; // "brand";
	private java.lang.Integer shopCount; // "shopCount";
	private java.lang.Integer sumHealth; // "sumHealth";
	private java.util.Date createdAt; // "createdAt";
	private java.util.Date updatedAt; // "updatedAt";
	private java.lang.String logoFileName; // "logoFileName";
	private java.lang.String logoContentType; // "logoContentType";
	private java.lang.Integer logoFileSize; // "logoFileSize";
	private java.util.Date logoUpdatedAt; // "logoUpdatedAt";

	// columns END

	public BrandsVO() {
	}

	public BrandsVO(java.lang.Integer id) {
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

	public void setContractDate(java.util.Date value) {
		this.contractDate = value;
	}

	public java.util.Date getContractDate() {
		return this.contractDate;
	}

	public void setContractType(java.lang.String value) {
		this.contractType = value;
	}

	public java.lang.String getContractType() {
		return this.contractType;
	}

	public void setOnlineDate(java.util.Date value) {
		this.onlineDate = value;
	}

	public java.util.Date getOnlineDate() {
		return this.onlineDate;
	}

	public void setExpirationDate(java.util.Date value) {
		this.expirationDate = value;
	}

	public java.util.Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setSale(java.lang.String value) {
		this.sale = value;
	}

	public java.lang.String getSale() {
		return this.sale;
	}

	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}

	public java.lang.Integer getUserId() {
		return this.userId;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setCrmId(java.lang.Integer value) {
		this.crmId = value;
	}

	public java.lang.Integer getCrmId() {
		return this.crmId;
	}

	public void setBrand(java.lang.String value) {
		this.brand = value;
	}

	public java.lang.String getBrand() {
		return this.brand;
	}

	public void setShopCount(java.lang.Integer value) {
		this.shopCount = value;
	}

	public java.lang.Integer getShopCount() {
		return this.shopCount;
	}

	public void setSumHealth(java.lang.Integer value) {
		this.sumHealth = value;
	}

	public java.lang.Integer getSumHealth() {
		return this.sumHealth;
	}

	public void setCreatedAt(java.util.Date value) {
		this.createdAt = value;
	}

	public java.util.Date getCreatedAt() {
		return this.createdAt;
	}

	public void setUpdatedAt(java.util.Date value) {
		this.updatedAt = value;
	}

	public java.util.Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setLogoFileName(java.lang.String value) {
		this.logoFileName = value;
	}

	public java.lang.String getLogoFileName() {
		return this.logoFileName;
	}

	public void setLogoContentType(java.lang.String value) {
		this.logoContentType = value;
	}

	public java.lang.String getLogoContentType() {
		return this.logoContentType;
	}

	public void setLogoFileSize(java.lang.Integer value) {
		this.logoFileSize = value;
	}

	public java.lang.Integer getLogoFileSize() {
		return this.logoFileSize;
	}

	public void setLogoUpdatedAt(java.util.Date value) {
		this.logoUpdatedAt = value;
	}

	public java.util.Date getLogoUpdatedAt() {
		return this.logoUpdatedAt;
	}

	private String sortColumns;

	public String getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId()).append("Name", getName())
				.append("ContractDate", getContractDate()).append("ContractType", getContractType())
				.append("OnlineDate", getOnlineDate()).append("ExpirationDate", getExpirationDate()).append("Sale", getSale())
				.append("UserId", getUserId()).append("Status", getStatus()).append("CrmId", getCrmId())
				.append("Brand", getBrand()).append("ShopCount", getShopCount()).append("SumHealth", getSumHealth())
				.append("CreatedAt", getCreatedAt()).append("UpdatedAt", getUpdatedAt())
				.append("LogoFileName", getLogoFileName()).append("LogoContentType", getLogoContentType())
				.append("LogoFileSize", getLogoFileSize()).append("LogoUpdatedAt", getLogoUpdatedAt()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof BrandsVO == false)
			return false;
		if (this == obj)
			return true;
		BrandsVO other = (BrandsVO) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
