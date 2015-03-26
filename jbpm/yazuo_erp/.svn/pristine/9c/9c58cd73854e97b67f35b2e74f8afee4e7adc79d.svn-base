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

package com.yazuo.erp.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.system.vo.SysGroupVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */


@Repository
public interface SysGroupDao{

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
	 * 返回XTree需要的数据
	 * @param sysGroupVO 
	 */
	public List<Map<String, Object>>  getSysGroupsForXTree(SysGroupVO sysGroupVO);
	
	/**
	 * 返回所有的对象,无查询条件
	 */
	public List<Map<String, Object>> getAllSysGroups();
	

	/**获取组织树形结构*/
	List<Map<String, String>> getSysGroupByParentId(Integer parentId);
	/**
	 * 获取同级最大的tree code
	 */
	SysGroupVO getMaxTreeCodeOfSameLevel(Integer parentId);
	
	int getCountOfGroupUser(Integer groupId);
}
