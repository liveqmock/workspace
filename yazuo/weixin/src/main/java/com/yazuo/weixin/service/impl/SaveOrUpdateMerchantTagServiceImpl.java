package com.yazuo.weixin.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
import com.yazuo.weixin.service.SaveOrUpdateMerchantTagService;
import com.yazuo.weixin.util.Constant;

/**
 * 添加或者修改商户标签信息
 * 
 * @author gaoshan
 * 
 */
@Scope("prototype")
@Service
public class SaveOrUpdateMerchantTagServiceImpl implements SaveOrUpdateMerchantTagService {

	@Value("#{propertiesReader['saveOrUpdateMerchantTag']}")
	private String saveOrUpdateMerchantTag;

	private final static String TAG_TYPE_ = "6";

	@Autowired
	private MemberDao memberDao;

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 调用crmapi的[添加或者修改商户标签信息saveOrUpdateMerchantTag]接口，添加商户标签
	 * 
	 * @param merchantId
	 *            商户ID
	 * @param id
	 *            标签ID
	 * @param tagName
	 *            标签名称
	 * @param tagType
	 *            标签类型
	 * @return
	 */
	public JSONObject saveOrUpdateMerchantTagService(Integer merchantId, Integer id, String tagName, String tagType) {

		try {
			tagName = URLEncoder.encode(tagName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 入参整理
		StringBuffer sb = new StringBuffer(128);
		sb.append("{");
		sb.append("\"merchantId\":\"").append(merchantId == null ? "" : merchantId).append("\"").append(",");
		sb.append("\"id\":\"").append(id == null ? "" : id).append("\"").append(",");
		sb.append("\"tagName\":\"").append(tagName == null ? "" : tagName).append("\"").append(",");
		sb.append("\"tagType\":\"").append(tagType == null ? "" : tagType).append("\"");
		sb.append("}");
		String input = sb.toString();
		JSONObject jo = null;

		try {
			// 服务调用
			input = URLEncoder.encode(input, "UTF-8");
			HttpClient httpclient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
			HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
			String url = saveOrUpdateMerchantTag + "?ciphertext=" + input + "&key=" + Constant.KEY;
			log.info("[添加或修改商户标签信息]请求：" + url);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response;
			response = httpclient.execute(httpGet);

			// 结果处理
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				log.info("[添加或修改商户标签信息]响应：" + result);
				JSONObject requestObject = JSONObject.fromObject(result);
				String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
				String Alldata = URLDecoder.decode(((JSONObject) requestObject).toString(), "UTF-8");
				jo = JSONObject.fromObject(data);
			} else {
				jo = null;
			}

		} catch (Exception e) {
			log.error("[添加或修改商户标签信息]服务调用失败");
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return jo;
	}
}
