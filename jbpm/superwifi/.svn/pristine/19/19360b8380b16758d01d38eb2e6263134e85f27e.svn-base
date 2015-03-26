package com.yazuo.superwifi.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;


public class TimeUtil
{
    private static Logger log = Logger.getLogger(TimeUtil.class);

    /**
     * 根据long类型返回 Timestamp 本方法返回的 时分秒是写死的，为了匹配超级报表数据库内的默认Timestamp 2014-03-28 08:00:00.0
     * 
     * @param currentTimeMillis
     * @return
     */
    public static Timestamp getTimestampData(Long currentTimeMillis)
        throws Exception
    {
        Date date = new Date(currentTimeMillis);
        // format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date) + " 08:00:00";
        Timestamp ts = Timestamp.valueOf(dateStr);
        return ts;
    }

    /**
     * 根据long类型返回 Timestamp 本方法返回的 时分秒是写死的，为了匹配超级报表数据库内的默认Timestamp 2014-03-28 08:00:00.0
     * 
     * @param currentTimeMillis
     * @return
     */
    public static Timestamp getTimestampData(Date now)
        throws Exception
    {
        // format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(now) + " 08:00:00";
        Timestamp ts = Timestamp.valueOf(dateStr);
        return ts;
    }

    /**
     * 根据now 类型返回 String 本方法返回的 时分秒是写死的，为了匹配超级报表数据库内的默认Timestamp 2014-03-28 08:00:00.0 --->>2014-03-28
     * 
     * @param currentTimeMillis
     * @return
     */
    public static String getDataToString(Timestamp now)
        throws Exception
    {
        // format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(now);

        return dateStr;
    }
    
    public static String getDateToString(Date now)
            throws Exception
        {
            // format的格式可以任意
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(now);

            return dateStr;
        }

    public static String getFormatString(Timestamp ts)
        throws Exception
    {
        // format的格式可以任意
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = sdf.format(ts);
        return dateStr;
    }

    /**
     * 根据long类型返回 Timestamp 本方法返回的 时分秒是写死的，为了匹配超级报表数据库内的默认Timestamp 2014-03-28 08:00:00.0
     * 
     * @param currentTimeMillis
     * @return
     */
    public static Timestamp getTimestampData(String date)
        throws Exception
    {
        date += " 08:00:00";

        Timestamp ts = Timestamp.valueOf(date);
        return ts;
    }

    public static boolean isSameDay(Timestamp t1, Timestamp t2)
    {
        if (null == t1 || null == t2)
        {
            log.error("参数不可为空");
            return false;
        }
        return t1.toString().split(" ")[0].equals(t2.toString().split(" ")[0]);
    }

    /**
     * 字符串到Timestamp的转换 例如 2014-03-01 --->>2014-03-01 23:59:59.0
     * 
     * @param timeString
     *            要转换的字符串
     * @param isEnd
     *            是否是当天的最后一秒 true的时候 返回2014-03-01 23:59:59.0 false的时候返回2014-03-01 00:00:01.0
     * @return
     */
    public static Timestamp string2Timestamp(String timeString, Boolean isEnd)
        throws Exception
    {
        timeString = timeString.replace("/", "-").replace("\\", "-");
        String end = " 00:00:01";
        if (isEnd)
        {
            end = " 23:59:59";
        }
        String dateStr = timeString + end;

        Timestamp ts = Timestamp.valueOf(dateStr);
        return ts;
    }

    public static Timestamp date2Timestamp(Date date, Boolean isEnd)
        throws Exception
    {
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        String timeString = format.format(date);
        String end = " 00:00:01";
        if (isEnd)
        {
            end = " 23:59:59";
        }
        String dateStr = timeString + end;

        Timestamp ts = Timestamp.valueOf(dateStr);
        return ts;
    }

    /**
     * 得到？天前的Timestamp实例
     * 
     * @param timeString
     * @param days
     * @return
     */
    public static Date getTimestampSeveralDaysAgo(Date today, int days)
        throws Exception
    {
        return new Date(today.getTime() - days * 24 * 3600 * 1000L);
    }

    /**
     * 获取某个日期的上个月
     * 
     * @param date
     * @return
     * @see
     */
    public static Date lastMonth(Date date)
        throws Exception
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取某个日期的下个月
     * 
     * @param date
     * @return
     * @see
     */
    public static Date nextMonth(Date date)
        throws Exception
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month)
        throws Exception
    {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取年月的第一天
     */
    public static String getFirstDayByYearMonth(String date)
        throws Exception
    {
        date = date.replace("/", "-").replace("\\", "-");
        date += "-01";
        return date;
    }

    /**
     * 根据日期获取星期
     */
    public static String getWeek(Date date)
    {
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0)
        {
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 获得指定指定日期几天数或后的日期
     */
    public static Date stepDay(Date date, int amount)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }
}
