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

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;

/**
 * @Description
 * @author zhaohuaqin
 * @date 2014-8-4 下午3:19:39
 */
@Repository
public class FesOnlineProcessDao extends BaseDAO {

	public List<FesOnlineProcessVO> getFesOnlineProcessByProgramIdAndStepId(Integer programId, String stepNum) {
		String sql = "SELECT process.* FROM fes.fes_online_process AS process,fes.fes_step AS step WHERE process.program_id = ? "
				+ " AND process.step_id = step.id  AND step.step_num =? AND step.is_enable = '1'";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<FesOnlineProcessVO>(FesOnlineProcessVO.class), programId,
				stepNum);
	}
}
