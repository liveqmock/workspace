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
package com.yazuo.weixin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallOrder;
import com.yazuo.weixin.service.LotteryRecordService;
import com.yazuo.weixin.service.LotteryRuleService;
import com.yazuo.weixin.service.NewLotteryService;
import com.yazuo.weixin.vo.MemberAwardVO;

/**
 * 在线办卡抽奖相关业务
 * @author kyy
 * @date 2014-8-24 上午10:00:22
 */
@Controller
@RequestMapping("/caffe/cardLottery")
public class NewLotteryController {

	private static final Log LOG = LogFactory.getLog("lottery");

	@Value("#{propertiesReader['getValidLotteryCountPath']}")
	private String getValidLotteryCountPath; // 在线办卡赠送的抽奖机会路径
	
	@Resource
	private WeixinMallMartService weixinMallMartService;
	@Resource
	private NewLotteryService newLotteryService;
	@Resource
	private LotteryRuleService lotteryRuleService;
	@Resource
	private LotteryRecordService lotteryRecordService;
	
	// 保存中奖收货信息
	@RequestMapping(value = "saveLotteryMessage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView saveLotteryMessage(@RequestParam(value = "firstAddr", required = false) String firstAddr
			,@RequestParam(value = "secondAddr", required = false) String secondAddr,
			@RequestParam(value = "thirdAddr", required = false) String thirdAddr,
			@RequestParam(value = "detailAddr", required = false) String detailAddr,
			@RequestParam(value = "productInfo", required = false) String productInfo,
			@RequestParam(value = "receiver", required = false) String receiver,
			@RequestParam(value = "mobile", required = false) String mobile, String orderId, Integer brandId) {
		WxMallOrder order = weixinMallMartService.getMallOrderById(Integer.parseInt(orderId));
		order.setFirstAddr(firstAddr);
		order.setSecondAddr(secondAddr);
		order.setThirdAddr(thirdAddr);
		order.setDetailAddr(detailAddr);
		order.setProductInfo(productInfo);
		order.setReceiver(receiver);
		order.setMobile(mobile);
		int count = 0;
		try {
			count = weixinMallMartService.updateMallOrderAddress(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView("/coffee/lottery/lottery_success");
		mav.addObject("mallOrder", order);
		mav.addObject("brandId", brandId);
		mav.addObject("message", count>0 ? "恭喜领取成功!" : "信息保存有误!");
		return mav;
	}
	
	// 查看中奖记录
	@RequestMapping(value = "lookLotteryRecord", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView lookLotteryRecord(String weixinId, Integer brandId) {
		
		List<Map<String, Object>> list = lotteryRecordService.getLotteryRecordByBrandIdAndWeixinId(weixinId, brandId);
		ModelAndView mav = new ModelAndView("/coffee/lottery/lottery_record");
		mav.addObject("recordList", list);
		return mav;
	}
	
	/**
	 * 幸运转盘
	 */
	@RequestMapping(value = "luckRoulette", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView luckRoulette(HttpServletResponse response,
			HttpServletRequest request, MemberAwardVO memberAward) {

		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		try {
			LOG.info("请求--幸运转盘页面数据：" + memberAward.toString());
			map = newLotteryService.whetherLuckyDraw(memberAward);

			// 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：可以抽奖5:没设置奖品
			int winflag = Integer.parseInt(map.get("winflag") + "");
			LOG.info("抽奖标识：=" + winflag
					+ "; 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：可以抽奖5:没设置奖品");
			if (winflag != 4) {// 不能抽奖
				error = getContent(winflag);
			}

		} catch (Exception e) {
			LOG.error("请求--幸运转盘页面错误", e);
			ModelAndView mav = new ModelAndView("lottery/error");
			mav.addObject("error", "该活动还没有开始，谢谢您的关注！");
			return mav;
		}
		ModelAndView mav = new ModelAndView("coffee/lottery/luckRoulette");
		mav.addObject("memberAward", memberAward);
		mav.addObject("lottery", map);
		mav.addObject("error", error);
		return mav;
	}

	/**
	 * 刮刮卡
	 */
	@RequestMapping(value = "scratchCard", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView scratchCard(HttpServletResponse response,
			HttpServletRequest request, MemberAwardVO memberAward) {

		Map<String, Object> lottery = new HashMap<String, Object>();
		String error = "";
		try {
			LOG.info("请求--刮刮卡页面数据：" + memberAward.toString());
			// 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：中奖了 5：没中奖 6：(可以抽奖)但是没添加奖项
			lottery = newLotteryService.luckyDraw(memberAward);
			int winflag = Integer.parseInt(lottery.get("winflag") + "");
			if (winflag < 4) {// 不能抽奖
				error = getContent(winflag);
			}

		} catch (Exception e) {
			LOG.error("请求--刮刮卡页面错误", e);
			ModelAndView mav = new ModelAndView("lottery/error");
			mav.addObject("error", "该活动还没有开始，谢谢您的关注！");
			return mav;
		}
		ModelAndView mav = new ModelAndView("coffee/lottery/scratchCard");
		mav.addObject("lottery", lottery);
		mav.addObject("memberAward", memberAward);
		mav.addObject("error", error);
		return mav;
	}

	/**
	 * 老虎机
	 */
	@RequestMapping(value = "slotMachine", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView slotMachine(HttpServletResponse response,
			HttpServletRequest request, MemberAwardVO memberAward) {

		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		try {
			LOG.info("请求--老虎机页面数据：" + memberAward.toString());
			map = newLotteryService.whetherLuckyDraw(memberAward);

			// 1:无该活动 2：超过了次数 3:抽奖次数用完抽奖结束 4：可以抽奖5:没设置奖品
			int winflag = Integer.parseInt(map.get("winflag") + "");
			LOG.info("抽奖标识：=" + winflag
					+ "; 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：可以抽奖5:没设置奖品");
			if (winflag != 4) {// 不能抽奖
				error = getContent(winflag);
			}
		} catch (Exception e) {
			LOG.error("请求--老虎机页面错误", e);
			ModelAndView mav = new ModelAndView("lottery/error");
			mav.addObject("error", "该活动还没有开始，谢谢您的关注！");
			return mav;
		}
		ModelAndView mav = new ModelAndView("coffee/lottery/slotMachine");
		mav.addObject("memberAward", memberAward);
		mav.addObject("error", error);
		mav.addObject("lottery", map);
		return mav;
	}

	/**
	 * 抽奖ajax
	 */
	@RequestMapping(value = "ajaxLottery", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Object ajaxGreatDishes(HttpServletResponse response,
			HttpServletRequest request, MemberAwardVO memberAward) {

		Map<String, Object> lottery = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";

		try {
			LOG.info("请求--抽奖ajax数据：" + memberAward.toString());
			// 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：中奖了 5：没中奖 6：(可以抽奖)但是没添加奖项
			map = newLotteryService.luckyDraw(memberAward);
			int winflag = Integer.parseInt(map.get("winflag") + "");
			if (winflag < 4) {// 不能抽奖
				error = getContent(winflag);
			}

		} catch (Exception e) {
			LOG.error("请求--抽奖ajax错误", e);
		}
		lottery.put("map", map);
		lottery.put("error", error);
		return lottery;
	}
	
	// 绑定实体卡成功，机会也送成功
	@RequestMapping(value = "boundSuccess", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView boundSuccess(String couponIdStr,String luckCount, Integer brandId, String weixinId) {
		ModelAndView mav = new ModelAndView("coffee/lottery/bound_success_new");
//		ModelAndView mav = new ModelAndView("coffee/lottery/bound_success");
		Map<String, Object> map = newLotteryService.getSendActivity(couponIdStr, luckCount, brandId);	
		mav.addObject("isGiveLuckCount", map.get("isGiveLuckCount"));
		mav.addObject("couponList", map.get("couponList"));
		mav.addObject("luckCount", map.get("luckCount"));
		mav.addObject("brandId", brandId);
		mav.addObject("weixinId", weixinId);
		return mav;
	}

	
	// 点马上抽奖进入的抽奖页面
	@RequestMapping(value = "nowLuckEvent", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object nowLuckEvent(Integer brandId, String weixinId, HttpServletRequest request) {
		String url = newLotteryService.luckRedirectPath(weixinId, brandId, request.getContextPath());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", 1);
		map.put("data", url);
		return map;
	}
	
	// 填写收货地址信息
	@RequestMapping(value = "redirectRervierPage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView redirectRervierPage(Integer membershipId, String lotteryItemName, String lotteryName, String weixinId, String orderId, Integer brandId) {
		ModelAndView mav = new ModelAndView("coffee/lottery/draw");
		mav.addObject("membershipId", membershipId);
		mav.addObject("lotteryItemName", lotteryItemName);
		mav.addObject("lotteryName", lotteryName);
		mav.addObject("weixinId", weixinId);
		mav.addObject("orderId", orderId);
		mav.addObject("brandId", brandId);
		return mav;
	}
	// 填写收货地址信息
	@RequestMapping(value = "errorPage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView errorPage() {
		ModelAndView mav = new ModelAndView("coffee/lottery/wx-xldPayError");
		mav.addObject("errorMsg", "活动还未开始，感谢您的参与!");
		return mav;
	}
	
	
	private String getContent(int flag) {
		String content = "该活动还没有开始，感谢您的参与！";
		// 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：可以抽奖5:没设置奖品
		if (flag == 2) {
			content = "您已没有抽奖机会了，感谢您的参与！";
		} else if (flag == 3) {
			content = "奖品已经送完，活动结束了，感谢您的参与！";
		}
		return content;
	}
}
