package com.yazuo.weixin.member.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.yazuo.weixin.member.service.ResourceManagerService;
import com.yazuo.weixin.member.vo.MerchantSettingResourceVo;
import com.yazuo.weixin.member.vo.WeixinSettingResourceVo;
import com.yazuo.weixin.user.vo.UserInfoVo;

@Controller
@RequestMapping("/setting/resource")
public class ResourceManagerController {

	private static final Log opLog = LogFactory.getLog("backstageOperate");
	
	@Resource
	private ResourceManagerService resourceManagerService;
	
	@RequestMapping(value = "listResource", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView listResource() {
		List<WeixinSettingResourceVo> list = resourceManagerService.getResourceList(true);
		if (list !=null && list.size()>0) {
			ModelAndView mav = new ModelAndView("/resource/list_resource");
			mav.addObject("list", list);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/resource/add_resource");
			return mav;
		}
	}
	
	
	@RequestMapping(value = "saveOrUpdateResource", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object saveOrUpdateResource(@RequestBody WeixinSettingResourceVo resource) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isEnable = resource.getTempEnable().equals("1") ? true : false;
		resource.setIsEnable(isEnable);
		// 判断是否有重复type值
		boolean isExists = resourceManagerService.judgeExistsSameTypeValue(resource.getType(), resource.getId());

		if (isExists) {
			map.put("flag", false);
			map.put("message", "输入的类型值已经存在，请重新输入");
			return map;
		}
		int count =0;
		if (resource.getId()!=null && resource.getId()!=0) {
			count = resourceManagerService.updateResource(resource);
		} else {
			count = resourceManagerService.saveResource(resource);
		}
		
		map.put("flag", count>0 ? true : false);
		map.put("message", count>0?"保存成功":"保存失败");
		return map;
	}
	
	
	@RequestMapping(value = "initMerchantResource", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView initMerchantResource(Integer brandId) {
		// 取可用商户的资源数据
		List<WeixinSettingResourceVo> list = resourceManagerService.getResourceList(false);
		List<Map<String, Object>> merchantResList = resourceManagerService.getResourceByBrandId(brandId);
		ModelAndView mav = new ModelAndView("/resource/resource_merchant");
		mav.addObject("resourceList", list);
		mav.addObject("merchantResList", merchantResList);
		mav.addObject("brandId", brandId);
		return mav;
	}
	
	@RequestMapping(value = "saveMerchantRes", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object saveMerchantRes(@RequestBody MerchantSettingResourceVo resource, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		UserInfoVo user = session.getAttribute("USER") !=null ? (UserInfoVo)session.getAttribute("USER"):null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		opLog.info("--"+sdf.format(new Date())+"调用saveMerchantRes(设置资源)的用户UserId:"+(user !=null ? user.getUserId():0));
		String [] array = resource.getResourceArray();
		List<String> list = array!=null && array.length> 0 ? Arrays.asList(array) : new ArrayList<String>();
		int count = resourceManagerService.saveMerchantResource(list, resource.getBrandId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", count>0 ? true : false);
		map.put("message", count>0?"保存成功":"保存失败");
		return map;
	}
	
	
	@RequestMapping(value = "editResource", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView editResource(Integer id) {
		WeixinSettingResourceVo resource = resourceManagerService.getResourceById(id);
		ModelAndView mav = new ModelAndView("/resource/add_resource");
		mav.addObject("resource", resource);
		return mav;
	}
	
	
}
