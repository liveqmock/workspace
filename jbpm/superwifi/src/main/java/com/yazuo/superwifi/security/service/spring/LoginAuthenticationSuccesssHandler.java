package com.yazuo.superwifi.security.service.spring;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.yazuo.superwifi.security.vo.PhoneUserDetails;


public class LoginAuthenticationSuccesssHandler implements AuthenticationSuccessHandler
{

    private static final Logger LOG = Logger.getLogger(LoginAuthenticationSuccesssHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws IOException, ServletException
    {
        // String url = "";
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        if (savedRequest != null)
        {
            // url = savedRequest.getRedirectUrl();
            // LOG.debug(url);
        }
        LOG.info("----------LoginAuthenticationSuccesssHandler.onAuthenticationSuccess()----------验证完成!!!");
        Object principal = authentication.getPrincipal();
        PhoneUserDetails userDetail = (PhoneUserDetails)principal;
        Map<String, Object> user = userDetail.getUser();
        user.put("sessionid", request.getSession().getId());
        userDetail.setUser(JSONObject.fromObject(user));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", 1);
        map.put("message", "登陆成功");
        JSONObject responseJSONObject = JSONObject.fromObject(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(responseJSONObject.toString());
    }

}
