package com.yazuo.erp.train.vo;

import java.util.Date;

/**
 * 日历类
 */
public class TraCalendarVO {

    private Date calendarDate;
    private String isPlayday;
    private String remark;

    public Date getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(Date calendarDate) {
        this.calendarDate = calendarDate;
    }

    public String getIsPlayday() {
        return isPlayday;
    }

    public void setIsPlayday(String isPlayday) {
        this.isPlayday = isPlayday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TraCalendarVO{" +
                "calendarDate=" + calendarDate +
                ", isPlayday='" + isPlayday + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
