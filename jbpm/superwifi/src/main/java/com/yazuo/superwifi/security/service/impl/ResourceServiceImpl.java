/*
 * 文件名：ResourceServiceImpl.java 版权：Copyright by www.yazuo.com 描述： 修改人：zhaohuaqin 修改时间：2014-12-17 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.security.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.superwifi.security.dao.ResourceDao;
import com.yazuo.superwifi.security.service.ResourceService;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhaohuaqin
 * @version 2014-12-17
 * @see ResourceServiceImpl
 * @since
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService
{
    Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ResourceDao resourceDao;

    @Value("#{propertiesReader['superwiki_productId']}")
    Integer productId;

    @Override
    public List<Map<String, Object>> listResources()
    {
        return resourceDao.listAllResources(productId);
    }

    @Override
    public List<Integer> getAuthoritiesByUserId(Integer userId)
    {
        List<Integer> list = resourceDao.getAuthoritiesByUserId(userId, productId);
        return list;
    }
}
