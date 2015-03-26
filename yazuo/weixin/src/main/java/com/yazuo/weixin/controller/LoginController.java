package com.yazuo.weixin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.weixin.user.service.UserManagerService;
import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.util.DateUtil;

/**
* @ClassName LoginController
* @Description 登录
* @author sundongfeng@yazuo.com
* @date 2014-7-9 上午10:31:22
* @version 1.0
*/
@Controller
@Scope("prototype")
@SessionAttributes("USER")
public class LoginController {
	private static final Log log = LogFactory.getLog(LoginController.class);
	@Value("#{propertiesReader['login.username']}")
	private String loginusername;
	@Value("#{propertiesReader['login.password']}")
	private String loginpassword;
	
	@Resource
	private UserManagerService userManagerService;
	
	@RequestMapping(value="toLogin", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String toLogin(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		return "backDoorLogin";
	}
	
	/**
	 * 登陆
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value="login", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String login(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username", required = false) String name,
			@RequestParam(value = "password", required = false) String pwd,
			ModelMap model){
		HttpSession session = request.getSession();
		String requestPageUrl = StringUtils.trimToEmpty((String) session.getAttribute("requestPageUrl"));
		if(StringUtils.isNotEmpty(requestPageUrl)){
			session.removeAttribute("requestPageUrl");
			if (requestPageUrl.indexOf(request.getContextPath()+ "/") != -1) {
				requestPageUrl = requestPageUrl.substring(requestPageUrl.indexOf(request.getContextPath() + "/"));
				requestPageUrl = requestPageUrl.replace(request.getContextPath()+ "/", "/");
			}
			if(requestPageUrl.indexOf("toLogin")==-1){
				model.addAttribute("requestPageUrl", StringUtils.trimToEmpty(requestPageUrl));
			}
		}
		String ip=request.getRemoteAddr();
		log.info(";"+DateUtil.getDate4d()+";"+name+";"+pwd+";"+requestPageUrl+";"+ip+";");
		
		// 判断是否让登录
		UserInfoVo user = userManagerService.getUserByLoginNameAndPwd(name, pwd);
		if (user !=null) { // 匹配成功
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("username", name);
//			map.put("password", pwd);
			UserInfoVo userInfo = new UserInfoVo();
			userInfo.setUserId(user.getId());
			userInfo.setLoginUser(user.getLoginUser());
			userInfo.setSupperUser(user.getSupperUser());
			session.setAttribute("USER", userInfo);
//			model.addAttribute("USER", user);
			return "backDoorLoginRedrect";
		}
		model.addAttribute("error", "请输入正确的用户名、密码");
		return "backDoorLogin";
	}
}
