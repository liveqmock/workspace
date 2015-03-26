package com.yazuo.weixin.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.WeixinCoffeeBeniDao;
import com.yazuo.weixin.dao.WeixinPayDao;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.service.WeixinPayService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.vo.WxOrder;
import com.yazuo.weixin.vo.WxPayCard;
import com.yazuo.weixin.vo.WxPayCouponState;
import com.yazuo.weixin.vo.WxPayDeliverStateVo;
import com.yazuo.weixin.vo.WxcoffeeCzFlag;

/**
* @ClassName WeixinPayServiceImpl
* @Description 微信支付调用服务类
* @author sundongfeng@yazuo.com
* @date 2014-6-10 上午11:15:03
* @version 1.0
*/
@Service
@Scope("prototype")
public class WeixinPayServiceImpl implements WeixinPayService {
	private static final Log log = LogFactory.getLog("wxpay");
	@Autowired
	private WeixinPayDao weixinPayDao;
	@Autowired
	private WeixinCoffeeBeniDao weixinCoffeeBeniDao;
	
	
	/**
	 * 插入用户支付信息
	 * @throws Exception 
	 */
	public int inserUserPayInfo(WxOrder wxorder) throws WeixinRuntimeException {
		return weixinPayDao.insertPayUserInfo(wxorder);
	}

	/**
	 * 更新支付结果
	 * @throws Exception 
	 */
	public int updatePayResultInfo(WxOrder wxorder) throws WeixinRuntimeException {
		return weixinPayDao.updatePayResultInfo(wxorder);
	}

	public void inserCardInfo(List<WxPayCard> list)
			throws WeixinRuntimeException {
		
		weixinPayDao.inserCardInfo(list);
	}

	public boolean queryHasTicketsCard(String outTradeNo) {
		return weixinPayDao.queryHasTicketsCard(outTradeNo);
	}
	/**
	 * 查询券信息
	 * @throws Exception 
	 * 
	 */
	public JSONObject queryCardInfos(String optainTicketInfoUrl,String brandId,String activeId,String key) throws Exception {
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", brandId);
		jsonMap.put("activeId", activeId);
		String input = JSONObject.fromObject(jsonMap).toString();
		String result = CommonUtil.postSendMessage(optainTicketInfoUrl, input, key);
		log.info("获取券信息结果:"+result);
		
		JSONObject requestObject = JSONObject.fromObject(result);
		
		String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
		JSONObject res=JSONObject.fromObject(data);
		return res;
	}
	/**
	 * 获取用户openid.
	 */
	public JSONObject getWeiXinId(String code,String refToken,String appId,String appSecret) throws Exception {

		if (StringUtils.isBlank(code)) {
			return null;// code 为空请重新关注
		}
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appId + "&secret=" + appSecret + "&code=" + code
				+ "&grant_type=authorization_code";

		String wxjson = CommonUtil.postMessage(url);
		
		log.info("url==" + url + ";wxjson=" + wxjson);

		JSONObject jsona = JSONObject.fromObject(wxjson);

		log.info("jsona=" + jsona);
		if(jsona.containsKey("openid")){
			return jsona;
		}else{
			if(StringUtils.isNotEmpty(refToken)){
				url="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appId+"&grant_type=refresh_token&refresh_token="+refToken;
				String rewxjson =CommonUtil.postMessage(url);
				log.info("url:"+url+";rewxjson:"+rewxjson);
				return JSONObject.fromObject(rewxjson);
			}else{
				return null;
			}
		}

	}

	/**
	 * 插入券状态
	 */
	public int insertCouponState(WxPayCouponState state)
			throws WeixinRuntimeException {
		return weixinPayDao.insertCouponState(state);
	}

	/**
	 * 发送短信内容
	 */
	public void sendMail(String mailUrl,String mail, String subject, WxPayCouponState obj,String key) {
		String[] mails= mail.split(",");
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("mails", mails);
		jsonMap.put("subject", subject);
		String content = "用户手机:"+obj.getMobile()+"\n"+
						"购买券Id:"+obj.getCardId()+"\n"+
						"购买数量:"+obj.getCounter()+"\n"+
						"购买金额:"+obj.getTotalFee()+"\n"+
						"商户订单号:"+obj.getOutTradeNo()+"\n"+
						"银行订单号:"+obj.getTransactionId()+"\n"+
						"商户brandId:"+obj.getPartnerId()+"\n"+
						"用户wxId:"+obj.getOpenId()+"\n";
		jsonMap.put("content", content);
		jsonMap.put("multiple", true);
		
		String input = JSONObject.fromObject(jsonMap).toString();
		log.info("input:"+input);
		String result="";
		try {
			result = CommonUtil.postSendMessage(mailUrl, input, key);
		} catch (WeixinRuntimeException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
		log.info("获取券信息结果:"+result);
		
		JSONObject requestObject = JSONObject.fromObject(result);
		
		String data="";
		try {
			data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
		JSONObject res=JSONObject.fromObject(data);
		Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
		String message = res.getString("message"); // 返回信息
		log.info("邮件服务器返回结果  成功标识:"+success+"\t返回信息:"+message);
	}
	/**
	 * 查询openid
	 */
	public String queryOpenId(String mobile, String brandId)
			throws WeixinRuntimeException {
		return weixinPayDao.queryOpenId(mobile, brandId);
	}
	/**
	 * 查询发货状态
	 */
	public List<WxPayDeliverStateVo> queryDeliver(String brandId)
			throws WeixinRuntimeException {
		return weixinPayDao.queryDeliver(brandId.trim());
	}
	/**
	 * 更新发货状态
	 */
	public int updateDeliverState(String outTradeNo)
			throws WeixinRuntimeException {
		return weixinPayDao.updateDeliverState(outTradeNo);
	}
	/**
	 * 查询未发券的订单信息
	 */
	public List<WxOrder> queryOrders(String brandId)
			throws WeixinRuntimeException {
		return weixinPayDao.queryOrders(brandId);
	}
	/**
	 * 查询是否绑定券状态
	 */
	public boolean queryHasBindCoupon(String outTradeNo) {
		return weixinPayDao.queryHasBindCoupon(outTradeNo);
	}
	/**
	 * 更新券状态
	 */
	public int updateWeixin_pay_coupon_state(WxPayCouponState state) {
		return weixinPayDao.updateWeixin_pay_coupon_state(state);
	}
	/**查询是否有充值状态*/
	public boolean queryHasCzflag(String outTradeNo) {
		return weixinCoffeeBeniDao.queryHasCzflag(outTradeNo);
	}
	/**插入充值状态*/
	public int insertCzflag(WxcoffeeCzFlag czflag) {
		return weixinCoffeeBeniDao.insertCzflag(czflag);
	}
	/**更新充值状态*/
	public int updateCzflag(WxcoffeeCzFlag czflag) {
		return weixinCoffeeBeniDao.updateCzflag(czflag);
	}
	/**查询充值成功状态*/
	public boolean queryCzflag(String outTradeNo) {
		return weixinCoffeeBeniDao.queryCzflag(outTradeNo);
	}
	
}
