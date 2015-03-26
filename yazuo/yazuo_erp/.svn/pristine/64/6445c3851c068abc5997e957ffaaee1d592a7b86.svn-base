/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.vo;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.trade.vo.TradeCardtypeVO;
import com.yazuo.erp.trade.vo.TradeMessageTemplateVO;

/**
 * @author erp team
 * @date 
 */
public class SysOperationLogVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "操作日志";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_MODULE_TYPE = "模块类型";
	public static final String ALIAS_FES_LOG_TYPE = "日志类型";
	public static final String ALIAS_OPERATING_TIME = "操作时间";
	public static final String ALIAS_DESCRIPTION = "操作描述";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_STORE_ID = "门店ID";
	public static final String ALIAS_RELATED_TYPE = "相关类型";
	public static final String ALIAS_RELATED_ID = "相关ID";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_MODULE_TYPE = "moduleType";
	public static final String COLUMN_FES_LOG_TYPE = "fesLogType";
	public static final String COLUMN_OPERATING_TIME = "operatingTime";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_STORE_ID = "storeId";
	public static final String COLUMN_RELATED_TYPE = "relatedType";
	public static final String COLUMN_RELATED_ID = "relatedId";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String moduleType; //"模块类型";
	private java.lang.String fesLogType; //"日志类型";
	private java.util.Date operatingTime; //"操作时间";
	private java.lang.String description; //"操作描述";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer storeId; //"门店ID";
	private java.lang.String relatedType; //"相关类型";
	private java.lang.Integer relatedId; //"相关ID";
	
	//流程总状态
	private String finalStatus;
	//上线流程状态
	private String onlineProcessStatus;
	private Integer stepId;
	private String stepNum;
	private Integer processId;
	//查询条件参数
	private String[] fesLogTypes;
	//可类型和奖励规则
	private List<TradeCardtypeVO> cardtypesAndAwardRules = null; 
	//交易短信模板
	private List<TradeMessageTemplateVO> tradeMessageTemplates = null;
	//操作人
	private String userName = null;
	private Date beginTime; //时间过滤 
	private Date endTime;
	//columns END

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<TradeMessageTemplateVO> getTradeMessageTemplates() {
		return tradeMessageTemplates;
	}

	public void setTradeMessageTemplates(List<TradeMessageTemplateVO> tradeMessageTemplates) {
		this.tradeMessageTemplates = tradeMessageTemplates;
	}

	public List<TradeCardtypeVO> getCardtypesAndAwardRules() {
		return cardtypesAndAwardRules;
	}

	public void setCardtypesAndAwardRules(List<TradeCardtypeVO> cardtypesAndAwardRules) {
		this.cardtypesAndAwardRules = cardtypesAndAwardRules;
	}

	public String[] getFesLogTypes() {
		return fesLogTypes;
	}

	public void setFesLogTypes(String[] fesLogTypes) {
		this.fesLogTypes = fesLogTypes;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public String getStepNum() {
		return stepNum;
	}

	public void setStepNum(String stepNum) {
		this.stepNum = stepNum;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getOnlineProcessStatus() {
		return onlineProcessStatus;
	}

	public void setOnlineProcessStatus(String onlineProcessStatus) {
		this.onlineProcessStatus = onlineProcessStatus;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public SysOperationLogVO(){
	}

	public SysOperationLogVO(
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
	public void setModuleType(java.lang.String value) {
		this.moduleType = value;
	}
	
	public java.lang.String getModuleType() {
		return this.moduleType;
	}
	public void setFesLogType(java.lang.String value) {
		this.fesLogType = value;
	}
	
	public java.lang.String getFesLogType() {
		return this.fesLogType;
	}
	public void setOperatingTime(java.util.Date value) {
		this.operatingTime = value;
	}
	
	public java.util.Date getOperatingTime() {
		return this.operatingTime;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
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
		
	public void setStoreId(java.lang.Integer value) {
		this.storeId = value;
	}
	
	public java.lang.Integer getStoreId() {
		return this.storeId;
	}
		
	public void setRelatedType(java.lang.String value) {
		this.relatedType = value;
	}
	
	public java.lang.String getRelatedType() {
		return this.relatedType;
	}
		
	public void setRelatedId(java.lang.Integer value) {
		this.relatedId = value;
	}
	
	public java.lang.Integer getRelatedId() {
		return this.relatedId;
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
			.append("ModuleType",getModuleType())
			.append("FesLogType",getFesLogType())
			.append("OperatingTime",getOperatingTime())
			.append("Description",getDescription())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("StoreId",getStoreId())
			.append("RelatedType",getRelatedType())
			.append("RelatedId",getRelatedId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SysOperationLogVO == false) return false;
		if(this == obj) return true;
		SysOperationLogVO other = (SysOperationLogVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

