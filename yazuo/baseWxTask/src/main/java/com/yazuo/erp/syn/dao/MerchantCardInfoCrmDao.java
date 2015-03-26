package com.yazuo.erp.syn.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;

@Repository
public class MerchantCardInfoCrmDao extends BaseDAO {
	public List<Map<String, Object>> getMerchantCardInfo() {

		// 1、查询卡类型(不为空)对应的卡余量
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT                                                     ");
		sql.append("	batch.cardtype_id,                                     ");
		sql.append("	COUNT(*)      as count                                ");
		sql.append("FROM                                                       ");
		sql.append("	trade.card_record AS record,                           ");
		sql.append("	trade.card_batch AS batch			       ");
		sql.append("WHERE						       ");
		sql.append("	EXISTS(						       ");
		sql.append("		SELECT                                         ");
		sql.append("			*				       ");
		sql.append("		FROM					       ");
		sql.append("			trade.merchant AS mer			 ");
		sql.append("		WHERE                                             ");
		sql.append("			mer.merchant_id = batch.merchant_id	  ");
		sql.append("		AND mer.status = 1				  ");
		sql.append("	)							  ");
		sql.append("AND batch.cardtype_id IS NOT NULL ");
		sql.append("AND record.batch_id = batch.batch_id                          ");
		sql.append("AND record.activation_flag IS FALSE                  	  ");
		sql.append("GROUP BY							  ");
		sql.append("	batch.cardtype_id					 ");

		List<Map<String, Object>> cardMarginList = jdbcTemplateCRMTrade.queryForList(sql.toString());
		Map<String, Object> cardMarginMap = new HashMap<String, Object>();
		for (Map<String, Object> map : cardMarginList) {
			Object object = map.get("cardtype_id");
			String cardtype_id = object.toString();
			cardMarginMap.put(cardtype_id, map);
		}

		// 2、商户卡类型(不为空 )最大批次信息
		StringBuffer card = new StringBuffer();
		card.append(" SELECT                                            ");
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
		card.append(" 			AND cb.cardtype_id IS NOT NULL					");
		card.append(" 		GROUP BY						");
		card.append(" 			cardtype_id					");
		card.append(" 	)								");

		List<Map<String, Object>> cardBatchList = jdbcTemplateCRMTrade.queryForList(card.toString());
		Map<String, Object> cardBatchMap = new HashMap<String, Object>();
		for (Map<String, Object> map : cardBatchList) {
			Object object = map.get("cardtype_id");
			String cardtype_id = object.toString();
			cardBatchMap.put(cardtype_id, map);
		}

		// 3、商户卡类别
		StringBuffer cardType = new StringBuffer();
		cardType.append("SELECT  ");
		cardType.append("	ID, ");
		cardType.append("	cardtype.merchant_id, ");
		cardType.append("	cardtype, ");
		cardType.append("	price, ");
		cardType.append("	tmp.membershipGrade, ");
		cardType.append("mer1.brand_id, ");
		cardType.append("mer1.merchant_name,");
		cardType.append("	mer1.is_face_shop ");
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
		cardType.append(")AS tmp ON tmp.cardtype_id = cardtype. ID  ");
		cardType.append("LEFT JOIN trade.merchant AS mer1 ON mer1.merchant_id = cardtype.merchant_id  ");
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

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : cardtypeList) {
			Integer cardtypeId = (Integer) map.get("ID");
			Object object = cardMarginMap.get(cardtypeId.toString());
			Long count = null;// 卡余量
			if (null != object) {
				Map<String, Object> map1 = (Map<String, Object>) object;
				count = (Long) map1.get("count");
			}
			map.put("card_count", count);
			Object object2 = cardBatchMap.get(cardtypeId.toString());
			Integer card_batch = null;
			if (null != object2) {
				Map<String, Object> map2 = (Map<String, Object>) object2;
				card_batch = (Integer) map2.get("card_batch");
			}

			String merchant_type = "";
			Object brand_id = map.get("brand_id");
			if (null != map.get("is_face_shop") && (Boolean) map.get("is_face_shop") == true) {
				merchant_type = "门店";
			} else if (null != brand_id && ((Integer) brand_id).intValue() == ((Integer) map.get("merchant_id")).intValue()) {
				merchant_type = "品牌";
			} else {
				merchant_type = "管理公司";
			}
			map.put("cardBatch", card_batch);
			map.put("merchant_type", merchant_type);
			resultList.add(map);
		}

		// 4、卡类型为空的情况
		// 4.1、查询卡类型为空时，商户最大开卡批次信息
		StringBuffer card1 = new StringBuffer();
		card1.append(" SELECT ");
		card1.append(" 	cb.cardtype_id,");
		card1.append(" 	cb.merchant_id,");
		card1.append(" cb.card_batch,   ");
		card1.append(" 	cb.batch_id,    ");
		card1.append(" 	mer.brand_id,   ");
		card1.append(" 	mer.merchant_name,");
		card1.append(" 	mer.is_face_shop  ");
		card1.append(" FROM		  ");
		card1.append(" 	trade.card_batch AS cb,");
		card1.append(" 	trade.merchant AS mer  ");
		card1.append(" WHERE		       ");
		card1.append(" 	cb.batch_id IN(                            ");
		card1.append(" 		SELECT				   ");
		card1.append(" 			MAX(batch_id)AS batch_id   ");
		card1.append(" 		FROM                               ");
		card1.append(" 			trade.card_batch AS cb	   ");
		card1.append(" 		WHERE				   ");
		card1.append(" 			cardtype_id IS NULL        ");
		card1.append(" AND EXISTS(				   ");
		card1.append(" 			SELECT			   ");
		card1.append(" 				*                  ");
		card1.append(" 			FROM			   ");
		card1.append(" 				trade.merchant mer1");
		card1.append(" 			WHERE                                     ");
		card1.append(" 				mer1.merchant_id = cb.merchant_id ");
		card1.append(" 			AND mer1.status = 1			  ");
		card1.append(" 		)                                                 ");
		card1.append(" 		GROUP BY					  ");
		card1.append(" 			merchant_id				  ");
		card1.append(" 	)                                                         ");
		card1.append(" AND mer.status = 1					  ");
		card1.append(" AND mer.merchant_id = cb.merchant_id			  ");

		List<Map<String, Object>> card1List = jdbcTemplateCRMTrade.queryForList(card1.toString());
		Map<String, Object> card1Map = new HashMap<String, Object>();
		for (Map<String, Object> map2 : card1List) {
			card1Map.put(map2.get("merchant_id").toString(), map2);
		}

		// 4.2、查询卡类型为空时，商户卡余量
		StringBuffer cardCountCardTypeIsNull = new StringBuffer("");
		cardCountCardTypeIsNull.append("SELECT ");
		cardCountCardTypeIsNull.append("	cb.merchant_id, ");
		cardCountCardTypeIsNull.append("	COUNT(1) card_count ");
		cardCountCardTypeIsNull.append("FROM ");
		cardCountCardTypeIsNull.append("	trade.card_batch  AS cb, ");
		cardCountCardTypeIsNull.append("	trade.card_record AS cr ");
		cardCountCardTypeIsNull.append("WHERE ");
		cardCountCardTypeIsNull.append("	cr.batch_id = cb.batch_id ");
		cardCountCardTypeIsNull.append("AND cb.cardtype_id IS NULL ");
		cardCountCardTypeIsNull.append("AND EXISTS( ");
		cardCountCardTypeIsNull.append("	SELECT ");
		cardCountCardTypeIsNull.append("		* ");
		cardCountCardTypeIsNull.append("	FROM ");
		cardCountCardTypeIsNull.append("		trade.merchant mer1 ");
		cardCountCardTypeIsNull.append("	WHERE ");
		cardCountCardTypeIsNull.append("		mer1.merchant_id = cb.merchant_id ");
		cardCountCardTypeIsNull.append("	AND mer1.status = 1 ");
		cardCountCardTypeIsNull.append(") ");
		cardCountCardTypeIsNull.append("AND cr.activation_flag IS FALSE ");
		cardCountCardTypeIsNull.append("GROUP BY ");
		cardCountCardTypeIsNull.append("	cb.merchant_id ");
		List<Map<String, Object>> cardCountCardTypeIsNullList = jdbcTemplateCRMTrade.queryForList(cardCountCardTypeIsNull.toString());
		for (Map<String, Object> map : cardCountCardTypeIsNullList) {
			Integer merchantId = (Integer) map.get("merchant_id");
			Object object = card1Map.get(merchantId.toString());
			if (null != object) {
				Map<String, Object> map3 = (Map<String, Object>) object;
				map.put("cardBatch", map3.get("card_batch"));
				map.put("brand_id", map3.get("brand_id"));
				map.put("merchant_name", map3.get("merchant_name"));
				map.put("ID", -1);
				String merchant_type = "";
				Object brand_id = map3.get("brand_id");
				if (null != map3.get("is_face_shop") && (Boolean) map3.get("is_face_shop") == true) {
					merchant_type = "门店";
				} else if ((merchantId.intValue() == 0)
						|| (null != brand_id && ((Integer) brand_id).intValue() == ((Integer) map.get("merchant_id")).intValue())) {
					merchant_type = "品牌";
				} else {
					merchant_type = "管理公司";
				}
				map.put("merchant_type", merchant_type);
				resultList.add(map);
			}
		}
		return resultList;
	}
}
