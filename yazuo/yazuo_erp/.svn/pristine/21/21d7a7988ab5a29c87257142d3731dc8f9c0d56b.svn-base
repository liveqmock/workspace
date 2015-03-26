package com.yazuo.erp.train.service.impl;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.dao.TraCourseCoursewareDao;
import com.yazuo.erp.train.dao.TraCourseDao;
import com.yazuo.erp.train.dao.TraFinalExamQuestionDao;
import com.yazuo.erp.train.dao.TraFinalExamRuleDao;
import com.yazuo.erp.train.dao.TraLearningProgressDao;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.erp.train.service.TraCourseService;
import com.yazuo.erp.train.vo.TraCourseVO;
import com.yazuo.erp.train.vo.TraLearningProgressVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TraCourseServiceImpl implements TraCourseService {

	@Resource
	private TraCourseDao traCourseDao;

	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

	@Resource
	private TraLearningProgressDao traLearningProgressDao;

	@Resource
	private TraFinalExamRuleDao traFinalExamRuleDao;

	@Resource
	private TraFinalExamQuestionDao traFinalExamQuestionDao;

	@Override
	public int saveCourse(TraCourseVO courseVO, SysUserVO user) {
		courseVO.setIsEnable("1");
		courseVO.setInsertBy(user.getId());
		courseVO.setUpdateBy(user.getId());
		courseVO.setTimeLimit(courseVO.getTimeLimit().multiply(new BigDecimal(60)));
		return traCourseDao.saveTraCourse(courseVO);
	}

	@Override
	public int updateCourse(TraCourseVO courseVO, SysUserVO user) {
		int courseId = courseVO.getId();
		List<TraLearningProgressVO> learningProgressList = getLearningCourseList(courseId);
		if (null != learningProgressList && learningProgressList.size() > 0) {
			throw new TrainBizException("此课程仍有学生学习中，不允许修改");
		}
		
		courseVO.setTimeLimit(courseVO.getTimeLimit().multiply(new BigDecimal(60)));
		courseVO.setUpdateBy(user.getId());
		return traCourseDao.updateTraCourse(courseVO);
	}

	@Override
	public int deleteCourse(List<Integer> idsList, SysUserVO user) {
		int sum = 0;
		// TODO 需要确认是否需要批量删除，如果需要，要考虑每一条课程是否都有学生使用
		if (idsList != null && idsList.size() > 0) {
			// 1、查询课程是否在使用中
			int courseId = idsList.get(0);
			List<TraLearningProgressVO> learningProgressList = getLearningCourseList(courseId);
			if (null != learningProgressList && learningProgressList.size() > 0) {
				throw new TrainBizException("此课程仍有学生学习中，不允许删除");
			}

			// 2、修改tra_course课程，有效标志为删除
			this.batchUpdateCourse(idsList, user);

			// 3、根据课程ID列表批量删除 tra_course_courseware 课程-课件关系表
			this.batchDeleteCourseCourseware(idsList);

			// 4、根据课程ID列表批量删除 tra_final_exam_question 期末考卷必考题
			this.traFinalExamQuestionDao.batchDeleteTraFinalExamQuestionByCourseIds(idsList);

			// 5、根据课程ID列表批量删除 tra_final_exam_rule 期末考卷规则
			this.traFinalExamRuleDao.batchDeleteTraFinalExamRuleByCourseIds(idsList);

			sum = 1;
		} else {
			sum = 0;
		}
		return sum;
	}

	private List<TraLearningProgressVO> getLearningCourseList(Integer courseId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courseId", courseId);
		map.put("courseStatus", "2");
		List<TraLearningProgressVO> learningProgressList = traLearningProgressDao.getTraLearningProgresssByCourseId(map);
		return learningProgressList;
	}

	private int batchDeleteCourseCourseware(List<Integer> idsList) {
		List<Map<String, Object>> courseList = new ArrayList<Map<String, Object>>();
		Map<String, Object> paramMap;
		for (Integer id : idsList) {
			paramMap = new HashMap<String, Object>();
			paramMap.put("courseId", id);
			courseList.add(paramMap);
		}
		return traCourseCoursewareDao.batchDeleteCourseCoursewareByCourseId(courseList);
	}

	private int batchUpdateCourse(List<Integer> idsList, SysUserVO user) {
		List<TraCourseVO> list = new ArrayList<TraCourseVO>();
		TraCourseVO traCourseVO;
		for (Integer id : idsList) {
			traCourseVO = new TraCourseVO();
			traCourseVO.setId(id);
			traCourseVO.setIsEnable("0");
			traCourseVO.setUpdateBy(user.getId());
			list.add(traCourseVO);
		}
		return traCourseDao.batchUpdateTraCourse(list);
	}

	@Override
	public Page<Map<String, Object>> getCourseInfo(int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize, true);
		Page<Map<String, Object>> traCoursesList = (Page<Map<String, Object>>) traCourseDao.getTraCourseInfo();
		return traCoursesList;

	}

	@Override
	public Map<String, Object> getCourseDetail(Integer courseId) {
		List<TraLearningProgressVO> learningCourseList = getLearningCourseList(courseId);
		String courseLearningFlag = "0";// 课程学习中标志,0为课程无人学习
		if (null != learningCourseList && learningCourseList.size() > 0) {
			courseLearningFlag = "1";// 课程正在学习中
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("courseId", courseId);
		// 1、查询必修课信息
		paramMap.put("isRequired", "1");// 选修
		List<Map<String, Object>> requiredCoursewaresInfoList = traCourseCoursewareDao.getTraCourseCoursewaresInfo(paramMap);
		// 2、查询选修课信息
		paramMap.put("isRequired", "0");// 选修
		List<Map<String, Object>> noRequiredCoursewaresInfolist = traCourseCoursewareDao.getTraCourseCoursewaresInfo(paramMap);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("courseLearningFlag", courseLearningFlag);
		resultMap.put("requiredCoursewaresInfoList", requiredCoursewaresInfoList);
		resultMap.put("noRequiredCoursewaresInfolist", noRequiredCoursewaresInfolist);
		return resultMap;
	}

	/**
	 * @Title getAllCoursesLearningInfo
	 * @Description
	 * @return
	 * @see com.yazuo.erp.train.service.TraCourseService#getAllCoursesLearningInfo()
	 */
	@Override
	public List<Map<String, Object>> getAllCoursesLearningInfo() {
		return traCourseDao.getAllCoursesLearningInfo();

	}
}
