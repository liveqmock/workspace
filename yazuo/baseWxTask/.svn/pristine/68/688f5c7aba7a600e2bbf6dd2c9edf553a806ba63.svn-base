/**
 * @Description 月报检查
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.bes.dao;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;

/**
 * @Description 月报检查
 * @author gaoshan
 * @date 2014-10-29 下午7:31:10
 */
@Repository
public class BesMonthlyCheckDao extends BaseDAO{

	/**
	 * @Description 根据月份删除ERP月报检查数据
	 * @param yearMonth
	 * @return int
	 * @throws
	 */
	public int deleteBesMonthlyCheckByMonth(String yearMonth) {
		String sql = " delete from bes.bes_monthly_check b where to_char(b.report_time,'yyyy-mm-dd') like '"+yearMonth+"-%'";
		return jdbcTemplate.update(sql);
	}

	/**
	 * @Description 批量执行sql
	 * @param addSqls
	 * @return int
	 * @throws
	 */
	public int batchExecuteSql(String sqls) {
		return jdbcTemplate.update(sqls);
	}
	
}
