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
package com.yazuo.erp.train.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yazuo.erp.train.dao.TraLearningProgressDao;
import com.yazuo.erp.train.dao.TraStudentRecordDao;
import com.yazuo.erp.train.dao.TraTeacherStudentDao;
import com.yazuo.erp.train.service.CourseStateCheckService;
import com.yazuo.erp.train.vo.TraLearningProgressVO;
import com.yazuo.erp.train.vo.TraStudentRecordVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;

/**
 * @Description 定时检查课程是否超时 相关服务实现类
 * @author Yazuo
 * @date 2014-11-20 下午4:45:15
 */
@Service
public class CourseStateCheckServiceIml implements CourseStateCheckService {

	private static final Log logger = LogFactory.getLog(CourseStateCheckServiceIml.class);

	@Resource
	private TraLearningProgressDao traLearningProgressDao;

	@Resource
	private TraStudentRecordDao traStudentRecordDao;

	@Resource
	private TraTeacherStudentDao traTeacherStudentDao;

	/**
	 * @Title courseStateCheck
	 * @Description 定时检查课程是否超时
	 * @return
	 * @throws Exception
	 * @see com.yazuo.erp.train.service.CourseStateCheckService#courseStateCheck()
	 */
	@Override
	public boolean courseStateCheck() throws Exception {

		List<TraLearningProgressVO> learningProgressVOs = this.traLearningProgressDao.getAllTraLearningProgresss();
		for (TraLearningProgressVO learningProgressVO : learningProgressVOs) {
			boolean isUpdated = false;
			// 0-未学习，1-正在学习，2-已毕业，3-已超时，4-异常终止，已毕业/已超时/异常终止的不进行超时判断
			if (null != learningProgressVO.getCourseEndTime() && !"3".equals(learningProgressVO.getCourseStatus())
					&& !"2".equals(learningProgressVO.getCourseStatus()) && !"4".equals(learningProgressVO.getCourseStatus())
					&& learningProgressVO.getCourseEndTime().getTime() < new Date().getTime()) {
				learningProgressVO.setCourseStatus("3");
				isUpdated = true;
				TraStudentRecordVO studentRecordVO = new TraStudentRecordVO();
				Integer studentId = learningProgressVO.getStudentId();
				studentRecordVO.setStudentId(studentId);
				studentRecordVO.setLearningProgressId(learningProgressVO.getId());
				studentRecordVO.setCourseId(learningProgressVO.getCourseId());
				Integer teacherId = null;
				if ("0".equals(learningProgressVO.getLearningProgressType())) {
					List<TraTeacherStudentVO> t = this.traTeacherStudentDao.getTeacherIdByStudentId(studentId);
					teacherId = null == t || 0 == t.size() ? null : t.get(0).getTeacherId();
					studentRecordVO.setDescription("课程超时");
				} else if ("1".equals(learningProgressVO.getLearningProgressType())) {
					studentRecordVO.setDescription("课件超时");
				} else {
					studentRecordVO.setDescription("课程超时");
				}
				studentRecordVO.setTeacherId(teacherId);
				studentRecordVO.setCoursewareId(learningProgressVO.getCoursewareId());
				studentRecordVO.setOperatingType("4");
				studentRecordVO.setBeginTime(new Date());
				studentRecordVO.setEndTime(new Date());
				studentRecordVO.setIsTimeout("1");
				studentRecordVO.setInsertBy(studentId);
				studentRecordVO.setInsertTime(new Date());
				studentRecordVO.setUpdateBy(studentId);
				studentRecordVO.setUpdateTime(new Date());
				this.traStudentRecordDao.saveTraStudentRecord(studentRecordVO);
			}
			if (isUpdated) {
				learningProgressVO.setCourseStatus("3");
				this.traLearningProgressDao.updateCourseStatus(learningProgressVO);
			}
		}

		return true;
	}

}
