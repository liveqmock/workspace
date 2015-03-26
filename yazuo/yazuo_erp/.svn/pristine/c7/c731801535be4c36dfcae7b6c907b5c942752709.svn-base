/**
 * @Description 老员工学习控制类
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
import com.yazuo.erp.train.service.OldStaffService;
import com.yazuo.erp.train.service.StudentManagementService;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;

/**
 * @Description 老员工学习控制类
 * @author gaoshan
 * @date 2014-10-09 下午6:45:53
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("oldStaff")
public class OldStaffController {

	private static final Log log = LogFactory.getLog(OldStaffController.class);

	@Resource
	private OldStaffService oldStaffService;

	/**
	 * @Description 查询有效的职位信息
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryPosition", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryPosition(@RequestBody Map<String, Object> params, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		List<Map<String, Object>> list = this.oldStaffService.queryPosition(params);
		return new JsonResult().setFlag(true).setData(list);
	}
}
