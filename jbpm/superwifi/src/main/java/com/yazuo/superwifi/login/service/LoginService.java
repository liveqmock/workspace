/*
 * 文件名：LoginService.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年8月5日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.login.service;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.yazuo.superwifi.merchant.vo.HomePage;


public interface LoginService
{
    Map<String, Object> requestPassword(String mobileNumber, String deviceMac, String userMac,String deviceSSID)
        throws Exception;

    Map<String, Object> login(String mobileNumber, String password, String deviceMac, String userMac, String deviceSSID)
        throws Exception;

    void macRelease(String pcmac, String rmac, String ssid,String userMobile)
        throws Exception;

    String sendIdentifyinginfo(String mobileNumber, Integer brandId)
        throws Exception;

    Integer getBrandIdByDeviceMac(String deviceMac)
        throws Exception;

    HomePage getHomePageInfo(Integer brandId);

    ModelAndView portal(String pcip, String pcmac, String rmac, String url, String ssid, HttpServletRequest request)
        throws Exception;
    
}
