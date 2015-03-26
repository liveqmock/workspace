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
 * @date 2014-6-11 下午12:41:56
 */
public interface TraRuleService {
	public int saveRule(Map<String, Object> paramMap);

	public int updateRule(Map<String, Object> paramMap);

	public List<Map<String, Object>> getQuestionByCoursewareId(Map<String, Object> paramMap);

	public List<Map<String, Object>> getPptByCoursewareId(Map<String, Object> paramMap);

	public Map<String, Object> getRuleByCoursewareId(Map<String, Object> paramMap);

	/**
	 * @Description 根据课件ID查询实操题
	 * @param paramMap
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws 
	 */
	public List<Map<String, Object>> getPracticeQuestionByCoursewareId(Map<String, Object> paramMap);
}
