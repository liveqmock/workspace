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

package com.yazuo.erp.fes.service;

import java.util.Map;

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface FesTrainPlanService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
 * @param sessionUser 
 * @param sessionUser 
	 */
	FesTrainPlanVO saveFesTrainPlan (FesTrainPlanVO fesTrainPlan, SysUserVO sessionUser);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertFesTrainPlans (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateFesTrainPlan (FesTrainPlanVO fesTrainPlan);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateFesTrainPlansToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateFesTrainPlansToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteFesTrainPlanById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesTrainPlanByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	FesTrainPlanVO getFesTrainPlanById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesTrainPlanVO> getFesTrainPlans (FesTrainPlanVO fesTrainPlan);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getFesTrainPlansMap (FesTrainPlanVO fesTrainPlan);
	/**
	 * 判断对有某流程是否存在培训计划
	 */
	Boolean haveFesTrainPlans(Integer processId);
	

}
