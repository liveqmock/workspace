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
package com.yazuo.weixin.member.controller;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.member.service.MerchantWifiService;
import com.yazuo.weixin.member.service.ResourceManagerService;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.service.NewLotteryService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.vo.BusinessVO;


/**
* @ClassName MerchantWifiWeixinController
* @Description 微信wifi
* @author sundongfeng@yazuo.com
* @date 2014-10-17 下午9:34:56
* @version 1.0
*/
@Controller
@RequestMapping("/weixin/cardLottery")
public class MerchantWifiWeixinController {

	private static final Log LOG = LogFactory.getLog("lottery");
	
	@Value("#{propertiesReader['queryCouponListByIdList']}")
	private String queryCouponListByIdList; // 办卡送的劵查询接口
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;

	@Resource
	private WeixinMallMartService weixinMallMartService;
	@Autowired
	private WeixinManageService weixinManageService;
	@Resource
	private NewLotteryService newLotteryService;
	@Resource
	private MerchantWifiService merchantWifiService;
	@Resource
	private ResourceManagerService resourceManagerService;
	
	
	// 绑定实体卡成功，机会也送成功
	@RequestMapping(value = "wifiBindSuccess", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView boundSuccess(String couponIdStr,String luckCount, Integer brandId, String weixinId,Boolean flag,
			@RequestParam(value="membershipId", required=false)Integer membershipId,@RequestParam(value="phoneNo", required=false)String phoneNo
			,HttpServletRequest request) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		ModelAndView mav = new ModelAndView("wifi/wifi_success");
		// 取送的劵或抽奖机会
		Map<String, Object> resultMap = newLotteryService.getSendActivity(couponIdStr, luckCount, brandId);
		
		boolean isPush = false;
		// 是否下发推送页
		boolean pushPage = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_PAGE_TYPE_VALUE, brandId);
		// 是否下发短信
		boolean pushSms = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_SMS_TYPE_VALUE, brandId);
		LOG.info("商户id："+brandId+";是否设置门店wifi下发推送页："+pushPage+";是否设置门店wifi下发短信："+pushSms);
		if (pushPage) {
			// 判断通过中间页跳转到哪个页面
			isPush = merchantWifiService.judgeRedirectPage(brandId, membershipId);
		}
		if (pushSms) {
			String smsUrl = "";
			if (isPush) { // 是否粉丝会员，推荐页和下发短信
				smsUrl = serverIp + request.getContextPath() + "/merchantWifi/pushPage.do?brandId="+brandId+"&weixinId="+weixinId;
			} else {
				smsUrl = serverIp+ request.getContextPath() + "/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId;
			}
			LOG.info("短信链接地址：" + smsUrl);
			merchantWifiService.dealSms(phoneNo, brandId, membershipId, smsUrl);
		}
		LOG.info("商户id："+brandId+"；是否进推送页：" + isPush);
		mav.addObject("isPush", isPush);
		mav.addObject("luckCount", resultMap.get("luckCount"));
		mav.addObject("isGiveLuckCount", resultMap.get("isGiveLuckCount"));
		mav.addObject("couponList", resultMap.get("couponList"));
		
		mav.addObject("flag",flag);
		mav.addObject("brandId", brandId);
		mav.addObject("weixinId", weixinId);
		mav.addObject("business", business);
		return mav;
	}
	
	
}
