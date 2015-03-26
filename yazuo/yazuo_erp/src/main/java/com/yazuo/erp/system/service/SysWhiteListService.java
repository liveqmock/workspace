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

import java.util.*;

import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface SysWhiteListService{
	
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
	public List<SysWhiteListVO> getSysWhiteLists(SysWhiteListVO sysWhiteListVO);
	
	/**
	 * 返回所有满足条件的对象，返回对象list<Map<String, Object>>
	 */
	public List<Map<String, Object>>  getSysWhiteListsMap(Map<String, Object> map);
	
	/**
	 * 返回所有的对象,无查询条件
	 */
	public List<Map<String, Object>> getAllSysWhiteLists();
	

}
