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

import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.base.DicVO;
import com.yazuo.erp.base.TreeVO;

/**
 * @author erp team
 * @date 
 */
public class MktBrandInterviewVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "品牌访谈";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_CONTACT_ID = "商户负责人ID";
	public static final String ALIAS_FORMAT = "业态";
	public static final String ALIAS_STORE_NUMBER = "门店数";
	public static final String ALIAS_JOIN_TYPE = "直营/加盟";
	public static final String ALIAS_BUSINESS_AREA = "营业面积";
	public static final String ALIAS_CUSTOMER_TYPE = "主要顾客类型";
	public static final String ALIAS_DAILY_TURNOVER = "日均营业额";
	public static final String ALIAS_PER_ORDER = "单均消费";
	public static final String ALIAS_PER_CAPITA = "人均消费";
	public static final String ALIAS_GROUP_PURCHASE = "团购";
	public static final String ALIAS_COUPON = "优惠券";
	public static final String ALIAS_BANK_CARD_OFFER = "银行卡优惠";
	public static final String ALIAS_OTHER_SHOP_DISCOUNT = "其他店内优惠";
	public static final String ALIAS_CARD_TYPE = "原有会员卡类别";
	public static final String ALIAS_MANAGEMENT_SYSTEM = "会员卡管理系统";
	public static final String ALIAS_MEMBER_RIGHT = "会员权益";
	public static final String ALIAS_SEND_CARD_NUMBER = "已发卡数量";
	public static final String ALIAS_POTENTIAL_CUSTOMER_SOURCE = "潜客来源";
	public static final String ALIAS_POTENTIAL_CUSTOMER_NUM = "潜客数量";
	public static final String ALIAS_ATTACHMENT_ID = "附件ID";
	public static final String ALIAS_NETWORK_CONDITION = "门店网络条件";
	public static final String ALIAS_NETWORK_SPEED = "门店网络速度 ";
	public static final String ALIAS_CONFIRM_TIME = "调研确认时间";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_NETWORK_REMARK = "网络条件备注";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_CONTACT_ID = "contactId";
	public static final String COLUMN_FORMAT = "format";
	public static final String COLUMN_STORE_NUMBER = "storeNumber";
	public static final String COLUMN_JOIN_TYPE = "joinType";
	public static final String COLUMN_BUSINESS_AREA = "businessArea";
	public static final String COLUMN_CUSTOMER_TYPE = "customerType";
	public static final String COLUMN_DAILY_TURNOVER = "dailyTurnover";
	public static final String COLUMN_CASH_COUNT_PER_DAY = "cashCountPerDay";
	public static final String COLUMN_PER_ORDER = "perOrder";
	public static final String COLUMN_PER_CAPITA = "perCapita";
	public static final String COLUMN_GROUP_PURCHASE = "groupPurchase";
	public static final String COLUMN_COUPON = "coupon";
	public static final String COLUMN_BANK_CARD_OFFER = "bankCardOffer";
	public static final String COLUMN_OTHER_SHOP_DISCOUNT = "otherShopDiscount";
	public static final String COLUMN_CARD_TYPE = "cardType";
	public static final String COLUMN_MANAGEMENT_SYSTEM = "managementSystem";
	public static final String COLUMN_MEMBER_RIGHT = "memberRight";
	public static final String COLUMN_SEND_CARD_NUMBER = "sendCardNumber";
	public static final String COLUMN_POTENTIAL_CUSTOMER_SOURCE = "potentialCustomerSource";
	public static final String COLUMN_POTENTIAL_CUSTOMER_NUM = "potentialCustomerNum";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_NETWORK_CONDITION = "networkCondition";
	public static final String COLUMN_NETWORK_SPEED = "networkSpeed";
	public static final String COLUMN_CONFIRM_TIME = "confirmTime";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_NETWORK_REMARK = "networkRemark";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.Integer contactId; //"商户负责人ID";
	private java.lang.String storeNumber; //"门店数";
	private java.lang.String joinType; //"直营/加盟";
	private java.lang.String businessArea; //"营业面积";
	private java.lang.String customerType; //"主要顾客类型";
	private java.lang.String dailyTurnover; //"日均营业额";
	private java.lang.Integer cashCountPerDay; //"cashCountPerDay";
	private java.lang.String perOrder; //"perOrder";
	private java.lang.String perCapita; //"人均消费";
	private java.lang.String groupPurchase; //"团购";
	private java.lang.String coupon; //"优惠券";
	private java.lang.String bankCardOffer; //"银行卡优惠";
	private java.lang.String otherShopDiscount; //"其他店内优惠";
	private java.math.BigDecimal sendCardNumber; //"已发卡数量";
	private java.lang.String potentialCustomerSource; //"潜客来源";
	private java.math.BigDecimal potentialCustomerNum; //"潜客数量";
	private java.lang.Integer attachmentId; //"附件ID";
	private java.lang.String networkCondition; //"门店网络条件";
	private java.lang.String networkSpeed; //"门店网络速度 ";
	private java.util.Date confirmTime; //"调研确认时间";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.String networkRemark; //"网络条件备注";

	private String[] format; //"业态";
	private String[] cardType; //"原有会员卡类别";
	private String[] managementSystem; //"会员卡管理系统";
	private String[] memberRight; //"会员权益";

	private String whiteListNumberStr; //"白名单数量";
	private String sendCardNumberStr; //"已发卡数量";
	private String moduleType; // 模块类型
	private String merchantName; // 门店名称
	
	private String fileName;// ERP生成的文件名
	private String originalFileName;// 实际文件名
	private Long fileSize; // 文件大小
	private String relativePath; // 展现图片路径
	private String attachmentPath; // 附件存储的部分路径
	private MktContactVO mktContactVO;
	
	/*以下是数据字典 */
	private List<TreeVO> dicFormat;//业态
	private Map<String,Object> dicJoinType;//直营加盟
	private Map<String,Object> dicCustomerType;//主要客户类型
	private Map<String,Object> dicCardType;//卡类型
	private Map<String,Object> dicMagSystem;//会员卡管理系统
	private Map<String,Object> dicMemberRight;//会员权益
	private Map<String,Object> dicNetworkCondition;//网络条件
	private Map<String,Object> dicNetworkSpeed;//网络速度
	private SysAttachmentVO attachDocument; //电子文档
	private List<Map<String,Object>> dicSales;//销售负责人下拉列表
	private Integer salesId;//销售负责人ID
	Map<String, Object> selectedContact;
	List<Map<String, Object>> contact;//下拉列表选择联系人
	public List<Map<String, Object>> getContact() {
		return contact;
	}

	public void setContact(List<Map<String, Object>> contact) {
		this.contact = contact;
	}

	public Integer getSalesId() {
		return salesId;
	}

	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}

	public List<Map<String, Object>> getDicSales() {
		return dicSales;
	}

	public void setDicSales(List<Map<String, Object>> dicSales) {
		this.dicSales = dicSales;
	}

	public java.lang.String getNetworkRemark() {
		return networkRemark;
	}

	public void setNetworkRemark(java.lang.String networkRemark) {
		this.networkRemark = networkRemark;
	}
	public SysAttachmentVO getAttachDocument() {
		return attachDocument;
	}

	public java.lang.Integer getCashCountPerDay() {
		return cashCountPerDay;
	}

	public void setCashCountPerDay(java.lang.Integer cashCountPerDay) {
		this.cashCountPerDay = cashCountPerDay;
	}

	public String[] getFormat() {
		return format;
	}

	public void setFormat(String[] format) {
		this.format = format;
	}

	public void setAttachDocument(SysAttachmentVO attachDocument) {
		this.attachDocument = attachDocument;
	}

	public Map<String, Object> getDicJoinType() {
		return dicJoinType;
	}

	public void setDicJoinType(Map<String, Object> dicJoinType) {
		this.dicJoinType = dicJoinType;
	}

	public Map<String, Object> getDicCustomerType() {
		return dicCustomerType;
	}

	public void setDicCustomerType(Map<String, Object> dicCustomerType) {
		this.dicCustomerType = dicCustomerType;
	}

	public Map<String, Object> getDicCardType() {
		return dicCardType;
	}

	public void setDicCardType(Map<String, Object> dicCardType) {
		this.dicCardType = dicCardType;
	}

	public Map<String, Object> getDicMagSystem() {
		return dicMagSystem;
	}

	public void setDicMagSystem(Map<String, Object> dicMagSystem) {
		this.dicMagSystem = dicMagSystem;
	}

	public Map<String, Object> getDicMemberRight() {
		return dicMemberRight;
	}

	public void setDicMemberRight(Map<String, Object> dicMemberRight) {
		this.dicMemberRight = dicMemberRight;
	}

	public Map<String, Object> getDicNetworkCondition() {
		return dicNetworkCondition;
	}

	public void setDicNetworkCondition(Map<String, Object> dicNetworkCondition) {
		this.dicNetworkCondition = dicNetworkCondition;
	}

	public Map<String, Object> getDicNetworkSpeed() {
		return dicNetworkSpeed;
	}

	public void setDicNetworkSpeed(Map<String, Object> dicNetworkSpeed) {
		this.dicNetworkSpeed = dicNetworkSpeed;
	}
	//columns END

	public List<TreeVO> getDicFormat() {
		return dicFormat;
	}

	public void setDicFormat(List<TreeVO> dicFormat) {
		this.dicFormat = dicFormat;
	}

	public Map<String, Object> getSelectedContact() {
		return selectedContact;
	}

	public void setSelectedContact(Map<String, Object> selectedContact) {
		this.selectedContact = selectedContact;
	}

	public String[] getCardType() {
		return cardType;
	}

	public void setCardType(String[] cardType) {
		this.cardType = cardType;
	}

	public String[] getManagementSystem() {
		return managementSystem;
	}

	public void setManagementSystem(String[] managementSystem) {
		this.managementSystem = managementSystem;
	}

	public String[] getMemberRight() {
		return memberRight;
	}

	public void setMemberRight(String[] memberRight) {
		this.memberRight = memberRight;
	}
	
	public MktContactVO getMktContactVO() {
		return mktContactVO;
	}

	public void setMktContactVO(MktContactVO mktContactVO) {
		this.mktContactVO = mktContactVO;
	}

	public String getWhiteListNumberStr() {
		return whiteListNumberStr;
	}

	public void setWhiteListNumberStr(String whiteListNumberStr) {
		this.whiteListNumberStr = whiteListNumberStr;
	}

	public String getSendCardNumberStr() {
		return sendCardNumberStr;
	}

	public void setSendCardNumberStr(String sendCardNumberStr) {
		this.sendCardNumberStr = sendCardNumberStr;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public MktBrandInterviewVO(){
	}

	public MktBrandInterviewVO(
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
	public void setContactId(java.lang.Integer value) {
		this.contactId = value;
	}
	
	public java.lang.Integer getContactId() {
		return this.contactId;
	}
	
	public void setStoreNumber(java.lang.String value) {
		this.storeNumber = value;
	}
	
	public java.lang.String getStoreNumber() {
		return this.storeNumber;
	}
	public void setJoinType(java.lang.String value) {
		this.joinType = value;
	}
	
	public java.lang.String getJoinType() {
		return this.joinType;
	}
	public void setBusinessArea(java.lang.String value) {
		this.businessArea = value;
	}
	
	public java.lang.String getBusinessArea() {
		return this.businessArea;
	}
	public void setCustomerType(java.lang.String value) {
		this.customerType = value;
	}
	
	public java.lang.String getCustomerType() {
		return this.customerType;
	}
	public void setDailyTurnover(java.lang.String value) {
		this.dailyTurnover = value;
	}
	
	public java.lang.String getDailyTurnover() {
		return this.dailyTurnover;
	}
	public void setPerOrder(java.lang.String value) {
		this.perOrder = value;
	}
	
	public java.lang.String getPerOrder() {
		return this.perOrder;
	}
	public void setPerCapita(java.lang.String value) {
		this.perCapita = value;
	}
	
	public java.lang.String getPerCapita() {
		return this.perCapita;
	}
	public void setGroupPurchase(java.lang.String value) {
		this.groupPurchase = value;
	}
	
	public java.lang.String getGroupPurchase() {
		return this.groupPurchase;
	}
	public void setCoupon(java.lang.String value) {
		this.coupon = value;
	}
	
	public java.lang.String getCoupon() {
		return this.coupon;
	}
	public void setBankCardOffer(java.lang.String value) {
		this.bankCardOffer = value;
	}
	
	public java.lang.String getBankCardOffer() {
		return this.bankCardOffer;
	}
	public void setOtherShopDiscount(java.lang.String value) {
		this.otherShopDiscount = value;
	}
	
	public java.lang.String getOtherShopDiscount() {
		return this.otherShopDiscount;
	}
	public void setSendCardNumber(java.math.BigDecimal value) {
		this.sendCardNumber = value;
	}
	
	public java.math.BigDecimal getSendCardNumber() {
		return this.sendCardNumber;
	}
	public void setPotentialCustomerSource(java.lang.String value) {
		this.potentialCustomerSource = value;
	}
	
	public java.lang.String getPotentialCustomerSource() {
		return this.potentialCustomerSource;
	}
	public void setPotentialCustomerNum(java.math.BigDecimal value) {
		this.potentialCustomerNum = value;
	}
	
	public java.math.BigDecimal getPotentialCustomerNum() {
		return this.potentialCustomerNum;
	}
	public void setAttachmentId(java.lang.Integer value) {
		this.attachmentId = value;
	}
	
	public java.lang.Integer getAttachmentId() {
		return this.attachmentId;
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
	public void setConfirmTime(java.util.Date value) {
		this.confirmTime = value;
	}
	
	public java.util.Date getConfirmTime() {
		return this.confirmTime;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("MerchantId",getMerchantId())
			.append("ContactId",getContactId())
			.append("Format",getFormat())
			.append("StoreNumber",getStoreNumber())
			.append("JoinType",getJoinType())
			.append("BusinessArea",getBusinessArea())
			.append("CustomerType",getCustomerType())
			.append("DailyTurnover",getDailyTurnover())
			.append("PerOrder",getPerOrder())
			.append("PerCapita",getPerCapita())
			.append("GroupPurchase",getGroupPurchase())
			.append("Coupon",getCoupon())
			.append("BankCardOffer",getBankCardOffer())
			.append("OtherShopDiscount",getOtherShopDiscount())
			.append("CardType",getCardType())
			.append("ManagementSystem",getManagementSystem())
			.append("MemberRight",getMemberRight())
			.append("SendCardNumber",getSendCardNumber())
			.append("PotentialCustomerSource",getPotentialCustomerSource())
			.append("PotentialCustomerNum",getPotentialCustomerNum())
			.append("AttachmentId",getAttachmentId())
			.append("NetworkCondition",getNetworkCondition())
			.append("NetworkSpeed",getNetworkSpeed())
			.append("ConfirmTime",getConfirmTime())
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
		if(obj instanceof MktBrandInterviewVO == false) return false;
		if(this == obj) return true;
		MktBrandInterviewVO other = (MktBrandInterviewVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

