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
package com.yazuo.external.account.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yazuo.external.account.dao.MerchantProductDao;
import com.yazuo.external.account.vo.MerchantProductVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-6 下午5:36:42
 */
@Repository
public class MerchantProductDaoImpl implements MerchantProductDao {
	@Resource(name = "jdbcTemplateCrm210")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<String> getProductsByMerchantId(Integer merchantId) {
		Assert.notNull(merchantId, "商户ID不能为空");
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT p.product_name FROM " + " account.merchant_product AS mp,trade.merchant AS mer,account.product AS p"
				+ " WHERE mer.merchant_id = mp.merchant_id AND p.product_id = mp.product_id AND mer.merchant_id = " + merchantId);
		if (merchantId.intValue() == 0) {
			sb.append(" AND mp.product_id IN(602, 33, 37)");
		}

		return jdbcTemplate.queryForList(sb.toString(), String.class);
	}

	@Override
	public List<MerchantProductVO> getProductsAllMerchant(List<Integer> brandIdsList) {
		String sql = "SELECT mp.merchant_id,mp.product_id,p.product_name AS productName FROM "
				+ " account.merchant_product AS mp,trade.merchant AS mer,account.product AS p"
				+ " WHERE mer.merchant_id = mp.merchant_id  AND P .product_id = mp.product_id AND mer.merchant_id = ANY(ARRAY "
				+ brandIdsList + ")  ORDER BY mer.merchant_id";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<MerchantProductVO>(MerchantProductVO.class));
	}
}
