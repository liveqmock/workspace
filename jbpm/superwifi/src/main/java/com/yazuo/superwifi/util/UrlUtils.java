/*
 * 文件名：UrlUtils.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2015年2月4日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.util;


import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.yazuo.superwifi.filter.PortalRequestInterceptors;
import com.yazuo.superwifi.login.vo.LoginLog;


public class UrlUtils
{
    private static final Logger LOG = Logger.getLogger(PortalRequestInterceptors.class);

    public static Map<String, LoginLog> portalMap = new ConcurrentHashMap<String, LoginLog>();

    public static boolean isIOSAutoRequest(String url)
    {
        if (url.indexOf("captive.apple.com") != -1 || url.indexOf("www.ibook.info") != -1
            || url.indexOf("www.itools.info") != -1 || url.indexOf("www.airport.us") != -1
            || url.indexOf("appleiphonecell.com") != -1 || url.indexOf("thinkdifferent.us") != -1
            || url.indexOf("www.apple.com") != -1 || url.indexOf("www.airport.us") != -1)
        {
            return true;
        }
        return false;
    }

    public static boolean isResourceRequest(String url)
    {
        if (url == null || url.endsWith(".jpg") || url.endsWith(".GPG") || url.endsWith(".gif") || url.endsWith(".GIF")
            || url.endsWith(".js") || url.endsWith(".JS") || url.endsWith(".css") || url.endsWith(".CSS")
            || url.endsWith(".png") || url.endsWith(".PNG") || url.endsWith(".swf") || url.endsWith(".SWF")
            || url.endsWith(".apk") || url.endsWith(".APK") || url.endsWith(".swf") || url.endsWith(".SWF"))
        {
            return true;
        }
        return false;

    }

    public static void cleanPortalMap()
    {
        Long start = System.currentTimeMillis();
        LOG.info("清理开始：" + start + "\nportalMap size" + portalMap.size());
        Iterator<String> iterator = portalMap.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            LoginLog log = portalMap.get(key);
            if ((System.currentTimeMillis() - log.getInsertTime().getTime()) > 10000)
            {
                portalMap.remove(key);
            }
        }
        Long end = System.currentTimeMillis();
        LOG.info("清理结束：" + end + "\nportalMap size" + portalMap.size() + "\n耗时" + (end - start));
    }
}
