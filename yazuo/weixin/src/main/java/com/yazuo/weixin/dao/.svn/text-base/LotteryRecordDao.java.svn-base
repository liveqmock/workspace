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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: LotteryRecordDao
 * @Description: 处理中奖记录DAO
 */
@Repository("lotteryRecordDao")
public class LotteryRecordDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private static final Log LOG = LogFactory.getLog("lottery");

	/**
	 * 
	 * <p>
	 * Description:根据时间，微信id和商户id查询抽了几次奖
	 * </p>
	 * 
	 */
	public int getLotteryRecord(String phoneNo, int brandId,
			String lottery_rule_id, String begintime, String endtime, int lotteryRecordType)
			throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from weixin.weixin_lottery_record where phone_no ='"
				+ phoneNo
				+ "' and brand_id="
				+ brandId
				+ " and lottery_rule_id=" + lottery_rule_id);

		if (!StringUtils.isBlank(begintime) && !StringUtils.isBlank(endtime)) {
			sql.append(" and addtime >= '" + begintime + "' and addtime < '"
					+ endtime + "' ");
		}
		if (lotteryRecordType !=0) {
			sql.append(" and lottery_record_type=").append(lotteryRecordType);
		}
		LOG.info("getLotteryRecord查询" + sql.toString());
		return jdbcTemplate.queryForInt(sql.toString());

	}

	/**
	 * <p>
	 * Description:保存中奖记录和相关表
	 * </p>
	 * 
	 * @param
	 * 
	 * 
	 * @return
	 */
	public boolean batchUpdateLottery(String[] sqls) {
		if (sqls==null || sqls.length ==0) {
			return true;
		}
		try {
			LOG.info("batchUpdateLottery保存" + sqls.toString());
			return jdbcTemplate.batchUpdate(sqls) != null ? true : false;
		} catch (Exception e) {
			LOG.error("batchUpdateLottery保存失败", e);
			return false;
		}

	}
	
	/**根据brand_id和weixin_id取中奖记录*/
	public List<Map<String, Object>> getLotteryRecordByBrandIdAndWeixinId (String weixinId, Integer brandId) {
		String sql = "select record.addtime,record.weixin_id,record.membership_id,record.brand_id,"+
					"rule.active_type, item.lottery_coupon_name,item.lottery_item_name,item.lottery_item_type"+
					", ord.detail_addr,ord.mobile,ord.receiver, ord.id orderId"+
					" from weixin.weixin_lottery_record record "+
					" INNER JOIN  public.weixin_lottery_rule rule  on (record.lottery_rule_id = rule.id)"+ 
					" INNER JOIN  public.weixin_lottery_item item on (item.id = record.lottery_item_id)"+
					" left JOIN  public.weixin_mall_order ord on (ord.goods_id = record.id and ord.open_id='"+weixinId+"' and ord.brand_id="+brandId+")"+				
					" where record.weixin_id='"+weixinId+"' and record.brand_id="+brandId+" and record.is_award=true order by record.addtime desc";
		return jdbcTemplate.queryForList(sql);
	}
	
	/**保存中奖记录*/
	public int insertRecord(String sql) {
		return jdbcTemplate.update(sql);
	}
	
	public Map<String, Object> getinsertRecordId(String sql) {
		return jdbcTemplate.queryForMap(sql);
	}
}
