package com.yazuo.erp.train.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.TraCourseCoursewareService;

@Controller
@RequestMapping("course")
@SessionAttributes(Constant.SESSION_USER)
public class CourseCoursewareController {
	@Resource
	private TraCourseCoursewareService traCourseCoursewareService;

	private static final Log LOG = LogFactory.getLog(CourseCoursewareController.class);

	@RequestMapping(value = "saveCourseware", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult saveCourseware(@RequestBody Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) throws Exception {
		paramMap.put("user", user);
		traCourseCoursewareService.saveCourseware(paramMap);
		return new JsonResult(true).setMessage("添加成功");
	}

	@RequestMapping(value = "deleteCoursewareRel", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult deleteCoursewareRel(@RequestBody Map<String, Object> paramMap) {
		traCourseCoursewareService.deleteCoursewareRel(paramMap);
		return new JsonResult(true).setMessage("删除成功");
	}

	@RequestMapping(value = "updateCoursewareSort", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult updateCoursewareSort(@RequestBody Map<String, Object> paramMap) {
		traCourseCoursewareService.updateCoursewareSort(paramMap);
		return new JsonResult(true).setMessage("排序成功");
	}
}
