package com.yazuo.weixin.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.vo.WxOrder;
import com.yazuo.weixin.vo.WxPayCard;
import com.yazuo.weixin.vo.WxPayCouponState;
import com.yazuo.weixin.vo.WxPayDeliverStateVo;


/**
* @ClassName WeixinPayDao
* @Description 
* @author sundongfeng@yazuo.com
* @date 2014-6-10 下午4:25:41
* @version 1.0
*/
@Repository
public class WeixinPayDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Log log = LogFactory.getLog("wxpay");
	
	/**
	 * 插入用户支付信息
	 * @param wxorder
	 * @return
	 */
	public int insertPayUserInfo(WxOrder wxorder)throws WeixinRuntimeException{
		String sql="insert into weixin.weixin_order ( "+
	      "mobile,app_id, partner,total_fee, time_begin,"+
	      "out_trade_no, nonce_tr, fee_type, "+
	      " trade_mode, sign_type,bank_type, product_info,card_id,counter,brand_id) values (?,?,?,?, CURRENT_TIMESTAMP,?,?,?,?,?,?,?,?,?,?)";
		int num = jdbcTemplate.update(sql,wxorder.getMobile(),
				wxorder.getAppId(),wxorder.getPartner(),wxorder.getTotal_fee(),
				wxorder.getOut_trade_no(),wxorder.getNonceTr(),
				wxorder.getFee_type(),wxorder.getTrade_mode(),
				wxorder.getSign_type(),wxorder.getBank_type(),
				wxorder.getProductInfo(),wxorder.getCardId(),
				wxorder.getCount(),wxorder.getBrandId());
		return num;
	}
	/**
	 * 更新支付结果，当tx通知第三方接口时
	 * @param wxorder
	 * @return
	 * @throws ParseException 
	 * @throws DataAccessException 
	 */
	public int updatePayResultInfo(WxOrder wxorder)throws WeixinRuntimeException{
		
		String sql =  "update weixin.weixin_order "+
			    "set open_id = ?,notify_id = ?,bank_billno =?,sign = ?,transaction_id = ?,product_fee = ?,   "+
				"transport_fee = ?,discount = ?,total_fee = ?,time_end =?,trade_state = ?,input_charset = ?, "+
				"pay_info = ?,is_subscribe = ?,app_signature = ? ,deliver_state=? "+
			    "where  app_id = ? and partner =? and out_trade_no =?  ";
		int num=0;
		try {
			num = jdbcTemplate.update(sql, wxorder.getOpenId(),wxorder.getNotify_id(),wxorder.getBank_billno(),
					wxorder.getSign(),wxorder.getTransaction_id(),wxorder.getProduct_fee(),
					wxorder.getTransport_fee(),wxorder.getDiscount(),wxorder.getTotal_fee(),sdf.parse(wxorder.getTime_end()),wxorder.getTrade_state(),
					wxorder.getInput_charset(),wxorder.getPayInfo(),wxorder.getIsSubscribe(),wxorder.getAppSignature(),wxorder.getDeliverState(),
					wxorder.getAppId(),wxorder.getPartner(),wxorder.getOut_trade_no());
		} catch (DataAccessException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		} catch (ParseException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 绑定券信息
	 * @param list
	 * @throws WeixinRuntimeException
	 */
	public void inserCardInfo(final List<WxPayCard> list)throws WeixinRuntimeException{
		String sql = "insert into weixin.weixin_pay_card_bind"+
				" ( mobile, open_id,                  "+
			    "  card_id, res_card_no, out_trade_no,  "+
			    "  create_time, partner_id, brand_id  "+
			    "  ) values (?,?,?,?,?,CURRENT_TIMESTAMP,?,?)         ";
		 jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				WxPayCard bean =  list.get(i);
				ps.setString(1,bean.getMobile());
				ps.setString(2,bean.getOpenId());
				ps.setString(3,bean.getCardId());
				ps.setString(4,bean.getRes_card_no());
				ps.setString(5,bean.getOutTradeNo());
				ps.setString(6,bean.getPartnerId());
				ps.setString(7,bean.getBrandId());
			}
			
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	/**
	 * 查询今天此订单是否有券了。
	 * @param outTradeNo
	 * @return
	 */
	public boolean queryHasTicketsCard(String outTradeNo){
		String sql = "select count(*) cn from weixin.weixin_pay_card_bind a where a.out_trade_no=? and a.create_time>CURRENT_DATE";
		int cn = jdbcTemplate.queryForInt(sql,outTradeNo);
		return cn>0?true:false;
	}
	/**
	 * 查询是否已经绑定券状态
	 * @param outTradeNo
	 * @return
	 */
	public boolean queryHasBindCoupon(String outTradeNo){
		String sql = "select count(*) cn from weixin.weixin_pay_coupon_state wp where wp.out_trade_no=?";
		int cn = jdbcTemplate.queryForInt(sql,outTradeNo);
		return cn>0?true:false;
	}
	/**
	 * 查询用户微信id
	 * @param mobile
	 * @param brandId
	 * @return
	 * @throws WeixinRuntimeException
	 */
	public String queryOpenId(String mobile,String brandId)throws WeixinRuntimeException{
		String sql = "select open_id from weixin.weixin_pay_card_bind where mobile=? and brand_id=? limit 1";
		String open_id = jdbcTemplate.queryForObject(sql, String.class, new Object[]{mobile,brandId});
		log.info("openId:"+open_id);
		return open_id;
	}
	
	/**
	 * 插入券状态表
	 * @param state
	 * @return
	 * @throws WeixinRuntimeException
	 */
	public int insertCouponState(WxPayCouponState state)throws WeixinRuntimeException{
		String sql =   " insert into weixin.weixin_pay_coupon_state ( mobile, total_fee,"+ 
					   "   counter, card_id, open_id,                            "+
					   "   trade_state, card_state, create_time,                 "+
					   "   transaction_id, out_trade_no, partner_id  )           "+
					   " values ( ?,?, ?, ?,?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?) ";
		int cn = jdbcTemplate.update(sql, state.getMobile(),state.getTotalFee(),state.getCounter(),
				state.getCardId(),state.getOpenId(),state.getTradeState(),state.getCardState(),
				state.getTransactionId(),state.getOutTradeNo(),state.getPartnerId()	);
		return cn;
	}
	/**
	 * 查询未发货
	 * @return
	 */
	public List<WxPayDeliverStateVo> queryDeliver(String brandId)throws WeixinRuntimeException{
		String sql ="select wo.mobile,wo.app_id,wo.transaction_id,wo.out_trade_no,wo.open_id "+
				"from weixin.weixin_order wo "+
				"where EXISTS (select 1 from weixin.weixin_pay_coupon_state wpcs   "+
				"where wo.out_trade_no = wpcs.out_trade_no    "+
				"and wo.mobile=wpcs.mobile   "+
				"and wpcs.trade_state='0'   "+
				"and wpcs.card_state='1' and wpcs.partner_id=?)   "+
				"and wo.trade_state='0'  "+
				"and wo.deliver_state='0'  ";
		
		List<WxPayDeliverStateVo> list = jdbcTemplate.query(sql, new Object[]{brandId}, new BeanPropertyRowMapper<WxPayDeliverStateVo>(WxPayDeliverStateVo.class));
		return list;
	}
	/**
	 * 更新发货状态
	 * @param outTradeNo
	 * @return
	 * @throws WeixinRuntimeException
	 */
	public int updateDeliverState(String outTradeNo)throws WeixinRuntimeException{
		String sql = "update weixin.weixin_order set deliver_state='1' where out_trade_no=?";
		int n = jdbcTemplate.update(sql, outTradeNo);
		return n;
	}
	
	/**
	 * 查询没有券但是发货的订单信息
	 * @param all
	 * @return
	 */
	public List<WxOrder> queryOrders(String brandId)throws WeixinRuntimeException{
		String sql = "select wo.mobile,wo.open_id openId,wo.card_id cardId,wo.out_trade_no,wo.transaction_id,wo.counter count from weixin.weixin_order wo "+
				"where not EXISTS(select 1 from weixin.weixin_pay_card_bind wpcb where wpcb.out_trade_no=wo.out_trade_no "+
				"and wpcb.mobile=wo.mobile) and wo.brand_id=?  and wo.trade_state='0' and wo.deliver_state='1' ";
		log.info("sql:"+sql+" brandid:"+brandId);
		List<WxOrder> list = jdbcTemplate.query(sql, new Object[]{brandId}, new BeanPropertyRowMapper<WxOrder>(WxOrder.class));
		return list;
	}
	
	/**
	 * 更新券状态，如果有券状态信息了
	 */
	public int updateWeixin_pay_coupon_state(WxPayCouponState state){
		String sql = "update weixin.weixin_pay_coupon_state set card_state=? where out_trade_no=?";
		log.info("更新券状态:"+sql+" card_state:"+state.getCardState()+" outTradeNo:"+state.getOutTradeNo());
		int update =  jdbcTemplate.update(sql, state.getCardState(),state.getOutTradeNo());
		return update;
	}
}
