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
package com.yazuo.external.card.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.external.card.dao.MemberCardInfoDao;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-20 下午3:42:38
 */
@Repository
public class MemberCardInfoDaoImpl implements MemberCardInfoDao {
	@Resource(name = "jdbcTemplateCrm210")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> getMemberCardInfoByBrandId(Integer brandId) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, ");
		sql.append("	merchant_id, ");
		sql.append("	cardtype, ");
		sql.append("	price, ");
		sql.append("	tmp.membershipGrade ");
		sql.append("FROM ");
		sql.append("	trade.cardtype ");
		sql.append("LEFT JOIN( ");
		sql.append("	SELECT ");
		sql.append("		ctg.cardtype_id, ");
		sql.append("		array_to_string( ");
		sql.append("			ARRAY_AGG( ");
		sql.append("				COALESCE(dmg.grade_name, '未分类') ");
		sql.append("			), ");
		sql.append("			',' ");
		sql.append("		)membershipGrade ");
		sql.append("	FROM ");
		sql.append("		trade.cardtype_grade ctg ");
		sql.append("	LEFT JOIN trade.dict_membership_grade dmg ON ctg.grade_level = dmg.grade_level ");
		sql.append("	WHERE ");
		sql.append("		ctg.status = 1 ");
		sql.append("	GROUP BY ");
		sql.append("		ctg.cardtype_id ");
		sql.append(")AS tmp ON tmp.cardtype_id = cardtype.id ");
		sql.append("WHERE ");
		sql.append("	merchant_id IN( ");
		sql.append("		SELECT ");
		sql.append("			merchant_id ");
		sql.append("		FROM ");
		sql.append("			trade.merchant ");
		sql.append("		WHERE ");
		sql.append("			merchant_id IN( ");
		sql.append("				SELECT ");
		sql.append("					merchant_id ");
		sql.append("				FROM ");
		sql.append("					trade.merchant ");
		sql.append("				WHERE ");
		sql.append("					brand_id = ? ");
		sql.append("				AND status = 1 ");
		sql.append("			) ");
		sql.append("		AND status = 1 ");
		sql.append("	); ");

		List<Map<String, Object>> cardtypeList = jdbcTemplate.queryForList(sql.toString(), brandId);

		StringBuffer cardCategory = new StringBuffer();
		cardCategory.append("SELECT  ");
		cardCategory.append("	tmp.cardtype_id, ");
		cardCategory.append("	cb.card_batch ");
		cardCategory.append("FROM  ");
		cardCategory.append("	trade.card_batch AS cb, ");
		cardCategory.append("	(");
		cardCategory.append("		SELECT ");
		cardCategory.append("			MAX(batch_id)AS batch_id,  ");
		cardCategory.append("			cardtype_id ");
		cardCategory.append("		FROM     ");
		cardCategory.append("			trade.card_batch	 ");
		cardCategory.append("WHERE ");
		cardCategory.append("merchant_id IN( ");
		cardCategory.append("		SELECT ");
		cardCategory.append("			merchant_id ");
		cardCategory.append("		FROM ");
		cardCategory.append("			trade.merchant ");
		cardCategory.append("		WHERE ");
		cardCategory.append("			brand_id = ? ");
		cardCategory.append("		AND status = 1 ");
		cardCategory.append("	) ");
		cardCategory.append("		GROUP BY	");
		cardCategory.append("			cardtype_id ");
		cardCategory.append("	)AS tmp	 ");
		cardCategory.append("WHERE ");
		cardCategory.append("	cb.batch_id = tmp.batch_id;");

		List<Map<String, Object>> cardCategoryList = jdbcTemplate.queryForList(cardCategory.toString(), brandId);
		Map<String, Object> cardCategoryMap = new HashMap<String, Object>();
		for (Map<String, Object> map : cardCategoryList) {
			if (null != map.get("cardtype_id")) {
				cardCategoryMap.put(map.get("cardtype_id").toString(), map.get("card_batch"));
			}
		}

		StringBuffer cardSurplus = new StringBuffer();
		cardSurplus.append("SELECT                                   ");
		cardSurplus.append("	cardtype_id,			      ");
		cardSurplus.append("	COUNT(1)	as count		      ");
		cardSurplus.append("FROM				      ");
		cardSurplus.append("	trade.card_record,			      ");
		cardSurplus.append("	(				      ");
		cardSurplus.append("		SELECT                        ");
		cardSurplus.append("			batch_id,             ");
		cardSurplus.append("			cardtype_id	      ");
		cardSurplus.append("		FROM			      ");
		cardSurplus.append("			trade.card_batch	      ");
		cardSurplus.append("WHERE ");
		cardSurplus.append("merchant_id IN( ");
		cardSurplus.append("		SELECT ");
		cardSurplus.append("			merchant_id ");
		cardSurplus.append("		FROM ");
		cardSurplus.append("			trade.merchant ");
		cardSurplus.append("		WHERE ");
		cardSurplus.append("			brand_id = ? ");
		cardSurplus.append("		AND status = 1 ");
		cardSurplus.append("	) ");
		cardSurplus.append("	)AS tmp				      ");
		cardSurplus.append("WHERE				      ");
		cardSurplus.append("	tmp.batch_id = card_record.batch_id   ");
		cardSurplus.append("AND activation_flag = FALSE	      ");
		cardSurplus.append("GROUP BY				      ");
		cardSurplus.append("	cardtype_id			      ");
		cardSurplus.append("ORDER BY				      ");
		cardSurplus.append("	cardtype_id			      ");

		List<Map<String, Object>> cardSurplusList = jdbcTemplate.queryForList(cardSurplus.toString(), brandId);
		Map<String, Object> cardSurplusMap = new HashMap<String, Object>();
		for (Map<String, Object> map : cardSurplusList) {
			if (null != map.get("cardtype_id")) {
				cardSurplusMap.put(map.get("cardtype_id").toString(), map.get("count"));
			}
		}

		StringBuffer merchantSql = new StringBuffer();
		merchantSql.append("SELECT  ");
		merchantSql.append("	merchant_id,    ");
		merchantSql.append("	merchant_name,     ");
		merchantSql.append("	is_face_shop,     ");
		merchantSql.append("	brand_id,       ");
		merchantSql.append("	parent         ");
		merchantSql.append("FROM            ");
		merchantSql.append("	trade.merchant     ");
		merchantSql.append("WHERE           ");
		merchantSql.append("	brand_id = ?   ");
		merchantSql.append("	 AND status = 1;  ");

		List<Map<String, Object>> merchantList = jdbcTemplate.queryForList(merchantSql.toString(), brandId);
		Map<String, Object> merchantMap = new HashMap<String, Object>();
		for (Map<String, Object> map : merchantList) {
			merchantMap.put(map.get("merchant_id").toString(), map);
		}

		for (Map<String, Object> map : cardtypeList) {
			Integer merchant_id = (Integer) map.get("merchant_id");
			Integer id = (Integer) map.get("id");
			Map<String, Object> mer = (Map<String, Object>) merchantMap.get(merchant_id.toString());
			Boolean is_face_shop = (Boolean) mer.get("is_face_shop");
			Integer brand_id = (Integer) mer.get("brand_id");

			String merchant_type = null;
			if (is_face_shop) {
				merchant_type = "门店";
			} else if (brand_id.intValue() == merchant_id.intValue()) {
				merchant_type = "品牌";
			} else {
				merchant_type = "管理公司";
			}
			map.put("merchant_name", mer.get("merchant_name"));
			map.put("merchant_type", merchant_type);
			Integer batch = (Integer) cardCategoryMap.get(id.toString());
			String is_solid = null;
			if (null != batch) {
				is_solid = (batch.intValue() == 3) ? "是" : "否";
			}
			map.put("is_solid", is_solid);
			Long card_surplus = 0l;
			if (null != cardSurplusMap.get(id.toString())) {
				card_surplus = (Long) cardSurplusMap.get(id.toString());
			}

			map.put("card_surplus", card_surplus);
		}
		return cardtypeList;
	}
}
