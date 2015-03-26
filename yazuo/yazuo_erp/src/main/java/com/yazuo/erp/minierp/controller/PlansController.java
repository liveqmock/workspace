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

import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.minierp.service.PlansService;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */

/**
 * Plans 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("plans")
public class PlansController {

	private static final Log LOG = LogFactory.getLog(PlansController.class);
	@Resource
	private PlansService plansService;

	/**
	 * 列表显示 Plans
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = "plansMigration", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult listPlanss() throws ParseException {
		boolean flag = plansService.savePlansList();
		return new JsonResult(flag).setMessage(flag ? "数据迁移成功" : "数据迁移失败");
	}

}
