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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;

import com.yazuo.erp.fes.service.FesPlanService;
import com.yazuo.erp.fes.service.FesReportService;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 运营报表 相关业务操作
 * 
 * @author
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("fesReport")
public class FesReportController {

	private static final Log log = LogFactory.getLog(FesReportController.class);

	@Resource
	private FesReportService fesReportService;

	/**
	 * @return 
	 * @Description 导出某月的运营报表，并下载
	 * @param paramMap
	 * @param response
	 * @param request
	 * @throws IOException
	 * @return void
	 * @throws
	 */ 
	@RequestMapping(value = "exportReport", method = { RequestMethod.POST, RequestMethod.GET })
	public void download(@RequestParam(required = true) Map<String, Object> paramMap, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		fesReportService.exportReport(paramMap, request, response);
	}

}
