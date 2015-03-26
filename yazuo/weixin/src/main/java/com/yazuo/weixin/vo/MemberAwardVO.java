/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-02-27
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.vo;

/**
 * @InterfaceName: MembershipVO
 * @Description: 抽奖人员的信息
 */
public class MemberAwardVO {

	private int membershipId;// 会员id
	private String weixinId;// 微信id
	private String phoneNo;// 手机号
	private int brandId;// 商户id
	private int type;// 抽奖类型
	
	private String beginTime; // 活动规则开始时间
	private String endTime;// 活动规则结束时间

	@Override
	public String toString() {
		return "MemberAwardVO [membershipId=" + membershipId + ", weixinId="
				+ weixinId + ", phoneNo=" + phoneNo + ", brandId=" + brandId
				+ ", type=" + type + "]";
	}

	public int getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
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

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
	
}
