/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service;

import java.util.*;

import com.yazuo.erp.system.TreeNode;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @author erp team
 * @date 
 */
public interface SysGroupService{

	/**
	 * 返回所有的下属
	 */
	public List<SysUserVO> getSubordinateEmployees(Map<String, Object> map);
	
	/**
	 * 新增对象
	 */
	public int saveSysGroup (SysGroupVO entity);
	
	/**
	 * 修改对象
	 */
	public int updateSysGroup (SysGroupVO entity);
	
	/**
	 * 删除对象
	 * 返回： 如果组存在人员
	 */
	public int deleteSysGroup(Integer id);
	
	/**
	 * 通过主键查找对象
	 */
	public SysGroupVO getSysGroupById(Integer id);
	
	/**
	 * 返回所有返回所有满足条件的对象，返回对象list<Object>
	 */
	public List<SysGroupVO> getSysGroups(Map<String, Object> inputMap);
	
	/**
	 * 返回所有满足条件的对象，返回对象list<Map<String, Object>>
	 */
	public List<Map<String, Object>>  getSysGroupsMap();
	
	/**
	 * 返回所有的对象,无查询条件
	 */
	public List<Map<String, Object>> getAllSysGroups();

	/** 获取所有组织树形结构 */
	TreeNode getAllGroupNode();

	/**
	 * 添加同级
	 */
	public int addSameLevel(SysGroupVO sysGroupVO);

	/**
	 *添加子级
	 */
	public int addNextSameLevel(SysGroupVO sysGroupVO);
	/**
	 * 查看当前组是否存在用户
	 */
	public int checkIfExistsChildren(Integer groupId);

	List<Map<String, Object>> getSysGroupsForXTree();
}
