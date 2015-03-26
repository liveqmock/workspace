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
package com.yazuo.erp.train.service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author yazuo
 * @date 2014-6-11 下午1:18:56
 */
public interface TraFinalExamRuleService {
	public int saveFinalExamRule(Map<String, Object> paramMap);

	public List<Map<String, Object>> getQuestionByCourseId(Map<String, Object> paramMap);

	public Map<String, Object> getFinalExamRuleByCourseId(Map<String, Object> paramMap);

	public int updateFinalExamRule(Map<String, Object> paramMap);
}
