/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.train.vo.TraFinalExamQuestionVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface TraFinalExamQuestionDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraFinalExamQuestion(TraFinalExamQuestionVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraFinalExamQuestion(TraFinalExamQuestionVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraFinalExamQuestion(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraFinalExamQuestionVO getTraFinalExamQuestionById(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraFinalExamQuestionVO> getAllTraFinalExamQuestions();

	public int batchSaveTraFinalExamQuestion(List<TraFinalExamQuestionVO> list);

	public List<Map<String, Object>> getTraFinalExamQuestionByRuleId(Map<String, Object> map);

	public int deleteTraFinalExamQuestionByRuleId(Integer ruleId);

	/**
	 * @Description 根据课程ID列表批量删除 tra_final_exam_question 期末考卷必考题
	 * @param list
	 * @throws
	 */
	public int batchDeleteTraFinalExamQuestionByCourseIds(List<Integer> list);

	/**
	 * @Description 根据课件ID批量删除 tra_final_exam_question 期末考卷必考题
	 * @param coursewareIdlist
	 * @throws
	 */
	public int batchDeleteTraFinalExamQuestionByCoursewareId(List<Integer> coursewareIdlist);

	/**
	 * @Description 根据课程Id对应的期末考卷规则和课件ID删除tra_final_exam_question 期末考卷必考题
	 * @param coursewareIdlist
	 * @param courseId
	 * @return
	 * @throws
	 */
	public int deleteTraFinalExamQuestionByCoursewareIdAndCourseId(@Param("list") List<Integer> list,
			@Param("courseId") Integer courseId);
	
	/**
	 * 根据课程ID和试题删除tra_final_exam_question 期末考卷必考题
	 * @param list 课程ID list
	 * @param questionId
	 * @return
	 * @return int
	 * @throws
	 */
	public int deleteTraFinalExamQuestionByCourseIdAndQuestionId(@Param("list") List<Integer> list,
			@Param("questionId") Integer questionId);

	/**
	 * @Description 根据试题ID列表批量删除 tra_final_exam_question 期末考卷必考题
	 * @param questionIdList
	 * @throws 
	 */
	public int batchDeleteTraFinalExamQuestionByQuestionId(List<Integer> questionIdList);
	
}
