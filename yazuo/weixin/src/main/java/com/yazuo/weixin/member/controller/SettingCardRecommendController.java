package com.yazuo.weixin.member.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.yazuo.weixin.es.vo.CardTypeVo;
import com.yazuo.weixin.member.service.SettingCardRecommendService;
import com.yazuo.weixin.user.vo.UserInfoVo;

@Controller
@RequestMapping("/setting/cardRecommend")
public class SettingCardRecommendController {
	
	// 后台操作日志
	private static final Log opLog = LogFactory.getLog("backstageOperate");

	@Resource
	private SettingCardRecommendService settingCardRecommendService;
	
	@RequestMapping(value = "loadCardType", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView loadCardType(Integer brandId) {
		// 该商户所有的卡类型
		List<CardTypeVo> cardList = settingCardRecommendService.getCardTypeByBrandId(brandId);
		// 商户选择的卡类型
		List<Integer> chooseCardList = settingCardRecommendService.getSettingCardByBrandId(brandId);
		ModelAndView mav = new ModelAndView("/resource/setting_card_recommend");
		mav.addObject("cardTypeList", cardList);
		mav.addObject("chooseCardList", chooseCardList);
		mav.addObject("brandId", brandId);
		return mav;
	}
	
	
	@RequestMapping(value = "saveCardType", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object saveCardType(Integer brandId, @RequestParam(value="cardTypeIds[]", required=false)String [] cardTypeIds,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		opLog.info("--"+sdf.format(new Date())+"调用saveCardType(设置卡类型)的用户UserId:"+(user !=null ? user.getUserId():0));
		boolean success = settingCardRecommendService.saveCardType(brandId, cardTypeIds);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", success);
		map.put("message", success ? "保存成功" : "保存失败");
		return map;
	}
}
