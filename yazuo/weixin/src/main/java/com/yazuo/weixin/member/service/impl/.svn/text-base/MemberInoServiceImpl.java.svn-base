package com.yazuo.weixin.member.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.MerchantWifiDao;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.member.service.MemberInfoService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.MemberVO;

@Service
public class MemberInoServiceImpl implements MemberInfoService{

	private static final Log log = LogFactory.getLog("weixin");
	
	@Value("#{propertiesReader['updateCardPassword']}")
	private String updateCardPassword;
	@Value("#{propertiesReader['resetCardPassword']}")
	private String resetCardPassword;
	@Value("#{propertiesReader['addOrUpdateWeixinScope']}")
	private String addOrUpdateWeixinScopeUrl;
	@Value("#{propertiesReader['updateAvailableWeixinScope']}")
	private String updateAvailableWeixinScopeUrl;
	
	@Resource
	private MerchantWifiDao merchantWifiDao;
	
	@Override
	public Map<String, Object>  updatePwd(Integer brandId, String cardNo, String newPwd, String pwd) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String input = "{'merchantId':"+brandId+",'cardNo':'"+cardNo+"','newPassword':'"+newPwd+"','password':'"+pwd+"'}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(updateCardPassword, input, Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			JSONObject jo = JSONObject.fromObject(result);
			boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
			String message =  jo.getJSONObject("data").getJSONObject("result").getString("message");
			
			resultMap.put("flag", success);
			resultMap.put("message", message);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object>  resetPwd(Integer brandId, String cardNo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String input = "{'merchantId':"+brandId+",'cardNo':'"+cardNo+"'}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(resetCardPassword, input, Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			JSONObject jo = JSONObject.fromObject(result);
			boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
			String message =  jo.getJSONObject("data").getJSONObject("result").getString("message");
			
			if (success) { // 重置密码成功
				boolean isSccess = merchantWifiDao.addResetPwdRecord(brandId, cardNo);
				log.info(DateUtil.getDate()+" 添加重置密码记录，返回结果：" +isSccess);
			}
			resultMap.put("flag", success);
			resultMap.put("message", message);
		}
		return resultMap;
	}

	@Override
	public boolean judgeCanResetPwd(Integer brandId, String cardNo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long count = merchantWifiDao.resetPwdCountOfToday(brandId, cardNo, sdf.format(new Date()));
		if (count >=3) { // 判断重置密码的次数不能超过三次
			return false;
		}
		return true;
	}

	@Override
	public JSONObject saveMemberScopeInfo(MemberVO member) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (member.getMembershipId()!=null) {
			paramMap.put("membershipId", member.getMembershipId());
		}
		paramMap.put("weixinId", member.getWeixinId());
		paramMap.put("source", member.getDataSource());
		paramMap.put("merchantId", member.getBrandId());
		paramMap.put("scopeId", member.getBrandId());
		paramMap.put("status", member.getStatus());
		paramMap.put("membershipStatus", (member.getIsMember() ? 1 : 2));
		
		Map<String, Object> finalParamMap = new HashMap<String, Object>();
		finalParamMap.put("weixinScope", paramMap);
		
		String input = JSONObject.fromObject(finalParamMap).toString(); // 参数
		String result = "";
		try {
			result = CommonUtil.postSendMessage(addOrUpdateWeixinScopeUrl, input, Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		
		if (!StringUtil.isNullOrEmpty(result)) {
			return JSONObject.fromObject(result);
		}
		return null;
	}

	@Override
	public JSONObject updateAvailableWeixinScope(Integer merchantId, String weixinId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("merchantId", merchantId);
		param.put("weixinId", weixinId);
		String input = JSONObject.fromObject(param).toString();
		
		String result = "";
		try {
			result = CommonUtil.postSendMessage(updateAvailableWeixinScopeUrl, input, Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			return JSONObject.fromObject(result);
		}
		return null;
	}
	
	
	
	
}
