/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.external.account.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Assume;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.external.account.dao.MerchantDao;
import com.yazuo.external.account.vo.MerchantVO;

/**
 * @author zhaohuaqin
 * @date 2014-8-6 下午6:50:04
 */
@Repository
public class MerchantDaoImpl implements MerchantDao {
	@Resource(name = "jdbcTemplateCrm210Trade")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Integer> getAllBrandId() {
		String sql = "SELECT merchant_id FROM trade.merchant WHERE merchant_id = brand_id AND status=1 ORDER BY merchant_id";

		return jdbcTemplate.queryForList(sql, Integer.class);
	}

	@Override
	public List<Map<String, Object>> getAllMerchants() {
		String sql = "SELECT merchant_id, merchant_name FROM trade.merchant  where merchant_id = brand_id ";

		return jdbcTemplate.queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getMerchantsForSurvey(Integer merchantId) {
		//TEST
//		if(true) return invokeTest();
		
		String sql = "SELECT merchant_id, merchant_name FROM trade.merchant  where trade.merchant.is_face_shop = 't' and brand_id = "+merchantId+" and status = 1 ";
		return jdbcTemplate.queryForList(sql);
	}

	private List<Map<String, Object>> invokeTest() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < 5; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("merchant_id", 2334+i);
			hashMap.put("merchant_name", "merchant_name"+i);
			list.add(hashMap);
		}
		return list;
	}
	
	/**
	 * 查询品牌下所有的门店
	 */
	@Override
	public List<Map<String, Object>> getMerchantsOfBrand(Integer merchantId) {
		String sql = "SELECT * FROM trade.merchant  where  brand_id = "+merchantId+" and status = 1 order by parent";
		return jdbcTemplate.queryForList(sql);
	}
	@Override
	public List<Integer> getCrmMerchantIdListByBrandId(Integer brandId) {
		String sql = "	SELECT mer.merchant_id FROM trade.merchant AS mer WHERE mer.brand_id  = ?";
		List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, brandId);
		return result;
	}

	@Override
	public List<Map<String, Object>> getMerchantsInfo(List<Integer> merchantIds, String merchantName) {
		StringBuffer sql = new StringBuffer("");
		if (!CollectionUtils.isEmpty(merchantIds)) {
			sql.append("WITH tmp(id) as (");
			for (int i = 0; i < merchantIds.size(); i++) {
				sql.append("VALUES(");
				sql.append(merchantIds.get(i));
				sql.append(") ");
				if (i != merchantIds.size() - 1) {
					sql.append(" UNION ");
				}
			}
			sql.append(") ");
		}
		sql.append(" SELECT merchant_id,merchant_name FROM trade.merchant ");
		if (!CollectionUtils.isEmpty(merchantIds)) {
			sql.append(" left join tmp ");
			sql.append(" ON tmp.id = merchant.merchant_id");
		}
		sql.append(" WHERE status = 1 AND merchant_id = brand_id ");
		if (!CollectionUtils.isEmpty(merchantIds)) {
			sql.append("AND tmp.id is null ");
		}
		if (!StringUtils.isEmpty(merchantName)) {
			sql.append(" AND merchant_name LIKE '%");
			sql.append(merchantName);
			sql.append("%'");
		}

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		return list;
	}

	@Override
	public MerchantVO getMerchantVO(Integer merchantId) {
		String sql = "SELECT * FROM trade.merchant where merchant_id =" + merchantId;
		List<MerchantVO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<MerchantVO>(MerchantVO.class));
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询品牌拥有的门店数
	 */
	@Override
	public Long getMerchantFaceShopCount(Integer brandId) {
		String sql = "SELECT COUNT(1) FROM trade.merchant WHERE brand_id =? AND is_face_shop = TRUE";
		return jdbcTemplate.queryForLong(sql, brandId);
	}

	@Override
	public MerchantVO getMerchantById(Integer merchantId) {
		String sql = "SELECT * FROM trade.merchant where merchant_id = " + merchantId
				+ "  AND status = 1 AND merchant_id = brand_id;";
		List<MerchantVO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<MerchantVO>(MerchantVO.class));
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
