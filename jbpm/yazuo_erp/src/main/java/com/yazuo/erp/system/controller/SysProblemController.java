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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysProblemCommentsService;
import com.yazuo.erp.system.service.SysProblemService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysProblemCommentsVO;
import com.yazuo.erp.system.vo.SysProblemVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description
 * @author erp team
 * @date 
 */

/**
 * 问题 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("sysProblem")
@SessionAttributes(Constant.SESSION_USER)
public class SysProblemController {

	private static final Log LOG = LogFactory.getLog(SysProblemController.class);
	@Resource private SysProblemService sysProblemService;
	@Resource private SysDictionaryService sysDictionaryService;
	@Resource private SysUserService sysUserService;

	/**
	 * 添加问题
	 */
	@RequestMapping(value = "loadSysProblem", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	@SuppressWarnings("unchecked")
	public JsonResult loadSysProblem(@RequestBody SysProblemVO sysProblem, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		final Integer userId = user.getId();
		final List<Map<String, Object>> productNameList = sysDictionaryService.querySysDictionaryByTypeStd("00000120");
		final List<Map<String, Object>> sourceList = sysDictionaryService.querySysDictionaryByTypeStd("00000121");
		final List<Map<String, Object>>  newSourceList = (List<Map<String, Object>>)CollectionUtils.select(sourceList, new Predicate() {
			public boolean evaluate(Object object) {
//				问题来源	1	前端
//				问题来源	2	后端
//				问题来源	3	运营
				Map<String, Object> map = (Map<String, Object>)object;
				if(map.get("value").toString().equals("1")) return sysUserService.checkIfExistsUserResource(userId, "front_end_service");//前端服务
				if(map.get("value").toString().equals("2")) return sysUserService.checkIfExistsUserResource(userId, "end_service");//后端服务
				if(map.get("value").toString().equals("3")) return sysUserService.checkIfExistsUserResource(userId, "product_operation");//产品运营
				return false;
			}
		});

		Integer id = sysProblem.getId();
		final SysProblemVO sysProblemDb = id==null? new SysProblemVO(): this.sysProblemService.getSysProblemById(id);
		//问题类型
		
		final List<Map<String, Object>> problemType = this.sysDictionaryService.getDropDownList("00000040", sysProblemDb.getProblemType());
		return new JsonResult(new HashMap<String, List<Map<String, Object>>>() {
			{
				put("productName", productNameList);
				put("source", newSourceList);
				put("problemType", problemType);
			}
		}, true);
	}
	/**
	 * 添加问题
	 */
	@RequestMapping(value = "saveSysProblem", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveSysProblem(@RequestBody SysProblemVO sysProblem, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysProblemService.saveSysProblem(sysProblem, user);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}

	/**
	 * 修改问题
	 */
	@RequestMapping(value = "updateSysProblem", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateSysProblem(@RequestBody SysProblemVO sysProblem, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysProblemService.updateSysProblem(sysProblem, user);
		return new JsonResult(count > 0).setMessage(count > 0 ? "修改成功!" : "修改失败!");
	}
	@Resource private SysProblemCommentsService sysProblemCommentsService;
	/**
	 * 添加评论
	 */
	@RequestMapping(value = "addComments", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult addComments(@RequestBody SysProblemCommentsVO sysProblemComments, @ModelAttribute(Constant.SESSION_USER) SysUserVO sessionUser) {
		
		Assert.assertNotNull("问题ID不能为空！", sysProblemComments.getProblemId());
		Assert.assertNotNull("评论不能为空！", sysProblemComments.getDescription());
		SysProblemCommentsVO problemComments = sysProblemCommentsService.saveSysProblemComments(sysProblemComments, sessionUser);
		return new JsonResult(problemComments, true);
	}
 
	/**
	 * 列表显示 问题
	 */
	@RequestMapping(value = "listSysProblems", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult listSysProblems(@RequestBody Map<String, Object> paramMap, 
			@ModelAttribute(Constant.SESSION_USER) SysUserVO sessionUser) {
		Page<Map<String, Object>> listSysProblems = sysProblemService.listSysProblems(paramMap, sessionUser);
		return new JsonResult(true).setMessage("查询成功").putData("totalSize", listSysProblems.getTotal())
				.putData("rows", listSysProblems);
	}

	/**
	 * 删除 问题
	 */
	@RequestMapping(value = "deleteSysProblem/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult deleteSysProblem(@PathVariable int id) {
		int count = sysProblemService.deleteSysProblemById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
