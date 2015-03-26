package com.yazuo.weixin.service;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 绑定会员手机号和实体卡卡号
 * 
 * @author gaoshan
 * 
 */
public interface BindingMemberAndCardService {

	/**
	 * 绑定会员手机号和实体卡卡号
	 * @param params
	 * @return
	 * @throws Exception
	 */
	JSONObject bindingMemberAndCardService(Map<String, Object> params) throws Exception;
}
