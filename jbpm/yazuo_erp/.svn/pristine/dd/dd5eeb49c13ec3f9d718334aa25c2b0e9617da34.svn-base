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
package com.yazuo.external.active.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.external.active.dao.ActiveCrmDao;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-19 下午5:26:23
 */
@Repository
public class ActiveCrmDaoImpl implements ActiveCrmDao {

	@Resource(name = "jdbcTemplateCrm")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> getActiveExecutiving(Integer brandId) {
		String sql = "SELECT a .active_name,a .active_begin,a .active_end,t .type_name FROM  public .active AS a "
				+ " LEFT JOIN public .active_type AS t ON a .active_type = t .type_id "
				+ " WHERE a .merchant_id IN ( SELECT mer1.merchant_id FROM "
				+ " trade.merchant AS mer1 WHERE mer1.brand_id = ? AND mer1.status = 1) AND A .status = 3";
		return jdbcTemplate.queryForList(sql, brandId);
	}

}
