package com.yazuo.weixin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	public static Date getNowYearBegin() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2
					.parse(simpleFormate.format(calendar.getTime())
							+ " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowYearEnd() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2
					.parse(simpleFormate.format(calendar.getTime())
							+ " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowMonthBegin() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2
					.parse(simpleFormate.format(calendar.getTime())
							+ " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowMonthEnd() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2
					.parse(simpleFormate.format(calendar.getTime())
							+ " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowWeekBegin() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);

		SimpleDateFormat simpleFormate2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(preMonday + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowWeekEnd() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(preMonday + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowDayBegin() {
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(simpleFormate.format(System
					.currentTimeMillis()) + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowDayEnd() {
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(simpleFormate.format(System
					.currentTimeMillis()) + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static boolean isDate(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 将指定日期往后amount天数
	 * 
	 * @param date
	 * @param amount
	 * @return String
	 * @throws ParseException
	 */
	public static String formerday(String date, int amount)
			throws ParseException {
		SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(year.parse(date));
		c.add(Calendar.DATE, +amount);
		return year.format(c.getTime());

	}
	
	/**
	 * 将日期类型转换为字符类型
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 获得当前日期字符[10位：yyyy-MM-dd]
	 */
	public static String getDate4d() {
		return format(new Date(), "yyyy-MM-dd");
	}
	/**
	 * 获得当前日期字符[14位：yyyyMMddHHmmss]
	 */
	public static String getDate() {
		return format(new Date(), "yyyyMMddHHmmss");
	}
	/**
	 * 获得当前日期字符[12位：yyMMddHHmmss]
	 */
	public static String get12Date() {
		return format(new Date(), "yyMMddHHmmss");
	}
}
