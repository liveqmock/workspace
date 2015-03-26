/**
 * @Description Sql相关工具类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Description Sql相关工具类
 * @date 2014-8-11 上午10:59:38
 */
public class SqlUtil {

	Log log = LogFactory.getLog(this.getClass());

	/**
	 * @throws Exception
	 * @Description 生成批量插入sql
	 * @param maps
	 * @throws
	 */
	public static String generateBatchInsertSql(List<Map<String, Object>> maps) throws Exception {
		StringBuilder sqls = new StringBuilder();
		for (Map<String, Object> m : maps) {
			StringBuilder columns = new StringBuilder("(");
			StringBuilder values = new StringBuilder("(");
			StringBuilder sql = new StringBuilder();

			String tableName = (String) m.get("tableName");
			if (null == tableName) {
				throw new Exception("请确定操作的表名"); 
			}

			sql.append("insert into ").append(tableName).append(" ");
			m.remove("tableName");

			if (m.size() < 1) {
				throw new Exception("列个数异常");
			}
			
			for (Map.Entry<String, Object> entry : m.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				columns.append(key).append(",");
				if (null == value) {
					values.append("NULL").append(",");
				}
				if (value instanceof Integer) {
					values.append(value).append(",");
				} else if (value instanceof String) {
					values.append("'").append(value).append("',");
				} else if (value instanceof Date) {
					values.append("'").append(value).append("',");
				} else if (value instanceof BigDecimal) {
					values.append("'").append(value).append("',");
				} else if (value instanceof Long) {
					values.append("'").append(value).append("',");
				} else {
					values.append("'").append(value).append("',");
				}
			}
			columns.deleteCharAt(columns.length() - 1);
			values.deleteCharAt(values.length() - 1);
			columns.append(")");
			values.append(")");
			sql.append(columns.toString()).append(" values ").append(values.toString()).append("; ").append("\n");
			sqls.append(sql);
		}
		System.out.println(sqls.toString());
		return sqls.toString();
	}

	/**
	 * @throws Exception
	 * @Description 生成批量更新sql
	 * @param maps
	 * @throws
	 */
	public static String generateBatchUpdateSql(List<Map<String, Object>> maps) throws Exception {
		StringBuilder sqls = new StringBuilder();
		for (Map<String, Object> m : maps) {
			StringBuilder values = new StringBuilder();
			StringBuilder sql = new StringBuilder();
		
			String tableName = (String) m.get("tableName");
			if (null == tableName) {
				throw new Exception("请确定操作的表名");
			}
			
			sql.append("update ").append(tableName).append(" set ");
			m.remove("tableName");
			
			if (m.size() < 2) {
				throw new Exception("列个数异常");
			}
			
			Object id = null;
			for (Map.Entry<String, Object> entry : m.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				values.append(key).append(" = ");
				if (null == value) {
					values.append("NULL").append(",");
				}
				if (value instanceof Integer) {
					values.append(value).append(",");
				} else if (value instanceof String) {
					values.append("'").append(value).append("',");
				} else if (value instanceof Date) {
					values.append("'").append(value).append("',");
				} else if (value instanceof BigDecimal) {
					values.append("'").append(value).append("',");
				} else if (value instanceof Long) {
					values.append("'").append(value).append("',");
				} else {
					values.append("'").append(value).append("',");
				}

				if ("id".equals(key)) {
					id = value;
				}
			}
			values.deleteCharAt(values.length() - 1);
			sql.append(values.toString()).append(" where id = '").append(id).append("'; ").append("\n");
			sqls.append(sql);
		}
		System.out.println(sqls.toString());
		return sqls.toString();
	}
	
}
