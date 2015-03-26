/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 */
package com.yazuo.erp.system.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.system.TreeNode;
import com.yazuo.erp.system.vo.SysRoleVO;

/**
 * @Description 角色及管理模块相关操作接口定义
 * @author kyy
 * @date 2014-6-4 上午11:07:00
 */
public interface SysRoleService {

	/**保存角色*/
	public Map<String, Object> saveRole(SysRoleVO sysRole, String []resourceId);
	
	/**修改角色*/
	public Map<String, Object> updateRole(SysRoleVO sysRole, String []resourceId);
	
	/**删除角色*/
	public int deleteRole(Integer id);
	
	/**根据id获取相关对象*/
	public SysRoleVO getById(Integer id);
	
	/**根据条件查询*/
	public List<Map<String, String>> getAllPageByOder(Map<String, String> paramMap);
	
	/**获取所有角色*/
	public List<Map<String, String>> getAllRole();
	
	/**判断该角色是否有用户在使用*/
	public boolean isExistsUserOfPosition(Integer roleId);
	
	/**判断是否重名*/
	public long isExistsSameName (String roleName);
	
	/**构建树形结构*/
	public TreeNode getAllNode();
	
	/**获取总数量*/
	public long getTotalCount();
	
	/**批量删除*/
	public Map<String, Object> deleteManyRole (String [] orders);
	
	/**删除角色时将与资源关系也删除*/
	public int deleteResAndRoleRelate(Map<String, Object> map);
	
	/**保存角色与资源的关系表*/
	void saveSysRoleResource(List<Map<String, Object>> resList);
	
	/**取该角色相关的资源*/
	List<Map<String, String>> getCheckedSysResource(Integer roleId);
}
