/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.system.controller;

import com.yazuo.erp.system.vo.*;

import java.io.IOException;
/**
 * @Description 填单题目集控制类
 * @author erp team
 * @date 
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
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

import com.yazuo.erp.system.service.SysQuestionService;

/**
 * 填单题目集 相关业务操作
 * @author 
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)  
@RequestMapping("sysQuestion")
public class SysQuestionController {
	
	private static final Log LOG = LogFactory.getLog(SysQuestionController.class);
	@Resource
	private SysQuestionService sysQuestionService;
	
	/**
	 * 保存修改填单题目集
	 */
	@RequestMapping(value = "saveSysQuestion", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveSysQuestion(@RequestBody SysQuestionVO sysQuestion) {
		int count = sysQuestionService.saveSysQuestion(sysQuestion);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 查询填单题目集 
	 */
	@RequestMapping(value = "listSysQuestions", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listSysQuestions(@RequestBody SysQuestionVO sysQuestionVO) {
		List<Map<String, Object>> pageList = sysQuestionService.querySysQuestionList(sysQuestionVO);
		return new JsonResult(true).setMessage("查询成功!").setData(pageList);
	}
	
	/**
	 * 查询(多个)填单题目集 
	 */
	@RequestMapping(value = "listMutiSysQuestions", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listMutiSysQuestions(@RequestBody SysQuestionVO[] sysQuestionVOs) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (SysQuestionVO sysQuestionVO : sysQuestionVOs) {
			List<Map<String, Object>> pageList = sysQuestionService.querySysQuestionList(sysQuestionVO);
			resultMap.put(sysQuestionVO.getDocumentType(), pageList);
		}
		return new JsonResult(true).setMessage("查询成功!").setData(resultMap);
	}
	
	/**
	 * 删除 填单题目集 
	 */
	@RequestMapping(value = "deleteSysQuestion/{id}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteSysQuestion(@PathVariable int id) {
		int count = sysQuestionService.deleteSysQuestionById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
