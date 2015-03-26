package com.yazuo.weixin.vo;
import java.util.*;

public class CardVO {
	private String cardNo;
	private String merchantNo;
	private Date storageDate;
	private Date activeDate;
	private Date expiredDate;
	private String queryPwd;
	private String consumePwd;
	private java.math.BigDecimal storeBalance;
	private java.math.BigDecimal integralBalance;
	private java.math.BigDecimal integralAvailable;
	private Integer merchantId;
	private Integer cardtypeId;
	private Integer status;
	private Integer membershipId;
	private java.sql.Timestamp lastTransTime;
	private java.math.BigDecimal expansivity;
	private java.math.BigDecimal expansivityOld;
	private Integer brandId;
	private String statusInfo;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public Date getStorageDate() {
		return storageDate;
	}
	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}
	public Date getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getQueryPwd() {
		return queryPwd;
	}
	public void setQueryPwd(String queryPwd) {
		this.queryPwd = queryPwd;
	}
	public String getConsumePwd() {
		return consumePwd;
	}
	public void setConsumePwd(String consumePwd) {
		this.consumePwd = consumePwd;
	}
	public java.math.BigDecimal getStoreBalance() {
		return storeBalance;
	}
	public void setStoreBalance(java.math.BigDecimal storeBalance) {
		this.storeBalance = storeBalance;
	}
	public java.math.BigDecimal getIntegralBalance() {
		return integralBalance;
	}
	public void setIntegralBalance(java.math.BigDecimal integralBalance) {
		this.integralBalance = integralBalance;
	}
	public java.math.BigDecimal getIntegralAvailable() {
		return integralAvailable;
	}
	public void setIntegralAvailable(java.math.BigDecimal integralAvailable) {
		this.integralAvailable = integralAvailable;
	}
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getCardtypeId() {
		return cardtypeId;
	}
	public void setCardtypeId(Integer cardtypeId) {
		this.cardtypeId = cardtypeId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(Integer membershipId) {
		this.membershipId = membershipId;
	}
	public java.sql.Timestamp getLastTransTime() {
		return lastTransTime;
	}
	public void setLastTransTime(java.sql.Timestamp lastTransTime) {
		this.lastTransTime = lastTransTime;
	}
	public java.math.BigDecimal getExpansivity() {
		return expansivity;
	}
	public void setExpansivity(java.math.BigDecimal expansivity) {
		this.expansivity = expansivity;
	}
	public java.math.BigDecimal getExpansivityOld() {
		return expansivityOld;
	}
	public void setExpansivityOld(java.math.BigDecimal expansivityOld) {
		this.expansivityOld = expansivityOld;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getStatusInfo() {
		return statusInfo;
	}
	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}
	
}
