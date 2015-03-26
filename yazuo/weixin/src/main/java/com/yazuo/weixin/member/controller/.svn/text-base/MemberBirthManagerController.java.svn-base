package com.yazuo.weixin.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.es.service.MemberShipCenterService;
import com.yazuo.weixin.member.service.MemberBirthManagerService;
import com.yazuo.weixin.member.service.ResourceManagerService;
import com.yazuo.weixin.member.vo.MembershipBirthdayFamily;
import com.yazuo.weixin.service.RegisterPageService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.Constants;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;

/***
 * 会员生日及家人生日管理
 * @author kyy
 */
@RequestMapping("/member/birth")
@Controller
@Scope("prototype")
public class MemberBirthManagerController {
	@Resource
	private MemberShipCenterService memberShipCenterService;
	@Resource
	private MemberBirthManagerService memberBirthManagerService;
	@Resource
	private RegisterPageService registerPageService;
	@Resource
	private WeixinPhonePageService weixinPhonePageService;
	@Resource
	private WeixinManageService weixinManageService;
	@Resource
	private ResourceManagerService resourceManagerService;

	
	private static final Log weixinLog = LogFactory.getLog("weixin");
	
	@RequestMapping(value = "queryMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView queryMemberInfo(MemberVO member, String birthDay, String title) {
		String year = "";
		String month = "";
		String day = "";
		if (!StringUtil.isNullOrEmpty(birthDay)) {
			String [] array = birthDay.split("-");
			year = array[0];
			month = array[1];
			day = array[2];
		}
		ModelAndView mav = new ModelAndView("/member/member_info_edit");
		// 判断该商户是否有家人生日此功能
		boolean existsMemberBirth = resourceManagerService.judgeExistsResourceOfBrand(Constant.MEMBER_BIRTH_TYPE_VALUE, member.getBrandId());
		weixinLog.info("[brandId="+member.getBrandId()+"]是否有家人生日："+existsMemberBirth);
		mav.addObject("existsMemberBirth", existsMemberBirth);
		if (existsMemberBirth) {
			List<MembershipBirthdayFamily> familyList = memberBirthManagerService.getMemberFamilyBirth(member.getMembershipId(), member.getBrandId());
			mav.addObject("familyList", familyList);
		}
		mav.addObject("member", member);
		mav.addObject("relationMap", Constants.RELATION_MAPS);
		mav.addObject("relationList", Constants.RELATIONLIST.toArray());
		mav.addObject("title", title);
		mav.addObject("year", year);
		mav.addObject("month", month);
		mav.addObject("day", day);
		return mav;
	}
	
	@RequestMapping(value = "verifyIdenfiyCode", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object verifyIdenfiyCode(String identifyCode, String weixinId, Integer brandId) {
		return memberBirthManagerService.verifyIdenfiyCode(identifyCode, brandId, weixinId);
	}

	
	// 隐藏域返回修改用户的全部信息
	@RequestMapping(value = "modifyUserFamily", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object modifyUserFamily(MemberVO requestMember, @RequestParam(value = "birthDay") String birthday,
			 @RequestParam(value = "family[]", required=false) String [] family, @RequestParam(value = "familyBirthDay[]", required=false) String [] familyBirthday
			 ,@RequestParam(value="birthTypes[]", required = false) String [] birthTypes) {
		weixinLog.info("会员修改信息数据:" + "brandId:" + requestMember.getBrandId() + ",weixinId:" + requestMember.getWeixinId()
				+ ",phoneNo:" + requestMember.getPhoneNo() + ",gender:" + requestMember.getGender() + ",name:" + requestMember.getGender()
				+ ",birthday:" + birthday+",birthType:"+requestMember.getBirthType());
		Map<String, Object> map = new HashMap<String, Object>();
		
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(
				requestMember.getBrandId(), requestMember.getWeixinId());
		BusinessVO business = weixinManageService.getBusinessByBrandId(requestMember.getBrandId());
		if (member == null) {
			map.put("backStatus", 0);
		}
		if(business == null){
			business =  new BusinessVO();
			business.setBrandId(requestMember.getBrandId());
		}
		member.setPhoneNo(requestMember.getPhoneNo());
		member.setGender(requestMember.getGender());
		member.setName(requestMember.getName());
		member.setBirthType(requestMember.getBirthType());//
		// 修改会员信息
		String info = registerPageService.modifyMemberInfo(birthday.trim(),member, business); // success/ error/noMembership
		map.put("backStatus", info);
		
		// 保存家人生日
		if ((family!=null && family.length>0) && familyBirthday!=null && familyBirthday.length>0) {
			Map<String, Object> resultMap = memberBirthManagerService.saveMemberFamilyBirthDay(requestMember.getMembershipId(), requestMember.getBrandId(), family, familyBirthday, birthTypes);
			map.put("message", resultMap.get("message"));
		}
		return map;
	}

	
}
