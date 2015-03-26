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
package com.yazuo.weixin.member.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.jms.JMSException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.member.service.BindingOldMemberService;
import com.yazuo.weixin.member.service.MemberInfoService;
import com.yazuo.weixin.service.SaveOrModifyMembershipTagService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.MessageSender;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.IdentifyinginfoVO;
import com.yazuo.weixin.vo.MemberVO;

/**
 * 老会员绑定业务
 * @author kyy
 * @date 2014-10-9 上午10:56:24
 */
@Service
public class BindingOldMemberServiceImpl implements BindingOldMemberService {

	@Resource
	private WeixinPhonePageService weixinPhonePageService;
	@Resource
	private WeixinManageService weixinManageService;
	@Resource
	private MemberDao memberDao;
	@Resource
	private SaveOrModifyMembershipTagService saveOrModifyMembershipTagService;
	@Resource
	private MemberInfoService memberInfoService;
	
	@Value("#{propertiesReader['smsAddress']}")
	private String smsAddress;
	@Value("#{propertiesReader['smsUsername']}")
	private String smsUsername;
	@Value("#{propertiesReader['queryMemberByPhoneNo']}")
	private String queryMemberByPhoneNo; // 根据手机号和brand_id取crm会员信息
	@Value("#{propertiesReader['registerMircoMessageMembership']}")
	private String registerMircoMessageMembership;
	
	private static final Log log = LogFactory.getLog("weixin");
	
	@Override
	public Object bingdingOldMember(String phoneNo, String identifyCode, Integer brandId, String weixinId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 验证码输入的是否正确
		// 查询验证码信息
		IdentifyinginfoVO identifyinginfo = memberDao.getLastIdentifyinginfoByWeixinId(weixinId, brandId);
		if (identifyinginfo == null// 属于重新获得验证码一类
				|| identifyinginfo.getIdentifyingCode() != null
				&& identifyinginfo.getIdentifyingCode() == null
				|| identifyinginfo.getIdentifyingCode() != null && identifyinginfo.getIdentifyingCode().equals("")) {
			
			resultMap.put("flag", 0);
			resultMap.put("msg", "验证码错误~~");
			return resultMap;// 未申请验证
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 1800 * 1000);
		if (!identifyinginfo.getIdentifyingCode().equals(identifyCode.trim())) {// 验证码错误，请重新申请验证
			resultMap.put("flag", 0);
			resultMap.put("msg", "验证码错误");
			return resultMap;
		}
		if (identifyinginfo.getIdentifyingTime().before(timestamp)) {// 验证码过期，请重新申请验证
			resultMap.put("flag", 0);
			resultMap.put("msg", "验证码过期,重新获取");
			return resultMap;
		}
		
		// 判断该门店是否存在
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		if (business ==null) {
			resultMap.put("flag", 0);
			resultMap.put("msg", "请到实体店办理");
			return resultMap;
		}
		
		// 判断是否为crm会员
		JSONObject json = queryCrmMember(brandId, phoneNo); // 调用crm接口查询是否会员
		boolean flag = json!=null ? json.getJSONObject("data").getJSONObject("result").getBoolean("success"): false;
		Integer tagId = business.getTagId();
		if (flag) { // 是crm会员，即只添加微信会员 (走绑定方式送活动)
			MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
			if (member == null) {
				member = new MemberVO();
			}
			// 调用crm添加会员接口更新微信id和送奖励（劵或机会）
			String input = "{\"mobile\":\"" + phoneNo + "\",\"merchantId\":" + brandId + ",\"microMessageId\":\"" + weixinId + "\"}";
			JSONObject jo = null;
			try {
				// 调用会员接口耗時
				long timeStart = System.currentTimeMillis();
				String result = CommonUtil.postSendMessage(registerMircoMessageMembership, input, Constant.KEY+"");
				if (!StringUtil.isNullOrEmpty(result)) {
					jo = JSONObject.fromObject(result);
				}
				log.info("--RegisterPageServiceImpl访问添加会员接口...耗时::" + (System.currentTimeMillis() - timeStart) + "毫秒");
			} catch (Exception e) {
				log.error("调用注册会员接口失败" + e.getMessage());
				e.printStackTrace();
			}
			
			/*---------------分域------begin----------------------------*/
			Integer membershipId = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getInt("id");
			MemberVO memberScope = new MemberVO();
			memberScope.setMembershipId(membershipId);
			memberScope.setBrandId(brandId);
			memberScope.setDataSource(Constant.MEMBER_SCOPE_SOURCE_1); // 微信加会员
			memberScope.setWeixinId(weixinId);
			memberInfoService.saveMemberScopeInfo(member); // 返回结果不处理
			/*---------------分域------end----------------------------*/
			
			String toUrl = "/bindOldMember/bindingResult.do?brandId=" + brandId + "&weixinId=" + weixinId+"&isSuccess=success";
			/*------------wifi------送抽奖机会---------------------*/
			JSONObject luckobj = jo.getJSONObject("data").getJSONObject("result").getJSONObject("activeMap");
			if(luckobj!=null ){
				String lunckCount = luckobj.get("4016_7")+""; // 赠送的抽奖机会数
				String couponIdStr = luckobj.get("4016_3")+""; // 赠送劵id
				resultMap.put("couponIdStr", couponIdStr);
				resultMap.put("lunckCount", lunckCount);
				toUrl +="&couponIdStr="+couponIdStr+"&lunckCount="+lunckCount;
			}
			
			boolean success = copyValueToMember(json, member, tagId, weixinId, brandId);
			resultMap.put("flag", success ? 1 : 0);
			resultMap.put("msg", "添加微信会员成功");
			resultMap.put("url", toUrl);
			return resultMap;
		} else { // 不是crm会员，即跳转到添加会员页面（走粉丝会员活动）
			resultMap.put("flag", 1);
			resultMap.put("msg", "需要添加会员");
			resultMap.put("url", "/bindOldMember/bindingResult.do?brandId=" +brandId + "&weixinId=" + weixinId+"&isSuccess=fail&phoneNo="+phoneNo);
			return resultMap;
		}
	}

	@Override
	public JSONObject queryCrmMember(Integer brandId, String phoneNo) {
		String input = "{'merchantId':"+brandId+",'mobile':"+phoneNo+"}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(queryMemberByPhoneNo, input,  Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			return JSONObject.fromObject(result);
		}
		return null;
	}

	/** 将从crm中取会员信息，且更新会员信息*/
	private boolean copyValueToMember(JSONObject jo, MemberVO member, Integer tagId, String weixinId, Integer brandId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Integer membershipId = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getInt("id");
		String cardNo = jo.getJSONObject("data").getJSONObject("result").getString("cardNo");
		String phoneNo = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("mobile");
		if (member.getBirthday() != null) {
			String birthday = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("birthday");
			try {
				if (!StringUtils.isBlank(birthday) && DateUtil.isDate(birthday)) {
					member.setBirthday(sdf.parse(birthday));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		String name = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("name");
		// 性别
		Integer gender = null;
		String genderStr = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("gender");

		if (!StringUtils.isBlank(genderStr) && !genderStr.equals("null")) {
			gender = Integer.parseInt(genderStr);
		}

		if (tagId!=null) { // tagId不为空的时候调用打标签服务
			// 调用[会员打标签服务]
			try {
				saveOrModifyMembershipTagService.saveMembershipTagService(tagId, membershipId);
			} catch (Exception e) {
				log.error("[会员打标签服务]调用失败");
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}

		// 如果此时生日为空，则根据membershipId找寻相关电子会员的生日记录
		if (member.getBirthday() == null) {
			List<MemberVO> memberList = memberDao.getMemberListByMembershipId(membershipId);
			if (memberList != null) {
				for (int i = 0; i < memberList.size(); i++) {
					MemberVO m = memberList.get(i);
					if (m.getBirthday() != null) {
						member.setBirthday(m.getBirthday());
						break;
					}
				}
			}
		}

		member.setName(name);
		member.setGender(gender);
		member.setMembershipId(membershipId);
		member.setCardNo(cardNo);
		member.setPhoneNo(phoneNo);
		member.setIsMember(true);
		MemberVO SubscribeMember = memberDao.getMember(weixinId, brandId);
		// 新入口更新的sql语句
		boolean saveFlag = memberDao.updateMemberPageInfo(member, SubscribeMember.getPhoneNo());

		// 根据membershipId批量修改该会员的相关电子会员信息
		if (saveFlag) {
			memberDao.batchUpdateMemberPageInfo(member);
		}
		return saveFlag;
	}

	@Override
	public String sendIdentifyingCode(String phoneNo, Integer brandId, String weixinId, String title) {
		Random r = new Random();
		String identifyingCode = String.valueOf(r.nextInt(999));
		while (identifyingCode.length() != 3) {
			identifyingCode = String.valueOf(r.nextInt(999));
		}
		log.info("验证码" + identifyingCode);
		IdentifyinginfoVO identifyinginfo = new IdentifyinginfoVO();
		identifyinginfo.setIdentifyingCode(identifyingCode);
		identifyinginfo.setIdentifyingTime(new Timestamp(System.currentTimeMillis()));
		identifyinginfo.setWeixinId(weixinId);
		identifyinginfo.setPhoneNo(phoneNo);
		identifyinginfo.setBrandId(brandId);
		memberDao.insertIdentifyinginfo(identifyinginfo);

		try {
			MessageSender.sendSMS("验证码为" + identifyingCode + ",欢迎关注" + title, phoneNo, smsAddress, smsUsername, brandId);
			return identifyingCode;
		} catch (JMSException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}
	
	
}
