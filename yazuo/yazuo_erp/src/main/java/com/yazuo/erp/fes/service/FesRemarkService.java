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

import java.util.List;
import java.util.Map;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.vo.FesRemarkVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface FesRemarkService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	public FesRemarkVO saveFesRemark (FesRemarkVO fesRemark, SysUserVO sessionUser);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertFesRemarks (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateFesRemark (FesRemarkVO fesRemark);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateFesRemarksToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateFesRemarksToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	JsonResult deleteFesRemarkById (FesRemarkVO remarkVO, SysUserVO user);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesRemarkByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	FesRemarkVO getFesRemarkById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesRemarkVO> getFesRemarks (FesRemarkVO fesRemark);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getFesRemarksMap (FesRemarkVO fesRemark);
	

	JsonResult saveAndUpdateRemarkAddOther(FesRemarkVO fesRemarkVO, SysUserVO user);
}
