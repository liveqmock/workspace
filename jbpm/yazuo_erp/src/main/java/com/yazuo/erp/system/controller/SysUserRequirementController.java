/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.service.SysProductOperationService;
import com.yazuo.erp.system.service.SysUserRequirementService;
import com.yazuo.erp.system.vo.SysProductOperationVO;
import com.yazuo.erp.system.vo.SysUserRequirementVO;
/**
 * @author erp team
 * @date 
 */
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 用户需求(产品运营的用户需求) 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("sysUserRequirement")
public class SysUserRequirementController {
	
	private static final Log LOG = LogFactory.getLog(SysUserRequirementController.class);
	@Resource private SysUserRequirementService sysUserRequirementService;
	@Resource private SysProductOperationService sysProductOperationService;
	
	/**
	 * 保存修改用户需求(产品运营的用户需求)
	 */
	@RequestMapping(value = "saveSysUserRequirement", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveSysUserRequirement(@RequestBody SysUserRequirementVO sysUserRequirement, HttpSession session) {
		LOG.info(JsonResult.getJsonString(sysUserRequirement));
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		int count = sysUserRequirementService.saveSysUserRequirement(sysUserRequirement, sessionUser);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 列表显示 用户需求(产品运营的用户需求) 
	 */
	@RequestMapping(value = "listSysUserRequirements", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listSysUserRequirements(@RequestBody SysUserRequirementVO sysUserRequirement) {
		Integer productOperationId = sysUserRequirement.getProductOperationId();
		if(productOperationId!=null){
			sysUserRequirement.setProductOperationId(productOperationId);
//			SysProductOperationVO sysProductOperation = this.sysProductOperationService.getSysProductOperationById(productOperationId);
//			sysUserRequirement.setBeginTime(sysProductOperation.getBeginTime());
//			sysUserRequirement.setEndTime(sysProductOperation.getEndTime());
//			sysUserRequirement.setArrayPromotionPlatform(sysProductOperation.getPromotionPlatform());
		}
		PageHelper.startPage(sysUserRequirement.getPageNumber(), sysUserRequirement.getPageSize(), true);
		Page<SysUserRequirementVO> pageList = (Page<SysUserRequirementVO>)sysUserRequirementService.getSysUserRequirements(sysUserRequirement);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		dataMap.put(Constant.ROWS, pageList.getResult());
		return new JsonResult(true).setData(dataMap);
	}
	
	/**
	 * 删除 用户需求(产品运营的用户需求) 
	 */
	@RequestMapping(value = "deleteSysUserRequirement/{id}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteSysUserRequirement(@PathVariable int id) {
		int count = sysUserRequirementService.deleteSysUserRequirementById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
