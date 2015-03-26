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

package com.yazuo.erp.syn.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.syn.vo.SynMerchantProductVO;

/**
 * @Description TODO
 * @author erp team
 * @date
 */
public interface SynMerchantProductService {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSynMerchantProduct(SynMerchantProductVO synMerchantProduct);

	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertSynMerchantProducts(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSynMerchantProduct(SynMerchantProductVO synMerchantProduct);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSynMerchantProductsToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSynMerchantProductsToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteSynMerchantProductById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSynMerchantProductByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	SynMerchantProductVO getSynMerchantProductById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SynMerchantProductVO> getSynMerchantProducts(SynMerchantProductVO synMerchantProduct);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getSynMerchantProductsMap(SynMerchantProductVO synMerchantProduct);

	/**
	 * 返回商户开通的产品列表
	 */
	List<String> getProdectsByMerchantId(Integer merchantId);
}
