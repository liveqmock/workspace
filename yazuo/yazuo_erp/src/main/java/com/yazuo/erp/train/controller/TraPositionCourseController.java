/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.train.controller;

import com.yazuo.erp.train.vo.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;

import com.yazuo.erp.train.service.TraPositionCourseService;
import com.yazuo.erp.system.controller.SysRoleController;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 职位-课程关系表 相关业务操作
 * @author 
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("traPositionCourse")
public class TraPositionCourseController {
	
	private static final Log LOG = LogFactory.getLog(TraPositionCourseController.class);
	@Resource
	private TraPositionCourseService traPositionCourseService;
	
	/**
	 * @Description 添加职位和课程的关系
	 * @param traPositionCourseList
	 * @param user
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "saveTraPositionCourse", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult saveTraPositionCourse(@RequestBody TraPositionCourseVO[] traPositionCourseList, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = traPositionCourseService.saveTraPositionCourse(traPositionCourseList, user);
		return new JsonResult(true).setMessage("添加成功");
	}
	
	/**
	 * 保存修改职位-课程关系表
	 */
	@RequestMapping(value = "saveTraPositionCourse", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveTraPositionCourse(@RequestBody TraPositionCourseVO traPositionCourse) {
		int count = traPositionCourseService.saveTraPositionCourse(traPositionCourse);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 列表显示 职位-课程关系表 
	 */
	@RequestMapping(value = "listTraPositionCourses/{pageNumber}/{pageSize}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listTraPositionCourses(@PathVariable int pageNumber, @PathVariable int pageSize) {
		Map<String, Object> paramerMap = new HashMap<String, Object>();
		paramerMap.put("pageNumber", pageNumber);
		paramerMap.put("pageSize", pageSize);
		//Page<Map<String, Object>> pageList = traPositionCourseService.getTraPositionCourses(paramerMap);
		//Map<String, Object> dataMap = new HashMap<String, Object>();
		//dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		//dataMap.put(Constant.ROWS, pageList.getResult());
//		return new JsonResult(true).setData(dataMap);
		return null;
	}
	
	/**
	 * 删除 职位-课程关系表 
	 */
	@RequestMapping(value = "deleteTraPositionCourse/{id}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteTraPositionCourse(@PathVariable int id) {
		int count = traPositionCourseService.deleteTraPositionCourseById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
