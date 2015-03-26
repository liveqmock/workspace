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

package com.yazuo.erp.bes.service;

import java.util.Map;

import java.util.*;
import com.yazuo.erp.bes.vo.*;
import com.yazuo.erp.bes.dao.*;
import com.yazuo.erp.interceptors.Page;

/**
 * @Description TODO
 * @author erp team
 * @date
 */
public interface BesMonthlyCheckService {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveBesMonthlyCheck(BesMonthlyCheckVO besMonthlyCheck);

	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertBesMonthlyChecks(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateBesMonthlyCheck(BesMonthlyCheckVO besMonthlyCheck);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateBesMonthlyChecksToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateBesMonthlyChecksToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteBesMonthlyCheckById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteBesMonthlyCheckByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	BesMonthlyCheckVO getBesMonthlyCheckById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<BesMonthlyCheckVO> getBesMonthlyChecks(BesMonthlyCheckVO besMonthlyCheck);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getBesMonthlyChecksMap(BesMonthlyCheckVO besMonthlyCheck);

	/**
	 * @Description 列表显示 月报检查
	 * @param params
	 * @return
	 * @return Page<Map<String,Object>>
	 * @throws
	 */
	Page<Map<String, Object>> queryBesMonthlyCheckList(Map<String, Object> params);

	boolean monthlyReportUncompleted(Integer merchantId);

}
