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

import com.yazuo.erp.system.vo.SysWhiteListVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */


@Repository
public interface SysWhiteListDao{
	
	/**
	 * 新增对象
	 */
	public int saveSysWhiteList (List<SysWhiteListVO> list);
	
	/**
	 * 修改对象
	 */
	public int updateSysWhiteList (SysWhiteListVO entity);
	
	/**
	 * 删除对象
	 */
	public int deleteSysWhiteList(Integer id);
	
	/**
	 * 通过主键查找对象
	 */
	public SysWhiteListVO getSysWhiteListById(Integer id);
	
	/**
	 * 返回所有返回所有满足条件的对象，返回对象list<Object>
	 */
	public List<SysWhiteListVO> getSysWhiteLists(Map<String, Object> map);
	
	/**
	 * 返回所有满足条件的对象，返回对象list<Map<String, Object>>
	 */
	public List<Map<String, Object>>  getSysWhiteListsMap(Map<String, Object> map);
	
	/**
	 * 返回所有的对象,无查询条件
	 */
	public List<Map<String, Object>> getAllSysWhiteLists();
	/**
	 * 通过用户Id删除白名单
	 */
	public int deleteSysWhiteByUser(Integer userId);
}
