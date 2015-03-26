package com.yazuo.erp.train.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.dao.TraCourseCoursewareDao;
import com.yazuo.erp.train.dao.TraFinalExamQuestionDao;
import com.yazuo.erp.train.dao.TraLearningProgressDao;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.erp.train.service.TraCourseCoursewareService;
import com.yazuo.erp.train.vo.TraCourseCoursewareVO;
import com.yazuo.erp.train.vo.TraLearningProgressVO;

@Service
public class TraCourseCoursewareServiceImpl implements TraCourseCoursewareService {

	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

	@Resource
	private TraLearningProgressDao traLearningProgressDao;

	@Resource
	private TraFinalExamQuestionDao traFinalExamQuestionDao;

	@Override
	public boolean saveCourseware(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();

		Integer courseId = (Integer) paramMap.get("courseId");
		// 1、判断该课程是否有人正在学习，若是，则不允许
		List<TraLearningProgressVO> learningProgressList = this.getLearningCourseList(courseId);
		if (null != learningProgressList && learningProgressList.size() > 0) {
			throw new TrainBizException("此课程已有学生在学习中，不允许修改");
		}

		List<Map<String, Object>> coursewareInfos = (List<Map<String, Object>>) paramMap.get("coursewareInfos");

		// 上送的新课件ID集合
		List<Integer> newCoursewareIdList = getCoursewareIdNew(coursewareInfos);

		// 课程对应的原课件ID集合
		List<Integer> oldCoursewareIdList = getCoursewareIdOld(courseId);

		// 2、删除课程下全部课件
		traCourseCoursewareDao.deleteCourseCoursewareByCourseId(courseId);
		// 3、批量增加课件
		if (null != coursewareInfos && coursewareInfos.size() > 0) {
			List<TraCourseCoursewareVO> list = new ArrayList<TraCourseCoursewareVO>();
			TraCourseCoursewareVO entity;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("courseId", courseId);
			int count = traCourseCoursewareDao.getTraCourseCoursewareCount(map);

			for (int i = 0; i < coursewareInfos.size(); i++) {
				entity = getCourseCoursewareVO(courseId, coursewareInfos.get(i), count, i, userId);
				list.add(entity);
			}
			traCourseCoursewareDao.batchSaveCourseCourseware(list);
		}

		// 4、过滤需与课程解除关系的课件ID
		List<Integer> RemoveRelationsCoursewareIdList = getRemoveRelationsCoursewareId(newCoursewareIdList, oldCoursewareIdList);

		// 5、删除该课程对应的期末考卷必考题中解除关系的课件的必考题
		if (null != RemoveRelationsCoursewareIdList && RemoveRelationsCoursewareIdList.size() > 0) {
			deleteFinalExamQuestion(RemoveRelationsCoursewareIdList, courseId);
		}

		return true;
	}

	/**
	 * @Description 删除该课程对应的期末考卷必考题中解除关系的课件的必考题
	 * @param removeRelationsCoursewareIdList
	 * @param courseId
	 * @return void
	 * @throws
	 */
	private void deleteFinalExamQuestion(List<Integer> removeRelaCoursewareIdList, Integer courseId) {
		traFinalExamQuestionDao.deleteTraFinalExamQuestionByCoursewareIdAndCourseId(removeRelaCoursewareIdList, courseId);
	}

	/**
	 * @Description 过滤解除关系的课件ID
	 * @param newCoursewareIdList
	 * @param oldCoursewareIdList
	 * @return
	 * @return List<Integer>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private List<Integer> getRemoveRelationsCoursewareId(List<Integer> newCoursewareIdList, List<Integer> oldCoursewareIdList) {
		Collection<Integer> disjunction = CollectionUtils.disjunction(newCoursewareIdList, oldCoursewareIdList);// 差异
		Collection<Integer> subtract = CollectionUtils.subtract(disjunction, newCoursewareIdList);
		List<Integer> removeRelationsCoursewareIdList = (List<Integer>) subtract;
		return removeRelationsCoursewareIdList;
	}

	/**
	 * @Description 查询课程对应的课件ID
	 * @param courseId
	 * @return
	 * @return List<Integer>
	 * @throws
	 */
	private List<Integer> getCoursewareIdOld(Integer courseId) {
		return traCourseCoursewareDao.getCoursewareIdByCourseId(courseId);
	}

	/**
	 * 
	 * @Description TODO
	 * @param coursewareInfos
	 * @return
	 * @return List<Integer>
	 * @throws
	 */
	private List<Integer> getCoursewareIdNew(List<Map<String, Object>> coursewareInfos) {
		List<Integer> coursewareIdList = new ArrayList<Integer>();
		Integer coursewareId;
		for (Map<String, Object> map : coursewareInfos) {
			coursewareId = (Integer) map.get("coursewareId");
			coursewareIdList.add(coursewareId);
		}
		return coursewareIdList;
	}

	private List<TraLearningProgressVO> getLearningCourseList(Integer courseId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courseId", courseId);
		map.put("courseStatus", "2");
		List<TraLearningProgressVO> learningProgressList = traLearningProgressDao.getTraLearningProgresssByCourseId(map);
		return learningProgressList;
	}

	/**
	 * @Description 构造课程-课件关系VO
	 * @param courseId
	 * @param isRequired
	 * @param coursewareId
	 * @param count
	 * @param i
	 * @return
	 * @return TraCourseCoursewareVO
	 * @throws
	 */
	private TraCourseCoursewareVO getCourseCoursewareVO(Integer courseId, Map<String, Object> map, int count, int i,
			Integer userId) {
		TraCourseCoursewareVO entity = new TraCourseCoursewareVO();
		entity.setCourseId(courseId);
		entity.setCoursewareId((Integer) map.get("coursewareId"));
		entity.setIsRequired((String) map.get("reRequired"));
		entity.setSort(count + i + 1);
		entity.setInsertBy(userId);
		entity.setUpdateBy(userId);
		return entity;
	}

	@Override
	public boolean deleteCoursewareRel(Map<String, Object> paramMap) {
		Integer courseId = (Integer) paramMap.get("courseId");
		Integer coursewareId = (Integer) paramMap.get("coursewareId");
		if (null == coursewareId) {
			// 删除课程下所有课件
			traCourseCoursewareDao.deleteCourseCoursewareByCourseId(courseId);
		} else {
			traCourseCoursewareDao.deleteCourseCoursewareByCourseIdAndCoursewareId(courseId, coursewareId);
		}
		return true;
	}

	@Override
	public boolean updateCoursewareSort(Map<String, Object> paramMap) {
		List<Map<String, Object>> coursecoursewareInfoList = (List<Map<String, Object>>) paramMap.get("coursecoursewareInfo");
		List<TraCourseCoursewareVO> list = new ArrayList<TraCourseCoursewareVO>();
		TraCourseCoursewareVO traCourseCoursewareVO;
		for (Map<String, Object> map : coursecoursewareInfoList) {
			traCourseCoursewareVO = new TraCourseCoursewareVO();
			traCourseCoursewareVO.setId((Integer) map.get("id"));
			traCourseCoursewareVO.setSort((Integer) map.get("sort"));
			traCourseCoursewareVO.setUpdateBy(1);
			list.add(traCourseCoursewareVO);
		}
		traCourseCoursewareDao.batchUpdateTraCourseCoursewareSort(list);
		return true;
	}

	/**
	 * @Title getCoursewareByCourseId
	 * @Description
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.train.service.TraCourseCoursewareService#getCoursewareByCourseId(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> getCoursewareByCourseId(Map<String, Object> paramMap) {
		return traCourseCoursewareDao.getCoursewareByCourseId(paramMap);
	}

}
