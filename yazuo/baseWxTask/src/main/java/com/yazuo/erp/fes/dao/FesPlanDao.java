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
package com.yazuo.erp.fes.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.fes.vo.FesPlanVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-4 下午3:19:39
 */
@Repository
public class FesPlanDao extends BaseDAO {

	public List<FesPlanVO> getFesPlan(String remindTime) {
		String sql = "SELECT  * FROM " + SCHEMANAME_FES
				+ ".fes_plan WHERE is_remind is TRUE AND is_enable = '1' AND status = '1' "
				+ " AND to_char(remind_time ,'yyyy-mm-dd HH24:mi:ss') =?";

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<FesPlanVO>(FesPlanVO.class), remindTime);
	}
}
