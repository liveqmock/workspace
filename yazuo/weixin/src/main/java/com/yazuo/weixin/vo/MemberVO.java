package com.yazuo.weixin.vo;

import java.util.Date;

public class MemberVO {

	private String weixinId;
	private String phoneNo;
	private Integer brandId;
	private Integer id;
	private String cardNo;
	private Boolean isSubscribe;
	private Boolean isMember;
	private Integer membershipId;
	private Date birthday;
	private Integer updateTimes;
	private Date subscribeTime;
	private String name; // 姓名
	private Integer gender; // 性别 1男 2女
	private Integer memberType; //-1-初始化，1-粉丝卡，2-实体卡，2014-06-06 by gaoshan
	private String birthType;//1 阳历 2 阴历 add by sundongfeng 2014-7-10
	private Integer dataSource;//数据来源 14 网页wifi 13 微信wifi
	private Integer merchantSource; // 商户来源 ； 1  只是wifi商户过来添加的会员
	
	private int status; // 状态
	
	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	public String getBirthType() {
		return birthType;
	}

	public void setBirthType(String birthType) {
		this.birthType = birthType;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Boolean getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public Boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}

	public Integer getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(Integer membershipId) {
		this.membershipId = membershipId;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getUpdateTimes() {
		return updateTimes;
	}

	public void setUpdateTimes(Integer updateTimes) {
		this.updateTimes = updateTimes;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Integer getMerchantSource() {
		return merchantSource;
	}

	public void setMerchantSource(Integer merchantSource) {
		this.merchantSource = merchantSource;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MemberVO [weixinId=" + weixinId + ", phoneNo=" + phoneNo
				+ ", brandId=" + brandId + ", id=" + id + ", cardNo=" + cardNo
				+ ", isSubscribe=" + isSubscribe + ", isMember=" + isMember
				+ ", membershipId=" + membershipId + ", birthday=" + birthday
				+ ", updateTimes=" + updateTimes + ", subscribeTime="
				+ subscribeTime + ", name=" + name + ", gender=" + gender
				+ ", memberType=" + memberType + ", birthType=" + birthType
				+ "]";
	}
}
