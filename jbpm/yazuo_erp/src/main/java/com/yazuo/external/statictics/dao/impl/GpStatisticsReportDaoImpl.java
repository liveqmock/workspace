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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.external.statictics.dao.GpStatisticsReportDao;
import com.yazuo.external.statictics.vo.DailyMerchantSummary;
import com.yazuo.external.statictics.vo.MonthMerchantSummary;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-11 下午2:25:27
 */
@Repository
public class GpStatisticsReportDaoImpl implements GpStatisticsReportDao {

	@Resource(name = "jdbcTemplateCrm")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "jdbcTemplateCrm210")
	private JdbcTemplate jdbcTemplate210;

	// 营销收益占比
	@Override
	public Map<String, Object> getMarketingRate(Integer brandId) {
		String sql = "SELECT value FROM public.merchant_index_value where merchant_id=? and value_id=24 and module_id = 9 limit 1";

		List<Map<String, Object>> list = jdbcTemplate210.queryForList(sql, brandId);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public MonthMerchantSummary getMonthMerchantSummary(Integer brandId, Date currentMonth, Date nextMonth, BigDecimal rate) {

		String sql = " SELECT COALESCE(SUM(new_member), 0) AS newMember," + " COALESCE(SUM(fans_member), 0) AS fansMember,"
				+ " COALESCE(SUM(integral_member), 0) AS integralMember," + " COALESCE(SUM(store_member), 0) AS storeMember,"
				+ " COALESCE(SUM(realcard), 0) AS realcard," + " ROUND(COALESCE(SUM(card_income), 0)) AS cardIncome,"
				+ " ROUND(COALESCE(SUM(store_pay_cash) , 0)) AS storePay," + " ROUND(COALESCE(SUM(cash), 0)) AS cash ,"
				+ " ROUND(COALESCE(SUM(store_consume), 0)) AS storeConsume,"
				+ " ROUND(COALESCE(SUM(integral_consume), 0)) AS integralConsume,"
				+ " COALESCE(SUM(trans_quantity), 0) AS transQuantity,"
				+ " COALESCE(SUM(old_trans_quantity), 0) AS oldTransQuantity,"
				+ " COALESCE(SUM(evaluate_quantity), 0) AS evaluateQuantity,"
				+ " COALESCE(SUM(evaluate_score), 0) AS evaluateScore," + " COALESCE(SUM(evaluate_sms), 0) AS evaluateSms,"
				+ " COALESCE(SUM(well_evaluate_quantity), 0) AS wellEvaluateQuantity,"
				+ " COALESCE(SUM(middle_evaluate_quantity), 0) AS middleEvaluateQuantity,"
				+ " COALESCE(SUM(poor_evaluate_quantity), 0) AS poorEvaluateQuantity, "
				+ " ROUND(COALESCE(SUM(coupon_amount), 0)) AS couponAmount ,"
				+ " ROUND(COALESCE(SUM(marketing_income), 0) *(1-?)-COALESCE(SUM(coupon_amount) ,0)  *?) AS marketingIncome"
				+ " FROM report.daily_merchant_summary AS dms WHERE dms.brand_id = ? AND trans_date >= ? AND trans_date < ?"
				+ " AND EXISTS(SELECT *  FROM trade.merchant AS mer1 WHERE status = 1 AND dms.merchant_id = mer1.merchant_id);";

		List<MonthMerchantSummary> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<MonthMerchantSummary>(
				MonthMerchantSummary.class), rate, rate, brandId, currentMonth, nextMonth);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	// 获取储值沉淀
	@Override
	public Map<String, Object> getStoreBalance(Integer brandId, Date date) {

		String sql = "select  ROUND(COALESCE(sum(store_balance), 0)) as store_balance from report.report_begining  as rb where report_date=? and rb.brand_id=? and type=1"
				+ " AND EXISTS(select * from trade.merchant as mer where mer.merchant_id=rb.merchant_id AND status = 1)";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, date, brandId);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	// 有消费的会员数
	@Override
	public Map<String, Object> getConsumeMemberNum(Integer brandId, Integer year, Integer month) {
		String sql = "select count(distinct membership_id) consumed_member_count,sum(trade_times) trade_times, sum(spending_amount) spending_amount "
				+ "from report.month_member_statistic "
				+ "where merchant_id in (select merchant_id from trade.merchant where brand_id = ? AND status = 1)"
				+ "and trade_times>0 " + "and s_year = ? " + "and s_month=?";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, brandId, year, month);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	// 会员总数
	public Map<String, Object> getMemberNum(Integer merchantId, String date) {
		String sql = "select membership_count from report.daily_merchant_accumulation where merchant_id=? and to_char(date,'yyyy-MM-dd')=?";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, merchantId, date);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public Map<String, Object> getMemberDiskNum(Integer brandId, String month, Integer indexId, BigDecimal proportion) {
		String sql = "SELECT COALESCE(sum(? * index_value),0) AS index_value FROM public.merchant_index_plan AS plan where index_id = ? AND month = ? AND plan.brand_id=?"
				+ " AND EXISTS(SELECT * FROM trade.merchant AS mer1 WHERE mer1.merchant_id = plan.merchant_id AND status = 1)";
		List<Map<String, Object>> list = jdbcTemplate210.queryForList(sql, proportion, indexId, month, brandId);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * @Title getDailyMerchantSummary
	 * @Description 查询商户日报信息
	 * @param brandId
	 * @param d
	 * @param rate
	 * @return
	 * @see com.yazuo.external.statictics.dao.GpStatisticsReportDao#getDailyMerchantSummary(java.lang.Integer, java.util.Date, java.math.BigDecimal)
	 */
	@Override
	public DailyMerchantSummary getDailyMerchantSummary(Integer brandId, Date d, BigDecimal rate) {

		String sql = " SELECT COALESCE(SUM(new_member), 0) AS newMember," + " COALESCE(SUM(fans_member), 0) AS fansMember,"
				+ " COALESCE(SUM(integral_member), 0) AS integralMember," + " COALESCE(SUM(store_member), 0) AS storeMember,"
				+ " COALESCE(SUM(realcard), 0) AS realcard," + " ROUND(COALESCE(SUM(card_income), 0)) AS cardIncome,"
				+ " ROUND(COALESCE(SUM(store_pay_cash) , 0)) AS storePay," + " ROUND(COALESCE(SUM(cash), 0)) AS cash ,"
				+ " ROUND(COALESCE(SUM(store_consume), 0)) AS storeConsume,"
				+ " ROUND(COALESCE(SUM(integral_consume), 0)) AS integralConsume,"
				+ " COALESCE(SUM(trans_quantity), 0) AS transQuantity,"
				+ " COALESCE(SUM(old_trans_quantity), 0) AS oldTransQuantity,"
				+ " COALESCE(SUM(evaluate_quantity), 0) AS evaluateQuantity,"
				+ " COALESCE(SUM(evaluate_score), 0) AS evaluateScore," + " COALESCE(SUM(evaluate_sms), 0) AS evaluateSms,"
				+ " COALESCE(SUM(well_evaluate_quantity), 0) AS wellEvaluateQuantity,"
				+ " COALESCE(SUM(middle_evaluate_quantity), 0) AS middleEvaluateQuantity,"
				+ " COALESCE(SUM(poor_evaluate_quantity), 0) AS poorEvaluateQuantity, "
				+ " ROUND(COALESCE(SUM(coupon_amount), 0)) AS couponAmount ,"
				+ " ROUND(COALESCE(SUM(marketing_income), 0) *(1-?)-COALESCE(SUM(coupon_amount) ,0)  *?) AS marketingIncome"
				+ " FROM report.daily_merchant_summary AS dms WHERE dms.brand_id = ? AND trans_date = ? "
				+ " AND EXISTS(SELECT *  FROM trade.merchant AS mer1 WHERE status = 1 AND dms.merchant_id = mer1.merchant_id);";

		List<DailyMerchantSummary> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<DailyMerchantSummary>(
				DailyMerchantSummary.class), rate, rate, brandId, d);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}
}
