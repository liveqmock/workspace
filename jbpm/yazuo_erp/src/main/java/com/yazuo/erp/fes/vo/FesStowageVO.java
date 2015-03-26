/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author erp team
 * @date 
 */
public class FesStowageVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "配载信息";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_PROCESS_ID = "流程ID";
	public static final String ALIAS_LOGISTICS_COMPANY = "物流公司";
	public static final String ALIAS_LOGISTICS_NUM = "物流单号";
	public static final String ALIAS_CONTACT_ID = "联系人";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_RECIPIENT = "recipient";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PROCESS_ID = "processId";
	public static final String COLUMN_LOGISTICS_COMPANY = "logisticsCompany";
	public static final String COLUMN_LOGISTICS_NUM = "logisticsNum";
	public static final String COLUMN_CONTACT_ID = "contactId";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_RECIPIENT = "recipient";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer processId; //"流程ID";
	private java.lang.String logisticsCompany; //"物流公司";
	private java.lang.String logisticsNum; //"物流单号";
	private java.lang.Integer contactId; //"联系人";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.Integer recipient; //"recipient";
	private List<FesStowageDtlVO> fesStowageDtls = new ArrayList<FesStowageDtlVO>();
	private Integer merchantId;
	private Integer isSendSMSToUser; //是否发送短信给前端负责人 1, 是
	private Integer distributeFlag; //区分是实体卡配送还是设备配送  0：实体卡， 1：设备
	
	public Integer getDistributeFlag() {
		return distributeFlag;
	}

	public void setDistributeFlag(Integer distributeFlag) {
		this.distributeFlag = distributeFlag;
	}

	public Integer getIsSendSMSToUser() {
		return isSendSMSToUser;
	}

	public void setIsSendSMSToUser(Integer isSendSMSToUser) {
		this.isSendSMSToUser = isSendSMSToUser;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	//columns END
	private FesOnlineProcessVO fesOnlineProcess;
	
	
	public void setFesOnlineProcess(FesOnlineProcessVO fesOnlineProcess){
		this.fesOnlineProcess = fesOnlineProcess;
	}
	
	public FesOnlineProcessVO getFesOnlineProcess() {
		return fesOnlineProcess;
	}
	public FesStowageVO(){
	}

	public FesStowageVO(
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
	public void setProcessId(java.lang.Integer value) {
		this.processId = value;
	}
	
	public java.lang.Integer getProcessId() {
		return this.processId;
	}
	public void setLogisticsCompany(java.lang.String value) {
		this.logisticsCompany = value;
	}
	
	public java.lang.String getLogisticsCompany() {
		return this.logisticsCompany;
	}
	public void setLogisticsNum(java.lang.String value) {
		this.logisticsNum = value;
	}
	
	public java.lang.String getLogisticsNum() {
		return this.logisticsNum;
	}
	public void setContactId(java.lang.Integer value) {
		this.contactId = value;
	}
	
	public java.lang.Integer getContactId() {
		return this.contactId;
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
	public void setRecipient(java.lang.Integer value) {
		this.recipient = value;
	}
	
	public java.lang.Integer getRecipient() {
		return this.recipient;
	}
	public List<FesStowageDtlVO> getFesStowageDtls() {
		return fesStowageDtls;
	}

	public void setFesStowageDtls(List<FesStowageDtlVO> fesStowageDtls) {
		this.fesStowageDtls = fesStowageDtls;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProcessId",getProcessId())
			.append("LogisticsCompany",getLogisticsCompany())
			.append("LogisticsNum",getLogisticsNum())
			.append("ContactId",getContactId())
			.append("IsEnable",getIsEnable())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.append("Recipient",getRecipient())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof FesStowageVO == false) return false;
		if(this == obj) return true;
		FesStowageVO other = (FesStowageVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

