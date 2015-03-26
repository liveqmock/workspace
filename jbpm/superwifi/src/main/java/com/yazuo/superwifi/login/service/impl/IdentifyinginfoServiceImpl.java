/*
 * 文件名：IdentifyinginfoServiceImpl.java 版权：Copyright by www.yazuo.com 描述： 修改人：zhaohuaqin 修改时间：2014-12-26 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.login.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.yazuo.superwifi.login.service.IdentifyinginfoService;
import com.yazuo.superwifi.login.vo.Identifyinginfo;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhaohuaqin
 * @version 2014-12-26
 * @see IdentifyinginfoServiceImpl
 * @since
 */
@Service("identifyinginfoServiceImpl")
public class IdentifyinginfoServiceImpl implements IdentifyinginfoService
{

    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public List<Identifyinginfo> getIdentifyinginfoByMobileAndIdentifyingCode(String mobileNumber,
                                                                              String identifyingCode, Integer merchantId)
    {
        Query query = new Query(
            Criteria.where("mobileNumber").is(mobileNumber).and("identifyingCode").is(identifyingCode).and("merchantId").is(
                merchantId).and("status").is(Identifyinginfo.STATUS_NOUSE).and("flag").is(
                Identifyinginfo.FLAG_MEMBERLOGIN));
        query.sort().on("insertTime", Order.DESCENDING);
        return mongoTemplate.find(query, Identifyinginfo.class);
    }

    @Override
    public void updateIdentifyinginfo(Map<String, Object> map)
    {
        String id = map.get("id").toString();
        Integer status = (Integer)map.get("status");
        Query query = new Query(Criteria.where("id").is(id));
        Update update = Update.update("status", status);
        if (null != status && status.intValue() == Identifyinginfo.STATUS_USED)
        {
            update.set("passTime", new Date());
        }
        mongoTemplate.updateFirst(query, update, Identifyinginfo.class);
    }

    @Override
    public void saveIdentifyinginfo(Identifyinginfo identifyinginfo)
    {
        mongoTemplate.insert(identifyinginfo);
    }

}
