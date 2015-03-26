/**
 * <p>Project: erp</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-04-18
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.erp.filter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @ClassName: RequestParameterInteceptor
 * @Description: 记录请求参数
 * 
 */
public class RequestParameterInteceptor extends HandlerInterceptorAdapter {
	private static final Log LOG = LogFactory.getLog("erp");

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// String referer = request.getHeader("Referer");
		String uri = request.getRequestURI();

		String method = request.getMethod();
		StringBuilder sb = new StringBuilder("******************本次" + method + "请求的参数如下************************\n");
		sb.append("********请求toUri=" + uri + "**********\n");
		Enumeration<String> en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String name = en.nextElement();
			sb.append("\t参数名getParameter：" + name + ",参数值：" + request.getParameter(name) + "\n");
		}
		HttpSession session = request.getSession();
		if (session != null) {
			Object attribute = session.getAttribute(Constant.SESSION_USER);
			if(attribute!=null){
				SysUserVO sysUserVO = (SysUserVO)attribute;
				LOG.info("sessionId: "+session.getId());
				LOG.info("userId: "+sysUserVO.getId());
				LOG.info("userName: "+sysUserVO.getUserName());
			}
		}
		LOG.info(sb.toString());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

}
