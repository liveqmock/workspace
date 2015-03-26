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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.syn.vo.SynHealthDegreeVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-12 下午1:39:26
 */
@Repository
public class SynHealthDegreeDao extends BaseDAO {
	public int[] batchInsertSynHealthDegree(final List<SynHealthDegreeVO> synMerchantVOs) {
		String sql = "insert into syn.syn_health_degree(merchant_id,target_type,target_value,actual_value,health_degree,report_time,update_by,update_time) values(?,?,?,?,?,?,?,?)";
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SynHealthDegreeVO synHealthDegreeVO = synMerchantVOs.get(i);
				ps.setInt(1, synHealthDegreeVO.getMerchantId());
				ps.setString(2, synHealthDegreeVO.getTargetType());
				ps.setBigDecimal(3, synHealthDegreeVO.getTargetValue());
				ps.setBigDecimal(4, synHealthDegreeVO.getActualValue());
				ps.setBigDecimal(5, synHealthDegreeVO.getHealthDegree());
				ps.setDate(6, new java.sql.Date(synHealthDegreeVO.getReportTime().getTime()));
				ps.setInt(7, synHealthDegreeVO.getUpdateBy());
				ps.setTimestamp(8, new Timestamp(synHealthDegreeVO.getUpdateTime().getTime()));
			}

			public int getBatchSize() {
				return synMerchantVOs.size();
			}
		};

		return jdbcTemplate.batchUpdate(sql, setter);
	}

	public List<Map<String, Object>> getSynHealthDegreeByIndexIdAndMonth(String indexId, Date startDate, Date endDate) {
		String sql = "SELECT id,merchant_id AS brand_id,target_type,to_char(report_time , 'yyyy-mm')  as month ,target_value,actual_value FROM syn.syn_health_degree WHERE target_type = ? and report_time >= ? and report_time < ?;";
		return jdbcTemplate.queryForList(sql, indexId, startDate, endDate);
	}
	
	public int deleteSynHealthDegreeByIndexIdAndMonth(String indexId, Date startDate, Date endDate) {
		String sql = "delete FROM syn.syn_health_degree WHERE target_type = ? and report_time >= ? and report_time < ?;";
		return jdbcTemplate.update(sql, indexId, startDate, endDate);
	}

	public List<Map<String, Object>> getSynHealthDegree(String indexId, Date date) {
		String sql = "SELECT id,merchant_id AS brand_id,target_type,to_char(report_time , 'yyyy-mm')  as month ,target_value,actual_value FROM syn.syn_health_degree WHERE target_type = ? and report_time = ?;";
		return jdbcTemplate.queryForList(sql, indexId, date);
	}
	
	public int deleteSynHealthDegreeByIndexIdAndCurrentMonth(String indexId, Date date) {
		String sql = "delete FROM syn.syn_health_degree WHERE target_type = ? and report_time = ? ;";
		return jdbcTemplate.update(sql, indexId, date);
	}

	public int[] batchUpdateSynHealthDegree(final List<SynHealthDegreeVO> synMerchantVOs) {
		String sql = "update syn.syn_health_degree set merchant_id=?,target_type=?,target_value=?,actual_value=?,health_degree=?,report_time=?,update_by=?,update_time=? where id =?";
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SynHealthDegreeVO synHealthDegreeVO = synMerchantVOs.get(i);
				ps.setInt(1, synHealthDegreeVO.getMerchantId());
				ps.setString(2, synHealthDegreeVO.getTargetType());
				ps.setBigDecimal(3, synHealthDegreeVO.getTargetValue());
				ps.setBigDecimal(4, synHealthDegreeVO.getActualValue());
				ps.setBigDecimal(5, synHealthDegreeVO.getHealthDegree());
				ps.setDate(6, new java.sql.Date(synHealthDegreeVO.getReportTime().getTime()));
				ps.setInt(7, synHealthDegreeVO.getUpdateBy());
				ps.setTimestamp(8, new Timestamp(synHealthDegreeVO.getUpdateTime().getTime()));
				ps.setInt(9, synHealthDegreeVO.getId());
			}

			public int getBatchSize() {
				return synMerchantVOs.size();
			}
		};

		return jdbcTemplate.batchUpdate(sql, setter);
	}
}
