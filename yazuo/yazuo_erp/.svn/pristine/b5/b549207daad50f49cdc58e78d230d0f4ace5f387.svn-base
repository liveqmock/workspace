/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.train.vo.CoursewareProgressVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.train.vo.TraCoursewareVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface TraCoursewareDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraCourseware(TraCoursewareVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraCourseware(TraCoursewareVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraCourseware(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraCoursewareVO getTraCoursewareById(Integer id);

	/**
	 * 按id列表查询TrCourseware
	 * 
	 * @param coursewareIds
	 * @return
	 */
	public List<TraCoursewareVO> getTraCoursewaresByIds(List<Integer> coursewareIds);

	/**
	 * 按id列表查询TrCourseware
	 * 
	 * @param courseId
	 * @return
	 */
	public List<CoursewareProgressVO> getTraCoursewaresByCourseId(Integer courseId);

	/**
	 * @parameter id
	 */
	public List<TraCoursewareVO> getAllTraCoursewares();

	public List<Map<String, Object>> getTraCoursewaresMap(Map<String, Object> paramMap);

	/**
	 * 批量更新课件
	 */
	public int batchUpdateTraCourseware(List<TraCoursewareVO> list);

	public List<Map<String, Object>> getCourseByCoursewareId(Integer coursewareId);

	/**
	 * @Description 查询课程的第一个有效课件
	 * @param courseId
	 * @return
	 * @throws
	 */
	public Map<String, Object> queryFirstCourseware(Integer courseId);

	public Map<String, Object> getCoursewareInfoByCoursewareId(Integer coursewareId);

	/**
	 * @Description 学生列表中，学生的学习进度展现
	 * @param courseId
	 * @param coursewareId
	 * @return
	 * @throws 
	 */
	public List<Map<String, Object>> getTraCoursewaresListByCourseId(@Param(value = "courseId") Integer courseId,@Param(value = "coursewareId") Integer coursewareId);

	/**
	 * @Description 查询有效的课件信息
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public List<Map<String, Object>> getTraCoursewareList(Map<String, Object> paramMap);

	/**
	 * @Description 根据课件名称模糊查询有效的课件
	 * @param params 
	 * @return
	 * @throws 
	 */
	public Page<Map<String, Object>> queryCoursewareList(Map<String, Object> params);

	/**
	 * @Description 根据老员工ID查询课件列表（正在学习的课件）
	 * @param studentId
	 * @return
	 * @return List<CoursewareProgressVO>
	 * @throws 
	 */
	public List<CoursewareProgressVO> getTraCoursewaresByOldStaffId(Integer studentId);

	/**
	 * @Description 查询老员工的课件（历史课件）
	 * @param studentId
	 * @return
	 * @return List<CoursewareProgressVO>
	 * @throws 
	 */
	public List<CoursewareProgressVO> getHistoryCoursewareByOldStaffId(Integer studentId);
}
