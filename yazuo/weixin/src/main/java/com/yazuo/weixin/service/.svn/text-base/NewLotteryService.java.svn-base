/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.weixin.service;

import java.util.Map;

import com.yazuo.weixin.vo.MemberAwardVO;

/**
 * 在线办卡抽奖相关业务
 * @author kyy
 * @date 2014-8-24 上午10:02:15
 */
public interface NewLotteryService {

	/**幸运抽奖 只有会员才能抽奖（在微信的clink事件去判断，本方法不做判断）*/
	public Map<String, Object> luckyDraw(MemberAwardVO member) throws Exception;
	
	public Map<String, Object> whetherLuckyDraw(MemberAwardVO member) throws Exception;
	
	/**根据weixinId和商户id取会员信息且拼装抽奖的url*/
	public String luckRedirectPath (String weixinId, Integer brandId, String path); 
	
	/**抽奖机会数不走短信提醒商户即短信预警 paramMap:抽奖活动规则信息*/
	public void smsWarning(Map<String, Object> ruleMap);
	
	public Map<String, Object> getSendActivity(String couponIdStr, String luckCount, Integer brandId);
}
