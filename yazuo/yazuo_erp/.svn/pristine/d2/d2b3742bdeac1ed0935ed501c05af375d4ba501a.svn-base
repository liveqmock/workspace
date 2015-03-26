package com.yazuo.erp.train.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.StudentService;
import com.yazuo.erp.train.service.TraCoursewareService;
import com.yazuo.erp.train.vo.CoursewareProgressVO;
import com.yazuo.erp.train.vo.TraCourseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 学生控制器
 */
@Controller
@RequestMapping("/train/student")
@SessionAttributes(Constant.SESSION_USER)
public class StudentController {

	@Resource
	private TraCoursewareService traCoursewareService;

	@Resource
	private StudentService studentService;

	/**
	 * 得到所有课程信息
	 * 
	 * @return
	 */
	@RequestMapping("getAllCoursewares")
	@ResponseBody
	public JsonResult getAllCoursewares(Integer isOldStaff, HttpSession session) {
		JsonResult jsonResult = null;
		if (null == isOldStaff || 0 == isOldStaff.intValue()) {// 新学员
			jsonResult = this.getAllCoursewaresOfNewStaff(session);
		} else if (1 == isOldStaff.intValue()) {// 老学员
			jsonResult = this.getAllCoursewaresOfOldStaff(session);
		}
		return jsonResult;
	}

	/**
	 * @Description 老学员课件列表
	 * @param session
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	private JsonResult getAllCoursewaresOfOldStaff(HttpSession session) {
		Integer userId = this.getUserId(session);
		List<CoursewareProgressVO> list = this.traCoursewareService.getCoursewareProgressOfOldStaff(userId);
		List<CoursewareProgressVO> historyList = this.traCoursewareService.getHistoryCoursewareProgressOfOldStaff(userId);

		return new JsonResult(true).putData("required", null).putData("optional", null).putData("course", null)
				.putData("teachername", "").putData("finalExamAvail", null).putData("learningCoursewareList", list)
				.putData("historyCoursewareList", historyList);
	}

	/**
	 * @Description 新学员课件列表
	 * @param session
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	private JsonResult getAllCoursewaresOfNewStaff(HttpSession session) {
		Integer userId = this.getUserId(session);
		TraCourseVO courseVO = this.studentService.getCourseByStudentId(userId);
		if (courseVO == null) {
			return new JsonResult(true, "还没分配课程").putData("required", Collections.emptyList())
					.putData("optional", Collections.emptyList()).putData("course", null).putData("teachername", "")
					.putData("finalExamAvail", false);
		}
		List<CoursewareProgressVO> list = this.traCoursewareService.getCoursewareProgresses(userId);

		// 按客户端要求，区分"必选"与"可选"，并进行排序
		Collections.sort(list, new Comparator<CoursewareProgressVO>() {
			@Override
			public int compare(CoursewareProgressVO o1, CoursewareProgressVO o2) {
				return o1.getSort() - o2.getSort();
			}
		});

		List<CoursewareProgressVO> required = new ArrayList<CoursewareProgressVO>();

		List<CoursewareProgressVO> optional = new ArrayList<CoursewareProgressVO>();
		for (CoursewareProgressVO vo : list) {
			if (vo.isRequired()) {
				required.add(vo);
			} else {
				if (!(vo.isStudied() || vo.isExamined() || vo.isPassed())) {
					vo.setIsNew(true);
				}
				optional.add(vo);
			}
		}

		boolean finalExamAvail = true;
		for (CoursewareProgressVO coursewareProgressVO : required) {
			if (!coursewareProgressVO.isPassed()) {
				finalExamAvail = false;
				break;
			}
		}
		return new JsonResult(true).putData("required", required).putData("optional", optional).putData("course", courseVO)
				.putData("teachername", this.studentService.getTeacherName(userId)).putData("finalExamAvail", finalExamAvail);
	}

	@RequestMapping("startStudy")
	@ResponseBody
	public JsonResult startStudy(Integer courseId, Integer coursewareId, Integer learningProgressId, Integer isOldStaff,
			HttpSession session) {
		if (null != courseId && 0 == courseId.intValue()) {
			courseId = null;
		}
		Integer userId = this.getUserId(session);
		this.studentService.executeStartStudy(userId, courseId, coursewareId, learningProgressId, isOldStaff);
		return new JsonResult(true, "记录完成");
	}

	@RequestMapping("completeStudy")
	@ResponseBody
	public JsonResult completeStudy(Integer courseId, Integer coursewareId, Integer learningProgressId, Integer isOldStaff,
			HttpSession session) {
		if (null != courseId && 0 == courseId.intValue()) {
			courseId = null;
		}
		Integer userId = this.getUserId(session);
		this.studentService.executeCompleteStudy(userId, courseId, coursewareId, learningProgressId, isOldStaff);
		return new JsonResult(true, "学习完成");
	}

	/**
	 * 学习课程
	 * 
	 * @param coursewareId
	 * @return
	 */
	@RequestMapping("study")
	@ResponseBody
	public JsonResult study(Integer courseId, Integer coursewareId, Integer learningProgressId, Integer isOldStaff,
			HttpSession session) {
		if (null != courseId && 0 == courseId.intValue()) {
			courseId = null;
		}
		Integer userId = this.getUserId(session);
		return this.getCoursewareWithHistories(userId, courseId, coursewareId, learningProgressId, isOldStaff);
	}

	@RequestMapping("studyNext")
	@ResponseBody
	public JsonResult studyNext(Integer courseId, Integer coursewareId, Integer learningProgressId, HttpSession session) {
		if (null != courseId && 0 == courseId.intValue()) {
			courseId = null;
		}
		Integer nextCoursewareId = this.traCoursewareService.nextCoursewareId(courseId, coursewareId);
		Integer userId = this.getUserId(session);
		if (nextCoursewareId == null) {
			return new JsonResult(false, "没有下一节课");
		}
		return this.getCoursewareWithHistories(userId, courseId, nextCoursewareId, learningProgressId, new Integer("0"));
	}

	/**
	 * 判断可否学习 不可学习抛出异常<br/>
	 * 其中包括 是否是第一次学习<br/>
	 * 得到课程信息<br/>
	 * 包含此课程的考试历史信息 视频地址<br/>
	 * 
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @param isOldStaff
	 * @return
	 */
	private JsonResult getCoursewareWithHistories(Integer studentId, Integer courseId, Integer coursewareId,
			Integer learningProgressId, Integer isOldStaff) {
		if (null != courseId && 0 == courseId.intValue()) {
			courseId = null;
		}
		CoursewareProgressVO coursewareProgress = this.traCoursewareService.getCoursewareWithHistories(studentId, courseId,
				coursewareId, learningProgressId, isOldStaff);
		return new JsonResult(true).setData(coursewareProgress);
	}

	private Integer getUserId(HttpSession session) {
		SysUserVO user = (SysUserVO) session.getAttribute(Constant.SESSION_USER);
		return user.getId();
	}
}
