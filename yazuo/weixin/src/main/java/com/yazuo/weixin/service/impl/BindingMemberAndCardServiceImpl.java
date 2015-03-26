package com.yazuo.weixin.service.impl;

import java.net.URLDecoder;
import java.net.URLEncoder;
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
import com.yazuo.weixin.service.BindingMemberAndCardService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.StringUtil;

/**
 * 添加或者修改商户标签信息
 * 
 * @author gaoshan
 * 
 */
@Scope("prototype")
@Service
public class BindingMemberAndCardServiceImpl implements BindingMemberAndCardService {

	@Value("#{propertiesReader['bindingMemberAndCard']}")
	private String bindingMemberAndCard;

	private final static String TAG_TYPE_ = "6";

	@Autowired
	private MemberDao memberDao;

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 调用crmapi的[绑定会员手机号和实体卡卡号BindingMemberAndCard]接口
	 * 
	 * <p>
	 * 业务背景： 快餐消费首次消费完成会员的信息为系统默认的信息， 会员卡在首次消费之后会处于冻结状态，
	 * 如果需要二次消费，必须解冻会员卡才可以做二次消费。
	 * 
	 * 需求介绍： 根据会员卡卡号和会员手机号，绑定卡和会员的在一起， 更新会员新和卡信息状态信息
	 * </p>
	 */
	@Override
	public JSONObject bindingMemberAndCardService(Map<String, Object> params) throws Exception {

		String cardNo = String.valueOf(params.get("cardNo")); // 会员卡号
		String merchantId = String.valueOf(params.get("merchantId")); // 商户ID
		String securityCode = String.valueOf(params.get("securityCode")); // 安全码
		String mobile = String.valueOf(params.get("mobile")); // 手机号
		String name = String.valueOf(params.get("name")); // 名字
		String microMessageId = String.valueOf(params.get("microMessageId")); // 微信ID
		String gender = String.valueOf(params.get("gender")); // 性别
		String birthType = String.valueOf(params.get("birthType")); // 生日类型
		String birthday = String.valueOf(params.get("birthday")); // 生日日期
		String password = String.valueOf(params.get("password")); // 密码
		

		// 入参整理
		StringBuffer sb = new StringBuffer(128);
		sb.append("{");
		sb.append("\"cardNo\":\"").append(cardNo == null ? "" : cardNo).append("\"").append(",");
		sb.append("\"merchantId\":\"").append(merchantId == null ? "" : merchantId).append("\"").append(",");
		sb.append("\"securityCode\":\"").append(securityCode == null ? "" : securityCode).append("\"").append(",");
		sb.append("\"mobile\":\"").append(mobile == null ? "" : mobile).append("\"").append(",");
		sb.append("\"name\":\"").append(name == null ? "" : name).append("\"").append(",");
		sb.append("\"microMessageId\":\"").append(microMessageId == null ? "" : microMessageId).append("\"").append(",");
		sb.append("\"gender\":\"").append(gender == null ? "" : gender).append("\"").append(",");
		sb.append("\"birthType\":\"").append(StringUtil.isNullOrEmpty(birthType) ? "" : birthType).append("\"").append(",");
		sb.append("\"birthday\":\"").append(birthday == null ? "" : birthday).append("\"").append(",");
		sb.append("\"password\":\"").append(password == null ? "" : password).append("\"");
		sb.append("}");
		String input = sb.toString();
		JSONObject jo = null;

		try {
			// 服务调用
			input = URLEncoder.encode(input, "UTF-8");
			HttpClient httpclient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 8000);// 设置连接超时时间(单位毫秒)
			HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);// 设置读数据超时时间(单位毫秒)
			String url = bindingMemberAndCard + "?ciphertext=" + input + "&key=" + Constant.ENTITYKEY;
			log.info("[绑定会员手机号和实体卡卡号]请求：" + url);
			long startTime = System.currentTimeMillis();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response;
			response = httpclient.execute(httpGet);

			// 结果处理
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				log.info("[绑定会员手机号和实体卡卡号]响应：" + result);

				String crmInterface = bindingMemberAndCard.substring((bindingMemberAndCard.lastIndexOf("/")+1),bindingMemberAndCard.lastIndexOf("."));
				log.info("--访用CRM【"+crmInterface+"】接口...耗时::" + (System.currentTimeMillis() - startTime) + "毫秒");
				
				JSONObject requestObject = JSONObject.fromObject(result);
				String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
				String Alldata = URLDecoder.decode(((JSONObject) requestObject).toString(), "UTF-8");
				jo = JSONObject.fromObject(Alldata);
			} else {
				jo = null;
			}

		} catch (Exception e) {
			log.error("[绑定会员手机号和实体卡卡号]服务调用失败");
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return jo;
	}
}
