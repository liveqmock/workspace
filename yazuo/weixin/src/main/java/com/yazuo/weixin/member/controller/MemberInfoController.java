package com.yazuo.weixin.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.member.service.MemberInfoService;

@Controller
@RequestMapping("weixin/member")
public class MemberInfoController {

	@Resource
	private MemberInfoService memberInfoService;
	
	@RequestMapping(value = "updatePwd", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object updatePwd(Integer brandId, String cardNo, String pwd, String newPwd) {
		Map<String, Object> map = memberInfoService.updatePwd(brandId, cardNo, newPwd, pwd);
		return map;
	}
	
	
	@RequestMapping(value = "resetPwd", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object resetPwd(Integer brandId, String cardNo) {
		boolean isCanReset = memberInfoService.judgeCanResetPwd(brandId, cardNo);
		if (isCanReset) {
			return memberInfoService.resetPwd(brandId, cardNo);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", false);
		map.put("message","您今天已经重置过三次密码，不能再重置啦~");
		return map;
	}
	
	@RequestMapping(value = "initUpdatePwdPage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView initUpdatePwdPage(Integer brandId, String cardNo, String weixinId) {
		ModelAndView mav = new ModelAndView("/member/update_pwd");
		mav.addObject("brandId", brandId);
		mav.addObject("cardNo", cardNo);
		mav.addObject("weixinId", weixinId);
		return mav;
	}
}
