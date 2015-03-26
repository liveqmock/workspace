package com.yazuo.weixin.minimart.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.minimart.vo.WxMallRefundRecord;



/**
* @ClassName WxMallRefundRecordDao
* @Description 退款记录dao
* @author sundongfeng@yazuo.com
* @date 2014-11-5 上午11:44:35
* @version 1.0
*/
@Repository
public class WxMallRefundRecordDao {
	private static final Log log = LogFactory.getLog("mall");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbc;
	/**
	 * 查询商家参数
	 * @param brandid
	 * @return
	 */
	public List<Map<String,Object>> queryRefundRecodeList(String outradeno){
		String sql = "select * from public.weixin_mall_refund_record where out_trade_no='"+outradeno+"'";
		log.info("sql:"+sql	);
		List<Map<String,Object>> obj = jdbc.queryForList(sql);
		return obj;
	}
	/**
	 * 插入退款参数
	 * @param dict 
	 * @return
	 */
	public boolean insertRefundRecord(WxMallRefundRecord dict){
		/*String sql = "select count(*) from public.weixin_mall_refund_record where out_trade_no='"+dict.getOutTradeNo()+"' and deal_state="+dict.getDealState();
		int num = jdbc.queryForInt(sql);
		log.info("sql:"+sql	+" num:"+num);
		if(num<1){*/
			String sql="insert into public.weixin_mall_refund_record (out_trade_no,deal_state,description,deal_user,brand_id,refund_num,deal_time) values(?,?,?,?,?,?,CURRENT_TIMESTAMP)";
			int num =jdbc.update(sql, dict.getOutTradeNo(),dict.getDealState(),dict.getDescription(),dict.getDealUser(),dict.getBrandId(),dict.getRefundNum());
			return num>0?true:false;
		/*}else{
			return false;
		}*/
	}
}
