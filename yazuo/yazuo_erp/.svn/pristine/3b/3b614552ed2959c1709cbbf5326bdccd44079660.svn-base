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

package com.yazuo.erp.fes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.ComplexSynMerchantVO;
import com.yazuo.erp.syn.vo.HealthDegree;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @author erp team
 * @date 
 */

/**
 *  我的主页 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("myHomePage")
public class FesMyHomePageController {
	
	private static final Log LOG = LogFactory.getLog(FesMyHomePageController.class);
	@Resource
	private SynMerchantService synMerchantService;		
	@Resource
	private SysUserMerchantService sysUserMerchantService;
	@Resource
	private SysToDoListService sysToDoListService;

	/**
	 *  查询待办事项
	 */
	@RequestMapping(value = "getComplexSysToDoLists", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult getComplexSysToDoLists(@RequestBody SysToDoListVO sysToDoList) {
//		LOG.info(JsonResult.getJsonString(sysToDoList));
		//如果为空,默认过滤0，未完成
		sysToDoList.setItemStatus(StringUtils.isEmpty(sysToDoList.getItemStatus())? "0": sysToDoList.getItemStatus());
		PageHelper.startPage(sysToDoList.getPageNumber(), sysToDoList.getPageSize(), true);
		Page<SysToDoListVO> pageList = (Page<SysToDoListVO>)sysToDoListService.getComplexSysToDoLists(sysToDoList);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		dataMap.put(Constant.ROWS, pageList.getResult());
		return new JsonResult(true).setData(dataMap);
	}	
	
	/**
	 *  查询前端我的主页查询出来的所有商户信息
	 *  @param inputMap 
		inputMap.put("userId", 1);
		inputMap.put("merchantName", "北京"); // like 模糊查询
		inputMap.put("merchantStatus", "2"); 
		inputMap.put("merchantStatusType", "0");
		
		inputMap.put(Constant.PAGE_NUMBER, 1);
		inputMap.put(Constant.PAGE_SIZE, 10);
	 */
	@RequestMapping(value = "getComplexSynMerchants", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getComplexSynMerchants(@RequestBody Map<String, Object> inputMap, HttpServletRequest request) {
		SysUserVO sessionUser = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		List<HealthDegree> stdHealthDegrees = ComplexSynMerchantVO.getStdHealthDegrees();
		Page<ComplexSynMerchantVO> pageList = (Page<ComplexSynMerchantVO>)synMerchantService.getComplexSynMerchants(inputMap, sessionUser);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("stdHealthDegree", stdHealthDegrees);//前台拼接 遗漏的健康度
		dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		dataMap.put(Constant.ROWS, pageList.getResult());
		return new JsonResult(true).setData(dataMap);
	}	
	
	/**
	 *  分配负责人
	 * @param 
		userId; //"用户ID";
		merchantId; //"商户ID";
		oldUserId//更改前的userId; 
	 */
	@RequestMapping(value = "assignPersionForMerchant", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult assignPersionForMerchant(@RequestBody SysUserMerchantVO sysUserMerchant, HttpServletRequest request) {
		SysUserVO sessionUser = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		//保存或者修改
		int count = sysUserMerchantService.saveSysUserMerchant(sysUserMerchant, sessionUser);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 解除商户负责人
	 */
	@Deprecated //目前没有这个需求
	@RequestMapping(value = "deleteSysUserMerchant/{id}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteSysUserMerchant(@PathVariable int id) {
		int count = sysUserMerchantService.deleteSysUserMerchantById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
