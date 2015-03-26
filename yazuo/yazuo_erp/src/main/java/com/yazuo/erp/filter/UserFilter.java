/**
 * @Description 过滤器，如果jsp没有session user，跳转到登录页，不拦截login.jsp
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

public class UserFilter implements Filter {

	private String sessionKey;

	private String excepUrl;

	private String redirectUrl;

	public void init(FilterConfig cfg) throws ServletException {
		sessionKey = cfg.getInitParameter("sessionKey");
		redirectUrl = cfg.getInitParameter("redirectUrl");
		excepUrl = cfg.getInitParameter("excepUrl");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// 如果 sessionKey 为空，则直接放行
		if (StringUtils.isBlank(sessionKey)) {
			chain.doFilter(req, res);
			return;
		}
		// * 请求 http://127.0.0.1:8080/webApp/home.jsp?&a=1&b=2 时
		// * request.getRequestURL()： http://127.0.0.1:8080/webApp/home.jsp
		// * request.getContextPath()： /webApp
		// * request.getServletPath()：/home.jsp
		// * request.getRequestURI()： /webApp/home.jsp
		// * request.getQueryString()：a=1&b=2
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String servletPath = request.getServletPath();// 当前页面的路径(如"/index.jsp")
		// 如果请求的路径与redirectUrl(将要跳转的页面)相同，或请求的路径是排除的URL时，则直接放行
		if (servletPath.equals("/" + redirectUrl) || servletPath.contains(excepUrl)) {
			chain.doFilter(req, res);
			return;
		}

		Object sessionObj = request.getSession().getAttribute(sessionKey);
		// 如果Session为空，则跳转到指定页面
		if (sessionObj == null) {
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
			response.sendRedirect(basePath+redirectUrl);
			chain.doFilter(req, res);
		} else {
			chain.doFilter(req, res);
		}
	}

	public void destroy() {

	}
}
