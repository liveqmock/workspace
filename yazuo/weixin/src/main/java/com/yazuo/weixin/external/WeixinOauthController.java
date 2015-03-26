package com.yazuo.weixin.external;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.service.ConsumerInterfaceService;
import com.yazuo.weixin.service.RegisterPageService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.impl.AutoreplyData;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.HttpClientCommonSSL;
import com.yazuo.weixin.util.IOUtil;
import com.yazuo.weixin.util.SHA1;
import com.yazuo.weixin.util.XmlUtil;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.Message;

/**
* @ClassName WeixinOauthController
* @Description 提供第三方获取code，依据code获取用户信息
* @author sundongfeng@yazuo.com
* @date 2014-9-25 下午4:46:22
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/weixin/{brandId}")
public class WeixinOauthController {
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	Logger log = Logger.getLogger(this.getClass());
	private static final Log log1 = LogFactory.getLog("external");
	@Autowired
	private WeixinMallMartService weixinMallMartService;
	@Autowired
	private WeixinManageService weixinManageService;
	@Autowired
	private RegisterPageService registerPageService;
	@Autowired
	private ConsumerInterfaceService consumerInterfaceService;
	/**
	 * 第三方跳转服务方url
	 * @param request
	 * @param appid
	 * @param redirect_uri
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "oauthCode", method = { RequestMethod.POST,RequestMethod.GET })
	public String oauthCode(HttpServletRequest request,
			@RequestParam(value = "appid") String appid,
			@RequestParam(value = "redirect_uri") String redirect_uri ,
			Model model) {
		String selfRedirectUrl = serverIp+"/yazuo-weixin/weixin/obtainOauthCode.do?";
		selfRedirectUrl+="appcode="+appid+"&red_url="+redirect_uri;
		String tourl="https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid="+appid+"&redirect_uri="+selfRedirectUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		model.addAttribute("tourl", tourl);
		return "/oauth/oauthCodeRedirect";
	}
	/**
	 * 服务方获取code，传回第三方的地址
	 * @param appid
	 * @param red_uri
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "obtainOauthCode", method = { RequestMethod.POST,RequestMethod.GET })
	public String obtainOauthCode(
			@RequestParam(value = "red_url") String red_url ,
			@RequestParam(value = "code",required=false) String code ,
			Model model) {
		if(red_url.contains("?")){
			red_url+="&code="+code;
		}else{
			red_url+="?code="+code;
		}
		model.addAttribute("red_url", red_url);
		return "/oauth/obatinOauthCode";
	}
	
	/**
	 * 第三方服务接入入口，完成消息转发
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "entry", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public void entry(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId){
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
		log1.info("接收到的消息XML" + messageIn);
		Message msgIn = null;
		try {
			msgIn = XmlUtil.convertString2Message(messageIn, log);
		} catch (Exception e1) {
			log.error(e1.getMessage());
			e1.printStackTrace();
		}

		if (Message.MSG_EVENT_CLICK.equals(msgIn.getEvent())) {
			
			String path = request.getContextPath();
			Message messageOut = registerPageService.message(msgIn, business,
					path, autoreplyList);
			String out = XmlUtil.convertMessage2String(messageOut);
			log.info("返回的消息XML" + out);
			log1.info("返回的消息XML" + out);
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
		}else{
			try {
				if(AutoreplyData.getTokenMap()==null){
					AutoreplyData.setTokenMap(new HashMap<String,Object>());
				}
				String token = (String) AutoreplyData.getTokenMap().get("token");
				log.info("mem-token:"+token);
				log1.info("mem-token:"+token);
				if(StringUtils.isEmpty(token)){
					token="falseToken";
				}
				String aurl = Constant.MSGURL; //发消息url
				
				String out = CommonUtil.postMessage2(aurl+token, messageIn);
				if(out!=null&&out.contains("用户验证失败")){
					String url = Constant.TOKENURL;//获取token
					String result = CommonUtil.postMessage(url);
					JSONObject requestObject = JSONObject.fromObject(result);
					String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("data").toString(), "UTF-8");
					JSONObject res=JSONObject.fromObject(data);
					if(res.getInt("error")==0){
						token = res.getString("token");
						if(AutoreplyData.getTokenMap()==null){
							AutoreplyData.setTokenMap(new HashMap<String,Object>());
						}
						if(AutoreplyData.getTokenMap().containsKey("token")){
							AutoreplyData.getTokenMap().remove("token");
						}
						AutoreplyData.getTokenMap().put("token", token);
					}
					out = CommonUtil.postMessage2(aurl+token, messageIn);
				}
				log.info("jiahe返回的消息XML" + out);
				log1.info("jiahe返回的消息XML" + out);
			
				response.getWriter().print(out);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 获取accesstoken
	 * @param message
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "queryAccessToken", method = { RequestMethod.POST,RequestMethod.GET })
	public void queryAccessToken(@PathVariable(value = "brandId") Integer brandId,
			HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		try {
			WxMallMerchantDict dict=weixinMallMartService.queryMerchantParam(brandId) ;
			//获取access_token
			String token = weixinMallMartService.getAcessTokenByDB(dict);
			json.put("success", true);
			json.put("accessToken", token);
		}catch (Exception e) {
			json.put("success", false);
			json.put("accessToken", "");
			log.error("queryAccessToken error.",e);
		}
		log1.info("brandId:"+brandId+" result:"+json.toString());
		response.getWriter().write(json.toString());
	}
	
/*	@RequestMapping(value = "fileupload", method = { RequestMethod.POST,RequestMethod.GET })
	public String fileupload(@PathVariable(value = "brandId") Integer brandId,Model model){
		WxMallMerchantDict dict=weixinMallMartService.queryMerchantParam(brandId) ;
		//获取access_token
		try {
			String token = weixinMallMartService.getAcessTokenByDB(dict);
			model.addAttribute("accessToken", token);
		} catch (Exception e) {
			log.error("code happen error.",e);
		}
		return "/oauth/fileupload";
	}*/
	
	
	@RequestMapping(value = "fromOther", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public String fromOther(@RequestParam(value = "message")String message,HttpServletResponse response) throws Exception{
		
		Message msgIn = XmlUtil.convertString2Message(message, log);
		XmlUtil xuOut = new XmlUtil();
		xuOut.setElementCData("ToUserName", msgIn.getFromUserName());
		xuOut.setElementCData("FromUserName", msgIn.getToUserName());
		xuOut.setElementText("CreateTime", msgIn.getCreateTime());
		xuOut.setElementCData("MsgType", msgIn.getMsgType());
		xuOut.setElementCData("Content", "hello nihao ma");
		xuOut.setElementText("FuncFlag", "0");
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
		WxMallMerchantDict dict=weixinMallMartService.queryMerchantParam(15) ;
		String token = weixinMallMartService.getAcessTokenByDB(dict);//获取access_token
		url += token;
		JSONObject json = new JSONObject();
		json.put("touser", msgIn.getFromUserName());
		json.put("msgtype", "text");
		json.put("text", "{'content':'测试客服消息!'}");
		
		String result = HttpClientCommonSSL.commonPostStream(url, json.toString());
		log.info("result:"+result);
		return xuOut.getXmlString();
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
