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

import java.util.Map;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface SysUserRequirementService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysUserRequirement (SysUserRequirementVO sysUserRequirement, SysUserVO sessionUser);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertSysUserRequirements (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysUserRequirement (SysUserRequirementVO sysUserRequirement);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysUserRequirementsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysUserRequirementsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSysUserRequirementById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysUserRequirementByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysUserRequirementVO getSysUserRequirementById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysUserRequirementVO> getSysUserRequirements (SysUserRequirementVO sysUserRequirement);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysUserRequirementsMap (SysUserRequirementVO sysUserRequirement);
	

}
