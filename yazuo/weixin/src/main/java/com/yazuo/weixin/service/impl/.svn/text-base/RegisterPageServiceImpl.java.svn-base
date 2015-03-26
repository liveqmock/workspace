package com.yazuo.weixin.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.jms.JMSException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.member.service.MemberInfoService;
import com.yazuo.weixin.minimart.dao.WxMallOrderCouponStateDao;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.service.BindingMemberAndCardService;
import com.yazuo.weixin.service.LotteryMessageVerifyService;
import com.yazuo.weixin.service.MessageClickVerifyService;
import com.yazuo.weixin.service.RegisterPageService;
import com.yazuo.weixin.service.SaveOrModifyMembershipTagService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.MessageSender;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.CardVO;
import com.yazuo.weixin.vo.DishesVO;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.IdentifyinginfoVO;
import com.yazuo.weixin.vo.ImageConfigVO;
import com.yazuo.weixin.vo.MemberCardVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.Message;
import com.yazuo.weixin.vo.PreferenceVO;
import com.yazuo.weixin.vo.Story;
import com.yazuo.weixin.vo.SubbranchVO;

@Scope("prototype")
@Service
public class RegisterPageServiceImpl implements RegisterPageService {
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
	@Value("#{propertiesReader['queryMemberByPhoneNo']}")
	private String queryMemberByPhoneNo; // 根据手机号和brand_id取crm会员信息
	@Value("#{propertiesReader['caFileIp']}")
	private String caFileIp;
	@Value("#{propertiesReader['newPictureUrl']}")
	private String newPictureUrl;
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
	private BindingMemberAndCardService bindingMemberAndCardService;
	@Resource
	private WeixinPhonePageService weixinPhonePageServiceImpl;
	@Resource
	private WxMallOrderCouponStateDao wxMallOrderCouponStateDao;
	@Resource
	private MerchantDao merchantDao;
	@Resource
	private CardService cardService;
	@Resource
	private MemberShipCenterService memberShipCenterService;
	@Resource
	private WeixinMallMartService weixinMallMartService;
	@Resource
	private WeixinManageService weixinManageService;
	@Resource
	private MemberInfoService memberInfoService;
	
	private String path;
	private String REPLYINFO = "";// 一些其它信息
	Logger log = Logger.getLogger(this.getClass());
	private static final Log templateLog = LogFactory.getLog("templateLog");
	
	ResourceBundle bundle = ResourceBundle.getBundle("image-config");

	private Integer buli_brandid = Integer.parseInt(bundle.getString("buli_brandid"));
	private Integer quanjude_brandid = Integer.parseInt(bundle.getString("quanjude_brandid"));
	
	@Value("#{propertiesReader['dfsFilePath']}")
	private String dfsFilePath; // 图片服务器地址

	// 需要用户的姓名 性别 手机号 生日
	@Override
	public Message message(Message messageIn, BusinessVO business, String path, List<AutoreplyVO> autoreplyList) {
		this.path = path;
		// 布力布力入口
		MemberVO member = memberDao.getMember(messageIn.getFromUserName(), business.getBrandId());
		if (member == null) {// 新用户
			member = new MemberVO();
			member.setBrandId(business.getBrandId());
			member.setWeixinId(messageIn.getFromUserName());
			member.setIsMember(false);
			memberDao.insertMember(member);
			member = memberDao.getMember(messageIn.getFromUserName(), business.getBrandId());
		}
		// 新用户关注,根据key做响应的处理事件,扫描二维码开始,
		if (messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)) {
			return executeEvent(messageIn, business, member, autoreplyList);
		}
		// 信息消息处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_TEXT)) {
			// 先去执行自定义消息处理
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
		// 链接消息处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_LINK)) {
			return executeLink(messageIn, business);
		}
		// 语音消息处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_VOICE)) {
			return executeVoice(messageIn, business);
		}
		// 地理位置消息处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_LOCATION)) {
			return executeLocation(messageIn, business);
		} else {
			return executeOther(messageIn, business);
		}

	}
	
	// 点击关注下发图文是可配置的
	private void eventSubscribe (Message messageIn, BusinessVO business, List<AutoreplyVO> autoreplyList, List<Message> articles, Message messageOut) {
		if (autoreplyList !=null && autoreplyList.size() > 0) {
			for (AutoreplyVO autoreply : autoreplyList) {
				if (autoreply.getEventType() !=null && autoreply.getEventType() ==1) { // 点击关注下发图文
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
					}
				} else if (autoreply.getEventType() ==2) { //点击非关注下发图文
					
				}
			}
		} else {
			Message message1 = new Message();
			message1.setTitle("点我-加粉丝，享优惠！");
			message1.setDescription("");
			
			String bigImageName = business.getBigimageName();
			if (!StringUtil.isNullOrEmpty(bigImageName) && bigImageName.startsWith("group1/M00/")) {
				message1.setPicUrl(dfsFilePath+bigImageName);
			} else {
				message1.setPicUrl(serverIp + path + weixinPhonePageServiceImpl.getNewImageUrl(business.getBrandId(), bigImageName));
			}
			// 进入到注册页面
			message1.setUrl(serverIp + path + "/weixin/phonePage/registerPage.do?brandId=" + business.getBrandId() + "&weixinId="
					+ messageIn.getFromUserName());

			articles.add(message1);
			Message message2 = new Message();
			String title = "";
			if (business.getSalutatory() == null || business.getSalutatory() != null
					&& business.getSalutatory().trim().equals("")) {
				title = "亲,欢迎你来到" + business.getTitle();
			} else {
				if (business.getSalutatory().equals("不送优惠")) {
					message2.setTitle("亲,欢迎你来到" + business.getTitle());
				}
				title = business.getSalutatory();
			}
			message2.setTitle(title);
			message2.setDescription("");
			String smallImageName = business.getSmallimageName();
			if (!StringUtil.isNullOrEmpty(smallImageName) && smallImageName.startsWith("group1/M00/")) {
				message2.setPicUrl(dfsFilePath+smallImageName);
			} else {
				message2.setPicUrl(serverIp + path + weixinPhonePageServiceImpl.getNewImageUrl(business.getBrandId(), smallImageName));
			}
			message2.setUrl(serverIp + path + "/weixin/phonePage/fensiCard.do?brandId=" + business.getBrandId() + "&weixinId="
					+ messageIn.getFromUserName());
			articles.add(message2);
			
			messageOut.setMsgType(Message.MSG_TYPE_NEWS);
		}
	}

	// 入口
	Message executeEvent(Message messageIn, BusinessVO business, MemberVO member, List<AutoreplyVO> autoreplyList) {
		if (messageIn.getEvent().equals(Message.MSG_EVENT_SUBSCRIBE)) { // 订阅
			Message messageOut = new Message();
			messageOut.setTitle("");
			List<Message> articles = new ArrayList<Message>();
			
			//将不是订阅的图文剔除
			List<AutoreplyVO> tempList = new ArrayList<AutoreplyVO>();
			if (autoreplyList!=null && autoreplyList.size()>0) {
				for (AutoreplyVO reply : autoreplyList) {
					if (reply.getEventType()!=null && reply.getEventType()==1) {
						tempList.add(reply);
					}
				}
			}
			// 调订阅下发图文接口
			eventSubscribe(messageIn, business, tempList, articles, messageOut);
			
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setFuncFlag("1");
			messageOut.setArticleCount(articles.size());
			messageOut.setArticles(articles);
			
			/*---------------分域------begin----------------------------*/
			MemberVO memberScope = new MemberVO();
			memberScope.setBrandId(business.getBrandId());
			memberScope.setDataSource(Constant.MEMBER_SCOPE_SOURCE_1); // 微信加会员
			memberScope.setWeixinId(messageIn.getFromUserName());
			memberScope.setStatus(1);
			memberScope.setIsMember(false);
			memberInfoService.saveMemberScopeInfo(memberScope); // 返回结果新不处理
			
			// 更新订阅状态
			member.setIsSubscribe(true);
			memberDao.updateMemberSubscribeStatus(member);
			return messageOut;
		}

		if (messageIn.getEvent().equals(Message.MSG_EVENT_UNSUBSCRIBE)) { // 取消
			if (member != null) {
				member.setIsSubscribe(false);
				memberDao.updateMemberSubscribeStatus(member);
				
				/*---------------分域------begin----------------------------*/
				// 将状态更新为不可用
				memberInfoService.updateAvailableWeixinScope(business.getBrandId(), messageIn.getFromUserName());
				/*---------------分域------end----------------------------*/
				
				Message messageOut = new Message();
				messageOut.setToUserName(messageIn.getFromUserName());
				messageOut.setFromUserName(messageIn.getToUserName());
				messageOut.setCreateTime(messageIn.getCreateTime());
				messageOut.setMsgType(Message.MSG_TYPE_TEXT);
				messageOut.setFuncFlag("0");
				messageOut.setContent("null");
				return messageOut;
			}
		}
		if (messageIn.getEvent().equals(Message.MSG_EVENT_TEMPLATE)) { // 消息模版
			templateLog.info(business.getBrandId()+";"+messageIn.getFromUserName()+";"+messageIn.getToUserName()+";"+messageIn.getCreateTime()+";"+messageIn.getMsgID()+";"+messageIn.getStatus()+";");
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setFuncFlag("0");
			messageOut.setContent("null");
			return messageOut;
		}
		// 根据key做响应的处理
		if (messageIn.getEvent().equals(Message.MSG_EVENT_CLICK)) {
			if (messageIn.getEventKey().equals("pinpaigushi")) {// 品牌故事
				return story(messageIn, business);
			} else if (messageIn.getEventKey().equals("youhui")) {// 优惠
				return preference(messageIn, business);
			} else if (messageIn.getEventKey().equals("mendian")) {// 门店
				return subbranch(messageIn, business);
			} else if (messageIn.getEventKey().equals("caipin")) {// 菜品
				return dishes(messageIn, business);
			} else if (messageIn.getEventKey().equals("jiaruhuiyuan")) {// 加入会员
				return join(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("huiyuanquanyi")) {// 会员权益
				return memberRights(messageIn, business);
			} else if (messageIn.getEventKey().equals("wodezhanghu")) {// 我的帐户
				return myAccount(messageIn, business, member);
			} else if (messageIn.getEventKey().equals("huiyuanziliao")) {// 会员资料
				return memberInfo(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("wodeyouhui")) {// 我的优惠
				return query(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("shengrilibao")) {// 生日礼包
				return memberInfo(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("yijianshangwang")) {// 一键上网
				return messageClickVerifyService.message(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("bangdingshitika")) {// 绑定实体卡
				return cardBound(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("zhongjiangjilu")) { // 中奖记录
				return luckRecord(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("bindweixinmember")) { // 绑定微信会员
				return bindWeixinMember(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("mendianwifi")) { // 门店wifi
				return merchantWifi(messageIn, member, business);
			} else if (messageIn.getEventKey().equals("weixinmall")) { // 微信商城
				return weixinMall(messageIn, member, business);
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
					member = new MemberVO();
					member.setBrandId(business.getBrandId());
					member.setWeixinId(messageIn.getFromUserName());
					member.setIsMember(false);
					memberDao.insertMember(member);
					member = memberDao.getMember(messageIn.getFromUserName(), business.getBrandId());

					Message messageOut = new Message();
					messageOut.setToUserName(messageIn.getFromUserName());
					messageOut.setFromUserName(messageIn.getToUserName());
					messageOut.setCreateTime(messageIn.getCreateTime());
					messageOut.setMsgType(Message.MSG_TYPE_TEXT);
					messageOut.setFuncFlag("0");
					messageOut.setContent("<a href=\"" + serverIp + path + "/weixin/phonePage/registerPage.do?brandId="
							+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "\">亲,该活动需完善会员资料,点击这里</a>");

					return messageOut;
				}
				// 要判断信息是否完整,姓名 性别 生日 手机号
				if (member != null && !member.getIsMember() || member != null && member.getIsMember()
						&& member.getPhoneNo() == "" || member != null && member.getIsMember() && member.getPhoneNo() == null
						|| member != null && member.getIsMember() && member.getPhoneNo().length() < 11 || member != null
						&& member.getIsMember() && !isMobileNO(member.getPhoneNo()) || member.getIsMember()
						&& member.getBirthday() == null || member.getIsMember() && member.getName() == null
						|| member.getIsMember() && member.getName().equals("") || member.getIsMember()
						&& member.getGender() == null) {
					Message messageOut = new Message();
					messageOut.setToUserName(messageIn.getFromUserName());
					messageOut.setFromUserName(messageIn.getToUserName());
					messageOut.setCreateTime(messageIn.getCreateTime());
					messageOut.setMsgType(Message.MSG_TYPE_TEXT);
					messageOut.setFuncFlag("0");
					messageOut.setContent("<a href=\"" + serverIp + path + "/weixin/phonePage/improveMembership.do?brandId="
							+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "\">亲,该活动需完善会员资料,点击这里</a>");
					return messageOut;
				}
				return activityService.executeAutoreply(messageIn, business, autoreply, path, member);

			} else if (autoreply != null) {// news text
				return activityService.executeAutoreply(messageIn, business, autoreply, path, member);
			} else { // autoreply==null空时
				Message messageOut = new Message();
				messageOut.setToUserName(messageIn.getFromUserName());
				messageOut.setFromUserName(messageIn.getToUserName());
				messageOut.setCreateTime(messageIn.getCreateTime());
				messageOut.setMsgType(Message.MSG_TYPE_TEXT);
				messageOut.setFuncFlag("0");
				messageOut.setContent("null");
				return messageOut;
			}

		} else {
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setFuncFlag("0");
			messageOut.setContent("null");
			return messageOut;
		}

	}

	static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	static boolean isNum(String input) {

		Pattern p = Pattern.compile("^[0-9_]+$");
		Matcher m = p.matcher(input);
		return m.matches();
	}

	@Override
	public void eventRecord(EventRecordVO eventRecord) {
		eventRecordDao.insertEventRecord(eventRecord);

	}

	@Override
	// 发送验证码
	public String sendIdentifyingCode(String phoneNo, Integer brandId, String weixinId, String title) {
		Random r = new Random();
		String identifyingCode = String.valueOf(r.nextInt(999));
		while (identifyingCode.length() != 3) {
			identifyingCode = String.valueOf(r.nextInt(999));
		}
		log.info("验证码" + identifyingCode);
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

	/**
	 * 通过验证码注册会员
	 * @Title identifyingAndRegister
	 * @Description 
	 * @param member
	 * @param identifyingCode
	 * @return
	 * @see com.yazuo.weixin.service.RegisterPageService#identifyingAndRegister(com.yazuo.weixin.vo.MemberVO, java.lang.String)
	 */
	public String identifyingAndRegister(MemberVO member, String identifyingCode, BusinessVO business) {
		JSONObject tempJson = new JSONObject();
		
		// 查询验证码信息
		IdentifyinginfoVO identifyinginfo = memberDao.getLastIdentifyinginfoByWeixinId(member.getWeixinId(), member.getBrandId());
		if (identifyinginfo == null// 属于重新获得验证码一类
				|| identifyinginfo.getIdentifyingCode() != null
				&& identifyinginfo.getIdentifyingCode() == null
				|| identifyinginfo.getIdentifyingCode() != null && identifyinginfo.getIdentifyingCode().equals("")) {
			tempJson.put("status", "NoIdentify");
			return tempJson.toString();// 未申请验证
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 1800 * 1000);
		if (!identifyinginfo.getIdentifyingCode().equals(identifyingCode.trim())) {// 验证码错误，请重新申请验证
			tempJson.put("status", "identifyError");
			return tempJson.toString();
		}
		if (identifyinginfo.getIdentifyingTime().before(timestamp)) {//
			// 验证码过期，请重新申请验证
			tempJson.put("status", "identifyExpire");
			return tempJson.toString();
		}
		Map<String, Object> resultMap =register(identifyinginfo.getPhoneNo(), member.getWeixinId(), member, business);
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * 不通过验证码注册会员
	 * @Title identifyingAndRegisterWithoutCode
	 * @Description 
	 * @param member
	 * @param phoneNo
	 * @return
	 * @see com.yazuo.weixin.service.RegisterPageService#identifyingAndRegisterWithoutCode(com.yazuo.weixin.vo.MemberVO, java.lang.String)
	 */
	public String identifyingAndRegisterWithoutCode(MemberVO member, String phoneNo, BusinessVO business) {
		Map<String, Object> resultMap =register(phoneNo, member.getWeixinId(), member, business);
		return JSONObject.fromObject(resultMap).toString();
	}

	/**
	 * update 2014-01-08 修改会员保存不成功bug
	 * 
	 * @param mobile
	 * @param weixinId
	 * @param member
	 * @param brandId
	 * @return
	 */
	Map<String, Object> register(String mobile, String weixinId, MemberVO member, BusinessVO bus) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询商户的标签ID
		Integer brandId = member.getBrandId();
//		BusinessVO bus = businessDao.getBusinessByBrandId(merchantId);
		if (bus == null) {
			log.info("未查询到business信息");
			resultMap.put("status","请到实体店办理");
			return resultMap;
		}
		Integer tagId = bus.getTagId();
		String tagIdStr = "";
		if (tagId != null) {
			tagIdStr = tagId.toString();
		}
		String source=Constant.MEMBERSOURCE_5.toString();//默认数据来源5
		if(member.getDataSource()!=null){
			source=member.getDataSource().toString();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String json = "";
		if (member.getBirthday() == null) {
			json = "{\"mobile\":\"" + mobile + "\",\"merchantId\":" + String.valueOf(brandId.intValue()) + ",\"name\":\""
					+ member.getName() + "\",\"gender\":" + String.valueOf(member.getGender().intValue())
					+ ",\"micromessage\":true,\"microMessageId\":\"" + weixinId + "\",\"tagId\":\"" + tagIdStr + "\",\"source\":\"" + source + "\"}";
		} else {
			String birthType=member.getBirthType();
			if(StringUtils.isEmpty(birthType)){
				birthType="1";
			}
			json = "{\"mobile\":\"" + mobile + "\",\"merchantId\":" + String.valueOf(brandId.intValue()) + ",\"name\":\""
					+ member.getName() + "\",\"gender\":" + String.valueOf(member.getGender().intValue())
					+ ",\"micromessage\":true,\"microMessageId\":\"" + weixinId + "\",\"birthday\":\""
					+ sdf.format(member.getBirthday()) + "\",\"birthType\":\""+birthType+"\",\"tagId\":\"" + tagIdStr + "\",\"source\":\"" + source + "\"}";
		}

		/*2014-10-19 创建会员之前，判断是不是老会员*/
		boolean oldMemberFlag=false;
		try {
			String result = "";
			String input = "{'merchantId':"+brandId+",'mobile':"+mobile+"}";
			//判断是否老会员
			result = CommonUtil.postSendMessage(queryMemberByPhoneNo, input,  Constant.KEY+"");
			JSONObject requestObject = JSONObject.fromObject(result);
			oldMemberFlag = requestObject!=null ? requestObject.getJSONObject("data").getJSONObject("result").getBoolean("success"): false;
			resultMap.put("oldMemberFlag", oldMemberFlag);
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		
		log.info("请求创建会员" + json);
		JSONObject jo = null;
		try {
			// 调用会员接口耗時
			long timeStart = System.currentTimeMillis();
			jo = registerMircoMessageMembership(json);
			log.info("--RegisterPageServiceImpl访问添加会员接口...耗时::" + (System.currentTimeMillis() - timeStart) + "毫秒");
		} catch (Exception e) {
			log.error("调用注册会员接口失败" + e.getMessage());
			e.printStackTrace();
		}
		if (jo == null) {

			// log.info("会员注册失败,请重新申请手机验证");
			// return "registerError";
			log.info("请到实体店办理，jo为null");
			resultMap.put("status", "error");
			return resultMap;
		}
		log.info("创建会员返回" + jo.toString());
		boolean flag = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");

		if (flag) {
			Integer membershipId = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getInt("id");
			String cardNo = jo.getJSONObject("data").getJSONObject("result").getString("card");
			JSONObject cardvo=jo.getJSONObject("data").getJSONObject("result").getJSONObject("cardVo");
			if(cardvo!=null&& !StringUtil.isNullOrEmpty(cardvo.getString("cardtypeId"))){
				Integer cardtypeId = cardvo.getInt("cardtypeId");
				resultMap.put("cardtypeId", cardtypeId.toString());
			}else{
				resultMap.put("cardtypeId", "");
			}
			double storeBalance=0;
			double integralAvailable=0;
			if(cardvo!=null&&!StringUtil.isNullOrEmpty(cardvo.getString("storeBalance"))){
				storeBalance  = cardvo.getDouble("storeBalance");
			}
			if(cardvo!=null&&!StringUtil.isNullOrEmpty(cardvo.getString("integralAvailable"))){
				integralAvailable=cardvo.getDouble("integralAvailable");
			}
			resultMap.put("cardNo", cardNo);
			resultMap.put("storeBalance", storeBalance);
			resultMap.put("integralAvailable", integralAvailable);
			String phoneNo = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("mobile");
			resultMap.put("membershipId", membershipId);
			resultMap.put("phoneNo", phoneNo);
			if (member.getBirthday() != null) {
				String birthday = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership")
						.getString("birthday");
				try {
					if (!StringUtils.isBlank(birthday) && DateUtil.isDate(birthday)) {
						member.setBirthday(sdf.parse(birthday));
					}
				} catch (ParseException e) {
					log.error(e.getMessage());
					resultMap.put("status", "birthParseError");
					return resultMap;
				}
			}

			String name = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("name");
			// 性别
			Integer gender = null;
			String genderStr = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("gender");

			if (!StringUtils.isBlank(genderStr) && !genderStr.equals("null")) {
				gender = Integer.parseInt(genderStr);
			}
			/*---------------分域------begin----------------------------*/
			MemberVO memberScope = new MemberVO();
			memberScope.setMembershipId(membershipId);
			memberScope.setBrandId(brandId);
			memberScope.setStatus(1);
			memberScope.setIsMember(true);
			if (member.getDataSource()!=null && member.getDataSource().intValue() == Constant.MEMBERSOURCE_13.intValue()) {
				memberScope.setDataSource(Constant.MEMBER_SCOPE_SOURCE_3); //微信wifi加会员
			} else if (member.getDataSource()!=null && member.getDataSource().intValue() == Constant.MEMBER_SCOPE_SOURCE_2) {
				memberScope.setDataSource(Constant.MEMBER_SCOPE_SOURCE_2); // 微信第三方加会员
			} else {
				memberScope.setDataSource(Constant.MEMBER_SCOPE_SOURCE_1); // 微信加会员
			}
			memberScope.setWeixinId(weixinId);
			memberInfoService.saveMemberScopeInfo(memberScope); // 返回结果新不处理
			/*---------------分域------end----------------------------*/
			
			/*------------wifi------送抽奖机会---------------------*/
			JSONObject luckobj = jo.getJSONObject("data").getJSONObject("result").getJSONObject("activeMap");
			if(luckobj!=null ){
				
				String lunckCount="0.00";//抽奖机会数
				String couponIdStr="";
				if(oldMemberFlag){
					lunckCount = luckobj.get("4016_7")+""; // 赠送的抽奖机会数
				}else{
					lunckCount = luckobj.get("0000_7")+""; // 赠送的抽奖机会数
					couponIdStr = luckobj.get("0000_3")+""; // 赠送劵id
				}
				resultMap.put("couponIdStr", couponIdStr);
				resultMap.put("lunckCount", lunckCount);
			}
			
			if (tagId!=null) { // tagId不为空的时候调用打标签服务
				// 调用[会员打标签服务]
				try {
					saveOrModifyMembershipTagService.saveMembershipTagService(tagId, membershipId);
				} catch (Exception e) {
					log.error("[会员打标签服务]调用失败");
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}

			// 如果此时生日为空，则根据membershipId找寻相关电子会员的生日记录
			if (member.getBirthday() == null) {
				List<MemberVO> memberList = memberDao.getMemberListByMembershipId(membershipId);
				if (memberList != null) {
					for (int i = 0; i < memberList.size(); i++) {
						MemberVO m = memberList.get(i);
						if (m.getBirthday() != null) {
							member.setBirthday(m.getBirthday());
							break;
						}
					}
				}
			}

			member.setName(name);
			member.setGender(gender);
			member.setMembershipId(membershipId);
			member.setCardNo(cardNo);
			member.setPhoneNo(phoneNo);
			member.setIsMember(true);
			MemberVO SubscribeMember = memberDao.getMember(weixinId, brandId);
			boolean saveFlag = memberDao// 新入口更新的sql语句
					.updateMemberPageInfo(member, SubscribeMember.getPhoneNo());
			if (!saveFlag) {
				resultMap.put("status", "saveFailure");
				return resultMap;
			}

			// 根据membershipId批量修改该会员的相关电子会员信息
			memberDao.batchUpdateMemberPageInfo(member);

			log.info("register会员保存用户成功：" + member.toString());
			resultMap.put("status", "success");
			return resultMap;

		} else {// 请到实体店办理
			resultMap.put("status", "error");
			return resultMap;
		}
	}

	/**
	 * createBy gaoshan 会员卡绑定时，验证码校验，并注册会员
	 */
	public String identifyingAndRegisterOfCardBound(MemberVO member, String identifyingCode, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询验证码信息
		IdentifyinginfoVO identifyinginfo = memberDao.getLastIdentifyinginfoByWeixinId(member.getWeixinId(), member.getBrandId());
		if (identifyinginfo == null// 属于重新获得验证码一类
				|| identifyinginfo.getIdentifyingCode() != null
				&& identifyinginfo.getIdentifyingCode() == null
				|| identifyinginfo.getIdentifyingCode() != null && identifyinginfo.getIdentifyingCode().equals("")) {
			resultMap.put("cardBoundStatus", "NoIdentify");
			return JSONObject.fromObject(resultMap).toString();// 未申请验证
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 1800 * 1000);
		if (!identifyinginfo.getIdentifyingCode().equals(identifyingCode.trim())) {// 验证码错误，请重新申请验证
			resultMap.put("cardBoundStatus", "identifyError");
			return JSONObject.fromObject(resultMap).toString();
		}
		if (identifyinginfo.getIdentifyingTime().before(timestamp)) {//
			// 验证码过期，请重新申请验证
			resultMap.put("cardBoundStatus", "identifyExpire");
			return JSONObject.fromObject(resultMap).toString();
		}
		resultMap = registerOfcardBound(identifyinginfo.getPhoneNo(), member.getWeixinId(), member, member.getBrandId(), params);
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * createBy gaoshan 会员卡绑定时，注册用户成为会员，不通过验证码注册
	 */
	public String identifyingAndRegisterOfCardBoundWithoutCode(MemberVO member, String phoneNo, Map<String, Object> params) {
		Map<String, Object> resultMap = registerOfcardBound(phoneNo, member.getWeixinId(), member, member.getBrandId(), params);
		return JSONObject.fromObject(resultMap).toString();
	}

	/**
	 * createBy gaoshan 会员卡绑定时，注册会员
	 * 
	 * @param mobile
	 * @param weixinId
	 * @param member
	 * @param brandId
	 * @param params
	 * @return
	 */
	Map<String, Object> registerOfcardBound(String mobile, String weixinId, MemberVO member, Integer brandId, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询商户的标签ID
		Integer merchantId = brandId;
		BusinessVO bus = businessDao.getBusinessByBrandId(merchantId);
		if (bus == null) {
			log.info("未查询到business信息");
			resultMap.put("cardBoundStatus", "请到实体店办理");
			return resultMap;
		}
		Integer tagId = bus.getTagId();
		String tagIdStr = "";
		if (tagId != null) {
			tagIdStr = tagId.toString();
		}

		String cardNum = (String) params.get("cardNum");
		String secCode = (String) params.get("secCode");
		String userPwd = (String) params.get("userPwd");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		params.put("cardNo", cardNum);
		params.put("merchantId", merchantId);
		params.put("securityCode", secCode);
		params.put("mobile", mobile);
		params.put("name", member.getName());
		params.put("microMessageId", weixinId);
		params.put("gender", String.valueOf(member.getGender().intValue()));
		params.put("birthType",member.getBirthType());
		params.put("birthday", member.getBirthday() == null ? "" : sdf.format(member.getBirthday()));
		params.put("password", userPwd);

		// 调用crmapi的[绑定会员手机号和实体卡卡号BindingMemberAndCard]接口
		JSONObject jo = null;
		try {
			// 调用会员接口耗時
			long timeStart = System.currentTimeMillis();
			jo = bindingMemberAndCardService.bindingMemberAndCardService(params);
			log.info("--RegisterPageServiceImpl访问会员卡绑定接口...耗时:" + (System.currentTimeMillis() - timeStart) + "毫秒");
		} catch (Exception e) {
			log.error("调用会员卡绑定失败" + e.getMessage());
			e.printStackTrace();
		}
		if (jo == null) {
			log.info("请到实体店办理，jo为null");
			resultMap.put("cardBoundStatus", "error");
			return resultMap;
		}
		boolean flag = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");

		if (flag) {
			Integer membershipId = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getInt("id");
			String cardNo = jo.getJSONObject("data").getJSONObject("result").getString("cardNo");
			JSONObject cardvo=jo.getJSONObject("data").getJSONObject("result").getJSONObject("cardVo");
			if(cardvo!=null&& !StringUtil.isNullOrEmpty(cardvo.getString("cardtypeId"))){
				Integer cardtypeId = cardvo.getInt("cardtypeId");
				resultMap.put("cardtypeId", cardtypeId.toString());
			}else{
				resultMap.put("cardtypeId", "");
			}
			double storeBalance=0;
			double integralAvailable=0;
			if(cardvo!=null&&!StringUtil.isNullOrEmpty(cardvo.getString("storeBalance"))){
				storeBalance  = cardvo.getDouble("storeBalance");
			}
			if(cardvo!=null&&!StringUtil.isNullOrEmpty(cardvo.getString("integralAvailable"))){
				integralAvailable=cardvo.getDouble("integralAvailable");
			}
			resultMap.put("cardNo", cardNo);
			resultMap.put("storeBalance", storeBalance);
			resultMap.put("integralAvailable", integralAvailable);
			String phoneNo = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("mobile");
			resultMap.put("phoneNo", phoneNo);
			// 绑卡成功将weixin_mall_order_card" 的 card_status更新为1即绑卡成功
			int count = wxMallOrderCouponStateDao.updateMallOrderStatus(cardNo);
			log.info("------------------------更新绑定状态的返回结果：" + (count > 0 ? "成功" : "失败"));
			//  取办卡赠送抽奖数量或者劵
			JSONObject luckobj = jo.getJSONObject("data").getJSONObject("result").getJSONObject("activeMap");
			String lunckCount = luckobj.get("4015_7")+""; // 赠送的抽奖机会数
			String couponIdStr = luckobj.get("4015_3")+""; // 赠送劵id
			resultMap.put("lunckCount", lunckCount);
			resultMap.put("couponIdStr", couponIdStr);
			
			resultMap.put("luckSuccess", true);
			if (member.getBirthday() != null) {
				String birthday = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("birthday");
				try {
					if (!StringUtils.isBlank(birthday) && DateUtil.isDate(birthday)) {
						member.setBirthday(sdf.parse(birthday));
					}
				} catch (ParseException e) {
					log.error(e.getMessage());
					e.printStackTrace();
					resultMap.put("cardBoundStatus", "birthParseError");
					return resultMap;
				}
			}
		
			/*---------------分域------begin----------------------------*/
			if (!member.getIsMember()) { // 非会员
				MemberVO memberScope = new MemberVO();
				memberScope.setMembershipId(membershipId);
				memberScope.setBrandId(brandId);
				memberScope.setDataSource(Constant.MEMBER_SCOPE_SOURCE_1); // 微信加会员
				memberScope.setWeixinId(weixinId);
				memberScope.setStatus(1);
				memberScope.setIsMember(true);
				memberInfoService.saveMemberScopeInfo(memberScope); // 返回结果新不处理
			}
			/*---------------分域------end----------------------------*/

			String name = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("name");
			Integer gender = null;// 性别
			String genderStr = jo.getJSONObject("data").getJSONObject("result").getJSONObject("membership").getString("gender");

			if (!StringUtils.isBlank(genderStr) && !genderStr.equals("null")) {
				gender = Integer.parseInt(genderStr);
			}
			if (tagId!=null) { // tagId不为空的时候调用打标签服务
				// 调用[会员打标签服务]
				try {
					saveOrModifyMembershipTagService.saveMembershipTagService(tagId, membershipId);
				} catch (Exception e) {
					log.error("[会员打标签服务]调用失败");
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}

			// 如果此时生日为空，则根据membershipId找寻相关电子会员的生日记录
			if (member.getBirthday() == null) {
				List<MemberVO> memberList = memberDao.getMemberListByMembershipId(membershipId);
				if (memberList != null) {
					for (int i = 0; i < memberList.size(); i++) {
						MemberVO m = memberList.get(i);
						if (m.getBirthday() != null) {
							member.setBirthday(m.getBirthday());
							break;
						}
					}
				}
			}

			member.setName(name);
			member.setGender(gender);
			member.setMembershipId(membershipId);
			member.setCardNo(cardNo);
			member.setPhoneNo(phoneNo);
			member.setIsMember(true);
			MemberVO SubscribeMember = memberDao.getMember(weixinId, brandId);
			boolean saveFlag = memberDao// 新入口更新的sql语句
					.updateMemberPageInfo(member, SubscribeMember.getPhoneNo());
			if (!saveFlag) {
				resultMap.put("cardBoundStatus", "saveFailure");
				return resultMap;// 保存失败
			}

			// 根据membershipId批量修改该会员的相关电子会员信息
			memberDao.batchUpdateMemberPageInfo(member);

			log.info("register，会员卡绑定，保存用户成功：" + member.toString());
			resultMap.put("cardBoundStatus", "success");
			return resultMap;// 保存用户成功

		} else {// 请到实体店办理
			String error = jo.getJSONObject("data").getJSONObject("result").getString("message");
			log.info("实体会员卡绑定业务异常==" + error);
			resultMap.put("cardBoundStatus", error);
			return resultMap;
			// return "error";
		}
	}

	public JSONObject registerMircoMessageMembership(String json) throws Exception {
		json = URLEncoder.encode(json, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = registerMircoMessageMembership + "?ciphertext=" + json + "&key=" + Constant.KEY + "&micromessage=true";
		log.info("创建会员访问地址：" + url);
		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);
			log.info("创建会员返回:"+requestObject.toString());
			return requestObject;
		} else {
			return null;
		}
	}

	public String modifyMemberInfo(String birthday, MemberVO member, BusinessVO business) {// 修改会员的信息

		// if (member.getUpdateTimes().intValue() > 2) {//超过容许的修改次数，暂时无法再次更改
		// return "beyond";
		// }
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM月dd日");
		String date = "";
		String cDate = "";
		String validateDate = "";
		if (!StringUtil.isNullOrEmpty(birthday)) {
			try {
				validateDate = sdf1.format(sdf1.parse(birthday.trim()));
				date = sdf2.format(sdf1.parse(birthday.trim()));
				cDate = sdf3.format(sdf1.parse(birthday.trim()));
				member.setBirthday(sdf1.parse(birthday.trim()));// 生日信息
			} catch (ParseException e1) {
				log.error("生日格式错误--" + birthday + e1.getMessage());
				return "parseBirError";
			}
			if (!validateDate.equals(birthday)) {
				log.error("生日格式错误--" + birthday);
				return "parseBirError";
			}
		}
		if (member.getIsMember()) {
			String birthType=member.getBirthType();
			if(StringUtils.isEmpty(birthType)){
				birthType="1";
			}
			String json = "{\"id\":\"" + member.getMembershipId() + "\",\"birthday\":\"" + date + "\",\"name\":\""
					+ member.getName() + "\",\"gender\":" + member.getGender() + ",\"birthType\":"+birthType+",\"mobile\":\""+member.getPhoneNo()+"\"}";
			log.info("修改会员信息发送" + json);
			JSONObject jo = null;
			try {
				jo = modifyMember(json);
				if (jo == null) {
					return "modifyFailure";
				}
			} catch (Exception e) {
				log.error(json + e.getMessage());
				e.printStackTrace();
			}
			log.info("修改会员信息返回" + jo.toString());
			boolean flag = jo.getBoolean("success");
			if (flag) {

				// 调用[会员打标签服务]
				Integer tagId = business!=null ? business.getTagId():null;
				Integer membershipId = member.getMembershipId();
				if (tagId!=null) { // tagId不为空的时候调用打标签服务
					try {
						saveOrModifyMembershipTagService.saveMembershipTagService(tagId, membershipId);
					} catch (Exception e) {
						log.error("[会员打标签服务]调用失败");
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}

				// 如果此时生日为空，则根据membershipId找寻相关电子会员的生日记录
				if (member.getBirthday() == null) {
					List<MemberVO> memberList = memberDao.getMemberListByMembershipId(membershipId);
					if (memberList != null) {
						for (int i = 0; i < memberList.size(); i++) {
							MemberVO m = memberList.get(i);
							if (m.getBirthday() != null) {
								member.setBirthday(m.getBirthday());
								break;
							}
						}
					}
				}

				log.info("修改成功");
				MemberVO SubscribeMember = memberDao.getMember(member.getWeixinId(), business.getBrandId());// 这一步是为了判断这个用户进入新入口前是否用手机投过票，没有update_times不用加1
				boolean saveFlag = memberDao.updateMemberPageInfo(member, SubscribeMember.getPhoneNo());

				if (!saveFlag) {
					return "error";// 修改失败
				}

				// 根据membershipId批量修改该会员的相关电子会员信息
				memberDao.batchUpdateMemberPageInfo(member);
				return "success";
			} else {
				return "error";
			}
		} else {// 您还不是会员哦，请先输入手机号成为会员，再进行会员相关操作~
			String info = "noMembership";
			return info;
		}

	}

	public JSONObject modifyMember(String json) throws Exception {//
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

	// 品牌故事
	public Message story(Message messageIn, BusinessVO business) {
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
		
		if (!StringUtil.isNullOrEmpty(story.getImageName()) && story.getImageName().startsWith("group1/M00/")) {
			message1.setPicUrl(dfsFilePath+story.getImageName());
		} else {
		message1.setPicUrl(serverIp + path
				+ weixinPhonePageServiceImpl.getNewImageUrl(business.getBrandId(), story.getImageName()));
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

	// 优惠
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

	// 分店
	public Message subbranch(Message messageIn, BusinessVO business) {
		//自定义回复的维护的图片及title
		Map<String, Object> autoreplyMap = weixinManageService.getSettingAutoreplyByMenuKey(business.getBrandId(), "mendian");
		Message newsMessageOut = new Message();
		newsMessageOut.setTitle(business.getTitle());
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		// 此图片是动态设置时，从图片服务器上取
		if (autoreplyMap !=null && autoreplyMap.size()>0) {
			message1.setTitle(autoreplyMap.get("reply_title")+"");
			message1.setDescription(autoreplyMap.get("descripation")+"");
		} else {
			message1.setTitle(business.getTitle());
			message1.setDescription("点击链接查看全部店面；\n输入位置查询附近店面，具体操作请参见上图");
		}
		if (business.getBrandId().intValue() == buli_brandid.intValue()) {// 表示是布力布力
			message1.setPicUrl(serverIp + path + "/imageBuli/weizhi.jpg");
		} else {
			// 此图片是动态设置时，从图片服务器上取
			if (autoreplyMap !=null && autoreplyMap.size()>0) {
				message1.setPicUrl(dfsFilePath + autoreplyMap.get("image_name"));
			} else {
				message1.setPicUrl(serverIp + path + "/images/weizhi.jpg");
			}
		}

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

	// 菜品
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
		sb.append("\n点击查看" + business.getTitle() + "<a href=\"" + serverIp + path + "/weixin/newFun/dishes.do?brandId="
				+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "&r="+Math.random()+"\">菜品信息</a>");
		sb.append(REPLYINFO);
		message.setContent(sb.toString());

		return message;
	}

	// 加入会员
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
				info = card.getStatusInfo();// 当前用户无正常状态的卡时，出
			} else {
				info = "您已经是粉丝会员了。\n您的卡是：粉丝会员卡\n开卡日期：" + sdf.format(card.getActiveDate()) + "\n<a href=\"" + serverIp + path
						+ "/weixin/phonePage/fensiCard.do?brandId=" + business.getBrandId() + "&weixinId="
						+ messageIn.getFromUserName() + "\">点击查看会员卡</a>";
			}

			message.setContent(info);
			log.info(info);
			return message;
		}

		info = "<a href=\"" + serverIp + path + "/weixin/phonePage/registerPage.do?brandId=" + business.getBrandId()
				+ "&weixinId=" + messageIn.getFromUserName() + "\">亲,加入会员点击这里</a>";
		message.setContent(info);
		log.info(info);
		return message;

	}
	
	// 绑定微信会员
	public Message bindWeixinMember(Message messageIn, MemberVO member, BusinessVO business) {
		Message message = new Message();
		message.setToUserName(messageIn.getFromUserName());
		message.setFromUserName(messageIn.getToUserName());
		message.setCreateTime(messageIn.getCreateTime());
		message.setMsgType(Message.MSG_TYPE_TEXT);
		message.setFuncFlag("0");
		String info = "";

		if (member.getIsMember()) { // 是微信会员
				info = "您已经绑定过微信。\n<a href=\"" + serverIp + path
						+ "/weixin/phonePage/fensiCard.do?brandId=" + business.getBrandId() + "&weixinId="
						+ messageIn.getFromUserName()+ "\">点击进入会员中心</a>";
			message.setContent(info);
			log.info(info);
			return message;
		}
		
		info = "<a href=\"" + serverIp + path + "/bindOldMember/validatePage.do?brandId=" + business.getBrandId()
				+ "&weixinId=" + messageIn.getFromUserName() + "\">亲,点击绑定微信</a>";
		message.setContent(info);
		log.info(info);
		return message;
	}
	
	// 门店wifi
	public Message merchantWifi(Message messageIn, MemberVO member, BusinessVO business) {
		Message newsMessageOut = new Message();
		newsMessageOut.setTitle(business.getTitle());
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		message1.setTitle(business.getTitle());
		if (!member.getIsMember()) {
			message1.setDescription("您还不是我们的会员，点击加入粉丝会员后，即可连接wifi。");
			message1.setPicUrl(serverIp + path + "/images/no_member.png");
			message1.setUrl(serverIp + path + "/weixin/phonePage/registerPage.do?brandId=" + business.getBrandId()
					+ "&weixinId=" + messageIn.getFromUserName() + "&source="+Constant.MEMBERSOURCE_13);
		}else{
			message1.setDescription("您已经是我们的会员，查看粉丝会员。");
			message1.setPicUrl("http://report.yazuo.com/image/failed.png");//区浩博提供的图片url
			message1.setUrl(serverIp + path + "/weixin/phonePage/fensiCard.do?brandId=" + business.getBrandId() + "&weixinId="
					+ messageIn.getFromUserName());
		}
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
	//进入微信商城
		public Message weixinMall(Message messageIn, MemberVO member, BusinessVO business) {
			WxMallMerchantDict dict = weixinMallMartService.queryMerchantParamByDB(business.getBrandId());
			Message newsMessageOut = new Message();
			newsMessageOut.setTitle(business.getTitle());
			List<Message> articles = new ArrayList<Message>();
			Message message1 = new Message();
			message1.setTitle("点击进入微信商城的世界");
			if(!StringUtil.isNullOrEmpty(dict.getPagePicurl())){
				if(dict.getPagePicurl().startsWith("M00/")){
					message1.setPicUrl(newPictureUrl+dict.getPagePicurl());
				}else{
					message1.setPicUrl(caFileIp+dict.getPagePicurl());
				}
			}
			message1.setUrl(serverIp + path + "/weixin/"+business.getBrandId()+"/mallMartIndex.do?weixinId="+ messageIn.getFromUserName());
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
	// 中奖记录菜单
	public Message luckRecord(Message messageIn, MemberVO member, BusinessVO business) {
		Message message = new Message();
		message.setToUserName(messageIn.getFromUserName());
		message.setFromUserName(messageIn.getToUserName());
		message.setCreateTime(messageIn.getCreateTime());
		message.setMsgType(Message.MSG_TYPE_TEXT);
		message.setFuncFlag("0");
		String info = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		info = "<a href=\"" + serverIp + path + "/caffe/cardLottery/lookLotteryRecord.do?brandId=" + business.getBrandId()
				+ "&weixinId=" + messageIn.getFromUserName() + "\">点击查看中奖记录</a>";
		message.setContent(info);
		log.info(info);
		return message;

	}

	// 绑定实体卡
	public Message cardBound(Message messageIn, MemberVO member, BusinessVO business) {
		Message message = new Message();
		message.setToUserName(messageIn.getFromUserName());
		message.setFromUserName(messageIn.getToUserName());
		message.setCreateTime(messageIn.getCreateTime());
		message.setMsgType(Message.MSG_TYPE_TEXT);
		message.setFuncFlag("0");
		String info = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		info = "<a href=\"" + serverIp + path + "/weixin/phonePage/cardBoundPage.do?brandId=" + business.getBrandId()
				+ "&weixinId=" + messageIn.getFromUserName() + "\">亲,绑定实体卡点击这里</a>";
		message.setContent(info);
		log.info(info);
		return message;
	}

	/**
	 * 注册页面的 当返回信息是卡失效，查别的有效的卡，通过手机号差别的有效的卡，通过在调用卡查询接口去返回值，更新默认membership的卡号
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
		card.setStoreBalance(StringUtil.isNullOrEmpty(jo.getString("store")) ? new BigDecimal(0): new BigDecimal(jo.getString("store")));
		card.setActiveDate(new Date(new Long(jo.getJSONObject("activate").getString("time"))));
		return card;
	}

	public JSONObject queryCard(String json) throws Exception {
		log.info("请求查询卡" + json);
		json = URLEncoder.encode(json, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = queryCard + "?ciphertext=" + json + "&key=" + Constant.KEY;
		long startTime = System.currentTimeMillis();
		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);

			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			String Alldata = URLDecoder.decode(((JSONObject) requestObject).toString(), "UTF-8");
			log.info("查询卡全部返回" + Alldata);
			// String reStr = EncryptUtil.dncrypt3DesString(secretKey, data);
			// log.info("查卡的全部信息"+requestObject.toString());
			log.info("查询卡返回~~~~" + data);
			
			String crmInterface = queryCard.substring((queryCard.lastIndexOf("/")+1),queryCard.lastIndexOf("."));
			log.info("--访用CRM【"+crmInterface+"】接口...耗时::" + (System.currentTimeMillis() - startTime) + "毫秒");
			return JSONObject.fromObject(data);
		} else {
			return null;
		}
	}

	// 会员权益
	public Message memberRights(Message messageIn, BusinessVO business) {
		Message newsMessageOut = new Message();
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		//自定义回复的维护的图片及title
		Map<String, Object> autoreplyMap = weixinManageService.getSettingAutoreplyByMenuKey(business.getBrandId(), "huiyuanquanyi");
		if (autoreplyMap !=null && autoreplyMap.size()>0) {
			message1.setTitle(autoreplyMap.get("reply_title")+"");
		} else {
			message1.setTitle("\"" + business.getTitle() + "\"粉丝会员卡");
		}
		
		
		if (business.getBrandId().intValue() == buli_brandid.intValue()) {// 表示是布力布力
			message1.setPicUrl(serverIp + path + "/imageBuli/huiyuanquanyi.jpg");
		}else if(business.getBrandId().intValue() == quanjude_brandid.intValue()){//全聚德
			message1.setPicUrl(serverIp + path + "/imageBuli/quanjudecard.jpg");
		} else {
			if (autoreplyMap !=null && autoreplyMap.size()>0) {
				message1.setPicUrl(dfsFilePath + autoreplyMap.get("image_name"));
			}else {
				message1.setPicUrl(serverIp + path + "/images/huiyuanquanyi.jpg");
			}
		}
		message1.setUrl("");
		articles.add(message1);

		Message message2 = new Message();

		if (business.getMemberRights() == null || business.getMemberRights().trim().equals("")) {//
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

	// 我的帐户
	public Message myAccount(Message messageIn, BusinessVO business, MemberVO member) {

		if (!member.getIsMember()) {
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setContent("\n<a href=\"" + serverIp + path + "/weixin/phonePage/registerPage.do?brandId="
					+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "\">亲,你还不是会员请点击这里</a>");
			messageOut.setFuncFlag("0");
			return messageOut;
		}

		Message newsMessageOut = new Message();
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		
		//自定义回复的维护的图片及title
		Map<String, Object> autoreplyMap = weixinManageService.getSettingAutoreplyByMenuKey(business.getBrandId(), "wodezhanghu");
		if (autoreplyMap !=null && autoreplyMap.size()>0) {
			message1.setTitle(autoreplyMap.get("reply_title")+"");
		} else {
			message1.setTitle("点击进入会员中心");
		}
		
		message1.setUrl(serverIp + path + "/weixin/phonePage/fensiCard.do?brandId=" + business.getBrandId() + "&weixinId="
				+ messageIn.getFromUserName());
		
		if (business.getBrandId().intValue() == buli_brandid.intValue()) {// 表示是布力布力
			message1.setPicUrl(serverIp + path + "/imageBuli/wodezhanghu.jpg");
		} else if (business.getBrandId().intValue() == Constant.CUSTOM_SETTING_BRAND) { // 表示眉州东坡集团特有的图
			message1.setPicUrl(serverIp + path + "/imageBuli/meizhou_wodezhanghu.jpg");
		} else {
			if (autoreplyMap !=null && autoreplyMap.size()>0) {
				message1.setPicUrl(dfsFilePath + autoreplyMap.get("image_name"));
			} else {
				message1.setPicUrl(serverIp + path + "/images/huiyuanquanyi.jpg");
			}
		}

		articles.add(message1);
		//是会员，读取卡列表接口，2014-08-12 add by sundongfeng 
		List<MemberCardVO> cardList = new ArrayList<MemberCardVO>();
		//读取搜索引擎会员ids
		Set<Object> set = memberShipCenterService.queryMemberShipsByMobile(member.getBrandId().toString(), member.getPhoneNo());
		//读取卡列表用搜索引擎
		cardList = memberShipCenterService.queryMemberCardList(member.getBrandId().toString(), set.toArray());
//		List<MemberCardVO> cardList = cardService.queryCardList(member.getBrandId(), member.getPhoneNo());
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

	// 会员资料 生日礼包
	public Message memberInfo(Message messageIn, MemberVO member, BusinessVO business) {

		if (!member.getIsMember()) {
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setContent("<a href=\"" + serverIp + path + "/weixin/phonePage/registerPage.do?brandId="
					+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "\">亲,你还不是会员请点击这里</a>");
			messageOut.setFuncFlag("0");
			return messageOut;
		}

		MemberVO esMember = memberShipCenterService.queryMemberByMsId(member.getBrandId()+"", member.getMembershipId()+"");
		if (esMember.getBirthday()!=null) {
			member.setBirthday(esMember.getBirthday());
		}
		
		Message message = new Message();
		message.setToUserName(messageIn.getFromUserName());
		message.setFromUserName(messageIn.getToUserName());
		message.setCreateTime(messageIn.getCreateTime());
		message.setMsgType(Message.MSG_TYPE_TEXT);
		message.setFuncFlag("0");
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM月dd日");
		if (member.getBirthday() == null) {
			message.setContent("您的生日资料还不完整，" + "\n<a href=\"" + serverIp + path + "/weixin/phonePage/registerPage.do?brandId="
					+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "\">亲,请点击这里完善</a>");
			return message;
		} else {
			message.setContent("您的生日是" + sdf3.format(member.getBirthday()) + "，请届时关注我们的店内活动~\n");
			return message;
		}
	}

	// 我的优惠
	public Message query(Message messageIn, MemberVO member, BusinessVO business) {
		if (!member.getIsMember()) {
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setContent("<a href=\"" + serverIp + path + "/weixin/phonePage/registerPage.do?brandId="
					+ business.getBrandId() + "&weixinId=" + messageIn.getFromUserName() + "\">亲,你还不是会员,点击这里</a>");
			messageOut.setFuncFlag("0");
			return messageOut;
		}

		Message newsMessageOut = new Message();
		List<Message> articles = new ArrayList<Message>();
		Message message1 = new Message();
		//自定义回复的维护的图片及title
		Map<String, Object> autoreplyMap = weixinManageService.getSettingAutoreplyByMenuKey(business.getBrandId(), "wodeyouhui");
		if (autoreplyMap !=null && autoreplyMap.size()>0) {
			message1.setTitle(autoreplyMap.get("reply_title")+"");
			message1.setPicUrl(dfsFilePath + autoreplyMap.get("image_name"));
		} else {
			message1.setTitle("点击进入会员中心");
			message1.setPicUrl(serverIp + path + "/images/huiyuanquanyi.jpg");
		}
		message1.setUrl(serverIp + path + "/weixin/phonePage/fensiCard.do?brandId=" + business.getBrandId() + "&weixinId="
				+ messageIn.getFromUserName());
		articles.add(message1);
//		List<MemberCardVO> cardList = cardService.queryCardList(member.getBrandId(), member.getMembershipId());//member.getPhoneNo());
		//是会员，读取卡列表接口，2014-08-12 add by sundongfeng 
		List<MemberCardVO> cardList = new ArrayList<MemberCardVO>();
		//读取搜索引擎会员ids
		Set<Object> set = memberShipCenterService.queryMemberShipsByMobile(member.getBrandId().toString(), member.getPhoneNo());
		//读取卡列表用搜索引擎
		cardList = memberShipCenterService.queryMemberCardList(member.getBrandId().toString(), set.toArray());
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

	// 自定义回复
	AutoreplyVO checkAutoreply(Message messageIn, List<AutoreplyVO> autoreplyList) {
		String[] ss = null;
		Collections.sort(autoreplyList);//排序，将非关键字排在最后
		for (AutoreplyVO autoreply : autoreplyList) {
			if (autoreply.getIsDelete() || (autoreply.getSpecificType()!=null && autoreply.getSpecificType().intValue()==1)) {
				continue;
			}
			// 关键字完全匹配且消息类型text
			if ("equals".equals(autoreply.getKeywordType()) && messageIn.getMsgType().equals(Message.MSG_TYPE_TEXT)
					&& messageIn.getContent().trim().equals(StringUtils.trim(autoreply.getKeyword()))) {
				return autoreply;
				// 关键字完全匹配且消息类型为event
			} else if ("equals".equals(autoreply.getKeywordType()) && messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)
					&& messageIn.getEvent().equals(Message.MSG_EVENT_CLICK)) {
				if (messageIn.getEventKey().trim().equals(StringUtils.trim(autoreply.getKeyword()))) {
					return autoreply;
				}
				// 关键字全部出现在句子内
			} else if ("like".equals(autoreply.getKeywordType()) && !messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)) {
				ss = autoreply.getKeyword().split(" ");
				for (String key : ss) {
					if (messageIn.getContent().trim().indexOf(key.trim()) != -1) {
						return autoreply;
					}
				}
			}else if((messageIn.getMsgType().equals(Message.MSG_TYPE_TEXT)
					&& !messageIn.getContent().trim().equals(autoreply.getKeyword()))
					|| (messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)
						&& messageIn.getEvent().equals(Message.MSG_EVENT_CLICK)
						&&!messageIn.getEventKey().trim().equals(autoreply.getKeyword()))
				){
				if(StringUtils.isEmpty(autoreply.getKeyword())&&StringUtils.isEmpty(autoreply.getKeywordType())){
					return autoreply;
				}
			}
		}

		return null;
	}

	// 地理位置消息处理
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
		sescription.append("\n查看大图请点击链接");
		message1.setDescription(sescription.toString());
		message1.setPicUrl("");
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

		String mylocation = "";
		String dest = "";
		String bj = "";
		try {
			mylocation = URLEncoder.encode("我的位置", "UTF-8");
			dest = URLEncoder.encode("目的地", "UTF-8");
			bj = URLEncoder.encode("北京", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		message1.setUrl("http://api.map.baidu.com/direction?origin=latlng:" + origin.toString() + "%7Cname:" + mylocation
				+ "&destination=latlng:" + destination.toString() + "%7Cname:" + dest + "&mode=driving&region=" + bj
				+ "&output=html");
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

	// ------------------未开通功能的消息处理-------------------------
	// 文字消息处理
	Message executeText(Message messageIn, BusinessVO business, MemberVO member) {
		Message messageOut = new Message();
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("感谢您的建议,留言已成功");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	// 链接消息处理
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

	// 图片消息处理
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

	// 语音消息处理
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

	// 其它消息处理
	Message executeOther(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("感谢您的建议，留言已成功！");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	/**
	 * @Title isAllowWeixinMember
	 * @Description 判断商户是否允许会员注册
	 * @param brandId
	 * @return
	 * @see com.yazuo.weixin.service.RegisterPageService#isAllowWeixinMember(java.lang.Integer)
	 */
	@Override
	public boolean isAllowWeixinMember(Integer brandId) {
		boolean isAllowWeixinMember = businessDao.getIsAllowWeixinMember(brandId).getIsAllowWeixinMember();
		return isAllowWeixinMember;
	}

}
