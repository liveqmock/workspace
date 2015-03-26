package com.yazuo.weixin.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtil {
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * @param strIn
	 * @return
	 */
	public static boolean isNullOrEmpty(String strIn) {
		if (strIn == null || strIn.length() == 0 || strIn.trim().equalsIgnoreCase("null")) {
			return true;
		}
		return false;
	}
	 public static boolean isNumeric1(String str){
		  Pattern pattern = Pattern.compile("[0-9]*");
		  return pattern.matcher(str).matches();
	 }
	/**
	 * @param s
	 * @return
	 */
	public static String clearString(String s) {
		int beginIndex = s.indexOf(">");
		int endIndex = s.indexOf("</");
		String ret = s.substring(beginIndex + 1, endIndex);
		// System.out.println(ret);
		return ret;
	}

	/**
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long[] getUnixTimeStamps(Date date) throws ParseException {
		long[] res = new long[2];
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String startTime = year + "-" + month + "-" + day + " 00:00:00";
		String endTime = year + "-" + month + "-" + day + " 23:59:59";
		Calendar c2 = Calendar.getInstance();
		c2.setTime(df.parse(startTime));
		res[0] = c2.getTimeInMillis() / 1000;
		c2.setTime(df.parse(endTime));
		res[1] = c2.getTimeInMillis() / 1000;
		return res;
	}

	public static long[] getUnixTimeStamps(String startDate, String endDate) {
		long[] res = new long[2];
		try {
			res[0] = df.parse(startDate+" 00:00:00").getTime()/1000;
			res[1] = df.parse(endDate+" 23:59:59").getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	public static long[] getUnixTimeStamps(int dateType) {
		Calendar c = Calendar.getInstance();
		if(dateType == 1){
			String startTime = df1.format(c.getTime());
			String endTime = df1.format(c.getTime());
			return getUnixTimeStamps(startTime, endTime);
		}else if(dateType == 2){
			String endTime = df1.format(c.getTime());
			c.add(Calendar.DAY_OF_MONTH, -3);
			String startTime = df1.format(c.getTime());
			return getUnixTimeStamps(startTime, endTime);
		}
		return null;
	}
	public static String formatFromUTF8(String utf8Chart) throws IOException {
		StringReader sr = new StringReader(utf8Chart);
		BufferedReader br = new BufferedReader(sr);
		StringBuffer sb = new StringBuffer();
		for (String ss = ""; (ss = br.readLine()) != null;) {
			sb.append(ss.trim());
		}
		br.close();
		sr.close();
		return sb.toString();
	}

	/**
	 * @param date
	 * @param mins
	 * @return
	 */
	public static long[] getUnixTimeStamps(Date date, int mins) {
		long[] res = new long[2];
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		res[1] = c.getTimeInMillis() / 1000;
		c.add(Calendar.MINUTE, mins);
		res[0] = c.getTimeInMillis() / 1000;
		return res;
	}

	/**
	 * @param c
	 * @return
	 */
	public static long getUnixTimeStamps(Calendar c) {
		return c.getTimeInMillis() / 1000;
	}

	public static String[] getIdArr(String ids) {
		if (isNullOrEmpty(ids)) {
			return null;
		}
		ids = ids.substring(1, ids.length() - 1);
		return ids.split(",");
	}

	public static void main(String[] args) throws Exception {
		String s = "\u4e24\u4e2a\u5c0f\u7f8e\u98df\u5bb6\u5927\u5757\u5403";
		System.out.println(formatFromUTF8(s));
	}

}
