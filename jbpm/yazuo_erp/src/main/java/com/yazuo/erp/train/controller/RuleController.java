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
import com.yazuo.erp.train.service.TraRuleService;

/**
 * @Description
 * @author yazuo
 * @date 2014-6-10 下午3:51:38
 */
@Controller
@RequestMapping("rule")
@SessionAttributes(Constant.SESSION_USER)
public class RuleController {
	@Resource
	private TraRuleService traRuleService;

	@Resource
	private SysDictionaryService sysDictionaryService;

	/**
	 * @Description 添加考试规则
	 * @param id
	 *            Integer
	 * @param coursewareId
	 *            Integer
	 * @param paperType
	 *            String
	 * @param timeLimit
	 *            String
	 * @param total
	 *            String
	 * @param ruleType
	 *            String
	 * @param passingScore
	 *            String
	 * @param questionIdList
	 *            List<Integer>
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "addRule", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult addRule(@RequestBody Map<String, Object> paramMap, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		Integer id = (Integer) paramMap.get("id");// 规则ID
		if (null == id || id.intValue() == 0) {
			traRuleService.saveRule(paramMap);
		} else {
			traRuleService.updateRule(paramMap);
		}
		return new JsonResult(true).setMessage("保存成功");
	}

	/**
	 * @Description 根据课件ID查询笔试题
	 * @param paramMap
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "getQuestionByCoursewareId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getQuestionByCoursewareId(@RequestBody Map<String, Object> paramMap) {
		List<Map<String, Object>> questionList = traRuleService.getQuestionByCoursewareId(paramMap);
		Map<String, String> dictionaryMap = sysDictionaryService.getSysDictionaryByType("00000019");
		for (Map<String, Object> m : questionList) {
			String type = String.valueOf(m.get("question_type"));
			m.put("question_type", dictionaryMap.get(type));
		}
		return new JsonResult(true).setMessage("查询成功").putData("rows", questionList).putData("totalSize", questionList.size());
	}

	/**
	 * @Description 根据课件ID查询PPT题
	 * @param paramMap
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "getPttByCoursewareId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getPttByCoursewareId(@RequestBody Map<String, Object> paramMap) {
		List<Map<String, Object>> questionList = traRuleService.getPptByCoursewareId(paramMap);
		return new JsonResult(true).setMessage("查询成功").putData("rows", questionList).putData("totalSize", questionList.size());
	}
	
	/**
	 * @Description 根据课件ID查询实操题
	 * @param paramMap
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "getPracticeQuestionByCoursewareId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getPracticeQuestionByCoursewareId(@RequestBody Map<String, Object> paramMap) {
		List<Map<String, Object>> questionList = traRuleService.getPracticeQuestionByCoursewareId(paramMap);
		Map<String, String> dictionaryMap = sysDictionaryService.getSysDictionaryByType("00000019");
		for (Map<String, Object> m : questionList) {
			String type = String.valueOf(m.get("question_type"));
			m.put("question_type", dictionaryMap.get(type));
		}
		return new JsonResult(true).setMessage("查询成功").putData("rows", questionList).putData("totalSize", questionList.size());
	}

	@RequestMapping(value = "getRuleByCoursewareId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getRuleByCoursewareId(@RequestBody Map<String, Object> paramMap) {
		Map<String, Object> map = traRuleService.getRuleByCoursewareId(paramMap);
		if (null != map) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("requiredQuestions");
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
