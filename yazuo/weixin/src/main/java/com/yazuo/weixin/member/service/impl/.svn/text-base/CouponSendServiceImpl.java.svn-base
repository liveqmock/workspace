/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-10-17
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.member.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.BusinessDao;
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.member.service.CouponSendService;
import com.yazuo.weixin.member.service.MemberInfoService;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.service.SaveOrModifyMembershipTagService;
import com.yazuo.weixin.service.WeixinPayService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;


/**
* @ClassName CouponSendServiceImpl
* @Description 优惠券赠送
* @author sundongfeng@yazuo.com
* @date 2014-11-6 下午3:54:46
* @version 1.0
*/
@Service("CouponSendServiceImpl")
public class CouponSendServiceImpl implements CouponSendService {

	private static final Log LOG = LogFactory.getLog("weixin");

	@Value("#{propertiesReader['queryCouponListByIdList']}")
	private String queryCouponListByIdList; // 办卡送的劵查询接口
	@Value("#{propertiesReader['executeCrmActive']}")
	private String executeCrmActive; //发送短信

	ResourceBundle bundle = ResourceBundle.getBundle("image-config");
	// 添加会员接口
	private final String webMembership_url = bundle.getString("registerMircoMessageMembership");

	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	
	@Resource
	private MemberDao memberDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private SaveOrModifyMembershipTagService saveOrModifyMembershipTagService;
	@Resource
	private WeixinMallMartService weixinMallMartService;
	@Resource
	private WeixinPayService weixinPayService;
	@Resource
	private MemberInfoService memberInfoService;

	public Map<String, Object> sendCouponAddMember(Integer brandId, String phoneNo) {

		Map<String, Object> member = new HashMap<String, Object>();

		Map<String, Object> map = memberDao.getMember(brandId, phoneNo);
		int flag = 1;// 有效
		Integer membershipId = 0;
		
		boolean b = false;
		if (map != null) {
			b = (Boolean) map.get("is_member");// 是否是会员
			if (b) {// 是会员
				member.put("weixin_id", map.get("weixin_id"));
				member.put("brand_id", brandId);
				membershipId = Integer.parseInt(map.get("membership_id")+"");
				member.put("membership_id", membershipId);
				member.put("flag", true);//会员标识
			}
		}
		if (b == false) {// 不是会员插入数据
			BusinessVO bus = businessDao.getBusinessByBrandId(brandId);

			if (bus == null) {
				flag = 0;// 无效
				LOG.info("添加会员-未查询到business信息");
			} else {
				Integer tagId = bus.getTagId();
				String tagIdStr = "";
				if (tagId != null) {
					tagIdStr = tagId.toString();
				}
				String uuid = UUID.randomUUID().toString().trim();
				uuid = (uuid.length() > 32 ? uuid.substring(0, 32) : uuid);

				String json = "{\"mobile\":\"" + phoneNo + "\",\"source\":" + Constant.MEMBERSOURCE_5 + ",\"merchantId\":"
						+ String.valueOf(brandId.intValue()) + ",\"micromessage\":true,\"microMessageId\":\""
						+ uuid + "\",\"tagId\":\"" + tagIdStr + "\"}";
				try {
					// 加入会员
					JSONObject jo = registerMembership(json);
					LOG.info("创建会员返回" + jo.toString());
					if (jo == null) {
						LOG.info("请到实体店办理，jo为null");
					} else {
						Map<String, Object> memberMap = new HashMap<String, Object>();
						memberMap.put("tagId", tagId);
						memberMap.put("brand_id", brandId);
						memberMap.put("phone_no", phoneNo);
						memberMap.put("weixin_id", uuid);
						// 微信加入会员
						flag = saveMembership(jo, memberMap);
						LOG.info("添加会员返回结果：" + flag + ";membership_id:" + memberMap.get("membership_id"));
						membershipId =memberMap.get("membership_id")!=null ? Integer.parseInt(memberMap.get("membership_id")+""):null;
						member.put("weixin_id", uuid);
						member.put("brand_id", brandId);
						member.put("membership_id", membershipId);
						member.put("flag", false);//会员标识
						
						/*---------------分域------begin----------------------------*/
						MemberVO memberScope = new MemberVO();
						memberScope.setMembershipId(membershipId);
						memberScope.setBrandId(brandId);
						memberScope.setDataSource(Constant.MEMBER_SCOPE_SOURCE_1); // 微信加会员
						memberScope.setWeixinId(uuid);
						memberInfoService.saveMemberScopeInfo(memberScope); // 返回结果新不处理
						/*---------------分域------end----------------------------*/
					}
				} catch (Exception e) {
					flag = 0;
					LOG.error("网页调用注册会员接口失败" + e.getMessage());
				}
			}
		}
		
		return member;
	}
	
	/**
	 * 
	 * @Description 保存会员
	 * @param resmap
	 * @return
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	private int saveMembership(JSONObject jo, Map<String, Object> memberMap) {

		boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
		int flag = 1;// 有效
		if (success) {
			Integer membershipId = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getInt("id");
			String cardNo = jo.getJSONObject("data").getJSONObject("result").getString("card");
			long timeStart = System.currentTimeMillis();
			if (!StringUtil.isNullOrEmpty(memberMap.get("tagId")+"")) { // tagId不为空的时候调用打标签服务
				Integer tagId = (Integer) memberMap.get("tagId");
				try {
					// 调用[会员打标签服务]
					saveOrModifyMembershipTagService.saveMembershipTagService(tagId, membershipId);
				} catch (Exception e) {
					LOG.error("网页版加会员调用[会员打标签服务]调用失败" + e.getMessage());
				}
			}
			LOG.info("--saveMembership网页版会员打标签接口...耗时::" + (System.currentTimeMillis() - timeStart) + "毫秒");

			memberMap.put("membership_id", membershipId);
			memberMap.put("card_no", cardNo);
			memberMap.put("is_subscribe", "t");
			memberMap.put("is_member", "t");
			memberMap.put("member_type", Constant.MEMBERTYPE_1);
			memberMap.put("data_source", Constant.MEMBERSOURCE_5);
			memberMap.put("update_times", 0);
			// 加入会员
			boolean saveFlag = memberDao.insertMember(memberMap);
			if (!saveFlag) {
				flag = 0;
			}
		} else {// 请到实体店办理
			flag = 0;
		}
		return flag;
	}
	
	/**
	 * 
	 * @Description 请求添加会员接口
	 * @param json
	 * @throws Exception
	 * @return Map<String,Object>
	 * @throws
	 */
	private JSONObject registerMembership(String json) throws Exception {
		// 调用加入会员接口耗時
		long timeStart = System.currentTimeMillis();

		json = URLEncoder.encode(json, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = webMembership_url + "?ciphertext=" + json + "&key=" + Constant.KEY + "&micromessage=true";
		LOG.info("---请求地址："+url);
		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			LOG.info("--registerMembership返回结果："+result);
			JSONObject requestObject = JSONObject.fromObject(result);
			LOG.info("--registerMembership网页版访问添加会员接口...耗时::" + (System.currentTimeMillis() - timeStart) + "毫秒");
			return requestObject;
		} else {
			return null;
		}
	}

}
