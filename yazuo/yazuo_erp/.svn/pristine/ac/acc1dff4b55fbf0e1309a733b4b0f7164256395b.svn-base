package com.yazuo.erp.train.service.impl;

import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.dao.*;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.erp.train.service.CalendarService;
import com.yazuo.erp.train.service.StudentService;
import com.yazuo.erp.train.vo.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
	@Resource
	private TraCourseDao traCourseDao;

	@Resource
	private TraCoursewareDao traCoursewareDao;

	@Resource
	private TraStudentRecordDao traStudentRecordDao;

	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

	@Resource
	private TraLearningProgressDao traLearningProgressDao;

	@Resource
	private CalendarService calendarService;

	@Resource
	private TraTeacherStudentDao traTeacherStudentDao;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private TraRuleDao traRuleDao;

	private static final Log LOG = LogFactory.getLog(StudentService.class);

	@Override
	public boolean hasCourse(Integer studentId) {
		TraCourseVO courseVO = this.traCourseDao.getCourseVOByStudentId(studentId);
		return courseVO != null;
	}

	@Override
	public TraCourseVO getCourseByStudentId(Integer studentId) {
		TraCourseVO courseVO = this.traCourseDao.getCourseVOByStudentId(studentId);
		if (courseVO == null) {
			return null;
		}
		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getCurrentTraLearningProcess(courseVO.getId(),
				studentId);
		courseVO.setEndTime(learningProgressVO.getCourseEndTime());
		courseVO.setLearningProgressId(learningProgressVO.getId());
		LOG.debug("studentId " + studentId + "得到课程:" + courseVO);
		return courseVO;
	}

	@Override
	public void executeStartStudy(Integer studentId, Integer courseId, Integer coursewareId, Integer learningProgressId,
			Integer isOldStaff) {
		if (null == isOldStaff || 0 == isOldStaff.intValue()) {// 新学员
			this.startStudyOfNewStaff(studentId, courseId, coursewareId, learningProgressId, isOldStaff);
		} else if (1 == isOldStaff.intValue()) {// 老学员
			this.startStudyOfOldStaff(studentId, coursewareId, learningProgressId, isOldStaff);
		}
	}

	/**
	 * @Description 老学员开始学习
	 * @param studentId
	 * @param coursewareId
	 * @param learningProgressId
	 * @param isOldStaff
	 * @return void
	 * @throws
	 */
	private void startStudyOfOldStaff(Integer studentId, Integer coursewareId, Integer learningProgressId, Integer isOldStaff) {
		TraCoursewareVO coursewareVO = this.traCoursewareDao.getTraCoursewareById(coursewareId);
		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getTraLearningProgressById(learningProgressId);
		if (null == learningProgressVO) {
			throw new TrainBizException("未查询到学生学习进度");
		}

		Integer count = this.traStudentRecordDao.getStudentRecordStudyCount(studentId, null, coursewareId,
				learningProgressVO.getId());
		StringBuffer description = new StringBuffer();
		description.append("第");
		description.append(count + 1);
		description.append("次参加 ");
		description.append(coursewareVO.getCoursewareName());
		description.append(" 学习");

		TraStudentRecordVO studentRecordVO = new TraStudentRecordVO();
		studentRecordVO.setStudentId(studentId);
		studentRecordVO.setTeacherId(null);
		studentRecordVO.setLearningProgressId(learningProgressVO.getId());
		studentRecordVO.setCourseId(null);
		studentRecordVO.setCoursewareId(coursewareId);
		studentRecordVO.setOperatingType("0");
		studentRecordVO.setBeginTime(new Date());
		studentRecordVO.setIsTimeout(learningProgressVO.getCoursewareStatus());
		studentRecordVO.setInsertBy(studentId);
		studentRecordVO.setInsertTime(new Date());
		studentRecordVO.setUpdateBy(studentId);
		studentRecordVO.setUpdateTime(new Date());
		studentRecordVO.setDescription(description.toString());
		this.traStudentRecordDao.saveTraStudentRecord(studentRecordVO);
	}

	/**
	 * @Description 新学员开始学习
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId
	 * @param isOldStaff
	 * @return void
	 * @throws
	 */
	private void startStudyOfNewStaff(Integer studentId, Integer courseId, Integer coursewareId, Integer learningProgressId,
			Integer isOldStaff) {
		Integer teacherId = this.traTeacherStudentDao.getTeacherIdByStudentId(studentId);
		TraCourseVO courseVO = this.traCourseDao.getTraCourseById(courseId);
		TraCoursewareVO coursewareVO = this.traCoursewareDao.getTraCoursewareById(coursewareId);
		TraCourseCoursewareVO courseCoursewareVO = this.traCourseCoursewareDao.getCourseCoursewareVO(courseId, coursewareId);

		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getTraLearningProgressById(learningProgressId);
		if (null == learningProgressVO) {
			throw new TrainBizException("未查询到学生学习进度");
		}

		Integer count = this.traStudentRecordDao.getStudentRecordStudyCount(studentId, courseId, coursewareId,
				learningProgressVO.getId());
		StringBuffer description = new StringBuffer();
		description.append("第");
		description.append(count + 1);
		description.append("次参加 ");
		description.append(coursewareVO.getCoursewareName());
		description.append(" 学习");
		if ("1".equals(courseCoursewareVO.getIsRequired())) {
			description.append("(必修课)");
		} else {
			description.append("(选修课)");
		}

		TraStudentRecordVO studentRecordVO = new TraStudentRecordVO();
		studentRecordVO.setStudentId(studentId);
		studentRecordVO.setTeacherId(teacherId);
		studentRecordVO.setLearningProgressId(learningProgressVO.getId());
		studentRecordVO.setCourseId(courseId);
		studentRecordVO.setCoursewareId(coursewareId);
		studentRecordVO.setOperatingType("0");
		studentRecordVO.setBeginTime(new Date());
		studentRecordVO.setIsTimeout("1");
		studentRecordVO.setInsertBy(studentId);
		studentRecordVO.setInsertTime(new Date());
		studentRecordVO.setUpdateBy(studentId);
		studentRecordVO.setUpdateTime(new Date());
		studentRecordVO.setDescription(description.toString());
		this.traStudentRecordDao.saveTraStudentRecord(studentRecordVO);

		LOG.debug("更新学习进度");
		Float courseTimelimit = courseVO.getTimeLimit().floatValue();
		Float coursewareTimelimit = coursewareVO.getTimeLimit().floatValue();

		// 未毕业，则更新学习进度
		String courseStatus = learningProgressVO.getCourseStatus();
		if (!"2".equals(courseStatus)) {
			learningProgressVO.setCourseStatus("1");// 未毕业
			if (learningProgressVO.getCourseEndTime() == null) {
				learningProgressVO.setCourseEndTime(this.calendarService.afterHours(courseTimelimit / 60.0f));
			}
			learningProgressVO.setCoursewareEndTime(this.calendarService.afterHours(coursewareTimelimit / 60.0f));
			learningProgressVO.setProgressType("0");
			this.traLearningProgressDao.updateTraLearningProgress(learningProgressVO);
		}
	}

	@Override
	public void executeCompleteStudy(Integer studentId, Integer courseId, Integer coursewareId, Integer learningProgressId,
			Integer isOldStaff) {
		if (null == isOldStaff || 0 == isOldStaff.intValue()) {// 新学员
			this.completeStudyOfNewStaff(studentId, courseId, coursewareId, learningProgressId);
		} else if (1 == isOldStaff) {// 老学员
			this.completeStudyOfOldStaff(studentId, coursewareId, learningProgressId);
		}
	}

	/**
	 * @Description 新学员，完成学习
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId
	 * @return void
	 * @throws
	 */
	private void completeStudyOfNewStaff(Integer studentId, Integer courseId, Integer coursewareId, Integer learningProgressId) {
		TraStudentRecordVO studentRecordVO = this.traStudentRecordDao.getStudentRecord(studentId, courseId, coursewareId,
				learningProgressId);
		if (studentRecordVO == null) {// 暂修复
			this.executeStartStudy(studentId, courseId, coursewareId, learningProgressId, new Integer(0));
			studentRecordVO = this.traStudentRecordDao.getStudentRecord(studentId, courseId, coursewareId, learningProgressId);
		}
		studentRecordVO.setEndTime(new Date());
		this.traStudentRecordDao.updateTraStudentRecord(studentRecordVO);
	}

	/**
	 * @Description 老学员，完成学习
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId
	 * @return void
	 * @throws
	 */
	private void completeStudyOfOldStaff(Integer studentId, Integer coursewareId, Integer learningProgressId) {
		TraStudentRecordVO studentRecordVO = this.traStudentRecordDao.getStudentRecord(studentId, null, coursewareId,
				learningProgressId);
		if (studentRecordVO == null) {// 暂修复
			this.executeStartStudy(studentId, null, coursewareId, learningProgressId, new Integer(0));
			studentRecordVO = this.traStudentRecordDao.getStudentRecord(studentId, null, coursewareId, learningProgressId);
		}
		studentRecordVO.setEndTime(new Date());
		this.traStudentRecordDao.updateTraStudentRecord(studentRecordVO);

		// 查询课件的考试规则，如果老学员不需要考试，则修改学习进度2-已毕业
		TraRuleVO traRule = traRuleDao.getTraRuleByCoursewareId(coursewareId);
		if (null == traRule) {
			throw new TrainBizException("未查询到课件的考卷规则");
		}

		String isTest = traRule.getIsTest();
		if (null != isTest && "0".equals(isTest)) {// 老学员不需要考试
			// 查询学习进度
			TraLearningProgressVO traLearningProgress = traLearningProgressDao.getTraLearningProgressById(learningProgressId);
			if (null == traLearningProgress) {
				throw new TrainBizException("未查询到学习进度");
			}
			if (!"2".equals(traLearningProgress.getCourseStatus())) {// 已毕业的学员，学习进度无需再次更新
				traLearningProgress.setCourseStatus("2");
				traLearningProgress.setProgressType("2");
				traLearningProgress.setUpdateBy(studentId);
				traLearningProgressDao.updateTraLearningProgress(traLearningProgress);
			}
		}
	}

	@Override
	public String getTeacherName(Integer studentId) {
		Integer teacherId = this.traTeacherStudentDao.getTeacherIdByStudentId(studentId);
		SysUserVO sysUserVO = this.sysUserService.getSysUserById(teacherId);
		if (sysUserVO == null) {
			// start,update by gaoshan,2014-09-26
			// 新学员培训需求调整，可暂时不分配老师，先学习
			// throw new TrainBizException("未分配老师，请联系管理员！");
			return "";
			// end,update by gaoshan,2014-09-26
		}
		return sysUserVO.getUserName();
	}

	@Override
	public void synchronizeExpiredState() {
		List<TraLearningProgressVO> learningProgressVOs = this.traLearningProgressDao.getAllTraLearningProgresss();
		for (TraLearningProgressVO learningProgressVO : learningProgressVOs) {
			boolean isUpdated = false;
			// update by
			// gaoshan，0-未学习，1-正在学习，2-已毕业，3-已超时，4-异常终止，已毕业/已超时/异常终止的不进行超时判断
			if (null != learningProgressVO.getCourseEndTime() && !"3".equals(learningProgressVO.getCourseStatus())
					&& !"2".equals(learningProgressVO.getCourseStatus()) && !"4".equals(learningProgressVO.getCourseStatus())
					&& learningProgressVO.getCourseEndTime().getTime() < new Date().getTime()) {
				learningProgressVO.setCourseStatus("3");
				isUpdated = true;
				TraStudentRecordVO studentRecordVO = new TraStudentRecordVO();
				Integer studentId = learningProgressVO.getStudentId();
				Integer teacherId = this.traTeacherStudentDao.getTeacherIdByStudentId(studentId);
				studentRecordVO.setStudentId(studentId);
				studentRecordVO.setTeacherId(teacherId);

				// start,update by gaoshan,2014-09-26
				// 新学员需求调整，流水添加字段进度ID
				studentRecordVO.setLearningProgressId(learningProgressVO.getId());
				// start,update by gaoshan,2014-09-26

				studentRecordVO.setCourseId(learningProgressVO.getCourseId());
				studentRecordVO.setOperatingType("4");
				studentRecordVO.setDescription("课程超时");
				studentRecordVO.setBeginTime(new Date());
				studentRecordVO.setEndTime(new Date());
				studentRecordVO.setIsTimeout("1");
				studentRecordVO.setInsertBy(studentId);
				studentRecordVO.setInsertTime(new Date());
				studentRecordVO.setUpdateBy(studentId);
				studentRecordVO.setUpdateTime(new Date());
				// if (teacherId != null) {
				this.traStudentRecordDao.saveTraStudentRecord(studentRecordVO);
				// }
			}
			/*
			 * if (null != learningProgressVO.getCoursewareEndTime() &&
			 * learningProgressVO.getCoursewareEndTime().getTime() > new
			 * Date().getTime()) { learningProgressVO.setCoursewareStatus("0");
			 * isUpdated = true; TraStudentRecordVO studentRecordVO = new
			 * TraStudentRecordVO(); Integer studentId =
			 * learningProgressVO.getStudentId(); Integer teacherId =
			 * this.traTeacherStudentDao.getTeacherIdByStudentId(studentId);
			 * studentRecordVO.setStudentId(studentId);
			 * studentRecordVO.setTeacherId(teacherId);
			 * studentRecordVO.setCourseId(learningProgressVO.getCourseId());
			 * studentRecordVO.setOperatingType("4");
			 * studentRecordVO.setDescription("课件超时");
			 * studentRecordVO.setBeginTime(new Date());
			 * studentRecordVO.setEndTime(new Date());
			 * studentRecordVO.setIsTimeout("1");
			 * studentRecordVO.setDescription("");
			 * studentRecordVO.setInsertBy(studentId);
			 * studentRecordVO.setInsertTime(new Date());
			 * studentRecordVO.setUpdateBy(studentId);
			 * studentRecordVO.setUpdateTime(new Date());
			 * this.traStudentRecordDao.saveTraStudentRecord(studentRecordVO); }
			 */
			if (isUpdated) {
				LOG.info("更新学习进度:" + learningProgressVO);
				this.traLearningProgressDao.updateCourseEndTime(learningProgressVO);
			}
		}
	}
}
