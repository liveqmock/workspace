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
package com.yazuo.external.active.controller;

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
import com.yazuo.external.active.service.ActiveCrmService;

/**
 * @Description 营销活动
 * @author zhaohuaqin
 * @date 2014-8-19 下午5:14:32
 */
@Controller
@RequestMapping("activeCrm")
public class ActiveCrmController {
	private static final Log LOG = LogFactory.getLog(ActiveCrmController.class);

	@Resource
	private ActiveCrmService activeCrmService;

	@RequestMapping(value = "getActiveExecutiving", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getActiveExecutiving(Integer brandId) {
		List<Map<String, Object>> list = activeCrmService.getActiveExecutiving(brandId);
		return new JsonResult(true).setMessage("查询成功").setData(list);
	}
}
