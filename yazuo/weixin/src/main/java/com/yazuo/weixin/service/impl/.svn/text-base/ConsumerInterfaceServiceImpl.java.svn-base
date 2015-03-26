package com.yazuo.weixin.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.jms.JMSException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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

import com.yazuo.weixin.card.service.CardService;
import com.yazuo.weixin.dao.ActivityDao;
import com.yazuo.weixin.dao.BusinessDao;
import com.yazuo.weixin.dao.EventRecordDao;
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.dao.MerchantDao;
import com.yazuo.weixin.dao.StoryDao;
import com.yazuo.weixin.dao.SubbranchDao;
import com.yazuo.weixin.es.service.MemberShipCenterService;
import com.yazuo.weixin.es.vo.BrandVO;
import com.yazuo.weixin.member.service.MemberInfoService;
import com.yazuo.weixin.service.ConsumerInterfaceService;
import com.yazuo.weixin.service.LotteryMessageVerifyService;
import com.yazuo.weixin.service.MessageClickVerifyService;
import com.yazuo.weixin.service.SaveOrModifyMembershipTagService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.MessageSender;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.CardVO;
import com.yazuo.weixin.vo.DishesVO;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.IdentifyinginfoVO;
import com.yazuo.weixin.vo.MemberCardVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.Message;
import com.yazuo.weixin.vo.PreferenceVO;
import com.yazuo.weixin.vo.Story;
import com.yazuo.weixin.vo.SubbranchVO;

@Scope("prototype")
@Service
public class ConsumerInterfaceServiceImpl implements ConsumerInterfaceService {
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Value("#{propertiesReader['registerMircoMessageMembership']}")
	private String registerMircoMessageMembership;
	@Value("#{propertiesReader['smsAddress']}")
	private String smsAddress;
	@Value("#{propertiesReader['smsUsername']}")
	private String smsUsername;
	@Value("#{propertiesReader['queryCard']}")
	private String queryCard;
	@Value("#{propertiesReader['modifyMemberMessage']}")
	private String modifyMemberMessage;
	@Value("#{propertiesReader['posCheck']}")
	private String posCheck;
	@Autowired
	private SubbranchDao subbranchDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private EventRecordDao eventRecordDao;
	@Autowired
	private BusinessDao businessDao;
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private StoryDao storyDao;
	@Autowired
	private ActivityService activityService;

	@Resource
	private LotteryMessageVerifyService lotteryMessageVerifyService;

	@Resource
	private MessageClickVerifyService messageClickVerifyService;

	@Resource
	private SaveOrModifyMembershipTagService saveOrModifyMembershipTagService;
	@Resource
	private WeixinPhonePageService weixinPhonePageServiceImpl;
	@Resource
	private MerchantDao merchantDao;
	@Resource
	private CardService cardService;
	@Resource
	private MemberShipCenterService memberShipCenterService;
	@Resource
	private MemberInfoService memberInfoService;
	// private final String REPLYINFO =
	// "\n\n回复1加入或绑定会员\n回复2会员查询\n回复3会员优惠查询\n回复4门店信息\n回复5菜品信息\n回复其他建议留言";
	private final String REPLYINFO = "\n\n回复2会员查询\n回复3会员优惠查询\n回复4门店信息\n回复5菜品信息\n回复其他建议留言";

	Logger log = Logger.getLogger(this.getClass());

	private String path;
	
	@Value("#{propertiesReader['dfsFilePath']}")
	private String dfsFilePath; // 图片服务器地址

	// message的具体不知道
	@Scope("prototype")
	@Override
	public Message message(Message messageIn, BusinessVO business, String path, List<AutoreplyVO> autoreplyList) {
		this.path = path;
		MemberVO member = memberDao.getMember(messageIn.getFromUserName(), business.getBrandId());
		if (member == null) {
			member = new MemberVO();
			member.setBrandId(business.getBrandId());
			member.setWeixinId(messageIn.getFromUserName());
			member.setIsMember(false);
			memberDao.insertMember(member);
			member = memberDao.getMember(messageIn.getFromUserName(), business.getBrandId());
		}
		// 文字消息处理
		if (messageIn.getMsgType().equals(Message.MSG_TYPE_TEXT)) {
			// 自定义消息处理
			AutoreplyVO autoreply = checkAutoreply(messageIn, autoreplyList);
			if (autoreply != null) {
				return activityService.executeAutoreply(messageIn, business, autoreply, path, member);
			}
			return executeText(messageIn, business, member);
		}
		// 图片消息处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_IMAGE)) {
			return executeImage(messageIn, business);
		}
		// 地理位置消息处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_LOCATION)) {
			return executeLocation(messageIn, business);
		}
		// 链接消息处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_LINK)) {
			return executeLink(messageIn, business);
		}
		// 事件消息处理 关注事件需要放到这处理,现在的商家也走自动回复表
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)) {
			return executeEvent(messageIn, business, member, autoreplyList);
		}
		// 语音消息处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_VOICE)) {
			return executeVoice(messageIn, business);
		}

		// 其他消息处理
		else
			return executeother(messageIn, business);
	}

	/**
	 * update 2013-12-06
	 * 
	 * 修改内容：去掉验证码验证
	 * 
	 * executeText方法里才有验证码
	 * 
	 * @param messageIn
	 * @param business
	 * @param member
	 * @return
	 */
	Message executeText(Message messageIn, BusinessVO business, MemberVO member) {

		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setFuncFlag("0");
		// 短信验证
		if (messageIn.getContent().trim().length() == 3 && isNum(messageIn.getContent().trim())) {
			String info = identifyingAndRegister(messageIn.getFromUserName(), business.getBrandId(), messageIn.getContent());
			messageOut.setContent(info);
			log.info(info);
			return messageOut;
			// 改生日信息
		} else if (messageIn.getContent().trim().length() == 8 && isDate(messageIn.getContent().trim())) {
			Message message = new Message();
			message.setToUserName(messageIn.getFromUserName());
			message.setFromUserName(messageIn.getToUserName());
			message.setCreateTime(messageIn.getCreateTime());
			message.setMsgType(Message.MSG_TYPE_TEXT);
			message.setFuncFlag("0");
			message.setContent(modifyMemberInfo(messageIn.getContent(), member, business));
			return message;

			// 手机号申请短信验证
		} else if (messageIn.getContent().trim().length() == 11 && isMobileNO(messageIn.getContent().trim())) {
			member = memberDao.getMember(messageIn.getFromUserName(), business.getBrandId());
			if (member == null) {
				member = new MemberVO();
				member.setBrandId(business.getBrandId());
				member.setWeixinId(messageIn.getFromUserName());
				member.setIsMember(false);
				memberDao.insertMember(member);
			}

			if (member != null && member.getIsMember()) {
				String info = "您已经是会员请勿重复注册";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}

			String phoneNo = messageIn.getContent().trim();// 电话号码
			int brandId = business.getBrandId();// 集团id
			String weixinId = messageIn.getFromUserName();

			BusinessVO business_new = businessDao.getBusinessByBrandId(brandId);
			// 创建会员
			String info = register(phoneNo, weixinId, member, business_new);
			log.info(info);
			messageOut.setContent(info);
			return messageOut;
		} else if (messageIn.getContent().trim().length() == 1) {
			if (messageIn.getContent().trim().equals("1")) {
				String info = "";
				member = memberDao.getMember(messageIn.getFromUserName(), business.getBrandId());
				if (member == null) {
					member = new MemberVO();
					member.setBrandId(business.getBrandId());
					member.setWeixinId(messageIn.getFromUserName());
					member.setIsMember(false);
					memberDao.insertMember(member);
				}
				if (member.getIsMember()) {
					info = "您已经绑定会员，回复2可以进行会员信息查询";
					messageOut.setContent(info);
					log.info(info);
					return messageOut;
				}
				info = "请输入手机号，绑定已有会员卡";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;

			} else if (messageIn.getContent().trim().equals("2")) {
				member = memberDao.getMember(messageIn.getFromUserName(), business.getBrandId());
				if (member == null) {
					member = new MemberVO();
					member.setBrandId(business.getBrandId());
					member.setWeixinId(messageIn.getFromUserName());
					member.setIsMember(false);
					memberDao.insertMember(member);
				}
				if (member.getIsMember()) {
					Message newsMessageOut = new Message();
					List<Message> articles = new ArrayList<Message>();
					Message message1 = new Message();
					message1.setTitle("点击进入会员中心");
					message1.setUrl(serverIp + path + "/weixin/phonePage/fensiCard.do?brandId=" + business.getBrandId() + "&weixinId="
							+ messageIn.getFromUserName());
					message1.setPicUrl(serverIp + path + "/images/huiyuanquanyi.jpg");
					articles.add(message1);
					List<MemberCardVO> cardList = cardService.queryCardList(member.getBrandId(), member.getMembershipId());//member.getPhoneNo());
					cardService.showCouponList(articles, cardList, member.getBrandId(), member.getPhoneNo(), messageIn.getFromUserName(), path);
					newsMessageOut.setToUserName(messageIn.getFromUserName());
					newsMessageOut.setFromUserName(messageIn.getToUserName());
					newsMessageOut.setCreateTime(messageIn.getCreateTime());
					newsMessageOut.setMsgType(Message.MSG_TYPE_NEWS);
					newsMessageOut.setFuncFlag("1");
					newsMessageOut.setArticleCount(articles.size());
					newsMessageOut.setArticles(articles);
					return newsMessageOut;
				} else {
					String info = "您还不是会员，回复手机号即可成为" + business.getTitle() + "会员哦~";
					log.info(info);
					messageOut.setContent(info);
					return messageOut;
				}
			} else if (messageIn.getContent().trim().equals("3")) {
				StringBuffer sb = new StringBuffer("");

				if (business.getPreferenceList() != null) {
					int i = 0;
					for (PreferenceVO preference : business.getPreferenceList()) {
						if (!preference.getIsDelete() && preference.getIsRecommend()) {
							if (i++ > 3)
								break;
							sb.append(i + "、" + preference.getTitle() + "\n");
						}
					}
				}

				sb.append("点击查看" + business.getTitle() + "<a href=\"" + serverIp + path
						+ "/weixin/phonePage/preference.do?brandId=" + business.getBrandId() + "&weixinId="
						+ messageIn.getFromUserName() + "\">" + "优惠信息</a>");
				sb.append(REPLYINFO);
				messageOut.setContent(sb.toString());
				return messageOut;
			} else if (messageIn.getContent().trim().equals("4")) {
				Message newsMessageOut = new Message();
				newsMessageOut.setTitle(business.getTitle());
				List<Message> articles = new ArrayList<Message>();
				Message message1 = new Message();
				message1.setTitle(business.getTitle());
				message1.setDescription("点击链接查看全部店面；\n输入位置查询附近店面，具体操作请参见上图");
				message1.setPicUrl(serverIp + path + "/images/weizhi.jpg");
//				message1.setUrl(serverIp + path + "/weixin/phonePage/subbranch.do?brandId=" + business.getBrandId()
//						+ "&weixinId=" + messageIn.getFromUserName());
				message1.setUrl(serverIp + path + "/newFun/phonePage/subbranch.do?brandId=" + business.getBrandId()
						+ "&weixinId=" + messageIn.getFromUserName());
				articles.add(message1);
				newsMessageOut.setToUserName(messageIn.getFromUserName());
				newsMessageOut.setFromUserName(messageIn.getToUserName());
				newsMessageOut.setCreateTime(messageIn.getCreateTime());
				newsMessageOut.setMsgType(Message.MSG_TYPE_NEWS);
				newsMessageOut.setFuncFlag("1");
				newsMessageOut.setArticleCount(articles.size());
				newsMessageOut.setArticles(articles);
				return newsMessageOut;
			} else if (messageIn.getContent().trim().equals("5")) {
				StringBuffer sb = new StringBuffer("");
				if (business.getDishesList() != null) {
					int i = 0;
					for (DishesVO dishes : business.getDishesList()) {
						if (!dishes.getIsDelete() && dishes.getIsRecommend()) {
							if (i++ > 3)
								break;
							sb.append(i + "、" + dishes.getName() + "\n");
						}
					}

				}
				sb.append("点击查看" + business.getTitle() + "<a href=\"" + serverIp + path + "/weixin/newFun/dishes.do?brandId="
						+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "&r="+Math.random()+"\">菜品信息</a>");
				sb.append(REPLYINFO);
				messageOut.setContent(sb.toString());

				return messageOut;
			} else {
				messageOut.setContent("感谢您的建议，留言已成功！");
				return messageOut;
			}
		} else {
			messageOut.setContent("感谢您的建议，留言已成功！");
			return messageOut;
		}
	}

	@Override
	public String sendIdentifyingCode(String phoneNo, Integer brandId, String weixinId, String title) {
		Random r = new Random();
		String identifyingCode = String.valueOf(r.nextInt(999));
		while (identifyingCode.length() != 3) {
			identifyingCode = String.valueOf(r.nextInt(999));
		}
		IdentifyinginfoVO identifyinginfo = new IdentifyinginfoVO();
		identifyinginfo.setIdentifyingCode(identifyingCode);
		identifyinginfo.setIdentifyingTime(new Timestamp(System.currentTimeMillis()));
		identifyinginfo.setWeixinId(weixinId);
		identifyinginfo.setPhoneNo(phoneNo);
		identifyinginfo.setBrandId(brandId);
		memberDao.insertIdentifyinginfo(identifyinginfo);

		try {
			MessageSender.sendSMS("验证码为" + identifyingCode + ",欢迎关注" + title, phoneNo, smsAddress, smsUsername, brandId);
			return identifyingCode;
		} catch (JMSException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}

	}

	@Override
	public String identifyingAndRegister(String weixinId, Integer brandId, String identifyingCode) {
		BusinessVO business = businessDao.getBusinessByBrandId(brandId);
		MemberVO member = memberDao.getMember(weixinId, brandId);

		if (member == null) {
			member = new MemberVO();
			member.setBrandId(brandId);
			member.setWeixinId(weixinId);
			member.setIsMember(false);
			memberDao.insertMember(member);
		}
		if (member != null && member.getIsMember()) {
			return "您已经是会员请勿重复验证" + REPLYINFO;
		}

		IdentifyinginfoVO identifyinginfo = memberDao.getLastIdentifyinginfoByWeixinId(weixinId, brandId);
		if (identifyinginfo == null || identifyinginfo.getIdentifyingCode() == null
				|| identifyinginfo.getIdentifyingCode().equals("")) {

			return "并未申请验证" + REPLYINFO;
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 1800 * 1000);
		if (!identifyinginfo.getIdentifyingCode().equals(identifyingCode)) {
			return "验证码错误，请重新申请验证" + REPLYINFO;
		}
		if (identifyinginfo.getIdentifyingTime().before(timestamp)) {
			return "验证码过期，请重新申请验证" + REPLYINFO;
		}
		return register(identifyinginfo.getPhoneNo(), weixinId, member, business);
	}

	@Override
	public String register(String weixinId, Integer brandId, String phoneNo) {
		BusinessVO business = businessDao.getBusinessByBrandId(brandId);
		MemberVO member = memberDao.getMember(weixinId, brandId);

		if (member == null) {
			member = new MemberVO();
			member.setBrandId(brandId);
			member.setWeixinId(weixinId);
			member.setIsMember(false);
			memberDao.insertMember(member);
		}
		if (member != null && member.getIsMember()) {
			return "您已经是会员请勿重复验证" + REPLYINFO;
		}

		return register(phoneNo, weixinId, member, business);
	}

	/**
	 * 注册会员
	 * 
	 * @param mobile
	 * @param weixinId
	 * @param member
	 * @param business
	 * @return
	 */
	String register(String mobile, String weixinId, MemberVO member, BusinessVO business) {
	
		Integer merchantId = business.getBrandId();
		Integer tagId = business.getTagId();
		String tagIdStr = "";
		if (tagId != null) {
			tagIdStr = tagId.toString();
		}
		
		String json = "{\"mobile\":\"" + mobile + "\",\"merchantId\":" + merchantId
				+ ",\"micromessage\":true,\"microMessageId\":\"" + weixinId + "\",\"tagId\":\"" + tagIdStr + "\"}";
		log.info("请求创建会员" + json);
		JSONObject jo = null;
		try {
			// 调用会员接口耗時
			long timeStart = System.currentTimeMillis();
			jo = registerMircoMessageMembership(json);
			log.info("--ConsumerInterfaceServiceImpl访问添加会员接口...耗时::" + (System.currentTimeMillis() - timeStart) + "毫秒");
		} catch (Exception e) {
			log.error("调用注册会员接口失败" + e.getMessage());
			e.printStackTrace();
		}
		if (jo == null) {
			log.info("请到实体店办理，jo为null");
			// return "会员注册失败，请重新申请手机验证";
			return "请到实体店办理";
		}
		log.info("创建会员返回" + jo.toString());
		boolean flag = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
		if (flag) {
			Integer membershipId = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getInt("id");
			String cardNo = jo.getJSONObject("data").getJSONObject("result").getString("card");
			String phoneNo = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("mobile");

			/*---------------分域------begin----------------------------*/
			MemberVO memberScope = new MemberVO();
			memberScope.setMembershipId(membershipId);
			memberScope.setBrandId(merchantId);
			memberScope.setDataSource(Constant.MEMBER_SCOPE_SOURCE_4); // 微信加会员
			memberScope.setWeixinId(weixinId);
			memberScope.setStatus(1);
			memberScope.setIsMember(true);
			memberInfoService.saveMemberScopeInfo(memberScope); // 返回结果新不处理
			/*---------------分域------end----------------------------*/
			
			if (tagId!=null) { // tagId不为空的时候调用打标签服务
				// 调用[会员打标签服务]
				try {
					saveOrModifyMembershipTagService.saveMembershipTagService(tagId,membershipId);
				} catch (Exception e) {
					log.error("[会员打标签服务]调用失败");
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
			//如果此时生日为空，则根据membershipId找寻相关电子会员的生日记录
			if(member.getBirthday()==null){
				List<MemberVO> memberList = memberDao.getMemberListByMembershipId(membershipId);
				if(memberList!=null){
					for (int i = 0; i < memberList.size(); i++) {
						MemberVO m = memberList.get(i);
						if(m.getBirthday()!=null){
							member.setBirthday(m.getBirthday());
							break;
						}
					}
				}
			}

			member.setMembershipId(membershipId);
			member.setCardNo(cardNo);
			member.setPhoneNo(phoneNo);
			member.setIsMember(true);
			memberDao.updateMemberInfo(member);
			
			//根据membershipId批量修改电子会员
			memberDao.batchUpdateMemberPageInfo(member);

			StringBuilder sb = new StringBuilder("");
			sb.append(business.getTitle());
			if (business.getBrandId().intValue() == 7098) {// 全聚德修改
				sb.append("恭喜您已成为本店普通会员\n\n" + "<a href=\"" + serverIp + path + "/weixin/phonePage/membershipCard.do?brandId="
						+ business.getBrandId() + "&weixinId=" + weixinId + "\">点击查看会员权益</a>" + "\n\n"
						+ business.getBirthdayMessage() + "~回复格式“19880808”\n\n");
			} else {
				sb.append("恭喜您已成为本店尊贵会员~\n\n" + "<a href=\"" + serverIp + path + "/weixin/phonePage/membershipCard.do?brandId="
						+ business.getBrandId() + "&weixinId=" + weixinId + "\">点击查看会员权益</a>" + "\n\n"
						+ business.getBirthdayMessage() + "~回复格式“19880808”\n\n");
			}

			sb.append(REPLYINFO);
			return sb.toString();

		} else {
			log.info("请会员注册失败，请重新申请手机验证 flag 为false");
			return "请到实体店办理";
			// return "会员注册失败，请重新申请手机验证";
		}
	}

	Message executeImage(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("感谢您的建议，留言已成功！");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	Message executeLocation(Message messageIn, BusinessVO business) {
		SubbranchVO subbranch = subbranchDao.getNearestSubbranch(business.getBrandId(), messageIn.getLocation_X(),
				messageIn.getLocation_Y());
		Message newsMessageOut = new Message();
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		message1.setTitle(business.getTitle());
		StringBuffer sescription = new StringBuffer("");
		sescription.append("您好，根据您输入的位置，我们发现离您最近的");
		sescription.append(business.getTitle());
		sescription.append("餐厅为");
		sescription.append(subbranch.getName());
		sescription.append("\n地址：");
		sescription.append(subbranch.getAddress());
		sescription.append("\n电话：");
		sescription.append(subbranch.getPhoneNo());
		sescription.append("\n查看大图请点击链接" + REPLYINFO);
		message1.setDescription(sescription.toString());
		StringBuffer picUrl = new StringBuffer("");
		picUrl.append("http://api.map.baidu.com/staticimage?width=640&height=320&center=");
		picUrl.append(subbranch.getPointX());
		picUrl.append(",");
		picUrl.append(subbranch.getPointY());
		picUrl.append("&markers=");
		picUrl.append(subbranch.getPointX());
		picUrl.append(",");
		picUrl.append(subbranch.getPointY());
		picUrl.append("&zoom=16");
		message1.setPicUrl(picUrl.toString());
		StringBuffer origin = new StringBuffer("");
		origin.append((messageIn.getLocation_X() + new Double(0.006)));
		origin.append(",");
		origin.append((messageIn.getLocation_Y() + new Double(0.006)));
		StringBuffer destination = new StringBuffer("");
		destination.append(subbranch.getPointY());
		destination.append(",");
		destination.append(subbranch.getPointX());
		log.info("http://api.map.baidu.com/direction?origin=latlng:" + origin.toString() + "|name:我的位置&destination=latlng:"
				+ destination.toString() + "|name:目的地&mode=driving&region=北京&output=html");
		message1.setUrl("http://api.map.baidu.com/direction?origin=latlng:" + origin.toString()
				+ "|name:我的位置&destination=latlng:" + destination.toString() + "|name:目的地&mode=driving&region=北京&output=html");
		// message1.setUrl("http://api.map.baidu.com/direction?origin=latlng:"
		// + origin.toString() + "|name:我所在位置&destination=latlng:"
		// + destination.toString()
		// + "|name:我想要去&mode=driving&region=北京&output=html");
		articles.add(message1);
		newsMessageOut.setToUserName(messageIn.getFromUserName());
		newsMessageOut.setFromUserName(messageIn.getToUserName());
		newsMessageOut.setCreateTime(messageIn.getCreateTime());
		newsMessageOut.setMsgType(Message.MSG_TYPE_NEWS);
		newsMessageOut.setFuncFlag("1");
		newsMessageOut.setArticleCount(articles.size());
		newsMessageOut.setArticles(articles);
		return newsMessageOut;
	}

	Message executeLink(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("感谢您的建议，留言已成功！");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	Message executeEvent(Message messageIn, BusinessVO business, MemberVO member, List<AutoreplyVO> autoreplyList) {

		if (messageIn.getEvent().equals(Message.MSG_EVENT_UNSUBSCRIBE)) {
			if (member != null) {
				member.setIsSubscribe(false);
				memberDao.updateMemberSubscribeStatus(member);
			}
		}
		if (messageIn.getEvent().equals(Message.MSG_EVENT_SUBSCRIBE)) {

			Message messageOut = new Message();
			messageOut.setTitle("");
			List<Message> articles = new ArrayList<Message>();
			Message message1 = new Message();
			message1.setTitle(business.getTitle());
			message1.setDescription("");
			String bigImageName = business.getBigimageName();
			if (!StringUtil.isNullOrEmpty(bigImageName) && bigImageName.startsWith("group1/M00/")) {
				message1.setPicUrl(dfsFilePath+bigImageName);
			} else {
				message1.setPicUrl(serverIp + path +weixinPhonePageServiceImpl.getNewImageUrl(business.getBrandId(), bigImageName));
			}
			message1.setUrl(serverIp + path + "/weixin/phonePage/membershipCard.do?brandId=" + business.getBrandId()
					+ "&weixinId=" + messageIn.getFromUserName());
			articles.add(message1);
			Message message3 = new Message();
			String title = "";
			// String salutatory=business.getSalutatory()==null
			if (business.getSalutatory() == null || business.getSalutatory().trim().equals("")) {
				title = "回复1加入或绑定会员\n回复2会员查询\n回复3会员优惠查询\n回复4门店信息\n回复5菜品信息\n回复其他建议留言";
			} else {
				title = business.getSalutatory() + "\n回复1加入或绑定会员\n回复2会员查询\n回复3会员优惠查询\n回复4门店信息\n回复5菜品信息\n回复其他建议留言";
			}
			message3.setTitle(title);
			message3.setDescription("");
			String smallImageName = business.getSmallimageName();
			if (!StringUtil.isNullOrEmpty(smallImageName) && smallImageName.startsWith("group1/M00/")) {
				message3.setPicUrl(dfsFilePath+smallImageName);
			} else {
				message3.setPicUrl(serverIp + path +weixinPhonePageServiceImpl.getNewImageUrl(business.getBrandId(), business.getSmallimageName()));
			}
			// message3.setUrl(serverIp + path
			// + "/weixin/phonePage/preference.do?brandId="
			// + business.getBrandId());
			message3.setUrl(serverIp + path + "/weixin/phonePage/membershipCard.do?brandId=" + business.getBrandId()
					+ "&weixinId=" + messageIn.getFromUserName());
			articles.add(message3);
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_NEWS);
			messageOut.setFuncFlag("1");
			messageOut.setArticleCount(articles.size());
			messageOut.setArticles(articles);
			member.setIsSubscribe(true);
			memberDao.updateMemberSubscribeStatus(member);
			return messageOut;
		} else if (messageIn.getEvent().equals(Message.MSG_EVENT_CLICK)) {
			if (messageIn.getEventKey().equals("pinpaigushi")) {
				return story(messageIn, business);
			} else if (messageIn.getEventKey().equals("youhui")) {
				return preference(messageIn, business);
			} else if (messageIn.getEventKey().equals("mendian")) {
				return subbranch(messageIn, business);
			} else if (messageIn.getEventKey().equals("caipin")) {
				return dishes(messageIn, business);
			} else if (messageIn.getEventKey().equals("jiaruhuiyuan")) {
				return join(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("huiyuanquanyi")) {
				return memberRights(messageIn, business);
			} else if (messageIn.getEventKey().equals("wodezhanghu")) {
				return myAccount(messageIn, business, member);
			} else if (messageIn.getEventKey().equals("huiyuanziliao")) {
				return memberInfo(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("wodeyouhui")) {
				return query(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("shengrilibao")) {
				return memberInfo(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("yijianshangwang")) {// 一键上网
				return messageClickVerifyService.message(messageIn, member, business);
			}

			// 新加的抽奖内容（刮刮卡，大转盘，老虎机）
			Message lotteryMessage = lotteryMessageVerifyService.message(messageIn, member, business, path);
			if (lotteryMessage != null) {
				return lotteryMessage;
			}

			// 这个为中奖的刮刮乐模式
			AutoreplyVO autoreply = checkAutoreply(messageIn, autoreplyList);
			if (autoreply != null && autoreply.getReplyType().equals("award")) {
				if (member == null) {
					Message messageOut = new Message();
					messageOut.setToUserName(messageIn.getFromUserName());
					messageOut.setFromUserName(messageIn.getToUserName());
					messageOut.setCreateTime(messageIn.getCreateTime());
					messageOut.setMsgType(Message.MSG_TYPE_TEXT);
					messageOut.setFuncFlag("0");
					messageOut.setContent("请回复手机号码加入会员~~");
					return messageOut;
				}
				if (member != null && !member.getIsMember() || member != null && member.getPhoneNo() == "" || member != null
						&& member.getPhoneNo() == null || member != null && member.getPhoneNo().length() < 11 || member != null
						&& !isNum(member.getPhoneNo()) || member != null && !isMobileNO(member.getPhoneNo())) {
					Message messageOut = new Message();
					messageOut.setToUserName(messageIn.getFromUserName());
					messageOut.setFromUserName(messageIn.getToUserName());
					messageOut.setCreateTime(messageIn.getCreateTime());
					messageOut.setMsgType(Message.MSG_TYPE_TEXT);
					messageOut.setFuncFlag("0");
					messageOut.setContent("请回复手机号码加入会员~~");
					return messageOut;
				}
				return activityService.executeAutoreply(messageIn, business, autoreply, path, member);

			} else if (autoreply != null) {// news text
				return activityService.executeAutoreply(messageIn, business, autoreply, path, member);
			} else {

				Message messageOut = new Message();
				messageOut.setToUserName(messageIn.getFromUserName());
				messageOut.setFromUserName(messageIn.getToUserName());
				messageOut.setCreateTime(messageIn.getCreateTime());
				messageOut.setMsgType(Message.MSG_TYPE_TEXT);
				messageOut.setFuncFlag("0");
				messageOut.setContent("亲,暂时木有活动,谢谢您的参与");
				return messageOut;
			}
		}
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setFuncFlag("0");
		messageOut.setContent("null");
		return messageOut;
	}

	Message executeVoice(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("感谢您的建议，留言已成功！");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	Message executeother(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("感谢您的建议，留言已成功！");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	public void eventRecord(EventRecordVO eventRecord) {
		eventRecordDao.insertEventRecord(eventRecord);
	}

	static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	static boolean isNum(String input) {

		Pattern p = Pattern.compile("^[0-9_]+$");
		Matcher m = p.matcher(input);
		return m.matches();
	}

	boolean isDate(String input) {
		if (!input.startsWith("19") && !input.startsWith("20")) {
			return false;
		}
		Pattern p = Pattern.compile("^[0-9_]+$");
		Matcher m = p.matcher(input);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	public JSONObject registerMircoMessageMembership(String json) throws Exception {
		json = URLEncoder.encode(json, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = registerMircoMessageMembership + "?ciphertext=" + json + "&key=" + Constant.KEY + "&micromessage=true";

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

	AutoreplyVO checkAutoreply(Message messageIn, List<AutoreplyVO> autoreplyList) {
		String[] ss = null;
		for (AutoreplyVO autoreply : autoreplyList) {
			if (autoreply.getIsDelete()) {
				continue;
			}
			ss = autoreply.getKeyword().split(" ");
			// 关键字完全匹配且消息类型text
			if (autoreply.getKeywordType().equals("equals") && messageIn.getMsgType().equals(Message.MSG_TYPE_TEXT)
					&& autoreply.getKeyword().trim().equals(messageIn.getContent().trim())) {
				return autoreply;
				// 关键字完全匹配且消息类型为event
			} else if (autoreply.getKeywordType().equals("equals") && messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)
					&& messageIn.getEvent().equals(Message.MSG_EVENT_CLICK)) {
				if (autoreply.getKeyword().trim().equals(messageIn.getEventKey())) {
					return autoreply;
				}
				// 关键字全部出现在句子内
			} else if (autoreply.getKeywordType().equals("like") && !messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)) {
				for (String key : ss) {
					if (messageIn.getContent().trim().indexOf(key.trim()) != -1) {
						return autoreply;
					}
				}

			}
		}

		return null;
	}

	public Message query(Message messageIn, MemberVO member, BusinessVO business) {
		if (!member.getIsMember()) {
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setContent("您还不是会员哦，请先输入手机号成为会员，再进行会员相关操作~");
			messageOut.setFuncFlag("0");
			return messageOut;
		}

		Message newsMessageOut = new Message();
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		message1.setTitle("点击进入会员中心");
		message1.setUrl(serverIp + path + "/weixin/phonePage/fensiCard.do?brandId=" + business.getBrandId() + "&weixinId="
				+ messageIn.getFromUserName());
		message1.setPicUrl(serverIp + path + "/images/huiyuanquanyi.jpg");
		articles.add(message1);
		List<MemberCardVO> cardList = cardService.queryCardList(member.getBrandId(), member.getMembershipId());//member.getPhoneNo());
		cardService.showCouponList(articles, cardList, member.getBrandId(), member.getPhoneNo(), messageIn.getFromUserName(), path);
		newsMessageOut.setToUserName(messageIn.getFromUserName());
		newsMessageOut.setFromUserName(messageIn.getToUserName());
		newsMessageOut.setCreateTime(messageIn.getCreateTime());
		newsMessageOut.setMsgType(Message.MSG_TYPE_NEWS);
		newsMessageOut.setFuncFlag("1");
		newsMessageOut.setArticleCount(articles.size());
		newsMessageOut.setArticles(articles);
		return newsMessageOut;

	}

	public String modifyMemberInfo(String birthday, MemberVO member, BusinessVO business) {

		if (member.getUpdateTimes().intValue() >= 2) {
			return "超过容许的修改次数，暂时无法再次更改";
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM月dd日");
		String date = null;
		String cDate = null;
		String validateDate = null;
		try {
			validateDate = sdf1.format(sdf1.parse(birthday));
			date = sdf2.format(sdf1.parse(birthday));
			cDate = sdf3.format(sdf1.parse(birthday));
			member.setBirthday(sdf1.parse(birthday));
		} catch (ParseException e1) {
			log.error("生日格式错误--" + birthday + e1.getMessage());
			return "生日格式错误";
		}
		if (!validateDate.equals(birthday)) {
			log.error("生日格式错误--" + birthday);
			return "生日格式错误";
		}

		if (member.getIsMember()) {
			String birthType=member.getBirthType();
			if(StringUtils.isEmpty(birthType)){
				birthType="1";
			}
			String json = "{\"id\":\"" + member.getMembershipId()
					+ "\",\"birthday\":\"" + date + "\",\"birthType\":\""+birthType+"\"}";
			JSONObject jo = null;
			try {
				jo = modifyMember(json);
			} catch (Exception e) {
				log.error(json + e.getMessage());
				e.printStackTrace();
			}
			log.info("修改会员信息返回" + jo.toString());
			boolean flag = jo.getBoolean("success");
			if (flag) {

				// 调用[会员打标签服务]
				Integer tagId=business.getTagId();
				Integer membershipId = member.getMembershipId();
				if (tagId!=null) { // tagId不为空的时候调用打标签服务
					try {
						saveOrModifyMembershipTagService.saveMembershipTagService(tagId,membershipId);
					} catch (Exception e) {
						log.error("[会员打标签服务]调用失败");
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}
				
				//如果此时生日为空，则根据membershipId找寻相关电子会员的生日记录
				if(member.getBirthday()==null){
					List<MemberVO> memberList = memberDao.getMemberListByMembershipId(membershipId);
					if(memberList!=null){
						for (int i = 0; i < memberList.size(); i++) {
							MemberVO m = memberList.get(i);
							if(m.getBirthday()!=null){
								member.setBirthday(m.getBirthday());
								break;
							}
						}
					}
				}

				log.info("修改成功");
				memberDao.updateMemberInfo(member);
				
				//根据membershipId批量修改电子会员
				memberDao.batchUpdateMemberPageInfo(member);
				
				return "您的生日是" + cDate + "，请届时关注我们的店内活动~\n如生日有误，请重新输入。";
			} else {
				return "修改失败，请稍后重试";
			}
		} else {
			String info = "您还不是会员哦，请先输入手机号成为会员，再进行会员相关操作~";
			return info;
		}

	}

	public Message join(Message messageIn, MemberVO member, BusinessVO business) {
		Message message = new Message();
		message.setToUserName(messageIn.getFromUserName());
		message.setFromUserName(messageIn.getToUserName());
		message.setCreateTime(messageIn.getCreateTime());
		message.setMsgType(Message.MSG_TYPE_TEXT);
		message.setFuncFlag("0");
		String info = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (member.getIsMember()) {
			CardVO card = getCard(member.getCardNo(), member, business);

			if (card.getStatusInfo() != null) {
				info = card.getStatusInfo();
			} else {
				info = "您已经是粉丝会员了。\n您的卡是：粉丝会员卡\n开卡日期：" + sdf.format(card.getActiveDate());
			}

			message.setContent(info);
			log.info(info);
			return message;
		}

//		boolean isAllowWeixinMember = business.getIsAllowWeixinMember();
//
//		if (isAllowWeixinMember) {
//			info = "请输入手机号，加入会员或者绑定已有会员";
//			message.setContent(info);
//			log.info(info);
//			return message;
//		} else {
			info = "请输入手机号，绑定已有会员卡";
			message.setContent(info);
			log.info(info);
			return message;
//		}

	}

	public Message preference(Message messageIn, BusinessVO business) {
		Message message = new Message();
		message.setToUserName(messageIn.getFromUserName());
		message.setFromUserName(messageIn.getToUserName());
		message.setCreateTime(messageIn.getCreateTime());
		message.setMsgType(Message.MSG_TYPE_TEXT);
		message.setFuncFlag("0");

		StringBuffer sb = new StringBuffer("");
		if (business.getPreferenceList() != null) {
			int i = 0;
			for (PreferenceVO preference : business.getPreferenceList()) {
				if (!preference.getIsDelete() && preference.getIsRecommend()) {
					if (i++ > 3)
						break;
					sb.append(i + "、" + preference.getTitle() + "\n");
				}
			}
		}

		sb.append("点击查看" + business.getTitle() + "<a href=\"" + serverIp + path + "/weixin/phonePage/preference.do?brandId="
				+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "\">" + "优惠信息</a>");
		sb.append(REPLYINFO);
		message.setContent(sb.toString());
		return message;
	}

	public Message subbranch(Message messageIn, BusinessVO business) {
		Message newsMessageOut = new Message();
		newsMessageOut.setTitle(business.getTitle());
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		message1.setTitle(business.getTitle());
		message1.setDescription("点击链接查看全部店面；\n输入位置查询附近店面，具体操作请参见上图");
		message1.setPicUrl(serverIp + path + "/images/weizhi.jpg");
//		message1.setUrl(serverIp + path + "/weixin/phonePage/subbranch.do?brandId=" + business.getBrandId() + "&weixinId="
//				+ messageIn.getFromUserName());
		message1.setUrl(serverIp + path + "/weixin/newFun/subbranch.do?brandId=" + business.getBrandId() + "&weixinId="
				+ messageIn.getFromUserName());
		articles.add(message1);
		newsMessageOut.setToUserName(messageIn.getFromUserName());
		newsMessageOut.setFromUserName(messageIn.getToUserName());
		newsMessageOut.setCreateTime(messageIn.getCreateTime());
		newsMessageOut.setMsgType(Message.MSG_TYPE_NEWS);
		newsMessageOut.setFuncFlag("1");
		newsMessageOut.setArticleCount(articles.size());
		newsMessageOut.setArticles(articles);
		return newsMessageOut;
	}

	public Message dishes(Message messageIn, BusinessVO business) {
		Message message = new Message();
		message.setToUserName(messageIn.getFromUserName());
		message.setFromUserName(messageIn.getToUserName());
		message.setCreateTime(messageIn.getCreateTime());
		message.setMsgType(Message.MSG_TYPE_TEXT);
		message.setFuncFlag("0");

		StringBuffer sb = new StringBuffer("");
		if (business.getDishesList() != null) {
			int i = 0;
			for (DishesVO dishes : business.getDishesList()) {
				if (!dishes.getIsDelete() && dishes.getIsRecommend()) {
					if (i++ > 3)
						break;
					sb.append(i + "、" + dishes.getName() + "\n");
				}
			}

		}
		sb.append("点击查看" + business.getTitle() + "<a href=\"" + serverIp + path + "/weixin/newFun/dishes.do?brandId="
				+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "&r="+Math.random()+"\">菜品信息</a>");
		sb.append(REPLYINFO);
		message.setContent(sb.toString());

		return message;
	}

	public Message story(Message messageIn, BusinessVO business) {
		// if(business.getBrandId().intValue()==3725){
		// Message message = new Message();
		// message.setToUserName(messageIn.getFromUserName());
		// message.setFromUserName(messageIn.getToUserName());
		// message.setCreateTime(messageIn.getCreateTime());
		// message.setMsgType(Message.MSG_TYPE_TEXT);
		// message.setFuncFlag("0");
		// message.setContent("2000年5月20日，俏江南首家餐厅在北京CBD国际贸易中心开业。2002年，俏江南走出北京，在上海淮海路的时代广场开设了第一家北京以外区域的餐厅，从此开启了跨区域连锁经营的步伐。历经多年的积累和发展，截至2012年9月底，俏江南集餐厅已遍布中国十五个省份、十八个城市，拥有近70家餐厅，成为国内领先的中式正餐连锁经营企业。“时尚、经典、品位、尊崇”的经营理念，是俏江南独具特色的企业精神。弘扬中华五千年的餐饮文化，致力于打造一个世界级的中餐品牌是俏江南持久追求的目标 。\n\n点击查看俏江南"
		// + "<a href=\""
		// + serverIp
		// + path
		// + "/weixin/phonePage/story.do?brandId="
		// + business.getBrandId()
		// + "&weixinId=" + messageIn.getFromUserName() + "\">品牌故事</a>");
		//
		// return message;
		// }
		Story story = storyDao.getStoryByBrandId(business.getBrandId());
		if (story == null) {
			Message message = new Message();
			message.setToUserName(messageIn.getFromUserName());
			message.setFromUserName(messageIn.getToUserName());
			message.setCreateTime(messageIn.getCreateTime());
			message.setMsgType(Message.MSG_TYPE_TEXT);
			message.setFuncFlag("0");
			message.setContent("null");
			return message;
		}
		Message newsMessageOut = new Message();
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		message1.setTitle(story.getTitle());
		
		String imageName = story.getImageName();
		if (!StringUtil.isNullOrEmpty(imageName) && imageName.startsWith("group1/M00/")) {
			message1.setPicUrl(dfsFilePath+imageName);
		} else {
			message1.setPicUrl(serverIp + path +weixinPhonePageServiceImpl.getNewImageUrl(business.getBrandId(), imageName));
		}
		articles.add(message1);
		Message message2 = new Message();
		message2.setTitle(story.getContent().replace("<br>", "\n"));
		articles.add(message2);
		newsMessageOut.setToUserName(messageIn.getFromUserName());
		newsMessageOut.setFromUserName(messageIn.getToUserName());
		newsMessageOut.setCreateTime(messageIn.getCreateTime());
		newsMessageOut.setMsgType(Message.MSG_TYPE_NEWS);
		newsMessageOut.setFuncFlag("1");
		newsMessageOut.setArticleCount(articles.size());
		newsMessageOut.setArticles(articles);
		return newsMessageOut;
	}

	public Message memberRights(Message messageIn, BusinessVO business) {
		Message newsMessageOut = new Message();
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		message1.setTitle("\"" + business.getTitle() + "\"粉丝会员卡");
		message1.setPicUrl(serverIp + path + "/images/huiyuanquanyi.jpg");
		message1.setUrl("");
		articles.add(message1);

		Message message2 = new Message();
		if (business.getMemberRights().trim().equals("")) {//
			message2.setTitle("");
		} else {
			message2.setTitle(business.getMemberRights().replace("<br>", "\n"));
		}
		articles.add(message2);

		newsMessageOut.setToUserName(messageIn.getFromUserName());
		newsMessageOut.setFromUserName(messageIn.getToUserName());
		newsMessageOut.setCreateTime(messageIn.getCreateTime());
		newsMessageOut.setMsgType(Message.MSG_TYPE_NEWS);
		newsMessageOut.setFuncFlag("1");
		newsMessageOut.setArticleCount(articles.size());
		newsMessageOut.setArticles(articles);
		return newsMessageOut;
	}

	public Message memberInfo(Message messageIn, MemberVO member, BusinessVO business) {

		if (!member.getIsMember()) {
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setContent("您还不是会员哦，请先输入手机号成为会员，再进行会员相关操作~");
			messageOut.setFuncFlag("0");
			return messageOut;
		}

		Message message = new Message();
		message.setToUserName(messageIn.getFromUserName());
		message.setFromUserName(messageIn.getToUserName());
		message.setCreateTime(messageIn.getCreateTime());
		message.setMsgType(Message.MSG_TYPE_TEXT);
		message.setFuncFlag("0");
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM月dd日");
		if (member.getBirthday() == null) {
			message.setContent("您的生日资料还不完整，" + business.getBirthdayMessage() + "~回复格式：“19880808”");
			return message;
		} else {
			message.setContent("您的生日是" + sdf3.format(member.getBirthday()) + "，请届时关注我们的店内活动~\n如生日有误，请重新输入。");
			return message;
		}
	}

	public Message myAccount(Message messageIn, BusinessVO business, MemberVO member) {

		if (!member.getIsMember()) {
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setContent("您还不是会员哦，请先输入手机号成为会员，再进行会员相关操作~");
			messageOut.setFuncFlag("0");
			return messageOut;
		}

		Message newsMessageOut = new Message();
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		message1.setTitle("点击进入会员中心");
		message1.setUrl(serverIp + path + "/weixin/phonePage/fensiCard.do?brandId=" + business.getBrandId() + "&weixinId="
				+ messageIn.getFromUserName());
		message1.setPicUrl(serverIp + path + "/images/huiyuanquanyi.jpg");
		articles.add(message1);
		List<MemberCardVO> cardList = cardService.queryCardList(member.getBrandId(), member.getMembershipId());//member.getPhoneNo());
		cardService.showCouponList(articles, cardList, member.getBrandId(), member.getPhoneNo(), messageIn.getFromUserName(), path);
		/*CardVO card = getCard(member.getCardNo(), member, business);
		if (card.getStatusInfo() != null) {// 异常卡
			String info = card.getStatusInfo();
			log.info(info);
			Message message = new Message();
			message.setToUserName(messageIn.getFromUserName());
			message.setFromUserName(messageIn.getToUserName());
			message.setCreateTime(messageIn.getCreateTime());
			message.setMsgType(Message.MSG_TYPE_TEXT);
			message.setFuncFlag("0");
			message.setContent(info);
			return message;
		}
		List<CouponVO> couponList = memberDao.getCouponList(member.getPhoneNo(), card.getCardNo(), member.getBrandId());
		List<CouponVO> daiJinCouponList = memberDao.getDaiJinCouponList(member.getPhoneNo(), member.getBrandId());
		List<CouponVO> guaGuaCouponList = memberDao.getGuaGuaCouponList(member.getPhoneNo(), member.getBrandId());
		MerchantVO merchantVO = null;
		if (daiJinCouponList != null && daiJinCouponList.size() != 0) {
			merchantVO = memberDao.getMerchantByBrandId(member.getBrandId());
		}

		if (card.getIntegralAvailable() != null) {
			Message cardInfo = new Message();
			cardInfo.setTitle("积分余额：" + card.getIntegralAvailable().doubleValue());
			articles.add(cardInfo);
		}
		if (card.getStoreBalance() != null) {
			Message cardInfo = new Message();
			cardInfo.setTitle("储值余额：" + card.getStoreBalance().doubleValue());
			articles.add(cardInfo);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int i = 0;
		for (CouponVO coupon : couponList) {
			if (i >= 6)
				break;
			String scope = "";
			if (coupon.getScope() != null && coupon.getScope().intValue() == 1) {
				scope = "仅限" + coupon.getMerchantName() + "可用";
			}
			if (coupon.getScope() != null && coupon.getScope().intValue() == 2) {
				scope = "连锁店可用";
			}
			if (coupon.getScope() != null && coupon.getScope().intValue() == 3) {
				scope = "部分门店可用";
			}
			Message couponInfo = new Message();
			couponInfo.setTitle(coupon.getName() + "\n" + sdf.format(coupon.getStartDate()) + "至"
					+ sdf.format(coupon.getExpiredDate()) + "有效" + "\n" + scope);
			articles.add(couponInfo);
			i++;
		}
		for (CouponVO coupon : daiJinCouponList) {
			if (i >= 6)
				break;
			String scope = "连锁店可用";
			if (coupon.getScope() != null && coupon.getScope().intValue() == 1) {
				scope = "仅限" + coupon.getMerchantName() + "可用";
			}
			if (coupon.getScope() != null && coupon.getScope().intValue() == 2) {
				scope = "连锁店可用";
			}
			if (coupon.getScope() != null && coupon.getScope().intValue() == 3) {
				scope = "部分门店可用";
			}
			try {
				String goodsId = getGoodsId("62c163c9c993d5842d18876d60ca1983", coupon.getCode(), merchantVO.getMerchantNo());
				Message couponInfo = new Message();
				couponInfo.setTitle(coupon.getCouponName() + "\n" + sdf.format(coupon.getStartDate()) + "至"
						+ sdf.format(coupon.getExpiredDate()) + "有效" + "\n" + scope);
				couponInfo.setUrl("http://food.weibo.com/wap/food.cash/" + goodsId);
				articles.add(couponInfo);
				i++;
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}

		}

		ActivityRecordVO activityRecord = new ActivityRecordVO();
		activityRecord.setBrandId(business.getBrandId());
		activityRecord.setWeixinId(messageIn.getFromUserName());
		activityRecord = activityDao.getLastActivityRecordByBrandIdAndWeixinId(activityRecord);
		if (activityRecord == null) {
			if (guaGuaCouponList != null && guaGuaCouponList.size() != 0) {
				for (CouponVO coupon : guaGuaCouponList) {
					if (i >= 6)
						break;
					String scope = "连锁店可用";
					if (coupon.getScope() != null && coupon.getScope().intValue() == 1) {
						scope = "仅限" + coupon.getMerchantName() + "可用";
					}
					if (coupon.getScope() != null && coupon.getScope().intValue() == 2) {
						scope = "连锁店可用";
					}
					if (coupon.getScope() != null && coupon.getScope().intValue() == 3) {
						scope = "部分门店可用";
					}
					Message couponInfo = new Message();
					couponInfo.setTitle(coupon.getCouponName() + "\n" + sdf.format(coupon.getStartDate()) + "至"
							+ sdf.format(coupon.getExpiredDate()) + "有效" + "\n" + scope);
					articles.add(couponInfo);
					i++;
				}
			}
		} else if (activityRecord != null && activityRecord.getIsDone()) {
			if (guaGuaCouponList != null && guaGuaCouponList.size() != 0) {
				for (CouponVO coupon : guaGuaCouponList) {
					if (i >= 6)
						break;
					String scope = "连锁店可用";
					if (coupon.getScope() != null && coupon.getScope().intValue() == 1) {
						scope = "仅限" + coupon.getMerchantName() + "可用";
					}
					if (coupon.getScope() != null && coupon.getScope().intValue() == 2) {
						scope = "连锁店可用";
					}
					if (coupon.getScope() != null && coupon.getScope().intValue() == 3) {
						scope = "部分门店可用";
					}
					Message couponInfo = new Message();
					couponInfo.setTitle(coupon.getCouponName() + "\n" + sdf.format(coupon.getStartDate()) + "至"
							+ sdf.format(coupon.getExpiredDate()) + "有效" + "\n" + scope);
					articles.add(couponInfo);
					i++;
				}
			}
		} else if (activityRecord != null && !activityRecord.getIsDone()) {
			if (guaGuaCouponList != null && guaGuaCouponList.size() != 0) {
				for (int j = 1; j < guaGuaCouponList.size(); j++) {
					if (i >= 6)
						break;
					String scope = "连锁店可用";
					if (guaGuaCouponList.get(j).getScope() != null && guaGuaCouponList.get(j).getScope().intValue() == 1) {
						scope = "仅限" + guaGuaCouponList.get(j).getMerchantName() + "可用";
					}
					if (guaGuaCouponList.get(j).getScope() != null && guaGuaCouponList.get(j).getScope().intValue() == 2) {
						scope = "连锁店可用";
					}
					if (guaGuaCouponList.get(j).getScope() != null && guaGuaCouponList.get(j).getScope().intValue() == 3) {
						scope = "部分门店可用";
					}
					Message couponInfo = new Message();
					couponInfo.setTitle(guaGuaCouponList.get(j).getCouponName() + "\n"
							+ sdf.format(guaGuaCouponList.get(j).getStartDate()) + "至"
							+ sdf.format(guaGuaCouponList.get(j).getExpiredDate()) + "有效" + "\n" + scope);
					articles.add(couponInfo);
					i++;
				}
			}
		}*/

		newsMessageOut.setToUserName(messageIn.getFromUserName());
		newsMessageOut.setFromUserName(messageIn.getToUserName());
		newsMessageOut.setCreateTime(messageIn.getCreateTime());
		newsMessageOut.setMsgType(Message.MSG_TYPE_NEWS);
		newsMessageOut.setFuncFlag("1");
		newsMessageOut.setArticleCount(articles.size());
		newsMessageOut.setArticles(articles);
		return newsMessageOut;

	}

	/**
	 * 最老的 当返回信息是卡失效，查别的有效的卡，通过手机号差别的有效的卡，通过在调用卡查询接口去返回值，更新默认membership的卡号
	 * 
	 * @param cardNo
	 * @param member
	 * @param business
	 * @return
	 */
	public CardVO getCard(String cardNo, MemberVO member, BusinessVO business) {
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
			card.setStatusInfo("查询失败,请稍候重试");
			return card;
		}
		// if (!jo.getBoolean("success")) {
		// card.setStatusInfo(jo.getString("message"));
		// return card;
		// }

		if (!jo.getBoolean("success")) {// 当前卡号无效
										// ，根据crm的membership表中查出的，做测试时要将其对应下的trade库中的记录也要删除
			MemberVO member2 = memberDao.getMember(member.getWeixinId(), business.getBrandId());
			if (member2 == null) {
				log.info("该用户不存在");
				card.setStatusInfo("该用户不存在");
				return card;
			}
			// phone（手机号），商家id,membershipId
			// 查询该用户的一张卡号，根据trade库中查出
			// 根据merchantId查询brandId
			int brandId = merchantDao.queryBrandIdByMerchantId(member.getBrandId());
			member.setBrandId(brandId);
			
			BrandVO brandvo = cardService.getMerchant(brandId);//查询brandId
			List<MemberCardVO> otherCardList = new ArrayList<MemberCardVO>();
			//读取搜索引擎会员ids
			Set<Object> set = memberShipCenterService.queryMemberShipsByMobile(brandvo.getBrandId().toString(), member.getPhoneNo());
			//读取卡列表用搜索引擎
			otherCardList = memberShipCenterService.queryMemberCardList(brandvo.getBrandId().toString(), set.toArray());
			if (otherCardList == null &&otherCardList.size()<1) {// 没有其它有效卡了
				card.setStatusInfo("卡已失效");
				log.info("该卡已失效");
				return card;
			} else {// 查询出有效的实体卡，更新卡号
				MemberCardVO mcv= otherCardList.get(0);
				member.setCardNo(mcv.getCardNo());
				CardVO otherCard = new CardVO();
				otherCard.setCardNo(mcv.getCardNo());
				otherCard.setMembershipId(Integer.parseInt(mcv.getMembershipId()));
				memberDao.updateMemebrShipInfoById(member, otherCard);
				log.info("修改成功");
				return otherCard;
			}
		}

		card.setIntegralAvailable(new BigDecimal(jo.getString("integral")));
		card.setStoreBalance(new BigDecimal(jo.getString("store")));
		card.setActiveDate(new Date(new Long(jo.getJSONObject("activate").getString("time"))));
		return card;
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
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			// String reStr = EncryptUtil.dncrypt3DesString(secretKey, data);
			log.info("查询卡返回" + data);
			return JSONObject.fromObject(data);
		} else {
			return null;
		}
	}

	public JSONObject modifyMember(String json) throws Exception {
		json = URLEncoder.encode(json, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = modifyMemberMessage + "?ciphertext=" + json + "&publicKey=" + Constant.KEY;
		log.info("请求改用户信息" + url);
		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			// String reStr = EncryptUtil.dncrypt3DesString(secretKey, data);
			log.info("查询卡返回" + data);
			return JSONObject.fromObject(data);
		} else {
			return null;
		}
	}

	public String getGoodsId(String secure_code, String code, String yazuoCode) throws IOException {
		// Post请求的url，与get不同的是不需要带参数
		URL postUrl = new URL(posCheck);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		String content = "code=" + code + "&secure_code=" + secure_code + "&yazuo_code=" + yazuoCode;// 3开头券查询校验接口(可开放)
		out.writeBytes(content);
		out.flush();
		out.close(); // flush and close
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
		String result = "";
		StringBuffer sb = new StringBuffer();
		while ((result = reader.readLine()) != null) {
			sb.append(result);
		}
		JSONObject requestObject = JSONObject.fromObject(sb.toString());
		String goodsId = requestObject.getJSONObject("code_info").getString("goods_id");
		System.out.println(requestObject);
		reader.close();
		connection.disconnect();
		return goodsId;
	}

}
