package com.yazuo.superwifi.sms.dao;


import org.springframework.stereotype.Repository;

import com.yazuo.superwifi.sms.vo.SmsLog;


@Repository("smsLogDao")
public class SmsLogDao
{
    public SmsLog getLastSmsLogByMobileNumber(String mobileNumber)
        throws Exception
    {
            return null;
    }
}
