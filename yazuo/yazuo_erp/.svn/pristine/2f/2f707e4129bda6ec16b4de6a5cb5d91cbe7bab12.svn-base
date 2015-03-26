/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.train.vo.TraQuestionVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.train.vo.TraRequiredQuestionVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface TraRequiredQuestionDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraRequiredQuestion(TraRequiredQuestionVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraRequiredQuestion(TraRequiredQuestionVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraRequiredQuestion(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraRequiredQuestionVO getTraRequiredQuestionById(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraRequiredQuestionVO> getAllTraRequiredQuestions();

	public int batchSaveTraRequiredQuestion(List<TraRequiredQuestionVO> list);

	/**
	 * 得到试题ID列表
	 * 
	 * @param coursewareId
	 * @return
	 */
	public List<Integer> getQuestionIdsByCoursewareId(Integer coursewareId);

	/**
	 * 
	 * @param coursewareId
	 * @return
	 */
	public TraRequiredQuestionVO getOneQuestionIdByCoursewareId(Integer coursewareId);

	public List<Map<String, Object>> getTraRequiredQuestionByRuleId(Map<String, Object> map);

	public int deleteTraRequiredQuestionByRuleId(Integer ruleId);

	public List<Map<String, Object>> getTraRequiredQuestionPptByRuleId(Map<String, Object> map);

	/**
	 * @Description 根据课件ID批量删除 tra_required_question 考卷必考题
	 * @param coursewareIdlist
	 * @throws
	 */
	public int batchDeleteTraRequiredQuestionByCoursewareId(List<Integer> coursewareIdlist);

	/**
	 * 
	 * @Description 根据课件ID和试题ID删除考卷必考题
	 * @param questionId
	 * @param coursewareId
	 * @return
	 * @throws
	 */
	public int deleteTraRequiredQuestionByRuleIdAndQuestionId(@Param("questionId") Integer questionId,
			@Param("coursewareId") Integer coursewareId);

	/**
	 * @Description 根据试题ID列表批量删除 tra_required_question 考卷必考题
	 * @param questionIdList
	 * @throws 
	 */
	public int batchDeleteTraRequiredQuestionByQuestionIdList(List<Integer> questionIdList);

}
