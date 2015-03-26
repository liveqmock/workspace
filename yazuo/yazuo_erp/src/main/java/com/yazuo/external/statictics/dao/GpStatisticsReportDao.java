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
package com.yazuo.external.statictics.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.yazuo.external.statictics.vo.DailyMerchantSummary;
import com.yazuo.external.statictics.vo.MonthMerchantSummary;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-11 下午2:24:38
 */
public interface GpStatisticsReportDao {
	public Map<String, Object> getMarketingRate(Integer brandId);

	public MonthMerchantSummary getMonthMerchantSummary(Integer brandId, Date currentMonth, Date nextMonth, BigDecimal rate);

	public Map<String, Object> getStoreBalance(Integer brandId, Date date);

	// 有消费的会员数
	public Map<String, Object> getConsumeMemberNum(Integer brandId, Integer year, Integer month);

	// 会员总数
	public Map<String, Object> getMemberNum(Integer merchantId, String date);

	public Map<String, Object> getMemberDiskNum(Integer brandId, String date, Integer indexId, BigDecimal proportion);

	/**
	 * @Description 查询商户日报信息
	 * @param brandId
	 * @param d
	 * @param rate
	 * @return
	 * @return DailyMerchantSummary
	 * @throws
	 */
	public DailyMerchantSummary getDailyMerchantSummary(Integer brandId, Date d, BigDecimal rate);
}
