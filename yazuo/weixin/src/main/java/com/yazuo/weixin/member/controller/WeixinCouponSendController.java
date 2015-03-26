package com.yazuo.weixin.member.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yazuo.weixin.member.CommonShortLink;
import com.yazuo.weixin.member.service.CouponSendService;
import com.yazuo.weixin.member.vo.ToSend;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;

/**
* @ClassName WeixinCouponSendController
* @Description 优惠券赠送功能
* @author sundongfeng@yazuo.com
* @date 2014-11-5 下午3:56:25
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/weixin/{brandId}")
public class WeixinCouponSendController {
	private static final Log log = LogFactory.getLog("weixin");
	
	@Value("#{propertiesReader['GiveCouponInto']}")
	private String GiveCouponInto;
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Value("#{payPropertiesReader['smsUrl']}")
	private String smsUrl;
	@Resource
	private CouponSendService couponSendService;
	@Autowired
	private WeixinPhonePageService weixinPhonePageService;
	@Autowired
	private WeixinManageService weixinManageService;
	
	@RequestMapping(value="toSendFriend", method = { RequestMethod.POST,RequestMethod.GET })
	public String toSendFriend(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			ToSend toSend,ModelMap model
			){
		model.addAttribute("toSend", toSend);
		model.addAttribute("brandId", brandId);
		return "/member/w_membershipCard_giveCoupon";
	}
	@RequestMapping(value="sendFriend", method = { RequestMethod.POST,RequestMethod.GET })
	public void sendFriend(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			ToSend toSend
			){
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, toSend.getWeixinId());
		Map<String,Object> obj = couponSendService.sendCouponAddMember(brandId,toSend.getMobile());
		JSONObject resJson = new JSONObject();
		boolean flag = false;
		String toMemberId = obj.get("membership_id").toString();
		String rec_weixinId =(String) obj.get("weixin_id");
		String longUrl = serverIp+"/yazuo-weixin/weixin/phonePage/membershipCard.do?brandId="+brandId+"&weixinId="+rec_weixinId	;
		log.info("longUrl:"+longUrl);
		String shortUrl = CommonShortLink.getShortLink(longUrl);
		log.info("shortUrl:"+shortUrl);
		//调用李斌送券接口
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", brandId);
		jsonMap.put("sendId",toSend.getFromUserId());
		jsonMap.put("receiveId", toMemberId);
		jsonMap.put("couponId", toSend.getCouponId());
		jsonMap.put("smsShortUrl", shortUrl);
		String input = JSONObject.fromObject(jsonMap).toString();
		try {
			String result = CommonUtil.postSendMessage(GiveCouponInto, input, Constant.KEY.toString());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			log.info("接口返回："+res.toString());
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("接口返回message："+message);
				if (success) {// 成功
					flag =true;
					String content = "您的好友 "+member.getName()+"，手机号："+member.getPhoneNo()+"，" +
							"向您赠送了"+business.getTitle()+"#"+toSend.getCouponName()+" "+toSend.getCouponNum()+"张# 。点击"+shortUrl+"查看优惠券。关注 "+business.getTitle()+"微信公众账号，可查看更多信息。";
					log.info("赠送内容:"+content);
					//发送短信内容
					String jsonpar = "{'mobile':'" + toSend.getMobile() + "','content':'" + content+ "','merchant_id':'"+brandId+"','source':'8'}";
					CommonUtil.postSendMessage(smsUrl,jsonpar,Constant.KEY.toString());//发送短信
				}
			}
		} catch (Exception e) {
			log.error("赠券  happen error.",e);
		}
		if(flag){
			resJson.put("flag", "success");
		}else{
			resJson.put("flag", "fail");
		}
		log.info("赠券返回json:"+resJson.toString());
		try {
			response.getWriter().print(resJson.toString());
			response.getWriter().close();
		} catch (IOException e) {
			log.error("code happen error.",e);
		}
	}
	
	
}
