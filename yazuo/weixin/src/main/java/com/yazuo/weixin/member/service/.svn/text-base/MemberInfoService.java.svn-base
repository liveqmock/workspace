package com.yazuo.weixin.member.service;

import java.util.Map;

import com.yazuo.weixin.vo.MemberVO;

import net.sf.json.JSONObject;
public interface MemberInfoService {

	/**修改密码*/
	public Map<String, Object> updatePwd(Integer brandId, String cardNo, String newPwd, String pwd);
	
	/**重置密码*/
	public Map<String, Object>  resetPwd(Integer brandId, String cardNo);
	
	public boolean judgeCanResetPwd(Integer brandId, String cardNo);
	
	/**加入会员分域*/
	public JSONObject saveMemberScopeInfo(MemberVO member);
	
	/**取消关注，更新微信分域关联表状态*/
	public JSONObject updateAvailableWeixinScope(Integer merchantId, String weixinId);
}
