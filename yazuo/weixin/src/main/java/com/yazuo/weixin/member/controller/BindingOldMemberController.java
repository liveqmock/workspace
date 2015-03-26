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

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.member.service.BindingOldMemberService;
import com.yazuo.weixin.service.NewLotteryService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;

/**
 * 老会员绑定
 * @author kyy
 * @date 2014-10-9 下午4:30:58
 */
@Controller
@RequestMapping("bindOldMember")
public class BindingOldMemberController {
	@Resource
	private BindingOldMemberService bindingOldMemberService;
	@Resource
	private WeixinManageService weixinManageService;
	@Resource
	private WeixinPhonePageService weixinPhonePageService;
	@Resource
	private NewLotteryService newLotteryService;

	@RequestMapping(value = "validatePage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView validatePage(Integer brandId, String weixinId) {
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		if (member==null || (member!=null && !member.getIsMember())) {
			// 跳转到输入手机号和验证码页面
			ModelAndView mav = new ModelAndView("/bind_weixin_member/verify_member_new");
//			ModelAndView mav = new ModelAndView("/bind_weixin_member/verify_member");
			mav.addObject("brandId", brandId);
			mav.addObject("weixinId", weixinId);
			mav.addObject("title", business!=null ? business.getTitle():"");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("w-registerSuccess");// 已经是会员了跳转
			mav.addObject("business", business);
			mav.addObject("weixinId", weixinId);
			mav.addObject("member", member);
			return mav;
		}
	}
	
	@RequestMapping(value = "verifyAndAddMember", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object verifyAndAddMember(Integer brandId, String weixinId, String phoneNo, String identifyCode) {
		return bindingOldMemberService.bingdingOldMember(phoneNo, identifyCode, brandId, weixinId);
	}
	
	@RequestMapping(value = "bindingResult", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView bindingResult(Integer brandId, String weixinId, String isSuccess, 
			@RequestParam(value="couponIdStr", required = false)String couponIdStr,@RequestParam(value="lunckCount", required = false) String luckCount
			,@RequestParam(value="phoneNo", required=false) String phoneNo) {
		if (!StringUtil.isNullOrEmpty(isSuccess) && isSuccess.trim().equals("success")) { // 微信会员绑定成功
			// 提示页面显示送的券和抽奖机会（劵id和抽奖次数）
			Map<String, Object> map = newLotteryService.getSendActivity(couponIdStr, luckCount, brandId);	
			ModelAndView mav = new ModelAndView("coffee/lottery/bound_success_new");
//			ModelAndView mav = new ModelAndView("coffee/lottery/bound_success");
			mav.addObject("brandId", brandId);
			mav.addObject("weixinId", weixinId);
			mav.addObject("isGiveLuckCount", map.get("isGiveLuckCount"));
			mav.addObject("luckCount", map.get("luckCount"));
			mav.addObject("couponList", map.get("couponList"));
			mav.addObject("isMemberBindWeixin", true);
			return mav;
		} else { // 添加粉丝会员
			ModelAndView mav = new ModelAndView("bind_weixin_member/bound_fail");
			mav.addObject("brandId", brandId);
			mav.addObject("weixinId", weixinId);
			mav.addObject("phoneNo", phoneNo);
			return mav;
		}
	}
	
	
	//下发验证码
	@RequestMapping(value = "registerIdentifyingCode", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object registerIdentifyingCode(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "brandId") Integer brandId,@RequestParam(value = "weixinId") String weixinId,
			@RequestParam(value = "phoneNo") String phoneNo,@RequestParam(value = "title") String title) {
		String sendIdentifyingCode = bindingOldMemberService.sendIdentifyingCode(phoneNo, brandId, weixinId, title);
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
}
