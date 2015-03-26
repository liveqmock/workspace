/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.*;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TraTeacherStudentDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraTeacherStudent(TraTeacherStudentVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraTeacherStudent(TraTeacherStudentVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraTeacherStudent(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraTeacherStudentVO getTraTeacherStudentById(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraTeacherStudentVO> getAllTraTeacherStudents();

	/**
	 * @Description 查询未分配老师的学生列表
	 * @param params
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public Page<Map<String, Object>> queryStudentListToBeAllocated(Map<String, Object> params);

	/**
	 * @Description 根据老师ID和学生ID查询老师-学生关系表
	 * @param traTeacherStudentVO
	 * @return
	 * @throws
	 */
	public List<TraTeacherStudentVO> queryTraTeacherStudent(TraTeacherStudentVO traTeacherStudentVO);

	/**
	 * @Description 条件查询学生信息列表
	 * @param params
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public Page<Map<String, Object>> queryStudentList(Map<String, Object> params);

	/**
	 * @Description 批量解除师生关系
	 * @param list
	 * @return
	 * @throws
	 */
	public int batchDeleteTraTeacherStudent(List<TraTeacherStudentVO> list);

	/**
	 * @Description 查询老师列表
	 * @param params
	 * @return
	 * @throws
	 */
	public Page<Map<String, Object>> queryTeacherList(Map<String, Object> params);

	/**
	 * 得到老师的ID
	 * 
	 * @param studentId
	 * @return
	 */
	public Integer getTeacherIdByStudentId(Integer studentId);

	/**
	 * @Description 修改师生关系为有效
	 * @param list
	 * @return
	 * @throws
	 */
	public int updateTraTeacherStudentRef(@Param(value = "id") Integer id, @Param(value = "teacherId") Integer teacherId,
			@Param(value = "studentId") Integer studentId);

	/**
	 * @Description 查询老学员信息列表
	 * @param params
	 * @return
	 * @throws
	 */
	public Page<Map<String, Object>> queryOldStaffList(Map<String, Object> params);

	/**
	 * @Description 根据用户ID列表查询详细信息
	 * @param userList
	 * @return
	 * @throws 
	 */
	public Page<Map<String, Object>> queryMemberList(List<SysUserVO> userList);

}
