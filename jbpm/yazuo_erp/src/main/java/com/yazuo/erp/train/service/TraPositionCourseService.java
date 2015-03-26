/**
 * @Description 职位-课程关系表相关接口
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

import java.util.*;

import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.vo.*;
import com.yazuo.erp.train.dao.*;

/**
 * @Description 职位-课程关系表相关接口
 * @author erp team
 * @date
 */
public interface TraPositionCourseService {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveTraPositionCourse(TraPositionCourseVO traPositionCourse);

	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertTraPositionCourses(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateTraPositionCourse(TraPositionCourseVO traPositionCourse);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateTraPositionCoursesToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateTraPositionCoursesToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteTraPositionCourseById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteTraPositionCourseByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	TraPositionCourseVO getTraPositionCourseById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<TraPositionCourseVO> getTraPositionCourses(TraPositionCourseVO traPositionCourse);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getTraPositionCoursesMap(TraPositionCourseVO traPositionCourse);

	/**
	 * @Description 添加职位和课程的关系
	 * @param traPositionCourseList
	 * @param user
	 * @return
	 * @throws 
	 */
	int saveTraPositionCourse(TraPositionCourseVO[] traPositionCourseList, SysUserVO user);

}
