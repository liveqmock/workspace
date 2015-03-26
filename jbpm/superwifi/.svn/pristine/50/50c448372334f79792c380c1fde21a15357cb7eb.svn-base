/*
 * 文件名：RequestParameterInteceptorInterceptor.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年7月30日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.filter;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



/**
 * Created with MyElipse.<br/> User: huijun.zheng<br/> Date: 2014-2-12<br/> Time: 下午6:30:54<br/> <br/>
 */
public class RequestParameterInteceptor extends HandlerInterceptorAdapter
{
    private static final Logger LOG = Logger.getLogger(RequestParameterInteceptor.class);

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
        String method = request.getMethod();
        String path = request.getRequestURL().toString();
        // if(null!=path&&path.endsWith("login/portal.do")){
        // return true;
        // }
        StringBuilder sb = new StringBuilder("\n******************本次" + method + "请求" + path
                                             + "的参数如下************************\n");
        sb.append("\tUser-Agent：" + request.getHeader("User-Agent") + "\n");
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements())
        {
            String name = en.nextElement();
            sb.append("\t参数名getParameter：" + name + ",参数值：" + request.getParameter(name) + "\n");
        }

        LOG.info(sb.toString());
        return true;
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