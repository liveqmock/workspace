package com.yazuo.weixin.service;

import java.util.Map;

/**
 * 根据merchantId查询brandId
 * 
 * @author gaoshan
 * 
 */
public interface QueryBrandIdService {

	/**
	 * 根据merchantId查询brandId
	 * 
	 * @param merchantId 商户ID
	 * @return
	 * @throws Exception
	 */
	int queryBrandIdByMerchantId(Integer merchantId);

}
