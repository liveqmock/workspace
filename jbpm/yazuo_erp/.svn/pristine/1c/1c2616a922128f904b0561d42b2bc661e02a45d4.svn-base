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

package com.yazuo.erp.fes.dao;

import java.util.*;
import com.yazuo.erp.fes.vo.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FesPlanDao {

	/**
	 * 新增对象
	 * 
	 * @return : 新增加的主键id
	 */
	int saveFesPlan(FesPlanVO fesPlan);

	/**
	 * 新增多个对象
	 * 
	 * @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertFesPlans(Map<String, Object> map);

	/**
	 * 修改对象
	 * 
	 * @return : 影响的行数
	 */
	int updateFesPlan(FesPlanVO fesPlan);

	/**
	 * 修改多个对象（每一条记录可以不同）
	 * 
	 * @return : 影响的行数
	 */
	int batchUpdateFesPlansToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同）
	 * 
	 * @return : 影响的行数
	 */
	int batchUpdateFesPlansToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteFesPlanById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesPlanByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	FesPlanVO getFesPlanById(Integer id);
	
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesPlanVO> getFesPlans(FesPlanVO fesPlan);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getFesPlansMap(FesPlanVO fesPlan);

	/**
	 * @Description 查询工作计划列表
	 * @param startTime
	 * @param endTime
	 * @param merchantId
	 * @param userId
	 * @return
	 * @throws
	 */
	List<Map<String, Object>> queryFesPlanList(@Param(value = "startTime") Date startTime,
			@Param(value = "endTime") Date endTime, @Param(value = "userId") Integer userId,
			@Param(value = "merchantId") Integer merchantId);

	/**
	 * @Description 根据指定ID查询工作计划
	 * @param planId
	 * @return
	 * @throws
	 */
	Map<String, Object> queryFesPlan(Integer planId);

	/**
	 * @Description 根据用户ID、商户ID、月份查询月报类型工作计划的个数
	 * @param inputMap
	 * @return
	 * @return int
	 * @throws 
	 */
	int getCountOfMonthlyPlans(Map<String, Object> inputMap);

    List<FesPlanVO> getMonthlyPlanVO(@Param("merchantId") Integer merchantId, @Param("from") Date from, @Param("to") Date to);

	/**
	 * @Description 根据用户ID、商户ID、月份查询已完成的、月报类型的工作计划的个数
	 * @param inputMap
	 * @return
	 * @return int
	 * @throws 
	 */
	int getCountOfCompletedMonthlyPlans(Map<String, Object> inputMap);

    FesPlanVO getCurrentMonthlyPlan(@Param("merchantId")Integer merchantId, @Param("from") Date from, @Param("to") Date to);
}
