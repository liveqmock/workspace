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

import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


@Repository
public interface SysResourceDao{
	
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
	 * @parameter entity
	 */
	public int updateSysResource (SysResourceVO entity);
	/**
	 *  根据启用或禁用条件批量更新resource visiable状态 , flag:1/0  启用/禁用
	 */
	public int updateSysResources (Map<String, Object> map);
	
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
	public List<Map<String, Object>> getSysResourcesMap(Map<String, Object> map);
	/**
	 * 返回记录数
	 */
	public long getSysResourceCount(Map<String, Object> map);
	/**
	 * 用户登录成功时查询所有用户的拥有的资源， 返回一个大的list， 具体导航菜单由前端来处理
	 */
	List<SysResourceVO>  getAllUserResourceByPrivilege(Map<String, Object> paramMap);
	/**
	 *  通过资源 remark 查询 所有拥有该资源的用户 
	 * @param sysResourceVO [treeCode]
	 */
	List<SysUserVO>  getAllUsersByResourceCode(SysResourceVO sysResourceVO);

}
