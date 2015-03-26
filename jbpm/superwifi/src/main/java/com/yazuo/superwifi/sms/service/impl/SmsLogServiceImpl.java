package com.yazuo.superwifi.sms.service.impl;


import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.yazuo.superwifi.sms.dao.SmsLogDao;
import com.yazuo.superwifi.sms.service.SmsLogService;
import com.yazuo.superwifi.sms.vo.SmsLog;


@Service("smsLogServiceImpl")
public class SmsLogServiceImpl implements SmsLogService
{
    @Resource(name="mongoTemplate")
    private MongoTemplate mongoTemplate; 

    public void addSmsLOg(SmsLog smsLog)
        throws Exception
    {
        mongoTemplate.insert(smsLog);
    }

}
