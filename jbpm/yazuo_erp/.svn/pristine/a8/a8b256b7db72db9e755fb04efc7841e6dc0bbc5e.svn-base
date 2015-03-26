/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
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

package com.yazuo.erp.minierp.controller;

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
import com.yazuo.erp.minierp.service.BrandsService;
import com.yazuo.erp.minierp.vo.BrandsVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */

/**
 * Brands 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("brands")
public class BrandsController {

	private static final Log LOG = LogFactory.getLog(BrandsController.class);
	@Resource
	private BrandsService brandsService;

	/**
	 * 列表显示 Brands
	 */
	@RequestMapping(value = "getBrandsInfo", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getBrandsInfo(@RequestParam(value = "name") String name) {
		List<Map<String, Object>> list = brandsService.getBrandsInfo(name);
		return new JsonResult(true).setMessage("查询成功!").setData(list);
	}

	@RequestMapping(value = "getBrandsById", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getBrandsById(Integer brandId) {
		BrandsVO brandsVO = brandsService.getBrandsById(brandId);
		return new JsonResult(true).setMessage("查询成功!").setData(brandsVO);
	}

}
