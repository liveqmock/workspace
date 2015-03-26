/*
 * 文件名：LoginController.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年8月5日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.login.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.superwifi.exception.BussinessException;
import com.yazuo.superwifi.login.service.LoginService;
import com.yazuo.superwifi.util.UrlUtils;


@Controller
@RequestMapping("/controller/login")
public class LoginController
{
    @Resource(name = "loginServiceImpl")
    private LoginService loginService;

    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    Logger log = Logger.getLogger(this.getClass());

    @RequestMapping(value = "requestPassword", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object requestPassword(String mobileNumber, String deviceMac, String userMac, String deviceSSID)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isEmpty(mobileNumber) || StringUtils.isEmpty(deviceMac) || StringUtils.isEmpty(userMac))
        {
            throw new BussinessException("param is empty");
        }

        Object o = loginService.requestPassword(mobileNumber, deviceMac, userMac, deviceSSID);
        result.put("status", "success");
        result.put("data", o);
        return result;
    }

    @RequestMapping(value = "login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object login(String mobileNumber, String password, String deviceMac, String userMac, String deviceSSID)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isEmpty(mobileNumber) || StringUtils.isEmpty(deviceMac) || StringUtils.isEmpty(userMac)
            || StringUtils.isEmpty(password) || StringUtils.isEmpty(deviceSSID))
        {
            throw new BussinessException("param is empty");
        }
        try
        {
            Map<String, Object> resultMap = loginService.login(mobileNumber, password, deviceMac, userMac, deviceSSID);
            result.put("status", "success");
            result.put("url", resultMap.get("url").toString());
            result.put("data", (Integer)resultMap.get("brandId"));
            return result;
        }
        catch (Exception e)
        {
            log.error("login error", e);
            result.put("status", "error");
            result.put("data", e.getMessage());
            return result;
        }
    }

    @RequestMapping(value = "portal", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView portal(HttpServletResponse response, String pcip, String pcmac, String rmac, String url,
                               String ssid, HttpServletRequest request)
        throws Exception
    {
        
        return loginService.portal(pcip, pcmac, rmac, url, ssid, request);
    }
    
    @RequestMapping(value = "cleanPortalMap", method = {RequestMethod.GET})
    @ResponseBody
    public void cleanPortalMap()
        throws Exception
    {
        
        UrlUtils.portalMap.clear();
    }

}
