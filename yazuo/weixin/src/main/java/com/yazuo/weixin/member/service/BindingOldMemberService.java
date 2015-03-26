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
package com.yazuo.weixin.member.service;

import net.sf.json.JSONObject;


/**
 * 老会员绑定业务
 * @author kyy
 * @date 2014-10-9 上午10:55:51
 */
public interface BindingOldMemberService {

	/** 老会员绑定*/
	Object bingdingOldMember(String phoneNo, String identifyCode, Integer brandId, String weixinId);
	
	/** 查询crm会员*/
	JSONObject queryCrmMember(Integer brandId, String phoneNo);
	/**下发验证码*/
	String sendIdentifyingCode(String phoneNo, Integer brandId, String weixinId, String title);
}
