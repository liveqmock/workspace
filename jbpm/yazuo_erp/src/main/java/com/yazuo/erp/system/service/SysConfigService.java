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

import java.util.Map;

import com.yazuo.erp.bes.vo.BesTalkingSkillsVO;
import com.yazuo.erp.system.vo.SysUserVO;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @author erp team
 * @date 
 */
public interface SysConfigService{

	/**
	 * 保存或修改
	 */
	int saveOrUpdateSysConfig (SysConfigVO sysConfig, SysUserVO sessionUser);
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysConfig (SysConfigVO sysConfig);
	/**
	 * 新增多个对象 @return 
	 */
	int batchInsertSysConfigs (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysConfig (SysConfigVO sysConfig);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysConfigsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysConfigsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSysConfigById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysConfigByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysConfigVO getSysConfigById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysConfigVO> getSysConfigs (SysConfigVO sysConfig);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysConfigsMap (SysConfigVO sysConfig);

    /**
     * 通过key返回相应的配置值
     * @param key
     * @return
     */
    String  getSysConfigValueByKey(String key);

    /**
     * 通过key返回相应的配置值，否则返回相应的默认值，并保存此默认值
     *
     * @param key
     * @param val
     * @return
     */
    String setSysConfigValueByKey(String key, String val,Integer userId);
}
