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
package com.yazuo.weixin.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.TestDao;
import com.yazuo.weixin.service.Test;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.vo.MemberVO;

/**
 * @Description TODO
 * @author yazuo
 * @date 2014-7-23 下午6:39:25
 */
@Service
public class TestImpl implements Test {
	//日志
	Logger log = Logger.getLogger(this.getClass());
	
	@Value("#{propertiesReader['registerMircoMessageMembershipTest']}")
	private String registerMircoMessageMembership;
	
	@Resource
	private TestDao testDao;
	/**
	 * @Title savePersonData
	 * @Description 
	 * @return
	 * @see com.yazuo.weixin.service.Test#savePersonData()
	 */
	@Override
	public int savePersonData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> list = testDao.getQhsfData();
		for (Map<String, Object> map : list) {
			String mobile = String.valueOf(map.get("telephone"));
			String name = String.valueOf(map.get("name"));
			int sex = Integer.parseInt(String.valueOf(map.get("sex")));
			String weixinId = String.valueOf(map.get("wxid"));
			weixinId = weixinId.substring(0, 28);
			String birthday = String.valueOf(map.get("birthday"));
			Date birth = null;
			try {
				birth = sdf.parse(birthday);
			} catch (java.text.ParseException e1) {
				e1.printStackTrace();
			}
			String nameTemp = "";
			try {
				nameTemp = URLEncoder.encode(name, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String json = "{\"mobile\":\"" + mobile + "\",\"merchantId\":137,\"name\":\""+ nameTemp + "\",\"gender\":" + sex
				+ ",\"micromessage\":true,\"microMessageId\":\"" + weixinId + "\",\"birthday\":\""+ sdf.format(birth) + "\",\"birthType\":\"1\"}";

			try {
				json = URLEncoder.encode(json, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			HttpClient httpclient = new DefaultHttpClient();
			String url = registerMircoMessageMembership + "?ciphertext=" + json + "&key=700029&micromessage=true";
			log.info("------url:" + url);
			// 获得HttpGet对象
			HttpGet httpGet = new HttpGet(url);
			// 发送请求
			HttpResponse response = null;
			JSONObject jo = null;
			try {
				response = httpclient.execute(httpGet);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = "";
				try {
					result = EntityUtils.toString(response.getEntity());
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				jo = JSONObject.fromObject(result);
			}
			log.info(jo);
			Integer membershipId = 0;
			String cardNo = "";
			boolean flag =jo!=null ? jo.getJSONObject("data").getJSONObject("result").getBoolean("success") : false;
			if (flag) {
				JSONObject resultJson = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership"); 
				membershipId = resultJson !=null ? resultJson.getInt("id") : 0;
				cardNo = jo.getJSONObject("data").getJSONObject("result").getString("card");
				log.info("-----------membershipId:"+membershipId + "-------会员卡：" + cardNo);
				Integer code = jo.getJSONObject("data").getJSONObject("result").getInt("code");

				if (code == 1) { // crm那边已经是会员
					int backCount = testDao.queryMembershipById(membershipId);
					if (backCount > 0) { // 判断在weixin中是否为会员
						continue;
					} else {
						int count = testDao.save(weixinId, mobile, cardNo, membershipId, birthday, sex, name);
						log.info("------------返回结果：" + count);
					}
				} else { // crm那边不是会员
					int count = testDao.save(weixinId, mobile, cardNo, membershipId, birthday, sex, name);
					log.info("------------返回结果：" + count);
				}
			}
		}
		return 0;
	}
}
