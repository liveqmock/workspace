package com.yazuo.weixin.member.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
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
import com.yazuo.weixin.dao.MerchantWifiDao;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.member.CommonShortLink;
import com.yazuo.weixin.member.service.MemberInfoService;
import com.yazuo.weixin.member.service.MerchantWifiService;
import com.yazuo.weixin.member.service.ResourceManagerService;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.service.SaveOrModifyMembershipTagService;
import com.yazuo.weixin.service.WeixinPayService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;

/**
 * @InterfaceName: MerchantWifiServiceImpl
 * @Description: 处理wifi逻辑的实现
 */
@Service("merchantWifiService")
public class MerchantWifiServiceImpl implements MerchantWifiService {

	private static final Log LOG = LogFactory.getLog("weixin");

	@Resource
	private ResourceManagerService resourceManagerService;
	
	@Value("#{propertiesReader['queryCouponListByIdList']}")
	private String queryCouponListByIdList; // 办卡送的劵查询接口
	@Value("#{propertiesReader['executeCrmActive']}")
	private String executeCrmActive; //发送短信
	@Value("#{propertiesReader['registerMircoMessageMembership']}")
	private String webMembership_url; // 添加会员接口
	@Value("#{propertiesReader['getMemberByMembershipId']}")
	private String queryRedirectPage; // 查询推送哪个页面
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Value("#{propertiesReader['getProtalSettingCardTypeId']}")
	private String getProtalSettingCardTypeIdUrl; // 取wifi设置卡推荐页的卡类型id
	@Value("#{propertiesReader['getWifiCustomAdvertUrl']}")
	private String getWifiCustomAdvertUrl; // 取wifi设置自定义广告内容
	
	@Resource
	private MemberDao memberDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private SaveOrModifyMembershipTagService saveOrModifyMembershipTagService;
	@Resource
	private MerchantWifiDao merchantWifiDao;
	@Resource
	private WeixinMallMartService weixinMallMartService;
	@Resource
	private WeixinPayService weixinPayService;
	@Resource
	private WeixinPhonePageService weixinPhonePageService;
	@Resource
	private MemberInfoService memberInfoService;
	

	public Map<String, Object> connectWifiAddMember(Integer brandId, String phoneNo, String path) {

		Map<String, Object> member = new HashMap<String, Object>();

		int flag = 1;// 有效
		Integer membershipId = 0;
		Map<String, Object> map = memberDao.getMember(brandId, phoneNo);
		
		boolean b = false;
		if (map != null) {
			b = (Boolean) map.get("is_member");// 是否是会员
			if (b) {// 是会员
				member.put("weixin_id", map.get("weixin_id"));
				member.put("brand_id", brandId);
				membershipId = Integer.parseInt(map.get("membership_id")+"");
			}
		}
		if (b == false) {// 不是会员插入数据
			BusinessVO bus = businessDao.getBusinessByBrandId(brandId);

			if (bus == null) {
				flag = 0;// 无效
				LOG.info("网页添加会员-未查询到business信息");
			} else {
				Integer tagId = bus.getTagId();
				String tagIdStr = "";
				if (tagId != null) {
					tagIdStr = tagId.toString();
				}

				String json = "{\"mobile\":\"" + phoneNo + "\",\"source\":" + Constant.MEMBERSOURCE_14 + ",\"merchantId\":"
						+ String.valueOf(brandId.intValue()) + ",\"micromessage\":true,\"microMessageId\":\""
						+ Constant.WEIXIN_WIFI + "\",\"tagId\":\"" + tagIdStr + "\"}";
				try {
					// 加入会员
					JSONObject jo = registerMembership(json);
					LOG.info("网页端创建会员返回" + jo.toString());
					if (jo == null) {
						LOG.info("请到实体店办理，jo为null");
					} else {
						Map<String, Object> memberMap = new HashMap<String, Object>();

						String uuid = UUID.randomUUID().toString().trim();
						uuid = (uuid.length() > 32 ? uuid.substring(0, 32) : uuid);
						memberMap.put("tagId", tagId);
						memberMap.put("brand_id", brandId);
						memberMap.put("phone_no", phoneNo);
						memberMap.put("weixin_id", uuid);
						// 微信加入会员
						flag = saveMembership(jo, memberMap);
						LOG.info("添加会员返回结果：" + flag + ";membership_id:" + memberMap.get("membership_id"));
						membershipId =memberMap.get("membership_id")!=null ? Integer.parseInt(memberMap.get("membership_id")+""):null;
						// 获得优惠券
						JSONObject luckobj = jo!=null ? jo.getJSONObject("data").getJSONObject("result").getJSONObject("activeMap") : null;
						String couponIdStr = StringUtil.isNullOrEmpty(luckobj.toString()) ? "" : (luckobj.get("0000_3") + ""); // 赠送劵id
						JSONArray coupons = getCoupons(couponIdStr, brandId);
						member.put("weixin_id", uuid);
						member.put("brand_id", brandId);
						member.put("coupons", coupons);
						member.put("business", bus);
					}
				} catch (Exception e) {
					flag = 0;
					LOG.error("网页调用注册会员接口失败" + e.getMessage());
				}
			}
		}
		// 调用crm接口判断推送哪个显示页
		if (membershipId==null || membershipId ==0) {
			if (brandId.intValue() == Constant.WIFI_PUSH_PAGE_BRAND) {
				member.put("isPush", false);
			} else {
				member.put("isPush", true);
			}
		} else {
			boolean isPush = false;
			// 是否下发推送页
			boolean pushPage = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_PAGE_TYPE_VALUE, brandId);
			// 是否下发短信
			boolean pushSms = resourceManagerService.judgeExistsResourceOfBrand(Constant.WIFI_PUSH_SMS_TYPE_VALUE, brandId);
			LOG.info("商户id："+brandId+";是否设置门店wifi下发推送页："+pushPage+";是否设置门店wifi下发短信："+pushSms);
			if(pushPage){
				isPush = judgeRedirectPage(brandId, membershipId);
			}
			if (pushSms) {
				String smsUrl = "";
				if (isPush) {
					smsUrl = serverIp + path + "/merchantWifi/pushPage.do?brandId="+brandId+"&weixinId="+member.get("weixin_id");
				} else {
					smsUrl = serverIp+ path + "/weixin/phonePage/fensiCard.do?brandId="+brandId+"&weixinId="+member.get("weixin_id");
				}
				LOG.info("短信链接地址：" + smsUrl);
				//调用发送短信
				dealSms(phoneNo, brandId, membershipId, smsUrl);
			}
			LOG.info("商户id："+brandId+"；是否进推送页：" + isPush);
			member.put("isPush", isPush);
		}
		
		member.put("flag", flag);
		member.put("data_source", Constant.MEMBERSOURCE_14);
		return member;
	}
	
	

	@Override
	public boolean judgeRedirectPage(Integer brandId, Integer membershipId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", brandId);
		map.put("id", membershipId);
		String input = JSONObject.fromObject(map).toString();//"{'merchantId'："+brandId+",'id':"+membershipId+"}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(queryRedirectPage, input, Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			JSONObject jo = JSONObject.fromObject(result);
			if (jo !=null) {
				boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
				if (success) {
					// 取返回的信息
					JSONArray array = jo.getJSONObject("data").getJSONObject("result").getJSONArray("level");
					if (array ==null || (array!=null && array.contains(1))) { // 返回的会员等级是空或者是1(粉丝会员)
						return true;
					}
				}
			}
		}
		return false;
	}



	@Override
	public void dealSms(String phoneNo, Integer brandId, Integer membershipId, String smsUrl) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = sdf.format(new Date());
		// 今天是否发送过短信
		boolean isSendSms = merchantWifiDao.isSendSmsOfToday(brandId, phoneNo, todayDate);
		LOG.info("是否已发送过短信："+ (isSendSms ? "已发送过" : "未发送"));
		if (!isSendSms) { // 未发送短信
			// 调用获取短链方法
			smsUrl = CommonShortLink.getShortLink(smsUrl);
			// 调crm接口发短信
			String input = "{\"merchantId\":"+brandId+",\"membershipId\":"+membershipId+",\"smsShortUrl\":\""+smsUrl+"\"}";
			String result = "";
			try {
				result = CommonUtil.postSendMessage(executeCrmActive, input, Constant.KEY+"");
			} catch (WeixinRuntimeException e) {
				e.printStackTrace();
			}
			if (!StringUtil.isNullOrEmpty(result)) {
				JSONObject jo = JSONObject.fromObject(result);
				if (jo !=null) {
					boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
					if (success) { // 发送短信成功
						boolean isSuccess = merchantWifiDao.addSmsRecord(brandId, phoneNo);
						LOG.info("添加短信记录：" + isSuccess);
					}
				}
			}
		}
		
	}

	@Override
	public String getWeixinId(String code, Integer brandId) {
		String weixinId = "";
		WxMallMerchantDict dict = weixinMallMartService.queryMerchantParam(brandId);
		if (dict == null) {
			LOG.info("商家id为："+brandId+"的未维护appId和appSecret参数值");
			return weixinId;
		} 
		LOG.info("AppId:" + dict.getAppId() + ";AppSecret:"+dict.getAppSecret()+";code:"+code);
		JSONObject json = null;
		try {
			json = weixinPayService.getWeiXinId(code,"", dict.getAppId(), dict.getAppSecret());
		} catch (Exception e) {
			LOG.info("获取微信id异常");
			e.printStackTrace();
		}
		if(json !=null) {
			weixinId = json.getString("openid");
		}
		return weixinId;
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
			memberMap.put("data_source", Constant.MEMBERSOURCE_14);
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
	
	

	@Override
	public boolean clearData() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = sdf.format(new Date());
		return merchantWifiDao.delSmsRecord(todayDate);
	}

	/**
	 * 
	 * @Description 获得优惠券接口
	 * @param json
	 * @return JSONArray
	 */
	public JSONArray getCoupons(String couponIdStr, Integer brandId) {
		JSONArray array = new JSONArray();
		if (!StringUtil.isNullOrEmpty(couponIdStr)) { // 送的劵
			String[] splitArray = couponIdStr.split(",");
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < splitArray.length; i++) {
				list.add(Integer.parseInt(splitArray[i]));
			}
			// 调用crm接口查询劵信息
			String input = "{'merchantId':" + brandId + ", 'idList':" + list + "}";
			String result = "";
			try {

				long timeStart = System.currentTimeMillis();
				result = CommonUtil.postSendMessage(queryCouponListByIdList, input, Constant.KEY + "");
				LOG.info("--getCoupons网页版查询券接口...耗时::" + (System.currentTimeMillis() - timeStart) + "毫秒");
			} catch (WeixinRuntimeException e) {
				e.printStackTrace();
			}
			if (!StringUtil.isNullOrEmpty(result)) {
				JSONObject json = JSONObject.fromObject(result);
				JSONObject resultObj = json.getJSONObject("data").getJSONObject("result");
				boolean success = resultObj.getBoolean("success");
				if (success) {
					array = resultObj.getJSONArray("couponList");
				}
			}
		}
		return array;
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



	@Override
	public Map<String, Object> visitMemberByWifi(String param) {
		LOG.info("--调用visitMemberByWifi方法---参数："+param);
		int flag = 1; // 有效
		Map<String, Object> member = new HashMap<String, Object>();
		Integer brandId = null;
		String phoneNo = "";
		Integer settingAdvertSource = null;
		boolean isCrmMemberUser = false;
		Integer merchantId = null;
		String brandName = "";
		if (!StringUtil.isNullOrEmpty(param)) {
			JSONObject jo = JSONObject.fromObject(param);
			if (jo !=null) {
				if (StringUtil.isNullOrEmpty(jo.get("brandId")+"")) {
					member.put("flag", 0);
					LOG.info("--调用visitMemberByWifi方法---参数brandId为空");
					return member;
				}
				if (StringUtil.isNullOrEmpty(jo.get("phoneNo")+"")) {
					LOG.info("--调用visitMemberByWifi方法---参数phoneNo为空");
					member.put("flag", 0);
					return member;
				}
				if (StringUtil.isNullOrEmpty(jo.get("settingAdvertSource")+"")) {
					LOG.info("--调用visitMemberByWifi方法---参数settingAdvertSource为空");
					member.put("flag", 0);
					return member;
				}
				if (StringUtil.isNullOrEmpty(jo.get("isCrmMemberUser")+"")) {
					LOG.info("--调用visitMemberByWifi方法---参数isCrmMemberUser为空");
					member.put("flag", 0);
					return member;
				}
				if (StringUtil.isNullOrEmpty(jo.get("merchantId")+"")) {
					LOG.info("--调用visitMemberByWifi方法---参数merchantId为空");
					member.put("flag", 0);
					return member;
				}
				brandId = jo.getInt("brandId");
				phoneNo = jo.getString("phoneNo");
				isCrmMemberUser = jo.getBoolean("isCrmMemberUser");
				settingAdvertSource = jo.getInt("settingAdvertSource");
				merchantId = jo.getInt("merchantId");
				brandName = jo.getString("brandName");
			}
		}
		member.put("isCrmMemberUser", isCrmMemberUser);
		member.put("settingAdvertSource", settingAdvertSource);
		member.put("brandId", brandId);
		
		
		Map<String, Object> map = memberDao.getMember(brandId, phoneNo);
		BusinessVO bus = businessDao.getBusinessByBrandId(brandId);
		//是wifi来源且此商户在微信数据库bussiness表中没有
		if (bus == null) {
			bus = new BusinessVO();
			bus.setBrandId(brandId);
			bus.setTitle(brandName);
			member.put("hasImage", false);
		} else {
			member.put("hasImage",weixinPhonePageService.hasImage(brandId,bus.getSmallimageName()));
		}
		member.put("business", bus);
		boolean b = false;
		if (map != null) {
			b = (Boolean) map.get("is_member");// 是否是会员
			if (b) {// 是会员
				member.put("weixin_id", map.get("weixin_id"));
				member.put("brand_id", brandId);
			}
		}
		if (b == false) {// 不是会员插入数据
			Integer tagId = bus.getTagId();
			String tagIdStr = "";
			if (tagId != null) {
				tagIdStr = tagId.toString();
			}
			String json = "{\"mobile\":\"" + phoneNo + "\",\"source\":" + Constant.MEMBERSOURCE_14 + ",\"merchantId\":"
					+ String.valueOf(brandId.intValue()) + ",\"micromessage\":true,\"microMessageId\":\""
					+ Constant.WEIXIN_WIFI + "\",\"tagId\":\"" + tagIdStr + "\"}";
			try {
				// 加入会员
				JSONObject jo = registerMembership(json);
				LOG.info("网页端创建会员返回" + jo.toString());
				if (jo != null) {
					Map<String, Object> memberMap = new HashMap<String, Object>();

					String uuid = UUID.randomUUID().toString().trim();
					uuid = (uuid.length() > 32 ? uuid.substring(0, 32) : uuid);
					memberMap.put("tagId", tagId);
					memberMap.put("brand_id", brandId);
					memberMap.put("phone_no", phoneNo);
					memberMap.put("weixin_id", uuid);

					/*---------------分域------begin----------------------------*/
					Integer membershipId = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getInt("id");
					MemberVO memberScope = new MemberVO();
					memberScope.setMembershipId(membershipId);
					memberScope.setBrandId(brandId);
					memberScope.setDataSource(Constant.MEMBER_SCOPE_SOURCE_4); // 微信加会员
					memberScope.setWeixinId(uuid);
					memberScope.setStatus(1);
					memberScope.setIsMember(true);
					memberInfoService.saveMemberScopeInfo(memberScope); // 返回结果新不处理
					/*---------------分域------end----------------------------*/
					
					// wifi过来的商户在微信中没有
					if (bus == null) memberMap.put("merchant_source", 1);
					
					// 微信加入会员
					flag = saveMembership(jo, memberMap);
					LOG.info("添加会员返回结果：" + flag);
					// 获得优惠券
					JSONObject luckobj = jo!=null ? jo.getJSONObject("data").getJSONObject("result").getJSONObject("activeMap") : null;
					String couponIdStr = "";
					couponIdStr =StringUtil.isNullOrEmpty(luckobj.toString()) ? "" : (luckobj.get("0000_3") + ""); // 赠送劵id
					if (settingAdvertSource.intValue() == Constant.ADVERT_SOURCE_CARD || settingAdvertSource.intValue() == Constant.ADVERT_SOURCE_CUSTOM) { // 进卡推荐页
						member.put("couponArray", getCoupons(couponIdStr, brandId)); // 券信息
					} else if(settingAdvertSource == Constant.ADVERT_SOURCE_MEMBER){ // 直接进会员中心
						member.put("couponIdStr", couponIdStr);
					}
					
					//------------------------------- 获取卡信息--begin-------------------------------
					String cardNo = jo.getJSONObject("data").getJSONObject("result").getString("card");
					String moblie = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("mobile");
					JSONObject cardvo=jo.getJSONObject("data").getJSONObject("result").getJSONObject("cardVo");
					if(cardvo!=null&& !StringUtil.isNullOrEmpty(cardvo.getString("cardtypeId"))){
						Integer cardtypeId = cardvo.getInt("cardtypeId");
						member.put("cardtypeId", cardtypeId.toString());
					}else{
						member.put("cardtypeId", "");
					}
					double storeBalance=0;
					double integralAvailable=0;
					if(cardvo!=null&&!StringUtil.isNullOrEmpty(cardvo.getString("storeBalance"))){
						storeBalance  = cardvo.getDouble("storeBalance");
					}
					if(cardvo!=null&&!StringUtil.isNullOrEmpty(cardvo.getString("integralAvailable"))){
						integralAvailable=cardvo.getDouble("integralAvailable");
					}
					member.put("cardNo", cardNo);
					member.put("storeBalance", storeBalance);
					member.put("integralAvailable", integralAvailable);
					member.put("phoneNo", moblie);
					//------------------------------- 获取卡信息-end--------------------------------
					member.put("weixin_id", uuid);
					member.put("brand_id", brandId);
				} else 
					LOG.info("请到实体店办理，jo为null");
				
			} catch (Exception e) {
				flag = 0;
				LOG.error("网页调用注册会员接口失败" + e.getMessage());
			}
		}
		member.put("flag", flag);
		return member;
	}




	@Override
	public List<Integer> getCardTypeIdsByMerchantId(Integer brandId) {
		String input = "{\"brandId\":"+brandId+"}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(getProtalSettingCardTypeIdUrl, input, null);
		} catch (WeixinRuntimeException e) {
			LOG.info("调用wifi取卡推荐页设置的卡类型id失败");
			e.printStackTrace();
			LOG.info(e.getMessage());
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			JSONObject jo = JSONObject.fromObject(result);
			if(jo !=null) {
				int flag = jo.getInt("flag");
				LOG.info("-------调用wifi取卡推荐页设置的卡类型id返回信息："+jo.getString("message"));
				if (flag == 1) {
					return jo.getJSONArray("data");
				}
			}
		}
		return null;
	}

	@Override
	public String getCustomAdvert(Integer merchantId) {
		String input = "{\"merchantId\":"+merchantId+"}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(getWifiCustomAdvertUrl, input, null);
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			JSONObject jo = JSONObject.fromObject(result);
			if (jo !=null) {
				if(jo.getInt("flag")==1){
					String resultStr = jo.getString("data");
					if (!StringUtil.isNullOrEmpty(resultStr)) {
						try {
							return URLDecoder.decode(resultStr, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							LOG.info("返回结果转码错误");
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}
	
}
