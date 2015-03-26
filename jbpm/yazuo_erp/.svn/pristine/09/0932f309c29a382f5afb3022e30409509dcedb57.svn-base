package com.yazuo.erp.train.service;

import java.util.Calendar;
import java.util.Date;

/**
 * 日历功能
 */
public interface CalendarService {
    /**
     * hoursNum小时之后,小数会转换成分数
     *
     * @param hoursNum
     * @return
     */
    Date afterHours(Float hoursNum);

    /**
     * hoursNum小时之后
     *
     * @param hoursNum
     * @return
     */
    Date afterHours(Integer hoursNum);

    /**
     * daysNum天之后
     *
     * @param daysNum
     * @return
     */
    Date afterDays(Integer daysNum);
    
    /**
     * daysNum天之后
     *
     * @param calendar
     * @param daysNum
     * @return
     */
    Date afterDays(Calendar calendar, Integer daysNum);

    /**
     * daysNum之前，不计算时间
     * @param date
     * @param daysNum 大于0
     * @return
     */
    Date beforeDays(Date date, Integer daysNum);

    /**
     * 今天之前的第daysNum个工作日,不计算时间
     * @param date
     * @param daysNum
     * @return
     */
    Date beforeDays(Integer daysNum);
}
