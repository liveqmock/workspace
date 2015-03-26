/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.vo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.base.TreeVO;

/**
 * @author erp team
 * @date 
 */
public class MktShopSurveyVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "门店调研";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_STORE_ID = "门店ID";
	public static final String ALIAS_CONTACT_ID = "门店负责人ID";
	public static final String ALIAS_FORMAT = "业态";
	public static final String ALIAS_NEAR = "附近";
	public static final String ALIAS_BUSINESS_AREA = "营业面积";
	public static final String ALIAS_DAILY_PASSENGER_FLOW = "日均客流量";
	public static final String ALIAS_TABLE_AVERAGE = "桌均消费";
	public static final String ALIAS_MEALS_NUMBER = "餐台数";
	public static final String ALIAS_ROOMS_NUMBER = "包间数";
	public static final String ALIAS_ATTENDANCE_RATIO = "上座比例";
	public static final String ALIAS_NETWORK_CONDITION = "门店网络条件";
	public static final String ALIAS_NETWORK_SPEED = "门店网络速度 ";
	public static final String ALIAS_PUBLICITY_MATERIAL = "现有宣传物料";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_NETWORK_REMARK = "网络条件备注";
	public static final String ALIAS_MATER_REMARK = "宣传物料备注";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_STORE_ID = "storeId";
	public static final String COLUMN_CONTACT_ID = "contactId";
	public static final String COLUMN_FORMAT = "format";
	public static final String COLUMN_NEAR = "near";
	public static final String COLUMN_BUSINESS_AREA = "businessArea";
	public static final String COLUMN_DAILY_PASSENGER_FLOW = "dailyPassengerFlow";
	public static final String COLUMN_TABLE_AVERAGE = "tableAverage";
	public static final String COLUMN_MEALS_NUMBER = "mealsNumber";
	public static final String COLUMN_ROOMS_NUMBER = "roomsNumber";
	public static final String COLUMN_ATTENDANCE_RATIO = "attendanceRatio";
	public static final String COLUMN_NETWORK_CONDITION = "networkCondition";
	public static final String COLUMN_NETWORK_SPEED = "networkSpeed";
	public static final String COLUMN_PUBLICITY_MATERIAL = "publicityMaterial";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_NETWORK_REMARK = "networkRemark";
	public static final String COLUMN_MATER_REMARK = "materRemark";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.Integer storeId; //"门店ID";
	private java.lang.Integer contactId; //"门店负责人ID";
	private String[] format; //"业态";
	private java.lang.String near; //"附近";
	private java.lang.String businessArea; //"营业面积";
	private java.lang.String dailyPassengerFlow; //"日均客流量";
	private java.lang.String tableAverage; //"桌均消费";
	private java.lang.String mealsNumber; //"餐台数";
	private java.lang.String roomsNumber; //"包间数";
	private java.lang.String attendanceRatio; //"上座比例";
	private java.lang.String networkCondition; //"门店网络条件";
	private java.lang.String networkSpeed; //"门店网络速度 ";
	private String[] publicityMaterial; //"现有宣传物料";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.String networkRemark; //"网络条件备注";
	private java.lang.String materRemark; //"宣传物料备注";
	//columns END
	private String merchantName; //商户名称
	private Integer processId; // 流程id
	private String moduleType; // 模块类型
	private String storeName; //"门店名称";
	
	/*以下是下拉列表*/
	private Map<String, Object> merchantsForSurvey;//选择门店下拉列表
	private Map<String, Object> mktContactList;//商户负责人
	/*以下是数据字典 */
	private List<TreeVO> dicFormat;//业态
	private Map<String, Object> dicNear;//附近
	private Map<String, Object> dicNetworkCondition;//网络条件
	private Map<String, Object> dicNetworkSpeed;//网络速度
	private Map<String, Object> dicPublicityMaterial;//现有宣传物料
	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Map<String, Object> getMerchantsForSurvey() {
		return merchantsForSurvey;
	}

	public void setMerchantsForSurvey(Map<String, Object> merchantsForSurvey) {
		this.merchantsForSurvey = merchantsForSurvey;
	}

	public Map<String, Object> getMktContactList() {
		return mktContactList;
	}

	public void setMktContactList(Map<String, Object> mktContactList) {
		this.mktContactList = mktContactList;
	}

	public List<TreeVO> getDicFormat() {
		return dicFormat;
	}

	public void setDicFormat(List<TreeVO> dicFormat) {
		this.dicFormat = dicFormat;
	}

	public Map<String, Object> getDicNetworkCondition() {
		return dicNetworkCondition;
	}

	public void setDicNetworkCondition(Map<String, Object> dicNetworkCondition) {
		this.dicNetworkCondition = dicNetworkCondition;
	}

	public Map<String, Object> getDicPublicityMaterial() {
		return dicPublicityMaterial;
	}

	public void setDicPublicityMaterial(Map<String, Object> dicPublicityMaterial) {
		this.dicPublicityMaterial = dicPublicityMaterial;
	}

	public Map<String, Object> getDicNetworkSpeed() {
		return dicNetworkSpeed;
	}

	public void setDicNetworkSpeed(Map<String, Object> dicNetworkSpeed) {
		this.dicNetworkSpeed = dicNetworkSpeed;
	}

	public Map<String, Object> getDicNear() {
		return dicNear;
	}

	public void setDicNear(Map<String, Object> dicNear) {
		this.dicNear = dicNear;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public MktShopSurveyVO(){
	}

	public MktShopSurveyVO(
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
	public void setStoreId(java.lang.Integer value) {
		this.storeId = value;
	}
	
	public java.lang.Integer getStoreId() {
		return this.storeId;
	}
	public void setContactId(java.lang.Integer value) {
		this.contactId = value;
	}
	
	public java.lang.Integer getContactId() {
		return this.contactId;
	}
	public void setFormat(String[] value) {
		this.format = value;
	}
	
	public String[] getFormat() {
		return this.format;
	}
	public void setNear(java.lang.String value) {
		this.near = value;
	}
	
	public java.lang.String getNear() {
		return this.near;
	}
	public void setBusinessArea(java.lang.String value) {
		this.businessArea = value;
	}
	
	public java.lang.String getBusinessArea() {
		return this.businessArea;
	}
	public void setDailyPassengerFlow(java.lang.String value) {
		this.dailyPassengerFlow = value;
	}
	
	public java.lang.String getDailyPassengerFlow() {
		return this.dailyPassengerFlow;
	}
	public void setTableAverage(java.lang.String value) {
		this.tableAverage = value;
	}
	
	public java.lang.String getTableAverage() {
		return this.tableAverage;
	}
	public void setMealsNumber(java.lang.String value) {
		this.mealsNumber = value;
	}
	
	public java.lang.String getMealsNumber() {
		return this.mealsNumber;
	}
	public void setRoomsNumber(java.lang.String value) {
		this.roomsNumber = value;
	}
	
	public java.lang.String getRoomsNumber() {
		return this.roomsNumber;
	}
	public void setAttendanceRatio(java.lang.String value) {
		this.attendanceRatio = value;
	}
	
	public java.lang.String getAttendanceRatio() {
		return this.attendanceRatio;
	}
	public void setNetworkCondition(java.lang.String value) {
		this.networkCondition = value;
	}
	
	public java.lang.String getNetworkCondition() {
		return this.networkCondition;
	}
	public void setNetworkSpeed(java.lang.String value) {
		this.networkSpeed = value;
	}
	
	public java.lang.String getNetworkSpeed() {
		return this.networkSpeed;
	}
	public void setPublicityMaterial(String[] value) {
		this.publicityMaterial = value;
	}
	
	public String[] getPublicityMaterial() {
		return this.publicityMaterial;
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

	public void setNetworkRemark(java.lang.String value) {
		this.networkRemark = value;
	}
	
	public java.lang.String getNetworkRemark() {
		return this.networkRemark;
	}
	public void setMaterRemark(java.lang.String value) {
		this.materRemark = value;
	}
	
	public java.lang.String getMaterRemark() {
		return this.materRemark;
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
			.append("StoreId",getStoreId())
			.append("ContactId",getContactId())
			.append("Format",getFormat())
			.append("Near",getNear())
			.append("BusinessArea",getBusinessArea())
			.append("DailyPassengerFlow",getDailyPassengerFlow())
			.append("TableAverage",getTableAverage())
			.append("MealsNumber",getMealsNumber())
			.append("RoomsNumber",getRoomsNumber())
			.append("AttendanceRatio",getAttendanceRatio())
			.append("NetworkCondition",getNetworkCondition())
			.append("NetworkSpeed",getNetworkSpeed())
			.append("PublicityMaterial",getPublicityMaterial())
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
		if(obj instanceof MktShopSurveyVO == false) return false;
		if(this == obj) return true;
		MktShopSurveyVO other = (MktShopSurveyVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

