package com.yazuo.erp.train.service.impl;

import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.exception.SystemBizException;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.dao.*;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.erp.train.service.ExamService;
import com.yazuo.erp.train.util.TimeUtils;
import com.yazuo.erp.train.vo.*;
import com.yazuo.util.DeviceTokenUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class ExamServiceImpl implements ExamService {

	@Resource
	private TraCoursewareDao traCoursewareDao;

	@Resource
	private TraCourseDao traCourseDao;

	@Resource
	private TraRuleDao traRuleDao;

	@Resource
	private TraRequiredQuestionDao traRequiredQuestionDao;

	@Resource
	private TraQuestionDao traQuestionDao;

	@Resource
	private TraQuestionOptionDao traQuestionOptionDao;

	@Resource
	private TraExamPaperDao traExamPaperDao;

	@Resource
	private TraExamDtlDao traExamDtlDao;

	@Resource
	private TraExamOptionDao traExamOptionDao;

	@Resource
	private TraTeacherStudentDao traTeacherStudentDao;

	@Resource
	private TraLearningProgressDao traLearningProgressDao;

	@Resource
	private TraStudentRecordDao traStudentRecordDao;

	@Resource
	private TraPptDao traPptDao;

	@Resource
	private TraPptDtlDao pptDtlDao;

	@Resource
	private TraAttachmentDao traAttachmentDao;

	@Resource
	private TraFinalExamRuleDao traFinalExamDao;

	@Resource
	private TraWrongQuestionDao traWrongQuestionDao;

	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

	@Resource
	private SysUserDao sysUserDao;

	@Override
	public boolean isExamAvailable(Integer userId, Integer courseId, Integer coursewareId, Integer learningProgressId) {
		Integer count = this.traStudentRecordDao.getStudentRecordStudyCount(userId, courseId, coursewareId, learningProgressId);
		return count > 0;
	}

	@Override
	public TraExamPaperVO getExamPaper(Integer studentId, Integer courseId, Integer coursewareId, Integer learningProgressId) {
		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getTraLearningProgressById(learningProgressId);// 学习进度
		TraCoursewareVO coursewareVO = this.traCoursewareDao.getTraCoursewareById(coursewareId);// 课件
		String coursewareName = coursewareVO.getCoursewareName();// 课件名称
		TraRuleVO traRule = this.getTraRule(coursewareId, coursewareName);// 考试规则
		String paperType = traRule.getPaperType();// 考卷类型
		String isFormal = this.getIsFormalByUserId(studentId);// 0-新员工，1-老员工
		Integer teacherId = this.traTeacherStudentDao.getTeacherIdByStudentId(studentId);// 老师ID
		this.checkTeacher(paperType, isFormal, teacherId);// 根据新老员工标识和考卷类型，检查考试前是否需要分配老师

		// 保存试卷
		TraExamPaperVO examPaperVO = new TraExamPaperVO();
		examPaperVO.setLearningProgressId(learningProgressVO.getId());
		examPaperVO.setCoursewareId(coursewareVO.getId());
		examPaperVO.setPaperName(coursewareName);
		examPaperVO.setStudentId(studentId);
		examPaperVO.setTeacherId(teacherId);
		examPaperVO.setBeginTime(null);// 考试开始时间不是查询出试卷的时间，PPT是第一次开始录音的时间，实操是点击开始实操的时间
		examPaperVO.setPaperStatus("0");
		examPaperVO.setInsertBy(studentId);
		examPaperVO.setInsertTime(new Date());
		examPaperVO.setUpdateBy(studentId);
		examPaperVO.setUpdateTime(new Date());
		examPaperVO.setExamPaperType(paperType);
		examPaperVO.setTeacherId(teacherId);
		examPaperVO.setTimeLimit(TimeUtils.toHours(traRule.getTimeLimit()));// 设置时间限制
		this.traExamPaperDao.saveTraExamPaper(examPaperVO);

		if ("0".equals(paperType)) {// 0-笔试题
			List<TraExamDtlVO> examDtlVOs = this.generateWrittenExam(studentId, examPaperVO.getId(), traRule);
			examPaperVO.setTraExamDtlVOs(examDtlVOs);
		} else if ("1".equals(paperType)) {// 1-ppt讲解
			examPaperVO = this.generatePptExamPaperVO(examPaperVO, traRule);
		} else if ("2".equals(paperType)) {// 2-产品实操
			List<TraExamDtlVO> examDtlVOs = this.generatePracticeExam(studentId, examPaperVO.getId(), traRule);
			examPaperVO.setTraExamDtlVOs(examDtlVOs);
		} else {// 3-问答题
			throw new TrainBizException("试题类型[" + paperType + "]不正确，");
		}

		return examPaperVO;
	}

	/**
	 * @Description 根据课件ID查询考试规则
	 * @param coursewareId
	 * @param coursewareName
	 * @return
	 * @return TraRuleVO
	 * @throws
	 */
	private TraRuleVO getTraRule(Integer coursewareId, String coursewareName) {
		TraRuleVO traRule = this.traRuleDao.getTraRuleByCoursewareId(coursewareId);
		if (traRule == null) {
			throw new TrainBizException("课件<<" + coursewareName + ">>未配置考试规则，请联系管理员！");
		}
		return traRule;
	}

	/**
	 * @Description 检查老师，新员工，且老师为空时，校验试卷类型，如果PPT或者简答题，则提示分配老师
	 * @param paperType
	 * @param isFormal
	 * @param teacherId
	 * @return void
	 * @throws
	 */
	private void checkTeacher(String paperType, String isFormal, Integer teacherId) {
		if (null != isFormal && "0".equals(isFormal) && null == teacherId) {
			if ("1".equals(paperType) || "3".equals(paperType)) {
				throw new TrainBizException("友情提示：您还没有指导老师，请联系管理员，为您分配指导老师，否则不能进行考试");
			}
		}
	}

	/**
	 * @Description 根据用户ID查询新/老用户标识
	 * @param studentId
	 * @return
	 * @return String
	 * @throws
	 */
	private String getIsFormalByUserId(Integer studentId) {
		SysUserVO sysUser = sysUserDao.getSysUserById(studentId);
		if (null == sysUser) {
			throw new TrainBizException("未查询到学生信息");
		}
		String isFormal = sysUser.getIsFormal();
		return isFormal;
	}

	/**
	 * @Description 生成实操试卷
	 * @param studentId
	 * @param examPaperId
	 * @param ruleVO
	 * @return
	 * @return List<TraExamDtlVO>
	 * @throws
	 */
	private List<TraExamDtlVO> generatePracticeExam(Integer studentId, Integer examPaperId, TraRuleVO ruleVO) {
		List<Integer> questionIds = this.getQuestionIds(ruleVO);
		if (questionIds.size() == 0) {
			throw new TrainBizException("没有实操试题！");
		}
		return this.generateAllPracticeExam(studentId, examPaperId, questionIds);
	}

	@Override
	public TraPptVO getPpt(Integer pptId) {
		TraPptVO pptVO = this.traPptDao.getTraPptById(pptId);
		List<TraPptDtlVO> pptDtlVOs = this.pptDtlDao.getPptDtlVOsByPptId(pptId);
		pptVO.setPptDtlVOs(pptDtlVOs);
		String prefix = DeviceTokenUtil.getPropertiesValue("pptPhotoPath");
		for (TraPptDtlVO pptDtlVO : pptDtlVOs) {
			pptDtlVO.setUri(prefix + "/" + pptDtlVO.getPptId() + "/" + pptDtlVO.getPptDtlName());
		}
		return pptVO;
	}

	@Override
	public List<TraExamDtlVO> executeScoreExam(Integer studentId, List<TraExamDtlVO> userExamDtlVOs) {
		List<TraExamDtlVO> result = this.scoreExam(studentId, userExamDtlVOs, false);
		return result;
	}

	@Transactional(value = "txManager", rollbackFor = Exception.class)
	private List<TraExamDtlVO> scoreExam(Integer studentId, List<TraExamDtlVO> userExamDtlVOs, boolean isFinalExam) {
		SysUserVO user = sysUserDao.getSysUserById(studentId);
		if (null == user) {
			throw new SystemBizException("未查询到学员信息");
		}
		String isFormal = user.getIsFormal();// 0-新学员，1-老学员

		Map<Integer, TraExamDtlVO> userExamDtlVOIdIndex = new HashMap<Integer, TraExamDtlVO>();
		for (TraExamDtlVO examDtl : userExamDtlVOs) {
			userExamDtlVOIdIndex.put(examDtl.getId(), examDtl);
		}

		List<Integer> examDtlIds = new ArrayList<Integer>(userExamDtlVOIdIndex.keySet());
		List<TraExamDtlVO> examDtlVOs = this.traExamDtlDao.getTraExamDtlVOsWithOptionsByIds(examDtlIds);
		List<TraExamOptionVO> examOptionVOs = this.traExamOptionDao.getTraExamOptionByExamDtlIds(examDtlIds);

		Map<Integer, TraExamDtlVO> examDtlVOIdIndex = new HashMap<Integer, TraExamDtlVO>();
		for (TraExamDtlVO examDtlVO : examDtlVOs) {
			examDtlVOIdIndex.put(examDtlVO.getId(), examDtlVO);
		}

		for (TraExamOptionVO examOptionVO : examOptionVOs) {
			examDtlVOIdIndex.get(examOptionVO.getExamDtlId()).getExamOptionVOs().add(examOptionVO);
		}

		// 保存对题和错题
		List<TraExamDtlVO> correctExam = new ArrayList<TraExamDtlVO>();
		List<TraExamDtlVO> wrongExam = new ArrayList<TraExamDtlVO>();

		Integer examPaperId = examDtlVOs.get(0).getPaperId();
		TraExamPaperVO examPaperVO = this.traExamPaperDao.getTraExamPaperById(examPaperId);
		String examPaperType = examPaperVO.getExamPaperType();

		// 判题
		if ("0".equals(examPaperType) || "5".equals(examPaperType)) {// 0-笔试题、5-期末试卷
			for (TraExamDtlVO examDtlVO : examDtlVOs) {
				TraExamDtlVO userExamDtlVO = userExamDtlVOIdIndex.get(examDtlVO.getId());
				if (this.judgeExamDtls(examDtlVO, userExamDtlVO)) {
					examDtlVO.setIsCorrect("1");
					correctExam.add(examDtlVO);
				} else {
					examDtlVO.setIsCorrect("0");
					wrongExam.add(examDtlVO);
				}
			}
		} else if ("2".equals(examPaperType)) {// 2-产品实操
			for (TraExamDtlVO examDtlVO : examDtlVOs) {
				TraExamDtlVO userExamDtlVO = userExamDtlVOIdIndex.get(examDtlVO.getId());
				String isCorrect = userExamDtlVO.getIsCorrect();
				if ("1".equals(isCorrect)) {
					examDtlVO.setIsCorrect("1");
					correctExam.add(examDtlVO);
				} else {
					examDtlVO.setIsCorrect("0");
					wrongExam.add(examDtlVO);
				}
			}
		} else {
			throw new TrainBizException("试卷类型异常[" + examPaperType + "]");
		}

		// 保存正确题干
		List<Integer> tmpCorrectIds = new ArrayList<Integer>();
		for (TraExamDtlVO examDtlVO : correctExam) {
			tmpCorrectIds.add(examDtlVO.getId());
		}
		if (tmpCorrectIds.size() > 0) {
			this.traExamDtlDao.updateTraExamDtlForCorrect(tmpCorrectIds);
		}

		// 保存错误题干
		List<Integer> tmpWrongIds = new ArrayList<Integer>();
		for (TraExamDtlVO examDtlVO : wrongExam) {
			tmpWrongIds.add(examDtlVO.getId());
		}
		if (tmpWrongIds.size() > 0) {
			this.traExamDtlDao.updateTraExamDtlForWrong(tmpWrongIds);
		}

		// 保存选项
		List<Integer> selectedOptionIds = new ArrayList<Integer>();
		List<Integer> nonSelectedOptionIds = new ArrayList<Integer>();
		for (TraExamDtlVO examDtlVO : userExamDtlVOs) {
			for (TraExamOptionVO userExamOptionVO : examDtlVO.getExamOptionVOs()) {
				if ("1".equals(userExamOptionVO.getIsSelected())) {
					selectedOptionIds.add(userExamOptionVO.getId());
				} else {
					nonSelectedOptionIds.add(userExamOptionVO.getId());
				}
			}
		}
		if (nonSelectedOptionIds.size() > 0) {
			this.traExamOptionDao.batchUpdateExamOptionForNonSelected(nonSelectedOptionIds);
		}
		if (selectedOptionIds.size() > 0) {
			this.traExamOptionDao.batchUpdateExamOptionForSelected(selectedOptionIds);
		}

		// 更新试题
		BigDecimal passScore;
		if ("5".equals(examPaperType)) {
			// 得到考试规则
			TraFinalExamRuleVO finalExamRuleVO = this.traFinalExamDao.getFinalExamRuleByExamPaperId(examPaperId);
			passScore = finalExamRuleVO.getPassScore();
		} else {
			TraRuleVO ruleVO = this.traRuleDao.getTraRuleByCoursewareId(examPaperVO.getCoursewareId());
			passScore = ruleVO.getPassingScore();
		}

		// 设置考试结果
		BigDecimal score = new BigDecimal("0");
		if ("2".equals(examPaperType)) {// 2-产品实操
			score = new BigDecimal(wrongExam.size() > 0 ? "0" : "100");
		} else {
			score = new BigDecimal(new Float(correctExam.size()) / (correctExam.size() + wrongExam.size()))
					.multiply(new BigDecimal(100));
		}
		examPaperVO.setEndTime(new Date());
		examPaperVO.setTotalScore(score);
		examPaperVO.setUpdateBy(studentId);
		examPaperVO.setUpdateTime(new Date());

		boolean isPassed = false;
		if ("2".equals(examPaperType)) {// 2-产品实操
			isPassed = score.compareTo(new BigDecimal("100")) >= 0;
		} else {
			isPassed = score.compareTo(passScore) >= 0;
		}

		if (isFinalExam) {
			examPaperVO.setPaperStatus(isPassed ? "1" : "4");
		} else {
			examPaperVO.setPaperStatus(isPassed ? "3" : "4");
		}
		this.traExamPaperDao.updateTraExamPaper(examPaperVO);

		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getTraLearningProgressById(examPaperVO
				.getLearningProgressId());
		String courseStatus = learningProgressVO.getCourseStatus();

		// 未毕业，新学员，通过考试，则向前移动一个课件
		if (!"2".equals(courseStatus) && "0".equals(isFormal) && isPassed
				&& examPaperVO.getCoursewareId().equals(learningProgressVO.getCoursewareId())) {
			TraCourseCoursewareVO courseCoursewareVO = this.traCourseCoursewareDao.nextCourseCoursewareVO(
					learningProgressVO.getCourseId(), learningProgressVO.getCoursewareId());
			if (courseCoursewareVO != null) {
				learningProgressVO.setCoursewareId(courseCoursewareVO.getCoursewareId());
				this.traLearningProgressDao.updateTraLearningProgress(learningProgressVO);
			}
		}

		// 未毕业，老学员，且通过考试，则修改学习进度为已毕业
		if (!"2".equals(courseStatus) && "1".equals(isFormal) && isPassed) {
			learningProgressVO.setCourseStatus("2");
			learningProgressVO.setProgressType("2");
			learningProgressVO.setUpdateBy(studentId);
			learningProgressVO.setUpdateTime(new Date());
			this.traLearningProgressDao.updateTraLearningProgress(learningProgressVO);
		}

		TraStudentRecordVO studentRecordVO = null;
		if (isFinalExam) {
			studentRecordVO = this.traStudentRecordDao.getFinalExamStudentRecord(examPaperVO.getStudentId(),
					learningProgressVO.getCourseId(), learningProgressVO.getId());
		} else {
			studentRecordVO = this.traStudentRecordDao.getStudentExamRecord(examPaperVO.getStudentId(),
					learningProgressVO.getCourseId(), examPaperVO.getCoursewareId(), learningProgressVO.getId());
		}
		studentRecordVO.setEndTime(new Date());
		DecimalFormat decimalFormat = new DecimalFormat("##.##");
		studentRecordVO.setDescription(studentRecordVO.getDescription() + ", "
				+ decimalFormat.format(examPaperVO.getTotalScore()) + "分");
		this.traStudentRecordDao.updateTraStudentRecord(studentRecordVO);

		List<TraExamDtlVO> result = correctExam;
		result.addAll(wrongExam);
		if (!isFinalExam && wrongExam.size() > 0) {
			int examCount = this.traStudentRecordDao.getStudentRecordExamCount(studentId, learningProgressVO.getCourseId(),
					examPaperVO.getCoursewareId(), learningProgressVO.getId());

			// 只有第一次考试
			if (examCount == 0) {
				// 更新错题库
				List<TraWrongQuestionVO> wrongQuestionVOs = new LinkedList<TraWrongQuestionVO>();
				for (TraExamDtlVO examDtlVO : wrongExam) {
					TraWrongQuestionVO wrongQuestionVO = new TraWrongQuestionVO();
					wrongQuestionVO.setStudentId(studentId);
					wrongQuestionVO.setPaperId(examPaperId);
					wrongQuestionVO.setQuestionId(examDtlVO.getQuestionId());
					wrongQuestionVO.setRemark("错题");
					wrongQuestionVO.setInsertBy(studentId);
					wrongQuestionVO.setInsertTime(new Date());
					wrongQuestionVO.setUpdateBy(studentId);
					wrongQuestionVO.setUpdateTime(new Date());
					wrongQuestionVOs.add(wrongQuestionVO);
				}
				// 保存错题
				this.traWrongQuestionDao.batchInsertWrongQuestions(wrongQuestionVOs);
			}
		}

		// 只复制isSelect属性
		Map<Integer, TraExamOptionVO> examOptionVOMap = new HashMap<Integer, TraExamOptionVO>();
		for (TraExamDtlVO examDtlVO : userExamDtlVOs) {
			for (TraExamOptionVO examOptionVO : examDtlVO.getExamOptionVOs()) {
				examOptionVOMap.put(examOptionVO.getId(), examOptionVO);
			}
		}

		for (TraExamDtlVO examDtlVO : result) {
			for (TraExamOptionVO examOptionVO : examDtlVO.getExamOptionVOs()) {
				TraExamOptionVO userExamOptionVO = examOptionVOMap.get(examOptionVO.getId());
				examOptionVO.setIsSelected(userExamOptionVO.getIsSelected());
			}
		}

		return result;
	}

	@Override
	@Transactional(value = "txManager", rollbackFor = Exception.class)
	public TraExamPaperVO getFinalExamPaper(Integer studentId, Integer courseId) {
		// 得到课程信息
		TraCourseVO courseVO = this.traCourseDao.getTraCourseById(courseId);
		// 得到考试规则
		TraFinalExamRuleVO finalExamRuleVO = this.traFinalExamDao.getFinalExamRuleByCourseId(courseId);
		if (finalExamRuleVO == null) {
			throw new TrainBizException("课程未配置期末考试规则，请联系管理员!");
		}
		// 得到学习进度信息
		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getCurrentTraLearningProcess(courseId, studentId);

		// 课程已超时，不可以参加期末考试
		if ("3".equals(learningProgressVO.getCourseStatus()) && learningProgressVO.getCourseEndTime() != null
				&& learningProgressVO.getCourseEndTime().getTime() < new Date().getTime()) {
			throw new TrainBizException("课程已超时，不可以参加期末考试，请联系您的班主任，予以延时！");
		}

		// 考试已通过，不可以再参加考试
		if (this.traExamPaperDao.getExamPapersForPassedForFinalExam(studentId, courseId, learningProgressVO.getId()).size() > 0) {
			throw new TrainBizException("笔试已通过，不可以再参加考试");
		}
		Integer teacherId = this.traTeacherStudentDao.getTeacherIdByStudentId(studentId);

		// 新学员需求调整，可暂不分配老师先学习
		if (null == teacherId) {
			throw new TrainBizException("友情提示：您还没有指导老师，请联系管理员，为您分配指导老师，否则不能进行期末考试");
		}

		TraExamPaperVO examPaperVO = new TraExamPaperVO();
		examPaperVO.setLearningProgressId(learningProgressVO.getId());
		examPaperVO.setExamPaperType("5");
		examPaperVO.setCoursewareId(-1);
		examPaperVO.setPaperName(courseVO.getCourseName() + "的期末考试");
		examPaperVO.setStudentId(studentId);
		examPaperVO.setTeacherId(teacherId);
		examPaperVO.setBeginTime(new Date());
		examPaperVO.setPaperStatus("4");
		examPaperVO.setRemark(null);
		examPaperVO.setInsertBy(studentId);
		examPaperVO.setInsertTime(new Date());
		examPaperVO.setUpdateBy(studentId);
		examPaperVO.setUpdateTime(new Date());
		// 实现小时与分钟转换
		examPaperVO.setTimeLimit(TimeUtils.toHours(finalExamRuleVO.getTimeLimit()));

		// 保存试卷类
		this.traExamPaperDao.saveTraExamPaper(examPaperVO);

		List<Integer> questionIds = this.getQuestionIdsForFinalExam(finalExamRuleVO, studentId);
		if (questionIds.size() == 0) {
			throw new TrainBizException("没有笔试试题！");
		}
		// 生成笔试试卷
		List<TraExamDtlVO> examDtlVOs = this.generateAllExam(studentId, examPaperVO.getId(), questionIds);

		examPaperVO.setTraExamDtlVOs(examDtlVOs);

		StringBuffer description = new StringBuffer();
		description.append("参加");
		description.append(courseVO.getCourseName());
		description.append("的期末考试");

		// 插入流水
		TraStudentRecordVO studentRecordVO = new TraStudentRecordVO();
		studentRecordVO.setCourseId(courseId);
		studentRecordVO.setStudentId(studentId);
		studentRecordVO.setTeacherId(teacherId);
		studentRecordVO.setLearningProgressId(learningProgressVO.getId());
		studentRecordVO.setOperatingType("1");
		studentRecordVO.setBeginTime(new Date());

		// 判断课程过期
		studentRecordVO.setIsTimeout(learningProgressVO.getCourseEndTime().getTime() < new Date().getTime() ? "0" : "1");
		studentRecordVO.setPaperId(examPaperVO.getId());
		studentRecordVO.setDescription(description.toString());
		studentRecordVO.setInsertBy(studentId);
		studentRecordVO.setInsertTime(new Date());
		studentRecordVO.setUpdateBy(studentId);
		studentRecordVO.setUpdateTime(new Date());
		this.traStudentRecordDao.saveTraStudentRecord(studentRecordVO);

		// 更新进度
		learningProgressVO.setCourseStatus("1");
		learningProgressVO.setProgressType("1");
		this.traLearningProgressDao.updateTraLearningProgress(learningProgressVO);
		return examPaperVO;
	}

	@Override
	public List<TraExamDtlVO> executeScoreFinalExam(Integer studentId, List<TraExamDtlVO> examDtlVOs) {
		return this.scoreExam(studentId, examDtlVOs, true);
	}

	@Override
	public void saveAudio(Integer studentId, Integer examPaperId, Integer pptId, String prefix, MultipartFile audioFile,
			BigDecimal hours) {
		String originalFilename = audioFile.getOriginalFilename();
		String[] splitFilenames = originalFilename.split("\\.");
		String suffix = splitFilenames[splitFilenames.length - 1].trim();
		String filename = UUID.randomUUID() + "." + suffix;
		String path = prefix + File.separator + pptId + File.separator + filename;
		// savefile
		try {
			File file = new File(path);
			FileUtils.forceMkdir(new File(file.getParent()));
			if (!file.exists()) {
				file.createNewFile();
			}
			audioFile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TrainBizException("保存文件失败");
		}
		TraAttachmentVO attachmentVO = new TraAttachmentVO();
		attachmentVO.setAttachmentName(filename);
		attachmentVO.setAttachmentSuffix(suffix);
		attachmentVO.setAttachmentPath(String.valueOf(pptId));
		attachmentVO.setIsDownloadable("0");
		attachmentVO.setIsEnable("1");
		attachmentVO.setHours(hours);
		attachmentVO.setAttachmentSize(String.valueOf(audioFile.getSize()));
		attachmentVO.setRemark(audioFile.getOriginalFilename());
		attachmentVO.setInsertBy(studentId);
		attachmentVO.setInsertTime(new Date());
		attachmentVO.setUpdateBy(studentId);
		attachmentVO.setUpdateTime(new Date());
		attachmentVO.setAttachmentType("2");
		this.traAttachmentDao.saveTraAttachment(attachmentVO);

		// 更新 ExamPaper
		TraExamOptionVO examOptionVO = this.traExamOptionDao.getExamOptionVOByPaperIdAndpptId(examPaperId, pptId);
		examOptionVO.setAttachmentId(attachmentVO.getId());
		examOptionVO.setUpdateBy(studentId);
		examOptionVO.setUpdateTime(new Date());
		this.traExamOptionDao.updateTraExamOption(examOptionVO);

		TraExamPaperVO traExamPaper = this.traExamPaperDao.getTraExamPaperById(examPaperId);
		if (null == traExamPaper) {
			throw new TrainBizException("未查询到试卷信息");
		}
		Integer learningProgressId = traExamPaper.getLearningProgressId();
		Integer coursewareId = traExamPaper.getCoursewareId();

		// 更新流水
		TraStudentRecordVO examRecord = this.traStudentRecordDao.getStudentExamRecord(studentId, null, coursewareId,
				learningProgressId);
		examRecord.setEndTime(new Date());
		examRecord.setUpdateBy(studentId);
		examRecord.setUpdateTime(new Date());
		this.traStudentRecordDao.updateTraStudentRecord(examRecord);
	}

	/**
	 * 生成笔试试题
	 * 
	 * @param studentId
	 * @param examPaperId
	 * @param ruleVO
	 * @return
	 */
	private List<TraExamDtlVO> generateWrittenExam(Integer studentId, Integer examPaperId, TraRuleVO ruleVO) {
		List<Integer> questionIds = this.getQuestionIds(ruleVO);
		if (questionIds.size() == 0) {
			throw new TrainBizException("没有笔试试题！");
		}
		return this.generateAllExam(studentId, examPaperId, questionIds);
	}

	/**
	 * 得到考题列表ID
	 */
	private List<Integer> getQuestionIds(TraRuleVO ruleVO) {
		String paperType = ruleVO.getPaperType();
		List<TraQuestionVO> questionVOs = this.traQuestionDao.getAllQuestionVOByCoursewareId(ruleVO.getCoursewareId(), paperType);

		List<Integer> allQuestionIds = new ArrayList<Integer>();
		for (TraQuestionVO vo : questionVOs) {
			allQuestionIds.add(vo.getId());
		}

		Integer totalQuestionNum = ruleVO.getTotal().intValue();
		if ("1".equals(ruleVO.getRuleType())) {
			// 必考题
			List<Integer> resultQuestionIds = this.traRequiredQuestionDao.getQuestionIdsByCoursewareId(ruleVO.getCoursewareId());
			allQuestionIds.removeAll(resultQuestionIds);
			// 随机生成QuestionIds
			resultQuestionIds.addAll(this.randomGetQuestionIds(new ArrayList<Integer>(allQuestionIds), totalQuestionNum
					- resultQuestionIds.size()));
			return resultQuestionIds;
		} else if ("0".equals(ruleVO.getRuleType())) {
			return this.randomGetQuestionIds(new ArrayList<Integer>(allQuestionIds), totalQuestionNum);
		} else {
			throw new TrainBizException("规则类型错误");
		}
	}

	/**
	 * 得到期末考试的QuestionId列表
	 * 
	 * @return
	 */
	private List<Integer> getQuestionIdsForFinalExam(TraFinalExamRuleVO finalExamRuleVO, Integer studentId) {
		// 得到错题库的QuestionId列表
		List<Integer> errorQuestionIds = this.traWrongQuestionDao
				.getAllWrongQuestionIds(studentId, finalExamRuleVO.getCourseId());
		// 得到对应courseId的CoursewareId列表
		List<Integer> allQuestionIds = this.traQuestionDao.getAllWrittenQuestionIdsForCourse(finalExamRuleVO.getCourseId());
		// 删除错误QuestionId列表
		allQuestionIds.removeAll(errorQuestionIds);

		// 随机生成考题
		List<Integer> randomIds = this.randomGetQuestionIds(new ArrayList<Integer>(allQuestionIds), finalExamRuleVO.getTotal()
				.intValue());

		randomIds.addAll(errorQuestionIds);
		return randomIds;
	}

	/**
	 * 
	 * @param allQuestionIds
	 * @param num
	 * @return
	 */
	private List<Integer> randomGetQuestionIds(List<Integer> allQuestionIds, Integer num) {
		List<Integer> resultQuestionIds;

		if (num >= allQuestionIds.size()) {
			resultQuestionIds = allQuestionIds;
		} else {
			resultQuestionIds = new ArrayList<Integer>();
			while (num - resultQuestionIds.size() > 0) {
				int index = (int) Math.floor(Math.random() * allQuestionIds.size());
				Integer questionId = allQuestionIds.get(index);
				resultQuestionIds.add(questionId);
				allQuestionIds.remove(index);
			}
		}
		return resultQuestionIds;
	}

	/**
	 * @Description 生成笔试试卷（添加题干和选项）
	 * @param studentId
	 * @param examPaperId
	 * @param questionIds
	 * @return
	 * @return List<TraExamDtlVO>
	 * @throws
	 */
	// @Transactional("erp")
	private List<TraExamDtlVO> generateAllExam(Integer studentId, Integer examPaperId, List<Integer> questionIds) {
		// 复制题干
		List<TraQuestionVO> questions = this.traQuestionDao.getQuestionVOsByIds(questionIds);
		List<TraExamDtlVO> examDtlVOs = new ArrayList<TraExamDtlVO>(questions.size());
		for (TraQuestionVO questionVO : questions) {
			TraExamDtlVO traExamDtlVO = new TraExamDtlVO();
			traExamDtlVO.setPaperId(examPaperId);
			traExamDtlVO.setContent(questionVO.getQuestion());
			traExamDtlVO.setQuestionId(questionVO.getId());
			traExamDtlVO.setQuestionScore(questionVO.getScore());
			traExamDtlVO.setQuestionType(questionVO.getQuestionType());
			traExamDtlVO.setIsSystemDetermine("1");
			traExamDtlVO.setInsertBy(studentId);
			traExamDtlVO.setInsertTime(new Date());
			traExamDtlVO.setUpdateBy(studentId);
			traExamDtlVO.setUpdateTime(new Date());
			examDtlVOs.add(traExamDtlVO);
		}
		// 批量添加
		this.traExamDtlDao.batchInsertExamDtl(examDtlVOs);

		examDtlVOs = this.traExamDtlDao.getExamDtlVOs(examPaperId);
		// 取得examDtlId至questionId的映射
		Map<Integer, Integer> examDtlVOMap = new HashMap<Integer, Integer>();
		for (TraExamDtlVO examDtlVO : examDtlVOs) {
			examDtlVOMap.put(examDtlVO.getQuestionId(), examDtlVO.getId());
		}
		// 批量插入TraExamOption
		List<TraQuestionOptionVO> questionOptionVOs = this.traQuestionOptionDao.getTraQuestionOptionsByQuestionIds(questionIds);
		List<TraExamOptionVO> examOptionVOs = new ArrayList<TraExamOptionVO>();
		for (TraQuestionOptionVO questionOptionVO : questionOptionVOs) {
			TraExamOptionVO examOptionVO = new TraExamOptionVO();
			examOptionVO.setExamDtlId(examDtlVOMap.get(questionOptionVO.getQuestionId()));
			examOptionVO.setOptionContent(questionOptionVO.getOptionContent());
			examOptionVO.setIsAnswer(questionOptionVO.getIsRight());
			examOptionVO.setInsertBy(studentId);
			examOptionVO.setInsertTime(new Date());
			examOptionVO.setUpdateBy(studentId);
			examOptionVO.setUpdateTime(new Date());
			examOptionVOs.add(examOptionVO);
		}
		// 批量插入
		if (examOptionVOs.size() > 0) {
			this.traExamOptionDao.batchInsertExamOptionVO(examOptionVOs);
		}

		examOptionVOs = this.traExamOptionDao.getTraExamOptionByExamDtlIds(new ArrayList<Integer>(examDtlVOMap.values()));
		for (TraExamOptionVO examOptionVO : examOptionVOs) {
			examOptionVO.setIsAnswer(null);
		}

		// 为examDtlVOs设置 examOptions
		Map<Integer, TraExamDtlVO> examDtlVOIndex = new HashMap<Integer, TraExamDtlVO>();
		for (TraExamDtlVO examDtlVO : examDtlVOs) {
			examDtlVOIndex.put(examDtlVO.getId(), examDtlVO);
		}
		for (TraExamOptionVO examOptionVO : examOptionVOs) {
			examDtlVOIndex.get(examOptionVO.getExamDtlId()).getExamOptionVOs().add(examOptionVO);
		}
		return examDtlVOs;
	}

	/**
	 * @Description 生成PPT试卷（添加题干和选项）
	 * @param examPaperVO
	 * @param ruleVO
	 * @return
	 * @return TraExamPaperVO
	 * @throws
	 */
	private TraExamPaperVO generatePptExamPaperVO(TraExamPaperVO examPaperVO, TraRuleVO ruleVO) {
		Integer studentId = examPaperVO.getStudentId();
		// 查找试题
		TraQuestionVO questionVO = null;
		if ("0".equals(ruleVO.getRuleType())) {
			String paperType = "1";
			List<TraQuestionVO> questionVOs = this.traQuestionDao.getAllQuestionVOByCoursewareId(examPaperVO.getCoursewareId(),
					paperType);
			int index = (int) Math.floor(Math.random() * questionVOs.size());
			questionVO = questionVOs.get(index);
		} else {
			TraRequiredQuestionVO requiredQuestionVO = this.traRequiredQuestionDao.getOneQuestionIdByCoursewareId(examPaperVO
					.getCoursewareId());
			if (requiredQuestionVO == null) {
				throw new TrainBizException("未指定Ppt课件");
			}
			questionVO = this.traQuestionDao.getTraQuestionById(requiredQuestionVO.getQuestionId());
		}

		// 查询PPT对象
		TraPptVO pptVO = this.traPptDao.getTraPptById(questionVO.getPptId());
		if (null == pptVO) {
			throw new TrainBizException("未配置PPT课件");
		}
		TraExamDtlVO examDtlVO = new TraExamDtlVO();
		examDtlVO.setPaperId(examPaperVO.getId());
		examDtlVO.setQuestionId(questionVO.getId());
		examDtlVO.setContent(questionVO.getQuestion());
		examDtlVO.setQuestionType("3");
		examDtlVO.setIsSystemDetermine("0");
		examDtlVO.setPptId(pptVO.getId());
		examDtlVO.setInsertBy(studentId);
		examDtlVO.setInsertTime(new Date());
		examDtlVO.setUpdateBy(studentId);
		examDtlVO.setUpdateTime(new Date());
		this.traExamDtlDao.saveTraExamDtl(examDtlVO);
		examPaperVO.getTraExamDtlVOs().add(examDtlVO);

		// 添加ExamOption
		TraExamOptionVO examOptionVO = new TraExamOptionVO();
		examOptionVO.setExamDtlId(examDtlVO.getId());
		examOptionVO.setOptionContent("PPT没有内容");
		examOptionVO.setInsertBy(studentId);
		examOptionVO.setInsertTime(new Date());
		examOptionVO.setUpdateBy(studentId);
		examOptionVO.setUpdateTime(new Date());
		this.traExamOptionDao.saveTraExamOption(examOptionVO);
		examDtlVO.getExamOptionVOs().add(examOptionVO);

		return examPaperVO;
	}

	/**
	 * @Description 生成实操试卷
	 * @param studentId
	 * @param examPaperId
	 * @param questionIds
	 * @return
	 * @return List<TraExamDtlVO>
	 * @throws
	 */
	private List<TraExamDtlVO> generateAllPracticeExam(Integer studentId, Integer examPaperId, List<Integer> questionIds) {
		// 复制题干
		List<TraQuestionVO> questions = this.traQuestionDao.getQuestionVOsByIds(questionIds);
		List<TraExamDtlVO> examDtlVOs = new ArrayList<TraExamDtlVO>(questions.size());
		for (TraQuestionVO questionVO : questions) {
			TraExamDtlVO traExamDtlVO = new TraExamDtlVO();
			traExamDtlVO.setPaperId(examPaperId);
			traExamDtlVO.setContent(questionVO.getQuestion());
			traExamDtlVO.setQuestionId(questionVO.getId());
			traExamDtlVO.setQuestionScore(questionVO.getScore());
			traExamDtlVO.setQuestionType(questionVO.getQuestionType());
			traExamDtlVO.setIsSystemDetermine("1");
			traExamDtlVO.setUrl(questionVO.getUrl());
			traExamDtlVO.setInsertBy(studentId);
			traExamDtlVO.setInsertTime(new Date());
			traExamDtlVO.setUpdateBy(studentId);
			traExamDtlVO.setUpdateTime(new Date());
			examDtlVOs.add(traExamDtlVO);
		}
		// 批量添加
		this.traExamDtlDao.batchInsertExamDtl(examDtlVOs);

		examDtlVOs = this.traExamDtlDao.getExamDtlVOs(examPaperId);
		return examDtlVOs;
	}

	/**
	 * 判断每道题是否正确
	 * 
	 * @param examDtlVO
	 * @param userExamDtlVO
	 */
	private boolean judgeExamDtls(TraExamDtlVO examDtlVO, TraExamDtlVO userExamDtlVO) {
		List<Integer> correct = new ArrayList<Integer>();
		List<Integer> selected = new ArrayList<Integer>();
		for (TraExamOptionVO optionVO : userExamDtlVO.getExamOptionVOs()) {
			if ("1".equals(optionVO.getIsSelected())) {
				selected.add(optionVO.getId());
			}
		}

		for (TraExamOptionVO optionVO : examDtlVO.getExamOptionVOs()) {
			if ("1".equals(optionVO.getIsAnswer())) {
				correct.add(optionVO.getId());
			}
		}
		return selected.size() == correct.size() && selected.containsAll(correct);
	}

	/**
	 * @Title startExam
	 * @Description 开始考试
	 * @param studentId
	 * @param examPaperId
	 * @see com.yazuo.erp.train.service.ExamService#executeStartExam(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public void executeStartExam(Integer studentId, Integer examPaperId) {
		TraExamPaperVO traExamPaper = this.traExamPaperDao.getTraExamPaperById(examPaperId);
		if (null == traExamPaper) {
			throw new TrainBizException("未查询到试卷信息");
		}

		Integer learningProgressId = traExamPaper.getLearningProgressId();// 进度ID
		Integer coursewareId = traExamPaper.getCoursewareId();// 课件ID
		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getTraLearningProgressById(learningProgressId);// 得到学习进度信息
		Integer courseId = learningProgressVO.getCourseId();// 课程ID
		TraCoursewareVO coursewareVO = this.traCoursewareDao.getTraCoursewareById(coursewareId);// 得到课件信息
		String coursewareName = coursewareVO.getCoursewareName();// 课件名称
		TraRuleVO traRule = this.getTraRule(coursewareId, coursewareName);// 考卷规则
		String paperType = traRule.getPaperType();// 考卷类型
		String isFormal = this.getIsFormalByUserId(studentId);// 0-新员工，1-老员工
		Integer teacherId = this.traTeacherStudentDao.getTeacherIdByStudentId(studentId);// 老师ID
		this.checkTeacher(paperType, isFormal, teacherId);// 根据新老员工标识和考卷类型，检查考试前是否需要分配老师

		// 更新考试的开始时间
		traExamPaper.setBeginTime(new Date());
		traExamPaper.setUpdateBy(studentId);
		traExamPaper.setUpdateTime(new Date());
		this.traExamPaperDao.updateTraExamPaper(traExamPaper);

		// 添加考试流水
		StringBuffer description = new StringBuffer();
		Integer examTimes = this.traStudentRecordDao.getStudentRecordExamCount(studentId, courseId, coursewareId,
				learningProgressVO.getId());
		description.append("第");
		description.append(examTimes + 1);// 次数
		description.append("次参加 ");
		description.append(coursewareName);
		description.append(" 考试");

		TraStudentRecordVO studentRecordVO = new TraStudentRecordVO();
		studentRecordVO.setStudentId(studentId);
		studentRecordVO.setTeacherId(teacherId);
		studentRecordVO.setLearningProgressId(learningProgressVO.getId());
		studentRecordVO.setCourseId(courseId);
		studentRecordVO.setCoursewareId(coursewareId);
		studentRecordVO.setOperatingType("1");
		studentRecordVO.setBeginTime(new Date());
		studentRecordVO.setPaperId(examPaperId);
		studentRecordVO.setDescription(description.toString());
		if ("0".equals(isFormal)) {
			studentRecordVO.setIsTimeout("1");
		} else {
			studentRecordVO.setIsTimeout(learningProgressVO.getCoursewareStatus());
		}
		studentRecordVO.setInsertBy(studentId);
		studentRecordVO.setInsertTime(new Date());
		studentRecordVO.setUpdateBy(studentId);
		studentRecordVO.setUpdateTime(new Date());
		this.traStudentRecordDao.saveTraStudentRecord(studentRecordVO);

		// 更新进度
		if (!"2".equals(learningProgressVO.getCourseStatus())) {// 已毕业的学员，学习进度无需再次更新
			learningProgressVO.setProgressType("1");
			learningProgressVO.setUpdateBy(studentId);
			learningProgressVO.setUpdateTime(new Date());
			this.traLearningProgressDao.updateCourseEndTime(learningProgressVO);
		}
	}
}
