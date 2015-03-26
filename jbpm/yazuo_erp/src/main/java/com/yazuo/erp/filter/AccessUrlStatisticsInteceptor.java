/**
 * @Description TODO
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
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.StringUtils;

/**
 * @Description TODO
 * @author song
 * @date 2014-6-5 下午4:25:02
 */

public class AccessUrlStatisticsInteceptor extends HandlerInterceptorAdapter {
	private static final Log log = LogFactory.getLog(AccessUrlStatisticsInteceptor.class);
	
	/**
	 * 1.session过滤
	 * 2.存储basePath到session
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String str = request.getRequestURL().toString();
		log.info("requestURL = "+str);
		HttpSession session = request.getSession();
		//项目临时文件的路径放到session中
		if (session.getAttribute(Constant.TEMP_FILE_PATH) == null) {
			String currentPath = request.getSession().getServletContext().getRealPath("temp");
			session.setAttribute(Constant.TEMP_FILE_PATH, currentPath);
			log.info("临时文件的路径: "+ session.getAttribute(Constant.TEMP_FILE_PATH));
		}
		//TODO 视频案例库超时问题以后代码重构的时候再处理
//		// 0.拦截的是案例库或者视频库，特殊处理
//		if (str.indexOf("erp/project") != -1||str.indexOf("erp/video") != -1) {
//			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
//			response.sendRedirect(basePath+"login.jsp");
//		}
		//放过的链接
		for(String link : Constant.WRITE_LIST_NO_SESSION_CTRL){
			if(str.indexOf(link) != -1){
				return true;
			}
		}
		//1. 如果不是登录操作，拦截请求，返回登录页面
		if (str.indexOf("/login") == -1) {// 拦截不是登录的页面
			//2. 用sessionId回传的方式解决flash上传丢失session的问题
			String sessionId = request.getParameter(Constant.SESSION_ID);
			log.info("Flash方式文件上传的时候 SESSION_ID:" + sessionId);
			if(!StringUtils.isEmpty(sessionId)){
				SysUserVO user = SessionUserList.getOnlineSessionUser(sessionId);
				if(user==null){
					outPrintTimeOutJson(response);
					return false;
				}else{
					request.setAttribute(Constant.SESSION_USER, SessionUserList.getOnlineSessionUser(sessionId));
					if (request.getSession().getAttribute(Constant.TEMP_FILE_PATH) == null) {
						String currentPath = request.getSession().getServletContext().getRealPath("temp");
						request.getSession().setAttribute(Constant.TEMP_FILE_PATH, currentPath);
						log.info("临时文件的路径: "+ session.getAttribute(Constant.TEMP_FILE_PATH));
					}
					return true;
				}
			}
//			log.info(this.getClass().getName()+"request.getCookies(): "+request.getCookies());
//			log.info(this.getClass().getName()+"session.getId(): "+session.getId());
			if (session.getAttribute(Constant.SESSION_USER) == null) {
				outPrintTimeOutJson(response);
				return false;
			}
		}
		//项目路径放到session中
		if (session.getAttribute(Constant.BASEPATH) == null) {
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
			session.setAttribute(Constant.BASEPATH, basePath);
		}

		return true;// 是登陆页面，直接返回ture
	}

	private void outPrintTimeOutJson(HttpServletResponse response) throws IOException {
		//点击菜单项的时候由于前台接受的是json格式，需要拼装json，跳转由前端处理
//				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		log.info("会话超时或未登录,返回到登录页面");
//				response.sendRedirect(basePath+"login.jsp");
		response.setContentType("textml;charset=UTF-8");
		PrintWriter out = response.getWriter();
//				out.println();
//				out.print("<script language='javascript' type='text/javascript'>");
//				out.print("window.alert('非法访问，请先登录');");
//				out.print("window.parent.location.href = '"+ basePath+"login.jsp'");
//				out.print("</script>");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("1", true);
		map.put(Constant.TIME_OUT, true);
		out.print(JSONObject.fromObject(map));
		out.flush();
		out.close();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * 可以根据ex是否为null判断是否发生了异常，进行日志记录。 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(ex!=null){
			log.error(ex);
		}
	}

}
