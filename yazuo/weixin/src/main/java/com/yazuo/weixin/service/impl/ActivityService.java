package com.yazuo.weixin.service.impl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

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
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.ActivityConfigVO;
import com.yazuo.weixin.vo.ActivityRecordVO;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.ImageConfigVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.Message;

@Scope("prototype")
@Service
public class ActivityService {
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Value("#{propertiesReader['getCouponForWeiXin']}")
	private String getCouponForWeiXin;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private ActivityDao activityDao;
	private static final int NO_Activity = 1;// 无该活动
	private static final int Beyond_Frequency = 2;// 超过了频度
	private static final int Lucky = 3;// 可以抽奖
	@Resource
	private WeixinPhonePageService weixinPhonePageServiceImpl;
	Logger log = Logger.getLogger(this.getClass());
	
	@Value("#{propertiesReader['dfsFilePath']}")
	private String dfsFilePath; // 图片服务器地址

	@Scope("prototype")
	public Message executeAutoreply(Message messageIn, BusinessVO business,
			AutoreplyVO autoreply, String path, MemberVO member) {
		if (autoreply.getReplyType().equals("award")
				&& autoreply.getReplyContent().trim().startsWith("[")
				&& autoreply.getReplyContent().trim().endsWith("]")
				|| autoreply.getReplyType().equals("text")
				&& autoreply.getReplyContent().trim().startsWith("[")
				&& autoreply.getReplyContent().trim().endsWith("]")) {

			// 好像是答复内容[127, 128, 129, 130, 131, 132]
			String[] activityIds = autoreply.getReplyContent().trim()
					.substring(1, autoreply.getReplyContent().length() - 1)
					.split(",");
			Integer[] activityCofingIDS = new Integer[activityIds.length];
			for (int i = 0; i < activityIds.length; i++) {
				activityCofingIDS[i] = Integer.parseInt(activityIds[i]);
			}
			// 获取用户的weixinID以及其所属商家的brandId的信息

			String weixinId = messageIn.getFromUserName();
			Integer brandId = business.getBrandId();

			// 抽奖活动是否可以抽奖

			// 当前有无活动以及是否超过了容许活动频度
			// 允许参与当前活动，但是当前活动向外发奖次数已经等于活动本身次数的限制，提示谢谢参与，当前无活动：

			int Allow = isAllow(brandId, weixinId, activityCofingIDS);

			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);

			if (Allow == NO_Activity) {
				messageOut.setFuncFlag("0");
				messageOut.setContent("呃，亲很遗憾！当前木有该活动，试试别的吧！");
				return messageOut;
			} else if (Allow == Beyond_Frequency) {
				List<ActivityConfigVO> activityConfigList = activityDao
						.getEffectiveActivityConfigListByActivityConfigID(
								activityCofingIDS, brandId);
				String str = "";
				if (activityConfigList.get(0).getTimeUnit() == 1) {
					str = "每日";
				} else if (activityConfigList.get(0).getTimeUnit() == 2) {
					str = "每周";
				} else if (activityConfigList.get(0).getTimeUnit() == 3) {
					str = "每月";
				} else if (activityConfigList.get(0).getTimeUnit() == 4) {
					str = "每年";
				} else {
					str = "该活动";
				}
				messageOut.setFuncFlag("0");
				messageOut.setContent("呃，亲已经抽过奖啦!"
						+ str
						+ "只可抽取"
						+ getMinFrequencyByActivityConfigID(activityCofingIDS,
								brandId) + "次，别太贪心哦～");
				return messageOut;
			} else {
				// 判断是否中奖
				ActivityRecordVO activityRecordVO = getCouponCode(
						activityCofingIDS, weixinId, brandId);

				if (activityRecordVO == null) {
					messageOut.setFuncFlag("0");
					messageOut.setContent("呃，亲谢谢你的参与");
					return messageOut;
				} else if (activityRecordVO != null
						&& activityRecordVO.getCouponId().trim() == "谢谢参与") {
					messageOut.setFuncFlag("0");
					messageOut.setContent("很遗憾!没有中奖,不要灰心,再试试吧！");
					return messageOut;
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					ActivityConfigVO activityConfigVO = getActivityConfigVOByActivityConfigId(
							activityRecordVO.getActivityConfigId(), brandId);
					messageOut.setFuncFlag("0");
					messageOut.setContent(activityConfigVO.getReplyContent()
							+ "\n" + "券名称:" + activityRecordVO.getName() + "\n"
							+ "券编码:" + activityRecordVO.getCouponId() + "\n"
							+ "有效期:"
							+ sdf.format(activityRecordVO.getStartTime())
							+ "\n" + "至:"
							+ sdf.format(activityRecordVO.getEndTime()) + "\n"
							+ "过期作废,不要浪费呦!");
					return messageOut;
				}

			}

		} else {
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			
			// 判断此条自定回复类型，是否需要限制会员操作
			int specificType = autoreply.getSpecificType()!=null ? autoreply.getSpecificType().intValue() : 0;
			if (specificType == 3) { //普通限制会员自定义回复
				if (!member.getIsMember()) { // 非会员进入添加会员页面
					String info = "<a href=\"" + serverIp + path + "/weixin/phonePage/registerPage.do?brandId=" + business.getBrandId()
							+ "&weixinId=" + messageIn.getFromUserName() + "\">亲,您还不是会员，请点击这里</a>";
					messageOut.setMsgType(Message.MSG_TYPE_TEXT);
					messageOut.setContent(info);
					messageOut.setFuncFlag("0");
					return messageOut;
				}
			}
			if (autoreply.getReplyType().equals("text")) {
				messageOut.setMsgType(Message.MSG_TYPE_TEXT);
				// 自动回复内容判断是否有url
				String replyContent = autoreply.getReplyContent();
				String regx = "\\{([^\\{]+?)\\}";
				if (replyContent.indexOf("{")>-1 && replyContent.indexOf("}")>-1) {
					Pattern pat = Pattern.compile(regx);
					Matcher mat = pat.matcher(replyContent);  
					while(mat.find()){  
						String str1 = mat.group(1)+messageIn.getFromUserName();
						replyContent = replyContent.replace(mat.group(), str1);
					}
					messageOut.setContent(replyContent);
				} else {
					messageOut.setContent(autoreply.getReplyContent());
				}
			} else if (autoreply.getReplyType().equals("news")) {
				messageOut.setTitle("");
				List<Message> articles = new ArrayList<Message>();

				List<ImageConfigVO> imageList = autoreply.getImageConfigList();
				if (imageList != null && imageList.size() != 0) {
					for (ImageConfigVO imageConfig : imageList) {
						Message message1 = new Message();
						String title = imageConfig.getReplyTitle();// 图片标题
						String imageName = imageConfig.getImageName();
						message1.setTitle(title == null
								|| title.trim().equals("") ? "" : title);
						
						if (!StringUtil.isNullOrEmpty(imageName) && imageName.startsWith("group1/M00/")) {
							message1.setPicUrl(dfsFilePath+imageName);
						} else {
							message1.setPicUrl(serverIp + path +weixinPhonePageServiceImpl.getNewImageUrl(business.getBrandId(),imageName));
						}
						String replyUrl = imageConfig.getReplyUrl();// 商家的所设的url
						// url中包含{}会加上openId
						String regx = "\\{([^\\{]+?)\\}";
						if (!StringUtil.isNullOrEmpty(replyUrl) && replyUrl.indexOf("{")>-1 && replyUrl.indexOf("}")>-1) {
							Pattern pat = Pattern.compile(regx);
							Matcher mat = pat.matcher(replyUrl);  
							while(mat.find()){  
								String str1 = mat.group(1)+messageIn.getFromUserName();
								replyUrl = replyUrl.replace(mat.group(), str1);
							}
						}
						
						message1.setUrl(replyUrl == null || replyUrl.trim().equals("") ? "" : replyUrl);
						message1.setDescription(imageConfig.getDescripation());
						articles.add(message1);

					}
				}
				messageOut.setMsgType(Message.MSG_TYPE_NEWS);
				messageOut.setFuncFlag("1");
				messageOut.setArticleCount(articles.size());
				messageOut.setArticles(articles);
				return messageOut;
			}
			messageOut.setFuncFlag("0");
			return messageOut;
		}
	}

	int isAllow(Integer brandId, String weixinId, Integer[] activityCofingIDS) {
		// 查询本商家的所有优惠券
		List<ActivityConfigVO> activityBusinessConfigList = activityDao
				.getEffectiveActivityConfigListByBrandId(brandId);
		if (activityBusinessConfigList == null
				|| activityBusinessConfigList.size() == 0) {
			return NO_Activity; // 当前商家没有活动
		}

		List<ActivityConfigVO> activityConfigList = activityDao
				.getEffectiveActivityConfigListByActivityConfigID(
						activityCofingIDS, brandId);
		if (activityConfigList == null || activityConfigList.size() == 0) {
			return NO_Activity;// 当前商家没有活动
		}
		List<ActivityConfigVO> activityCount = new ArrayList<ActivityConfigVO>();

		for (ActivityConfigVO activityConfig : activityConfigList) {
			if (matchFrequency(activityConfig, brandId, weixinId,
					activityCofingIDS)) {
				activityCount.add(activityConfig);
			}
		}
		if (activityCount.size() == 0) {
			return Beyond_Frequency; // 超过了活动允许的频率
		}

		return Lucky; // 允许抽奖
	}

	boolean matchFrequency(ActivityConfigVO activityConfig, Integer brandId,
			String weixinId, Integer[] activityCofingIDS) {
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
			// 每周一次有bug
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
		return count < getMinFrequencyByActivityConfigID(activityCofingIDS,
				brandId);
	}

	// 获取最小活动频率的值
	int getMinFrequencyByActivityConfigID(Integer[] activityCofingIDS,
			Integer brandId) {
		return activityDao.getMinFrequencyByActivityConfigID(activityCofingIDS,
				brandId);

	}

	ActivityRecordVO getCouponCode(Integer[] activityCofingIDS,
			String weixinId, Integer brandId) {
		ActivityRecordVO activityRecord = new ActivityRecordVO();
		activityRecord.setBrandId(brandId);
		activityRecord.setWeixinId(weixinId);
		// 根据传过来的商家ID和 具体的ActivityConfigId很有可能商家填写的活动Id是别的商户的
		List<ActivityConfigVO> activityConfigList = activityDao
				.getEffectiveActivityConfigListByActivityConfigID(
						activityCofingIDS, brandId);

		// 人品问题
		int index = RPWT(activityConfigList);
		// 所有奖项都未中奖
		if (index == -1
				|| isMoreThanCountLimit(activityConfigList.get(index))
				|| !matchFrequency(activityConfigList.get(index), brandId,
						weixinId, activityCofingIDS)) {
			log.info(brandId.toString() + "~~" + weixinId + "未中奖");
			activityRecord.setName("");
			activityRecord.setCouponId("谢谢参与");
			activityRecord.setIsDone(true);
			activityRecord.setOperateTime(new Timestamp(System
					.currentTimeMillis()));
			activityRecord.setWeixinId(weixinId);
			activityRecord.setBrandId(brandId);
			activityRecord.setIsAward(false);
			for (ActivityConfigVO activityConfig : activityConfigList) {
				activityRecord.setActivityId(activityConfig.getActivityId());
				activityRecord.setActivityConfigId(activityConfig.getId());
				// 保存中奖信息***************
				activityDao.insertActivityRecord(activityRecord);
			}

			return activityRecord;

		} else {// 中奖，插入中奖信息

			MemberVO member = memberDao.getMemberByWeixinIdAndBrandId(weixinId,
					brandId);
			log.info(brandId.toString() + "~~" + weixinId + "中奖，插入中奖信息");
			if (member == null || member.getPhoneNo() == null
					|| member.getPhoneNo().equals("")) {
				log.info("会员信息不完整");
				return null;
			}
			activityRecord.setName("");
			activityRecord.setCouponId("谢谢参与");
			activityRecord.setIsDone(true);
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
					// 保存中奖信息----------------------------
					activityDao.insertActivityRecord(activityRecord);
				}
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
			activityRecord.setName(activityRecordJO.getJSONObject("coupon")
					.getString("couponName"));
			activityRecord.setOperateTime(new Timestamp(System
					.currentTimeMillis()));
			activityRecord.setWeixinId(weixinId);
			activityRecord.setBrandId(brandId);
			activityRecord.setIsAward(true);
			activityRecord.setIsDone(true);
			try {//
				activityRecord.setStartTime(new Timestamp(sdf.parse(
						activityRecordJO.getJSONObject("coupon").getString(
								"startDate")).getTime()));
				activityRecord
						.setEndTime(new Timestamp(
								sdf.parse(
										activityRecordJO
												.getJSONObject("coupon")
												.getString("expiredDate"))
										.getTime() + 23 * 3600000 + 3599000));
			} catch (ParseException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			activityDao.insertActivityRecord(activityRecord);
			activityDao.addAwardCount(activityRecord);
			return activityRecord;
		}
	}

	ActivityConfigVO getActivityConfigVOByActivityConfigId(
			Integer ActivityConfigID, Integer brandId) {
		return activityDao.getActivityConfigVOByActivityConfigId(
				ActivityConfigID, brandId);
	}

	// 人品问题
	int RPWT(List<ActivityConfigVO> activityConfigList) {
		Random r = new Random();
		int j = r.nextInt(100);
		int size = activityConfigList.size();
		int bingoId = r.nextInt(size);
		// activityConfigList.get(bingoId).getProbability().intValue()得到中奖率
		if (activityConfigList.get(bingoId).getProbability().intValue() > j) {
			return bingoId;
		} else {
			return -1;
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
		long startTime = System.currentTimeMillis();
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
			String crmInterface = getCouponForWeiXin.substring((getCouponForWeiXin.lastIndexOf("/")+1),getCouponForWeiXin.lastIndexOf("."));
			log.info("--访用CRM【"+crmInterface+"】接口...耗时::" + (System.currentTimeMillis() - startTime) + "毫秒");
			return JSONObject.fromObject(data);
		} else {
			log.info("请求失败等错误");
			return null;
		}
	}

	public boolean isMoreThanCountLimit( // 其中的任意一项是否超过了总次数的限制
			List<ActivityConfigVO> activityConfigList) {

		for (ActivityConfigVO activityConfig : activityConfigList) {
			if (activityConfig.getAwardCount() >= activityConfig
					.getCountLimit()) {
				return true;
			}
		}
		return false;
	}

	public boolean isMoreThanCountLimit(ActivityConfigVO activityConfig) {// 超过总次数的限制

		if (activityConfig.getAwardCount() >= activityConfig.getCountLimit()) {
			return true;
		}
		return false;
	}

	public ActivityRecordVO getLastActivityRecord(Integer brandId,
			String weixinId) {
		ActivityRecordVO activityRecord = new ActivityRecordVO();
		activityRecord.setBrandId(brandId);
		activityRecord.setWeixinId(weixinId);
		return activityDao
				.getLastActivityRecordByBrandIdAndWeixinId(activityRecord);
	}

	boolean isNum(String input) {// 是否是数字

		Pattern p = Pattern.compile("^[0-9_]+$");
		Matcher m = p.matcher(input);
		return m.matches();
	}
}
