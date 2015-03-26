package com.yazuo.weixin.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.weixin.service.ConsumerInterfaceService;
import com.yazuo.weixin.service.RegisterPageService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.impl.AutoreplyData;
import com.yazuo.weixin.service.vote.VoteInterfaceForNewService;
import com.yazuo.weixin.util.IOUtil;
import com.yazuo.weixin.util.SHA1;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.util.XmlUtil;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.Message;
/*
 * 
 * 微信商家交互类
 */
@Controller
@Scope("prototype")
@RequestMapping("/weixin/{brandId}")
public class ConsumerInterfaceController {
	@Autowired
	private ConsumerInterfaceService consumerInterfaceService;

	@Autowired
	private VoteInterfaceForNewService voteInterfaceForNewService;

	@Autowired
	private WeixinManageService weixinManageService;
	@Autowired
	private RegisterPageService registerPageService;
	Logger log = Logger.getLogger(this.getClass());

	
	/*
	 * 
	 * 最早的接口
	 */
	@RequestMapping(value = "message", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void message(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId) {
		long timeStart = System.currentTimeMillis();
		Map<String, List<AutoreplyVO>> map = AutoreplyData.getMap();
		List<AutoreplyVO> autoreplyList = map.get(brandId);
		BusinessVO business = weixinManageService.getBusinessByBrandId(
				Integer.valueOf(brandId), false);
		if (request.getParameter("echostr") != null
				&& !request.getParameter("echostr").equals("")) {
			log.info("debug:" + request.getParameter("signature") + "---"
					+ request.getParameter("timestamp") + "---"
					+ request.getParameter("nonce") + "---"
					+ business.getToken());
			if (checkUser(request, business.getToken())) {
				try {
					response.getWriter().print(request.getParameter("echostr"));
				} catch (IOException e) {
					log.error(e.getMessage());
					return;
				}
			}
			
			return;
		}
		String messageIn = null;
		try {
			messageIn = IOUtil.inputStream2String(request.getInputStream());
		} catch (IOException e1) {
			log.error(e1.getMessage());
			return;
		}
		log.info("接收到的消息XML" + messageIn);
		Message msgIn = null;
		try {
			if(StringUtil.isNullOrEmpty(messageIn)){
				return;
			}
			msgIn = XmlUtil.convertString2Message(messageIn, log);
		} catch (Exception e1) {
			log.error(e1.getMessage());
			e1.printStackTrace();
		}

		XmlUtil xuOut = new XmlUtil();
		if (!checkUser(request, business.getToken())) {
			xuOut = new XmlUtil();
			xuOut.setElementCData("ToUserName", msgIn.getFromUserName());
			xuOut.setElementCData("FromUserName", msgIn.getToUserName());
			xuOut.setElementText("CreateTime", msgIn.getCreateTime());
			xuOut.setElementCData("MsgType", msgIn.getMsgType());
			xuOut.setElementCData("Content", "验证没能通过");
			xuOut.setElementText("FuncFlag", "0");
			try {
				response.getWriter().print(xuOut.getXmlString());
			} catch (IOException e) {
				log.error(e.getMessage());
				return;
			}
			return;
		}
		String path = request.getContextPath();
		
		Message messageOut = consumerInterfaceService.message(msgIn, business,
				path, autoreplyList);
		String out = XmlUtil.convertMessage2String(messageOut);
		log.info("返回的消息XML" + out);
		EventRecordVO eventRecord = new EventRecordVO();
		eventRecord.setFromUsername(msgIn.getFromUserName());
		eventRecord.setReceiveContent(messageIn);
		eventRecord.setReceiveType(msgIn.getMsgType().equals("event") ? msgIn
				.getMsgType() + ":" + msgIn.getEvent() : msgIn.getMsgType());
		eventRecord.setReplyContent(out);
		eventRecord.setReplyType(messageOut.getMsgType());
		eventRecord.setToUsername(msgIn.getToUserName());
		eventRecord.setMessageTime(new Timestamp(System.currentTimeMillis()));
		eventRecord.setProcessTime(System.currentTimeMillis() - timeStart);
		consumerInterfaceService.eventRecord(eventRecord);
		try {
			response.getWriter().print(out);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	/*
	 * 
	 * 投票
	 */
	/*@RequestMapping(value = "vote", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void vote(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId) {
		long timeStart = System.currentTimeMillis();
		Map<String, List<AutoreplyVO>> map = AutoreplyData.getMap();
		List<AutoreplyVO> autoreplyList = map.get(brandId);
		BusinessVO business = weixinManageService.getBusinessByBrandId(
				Integer.valueOf(brandId), false);

		if (request.getParameter("echostr") != null
				&& !request.getParameter("echostr").equals("")) {
			log.info("debug:" + request.getParameter("signature") + "---"
					+ request.getParameter("timestamp") + "---"
					+ request.getParameter("nonce") + "---"
					+ business.getToken());
			if (checkUser(request, business.getToken())) {
				try {
					response.getWriter().print(request.getParameter("echostr"));
				} catch (IOException e) {
					log.error(e.getMessage());
					return;
				}
			}
			return;
		}
		String messageIn = null;

		try {
			
			messageIn = IOUtil.inputStream2String(request.getInputStream());
		} catch (IOException e1) {
			log.error(e1.getMessage());
			return;
		}
		log.info("接收到的消息XML" + messageIn);
		Message msgIn = null;
		try {
			msgIn = XmlUtil.convertString2Message(messageIn, log);
		} catch (Exception e1) {
			log.error(e1.getMessage());
			e1.printStackTrace();
		}

		XmlUtil xuOut = new XmlUtil();
		if (!checkUser(request, business.getToken())) {
			xuOut = new XmlUtil();
			xuOut.setElementCData("ToUserName", msgIn.getFromUserName());
			xuOut.setElementCData("FromUserName", msgIn.getToUserName());
			xuOut.setElementText("CreateTime", msgIn.getCreateTime());
			xuOut.setElementCData("MsgType", msgIn.getMsgType());
			xuOut.setElementCData("Content", "验证没能通过");
			xuOut.setElementText("FuncFlag", "0");
			try {
				response.getWriter().print(xuOut.getXmlString());
			} catch (IOException e) {
				log.error(e.getMessage());
				return;
			}
			return;
		}
		String path = request.getContextPath();
		Message messageOut = null;
		
		
		 * 如果过了2013年,也就是到了2014年刚开始时，服务就会调整为如下
		 * 新商户，用户关注后图片不变，文字统一改为“2013中国好味道活动已结束，谢谢亲的关注！”，用户发任何信息，均回复这句话。
		 * 2013-12-25 yilang
		 
		Calendar cal = Calendar.getInstance();
		String voteEndDate = null;
		try
		{
			voteEndDate = PropertyUtil.getInstance().getProperty("image-config.properties", "voteEndDate");
		}
		catch (IOException e1)
		{
			log.error("从配置文件image-config.properties中获取投票结束日期失败", e1);
		}
		
		if(null == voteEndDate)
		{
			cal.set(2014, 0, 1, 0, 0, 0);//设置为2014-01-01 00:00:00
		}	
		else
		{
			String[] fragment = voteEndDate.split("-");
			
			//设置为配置文件中的时间
			cal.set(Integer.parseInt(fragment[0]), Integer.parseInt(fragment[1]), Integer.parseInt(fragment[2]), 0, 0, 0);
		}
		
		Date year2014 = cal.getTime();
		if(new Date().before(year2014))
		{
			if (business.getVoteStatus().intValue() == 1) {
				messageOut = voteInterfaceForOldService.message(msgIn, business,
						path, autoreplyList);
			} else if (business.getVoteStatus().intValue() == 2) {
				messageOut = voteInterfaceForNewService.message(msgIn, business,
						path, autoreplyList);
			}
		}	
		else
		{
			if (business.getVoteStatus().intValue() == 1) {
				messageOut = voteInterfaceForOldService.messageNew(msgIn, business,
						path, autoreplyList);
			} else if (business.getVoteStatus().intValue() == 2) {
				messageOut = voteInterfaceForNewService.messageNew(msgIn, business,
						path, autoreplyList);
			}
		}	
		
		String out = XmlUtil.convertMessage2String(messageOut);
		log.info("返回的消息XML" + out);
		EventRecordVO eventRecord = new EventRecordVO();
		eventRecord.setFromUsername(msgIn.getFromUserName());
		eventRecord.setReceiveContent(messageIn);
		eventRecord.setReceiveType(msgIn.getMsgType().equals("event") ? msgIn
				.getMsgType() + ":" + msgIn.getEvent() : msgIn.getMsgType());
		eventRecord.setReplyContent(out);
		eventRecord.setReplyType(messageOut.getMsgType());
		eventRecord.setToUsername(msgIn.getToUserName());
		eventRecord.setMessageTime(new Timestamp(System.currentTimeMillis()));
		eventRecord.setProcessTime(System.currentTimeMillis() - timeStart);
		voteInterfaceForOldService.eventRecord(eventRecord);
		try {
			response.getWriter().print(out);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}*/
	/**
	 * 走注册页面
	 * @param request
	 * @param response
	 * @param brandId
	 */
	@RequestMapping(value = "page", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void page(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId) {
		long timeStart = System.currentTimeMillis();
		Map<String, List<AutoreplyVO>> map = AutoreplyData.getMap();
		List<AutoreplyVO> autoreplyList = map.get(brandId);
		BusinessVO business = weixinManageService.getBusinessByBrandId(
				Integer.valueOf(brandId), false);

		if (request.getParameter("echostr") != null
				&& !request.getParameter("echostr").equals("")) {
			log.info("debug:" + request.getParameter("signature") + "---"
					+ request.getParameter("timestamp") + "---"
					+ request.getParameter("nonce") + "---"
					+ business.getToken());
			if (checkUser(request, business.getToken())) {
				try {
					response.getWriter().print(request.getParameter("echostr"));
				} catch (IOException e) {
					log.error(e.getMessage());
					return;
				}
			}
			return;
		}
		String messageIn = null;

		try {
			
			messageIn = IOUtil.inputStream2String(request.getInputStream());
		} catch (IOException e1) {
			log.error(e1.getMessage());
			return;
		}
		log.info("接收到的消息XML" + messageIn);
		Message msgIn = null;
		try {
			if(StringUtil.isNullOrEmpty(messageIn)){
				return;
			}
			msgIn = XmlUtil.convertString2Message(messageIn, log);
		} catch (Exception e1) {
			log.error(e1.getMessage());
			e1.printStackTrace();
		}

		XmlUtil xuOut = new XmlUtil();
		if (!checkUser(request, business.getToken())) {
			xuOut = new XmlUtil();
			xuOut.setElementCData("ToUserName", msgIn.getFromUserName());
			xuOut.setElementCData("FromUserName", msgIn.getToUserName());
			xuOut.setElementText("CreateTime", msgIn.getCreateTime());
			xuOut.setElementCData("MsgType", msgIn.getMsgType());
			xuOut.setElementCData("Content", "验证没能通过");
			xuOut.setElementText("FuncFlag", "0");
			try {
				response.getWriter().print(xuOut.getXmlString());
			} catch (IOException e) {
				log.error(e.getMessage());
				return;
			}
			return;
		}
		String path = request.getContextPath();
		Message messageOut = registerPageService.message(msgIn, business,
				path, autoreplyList);
		String out = XmlUtil.convertMessage2String(messageOut);
		log.info("返回的消息XML" + out);
		EventRecordVO eventRecord = new EventRecordVO();
		eventRecord.setFromUsername(msgIn.getFromUserName());
		eventRecord.setReceiveContent(messageIn);
		eventRecord.setReceiveType(msgIn.getMsgType().equals("event") ? msgIn
				.getMsgType() + ":" + msgIn.getEvent() : msgIn.getMsgType());
		eventRecord.setReplyContent(out);
		eventRecord.setReplyType(messageOut.getMsgType());
		eventRecord.setToUsername(msgIn.getToUserName());
		eventRecord.setMessageTime(new Timestamp(System.currentTimeMillis()));
		eventRecord.setProcessTime(System.currentTimeMillis() - timeStart);
		consumerInterfaceService.eventRecord(eventRecord);
		try {
			response.getWriter().print(out);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	public boolean checkUser(HttpServletRequest request, String token) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String[] sList = new String[] { token, timestamp, nonce };
		Arrays.sort(sList);
		String s = sList[0] + sList[1] + sList[2];
		SHA1 sha1 = new SHA1();
		String code = sha1.getDigestOfString(s.getBytes());
		if (signature.equals(code)) {
			return true;
		}
		return false;
	}
	
	

}