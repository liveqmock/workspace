package com.yazuo.erp.trade.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;

/**
 * @Description 会员卡
 * @author erp team
 * @date 
 */
public class TradeCardtypeVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TradeCardtype";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_MERCHANT_ID = "merchantId";
	public static final String ALIAS_CARDTYPE = "cardtype";
	public static final String ALIAS_PRICE = "该卡售卡价格,默认为零";
	public static final String ALIAS_GRADE_LEVEL = "会员级别";
	public static final String ALIAS_IS_SYNCHRONIZE = "是否同步，true同步，false没同步";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_CARDTYPE = "cardtype";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_GRADE_LEVEL = "gradeLevel";
	public static final String COLUMN_IS_SYNCHRONIZE = "isSynchronize";

	//columns START
	private java.lang.Integer id; //"id";
	private java.lang.Integer merchantId; //"merchantId";
	private java.lang.String cardtype; //"cardtype";
	private java.math.BigDecimal price; //"该卡售卡价格,默认为零";
	private Integer gradeLevel; //"会员级别";
	private java.lang.Boolean isSynchronize; //"是否同步，true同步，false没同步";
    //columns END
    private int rebateType;
    private List<TradeAwardRuleVO> awardRuleVos;

    public TradeCardtypeVO(){
	}

	public TradeCardtypeVO(
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
	public void setCardtype(java.lang.String value) {
		this.cardtype = value;
	}
	
	public java.lang.String getCardtype() {
		return this.cardtype;
	}
	public void setPrice(java.math.BigDecimal value) {
		this.price = value;
	}
	
	public java.math.BigDecimal getPrice() {
		return this.price;
	}
	public void setGradeLevel(Integer value) {
		this.gradeLevel = value;
	}
	
	public Integer getGradeLevel() {
		return this.gradeLevel;
	}
	public void setIsSynchronize(java.lang.Boolean value) {
		this.isSynchronize = value;
	}
	
	public java.lang.Boolean getIsSynchronize() {
		return this.isSynchronize;
	}

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

    public int getRebateType() {
        return rebateType;
    }

    public void setRebateType(int rebateType) {
        this.rebateType = rebateType;
    }

    public List<TradeAwardRuleVO> getAwardRuleVos() {
        return awardRuleVos;
    }

    public void setAwardRuleVos(List<TradeAwardRuleVO> awardRuleVos) {
        this.awardRuleVos = awardRuleVos;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("MerchantId",getMerchantId())
			.append("Cardtype",getCardtype())
			.append("Price",getPrice())
			.append("GradeLevel",getGradeLevel())
			.append("IsSynchronize",getIsSynchronize())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TradeCardtypeVO == false) return false;
		if(this == obj) return true;
		TradeCardtypeVO other = (TradeCardtypeVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

}

