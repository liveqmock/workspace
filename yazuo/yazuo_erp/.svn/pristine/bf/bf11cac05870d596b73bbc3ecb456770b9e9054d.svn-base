/**
 * @Description 跟主页相关的处理
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description 职位相关的业务操作
 * @author song
 * @deprecated 测试类
 * @date 2014-6-9 11:06:21
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)  
public class IndexController {

private static final Log log = LogFactory.getLog(LoginController.class);
	
	@Resource
	private SysUserService userService;
	
	/**
	 * 获得权限，跳转到index页面
	 */
	@RequestMapping(value = "index", method = { RequestMethod.POST,
			RequestMethod.GET })	
	@Deprecated //测试方法 
	public ModelAndView index( ModelMap model) {
		//SysUserVO user = (SysUserVO)model.get(Constant.SESSION_USER); 
		//TODO 测试代码 ，为了 取出默认放置的 userID
//		SysUserVO user = userService.getSysUserById(1);
//		List<SysResourceVO> listPrivilege = userService.getAllUserResourceByPrivilege(user.getId());
//		user.setListPrivilege(listPrivilege);
//		model.put(Constant.SESSION_USER, user);
//		ModelAndView mav = new ModelAndView("../index");
//		mav.addObject(user);
		ModelAndView mav = new ModelAndView("../index");
	    return mav;  
	    
	}
}
