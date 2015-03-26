/**
 * @Description TODO Copyright Copyright (c) 2014 Company 雅座在线（北京）科技发展有限公司 author date description
 *              —————————————————————————————————————————————
 */
package com.yazuo.external.statictics.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.external.account.dao.MerchantDao;
import com.yazuo.external.statictics.dao.GpStatisticsReportDao;
import com.yazuo.external.statictics.service.GpStatisticsReportService;
import com.yazuo.external.statictics.vo.DailyMerchantSummary;
import com.yazuo.external.statictics.vo.MonthMerchantSummary;
import com.yazuo.util.DateUtil;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-11 下午2:21:08
 */
@Service
public class GpStatisticsReportServiceImpl implements GpStatisticsReportService {

	@Resource
	private GpStatisticsReportDao gpStatisticsReportDao;

	@Resource
	private MerchantDao merchantDao;

	@Override
	public Map<String, Object> getMonthMerchantSummary(Integer brandId, String date) throws ParseException {

		Map<String, Object> mapMonSum = new HashMap<String, Object>();
		MonthMerchantSummary monthMerSum = getMonthSummary(brandId, date);
		mapMonSum.put("merchantMonthSum", monthMerSum);
		return mapMonSum;
	}

	private MonthMerchantSummary getMonthSummary(Integer brandId, String date) throws ParseException {
		MonthMerchantSummary monthMerSum = new MonthMerchantSummary();
		Map<String, Object> rateMap = gpStatisticsReportDao.getMarketingRate(brandId);
		BigDecimal rate = new BigDecimal(0.4);
		if (rateMap != null) {
			rate = (BigDecimal) rateMap.get("value");
			if (rate.compareTo(new BigDecimal(0)) == 0)
				rate = new BigDecimal(0.4);
		}

		String[] str = date.split("-");
		Integer year = Integer.parseInt(str[0]);
		Integer month = Integer.parseInt(str[1]);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse(date + "-01");// 查询月第一天
		Date nextMonth = DateUtil.getNextMonth(d);// 下月第一天
		Date today = new Date();// 今天

		monthMerSum = gpStatisticsReportDao.getMonthMerchantSummary(brandId, d, nextMonth, rate);
		Integer oldTransQuantity = monthMerSum.getOldTransQuantity();
		Integer integralMember = monthMerSum.getIntegralMember();

		// 开台数目标值，若date为当前月则取上个月的目标值
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		String todayYearMon = simpleDateFormat.format(today);
		BigDecimal proportion = new BigDecimal(1);
		String monString = date;
		if (StringUtils.equals(todayYearMon, simpleDateFormat.format(d))) {
			int day = DateUtil.getDay(today);// 当前天数
			int daysOfMonth = DateUtil.getDaysOfMonth(today);// 本月总天数
			// 本月当前天数占比
			proportion = new BigDecimal(day).divide(new BigDecimal(daysOfMonth), 2, BigDecimal.ROUND_HALF_UP);
			Date previousDay = DateUtil.getPreviousMonth(today);
			String previousMonth = simpleDateFormat.format(previousDay);// 上月
			monString = previousMonth;
		}
		Map<String, Object> memberDiskNumMap = gpStatisticsReportDao.getMemberDiskNum(brandId,
				simpleDateFormat.format(simpleDateFormat.parse(monString)), 11, proportion);// 开台数
		if (null != memberDiskNumMap && null != memberDiskNumMap.get("index_value")) {
			BigDecimal index_value = (BigDecimal) memberDiskNumMap.get("index_value");
			BigDecimal subtract = index_value.subtract(new BigDecimal(oldTransQuantity));
			if (subtract.compareTo(new BigDecimal(0)) > 0) {
				BigDecimal divide = new BigDecimal(integralMember).divide(subtract, 2, BigDecimal.ROUND_HALF_UP);
				monthMerSum.setIntegralMemberRate(divide.multiply(new BigDecimal(100)));
			} else {
				monthMerSum.setIntegralMemberRate(new BigDecimal(0));
			}
		} else {
			monthMerSum.setIntegralMemberRate(new BigDecimal(0));
		}

		// 会员评价率计算，会员评价率=会员评价数/评价短信下发数
		Integer evaluateQuantity = monthMerSum.getEvaluateQuantity();// 会员评价数
		Integer evaluateSms = monthMerSum.getEvaluateSms();// 会员评价短信下发数
		if (evaluateSms != null && evaluateQuantity != null && evaluateSms.intValue() != 0) {
			monthMerSum.setEvaluateRate(getBigDec(evaluateQuantity, 0).divide(getBigDec(evaluateSms, 1), 4,
					BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)));// 会员评价率
		} else {
			monthMerSum.setEvaluateRate(new BigDecimal(0));
		}

		// 会员满意度计算 会员满意度=会员评价平均分/5*100
		Integer evaluateScore = monthMerSum.getEvaluateScore();// 会员评价总分
		if (evaluateScore != null && evaluateQuantity != null && evaluateQuantity.intValue() != 0) {
			monthMerSum.setEvaluateWellRate(((getBigDec(evaluateScore, 0).divide(getBigDec(evaluateQuantity, 1), 4,
					BigDecimal.ROUND_HALF_EVEN)).divide(new BigDecimal(5), 4, BigDecimal.ROUND_HALF_EVEN)).multiply(
					new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		} else {
			monthMerSum.setEvaluateWellRate(new BigDecimal(0));
		}

		// 获取年度累计 年度会员数，年度累计营销收益，年度累计售卡收益
		// 比如需要查询2014-07的年度累计，则需要累计2014-01到2014-07的数据
		int currYear = DateUtil.getYear(d);
		Date currentYear = sdf.parse(String.valueOf(currYear) + "-01-01");
		MonthMerchantSummary yearMerSum = gpStatisticsReportDao.getMonthMerchantSummary(brandId, currentYear, nextMonth, rate);
		monthMerSum.setYearCardInconsume(yearMerSum.getCardIncome());// 年度售卡收益
		monthMerSum.setYearMember(yearMerSum.getNewMember());// 年度会员数
		monthMerSum.setYearMarketingConsume(yearMerSum.getMarketingIncome());// 年度营销收益

		// 储值沉淀，需要到另外一张表中获取值
		// 当月的储值沉淀，取前一天的沉淀，当月第一天取上月的储值沉淀时，取上月最后一天的沉淀
		Date newDate = null;
		int currentDay = DateUtil.getDay(today);// 当前天数
		Date preMonth = DateUtil.getPreviousMonth(today);
		String pMonth = simpleDateFormat.format(preMonth);// 上月

		if (StringUtils.equals(todayYearMon, simpleDateFormat.format(d))) {
			newDate = DateUtil.getPreviousDay(today);
		} else if (StringUtils.equals(pMonth, simpleDateFormat.format(d))) {
			if (1 == currentDay) {
				newDate = DateUtil.getPreviousDay(today);
			} else {
				newDate = nextMonth;
			}
		} else {
			newDate = nextMonth;
		}

		// Map<String, Object> m =
		// gpStatisticsReportDao.getStoreBalance(brandId, nextMonth);
		Map<String, Object> m = gpStatisticsReportDao.getStoreBalance(brandId, newDate);
		BigDecimal storeBalance = new BigDecimal(0);
		if (m != null)
			storeBalance = (BigDecimal) m.get("store_balance");
		monthMerSum.setStoreBalance(storeBalance);// 储值沉淀

		// 计算得到会员总消费
		BigDecimal cash = monthMerSum.getCash();// 现金消费
		BigDecimal storeConsume = monthMerSum.getStoreConsume();// 储值消费
		BigDecimal integralConsume = monthMerSum.getIntegralConsume();// 积分消费
		BigDecimal totalConsume = new BigDecimal(0);
		totalConsume = totalConsume.add(getBigDecByBig(cash)).add(getBigDecByBig(storeConsume))
				.add(getBigDecByBig(integralConsume));
		monthMerSum.setTotalConsume(totalConsume);// 会员总消费放入集合中

		// 会员桌均消费 会员总消费/会员交易量
		Integer transQuantity = monthMerSum.getTransQuantity();// 会员交易量
		if (totalConsume != null && transQuantity != null && transQuantity.intValue() != 0) {
			monthMerSum.setConsumedAvg(totalConsume.divide(getBigDec(transQuantity, 1), 2, BigDecimal.ROUND_HALF_EVEN));// 会员桌均消费
		} else {
			monthMerSum.setConsumedAvg(new BigDecimal(0));// 会员桌均消费
		}

		// 会员活跃度 会员活跃度=有消费的会员数/总会员数
		Map<String, Object> cmn = gpStatisticsReportDao.getConsumeMemberNum(brandId, year, month);
		Long consumeMemberNum = new Long(0);
		Integer memberNum = 0;

		if (cmn != null) {
			consumeMemberNum = (Long) cmn.get("consumed_member_count");// 有消费的会员数

			int days = DateUtil.getDaysOfMonth(d);// 获取该月份共有多少天
			String monthDateEnd = date + "-" + days;// 拼出该月份最后一天的日期

			Map<String, Object> getMemberNum = null;

			getMemberNum = gpStatisticsReportDao.getMemberNum(brandId, monthDateEnd);// 总会员数
			if (getMemberNum != null) {
				memberNum = (Integer) getMemberNum.get("membership_count");
			}
			// 开始计算
			if (consumeMemberNum != null && memberNum != null && memberNum.intValue() != 0) {
				monthMerSum.setConsumedRate(new BigDecimal(consumeMemberNum).divide(new BigDecimal(memberNum), 4,
						BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)));
			} else {
				monthMerSum.setConsumedRate(new BigDecimal(0));
			}
		}
		return monthMerSum;
	}

	private BigDecimal getBigDec(Integer number, Integer type) {
		// number 需要转换的值，type 需要返回的类型，比如正常值如果满足要求返回数字 如果不满足返回0
		// 如果是用来作为除数则需要type=1
		BigDecimal big = new BigDecimal(type);
		if (number == null || number == 0) {
			big = new BigDecimal(type);
		} else {
			big = new BigDecimal(number);
		}
		return big;
	}

	private BigDecimal getBigDecByBig(BigDecimal number) {
		// number 需要转换的值，type 需要返回的类型，比如正常值如果满足要求返回数字 如果不满足返回0
		// 如果是用来作为除数则需要type=1
		BigDecimal big = new BigDecimal(0);
		if (number != null) {
			big = number;
		}
		return big;
	}

	/**
	 * @Title getDailyMerchantSummary
	 * @Description 查询商户日报信息
	 * @param brandId
	 * @param date
	 * @return
	 * @see com.yazuo.external.statictics.service.GpStatisticsReportService#getDailyMerchantSummary(java.lang.Integer,
	 *      java.lang.String)
	 */
	@Override
	public DailyMerchantSummary getDailyMerchantSummary(Map<String, Object> paramMap) throws ParseException {
		Integer brandId = (Integer) paramMap.get("merchantId");
		Object date = paramMap.get("date");

		// 校验入参不为空
		this.checkObjParam(brandId, "商户ID");
		this.checkObjParam(date, "日期");

		DailyMerchantSummary dailyMerSum = new DailyMerchantSummary();
		Map<String, Object> rateMap = gpStatisticsReportDao.getMarketingRate(brandId);
		BigDecimal rate = new BigDecimal(0.4);
		if (rateMap != null) {
			rate = (BigDecimal) rateMap.get("value");
			if (rate.compareTo(new BigDecimal(0)) == 0)
				rate = new BigDecimal(0.4);
		}

		Date d = DateUtil.toDateFromMillisecond(date);
		dailyMerSum = gpStatisticsReportDao.getDailyMerchantSummary(brandId, d, rate);

		// 计算得到会员总消费
		BigDecimal cash = dailyMerSum.getCash();// 现金消费
		BigDecimal storeConsume = dailyMerSum.getStoreConsume();// 储值消费
		BigDecimal integralConsume = dailyMerSum.getIntegralConsume();// 积分消费
		BigDecimal totalConsume = new BigDecimal(0);
		totalConsume = totalConsume.add(getBigDecByBig(cash)).add(getBigDecByBig(storeConsume))
				.add(getBigDecByBig(integralConsume));
		dailyMerSum.setTotalConsume(totalConsume);// 会员总消费放入集合中

		return dailyMerSum;
	}

	/**
	 * @Description 校验参数不能为空
	 * @param obj
	 * @param name
	 * @throws FesBizException
	 * @throws
	 */
	private void checkObjParam(Object obj, String name) throws FesBizException {
		if (null == obj) {
			throw new FesBizException("[" + name + "]" + "不能为空");
		}
	}

}
