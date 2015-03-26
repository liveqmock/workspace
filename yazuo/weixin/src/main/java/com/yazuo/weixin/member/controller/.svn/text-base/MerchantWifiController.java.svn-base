package com.yazuo.weixin.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.card.service.CardService;
import com.yazuo.weixin.es.vo.CardTypeVo;
import com.yazuo.weixin.member.service.MerchantWifiService;
import com.yazuo.weixin.member.service.ResourceManagerService;
import com.yazuo.weixin.member.service.SettingCardRecommendService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;

/**
 * 门店wifi相关业务逻辑
 * 
 * @author kyy
 * @date 2014-10-9 下午4:30:58
 */
@Controller
@RequestMapping("merchantWifi")
public class MerchantWifiController {
	@Resource
	private MerchantWifiService merchantWifiService;
	@Resource
	private WeixinManageService weixinManageService;
	@Resource
	private WeixinPhonePageService weixinPhonePageService;
	@Resource
	private ResourceManagerService resourceManagerService;
	@Resource
	private CardService cardService;
	@Resource
	private SettingCardRecommendService settingCardRecommendService;
	
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	
	@Value("#{propertiesReader['dfsFilePath']}")
	private String dfsFilePath; // 图片服务器地址
	
	
	private static final Log LOG = LogFactory.getLog("weixin");

	// 浏览器(提供给浩博调用接口)
	@RequestMapping(value = "connectWifiAddMember", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView connectWifiAddMember(@RequestParam(value = "brandId", required = true) Integer brandId,
			@RequestParam(value = "phoneNo", required = true) String phoneNo, HttpServletRequest request) {
		long timeStart = System.currentTimeMillis();
		Map<String, Object> map = merchantWifiService.connectWifiAddMember(brandId, phoneNo, request.getContextPath());
		ModelAndView mav = new ModelAndView("wifi/webWifi_Success");
		mav.addObject("map", map);
		LOG.info("--connectWifiAddMember网页版加入会员总...耗时::" + (System.currentTimeMillis() - timeStart) + "毫秒");
		return mav;
	}

	// 清空今天以前的数据
	@RequestMapping(value = "clearData", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object clearData() {
		boolean success = merchantWifiService.clearData();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", success);
		return map;
	}
	
	@RequestMapping(value = "pushPage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView pushPage(Integer brandId, String weixinId) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		if (business == null) {
			business = new BusinessVO();
			business.setBrandId(brandId);
		}
		ModelAndView mav = null;
		if (brandId.intValue() == Constant.WIFI_PUSH_PAGE_BRAND) {
			mav = new ModelAndView("wifi/w_push_member_new");
//			mav = new ModelAndView("wifi/w_push_member"); 
		} else {
//			mav = new ModelAndView("wifi/w_test_push_member_new");
			mav = new ModelAndView("wifi/card_recommend");
			
			List<Integer> cardTypeIdList = settingCardRecommendService.getSettingCardByBrandId(brandId); // 微信后台设置的卡类型id
//			// 调用crm接口根据cardTypeId取卡相关信息（图片和卡描述）
//			List<Integer> cardTypeIdList = merchantWifiService.getCardTypeIdsByMerchantId(brandId);
			// 推荐页卡信息
			List<CardTypeVo> cardTypeList = cardService.getCardType(cardTypeIdList);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hasImage",weixinPhonePageService.hasImage(brandId,business.getSmallimageName()));
			mav.addObject("cardTypeList", cardTypeList);
			mav.addObject("map", map);
		}
		mav.addObject("weixinId", weixinId);
		mav.addObject("business", business);
		mav.addObject("pictureUrl", dfsFilePath);
		return mav;
	}
	
	
	@RequestMapping(value = "weixinWifiConnect", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView weixinWifiConnect(@RequestParam(value="code", required=false) String code, 
			@RequestParam(value="brandId", required=false)Integer brandId, HttpServletRequest request) {
		if (StringUtil.isNullOrEmpty(code)) { // code即账号未交300钱 
			LOG.info("code值：" + code);
			ModelAndView mav = new ModelAndView("/wifi/wxWifi_money_connect");
			return mav;
		}
		String weixinId = merchantWifiService.getWeixinId(code, brandId);
		if (StringUtil.isNullOrEmpty(weixinId)) {
			ModelAndView mav = new ModelAndView("/wifi/wxWifi_money_connect");
			return mav;
		}
		// 获取会员信息
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
		ModelAndView mav = new ModelAndView("/wifi/wxWifi_connect");
		if (member ==null) {
			mav.addObject("isMember", false);
		} else {
			mav.addObject("isMember", member.getIsMember());
			if (member.getIsMember()) { // 是会员
				boolean isPush = false;
				// 是否下发推送页
				boolean pushPage = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_PAGE_TYPE_VALUE, brandId);
				// 是否下发短信
				boolean pushSms = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_SMS_TYPE_VALUE, brandId);
				LOG.info("商户id："+brandId+";是否设置门店wifi下发推送页："+pushPage+";是否设置门店wifi下发短信："+pushSms);
				if (pushPage) {
					// 是否推送页
					isPush = merchantWifiService.judgeRedirectPage(brandId, member.getMembershipId());
				}
				if (pushSms) {
					String smsUrl = combineSmsUrl(brandId, request, weixinId, isPush);
					LOG.info("短信链接地址：" + smsUrl);
					//下发短信
					merchantWifiService.dealSms(member.getPhoneNo(), brandId, member.getMembershipId(), smsUrl);
				}
				LOG.info("商户id："+brandId+"；是否进推送页：" + isPush);
				mav.addObject("isPush", isPush);
			}
		}
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		mav.addObject("business", business);
		mav.addObject("weixinId", weixinId);
		mav.addObject("brandId", brandId);
		return mav;
	}
	
//	// 判断只有连接餐厅的网络，才会有推送页和短信
//	@RequestMapping(value = "connectAfterWifi", method = { RequestMethod.POST, RequestMethod.GET })
//	@ResponseBody
//	public ModelAndView connectAfterWifi(Integer brandId, String weixinId, @RequestParam(value="", required=false)String isCanWifi,
//			HttpServletRequest request) {
//		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
//		
//		if (isCanWifi.equals("true")) { // 连接的是当前餐厅wifi
//			boolean isPush = false;
//			// 是否下发推送页
//			boolean pushPage = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_PAGE_TYPE_VALUE, brandId);
//			// 是否下发短信
//			boolean pushSms = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_SMS_TYPE_VALUE, brandId);
//			LOG.info("商户id："+brandId+";是否设置门店wifi下发推送页："+pushPage+";是否设置门店wifi下发短信："+pushSms);
//			if (pushPage) {// 是否推送页
//				isPush = merchantWifiService.judgeRedirectPage(brandId, member.getMembershipId());
//			}
//			if (pushSms) {
//				String smsUrl = combineSmsUrl(brandId, request, weixinId, isPush);
//				LOG.info("短信链接地址：" + smsUrl);
//				//下发短信
//				merchantWifiService.dealSms(member.getPhoneNo(), brandId, member.getMembershipId(), smsUrl);
//			}
//			LOG.info("商户id："+brandId+"；是否进推送页：" + isPush);
//			
//			if (isPush) { // 下发推送页
//				ModelAndView mav = null;
//				if (brandId.intValue() == Constant.WIFI_PUSH_PAGE_BRAND) {
//					mav = new ModelAndView("wifi/w_push_member"); 
//				} else {
//					mav = new ModelAndView("wifi/w_test_push_member");
//				}
//				BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
//				mav.addObject("brandId", brandId);
//				mav.addObject("weixinId", weixinId);
//				mav.addObject("business", business);
//				mav.addObject("hasImage", business!=null ? weixinPhonePageService.hasImage(brandId, business.getSmallimageName()):"");
//				return mav;
//			} else {// 进会员中心页面
//				ModelAndView mav = new ModelAndView("wifi/connect_success");
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("flag", 1);
//				map.put("brandId", brandId);
//				map.put("weixinId", weixinId);
//				mav.addObject("map", map);
//				return mav;
//			}
//		} else { // 进会员中心页面
//			ModelAndView mav = new ModelAndView("wifi/connect_success");
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("flag", 1);
//			map.put("brandId", brandId);
//			map.put("weixinId", weixinId);
//			mav.addObject("map", map);
//			return mav;
//		}
//	}
	

	private String combineSmsUrl(Integer brandId, HttpServletRequest request,
			String weixinId, boolean isPush) {
		String smsUrl = "";
		if (isPush) {
			smsUrl = serverIp + request.getContextPath() + "/merchantWifi/pushPage.do?brandId="+brandId+"&weixinId="+weixinId;
		} else {
			smsUrl = serverIp+ request.getContextPath() + "/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+weixinId;
		}
		return smsUrl;
	}
	
	// 浏览器(提供给浩博调用接口)
	@RequestMapping(value = "addMemberByWifi", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView addMemberByWifi(@RequestParam(value = "brandId", required = true) Integer brandId,
			@RequestParam(value = "phoneNo", required = true) String phoneNo, HttpServletRequest request) {
		long timeStart = System.currentTimeMillis();
		Map<String, Object> map = merchantWifiService.connectWifiAddMember(brandId, phoneNo, request.getContextPath());
		ModelAndView mav = new ModelAndView("wifi/webWifi_Success_toMember");
		mav.addObject("map", map);
		LOG.info("--connectWifiAddMember网页版加入会员总...耗时::" + (System.currentTimeMillis() - timeStart) + "毫秒");
		return mav;
	}
	
	// 20141230 最新接口 浏览器(提供给浩博调用接口)
	@RequestMapping(value = "visitMemberByWifi", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView visitMemberByWifi(@RequestParam(value="param")String param) {
		ModelAndView mav = null;
		// wifi业务
		Map<String, Object> resultMap = merchantWifiService.visitMemberByWifi(param);
		LOG.info("----调用visitMemberByWifi接口返回的业务结果值："+resultMap.get("flag"));
		if(resultMap.get("flag").toString().equals("0")) { // 服务异常
			mav = new ModelAndView("wifi/connect_to_member"); // 提示服务器异常页
			mav.addObject("map", resultMap);
			return mav;
		}
		String settingAdvertSource = String.valueOf(resultMap.get("settingAdvertSource"));
		int settingSource = Integer.parseInt(settingAdvertSource);
		if(!StringUtil.isNullOrEmpty(settingAdvertSource)){
			Integer brandId = Integer.parseInt(resultMap.get("brandId")+"");
			
			if (settingSource == Constant.ADVERT_SOURCE_CARD) { // 说明wifi后台设置的卡推荐页
				mav = new ModelAndView("wifi/card_recommend");
				// 调用crm接口根据cardTypeId取卡相关信息（图片和卡描述）
				List<Integer> cardTypeIdList = merchantWifiService.getCardTypeIdsByMerchantId(brandId);
				// 推荐页卡信息
				List<CardTypeVo> cardTypeList = cardService.getCardType(cardTypeIdList);
				mav.addObject("cardTypeList", cardTypeList);
				mav.addObject("pictureUrl", dfsFilePath);
			} else if (settingSource == Constant.ADVERT_SOURCE_CUSTOM) { //  说明wifi后台设置的自定义广告
				mav = new ModelAndView("wifi/custom_advter");
				// 调用wifi那边接口取自定义广告信息
				String result = merchantWifiService.getCustomAdvert(brandId);
				mav.addObject("customAdvter", result);
			} else { // 进会员中心
				mav = new ModelAndView("wifi/connect_to_member");
			}
			mav.addObject("map", resultMap);
		}
		mav.addObject("isCrmMemberUser", resultMap.get("isCrmMemberUser")); // 是否为crm会员
		mav.addObject("dataSource", Constant.MEMBERSOURCE_14); // WIFI网页来源
		mav.addObject("business", resultMap.get("business"));
		mav.addObject("weixinId", resultMap.get("weixin_id"));
		return mav;
	}
	
	// wifi设置卡推荐页，同时微信也开启,关闭的时候以微信为主
	@RequestMapping(value = "saveMemberRightSetting", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object saveMemberRightSetting(@RequestParam(value="param")String param) {
		return resourceManagerService.operateMemberRight(param);
	}
}
