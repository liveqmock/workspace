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
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 工作计划 相关业务操作
 * 
 * @author
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("fesPlan")
public class FesPlanController {

	private static final Log LOG = LogFactory.getLog(FesPlanController.class);
	@Resource
	private FesPlanService fesPlanService;

	/**
	 * 保存工作计划
	 */
	@RequestMapping(value = "saveFesPlan", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveFesPlan(@RequestBody Map<String, Object> paramMap, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		int count = fesPlanService.saveFesPlan(paramMap, request);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}

	/**
	 * 修改工作计划
	 */
	@RequestMapping(value = "updateFesPlan", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateFesPlan(@RequestBody Map<String, Object> paramMap, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		int count = fesPlanService.updateFesPlan(paramMap, request);
		return new JsonResult(count > 0).setMessage(count > 0 ? "修改成功!" : "修改失败!");
	}

	/**
	 * 查询工作计划列表
	 */
	@RequestMapping(value = "queryFesPlanList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult queryFesPlanList(@RequestBody Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		List<Map<String, Object>> list = fesPlanService.queryFesPlanList(paramMap);
		return new JsonResult(true).setData(list);
	}

	/**
	 * 查询一天的工作计划列表
	 */
	@RequestMapping(value = "queryDailyFesPlanList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult queryDailyFesPlanList(@RequestBody Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		List<Map<String, Object>> list = fesPlanService.queryDailyFesPlanList(paramMap);
		return new JsonResult(true).setData(list);
	}

	/**
	 * 根据工作计划ID查询详细信息
	 */
	@RequestMapping(value = "queryFesPlanById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult queryFesPlanById(@RequestBody Map<String, Object> paramMap) {
		Map<String, Object> map = fesPlanService.queryFesPlanById(paramMap);
		return new JsonResult(null == map ? false : true).setMessage(null == map ? "未查询到工作计划" : "查询成功").setData(map);
	}

	/**
	 * 放弃工作计划
	 */
	@RequestMapping(value = "updateAbandonFesPlanById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateAbandonFesPlanById(@RequestBody Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		int count = fesPlanService.updateAbandonFesPlanById(paramMap);
		return new JsonResult(count > 0).setMessage(count > 0 ? "成功!" : "失败!");
	}

	/**
	 * 延期工作计划
	 */
	@RequestMapping(value = "updateDelayFesPlanById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateDelayFesPlanById(@RequestBody Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		int count = fesPlanService.updateDelayFesPlanById(paramMap);
		return new JsonResult(count > 0).setMessage(count > 0 ? "延期成功!" : "延期失败!");
	}
	
	/**
	 * 完成工作计划
	 */
	@RequestMapping(value = "updateCompleteFesPlanById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateCompleteFesPlanById(@RequestBody Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		int count = fesPlanService.updateCompleteFesPlanById(paramMap);
		return new JsonResult(count > 0).setMessage(count > 0 ? "完成成功!" : "完成失败!");
	}
	
	/**
	 * 工作计划提醒设置
	 */
	@RequestMapping(value = "updateRemindFesPlanById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateRemindFesPlanById(@RequestBody Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		int count = fesPlanService.updateRemindFesPlanById(paramMap);
		return new JsonResult(count > 0).setMessage(count > 0 ? "设置成功!" : "设置失败!");
	}

	/**
	 * 删除 工作计划
	 */
	@RequestMapping(value = "deleteFesPlan/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult deleteFesPlan(@PathVariable int id) {
		int count = fesPlanService.deleteFesPlanById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}

	/**
	 * @Description 上传工作计划
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping(value = "uploadPlanFiles", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object uploadPlanFiles(@RequestParam(value = "myfiles", required = false) MultipartFile[] myfiles,
			HttpServletRequest request) throws IOException {
		return fesPlanService.uploadPlanFiles(myfiles, request);
	}
}
