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
public class SynMembershipCardVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "会员卡信息";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_MERCHANT_NAME = "商户名称";
	public static final String ALIAS_BRAND_ID = "品牌ID";
	public static final String ALIAS_MERCHANT_TYPE = "商户类型";
	public static final String ALIAS_CARDTYPE_ID = "卡类型ID";
	public static final String ALIAS_CARD_COUNT = "卡余量";
	public static final String ALIAS_CARD_PRICE = "卡单价";
	public static final String ALIAS_IS_SOLD_CARD = "是否是实体卡";
	public static final String ALIAS_CARD_NAME = "卡名称";
	public static final String ALIAS_CARD_TYPE = "卡类型";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_MERCHANT_NAME = "merchantName";
	public static final String COLUMN_BRAND_ID = "brandId";
	public static final String COLUMN_MERCHANT_TYPE = "merchantType";
	public static final String COLUMN_CARDTYPE_ID = "cardtypeId";
	public static final String COLUMN_CARD_COUNT = "cardCount";
	public static final String COLUMN_CARD_PRICE = "cardPrice";
	public static final String COLUMN_IS_SOLD_CARD = "isSoldCard";
	public static final String COLUMN_CARD_NAME = "cardName";
	public static final String COLUMN_CARD_TYPE = "cardType";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String merchantName; //"商户名称";
	private java.lang.Integer brandId; //"品牌ID";
	private java.lang.String merchantType; //"商户类型";
	private java.lang.Integer cardtypeId; //"卡类型ID";
	private Long cardCount; //"卡余量";
	private Long cardPrice; //"卡单价";
	private java.lang.Boolean isSoldCard; //"是否是实体卡";
	private java.lang.String cardName; //"卡名称";
	private java.lang.String cardType; //"卡类型";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END

	public SynMembershipCardVO(){
	}

	public SynMembershipCardVO(
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
	public void setMerchantName(java.lang.String value) {
		this.merchantName = value;
	}
	
	public java.lang.String getMerchantName() {
		return this.merchantName;
	}
	public void setBrandId(java.lang.Integer value) {
		this.brandId = value;
	}
	
	public java.lang.Integer getBrandId() {
		return this.brandId;
	}
	public void setMerchantType(java.lang.String value) {
		this.merchantType = value;
	}
	
	public java.lang.String getMerchantType() {
		return this.merchantType;
	}
	public void setCardtypeId(java.lang.Integer value) {
		this.cardtypeId = value;
	}
	
	public java.lang.Integer getCardtypeId() {
		return this.cardtypeId;
	}
	public void setCardCount(Long value) {
		this.cardCount = value;
	}
	
	public Long getCardCount() {
		return this.cardCount;
	}
	public void setCardPrice(Long value) {
		this.cardPrice = value;
	}
	
	public Long getCardPrice() {
		return this.cardPrice;
	}
	public void setIsSoldCard(java.lang.Boolean value) {
		this.isSoldCard = value;
	}
	
	public java.lang.Boolean getIsSoldCard() {
		return this.isSoldCard;
	}
	public void setCardName(java.lang.String value) {
		this.cardName = value;
	}
	
	public java.lang.String getCardName() {
		return this.cardName;
	}
	public void setCardType(java.lang.String value) {
		this.cardType = value;
	}
	
	public java.lang.String getCardType() {
		return this.cardType;
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
			.append("MerchantName",getMerchantName())
			.append("BrandId",getBrandId())
			.append("MerchantType",getMerchantType())
			.append("CardtypeId",getCardtypeId())
			.append("CardCount",getCardCount())
			.append("CardPrice",getCardPrice())
			.append("IsSoldCard",getIsSoldCard())
			.append("CardName",getCardName())
			.append("CardType",getCardType())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SynMembershipCardVO == false) return false;
		if(this == obj) return true;
		SynMembershipCardVO other = (SynMembershipCardVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

