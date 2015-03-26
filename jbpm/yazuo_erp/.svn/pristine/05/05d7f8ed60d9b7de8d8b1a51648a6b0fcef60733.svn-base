package com.yazuo.erp.train.service.impl;

import com.yazuo.erp.train.dao.TraCalendarDao;
import com.yazuo.erp.train.service.CalendarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 */
@Service
public class CalendarServiceImpl implements CalendarService {

    @Resource
    private TraCalendarDao traCalendarDao;
    
    public Date afterHours(Float hoursNum) {
        //工作日以内设置
        Calendar resultCalendar = Calendar.getInstance();
        resultCalendar.setTimeInMillis(System.currentTimeMillis());
    	return this.afterHours(resultCalendar, hoursNum);
    }

    @Override
    public Date afterDays(Calendar calendar, Integer daysNum) {
        return this.afterHours(calendar, daysNum * 8.0f);
    }

    @Override
    public Date beforeDays(Date date, Integer daysNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar = this.cleanCalendar(calendar);
        return this.traCalendarDao.nthFormerWorkdays(calendar.getTime(),daysNum);
    }

    @Override
    public Date beforeDays(Integer daysNum) {
        return this.beforeDays(new Date(),daysNum);
    }

    protected Date afterHours(Calendar resultCalendar,Float hoursNum) {
        int totalHours = hoursNum.intValue();
        int days = totalHours / 8;
        int hours = totalHours % 8;
        int minutes = new Float((hoursNum - days * 8 - hours) * 60).intValue();

        // 设置计算的基础开始时间(设置为工作日)
        Calendar currentCalendar = this.cleanCalendar(resultCalendar);
        if (!this.isWorkday(currentCalendar)) {
            Date firstDate = this.firstWorkday(currentCalendar);
            resultCalendar.setTime(firstDate);
            resultCalendar.set(Calendar.HOUR_OF_DAY, 9);
        }

        // 计算时间
        Calendar calcCalendar;
        if (resultCalendar.get(Calendar.HOUR_OF_DAY) < 12) {//上午
            calcCalendar = (Calendar) resultCalendar.clone();
            calcCalendar.add(Calendar.HOUR_OF_DAY, hours);
            calcCalendar.add(Calendar.MINUTE, minutes);
            if (this.greaterThan(calcCalendar,12)) {
                hours += 1;
                calcCalendar.add(Calendar.HOUR, 1);
                if (this.greaterThan(calcCalendar, 18)) {
                    hours += 15;
                }
            }
        } else { //下午
            calcCalendar = (Calendar) resultCalendar.clone();
            calcCalendar.add(Calendar.HOUR_OF_DAY, hours);
            calcCalendar.add(Calendar.MINUTE, minutes);
            if (this.greaterThan(calcCalendar,18)) {
                hours += 15;
                calcCalendar.add(Calendar.HOUR_OF_DAY,15);
                if (this.greaterThan(calcCalendar,12)) {
                    hours += 1;
                }
            }

        }

        // 计算休息天数
        Calendar currentDay = (Calendar) resultCalendar.clone();
        currentDay = this.cleanCalendar(currentDay);
        Date nextworkday = this.traCalendarDao.nthWorkdays(currentDay.getTime(), 1);
        Calendar nextWorkday = Calendar.getInstance();
        nextWorkday.setTime(nextworkday);

        hours += this.daySpace(nextWorkday, currentDay) * 24;

        resultCalendar.add(Calendar.HOUR_OF_DAY, hours);
        resultCalendar.add(Calendar.MINUTE,minutes);

        //计算天数
        Calendar tmpCalendar = this.cleanCalendar((Calendar) resultCalendar.clone());
        Date r = this.traCalendarDao.nthWorkdays(tmpCalendar.getTime(), days + 1);
        Calendar kk = Calendar.getInstance();
        kk.setTime(r);
        resultCalendar.set(Calendar.YEAR, kk.get(Calendar.YEAR));
        resultCalendar.set(Calendar.MONTH, kk.get(Calendar.MONTH));
        resultCalendar.set(Calendar.DAY_OF_MONTH,kk.get(Calendar.DAY_OF_MONTH));
        return resultCalendar.getTime();
    }

    @Override
    public Date afterHours(Integer hoursNum) {
        return this.afterHours(hoursNum*1.0f);
    }

    @Override
    public Date afterDays(Integer daysNum) {
        return this.afterHours(daysNum * 8);
    }

    public boolean isWorkday(Calendar calendar) {
        return this.isWorkday(calendar.getTime());
    }

    public boolean isWorkday(Date date) {
        return this.traCalendarDao.isWorkday(date);
    }

    private Calendar cleanCalendar(Calendar calendar) {
        Calendar result = (Calendar) calendar.clone();
        result.set(Calendar.HOUR_OF_DAY, 0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);
        return result;
    }
    private Date firstWorkday(Calendar calendar){
        return this.traCalendarDao.nthWorkdays(calendar.getTime(), 1);
    }

    private Integer daySpace(Calendar first, Calendar second) {
        return (int) ((first.getTime().getTime() - second.getTime().getTime()) / 1000 / 3600 / 24);
    }

    private boolean greaterThan(Calendar calendar, Integer hour) {
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        return (h > hour) || (h == hour && calendar.get(Calendar.MINUTE) > 0);
    }

}
