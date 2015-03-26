package com.yazuo.weixin.minimart.dao;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
* @ClassName WxMallInventoryDao
* @Description 微信商城商品库存dao
* @author sundongfeng@yazuo.com
* @date 2014-8-6 上午9:01:10
* @version 1.0
*/
@Repository
public class WxMallInventoryDao {
	private static final Log log = LogFactory.getLog("mall");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbc;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 查询剩余库存
	 * @param goosId
	 * @return
	 */
	public int selectRemainNum(Integer goosId){
		String sql = "select remain_num from public.weixin_mall_inventory where goods_id=?";
		return jdbc.queryForInt(sql, goosId);
	}
	/**
	 * 查询库存变量
	 * @param goosId
	 * @return
	 */
	public Map<String,Object> queryGoodInventory(Integer goosId){
		String sql = "select * from public.weixin_mall_inventory where goods_id=?";
		return jdbc.queryForMap(sql, goosId);
	}
	/**
	 * 更新库存量
	 * @param num
	 * @param goosId
	 * @return
	 */
	public int updateRemainNum(Integer num,Integer goosId){
		String sql = "update public.weixin_mall_inventory set remain_num=remain_num-? where goods_id=?";
		return jdbc.update(sql, num,goosId);
	}
	/**
	 * 更新发送短信次数
	 * @param num
	 * @param goosId
	 * @return
	 */
	public int updateSendSmsNum(Integer goosId){
		String sql = "update public.weixin_mall_inventory set send_sms_num=1 where goods_id=?";
		return jdbc.update(sql, goosId);
	}
	
	/**
	 * 查询安全剩余库存
	 * @param goosId
	 * @return
	 */
	public int selectSafeRemainNum(Integer goosId){
		String sql = "select safe_remain_num from public.weixin_mall_inventory where goods_id=?";
		return jdbc.queryForInt(sql, goosId);
	}
	
	/**
	 * 更新安全剩余库存
	 * @param num
	 * @param goosId
	 * @return
	 */
	public int updateSafeRemainNum(Integer num,Integer goosId){
		String sql = "update public.weixin_mall_inventory set safe_remain_num=safe_remain_num-? where goods_id=?";
		return jdbc.update(sql, num,goosId);
	}
}
