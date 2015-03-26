package com.yazuo.weixin.minimart.dao;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.minimart.vo.WxMallOrderCouponState;

/**
* @ClassName WxMallOrderDaoCoupon
* @Description 微信商城订单券dao
* @author sundongfeng@yazuo.com
* @date 2014-8-5 上午10:54:31
* @version 1.0
*/
@Repository
public class WxMallOrderCouponStateDao {
	private static final Log log = LogFactory.getLog("mall");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 查询是否已经绑定券状态
	 * @param outTradeNo
	 * @return
	 */
	public boolean queryHasBindCouponState(String outTradeNo){
		String sql = "select count(*) cn from public.weixin_mall_order_coupon_state wp where wp.out_trade_no=?";
		int cn = jdbcTemplate.queryForInt(sql,outTradeNo);
		return cn>0?true:false;
	}
	
	/**
	 * 插入券状态表
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int insertCouponState(WxMallOrderCouponState state)throws Exception{
		String sql =   " insert into public.weixin_mall_order_coupon_state ( mobile, total_fee,"+ 
					   "   counter, goods_id, open_id,                            "+
					   "   trade_state, send_state, create_time,                 "+
					   "   transaction_id, out_trade_no, brand_id ,integral )           "+
					   " values ( ?,?, ?, ?,?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?,?) ";
		int cn = jdbcTemplate.update(sql, state.getMobile(),state.getTotalFee(),state.getCounter(),
				state.getGoodsId(),state.getOpenId(),state.getTradeState(),state.getSendState(),
				state.getTransactionId(),state.getOutTradeNo(),state.getBrandId(),state.getIntegral());
		return cn;
	}
	/**
	 * 更新订单发券状态，如果有券状态信息了
	 * @param state
	 * @return
	 */
	public int updateWeixin_pay_coupon_state(WxMallOrderCouponState state)throws Exception{
		String sql = "update public.weixin_mall_order_coupon_state set send_state=? where out_trade_no=?";
		log.info("更新微信商城券状态:"+sql+" card_state:"+state.getSendState()+" outTradeNo:"+state.getOutTradeNo());
		int update =  jdbcTemplate.update(sql, state.getSendState(),state.getOutTradeNo());
		return update;
	}
	
	/**根据卡号更新该卡的绑定状态*/
	public int updateMallOrderStatus(String cardNo) {
		String sql = "update public.weixin_mall_order_card set card_status=1 where card_no=?";
		return jdbcTemplate.update(sql, cardNo);
	}
	/**根据卡号更新该卡的绑定状态*/
	public int insertMallOrderCard(String orderNo,String cardNo) {
		String sql = "insert into public.weixin_mall_order_card(out_trade_no,card_no,add_time,card_status) values(?,?,CURRENT_TIMESTAMP,1)";
		return jdbcTemplate.update(sql, orderNo,cardNo);
	}
	/**查询是否有卡了*/
	public int queryMallOrderCard(String outTradeNo) {
		String sql = "select count(*) cn from  public.weixin_mall_order_card where out_trade_no=?";
		return  jdbcTemplate.queryForInt(sql,outTradeNo);
	}
}
