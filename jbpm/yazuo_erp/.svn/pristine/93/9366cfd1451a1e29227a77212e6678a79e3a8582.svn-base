/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.base.TreeVO;
import com.yazuo.erp.bes.vo.BesTalkingSkillsVO;
import com.yazuo.erp.system.vo.SysUserVO;

import java.util.*;
import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;

/**
 * @author erp team
 * @date 
 */
public interface MktBusinessTypeService{

	/**
	 * 保存或修改
	 */
	int saveOrUpdateMktBusinessType (MktBusinessTypeVO mktBusinessType, SysUserVO sessionUser);
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveMktBusinessType (MktBusinessTypeVO mktBusinessType);
	/**
	 * 新增多个对象 @return 
	 */
	int batchInsertMktBusinessTypes (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateMktBusinessType (MktBusinessTypeVO mktBusinessType);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateMktBusinessTypesToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateMktBusinessTypesToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteMktBusinessTypeById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteMktBusinessTypeByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	MktBusinessTypeVO getMktBusinessTypeById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<MktBusinessTypeVO> getMktBusinessTypes (MktBusinessTypeVO mktBusinessType);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getMktBusinessTypesMap (MktBusinessTypeVO mktBusinessType);
	List<TreeVO> getAllNode(String[] formats);
}
