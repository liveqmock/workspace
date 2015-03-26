/**
 * @Description 学生管理控制类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 		gaoshan			2014-06-04	创建文档
 * 
 */
package com.yazuo.erp.train.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.StudentManagementService;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;

/**
 * @Description 学生管理控制类
 * @author gaoshan
 * @date 2014-6-3 下午6:45:53
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("studentManagement")
public class StudentManagementController {

	private static final Log log = LogFactory.getLog(StudentManagementController.class);

	@Resource
	private StudentManagementService studentManagementService;

	/**
	 * @Description 查询有效的职位信息
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryPosition", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryPosition(@RequestBody Map<String, Object> params) {
		List<Map<String, Object>> list = this.studentManagementService.queryPosition(params);
		return new JsonResult().setFlag(true).setData(list);
	}

	/**
	 * @Description 查询有效的课程信息
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryCourse", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryCourse() {
		List<Map<String, Object>> list = this.studentManagementService.queryCourse();
		return new JsonResult().setFlag(true).setData(list);
	}

	/**
	 * @Description 查询未分配老师的学生列表
	 * @param userName
	 * @param tel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryStudentListToBeAllocated", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryStudentListToBeAllocated(@RequestBody Map<String, Object> params) {
		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Page<Map<String, Object>> list = this.studentManagementService.queryStudentListToBeAllocated(params);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());
		dataMap.put(Constant.ROWS, list.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * @Description 根据老师ID查询待办事项的数量
	 * @param teacherId
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryToDoListCountByTeacherId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryToDoListCountByTeacherId(@RequestBody Map<String, Object> params) {
		Integer teacherId = (Integer) params.get("teacherId");

		PageHelper.startPage(1, 10, true);// 分页
		Page<Map<String, Object>> list = this.studentManagementService.queryToDoListByTeacherId(teacherId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * @Description 根据老师ID查询待办事项列表
	 * @param teacherId
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryToDoListByTeacherId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryToDoListByTeacherId(@RequestBody Map<String, Object> params) {
		Integer teacherId = (Integer) params.get("teacherId");
		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Page<Map<String, Object>> list = this.studentManagementService.queryToDoListByTeacherId(teacherId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());
		dataMap.put(Constant.ROWS, list.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * @Description 添加老师-学生关系
	 * @param teacherId
	 * @param courseId
	 * @param studentIdList
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "saveTraTeacherStudent", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult saveTraTeacherStudent(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		params.put("user", user);
		int sum = this.studentManagementService.saveTraTeacherStudent(params);

		JsonResult json = new JsonResult();
		json.setFlag(sum > 0);
		json.setMessage(sum > 0 ? "添加成功!" : "添加失败!");
		return json;
	}

	/**
	 * @Description 学员管理，条件查询学生信息列表
	 * @param teacherId
	 * @param positionId
	 *            0-全部
	 * @param courseStatus
	 *            0-全部
	 * @param tel
	 *            ''-全部
	 * @param userName
	 *            ''-全部
	 * @param pageSize
	 * @param pageNumber
	 * @param hasTeacherFlag
	 *            -1-全部，0-未分配老师，1-已分配老师，
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryStudentList", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryStudentList(@RequestBody Map<String, Object> params) {
		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Page<Map<String, Object>> list = this.studentManagementService.queryStudentList(params);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());
		dataMap.put(Constant.ROWS, list.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * @Description 根据学生ID查看流水
	 * @param studentId
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryTraStudentRecordByStudentId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryTraStudentRecordByStudentId(@RequestBody Map<String, Object> params) {
		Integer studentId = (Integer) params.get("studentId");
		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Page<Map<String, Object>> list = this.studentManagementService.queryTraStudentRecordByStudentId(studentId);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());
		dataMap.put(Constant.ROWS, list.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * @Description 解除师生关系
	 * @param traTeacherStudentVO
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "deleteTraTeacherStudent", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult deleteTraTeacherStudent(@RequestBody TraTeacherStudentVO traTeacherStudentVO) {
		int sum = this.studentManagementService.deleteTraTeacherStudent(traTeacherStudentVO);

		JsonResult json = new JsonResult();
		json.setFlag(sum > 0);
		json.setMessage(sum > 0 ? "解除成功!" : "解除失败!");
		return json;
	}

	/**
	 * @Description 批量解除师生关系
	 * @param traTeacherStudentVO
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "batchDeleteTraTeacherStudent", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult batchDeleteTraTeacherStudent(@RequestBody Map<String, Object> params) {
		List<TraTeacherStudentVO> list = (List<TraTeacherStudentVO>) params.get("list");
		int sum = this.studentManagementService.batchDeleteTraTeacherStudent(list);

		JsonResult json = new JsonResult();
		json.setFlag(sum > 0);
		json.setMessage(sum > 0 ? "批量解除成功!" : "批量解除失败!");
		return json;
	}

	/**
	 * @Description 根据试卷ID查询PPT、录音信息和评语信息
	 * @param paperId
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryPptExamInfo", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryPptExamInfo(@RequestBody Map<String, Object> params) {
		Integer examPaperId = (Integer) params.get("paperId");
		Map<String, Object> map = this.studentManagementService.queryPptExamInfo(examPaperId);

		return new JsonResult().setFlag(true).setData(map);
	}

	/**
	 * @Description 查询老师列表
	 * @param params
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryTeacherList", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryTeacherList(@RequestBody Map<String, Object> params) {
		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Page<Map<String, Object>> list = this.studentManagementService.queryTeacherList(params);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());
		dataMap.put(Constant.ROWS, list.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * @Description 根据试卷ID进行PPT录音评分
	 * @param paperId
	 *            Integer
	 * @param mark
	 *            String
	 * @param comment
	 *            String
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "updatePptPaper", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult updatePptPaper(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		params.put("user", user);
		int sum = this.studentManagementService.updatePptPaper(params);

		JsonResult json = new JsonResult();
		json.setFlag(sum > 0);
		json.setMessage(sum > 0 ? "评分成功!" : "评分失败!");
		return json;
	}

	/**
	 * @Description 根据试卷ID进行真人互动评分
	 * @param paperId
	 *            Integer
	 * @param mark
	 *            String
	 * @param comment
	 *            String
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "updateMark", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult updateMark(@RequestBody Map<String, Object> params, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		params.put("user", user);
		int sum = this.studentManagementService.updateMark(params);

		JsonResult json = new JsonResult();
		json.setFlag(sum > 0);
		json.setMessage(sum > 0 ? "真人互动评分成功!" : "真人互动评分失败!");
		return json;
	}

	/**
	 * @Description 课程延时
	 * @param endDate
	 *            YYYY-mm-dd
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "updateCourseEndTime", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult updateCourseEndTime(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		params.put("user", user);
		int sum = this.studentManagementService.updateCourseEndTime(params);

		JsonResult json = new JsonResult();
		json.setFlag(sum > 0);
		json.setMessage(sum > 0 ? "延时成功!" : "延时失败!");
		return json;
	}

	/**
	 * @Description 查看评语
	 * @param paperId
	 *            Integer
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryCommentOfPaper", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryCommentOfPaper(@RequestBody Map<String, Object> params) {
		Map<String, Object> paper = this.studentManagementService.queryCommentOfPaper(params);
		return new JsonResult().setFlag(true).setData(paper);
	}

	/**
	 * @Description 修正生产数据，更新学生流水表的学习进度，针对大于学习进度创建时间的流水，进度ID修改为最近的学习进度（暂时使用，不通用）
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "updateRecord", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult updateRecord(@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		int flag = this.studentManagementService.updateRecord(params);
		return new JsonResult().setFlag(true).setMessage("修改成功"); 
	}
	
	/**
	 * @Description 修正生产数据，修正学生进度表，将定时器跑错的数据（已毕业的改为超时）修正正常（已经毕业）
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "updateLearningProcess", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult updateLearningProcess(@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		int flag = this.studentManagementService.updateLearningProcess(params);
		return new JsonResult().setFlag(true).setMessage("修改成功"); 
	}

}
