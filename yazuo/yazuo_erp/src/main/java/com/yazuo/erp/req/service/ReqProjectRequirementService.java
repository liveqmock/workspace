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

package com.yazuo.erp.req.service;

import java.util.Map;

import java.util.*;
import com.yazuo.erp.req.vo.*;
import com.yazuo.erp.req.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface ReqProjectRequirementService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveReqProjectRequirement (ReqProjectRequirementVO ReqProjectRequirementVO);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertReqProjectRequirements (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateReqProjectRequirement (ReqProjectRequirementVO ReqProjectRequirementVO);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateReqProjectRequirementsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateReqProjectRequirementsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteReqProjectRequirementById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteReqProjectRequirementByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	ReqProjectRequirementVO getReqProjectRequirementById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<ReqProjectRequirementVO> getReqProjectRequirements (ReqProjectRequirementVO ReqProjectRequirementVO);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getReqProjectRequirementsMap (ReqProjectRequirementVO ReqProjectRequirementVO);
	

}
