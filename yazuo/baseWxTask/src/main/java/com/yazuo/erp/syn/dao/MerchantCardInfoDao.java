package com.yazuo.erp.syn.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;

@Repository
public class MerchantCardInfoDao extends BaseDAO {
	public List<Map<String, Object>> getMerchantCardInfo() {

		// 1、查询商户对应的各种卡余量
		StringBuffer sql = new StringBuffer("");
		sql.append("WITH mer(         ");
		sql.append("	merchant_id,  ");
		sql.append("	merchant_name,");
		sql.append("	is_face_shop, ");
		sql.append("	brand_id      ");
		sql.append(")AS(                        ");
		sql.append("	SELECT			");
		sql.append("		merchant_id,	");
		sql.append("		merchant_name,	");
		sql.append("		is_face_shop,	");
		sql.append("		brand_id         ");
		sql.append("	FROM			 ");
		sql.append("		trade.merchant	 ");
		sql.append("	WHERE			 ");
		sql.append("		status = 1	 ");
		sql.append("),                           ");
		sql.append(" cardbatch(                 ");
		sql.append("	merchant_id,		");
		sql.append("	cardtype_id,		");
		sql.append("	batch_id		");
		sql.append(")AS(			");
		sql.append("	SELECT                              ");
		sql.append("		cb.merchant_id,		    ");
		sql.append("		cb.cardtype_id,		    ");
		sql.append("		cb.batch_id		    ");
		sql.append("	FROM				    ");
		sql.append("		trade.card_batch AS cb       ");
		sql.append("	WHERE				     ");
		sql.append("		EXISTS(			     ");
		sql.append("			SELECT		     ");
		sql.append("				*	     ");
		sql.append("			FROM                                      ");
		sql.append("				mer				  ");
		sql.append("			WHERE					  ");
		sql.append("				mer.merchant_id = cb.merchant_id  ");
		sql.append("		)						  ");
		sql.append("),                                               ");
		sql.append(" cardrecord(batch_id, COUNT)AS(              ");
		sql.append("	SELECT					 ");
		sql.append("		cr.batch_id,			 ");
		sql.append("		COUNT(cr.batch_id)		 ");
		sql.append("	FROM					 ");
		sql.append("		card_record AS cr               ");
		sql.append("	WHERE					");
		sql.append("		activation_flag IS FALSE	");
		sql.append("	GROUP BY				");
		sql.append("		cr.batch_id			");
		sql.append(")SELECT                                     ");
		sql.append("	COALESCE(cardbatch.cardtype_id,-1) as cardtype_id,			");
		sql.append("	mer.merchant_id,			");
		sql.append("	mer.merchant_name,			");
		sql.append("	mer.is_face_shop,			");
		sql.append("	mer.brand_id,                           ");
		sql.append("	SUM(cardrecord. COUNT) as card_count			");
		sql.append("FROM					");
		sql.append("	cardrecord,				");
		sql.append("	cardbatch,				");
		sql.append("	mer                                      ");
		sql.append("WHERE					 ");
		sql.append("	cardrecord.batch_id = cardbatch.batch_id ");
		sql.append("AND cardbatch.merchant_id = mer.merchant_id	 ");
		sql.append("GROUP BY					 ");
		sql.append("	cardbatch.cardtype_id,                   ");
		sql.append("	mer.merchant_id,			 ");
		sql.append("	mer.merchant_name,			 ");
		sql.append("	mer.is_face_shop,			 ");
		sql.append("	mer.brand_id				 ");
		List<Map<String, Object>> list = jdbcTemplateCRMTrade.queryForList(sql.toString());

		// 2、商户卡类别
		StringBuffer cardType = new StringBuffer();
		cardType.append("SELECT  ");
		cardType.append("	ID, ");
		cardType.append("	merchant_id, ");
		cardType.append("	cardtype, ");
		cardType.append("	price, ");
		cardType.append("	tmp.membershipGrade ");
		cardType.append("FROM ");
		cardType.append("	trade.cardtype ");
		cardType.append("LEFT JOIN( ");
		cardType.append("	SELECT ");
		cardType.append("		ctg.cardtype_id, ");
		cardType.append("		array_to_string( ");
		cardType.append("			ARRAY_AGG( ");
		cardType.append("				COALESCE(dmg.grade_name, '未分类') ");
		cardType.append("			), ");
		cardType.append("			',' ");
		cardType.append("		)membershipGrade ");
		cardType.append("	FROM ");
		cardType.append("		trade.cardtype_grade ctg ");
		cardType.append("	LEFT JOIN trade.dict_membership_grade dmg ON ctg.grade_level = dmg.grade_level ");
		cardType.append("	WHERE ");
		cardType.append("		ctg.status = 1 ");
		cardType.append("	GROUP BY ");
		cardType.append("		ctg.cardtype_id ");
		cardType.append(")AS tmp ON tmp.cardtype_id = cardtype. ID ");
		cardType.append("WHERE ");
		cardType.append("	EXISTS( ");
		cardType.append("		SELECT ");
		cardType.append("			* ");
		cardType.append("		FROM ");
		cardType.append("			trade.merchant AS mer1 ");
		cardType.append("		WHERE ");
		cardType.append("			status = 1 ");
		cardType.append("		AND mer1.merchant_id = cardtype.merchant_id ");
		cardType.append("	) ");

		List<Map<String, Object>> cardtypeList = jdbcTemplateCRMTrade.queryForList(cardType.toString());
		Map<String, Object> cardtypeMap = new HashMap<String, Object>();
		for (Map<String, Object> map : cardtypeList) {
			cardtypeMap.put(map.get("id").toString(), map);
		}

		// 3、商户卡类型最大批次信息
		StringBuffer card = new StringBuffer();
		card.append(" SELECT                                            ");
		card.append(" 	batch.merchant_id,                         ");
		card.append(" 	batch.cardtype_id,                         ");
		card.append(" 	batch.batch_id,				   ");
		card.append(" 	batch.card_batch                           ");
		card.append(" FROM						   ");
		card.append(" 	trade.card_batch AS batch                        ");
		card.append(" WHERE						   ");
		card.append(" 	batch.batch_id IN(			   ");
		card.append(" 		SELECT				   ");
		card.append(" 			MAX(batch_id)AS batch_id   ");
		card.append(" 		FROM				   ");
		card.append(" 			trade.card_batch AS cb              ");
		card.append(" 		WHERE				      ");
		card.append(" 			EXISTS(			      ");
		card.append(" 				SELECT		      ");
		card.append(" 					*	      ");
		card.append(" 				FROM		      ");
		card.append(" 					trade.merchant AS mer1                ");
		card.append(" 				WHERE					");
		card.append(" 					mer1.status = 1			");
		card.append(" 				AND cb.merchant_id = mer1.merchant_id	");
		card.append(" 			)						");
		card.append(" 		GROUP BY						");
		card.append(" 			cb.merchant_id,                                 ");
		card.append(" 			cardtype_id					");
		card.append(" 	)								");

		List<Map<String, Object>> list2 = jdbcTemplateCRMTrade.queryForList(card.toString());
		Map<String, Object> cardMap = new HashMap<String, Object>();
		for (Map<String, Object> map : list2) {
			cardMap.put(map.get("merchant_id").toString() + "|" + map.get("cardtype_id"), map);
		}

		for (Map<String, Object> map : list) {
			String merchantId = map.get("merchant_id").toString();
			Object cardtypeId = map.get("cardtype_id");
			Map<String, Object> map1 = new HashMap<String, Object>();
			if (cardtypeId != null) {
				map1 = (Map<String, Object>) cardtypeMap.get(cardtypeId.toString());
			}
			Map<String, Object> map2 = (Map<String, Object>) cardMap.get(merchantId + "|" + cardtypeId);
			String cardtypeName = (map1 != null) ? (String) map1.get("cardtype") : null;
			BigDecimal price = (map1 != null) ? (BigDecimal) map1.get("price") : null;
			String membershipGrade = (map1 != null) ? (String) map1.get("membershipGrade") : null;
			Integer cardBatch = (map2 != null) ? (Integer) map2.get("card_batch") : null;
			String merchant_type = "";
			Object brand_id = map.get("brand_id");
			if (null != map.get("is_face_shop") && (Boolean) map.get("is_face_shop") == true) {
				merchant_type = "门店";
			} else if (null != brand_id && ((Integer) brand_id).intValue() == ((Integer) map.get("merchant_id")).intValue()) {
				merchant_type = "品牌";
			} else {
				merchant_type = "管理公司";
			}
			map.put("cardtypeName", cardtypeName);
			map.put("price", price);
			map.put("membershipGrade", membershipGrade);
			map.put("cardBatch", cardBatch);
			map.put("merchant_type", merchant_type);
		}
		return list;
	}
}
