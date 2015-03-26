/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.bes.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.bes.vo.BesCallRecordVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date 
 */
public interface BesCallRecordService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveBesCallRecord (BesCallRecordVO besCallRecord, SysUserVO sessionUser);
	/**
	 * 新增多个对象 @return : 
	 */
	int batchInsertBesCallRecords (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateBesCallRecord (BesCallRecordVO besCallRecord);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateBesCallRecordsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateBesCallRecordsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteBesCallRecordById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteBesCallRecordByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	BesCallRecordVO getBesCallRecordById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<BesCallRecordVO> getBesCallRecords (BesCallRecordVO besCallRecord);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getBesCallRecordsMap (BesCallRecordVO besCallRecord);
	

}
