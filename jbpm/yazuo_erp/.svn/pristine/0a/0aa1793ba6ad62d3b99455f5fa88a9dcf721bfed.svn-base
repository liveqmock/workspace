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

import java.util.HashMap;
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

import com.yazuo.external.account.service.MerchantProductService;

/**
 * 产品表 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("product")
public class MerchantProductController {
	private static final Log LOG = LogFactory.getLog(MerchantProductController.class);

	@Resource
	private MerchantProductService productService;

	// 门店开通的产品
	public static Map<Integer, Object> MERCHANT_PRODUCT = new HashMap<Integer, Object>();

	@RequestMapping(value = "getProductByMerchantId", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object getProductByMerchantId(@RequestParam(value = "merchantId") Integer merchantId) {
		MERCHANT_PRODUCT = judgeData(merchantId);
		List<String> list = (List<String>) MERCHANT_PRODUCT.get(merchantId);
		return list;
	}

	private Map<Integer, Object> judgeData(Integer merchantId) {

		Map<Integer, Object> resultMap = MERCHANT_PRODUCT;
		List<String> productMap = (List<String>) MERCHANT_PRODUCT.get(merchantId); // 门店开通的产品
		if (productMap == null || productMap.size() == 0) {
			List<String> list = productService.getProductByMerchantId(merchantId);
			resultMap.put(merchantId, list);
		} else {
			resultMap = MERCHANT_PRODUCT;
		}

		return resultMap;
	}

	@RequestMapping(value = "updateMerProductsCache", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void updateMerProductsCache() {
		LOG.info("开始更新");
		MERCHANT_PRODUCT.clear();
		MERCHANT_PRODUCT = productService.getProductsAllMerchant();
		LOG.info("更新结束");
	}
}
