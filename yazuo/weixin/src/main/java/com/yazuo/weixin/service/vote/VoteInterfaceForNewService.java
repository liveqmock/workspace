package com.yazuo.weixin.service.vote;

import java.util.List;

import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.Message;

public interface VoteInterfaceForNewService {
	public Message message(Message messageIn, BusinessVO business, String path,
			List<AutoreplyVO> autoreplyList);
	
	public Message messageNew(Message messageIn, BusinessVO business, String path,
			List<AutoreplyVO> autoreplyList);

	public void eventRecord(EventRecordVO eventRecord);

	public String sendIdentifyingCode(String phoneNo, Integer brandId,
			String weixinId, String title);

	public String identifyingAndVote(String weixinId, Integer brandId,
			String identifyingCode);
}
