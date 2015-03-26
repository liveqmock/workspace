package com.yazuo.weixin.service.impl.vote;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.jms.JMSException;

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

import com.yazuo.weixin.dao.BusinessDao;
import com.yazuo.weixin.dao.EventRecordDao;
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.service.vote.VoteInterfaceForNewService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.MessageSender;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.IdentifyinginfoVO;
import com.yazuo.weixin.vo.ImageConfigVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.Message;

@Scope("prototype")
@Service
public class VoteInterfaceForNewServiceImpl extends
		AbstractVoteInterfaceService implements VoteInterfaceForNewService {
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Value("#{propertiesReader['voteMembership']}")
	private String voteMembership;
	@Value("#{propertiesReader['smsAddress']}")
	private String smsAddress;
	@Value("#{propertiesReader['smsUsername']}")
	private String smsUsername;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private EventRecordDao eventRecordDao;
	@Autowired
	private BusinessDao businessDao;
	@Resource
	private WeixinPhonePageService weixinPhonePageServiceImpl;
	Logger log = Logger.getLogger(this.getClass());

	private String path;

	@Override
	public Message message(Message messageIn, BusinessVO business, String path,
			List<AutoreplyVO> autoreplyList) {
		this.path = path;
		// 文字消息处理
		if (messageIn.getMsgType().equals(Message.MSG_TYPE_TEXT)) {
			// 自定义消息处理
			return executeTextForVoteNew(messageIn, business);
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
		// 事件消息处理 关注事件需要放到这处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)) {
			return executeEvent(messageIn, business, autoreplyList);
		}
		// 语音消息处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_VOICE)) {
			return executeVoice(messageIn, business);
		}

		// 其他消息处理
		else
			return executeother(messageIn, business);
	}

	public Message messageNew(Message messageIn, BusinessVO business,
			String path, java.util.List<AutoreplyVO> autoreplyList) {
		this.path = path;

		// 事件消息处理 关注事件需要放到这处理
		if (messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)) {
			return executeEventNew(messageIn, business, autoreplyList);
		}
		// 其他消息处理
		else {
			return executeother(messageIn, business);
		}
	};

	Message executeTextForVoteNew(Message messageIn, BusinessVO business) {

		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setFuncFlag("0");
		// 短信验证
		if (messageIn.getContent().trim().length() == 3
				&& isNum(messageIn.getContent().trim())) {
			/*boolean isVote = memberDao.getVoteCount(messageIn.getContent()
					.trim(), business.getBrandId(), new Integer(2), messageIn
					.getFromUserName()) > 0 ? true : false;*/
			if (true) {
				// 是会员
				String info = "您已经投过一票了哦，祝用餐愉快！";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}
			String info = identifyingAndVote(messageIn.getFromUserName(),
					business.getBrandId(), messageIn.getContent());
			messageOut.setContent(info);
			log.info(info);
			return messageOut;
			// 手机号申请短信验证
		} else if (messageIn.getContent().trim().length() == 11
				&& isMobileNO(messageIn.getContent().trim())) {
			// 不管是不是会员走得都是一个接口，接口只投票，不注册
			// 需要验证码，调用验证程序，验证如果通过调接口
			/*boolean isVote = memberDao.getVoteCount(messageIn.getContent()
					.trim(), business.getBrandId(), new Integer(2), messageIn
					.getFromUserName()) > 0 ? true : false;*/
			if (true) {
				String info = "您已经投过一票了哦，祝用餐愉快！";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			} else {
				// 是否需要验证码如果是true就表示要验证码（验证码数据库可配置）
				if (business.getIsNeedIdentifying()) {
					String info = "亲，请查收短信，在此回复验证码。";
					messageOut.setContent(info);
					String identifyingCode = sendIdentifyingCode(messageIn
							.getContent().trim(), business.getBrandId(),
							messageIn.getFromUserName(), business.getTitle());
					log.info(info + "-----您在" + business.getTitle() + "微信端验证码为"
							+ identifyingCode);
					return messageOut;
					// 不需要验证码，直接掉接口
				} else {
					String info = vote(messageIn.getContent().trim(),
							messageIn.getFromUserName(), business);
					messageOut.setContent(info);
					log.info(info);
					return messageOut;
				}
			}
		} else {
			messageOut.setContent("请回复手机号码参与投票~~");
			return messageOut;
		}
	}

	@Override
	public String sendIdentifyingCode(String phoneNo, Integer brandId,
			String weixinId, String title) {
		Random r = new Random();
		String identifyingCode = String.valueOf(r.nextInt(999));
		while (identifyingCode.length() != 3) {
			identifyingCode = String.valueOf(r.nextInt(999));
		}
		IdentifyinginfoVO identifyinginfo = new IdentifyinginfoVO();
		identifyinginfo.setIdentifyingCode(identifyingCode);
		identifyinginfo.setIdentifyingTime(new Timestamp(System
				.currentTimeMillis()));
		identifyinginfo.setWeixinId(weixinId);
		identifyinginfo.setPhoneNo(phoneNo);
		identifyinginfo.setBrandId(brandId);
		memberDao.insertIdentifyinginfo(identifyinginfo);

		try {
			MessageSender.sendSMS("验证码为" + identifyingCode + ",欢迎关注" + title,
					phoneNo, smsAddress, smsUsername, brandId);
			return identifyingCode;
		} catch (JMSException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}

	}

	@Override
	public String identifyingAndVote(String weixinId, Integer brandId,
			String identifyingCode) {
		BusinessVO business = businessDao.getBusinessByBrandId(brandId);

		IdentifyinginfoVO identifyinginfo = memberDao
				.getLastIdentifyinginfoByWeixinId(weixinId, brandId);
		if (identifyinginfo == null
				|| identifyinginfo.getIdentifyingCode() == null
				|| identifyinginfo.getIdentifyingCode().equals("")) {

			return "并未申请验证";
		}
		Timestamp timestamp = new Timestamp(
				System.currentTimeMillis() - 1800 * 1000);
		if (!identifyinginfo.getIdentifyingCode().equals(identifyingCode)) {
			return "验证码错误，请重新申请验证";
		}
		if (identifyinginfo.getIdentifyingTime().before(timestamp)) {
			return "验证码过期，请重新申请验证";
		}
		return vote(identifyinginfo.getPhoneNo(), weixinId, business);
	}

	String vote(String mobile, String weixinId, BusinessVO business) {
		String json = "{\"mobile\":\"" + mobile + "\",\"merchantId\":"
				+ business.getBrandId().toString()
				+ ",\"micromessage\":true,\"microMessageId\":\"" + weixinId
				+ "\",\"source\":\"5\"}";
		log.info("请求投票但不创建会员" + json);
		JSONObject jo = null;
		try {
			jo = registerMircoMessageMembership(json);
		} catch (Exception e) {
			log.error("调用投票但不创建接口失败" + e.getMessage());
			e.printStackTrace();
			return "投票成功";
		}
		if (jo == null) {
			log.info("投票失败，接口调用无返回值，但告知用户的是已成功");
			return "投票成功";
		}
		log.info("投票但不创建会员返回" + jo.toString());
		boolean flag = jo.getJSONObject("data").getJSONObject("result")
				.getBoolean("success");
		if (flag) {
			// if(business.getSalutatory().equals("不送优惠")){
			// sb.append("投票已成功，感谢您的支持与厚爱！");
			// }else{
			return business.getVoteMsg();
			// }
		} else {
			log.info("投票失败但告知用户的是已成功");
			return "投票成功";
		}
	}

	public JSONObject registerMircoMessageMembership(String json)
			throws Exception {
		json = URLEncoder.encode(json, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = voteMembership + "?ciphertext=" + json + "&key="
				+ Constant.KEY + "&micromessage=true";

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

	public void eventRecord(EventRecordVO eventRecord) {
		eventRecordDao.insertEventRecord(eventRecord);
	}

	static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	static boolean isNum(String input) {

		Pattern p = Pattern.compile("^[0-9_]+$");
		Matcher m = p.matcher(input);
		return m.matches();
	}

	Message executeImage(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("请回复手机号码参与投票~~");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	Message executeLocation(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("请回复手机号码参与投票~~");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	Message executeLink(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("请回复手机号码参与投票~~");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	Message executeEvent(Message messageIn, BusinessVO business,
			List<AutoreplyVO> autoreplyList) {

		MemberVO member = memberDao.getMember(messageIn.getFromUserName(),
				business.getBrandId());
		if (messageIn.getEvent().equals(Message.MSG_EVENT_SUBSCRIBE)) {
			Message messageOut = new Message();
			messageOut.setTitle("");
			List<Message> articles = new ArrayList<Message>();
			Message message1 = new Message();
			message1.setTitle("亲，喜欢就投我一票！");
			message1.setDescription("");
			message1.setPicUrl(serverIp + path + "/images/weixin_toupiao_1.jpg");
			message1.setUrl("");
			Message message2 = new Message();
			if (business.getSalutatory().equals("不送优惠")) {
				message2.setTitle("回复您的手机号,喜欢,就投我一票吧！");
			} else {
				message2.setTitle("回复您的手机号投我一票，您将获得" + business.getSalutatory()
						+ "。");
			}
			message2.setDescription("");
			message2.setPicUrl("");
			message2.setUrl("");
			articles.add(message1);
			articles.add(message2);
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_NEWS);
			messageOut.setFuncFlag("1");
			messageOut.setArticleCount(articles.size());
			messageOut.setArticles(articles);
			if (member == null) {
				member = new MemberVO();
				member.setBrandId(business.getBrandId());
				member.setWeixinId(messageIn.getFromUserName());
				memberDao.insertMember(member);
				return messageOut;
			} else {
				member.setIsSubscribe(true);
				memberDao.updateMemberSubscribeStatus(member);
				return messageOut;
			}

		}
		if (messageIn.getEvent().equals(Message.MSG_EVENT_UNSUBSCRIBE)) {
			if (member != null) {
				member.setIsSubscribe(false);
				memberDao.updateMemberSubscribeStatus(member);
			}
		}
		Message messageOut2 = new Message();
		messageOut2.setToUserName(messageIn.getFromUserName());
		messageOut2.setFromUserName(messageIn.getToUserName());
		messageOut2.setCreateTime(messageIn.getCreateTime());
		messageOut2.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut2.setFuncFlag("0");
		messageOut2.setContent("null");
		return messageOut2;

	}

	private Message executeEventNew(Message messageIn, BusinessVO business,
			List<AutoreplyVO> autoreplyList) {
		MemberVO member = memberDao.getMember(messageIn.getFromUserName(),
				business.getBrandId());
		if (messageIn.getEvent().equals(Message.MSG_EVENT_SUBSCRIBE)) {
			Message messageOut = new Message();
			messageOut.setTitle("");
			List<Message> articles = new ArrayList<Message>();
			Message message1 = new Message();
			message1.setTitle("");
			message1.setDescription("");
			message1.setPicUrl(serverIp + path + "/images/weixin_toupiao_1.jpg");
			message1.setUrl("");

			Message message2 = new Message();
			message2.setTitle(CONTENT_2014);
			message2.setDescription("");
			message2.setPicUrl("");
			message2.setUrl("");
			articles.add(message1);
			articles.add(message2);
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_NEWS);
			messageOut.setFuncFlag("1");
			messageOut.setArticleCount(articles.size());
			messageOut.setArticles(articles);
			if (member == null) {
				member = new MemberVO();
				member.setBrandId(business.getBrandId());
				member.setWeixinId(messageIn.getFromUserName());
				memberDao.insertMember(member);
				return messageOut;
			} else {
				member.setIsSubscribe(true);
				memberDao.updateMemberSubscribeStatus(member);
				return messageOut;
			}

		} else if (messageIn.getEvent().equals(Message.MSG_EVENT_UNSUBSCRIBE)) {
			if (member != null) {
				member.setIsSubscribe(false);
				memberDao.updateMemberSubscribeStatus(member);
			}
		}
		Message messageOut2 = new Message();
		messageOut2.setToUserName(messageIn.getFromUserName());
		messageOut2.setFromUserName(messageIn.getToUserName());
		messageOut2.setCreateTime(messageIn.getCreateTime());
		messageOut2.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut2.setFuncFlag("0");
		messageOut2.setContent(CONTENT_2014);
		return messageOut2;

	}

	AutoreplyVO checkAutoreply(Message messageIn,
			List<AutoreplyVO> autoreplyList) {
		String[] ss = null;
		for (AutoreplyVO autoreply : autoreplyList) {
			if (autoreply.getIsDelete()) {
				continue;
			}
			ss = autoreply.getKeyword().split(" ");
			// 关键字完全匹配且消息类型text
			if (autoreply.getKeywordType().equals("equals")
					&& messageIn.getMsgType().equals(Message.MSG_TYPE_TEXT)
					&& autoreply.getKeyword().trim()
							.equals(messageIn.getContent().trim())) {
				return autoreply;
				// 关键字完全匹配且消息类型为event
			} else if (autoreply.getKeywordType().equals("equals")
					&& messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)
					&& messageIn.getEvent().equals(Message.MSG_EVENT_CLICK)) {
				if (autoreply.getKeyword().trim()
						.equals(messageIn.getEventKey())) {
					return autoreply;
				}
				// 关键字全部出现在句子内
			} else if (autoreply.getKeywordType().equals("like")
					&& !messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)) {
				for (String key : ss) {
					if (messageIn.getContent().trim().indexOf(key.trim()) != -1) {
						return autoreply;
					}
				}

			}
		}

		return null;
	}

	Message executeVoice(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("请回复手机号码参与投票~~");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	Message executeAutoreply(Message messageIn, BusinessVO business,
			AutoreplyVO autoreply) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());

		if (autoreply.getReplyType().equals("text")) {
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setContent(autoreply.getReplyContent());
		} else if (autoreply.getReplyType().equals("news")) {
			messageOut.setTitle("");
			List<Message> articles = new ArrayList<Message>();
			List<ImageConfigVO> imageList = autoreply.getImageConfigList();
			if (imageList != null && imageList.size() != 0) {
				for (ImageConfigVO imageConfig : imageList) {
					Message message1 = new Message();
					String title = imageConfig.getReplyTitle();// 图片的url
					String imageName = imageConfig.getImageName();
					message1.setTitle(title == null || title.trim().equals("") ? ""
							: title);
					message1.setPicUrl(serverIp + path +weixinPhonePageServiceImpl.getNewImageUrl(business.getBrandId(), imageName));
					String replyUrl = imageConfig.getReplyUrl();
					message1.setUrl(replyUrl == null
							|| replyUrl.trim().equals("") ? "" : replyUrl);
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

	Message executeother(Message messageIn, BusinessVO business) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("请回复手机号码参与投票~~");
		messageOut.setFuncFlag("0");
		return messageOut;
	}
}
