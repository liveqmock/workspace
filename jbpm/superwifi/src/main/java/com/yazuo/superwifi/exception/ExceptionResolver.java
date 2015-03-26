/*
 * 文件名：ExceptionResolver.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年8月15日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


public class ExceptionResolver extends SimpleMappingExceptionResolver
{
    public static final String ERROR_PAGE = "/mobile/online-form-error";

    private static final Logger log = Logger.getLogger(ExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                              Exception ex)
    {
        log.error("捕获到系统异常", ex);
        String viewName = determineViewName(ex, request);
        if (ex instanceof BussinessException)
        {
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader(
                "X-Requested-With").indexOf("XMLHttpRequest") > -1)))
            {
                // 如果不是异步请求
                return new ModelAndView(ERROR_PAGE);
            }
            else
            {// JSON格式返回
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("status", "failed");
                result.put("data", ex.toString());
                try
                {
                    PrintWriter writer = response.getWriter();
                    writer.write(JSONObject.fromObject(result).toString());
                    writer.flush();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;

            }
        }
        else
        {
            ex.printStackTrace();
            return new ModelAndView(ERROR_PAGE);
        }
    }
}
