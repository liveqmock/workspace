package com.yazuo.weixin.minimart.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.minimart.vo.WxMallOrder;
import com.yazuo.weixin.vo.WxPayDeliverStateVo;

/**
* @ClassName WxMallOrderDao
* @Description 微信商城订单dao
* @author sundongfeng@yazuo.com
* @date 2014-8-5 上午10:53:28
* @version 1.0
*/
@Repository
public class WxMallOrderDao {
	private static final Log log = LogFactory.getLog("mall");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 微商城订单入库
	 * @param wxorder
	 * @return
	 */
	public int insertMallOrder(WxMallOrder wxorder){
		String sql="insert into public.weixin_mall_order ( "+
			      "mobile,app_id, partner,integral,total_fee, time_begin,"+
			      "out_trade_no, nonce_tr, fee_type,pay_type, "+
			      "trade_mode, sign_type,bank_type, product_info," +
			      "goods_id,buy_num,brand_id,source,card_no,password," +
			      "first_addr,second_addr,third_addr,detail_addr,receiver," +
			      "zipcode,product_type,open_id,jifen_state,master_id,order_state) " +
			      "values (?,?,?,?,?, CURRENT_TIMESTAMP,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if(wxorder.getProductType()==null){wxorder.setProductType(1);}//
		
		int num = jdbcTemplate.update(sql,wxorder.getMobile(),
				wxorder.getAppId(),wxorder.getPartner(),wxorder.getIntegral(),wxorder.getTotal_fee(),
				wxorder.getOut_trade_no(),wxorder.getNonceTr(),
				wxorder.getFee_type(),wxorder.getPayType(),wxorder.getTrade_mode(),
				wxorder.getSign_type(),wxorder.getBank_type(),
				wxorder.getProductInfo(),wxorder.getGoodsId(),
				wxorder.getBuyNum(),wxorder.getBrandId(),wxorder.getSource(),
				wxorder.getCardNo(),wxorder.getPassword(),
				wxorder.getFirstAddr(),wxorder.getSecondAddr(),wxorder.getThirdAddr(),
				wxorder.getDetailAddr(),wxorder.getReceiver(),wxorder.getZipcode(),
				wxorder.getProductType(),wxorder.getOpenId(),wxorder.getJifenState(),wxorder.getMasterId(),wxorder.getOrderState());
		return num;
	}
	/**
	 *  查询成功支付的订单 
	 * @param outTradeNo
	 * @return
	 */
	public WxMallOrder queryIntegralMallOrder(String outTradeNo,Integer brandId){
		String sql = "select * from public.weixin_mall_order  where trade_state='0' and brand_id="+brandId+" and out_trade_no ='"+outTradeNo+"'";
		log.info("查询成功支付的订单sql:"+sql	);
		try {
			WxMallOrder order = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<WxMallOrder>(WxMallOrder.class));
			return order;
		} catch (Exception e) {
			log.error("code happen error.",e);
			return null;
		}
	}
	/**
	 *  查询 订单 信息
	 * @param outTradeNo
	 * @return
	 */
	public WxMallOrder queryMallOrder(String outTradeNo,Integer brandId){
		String sql = "select * from public.weixin_mall_order  where  brand_id="+brandId+" and out_trade_no ='"+outTradeNo+"'";
		log.info("查询订单信息sql:"+sql	);
		try {
			WxMallOrder order = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<WxMallOrder>(WxMallOrder.class));
			return order;
		} catch (Exception e) {
			log.error("code happen error.",e);
			return null;
		}
	}
	
	/**
	 * 更新是否销掉积分状态
	 * @param outTradeNo
	 * @return
	 * @throws Exception
	 */
	public int updateJifenState(WxMallOrder wxorder)throws Exception{
		String sql = "update public.weixin_mall_order set trade_state=?,order_state=?,card_no=?,password=? " ;
				if(wxorder.getJifenState()!=null){
					sql+=",jifen_state=?,master_id=?";
				}
				sql+=" where out_trade_no=?";
				int n=0;
				if(wxorder.getJifenState()!=null){
					 n= jdbcTemplate.update(sql, wxorder.getTrade_state(),
							wxorder.getOrderState(),wxorder.getCardNo(),wxorder.getPassword(),
							wxorder.getJifenState(),wxorder.getMasterId(),
							wxorder.getOut_trade_no());
				}else{
					n= jdbcTemplate.update(sql, wxorder.getTrade_state(),
							wxorder.getOrderState(),wxorder.getCardNo(),wxorder.getPassword(),
							wxorder.getOut_trade_no());
				}
		return n;
	}
	
	/**
	 * 纯积分消费插入方法
	 * @param wxorder
	 * @return
	 */
	public int insertIntegralMallOrder(WxMallOrder wxorder)throws Exception{
		String sql="insert into public.weixin_mall_order ( "+
				"mobile,open_id,app_id, partner,integral,total_fee, " +
				"time_begin,time_end,trade_state,order_state,"+
				"out_trade_no, nonce_tr, fee_type,pay_type, "+
				"trade_mode, sign_type,bank_type, product_info," +
				"goods_id,buy_num,brand_id,source,deliver_state,jifen_state,master_id,card_no,password) " +
				"values (?,?,?,?,?,?, CURRENT_TIMESTAMP,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int num = jdbcTemplate.update(sql,wxorder.getMobile(),wxorder.getOpenId(),
				wxorder.getAppId(),wxorder.getPartner(),wxorder.getIntegral(),wxorder.getTotal_fee(),
				sdf.parse(wxorder.getTime_end()),wxorder.getTrade_state(),wxorder.getOrderState(),
				wxorder.getOut_trade_no(),wxorder.getNonceTr(),
				wxorder.getFee_type(),wxorder.getPayType(),
				wxorder.getTrade_mode(),
				wxorder.getSign_type(),wxorder.getBank_type(),
				wxorder.getProductInfo(),wxorder.getGoodsId(),
				wxorder.getBuyNum(),wxorder.getBrandId(),
				wxorder.getSource(),wxorder.getDeliverState(),
				wxorder.getJifenState(),wxorder.getMasterId(),wxorder.getCardNo(),wxorder.getPassword());
		return num;
	}
	
	/**
	 * 更新支付结果，当tx通知第三方接口时
	 * @param wxorder
	 * @return
	 * @throws ParseException 
	 * @throws DataAccessException 
	 */
	public int updatePayResultInfo(WxMallOrder wxorder) throws Exception{
		log.info(""+wxorder.getJifenState()+wxorder.getMasterId());
		String sql =  "update public.weixin_mall_order "+
			    "set open_id = ?,notify_id = ?,bank_billno =?,sign = ?,transaction_id = ?,product_fee = ?, "+
				"transport_fee = ?,discount = ?,time_end =?,trade_state = ?,input_charset = ?,"+
				"pay_info = ?,is_subscribe = ?,app_signature = ? ,deliver_state=? ,order_state=?" ;
				if(wxorder.getJifenState()!=null){
					sql+=",jifen_state=?,master_id=?";
				}
				sql+=" where out_trade_no=?";
		int num=0;
		if(wxorder.getJifenState()!=null){
			num = jdbcTemplate.update(sql, wxorder.getOpenId(),wxorder.getNotify_id(),wxorder.getBank_billno(),
					wxorder.getSign(),wxorder.getTransaction_id(),wxorder.getProduct_fee(),
					wxorder.getTransport_fee(),wxorder.getDiscount(),
					sdf.parse(wxorder.getTime_end()),wxorder.getTrade_state(),
					wxorder.getInput_charset(),wxorder.getPayInfo(),
					wxorder.getIsSubscribe(),wxorder.getAppSignature(),
					wxorder.getDeliverState(),wxorder.getOrderState(),
					wxorder.getJifenState(),wxorder.getMasterId(),
					wxorder.getOut_trade_no());
		}else{
			num = jdbcTemplate.update(sql, wxorder.getOpenId(),wxorder.getNotify_id(),wxorder.getBank_billno(),
					wxorder.getSign(),wxorder.getTransaction_id(),wxorder.getProduct_fee(),
					wxorder.getTransport_fee(),wxorder.getDiscount(),
					sdf.parse(wxorder.getTime_end()),wxorder.getTrade_state(),
					wxorder.getInput_charset(),wxorder.getPayInfo(),
					wxorder.getIsSubscribe(),wxorder.getAppSignature(),
					wxorder.getDeliverState(),wxorder.getOrderState(),
					wxorder.getOut_trade_no());
		}
		return num;
	}
	/**
	 * 查询未发货订单
	 * @param brandId
	 * @return
	 * @throws WeixinRuntimeException
	 */
	public List<WxPayDeliverStateVo> queryNoDeliver(String brandId)throws Exception{
		String sql ="select wo.mobile,wo.app_id,wo.transaction_id,wo.out_trade_no,wo.open_id "+
				"from public.weixin_mall_order wo "+
				"where EXISTS (select 1 from public.weixin_mall_order_coupon_state wpcs   "+
				"where wo.out_trade_no = wpcs.out_trade_no    "+
				"and wo.mobile=wpcs.mobile   "+
				"and wpcs.trade_state='0'   "+
				"and wpcs.send_state='t' and wpcs.brand_id=?)   "+
				"and wo.trade_state='0'  "+
				"and wo.deliver_state=0  ";
		
		List<WxPayDeliverStateVo> list = jdbcTemplate.query(sql, new Object[]{brandId}, new BeanPropertyRowMapper<WxPayDeliverStateVo>(WxPayDeliverStateVo.class));
		return list;
	}
	/**
	 * 更新发货状态
	 * @param outTradeNo
	 * @return
	 * @throws Exception
	 */
	public int updateDeliverState(String outTradeNo)throws Exception{
		String sql = "update public.weixin_mall_order set deliver_state=1 where out_trade_no=?";
		int n = jdbcTemplate.update(sql, outTradeNo);
		return n;
	}
	/**
	 * 更新退款原因字段
	 * @param outTradeNo
	 * @return
	 * @throws Exception
	 */
	public int updateDrawBackDesc(String desc,String outTradeNo)throws Exception{
		String sql = "update public.weixin_mall_order set drawback_desc=?,order_state=4 where out_trade_no=?";
		log.info("更新退款原因sql:"+sql+" desc:"+desc+" orderno:"+outTradeNo);
		int n = jdbcTemplate.update(sql,desc, outTradeNo);
		return n;
	}
	
	/**
	 * 查询没有券但是发货的订单信息
	 * @param brandid
	 * @return
	 */
	public List<WxMallOrder> queryNoCouponOrders(String brandId)throws Exception{
		String sql = "select wo.mobile,wo.open_id ,wo.goods_id ,wo.out_trade_no ,wo.transaction_id ,wo.buy_num  from public.weixin_mall_order wo "+
				"where not EXISTS(select 1 from public.weixin_mall_order_coupon wpcb where wpcb.out_trade_no=wo.out_trade_no "+
				"and wpcb.mobile=wo.mobile) and wo.brand_id=?  and wo.trade_state='0' and wo.deliver_state=1 ";
		log.info("sql:"+sql+" brandid:"+brandId);
		List<WxMallOrder> list = jdbcTemplate.query(sql, new Object[]{brandId}, new BeanPropertyRowMapper<WxMallOrder>(WxMallOrder.class));
		return list;
	}
	/**
	 * 查询用户支付订单
	 * @param brandId
	 * @param openId
	 * @return
	 * @throws WeixinRuntimeException
	 */
	public List<Map<String,Object>> queryPaySuccessOrders( String openId,Integer brandId,Integer source)throws Exception{
		String sql = " select wmo.*,dwmo.name order_state_name,wmg.rules," +
				" wmg.expire_type,wmg.expire_begin,wmg.expire_end,wmg.expire_days,wmg.expire_days_begin," +
				" wmg.original_price,wmg.original_price_show,wmg.now_price_cash," +
				" wmg.type_id,dwmoa.name order_card_name,wmg.picurl,wmo.product_type " +
				" from public.weixin_mall_order  wmo" +
				" left join public.weixin_mall_goods  wmg on wmo.goods_id = wmg.id" +
				" left join public.dict_weixin_mall_orders_status dwmo on wmo.order_state=dwmo.id" +
				" left join public.dict_weixin_mall_card_status dwmoa on wmo.order_state=dwmoa.id" +
				" where (wmo.trade_state='0' or wmo.jifen_state=true) and wmo.source="+source+" and wmo.brand_id="+brandId +
				" and wmo.open_id='"+openId+"' order by wmo.time_end desc";
		log.info("查询用户支付订单sql:"+sql);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 查询用户订单详情，数据来源为6的是微信商城订单
	 * @param brandId
	 * @param openId
	 * @return
	 * @throws WeixinRuntimeException
	 */
	public Map<String,Object> queryPaySuccessSingleOrder( String outTradeNo,Integer brandId)throws Exception{
		String sql = " select wmo.*,dwmo.name order_state_name,wmg.rules, " +
				" wmg.expire_type,wmg.expire_begin,wmg.expire_end,wmg.expire_days,wmg.expire_days_begin," +
				" wmg.original_price,wmg.picurl " +
				" from public.weixin_mall_order  wmo" +
				" left join public.weixin_mall_goods  wmg on wmo.goods_id = wmg.id" +
				" left join public.dict_weixin_mall_orders_status dwmo on wmo.order_state=dwmo.id" +
				" where wmo.source=6 and wmo.brand_id="+brandId +
				" and wmo.out_trade_no='"+outTradeNo+"'";
		log.info("sql:"+sql);
		Map<String,Object> obj = jdbcTemplate.queryForMap(sql);
		return obj;
	}
	/**
	 * 查询用户购买商品个数
	 * @param weixinId
	 * @param brandId
	 * @param flag 是否查今天
	 * @param goodId
	 * @return
	 */
	public int queryUserBuyCount(String weixinId,Integer brandId,boolean flag,Integer goodId){
		String sql = "select sum(buy_num) from public.weixin_mall_order where goods_id="+goodId+" and brand_id="+brandId+
				" and open_id='"+weixinId+"' and trade_state='0' ";
		if(flag){
			sql+=" and time_end>=CURRENT_DATE";
		}
		log.info("查询用户购买商品个数sql:"+sql);
		int num=0;
		try {
			num = jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			num=0;
			log.error("code happen error.",e);
		}
		return num;
	}
	public WxMallOrder getMallOrderById(Integer id) {
		String sql = "select * from public.weixin_mall_order where id=?";
		List<WxMallOrder> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(WxMallOrder.class), id);
		if (list !=null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public int updateMallOrderAddress(WxMallOrder order) {
		String sql =  "update public.weixin_mall_order "+
			    "set  first_addr=?,second_addr=?,third_addr=?,detail_addr=?,receiver=?,mobile=?,product_info=?,time_begin=CURRENT_TIMESTAMP,time_end=CURRENT_TIMESTAMP"+
			    " where id =? ";
		int num = jdbcTemplate.update(sql, order.getFirstAddr(),order.getSecondAddr(),order.getThirdAddr(),order.getDetailAddr()
				,order.getReceiver(), order.getMobile(),order.getProductInfo(),order.getId());
		return num;
	}
	
	public int insertMallOrderLuckUse(WxMallOrder wxorder){
		String sql="insert into public.weixin_mall_order ( "+
			      "id,mobile,app_id, partner,integral,total_fee, time_begin,"+
			      "out_trade_no, nonce_tr, fee_type,pay_type, "+
			      "trade_mode, sign_type,bank_type, product_info," +
			      "goods_id,buy_num,brand_id,source,card_no,password," +
			      "first_addr,second_addr,third_addr,detail_addr,receiver," +
			      "zipcode,open_id,trade_state,order_state,time_end) " +
			      "values (?,?,?,?,?,?, CURRENT_TIMESTAMP,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
		int num = jdbcTemplate.update(sql,wxorder.getId(), wxorder.getMobile(),
				wxorder.getAppId(),wxorder.getPartner(),wxorder.getIntegral(),wxorder.getTotal_fee(),
				wxorder.getOut_trade_no(),wxorder.getNonceTr(),
				wxorder.getFee_type(),wxorder.getPayType(),wxorder.getTrade_mode(),
				wxorder.getSign_type(),wxorder.getBank_type(),
				wxorder.getProductInfo(),wxorder.getGoodsId(),
				wxorder.getBuyNum(),wxorder.getBrandId(),wxorder.getSource(),
				wxorder.getCardNo(),wxorder.getPassword(),
				wxorder.getFirstAddr(),wxorder.getSecondAddr(),wxorder.getThirdAddr(),
				wxorder.getDetailAddr(),wxorder.getReceiver(),wxorder.getZipcode(),wxorder.getOpenId(),
				wxorder.getTrade_state(), wxorder.getOrderState());
		return num;
	}
	
	/**
	 *  查询在线售卡成功支付的订单 
	 * @param outTradeNo
	 * @return
	 */
	public WxMallOrder queryCardMallOrder(String outTradeNo,Integer brandId){
		String sql = "select wmo.*,wmoc.card_no xnCardNo from public.weixin_mall_order wmo " +
				"left join public.weixin_mall_order_card wmoc on wmo.out_trade_no=wmoc.out_trade_no " +
				"where wmo.trade_state='0' " +
				"and wmo.brand_id="+brandId+" and wmo.out_trade_no ='"+outTradeNo+"'";
		log.info("查询成功支付的订单sql:"+sql	);
		try {
			WxMallOrder order = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<WxMallOrder>(WxMallOrder.class));
			return order;
		} catch (Exception e) {
			log.error("code happen error.",e);
			return null;
		}
	}
}
