package com.yazuo.erp.train.service;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.vo.TraCourseVO;

import java.util.List;
import java.util.Map;

public interface TraCourseService {
	public int saveCourse(TraCourseVO courseVO, SysUserVO user);

	public int updateCourse(TraCourseVO courseVO, SysUserVO user);

	public int deleteCourse(List<Integer> idsList, SysUserVO user);

	public Page<Map<String, Object>> getCourseInfo(int pageNumber, int pageSize);

	public Map<String, Object> getCourseDetail(Integer courseId);

	public List<Map<String, Object>> getAllCoursesLearningInfo();

}
