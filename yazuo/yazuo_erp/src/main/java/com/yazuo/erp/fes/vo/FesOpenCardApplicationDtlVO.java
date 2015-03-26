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

package com.yazuo.erp.fes.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;
import com.yazuo.erp.system.vo.SysAttachmentVO;

/**
 * @Description TODO
 * @author erp team
 * @date
 */
public class FesOpenCardApplicationDtlVO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "开卡申请单明细";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_APPLICATION_ID = "开卡申请单ID";
	public static final String ALIAS_CARD_NAME = "卡类型名称";
	public static final String ALIAS_CARD_AMOUNT = "开卡数量";
	public static final String ALIAS_VALIDITY_TERM = "有效期";
	public static final String ALIAS_IS_CONTAIN_FOUR = "是否包含4";
	public static final String ALIAS_IS_CONTAIN_SEVEN = "是否包含7";
	public static final String ALIAS_MEMBER_LEVEL = "会员等级";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_ATTACHMENT_ID = "附件ID";
	public static final String ALIAS_CARD_TAG = "cardTag";
	public static final String ALIAS_IS_CONTAIN_SECURITY_CODE = "isContainSecurityCode";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_APPLICATION_ID = "applicationId";
	public static final String COLUMN_CARD_NAME = "cardName";
	public static final String COLUMN_CARD_AMOUNT = "cardAmount";
	public static final String COLUMN_VALIDITY_TERM = "validityTerm";
	public static final String COLUMN_IS_CONTAIN_FOUR = "isContainFour";
	public static final String COLUMN_IS_CONTAIN_SEVEN = "isContainSeven";
	public static final String COLUMN_REMARK = "emark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_MEMBER_LEVEL = "memberLevel";
	public static final String COLUMN_CARD_TAG = "cardTag";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_IS_CONTAIN_SECURITY_CODE = "isContainSecurityCode";

	// columns START
	private java.lang.Integer id; // "ID";
	private java.lang.Integer applicationId; // "开卡申请单ID";
	private java.lang.String cardName; // "卡类型名称";
	private java.math.BigDecimal cardAmount; // "开卡数量";
	private java.lang.Integer validityTerm; // "有效期";
	private java.lang.String isContainFour; // "是否包含4";
	private java.lang.String isContainSeven; // "是否包含7";
	private java.lang.String remark; // "备注";
	private java.lang.Integer insertBy; // "创建人";
	private java.util.Date insertTime; // "创建时间";
	private java.lang.String memberLevel; //"memberLevel";
	private java.lang.String cardTag; //"卡标签";
	private java.lang.String isContainSecurityCode; //"是否包含安全码 1:是, 0:否";


	// 临时借用
	private java.lang.Integer processId; // "流程ID";
	private java.lang.Integer merchantId; // "商户ID";
	private java.lang.Integer attachmentId; //"附件ID";
	private SysAttachmentVO sysAttachment;//附件对象

	public SysAttachmentVO getSysAttachment() {
		return sysAttachment;
	}

	public void setSysAttachment(SysAttachmentVO sysAttachment) {
		this.sysAttachment = sysAttachment;
	}

	public java.lang.Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(java.lang.Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	private FesOpenCardApplicationVO fesOpenCardApplication;

	// columns END

	public java.lang.String getCardTag() {
		return cardTag;
	}

	public void setCardTag(java.lang.String cardTag) {
		this.cardTag = cardTag;
	}
	public void setFesOpenCardApplication(FesOpenCardApplicationVO fesOpenCardApplication) {
		this.fesOpenCardApplication = fesOpenCardApplication;
	}

	public java.lang.String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(java.lang.String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public FesOpenCardApplicationVO getFesOpenCardApplication() {
		return fesOpenCardApplication;
	}
	public java.lang.Integer getProcessId() {
		return processId;
	}

	public void setProcessId(java.lang.Integer processId) {
		this.processId = processId;
	}

	public java.lang.Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(java.lang.Integer merchantId) {
		this.merchantId = merchantId;
	}


	public FesOpenCardApplicationDtlVO() {
	}

	public FesOpenCardApplicationDtlVO(java.lang.Integer id) {
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setApplicationId(java.lang.Integer value) {
		this.applicationId = value;
	}

	public java.lang.Integer getApplicationId() {
		return this.applicationId;
	}

	public void setCardName(java.lang.String value) {
		this.cardName = value;
	}

	public java.lang.String getCardName() {
		return this.cardName;
	}

	public void setCardAmount(java.math.BigDecimal value) {
		this.cardAmount = value;
	}

	public java.math.BigDecimal getCardAmount() {
		return this.cardAmount;
	}

	public void setValidityTerm(java.lang.Integer value) {
		this.validityTerm = value;
	}

	public java.lang.Integer getValidityTerm() {
		return this.validityTerm;
	}

	public void setIsContainFour(java.lang.String value) {
		this.isContainFour = value;
	}

	public java.lang.String getIsContainFour() {
		return this.isContainFour;
	}

	public void setIsContainSeven(java.lang.String value) {
		this.isContainSeven = value;
	}

	public java.lang.String getIsContainSeven() {
		return this.isContainSeven;
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

	public void setIsContainSecurityCode(java.lang.String value) {
		this.isContainSecurityCode = value;
	}
	
	public java.lang.String getIsContainSecurityCode() {
		return this.isContainSecurityCode;
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
			.append("ApplicationId",getApplicationId())
			.append("CardName",getCardName())
			.append("CardAmount",getCardAmount())
			.append("ValidityTerm",getValidityTerm())
			.append("IsContainFour",getIsContainFour())
			.append("IsContainSeven",getIsContainSeven())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("MemberLevel",getMemberLevel())
			.append("CardTag",getCardTag())
			.append("AttachmentId",getAttachmentId())
			.append("IsContainSecurityCode",getIsContainSecurityCode())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof FesOpenCardApplicationDtlVO == false)
			return false;
		if (this == obj)
			return true;
		FesOpenCardApplicationDtlVO other = (FesOpenCardApplicationDtlVO) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
