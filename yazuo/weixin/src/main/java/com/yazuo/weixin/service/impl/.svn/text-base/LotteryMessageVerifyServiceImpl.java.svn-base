/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-02-28
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.service.LotteryMessageVerifyService;
import com.yazuo.weixin.service.LotteryRuleService;
import com.yazuo.weixin.service.NewLotteryService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.Message;

/**
 * @ClassName: LotteryMessageVerifyServiceImpl
 * @Description: 处理微信抽奖消息的逻辑接口
 */
@Service("lotteryMessageVerifyService")
public class LotteryMessageVerifyServiceImpl implements
		LotteryMessageVerifyService {

	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	
	@Resource
	private LotteryRuleService lotteryRuleService;
	@Resource
	private NewLotteryService newLotteryService;
	@Resource
	private WeixinManageService weixinManageService;
	
	@Value("#{propertiesReader['dfsFilePath']}")
	private String dfsFilePath; // 图片服务器地址

	/**
	 * Description:返回给微信平台的message
	 * @param messageIn
	 * @return
	 */
	public Message message(Message messageIn, MemberVO member,
			BusinessVO business, String path) {

		if (messageIn.getEventKey().equals("guaguaka")) {// 刮刮卡
			return scratchCard(messageIn, member, business, path);
		} else if (messageIn.getEventKey().equals("laohuji")) {// 老虎机
			return slotMachine(messageIn, member, business, path);
		} else if (messageIn.getEventKey().equals("dazhuanpan")) {// 大转盘
			return luckRoulette(messageIn, member, business, path);
		}else if (messageIn.getEventKey().equals("bankachoujiang")) {// 咖啡陪你办卡抽奖
			return cardLottery(messageIn, member, business, path);
		}  else {
			return null;
		}
	}
	
	

	/**
	 * Description:刮刮卡的message
	 */
	private Message scratchCard(Message messageIn, MemberVO member,
			BusinessVO business, String path) {

		if (member.getIsMember()) {// 是会员
			return isMember(messageIn, business, member, path, 1, false,"");
		} else {// 不是会员
			return isNotMember(messageIn, business, path);
		}

	}

	/**
	 * Description:老虎机的message
	 */
	private Message slotMachine(Message messageIn, MemberVO member,
			BusinessVO business, String path) {
		if (member.getIsMember()) {// 是会员
			return isMember(messageIn, business, member, path, 3, false, "");
		} else {// 不是会员
			return isNotMember(messageIn, business, path);
		}

	}

	/**
	 * Description:大转盘的message
	 */
	private Message luckRoulette(Message messageIn, MemberVO member,
			BusinessVO business, String path) {
		if (member.getIsMember()) {// 是会员
			return isMember(messageIn, business, member, path, 2, false, "");
		} else {// 不是会员
			return isNotMember(messageIn, business, path);
		}

	}
	
	/**
	 * 咖啡陪你办卡抽奖的message
	 * 
	 */
	private Message cardLottery(Message messageIn, MemberVO member, BusinessVO business, String path) {
		if (member.getIsMember()) {// 是会员
			// 查询活动规则表
			Map<String, Object> map = lotteryRuleService.getCardLotterRule(business.getBrandId(), 28);
//			// 短信预警（提醒商户抽奖机会数不足）
//			if(map!=null && map.size()>0)
//				newLotteryService.smsWarning(map);
			
			int type = map !=null && map.size()>0 ? Integer.parseInt(String.valueOf(map.get("type"))) : 0;
			// 下发的图文url上追加活动规则的开始和结束时间
			String otherUrl = "&beginTime="+(map!=null ? map.get("begin_time"):"")+"&endTime=" + (map !=null ? map.get("end_time") : "");
			return isMember(messageIn, business, member, path, type, true, otherUrl);
		} else {// 没有绑定实体卡
			Message messageOut = new Message();
			messageOut.setToUserName(messageIn.getFromUserName());
			messageOut.setFromUserName(messageIn.getToUserName());
			messageOut.setCreateTime(messageIn.getCreateTime());
			messageOut.setMsgType(Message.MSG_TYPE_TEXT);
			messageOut.setFuncFlag("0");
			String info = "<a href=\"" + serverIp + path + "/weixin/phonePage/cardBoundPage.do?brandId=" + business.getBrandId()
					+ "&weixinId=" + messageIn.getFromUserName() + "\">亲,绑定实体卡点击这里</a>";
			messageOut.setContent(info);
			return messageOut;
		}
	}

	/**
	 * Description:不是会员的message
	 */
	private Message isNotMember(Message messageIn, BusinessVO business,
			String path) {

		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("<a href=\"" + serverIp + path
				+ "/weixin/phonePage/registerPage.do?brandId="
				+ business.getBrandId() + "&weixinId="
				+ messageIn.getFromUserName()
				+ "\">亲,您还不是会员,只有会员才可以抽奖,请点击这里</a>");

		messageOut.setFuncFlag("0");
		return messageOut;

	}

	/**
	 * Description:是会员的message
	 */
	private Message isMember(Message messageIn, BusinessVO business,
			MemberVO member, String path, int type, boolean isOnlineCardLottery, String otherUrl) {

		
		List<Message> articles = new ArrayList<Message>();
		String name = "";
		String pic = "";
		String title = "";
		if (type == 1) {// 刮刮卡
			//自定义回复的维护的图片及title
			Map<String, Object> autoreplyMap = weixinManageService.getSettingAutoreplyByMenuKey(business.getBrandId(), "guaguaka");
			if (autoreplyMap !=null && autoreplyMap.size() > 0) {
				title = autoreplyMap.get("reply_title")+"";
				pic = dfsFilePath + autoreplyMap.get("image_name");
			} else {
				title = "刮刮卡";
				pic =serverIp + path + "/lotteryImages/weixin_ggk.jpg";
			}
			name = "scratchCard.do?type=1";
		} else if (type == 2) {// 大转盘
			//自定义回复的维护的图片及title
			Map<String, Object> autoreplyMap = weixinManageService.getSettingAutoreplyByMenuKey(business.getBrandId(), "dazhuanpan");
			if (autoreplyMap !=null && autoreplyMap.size() > 0) {
				title = autoreplyMap.get("reply_title")+"";
				pic = dfsFilePath + autoreplyMap.get("image_name");
			} else {
				title = "大转盘";
				pic = serverIp + path + "/lotteryImages/weixin_dzp.jpg";
			}
			name = "luckRoulette.do?type=2";
		} else if (type == 3) {// 老虎机
			name = "slotMachine.do?type=3";
			if (business.getBrandId().intValue() == Constant.CUSTOM_SETTING_BRAND) { // 表示眉州东坡集团特有的图
				title = "眉州东坡摇奖啦！";
				pic = serverIp + path + "/lotteryImages/meizhouSlotMachine.jpg";
			} else {
				//自定义回复的维护的图片及title
				Map<String, Object> autoreplyMap = weixinManageService.getSettingAutoreplyByMenuKey(business.getBrandId(), "laohuji");
				if (autoreplyMap !=null && autoreplyMap.size() > 0) {
					title = autoreplyMap.get("reply_title")+"";
					pic = dfsFilePath + autoreplyMap.get("image_name");
				} else {
					title = "老虎机";
					pic =serverIp + path + "/lotteryImages/weixin_lhj.jpg";
				}
			}
		} else { // 如果活动未创建时进错误页面
			name = "/caffe/cardLottery/errorPage.do";
		}

		StringBuilder url = new StringBuilder(serverIp);
		url.append(path);
		if (type !=0) { // 有活动规则
			if (isOnlineCardLottery) { // true 在线办卡抽奖
				url.append("/caffe/cardLottery/");
			} else { //默认方式抽奖
				url.append("/weixin/consumerLottery/");
			}
			url.append(name).append("&brandId=").append(business.getBrandId()).append("&weixinId=").append(
					messageIn.getFromUserName()).append("&membershipId=").append(member.getMembershipId()).append("&phoneNo="
			).append(member.getPhoneNo());
			if (isOnlineCardLottery) { // true 在线办卡抽奖
				url.append(otherUrl);
			}
		} else { // 无活动规则
			url.append(name);
		}
		
		//
		Message message1 = new Message();
		message1.setTitle(title);
		message1.setPicUrl(pic);
		message1.setDescription("点击进入抽奖！");
		message1.setUrl(url.toString());

		articles.add(message1);
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_NEWS);// 图文模式
		messageOut.setTitle("");
		messageOut.setFuncFlag("1");
		messageOut.setArticleCount(articles.size());
		messageOut.setArticles(articles);

		return messageOut;

	}
}
