package com.yazuo.erp.trade.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.*;
import com.yazuo.erp.trade.vo.*;
import com.yazuo.erp.trade.dao.*;

/**
 * @Description  奖励规则
 * @author erp team
 * @date 
 */
public class TradeAwardRuleVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TradeAwardRule";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_LOWER_LIMIT = "lowerLimit";
	public static final String ALIAS_UPPER_LIMIT = "upperLimit";
	public static final String ALIAS_AWARD_AMOUNT = "awardAmount";
	public static final String ALIAS_TRANS_CODE = "transCode";
	public static final String ALIAS_CARDTYPE_ID = "cardtypeId";
	public static final String ALIAS_AWARD_TYPE = "awardType";
	public static final String ALIAS_AWARD_AMOUNT_TYPE = "awardAmountType";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_LOWER_LIMIT = "lowerLimit";
	public static final String COLUMN_UPPER_LIMIT = "upperLimit";
	public static final String COLUMN_AWARD_AMOUNT = "awardAmount";
	public static final String COLUMN_TRANS_CODE = "transCode";
	public static final String COLUMN_CARDTYPE_ID = "cardtypeId";
	public static final String COLUMN_AWARD_TYPE = "awardType";
	public static final String COLUMN_AWARD_AMOUNT_TYPE = "awardAmountType";

	//columns START
	private java.lang.Integer id; //"id";
	private java.math.BigDecimal lowerLimit; //"lowerLimit";
	private java.math.BigDecimal upperLimit; //"upperLimit";
	private java.math.BigDecimal awardAmount; //"awardAmount";
	private java.lang.String transCode; //"transCode";
	private java.lang.Integer cardtypeId; //"cardtypeId";
	private Integer awardType; //"awardType";
	private Integer awardAmountType; //"awardAmountType";
	//columns END

    private int rebateType;

	public TradeAwardRuleVO(){
	}

	public TradeAwardRuleVO(
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
	public void setLowerLimit(java.math.BigDecimal value) {
		this.lowerLimit = value;
	}
	
	public java.math.BigDecimal getLowerLimit() {
		return this.lowerLimit;
	}
	public void setUpperLimit(java.math.BigDecimal value) {
		this.upperLimit = value;
	}
	
	public java.math.BigDecimal getUpperLimit() {
		return this.upperLimit;
	}
	public void setAwardAmount(java.math.BigDecimal value) {
		this.awardAmount = value;
	}
	
	public java.math.BigDecimal getAwardAmount() {
		return this.awardAmount;
	}
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	public void setCardtypeId(java.lang.Integer value) {
		this.cardtypeId = value;
	}
	
	public java.lang.Integer getCardtypeId() {
		return this.cardtypeId;
	}
	public void setAwardType(Integer value) {
        this.awardType = value;
        if (this.awardType == 1) {
            this.lowerLimit = new BigDecimal(0);
            this.upperLimit = new BigDecimal(10000000);
        }
    }

    public Integer getAwardType() {
		return this.awardType;
	}
	public void setAwardAmountType(Integer value) {
		this.awardAmountType = value;
	}
	
	public Integer getAwardAmountType() {
		return this.awardAmountType;
	}

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

    public int getRebateType() {
        if (this.lowerLimit.equals(0) && this.upperLimit.equals(10000000)) {
            rebateType = 1;
        }else {
            rebateType = 2;
        }
        return rebateType;
    }

    public void setRebateType(int rebateType) {
        this.rebateType = rebateType;
        if (rebateType == 1) {
            this.lowerLimit = new BigDecimal(0);
            this.upperLimit = new BigDecimal(10000000);
        }
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("LowerLimit",getLowerLimit())
			.append("UpperLimit",getUpperLimit())
			.append("AwardAmount",getAwardAmount())
			.append("TransCode",getTransCode())
			.append("CardtypeId",getCardtypeId())
			.append("AwardType",getAwardType())
			.append("AwardAmountType",getAwardAmountType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TradeAwardRuleVO == false) return false;
		if(this == obj) return true;
		TradeAwardRuleVO other = (TradeAwardRuleVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

