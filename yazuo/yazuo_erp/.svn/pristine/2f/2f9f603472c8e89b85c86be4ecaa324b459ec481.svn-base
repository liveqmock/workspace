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

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface FesStepService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveFesStep (FesStepVO fesStep);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertFesSteps (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateFesStep (FesStepVO fesStep);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateFesStepsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateFesStepsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteFesStepById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesStepByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	FesStepVO getFesStepById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesStepVO> getFesSteps (FesStepVO fesStep);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getFesStepsMap (FesStepVO fesStep);
	

}
