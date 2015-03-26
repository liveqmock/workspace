package com.yazuo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by IntelliJ IDEA. User: lixueliang Date: 11-10-28 Time: 下午2:48 To
 * change this template use File | Settings | File Templates.
 */
public class DateUtils {
	/**
	 * Date 类型转化为XMLGregorianCalendar类型
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date)
			throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		javax.xml.datatype.DatatypeFactory dtf = javax.xml.datatype.DatatypeFactory
				.newInstance();
		return dtf.newXMLGregorianCalendar(calendar.get(calendar.YEAR),
				calendar.get(calendar.MONTH) + 1,
				calendar.get(calendar.DAY_OF_MONTH),
				calendar.get(calendar.HOUR), calendar.get(calendar.MINUTE),
				calendar.get(calendar.SECOND),
				calendar.get(calendar.MILLISECOND),
				calendar.get(calendar.ZONE_OFFSET) / (1000 * 60));
	}

	/**
	 * 将日期字符串转化为需要格式的日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 *            格式字符串
	 * @return 转换后的日期对象
	 */
	public static Date strToFormatDate(String date, String format) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date, new ParsePosition(0));
	}

	/**
	 * 将字符串转换为yyyy-MM-dd格式的日期
	 * 
	 * @param date
	 * @return 转换后的日期对象
	 */
	public static Date strToDate(String date) {
		return DateUtils.strToFormatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 将字符串转换为yyyy-MM-dd HH:mm:ss格式的日期
	 * 
	 * @param date
	 * @return 转换后的日期对象
	 */
	public static Date strToDateTime(String date) {
		return DateUtils.strToFormatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将date型日期转换成特定格式的时间字符串
	 * 
	 * @param date
	 * @param format
	 * @return 转换后的日期对象
	 */
	public static String dateToFormatStr(Date date, String format) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将date型日期转换成yyyy-MM-dd HH:mm:ss格式的时间字符串
	 * 
	 * @param date
	 *            日期
	 * @return 返回yyyy-MM-dd HH:mm格式的时间字符串
	 */
	public static String dateTimeToStr(Date date) {
		return DateUtils.dateToFormatStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将date型日期转换成yyyy-MM-dd格式的日期字符串
	 * 
	 * @param date
	 *            日期
	 * @return 返回yyyy-MM-dd格式的日期字符串
	 */
	public static String dateToStr(Date date) {
		return DateUtils.dateToFormatStr(date, "yyyy-MM-dd");
	}

	/**
	 * 计算出date day天之前或之后的日期
	 * 
	 * @param date
	 *            {@link java.util.Date} 日期
	 * @param days
	 *            int 天数，正数为向后几天，负数为向前几天
	 * @return 返回Date日期类型
	 */
	public static Date getDateBeforeOrAfterDays(Date date, int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		return now.getTime();
	}

	/**
	 * 计算出date monthes月之前或之后的日期
	 * 
	 * @param date
	 *            日期
	 * @param monthes
	 *            月数，正数为向后几天，负数为向前几天
	 * @return 返回Date日期类型
	 */
	public static Date getDateBeforeOrAfterMonthes(Date date, int monthes) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + monthes);
		return now.getTime();
	}

	/**
	 * 计算出date years年之前或之后的日期
	 * 
	 * @param date
	 *            日期
	 * @param years
	 *            年数，正数为向后几年，负数为向前几年
	 * @return 返回Date日期类型
	 */
	public static Date getDateBeforeOrAfterYears(Date date, int years) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) + years);
		return now.getTime();
	}

	/**
	 * 计算两个日期之间的天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return 如果beginDate 在 endDate之后返回负数 ，反之返回正数
	 */
	public static int daysOfTwoDate(Date beginDate, Date endDate) {

		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();

		beginCalendar.setTime(beginDate);
		endCalendar.setTime(endDate);

		return daysOfTwoDate(beginCalendar, endCalendar);

	}

	/**
	 * 计算两个日期之间的天数
	 * 
	 * @param d1
	 * @param d2
	 * @return 如果d1 在 d2 之后返回负数 ，反之返回正数
	 */
	public static int daysOfTwoDate(Calendar d1, Calendar d2) {
		int days = 0;
		int years = d1.get(Calendar.YEAR) - d2.get(Calendar.YEAR);
		if (years == 0) {// 同一年中
			days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
			return days;
		} else if (years > 0) {// 不同一年
			for (int i = 0; i < years; i++) {
				d2.add(Calendar.YEAR, 1);
				days += -d2.getActualMaximum(Calendar.DAY_OF_YEAR);
				if (d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) {
					days += d2.get(Calendar.DAY_OF_YEAR)
							- d1.get(Calendar.DAY_OF_YEAR);
					return days;
				}
			}
		} else
			for (int i = 0; i < -years; i++) {
				d1.add(Calendar.YEAR, 1);
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				if (d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) {
					days += d2.get(Calendar.DAY_OF_YEAR)
							- d1.get(Calendar.DAY_OF_YEAR);
					return days;
				}
			}
		return days;
	}

	/**
	 * 获得当前时间当天的开始时间，即当前给出的时间那一天的00:00:00的时间
	 * 
	 * @param date
	 *            当前给出的时间
	 * @return 当前给出的时间那一天的00:00:00的时间的日期对象
	 */
	public static Date getDateBegin(Date date) {
		SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			try {
				return DateFormat.getDateInstance(DateFormat.DEFAULT,
						Locale.CHINA).parse(ymdFormat.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 获得当前时间当天的结束时间，即当前给出的时间那一天的23:59:59的时间
	 * 
	 * @param date
	 *            当前给出的时间
	 * @return 当前给出的时间那一天的23:59:59的时间的日期对象
	 */
	public static Date getDateEnd(Date date) {
		SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			try {
				date = getDateBeforeOrAfterDays(date, 1);
				date = DateFormat.getDateInstance(DateFormat.DEFAULT,
						Locale.CHINA).parse(ymdFormat.format(date));
				Date endDate = new Date();
				endDate.setTime(date.getTime() - 1000);
				return endDate;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 取得当月天数
	 */
	public static int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取指定日期为当年周数
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekNumber(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.WEEK_OF_YEAR);

	}

	/**
	 * 获取指定日期的月数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthNumber(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取若干天前后的日期
	 * 
	 * @param days
	 *            天数（可以为负，为负时取若干天前的日期，为正时取若干天后的日期）
	 * @return
	 */
	public static Date getDateRelativeToToday(int days) {
		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(new Date());

		grc.add(GregorianCalendar.DATE, days);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return grc.getTime();
	}

	/**
	 * 获取若干月前后的日期
	 * 
	 * @param days
	 *            月数（可以为负，为负时取若干天月的日期，为正时取若干月后的日期）
	 * @return
	 */
	public static Date getMonthRelativeToToday(int months) {
		GregorianCalendar grc = new GregorianCalendar();
		grc.setTime(new Date());
		grc.add(GregorianCalendar.MONTH, months);
		return grc.getTime();
	}

	public static String getMonthStrRelativeToToday(int months) {
		Date date = getMonthRelativeToToday(months);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		return sdf.format(date);
	}

	/**
	 * 获取若干天前后的日期
	 * 
	 * @param days
	 *            天数（可以为负，为负时取若干天前的日期，为正时取若干天后的日期）
	 * @return
	 */
	public static String getDateStrRelativeToToday(int days) {
		Date date = getDateRelativeToToday(days);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(date);
	}

	public static void main(String args[]) {
		System.out.println(getMonthStrRelativeToToday(12));
	}

}
