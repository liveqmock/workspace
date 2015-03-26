/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-02-21
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.service.LotteryService;
import com.yazuo.weixin.vo.MemberAwardVO;

/**
 * @ClassName: ConsumerLotteryController
 * @Description: 微信用户抽奖
 * 
 */
@Controller
@RequestMapping("/weixin/consumerLottery")
public class ConsumerLotteryController {

	private static final Log LOG = LogFactory.getLog("lottery");

	@Resource
	private LotteryService lotteryService;

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
			map = lotteryService.whetherLuckyDraw(memberAward);

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
		ModelAndView mav = new ModelAndView("lottery/luckRoulette");
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
			lottery = lotteryService.luckyDraw(memberAward);
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
		ModelAndView mav = new ModelAndView("lottery/scratchCard");
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
			map = lotteryService.whetherLuckyDraw(memberAward);

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
		ModelAndView mav = new ModelAndView("lottery/slotMachine");
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
			map = lotteryService.luckyDraw(memberAward);
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