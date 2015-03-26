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
package com.yazuo.erp.sys.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.sys.vo.SysRemindVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-4 下午3:58:28
 */
@Repository
public class SysRemindDao extends BaseDAO {
	public int[] batchInsertSysRemind(final List<SysRemindVO> sysRemindVOs) {
		String sql = "INSERT INTO "
				+ SCHEMANAME_SYS
				+ ".sys_remind(user_id,merchant_id,priority_level_type,item_type,item_content,item_status,process_id,remark,is_enable,insert_by,insert_time,update_by,update_time) "
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SysRemindVO sysRemindVO = sysRemindVOs.get(i);
				ps.setInt(1, sysRemindVO.getUserId());
				if (sysRemindVO.getMerchantId() != null) {
					ps.setInt(2, sysRemindVO.getMerchantId());
				} else {
					ps.setNull(2, Types.INTEGER);
				}

				ps.setString(3, sysRemindVO.getPriorityLevelType());
				ps.setString(4, sysRemindVO.getItemType());
				ps.setString(5, sysRemindVO.getItemContent());
				ps.setString(6, sysRemindVO.getItemStatus());
				if (sysRemindVO.getProcessId() != null) {
					ps.setInt(7, sysRemindVO.getProcessId());
				} else {
					ps.setNull(7, Types.INTEGER);
				}

				ps.setString(8, sysRemindVO.getRemark());
				ps.setString(9, sysRemindVO.getIsEnable());

				ps.setInt(10, sysRemindVO.getInsertBy());
				ps.setTimestamp(11, new Timestamp(sysRemindVO.getInsertTime().getTime()));
				ps.setInt(12, sysRemindVO.getUpdateBy());
				ps.setTimestamp(13, new Timestamp(sysRemindVO.getUpdateTime().getTime()));
			}

			public int getBatchSize() {
				return sysRemindVOs.size();
			}
		};

		return jdbcTemplate.batchUpdate(sql, setter);
	}
}
