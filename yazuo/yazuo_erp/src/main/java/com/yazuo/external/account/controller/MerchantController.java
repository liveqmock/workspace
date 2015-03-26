/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.external.account.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.external.account.service.MerchantService;

/**
 * 运营商户表 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("merchantCrm")
public class MerchantController {
	private static final Log LOG = LogFactory.getLog(MerchantController.class);

	@Resource
	private MerchantService merchantService;

	@RequestMapping(value = "getMerchantsInfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getMerchantsInfo(@RequestParam(value = "merchantName") String merchantName) {
		List<Map<String, Object>> list = merchantService.getMerchantsInfo(merchantName);
		return new JsonResult(true).setMessage("查询成功").setData(list);
	}

	@RequestMapping(value = "getMerchantFaceShopCount", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getMerchantFaceShopCount(Integer brandId) {
		Long count = merchantService.getMerchantFaceShopCount(brandId);
		return new JsonResult(true).setMessage("查询成功").setData(count);
	}
}
