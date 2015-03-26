package com.yazuo.util;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLUtil {

	public static String replaceSPChar(String str) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static String MYSQLRPChar(String str) {
		String newKey = str.replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"");
		return newKey;
	}

	/**
	 * @param str
	 * @return
	 */
	public static String PGSQLRPChar(String str) {
		if (StringUtil.isNullOrEmpty(str)) {
			return null;
		}
		return "E'" + str.trim().replaceAll("'", "''").replace("\\", "\\\\") + "'";
	}

	public static String formatDBStr(List<String> list, boolean needComma) {
		String retStr = "";
		for (String s : list) {
			if (needComma) {
				retStr += "'" + s.trim() + "',";
			} else {
				retStr += s + ",";
			}

		}
		if (StringUtil.isNullOrEmpty(retStr)) {
			return null;
		}
		return retStr.substring(0, retStr.lastIndexOf(","));
	}

	public static void main(String[] args) {
		List<String> a = new ArrayList<String>();
		a.add("aas");
		a.add("sssff");
		String s = formatDBStr(a, false);
		System.out.println(s);

	}

	/**
	 * 获取分页SQL
	 * @param sql	原始SQL
	 * @param page	页码, 从1开始
	 * @param pageSize	分页大小
	 * @return
	 */
	public static String getPageSql(String sql, int page, int pageSize) {
		int start = (page - 1) * pageSize;
		int end = start + pageSize;
		return getLimitSql(sql, start, end);
	}

	/**
	 * getLimitSql(分页支持)
	 *
	 * @Title: getLimitString
	 * @param @param sql
	 * @param @param startIndex
	 * @param @param endIndex
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	*/
	public static String getLimitSql(String sql, int startIndex, int endIndex) {
		//MYSQL
		System.out.println("page SQL: " + sql + " limit " + endIndex + " offset " + startIndex);
		return sql + " limit " + endIndex + " offset " + startIndex;
	}
}