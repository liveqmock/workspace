/**
 * @Description 
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.fes.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;

/**
 * @Description 上线计划
 * @author zhaohuaqin
 * @date 2014-8-4 上午11:01:34
 */
@Repository
public class FesOnlineProgramDao extends BaseDAO {

	/**
	 * 查询未上线商户上线计划
	 * 
	 * @return
	 * @return List<FesOnlineProgramVO>
	 * @throws
	 */
	public List<Map<String, Object>> getNotOnlineMerchant(String stepNum, String date) {
		String sql = "SELECT program. ID, program.merchant_id,program.begin_time,process.step_id,process.online_process_status FROM "
				+ SCHEMANAME_FES
				+ ".fes_online_program AS program, "
				+ SCHEMANAME_FES
				+ ".fes_online_process AS process "
				+ " WHERE program.id = process.program_id AND program.online_program_status = '0' AND process.step_id IN( "
				+ " SELECT id FROM "
				+ SCHEMANAME_FES
				+ ".fes_step WHERE is_enable = '1' AND step_num =?) AND EXISTS("
				+ " SELECT * FROM "
				+ SCHEMANAME_SYN
				+ ".syn_merchant AS mer WHERE mer.merchant_id = program.merchant_id AND mer.status = 1) AND to_char(program.begin_time,'yyyy-mm-dd')= ?";
		return jdbcTemplate.queryForList(sql, stepNum, date);
	}
}
