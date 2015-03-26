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

/**
 * @ClassName: LotteryItemDao
 * @Description: 处理奖项DAO
 */
@Repository("lotteryItemCouponDao")
public class LotteryItemCouponDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private static final Log LOG = LogFactory.getLog("lottery");

	/**
	 * 
	 * <p>
	 * Description:根据奖项id查询对应批券次号
	 * </p>
	 * 
	 */
	public List<Map<String, Object>> getLotteryItemCoupon(int lottery_item_id) {
		String sql = "select coupon_batch_no as batchNo from public.weixin_lottery_item_coupon where lottery_item_id= "
				+ lottery_item_id + " order by id desc";
		LOG.info("getLotteryItemCoupon查询" + sql);
		return jdbcTemplate.queryForList(sql);
	}

}
