package com.yazuo.erp.train.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.TraCourseService;
import com.yazuo.erp.train.vo.TraCourseVO;

@Controller
@RequestMapping("course")
@SessionAttributes(Constant.SESSION_USER)
public class CourseController {

	@Resource
	private TraCourseService courseService;

	private static final Log LOG = LogFactory.getLog(CourseController.class);

	/**
	 * 
	 * @Description 添加课程
	 * @param courseVO
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "saveCourse", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult saveCourse(TraCourseVO courseVO, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		courseService.saveCourse(courseVO, user);
		return new JsonResult(true).setMessage("添加成功");
	}

	/**
	 * 
	 * @Description 修改课程
	 * @param courseVO
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "updateCourse", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult updateCourse(TraCourseVO courseVO, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		courseService.updateCourse(courseVO, user);
		return new JsonResult(true).setMessage("修改成功");
	}

	/**
	 * 
	 * @Description 删除课程(只支持单条删除，若需批量删除，需修改service方法)
	 * @param paramMap
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "deleteCourse", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult deleteCourse(@RequestBody Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		List<Integer> listIds = new ArrayList<Integer>();
		List<Object> idString = (List<Object>) paramMap.get("idString");
		for (Object obj : idString) {
			listIds.add(Integer.parseInt(obj.toString()));
		}
		courseService.deleteCourse(listIds, user);
		return new JsonResult(true).setMessage("删除成功");
	}

	/**
	 * 
	 * @Description 查询课程基本信息
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "getCourseInfo", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getCourseInfo(@RequestParam(value = "pageNumber") int pageNumber,
			@RequestParam(value = "pageSize") int pageSize) {
		Page<Map<String, Object>> courseInfoList = courseService.getCourseInfo(pageNumber, pageSize);
		return new JsonResult(true).setMessage("查询成功").putData("rows", courseInfoList)
				.putData("totalSize", courseInfoList.getTotal());
	}

	/**
	 * 
	 * @Description 查询课程详细信息
	 * @param courseId
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "getCourseDetail", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getCourseDetail(int courseId) {
		Map<String, Object> resultMap = courseService.getCourseDetail(courseId);
		return new JsonResult(true).setMessage("查询成功")
				.putData("requiredCourseware", resultMap.get("requiredCoursewaresInfoList"))
				.putData("noRequiredCourseware", resultMap.get("noRequiredCoursewaresInfolist"))
				.putData("courseLearningFlag", resultMap.get("courseLearningFlag"));

	}

	@RequestMapping(value = "getAllCourses", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getAllCourses() {
		return new JsonResult(true).setMessage("success message or failure message").putData("hello", "中文测试")
				.putData("table", new ArrayList()).putData("now", new Date()).putData("calendar", Calendar.getInstance())
				.putData("timestamp", new Timestamp(System.currentTimeMillis()));
	}

	@RequestMapping(value = "getAllCoursesLearningInfo", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getAllCoursesLearningInfo() {
		List<Map<String, Object>> coursesLearningInfoList = courseService.getAllCoursesLearningInfo();
		return new JsonResult(true).setMessage("查询成功").putData("rows", coursesLearningInfoList)
				.putData("totalSize", coursesLearningInfoList.size());
	}
}
