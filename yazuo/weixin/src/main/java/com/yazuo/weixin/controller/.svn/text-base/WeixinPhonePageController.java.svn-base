package com.yazuo.weixin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.card.service.CardService;
import com.yazuo.weixin.es.service.MemberShipCenterService;
import com.yazuo.weixin.es.vo.BrandVO;
import com.yazuo.weixin.es.vo.CardTypeVo;
import com.yazuo.weixin.member.service.MemberBirthManagerService;
import com.yazuo.weixin.member.service.MerchantWifiService;
import com.yazuo.weixin.member.service.ResourceManagerService;
import com.yazuo.weixin.member.vo.MembershipBirthdayFamily;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.service.ConsumerInterfaceService;
import com.yazuo.weixin.service.RegisterPageService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.Constants;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.ActivityRecordVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.CouponVO2;
import com.yazuo.weixin.vo.MemberCardVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.PreferenceVO;

@Controller
@RequestMapping("/weixin/phonePage")
public class WeixinPhonePageController {

	@Value("#{propertiesReader['getValidLotteryCountPath']}")
	private String getValidLotteryCountPath; // 在线办卡送一次抽奖机会路径
	
	@Autowired
	private WeixinPhonePageService weixinPhonePageService;
	@Autowired
	private WeixinManageService weixinManageService;
	@Autowired
	private ConsumerInterfaceService consumerInterfaceService;
	@Autowired
	private RegisterPageService registerPageService;
	@Resource
	private ResourceManagerService resourceManagerService;
	@Resource
	private CardService cardService;
	@Resource
	private MemberShipCenterService memberShipCenterService;
	@Resource
	private WeixinMallMartService weixinMallMartService;
	@Resource
	private MemberBirthManagerService memberBirthManagerService;
	@Resource
	private MerchantWifiService merchantWifiService;
	
	Logger log = Logger.getLogger(this.getClass());

	private static final Log LOGS = LogFactory.getLog("statistical");
	@Value("#{propertiesReader['getMemAndCardMobileAndmerchantId']}")
	private String getMemAndCardMobileAndmerchantIdUrl;
	@Value("#{propertiesReader['getCouponByCardNoAndMerchantId']}")
	private String getCouponByCardNoAndMerchantIdUrl;
	@Value("#{propertiesReader['imageFileUrl']}")
	private String imageFileUrl;
	
	@Value("#{propertiesReader['dfsFilePath']}")
	private String dfsFilePath; // 图片服务器地址
	
	
	@RequestMapping(value = "dishes", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView dishes(
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		ModelAndView mav = new ModelAndView("w_dishes");
		mav.addObject("business", business);
		mav.addObject("weixinId", weixinId);
		mav.addObject(
				"hasImage",
				weixinPhonePageService.hasImage(brandId,
						business.getSmallimageName()));
		return mav;
	}

	@RequestMapping(value = "preference", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView preference(
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId, @RequestParam(value="brandName", required=false)String brandName) {
		ModelAndView mav = new ModelAndView("/member/w_preference_new");
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		if (business == null) {
			business = new BusinessVO();
			business.setTitle(brandName);
			business.setBrandId(brandId);
		}
		mav.addObject("business", business);
		mav.addObject("weixinId", weixinId);
		mav.addObject(
				"hasImage",
				weixinPhonePageService.hasImage(brandId,
						business.getSmallimageName()));
		return mav;

	}

	@RequestMapping(value = "preferenceDetail", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView viewBusiness(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "weixinId") String weixinId, @RequestParam(value="brandName", required=false)String brandName) {
		// String s2 = request.getHeader("User-Agent"); 获取浏览器信息
		PreferenceVO preference = weixinPhonePageService.getPreferenceById(id);
		BusinessVO business = weixinManageService
				.getBusinessByBrandId(preference.getBrandId());
		if (business == null) {
			business = new BusinessVO();
			business.setTitle(brandName);
			business.setBrandId(preference.getBrandId());
		}
		ModelAndView mav = new ModelAndView("/member/w_preferenceDetail_new");
		mav.addObject("business", business);
		mav.addObject("preference", preference);
		String title = business.getTitle();
		mav.addObject("title", title);
		mav.addObject("weixinId", weixinId);
		mav.addObject("hasImage", weixinPhonePageService.hasImage(
				preference.getBrandId(), business.getSmallimageName()));
		return mav;

	}

	@RequestMapping(value = "subbranch", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView subbranch(
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		ModelAndView mav = new ModelAndView("w_subbranch");
		mav.addObject("business", business);
		mav.addObject("weixinId", weixinId);
		mav.addObject(
				"hasImage",
				weixinPhonePageService.hasImage(brandId,
						business.getSmallimageName()));
		return mav;
	}

	@RequestMapping(value = "getLocation", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView getLocation(
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId) {

		ModelAndView mav = new ModelAndView("w_getLocation");
		mav.addObject("weixinId", weixinId);
		mav.addObject("brandId", brandId);
		return mav;
	}

	@RequestMapping(value = "getImage", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void getImage(HttpServletResponse response,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "name") String name) {

		FileImageInputStream fiis = weixinPhonePageService.getImageStream(
				brandId, name);
		response.setHeader("Content-Length", fiis.length() + "");
		response.setHeader("Content-Type", "image/jpeg");
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			int c;
			byte buffer[] = new byte[1024];
			while ((c = fiis.read(buffer)) != -1) {
				for (int i = 0; i < c; i++)
					os.write(buffer[i]);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (fiis != null) {
					fiis.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	
	/**
	 * 会员页面，add by sundongfeng 2014-08-13
	 * @throws Exception 
	 */
	@RequestMapping(value = "membershipCoupon", method = { RequestMethod.POST, RequestMethod.GET })
	public String membershipCoupon(HttpServletRequest request,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "cardNo") String cardNo,
			@RequestParam(value = "mobile") String mobile,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "cardType") String cardType,
			@RequestParam(value = "storeBalance") String storeBalance,
			@RequestParam(value = "integralAvailable") String integralAvailable,
			@RequestParam(value = "pwdIf") Boolean pwdIf,
			@RequestParam(value = "icon") String icon,
			Model model
			) throws Exception {
		log.info(storeBalance+","+integralAvailable);
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
		BrandVO brandvo = cardService.getMerchant(brandId);//查询brandId
		List<CouponVO2> cardCouponList = new ArrayList<CouponVO2>();
		List<CouponVO2> phoneCouponList = new ArrayList<CouponVO2>();
		
		Map<String,List<CouponVO2>> couponList=memberShipCenterService.getCardCouponList(brandvo.getBrandId().toString(), cardNo, mobile);
		cardCouponList=couponList.get("card");
		phoneCouponList=couponList.get("mobile");
		
		model.addAttribute("phoneCouponList", phoneCouponList);
		model.addAttribute("cardCouponList", cardCouponList);
		model.addAttribute("business", business);
		model.addAttribute("hasImage",weixinPhonePageService.hasImage(brandId,business.getSmallimageName()));
		model.addAttribute("weixinId", weixinId);
		model.addAttribute("member", member);
		model.addAttribute("cardType", cardType);
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("mobile", mobile);
		model.addAttribute("brandId", brandId);
		model.addAttribute("icon", icon);
		model.addAttribute("storeBalance", storeBalance);
		model.addAttribute("integralAvailable", integralAvailable);
		model.addAttribute("pwdIf", pwdIf);
		model.addAttribute("imageFileUrl", imageFileUrl);
		model.addAttribute("pictureUrl", dfsFilePath);
		// 判断是否下发推送页
		boolean existsPushPage = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_PAGE_TYPE_VALUE, brandId);
		log.info(existsPushPage);
		model.addAttribute("existsPushPage", existsPushPage);
		model.addAttribute("pageSource","singleCard");
//		return "w_membershipCard_new";
		return "/member/w_membershipCard_new";
	}
	
		/**
	 * 加入会员，绑定实体卡后，跳转会员页面 ，注意和上面的方法
	 * @throws Exception 
	 */
	@RequestMapping(value = "membershipCenter", method = { RequestMethod.POST, RequestMethod.GET })
	public String membershipCenter(HttpServletRequest request,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "mobile") String mobile,
			@RequestParam(value = "cardNo") String cardNo,
			@RequestParam(value = "cardtypeId") String cardtypeId,
			@RequestParam(value = "storeBalance") String storeBalance,
			@RequestParam(value = "integralAvailable") String integralAvailable,
			@RequestParam(value = "brandName",required=false)String brandName,
			@RequestParam(value="dataSource",required=false)Integer dataSource, 
			@RequestParam(value="couponIdStr", required=false)String couponIdStr, 
			@RequestParam(value="isCrmMemberUser", required=false)String isCrmMemberUser,
			Model model) throws Exception {
		log.info(storeBalance+","+integralAvailable);
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		boolean hasImage = false;
		if (business == null) { // 是wifi来源且此商户在微信数据库bussiness表中没有
			business = new BusinessVO();
			business.setBrandId(brandId);
			business.setTitle(brandName);
		} else {
			hasImage = weixinPhonePageService.hasImage(brandId,business.getSmallimageName());
		}
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
		CardTypeVo cardTypevo =null;
		if(!StringUtil.isNullOrEmpty(cardtypeId)){
			cardTypevo = cardService.getCardType(Integer.parseInt(cardtypeId));
		}
		BrandVO brandvo = cardService.getMerchant(brandId);//查询brandId
		List<CouponVO2> cardCouponList = new ArrayList<CouponVO2>();
		List<CouponVO2> phoneCouponList = new ArrayList<CouponVO2>();
		
		Map<String,List<CouponVO2>> couponList=memberShipCenterService.getCardCouponList(brandvo.getBrandId().toString(), cardNo, mobile);
		cardCouponList=couponList.get("card");
		phoneCouponList=couponList.get("mobile");
		
		model.addAttribute("phoneCouponList", phoneCouponList);
		model.addAttribute("cardCouponList", cardCouponList);
		model.addAttribute("business", business);
		model.addAttribute("hasImage", hasImage);
		model.addAttribute("weixinId", weixinId);
		model.addAttribute("member", member);
		if(cardTypevo!=null){
			model.addAttribute("cardType", cardTypevo.getCardtype());
			model.addAttribute("icon", StringUtil.isNullOrEmpty(cardTypevo.getCardtypeIcon())? "" : imageFileUrl+cardTypevo.getCardtypeIcon());
			model.addAttribute("pwdIf", cardTypevo.getIsTradePassword());
		}else{
			model.addAttribute("cardType", "");
			model.addAttribute("icon", "");
			model.addAttribute("pwdIf", false);
		}
		model.addAttribute("mobile", mobile);
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("brandId", brandId);
		model.addAttribute("storeBalance", storeBalance);
		model.addAttribute("integralAvailable", integralAvailable);
		model.addAttribute("imageFileUrl", imageFileUrl);
		// 判断是否下发推送页
		boolean existsPushPage = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_PAGE_TYPE_VALUE, brandId);
		log.info(existsPushPage);
		// 配置的导航菜单
		List<Map<String, Object>> resourceList = resourceManagerService.getResourceOfBrandId(brandId, Constants.RESOURCE_SOURCE_2+"");
		// 判断该商户是否有家人生日此功能
		boolean existsMemberBirth = resourceManagerService.judgeExistsResourceOfBrand(Constant.MEMBER_BIRTH_TYPE_VALUE, brandId);
		log.info("[brandId="+brandId+"]是否有家人生日："+existsMemberBirth);
		List<MembershipBirthdayFamily> fmailyList = null;
		if (existsMemberBirth) {
			// 取家人生日个数
			fmailyList = memberBirthManagerService.getMemberFamilyBirth(member.getMembershipId(), member.getBrandId());
		}
		// 从wifi网页进入，即来源为14,且添加会员有送券
		JSONArray couponArray = null;
		if (dataSource !=null && dataSource.intValue() == Constant.MEMBERSOURCE_14 && !StringUtil.isNullOrEmpty(couponIdStr)) {
			couponArray = merchantWifiService.getCoupons(couponIdStr, brandId);
		}
		model.addAttribute("existsPushPage", existsPushPage);
		model.addAttribute("resourceList", resourceList);
		model.addAttribute("existsMemberBirth", existsMemberBirth);
		model.addAttribute("familyCount", fmailyList!=null ? fmailyList.size():0);
		//--------wifi需要用的参数-begain-----------
		model.addAttribute("isCrmMemberUser", isCrmMemberUser);
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("couponArray", couponArray);
		
		model.addAttribute("pictureUrl", dfsFilePath);
		return "/member/w_membershipCard_new";
	}
	
	/**
	 * 添加了优惠券描述功能，修改日期2014-01-02
	 * @throws Exception 
	 */
	@RequestMapping(value = "membershipCard", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView membershipCard(HttpServletRequest request,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "weixinId", required = false) String weixinId, @RequestParam(value="code", required=false) String code) throws Exception {
		if (StringUtil.isNullOrEmpty(weixinId)) { // 微信id为空
			if (!StringUtil.isNullOrEmpty(code)) { // code不为空即账号交300钱
				weixinId = merchantWifiService.getWeixinId(code, brandId);
			} else {
				ModelAndView mav = new ModelAndView("/lottery/error");
				mav.addObject("error", "服务异常");
				return mav;
			}
		}
		return toMemberInfoPage(brandId, weixinId, null, null, null, null, null);
	}

	@RequestMapping(value = "sendIdentifyingCode", method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object sendIdentifyingCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "phoneNo") String phoneNo,
			@RequestParam(value = "title") String title) {
		// String s2 = request.getHeader("User-Agent"); // 获取浏览器信息summaryDetail
		// if (s2.indexOf("Windows") >= 0) {
		// ModelAndView mav = new ModelAndView("error");
		// return mav;
		// }
		String sendIdentifyingCode = consumerInterfaceService
				.sendIdentifyingCode(phoneNo, brandId, weixinId, title);
		try {
			if (sendIdentifyingCode != null) {
				if (sendIdentifyingCode.equals("-1")) {
					response.getWriter().print("-1");
					return null;
				}
				response.getWriter().print("1");
				return null;
			} else {
				response.getWriter().print("0");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * update 2013-12-06 保存会员去掉验证码
	 * 
	 * @param request
	 * @param response
	 * @param brandId
	 * @param weixinId
	 * @param phoneNo
	 * @return
	 */
	@RequestMapping(value = "identifyingAndRegister", method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView identifyingAndRegister(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "phoneNo") String phoneNo,
			@RequestParam(value = "identifyingCode") String identifyingCode) {
		// String s2 = request.getHeader("User-Agent"); // 获取浏览器信息summaryDetail
		// if (s2.indexOf("Windows") >= 0) {
		// ModelAndView mav = new ModelAndView("error");
		// return mav;
		// }
		// String info = consumerInterfaceService.register(weixinId, brandId,
		// phoneNo);
		String info = consumerInterfaceService.identifyingAndRegister(weixinId,
				brandId, identifyingCode);
		if (info == null) {
			info = "3";
		}
		info = info.split("\n")[0];
		if (info.endsWith("已成为本店尊贵会员~")) {
			info = "1";
		} else if (info.endsWith("抱歉，微信端暂时无法注册会员，请到门店办理")) {
			info = "2";
		}
		try {
			response.getWriter().print(info);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "modifyActivityRecordStatus", method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView modifyActivityRecordStatus(HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "id") Integer id) {
		// String s2 = request.getHeader("User-Agent");
		// log.info(s2);
		ActivityRecordVO activityRecord = new ActivityRecordVO();
		activityRecord.setId(id);
		activityRecord
				.setOperateTime(new Timestamp(System.currentTimeMillis()));
		activityRecord.setIsDone(true);
		weixinPhonePageService.modifyActivityRecordStatus(activityRecord);
		try {
			response.getWriter().print("1");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "story", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView story(@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		ModelAndView mav = new ModelAndView("w_story");
		mav.addObject("business", business);
		mav.addObject("weixinId", weixinId);
		mav.addObject(
				"hasImage",
				weixinPhonePageService.hasImage(brandId,
						business.getSmallimageName()));
		return mav;
	}

	@RequestMapping(value = "modifyMemberInfo", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView modifyMemberInfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "birthday") String birthday,
			@RequestParam(value = "birthType") String birthType) {
		// String s2 = request.getHeader("User-Agent"); // 获取浏览器信息summaryDetail
		// if (s2.indexOf("Windows") >= 0) {
		// ModelAndView mav = new ModelAndView("error");
		// return mav;
		// }
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
				brandId, weixinId);
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		if (member == null || business == null) {
			try {
				response.getWriter().print("0");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		member.setBirthType(birthType);//add by SunDongfeng 2014-07-11
		String info = registerPageService.modifyMemberInfo(birthday,
				member, business);
		if (info == null) {
			info = "0";
		}
		if("noMembership".equals(info)){
			info = "3";
		}else if("error".equals(info)||"modifyFailure".equals(info)){
			info = "2";
		}else if ("parseBirError".equals(info)) {
			info = "4";
		} else if("success".equals(info)){
			info = "5";
		}
		try {
			response.getWriter().print(info);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// -----------------------用户注册基本信息--------------------------
	@RequestMapping(value = "registerPage", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView registerPage(HttpServletRequest request,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "source",required=false) Integer source) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
		if (member == null) {
			member = new MemberVO();
			member.setBrandId(brandId);
			member.setWeixinId(weixinId);
			member.setIsMember(false);
			member.setDataSource(source);
			weixinPhonePageService.insertMember(member);
			member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
		}else{
			member.setDataSource(source);
		}
		if (!member.getIsMember()) {// 新用户注册页面
			// 商户设置是否发送验证码注册会员
			boolean isAllowWeixinMember = registerPageService.isAllowWeixinMember(brandId);
			/*if(isAllowWeixinMember){*///发送验证码
			ModelAndView mav = new ModelAndView("/member/w-registerInfo");
			mav.addObject("business", business);
			mav.addObject("weixinId", weixinId);
			mav.addObject("member", member);
			mav.addObject("isAllowWeixinMember",isAllowWeixinMember);
			return mav;
		/*}else{//不发送验证码
			ModelAndView mav = new ModelAndView("w-registerInfoWithoutCode");
			mav.addObject("business", business);
			mav.addObject("weixinId", weixinId);
			mav.addObject("member", member);
			return mav;	
		}*/
			
		} else {
			ModelAndView mav = new ModelAndView("w-registerSuccess");// 已经是会员了跳转
			mav.addObject("business", business);
			mav.addObject("weixinId", weixinId);
			mav.addObject("member", member);
			return mav;
		}
	}
	
	// -----------------------绑定会员卡--------------------------
	@RequestMapping(value = "cardBoundPage", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView cardBoundPage(HttpServletRequest request,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
				brandId, weixinId);
		if (member == null) {
			member = new MemberVO();
			member.setBrandId(brandId);
			member.setWeixinId(weixinId);
			member.setIsMember(false);
			weixinPhonePageService.insertMember(member);
			member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
					brandId, weixinId);
		}
			// 商户设置是否发送验证码注册会员
			boolean isAllowWeixinMember = registerPageService.isAllowWeixinMember(brandId);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
			SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
			Date birthday = member.getBirthday();
			String year = "";
			String month="";
			String day = "";
			if(birthday!=null){
				year = sdf1.format(birthday);
				month = sdf2.format(birthday);
				day = sdf3.format(birthday);
			}
			/*if(isAllowWeixinMember){*///发送验证码
			ModelAndView mav = new ModelAndView("/member/cardBound");
			mav.addObject("business", business);
			mav.addObject("weixinId", weixinId);
			mav.addObject("member", member);
			mav.addObject("year", year);
			mav.addObject("month", month);
			mav.addObject("day", day);
			mav.addObject("isAllowWeixinMember", isAllowWeixinMember);
			return mav;			
		/*}else{//不发送验证码
			ModelAndView mav = new ModelAndView("cardBoundWithoutCode");
			mav.addObject("business", business);
			mav.addObject("weixinId", weixinId);
			mav.addObject("member", member);
			mav.addObject("year", year);
			mav.addObject("month", month);
			mav.addObject("day", day);
			return mav;
		}*/
	}

	// 完善会员资料
	@RequestMapping(value = "improveMembership", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView improveMembership(HttpServletRequest request,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
				brandId, weixinId);
		if (member == null) {
			member = new MemberVO();
			member.setBrandId(brandId);
			member.setWeixinId(weixinId);
			member.setIsMember(false);
			weixinPhonePageService.insertMember(member);
			member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
					brandId, weixinId);
		}
		if (!member.getIsMember()) {// 新用户注册页面
			
			// update by gaoshan 2014-06-24
			// 商户设置是否发送验证码注册会员
			boolean isAllowWeixinMember = registerPageService.isAllowWeixinMember(brandId);
//			if(isAllowWeixinMember){//发送验证码
				ModelAndView mav = new ModelAndView("/member/w-registerInfo");
//				ModelAndView mav = new ModelAndView("w-registerInfo");
				mav.addObject("business", business);
				mav.addObject("weixinId", weixinId);
				mav.addObject("member", member);
				mav.addObject("isAllowWeixinMember",isAllowWeixinMember);
				return mav;
//			}else{//不发送验证码
//				ModelAndView mav = new ModelAndView("/member/w-registerInfoWithoutCode");
////				ModelAndView mav = new ModelAndView("w-registerInfoWithoutCode");
//				mav.addObject("business", business);
//				mav.addObject("weixinId", weixinId);
//				mav.addObject("member", member);
//				return mav;	
//			}
			
		} else if (member.getIsMember() && member.getBirthday() == null
				|| member.getIsMember() && member.getName() == null
				|| member.getIsMember() && member.getName() != null
				&& member.getName().equals("") || member.getIsMember()
				&& member.getGender() == null || member.getIsMember()
				&& member.getPhoneNo() == null || member.getIsMember()
				&& member.getPhoneNo() != null
				&& !isMobileNO(member.getPhoneNo())) {

			// 生日姓名性别未填写已经是会员了跳转
			// update by gaoshan 2014-06-24
			// 商户设置是否发送验证码注册会员
			boolean isAllowWeixinMember = registerPageService.isAllowWeixinMember(brandId);
//			if(isAllowWeixinMember){//发送验证码
				ModelAndView mav = new ModelAndView("/member/w-registerInfo");
//				ModelAndView mav = new ModelAndView("w-registerInfo");
				mav.addObject("business", business);
				mav.addObject("weixinId", weixinId);
				mav.addObject("member", member);
				mav.addObject("isAllowWeixinMember",isAllowWeixinMember);
				return mav;
//			}else{//不发送验证码
//				ModelAndView mav = new ModelAndView("/member/w-registerInfoWithoutCode");
////				ModelAndView mav = new ModelAndView("w-registerInfoWithoutCode");
//				mav.addObject("business", business);
//				mav.addObject("weixinId", weixinId);
//				mav.addObject("member", member);
//				return mav;	
//			}
		
		} else {
			ModelAndView mav = new ModelAndView("w-registerSuccess");
			mav.addObject("business", business);
			mav.addObject("weixinId", weixinId);
			mav.addObject("member", member);
			return mav;
		}
	}

	/**
	 * update:2013-12-05 保存用户资料(去掉验证码验证) 
	 * update:2014-05-21 添加验证码功能（原有去掉部分补回来）
	 * 
	 * @param request
	 * @param response
	 * @param brandId
	 * @param weixinId
	 * @param phoneNo
	 * @param gender
	 * @param name
	 * @param birthday
	 * @return
	 */
	@RequestMapping(value = "saveUser", method = { RequestMethod.POST,
			RequestMethod.GET })
	// 保存用户
	public void saveUser(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "phoneNo") String phoneNo,
			@RequestParam(value = "gender") Integer gender,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "birthday") String birthday,
			@RequestParam(value = "birthType",required=false) String birthType,
			@RequestParam(value = "identifyCode",required = false) String identifyCode,
			@RequestParam(value = "source",required = false) Integer source) {
		log.info("会员注册时数据:" + "brandId:" + brandId + ",weixinId:" + weixinId
				+ ",phoneNo:" + phoneNo + ",gender:" + gender + ",name:" + name
				+ ",birthday:" + birthday + ",identifyCode" + identifyCode+",birthType:"+birthType);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		JSONObject tempJson = new JSONObject();
		if (member == null || business == null) {
			try {
				tempJson.put("status", "0");
				response.getWriter().print(tempJson.toString()); //
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ;
		}
		
		Integer memberType=member.getMemberType();
		if(memberType!=null && 2 == memberType.intValue()){
			try {
				tempJson.put("status", "4");
				response.getWriter().print(tempJson.toString());//实体卡类型，提示已经是粉丝会员
				return ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (name == null || name != null && name.equals("") || phoneNo == null
				|| phoneNo != null && phoneNo.equals("") || phoneNo != null
				&& !isNum(phoneNo) || phoneNo != null && !isMobileNO(phoneNo)
				|| gender == null || gender != null
				&& !isNum(String.valueOf(gender.intValue()))) {
			try {
				tempJson.put("status", "3");
				response.getWriter().print(tempJson.toString());// 参数不符合格式
				return ;
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}

		}
		member.setMemberType(1);//-1-初始态，1-粉丝卡，2-实体卡
		member.setName(name);
		member.setPhoneNo(phoneNo);
		member.setGender(gender);
		member.setDataSource(source);//2014-10-19 add sundf
		if (birthday != null && !birthday.trim().equals("")) {// 生日不为空
			try {
				member.setBirthday(sdf.parse(birthday.trim()));
				member.setBirthType(birthType);
			} catch (ParseException e) {
				try {
					tempJson.put("status", "1");
					response.getWriter().print(tempJson);// 生日格式错误
					return ;
				} catch (IOException e1) {
					log.error(e1.getMessage());
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		}
		
		String info = null;
		if(identifyCode != null && identifyCode.trim().length() > 0){//验证码注册
			info = registerPageService.identifyingAndRegister(member,identifyCode, business);
			// identifyError success 
			// error identifyExpire registerError NoIdentify
		}else{//不通过验证码注册
			info = registerPageService.identifyingAndRegisterWithoutCode(member, phoneNo, business);
		}
		log.info("注册会员返回:"+info);
		JSONObject jsonObj = JSONObject.fromObject(info);
		try {
			response.getWriter().print(jsonObj.toString());
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	// 修改用户信息
	@RequestMapping(value = "modifyUserInfo", method = { RequestMethod.POST,
			RequestMethod.GET })
	// 隐藏域返回修改用户的全部信息
	public ModelAndView modifyUserInfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "phoneNo") String phoneNo,
			@RequestParam(value = "gender") Integer gender,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "birthType") String birthType,
			@RequestParam(value = "birthday") String birthday) {
		log.info("会员修改信息数据:" + "brandId:" + brandId + ",weixinId:" + weixinId
				+ ",phoneNo:" + phoneNo + ",gender:" + gender + ",name:" + name
				+ ",birthday:" + birthday+",birthType:"+birthType);
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
				brandId, weixinId);
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		if (member == null) {
			try {
				response.getWriter().print("0");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		if(business == null){
			business =  new BusinessVO();
			business.setBrandId(brandId);
		}
		member.setPhoneNo(phoneNo);
		member.setGender(gender);
		member.setName(name);
		member.setBirthType(birthType);//
		String info = registerPageService.modifyMemberInfo(birthday.trim(),
				member, business); // success
									// error
									// noMembership
		try {
			response.getWriter().print(info);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "registerIdentifyingCode", method = {// 用户注册验证码
	RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object registerIdentifyingCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "phoneNo") String phoneNo,
			@RequestParam(value = "title") String title) {
		String sendIdentifyingCode = registerPageService.sendIdentifyingCode(
				phoneNo, brandId, weixinId, title);
		try {
			if (sendIdentifyingCode != null) {
				if (sendIdentifyingCode.equals("-1")) {// 商家不允许微信注册会员且没有实体卡,用户到门店办理
					response.getWriter().print("-1");
					return null;
				}
				response.getWriter().print(sendIdentifyingCode);// 成功返回验证码
				return null;
			} else {
				response.getWriter().print("0"); // 重新获取,手机发送验证码出现的异常
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 注册成功后跳转到这个页面
	 * 
	 * 添加了优惠券描述功能，修改日期2014-01-02
	 * 
	 */
	@RequestMapping(value = "fensiCard", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView fensiCard(HttpServletRequest request,@RequestParam(value = "brandId") Integer brandId,@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value="birthday", required=false)String birthday, @RequestParam(value="dataSource",required=false)Integer dataSource, 
			@RequestParam(value="couponIdStr", required=false)String couponIdStr, @RequestParam(value="isCrmMemberUser", required=false)String isCrmMemberUser,
			@RequestParam(value="brandName", required = false) String brandName) throws Exception{
		return toMemberInfoPage(brandId,weixinId, birthday, dataSource, couponIdStr, isCrmMemberUser, brandName);
	}
	
	/**
	 * 会员卡绑定
	 * @param request
	 * @param response
	 * @param name
	 * @param gender
	 * @param birthday
	 * @param cardNum
	 * @param secCode
	 * @param userPwd
	 * @param phoneNo
	 * @param identifyCode
	 * @param brandId
	 * @param weixinId
	 * @param random
	 * @return
	 */
	@RequestMapping(value = "cardBound", method = { RequestMethod.POST,
			RequestMethod.GET })
	public void cardBound(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "gender", required = true) Integer gender,
			@RequestParam(value = "birthday", required = false) String birthday,
			@RequestParam(value = "cardNum", required = true) String cardNum,
			@RequestParam(value = "secCode", required = true) String secCode,
			@RequestParam(value = "userPwd", required = true) String userPwd,
			@RequestParam(value = "phoneNo", required = true) String phoneNo,
			@RequestParam(value = "identifyCode", required = false) String identifyCode,
			@RequestParam(value = "brandId", required = true) Integer brandId,
			@RequestParam(value = "weixinId", required = true) String weixinId,
			@RequestParam(value = "birthType", required = false) Integer birthType,
			@RequestParam(value = "random", required = false) String random) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
				brandId, weixinId);
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		JSONObject tempJson = new JSONObject();
		if (member == null || business == null) {
			try {
				tempJson.put("cardBoundStatus", "0");
				response.getWriter().print(tempJson); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		if (name == null 
				|| name != null && name.equals("") 
				|| phoneNo == null
				|| phoneNo != null && phoneNo.equals("") 
				|| phoneNo != null && !isNum(phoneNo) 
				|| phoneNo != null && !isMobileNO(phoneNo)
				|| gender == null 
				|| gender != null && !isNum(String.valueOf(gender.intValue()))
				|| cardNum == null
				|| cardNum != null && cardNum.equals("")
				|| secCode == null
				|| secCode != null && secCode.equals("")) {
			try {
				tempJson.put("cardBoundStatus", "3");
				response.getWriter().print(tempJson);// 参数不符合格式
				return;
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		member.setMemberType(2);//-1-初始态，1-粉丝卡，2-实体卡
		member.setName(name);
		member.setPhoneNo(phoneNo);
		member.setGender(gender);
		member.setBirthType(birthType+"");
		if (birthday != null && !birthday.trim().equals("")) {// 生日不为空
			try {
				member.setBirthday(sdf.parse(birthday.trim()));
			} catch (ParseException e) {
				try {
					tempJson.put("cardBoundStatus", "1");
					response.getWriter().print(tempJson);// 生日格式错误
					return;
				} catch (IOException e1) {
					log.error(e1.getMessage());
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}

		Map<String, Object> params=new HashMap<String, Object>();
		if (cardNum.length()>16) { // 前16位是卡号
			cardNum = cardNum.substring(0, 16);
		}
		params.put("cardNum",cardNum );
		params.put("secCode",secCode );
		params.put("userPwd", userPwd);
		
		String backResult = null;
		if(identifyCode != null && identifyCode.trim().length() > 0){//验证码注册
			backResult = registerPageService.identifyingAndRegisterOfCardBound(member, identifyCode, params);
		}else{//不通过验证码注册
			backResult = registerPageService.identifyingAndRegisterOfCardBoundWithoutCode(member, phoneNo, params);
		}
		log.info("实体卡绑定返回:"+backResult);
		JSONObject jsonObj = JSONObject.fromObject(backResult);
		jsonObj.put("membershipId ", member.getMembershipId());
		try {
			response.getWriter().print(jsonObj.toString());
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "judgeNeedPwd", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public Object judgeNeedPwd(String cardNo, Integer brandId){
		return weixinPhonePageService.judgeNeedPwd(cardNo, brandId);
	}
	
	public ModelAndView toMemberInfoPage(Integer brandId,String weixinId, String birthday, Integer dataSource,
			String couponIdStr, String isCrmMemberUser, String brandName){
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId, false);
		boolean hasImage = false;
		if (business == null) { // 是wifi来源且此商户在微信数据库bussiness表中没有
			business = new BusinessVO();
			business.setBrandId(brandId);
			business.setTitle(brandName);
		} else {
			hasImage = weixinPhonePageService.hasImage(brandId,business.getSmallimageName());
		}
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
		if (member == null) {
			member = new MemberVO();
			member.setBrandId(brandId);
			member.setWeixinId(weixinId);
			member.setIsMember(false);
			weixinPhonePageService.insertMember(member);
		}
		if (!member.getIsMember()) {
			// 商户设置是否发送验证码注册会员
			boolean isAllowWeixinMember = registerPageService.isAllowWeixinMember(brandId);
				ModelAndView mav = new ModelAndView("/member/w-registerInfo");
				mav.addObject("business", business);
				mav.addObject("weixinId", weixinId);
				mav.addObject("isAllowWeixinMember",isAllowWeixinMember);
				mav.addObject("member", member);
				return mav;
		}

		// 加入统计日志
		if (brandId != null && !StringUtils.isBlank(weixinId)) {
			LOGS.info(";" + DateUtil.getDate4d() + ";" + brandId + ";"+ weixinId + ";1");
		}
		BrandVO brandvo = cardService.getMerchant(brandId);//查询brandId
		//此地可以从搜索引擎拿数据
		MemberVO crmMember=member!=null ?memberShipCenterService.queryMemberByMsId(brandvo.getBrandId().toString(), member.getMembershipId().toString()):null;
		if (crmMember!=null && !StringUtil.isNullOrEmpty(crmMember.getPhoneNo())) {
			member.setBirthday(crmMember.getBirthday());
			member.setBirthType(crmMember.getBirthType());
			member.setPhoneNo(crmMember.getPhoneNo());
		}
		if (!StringUtil.isNullOrEmpty(birthday)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date tBirthDay = null;
			try {
				tBirthDay = sdf.parse(birthday);
			} catch (ParseException e) {
				LOGS.info("时间转换错误"+e);
				e.printStackTrace();
			}
			member.setBirthday(tBirthDay);
		}
		//是会员，读取卡列表接口，2014-08-12 add by sundongfeng 
		List<MemberCardVO> cardList = new ArrayList<MemberCardVO>();
		//读取搜索引擎会员ids
		Set<Object> set = memberShipCenterService.queryMemberShipsByMobile(brandvo.getBrandId().toString(), member.getPhoneNo());
		//读取卡列表用搜索引擎
		cardList = memberShipCenterService.queryMemberCardList(brandvo.getBrandId().toString(), set.toArray());
		// 配置的导航菜单
		List<Map<String, Object>> resourceList = resourceManagerService.getResourceOfBrandId(brandId, Constants.RESOURCE_SOURCE_2+"");
		// 判断是否下发推送页
		boolean existsPushPage = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_PAGE_TYPE_VALUE, brandId);
		log.info("[brandId="+brandId+"]是否下发推送页："+existsPushPage);
		// 判断该商户是否有家人生日此功能
		boolean existsMemberBirth = resourceManagerService.judgeExistsResourceOfBrand(Constant.MEMBER_BIRTH_TYPE_VALUE, brandId);
		log.info("[brandId="+brandId+"]是否有家人生日："+existsMemberBirth);
		List<MembershipBirthdayFamily> fmailyList = null;
		if (existsMemberBirth) {
			// 取家人生日个数
			fmailyList = memberBirthManagerService.getMemberFamilyBirth(member.getMembershipId(), member.getBrandId());
		}
		// 从wifi网页进入，即来源为14,且添加会员有送券
		JSONArray couponArray = null;
		if (dataSource !=null && dataSource.intValue() == Constant.MEMBERSOURCE_14 && !StringUtil.isNullOrEmpty(couponIdStr)) {
			couponArray = merchantWifiService.getCoupons(couponIdStr, brandId);
		}
		if(cardList!=null&&cardList.size()==1){
			ModelAndView mav = new ModelAndView("/member/w_membershipCard_new");	
			MemberCardVO only = cardList.get(0);
			List<CouponVO2> cardCouponList = new ArrayList<CouponVO2>();
			List<CouponVO2> phoneCouponList = new ArrayList<CouponVO2>();
			
			Map<String,List<CouponVO2>> couponList=memberShipCenterService.getCardCouponList(brandvo.getBrandId().toString(), only.getCardNo(), member.getPhoneNo());
			cardCouponList=couponList.get("card");
			phoneCouponList=couponList.get("mobile");
			mav.addObject("phoneCouponList", phoneCouponList);
			mav.addObject("cardCouponList", cardCouponList);
			mav.addObject("business", business);
			mav.addObject("hasImage", hasImage);
			mav.addObject("weixinId", weixinId);
			mav.addObject("member", member);
			mav.addObject("mobile", member.getPhoneNo());
			mav.addObject("cardType", only.getCardtype());
			mav.addObject("cardNo", only.getCardNo());
			mav.addObject("brandId", brandId);
			mav.addObject("icon", only.getIcon());
			mav.addObject("storeBalance", only.getStoreBalance());
			mav.addObject("integralAvailable", only.getIntegralAvailable());
			mav.addObject("pwdIf", only.getIsTradePassword());
			mav.addObject("imageFileUrl", imageFileUrl);
			mav.addObject("existsPushPage", existsPushPage);
			mav.addObject("resourceList", resourceList);
			mav.addObject("existsMemberBirth", existsMemberBirth);
			mav.addObject("familyCount", fmailyList!=null ? fmailyList.size():0);
			//--------wifi需要用的参数-begain-----------
			mav.addObject("isCrmMemberUser", isCrmMemberUser);
			mav.addObject("dataSource", dataSource);
			mav.addObject("couponArray", couponArray);
			//----------end----------
			mav.addObject("pictureUrl", dfsFilePath);
			return mav;
	} else {
			ModelAndView mav = new ModelAndView("/member/w_membershipCardList");
			mav.addObject("business", business);
			mav.addObject("hasImage", hasImage);
			mav.addObject("cardList", cardList);
			mav.addObject("weixinId", weixinId);
			mav.addObject("member", member);
			mav.addObject("brandId", brandId);
			mav.addObject("existsPushPage", existsPushPage);
			mav.addObject("resourceList", resourceList);
			mav.addObject("existsMemberBirth", existsMemberBirth);
			mav.addObject("familyCount", fmailyList!=null ? fmailyList.size() : 0);
			//--------wifi需要用的参数-begain-----------
			mav.addObject("isCrmMemberUser", isCrmMemberUser);
			mav.addObject("dataSource", dataSource);
			mav.addObject("couponArray", couponArray);
			//----------end----------
			mav.addObject("pictureUrl", dfsFilePath);
			return mav;
		}
	}

	@RequestMapping(value = "getCouponDesc", method = { RequestMethod.POST,RequestMethod.GET })
	// 保存用户
	public void getCouponDesc(HttpServletResponse response,@RequestParam(value = "batchNo") Integer batchNo) throws Exception{
		String desc = cardService.getCouponDesc(batchNo);
		response.getWriter().print(desc);
		response.getWriter().close();
	}
	
	static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	static boolean isNum(String input) {

		Pattern p = Pattern.compile("^[0-9_]+$");
		Matcher m = p.matcher(input);
		return m.matches();
	}

	boolean isDate(String input) {
		if (!input.startsWith("19") && !input.startsWith("20")) {
			return false;
		}
		Pattern p = Pattern.compile("^[0-9_]+$");
		Matcher m = p.matcher(input);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

}