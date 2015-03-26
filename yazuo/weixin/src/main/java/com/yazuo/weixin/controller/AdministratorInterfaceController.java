package com.yazuo.weixin.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.service.AdministratorInterfaceService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.util.IOUtil;
import com.yazuo.weixin.util.SHA1;
import com.yazuo.weixin.util.XmlUtil;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.Message;
import com.yazuo.weixin.vo.SummaryDetail;

@Controller
@RequestMapping("/weixin/report")
public class AdministratorInterfaceController {
	@Autowired
	private AdministratorInterfaceService administratorInterfaceService;
	@Autowired
	private WeixinManageService weixinManageService;
	Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value = "message", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void message(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("echostr") != null
				&& !request.getParameter("echostr").equals("")) {
			if (checkUser(request, "weixin")) {
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
			msgIn = XmlUtil.convertString2Message(messageIn,log);
		} catch (Exception e1) {
			log.error(e1.getMessage());
			e1.printStackTrace();
		}

		XmlUtil xuOut = new XmlUtil();
		if (!checkUser(request, "weixin")) {
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
		Message messageOut = administratorInterfaceService.message(msgIn, path);
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
		administratorInterfaceService.eventRecord(eventRecord);
		try {
			response.getWriter().print(out);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	@RequestMapping(value = "summaryDetail", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView summaryDetail(
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "brandId", required = false) String brandId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "detailType", required = false) Integer detailType) {
		String s2 = request.getHeader("User-Agent"); // 获取浏览器信息summaryDetail
		if(s2.indexOf("Windows")>=0){
			ModelAndView mav = new ModelAndView("error");
			return mav;
		}
		List<SummaryDetail> summaryDetailList = administratorInterfaceService
				.summaryDetail(brandId, startDate, endDate, detailType);
		ModelAndView mav = new ModelAndView("w_summaryDetail");
		mav.addObject("summaryDetailList", summaryDetailList);
		return mav;

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