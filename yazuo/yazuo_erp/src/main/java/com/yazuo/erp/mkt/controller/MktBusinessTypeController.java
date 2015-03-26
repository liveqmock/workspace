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
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.controller;

import java.util.*;
import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;

/**
 * @author erp team
 * @date 
 */
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.yazuo.erp.mkt.service.MktBusinessTypeService;
import com.yazuo.erp.system.controller.SysRoleController;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 业态表 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("mktBusinessType")
public class MktBusinessTypeController {
	
	private static final Log LOG = LogFactory.getLog(MktBusinessTypeController.class);
	@Resource
	private MktBusinessTypeService mktBusinessTypeService;
	
	/**
	 * 保存修改业态表
	 */
	@RequestMapping(value = "saveMktBusinessType", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveMktBusinessType(@RequestBody MktBusinessTypeVO mktBusinessType, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		int count = mktBusinessTypeService.saveOrUpdateMktBusinessType(mktBusinessType, sessionUser);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 列表显示 业态表 
	 */
	@RequestMapping(value = "listMktBusinessTypes/{pageNumber}/{pageSize}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listMktBusinessTypes(@PathVariable int pageNumber, @PathVariable int pageSize) {
		Map<String, Object> paramerMap = new HashMap<String, Object>();
		paramerMap.put("pageNumber", pageNumber);
		paramerMap.put("pageSize", pageSize);
		//Page<Map<String, Object>> pageList = mktBusinessTypeService.getMktBusinessTypes(paramerMap);
		//Map<String, Object> dataMap = new HashMap<String, Object>();
		//dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		//dataMap.put(Constant.ROWS, pageList.getResult());
//		return new JsonResult(true).setData(dataMap);
		return null;
	}
	
	/**
	 * 删除 业态表 
	 */
	@RequestMapping(value = "deleteMktBusinessType/{id}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteMktBusinessType(@PathVariable int id) {
		int count = mktBusinessTypeService.deleteMktBusinessTypeById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
