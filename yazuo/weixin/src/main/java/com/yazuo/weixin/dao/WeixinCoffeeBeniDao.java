package com.yazuo.weixin.dao;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.vo.WxcoffeeCzFlag;


/**
* @ClassName WeixinCoffeeBeniDao
* @Description 咖啡陪你Dao类
* @author sundongfeng@yazuo.com
* @date 2014-7-8 下午3:57:28
* @version 1.0
*/
@Repository
public class WeixinCoffeeBeniDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Log log = LogFactory.getLog("wxpay");
	
	/**
	 * 查询是否有充值状态，不代表充值成功
	 * @param outTradeNo
	 * @return
	 */
	public boolean queryHasCzflag(String outTradeNo){
		String sql = "select count(*) cn from weixin.weixin_coffee_czflag where out_trade_no=?";
		int cn = jdbcTemplate.queryForInt(sql,outTradeNo);
		log.info("查询是否有充值状态sql:"+sql+" outtradeno:"+outTradeNo+" cn:"+cn);
		return cn>0?true:false;
	}
	/**
	 * 查询 充值成功状态
	 * @param outTradeNo
	 * @return
	 */
	public boolean queryCzflag(String outTradeNo){
		String sql = "select count(1) cn from weixin.weixin_coffee_czflag where out_trade_no=? and status='1' ";
		int cn = jdbcTemplate.queryForInt(sql,outTradeNo);
		log.info("查询是充值成功状态sql:"+sql+" outtradeno:"+outTradeNo+" cn:"+cn);
		return cn>0?true:false;
	}
	
	/**
	 * 插入充值状态信息
	 * @return
	 */
	public int insertCzflag(WxcoffeeCzFlag czflag){
		log.info(czflag.toString());
		String sql = "insert into weixin.weixin_coffee_czflag (out_trade_no, mobile,"+ 
				    " card_no, brand_id, amount,status, remark,create_time) "+
				    " values (?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
		return jdbcTemplate.update(sql, czflag.getOutTradeNo(),czflag.getMobile(),czflag.getCardNo(),
				czflag.getBrandId(),czflag.getAmount(),czflag.getStatus(),czflag.getRemark());
	}
	
	/**
	 * 更新充值状态信息
	 * @return
	 */
	public int updateCzflag(WxcoffeeCzFlag czflag){
		String sql = "update weixin.weixin_coffee_czflag set status =?,amount=? where out_trade_no=?";
		log.info("更新充值状态信息sql:"+sql+" status:"+czflag.getStatus()+" outtradeno:"+czflag.getOutTradeNo());
		return jdbcTemplate.update(sql, czflag.getStatus(),czflag.getAmount(),czflag.getOutTradeNo());
	}

}
