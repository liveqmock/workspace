/**
 * @Description 学生管理相关服务
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 		gaoshan		2014-06-04	创建文档
 * 
 */
package com.yazuo.erp.train.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;

/**
 * @Description 学生管理相关服务接口类
 * @author gaoshan
 * @date 2014-6-3 下午6:44:07
 */
public interface StudentManagementService {
	
	/**
	 * @Description 查询有效的职位信息
	 * @param params 
	 * @return
	 * @throws
	 */
	List<Map<String,Object>> queryPosition(Map<String, Object> params);

	/**
	 * @Description 查询有效的课程信息
	 * @return
	 * @throws
	 */
	List<Map<String, Object>> queryCourse();

	/**
	 * @Description 根据老师ID查询待办事项列表
	 * @param teacherId
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	Page<Map<String, Object>> queryToDoListByTeacherId(Integer teacherId);

	/**
	 * @Description 查询未分配老师的学生列表
	 * @param params
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	Page<Map<String, Object>> queryStudentListToBeAllocated(Map<String, Object> params);

	/**
	 * @Description 添加老师-学生关系
	 * @param traTeacherStudentVO
	 * @return int
	 * @throws
	 */
	int saveTraTeacherStudent(Map<String, Object> params);

	/**
	 * @Description 条件查询学生信息列表
	 * @param params
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	Page<Map<String, Object>> queryStudentList(Map<String, Object> params);

	/**
	 * @Description 根据学生ID查看流水
	 * @param studentId
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	Page<Map<String, Object>> queryTraStudentRecordByStudentId(Integer studentId);

	/**
	 * @Description 解除师生关系
	 * @param traTeacherStudentVO
	 * @return int
	 * @throws
	 */
	int deleteTraTeacherStudent(TraTeacherStudentVO traTeacherStudentVO);

	/**
	 * @Description 批量解除师生关系
	 * @param traTeacherStudentList
	 * @return int
	 * @throws
	 */
	int batchDeleteTraTeacherStudent(List<TraTeacherStudentVO> list);

	/**
	 * @Description 根据试卷ID查询PPT、录音信息和评语信息
	 * @param paperId
	 * @return Map<String,Object>
	 * @throws
	 */
	Map<String, Object> queryPptExamInfo(Integer paperId);

	/**
	 * @Description 根据试卷ID进行PPT录音评分
	 * @param params
	 * @return int
	 * @throws
	 */
	int updatePptPaper(Map<String, Object> params);

	/**
	 * @Description 根据试卷ID进行真人互动评分
	 * @param params
	 * @return int
	 * @throws
	 */
	int updateMark(Map<String, Object> params);

	/**
	 * @Description 查询老师列表
	 * @param params
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	Page<Map<String, Object>> queryTeacherList(Map<String, Object> params);

	/**
	 * @Description 课程延时
	 * @param params
	 * @return int
	 * @throws
	 */
	int updateCourseEndTime(Map<String, Object> params);

	/**
	 * @Description 查看评语
	 * @param params
	 * @return
	 * @throws 
	 */
	Map<String, Object> queryCommentOfPaper(Map<String, Object> params);

	/**
	 * @Description 新学员创建时，根据所属职位默认创建学习进度
	 * @param sysUserVO
	 * @param userId session中用户ID
	 * @throws 
	 */
	void saveTraLearningProgress(SysUserVO sysUserVO, Integer userId);

	/**
	 * @Description 新学员，所属职位进行修改时，如果新老职位所对应的课程不同，则原学习进度置为4-异常终止，并建立新的学习进度
	 * @param sysUserVO
	 * @param oldPositionId 
	 * @param userId
	 * @throws 
	 */
	void updateTraLearningProgressToAbnormalTermination(SysUserVO sysUserVO, Integer oldPositionId, Integer userId);

	/**
	 * @Description 更新学生流水表的学习进度，针对大于学习进度创建时间的流水，进度ID修改为最近的学习进度（暂时使用，不通用）
	 * @param int
	 * @return
	 * @throws 
	 */
	int updateRecord(Map<String, Object> params);

	/**
	 * @Description 修正生产数据，修正学生进度表，将定时器跑错的数据（已毕业的改为超时）修正正常（已经毕业）
	 * @param params
	 * @return
	 * @throws 
	 */
	int updateLearningProcess(Map<String, Object> params);

}
