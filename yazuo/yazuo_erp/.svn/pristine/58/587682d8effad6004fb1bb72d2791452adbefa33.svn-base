/**
 * @Description 老员工管理控制类
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
import com.yazuo.erp.train.service.OldStaffManagementService;
import com.yazuo.erp.train.service.StudentManagementService;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;

/**
 * @Description 老员工管理控制类
 * @author gaoshan
 * @date 2014-10-09 下午6:45:53
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("oldStaffManagement")
public class OldStaffManagementController {
 
	private static final Log log = LogFactory.getLog(OldStaffManagementController.class);

	@Resource
	private OldStaffManagementService oldStaffManagementService;
    
	/**
	 * @Description 根据课件名称模糊查询有效的课件
	 * @param coursewareName
	 *            课件名称，如果为空，则''
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryCoursewareList", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryCoursewareList(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Page<Map<String, Object>> list = this.oldStaffManagementService.queryCoursewareList(params);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());
		dataMap.put(Constant.ROWS, list.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * @Description 发起学习-查询，根据条件（1-按照部门，2-按照职位，3-按照员工），查询部门列表，职位列表，员工列表
	 * @return
	 * @param queryType
	 *            1-按照部门，2-按照职位，3-按照员工，必须上送
	 * @param pageNumber
	 *            按照员工查询时必输上送
	 * @param pageSize
	 *            按照员工查询时必输上送
	 * @throws
	 */
	@RequestMapping(value = "queryInfo", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryInfo(@RequestBody Map<String, Object> params, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Map<String, Object> data = this.oldStaffManagementService.queryInfo(params);
		return new JsonResult().setFlag(true).setData(data);
	}

	/**
	 * @Description 发起学习，根据条件发起学习，如果排除已学习过的学员，则学员历史有学习过该课件，就不发起该课件的学习
	 * @param conditionType
	 *            1-按照部门，2-按照职位，3-按照员工
	 * @param idList
	 *            id列表（组ID/职位ID/员工ID），具体类型根据conditionType判定
	 * @param flag
	 *            是否排除已学习过的学员标志（0-不排除，1-排除）
	 * @param coursewareId
	 *            课件ID
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "executeTermBegin", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult termBegin(@RequestBody Map<String, Object> params, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		params.put("user", user);
		int count = this.oldStaffManagementService.executeTermBegin(params);
		return new JsonResult().setFlag(count > 0).setMessage(count > 0 ? "发起成功" : "发起失败");
	}

	/**
	 * @Description 学员管理-列表，根据姓名、状态查询学员信息，学习状态显示最近的一次学习进度（除了异常结束的）
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryOldStaffList", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryOldStaffList(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Map<String, Object> map = this.oldStaffManagementService.queryOldStaffList(params);
		return new JsonResult().setFlag(true).setData(map);
	}

	/**
	 * @Description 移除学员
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "executeRemove", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult executeRemove(@RequestBody Map<String, Object> params, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		params.put("user", user);
		int count = this.oldStaffManagementService.executeRemove(params);
		return new JsonResult().setFlag(count > 0).setMessage(count > 0 ? "移除成功" : "移除失败");
	}

	/**
	 * @Description 延时学习
	 * @param learningProgressId
	 *            学习进度ID
	 * @param endDate
	 *            截止日期
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "executeDelay", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult executeDelay(@RequestBody Map<String, Object> params, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		params.put("user", user);
		int count = this.oldStaffManagementService.executeDelay(params);
		return new JsonResult().setFlag(count > 0).setMessage(count > 0 ? "延时成功" : "延时失败");
	}
	
	/**
	 * @Description 根据领导ID查询待办事项
	 * @param coursewareName
	 *            课件名称，如果为空，则''
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryToDoListByLeaderId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryToDoListByLeaderId(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Map<String, Object> dataMap = this.oldStaffManagementService.queryToDoListByLeaderId(params);
		return new JsonResult().setFlag(true).setData(dataMap);
	}
	
	/**
	 * @Description 根据领导ID查询待办事项数量
	 * @param coursewareName
	 *            课件名称，如果为空，则''
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryToDoListCountByLeaderId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryToDoListCountByLeaderId(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Map<String, Object> dataMap = this.oldStaffManagementService.queryToDoListCountByLeaderId(params);
		return new JsonResult().setFlag(true).setData(dataMap);
	}

}
