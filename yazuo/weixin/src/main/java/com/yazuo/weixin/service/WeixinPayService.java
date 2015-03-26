package com.yazuo.weixin.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.vo.WxOrder;
import com.yazuo.weixin.vo.WxPayCard;
import com.yazuo.weixin.vo.WxPayCouponState;
import com.yazuo.weixin.vo.WxPayDeliverStateVo;
import com.yazuo.weixin.vo.WxcoffeeCzFlag;
	
public interface WeixinPayService {
	
	public int inserUserPayInfo(WxOrder wxorder)throws WeixinRuntimeException;
	
	public int updatePayResultInfo(WxOrder wxorder)throws WeixinRuntimeException;
	
	public void inserCardInfo(List<WxPayCard> list)throws WeixinRuntimeException;
	
	public boolean queryHasTicketsCard(String outTradeNo);
	
	public boolean queryHasBindCoupon(String outTradeNo);
	
	public String queryOpenId(String mobile,String brandId)throws WeixinRuntimeException;
	
	public int insertCouponState(WxPayCouponState state)throws WeixinRuntimeException;
	
	public JSONObject queryCardInfos(String optainTicketInfoUrl,String brandId,String activeId,String key)throws Exception;
	
	public JSONObject getWeiXinId(String code,String refToken,String appId,String appSecret) throws Exception; 
	
	public void sendMail(String mailUrl,String mails,String subject,WxPayCouponState pcsvo,String key);
	
	public List<WxPayDeliverStateVo> queryDeliver(String brandId)throws WeixinRuntimeException;
	
	public int updateDeliverState(String outTradeNo)throws WeixinRuntimeException;
	
	public List<WxOrder> queryOrders(String brandId)throws WeixinRuntimeException;
	
	public int updateWeixin_pay_coupon_state(WxPayCouponState state);//add by sundongfeng 2014-7-8
	
	public boolean queryHasCzflag(String outTradeNo);//add by sundongfeng 2014-7-8
	
	public int insertCzflag(WxcoffeeCzFlag czflag);//add by sundongfeng 2014-7-8
	
	public int updateCzflag(WxcoffeeCzFlag czflag); //add by sundongfeng 2014-7-8
	
	public boolean queryCzflag(String outTradeNo);//add by sundongfeng 2014-7-14
}
