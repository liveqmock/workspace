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
package com.yazuo.erp.statistics.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-7 上午11:04:55
 */
@Repository
public class MerchantSMSCountStatisticsDao extends BaseDAO {

	public List<Map<String, Object>> merchantSMSCount(int count) {
		String sql = "SELECT m.merchant_id,m.merchant_name,s.smsbalance  FROM "
				+ SCHEMANAME_TRADE
				+ ".merchant_sms_balance s,"
				+ SCHEMANAME_TRADE
				+ ".merchant m WHERE s.merchant_id = m .merchant_id AND s.smsbalance <= ? AND m .status = 1 ORDER BY s.smsbalance";

		List<Map<String, Object>> list = jdbcTemplateCrm.queryForList(sql, count);
		return list;
	}
}
