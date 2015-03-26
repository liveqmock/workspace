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

package com.yazuo.erp.mkt.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.mkt.vo.MktShopSurveyVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.external.account.vo.MerchantVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface MktShopSurveyService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	JsonResult saveMktShopSurvey (MktShopSurveyVO mktShopSurvey, SysUserVO user);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertMktShopSurveys (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	JsonResult updateMktShopSurvey (MktShopSurveyVO mktShopSurvey, SysUserVO user);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateMktShopSurveysToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateMktShopSurveysToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	JsonResult deleteMktShopSurveyById (MktShopSurveyVO shopVo, SysUserVO user);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteMktShopSurveyByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	MktShopSurveyVO getMktShopSurveyById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<MktShopSurveyVO> getMktShopSurveys (MktShopSurveyVO mktShopSurvey);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getMktShopSurveysMap (MktShopSurveyVO mktShopSurvey);
	
	/**点确认完成按钮，结束待办事项和插入流水*/
	JsonResult salesConfirm(Integer merchantId, SysUserVO user);
	
	/**判断确认按钮是否可用*/
	String judgeSalesConfirmBtnEnble(Integer merchantId);
	
	/**访谈单是否存在*/
	boolean brandInterviewExists(Integer merchantId);
	List<Map<String, Object>> getMerchantsForSurvey(Integer merchantId);
	MktShopSurveyVO loadMktShopSurvey(MktShopSurveyVO mktShopSurvey);
	MerchantVO getMerchantVOFromCRM(Integer merchantId);
}
