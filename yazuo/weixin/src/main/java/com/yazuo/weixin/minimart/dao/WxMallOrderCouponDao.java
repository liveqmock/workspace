package com.yazuo.weixin.minimart.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.minimart.vo.WxMallOrderCoupon;

/**
* @ClassName WxMallOrderDaoCoupon
* @Description 微信商城订单券dao
* @author sundongfeng@yazuo.com
* @date 2014-8-5 上午10:54:31
* @version 1.0
*/
@Repository
public class WxMallOrderCouponDao {
	private static final Log log = LogFactory.getLog("mall");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 绑定券信息
	 * @param list
	 * @throws WeixinRuntimeException
	 */
	public void inserMallCouponInfo(final List<WxMallOrderCoupon> list)throws Exception{
		String sql = "insert into public.weixin_mall_order_coupon"+
				" ( mobile, open_id,  goods_id, coupon_id, out_trade_no, create_time, brand_id  "+
			    "  ) values (?,?,?,?,?,CURRENT_TIMESTAMP,?) ";
		int[] updatenums =   jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				WxMallOrderCoupon bean =  list.get(i);
				ps.setString(1,bean.getMobile());
				ps.setString(2,bean.getOpenId());
				ps.setLong(3,bean.getGoodsId());
				ps.setLong(4,bean.getCouponId());
				ps.setString(5,bean.getOutTradeNo());
				ps.setLong(6,bean.getBrandId());
			}
			
			public int getBatchSize() {
				return list.size();
			}
		});
		log.info("微信商城绑定券数组:"+Arrays.toString(updatenums));
	}
	/**
	 * 查询今天此订单是否有券了。
	 * @param outTradeNo
	 * @return
	 */
	public boolean queryHasOrderCoupon(String outTradeNo){
		String sql = "select count(*) cn from public.weixin_mall_order_coupon a where a.out_trade_no=? and a.create_time>CURRENT_DATE";
		int cn = jdbcTemplate.queryForInt(sql,outTradeNo);
		return cn>0?true:false;
	}
	
	public List<Map<String,Object>> queryRemainOrderCoupon(String outTradeNo,Integer brandId){
		String sql = "select coupon_id from public.weixin_mall_order_coupon where out_trade_no=? and brand_id=?";
		return jdbcTemplate.queryForList(sql, outTradeNo, brandId);
//		String sql = "select vc.id from v_coupon vc inner join weixin_mall_order_coupon  wmoc " +
//				"  on vc.id=wmoc.coupon_id where wmoc.out_trade_no=? and wmoc.brand_id=?  and vc.available_quantity<>0";
//		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, outTradeNo,brandId);
//		return list;
	}
	
}
