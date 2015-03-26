/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2013-11-25
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.util.DateUtil;

/**
 * @ClassName: CouponDescribeDao
 * @Description: 处理中奖规则DAO
 */
@Repository("lotteryRuleDao")
public class LotteryRuleDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private static final Log LOG = LogFactory.getLog("lottery");

	/**
	 * <p>
	 * Description:根据厂家id和类型查询合法的抽奖规则
	 * </p>
	 * 
	 * @param brandId
	 * 
	 * 
	 * @return Map
	 */
	public Map<String, Object> getLotteryRule(int brandId, int type)
			throws Exception {
		// 当天日期
		String day = DateUtil.getDate4d();// yyyy-MM-dd

		// String sql =
		// "select id,time_unit,frequency,lottery_number,residue_number,type,title,title_pic,to_char(begin_time,'yyyy-MM-dd') as begin_time,to_char(end_time,'yyyy-MM-dd') as end_time from public.weixin_lottery_rule where brand_id="
		// + brandId
		// + " and type="
		// + type
		// +
		// " and flag = 1 and begin_time<=now() and end_time>=now() order by id limit 1";
		String sql = "select id,time_unit,frequency,lottery_number,residue_number,type,title,title_pic,to_char(begin_time,'yyyy-MM-dd') as begin_time,to_char(end_time,'yyyy-MM-dd') as end_time from public.weixin_lottery_rule where brand_id="
				+ brandId
				+ " and type="
				+ type
				+ " and flag = 1 and begin_time<='"
				+ day
				+ "' and end_time>='"
				+ day + "' order by id limit 1";

		LOG.info("getLotteryRule查询" + sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	
	public List<Map<String, Object>> getCardLotterRule (Integer brandId, Integer activtiyType) {
		String sql = "select id,time_unit,frequency,lottery_number,residue_number,type,title,title_pic,to_char(begin_time,'yyyy-MM-dd') begin_time,to_char(end_time,'yyyy-MM-dd') end_time,monitor_number,is_send_sms, brand_id" +
				" from public.weixin_lottery_rule where brand_id=" + brandId + " and active_type=" + activtiyType +" and flag=1 order by id desc";
		return jdbcTemplate.queryForList(sql);
	}
	
	public int updateRuleSmsStatus(Integer id) {
		String sql = "update public.weixin_lottery_rule set is_send_sms=1 where id=" + id;
		return jdbcTemplate.update(sql);
	}
	
	public List<Map<String, Object>> getMonitorMsgByRuleId (Integer ruleId) {
		String sql = "select * from public.weixin_lottery_monitor_receiver where lottery_rule_id=" + ruleId;
		return jdbcTemplate.queryForList(sql);
	}
	
	/**查询某个商户下同时在进行的活动*/
	public List<Map<String, Object>> getLotteryRuleOfBrand(int brandId, int activeType) {
		String day = DateUtil.getDate4d();// yyyy-MM-dd
		String sql = "select id,time_unit,frequency,lottery_number,residue_number,type,title,title_pic,to_char(begin_time,'yyyy-MM-dd') as begin_time,to_char(end_time,'yyyy-MM-dd') as end_time from public.weixin_lottery_rule where brand_id=?"
				+" and active_type=?"
				+ " and flag = 1 and begin_time<='" + day
				+ "' and end_time>='" + day + "' order by id";
		LOG.info("getLotteryRule查询" + sql);
		return jdbcTemplate.queryForList(sql, brandId, activeType);
	}
}
