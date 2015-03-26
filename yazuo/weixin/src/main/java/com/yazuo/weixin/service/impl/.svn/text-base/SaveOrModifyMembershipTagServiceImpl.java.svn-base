package com.yazuo.weixin.service.impl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.service.SaveOrModifyMembershipTagService;
import com.yazuo.weixin.util.Constant;

/**
 * 会员打标签服务
 * 
 * @author gaoshan
 * 
 */
@Scope("prototype")
@Service
public class SaveOrModifyMembershipTagServiceImpl implements SaveOrModifyMembershipTagService {

	@Value("#{propertiesReader['saveMembershipTag']}")
	private String saveMembershipTag;

	@Autowired
	private MemberDao memberDao;

	Logger log = Logger.getLogger(this.getClass());

	@Override
	public Map<String, Object> saveMembershipTagService(String weixinId, Integer merchantId, Integer membershipId)
			throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		if (merchantId == null) {
			log.info("商户ID为必输项，请检查");
			response.put("type", "F");
			response.put("message", "商户ID为必输项，请检查");
			return response;
		}

		Map<String, Object> m = null;
		if (membershipId != null) {
			// 根据商户ID和会员ID查询所属商户的标签ID
			m = memberDao.getTagId(null, merchantId, membershipId);
		} else if (weixinId != null && weixinId.trim().length() > 0) {
			// 根据商户ID和微信ID查询会员所属商户的标签ID
			m = memberDao.getTagId(weixinId, merchantId, null);
		} else {
			log.info("请输入[微信ID]或者[会员ID]，请检查");
			response.put("type", "F");
			response.put("message", "请输入[微信ID]或者[会员ID]，请检查");
			return response;
		}

		if (m == null) {
			log.info("未查询到会员信息");
			response.put("type", "F");
			response.put("message", "未查询到会员信息");
			return response;
		}

		Integer memId = Integer.parseInt(String.valueOf(m.get("membership_id")));
		Integer tagId = Integer.parseInt(String.valueOf(m.get("tag_id")));

		if (tagId != null) {
			JSONObject res = this.saveMembershipTag(tagId, memId);
			if (res == null) {
				response.put("type", "F");
				log.info("[会员打标签服务]调用失败");
			} else {
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				if (success) {// 成功
					response.put("type", "S");
				} else {// 失败
					response.put("type", "F");
					response.put("message", message);
					log.info("[会员打标签服务]调用失败，" + message);
					return response;
				}
			}
		} else {
			response.put("type", "F");
			response.put("message", "商户未添加标签，会员绑定失败");
			log.info("商户未添加标签，会员绑定标签失败");
			return response;
		}
		response.put("type", "S");
		return response;
	}

	private JSONObject saveMembershipTag(Integer tagId, Integer merchantId) {

		// 入参整理
		StringBuffer sb = new StringBuffer(128);
		sb.append("{");
		sb.append("\"membershipId\":\"").append(merchantId == null ? "" : merchantId).append("\"").append(",");
		sb.append("\"tagId\":\"").append(tagId == null ? "" : tagId).append("\"");
		sb.append("}");
		String input = sb.toString();
		JSONObject jo = null;

		try {
			// 服务调用
			input = URLEncoder.encode(input, "UTF-8");
			HttpClient httpclient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
			HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
			String url = saveMembershipTag + "?ciphertext=" + input + "&key=" + Constant.KEY;
			log.info("[会员打标签服务]请求：" + url);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response;
			response = httpclient.execute(httpGet);

			// 结果处理
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				log.info("[会员打标签服务]响应：" + result);
				JSONObject requestObject = JSONObject.fromObject(result);
				String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
				String Alldata = URLDecoder.decode(((JSONObject) requestObject).toString(), "UTF-8");
				jo = JSONObject.fromObject(data);
			} else {
				jo = null;
			}

		} catch (Exception e) {
			log.error("[会员打标签服务]调用失败");
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return jo;
	}

	@Override
	public Map<String, Object> saveMembershipTagService(Integer tabId, Integer membershipId) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
//		if (tabId == null) {
//			log.info("标签ID为必输项，请检查");
//			response.put("type", "F");
//			response.put("message", "标签ID为必输项，请检查");
//			return response;
//		}

		if (membershipId == null) {
			log.info("会员ID为必输项，请检查");
			response.put("type", "F");
			response.put("message", "会员ID为必输项，请检查");
			return response;
		}

		JSONObject res = this.saveMembershipTag(tabId, membershipId);
		if (res == null) {
			response.put("type", "F");
			log.info("[会员打标签服务]调用失败");
		} else {
			Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			if (success) {// 成功
				response.put("type", "S");
			} else {// 失败
				response.put("type", "F");
				response.put("message", message);
				log.info("[会员打标签服务]调用失败，" + message);
				return response;
			}
		}
		
		response.put("type", "S");
		return response;
	}
}
