package com.yazuo.weixin.service.impl;

import java.net.URLEncoder;

import javax.jms.JMSException;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.BusinessDao;
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.service.ExternalService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.MessageSender;
import com.yazuo.weixin.vo.MemberVO;

@Scope("prototype")
@Service
public class ExternalServiceImpl implements ExternalService {
	@Value("#{propertiesReader['imageLocationPath']}")
	private String imageLocationPath;
	@Value("#{propertiesReader['poiCompanyStore']}")
	private String poiCompanyStore;
	@Value("#{propertiesReader['smsAddress']}")
	private String smsAddress;
	@Value("#{propertiesReader['smsUsername']}")
	private String smsUsername;
	@Value("#{propertiesReader['voteMembership']}")
	private String voteMembership;

	@Autowired
	private BusinessDao businessDao;
	@Autowired
	private MemberDao memberDao;

	Logger log = Logger.getLogger(this.getClass());

	@Override
	public MemberVO getMemberByOpenId(String openId) {
		return memberDao.getMemberByOpenId(openId);
	}

	@Override
	public String sendSMSByOpenId(String openId, String content) {
		MemberVO member = memberDao.getMemberByOpenId(openId);
		if (member == null) {
			return "the openId is not exist";
		}
		if (!member.getIsMember()) {
			return "the weixin user is not member";
		}

		try {
			MessageSender.sendSMS(content, member.getPhoneNo(), smsAddress,
					smsUsername, member.getBrandId());
		} catch (JMSException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "JMS ERROR";
		}
		return "success";
	}

	@Override
	public String sendSMSByMobileNo(String mobileNo, String content) {

		try {
			MessageSender.sendSMS(content, mobileNo, smsAddress, smsUsername,
					2148);
		} catch (JMSException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "JMS ERROR";
		}
		return "success";
	}

	@Override
	public String bindMobileNoAndOpenId(String mobileNo, String openId,
			Integer brandId) {
		MemberVO member = memberDao.getMemberByOpenId(openId);
		if (member == null) {
			member = new MemberVO();
			member.setBrandId(brandId);
			member.setWeixinId(openId);
			member.setIsMember(false);
			memberDao.insertMember(member);
			member = memberDao.getMember(openId, brandId);
		}
		if (member.getIsMember()) {
			return "openId is binded!";
		}
		if (registerAndVote(mobileNo, openId, member.getBrandId().toString())) {
			return "success";
		} else {
			return "error";
		}

	}

	boolean registerAndVote(String mobile, String weixinId, String merchantId) {
		String json = "{\"mobile\":\"" + mobile + "\",\"merchantId\":"
				+ merchantId + ",\"micromessage\":true,\"microMessageId\":\""
				+ weixinId + "\",\"source\":\"5\"}";
		log.info("排位接口投票并创建会员" + json);
		JSONObject jo = null;
		try {
			jo = voteMembership(json);
		} catch (Exception e) {
			log.error("排位接口投票并创建接口失败" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		if (jo == null) {
			log.info("排位接口失败，接口调用无返回值，但告知用户的是已成功");
			return false;
		}
		log.info("排位接口投票并创建返回" + jo.toString());
		boolean flag = jo.getJSONObject("data").getJSONObject("result")
				.getBoolean("success");
		if (flag) {
			boolean hasMemberInfo = jo.getJSONObject("data")
					.getJSONObject("result").has("membership");
			if (hasMemberInfo) {
				Integer membershipId = jo.getJSONObject("data")
						.getJSONObject("result").getJSONObject("membership")
						.getInt("id");
				String cardNo = jo.getJSONObject("data")
						.getJSONObject("result").getString("card");

				MemberVO member = memberDao.getMemberByOpenId(weixinId);
				member.setMembershipId(membershipId);
				member.setCardNo(cardNo);
				member.setPhoneNo(mobile);
				member.setIsMember(true);
				memberDao.updateMemberInfo(member);
			}
			return true;
		} else {
			log.info("投票失败但告知用户的是已成功");
			return false;
		}
	}

	public JSONObject voteMembership(String json) throws Exception {
		json = URLEncoder.encode(json, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = voteMembership + "?ciphertext=" + json + "&key="
				+ Constant.KEY + "&micromessage=true";

		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);
			return requestObject;
		} else {
			return null;
		}
	}
}
