package base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTest {
	public static long FromYear() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.set(Calendar.DAY_OF_MONTH, 1);
		nowDate.set(Calendar.MONTH, 0);
		return nowDate.getTimeInMillis() / 1000;
	}

	public static long ToYear() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.set(Calendar.DAY_OF_MONTH, 1);
		nowDate.set(Calendar.MONTH, 12);
		return nowDate.getTimeInMillis() / 1000;
	}

	public static long FromMonth() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.set(Calendar.DAY_OF_MONTH, 1);
		return nowDate.getTimeInMillis() / 1000;
	}

	public static long ToMonth() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.set(Calendar.DAY_OF_MONTH, 1);
		nowDate.add(Calendar.MONTH, 1);
		return nowDate.getTimeInMillis() / 1000;
	}

	public static long FromWeek() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return nowDate.getTimeInMillis() / 1000;
	}

	public static long ToWeek() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		nowDate.add(Calendar.WEEK_OF_MONTH, 1);
		return nowDate.getTimeInMillis() / 1000;
	}

	public static long FromYesterday() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.add(Calendar.DAY_OF_MONTH, -1);
		return nowDate.getTimeInMillis() / 1000;
	}

	public static long ToYesterday() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		return nowDate.getTimeInMillis() / 1000;
	}

	public static long FromToday() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		return nowDate.getTimeInMillis() / 1000;
	}

	public static long ToToday() {
		Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.add(Calendar.DAY_OF_MONTH, 1);
		return nowDate.getTimeInMillis() / 1000;
	}

	/**
	 * startDate 格式 2009-02-03 startTime 格式 12:20
	 * */
	public static long FromTime(String startDate, String startTime) {
		Calendar nowDate = new java.util.GregorianCalendar();
		long fromtime = 0;
		if (!startDate.equals("")) {
			String[] s = startDate.split("-");
			nowDate = new java.util.GregorianCalendar();
			nowDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s[2]));
			nowDate.set(Calendar.MONTH, Integer.parseInt(s[1]) - 1);
			nowDate.set(Calendar.YEAR, Integer.parseInt(s[0]));

			String[] t = startTime.split(":");
			nowDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t[0]));
			nowDate.set(Calendar.MINUTE, Integer.parseInt(t[1]));
			nowDate.set(Calendar.SECOND, 0);
			fromtime = nowDate.getTimeInMillis() / 1000;
		}
		return fromtime;
	}

	/**
	 * endDate 格式 2009-02-03 endTime 格式 12:20
	 * */
	public static long ToTime(String endDate, String endTime) {
		Calendar nowDate = new java.util.GregorianCalendar();
		long totime = 0;
		if (!endDate.equals("")) {
			String[] s = endDate.split("-");
			nowDate = new java.util.GregorianCalendar();
			nowDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s[2]));
			nowDate.set(Calendar.MONTH, Integer.parseInt(s[1]) - 1);
			nowDate.set(Calendar.YEAR, Integer.parseInt(s[0]));

			String[] t = endTime.split(":");
			nowDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t[0]));
			nowDate.set(Calendar.MINUTE, Integer.parseInt(t[1]));
			nowDate.set(Calendar.SECOND, 0);
			totime = nowDate.getTimeInMillis() / 1000;
		}
		return totime;
	}

	// 格式化时间戳，参数为秒，不需要乘以1000
	public static String FormatTimeStamp(String pattern, long date) {
		if (pattern.length() == 0)
			pattern = "yyyy-MM-dd HH:mm:ss";
		java.util.Calendar nowDate = new java.util.GregorianCalendar();
		nowDate.setTimeInMillis(date * 1000);
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(nowDate.getTime());
	}

	public static void main(String args[]) {
		System.out.println("今天:");
		System.out.println(FormatTimeStamp("", FromToday()) + "---" + FormatTimeStamp("", ToToday()));
		System.out.println("昨天:");
		System.out.println(FormatTimeStamp("", FromYesterday()) + "---" + FormatTimeStamp("", ToYesterday()));
		System.out.println("本周:");
		System.out.println(FormatTimeStamp("", FromWeek()) + "---" + FormatTimeStamp("", ToWeek()));
		System.out.println("本月:");
		System.out.println(FormatTimeStamp("", FromMonth()) + "---" + FormatTimeStamp("", ToMonth()));
		System.out.println("本年:");
		System.out.println(FormatTimeStamp("", FromYear()) + "---" + FormatTimeStamp("", ToYear()));
		System.out.println("固定时间段：2008-02-28 00:00-----2009-03-31 12:22");
		System.out.println(FormatTimeStamp("", FromTime("2008-02-28", "00:00")) + "---"
				+ FormatTimeStamp("", ToTime("2009-03-31", "12:22")));
	}
}