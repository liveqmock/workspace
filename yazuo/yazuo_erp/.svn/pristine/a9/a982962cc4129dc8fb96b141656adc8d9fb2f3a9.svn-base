/**
 * @Description 对外接口，工作管理相关操作
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.external.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.service.WebkitManagerService;
import com.yazuo.erp.system.vo.SysWebkitVO;
import com.yazuo.util.StringUtil;

/**
 * 工作计划 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("external/webkit")
public class WebKitController {

	private static final Log LOG = LogFactory.getLog(PlanController.class);
	@Resource
	private WebkitManagerService webkitManagerService;
	@Value("${webkitFilePath}")
	private String webkitRealPath;
	
	/**
	 * 取最新版本
	 */
	@RequestMapping(value = "getNewVersionWebKit", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getNewVersionWebKit(@RequestParam(value = "versionNum", required = true) String versionNum, HttpServletRequest request) {
		LOG.info("版本号："+versionNum);
		if (StringUtil.isNullOrEmpty(versionNum)) {
			return new JsonResult(false).setMessage("参数版本号为空!");
		}
		SysWebkitVO webkit = webkitManagerService.getWebKitMaxVersion();
		if (webkit ==null) {
			return new JsonResult(false).setMessage("未上传webkit版本!");
		}
		if (webkit.getVersionNum().compareTo(versionNum) >0) { // 判断是否为最新版本
			Map<String, Object> result = new HashMap<String, Object>();
			String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort() + "/"
					+ request.getContextPath()+"/"+webkitRealPath+ "/" + webkit.getFileName();
			result.put("versionNum", webkit.getVersionNum());
			result.put("description", webkit.getDescription());
			result.put("isForcedUpgrade", webkit.getIsForcedUpgrade());
			result.put("downFilePath", path);
			return new JsonResult(true).setMessage("有最新版本!").setData(result);
		}
		return new JsonResult(false).setMessage("没有最新版本!");
	}
}
