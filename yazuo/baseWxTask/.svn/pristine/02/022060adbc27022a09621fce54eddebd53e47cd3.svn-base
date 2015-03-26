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
 * @date 2014-8-12 下午4:15:19
 */
@Repository
public class HealthDegreeActualValueStatisticsDao extends BaseDAO {
	public List<Map<String, Object>> getTargetActualValueByBrandIds(String transDate, String maxMonth) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT                                                                  ");
		sb.append(" 	dms.brand_id,                                                       ");
		sb.append(" 	to_char(date_trunc('day', dms.trans_date), 'yyyy-mm')AS trans_date, ");
		sb.append(" 	COALESCE(ROUND(SUM(dms.card_income)), 0)AS card_income,             ");
		sb.append(" 	COALESCE(ROUND(SUM(dms.store_pay_cash)), 0)AS store_pay,            ");
		sb.append(" 	COALESCE(SUM(dms.integral_member), 0)AS integral_member,            ");
		sb.append(" 	COALESCE(SUM(dms.trans_quantity), 0)AS trans_quantity,              ");
		sb.append(" 	COALESCE(SUM(dms.old_trans_quantity), 0)AS old_trans_quantity,      ");
		sb.append(" 	COALESCE(SUM(dms.integral_card), 0)AS integral_card,                ");
		sb.append(" 	COALESCE(SUM(dms.store_card), 0)AS store_card                       ");
		sb.append(" FROM                                                                    ");
		sb.append(" 	report.daily_merchant_summary AS dms                                ");
		sb.append(" WHERE                                                                   ");
		sb.append(" 	dms.trans_date >= ?                                                 ");
		sb.append(" AND dms.trans_date < ?                                                  ");
		sb.append(" AND EXISTS(                                                             ");
		sb.append(" 	SELECT                                                              ");
		sb.append(" 		*                                                               ");
		sb.append(" 	FROM                                                                ");
		sb.append(" 		trade.merchant AS mer1                                          ");
		sb.append(" 	WHERE                                                               ");
		sb.append(" 		mer1.status = 1                                                 ");
		sb.append(" 	AND dms.merchant_id = mer1.merchant_id                              ");
		sb.append(" )                                                                       ");
		sb.append(" GROUP BY                                                                ");
		sb.append(" 	dms.brand_id,                                                       ");
		sb.append(" 	to_char(date_trunc('day', dms.trans_date), 'yyyy-mm')               ");
		return jdbcTemplateCrm46.queryForList(sb.toString(), transDate, maxMonth);
	}
}
