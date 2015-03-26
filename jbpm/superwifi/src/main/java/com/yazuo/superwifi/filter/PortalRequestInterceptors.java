/*
 * 文件名：RequestParameterInteceptorInterceptor.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年7月30日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.filter;


import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yazuo.superwifi.login.vo.LoginLog;
import com.yazuo.superwifi.util.UrlUtils;



/**
 * Created with MyElipse.<br/> User: huijun.zheng<br/> Date: 2014-2-12<br/> Time: 下午6:30:54<br/> <br/>
 */
public class PortalRequestInterceptors extends HandlerInterceptorAdapter
{
    private static final Logger LOG = Logger.getLogger(PortalRequestInterceptors.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {

        // 1 资源文件不必响应 2 特殊的域名下的请求不必相应 3 特殊的UA不必相应
        String rmac = request.getParameter("rmac");
        String ssid = request.getParameter("ssid");
        String pcip = request.getParameter("pcip");
        String pcmac = request.getParameter("pcmac");
        String url = request.getParameter("url");
        String userAgent = request.getHeader("User-Agent");
        // 头信息为空的时候不做处理
        if (userAgent == null)
        {
            userAgent="";
        }
        // 头信息包含特殊字符的时候返回false
        // 比如qqlive 之类的，这个需要不断收集
        if (userAgent.indexOf("Apache-HttpClient") != -1 || userAgent.indexOf("qqlive") != -1
            || userAgent.indexOf("QQMusic") != -1 || userAgent.indexOf("IQIYI") != -1
            || userAgent.indexOf("dianping") != -1 || userAgent.toLowerCase().indexOf("weibo") != -1
            || userAgent.indexOf("YouShaQi") != -1 || userAgent.indexOf("IQIYI") != -1
            || userAgent.indexOf("QQMusic") != -1 || userAgent.indexOf("IQIYI") != -1)
        {
            LOG.info("userAgent:" + userAgent + "\nurl:" + url + "\nrmac:" + rmac + "\npcmac:" + pcmac);
            return false;
        }

        // 资源请求不处理
        if (UrlUtils.isResourceRequest(url))
        {
            return false;
        }

        // 苹果设备连接到WIFI时的请求不处理,交给service做处理，必要的时候
        if (UrlUtils.isIOSAutoRequest(url))
        {
            return true;
        }
        // 第一次来的请求返回true，并记录对应的数据到内存里
        LoginLog log = UrlUtils.portalMap.get(pcmac + rmac + userAgent);
        if (log == null)
        {
            log = new LoginLog();
            log.setDevice_mac(rmac);
            log.setUrl(url);
            log.setMember_mac(pcmac);
            log.setInsertTime(new Timestamp(System.currentTimeMillis()));
            UrlUtils.portalMap.put(pcmac + rmac + userAgent, log);
            return true;
        }
        else
        {
            // 如果之前已经有对应用户的访问记录 检查时间差，如果是三秒内访问过的，直接返回false
            if ((System.currentTimeMillis() - log.getInsertTime().getTime()) < 3000L)
            {
                log.setDevice_mac(rmac);
                log.setUrl(url);
                log.setMember_mac(pcmac);
                log.setInsertTime(new Timestamp(System.currentTimeMillis()));
                UrlUtils.portalMap.put(pcmac + rmac + userAgent, log);
                return false;
            }
            else
            {
                // 还没想好其他过滤方式
                log.setDevice_mac(rmac);
                log.setUrl(url);
                log.setMember_mac(pcmac);
                log.setInsertTime(new Timestamp(System.currentTimeMillis()));
                UrlUtils.portalMap.put(pcmac + rmac + userAgent, log);
                if (UrlUtils.portalMap.size() > 10000)
                {
                    UrlUtils.cleanPortalMap();
                }
                return true;
            }

        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView)
        throws Exception
    {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception
    {
        super.afterCompletion(request, response, handler, ex);
    }

}