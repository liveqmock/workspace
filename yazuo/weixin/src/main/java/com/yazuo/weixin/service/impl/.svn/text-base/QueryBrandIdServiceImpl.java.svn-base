package com.yazuo.weixin.service.impl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.MerchantDao;
import com.yazuo.weixin.service.QueryBrandIdService;

/**
 * 根据merchantId查询brandId
 * 
 * @author gaoshan
 * 
 */
@Scope("prototype")
@Service
public class QueryBrandIdServiceImpl implements QueryBrandIdService {

	@Autowired
	private MerchantDao merchantDao;

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 根据merchantId查询brandId
	 */
	@Override
	public int queryBrandIdByMerchantId(Integer merchantId)  {
		int brandId = merchantDao.queryBrandIdByMerchantId(merchantId);
		return brandId;
	}


}
