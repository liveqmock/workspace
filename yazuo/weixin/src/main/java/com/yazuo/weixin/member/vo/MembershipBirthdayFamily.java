package com.yazuo.weixin.member.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MembershipBirthdayFamily implements Serializable{

	private Integer id;
	private Integer membershipId; 
	private Integer merchantId; //商户ID
	private Integer birthdayFamilySeq;	// 家人生日序号	（默认给0）排序用
	private Integer dictFamilyRelationId;// 关系Id	
	private String birthdayFamilyDate;//生日日期	
	private Integer birthdayFamilyFlag;	// 标识	是
	private Integer birthdayType; // 生日类型
	private String birthdayFamilyAddtime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(Integer membershipId) {
		this.membershipId = membershipId;
	}
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getBirthdayFamilySeq() {
		return birthdayFamilySeq;
	}
	public void setBirthdayFamilySeq(Integer birthdayFamilySeq) {
		this.birthdayFamilySeq = birthdayFamilySeq;
	}
	public Integer getDictFamilyRelationId() {
		return dictFamilyRelationId;
	}
	public void setDictFamilyRelationId(Integer dictFamilyRelationId) {
		this.dictFamilyRelationId = dictFamilyRelationId;
	}
	public String getBirthdayFamilyAddtime() {
		return birthdayFamilyAddtime;
	}
	public void setBirthdayFamilyAddtime(String birthdayFamilyAddtime) {
		this.birthdayFamilyAddtime = birthdayFamilyAddtime;
	}
	public String getBirthdayFamilyDate() {
		return birthdayFamilyDate;
	}
	public void setBirthdayFamilyDate(String birthdayFamilyDate) {
		this.birthdayFamilyDate = birthdayFamilyDate;
	}
	public Integer getBirthdayFamilyFlag() {
		return birthdayFamilyFlag;
	}
	public void setBirthdayFamilyFlag(Integer birthdayFamilyFlag) {
		this.birthdayFamilyFlag = birthdayFamilyFlag;
	}
	public Integer getBirthdayType() {
		return birthdayType;
	}
	public void setBirthdayType(Integer birthdayType) {
		this.birthdayType = birthdayType;
	}
	
}
