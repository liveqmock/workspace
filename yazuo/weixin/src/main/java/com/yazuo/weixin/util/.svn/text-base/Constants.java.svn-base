package com.yazuo.weixin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

	/**WIFI相关来源*/
	public static final Integer RESOURCE_SOURCE_1 = 1;
	/**会员页导航来源*/
	public static final Integer RESOURCE_SOURCE_2 = 2;
	/**会员页其他功能来源*/
	public static final Integer RESOURCE_SOURCE_3 = 3;
	/**其他功能*/
	public static final Integer RESOURCE_SOURCE_4 = 4;
	/**资源集合*/
	public static Map<String, Object> RESOURCE_MAPS = new HashMap<String, Object>();
	
	/**家人*/
	public static final Integer FAMILY =1;
	/**伴侣*/
	public static final Integer COMPANION=2;
	/**子女*/
	public static final Integer CHILD=3;	
	/**父亲*/
	public static final Integer FATHER=4;
	/**母亲*/
	public static final Integer MONTHER=5;
	/**朋友*/
	public static final Integer FRIEND =6;
	/**恋人*/
	public static final Integer SWEETHEART=7;
	/**其他*/
	public static final Integer OTHER=9;
	
	/**与该会员关系*/
	public static Map<String, Object> RELATION_MAPS = new HashMap<String, Object>();
	public static List<Map<String, Object>> RELATIONLIST = new ArrayList<Map<String,Object>>();
	/**设置需要过滤的url*/
	public static List<String> RESOURCELIST = new ArrayList<String>();
	
	static {
		RESOURCE_MAPS.put("WIFI相关", RESOURCE_SOURCE_1);
		RESOURCE_MAPS.put("会员页导航", RESOURCE_SOURCE_2);
		RESOURCE_MAPS.put("会员页其他功能", RESOURCE_SOURCE_3);
		RESOURCE_MAPS.put("其他", RESOURCE_SOURCE_4);
		
		RELATION_MAPS.put("家人", FAMILY);
		RELATION_MAPS.put("伴侣", COMPANION);
		RELATION_MAPS.put("子女", CHILD);
		RELATION_MAPS.put("父亲", FATHER);
		RELATION_MAPS.put("母亲", MONTHER);
		RELATION_MAPS.put("朋友", FRIEND);
		RELATION_MAPS.put("恋人", SWEETHEART);
		RELATION_MAPS.put("其他", OTHER);
		
		for (String key : RELATION_MAPS.keySet()) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", RELATION_MAPS.get(key));
			m.put("name", key);
			RELATIONLIST.add(m);
		}
		// url需要过滤的菜单
//		RESOURCELIST.add("/weixin/{brandId}/mallMartIndex.do");// 粉丝特卖会
//		RESOURCELIST.add("/weixin/{brandId}/goCoffeeRecharge.do");	 //在线充值
		RESOURCELIST.add("/weixin/{brandId}/toSendFriend.do");	 // 赠卷
//		RESOURCELIST.add("/weixin/{brandId}/sellCardIndex.do");	 // 在线办卡
//		RESOURCELIST.add("/caffe/cardLottery/luckRoulette.do");	 // 幸运转盘
//		RESOURCELIST.add("/caffe/cardLottery/scratchCard");	 //刮刮卡
//		RESOURCELIST.add("/caffe/cardLottery/slotMachine");  //老虎机
//		RESOURCELIST.add("/weixin/consumerLottery/luckRoulette.do"); // 幸运转盘
//		RESOURCELIST.add("/weixin/consumerLottery/scratchCard");	 //刮刮卡
//		RESOURCELIST.add("/weixin/consumerLottery/slotMachine"); //老虎机

	}
	
	
	public static void main(String [] args){
		String resourceUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={appId}&redirect_uri={path}/weixin/{brandId}/mallMartIndex.do&response_type=code&scope=snsapi_base&state=0.8#wechat_redirect";
		resourceUrl  = resourceUrl.substring(resourceUrl.indexOf("{path}"), resourceUrl.lastIndexOf(".do")+3).replace("{path}", "").replace("{brandId}", 15+"");
		System.out.println("---------------" + resourceUrl);
	}
}
