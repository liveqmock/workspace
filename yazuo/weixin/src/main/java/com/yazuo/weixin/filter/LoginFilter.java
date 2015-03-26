package com.yazuo.weixin.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.util.DateUtil;

/**
* @ClassName LoginFilter
* @Description 访问jsp必须先登录，登录过滤器
* @author sundongfeng@yazuo.com
* @date 2014-7-9 上午9:22:03
* @version 1.0
*/
public class LoginFilter implements Filter{
	private static final Log log = LogFactory.getLog("logfilter");//日志记录分析
	/**
	 * 过滤规则方法
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse)res;
		HttpSession session = request.getSession();
		UserInfoVo user = (UserInfoVo) session.getAttribute("USER");//session map
		boolean toJsp = request.getRequestURI().indexOf("/jsp/")!=-1;
		boolean notLoginJsp = request.getRequestURI().indexOf("/jsp/backDoorLogin")==-1;
		StringBuffer query =request.getRequestURL();
		/********没有session，访问非登录jsp页面**************/
		if(user==null && toJsp &&notLoginJsp){
			String ip=request.getRemoteAddr();
			log.info(";"+DateUtil.getDate4d()+";"+query+";"+ip+";");
			request.setCharacterEncoding("UTF-8");
			String route = request.getContextPath() + "/toLogin.do";
			query.append("?");
			Map<String, String[]>  map=request.getParameterMap();
			Set<Map.Entry<String, String[]>> setMap = map.entrySet();
			for (Map.Entry<String, String[]> sm : setMap) {
				String name = sm.getKey();
				String[] value=sm.getValue();
				if (StringUtils.isNotEmpty(value[0])) {
					query.append(name).append("=").append(URLEncoder.encode(value[0],"UTF-8")).append("&"); 
				}
			}
			session.setAttribute("requestPageUrl",query.substring(0,query.length()-1));//记录访问路径
			response.sendRedirect(route);//跳转登录
		}else{
			chain.doFilter(request, response);
		}
	}

	
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	public void destroy() {
	}
	
}
