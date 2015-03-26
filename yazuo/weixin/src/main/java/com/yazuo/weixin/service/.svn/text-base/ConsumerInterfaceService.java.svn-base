package com.yazuo.weixin.service;

import java.util.List;

import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.Message;

public interface ConsumerInterfaceService {
	public Message message(Message messageIn, BusinessVO business, String path,
			List<AutoreplyVO> autoreplyList);

	public void eventRecord(EventRecordVO eventRecord);

	public String sendIdentifyingCode(String phoneNo, Integer brandId,
			String weixinId, String title);

	public String identifyingAndRegister(String weixinId, Integer brandId,
			String identifyingCode);

	/**
	 * update 2013-12-06
	 * 
	 * 不带验证码注册会员
	 * 
	 */
	public String register(String weixinId, Integer brandId, String phoneNo);
}
