package com.yazuo.weixin.minimart.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.minimart.vo.WxMallGoods;

/**
* @ClassName WxMallInventoryDao
* @Description 微信商城商品库存dao
* @author sundongfeng@yazuo.com
* @date 2014-8-6 上午9:01:10
* @version 1.0
*/
@Repository
public class WxMallGoodsDao {
	private static final Log log = LogFactory.getLog("mall");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbc;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
	
	/**
	 * 更新商品状态, 2 出售中，3 售罄
	 * @param goodsId
	 * @return
	 */
	public int updateGoodState(Integer goodsId,Integer status){
		String sql = "update public.weixin_mall_goods set goods_status_id="+status+" where id="+goodsId;
		log.info("更新商品下架sql："+sql);
		return jdbc.update(sql);
	}
	
	/**
	 * 判断商品是否过期
	 * @param goodsId
	 * @return
	 */
	public boolean ifOutDate(Integer goodsId){
		String today = sdf.format(new Date());
		String sql = " select count(1) from public.weixin_mall_goods where expire_type=1 and id="+goodsId+" and " +
				"(to_timestamp('"+today+"', 'YYYY-MM-DD HH24:MI:SS') BETWEEN time_begin and time_end)";
		int cn = jdbc.queryForInt(sql);
		return cn>0?false:true;//大于0，不过期，小于过期
	}
	/**
	 * 查询商户的商品
	 * @param brandid
	 * @return
	 */
	public List<WxMallGoods> queryMallGoods(Integer brandid,Integer categoryId){
		String sql ="select wmt.name bigName,wmt.type_order bigOrder," +
				" wmtt.name smallName,wmtt.type_order smallOrder," +
				" wmg.*,wmi.remain_num, wmg.deals_type_id,dwmdt.name deal_name,wmg.product_type" +
				" from public.weixin_mall_goods wmg" +
				" left join public.weixin_mall_inventory wmi on wmg.id=wmi.goods_id " +
				" left join public.dict_weixin_mall_deals_type dwmdt on wmg.deals_type_id=dwmdt.id" +
				" left JOIN public.weixin_mall_type wmt on wmg.type_id=wmt.id" +
				" left JOIN public.weixin_mall_type wmtt on wmg.sub_type_id=wmtt.id" +
				" where wmg.brand_id="+brandid+" and wmg.category_id="+categoryId+" and wmg.status=1 " +
				" and wmg.goods_status_id in(2,3) " ;
		log.info("查询商品sql:"+sql);
		List<WxMallGoods> list = jdbc.query(sql,new BeanPropertyRowMapper<WxMallGoods>(WxMallGoods.class));
		return list;
	}
	/**
	 * 查询单个商品
	 * @param brandId
	 * @param goodCode
	 * @return
	 */
	public WxMallGoods queryEachGood(Integer brandId,Integer goodCode){
		String sql ="select wmt.name bigName,wmtt.name smallName,wmg.*,wmi.remain_num ," +
				" wmg.deals_type_id,dwmdt.name deal_name,wmg.product_type" +
				" from public.weixin_mall_goods wmg" +
				" left join public.weixin_mall_inventory wmi on wmg.id=wmi.goods_id " +
				" left join public.dict_weixin_mall_deals_type dwmdt on wmg.deals_type_id=dwmdt.id" +
				" left JOIN public.weixin_mall_type wmt on wmg.type_id=wmt.id" +
				" left JOIN public.weixin_mall_type wmtt on wmg.sub_type_id=wmtt.id" +
				" where wmg.brand_id="+brandId+" and wmg.id="+goodCode +
				" and wmg.status=1 " ;
				 
		log.info("查单个商品sql:"+sql);
		try {
			List<WxMallGoods> list = jdbc.query(sql,new BeanPropertyRowMapper<WxMallGoods>(WxMallGoods.class));
			return list.get(0);
		} catch (DataAccessException e) {
			log.error("code happen error.",e);
			return null;
		}
		
	}
	/**
	 * 查询商品对应的券id
	 * @param goodId
	 * @return
	 */
	public Integer queryGoodsCouponId(Long goodId){
		String sql = " select coupon_id from public.weixin_mall_goods_coupon wmgc " +
				"where goods_id="+goodId;
		log.info("查询商品对应的券id-sql:"+sql);
		try {
			Integer couponId = jdbc.queryForInt(sql);
			return couponId;
		} catch (DataAccessException e) {
			log.error("code happen error.",e);
			return -1;
		}
	}
}
