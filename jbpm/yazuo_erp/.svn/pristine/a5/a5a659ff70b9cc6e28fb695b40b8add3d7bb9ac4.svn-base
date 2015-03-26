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

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.train.vo.*;
import com.yazuo.erp.train.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TraLearningProgressDao {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveTraLearningProgress(TraLearningProgressVO traLearningProgress);

	/**
	 * 新增多个对象 @return : 影响的行数
	 * 
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertTraLearningProgresss(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateTraLearningProgress(TraLearningProgressVO traLearningProgress);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateTraLearningProgresssToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateTraLearningProgresssToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteTraLearningProgressById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteTraLearningProgressByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	TraLearningProgressVO getTraLearningProgressById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<TraLearningProgressVO> getTraLearningProgresss(TraLearningProgressVO traLearningProgress);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getTraLearningProgresssMap(TraLearningProgressVO traLearningProgress);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraLearningProgress(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraLearningProgressVO> getAllTraLearningProgresss();

	public List<TraLearningProgressVO> getTraLearningProgresssByCourseId(Map<String, Object> map);

	public List<TraLearningProgressVO> getTraLearningProgresssByCoursewareId(Map<String, Object> map);

	/**
	 * @Description 通过期末考试，修改学习进度表的课程状态和学习进度
	 * @param paperId
	 * @param courseStatus
	 * @param progressType
	 * @param updateBy
	 * @return
	 * @throws
	 */
	public int updateTraLearningProgressByPaperId(@Param("paperId") Integer paperId, @Param("courseStatus") String courseStatus,
			@Param("progressType") String progressType, @Param("updateBy") Integer updateBy);

	/**
	 * @Description 课程延时，修改课程截止时间
	 * @param vo
	 * @throws
	 */
	public int updateCourseEndTime(TraLearningProgressVO vo);

	/**
	 * 待删除方法 通过StudentId得到CourseId
	 * 
	 * @param StudentId
	 * @return
	 */
	@Deprecated
	public Integer getCourseIdByStudentId(Integer StudentId);

	/**
	 * 得到已经考试的学习进度ID
	 * 
	 * @param studentId
	 * @return
	 */
	public TraLearningProgressVO getLearningProgressVOForExamByStudentId(Integer studentId);

	/**
	 * 得到学习进度ID
	 * 
	 * @param studentId
	 * @return
	 */
	public TraLearningProgressVO getLearningProgressVOByStudentId(Integer studentId);

	/**
	 * @Description 根据课程ID、学生ID查询学习进度记录
	 * @param courseId
	 * @param studentId
	 * @return
	 * @throws
	 */
	public TraLearningProgressVO queryTraLearningProcess(@Param("courseId") Integer courseId,
			@Param("studentId") Integer studentId);

	/**
	 * 得到当前学生进度
	 * 
	 * @param courseId
	 * @param studentId
	 * @return
	 */
	public TraLearningProgressVO getCurrentTraLearningProcess(@Param("courseId") Integer courseId,
			@Param("studentId") Integer studentId);

	/**
	 * @Description 根据学生ID列表和课程ID查询学习进度表，查询学生的未毕业的课程，且不为指定课程的记录条数
	 * @param courseId
	 * @param studentIdList
	 * @return
	 * @throws
	 */
	public int getTraLearningProgressOfLearning(@Param("courseId") Integer courseId,
			@Param("studentIdList") List<Integer> studentIdList);

	/**
	 * @Description 根据学生查询未毕业的学习进度记录
	 * @param studentId
	 * @return
	 * @throws
	 */
	public TraLearningProgressVO queryTraLearningProcessByStudentId(Integer studentId);

	/**
	 * @Description 根据学生ID查询最近一条学习进度
	 * @param studentId
	 * @return
	 * @throws
	 */
	public TraLearningProgressVO getLastLearningProgressVOByStudentId(Integer studentId);

	/**
	 * @Description 根据员工ID和课件ID查询，当前正在学习的学习进度个数
	 * @param input
	 * @return
	 * @throws
	 */
	int getCountOfTraLearningProgresssByCoursewareIdAndStudentId(Map<String, Object> input);

	/**
	 * @Description 根据员工ID和课件ID查询，历史中毕业的学习进度个数
	 * @param input
	 * @return
	 * @throws
	 */
	int getStudiedCountOfTraLearningProgresssByCoursewareIdAndStudentId(Map<String, Object> input);

	/**
	 * @Description 批量移除学员，学习进度的课程状态修改为4-异常终止
	 * @param list
	 * @return
	 * @throws
	 */
	int batchUpdateTraLearningProgresssToExceptionEnding(@Param("list") List<Integer> list, @Param("userId") Integer userId);

	/**
	 * @Description 根据学习进度ID查询学习次数，考试次数，和最好成绩
	 * @param learningProgressId
	 * @param coursewareId
	 * @throws 
	 */
	Map<String, Object> getCountAndBestScore(@Param("learningProgressId") Integer learningProgressId,@Param("coursewareId")  Integer coursewareId);

	/**
	 * @Description 根据课程状态和课件ID查询记录数
	 * @param input
	 * @return
	 * @throws 
	 */
	List<Map<String, Object>> getCountByCourseStatusAndCourseWareId(Map<String, Object> input);

	/**
	 * @Description 根据学生ID统计正在学习的课件数
	 * @param studentId
	 * @return
	 * @throws 
	 */
	int getCoursewareCountByStudentId(Integer studentId);

	/**
	 * @Description 根据用户ID查询基本信息，学习进度
	 * @param params
	 * @return
	 * @return Page<Map<String,Object>>
	 * @throws 
	 */
	Page<Map<String, Object>> queryLearningProcessList(Map<String, Object> params);

	/**
	 * @Description 查询课件是否有老学员正在学习（查询正在学习的学习进度个数）
	 * @param coursewareId
	 * @return
	 * @return int
	 * @throws 
	 */
	int getLearningCountOfOldStaffByCoursewareId(Integer coursewareId);

}
