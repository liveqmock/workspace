/**
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

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date 
 */
public interface FesMaterialRequestService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveFesMaterialRequest (FesMaterialRequestVO fesMaterialRequest);
	/**
	 * 新增多个对象
	 */
	int batchInsertFesMaterialRequests (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateFesMaterialRequest (FesMaterialRequestVO fesMaterialRequest);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateFesMaterialRequestsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateFesMaterialRequestsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteFesMaterialRequestById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesMaterialRequestByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	FesMaterialRequestVO getFesMaterialRequestById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesMaterialRequestVO> getFesMaterialRequests (FesMaterialRequestVO fesMaterialRequest);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getFesMaterialRequestsMap (FesMaterialRequestVO fesMaterialRequest);
	/**
	 * 保存设计需求单和明细
	 */
	FesOnlineProcessVO saveFesMaterialRequestAndDtls(FesMaterialRequestVO fesMaterialRequest, SysUserVO sessionUser);
	/**
	 * 判断是否存在 表单和附件
	 */
	boolean checkHasBothRequirementAndAttachment(FesOnlineProcessVO fesOnlineProcess);
	/**
	 * 保存或修改
	 */
	int saveOrUpdateFesMaterialRequest(FesMaterialRequestVO fesMaterialRequest, Integer userId);
	JsonResult uploadFesmaterialAttr(MultipartFile myfile, String realPath, Object object,
			SysUserVO sessionUser);
}
