/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-04-18
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.filter;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yazuo.weixin.member.service.ResourceManagerService;
import com.yazuo.weixin.util.Constants;
import com.yazuo.weixin.util.StringUtil;

/**
 * @ClassName: RequestParameterInteceptorInterceptor
 * @Description: 记录请求参数
 * 
 */
public class RequestParameterInteceptorInterceptor extends HandlerInterceptorAdapter {
	@Resource
	private ResourceManagerService resourceManagerService;
	
	private static final Log LOG = LogFactory.getLog(RequestParameterInteceptorInterceptor.class);
	
	private static final Log weixinLog = LogFactory.getLog("weixin");

	private static Pattern SCRIPT_PATTERN = Pattern.compile("<script.*>.*<\\/script\\s*>");

	private ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<StopWatch>(); 

	
	// private static Pattern SH_PATTERN = Pattern.compile("../");


	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		StopWatch stopWatch = new StopWatch(handler.toString());
	    stopWatchLocal.set(stopWatch);
	    stopWatch.start(handler.toString());
		
		boolean b = true;
		String referer = request.getHeader("Referer");
		String uri = request.getRequestURI();
		
//		request.getContextPath()

		String method = request.getMethod();
		StringBuilder sb = new StringBuilder("******************本次" + method + "请求的参数如下************************\n");
		sb.append("********来自地址fromUrl=" + referer + ";toUri=" + uri + "**********\n");
		Enumeration<String> en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String name = en.nextElement();
			String values[] = request.getParameterValues(name);

			for (int i = 0; i < values.length; i++) {
				sb.append("\t参数名getParameter：" + name + ",参数值：" + values[i] + "\n");
				// 过滤script脚本
				Matcher m = SCRIPT_PATTERN.matcher(values[i]);

				if (m.find()) {
					weixinLog.info("出现script脚本！关键词=" + values[i]);
					b = false;
				}
				// Matcher s = SH_PATTERN.matcher(values[i]);
				// if (s.find()) {
				// b = false;
				// }
				// 过滤sql转换函数
				if (values[i].contains("ascii(") || values[i].contains("ascii (") || values[i].contains("chr(")
						|| values[i].contains("chr (")) {
					weixinLog.info("出现sql转换函数！关键词=" + values[i]);
					b = false;
				}
				// 过滤sql关键字
				if (values[i].contains("alter") || values[i].contains("create") || values[i].contains("truncate")
						|| values[i].contains("drop") || values[i].contains("lock table") || values[i].contains("insert")
						|| values[i].contains("update") || values[i].contains("delete") || values[i].contains("select")
						|| values[i].contains("grant") || values[i].contains("../") || values[i].contains("/etc")) {
					weixinLog.info("出现sql关键字！关键词=" + values[i]);
					b = false;
				}
			}

		}
		weixinLog.info(sb.toString());
		boolean isExpressUrl = dealRequestUrl(uri, request);
		if (!isExpressUrl){
			return false;
		}
		return b;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		String uri = request.getRequestURI();
		 StopWatch stopWatch = stopWatchLocal.get();
		 if(stopWatch.isRunning()) {
			 stopWatch.stop();
		 }
		 weixinLog.info("=====访问链接【"+uri+"】，请求所耗时间："+stopWatch.getTotalTimeMillis()+"毫秒");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	
	// 将拦截链接中的部分链接判断是有权限
	private boolean dealRequestUrl(String requestUrl, HttpServletRequest request) {
		String path = request.getContextPath();
		requestUrl = requestUrl.replace(path, ""); // 请求地址
		String brandIdStr = request.getParameter("brandId");
		Integer brandId = 0;
		if(!StringUtil.isNullOrEmpty(brandIdStr)) {
			brandId = Integer.parseInt(brandIdStr);
		} else {
			String tempUrl = requestUrl.substring(0,requestUrl.lastIndexOf("/"));
			brandIdStr = tempUrl.substring(tempUrl.lastIndexOf("/")+1);
			try{
				brandId = StringUtil.isNullOrEmpty(brandIdStr) ? 0 : Integer.parseInt(brandIdStr);
			}catch(Exception e){
				return true;
			}
		}
		List<String> rList = Constants.RESOURCELIST; // 需要过滤的请求
		List<Map<String, Object>> resourceList = resourceManagerService.getResourceOfBrandId(brandId, null);
		int count = 0;
		for (String s : rList) {
			if (s.replace("{brandId}", brandId+"").equals(requestUrl)) { // 是需要过滤的url
				// 判断该商户是否有权限
				for (Map<String, Object> map : resourceList) {
					String resourceUrl = map.get("resource_url")+"";
					if (!StringUtil.isNullOrEmpty(resourceUrl)) {
						int index = 0;
						if (resourceUrl.indexOf("{path}") > -1) {
							index = resourceUrl.indexOf("{path}");
						}
						resourceUrl = resourceUrl.substring(index, resourceUrl.lastIndexOf(".do")+3).replace("{path}", "").replace("{brandId}", brandId+"");
						if (requestUrl.startsWith("/weixin/consumerLottery/") || requestUrl.startsWith("/caffe/cardLottery/")) { // 抽奖的 
							if (resourceUrl.startsWith("/setting/lottery/")) { // 是否有抽奖权限
								return true;
							}	
						} else { // 非抽奖的
							if (resourceUrl.equals(requestUrl)) {
								return true;
							}
						}
					}
				}
			} else {
				count ++;
			}
		}
		if (count == rList.size()) {
			return true;
		}
		return false;
	}

}
