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

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonDateValueProcessor;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.filter.SessionUserList;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.service.ResourceService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description 职位相关的业务操作
 * @author kyy
 * @date 2014-6-4 上午9:56:44
 */
@Controller
@RequestMapping("resource")
@SessionAttributes(Constant.SESSION_USER)  
public class ResourceController {

	private static final Log LOG = LogFactory.getLog(ResourceController.class);
	@Resource
	private ResourceService resourceService;
	
	/**
	 * 保存资源
	 */
	@RequestMapping(value = "saveSysResource", produces = { "application/json;charset=UTF-8" }, method = { RequestMethod.POST,
			RequestMethod.GET })	
	@ResponseBody
	public JsonResult saveSysResource(@RequestBody SysResourceVO sysResourceVO) {
		int count = this.resourceService.saveSysResource(sysResourceVO);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 列表显示
	 */
	@RequestMapping(value = "list/{parentId}/{pageNumber}/{pageSize}", method = { RequestMethod.GET })
	@ResponseBody 
	public JsonResult list(@PathVariable int parentId, @PathVariable int pageNumber, @PathVariable int pageSize, 
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		//TODO  test get session user.
		LOG.info(user);
		Map<String, Object> paramerMap = new HashMap<String, Object>();
		paramerMap.put("parentId", parentId);// 查找的级别
		paramerMap.put("pageNumber", pageNumber);
		paramerMap.put("pageSize", pageSize);
		Page<Map<String, Object>> pageList = resourceService.getSysResourcesMap(paramerMap);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		dataMap.put(Constant.ROWS, pageList.getResult());
		//配置显示导航 如： 资源管理 -> 培训考试
		LOG.info(resourceService.getPageNavigate(parentId));
		dataMap.put("pageNavigate", resourceService.getPageNavigate(parentId));
		return new JsonResult(true).setData(dataMap);
	}

    /** 
     * 更新 visable状态
     */  
    @RequestMapping(value = "triggerOnOrOffResource/{id}", method = RequestMethod.GET)  
    @ResponseBody  
    public JsonResult triggerOnOrOffResource(@PathVariable int id) {
    	
    	int count = resourceService.updateSysResourceVisableStatus(id);
    	return new JsonResult(count>0? true: false);
    }  
    
    /** 
     * 更新 资源
     */  
    @RequestMapping(value = "updateResource", method = RequestMethod.POST)  
    @ResponseBody  
    public JsonResult updateResource(@RequestBody SysResourceVO entity) {
    	int count = resourceService.updateSysResource(entity);
    	return new JsonResult(count>0? true: false);
    }  
    
    /** 
     * batch 更新 资源
     */  
    @RequestMapping(value = "updateResources", method = RequestMethod.POST)  
    @ResponseBody  
    public JsonResult updateResources(@RequestBody Map<String, Object> paramerMap) {
    	
    	int count = resourceService.updateSysResources(paramerMap);
    	return new JsonResult(count>0? true: false).setMessage(count> 0 ? "有记录!" : "无记录!");
    }  
    
	/**
	 * 按级别查找资源
	 * 
	 * @param positionVO
	 * @throws
	 */
	@RequestMapping(value = "getSysResourcesByLevel", produces = { "application/json;charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getSysResourcesByLevel(@RequestBody Map<String, Object> paramerMap) {
		String level = (String) paramerMap.get("level");
		Integer userId = (Integer) paramerMap.get("userId");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("level", level);
		inputMap.put("sortColumns", "sort");
		inputMap.put("userId", userId);
		List<SysResourceVO> list = this.resourceService.getSysResourcesByLevel(inputMap);
		return new JsonResult(true).setMessage(list.size() > 0 ? "有记录!" : "无记录!").putData("records", list);
	}

}
