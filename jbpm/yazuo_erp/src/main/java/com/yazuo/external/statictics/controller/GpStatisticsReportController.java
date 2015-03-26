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

import java.text.ParseException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.external.statictics.service.GpStatisticsReportService;
import com.yazuo.external.statictics.vo.DailyMerchantSummary;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-11 下午2:16:49
 */
@Controller
@RequestMapping("report")
public class GpStatisticsReportController {

	@Resource
	private GpStatisticsReportService gpStatisticsReportService;

	@RequestMapping(value = "getMonthMerchantSummary", method = { RequestMethod.POST, RequestMethod.POST })
	@ResponseBody
	public JsonResult getMonthMerchantSummary(@RequestBody Map<String, Object> paramMap) throws ParseException {
		Integer brandId = (Integer) paramMap.get("merchantId");
		String date = (String) paramMap.get("date");
		Map<String, Object> map = gpStatisticsReportService.getMonthMerchantSummary(brandId, date);
		return new JsonResult(true).setMessage("查询成功").setData(map);
	}
	
	@RequestMapping(value = "getDailyMerchantSummary", method = { RequestMethod.POST, RequestMethod.POST })
	@ResponseBody
	public JsonResult getDailyMerchantSummary(@RequestBody Map<String, Object> paramMap) throws ParseException {
		DailyMerchantSummary dailyMerchantSummary = gpStatisticsReportService.getDailyMerchantSummary(paramMap);
		return new JsonResult(true).setMessage("查询成功").setData(dailyMerchantSummary); 
	}
}
