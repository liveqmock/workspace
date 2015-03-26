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

import java.util.*;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface SysMailTemplateService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysMailTemplate (SysMailTemplateVO sysMailTemplate);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertSysMailTemplates (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysMailTemplate (SysMailTemplateVO sysMailTemplate);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysMailTemplatesToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysMailTemplatesToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSysMailTemplateById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysMailTemplateByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysMailTemplateVO getSysMailTemplateById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	Page<SysMailTemplateVO> getSysMailTemplates (SysMailTemplateVO sysMailTemplate);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysMailTemplatesMap (SysMailTemplateVO sysMailTemplate);
	

}
