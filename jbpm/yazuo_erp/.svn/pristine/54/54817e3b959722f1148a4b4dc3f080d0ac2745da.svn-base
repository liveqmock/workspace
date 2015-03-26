/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 */
package com.yazuo.erp.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.TreeNode;
import com.yazuo.erp.system.dao.SysRoleDao;
import com.yazuo.erp.system.service.SysRoleService;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.util.StringUtils;

/**
 * @Description 角色及管理模块相关操作接口实现
 * @author kyy
 * @date 2014-6-4 上午11:07:00
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;
	
	@Override
	public Map<String, Object> saveRole(SysRoleVO sysRole, String []resourceId) {
		Integer roleId = 0;
		String msg = "";
		// 判断是否存在
		long sameCount = isExistsSameName(sysRole.getRoleName());
		if (sameCount > 0) {
			msg = sysRole.getRoleName()+ "角色已经存在！";
		} else {
			// 保存权限组
			sysRoleDao.saveSysRole(sysRole);;
			roleId = sysRole.getId();
			// 添加资源与角色关联关系
			List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
			for (int i=0; i < resourceId.length; i++) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("roleId", roleId);
				params.put("insertBy", sysRole.getUpdateBy());
				params.put("insertTime", new Date());
				params.put("resourceId", Integer.parseInt(resourceId[i]));
				resList.add(params);
			}
			saveSysRoleResource(resList);
			msg = roleId > 0 ? "添加成功!" : "添加失败!";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", roleId > 0 ? 1 : 0);
		map.put("message", msg);
		return map;
	}

	@Override
	public Map<String, Object> updateRole(SysRoleVO sysRole, String []resourceId) {
		Integer roleId = sysRole.getId();
		String msg = "";
		int count = 0;
		// 判断是否存在
		long sameCount = isExistsSameName(sysRole.getRoleName());
		if (sameCount > 1) {
			msg = sysRole.getRoleName()+ "角色已经存在！";
		} else {
			// 添加角色
			count = sysRoleDao.updateSysRole(sysRole);
			// 修改角色时将资源与角色关联关系删除，在重新添加
			List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("id", roleId);
			list.add(map);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("list", list);
			deleteResAndRoleRelate(paramMap);
		
			//添加资源与角色关联关系
			List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
			for (int i=0; i < resourceId.length; i++) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("roleId", roleId);
				params.put("insertBy", sysRole.getUpdateBy());
				params.put("insertTime", new Date());
				params.put("resourceId", Integer.parseInt(resourceId[i]));
				resList.add(params);
			}
			
			saveSysRoleResource(resList); 
			msg = count > 0 ? "修改成功!" : "修改失败!";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", roleId > 0 ? 1 : 0);
		map.put("message", msg);
		return map;
	}

	@Override
	public int deleteRole(Integer id) {
		return sysRoleDao.deleteSysRole(id);
	}

	@Override
	public SysRoleVO getById(Integer id) {
		return sysRoleDao.getSysRoleById(id);
	}

	@Override
	public List<Map<String, String>> getAllPageByOder(Map<String, String> paramMap) {
		int pageSize = Integer.parseInt(paramMap.get("pageSize"));
		int pageNumber = Integer.parseInt(paramMap.get("pageNumber"));
		// 分页
		PageHelper.startPage(pageNumber, pageSize, false);
		return sysRoleDao.getAllSysRoles(paramMap);
	}

	@Override
	public List<Map<String, String>> getAllRole() {
		return sysRoleDao.getAllSysRoles(new HashMap<String, String>());
	}

	@Override
	public boolean isExistsUserOfPosition(Integer roleId) {
		long count = sysRoleDao.getUserCountByRoleId(roleId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public long isExistsSameName(String roleName) {
		long count = sysRoleDao.getCountByRoleName(roleName);
		return count;
	}

	public TreeNode getAllNode() {
		TreeNode node = new TreeNode();
		node.setId("0");
		node.setText("根目录");
		node.setUrl("");
		this.getChildrenNode(node);
		return node;
	}

	private void getChildrenNode (TreeNode parentNode) {
		List<Map<String, String>> pList = sysRoleDao.getSysResourceByParentId(Integer.parseInt(parentNode.getId()));
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (Map<String, String> map : pList) {
			TreeNode node = new TreeNode();
			node.setId(String.valueOf(map.get("id")));
			node.setText(map.get("resource_name"));
			node.setUrl(map.get("url"));
			node.setRemark(map.get("remark"));
			getChildrenNode(node);
			treeList.add(node);
		}
		if(treeList.size()>0) parentNode.setChildrenList(treeList);
	}


	@Override
	public long getTotalCount() {
		return sysRoleDao.getSysRoleCount();
	}

	@Override
	public Map<String, Object> deleteManyRole(String [] orders) {
		StringBuilder noSb = new StringBuilder();
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		for (int i = 0; i < orders.length; i ++) {
			Integer id = Integer.parseInt(orders[i]);
			// 判断该岗位下是否存在用户
			boolean isExistsUser = isExistsUserOfPosition(id);
			if (isExistsUser) { // 存在
				noSb.append(id).append(",");
			} else {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put("id", id);
				list.add(map);
			}
		}
		// 批量删除角色与资源关联关系
		if (list !=null && list.size() > 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("list", list);
			sysRoleDao.deleteResoucerByRoleId(paramMap);
		}
		// 批量删除
		int count = sysRoleDao.deleteMany(list);
		String msg = "";
		if (!StringUtils.isEmpty(noSb.toString())) {
			msg = "选中的职位中部分被用户使用，故部分删除成功!";
		} else {
			msg = "删除成功!";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", count > 0 ? "1" : "0");
		map.put("message", count > 0 ? msg : "删除失败!");
		return map;
	}

	@Override
	public int deleteResAndRoleRelate(Map<String, Object> map) {
		return sysRoleDao.deleteResoucerByRoleId(map);
	}

	@Override
	public void saveSysRoleResource(List<Map<String, Object>> resList) {
		sysRoleDao.saveSysRoleResource(resList);
	}

	@Override
	public List<Map<String, String>> getCheckedSysResource(Integer roleId) {
		return sysRoleDao.getCheckedSysResource(roleId);
	}
	
	
	
}
