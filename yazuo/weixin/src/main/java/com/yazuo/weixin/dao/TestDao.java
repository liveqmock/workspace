/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.weixin.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @author yazuo
 * @date 2014-7-23 下午6:39:46
 */
@Repository
public class TestDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());
	
	public int save(String weixinId, String phoneNo, String cardNo, Integer shipId, String birthday, int sex, String name) {
		String sql = " INSERT INTO weixin.membership (weixin_id,phone_no,brand_id,card_no,is_subscribe,is_member,membership_id"
				+",birthday,update_times,gender,name,member_type,birth_type)"
				+"VALUES(?,?,137,?,true,true,?,'"+birthday+"',0,?,?,1,1)";
		return jdbcTemplate.update(sql, weixinId, phoneNo, cardNo, shipId, sex, name);
	}
	
	
	public List<Map<String, Object>> getQhsfData () {
		String sql = "select * from weixin.weixin_qhsf_temp";
		return jdbcTemplate.queryForList(sql);
	}
	
	public int queryMembershipById (Integer shipId) {
		String sql = "select count(*) from weixin.membership where membership_id=?";
		return jdbcTemplate.queryForInt(sql, shipId);
	}
}
