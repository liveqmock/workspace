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

package com.yazuo.erp.system.dao;

import java.util.*;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.*;

/**
 * @Description 客户投诉DAO
 * @author erp team
 * @date 
 */
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface SysCustomerComplaintDao {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysCustomerComplaint(SysCustomerComplaintVO sysCustomerComplaint);

	/**
	 * 新增多个对象 @return : 影响的行数
	 * 
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertSysCustomerComplaints(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysCustomerComplaint(SysCustomerComplaintVO sysCustomerComplaint);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysCustomerComplaintsToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysCustomerComplaintsToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteSysCustomerComplaintById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysCustomerComplaintByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	SysCustomerComplaintVO getSysCustomerComplaintById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysCustomerComplaintVO> getSysCustomerComplaints(SysCustomerComplaintVO sysCustomerComplaint);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getSysCustomerComplaintsMap(Map<String, Object> paramerMap);

	/**
	 * @Description 通过主键查找客户投诉
	 * @param id
	 * @return
	 * @throws
	 */
	Map<String, Object> querySysCustomerComplaintById(Integer id);

	/**
	 * @Description 返回所有返回所有满足条件的Map客户投诉的List
	 * @param paramerMap
	 * @return
	 * @throws
	 */ 
	Page<Map<String, Object>> querySysCustomerComplaintList(Map<String, Object> paramerMap);

}
