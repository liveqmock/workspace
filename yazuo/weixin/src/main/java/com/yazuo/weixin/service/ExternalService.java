package com.yazuo.weixin.service;

import com.yazuo.weixin.vo.MemberVO;

public interface ExternalService {
	public MemberVO getMemberByOpenId(String openId);

	public String sendSMSByOpenId(String openId, String content);

	public String sendSMSByMobileNo(String mobileNo, String content);

	public String bindMobileNoAndOpenId(String mobileNo, String openId,Integer brandId);
}
