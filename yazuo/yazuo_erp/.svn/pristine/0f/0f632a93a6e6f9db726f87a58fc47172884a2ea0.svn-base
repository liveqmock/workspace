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

import com.yazuo.erp.train.vo.TraQuestionVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface TraQuestionDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraQuestion(TraQuestionVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraQuestion(TraQuestionVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraQuestion(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraQuestionVO getTraQuestionById(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraQuestionVO> getAllTraQuestions();

	public List<Map<String, Object>> getTraQuestionByOption(Map<String, Object> map);

	public List<Map<String, Object>> getTraQuestionByOptionAndCoursewareName(Map<String, Object> map);

	public Map<String, Object> getQuestionByQId(Integer questionId);

	/**
	 * 批量更新试题有效状态
	 */
	public int batchUpdateTraQuestion(List<TraQuestionVO> list);

	public List<Map<String, Object>> getTraQuestionsMap(Map<String, Object> map);

	public List<Map<String, Object>> getTraQuestionsByCourseIdMap(Map<String, Object> map);

	/**
	 * 根据coursewareId得到TraQuestionVO列表
	 * 
	 * @param coursewareId
	 * @param paperType
	 * @return
	 */
	public List<TraQuestionVO> getAllQuestionVOByCoursewareId(@Param("coursewareId") Integer coursewareId,
			@Param("paperType") String paperType);

	/**
	 * TraQuestionVO的id列表,查询TraQuestionVO列表
	 * 
	 * @param questionIds
	 * @return
	 */
	public List<TraQuestionVO> getQuestionVOsByIds(List<Integer> questionIds);

	public List<Map<String, Object>> getQuestionOptionsById(Integer questionId);

	/**
	 * 
	 * @Description 根据课件ID更改试题状态
	 * @param map
	 * @return
	 * @return int
	 * @throws
	 */
	public int updateTraQuestionByCoursewareId(Map<String, Object> map);

	public List<Map<String, Object>> getPptDtlByPptId(Map<String, Object> map);

	/**
	 * 得到所有课程的ids
	 * 
	 * @param courseId
	 * @return
	 */
	public List<Integer> getAllWrittenQuestionIdsForCourse(Integer courseId);

	public List<Map<String, Object>> getTraPptsByCoursewareId(Map<String, Object> map);

	/**
	 * @Description 根据课件ID批量删除 tra_question 试题（修改is_enable为'0'）
	 * @param coursewareIdlist
	 * @throws
	 */
	public int batchUpdateTraQuestionByCoursewareId(List<Integer> coursewareIdlist);

	/**
	 * @Description 根据试题ID列表批量删除 tra_question 试题（修改is_enable为'0'）
	 * @param questionIdList
	 * @throws
	 */
	public int batchUpdateTraQuestionByIdList(List<Integer> questionIdList);

	/**
	 * @Description 根据课件ID查询实操题
	 * @param paramMap
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> getPracticeQuestionByCoursewareId(Map<String, Object> paramMap);

}
