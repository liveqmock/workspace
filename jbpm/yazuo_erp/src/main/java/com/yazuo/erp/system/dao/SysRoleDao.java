/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.system.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.yazuo.erp.system.vo.SysRoleVO;

/**
 * 角色相关操作接口与mapper文件对应
 * @author kyy
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface SysRoleDao {
	
	public Integer saveSysRole(SysRoleVO entity);

	public int updateSysRole(SysRoleVO entity);

	public int deleteSysRole(Integer id);

	public SysRoleVO getSysRoleById(Integer id);

	public List<Map<String, String>> getAllSysRoles(Map<String, String> paramMap);
	
	/**判断该角色是否有用户在使用*/
	public long getUserCountByRoleId(Integer roleId);
	
	/**判断是否重名*/
	public long getCountByRoleName (String roleName);
	
	public List<SysRoleVO> getSysRoles();

	/**根据parent_id获取子节点*/
	public List<Map<String, String>> getSysResourceByParentId(Integer id);
	
	/**根据角色id获取相关联的资源*/
	public List<Map<String, String>> getCheckedSysResource(Integer roleId);
	
	public long getSysRoleCount();
	
	int deleteMany (List<Map<String, Integer>> list);
	
	int deleteResoucerByRoleId (Map<String, Object> map);
	
	void saveSysRoleResource(List<Map<String, Object>> resList);
}
