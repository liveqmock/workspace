/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.*;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.train.vo.TraStudentRecordVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TraStudentRecordDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraStudentRecord(TraStudentRecordVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraStudentRecord(TraStudentRecordVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraStudentRecord(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraStudentRecordVO getTraStudentRecordById(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraStudentRecordVO> getAllTraStudentRecords();

	/**
	 * @Description 根据老师ID查询待办事项列表
	 * @param teacherId
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public Page<Map<String, Object>> queryToDoListByTeacherId(@Param("teacherId") Integer teacherId);

	/**
	 * @Description 根据学生ID查看流水
	 * @param studentId
	 * @return
	 * @throws
	 */
	public Page<Map<String, Object>> queryTraStudentRecordByStudentId(Integer studentId);

	/**
	 * @Description 过滤掉学过的课件Id
	 * @param studentId
	 * @param coursewareIds
	 * @param studentId
	 * @param learningProgressId
	 * @return
	 * @throws
	 */
	public List<Integer> getStudiedCoursewareIds(@Param(value = "studentId") Integer studentId, List<Integer> coursewareIds,
			@Param(value = "courseId") Integer courseId, @Param(value = "learningProgressId") Integer learningProgressId);

	/**
	 * @Description 根据试卷ID、课件ID查询学生的流水信息
	 * @param paperId
	 * @param coursewareId
	 * @return
	 * @throws
	 */
	public TraStudentRecordVO queryTraStudentRecord(@Param(value = "paperId") Integer paperId,
			@Param(value = "coursewareId") Integer coursewareId);

	/**
	 * 得到学生学习此课程的次数
	 * 
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId
	 * @return
	 */
	public Integer getStudentRecordStudyCount(@Param(value = "studentId") Integer studentId,
			@Param(value = "courseId") Integer courseId, @Param(value = "coursewareId") Integer coursewareId,
			@Param(value = "learningProgressId") Integer learningProgressId);

	/**
	 * 得到学生学习此课程的次数
	 * 
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @param integer
	 * @return
	 */
	public Integer getStudentRecordExamCount(@Param(value = "studentId") Integer studentId,
			@Param(value = "courseId") Integer courseId, @Param(value = "coursewareId") Integer coursewareId,
			@Param(value = "learningProgressId") Integer learningProgressId);

	/**
	 * 考试记录
	 * 
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @param integer
	 * @return
	 */
	public TraStudentRecordVO getStudentExamRecord(@Param(value = "studentId") Integer studentId,
			@Param(value = "courseId") Integer courseId, @Param(value = "coursewareId") Integer coursewareId,
			@Param(value = "learningProgressId") Integer learningProgressId);

	/**
	 * 学习记录
	 * 
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId
	 * @return
	 */
	public TraStudentRecordVO getStudentRecord(@Param(value = "studentId") Integer studentId,
			@Param(value = "courseId") Integer courseId, @Param(value = "coursewareId") Integer coursewareId,
			@Param(value = "learningProgressId") Integer learningProgressId);

	/**
	 * 期末考试记录
	 * 
	 * @param studentId
	 * @param courseId
	 * @param integer
	 * @return
	 */
	public TraStudentRecordVO getFinalExamStudentRecord(@Param(value = "studentId") Integer studentId,
			@Param(value = "courseId") Integer courseId, @Param(value = "learningProgressId") Integer learningProgressId);

	/**
	 * 查询学生学习课程的历史记录
	 * 
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @param integer
	 * @return
	 */
	public List<TraStudentRecordVO> getStudentRecordsByCoursewareId(@Param("studentId") Integer studentId,
			@Param("courseId") Integer courseId, @Param("coursewareId") Integer coursewareId,
			@Param(value = "learningProgressId") Integer learningProgressId);

	/**
	 * @Description 更新学生流水表中进度ID为当前最近的进度ID
	 * @param entity
	 * @return
	 * @throws
	 */
	public int updateLearningProgressIdOfTraStudentRecord(TraStudentRecordVO entity);

	/**
	 * @Description 查询所有的真人互动评分的流水记录
	 * @return
	 * @throws
	 */
	public List<TraStudentRecordVO> getTraStudentRecordsOfInterview();

	/**
	 * @Description 根据学习进度ID、课件ID、学生ID查询第一次学习的时间
	 * @param params
	 * @return
	 * @return Map<String,Object>
	 * @throws 
	 */
	public Map<String, Object> queryBeginTime(Map<String, Object> params);

	/**
	 * @Description 根据学员ID列表查询待办事项
	 * @param userIdList
	 * @return
	 * @return Page<Map<String,Object>>
	 * @throws 
	 */
	public Page<Map<String, Object>> queryToDoListByUserIdList(List<Integer> userIdList);

	/**
	 * @Description 根据学员ID列表查询待办事项数量
	 * @param userIdList
	 * @return
	 * @return int
	 * @throws 
	 */
	public int queryToDoListCountByUserIdList(List<Integer> userIdList);

}
