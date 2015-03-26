package com.yazuo.erp.trade.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.trade.vo.*;
import com.yazuo.erp.trade.dao.*;

/**
 * @Description 短信模板
 * @author erp team
 * @date 
 */
public class TradeMessageTemplateVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TradeMessageTemplate";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_MERCHANT_NO = "merchantNo";
	public static final String ALIAS_TRANS_CODE = "transCode";
	public static final String ALIAS_MESSAGE_TEMPLATE = "messageTemplate";
	public static final String ALIAS_TEMPLATE_FEILDS = "templateFeilds";
	public static final String ALIAS_PARENT = "parent";
	public static final String ALIAS_SOURCE = "source";
	public static final String ALIAS_IS_COMBINED = "是否为组合交易短信";
	public static final String ALIAS_IS_AVAIL = "isAvail";
	public static final String ALIAS_IS_SYNCHRONIZE = "是否同步到运营平台";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_NO = "merchantNo";
	public static final String COLUMN_TRANS_CODE = "transCode";
	public static final String COLUMN_MESSAGE_TEMPLATE = "messageTemplate";
	public static final String COLUMN_TEMPLATE_FEILDS = "templateFeilds";
	public static final String COLUMN_PARENT = "parent";
	public static final String COLUMN_SOURCE = "source";
	public static final String COLUMN_IS_COMBINED = "isCombined";
	public static final String COLUMN_MESSAGE_TYPE = "messageType";
	public static final String COLUMN_IS_AVAIL = "isAvail";
	public static final String COLUMN_IS_SYNCHRONIZE = "isSynchronize";

	//columns START
	private java.lang.Long id; //"id";
	private java.lang.String merchantNo; //"merchantNo";
	private java.lang.String transCode; //"transCode";
	private java.lang.String messageTemplate; //"messageTemplate";
	private java.lang.String templateFeilds; //"templateFeilds";
	private java.lang.Long parent; //"parent";
	private Integer source; //"source";
	private java.lang.Boolean isCombined; //"是否为组合交易短信";
	private java.lang.Boolean isAvail; //"isAvail";
	private java.lang.Boolean isSynchronize; //"是否同步到运营平台";
    //columns END
    private String templateTxt; // 生成的文本
    private String transName;
    private Integer merchantId;
    private TradeMessageTemplateVO parentMessageTemplateVO;

    public TradeMessageTemplateVO(){
	}

	public TradeMessageTemplateVO(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setMerchantNo(java.lang.String value) {
		this.merchantNo = value;
	}
	
	public java.lang.String getMerchantNo() {
		return this.merchantNo;
	}
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	public void setMessageTemplate(java.lang.String value) {
		this.messageTemplate = value;
	}
	
	public java.lang.String getMessageTemplate() {
		return this.messageTemplate;
	}
	public void setTemplateFeilds(java.lang.String value) {
		this.templateFeilds = value;
	}
	
	public java.lang.String getTemplateFeilds() {
		return this.templateFeilds;
	}
	public void setParent(java.lang.Long value) {
		this.parent = value;
	}
	
	public java.lang.Long getParent() {
		return this.parent;
	}
	public void setSource(Integer value) {
		this.source = value;
	}
	
	public Integer getSource() {
		return this.source;
	}
	public void setIsCombined(java.lang.Boolean value) {
		this.isCombined = value;
	}
	
	public java.lang.Boolean getIsCombined() {
		return this.isCombined;
	}

	public void setIsAvail(java.lang.Boolean value) {
		this.isAvail = value;
	}
	
	public java.lang.Boolean getIsAvail() {
		return this.isAvail;
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

    public String getTemplateTxt() {
        return templateTxt;
    }

    public void setTemplateTxt(String templateTxt) {
        this.templateTxt = templateTxt;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public TradeMessageTemplateVO getParentMessageTemplateVO() {
        return parentMessageTemplateVO;
    }

    public void setParentMessageTemplateVO(TradeMessageTemplateVO parentMessageTemplateVO) {
        this.parentMessageTemplateVO = parentMessageTemplateVO;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id", getId())
			.append("MerchantNo",getMerchantNo())
			.append("TransCode",getTransCode())
			.append("MessageTemplate",getMessageTemplate())
			.append("TemplateFeilds",getTemplateFeilds())
			.append("Parent",getParent())
			.append("Source",getSource())
			.append("IsCombined",getIsCombined())
			.append("IsAvail",getIsAvail())
			.append("IsSynchronize",getIsSynchronize())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TradeMessageTemplateVO == false) return false;
		if(this == obj) return true;
		TradeMessageTemplateVO other = (TradeMessageTemplateVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

