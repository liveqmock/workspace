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
package com.yazuo.erp.train.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.dao.TraFinalExamQuestionDao;
import com.yazuo.erp.train.dao.TraFinalExamRuleDao;
import com.yazuo.erp.train.dao.TraQuestionDao;
import com.yazuo.erp.train.service.TraFinalExamRuleService;
import com.yazuo.erp.train.vo.TraFinalExamQuestionVO;
import com.yazuo.erp.train.vo.TraFinalExamRuleVO;

/**
 * @Description
 * @author yazuo
 * @date 2014-6-11 下午1:19:39
 */
@Service
public class TraFinalExamRuleServiceImpl implements TraFinalExamRuleService {

	@Resource
	private TraFinalExamRuleDao traFinalExamRuleDao;

	@Resource
	private TraFinalExamQuestionDao traFinalExamQuestionDao;

	@Resource
	private TraQuestionDao traQuestionDao;

	/**
	 * @Title saveFinalExamRule
	 * @Description
	 * @param paramMap
	 * @return
	 */
	@Override
	public int saveFinalExamRule(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();
		// 1、添加考卷规则
		TraFinalExamRuleVO traFinalExamRuleVO = getTraRuleVO(paramMap, userId);
		traFinalExamRuleVO.setInsertBy(userId);
		traFinalExamRuleDao.saveTraFinalExamRule(traFinalExamRuleVO);

		// 2、部分随机时，批量添加必考题
		String ruleType = (String) paramMap.get("finalExamRuleType");
		if (StringUtils.equals("1", ruleType)) {
			List<Integer> questionIdList = (List<Integer>) paramMap.get("questionIdList");
			Integer id = traFinalExamRuleVO.getId();
			addFinalExamQuestion(id, questionIdList, userId);
		}
		return 1;
	}

	private TraFinalExamRuleVO getTraRuleVO(Map<String, Object> paramMap, Integer userId) {
		TraFinalExamRuleVO traFinalExamRuleVO = new TraFinalExamRuleVO();
		traFinalExamRuleVO.setTimeLimit((String) paramMap.get("timeLimit"));
		traFinalExamRuleVO.setTotal(new BigDecimal((String) paramMap.get("total")));
		traFinalExamRuleVO.setFinalExamRuleType((String) paramMap.get("finalExamRuleType"));
		traFinalExamRuleVO.setPassScore(new BigDecimal((String) paramMap.get("passScore")));
		traFinalExamRuleVO.setInterviewScores(new BigDecimal((String) paramMap.get("interviewScores")));
		traFinalExamRuleVO.setUpdateBy(userId);
		traFinalExamRuleVO.setCourseId((Integer) paramMap.get("courseId"));
		return traFinalExamRuleVO;
	}

	/**
	 * @Title getQuestionByCourseId
	 * @Description
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getQuestionByCourseId(Map<String, Object> paramMap) {
		return traQuestionDao.getTraQuestionsByCourseIdMap(paramMap);
	}

	/**
	 * @Title getFinalExamRuleByCourseId
	 * @Description 根据课程ID查询期末考试规则
	 * @param paramMap
	 * @return
	 */
	@Override
	public Map<String, Object> getFinalExamRuleByCourseId(Map<String, Object> paramMap) {
		Map<String, Object> finalExamRulesMap = traFinalExamRuleDao.getTraFinalExamRulesMap(paramMap);
		if (null != finalExamRulesMap) {
			String finalExamRuleType = (String) finalExamRulesMap.get("final_exam_rule_type");
			if (StringUtils.equals(finalExamRuleType, "1")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ruleId", (Integer) finalExamRulesMap.get("id"));
				List<Map<String, Object>> finalExamQuestionListByRuleId = traFinalExamQuestionDao
						.getTraFinalExamQuestionByRuleId(map);
				finalExamRulesMap.put("finalExamQuestions", finalExamQuestionListByRuleId);
			}
		}
		return finalExamRulesMap;
	}

	/**
	 * @Title updateFinalExamRule
	 * @Description 修改期末考试规则
	 * @param paramMap
	 * @return
	 */
	@Override
	public int updateFinalExamRule(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();
		// 1、修改期末考试规则
		Integer id = (Integer) paramMap.get("id");
		TraFinalExamRuleVO traFinalExamRuleVO = getTraRuleVO(paramMap, userId);
		traFinalExamRuleVO.setId(id);
		traFinalExamRuleDao.updateTraFinalExamRule(traFinalExamRuleVO);

		// 2、判断规则类型，若为1-部分随机时，删除原有必考题，添加新的必考题
		String ruleType = (String) paramMap.get("finalExamRuleType");
		if (StringUtils.equals(ruleType, "1")) {
			// 2.1、删除原必考题
			traFinalExamQuestionDao.deleteTraFinalExamQuestionByRuleId(id);
			// 2.2、添加新必考題
			List<Integer> questionIdList = (List<Integer>) paramMap.get("questionIdList");
			addFinalExamQuestion(id, questionIdList, userId);
		}
		return 1;
	}

	private void addFinalExamQuestion(Integer id, List<Integer> questionIdList, Integer userId) {
		List<TraFinalExamQuestionVO> list = new ArrayList<TraFinalExamQuestionVO>();
		TraFinalExamQuestionVO traFinalExamQuestionVO;
		for (Integer questionId : questionIdList) {
			traFinalExamQuestionVO = new TraFinalExamQuestionVO();
			traFinalExamQuestionVO.setQuestionId(questionId);
			traFinalExamQuestionVO.setRuleId(id);
			traFinalExamQuestionVO.setInsertBy(userId);
			list.add(traFinalExamQuestionVO);
		}
		traFinalExamQuestionDao.batchSaveTraFinalExamQuestion(list);
	}
}
