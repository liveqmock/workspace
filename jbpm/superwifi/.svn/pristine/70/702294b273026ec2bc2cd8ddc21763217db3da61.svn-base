/*
 * 文件名：CustomUsernamePasswordAuthenticationFilter.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2015年1月29日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.security.service.spring;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yazuo.api.service.account.ClientContents;
import com.yazuo.api.service.account.ClientContents.LoginCode;
import com.yazuo.api.service.account.exception.AccountCheckedException;
import com.yazuo.api.service.account.user.UserAuthWebService;
import com.yazuo.api.service.account.vo.LoginUser;
import com.yazuo.superwifi.exception.BussinessException;


public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    @Resource
    private UserAuthWebService userAuthWebService;

    private boolean postOnly = true;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    {
        if (postOnly && !request.getMethod().equals("POST"))
        {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null)
        {
            username = "";
        }

        if (password == null)
        {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
