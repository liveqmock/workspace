/**
 * @Description 
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.external.statictics.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.external.statictics.dao.MerchantSMSCountStatisticsDao;
import com.yazuo.external.statictics.service.MerchantSMSCountStatisticsService;

/**
 * @Description 商户短信余量
 * @author zhaohuaqin
 * @date 2014-8-14 下午3:02:05
 */
@Service
public class MerchantSMSCountStatisticsServiceImpl implements MerchantSMSCountStatisticsService {

	@Resource
	private MerchantSMSCountStatisticsDao merchantSMSCountStatisticsDao;

	/**
	 * 查询商户短信余量
	 */
	@Override
	public Map<String, Object> getMerchantSMSCount(Integer merchantId) {
		return merchantSMSCountStatisticsDao.getMerchantSMSCount(merchantId);
	}

}
