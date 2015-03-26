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
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.vo.CouponDescribeVO;

/**
 * @ClassName: LotteryItemDao
 * @Description: 处理奖项DAO
 */
@Repository("lotteryItemDao")
public class LotteryItemDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private static final Log LOG = LogFactory.getLog("lottery");

	/**
	 * 
	 * <p>
	 * Description:根据抽奖规则的id查询出对应的奖品列表（必须是按等级升序排列）
	 * </p>
	 * 
	 */
	public List<Map<String, Object>> getLotteryItem(String lotteryRuleId)
			throws Exception {
		String sql = "select it.* from public.weixin_lottery_rule_item ru, public.weixin_lottery_item it "
				+ " where ru.lottery_item_id = it.id and ru.lottery_rule_id="
				+ lotteryRuleId + " order by it.lottery_item_seq";

		LOG.info("getLotteryItem查询" + sql);
		return jdbcTemplate.queryForList(sql);

	}

}
