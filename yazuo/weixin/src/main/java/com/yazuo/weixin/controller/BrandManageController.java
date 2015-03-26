package com.yazuo.weixin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.yazuo.weixin.service.BrandManageService;
import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.vo.BrandAndKeyRelativeVO;

@Controller
@RequestMapping("/weixin/brandAndKey")
public class BrandManageController {
	@Resource
	private BrandManageService brandManageService;
	Logger log = Logger.getLogger(this.getClass());
	private static final Log opLog = LogFactory.getLog("backstageOperate");

	@RequestMapping(value = "saveBrandKeyRelative", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView saveBrandKeyRelative( HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "isDelete", required = false) Boolean isEnable, 
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		opLog.info("--"+sdf.format(new Date())+"调用saveBrandKeyRelative(设置key)的用户UserId:"+(user !=null ? user.getUserId():0));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		boolean success = false;
		if (id ==null) { // 添加
			BrandAndKeyRelativeVO relative = new BrandAndKeyRelativeVO();
			relative.setBrandId(brandId);
			relative.setTokenKey(key);
			relative.setStatus(isEnable);
			success = brandManageService.saveRelative(relative);
		} else { // 修改
			BrandAndKeyRelativeVO relative = new BrandAndKeyRelativeVO();
			relative.setBrandId(brandId);
			relative.setTokenKey(key);
			relative.setStatus(isEnable);
			relative.setId(id);
			success = brandManageService.updateActivityRecord(relative);
		}
		ModelAndView mav = new ModelAndView("backDoor");
		mav.addObject("flag", success);
		mav.addObject("message", success ? "保存成功!" : "保存失败!");
		return mav;
	}

	@RequestMapping(value = "editRelative", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView editRelative( HttpServletResponse response, @RequestParam(value = "brandId", required = false) Integer brandId) {
		BrandAndKeyRelativeVO relative = brandManageService.getRelativeByBrandId(brandId);
		if (relative==null) {
			relative = new BrandAndKeyRelativeVO();
			String str = UUID.randomUUID().toString().trim();
			relative.setTokenKey((str.length()>32 ? str.substring(0, 32) : str));
		}
		ModelAndView mav = new ModelAndView("addBrandKey");
		mav.addObject("relative", relative);
		
		return mav;
	}
}
