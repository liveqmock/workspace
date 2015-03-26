package com.yazuo.erp.syn.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.syn.vo.SynMembershipCardVO;

@Repository
public class SynMembershipCardDao extends BaseDAO {
	public int[] batchInsertSynMembershipCard(final List<SynMembershipCardVO> synMembershipCardVOs) {
		String sql = "INSERT INTO "
				+ "syn.syn_membership_card(merchant_id,merchant_name,brand_id,merchant_type,cardtype_id,card_count,card_price,is_sold_card,card_name,card_type,update_time) "
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SynMembershipCardVO synMembershipCardVO = synMembershipCardVOs.get(i);
				ps.setInt(1, synMembershipCardVO.getMerchantId());
				ps.setString(2, synMembershipCardVO.getMerchantName());
				if (synMembershipCardVO.getBrandId() != null) {
					ps.setInt(3, synMembershipCardVO.getBrandId());
				} else {
					ps.setNull(3, Types.INTEGER);
				}
				ps.setString(4, synMembershipCardVO.getMerchantType());
				if (synMembershipCardVO.getCardtypeId() != null) {
					ps.setInt(5, synMembershipCardVO.getCardtypeId());
				} else {
					ps.setNull(5, Types.INTEGER);
				}
				if (synMembershipCardVO.getCardCount() != null) {
					ps.setBigDecimal(6, synMembershipCardVO.getCardCount());
				} else {
					ps.setNull(6, Types.DECIMAL);
				}

				if (synMembershipCardVO.getCardPrice() != null) {
					ps.setBigDecimal(7, synMembershipCardVO.getCardPrice());
				} else {
					ps.setNull(7, Types.DECIMAL);
				}

				if (synMembershipCardVO.getIsSoldCard() != null) {
					String isSoldCard = synMembershipCardVO.getIsSoldCard();
					ps.setBoolean(8, isSoldCard.equals("true") ? true : false);
				} else {
					ps.setNull(8, Types.BOOLEAN);
				}
				ps.setString(9, synMembershipCardVO.getCardName());
				ps.setString(10, synMembershipCardVO.getCardType());
				ps.setTimestamp(11, new Timestamp(synMembershipCardVO.getUpdateTime().getTime()));
			}

			public int getBatchSize() {
				return synMembershipCardVOs.size();
			}
		};

		return jdbcTemplate.batchUpdate(sql, setter);
	}

	public int[] batchUpdateSynMembershipCard(final List<SynMembershipCardVO> synMembershipCardVOs) {
		String sql = "update  syn.syn_membership_card  set merchant_name=?,brand_id=?,merchant_type=?,card_count=?,card_price=?,is_sold_card=?,card_name=?,card_type=? ,update_time=?"
				+ " WHERE merchant_id=? and cardtype_id=?";
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SynMembershipCardVO synMembershipCardVO = synMembershipCardVOs.get(i);
				ps.setString(1, synMembershipCardVO.getMerchantName());
				if (synMembershipCardVO.getBrandId() != null) {
					ps.setInt(2, synMembershipCardVO.getBrandId());
				} else {
					ps.setNull(2, Types.INTEGER);
				}
				ps.setString(3, synMembershipCardVO.getMerchantType());
				if (synMembershipCardVO.getCardCount() != null) {
					ps.setBigDecimal(4, synMembershipCardVO.getCardCount());
				} else {
					ps.setNull(4, Types.DECIMAL);
				}

				if (synMembershipCardVO.getCardPrice() != null) {
					ps.setBigDecimal(5, synMembershipCardVO.getCardPrice());
				} else {
					ps.setNull(5, Types.DECIMAL);
				}
				if (synMembershipCardVO.getIsSoldCard() != null) {
					String isSoldCard = synMembershipCardVO.getIsSoldCard();
					ps.setBoolean(6, isSoldCard.equals("true") ? true : false);
				} else {
					ps.setNull(6, Types.BOOLEAN);
				}
				ps.setString(7, synMembershipCardVO.getCardName());
				ps.setString(8, synMembershipCardVO.getCardType());
				ps.setTimestamp(9, new Timestamp(synMembershipCardVO.getUpdateTime().getTime()));

				ps.setInt(10, synMembershipCardVO.getMerchantId());
				if (synMembershipCardVO.getCardtypeId() != null) {
					ps.setInt(11, synMembershipCardVO.getCardtypeId());
				} else {
					ps.setNull(11, Types.INTEGER);
				}
			}

			public int getBatchSize() {
				return synMembershipCardVOs.size();
			}
		};

		return jdbcTemplate.batchUpdate(sql, setter);
	}

	public List<Map<String, Object>> getSynMembershipCard() {
		String sql = "select * from syn.syn_membership_card;";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	public boolean deleteSynMembershipCard(Integer merchantId, Object cardtype_id) {
		String sql = "delete from syn.syn_membership_card where merchant_id =?";
		if (null != cardtype_id) {
			sql = sql + "and cardtype_id = ?";
			jdbcTemplate.update(sql, merchantId, cardtype_id);
		} else {
			sql = sql + "and cardtype_id is null;";
			jdbcTemplate.update(sql, merchantId);
		}
		return true;
	}
}
