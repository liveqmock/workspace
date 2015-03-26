package com.yazuo.weixin.member.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.member.service.MemberBirthManagerService;
import com.yazuo.weixin.member.vo.MembershipBirthdayFamily;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.IdentifyinginfoVO;
@Service
public class MemberBirthManagerServiceImpl implements MemberBirthManagerService {
	private static final Log weixinLog = LogFactory.getLog("weixin");
	@Resource
	private MemberDao memberDao;
	@Value("#{propertiesReader['saveMemberFamilyBirthDay']}")
	private String saveMemberFamilyBirthDayUrl; // 保存会员家人生日
	@Value("#{propertiesReader['getMemberFamilyBirthDay']}")
	private String getMemberFamilyBirthDayUrl; // 查询会员家人生日
	
	@Override
	public Map<String, Object> verifyIdenfiyCode(String identifyCode, Integer brandId, String weixinId) {
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
		resultMap.put("flag", 1);
		resultMap.put("msg", "验证码正确");
		return resultMap;
	}


	@Override
	public Map<String, Object> saveMemberFamilyBirthDay(Integer membershipId, Integer brandId, String [] family, String [] familyBirthDay, String [] birthTypes) {
		List<MembershipBirthdayFamily> list = new ArrayList<MembershipBirthdayFamily>();
		for(int i=0; i <family.length; i++) {
			MembershipBirthdayFamily mf = new MembershipBirthdayFamily();
			mf.setMembershipId(membershipId);
			mf.setMerchantId(brandId);
			mf.setDictFamilyRelationId(Integer.parseInt(family[i]));
			mf.setBirthdayFamilyDate(familyBirthDay[i]);
			mf.setBirthdayFamilySeq(i);
			mf.setBirthdayFamilyFlag(0);
			mf.setBirthdayFamilyAddtime("");
			mf.setBirthdayType(Integer.parseInt(birthTypes[i]));
			list.add(mf);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("membershipId", membershipId);
		paramMap.put("familyList", list);
		String input = JSONObject.fromObject(paramMap).toString();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = "";
		try {
			result = CommonUtil.postSendMessageForCrmOldInterface(saveMemberFamilyBirthDayUrl, input, Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			JSONObject jo = JSONObject.fromObject(result);
			if(jo !=null) {
				boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
				String message = jo.getJSONObject("data").getJSONObject("result").getString("message");
				resultMap.put("flag", success);
				resultMap.put("message", message);
			}
		}
		return resultMap;
	}


	@Override
	public List<MembershipBirthdayFamily> getMemberFamilyBirth(Integer membershipId, Integer merchantId) {
		List<MembershipBirthdayFamily> familyList = new ArrayList<MembershipBirthdayFamily>();
		String input = "{\"membershipId\":"+membershipId+",\"merchantId\":"+merchantId+"}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(getMemberFamilyBirthDayUrl, input, Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			JSONObject jo = JSONObject.fromObject(result);
			if(jo !=null) {
				boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
				if(success) {
					JSONArray array = jo.getJSONObject("data").getJSONObject("result").getJSONArray("list");
					Iterator it = array.iterator();
					while(it.hasNext()){
						JSONObject temp = (JSONObject)it.next();
						MembershipBirthdayFamily family = (MembershipBirthdayFamily) JSONObject.toBean(temp, MembershipBirthdayFamily.class);
						familyList.add(family);
					}
				}
			}
		}
		return familyList;
	}
}
