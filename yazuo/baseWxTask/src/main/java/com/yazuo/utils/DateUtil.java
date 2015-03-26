/**
 * <p>Project: weixin</p>
 * <p></p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2013-12-09
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	/**
	 * 1000毫秒*60秒*60分*24小时=一天毫秒数
	 */
	public static final long MILLISECOND_DAY = 86400000;

	/**
	 * 1年=31536000秒
	 */
	public static final int SECOND_YEAR = 31536000;

	/**
	 * 将日期格式"yyyy-MM-dd"的字符串转化为Date
	 * 
	 * @param date
	 * @param amount
	 * @return String
	 * @throws ParseException
	 */
	public static java.sql.Date formateDate(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = simpleDateFormat.parse(date);
		return new java.sql.Date(utilDate.getTime());

	}

	/**
	 * 将指定日期往后amount月数
	 * 
	 * @param date
	 * @param amount
	 * @return String
	 * @throws ParseException
	 */
	public static String formermonth(String date, int amount) throws ParseException {
		SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(year.parse(date));
		c.add(Calendar.MONTH, amount);
		return year.format(c.getTime());

	}

	/**
	 * 将指定日期往后amount月数
	 * 
	 * @param date
	 * @param amount
	 * @return String
	 * @throws ParseException
	 */
	public static Date formerDateMonth(Date date, int amount) throws ParseException {
		SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, +amount);
		return c.getTime();

	}

	/**
	 * 将指定日期往后amount天数
	 * 
	 * @param date
	 * @param amount
	 * @return String
	 * @throws ParseException
	 */
	public static String formerday(String date, int amount) throws ParseException {
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
	 * 将日期类型转换为字符类型[19位：yyyy-MM-dd HH:mm:ss]
	 * 
	 * @param date
	 * @return String
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将日期类型转换为字符类型[10位：yyyy-MM-dd]
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDay(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 将字符类型转换为日期类型
	 * 
	 * @param date
	 * @param pattern
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parse(String date, String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).parse(date);
	}

	/**
	 * 将字符类型[19位：yyyy-MM-dd HH:mm:ss]转换为日期类型
	 * 
	 * @param date
	 * @return String
	 * @throws ParseException
	 */
	public static Date parse(String date) throws ParseException {
		return parse(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将字符类型[yyyy-MM-dd]转换为字符串日期类型
	 * 
	 * @param date
	 * @return String
	 * @throws ParseException
	 */
	public static String getDateString(String date) throws ParseException {
		return format(parse(date, "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/**
	 * 获得当前日期时间字符[19位：yyyy-MM-dd HH:mm:ss]
	 */
	public static String getDate() {
		return format(new Date());
	}

	/**
	 * 获得当前日期字符[7位：yyyy-MM]
	 */
	public static String getDate4m() {
		return format(new Date(), "yyyy-MM");
	}

	/**
	 * 获得当前日期字符[10位：yyyy-MM-dd]
	 */
	public static String getDate4d() {
		return format(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 获得当前时间字符[8位：HH:mm:ss]
	 */
	public static String getDate4t() {
		return format(new Date(), "HH:mm:ss");
	}

	/**
	 * 获得指定步进<b>分钟</b>数后的日期
	 * 
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date stepMinute(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, amount);
		return calendar.getTime();
	}

	/**
	 * 获得指定步进<b>小时</b>数后的日期
	 * 
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date stepHourOfDay(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, amount);
		return calendar.getTime();
	}

	/**
	 * 获得指定步进<b>周</b>数后的日期
	 * 
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date stepWeekOfMonth(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_MONTH, amount);
		return calendar.getTime();
	}

	/**
	 * 获得指定步进<b>天</b>数后的日期
	 * 
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date stepDay(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, amount);
		return calendar.getTime();
	}

	/**
	 * 获得指定步进<b>月</b>数后的日期
	 * 
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date stepMonth(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, amount);
		return calendar.getTime();
	}

	/**
	 * 获得指定步进<b>月</b>数后的日期
	 * 
	 * @param date
	 * @param amount
	 * @return Date
	 */
	public static Date stepYear(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, amount);
		return calendar.getTime();
	}

	/**
	 * 获得上一个月的日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousMonth(Date date) {
		return stepMonth(date, -1);
	}

	/**
	 * 获得上几个月的日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousSeveralMonth(Date date, int count) {
		return stepMonth(date, -count);
	}

	/**
	 * 获得上一年的日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousYear(Date date) {
		return stepYear(date, -1);
	}

	/**
	 * 获得下一个月的日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getNextMonth(Date date) {
		return stepMonth(date, 1);
	}

	/**
	 * 获得上一天的日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousDay(Date date) {
		return stepDay(date, -1);
	}

	/**
	 * 获得下一天的日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getNextDay(Date date) {
		return stepDay(date, 1);
	}

	/**
	 * 日期相减，返回长整型数值
	 * 
	 * @param begin
	 * @param end
	 * @return long
	 */
	public static long subDate(Date begin, Date end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		long beginTime = calendar.getTimeInMillis();
		calendar.setTime(end);
		long endTime = calendar.getTimeInMillis();
		return beginTime - endTime;
	}

	/**
	 * 日期相减，返回长整型数值
	 * 
	 * @param begin
	 * @param end
	 * @return long
	 * @throws ParseException
	 */
	public static long subDate(String begin, String end) throws ParseException {
		return subDate(parse(begin), parse(end));
	}

	/**
	 * 日期[yyyy-MM-dd]相减，返回日差
	 * 
	 * @param beginDay
	 * @param endDay
	 * @return long
	 * @throws ParseException
	 */
	public static long subDay(Date beginDay, Date endDay) throws ParseException {
		return subDay(format(beginDay, "yyyy-MM-dd"), format(endDay, "yyyy-MM-dd"));
	}

	/**
	 * 日期[yyyy-MM-dd]相减，返回日差
	 * 
	 * @param beginDay
	 * @param endDay
	 * @return long
	 * @throws ParseException
	 */
	public static long subDay(String beginDay, String endDay) throws ParseException {
		return subDate(parse(beginDay, "yyyy-MM-dd"), parse(endDay, "yyyy-MM-dd")) / MILLISECOND_DAY;
	}

	/**
	 * 时间[HH:mm:ss]相减，返回秒差
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return long
	 * @throws ParseException
	 */
	public static long subTime(String beginTime, String endTime) throws ParseException {
		return subDate(parse(beginTime, "HH:mm:ss"), parse(endTime, "HH:mm:ss")) / 1000;
	}

	/**
	 * 时间[HH:mm]相减，返回秒差
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return long
	 * @throws ParseException
	 */
	public static long subShortTime(String beginTime, String endTime) throws ParseException {
		return subDate(parse(beginTime, "HH:mm"), parse(endTime, "HH:mm")) / 1000;
	}

	/**
	 * 时间[HH:mm:ss]相减，返回秒差
	 * 
	 * @param nowTime
	 * @param beginTime
	 * @return long
	 * @throws ParseException
	 *             86400000 是一天的毫秒数
	 */
	public static long subTime(Date nowTime, Date beginTime, int step) {
		return nowTime.getTime() - (beginTime.getTime() + 86400000 * step);
	}

	/**
	 * 获得日期月份的天数
	 * 
	 * @param date
	 * @return int
	 */
	public static int getDaysOfMonth(Date date) {
		final int[] DaysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (date.getMonth() + 1 == 2)
			return (date.getYear() % 4 == 0 && date.getYear() % 100 != 0) || date.getYear() % 400 == 0 ? 29 : 28;
		else
			return DaysOfMonth[date.getMonth()];
	}

	/**
	 * 获得日期月份的英文名称
	 * 
	 * @param date
	 * @return String
	 */
	public static String getNameOfMonth(Date date) {
		final String[] NameOfMonth = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		return NameOfMonth[getMonth(date) - 1];
	}

	/**
	 * 获得日期的年份
	 * 
	 * @param date
	 * @return int
	 */
	public static int getYear(Date date) {
		return date.getYear() + 1900;
	}

	/**
	 * 获得日期的月份
	 * 
	 * @param date
	 * @return int
	 */
	public static int getMonth(Date date) {
		return date.getMonth() + 1;
	}

	/**
	 * 获得日期的号数
	 * 
	 * @param date
	 * @return int
	 */
	public static int getDay(Date date) {
		return date.getDate();
	}

	/**
	 * 获得日期的星期数
	 * 
	 * @param date
	 * @return int
	 */
	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekNum = calendar.get(Calendar.DAY_OF_WEEK);
		if (weekNum == 1) {
			weekNum = 8;
		}
		return weekNum - 1;
	}

	/**
	 * 获得当周的周一
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getMonday(Date date) {
		return stepDay(date, 1 - getDayOfWeek(date));
	}

	/**
	 * 获得当周的周日
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getSunday(Date date) {
		return stepDay(date, 7 - getDayOfWeek(date));
	}

	public static String roize(int number) {
		return roize(Integer.toString(number));
	}

	public static String roize(String number) {
		return number != null && number.length() < 2 ? ("0" + number) : number;
	}

	/**
	 * 获得日期的年
	 * 
	 * @param date
	 * @return int
	 */
	public static int getYear(String date) {
		return Integer.parseInt(date.substring(0, 4));
	}

	/**
	 * 获得日期的月
	 * 
	 * @param date
	 * @return int
	 */
	public static int getMonth(String date) {
		return Integer.parseInt(date.substring(5, 7));
	}

	/**
	 * 获得日期的号
	 * 
	 * @param date
	 * @return int
	 */
	public static int getDay(String date) {
		return Integer.parseInt(date.substring(8, 10));
	}

	/**
	 * 获得年月日
	 * 
	 * @param date
	 * @return String
	 */
	public static String getYearToDay(String date) {
		return date.substring(0, 10);
	}

	/**
	 * 获得日期的时
	 * 
	 * @param date
	 * @return int
	 */
	public static int getHour(String date) {
		return Integer.parseInt(date.substring(11, 13));
	}

	/**
	 * 获得日期的分
	 * 
	 * @param date
	 * @return int
	 */
	public static int getMinute(String date) {
		return Integer.parseInt(date.substring(14, 16));
	}

	/**
	 * 获得日期的秒
	 * 
	 * @param date
	 * @return int
	 */
	public static int getSecond(String date) {
		return Integer.parseInt(date.substring(17, 19));
	}

	/**
	 * 获得日期的时分秒
	 * 
	 * @param date
	 * @return int
	 */
	public static String getTime(String date) {
		return date.substring(11, 19);
	}

	/**
	 * 设置日期的时分秒为00:00:00，并返回
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getMinimum4Time(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 设置日期的天为当月第一天，并返回
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getMinimum4Day(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 设置日期的时分秒为23:59:59，并返回
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getMaximum4Time(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 设置日期的天为当月第一天，并返回
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getMaximum4Day(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		return calendar.getTime();
	}

	/**
	 * 获得用于BETWEEN条件的最早日期
	 */
	public static Date getBetweenDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.getMinimum(Calendar.YEAR));
		calendar.set(Calendar.MONTH, calendar.getMinimum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	public static void main(String args[]) throws Exception {
		// Date now = new Date(), monday = getMonday(now), sunday =
		// getSunday(now);
		// System.out.println("NOW = " + format(now));
		// System.out.println("MONDAY = " + format(monday));
		// System.out.println("SUNDAY = " + format(sunday));
		// System.out.println("MIN = " + format(getMinimum4Time(now)));
		// System.out.println("MAX = " + format(getMaximum4Time(now)));
		// System.out.println("SUNDAY - MONDAY = " + subDate(sunday, monday)
		// / MILLISECOND_DAY);

		// Date date = getPreviousDay(new Date());
		//
		// String ss = formatDay(date);
		// System.out.println(formatDay(date));

		SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(year.parse("2012-01-01"));
		c.add(Calendar.MONTH, +1);
		String formermonth = year.format(c.getTime());
		System.out.println(formermonth);

	}

	public static String getDateSetTime(String date, String hour, String minute, String second) {
		return date + " " + createTime(hour, minute, second);
	}

	public static String getDateSetDate(String year, String month, String day, String time) {
		return createDate(year, month, day) + " " + time;
	}

	public static String createTime(String hour, String minute, String second) {
		return roize(hour) + ":" + roize(minute) + ":" + roize(second);
	}

	public static String createDate(String year, String month, String day) {
		return roize(year) + "-" + roize(month) + "-" + roize(day);
	}

	/**
	 * 时间[HH:mm:ss]相减，返回秒差
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return long
	 * @throws ParseException
	 */
	public static long subTime2(String beginTime, String endTime) throws ParseException {
		return subDate(parse(beginTime, "HH:mm"), parse(endTime, "HH:mm")) / 1000;
	}

	public static String getHourM(long time) {
		long hour = time / 3600;
		long min_a = time % 3600;
		long min = min_a / 60;
		// long N = time/3600;
		// s = s%3600;
		// int K = s/60;
		// s = s%60;
		// int M = s;

		return hour + "小时 " + min + "分钟  ";
	}

	/**
	 * 日期比较
	 * 
	 * @param
	 * @return
	 */
	public static boolean compare_date(String date1, String date2) {
		SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = year.parse(date1);
			Date d2 = year.parse(date2);
			if (d1.getTime() > d2.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public static Date getNowYearBegin() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(simpleFormate.format(calendar.getTime()) + " 00:00:00");
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
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(simpleFormate.format(calendar.getTime()) + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowMonthBegin() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(simpleFormate.format(calendar.getTime()) + " 00:00:00");
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
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(simpleFormate.format(calendar.getTime()) + " 23:59:59");
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

		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(simpleFormate.format(System.currentTimeMillis()) + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getNowDayEnd() {
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleFormate2.parse(simpleFormate.format(System.currentTimeMillis()) + " 23:59:59");
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

}
