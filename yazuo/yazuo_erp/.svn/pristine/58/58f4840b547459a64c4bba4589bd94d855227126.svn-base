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
package com.yazuo.external.statictics.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.external.statictics.service.MerchantSMSCountStatisticsService;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-14 下午3:11:46
 */
@Controller
@RequestMapping("sms")
public class MerchantSMSCountStatisticsController {

	@Resource
	private MerchantSMSCountStatisticsService merchantSMSCountStatisticsService;

	@RequestMapping(value = "getMerchantSMSCount", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getMerchantSMSCount(Integer merchantId) {
		Map<String, Object> map = merchantSMSCountStatisticsService.getMerchantSMSCount(merchantId);
		return new JsonResult(true).setMessage("查询成功").setData(map);
	}
}
