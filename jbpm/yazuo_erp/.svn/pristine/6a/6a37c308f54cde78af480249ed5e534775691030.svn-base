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
package com.yazuo.external.account.dao;

import java.util.List;
import java.util.Map;

import com.yazuo.external.account.vo.MerchantVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-6 下午6:47:48
 */
public interface MerchantDao {
	List<Integer> getAllBrandId();

	List<Map<String, Object>> getAllMerchants();

	List<Integer> getCrmMerchantIdListByBrandId(Integer brandId);

	List<Map<String, Object>> getMerchantsInfo(List<Integer> merchantIds, String merchantName);

	MerchantVO getMerchantVO(Integer merchantId);

	Long getMerchantFaceShopCount(Integer brandId);

	/**
	 * 查询商户信息，merchantId为品牌号
	 */
	MerchantVO getMerchantById(Integer merchantId);

	List<Map<String, Object>> getMerchantsForSurvey(Integer merchantId);
	
}
