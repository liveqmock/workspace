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
import java.util.List;
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
import com.yazuo.erp.system.vo.SysProductOperationVO;
/**
 * @author erp team
 * @date 
 */
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 产品运营 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("sysProductOperation")
public class SysProductOperationController {
	
	private static final Log LOG = LogFactory.getLog(SysProductOperationController.class);
	@Resource
	private SysProductOperationService sysProductOperationService;
	
	/**
	 * 保存修改产品运营
	 */
	@RequestMapping(value = "saveSysProductOperation", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveSysProductOperation(@RequestBody SysProductOperationVO sysProductOperation, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		int count = sysProductOperationService.saveOrUpdateSysProductOperation(sysProductOperation, sessionUser);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 点修改的时候查询当前的 产品运营 
	 */
	@RequestMapping(value = "getSysProductOperation", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult getSysProductOperation(@RequestBody SysProductOperationVO sysProductOperation) {
		SysProductOperationVO sysProductOperationById = sysProductOperationService.getSysProductOperationById(sysProductOperation.getId());
		return new JsonResult(true).setData(sysProductOperationById);
	}
	/**
	 * 列表显示 产品运营 
	 */
	@RequestMapping(value = "listSysProductOperations", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listSysProductOperations(@RequestBody SysProductOperationVO sysProductOperation) {
		if(sysProductOperation.getPageNumber()!=null){
			//1. ERP项目, 产品运营列表展示； 输入标题点击查询按钮查询 
			PageHelper.startPage(sysProductOperation.getPageNumber(), sysProductOperation.getPageSize(), true);
			List<SysProductOperationVO> list = sysProductOperationService.getSysProductOperations(sysProductOperation);
			Page<SysProductOperationVO> pageList = (Page<SysProductOperationVO>)list;
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put(Constant.TOTAL_SIZE, pageList ==null ? 0: pageList.getTotal());
			dataMap.put(Constant.ROWS, pageList ==null ? null: pageList.getResult());
			return new JsonResult(true).setData(dataMap);
		}else{
			//2. 登录系统ERP或CRP等产品，如果返回记录属性data长度大于0显示弹窗和内容 ， 具体参照原型设计 
			sysProductOperation.setFilterByCurrentDate("notNull"); //系统登录的时候按时间过滤
			List<SysProductOperationVO> list = sysProductOperationService.getSysProductOperations(sysProductOperation);
			return new JsonResult(true).setData(list);
		}
	}
	
	/**
	 * 删除 产品运营 
	 */
	@RequestMapping(value = "deleteSysProductOperation/{id}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteSysProductOperation(@PathVariable int id) {
		int count = sysProductOperationService.deleteSysProductOperationById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
