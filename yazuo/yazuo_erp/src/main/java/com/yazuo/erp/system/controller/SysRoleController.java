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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.TreeNode;
import com.yazuo.erp.system.service.SysRoleService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 角色相关业务操作
 * @author kyy
 * @date 2014-6-5 上午9:42:16
 */
@Controller
@RequestMapping("role")
public class SysRoleController {
	
	private static final Log LOG = LogFactory.getLog(SysRoleController.class);
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 保存修改角色
	 * 
	 * @param positionVO
	 * @throws
	 */
	@RequestMapping(value = "saveRole", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object saveRole(SysRoleVO sysRole, @RequestParam(value="permission[]") String [] resourceId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUserVO user = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		Map<String, Object> map = null;
		if (sysRole.getId() != null) { // 修改
			String name = sysRole.getRoleName();
			sysRole = sysRoleService.getById(sysRole.getId());
			sysRole.setRoleName(name);
			sysRole.setUpdateBy(user.getId());
			sysRole.setUpdateTime(new Date());
			sysRole.setIsEnable(Constant.Enable);
			map = sysRoleService.updateRole(sysRole, resourceId);
			//需要从新修改session中的用户权限
			List<SysResourceVO> listPrivilege = sysUserService.getAllUserResourceByPrivilege(user.getId());
			user.setListPrivilege(listPrivilege);
			session.setAttribute(Constant.SESSION_USER, user);
			
			LOG.debug("修改成功!");
		} else { // 添加
			sysRole.setInsertBy(user.getId());
			sysRole.setInsertTime(new Date());
			sysRole.setUpdateBy(user.getId());
			sysRole.setUpdateTime(new Date());
			sysRole.setIsEnable(Constant.Enable);
			map = sysRoleService.saveRole(sysRole, resourceId);
			LOG.debug("添加成功!");
		}
		return map;
	}

	 @RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	 @ResponseBody
	 public Object list(@RequestParam Map<String, String> paramMap) {
		 List<Map<String, String>> listMap = sysRoleService.getAllPageByOder(paramMap);
		 // 总数量
		 long totalCount = sysRoleService.getTotalCount();
		 // 返回页面的分页信息
		 Map<String, Object> pageMap = new HashMap<String, Object>();
		
		 Map<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("totalSize", totalCount);
		 dataMap.put("rows", listMap);
		 
		 pageMap.put("data", dataMap);
		 pageMap.put("flag", 1);
		 pageMap.put("message", "");
		 //格式化json
		 return pageMap;
	}
	 
	 @RequestMapping(value = "initRole", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	 @ResponseBody
	 public Object initRole() {
		 List<Map<String, String>> listMap = sysRoleService.getAllRole();
		 Map<String, Object> pageMap = new HashMap<String, Object>();
		 pageMap.put("data", listMap);
		 pageMap.put("flag", 1);
		 pageMap.put("message", "");
		 //格式化json
		 return pageMap;
	}
	

	@RequestMapping(value = "edit", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object edit(Integer id) {
		List<String> list = new ArrayList<String>();
		// 角色与资源关联表
		List<Map<String, String>> resList = sysRoleService.getCheckedSysResource(id);
		for (Map<String, String> m : resList) {
			String res = String.valueOf(m.get("resource_id"));
			if (!list.contains(res)) {
				list.add(res);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("flag", 1);
		map.put("message", "");
		return map;
	}
	
	 @RequestMapping(value = "initTree", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	 @ResponseBody
	 //资源树形
	 public Object initTree() {
		TreeNode  treeNode = sysRoleService.getAllNode();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", treeNode.getChildrenList());
		dataMap.put("flag", 1);
		 return dataMap;
	 }

	@RequestMapping(value = "delete", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object delete(@RequestParam(value="id[]") String [] orders) {
		if (orders.length ==1) {
			Integer pId = Integer.parseInt(orders[0]);
			Map<String, Object> map = new HashMap<String, Object>();
			// 判断该岗位下是否存在用户
			boolean isExistsUser = sysRoleService.isExistsUserOfPosition(pId);
			if (isExistsUser) {
				map.put("flag", 0);
				map.put("message", "要删除的权限组已经被用户使用或为班主任、老师权限组故不能删除!");
			} else {
				List<Map<String, Integer>> list = new ArrayList<Map<String,Integer>>();
				Map<String, Integer> paramMap = new HashMap<String, Integer>();
				paramMap.put("id", pId);
				list.add(paramMap);
				// 删除引用资源
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("list", list);
				sysRoleService.deleteResAndRoleRelate(map1);
				//删除角色
				int count = sysRoleService.deleteRole(pId);
				map.put("flag", count > 0 ? "1" : 0);
				map.put("message", count > 0 ? "删除成功!" : "删除失败!");
			}
			return map;
		} else {
			Map<String, Object> mapList = sysRoleService.deleteManyRole(orders);
			return mapList;
		}
	}
}
