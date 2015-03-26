package com.yazuo.erp.train.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.ExamPaperService;
import com.yazuo.erp.train.service.ExamService;
import com.yazuo.erp.train.service.TraCoursewareService;
import com.yazuo.erp.train.vo.TraExamDtlVO;
import com.yazuo.erp.train.vo.TraExamOptionVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraPptVO;
import com.yazuo.util.DeviceTokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * 考试的控制器
 * 
 * @author congshuanglong
 */
@Controller
@RequestMapping("/train/exam")
@SessionAttributes(Constant.SESSION_USER)
public class ExamController {

	@Resource
	private ExamService examService;

	@Resource
	private ExamPaperService examPaperService;

	@Resource
	private TraCoursewareService coursewareService;

	@Resource
	private SysUserService sysUserService;

	/**
	 * @Description 查询试卷信息（笔试、实操）
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId
	 * @param session
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "examine", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult examine(Integer courseId, Integer coursewareId, Integer learningProgressId, HttpSession session) {
		if (null != courseId && 0 == courseId.intValue()) {
			courseId = null;
		}
		Integer userId = this.getUserId(session);
		// 先学习，再考试，查询课件的学习记录，如果没有，不可以参加考试
		if (this.examService.isExamAvailable(userId, courseId, coursewareId, learningProgressId)) {
			TraExamPaperVO examPaperVO = this.examService.getExamPaper(userId, courseId, coursewareId, learningProgressId);
			return new JsonResult(true).setData(examPaperVO);
		} else {
			return new JsonResult(false, "不可参加考试！");
		}
	}

	/**
	 * @Description 查询PPT试卷信息
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId
	 * @param session
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "pptExam", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult pptExam(Integer courseId, Integer coursewareId, Integer learningProgressId, HttpSession session) {
		if (null != courseId && 0 == courseId.intValue()) {
			courseId = null;
		}
		Integer userId = this.getUserId(session);
		// 先学习，再考试，查询课件的学习记录，如果没有，不可以参加考试
		if (this.examService.isExamAvailable(userId, courseId, coursewareId, learningProgressId)) {
			TraExamPaperVO examPaperVO = this.examService.getExamPaper(userId, courseId, coursewareId, learningProgressId);
			TraPptVO pptVO = this.examService.getPpt(examPaperVO.getTraExamDtlVOs().get(0).getPptId());
			return new JsonResult(true).putData("exampaper", examPaperVO).putData("ppt", pptVO);
		} else {
			return new JsonResult(false, "不可参加考试！");
		}
	}

	/**
	 * 保存考试信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "saveExam", method = { RequestMethod.POST })
	@ResponseBody
	public JsonResult saveExam(@RequestBody TraExamDtlVO[] examDtlVOs, HttpSession session) {
		Integer userId = this.getUserId(session);
		SysUserVO user = sysUserService.getSysUserById(userId);
		String isFormal = user.getIsFormal();

		List<TraExamDtlVO> traExamDtlVOs = this.examService.executeScoreExam(userId, Arrays.asList(examDtlVOs));
		Integer examPaperId = traExamDtlVOs.get(0).getPaperId();
		TraExamPaperVO examPaperVO = this.examPaperService.getExamPaperWithLearningProcessById(examPaperId);

		// 笔试考试时，需分题型处理 单选、多选、判断的考试结果
		String questionType = traExamDtlVOs.get(0).getQuestionType();
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
		if ("0".equals(questionType) || "1".equals(questionType) || "2".equals(questionType)) {
			result = this.getResult(traExamDtlVOs);
		}

		boolean isPassed = "3".equals(examPaperVO.getPaperStatus());
		JsonResult jsonResult = new JsonResult(true, "成绩保存成功").putData("examResult", result)
				.putData("score", examPaperVO.getTotalScore()).putData("passed", "3".equals(examPaperVO.getPaperStatus()));

		// 分新老员工处理返回结果
		this.handleJsonResult(isFormal, examPaperVO, isPassed, jsonResult);
		return jsonResult;
	}

	/**
	 * @Description 分新老员工处理返回结果
	 * @param isFormal
	 * @param examPaperVO
	 * @param isPassed
	 * @param jsonResult
	 * @return void
	 * @throws
	 */
	private void handleJsonResult(String isFormal, TraExamPaperVO examPaperVO, boolean isPassed, JsonResult jsonResult) {
		if ("0".equals(isFormal)) {
			Integer courseId = examPaperVO.getLearningProgressVO().getCourseId();
			Integer coursewareId = examPaperVO.getCoursewareId();
			Integer nextCoursewareId = this.coursewareService.nextCoursewareId(courseId, coursewareId);

			// 是否可以进入下一节课学习
			Map<String, Integer> nextCourse = null;
			if (nextCoursewareId != null && isPassed) {
				nextCourse = new HashMap<String, Integer>();
				nextCourse.put("courseId", courseId);
				nextCourse.put("coursewareId", nextCoursewareId);
			}
			jsonResult.putData("nextCourse", nextCourse);

			// 考试科目(期末)
			if (isPassed && this.coursewareService.isLastCourseware(courseId, coursewareId)) {
				jsonResult.putData("isFinalExamAvailable", true);
				jsonResult.putData("courseId", courseId);
			}
		} else {
			Map<String, Integer> nextCourse = null;
			nextCourse = new HashMap<String, Integer>();
			nextCourse.put("courseId", 0);
			nextCourse.put("coursewareId", 0);
			jsonResult.putData("nextCourse", nextCourse);
			jsonResult.putData("isFinalExamAvailable", false);
			jsonResult.putData("courseId", 0);
		}
	}

	/**
	 * @Title getResult
	 * @Description 分题型处理 单选、多选、判断
	 * @param resultExamDtlVOs
	 * @return
	 * @see com.yazuo.erp.train.service.ExamPaperService#getResult(java.util.List)
	 */
	private Map<String, Map<String, Object>> getResult(List<TraExamDtlVO> examDtlVOs) {
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
		Map<String, String> examType = new HashMap<String, String>();
		examType.put("0", "single");
		examType.put("1", "multiple");
		examType.put("2", "judgement");
		for (TraExamDtlVO examDtlVO : examDtlVOs) {
			String examTypeName = examType.get(examDtlVO.getQuestionType());
			Map<String, Object> examTypeMap = result.get(examTypeName);
			if (examTypeMap == null) {
				examTypeMap = new HashMap<String, Object>();
				result.put(examTypeName, examTypeMap);
			}
			if ("1".equals(examDtlVO.getIsCorrect())) {// 错误
				List<Integer> correct = (List<Integer>) examTypeMap.get("correct");
				if (correct == null) {
					correct = new ArrayList<Integer>();
					examTypeMap.put("correct", correct);
				}
				correct.add(examDtlVO.getId());

			} else {// 错误
				if (this.isAnswered(examDtlVO)) {
					List<Integer> wrong = (List<Integer>) examTypeMap.get("wrong");
					if (wrong == null) {
						wrong = new ArrayList<Integer>();
						examTypeMap.put("wrong", wrong);
					}
					wrong.add(examDtlVO.getId());
				} else {
					List<Integer> unanswered = (List<Integer>) examTypeMap.get("unanswered");
					if (unanswered == null) {
						unanswered = new ArrayList<Integer>();
						examTypeMap.put("unanswered", unanswered);
					}
					unanswered.add(examDtlVO.getId());
				}
			}
		}

		// 填空
		String[] keys = new String[] { "correct", "wrong", "unanswered" };
		for (String kind : examType.values()) {
			Map<String, Object> v = result.get(kind);
			if (v != null) {// 无相应的题型
				for (String key : keys) {
					if (v.get(key) == null) {
						v.put(key, Collections.EMPTY_LIST);
					}
				}
				List<Integer> correct = (List<Integer>) v.get("correct");
				if (correct != null) {
					Double score = correct.size() * 100.0 / examDtlVOs.size();
					v.put("score", String.format("%.1f", score));
				}
			}
		}
		return result;
	}

	private boolean isAnswered(TraExamDtlVO examDtlVO) {
		boolean isAnswered = false;
		for (TraExamOptionVO examOptionVO : examDtlVO.getExamOptionVOs()) {
			if ("1".equals(examOptionVO.getIsSelected())) {
				isAnswered = true;
				break;
			}
		}
		return isAnswered;
	}

	/**
	 * 生成期末考试卷
	 * 
	 * @param courseId
	 * @return
	 */
	@RequestMapping("finalExamine")
	@ResponseBody
	public JsonResult finalExamine(Integer courseId, Integer learningProgressId, HttpSession session) {
		Integer userId = this.getUserId(session);
		TraExamPaperVO examPaperVO = this.examService.getFinalExamPaper(userId, courseId);
		return new JsonResult(true, "考卷生成完成").setData(examPaperVO);
	}

	/**
	 * 计算期末考试成绩
	 * 
	 * @param examDtlVOs
	 * @return
	 */
	@RequestMapping("saveFinalExam")
	@ResponseBody
	public JsonResult saveFinalExam(@RequestBody TraExamDtlVO[] examDtlVOs, HttpSession session) {
		Integer userId = this.getUserId(session);
		List<TraExamDtlVO> resultExamDtlVOs = this.examService.executeScoreFinalExam(userId, Arrays.asList(examDtlVOs));
		Integer examPaperId = resultExamDtlVOs.get(0).getPaperId();
		TraExamPaperVO examPaperVO = this.examPaperService.getExamPaperWithLearningProcessById(examPaperId);
		Map<String, Map<String, Object>> result = this.getResult(resultExamDtlVOs);

		JsonResult jsonResult = new JsonResult(true, "成绩保存成功").putData("examResult", result)
				.putData("score", examPaperVO.getTotalScore()).putData("passed", "1".equals(examPaperVO.getPaperStatus()))
				.putData("courseId", examPaperVO.getLearningProgressVO().getCourseId());
		return jsonResult;
	}

	/**
	 * 保存录音
	 * 
	 * @param hours
	 * @param pptId
	 * @param pptfile
	 * @return
	 */
	@RequestMapping("saveAudio")
	@ResponseBody
	public JsonResult saveAudio(Integer examPaperId, Integer pptId, Float hours, MultipartFile pptfile, HttpServletRequest request) {
		Integer studentId = ((SysUserVO) request.getAttribute(Constant.SESSION_USER)).getId();
		String prefix = request.getSession().getServletContext().getRealPath(DeviceTokenUtil.getPropertiesValue("audioPath"));
		this.examService.saveAudio(studentId, examPaperId, pptId, prefix, pptfile, new BigDecimal(hours));
		return new JsonResult(true, "保存成功!").putData("examPaperId", examPaperId).putData("pptId", pptId).putData("hours", hours)
				.putData("filename", pptfile.getOriginalFilename());
	}

	/**
	 * 开始考试
	 * 
	 * @param hours
	 * @param pptId
	 * @param pptfile
	 * @return
	 */
	@RequestMapping("startExam")
	@ResponseBody
	public JsonResult startExam(Integer examPaperId, HttpSession session) {
		Integer studentId = this.getUserId(session);
		this.examService.executeStartExam(studentId, examPaperId);
		return new JsonResult(true, "保存成功!").putData("examPaperId", examPaperId);
	}

	/**
	 * 开始PPT考试
	 * 
	 * @param hours
	 * @param pptId
	 * @param pptfile
	 * @return
	 */
	@RequestMapping("startPptExam")
	@ResponseBody
	public JsonResult startPptExam(Integer examPaperId, HttpServletRequest request) {
		Integer studentId = ((SysUserVO) request.getAttribute(Constant.SESSION_USER)).getId();
		this.examService.executeStartExam(studentId, examPaperId);
		return new JsonResult(true, "保存成功!").putData("examPaperId", examPaperId);
	}

	private Integer getUserId(HttpSession session) {
		SysUserVO user = (SysUserVO) session.getAttribute(Constant.SESSION_USER);
		return user.getId();
	}
}
