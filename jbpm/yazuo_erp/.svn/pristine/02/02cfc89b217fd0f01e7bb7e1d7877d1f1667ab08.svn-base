package com.yazuo.erp.train.service;

import com.yazuo.erp.train.JUnitBase;

import fes.service.FesOnlineProcessServiceTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 */
public class CalendarServiceTest extends JUnitBase {
	private static final Log LOG = LogFactory.getLog(CalendarServiceTest.class);
    @Resource
    private CalendarService calendarService;

    @Test
    public void testAfterHours() {
        System.out.println(this.calendarService.afterHours(1.5f));
        System.out.println(this.calendarService.afterHours(3));
        System.out.println(this.calendarService.afterHours(2));
        System.out.println(this.calendarService.afterHours(10));
        System.out.println(this.calendarService.afterHours(11));
        System.out.println(this.calendarService.afterHours(43));
        System.out.println(this.calendarService.afterDays(2));
    }

}
