package com.yazuo.weixin.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.stream.FileImageInputStream;

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

import com.yazuo.weixin.dao.ActivityDao;
import com.yazuo.weixin.dao.DishesDao;
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.dao.PreferenceDao;
import com.yazuo.weixin.dao.SubbranchDao;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.ActivityConfigVO;
import com.yazuo.weixin.vo.ActivityRecordVO;
import com.yazuo.weixin.vo.CardVO;
import com.yazuo.weixin.vo.CouponVO;
import com.yazuo.weixin.vo.DishesVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.PreferenceVO;
import com.yazuo.weixin.vo.SubbranchVO;

@Scope("prototype")
@Service
public class WeixinPhonePageServiceImpl implements WeixinPhonePageService {

	@Value("#{propertiesReader['imageLocationPath']}")
	private String imageLocationPath;
	@Value("#{propertiesReader['queryCard']}")
	private String queryCard;
	@Value("#{propertiesReader['getCouponForWeiXin']}")
	private String getCouponForWeiXin;
	@Autowired
	private SubbranchDao subbranchDao;
	@Autowired
	private DishesDao dishesDao;
	@Autowired
	private PreferenceDao preferenceDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private ActivityDao activityDao;
	@Value("#{propertiesReader.judgeCardNeedWritePwd}")
	private String judgeCardNeedWritePwd; // 取此卡在绑定实体卡时是否需要输入密码
	@Value("#{propertiesReader.getMemberByMembershipId}")
	private String getMemberByMembershipId; // 根据membershipId查询crm会员信息

	Logger log = Logger.getLogger(this.getClass());

	@Override
	public DishesVO getDishesById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DishesVO> getDishesListByBrandId(Integer brandId) {
		return dishesDao.getDishesListByBrandId(brandId);
	}

	@Override
	public PreferenceVO getPreferenceById(Integer id) {
		return preferenceDao.getPreferenceById(id);
	}

	@Override
	public List<PreferenceVO> getPreferenceListByBrandId(Integer brandId) {
		return preferenceDao.getPreferenceListByBrandId(brandId);
	}

	@Override
	public SubbranchVO getSubbranchById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubbranchVO> getSubbranchListByBrandId(Integer brandId) {
		// TODO Auto-generated method stub
		return subbranchDao.getSubbranchListByBrandId(brandId);
	}

	@Override
	public FileImageInputStream getImageStream(Integer brandId, String name) {
		String path = this.getClass().getClassLoader().getResource("")
				.getPath();
		path = path.split("WEB-INF")[0];
		File imageFile = new File(path + imageLocationPath + File.separator
				+ brandId.toString() + File.separator + name);
		if (!imageFile.exists()) {
			imageFile = new File(path + "images" + File.separator + "yazuo.jpg");
		}
		FileImageInputStream fiis;
		try {
			fiis = new FileImageInputStream(imageFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return fiis;
	}

	@Override
	public boolean hasImage(Integer brandId, String name) {
		// 判断图片是否在图片服务器上
		if (!StringUtil.isNullOrEmpty(name) && name.startsWith("group1/M00/")) {
			return true;
		}
		
		String path = this.getClass().getClassLoader().getResource("")
				.getPath();
		path = path.split("WEB-INF")[0];
		File imageFile = new File(path + imageLocationPath + File.separator
				+ brandId.toString() + File.separator + name);
		if (imageFile.exists()) {
			try {
				FileInputStream fis = new FileInputStream(imageFile);
				if (fis.available() > 100) {
					fis.close();
					return true;
				} else {
					fis.close();
					return false;
				}
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public MemberVO getMemberByWeixinIdAndBrandId(Integer brandId,
			String weixinId) {
		return memberDao.getMemberByWeixinIdAndBrandId(weixinId, brandId);
	}

	@Override
	public CardVO getCard(String cardNo) {
		String json = "{\"card\":\"" + cardNo + "\"}";
		JSONObject jo = null;
		try {
			jo = queryCard(json);
		} catch (Exception e) {
			log.error(json + e.getMessage());
			e.printStackTrace();
		}
		CardVO card = new CardVO();
		card.setCardNo(cardNo);
		if (jo == null) {
			card.setStatusInfo("卡不存在");
			return card;
		}
		if (!jo.getBoolean("success")) {
			card.setStatusInfo(jo.getString("message"));
			return card;
		}
		card.setIntegralAvailable(new BigDecimal(jo.getString("integral")));
		card.setStoreBalance(StringUtil.isNullOrEmpty(jo.getString("store")) ? new BigDecimal(0) : new BigDecimal(jo.getString("store")));
		return card;
	}

//	@Override
//	public List<CouponVO> getCouponList(String mobile, String card_no,
//			Integer brand_id) {
//		return memberDao.getCouponList(mobile, card_no, brand_id);
//	}

//	@Override
//	public List<CouponVO> getWeiboCouponList(String mobile, Integer brand_id) {
//		return memberDao.getGuaGuaCouponList(mobile, brand_id);
//	}

	@Override
	public boolean insertMember(MemberVO member) {
		return memberDao.insertMember(member);
	}

	@Override
	public List<ActivityConfigVO> getEffectiveActivityConfigListByBrandId(
			Integer brandId) {
		return activityDao.getEffectiveActivityConfigListByBrandId(brandId);
	}

	@Override
	public boolean isAllow(Integer brandId, String weixinId) {
		ActivityRecordVO activityRecord = new ActivityRecordVO();
		activityRecord.setBrandId(brandId);
		activityRecord.setWeixinId(weixinId);
		// List<ActivityRecordVO> activityRecordList = activityDao
		// .getActivityRecordListByBrandIdAndWeixinId(activityRecord);

		List<ActivityConfigVO> activityConfigList = activityDao
				.getEffectiveActivityConfigListByBrandId(brandId);
		// 当前无活动
		if (activityConfigList == null || activityConfigList.size() == 0) {
			return false;
		}
		// 当前有活动
		// ActivityConfigVO activityConfig = activityConfigList.get(0);
		// 之前无中奖纪录
		// CouponVO coupon = new CouponVO();
		// 超过容许活动频度
		for (ActivityConfigVO activityConfig : activityConfigList) {
			if (!matchFrequency(activityConfig, brandId, weixinId)) {
				return false;
			}
		}

		// // 超过总次数限制
		// if (activityConfig.getAwardCount()>=activityConfig.getCountLimit()) {
		// return false;
		// }
		return true;
	}

	public boolean isMoreThanCountLimit(
			List<ActivityConfigVO> activityConfigList) {

		for (ActivityConfigVO activityConfig : activityConfigList) {
			if (activityConfig.getAwardCount() >= activityConfig
					.getCountLimit()) {
				return true;
			}
		}
		return false;
	}

	public boolean isMoreThanCountLimit(ActivityConfigVO activityConfig) {

		if (activityConfig.getAwardCount() >= activityConfig.getCountLimit()) {
			return true;
		}
		return false;
	}

	@Override
	public ActivityRecordVO getCouponCode(Integer brandId, String weixinId,
			List<CouponVO> guaGuaCouponList) {

		ActivityRecordVO activityRecord = new ActivityRecordVO();
		activityRecord.setBrandId(brandId);
		activityRecord.setWeixinId(weixinId);

		List<ActivityConfigVO> activityConfigList = activityDao
				.getEffectiveActivityConfigListByBrandId(brandId);
		if (activityConfigList == null || activityConfigList.size() == 0) {
			return null;
		}
		// 当前有活动
		// ActivityConfigVO activityConfig = activityConfigList.get(0);
		int index = RPWT(activityConfigList);
		// 所有奖项都未中奖
		if (index == -1 || isMoreThanCountLimit(activityConfigList.get(index))) {
			log.info(brandId.toString() + "~~" + weixinId + "未中奖");
			activityRecord.setName("");
			activityRecord.setCouponId("谢谢参与");
			activityRecord.setIsDone(false);
			activityRecord.setOperateTime(new Timestamp(System
					.currentTimeMillis()));
			activityRecord.setWeixinId(weixinId);
			activityRecord.setBrandId(brandId);
			activityRecord.setIsAward(false);
			for (ActivityConfigVO activityConfig : activityConfigList) {
				activityRecord.setActivityId(activityConfig.getActivityId());
				activityRecord.setActivityConfigId(activityConfig.getId());
				activityDao.insertActivityRecord(activityRecord);
			}
			activityRecord = activityDao
					.getActivityRecordListByBrandIdAndWeixinId(activityRecord)
					.get(0);

			return activityRecord;
		} else {
			// 其中某项中奖，处理记录其他未中奖的
			log.info(brandId.toString() + "~~" + weixinId
					+ "中奖，处理同时期其他未中奖的奖项记录");
			activityRecord.setName("");
			activityRecord.setCouponId("谢谢参与");
			activityRecord.setIsDone(false);
			activityRecord.setOperateTime(new Timestamp(System
					.currentTimeMillis()));
			activityRecord.setWeixinId(weixinId);
			activityRecord.setBrandId(brandId);
			activityRecord.setIsAward(false);
			for (int i = 0; i < activityConfigList.size(); i++) {
				if (i != index) {
					activityRecord.setActivityId(activityConfigList.get(i)
							.getActivityId());
					activityRecord.setActivityConfigId(activityConfigList
							.get(i).getId());
					activityDao.insertActivityRecord(activityRecord);
				}
			}
			// log.info("出错的地方"+activityRecord.getBrandId().intValue()+activityRecord.getWeixinId());
			// activityRecord = activityDao
			// .getActivityRecordListByBrandIdAndWeixinId(activityRecord)
			// .get(0);
		}
		// 中奖，插入中奖信息
		MemberVO member = memberDao.getMemberByWeixinIdAndBrandId(weixinId,
				brandId);
		log.info(brandId.toString() + "~~" + weixinId + "中奖，插入中奖信息");
		if (member == null || member.getPhoneNo() == null
				|| member.getPhoneNo().equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String json = "{\"mobile\":\"" + member.getPhoneNo()
				+ "\",\"proxyActivtyId\":\""
				+ activityConfigList.get(index).getActivityId()
				+ "\",\"brandId\":\"" + brandId + "\",\"source\":\"8\"}";
		JSONObject activityRecordJO = null;
		try {
			activityRecordJO = getCouponForWeiXin(json);
		} catch (Exception e1) {
			log.error(e1.getMessage());
			e1.printStackTrace();
		}
		if (activityRecordJO == null) {
			log.error("接口调用失败");
			return null;
		}
		if (!activityRecordJO.getBoolean("success")) {
			log.error("活动不存在");
			return null;
		}
		activityRecord.setActivityId(activityConfigList.get(index)
				.getActivityId());
		activityRecord.setActivityConfigId(activityConfigList.get(index)
				.getId());
		activityRecord.setCouponId(activityRecordJO.getJSONObject("coupon")
				.getString("code"));
		activityRecord.setIsDone(false);
		activityRecord.setName(activityRecordJO.getJSONObject("coupon")
				.getString("couponName"));
		activityRecord
				.setOperateTime(new Timestamp(System.currentTimeMillis()));
		activityRecord.setWeixinId(weixinId);
		activityRecord.setBrandId(brandId);
		activityRecord.setIsAward(true);
		// coupon.setCode(activityRecord.getCouponId());
		// coupon.setName(activityRecord.getName());
		// guaGuaCouponList.add(coupon);
		try {
			activityRecord.setStartTime(new Timestamp(sdf.parse(
					activityRecordJO.getJSONObject("coupon").getString(
							"startDate")).getTime()));
			activityRecord
					.setEndTime(new Timestamp(
							sdf.parse(
									activityRecordJO.getJSONObject("coupon")
											.getString("expiredDate"))
									.getTime() + 23 * 3600000 + 3599000));
		} catch (ParseException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		activityDao.insertActivityRecord(activityRecord);
		activityDao.addAwardCount(activityRecord);
		activityRecord = activityDao.getActivityRecordListByBrandIdAndWeixinId(
				activityRecord).get(0);
		return activityRecord;
	}

	// 人品问题
	int RPWT(List<ActivityConfigVO> activityConfigList) {
		Random r = new Random();
		int j = r.nextInt(101);
		int k = 0;
		for (int i = 0; i < activityConfigList.size(); i++) {
			k += activityConfigList.get(i).getProbability().intValue();
			if (j < k) {
				return i;
			}
		}
		return -1;
	}

	boolean matchFrequency(ActivityConfigVO activityConfig, Integer brandId,
			String weixinId) {
		ActivityRecordVO activityRecord = new ActivityRecordVO();
		activityRecord.setBrandId(brandId);
		activityRecord.setWeixinId(weixinId);
		activityRecord.setActivityConfigId(activityConfig.getId());
		int count = 0;
		if (activityConfig.getTimeUnit().intValue() == 1) {
			activityRecord.setStartTime(new Timestamp(DateUtil.getNowDayBegin()
					.getTime()));
			activityRecord.setEndTime(new Timestamp(DateUtil.getNowDayEnd()
					.getTime()));
			count = activityDao
					.getActivityRecordCountByBrandIdAndWeixinId(activityRecord);
		} else if (activityConfig.getTimeUnit().intValue() == 2) {
			activityRecord.setStartTime(new Timestamp(DateUtil
					.getNowWeekBegin().getTime()));
			activityRecord.setEndTime(new Timestamp(DateUtil.getNowWeekEnd()
					.getTime()));
			count = activityDao
					.getActivityRecordCountByBrandIdAndWeixinId(activityRecord);
		} else if (activityConfig.getTimeUnit().intValue() == 3) {
			activityRecord.setStartTime(new Timestamp(DateUtil
					.getNowMonthBegin().getTime()));
			activityRecord.setEndTime(new Timestamp(DateUtil.getNowMonthEnd()
					.getTime()));
			count = activityDao
					.getActivityRecordCountByBrandIdAndWeixinId(activityRecord);
		} else if (activityConfig.getTimeUnit().intValue() == 4) {
			activityRecord.setStartTime(new Timestamp(DateUtil
					.getNowYearBegin().getTime()));
			activityRecord.setEndTime(new Timestamp(DateUtil.getNowYearEnd()
					.getTime()));
			count = activityDao
					.getActivityRecordCountByBrandIdAndWeixinId(activityRecord);
		} else if (activityConfig.getTimeUnit().intValue() == 5) {
			count = activityDao
					.getActivityRecordCountByBrandIdAndWeixinId(activityRecord);
		}
		return count < activityConfig.getFrequency();
	}

	@Override
	public boolean modifyActivityRecordStatus(ActivityRecordVO activityRecord) {
		ActivityRecordVO activityRecord2 = activityDao
				.getActivityRecordById(activityRecord);
		List<ActivityConfigVO> activityConfigList = activityDao
				.getEffectiveActivityConfigListByBrandId(activityRecord2
						.getBrandId());
		for (ActivityConfigVO acv : activityConfigList) {
			List<ActivityRecordVO> activityRecordList = activityDao
					.getActivityRecordListByConfigIdAndWeixinId(acv.getId(),
							activityRecord2.getWeixinId());
			for (ActivityRecordVO arv : activityRecordList) {
				arv.setOperateTime(new Timestamp(System.currentTimeMillis()));
				arv.setIsDone(true);
				activityDao.updateActivityRecord(arv);
			}
		}

		return true;
	}

	@Override
	public ActivityRecordVO getLastActivityRecord(Integer brandId,
			String weixinId) {
		ActivityRecordVO activityRecord = new ActivityRecordVO();
		activityRecord.setBrandId(brandId);
		activityRecord.setWeixinId(weixinId);
		return activityDao
				.getLastActivityRecordByBrandIdAndWeixinId(activityRecord);
	}

	public JSONObject queryCard(String json) throws Exception {
		log.info("请求查询卡" + json);
		// json = URLEncoder.encode(EncryptUtil.encrypt3DesObject(secretKey,
		// json), "UTF-8");
		json = URLEncoder.encode(json, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = queryCard + "?ciphertext=" + json + "&key=" + Constant.KEY;

		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject
					.get("data")).get("result").toString(), "UTF-8");
			// String reStr = EncryptUtil.dncrypt3DesString(secretKey, data);
			log.info("查询卡返回" + data);
			return JSONObject.fromObject(data);
		} else {
			return null;
		}
	}

	public JSONObject getCouponForWeiXin(String json) throws Exception {
		log.info("请求发放优惠券" + json);
		// json = URLEncoder.encode(EncryptUtil.encrypt3DesObject(secretKey,
		// json), "UTF-8");
		json = URLEncoder.encode(json, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = getCouponForWeiXin + "?ciphertext=" + json + "&key="
				+ Constant.KEY;

		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject
					.get("data")).get("result").toString(), "UTF-8");
			// String reStr = EncryptUtil.dncrypt3DesString(secretKey, data);
			log.info("发放优惠券返回" + data);
			return JSONObject.fromObject(data);
		} else {
			return null;
		}
	}
	//获取图片地址
	public String getNewImageUrl(Integer brandId, String name) {
		String imagePath = "";
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.split("WEB-INF")[0];
		File imageFile = new File(path + imageLocationPath + File.separator
				+ brandId.toString() + File.separator + name);
		log.info("imageLocationPath:"+imageLocationPath);
		imagePath = "/"+imageLocationPath + "/"	+ brandId.toString() + "/" + name;
		if (!imageFile.exists()) {
			imagePath ="/images/yazuo.jpg";
		}
		log.info("图片地址："+imagePath+"\tbrandId="+brandId+"\tname="+name);
		return imagePath;
	}

	@Override
	public Object judgeNeedPwd(String cardNo, Integer brandId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String input = "{\"merchantId\":"+brandId+",\"cardNo\":\""+cardNo+"\"}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(judgeCardNeedWritePwd, input,  Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		JSONObject jo = null;
		if (!StringUtil.isNullOrEmpty(result)) {
			jo = JSONObject.fromObject(result);
			boolean flag = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
			String message = jo.getJSONObject("data").getJSONObject("result").getString("message");
			if (flag) {
				// 取是否需要输入密码
				boolean isTradePassword = jo.getJSONObject("data").getJSONObject("result").getBoolean("isTradePassword");
				map.put("flag", isTradePassword);
				map.put("isRequireSuccess", true);
			} else {
				map.put("isRequireSuccess", false);
				String temp = "会员卡号【"+cardNo+"】在商户【"+brandId+"】不存在,请重新输入！";
				if (message.equals(temp)){
					map.put("message", "此卡未激活，请先到服务台激活");
				} else {
					map.put("message", message);
				}
			}
		} else {
			map.put("isRequireSuccess", false);
			map.put("message", "卡号验证失败，请重新输入卡号");
		}
		return map;
	}

	@Override
	public MemberVO getMemberMessageBymembershipId(Integer membershipId, Integer brandId) {
		String input = "{'merchantId':"+brandId+",'id':"+membershipId+"}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(getMemberByMembershipId, input,  Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		MemberVO member = null;
		JSONObject jo = null;
		if (!StringUtil.isNullOrEmpty(result)) {
			jo = JSONObject.fromObject(result);
			boolean flag = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
			if (flag) {
				JSONObject memberJson = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership");
				
				member = new MemberVO();
				member.setPhoneNo(memberJson.getString("mobile"));
				member.setBirthType(memberJson.getString("birthType"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date birthday = null;
				try {
					birthday = memberJson.get("birthday")!=null && !StringUtil.isNullOrEmpty(memberJson.getString("birthday"))? sdf.parse(memberJson.getString("birthday")) : null;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				member.setBirthday(birthday);
			}
		}
		return member;
	}
}
