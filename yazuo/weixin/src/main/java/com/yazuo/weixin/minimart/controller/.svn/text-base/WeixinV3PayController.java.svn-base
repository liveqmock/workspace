package com.yazuo.weixin.minimart.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.minimart.vo.WxCardSign;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.IOUtil;
import com.yazuo.weixin.util.SHA1Util;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.util.WxPayUtilV3;

/**
* @ClassName WeixinV3PayController
* @Description v3版测试
* @author sundongfeng@yazuo.com
* @date 2015-1-8 下午1:52:05
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/weixin/v3")
public class WeixinV3PayController {
	@Autowired
	private WxPayUtilV3 wxPayUtilV3;
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Autowired
	private MemcachedClient memcachedClient;
	private static final Log log = LogFactory.getLog("mall");
	
	@RequestMapping(value="mallMartv3Index", method = { RequestMethod.POST,RequestMethod.GET })
	public String mallMartv3Index(HttpServletRequest request,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			ModelMap model
			) throws Exception{
		model.addAttribute("weixinId", weixinId);
		String js_tiket = memcachedClient.get("js_tiket");
		if(StringUtil.isNullOrEmpty(js_tiket)){
			String accesstoken = CommonUtil.getToken("wxda8f17411ad516ef", "be830e58e809aa9f4a4e07608e46ea12");
			String url = "http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+accesstoken;
			js_tiket = CommonUtil.postMessage(url);
			JSONObject sjon = JSONObject.fromObject(js_tiket);
			String err_msg = sjon.getString("errmsg");
			if("ok".equals(err_msg)){
				js_tiket= sjon.getString("ticket");
				if(!memcachedClient.replace("js_tiket", 7200, js_tiket))  memcachedClient.add("js_tiket", 7200, js_tiket);
			}
		}
		String noncestr=CommonUtil.CreateNoncestr();
		String timeStamp=Long.toString(new Date().getTime()/1000);
		String url="http://wxde.yazuo.com/yazuo-weixin/weixin/v3/mallMartv3Index.do?weixinId=ooj7DjswcwaoTl424KmtaQj5Kub0";
		String string1 = "jsapi_ticket=" + js_tiket +
                 "&noncestr=" + noncestr +
                 "&timestamp=" + timeStamp +
                 "&url=" + url; 
		String sign = SHA1Util.Sha1(string1);
		model.addAttribute("timestamp", timeStamp);
		model.addAttribute("nonceStr", noncestr);
		model.addAttribute("sign", sign);
		return "wx-payv3";
	}
	
	@RequestMapping(value="getProductInfov3", method = { RequestMethod.POST,RequestMethod.GET })
	public void getProductInfov3(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			ModelMap model) throws Exception{
		String path = request.getContextPath();
		String notify_url=serverIp+path+"/weixin/v3/payResultv3.do";
		String appid ="wxda8f17411ad516ef";
		String apikey ="sDGYszkxbbBMai7G8Vap7FL4gjwNXhvz";
		String mch_id="10063341";
		String nonce_str=CommonUtil.CreateNoncestr();
		String body="v3版支付测试";
		String out_trade_no=DateUtil.get12Date()+CommonUtil.CreateNoncestr();//生成订单号，前12位时间，后面16位随机数;
		String total_fee="1";
		String spbill_create_ip=request.getRemoteAddr();
		String trade_type="JSAPI";
		String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String openid=weixinId;
		wxPayUtilV3.SetParameter("appid", appid);
		wxPayUtilV3.SetParameter("body", body);
		wxPayUtilV3.SetParameter("mch_id", mch_id);
		wxPayUtilV3.SetParameter("nonce_str", nonce_str);
		wxPayUtilV3.SetParameter("notify_url", notify_url);
		wxPayUtilV3.SetParameter("openid", openid);
		wxPayUtilV3.SetParameter("out_trade_no", out_trade_no);
		wxPayUtilV3.SetParameter("spbill_create_ip",spbill_create_ip);
		wxPayUtilV3.SetParameter("total_fee", total_fee);
		wxPayUtilV3.SetParameter("trade_type", trade_type);
		wxPayUtilV3.setAppid(appid);
		wxPayUtilV3.setApikey( apikey);
		String sign = wxPayUtilV3.createSign(wxPayUtilV3.packageMap);
		wxPayUtilV3.SetParameter("sign", sign);
		String pre_pay_xml = wxPayUtilV3.createXml();
		log.info(pre_pay_xml);
		String pre_pay_res = CommonUtil.postMessage2(unifiedorder, pre_pay_xml);
		log.info(pre_pay_res);
		InputStream is = null;
		is = new ByteArrayInputStream(pre_pay_res.getBytes());
		Document doc = new SAXReader().read(is);
		String prepay_id=doc.selectSingleNode("/xml/prepay_id").getText();
		log.info(prepay_id);
		wxPayUtilV3.setPrepay_id(prepay_id);
		String packageInfo = wxPayUtilV3.CreateBizPackage();
		log.info( packageInfo);
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("outTradeNo", out_trade_no);
		jsonRes.put("order", packageInfo);
		response.getWriter().write(jsonRes.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	@RequestMapping(value="payResultv3", method = { RequestMethod.POST,RequestMethod.GET })
	public void payResultv3(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception{
		String messageIn = "";
		
		HashMap<String,String> map = (HashMap<String, String>) CommonUtil.convertString(request);
		System.out.println(CommonUtil.ArrayToXml(map));
		messageIn = IOUtil.inputStream2String(request.getInputStream());
		log.info("腾讯返回支付信息xml："+messageIn);
		SortedMap<String, String> packageMap = new TreeMap<String,String>();
		packageMap.put("return_code", "SUCCESS");
		 packageMap.put("return_msg", "OK");
		String return_to_tx=CommonUtil.ArrayToXml(packageMap);
		response.getWriter().write(return_to_tx);//返回给腾讯success后，腾讯不再继续通知支付结果
		log.info("通知腾讯success");
	}
	
	
	
	@RequestMapping(value="getcard",method = { RequestMethod.POST,RequestMethod.GET })
	public String getcard(HttpServletRequest request,HttpServletResponse response,ModelMap model,
			@RequestParam(value = "weixinId", required = false) String weixinId) throws Exception{ 
		String wx_card_tiket = memcachedClient.get("wx_card_js_tiket");
		String weixin_api_card_token = memcachedClient.get("wx_card_token"); 
		if(StringUtils.isEmpty(wx_card_tiket)){
			weixin_api_card_token= StringUtils.isEmpty(weixin_api_card_token)?CommonUtil.getToken("wxda8f17411ad516ef", "be830e58e809aa9f4a4e07608e46ea12"):weixin_api_card_token;
			memcachedClient.delete("wx_card_token");
			memcachedClient.set("wx_card_token",7200,weixin_api_card_token);
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+weixin_api_card_token+"&type=wx_card";
			wx_card_tiket = CommonUtil.postMessage(url);
			JSONObject sjon = JSONObject.fromObject(wx_card_tiket);
			String err_msg = sjon.getString("errmsg");
			if("ok".equals(err_msg)){
				wx_card_tiket= sjon.getString("ticket");
				if(!memcachedClient.replace("wx_card_js_tiket", 7200, wx_card_tiket))  memcachedClient.add("wx_card_js_tiket", 7200, wx_card_tiket);
			}
		}
		
		String noncestr=CommonUtil.CreateNoncestr();
		String card_id="poj7DjrUFY2Sd-QbJuxMdjdG8Rhw";
		String timeStamp=Long.toString(new Date().getTime()/1000);
		
		 WxCardSign signer = new WxCardSign();
         signer.AddData(wx_card_tiket);
         signer.AddData(card_id);
         signer.AddData(timeStamp);
         signer.AddData(timeStamp);
         signer.AddData(weixinId);
		
         log.info(timeStamp.length());
		String signature = signer.GetSignature();
		
		model.addAttribute("signature", signature);
		model.addAttribute("timestamp", timeStamp);
		model.addAttribute("nonceStr", noncestr);
		model.addAttribute("weixinId", weixinId);
		model.addAttribute("code", timeStamp);
		model.addAttribute("card_id", card_id);
		return "/card/getcard";
	}
	
	
	protected String createSign(SortedMap<String, String> packageParams) throws WeixinRuntimeException {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String sign =  SHA1Util.Sha1(sb.toString());
		return sign;
	}
}
