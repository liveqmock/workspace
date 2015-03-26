package com.yazuo.superwifi.security.service.spring;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler
{
    private static final Logger LOG = Logger.getLogger(LoginAuthenticationFailureHandler.class);

    // private String defaultUrl = "/jsp/login_front.jsp?flag=1";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException authentication)
        throws IOException, ServletException
    {
        LOG.info("-------LoginAuthenticationFailureHandler.onAuthenticationFailure()------------验证失败！！！");
        String message = "用户名或密码错误，请重新输入!";
        Throwable cause = authentication.getCause();
        if (null != cause)
        {
            message = cause.getMessage();
            LOG.info(message);
        }
      
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", 0);
        map.put("message", message);
        JSONObject responseJSONObject = JSONObject.fromObject(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(responseJSONObject.toString());
    }

}
