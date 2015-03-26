/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.syn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.syn.vo.ComplexSynMerchantVO;
import com.yazuo.erp.syn.vo.ExternalMerchantInfoVO;
import com.yazuo.erp.syn.vo.SynMerchantVO;
/**
 * @author erp team
 * @date 
 */

@Repository
public interface SynMerchantDao {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	@CacheEvict(value="merchantCache", allEntries=true)
	int saveSynMerchant(SynMerchantVO synMerchant);

	/**
	 * 新增多个对象 @return : 影响的行数
	 * 
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertSynMerchants(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	@CacheEvict(value="merchantCache", allEntries=true)
	int updateSynMerchant(SynMerchantVO synMerchant);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSynMerchantsToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSynMerchantsToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteSynMerchantById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSynMerchantByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	SynMerchantVO getSynMerchantById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SynMerchantVO> getSynMerchants(SynMerchantVO synMerchant);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getSynMerchantsMap(SynMerchantVO synMerchant);

	/**
	 * @Description 根据用户ID查询自己所管理的商户的列表（前端咨询人员-商户）
	 * @param userId
	 * @return
	 * @throws
	 */
	List<Map<String, Object>> getSynMerchantByUserId(Integer userId);

	/**
	 * 查询商户表记录数
	 */
	long getSynMerchantCount();
	int getMaxSynMerchantId();

	/**
	 * 查询商户列表
	 */
	List<Map<String, Object>> getSynMerchantInfo(@Param(value="merchantName")String merchantName);
	
	/**
	 * 返回前端我的主页查询出来的所有商户信息
	 */
	List<ComplexSynMerchantVO> getComplexSynMerchants(Map<String, Object> inputMap);
	
	/**取某个用户所负责的商户信息*/
	List<Map<String, Object>> getSynMerchantInfoByUserId(Map<String, Object> map);
	
	/**商户看版上商户及相关信息填写访谈单的*/
	List<ExternalMerchantInfoVO> getMerchantInfoByOrder(Map<String, Object> paramMap);
	
	/**商户看版上商户及相关信息没有填写访谈单的*/
	List<ExternalMerchantInfoVO> getMerchantInfoNoneInterviewByOrder(Map<String, Object> paramMap);

	/**
	 * @Description 查询已上线商户基本信息
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws 
	 */
	List<Map<String, Object>> getSynMerchantReport();


	List<Integer> getAllAvailMerchantIds();

	List<SynMerchantVO> getMerchantNamesFromErp(@Param("merchantIds") List<Integer> merchantIds);
}
