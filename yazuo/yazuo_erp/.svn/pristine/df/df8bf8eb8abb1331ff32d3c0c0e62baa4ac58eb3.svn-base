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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JsonConfig;

import org.apache.commons.collections.CollectionUtils;
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
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.req.vo.ReqRequirementVO;
import com.yazuo.erp.system.TreeNode;
import com.yazuo.erp.system.service.SysGroupService;
import com.yazuo.erp.system.vo.SysGroupVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description 职位相关的业务操作
 * @author kyy
 * @date 2014-6-4 上午9:56:44
 */
@Controller
@RequestMapping("group")
@SessionAttributes(Constant.SESSION_USER)  
public class SysGroupController {

	private static final Log LOG = LogFactory.getLog(SysGroupController.class);
	@Resource
	private SysGroupService sysGroupService;

	/**
	 * 保存
	 */
	@RequestMapping(value = "saveSysGroup", method = { RequestMethod.POST,
			RequestMethod.GET })	
	@ResponseBody
	public JsonResult saveSysGroup(@RequestBody SysGroupVO sysGroupVO) {
		int count = this.sysGroupService.saveSysGroup(sysGroupVO);
		return new JsonResult(count > 0).setMessage(count > 0 ? "有记录!" : "无记录!");
	}
	
	/**
	 * 添加同级
	 */
	@RequestMapping(value = "addSameLevel", method = { RequestMethod.POST, RequestMethod.GET })		
	@ResponseBody
	public JsonResult addSameLevel(@RequestBody SysGroupVO sysGroupVO, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		sysGroupVO.setInsertBy(user.getInsertBy());
		sysGroupVO.setUpdateBy(user.getUpdateBy());
		int newId = this.sysGroupService.addSameLevel(sysGroupVO);
		return new JsonResult(newId > 0).setMessage(newId > 0 ? "保存成功!" : "保存失败!").setData(newId);
	}
	/**
	 * 添加子级
	 */
	@RequestMapping(value = "addNextLevel", method = { RequestMethod.POST, RequestMethod.GET })		
	@ResponseBody
	public JsonResult addNextLevel(@RequestBody SysGroupVO sysGroupVO, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		sysGroupVO.setInsertBy(user.getInsertBy());
		sysGroupVO.setUpdateBy(user.getUpdateBy());
		int newId = this.sysGroupService.addNextSameLevel(sysGroupVO);
		return new JsonResult(newId > 0).setMessage(newId > 0 ? "保存成功!" : "保存失败!").setData(newId);
	}
	/**
	 * 删除节点
	 */
	@RequestMapping(value = "deleteGroup/{id}", method = { RequestMethod.GET })		
	@ResponseBody
	public JsonResult deleteGroup(@PathVariable int id) {
		int count = this.sysGroupService.deleteSysGroup(id);
		String message = "删除成功！";
		if(count==-1){
			//特殊处理， 组存在人员不能删除
			message = "组存在人员不能删除!";
			new JsonResult(false).setMessage(message);
		}
		return new JsonResult(count > 0).setMessage(message);
	}
	/**
	 * 重命名
	 */
	@RequestMapping(value = "renameGroup", method = { RequestMethod.POST, RequestMethod.GET })		
	@ResponseBody
	public JsonResult renameGroup(@RequestBody SysGroupVO sysGroupVO) {
		int count = this.sysGroupService.updateSysGroup(sysGroupVO);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}

	/**
	 * 查找
	 */
	@RequestMapping(value = "getSysGroupsByParentId", produces = { "application/json;charset=UTF-8" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getSysGroupsByParentId(@RequestBody Map<String, Object> paramerMap) {
		Object objParentId = paramerMap.get("parentId");
		Integer parentId = objParentId==null? 0 :((Integer) objParentId).intValue();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("parentId", parentId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", parentId);
		resultMap.put("groupName", paramerMap.get("groupName").toString());
		List<SysGroupVO> list = this.sysGroupService.getSysGroups(inputMap);
		resultMap.put("children", list);
		return new JsonResult(true).setMessage(list.size() > 0 ? "有记录!" : "无记录!").setData(resultMap);
	}
	
	/**
	 * 查找XTree需要的数据
	 */
	@RequestMapping(value = "getSysGroupsForXTree",  method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getSysGroupsForXTree() {
		List<Map<String, Object>> list = this.sysGroupService.getSysGroupsForXTree();
		return new JsonResult(list.size()>0).setData(list);
	}

	@RequestMapping(value = "initGroup", method = { RequestMethod.POST,
			RequestMethod.GET })	
	@ResponseBody
	public JsonResult initGroup() {
		TreeNode node = sysGroupService.getAllGroupNode();
		JsonResult json = new JsonResult();
		json.setData(node.getChildrenList());
		json.setFlag(true);
		json.setMessage("");
		return json;
	}
	/**
	 * 公用接口：根据userId查找所有管辖的人
	 * @param paramerMap:  map(key='baseUserId', <userId>;  key="subUserName", "张三"))  
	 */
	@RequestMapping(value = "getSubordinateEmployees",method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getSubordinateEmployees(@RequestBody Map<String, Object> paramerMap) {
		//通过包含分页参数断定是否需要分页
		if(paramerMap.keySet().contains(Constant.PAGE_NUMBER) && paramerMap.keySet().contains(Constant.PAGE_SIZE)){
			Integer pageNum = (Integer)paramerMap.get(Constant.PAGE_NUMBER);
			Integer pageSize=(Integer)paramerMap.get(Constant.PAGE_SIZE);
			PageHelper.startPage(pageNum, pageSize, true);
			Page<SysUserVO> pageList = (Page<SysUserVO>)sysGroupService.getSubordinateEmployees(paramerMap);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
			dataMap.put(Constant.ROWS, pageList.getResult());
			return new JsonResult(true).setData(dataMap);
		}else{
			return new JsonResult(true).setData(sysGroupService.getSubordinateEmployees(paramerMap));
		}
	}
}
