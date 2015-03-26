/**
 * @Description 组员管理控制类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 		gaoshan		2014-10-09	创建文档
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
import com.yazuo.erp.train.service.TeamMemberService;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;

/**
 * @Description 组员管理控制类
 * @author gaoshan
 * @date 2014-10-09 下午6:45:53
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("teamMember")
public class TeamMemberController {

	private static final Log log = LogFactory.getLog(TeamMemberController.class);

	@Resource
	private TeamMemberService teamMemberService;

	@Resource
	private StudentManagementService studentManagementService;

	/**
	 * @Description 列表，领导查看下属的信息，并统计正在学习的课件数，根据姓名模糊查询
	 * @param baseUserId
	 *            领导ID
	 * @param subUserName
	 *            下属姓名
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryMemberList", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryMemberList(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Map<String,Object> dataMap = this.teamMemberService.queryMemberList(params);
		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * @Description 查看学习情况，根据用户ID查询基本信息，学习进度，包括异常结束的
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryLearningProcessList", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryLearningProcessList(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Map<String, Object> map = this.teamMemberService.queryLearningProcessList(params);
		return new JsonResult().setFlag(true).setData(map);
	}

	/**
	 * @Description 查看历史，根据用户ID查询学习流水
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryStudentRecordList", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryStudentRecordList(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Page<Map<String, Object>> list = this.teamMemberService.queryStudentRecordList(params);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());
		dataMap.put(Constant.ROWS, list.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
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
		int sum = this.teamMemberService.updatePptPaper(params);

		JsonResult json = new JsonResult();
		json.setFlag(sum > 0);
		json.setMessage(sum > 0 ? "评分成功!" : "评分失败!");
		return json;
	}
}
