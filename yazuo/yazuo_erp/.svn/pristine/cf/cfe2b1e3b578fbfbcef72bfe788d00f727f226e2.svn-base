/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.train.dao;

import java.util.*;
import com.yazuo.erp.train.vo.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TraRuleDao {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveTraRule(TraRuleVO traRule);

	/**
	 * 新增多个对象 @return : 影响的行数
	 * 
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertTraRules(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateTraRule(TraRuleVO traRule);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateTraRulesToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateTraRulesToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteTraRuleById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteTraRuleByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	TraRuleVO getTraRuleById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<TraRuleVO> getTraRules(TraRuleVO traRule);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getTraRulesMap(TraRuleVO traRule);

	/**
	 * 通过coursewareId查找考试规则
	 * 
	 * @param couresewareId
	 * @return
	 */

	public TraRuleVO getTraRuleByCoursewareId(Integer couresewareId);

	/**
	 * @parameter id
	 */
	public List<TraRuleVO> getAllTraRules();

	/**
	 * @Description 根据课件ID，考卷类型查询考卷规则
	 * @param coursewareId
	 * @param paperType
	 * @return
	 * @throws
	 */
	public TraRuleVO queryTraRule(@Param(value = "coursewareId") Integer coursewareId,
			@Param(value = "paperType") String paperType);

	/**
	 * @Description 根据课件ID查询考试规则
	 * @param map
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getTraRulesMap(Map<String, Object> map);

	/**
	 * @Description 根据课件ID批量删除 tra_rule 考卷规则
	 * @param coursewareIdlist
	 * @throws
	 */
	public int batchDeleteTraRuleByCoursewareId(List<Integer> coursewareIdlist);
}
