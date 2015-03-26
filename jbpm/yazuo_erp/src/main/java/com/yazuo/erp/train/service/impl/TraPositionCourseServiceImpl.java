/**
 * @Description 职位-课程关系表相关接口实现
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.train.service.impl;

import java.util.*;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.vo.*;
import com.yazuo.erp.train.dao.*;
import com.yazuo.erp.train.exception.TrainBizException;

/**
 * @Description 职位-课程关系表相关接口实现
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.train.service.TraPositionCourseService;

@Service
public class TraPositionCourseServiceImpl implements TraPositionCourseService {

	@Resource
	private TraPositionCourseDao traPositionCourseDao;

	public int saveTraPositionCourse(TraPositionCourseVO traPositionCourse) {
		return traPositionCourseDao.saveTraPositionCourse(traPositionCourse);
	}

	public int batchInsertTraPositionCourses(Map<String, Object> map) {
		return traPositionCourseDao.batchInsertTraPositionCourses(map);
	}

	public int updateTraPositionCourse(TraPositionCourseVO traPositionCourse) {
		return traPositionCourseDao.updateTraPositionCourse(traPositionCourse);
	}

	public int batchUpdateTraPositionCoursesToDiffVals(Map<String, Object> map) {
		return traPositionCourseDao.batchUpdateTraPositionCoursesToDiffVals(map);
	}

	public int batchUpdateTraPositionCoursesToSameVals(Map<String, Object> map) {
		return traPositionCourseDao.batchUpdateTraPositionCoursesToSameVals(map);
	}

	public int deleteTraPositionCourseById(Integer id) {
		return traPositionCourseDao.deleteTraPositionCourseById(id);
	}

	public int batchDeleteTraPositionCourseByIds(List<Integer> ids) {
		return traPositionCourseDao.batchDeleteTraPositionCourseByIds(ids);
	}

	public TraPositionCourseVO getTraPositionCourseById(Integer id) {
		return traPositionCourseDao.getTraPositionCourseById(id);
	}

	public List<TraPositionCourseVO> getTraPositionCourses(TraPositionCourseVO traPositionCourse) {
		return traPositionCourseDao.getTraPositionCourses(traPositionCourse);
	}

	public List<Map<String, Object>> getTraPositionCoursesMap(TraPositionCourseVO traPositionCourse) {
		return traPositionCourseDao.getTraPositionCoursesMap(traPositionCourse);
	}

	/**
	 * @Title saveTraPositionCourse
	 * @Description 添加职位和课程的关系
	 * @param traPositionCourseList
	 * @param user
	 * @return
	 * @see com.yazuo.erp.train.service.TraPositionCourseService#saveTraPositionCourse(java.util.List,
	 *      com.yazuo.erp.system.vo.SysUserVO)
	 */
	@Override
	public int saveTraPositionCourse(TraPositionCourseVO[] traPositionCourseList, SysUserVO user) {
		Integer userId = user.getId();
		List<TraPositionCourseVO> list=new ArrayList<TraPositionCourseVO>();
		for (TraPositionCourseVO vo : traPositionCourseList) {
			vo.setInsertBy(userId);
			vo.setInsertTime(new Date());
			
			list.add(vo);
		}

		if (null != traPositionCourseList && 0 < traPositionCourseList.length) {
			// 根据课程ID删除原职位和课程的关系
			Integer courseId = traPositionCourseList[0].getCourseId();
			int count = traPositionCourseDao.deleteTraPositionCourseByCourseId(courseId);

			// 根据职位ID列表批量删除原职位和课程的关系
			count = traPositionCourseDao.batchDeleteTraPositionCourseByPositionIds(list);

			// 添加职位和课程的关系
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TraPositionCourseVO.COLUMN_ID, Constant.NOT_NULL);
			map.put(TraPositionCourseVO.COLUMN_COURSE_ID, Constant.NOT_NULL);// 课程ID
			map.put(TraPositionCourseVO.COLUMN_POSITION_ID, Constant.NOT_NULL);// 职位ID
			map.put(TraPositionCourseVO.COLUMN_INSERT_BY, Constant.NOT_NULL);// 添加人
			map.put(TraPositionCourseVO.COLUMN_INSERT_TIME, Constant.NOT_NULL);// 添加时间
			map.put("list", traPositionCourseList);
			count = traPositionCourseDao.batchInsertTraPositionCourses(map);
			if (1 > count) {
				throw new TrainBizException("课程关联职位失败");
			}
		}
		return 1;
	}
}
