package com.yazuo.weixin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.minimart.vo.WxMallOrder;
import com.yazuo.weixin.service.WeixinPayService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.EncryptUtil;
import com.yazuo.weixin.util.HttpClientCommonSSL;
import com.yazuo.weixin.util.IOUtil;
import com.yazuo.weixin.util.MD5Util;
import com.yazuo.weixin.util.MailUtil;
import com.yazuo.weixin.vo.MemberCardVO;
import com.yazuo.weixin.vo.WxcoffeeCzFlag;

/**
* @ClassName WeixinCoffeeController
* @Description 咖啡陪你项目充值中心
* @author sundongfeng@yazuo.com
* @date 2014-6-25 下午2:00:01
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/weixin/{brandId}")
@SessionAttributes({"openid","dict"})
public class WeixinCoffeeController {
	private static final Log log = LogFactory.getLog("coffee");
	private static final Log LOGS = LogFactory.getLog("coffeeLogAnalysis");

	@Value("#{coffeePropertiesReader['smsUrl']}")
	private String smsUrl;
	@Value("#{coffeePropertiesReader['coffee.queryCardList']}")
	private String queryCardListUrl;
	@Value("#{coffeePropertiesReader['coffee.chongzhi']}")
	private String chongzhiUrl;
	@Value("#{propertiesReader['checkCardTrade']}")
	private String checkStoreUrl;
	@Value("#{coffeePropertiesReader['coffee.mails']}")
	private String mails;
	@Value("#{coffeePropertiesReader['coffee.subject']}")
	private String subject;	
	@Value("#{coffeePropertiesReader['coffee.error']}")
	private String error;	
	@Value("#{coffeePropertiesReader['coffee.orderQuery']}")
	private String orderQueryUrl;	
	
	@Autowired
	private WeixinPayService weixinPayService;
	@Autowired
	private WeixinMallMartService weixinMallMartService;
	private String secretKey = "HKpaL3cAOIGggxnq6moKVFI9";
	/**
	 * 跳转充值页面
	 * @param brandId
	 * @param code 2级跳转code
	 * @param model
	 * @return
	 */
	@RequestMapping(value="goCoffeeRecharge", method = { RequestMethod.POST,RequestMethod.GET })
	public String goCoffeeRecharge(
			@ModelAttribute("brandId") @PathVariable(value = "brandId") String brandId, 
			@RequestParam(value = "code", required = false) String code,
			ModelMap model)
	{
		log.info("brandId:"+brandId);
		LOGS.info(";"+DateUtil.getDate()+";goCoffeeRecharge;");
		return "wx-coffeeRecharge";
	}
	/**
	 * 获取储值卡号,返回储值卡信息 {key:value}
	 * @param brandId
	 * @param code 2级跳转code
	 * @param model
	 * @return
	 */
	@RequestMapping(value="goObtainCardInfo", method = { RequestMethod.POST,RequestMethod.GET })
	public void obtainCardInfo(HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId, 
			@RequestParam(value = "mobile", required = false) String mobile,
			ModelMap model)
	{
		log.info("mobile:"+mobile);
		LOGS.info(";"+DateUtil.getDate()+";goObtainCardInfo;");
		//通过手机号，brandid获取储值卡号
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", brandId);
		jsonMap.put("mobile", mobile);
		jsonMap.put("isStore", true);
		String input = JSONObject.fromObject(jsonMap).toString();
		log.info("queryCardListUrl_input.json:"+input);
		
		List<String> cardList = new ArrayList<String>();//储值卡号
		String result="";
		try {
			//获取储值卡号
			result = CommonUtil.postSendMessage(queryCardListUrl, input, Constant.ENTITYKEY.toString());
		
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("result message:"+message);
				if(success){
					JSONArray array = res.getJSONArray("cardList");
					if(array!=null && array.size()>0){
						for(Iterator iter = array.iterator();iter.hasNext();){
							JSONObject jsonObject = (JSONObject) iter.next();
							MemberCardVO cardInfo = (MemberCardVO) JSONObject.toBean(jsonObject, MemberCardVO.class);
							cardList.add(cardInfo.getCardNo());
						}
					}else{
						cardList.add("没有找到可用的储值卡");
					}
				}else{
					cardList.add("无储值卡信息");
				}
			}
		}catch(Exception ex){
			cardList.add("无储值卡信息");
			log.error("获取储值卡信息失败。",ex);
		}finally{
			try {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("key", cardList);
				String json = JSONObject.fromObject(map).toString();
				log.info("获取储值卡信息json:"+json);
				response.getWriter().write(json);
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				log.error("code happen error.",e);
			}
		}
		
	}
	
	/**
	 * 充值前校验方法
	 * @param request
	 * @param response
	 * @param brandId
	 * @param card
	 * @param totalFee
	 * @param code
	 * @throws Exception 
	 */
	@RequestMapping(value="checkStoreCard", method = { RequestMethod.POST,RequestMethod.GET })
	public void checkStoreCard(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "card") String card,
			@RequestParam(value = "totalFee") String totalFee,
			@RequestParam(value = "mobile") String mobile 
			) throws Exception{
		JSONObject json = new JSONObject();
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", brandId);
		jsonMap.put("cardNo", card);
		jsonMap.put("amount", totalFee);
		jsonMap.put("transCode", "2001");
		String input = JSONObject.fromObject(jsonMap).toString();
		log.info("checkStoreUrl-input.json:"+input);
		
		String result="";
		try {
			//获取储值卡号
			result = CommonUtil.postSendMessage(checkStoreUrl, input, Constant.COUPONKEY.toString());
			log.info("checkStoreUrl返回:"+result);
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("result message:"+message);
				if(success){
					json.put("flag", "0");
				}else{
					json.put("flag", "1");
					json.put("message", "此卡限额限制充值");
				}
			}
		}catch(Exception e){
			json.put("flag", "1");
			json.put("message", "暂时不能提供充值");
		}
		response.getWriter().print(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 跳转成功页面
	 * @param request
	 * @param brandId
	 * @param num 购买数量
	 * @param totalFee 总价
	 * @param outTradeNo 订单号
	 * @param mobile 手机号
	 * @param code 2级跳转code
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{card}/{totalFee}/{outTradeNo}/goCoffeeSuccessPage", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String goCoffeeSuccessPage(HttpServletRequest request,
			@ModelAttribute("brandId") @PathVariable(value = "brandId") Integer brandId,
			@ModelAttribute("card") @PathVariable(value = "card") String card,
			@ModelAttribute("totalFee") @PathVariable(value = "totalFee") String totalFee,
			@PathVariable(value = "outTradeNo") String outTradeNo,
			@RequestParam(value = "code", required = false) String code, 
			ModelMap model
			){
		log.info("totalFee:"+totalFee+"\tcard:"+card+"\toutTradeNo:"+outTradeNo);
		LOGS.info(";"+DateUtil.getDate()+";goCoffeeSuccessPage;");
		try {
			WxMallMerchantDict dict = weixinMallMartService.queryMerchantParam(brandId);
			model.addAttribute("dict", dict);
			try {
				JSONObject json = weixinPayService.getWeiXinId(code,"", dict.getAppId(), dict.getAppSecret());
				log.info("获取auth2-openid:"+json.getString("openid"));
				String openid = json.getString("openid");
				model.addAttribute("openid", openid);
			} catch (Exception e) {
				log.error("code happen error.",e);
			}
			
			 
			String token = weixinMallMartService.getAcessTokenByDB(dict);;
			HashMap<String,String> mp = new HashMap<String,String>();
			mp.put("appid",dict.getAppId());
			String pack = "out_trade_no="+outTradeNo+"&partner="+dict.getPartnerId()+"&sign="+MD5Util.MD5("out_trade_no="+outTradeNo+"&partner="+dict.getPartnerId()+"&key="+dict.getPartnerKey()).toUpperCase();
			mp.put("package",pack);
			mp.put("timestamp", String.valueOf(new Date().getTime()/1000));
			mp.put("app_signature",CommonUtil.createSign(mp, dict.getAppKey()));
			mp.put("sign_method", "sha1");
			log.info("订单支付状态json:"+JSONObject.fromObject(mp).toString());
			String content = JSONObject.fromObject(mp).toString();
			orderQueryUrl+=token;
			log.info(orderQueryUrl);
			String result = HttpClientCommonSSL.commonPostStream(orderQueryUrl, content);
			log.info("查询订单支付状态返回值:"+result);
			String trade_state =((JSONObject) JSONObject.fromObject(result).get("order_info")).getString("trade_state");//查询订单支付状态
			
			model.addAttribute("trade_state", trade_state);
//			model.addAttribute("trade_state", "0");
			model.addAttribute("brandId", brandId);
		} catch (Exception e) {
			log.error("code happen error.",e);
			return "wx-xldPayError";
		}
		
		return "wx-coffeeRecharge";//跳转购买页面
	}
	/**
	 *  腾讯通知支付结果url
	 * @param request
	 * @param response
	 * @param brandId
	 * @param num 数量，传给李斌接口
	 * @param mobile 电话
	 * @param cardId 优惠券id
	 */
	@RequestMapping(value="/{card}/{mobile}/coffeePayResult",method = { RequestMethod.POST,
			RequestMethod.GET })
	public void coffeePayResult(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId,
			@PathVariable(value = "mobile") String mobile,
			@PathVariable(value = "card") String card,
			ModelMap model){
			WxMallOrder order = new WxMallOrder();
			String messageIn = "";
			try{
				Map<String,String> map = CommonUtil.convertString(request);
				BeanUtils.copyProperties(order,map);//解析request parameters
				messageIn = IOUtil.inputStream2String(request.getInputStream()); //获取腾讯返回支付信息
				log.info("腾讯返回支付信息xml："+messageIn);
			}catch(IOException ex){
				log.error("支付结果通知异常.", ex);
			} catch (IllegalAccessException e1) {
				log.error("复制信息异常.",e1);
			} catch (InvocationTargetException e1) {
				log.error("复制信息异常.",e1);
			}
			
			InputStream is = null;
			String tradeState = order.getTrade_state();//支付成功状态,0成功
			/**********充值卡充值状态**********/
			WxcoffeeCzFlag czflag = new WxcoffeeCzFlag();
			czflag.setOutTradeNo(order.getOut_trade_no());
			czflag.setMobile(mobile);
			czflag.setCardNo(card);
			czflag.setBrandId(brandId);
			czflag.setAmount(order.getTotal_fee()/100);
			czflag.setStatus("0");//先插入0，如果充值成功，将状态更改为1
			czflag.setRemark("微信咖啡陪你充值");
			String chongzhiResMess = "";
			
			try {
				log.info("读取tx xml信息 start.");
				is = new ByteArrayInputStream(messageIn.getBytes());
				Document doc = new SAXReader().read(is);
				String OpenId=doc.selectSingleNode("/xml/OpenId").getText();
				String AppId=doc.selectSingleNode("/xml/AppId").getText();
				String IsSubscribe=doc.selectSingleNode("/xml/IsSubscribe").getText();
				String NonceStr=doc.selectSingleNode("/xml/NonceStr").getText();
				String AppSignature=doc.selectSingleNode("/xml/AppSignature").getText();
				LOGS.info(";"+DateUtil.getDate()+";coffeePayResult;"+mobile+";"+order.getOut_trade_no()+";"+card+";"+order.getTotal_fee()/100+";"+1+";"+order.getTrade_state()+";"+OpenId+";");
				/*将支付结果加入bean*/
				order.setAppId(AppId);
				order.setOpenId(OpenId);
				order.setIsSubscribe("1".equals(IsSubscribe)?true:false);
				order.setNonceTr(NonceStr);
				order.setAppSignature(AppSignature);
				log.info("读取tx xml信息 end.");
				model.addAttribute("openid", OpenId);//加入缓存openid
				
				/*****返回给腾讯success后，腾讯不再继续通知支付结果*****/
				response.getWriter().write("success");
				log.info("通知腾讯success");
				boolean chzflag = weixinPayService.queryHasCzflag(czflag.getOutTradeNo());
				if("0".equals(tradeState)&&!chzflag){
					/*****************充值接口****************/
					Map<String,Object> jsonMap = new HashMap<String,Object>();
					jsonMap.put("merchantId", brandId);
					jsonMap.put("mobile", mobile);
					jsonMap.put("cardNo", card);
					jsonMap.put("amount", order.getTotal_fee()/100);
					jsonMap.put("source", 5);
					jsonMap.put("remark", "微信充值");
					
					String encodeInput = JSONObject.fromObject(jsonMap).toString();
					String input = EncryptUtil.encrypt3DesObject(secretKey, encodeInput);
					input = URLEncoder.encode(input, "UTF-8");
					String  result = CommonUtil.postSendMessage(chongzhiUrl, input, Constant.COFFEEKEY);
					JSONObject requestObject = JSONObject.fromObject(result);
					Boolean flag = requestObject.getBoolean("flag");
					String data = "";
					if(flag){
						data = EncryptUtil.dncrypt3DesString(secretKey,  URLEncoder.encode(((JSONObject) requestObject.get("data")).get("result").toString(),"UTF-8"));
					}else{
						data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
					}
					log.info("充值返回data:"+data);
					JSONObject res=JSONObject.fromObject(data);
					if (res != null) { 
						Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
						String message = res.getString("message"); // 返回信息
						chongzhiResMess = message;
						log.info("result message:"+message+"\t outTradeNo:"+order.getOut_trade_no());
						if (success&&!message.contains("失败")) {// 成功
							czflag.setStatus("1");//将充值状态更改为成功状态1
						}
					}
				}
			} catch (DocumentException e) {
				log.error("解析支付结果 error.", e);
			} catch (WeixinRuntimeException e) {
				log.error("code happen error.",e);
			}catch (UnsupportedEncodingException e) {
				log.error("code happen error.",e);
			} catch (IOException e) {
				log.error("code happen error.",e);
			}finally{
				/***************立即发货****************/
				order.setDeliverState(0L);//发货状态
				try{
					WxMallMerchantDict dict = weixinMallMartService.queryMerchantParam(Integer.parseInt(brandId));
					String token = weixinMallMartService.getAcessTokenByDB(dict);//获取token
					Map<String,String> ma = new HashMap<String,String>();
					ma.put("appid",order.getAppId() );
					ma.put("openid",order.getOpenId() );
					ma.put("transid",order.getTransaction_id() );
					ma.put("out_trade_no", order.getOut_trade_no());
					String rs = deliverState(token,ma,dict.getAppKey());//立即发货
					String errmsg = JSONObject.fromObject(rs).getString("errmsg");
					log.info("发货:"+errmsg);
					if("ok".equals(errmsg)){
						order.setDeliverState(1L);//发货状态 1 成功 0 失败
					}
				}catch(Exception ex){
					log.error("发货异常:",ex);
				}
				try {
					/************更新数据库支付信息*************/
					int update = weixinMallMartService.updatePayResultInfo(order);
					log.info("更新数据库支付信息num:"+update);
					/****************查询是否充值状态*****************/
					boolean flag = weixinPayService.queryHasCzflag(czflag.getOutTradeNo());
					if(flag){
						if("1".equals(czflag.getStatus())){
							weixinPayService.updateCzflag(czflag);//更新充值成功状态
						}
					}else{
						int a = weixinPayService.insertCzflag(czflag);
						log.info("插入券状态成功:"+(a>0));
						if("0".equals(czflag.getStatus())){
							String content = "用户手机:"+czflag.getMobile()+"\n"+
									"充值卡号:"+czflag.getCardNo()+"\n"+
									"充值金额:"+czflag.getAmount()+"\n"+
									"充值接口返回信息:"+chongzhiResMess+"\n"+
									"商户订单号:"+czflag.getOutTradeNo()+"\n"+
									"银行订单号:"+order.getTransaction_id()+"\n"+
									"商户brandId:"+czflag.getBrandId()+"\n"+
									"用户wxId:"+order.getOpenId()+"\n";
							String[] targetMails = mails.split(",");
							//失败发送邮件
							MailUtil.sendMail(Arrays.asList(targetMails),subject,content);
							//发送短信内容
							String jsonpar = "{'mobile':'" + czflag.getMobile() + "','content':'" + error
					                 + "','merchant_id':'"+czflag.getBrandId()+"','source':'8'}";
							
							CommonUtil.postSendMessage(smsUrl,jsonpar,Constant.ENTITYKEY.toString());//发送短信
						}
					}
				} catch (Exception e1) {
					log.error("code happen error.",e1);
				}
				
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
						log.error("code happen error.",e);
					}
				}
			}
	}
	/**
	 * 获取用户购买信息，组装商品信息
	 * @param request
	 * @param response
	 * @param mobile 手机号
	 * @param productInfo 商品title
	 * @param totalFee 总价
	 * @param nonceTr 随机数
	 * @param cardId 优惠券id
	 */
	@RequestMapping(value="getProductInfo",method = { RequestMethod.POST,
			RequestMethod.GET })
	public void getProductInfo(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Long brandId,
			@RequestParam(value = "card", required = false) String card,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "productInfo", required = false) String productInfo,
			@RequestParam(value = "totalFee", required = false) String totalFee,
			@RequestParam(value = "nonceTr", required = false) String nonceTr){
		log.info("mobile:"+mobile+"\tproductInfo:"+productInfo+"\t totalFee:"+totalFee+"\tnonceTr："+nonceTr);
		WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
		if(dict==null){
			 dict=weixinMallMartService.queryMerchantParam(brandId.intValue()) ;
		}
		//返回package json
		String spbill_create_ip=request.getRemoteAddr();
		String outTradeNo = DateUtil.get12Date()+CommonUtil.CreateNoncestr();//生成商户订单号
		log.info("spbill_create_ip:"+spbill_create_ip);
		Map<String,String> map = new HashMap<String,String>();
		map.put("outTradeNo", outTradeNo);
		map.put("appId", dict.getAppId());
		map.put("partnerId", dict.getPartnerId());
		map.put("paternerKey", dict.getPartnerKey());
		map.put("appKey", dict.getAppKey());
		map.put("spbillCreateIp", spbill_create_ip);
		map.put("nonceTr", nonceTr);
		LOGS.info(";"+DateUtil.getDate()+";getProductInfo;"+mobile+";"+outTradeNo+";"+card+";"+totalFee+";");
		WxMallOrder wxorder  = new WxMallOrder();
		try {
			wxorder.setAppId(dict.getAppId());
			wxorder.setMobile(mobile);
			wxorder.setPartner(dict.getPartnerId());
			wxorder.setTotal_fee(Double.parseDouble(totalFee));
			wxorder.setBuyNum(1L); //购买数量
			wxorder.setOut_trade_no(outTradeNo);
			wxorder.setNonceTr(nonceTr);
			wxorder.setFee_type(1L);
			wxorder.setTrade_mode(1L);
			wxorder.setSign_type("MD5");
			wxorder.setBank_type("WX");
			wxorder.setProductInfo(productInfo);
			wxorder.setGoodsId(Long.parseLong(card));//卡编号
			wxorder.setBrandId(brandId);
			wxorder.setSource(4L);//在线充值订单来源4
			int num = weixinMallMartService.insertMallOrder(wxorder);//生成支付流水信息
			log.info("插入订单数据:"+num);
		}catch(Exception ex){
			log.error("weixinPayService.inserUserPayInfo error.", ex);
		}
		try {
			log.info(JSONObject.fromObject(map).toString());
			response.getWriter().write(JSONObject.fromObject(map).toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			log.error("response date error.",e);
		} 
	}
	
	/**
	 * 跳转错误页面
	 * @return
	 */
	@RequestMapping(value="goCoffeeErrorPage",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String goCoffeeErrorPage(){
		return "wx-xldPayError";
	}
	
	/**
	 * 发货通知
	 * @param token
	 * @return
	 * @throws WeixinRuntimeException 
	 */
	public String deliverState(String token,Map<String,String> map,String appkey) throws WeixinRuntimeException{
		String url="https://api.weixin.qq.com/pay/delivernotify?access_token="+token;
		HashMap<String,String> mp = new HashMap<String,String>();
		mp.put("appid",map.get("appid"));
		mp.put("openid",map.get("openid"));
		mp.put("transid",map.get("transid"));
		mp.put("out_trade_no",map.get("out_trade_no"));
		mp.put("deliver_timestamp",String.valueOf(new Date().getTime()/1000));
		mp.put("deliver_status","1");
		mp.put("deliver_msg","ok");
		mp.put("app_signature",CommonUtil.createSign(mp,appkey));//生成签名
		mp.put("sign_method", "sha1");
		log.info("发货json:"+JSONObject.fromObject(mp).toString());
		String content = JSONObject.fromObject(mp).toString();
		String result = HttpClientCommonSSL.commonPostStream(url, content);
		log.info("发货返回:"+result);
		return result;
	}
}
