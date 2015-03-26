package com.yazuo.weixin.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.service.WeixinPayService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.HttpClientCommonSSL;
import com.yazuo.weixin.util.IOUtil;
import com.yazuo.weixin.util.MD5Util;
import com.yazuo.weixin.util.MailUtil;
import com.yazuo.weixin.vo.WxPayDeliverStateVo;

/**
 * 
* @ClassName WeiXinFeedBackWaringController
* @Description  维权，告警 ，发货，订单查询
* @author sundongfeng@yazuo.com
* @date 2014-6-20 上午11:13:50
* @version 1.0
 */
@Controller
@Scope("prototype")
@RequestMapping("/weixin/{brandId}")
public class WeiXinFeedBackWaringController {
	private static final Log log = LogFactory.getLog("feedback");
	@Value("#{payPropertiesReader['xld.mails']}")
	private String mails;
	@Value("#{payPropertiesReader['xld.appId']}")
	private String appid;
	@Value("#{payPropertiesReader['xld.appKey']}")
	private String appkey;
	@Value("#{payPropertiesReader['xld.appSecret']}")
	private String appSecret;
	@Value("#{payPropertiesReader['xld.partnerKey']}")
	private String partnerKey;
	@Autowired
	private WeixinPayService weixinPayService;
	@Autowired
	private WeixinMallMartService weixinMallMartService;
	/**
	 * 维权
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="feedback", method = { RequestMethod.POST,
			RequestMethod.GET })
	public void feedback(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId){
		try {
			String messageIn = IOUtil.inputStream2String(request.getInputStream());
			log.info("腾讯返回feedback维权信息xml："+messageIn);
			try{
				String[] targetMails = mails.split(",");
				//失败发送邮件
				MailUtil.sendMail(Arrays.asList(targetMails),brandId+"维权单",messageIn);
			}catch(Exception e){
				log.error("code happen error.",e);
			}
			
			response.getWriter().print("success");
		} catch (IOException e) {
			log.error("code happen error.",e);
		}
	}
	
	
	
	/**
	 * 告警
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="feedbackWaring", method = { RequestMethod.POST,
			RequestMethod.GET })
	public void feedbackWaring(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId){
		try {
			String messageIn = IOUtil.inputStream2String(request.getInputStream());
			log.info("腾讯返回告警信息xml："+messageIn);
			try{
				String[] targetMails = mails.split(",");
				//失败发送邮件
				MailUtil.sendMail(Arrays.asList(targetMails),brandId+"告警单",messageIn);
			}catch(Exception e){
				log.error("code happen error.",e);
			}
			
			response.getWriter().print("success");
		} catch (IOException e) {
			log.error("code happen error.",e);
		}
	}
	/**
	 * 查询未发货的
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="deliverQuery", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String deliverQuery(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId, 
			ModelMap model)
	{
		try {
			List<WxPayDeliverStateVo> list = weixinPayService.queryDeliver(brandId);
			model.addAttribute("list", list);
			int size = list==null?0:list.size();
			model.addAttribute("size", size);
		} catch (WeixinRuntimeException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
		return "wx-xldDeliverQuery";
	}
	
	/**
	 * 发货的
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="deliverNotify", method = { RequestMethod.POST,
			RequestMethod.GET })
	public void deliver(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId)
	{
		String token="";
		try {
			response.setCharacterEncoding("utf-8");
			WxMallMerchantDict dict = weixinMallMartService.queryMerchantParam(Integer.parseInt(brandId));
			token = weixinMallMartService.getAcessTokenByDB(dict);//获取token
			List<WxPayDeliverStateVo> list = weixinPayService.queryDeliver(brandId);
			if(list!=null &&list.size()>0){
				int successCounter = 0;
				int errorCounter = 0;
				for(WxPayDeliverStateVo vo:list){
					String rs = CommonUtil.deliverState(token,vo,appkey);//立即发货
					String errmsg = JSONObject.fromObject(rs).getString("errmsg");
					if("ok".equals(errmsg)){
						weixinPayService.updateDeliverState(vo.getOut_trade_no());
						successCounter++;
					}else{
						errorCounter++;
					}
				}
				response.getWriter().print("成功发货"+successCounter+"个.失败"+errorCounter+"个。");
			}else{
				response.getWriter().print("没有需要发货的。");
			}
		} catch (Exception e) {
			log.error("code happen error.",e);
			try {
				response.getWriter().print("发货异常");
			} catch (IOException e1) {
				log.error("code happen error.",e1);
			}
		}
	}
	/**
	 * 新辣道订单查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="orderQuery", method = { RequestMethod.POST,
			RequestMethod.GET })
	public void orderQuery(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="outTradeNo" ,required=false) String outTradeNo)
	{
		String token="";
		try {
			response.setCharacterEncoding("utf-8");
			token = CommonUtil.getToken(appid,appSecret);//获取access_token
			String partner ="1218431801";
			String partnetkey ="88b6756634726349fc98b6fbc33e1379";
			String url = "https://api.weixin.qq.com/pay/orderquery?access_token="+token;
			HashMap<String,String> mp = new HashMap<String,String>();
			mp.put("appid",appid);
			String pack = "out_trade_no="+outTradeNo+"&partner="+partner+"&sign="+MD5Util.MD5("out_trade_no="+outTradeNo+"&partner="+partner+"&key="+partnetkey).toUpperCase();
			mp.put("package",pack);
			mp.put("timestamp", String.valueOf(new Date().getTime()/1000));
			mp.put("app_signature",CommonUtil.createSign(mp, appkey));
			mp.put("sign_method", "sha1");
			log.info("查询订单json:"+JSONObject.fromObject(mp).toString());
			String content = JSONObject.fromObject(mp).toString();
			
			String result = HttpClientCommonSSL.commonPostStream(url, content);
			log.info("查询订单返回:"+result);
			response.getWriter().print(result);
		} catch (Exception e) {
			log.error("code happen error.",e);
		}
	}
	
}
