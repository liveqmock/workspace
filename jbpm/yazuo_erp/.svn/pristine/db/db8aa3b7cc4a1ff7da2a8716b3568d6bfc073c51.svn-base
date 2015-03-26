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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.syn.vo.SynMerchantVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class FesOpenCardApplicationVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "开卡申请单";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_PROCESS_ID = "流程ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_OPEN_CARD_APPLICATION_STATUS = "状态";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PROCESS_ID = "processId";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_OPEN_CARD_APPLICATION_STATUS = "openCardApplicationStatus";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer processId; //"流程ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String openCardApplicationStatus; //"状态";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	
	private List<Map<String, Object>> membershipDic; //会员等级数据字典下拉框

	public List<Map<String, Object>> getMembershipDic() {
		return membershipDic;
	}

	public void setMembershipDic(List<Map<String, Object>> membershipDic) {
		this.membershipDic = membershipDic;
	}

	public FesOpenCardApplicationVO(){
	}

	public FesOpenCardApplicationVO(
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
	public void setMerchantId(java.lang.Integer value) {
		this.merchantId = value;
	}
	
	public java.lang.Integer getMerchantId() {
		return this.merchantId;
	}
	public void setOpenCardApplicationStatus(java.lang.String value) {
		this.openCardApplicationStatus = value;
	}
	
	public java.lang.String getOpenCardApplicationStatus() {
		return this.openCardApplicationStatus;
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
	
	private List<FesOpenCardApplicationDtlVO> fesOpenCardApplicationDtls;
	
	public List<FesOpenCardApplicationDtlVO> getFesOpenCardApplicationDtls() {
		return fesOpenCardApplicationDtls;
	}

	public void setFesOpenCardApplicationDtls(List<FesOpenCardApplicationDtlVO> fesOpenCardApplicationDtls) {
		this.fesOpenCardApplicationDtls = fesOpenCardApplicationDtls;
	}

	private SynMerchantVO synMerchant;
	
	public void setSynMerchant(SynMerchantVO synMerchant){
		this.synMerchant = synMerchant;
	}
	
	public SynMerchantVO getSynMerchant() {
		return synMerchant;
	}
	
	private FesOnlineProcessVO fesOnlineProcess;
	
	public void setFesOnlineProcess(FesOnlineProcessVO fesOnlineProcess){
		this.fesOnlineProcess = fesOnlineProcess;
	}
	
	public FesOnlineProcessVO getFesOnlineProcess() {
		return fesOnlineProcess;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProcessId",getProcessId())
			.append("MerchantId",getMerchantId())
			.append("OpenCardApplicationStatus",getOpenCardApplicationStatus())
			.append("IsEnable",getIsEnable())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof FesOpenCardApplicationVO == false) return false;
		if(this == obj) return true;
		FesOpenCardApplicationVO other = (FesOpenCardApplicationVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

