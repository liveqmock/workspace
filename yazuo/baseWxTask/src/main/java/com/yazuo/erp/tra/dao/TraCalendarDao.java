package com.yazuo.erp.tra.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.BaseDAO;

@Repository
public class TraCalendarDao extends BaseDAO {
	public Map<String, Object> beforeDays(Date date, Integer daysNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar = this.cleanCalendar(calendar);

		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT                                              ");
		sql.append("	MIN(calendar_date)	as calendar_date			");
		sql.append("FROM						");
		sql.append("	(						");
		sql.append("		SELECT					");
		sql.append("			calendar_date			");
		sql.append("		FROM					");
		sql.append("			train.tra_calendar		");
		sql.append("		WHERE					");
		sql.append("			calendar_date < ?	");
		sql.append("		AND is_playday = '0'			");
		sql.append("    ORDER BY calendar_date DESC			");
		sql.append("    LIMIT ?						");
		sql.append("	)AS cdate					");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), calendar.getTime(), daysNum);
		if (null != list && list.size() > 0) {
			Map<String, Object> map = list.get(0);
			return map;
		}
		return null;
	}

	private Calendar cleanCalendar(Calendar calendar) {
		Calendar result = (Calendar) calendar.clone();
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result;
	}
}
