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

package com.yazuo.erp.bes.controller;

import java.util.*;
import com.yazuo.erp.bes.vo.*;
import com.yazuo.erp.bes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.HashMap;
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

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;

import com.yazuo.erp.bes.service.BesMonthlyCheckService;
import com.yazuo.erp.system.controller.SysRoleController;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 月报检查 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("besMonthlyCheck")
public class BesMonthlyCheckController {

	private static final Log LOG = LogFactory.getLog(BesMonthlyCheckController.class);
	@Resource
	private BesMonthlyCheckService besMonthlyCheckService;

	/**
	 * 保存修改月报检查
	 */
	@RequestMapping(value = "saveBesMonthlyCheck", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveBesMonthlyCheck(@RequestBody BesMonthlyCheckVO besMonthlyCheck) {
		int count = besMonthlyCheckService.saveBesMonthlyCheck(besMonthlyCheck);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}

	/**
	 * 列表显示 月报检查
	 */
	@RequestMapping(value = "queryBesMonthlyCheckList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult queryBesMonthlyCheckList(@RequestBody Map<String, Object> params,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Page<Map<String, Object>> list = this.besMonthlyCheckService.queryBesMonthlyCheckList(params);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());
		dataMap.put(Constant.ROWS, list.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * 删除 月报检查
	 */
	@RequestMapping(value = "deleteBesMonthlyCheck/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult deleteBesMonthlyCheck(@PathVariable int id) {
		int count = besMonthlyCheckService.deleteBesMonthlyCheckById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
