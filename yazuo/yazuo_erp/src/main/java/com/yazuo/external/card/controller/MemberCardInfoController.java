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
package com.yazuo.external.card.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.external.card.service.MemberCardInfoService;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-20 下午7:54:53
 */
@Controller
public class MemberCardInfoController {
	private static final Log LOG = LogFactory.getLog(MemberCardInfoController.class);

	@Resource
	private MemberCardInfoService memberCardInfoService;

	@RequestMapping(value = "getMemberCardInfoByBrandId", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getMemberCardInfoByBrandId(Integer brandId) {
		List<Map<String, Object>> list = memberCardInfoService.getMemberCardInfoByBrandId(brandId);
		return new JsonResult(true).setMessage("查询成功").setData(list);
	}
}
