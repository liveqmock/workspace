package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.TraCalendarVO;
import junit.framework.Assert;
import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *节假日相关描述：
 *
 *2014年全年公休假放假安排_中国政府网
	节日	放假时间	调休上班日期	放假天数
	元旦	1月1日	无	1天
	春节	1月31日~2月6日	1月26日（周日）、2月8日（周六）	7天
	清明节	4月5日~7日	无	3天
	劳动节	5月1日~3日	5月4日（周日）	3天
	端午节	5月31日~6月2日	无	3天
	中秋节	9月6日~8日	无	3天
	国庆节	10月1日~7日	9月28日（周日）、10月11日（周六）	7天
	
	
 */
public class CalendarDaoTest extends JUnitBase {
    @Resource
    private TraCalendarDao traCalendarDao;

    @Test
    public void init() {
    	//从哪一年开始计算
        int year = 2014;
        int month = 11; //十二月
        int date = 1;
    	int maxYear = 2015;//计算到哪一年
        TraCalendarVO calendarVO;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        List<Integer> playdays = Arrays.asList(new Integer[]{Calendar.SUNDAY, Calendar.SATURDAY});
        for (int i = 0; i < 600; i++) {
            calendarVO = new TraCalendarVO();
            Calendar tmpCalendar = (Calendar) calendar.clone();
			tmpCalendar.set(year, month, date);
            tmpCalendar.add(Calendar.DATE, i);
            if (tmpCalendar.get(Calendar.YEAR) > maxYear) {
                break;
            }
            calendarVO.setCalendarDate(tmpCalendar.getTime());
            if (playdays.contains(tmpCalendar.get(Calendar.DAY_OF_WEEK))) {
                calendarVO.setIsPlayday("1");
                calendarVO.setRemark("周末");
            } else {
                calendarVO.setIsPlayday("0");
            }
            this.traCalendarDao.insertCalendarVO(calendarVO);
        }
    }

    @Test
    public void test() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = this.traCalendarDao.nthWorkdays(calendar.getTime(), 1);
        Assert.assertNotNull(date);
    }



    @Test
    public void addCalendarsFor2015() throws ParseException {
        String[] playday = new String[]{
                "2015-01-01","2015-01-02","2015-01-03",
                "2015-02-18","2015-02-19","2015-02-20","2015-02-21","2015-02-22","2015-02-23","2015-02-24",
                "2015-04-06",
                "2015-05-01",
                "2015-06-22",
                "2015-09-27",
                "2015-10-01","2015-10-02","2015-10-05","2015-10-06","2015-10-07"
        };
        String[] workday = new String[]{
                "2015-01-04","2015-02-15","2015-02-28",
                "2015-10-10"
        };
        this.addCalendars(2015,playday,workday);
    }

    /**
     *
     * @param year
     * @param playdays
     * @param workdays
     */
    protected void addCalendars(int year, String[] playdays, String[] workdays) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar = DateUtils.truncate(calendar, Calendar.YEAR);
        Calendar nextYear = Calendar.getInstance();
        nextYear.setTime(calendar.getTime());
        nextYear.add(Calendar.YEAR, 1);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);

        // 正常添加节假日
        List<Date> playdayList = new LinkedList<Date>();
        for (String playdayStr : playdays) {
            playdayList.add(dateFormat.parse(playdayStr));
        }

        List<Date> workdayList = new LinkedList<Date>();
        for (String workdayStr : workdays) {
            workdayList.add(dateFormat.parse(workdayStr));
        }
        List<Integer> weekend = Arrays.asList(new Integer[]{Calendar.SUNDAY, Calendar.SATURDAY});
        while (calendar.before(nextYear)) {
            TraCalendarVO calendarVO = new TraCalendarVO();
            calendarVO.setCalendarDate(calendar.getTime());
            boolean isPlayday = weekend.contains(calendar.get(Calendar.DAY_OF_WEEK));
            calendarVO.setIsPlayday(isPlayday ? "1" : "0");
            calendarVO.setRemark(isPlayday ? "周末" : null);

            //修改工作日与调休日
            if (workdayList.contains(calendar.getTime())) {
                calendarVO.setIsPlayday("0");
                calendarVO.setRemark("调休");
            }
            if (playdayList.contains(calendar.getTime())) {
                calendarVO.setIsPlayday("1");
                calendarVO.setRemark(null);
            }
            System.out.println(calendarVO);
            this.traCalendarDao.insertCalendarVO(calendarVO);
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
    }


    @Test
    public void testIsWorkday() {
        boolean isWorkday = this.traCalendarDao.isWorkday(toDate(new Date()));
        Assert.assertNotNull(isWorkday);
    }


    @After
    public void destroy() {

    }

    private Date toDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date currentDay() {
        return toDate(new Date());
    }
    /*//春节放假三天，定义到2020年
    public boolean holidayOfCN(String year){
     List ls = new ArrayList();
//     Arrays.asList("2014-01-01", )
     ls.add("2014-01-31");ls.add("2014-02-01");ls.add("2014-02-02");
     ls.add("2015-02-19");ls.add("2015-02-20");ls.add("2015-02-21");
     ls.add("2006-02-08");ls.add("2006-02-09");ls.add("2006-02-10");
     ls.add("2017-01-28");ls.add("2017-01-29");ls.add("2017-01-30");
     ls.add("2018-02-16");ls.add("2018-02-17");ls.add("2018-02-18");
     ls.add("2019-02-05");ls.add("2019-02-06");ls.add("2019-02-07");
     ls.add("2020-01-25");ls.add("2020-01-26");ls.add("2020-01-27");
     if(ls.contains(year))
      return true;
     return false; 
    }
    //法定假日，五一和国庆
    public boolean holidayList(String findDate){
     List ls = new ArrayList();
     ls.add("05-01");
     ls.add("05-02");
     ls.add("05-03");
     ls.add("10-01");
     ls.add("10-02");
     ls.add("10-03");
     if(ls.contains(findDate))
      return true;
     return false; 
   }*/
}
