package com.yazuo.weixin.member.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.service.LotteryRuleService;

@Controller
@RequestMapping("/setting/lottery")
public class SettingLotteryController {

	@Resource
	private LotteryRuleService lotteryRuleService;
	
	// 设置粉丝抽奖
	@RequestMapping(value = "fensiLottery", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView fensiLottery(Integer brandId, String weixinId, HttpServletRequest request) {
		List<Map<String, Object>> resultList = lotteryRuleService.getLotteryRuleOfBrand(brandId, 22, weixinId, request.getContextPath());
		ModelAndView mav = new ModelAndView("/member/setting_lottery");
		mav.addObject("resultList", resultList);
		mav.addObject("title", "粉丝抽奖");
		return mav;
	}
	
	//设置幸运抽奖
	@RequestMapping(value = "luckLottery", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView luckLottery(Integer brandId, String weixinId, HttpServletRequest request) {
		List<Map<String, Object>> resultList = lotteryRuleService.getLotteryRuleOfBrand(brandId, 28, weixinId, request.getContextPath());
		ModelAndView mav = new ModelAndView("/member/setting_lottery");
		mav.addObject("resultList", resultList);
		mav.addObject("title", "幸运抽奖抽奖");
		return mav;
	}
}
