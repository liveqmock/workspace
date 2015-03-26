package com.yazuo.weixin.vo;

import java.io.Serializable;
/**
 * 
* @ClassName WxCardInfo
* @Description  微信券vo类
* @author sundongfeng@yazuo.com
* @date 2014-6-10 下午5:31:51
* @version 1.0
 */
public class WxCardInfo implements Serializable {
	private String couponId;
	private String activeId;
	private String couponName;
	private String couponAmount;
	private String couponCost;
	private String couponCount;
	private String expireBegin;
	private String expireEnd;
	private String relativeExpireBegin;
	private String relativeExpireEnd;
	private String couponDesc;
	private String status;
	private String expireType;
	private String batchNo;
	private String templateKey;
	private String beginTime;
	private String endTime;
	private String smsNotice;
	private String startWeek;
	private String endWeek;
	private String scope;
	private String useMerchantIds;
	private String execMode;
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}
	public String getCouponCost() {
		return couponCost;
	}
	public void setCouponCost(String couponCost) {
		this.couponCost = couponCost;
	}
	public String getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(String couponCount) {
		this.couponCount = couponCount;
	}
	public String getExpireBegin() {
		return expireBegin;
	}
	public void setExpireBegin(String expireBegin) {
		this.expireBegin = expireBegin;
	}
	public String getExpireEnd() {
		return expireEnd;
	}
	public void setExpireEnd(String expireEnd) {
		this.expireEnd = expireEnd;
	}
	public String getRelativeExpireBegin() {
		return relativeExpireBegin;
	}
	public void setRelativeExpireBegin(String relativeExpireBegin) {
		this.relativeExpireBegin = relativeExpireBegin;
	}
	public String getRelativeExpireEnd() {
		return relativeExpireEnd;
	}
	public void setRelativeExpireEnd(String relativeExpireEnd) {
		this.relativeExpireEnd = relativeExpireEnd;
	}
	public String getCouponDesc() {
		return couponDesc;
	}
	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExpireType() {
		return expireType;
	}
	public void setExpireType(String expireType) {
		this.expireType = expireType;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getTemplateKey() {
		return templateKey;
	}
	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSmsNotice() {
		return smsNotice;
	}
	public void setSmsNotice(String smsNotice) {
		this.smsNotice = smsNotice;
	}
	public String getStartWeek() {
		return startWeek;
	}
	public void setStartWeek(String startWeek) {
		this.startWeek = startWeek;
	}
	public String getEndWeek() {
		return endWeek;
	}
	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getUseMerchantIds() {
		return useMerchantIds;
	}
	public void setUseMerchantIds(String useMerchantIds) {
		this.useMerchantIds = useMerchantIds;
	}
	public String getExecMode() {
		return execMode;
	}
	public void setExecMode(String execMode) {
		this.execMode = execMode;
	}
	public String toString() {
		return "WxCardInfo [couponId=" + couponId + ", activeId=" + activeId
				+ ", couponName=" + couponName + ", couponAmount="
				+ couponAmount + ", couponCost=" + couponCost
				+ ", couponCount=" + couponCount + ", expireBegin="
				+ expireBegin + ", expireEnd=" + expireEnd
				+ ", relativeExpireBegin=" + relativeExpireBegin
				+ ", relativeExpireEnd=" + relativeExpireEnd + ", couponDesc="
				+ couponDesc + ", status=" + status + ", expireType="
				+ expireType + ", batchNo=" + batchNo + ", templateKey="
				+ templateKey + ", beginTime=" + beginTime + ", endTime="
				+ endTime + ", smsNotice=" + smsNotice + ", startWeek="
				+ startWeek + ", endWeek=" + endWeek + ", scope=" + scope
				+ ", useMerchantIds=" + useMerchantIds + ", execMode="
				+ execMode + "]";
	} 
	
	
}
