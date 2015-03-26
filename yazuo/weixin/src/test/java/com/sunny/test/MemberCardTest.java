package com.sunny.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;

/**
* @ClassName MemberCardTest
* @Description 会员卡类型获取列表
* @author sundongfeng@yazuo.com
* @date 2014-8-11 上午11:04:47
* @version 1.0
*/
public class MemberCardTest {
	
	@Test
	public void getMemberCardList(){
		String url = "http://192.168.232.115:8080/crmapi/newmembership/getMemAndCardMobileAndmerchantId.do";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", 15);
		jsonMap.put("mobile", "13400000003");
		String input = JSONObject.fromObject(jsonMap).toString();
		
		String result="";
		try {
			result = CommonUtil.postSendMessage(url, input, Constant.KEY.toString());
		} catch (WeixinRuntimeException e1) {
			e1.printStackTrace();
		}
		System.out.println(result);
	}
	
	@Test
	public void getMemberCouponList(){
		String url = "http://192.168.232.115:8080/crmapi/couponnew/getCouponByCardNoAndMerchantId.do";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", 21);
		jsonMap.put("mobile", "15011337716");
		jsonMap.put("cardNo", "6201300008977188");
		String input = JSONObject.fromObject(jsonMap).toString();
		
		String result="";
		try {
			result = CommonUtil.postSendMessage(url, input, Constant.KEY.toString());
		} catch (WeixinRuntimeException e1) {
			e1.printStackTrace();
		}
		System.out.println(result);
	}
	
}
