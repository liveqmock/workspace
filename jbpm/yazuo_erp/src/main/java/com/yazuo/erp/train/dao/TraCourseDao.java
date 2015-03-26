/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.train.vo.TraCourseVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface TraCourseDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraCourse(TraCourseVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraCourse(TraCourseVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraCourse(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraCourseVO getTraCourseById(Integer id);

	/**
	 * @parameter id
	 */
	public List<Map<String, Object>> getTraCourses(Map<String, Object> paramMap);

	/**
	 * 批量更新课程有效状态
	 */
	public int batchUpdateTraCourse(List<TraCourseVO> list);

	public List<Map<String, Object>> getTraCourseInfo();

	public List<Map<String, Object>> getAllCoursesLearningInfo();

	/**
	 * @Description 查询有效的课程信息
	 * @return
	 * @throws 
	 */
	public List<Map<String, Object>> queryCourse();

    /**
     * 通过studentId得到CourseVO
     * @param studentId
     * @return
     */
    public TraCourseVO getCourseVOByStudentId(Integer studentId);
}
