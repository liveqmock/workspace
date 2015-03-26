package com.yazuo.erp.syn.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.syn.vo.SynMerchantProductVO;

@Repository
public class SynMerchantProductDao extends BaseDAO {

	public int[] batchInsertSynMerchantProduct(final List<SynMerchantProductVO> synMerchantProductVOs) {
		String sql = "INSERT INTO " + "syn.syn_merchant_product(merchant_id,product_id,product_name,update_time) "
				+ " VALUES(?,?,?,?)";
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SynMerchantProductVO synMerchantProductVO = synMerchantProductVOs.get(i);
				ps.setInt(1, synMerchantProductVO.getMerchantId());
				ps.setInt(2, synMerchantProductVO.getProductId());
				ps.setString(3, synMerchantProductVO.getProductName());
				ps.setTimestamp(4, new Timestamp(synMerchantProductVO.getUpdateTime().getTime()));
			}

			public int getBatchSize() {
				return synMerchantProductVOs.size();
			}
		};

		return jdbcTemplate.batchUpdate(sql, setter);
	}

	public int[] batchUpdateSynMerchantProduct(final List<SynMerchantProductVO> synMerchantProductVOs) {
		String sql = "update  syn.syn_merchant_product  set product_name=?,update_time=?"
				+ " WHERE merchant_id=? and product_id=?";
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SynMerchantProductVO synMerchantProductVO = synMerchantProductVOs.get(i);
				ps.setString(1, synMerchantProductVO.getProductName());
				ps.setTimestamp(2, new Timestamp(synMerchantProductVO.getUpdateTime().getTime()));
				ps.setInt(3, synMerchantProductVO.getMerchantId());
				ps.setInt(4, synMerchantProductVO.getProductId());
			}

			public int getBatchSize() {
				return synMerchantProductVOs.size();
			}
		};

		return jdbcTemplate.batchUpdate(sql, setter);
	}

	public List<Map<String, Object>> getSynMerchantProduct() {
		String sql = "SELECT * FROM syn.syn_merchant_product;";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	public int deleteSynMerchantProduct(Integer merchantId, Integer productId) {
		String sql = "delete from syn.syn_merchant_product where merchant_id =? and product_id = ?";
		return jdbcTemplate.update(sql, merchantId, productId);
	}
}
