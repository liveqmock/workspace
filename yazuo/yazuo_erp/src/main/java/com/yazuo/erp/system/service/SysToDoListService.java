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
public interface SysToDoListService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysToDoList (SysToDoListVO sysToDoList);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertSysToDoLists (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysToDoList (SysToDoListVO sysToDoList);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysToDoListsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysToDoListsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSysToDoListById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysToDoListByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysToDoListVO getSysToDoListById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysToDoListVO> getSysToDoLists (SysToDoListVO sysToDoList);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysToDoListsMap (SysToDoListVO sysToDoList);
	/**
	 * @Description 完成代办事项 更改状态为已完成
	 */
	int batchUpdateCloseSysToDoLists(SysToDoListVO inputSysToDoListVO);
	/**
	 * @Description 查询代办事项 返回关联的数据
	 */
	List<SysToDoListVO> getComplexSysToDoLists(SysToDoListVO sysToDoList);
	/**
	 * @param sessionUser
	 * @param sysToDoList
	 * @throws 
	 */
	void saveToDoList(SysUserVO sessionUser, SysToDoListVO sysToDoList);
	void saveToDoListForClickOnline(Integer merchantId, SysUserVO sessionUser);
}
