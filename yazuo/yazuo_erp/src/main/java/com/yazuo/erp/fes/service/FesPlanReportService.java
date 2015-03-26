/**
 * @Description 工作计划导出接口类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;

/**
 * @Description 工作计划导出接口类
 * @author erp team
 * @date
 */
public interface FesPlanReportService {

	/**
	 * @Description 导出工作计划，并下载
	 * @param paramMap
	 * @param request
	 * @param response
	 * @return
	 * @return boolean
	 * @throws 
	 */
	boolean exportReport(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException ;

}
