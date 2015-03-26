package com.yazuo.weixin.member.service;

import java.util.List;
import java.util.Map;

import com.yazuo.weixin.member.vo.MembershipBirthdayFamily;

import net.sf.json.JSONObject;

public interface MemberBirthManagerService {

	/**验证输入的验证码是否正确*/
	Map<String, Object> verifyIdenfiyCode(String identifyCode, Integer brandId, String weixinId);
	/**保存家人生日*/
	Map<String, Object> saveMemberFamilyBirthDay(Integer membershipId, Integer brandId, String [] family, String [] familyBirthDay, String [] birthTypes);
	/**查询会员家人生日*/
	List<MembershipBirthdayFamily> getMemberFamilyBirth(Integer membershipId, Integer merchantId);
}
