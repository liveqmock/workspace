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
package com.yazuo.erp.syn.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.syn.vo.SynMerchantVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-18 下午6:32:28
 */
@Repository
public class SynMerchantDao extends BaseDAO {
	public List<SynMerchantVO> querySynMerchantList(String merchantStatus, String date) {
		String sql = "SELECT mer1.*,um.user_id FROM syn.syn_merchant AS mer1,sys.sys_user_merchant AS um "
				+ " WHERE mer1.status = 1 AND mer1.merchant_status =? AND mer1.merchant_id = um.merchant_id "
				+ " AND to_char(mer1.service_end_time,'yyyy-mm-dd')= ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<SynMerchantVO>(SynMerchantVO.class), merchantStatus, date);
	}
}
