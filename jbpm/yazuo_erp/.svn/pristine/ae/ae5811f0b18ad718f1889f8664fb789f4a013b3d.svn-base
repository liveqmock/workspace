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
import com.yazuo.erp.train.dao.TraPptDao;
import com.yazuo.erp.train.dao.TraQuestionDao;
import com.yazuo.erp.train.dao.TraRequiredQuestionDao;
import com.yazuo.erp.train.dao.TraRuleDao;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.erp.train.service.TraRuleService;
import com.yazuo.erp.train.vo.TraRequiredQuestionVO;
import com.yazuo.erp.train.vo.TraRuleVO;

/**
 * @Description
 * @author yazuo
 * @date 2014-6-11 下午12:42:17
 */
@Service
public class TraRuleServiceImpl implements TraRuleService {

	@Resource
	private TraRuleDao traRuleDao;

	@Resource
	private TraRequiredQuestionDao traRequiredQuestionDao;

	@Resource
	private TraQuestionDao traQuestionDao;

	@Resource
	private TraPptDao traPptDao;

	/**
	 * @Title saveRule
	 * @Description
	 * @param paramMap
	 * @return
	 */
	@Override
	public int saveRule(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();
		
		String paperType = (String) paramMap.get("paperType");
		if (null == paperType || "".equals(paperType)) {
			throw new TrainBizException("请选择考卷类型");
		}
		
		String ruleType = (String) paramMap.get("ruleType");
		if (null == ruleType || "".equals(ruleType)) {
			throw new TrainBizException("请选择组卷规则类型");
		}
		
		// 1、添加考卷规则
		TraRuleVO traRuleVO = getTraRuleVO(paramMap, userId);
		traRuleDao.saveTraRule(traRuleVO);

		// 2、部分随机时，批量添加必考题
		if (StringUtils.equals("1", ruleType)) {
			List<Integer> questionIdList = (List<Integer>) paramMap.get("questionIdList");
			List<TraRequiredQuestionVO> list = new ArrayList<TraRequiredQuestionVO>();
			TraRequiredQuestionVO traRequiredQuestionVO;
			for (Integer questionId : questionIdList) {
				traRequiredQuestionVO = new TraRequiredQuestionVO();
				traRequiredQuestionVO.setQuestionId(questionId);
				traRequiredQuestionVO.setRuleId(traRuleVO.getId());
				traRequiredQuestionVO.setInsertBy(userId);
				list.add(traRequiredQuestionVO);
			}
			traRequiredQuestionDao.batchSaveTraRequiredQuestion(list);
		}
		return 1;
	}

	private TraRuleVO getTraRuleVO(Map<String, Object> paramMap, Integer userId) {
		TraRuleVO traRuleVO = new TraRuleVO();
		String paperType = getPaperType((String) paramMap.get("paperType"));
		traRuleVO.setPaperType(paperType);
		traRuleVO.setIsShortAnswer("0");// 不开启
		traRuleVO.setTimeLimit((String) paramMap.get("timeLimit"));
		String total = (String) paramMap.get("total");
		if (!StringUtils.isEmpty(total)) {
			traRuleVO.setTotal(new BigDecimal(total));
		}
		traRuleVO.setRuleType((String) paramMap.get("ruleType"));
		traRuleVO.setPassingScore(new BigDecimal((String) paramMap.get("passingScore")));
		traRuleVO.setIsTest((String) paramMap.get("isTest"));
		traRuleVO.setInsertBy(userId);
		traRuleVO.setUpdateBy(userId);
		traRuleVO.setCoursewareId((Integer) paramMap.get("coursewareId"));
		return traRuleVO;
	}

	/**
	 * @return void
	 * @throws
	 */
	private String getPaperType(String paperType) {
		String type = null;
		if (StringUtils.equals("write", paperType)) {
			type = "0";
		} else if (StringUtils.equals("ppt", paperType)) {
			type = "1";
		} else if (StringUtils.equals("practice", paperType)) {
			type = "2";
		} else {
			type = null;
		}
		return type;
	}

	/**
	 * @Title getQuestionByCoursewareId
	 * @Description
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getQuestionByCoursewareId(Map<String, Object> paramMap) {
		return traQuestionDao.getTraQuestionsMap(paramMap);
	}

	/**
	 * @Title getPptByCoursewareId
	 * @Description
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPptByCoursewareId(Map<String, Object> paramMap) {
		return traQuestionDao.getTraPptsByCoursewareId(paramMap);
	}

	/**
	 * @Title getRuleByCoursewareId
	 * @Description 根据课件ID查询考试规则
	 * @param paramMap
	 * @return
	 */
	@Override
	public Map<String, Object> getRuleByCoursewareId(Map<String, Object> paramMap) {
		Map<String, Object> rulesMap = traRuleDao.getTraRulesMap(paramMap);
		if (null != rulesMap) {
			String ruleType = (String) rulesMap.get("rule_type");
			if (StringUtils.equals("1", ruleType)) {
				Integer ruleId = (Integer) rulesMap.get("id");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ruleId", ruleId);
				List<Map<String, Object>> requiredQuestionList = new ArrayList<Map<String, Object>>();
				if (StringUtils.equals("0", (String) rulesMap.get("paper_type"))) {
					requiredQuestionList = traRequiredQuestionDao.getTraRequiredQuestionByRuleId(map);
				} else if (StringUtils.equals("1", (String) rulesMap.get("paper_type"))) {
					requiredQuestionList = traRequiredQuestionDao.getTraRequiredQuestionPptByRuleId(map);
				} else if (StringUtils.equals("2", (String) rulesMap.get("paper_type"))) {
					requiredQuestionList = traRequiredQuestionDao.getTraRequiredQuestionByRuleId(map);
				}
				rulesMap.put("requiredQuestions", requiredQuestionList);
			}
		}
		return rulesMap;
	}

	/**
	 * @Title updateRule
	 * @Description
	 * @param paramMap
	 * @return
	 */
	@Override
	public int updateRule(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();
		// 1、修改考卷规则
		Integer id = (Integer) paramMap.get("id");
		TraRuleVO traRuleVO = getUpdateTraRuleVO(paramMap, userId);
		traRuleVO.setId(id);
		traRuleDao.updateTraRule(traRuleVO);

		// 2、删除原必考题
		traRequiredQuestionDao.deleteTraRequiredQuestionByRuleId(id);

		// 3、判断规则类型，若为1-部分随机时，添加新的必考题
		String ruleType = (String) paramMap.get("ruleType");
		if (StringUtils.equals(ruleType, "1")) {
			List<Integer> questionIdList = (List<Integer>) paramMap.get("questionIdList");
			addRequiredQuestion(id, questionIdList, userId);
		}
		return 1;

	}

	/**
	 * @Description
	 * @param id
	 * @param questionIdList
	 * @return void
	 * @throws
	 */
	private void addRequiredQuestion(Integer id, List<Integer> questionIdList, Integer userId) {
		List<TraRequiredQuestionVO> list = new ArrayList<TraRequiredQuestionVO>();
		TraRequiredQuestionVO traRequiredQuestionVO;
		for (Integer questionId : questionIdList) {
			traRequiredQuestionVO = new TraRequiredQuestionVO();
			traRequiredQuestionVO.setQuestionId(questionId);
			traRequiredQuestionVO.setRuleId(id);
			traRequiredQuestionVO.setInsertBy(userId);
			list.add(traRequiredQuestionVO);
		}
		traRequiredQuestionDao.batchSaveTraRequiredQuestion(list);
	}

	/**
	 * @param paramMap
	 * @return void
	 * @throws
	 */
	private TraRuleVO getUpdateTraRuleVO(Map<String, Object> paramMap, Integer userId) {
		TraRuleVO traRuleVO = new TraRuleVO();
		traRuleVO.setTimeLimit((String) paramMap.get("timeLimit"));
		Object total = paramMap.get("total");
		if (total != null) {
			traRuleVO.setTotal(new BigDecimal((String) total));
		}
		traRuleVO.setRuleType((String) paramMap.get("ruleType"));
		String paperType = getPaperType((String) paramMap.get("paperType"));
		traRuleVO.setPaperType(paperType);
		traRuleVO.setPassingScore(new BigDecimal((String) paramMap.get("passingScore")));
		traRuleVO.setIsTest((String) paramMap.get("isTest"));
		traRuleVO.setUpdateBy(userId);
		return traRuleVO;
	}

	/**
	 * @Title getPracticeQuestionByCoursewareId
	 * @Description 根据课件ID查询实操题
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.train.service.TraRuleService#getPracticeQuestionByCoursewareId(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> getPracticeQuestionByCoursewareId(Map<String, Object> paramMap) {
		return traQuestionDao.getPracticeQuestionByCoursewareId(paramMap);
	}

}
