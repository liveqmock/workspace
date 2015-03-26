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
package com.yazuo.erp.sys.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.sys.vo.SysUserMerchantVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-4 下午3:58:12
 */
@Repository
public class SysUserMerchantDao extends BaseDAO {
	public List<SysUserMerchantVO> getSysUserMerchantByMerchantId(Integer merhantId) {
		String sql = "SELECT * FROM  " + SCHEMANAME_SYS + ".sys_user_merchant AS um ,sys.sys_user AS u WHERE um.merchant_id = ?"
				+ " AND u.id = um.user_id AND u.is_enable = '1' ";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<SysUserMerchantVO>(SysUserMerchantVO.class), merhantId);
	}
}
