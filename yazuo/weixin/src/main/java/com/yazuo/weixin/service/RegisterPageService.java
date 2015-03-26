package com.yazuo.weixin.service;

import java.util.List;
import java.util.Map;

import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.CardVO;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.Message;

public interface RegisterPageService {
	public Message message(Message messageIn, BusinessVO business, String path, List<AutoreplyVO> autoreplyList); // 返回的信息

	public void eventRecord(EventRecordVO eventRecord); // 记录交互的信息

	public String sendIdentifyingCode(String phoneNo, Integer brandId, String weixinId, String title);// 验证码的发放

	/**
	 * update:2013-12-05 注册会员去掉验证码 update:2014-05-21 注册会员增加验证码功能（去掉的补回来）
	 * 
	 * @param member
	 * @param identifyingCode
	 * @return
	 */
	public String identifyingAndRegister(MemberVO member, String identifyCode, BusinessVO business);// 通过验证码注册用户
	
	public String identifyingAndRegisterWithoutCode(MemberVO member, String phoneNo, BusinessVO business);// 不通过验证码注册用户

	public String identifyingAndRegisterOfCardBound(MemberVO member, String identifyCode, Map<String, Object> params);// 会员卡绑定时，注册用户成为会员

	public String modifyMemberInfo(String birthday, MemberVO member, BusinessVO business);// 修改用户信息

	public CardVO getCard(String cardNo, MemberVO member, BusinessVO business);// 查卡

	public boolean isAllowWeixinMember(Integer brandId);

	/**
	 * @Description 会员卡绑定时，注册用户成为会员，不通过验证码注册
	 * @param member
	 * @param phoneNo
	 * @return
	 * @throws 
	 */
	public String identifyingAndRegisterOfCardBoundWithoutCode(MemberVO member, String phoneNo, Map<String, Object> params);
}
