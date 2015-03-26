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
package com.yazuo.external.statictics.dao.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.external.statictics.dao.MerchantSMSCountStatisticsDao;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-14 下午3:04:01
 */
@Repository
public class MerchantSMSCountStatisticsDaoImpl implements MerchantSMSCountStatisticsDao {
	@Resource(name = "jdbcTemplateCrm210")
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> getMerchantSMSCount(Integer merchantId) {
		String sql = "SELECT s.merchant_id,s.smsbalance FROM trade.merchant_sms_balance s WHERE s.merchant_id = ?";
		try {
			return jdbcTemplate.queryForMap(sql, merchantId);
		} catch (DataAccessException e) {
			return null;
		}
	}

}
