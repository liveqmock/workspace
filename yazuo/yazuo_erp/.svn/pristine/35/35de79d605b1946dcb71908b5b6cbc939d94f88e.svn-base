/**
 * @Description 学生管理相关服务实现类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 		gaoshan		2014-06-04	创建文档
 * 
 */
package com.yazuo.erp.train.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.dao.SysPositionDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.vo.SysPositionVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.dao.TraCourseCoursewareDao;
import com.yazuo.erp.train.dao.TraCourseDao;
import com.yazuo.erp.train.dao.TraCoursewareDao;
import com.yazuo.erp.train.dao.TraExamDtlDao;
import com.yazuo.erp.train.dao.TraExamPaperDao;
import com.yazuo.erp.train.dao.TraFinalExamRuleDao;
import com.yazuo.erp.train.dao.TraLearningProgressDao;
import com.yazuo.erp.train.dao.TraPositionCourseDao;
import com.yazuo.erp.train.dao.TraPptDao;
import com.yazuo.erp.train.dao.TraPptDtlDao;
import com.yazuo.erp.train.dao.TraRuleDao;
import com.yazuo.erp.train.dao.TraStudentRecordDao;
import com.yazuo.erp.train.dao.TraTeacherStudentDao;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.erp.train.service.StudentManagementService;
import com.yazuo.erp.train.vo.CoursewareProgressVO;
import com.yazuo.erp.train.vo.TraCourseCoursewareVO;
import com.yazuo.erp.train.vo.TraCourseVO;
import com.yazuo.erp.train.vo.TraExamDtlVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraFinalExamRuleVO;
import com.yazuo.erp.train.vo.TraLearningProgressVO;
import com.yazuo.erp.train.vo.TraPositionCourseVO;
import com.yazuo.erp.train.vo.TraRuleVO;
import com.yazuo.erp.train.vo.TraStudentRecordVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;
import com.yazuo.util.DateUtil;

/**
 * @Description 学生管理相关服务实现类
 * @author gaoshan
 * @date 2014-6-3 下午6:44:38
 */
@Service
public class StudentManagementServiceImpl implements StudentManagementService {

	/**
	 * 老师-学生关系表DAO
	 */
	@Resource
	private TraTeacherStudentDao traTeacherStudentDao;

	/**
	 * 老师-学生关系表DAO
	 */
	@Resource
	private TraStudentRecordDao traStudentRecordDao;

	/**
	 * PPT表DAO
	 */
	@Resource
	private TraPptDao traPptDao;

	/**
	 * PPT详情表DAO
	 */
	@Resource
	private TraPptDtlDao traPptDtlDao;

	/**
	 * 考卷规则表DAO
	 */
	@Resource
	private TraRuleDao traRuleDao;

	/**
	 * 试卷表DAO
	 */
	@Resource
	private TraExamPaperDao traExamPaperDao;

	/**
	 * 试卷题干表DAO
	 */
	@Resource
	private TraExamDtlDao traExamDtlDao;

	/**
	 * 期末考卷规则表DAO
	 */
	@Resource
	private TraFinalExamRuleDao traFinalExamRuleDao;

	/**
	 * 学习进度表DAO
	 */
	@Resource
	private TraLearningProgressDao traLearningProgressDao;

	/**
	 * 课程表DAO
	 */
	@Resource
	private TraCourseDao traCourseDao;

	/**
	 * 职位表DAO
	 */
	@Resource
	private SysPositionDao sysPositionDao;

	/**
	 * 课件表DAO
	 */
	@Resource
	private TraCoursewareDao traCoursewareDao;

	/**
	 * 用户表DAO
	 */
	@Resource
	private SysUserDao sysUserDao;

	/**
	 * 职位-课程关系表
	 */
	@Resource
	private TraPositionCourseDao traPositionCourseDao;

	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

	/**
	 * 上传的试题PPT(真正用的文件)配置路径
	 */
	@Value("${pptPhotoPath}")
	private String pptPhotoPath;

	/**
	 * 上传的录音配置路径
	 */
	@Value("${audioPath}")
	private String audioPath;

	/**
	 * @Title queryPosition
	 * @Description 查询有效的职位信息
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryPosition(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryPosition(Map<String, Object> params) {
		Integer courseId = (Integer) params.get("courseId");
		List<Map<String, Object>> list = this.sysPositionDao.queryPosition(courseId);
		return list;
	}

	/**
	 * @Title queryCourse
	 * @Description 查询有效的课程信息
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryCourse()
	 */
	@Override
	public List<Map<String, Object>> queryCourse() {
		List<Map<String, Object>> list = this.traCourseDao.queryCourse();
		return list;
	}

	/**
	 * @Title queryToDoListByTeacherId
	 * @Description 根据老师ID查询待办事项列表
	 * @param teacherId
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryToDoListByTeacherId(java.lang.Integer)
	 */
	@Override
	public Page<Map<String, Object>> queryToDoListByTeacherId(Integer teacherId) {
		Page<Map<String, Object>> list = this.traStudentRecordDao.queryToDoListByTeacherId(teacherId);
		return list;
	}

	/**
	 * @Title queryStudentListToBeAllocated
	 * @Description 查询未分配老师的学生列表
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryStudentListToBeAllocated(java.util.Map)
	 */
	@Override
	public Page<Map<String, Object>> queryStudentListToBeAllocated(Map<String, Object> params) {
		Page<Map<String, Object>> list = this.traTeacherStudentDao.queryStudentListToBeAllocated(params);
		return list;
	}

	/**
	 * @Title insertTraTeacherStudent
	 * @Description 添加老师-学生关系
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#insertTraTeacherStudent(java.util.Map)
	 */
	@Override
	public int saveTraTeacherStudent(Map<String, Object> params) {
		// Integer courseId = (Integer) params.get("courseId");
		List<Integer> studentIdList = (List<Integer>) params.get("studentIdList");
		Integer teacherId = (Integer) params.get("teacherId");
		SysUserVO user = (SysUserVO) params.get("user");
		Integer userId = user.getId();

		// 校验课程有效性
		// this.checkCourse(courseId, studentIdList);

		// 查询课程的第一个有效课件
		// Map<String, Object> courseware =
		// this.traCoursewareDao.queryFirstCourseware(courseId);
		// if (courseware == null) {
		// throw new TrainBizException("未查询到课件信息");
		// }
		// Integer coursewareId = (Integer) courseware.get("id");

		int sum = 0;
		for (int i = 0; i < studentIdList.size(); i++) {
			Integer studentId = (Integer) studentIdList.get(i);

			// 处理老师学生关系表
			sum = handleTeacherStudentRef(teacherId, studentId, userId);

			// 添加学习进度
			// sum = saveTraLearningProgress(courseId, coursewareId, studentId,
			// userId);
		}

		return sum;
	}

	/**
	 * @Description 处理老师学生关系表
	 * @param teacherId
	 * @param studentId
	 * @param userId
	 * @return
	 * @throws TrainBizException
	 * @throws
	 */
	private int handleTeacherStudentRef(Integer teacherId, Integer studentId, Integer userId) throws TrainBizException {
		int sum;
		TraTeacherStudentVO vo = new TraTeacherStudentVO();
		vo.setStudentId(studentId);
		vo.setTeacherId(teacherId);
		List<TraTeacherStudentVO> list = traTeacherStudentDao.queryTraTeacherStudent(vo);

		if (null == list || 0 == list.size()) {// 初次，建立老师和学生的关系
			vo.setIsEnable("1");
			vo.setInsertBy(userId);
			vo.setInsertTime(new Date());
			sum = this.traTeacherStudentDao.saveTraTeacherStudent(vo);
			if (0 >= sum) {
				throw new TrainBizException("师生关系建立失败" + "[" + "学生ID：" + studentId + "]");
			}
		} else {// 如已有记录，则进行开启，将is_enable置为1-正常
			TraTeacherStudentVO ref = list.get(0);
			Integer id = ref.getId();
			ref.setIsEnable("1");
			sum = this.traTeacherStudentDao.updateTraTeacherStudentRef(id, teacherId, studentId);
			if (0 >= sum) {
				throw new TrainBizException("师生关系建立失败" + "[" + "学生ID：" + studentId + "]");
			}
		}
		return sum;
	}

	/**
	 * @Description 添加学习进度
	 * @param courseId
	 * @param coursewareId
	 * @param studentId
	 * @param userId
	 * @return
	 * @throws TrainBizException
	 * @throws
	 */
	private int saveTraLearningProgress(Integer courseId, Integer coursewareId, Integer studentId, Integer userId)
			throws TrainBizException {
		// 根据学生查询未毕业的学习进度记录，如果有未毕业的记录，则不予重建记录
		// TraLearningProgressVO vo =
		// this.traLearningProgressDao.queryTraLearningProcessByStudentId(studentId);
		int sum = 0;
		// if (vo != null) {
		// return 1;
		// }

		TraLearningProgressVO lp = new TraLearningProgressVO();
		lp.setStudentId(studentId);// 学生ID
		lp.setLearningProgressType("0");// 学习进度类型，0-新学员，1-老学员
		lp.setCourseId(courseId);// 课程ID
		lp.setCoursewareId(coursewareId);// 课件ID
		lp.setCourseStatus("0");// 课程状态
		lp.setCoursewareStatus("1");// 课件状态
		lp.setCourseEndTime(null);// 课程截止时间
		lp.setCoursewareEndTime(null);// 课件截止时间
		lp.setProgressType("1");// 学习进度
		lp.setRemark(null);// 备注
		lp.setInsertBy(userId);// 创建人
		lp.setInsertTime(new Date());// 创建时间
		lp.setUpdateBy(userId);// 最后修改人
		lp.setUpdateTime(new Date());// 最后修改时间
		sum = this.traLearningProgressDao.saveTraLearningProgress(lp);
		if (0 >= sum) {
			throw new TrainBizException("添加学习进度失败" + "[" + "学生ID：" + studentId + "]");
		}

		return sum;
	}

	/**
	 * @Description 校验课程有效性
	 * @param courseId
	 * @throws TrainBizException
	 * @throws
	 */
	private void checkCourse(Integer courseId, List<Integer> studentIdList) throws TrainBizException {
		TraCourseVO course = this.traCourseDao.getTraCourseById(courseId);
		if (course == null) {
			throw new TrainBizException("未查询到课程信息");
		}

		String isEnable = course.getIsEnable();
		if ("0".equals(isEnable)) {
			throw new TrainBizException("该课程已被管理员删除");
		}

		// 如果学员有未毕业的课程，则只能绑定该课程，一个学生同时只可以学一个课程，可以换老师，不可以换课程
		int count = this.traLearningProgressDao.getTraLearningProgressOfLearning(courseId, studentIdList);
		if (count > 0) {
			throw new TrainBizException("学员有未结业的课程，请先结束课程再开始新课程");
		}
	}

	/**
	 * @Title queryStudentList
	 * @Description 条件查询学生信息列表
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryStudentList(java.util.Map)
	 */
	@Override
	public Page<Map<String, Object>> queryStudentList(Map<String, Object> params) {
		Page<Map<String, Object>> list = this.traTeacherStudentDao.queryStudentList(params);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				Integer studentId = (Integer) map.get("student_id");
				Integer courseId = (Integer) map.get("course_id");
				Integer coursewareId = (Integer) map.get("courseware_id");
				String coursewareStatus = (String) map.get("courseware_status");
				String course_status = (String) map.get("course_status");
				String course_status_name = (String) map.get("course_status_name");
				String progressType = (String) map.get("progress_type");
				String coursewareName = (String) map.get("courseware_name");

				StringBuffer sb = new StringBuffer(512);
				if ("0".equals(course_status)) {// 0-未学习
					sb.append("还没有开始学习");
				} else if ("2".equals(course_status)) {// 2-已毕业
					sb.append("已毕业");
				} else if ("1".equals(course_status)) {// 1-正在学习
					if ("0".equals(progressType)) {// 0-学习
						sb.append("学习 ").append(coursewareName).append(" 课件");
					} else if ("1".equals(progressType)) {// 1-参加
						sb.append("参加 ").append(coursewareName).append(" 考试");
					} else if ("2".equals(progressType)) {// 2-已毕业
						sb.append(" ");
					}
				} else if ("3".equals(course_status)) {// 3-已超时
					if ("0".equals(progressType)) {// 0-学习
						sb.append("学习 ").append(coursewareName).append(" 课件");
					} else if ("1".equals(progressType)) {// 1-参加
						sb.append("参加 ").append(coursewareName).append(" 考试");
					}
					sb.append("（课程已超时）");
				}
				map.put("progress_tip", sb.toString());

				Map<String, Object> progress = new HashMap<String, Object>();

				// 学生列表中，学生的学习进度展现，仅限必修课
				List<Map<String, Object>> coursewareList = this.traCoursewareDao.getTraCoursewaresListByCourseId(courseId,
						coursewareId);

				// 给每一条课件记录中添加属性，表识是否超时
				for (Map<String, Object> c : coursewareList) {
					Integer id = (Integer) c.get("id");
					if (coursewareId.equals(id)) {
						if ("0".equals(coursewareStatus)) {
							c.put("is_timeout", true);
						} else {
							c.put("is_timeout", false);
						}
					} else {
						c.put("is_timeout", false);
					}

					// 当已毕业时，所有课件都标记为已学过
					if ("2".equals(course_status)) {// 2-已毕业
						c.put("is_studied", true);
					}
				}

				progress.put("coursewareList", coursewareList);
				map.put("progress", progress);
			}
		}
		return list;
	}

	/**
	 * @Title queryTraStudentRecordByStudentId
	 * @Description 根据学生ID查看流水
	 * @param studentId
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryTraStudentRecordByStudentId(java.lang.Integer)
	 */
	@Override
	public Page<Map<String, Object>> queryTraStudentRecordByStudentId(Integer studentId) {
		Page<Map<String, Object>> list = this.traStudentRecordDao.queryTraStudentRecordByStudentId(studentId);
		return list;
	}

	/**
	 * @Title deleteTraTeacherStudent
	 * @Description 解除师生关系
	 * @param traTeacherStudentVO
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#deleteTraTeacherStudent(com.yazuo.erp.train.vo.TraTeacherStudentVO)
	 */
	@Override
	public int deleteTraTeacherStudent(TraTeacherStudentVO vo) {
		List<TraTeacherStudentVO> list = traTeacherStudentDao.queryTraTeacherStudent(vo);

		// 没有师生关系，提示异常
		if (null == list || 0 == list.size()) {
			throw new TrainBizException("未查询到师生关系，解除失败");
		}

		TraTeacherStudentVO ref = list.get(0);
		ref.setIsEnable("0");

		List<TraTeacherStudentVO> array = new ArrayList<TraTeacherStudentVO>();
		array.add(ref);

		// 解除师生关系
		int sum = this.traTeacherStudentDao.batchDeleteTraTeacherStudent(array);
		return sum;
	}

	/**
	 * @Title batchDeleteTraTeacherStudent
	 * @Description 批量解除师生关系
	 * @param traTeacherStudentList
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#batchDeleteTraTeacherStudent(java.util.List)
	 */
	@Override
	public int batchDeleteTraTeacherStudent(List<TraTeacherStudentVO> list) {
		int sum = this.traTeacherStudentDao.batchDeleteTraTeacherStudent(list);
		return sum;
	}

	/**
	 * @Title queryPptExamInfo
	 * @Description 根据试卷ID查询PPT、录音信息和评语信息
	 * @param paperId
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryPptExamInfo(java.lang.Integer)
	 */
	@Override
	public Map<String, Object> queryPptExamInfo(Integer paperId) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 根据试卷ID查询试卷信息
		TraExamPaperVO paper = this.traExamPaperDao.getTraExamPaperById(paperId);
		if (paper != null) {
			map.put("traExamPaper", paper);
		} else {
			throw new TrainBizException("未查询到试卷信息");
		}

		// 根据试卷ID查询PPT基本信息
		Map<String, Object> ppt = this.traPptDao.queryPptInfoByPaperId(paperId);
		if (ppt != null && ppt.size() > 0) {
			map.put("pptPhotoPath", pptPhotoPath);// PPT配置路径
			map.put("ppt", ppt);
		} else {
			throw new TrainBizException("未查询到PPT");
		}

		// 根据试卷ID查询PPT的详情信息
		List<Map<String, Object>> pptDtlList = this.traPptDtlDao.queryPptDtlByPaperId(paperId);
		if (pptDtlList != null && pptDtlList.size() > 0) {
			map.put("pptDtlList", pptDtlList);
		} else {
			throw new TrainBizException("未查询到PPT的详细信息");
		}

		// 根据试卷ID查询PPT的录音信息
		Map<String, Object> audioInfo = this.traPptDao.queryRecordingByPaperId(paperId);
		if (audioInfo != null && audioInfo.size() > 0) {
			map.put("audioPath", audioPath);// 录音配置路径
			map.put("audioInfo", audioInfo);
		} else {
			throw new TrainBizException("未查询到录音信息");
		}

		Integer studentId = paper.getStudentId();
		SysUserVO studentInfo = this.sysUserDao.getSysUserById(studentId);
		if (studentInfo != null) {
			map.put("studentInfo", studentInfo);
		} else {
			throw new TrainBizException("未查询到学生基本信息");
		}

		return map;
	}

	/**
	 * @Title markingPptPaper
	 * @Description 根据试卷ID进行PPT录音评分
	 * @param traExamPaper
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#updatePptPaper(com.yazuo.erp.train.vo.TraExamPaperVO)
	 */
	@Override
	public int updatePptPaper(Map<String, Object> params) {
		Integer paperId = (Integer) params.get("paperId");// 试卷ID
		String totalScore = (String) params.get("mark"); // 成绩
		String comment = (String) params.get("comment");// 评语
		SysUserVO user = (SysUserVO) params.get("user");
		Integer userId = user.getId();

		// 校验成绩不能为空
		if (null == totalScore || "".equals(totalScore)) {
			throw new TrainBizException("成绩不能为空");
		}
		BigDecimal totalScoreB = new BigDecimal(totalScore);

		// 根据试卷ID查询试卷信息
		TraExamPaperVO paper = getTraExamPaperById(paperId);

		// 判断是否重复评分
		BigDecimal totalScoreOld = paper.getTotalScore();
		if (totalScoreOld != null) {
			throw new TrainBizException("PPT录音已评分，不能重复评分");
		}

		// 根据课件ID和考卷类型查询考卷规则
		Integer coursewareId = paper.getCoursewareId();// 课件ID
		if (null == coursewareId || -1 == coursewareId) {
			throw new TrainBizException("未查询到课件信息");
		}
		TraRuleVO traRule = queryTraRule(coursewareId, "1");
		BigDecimal passingScore = traRule.getPassingScore();// 分数线
		boolean isPassFlag = passingScore.compareTo(totalScoreB) <= 0;// 判断是否通过考试

		// 修改试卷成绩和评语
		int sum = updateTraExamPaper(comment, totalScoreB, paper, isPassFlag, userId);

		// 修改试卷题干得分信息
		sum = updateTraExamDtl(paperId, totalScoreB, isPassFlag, userId);

		// 修改学生流水信息
		sum = updateTraStudentRecord(paperId, totalScore, coursewareId, "成绩", userId);

		// 如果是新学员，向前移动一个课件
		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getTraLearningProgressById(paper
				.getLearningProgressId());
		String courseStatus = learningProgressVO.getCourseStatus();
		Integer studentId = learningProgressVO.getStudentId();
		SysUserVO student = this.sysUserDao.getSysUserById(studentId);
		if (null == student) {
			throw new TrainBizException("未查询到学员信息");
		}
		String isFormal = student.getIsFormal();
		if (!"2".equals(courseStatus) && "0".equals(isFormal) && isPassFlag
				&& paper.getCoursewareId().equals(learningProgressVO.getCoursewareId())) {
			TraCourseCoursewareVO courseCoursewareVO = this.traCourseCoursewareDao.nextCourseCoursewareVO(
					learningProgressVO.getCourseId(), learningProgressVO.getCoursewareId());
			if (courseCoursewareVO != null) {
				learningProgressVO.setCoursewareId(courseCoursewareVO.getCoursewareId());
				this.traLearningProgressDao.updateTraLearningProgress(learningProgressVO);
			}
		}

		// 如果是老学员，且通过考试，则修改学习进度
		if (!"2".equals(courseStatus) && "1".equals(isFormal) && isPassFlag) {
			learningProgressVO.setCourseStatus("2");
			learningProgressVO.setProgressType("2");
			learningProgressVO.setUpdateBy(userId);
			learningProgressVO.setUpdateTime(new Date());
			this.traLearningProgressDao.updateTraLearningProgress(learningProgressVO);
		}

		return sum;
	}

	/**
	 * @Description 根据课件ID和考卷类型查询考卷规则
	 * @param coursewareId
	 * @return
	 * @throws TrainBizException
	 * @throws
	 */
	private TraRuleVO queryTraRule(Integer coursewareId, String paperType) throws TrainBizException {
		TraRuleVO traRule = this.traRuleDao.queryTraRule(coursewareId, paperType);
		if (null == traRule) {
			throw new TrainBizException("未查询到考卷规则");
		}
		return traRule;
	}

	/**
	 * @Description 根据试卷ID查询试卷信息
	 * @param paperId
	 * @return
	 * @throws TrainBizException
	 * @throws
	 */
	private TraExamPaperVO getTraExamPaperById(Integer paperId) throws TrainBizException {
		TraExamPaperVO paper = this.traExamPaperDao.getTraExamPaperById(paperId);
		if (null == paper) {
			throw new TrainBizException("未查询到试卷信息");
		}
		return paper;
	}

	/**
	 * @Description PPT打分时，修改试卷成绩和评语
	 * @param comment
	 * @param totalScoreB
	 * @param paper
	 * @param isPassFlag
	 * @param userId
	 * @throws TrainBizException
	 */
	private int updateTraExamPaper(String comment, BigDecimal totalScoreB, TraExamPaperVO paper, boolean isPassFlag,
			Integer userId) throws TrainBizException {
		paper.setTotalScore(totalScoreB);
		paper.setComment(comment);
		paper.setUpdateBy(userId);
		paper.setUpdateTime(new Date());

		if (isPassFlag) {// 及格
			paper.setPaperStatus("3");// 0-待批阅，1-待真人互动评分，3-考试通过，4-考试未通过
		} else {// 不及格
			paper.setPaperStatus("4");
		}

		// 根据试卷ID修改成绩和评语
		int sum = this.traExamPaperDao.updateTraExamPaper(paper);
		if (0 > sum) {
			throw new TrainBizException("系统异常，试卷评分失败");
		}
		return sum;
	}

	/**
	 * @Description PPT打分时，修改试卷题干得分信息
	 * @param paperId
	 * @param totalScoreB
	 * @param isPassFlag
	 * @param userId
	 * @throws TrainBizException
	 */
	private int updateTraExamDtl(Integer paperId, BigDecimal totalScoreB, boolean isPassFlag, Integer userId)
			throws TrainBizException {
		// 根据试卷ID查询试卷题干
		List<TraExamDtlVO> traExamDtlList = this.traExamDtlDao.queryTraExamDtlByPaperId(paperId);
		if (null == traExamDtlList || 0 == traExamDtlList.size()) {
			throw new TrainBizException("未查询到试卷题干信息");
		}

		TraExamDtlVO traExamDtl = traExamDtlList.get(0);// PPT只有一个考题
		traExamDtl.setScore(totalScoreB);
		traExamDtl.setUpdateBy(userId);
		traExamDtl.setUpdateTime(new Date());
		traExamDtl.setIsCorrect(isPassFlag ? "1" : "0");

		// 修改试卷题干信息
		int sum = this.traExamDtlDao.updateTraExamDtl(traExamDtl);
		if (0 > sum) {
			throw new TrainBizException("系统异常，试题评分失败");
		}
		return sum;
	}

	/**
	 * @Description PPT打分时，修改学生流水信息
	 * @param paperId
	 * @param totalScore
	 * @param coursewareId
	 * @param description
	 * @param userId
	 * @return
	 * @throws TrainBizException
	 */
	private int updateTraStudentRecord(Integer paperId, String totalScore, Integer coursewareId, String description,
			Integer userId) throws TrainBizException {
		// 根据试卷ID、课件ID查询学生的流水信息
		TraStudentRecordVO record = this.traStudentRecordDao.queryTraStudentRecord(paperId, coursewareId);

		StringBuffer sb = new StringBuffer(128);
		sb.append(record.getDescription()).append("，").append(description).append("：").append(totalScore).append("分");

		record.setDescription(sb.toString());
		record.setUpdateBy(userId);
		record.setUpdateTime(new Date());

		// 修改学生流水信息
		int sum = this.traStudentRecordDao.updateTraStudentRecord(record);
		if (0 > sum) {
			throw new TrainBizException("系统异常，更新学生流水信息失败");
		}
		return sum;
	}

	/**
	 * @Title updateMark
	 * @Description 根据试卷ID进行真人互动评分
	 * @param traExamPaper
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#updateMark(com.yazuo.erp.train.vo.TraExamPaperVO)
	 */
	@Override
	public int updateMark(Map<String, Object> params) {
		Integer paperId = (Integer) params.get("paperId");// 试卷ID
		String mark = (String) params.get("mark"); // 评分
		String comment = (String) params.get("comment");// 评语
		SysUserVO user = (SysUserVO) params.get("user");
		Integer userId = user.getId();

		// 校验评分不能为空
		if (null == mark || "".equals(mark)) {
			throw new TrainBizException("评分不能为空");
		}
		BigDecimal markB = new BigDecimal(mark);

		// 根据试卷ID查询试卷信息
		TraExamPaperVO paper = getTraExamPaperById(paperId);

		// 判断是否重复评分
		BigDecimal markOld = paper.getMark();
		if (markOld != null) {
			throw new TrainBizException("试卷已真人互动评分，不能重复评分");
		}

		// 根据试卷ID查询期末考卷规则
		TraFinalExamRuleVO examRule = queryTraFinalExamRule(paperId);
		BigDecimal interviewScores = examRule.getInterviewScores();// 面试分数线
		boolean isPassFlag = interviewScores.compareTo(markB) <= 0;// 判断是否通过考试

		// 修改试卷评分和评语
		int sum = updateMarkOfTraExamPaper(comment, markB, paper, isPassFlag, userId);

		// 修改学生流水信息
		sum = updateTraStudentRecord(paperId, mark, null, "真人互动得分", userId);

		// 通过期末考试，修改学习进度表的课程状态和学习进度
		if (isPassFlag) {
			String courseStatus = "2";
			String progressType = "2";
			sum = this.traLearningProgressDao.updateTraLearningProgressByPaperId(paperId, courseStatus, progressType, userId);
		}

		// 如果考试通过，将新员工改为老员工
		if (isPassFlag) {
			Integer studentId = paper.getStudentId();
			SysUserVO student = sysUserDao.getSysUserById(studentId);
			if (null == student) {
				throw new TrainBizException("未查询到学生信息");
			}

			student.setIsFormal("1");
			sum = this.sysUserDao.updateSysUser(student);
		}

		return sum;
	}

	/**
	 * @Description 真人互动评分时，修改试卷评分和评语
	 * @param comment
	 * @param totalScoreB
	 * @param paper
	 * @param isPassFlag
	 * @param userId
	 * @throws TrainBizException
	 */
	private int updateMarkOfTraExamPaper(String comment, BigDecimal mark, TraExamPaperVO paper, boolean isPassFlag, Integer userId)
			throws TrainBizException {
		paper.setMark(mark);
		paper.setComment(comment);
		paper.setUpdateBy(userId);
		paper.setUpdateTime(new Date());
		if (isPassFlag) {// 及格
			paper.setPaperStatus("3");// 0-待批阅，1-待真人互动评分，3-考试通过，4-考试未通过
		} else {// 不及格
			paper.setPaperStatus("4");
		}

		// 根据试卷ID修改评分和评语
		int sum = this.traExamPaperDao.updateTraExamPaper(paper);
		if (0 > sum) {
			throw new TrainBizException("系统异常，真人互动评分失败");
		}
		return sum;
	}

	/**
	 * @Description 根据试卷ID查询期末考卷规则
	 * @param coursewareId
	 * @return
	 * @throws TrainBizException
	 * @throws
	 */
	private TraFinalExamRuleVO queryTraFinalExamRule(Integer paperId) throws TrainBizException {
		TraFinalExamRuleVO finalExamRule = this.traFinalExamRuleDao.queryTraFinalExamRuleByPaperId(paperId);
		if (null == finalExamRule) {
			throw new TrainBizException("未查询到期末考卷规则");
		}
		return finalExamRule;
	}

	/**
	 * @Title queryTeacherList
	 * @Description 查询老师列表
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryTeacherList(java.util.Map)
	 */
	@Override
	public Page<Map<String, Object>> queryTeacherList(Map<String, Object> params) {
		params.put("roleName", "老师");// 用角色名称为“老师”为条件进行完全匹配
		Page<Map<String, Object>> list = this.traTeacherStudentDao.queryTeacherList(params);
		return list;
	}

	/**
	 * 
	 * @Title updateCourseEndTime
	 * @Description 课程延时
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#updateCourseEndTime(java.util.Map)
	 */
	@Override
	public int updateCourseEndTime(Map<String, Object> params) {
		SysUserVO user = (SysUserVO) params.get("user");
		Integer userId = user.getId();
		Long endDateL = Long.parseLong(String.valueOf(params.get("endDate")));
		Date date = new Date(endDateL);
		String endDateStr = DateUtil.format(date, "yyyy-MM-dd 23:59:59");

		// 校验日期不可早于今天
		Date endDate = checkEndTime(endDateStr);

		// 课程延时，修改课程截止时间
		int sum = updateCourseEndTime(params, endDate, userId);

		// 增加学生流水信息
		sum = saveTraStudentRecord(params, endDateStr, userId);
		return sum;
	}

	/**
	 * @Description 校验日期不可早于今天
	 * @param endDateStr
	 * @return
	 * @throws TrainBizException
	 * @throws
	 */
	private Date checkEndTime(String endDateStr) throws TrainBizException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDate = null;
		Date todayDate = null;
		try {
			endDate = sdf.parse(endDateStr);
			todayDate = new Date();
			if (endDate.compareTo(todayDate) < 0) {
				throw new TrainBizException("日期不可早于今天");
			}
		} catch (ParseException e) {
			throw new TrainBizException("日期格式错误");
		}
		return endDate;
	}

	/**
	 * @Description 课程延时，修改课程截止时间
	 * @param params
	 * @param endDate
	 * @param userId
	 * @throws TrainBizException
	 */
	private int updateCourseEndTime(Map<String, Object> params, Date endDate, Integer userId) throws TrainBizException {
		Integer id = (Integer) params.get("learningProgressId");
		Date date = new Date();

		TraLearningProgressVO vo = new TraLearningProgressVO();
		vo.setId(id);
		vo.setCourseEndTime(endDate);
		vo.setCourseStatus("1");
		vo.setUpdateBy(userId);
		vo.setUpdateTime(date);
		int sum = this.traLearningProgressDao.updateCourseEndTime(vo);
		if (0 > sum) {
			throw new TrainBizException("系统异常，课程延时失败");
		}
		return sum;
	}

	/**
	 * @Description 增加学生流水信息
	 * @param params
	 * @param endDateStr
	 * @param userId
	 * @return
	 * @throws
	 */
	private int saveTraStudentRecord(Map<String, Object> params, String endDateStr, Integer userId) {
		int sum = 0;
		Date date = new Date();
		Integer studentId = (Integer) params.get("studentId");
		Integer teacherId = (Integer) params.get("teacherId");
		Integer courseId = (Integer) params.get("courseId");
		Integer coursewareId = (Integer) params.get("coursewareId");

		StringBuffer sb = new StringBuffer(128);
		sb.append("班主任 将课程时限延长至 ").append(endDateStr);

		TraStudentRecordVO record = new TraStudentRecordVO();
		record.setStudentId(studentId);// 学生ID

		// start,update by gaoshan,2014-09-26
		// 新学员需求调整，流水添加字段进度ID
		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getLearningProgressVOByStudentId(studentId);
		if (null == learningProgressVO) {
			throw new TrainBizException("未查询到学生学习进度");
		}
		record.setLearningProgressId(learningProgressVO.getId());
		// start,update by gaoshan,2014-09-26

		record.setTeacherId(teacherId);// 老师ID
		record.setCourseId(courseId);// 课程ID
		record.setCoursewareId(coursewareId);// 课件ID
		record.setOperatingType("3");// 操作类型
		record.setBeginTime(date);// 开始时间
		record.setEndTime(null);// 结束时间
		record.setDescription(sb.toString());// 描述
		record.setPaperId(null);// 试卷ID
		record.setIsTimeout("1");// 是否超时
		record.setInsertBy(userId);// 创建人
		record.setInsertTime(date);// 创建时间
		record.setUpdateBy(userId);// 最后修改人
		record.setUpdateTime(date);// 最后修改时间

		sum = this.traStudentRecordDao.saveTraStudentRecord(record);
		if (0 > sum) {
			throw new TrainBizException("系统异常，延时处理时，添加学生流水信息失败");
		}
		return sum;
	}

	/**
	 * @Title queryCommentOfPaper
	 * @Description 查看评语
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryCommentOfPaper(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryCommentOfPaper(Map<String, Object> params) {
		Integer paperId = (Integer) params.get("paperId");
		// 根据试卷ID查询试卷信息
		Map<String, Object> paper = this.traExamPaperDao.queryTraExamPaperById(paperId);
		if (paper == null) {
			throw new TrainBizException("未查询到试卷信息");
		}
		return paper;
	}

	/**
	 * @Title saveTraLearningProgress
	 * @Description 新学员创建时，根据所属职位默认创建学习进度
	 * @param sysUserVO
	 * @param userId
	 * @see com.yazuo.erp.train.service.StudentManagementService#saveTraLearningProgress(com.yazuo.erp.system.vo.SysUserVO,
	 *      java.lang.Integer)
	 */
	@Override
	public void saveTraLearningProgress(SysUserVO sysUserVO, Integer userId) {
		// 根据职位ID查询默认课程信息
		Integer positionId = sysUserVO.getPositionId();
		Integer courseId = this.getCourseIdByPositionId(positionId);

		// 校验课程有效性，并添加学习进度
		this.checkAndCreateLearningProgress(sysUserVO, userId, courseId);
	}

	/**
	 * @Description 校验课程有效性，并添加学习进度
	 * @param sysUserVO
	 * @param userId
	 * @param courseId
	 * @throws TrainBizException
	 * @throws
	 */
	private void checkAndCreateLearningProgress(SysUserVO sysUserVO, Integer userId, Integer courseId) throws TrainBizException {
		// 校验课程有效性
		List<Integer> studentIdList = new ArrayList<Integer>();
		studentIdList.add(sysUserVO.getId());

		TraCourseVO course = this.traCourseDao.getTraCourseById(courseId);
		if (course == null) {
			throw new TrainBizException("未查询到课程信息");
		}

		String isEnable = course.getIsEnable();
		if ("0".equals(isEnable)) {
			throw new TrainBizException("该课程已被管理员删除");
		}

		// 查询课程的第一个有效课件
		Map<String, Object> courseware = this.traCoursewareDao.queryFirstCourseware(courseId);
		if (null == courseware) {
			throw new TrainBizException("未查询到课件信息");
		}
		Integer coursewareId = (Integer) courseware.get("id");

		// 添加学习进度
		this.saveTraLearningProgress(courseId, coursewareId, sysUserVO.getId(), userId);
	}

	/**
	 * @Description 根据职位ID查询默认课程信息
	 * @param positionId
	 * @return
	 * @throws TrainBizException
	 * @throws
	 */
	private Integer getCourseIdByPositionId(Integer positionId) throws TrainBizException {
		if (null == positionId) {
			throw new TrainBizException("职位ID为空");
		}

		SysPositionVO position = sysPositionDao.getById(positionId);
		if (null == position) {
			throw new TrainBizException("未查询到职位信息");
		} else if ("0".equals(position.getIsEnable())) {
			throw new TrainBizException("无效职位");
		}

		TraPositionCourseVO pc = new TraPositionCourseVO();
		pc.setPositionId(positionId);
		List<TraPositionCourseVO> traPositionCourses = traPositionCourseDao.getTraPositionCourses(pc);
		if (null == traPositionCourses) {
			throw new TrainBizException("未查询到该职位[" + position.getPositionName() + "]默认需要学习的课程");
		} else if (1 != traPositionCourses.size()) {
			throw new TrainBizException("系统异常，未查询到该职位[" + position.getPositionName() + "]默认需要学习的课程");
		}

		Integer courseId = traPositionCourses.get(0).getCourseId();
		return courseId;
	}

	/**
	 * @Title updateTraLearningProgressToAbnormalTermination
	 * @Description 新学员，所属职位进行修改时，如果新老职位所对应的课程不同，则原学习进度置为4-异常终止，并建立新的学习进度
	 * @param sysUserVO
	 * @param oldPositionId
	 * @param userId
	 * @see com.yazuo.erp.train.service.StudentManagementService#updateTraLearningProgressToAbnormalTermination(com.yazuo.erp.system.vo.SysUserVO,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void updateTraLearningProgressToAbnormalTermination(SysUserVO sysUserVO, Integer oldPositionId, Integer userId) {
		Integer uid = sysUserVO.getId();
		Integer newPositionId = sysUserVO.getPositionId();

		// 根据职位ID查询默认课程信息
		Integer newCourseId = this.getCourseIdByPositionId(newPositionId);

		if (null == oldPositionId) {
			throw new TrainBizException("职位ID为空");
		}

		SysPositionVO position = sysPositionDao.getById(oldPositionId);
		if (null == position) {
			throw new TrainBizException("未查询到职位信息");
		} else if ("0".equals(position.getIsEnable())) {
			throw new TrainBizException("无效职位");
		}

		TraPositionCourseVO pc = new TraPositionCourseVO();
		pc.setPositionId(oldPositionId);
		List<TraPositionCourseVO> traPositionCourses = traPositionCourseDao.getTraPositionCourses(pc);
		Integer oldCourseId = null;
		if (null == traPositionCourses) {
			oldCourseId = null;// 原职位关联课程为空
		} else if (1 != traPositionCourses.size()) {
			throw new TrainBizException("系统异常，该职位[" + position.getPositionName() + "]默认需要学习的课程不为一");
		} else {
			oldCourseId = traPositionCourses.get(0).getCourseId();
		}

		if (null == oldCourseId) {
			// 原职位关联课程为空，查询是否有学习进度
			// 如果有，判断课程是否相同，不同时，且没有毕业，则原学习进度置为4-异常终止，并建立新的学习进度
			// 如果没有，创建新的学习进度
			TraLearningProgressVO oldProcess = traLearningProgressDao.queryTraLearningProcessByStudentId(uid);
			this.handleLearningProgress(sysUserVO, userId, newCourseId, oldProcess);

		} else if (newCourseId.intValue() != oldCourseId.intValue()) {
			// 原职位关联课程不为空，且新旧课程不同，查询是否有学习进度
			// 如果有，判断课程是否相同，不同时，且没有毕业，则原学习进度置为4-异常终止，并建立新的学习进度
			// 如果没有，创建新的学习进度
			TraLearningProgressVO oldProcess = traLearningProgressDao.queryTraLearningProcess(oldCourseId, uid);
			this.handleLearningProgress(sysUserVO, userId, newCourseId, oldProcess);
		}
	}

	/**
	 * @Description TODO
	 * @param sysUserVO
	 * @param userId
	 * @param newCourseId
	 * @param oldProcess
	 * @throws TrainBizException
	 * @throws
	 */
	private void handleLearningProgress(SysUserVO sysUserVO, Integer userId, Integer newCourseId, TraLearningProgressVO oldProcess)
			throws TrainBizException {
		if (null != oldProcess && newCourseId != oldProcess.getCourseId() && !"2".equals(oldProcess.getCourseStatus())) {
			oldProcess.setCourseStatus("4");// 4-异常终止
			oldProcess.setUpdateBy(userId);
			oldProcess.setUpdateTime(new Date());
			int count = traLearningProgressDao.updateTraLearningProgress(oldProcess);
			if (1 > count) {
				throw new TrainBizException("原课程异常终止失败");
			}
		}

		// 校验课程有效性，并建立新的学习进度
		this.checkAndCreateLearningProgress(sysUserVO, userId, newCourseId);
	}

	/**
	 * @Title updateRecord
	 * @Description 更新学生流水表的学习进度，针对大于学习进度创建时间的流水，进度ID修改为最近的学习进度（暂时使用，不通用）
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#updateRecord(java.util.Map)
	 */
	@Override
	public int updateRecord(Map<String, Object> params) {
		SysUserVO user = (SysUserVO) params.get("user");
		Integer userId = user.getId();

		// 查询所有的有效用户
		List<SysUserVO> sysUserList = sysUserDao.getSysUserList();

		for (SysUserVO sysUserVO : sysUserList) {
			Integer studentId = sysUserVO.getId();

			// 根据学生ID查询最近一条学习进度
			TraLearningProgressVO learningProgressVO = traLearningProgressDao.getLastLearningProgressVOByStudentId(studentId);
			if (learningProgressVO != null) {
				Integer learningProgressId = learningProgressVO.getId();
				Date insertTime = learningProgressVO.getInsertTime();

				TraStudentRecordVO entity = new TraStudentRecordVO();
				entity.setStudentId(studentId);
				entity.setLearningProgressId(learningProgressId);
				entity.setInsertTime(insertTime);
				entity.setUpdateBy(userId);
				entity.setUpdateTime(new Date());

				// 更新学生流水表中进度ID为当前最近的进度ID
				int count = traStudentRecordDao.updateLearningProgressIdOfTraStudentRecord(entity);
			}
		}

		return 1;
	}

	/**
	 * @Title updateLearningProcess
	 * @Description 修正生产数据，修正学生进度表，将定时器跑错的数据（已毕业的改为超时）修正正常（已经毕业）
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#updateLearningProcess(java.util.Map)
	 */
	@Override
	public int updateLearningProcess(Map<String, Object> params) {
		SysUserVO user = (SysUserVO) params.get("user");
		Integer userId = user.getId();

		List<TraStudentRecordVO> recordVOs = traStudentRecordDao.getTraStudentRecordsOfInterview();
		for (TraStudentRecordVO recordVO : recordVOs) {
			Integer paperId = recordVO.getPaperId();

			TraExamPaperVO paper = traExamPaperDao.getTraExamPaperById(paperId);
			int count = traLearningProgressDao.updateTraLearningProgressByPaperId(paperId, "2", "2", userId);
		}
		return 1;
	}

}
