package com.yazuo.weixin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.weixin.service.ExternalService;
import com.yazuo.weixin.vo.MemberVO;

@Controller
@RequestMapping("/weixin/interface")
public class ExternalInterface {
	@Autowired
	private ExternalService externalService;
	Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value = "getMemberByOpenId", method = { RequestMethod.GET })
	@ResponseBody
	public Object getMemberByOpenId(
			@RequestParam(value = "openId", required = false) String openId) {
		log.info("getMemberByOpenId=" + openId);
		Map<String, String> map = new HashMap<String, String>();
		MemberVO member = externalService.getMemberByOpenId(openId);
		if (member == null || member.getIsMember() == false) {
			map.put("isMember", "false");
			return map;
		}
		map.put("isMember", "true");
		map.put("openId", member.getWeixinId());
		map.put("mobileNo", member.getPhoneNo());
		map.put("brandId", member.getBrandId().toString());
		return map;
	}

	@RequestMapping(value = "jsonpTest", method = { RequestMethod.GET })
	@ResponseBody
	public void jsonpTest(HttpServletResponse response,
			@RequestParam(value = "openId", required = false) String openId) {
		log.info("getMemberByOpenId=" + openId);
		MemberVO member = externalService.getMemberByOpenId(openId);
		try {
			response.getWriter().print("alert('");
			response.getWriter().print("cardNo="+member.getCardNo());
			response.getWriter().print("phoneNo="+member.getPhoneNo());
			response.getWriter().print("')");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "sendSMSByOpenId", method = { RequestMethod.GET })
	@ResponseBody
	public Object sendSMSByOpenId(HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value = "openId", required = false) String openId,
			@RequestParam(value = "content", required = false) String content) {
		log.info("sendSMSByOpenId=" + openId + content);
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", externalService.sendSMSByOpenId(openId, content));
		return map;

	}
	
	@RequestMapping(value = "sendSMSByMobileNo", method = { RequestMethod.GET })
	@ResponseBody
	public Object sendSMSByMobileNo(HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "content", required = false) String content) {
		log.info("sendSMSByMobileNo=" + mobileNo + content);
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", externalService.sendSMSByMobileNo(mobileNo, content));
		return map;

	}
	@RequestMapping(value = "bindMobileNoAndOpenId", method = { RequestMethod.GET })
	@ResponseBody
	public Object bindMobileNoAndOpenId(HttpServletResponse response,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "openId", required = false) String openId,
			@RequestParam(value = "brandId", required = false) Integer brandId) {
		log.info("bindMobileNoAndOpenId=" + mobileNo + openId+brandId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", externalService.bindMobileNoAndOpenId(mobileNo, openId,brandId));
		return map;
	}

}