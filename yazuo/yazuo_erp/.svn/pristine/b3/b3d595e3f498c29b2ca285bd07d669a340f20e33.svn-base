/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.controller;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;

import com.yazuo.erp.fes.service.FesPlanReportService;

/**
 * 工作计划导出 相关操作
 * 
 * @author
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("fesPlanReport")
public class FesPlanReportController {

	private static final Log log = LogFactory.getLog(FesPlanReportController.class);

	@Resource
	private FesPlanReportService fesPlanReportService;

	/**
	 * @return 
	 * @Description 导出工作计划，并下载
	 * @param paramMap
	 * @param response
	 * @param request
	 * @throws IOException
	 * @return void
	 * @throws
	 */ 
	@RequestMapping(value = "exportPlanReport", method = { RequestMethod.POST, RequestMethod.GET })
	public void download(@RequestParam(required = true) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		fesPlanReportService.exportReport(paramMap, request, response);
	}

}
