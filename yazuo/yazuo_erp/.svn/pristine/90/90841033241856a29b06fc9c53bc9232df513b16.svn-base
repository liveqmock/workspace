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
package com.yazuo.erp.system.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.TreeNode;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description TODO
 * @author song
 * @date 2014-6-5 上午10:23:30
 */
public interface ResourceService {
	/**
	 * 根据级别查询没一级对应的记录
	 * @parameter map ,包含级别和其他查询条件
	 */
	public List<SysResourceVO> getSysResourcesByLevel (Map<String, Object> map);
	/**
	 * 新增对象
	 * @parameter entity
	 */
	public int saveSysResource (SysResourceVO entity);
	
	/**
	 * 修改对象
	 * @throws Exception 
	 * @parameter entity
	 */
	public int updateSysResource (SysResourceVO entity);
	
	/**
	 * 删除对象
	 * @parameter id
	 */
	public int deleteSysResource(Integer id);
	
	/**
	 * 通过主键查找对象
	 * @parameter id
	 */
	public SysResourceVO getSysResourceById(Integer id);
	/**
	 * 返回所有记录
	 */
	public List<Map<String, Object>> getAllSysResources();
	
	/**
	 * 返回满足条件的记录
	 */
	public List<SysResourceVO> getSysResources(Map<String, Object> map);
	
	/**
	 * 返回满足条件的记录
	 */
	public Page<Map<String, Object>> getSysResourcesMap(Map<String, Object> map);
	/**
	 * 返回记录数
	 */
	public Integer getSysResourceCount(Map<String, Object> map);
	/**
	 * 根据主键更新 可见/不可见状态 
	 */
	int updateSysResourceVisableStatus(int id);
	/**
	 *  根据启用或禁用条件批量更新resource visiable状态 , flag:1/0  启用/禁用
	 */
	int updateSysResources(Map<String, Object> paramerMap);
	/**
	 * 配置显示导航 如： 资源管理 -> 培训考试
	 */
	List<Map<String, Object>> getPageNavigate(int currentId);
	List<Map<String, Object>> getResourceByParent(String resourceRemark);
	List<SysUserVO> getAllUsersByResourceCode(SysResourceVO sysResourceVO);
}
