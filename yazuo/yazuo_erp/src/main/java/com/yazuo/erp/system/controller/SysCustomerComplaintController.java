/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.controller;

import com.yazuo.erp.system.vo.*;

import java.io.IOException;
/**
 * @Description 客户投诉控制类
 * @author erp team
 * @date 
 */
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;

import com.yazuo.erp.system.service.SysCustomerComplaintService;

/**
 * 客户投诉 相关业务操作
 * 
 * @author
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("sysCustomerComplaint")
public class SysCustomerComplaintController {

	@Resource
	private SysCustomerComplaintService sysCustomerComplaintService;

	/**
	 * @Description 上传客户投诉附件
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping(value = "uploadFiles", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object uploadPlanFiles(@RequestParam(value = "myfiles", required = false) MultipartFile[] myfiles,
			HttpServletRequest request) throws IOException {
		return sysCustomerComplaintService.uploadFiles(myfiles, request);
	}

	/**
	 * 添加客户投诉
	 */
	@RequestMapping(value = "saveSysCustomerComplaint", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveSysCustomerComplaint(@RequestBody SysCustomerComplaintVO sysCustomerComplaint,
			HttpServletRequest request, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysCustomerComplaintService.saveSysCustomerComplaint(sysCustomerComplaint, user, request);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}

	/**
	 * 修改客户投诉
	 */
	@RequestMapping(value = "updateSysCustomerComplaint", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult updateSysCustomerComplaint(@RequestBody SysCustomerComplaintVO sysCustomerComplaint,
			HttpServletRequest request, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysCustomerComplaintService.updateSysCustomerComplaint(sysCustomerComplaint, user);
		if (count > 0) {
			Map<String, Object> sysCustomerComplaintMap = sysCustomerComplaintService.querySysCustomerComplaintById(sysCustomerComplaint.getId());
			return new JsonResult(true).setMessage("修改成功!").setData(sysCustomerComplaintMap);
		} else {
			return new JsonResult(false).setMessage("修改失败!");
		}

	}

	/**
	 * 根据ID查询客户投诉信息
	 */
	@RequestMapping(value = "getSysCustomerComplaintById", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getSysCustomerComplaintById(@RequestBody SysCustomerComplaintVO sysCustomerComplaint) {
		Map<String, Object> map = sysCustomerComplaintService.querySysCustomerComplaintById(sysCustomerComplaint.getId());
		return new JsonResult(true).setMessage("查询成功!").setData(map);
	}

	/**
	 * 列表显示 客户投诉
	 */
	@RequestMapping(value = "querySysCustomerComplaintList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult querySysCustomerComplaintList(@RequestBody Map<String, Object> paramMap) {
		Integer pageSize = (Integer) paramMap.get("pageSize");
		Integer pageNumber = (Integer) paramMap.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Page<Map<String, Object>> pageList = sysCustomerComplaintService.querySysCustomerComplaintList(paramMap);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		dataMap.put(Constant.ROWS, pageList.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * 删除客户投诉
	 */
	@RequestMapping(value = "deleteSysCustomerComplaint", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult deleteSysCustomerComplaint(@RequestBody SysCustomerComplaintVO sysCustomerComplaint,
			HttpServletRequest request, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysCustomerComplaintService.deleteSysCustomerComplaintById(sysCustomerComplaint);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
