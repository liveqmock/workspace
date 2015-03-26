/**
 * @Description 
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.train.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.TraFinalExamRuleService;

/**
 * @Description
 * @author yazuo
 * @date 2014-6-11 下午7:10:32
 */
@Controller
@RequestMapping("finalExamRule")
@SessionAttributes(Constant.SESSION_USER)
public class FinalExamRuleController {

	@Resource
	private TraFinalExamRuleService traFinalExamRuleService;

	@Resource
	private SysDictionaryService sysDictionaryService;

	@RequestMapping(value = "addFinalExamRule", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult addFinalExamRule(@RequestBody Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		Integer id = (Integer) paramMap.get("id");// 规则ID
		if (null == id || id.intValue() == 0) {
			traFinalExamRuleService.saveFinalExamRule(paramMap);
		} else {
			traFinalExamRuleService.updateFinalExamRule(paramMap);
		}
		return new JsonResult(true).setMessage("保存成功");
	}

	@RequestMapping(value = "getQuestionByCourseId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getQuestionByCourseId(@RequestBody Map<String, Object> paramMap) {
		List<Map<String, Object>> questionList = traFinalExamRuleService.getQuestionByCourseId(paramMap);
		Map<String, String> dictionaryMap = sysDictionaryService.getSysDictionaryByType("00000019");
		for (Map<String, Object> m : questionList) {
			String type = String.valueOf(m.get("question_type"));
			m.put("question_type", dictionaryMap.get(type));
		}
		return new JsonResult(true).setMessage("查询成功").putData("rows", questionList);
	}

	@RequestMapping(value = "getFinalExamRuleByCourseId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getFinalExamRuleByCourseId(@RequestBody Map<String, Object> paramMap) {
		Map<String, Object> map = traFinalExamRuleService.getFinalExamRuleByCourseId(paramMap);
		if (null != map) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("finalExamQuestions");
			if (null != list) {
				Map<String, String> dictionaryMap = sysDictionaryService.getSysDictionaryByType("00000019");
				for (Map<String, Object> m : list) {
					String type = String.valueOf(m.get("question_type"));
					m.put("question_type", dictionaryMap.get(type));
				}
			}
		}

		return new JsonResult(true).setMessage("查询成功").setData(map);
	}

}
