/** 
* @author kyy
* @version 创建时间：2015-1-22 上午11:39:38 
* 类说明  微信后台用户管理
*/ 
package com.yazuo.weixin.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.user.service.UserManagerService;
import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.util.StringUtil;

@RequestMapping("/user/manager/")
@Controller
@SessionAttributes("USER")
public class UserManagerController {

	@Resource
	private UserManagerService userManagerService;
	
	@RequestMapping(value = "saveUser", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object saveUser(UserInfoVo user) {
		boolean isEnable = Integer.parseInt(user.getTempEnable())==1 ? true : false;
		user.setIsEnable(isEnable);
		if (user !=null && user.getId() !=null) { // 修改
			return userManagerService.editUser(user);
		} else { // 保存
			return userManagerService.saveUser(user);
		}
	}
	
	@RequestMapping(value = "modfiyPwd", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object modifyPwd(UserInfoVo user) {
		return userManagerService.modifyPwd(user.getOldPassword(), user.getPassword(), user.getUserName(), user.getId());
	}
	
	
	@RequestMapping(value = "editUser", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView editUser(Integer userId) {
		UserInfoVo user = userManagerService.getUserById(userId);
		ModelAndView mav = new ModelAndView("/resource/add_edit");
		mav.addObject("user", user);
		return mav;
	}
	
	@RequestMapping(value = "modfiyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView modfiyInfo(Integer userId) {
		UserInfoVo user = userManagerService.getUserById(userId);
		ModelAndView mav = new ModelAndView("/resource/modfiy_pwd");
		mav.addObject("user", user);
		return mav;
	}
	
	
	
//	@RequestMapping(value = "modfiyUser", method = { RequestMethod.POST, RequestMethod.GET })
//	@ResponseBody
//	public Object modfiyUser(@ModelAttribute("USER") UserInfoVo user, UserInfoVo paramUser) {
//		
//		Map<String, Object> resultMap = userManagerService.modifyPwd(paramUser.getOldPassword(), paramUser.getPassword(), paramUser.getUserName(), user.getId());
//		
//		return resultMap;
//	}
	
	@RequestMapping(value = "listUser", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView listUser(@RequestParam(value="userName", required=false) String userName
			,@RequestParam(value="isEnble", required = false)Boolean isEnble, @RequestParam(value="isSearch", required=false)String isSearchStr) {
		List<UserInfoVo> list = userManagerService.getAllUser(userName, isEnble);
		if ((list !=null && list.size() > 0) || (!StringUtil.isNullOrEmpty(isSearchStr))) {
			ModelAndView mav = new ModelAndView("/resource/list_user");
			mav.addObject("list", list);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/resource/add_edit");
			return mav;
		}
	}
}
