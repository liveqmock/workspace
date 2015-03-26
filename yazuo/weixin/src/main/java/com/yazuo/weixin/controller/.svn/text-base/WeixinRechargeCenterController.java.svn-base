package com.yazuo.weixin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
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
import org.apache.commons.lang.StringUtils;
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
import com.yazuo.weixin.service.WeixinPayService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.HttpClientCommonSSL;
import com.yazuo.weixin.util.IOUtil;
import com.yazuo.weixin.util.MailUtil;
import com.yazuo.weixin.vo.WxCardInfo;
import com.yazuo.weixin.vo.WxOrder;
import com.yazuo.weixin.vo.WxPayCard;
import com.yazuo.weixin.vo.WxPayCouponState;

/**
* @ClassName WeixinRechargeCenterController
* @Description 微信支付中心
* @author sundongfeng@yazuo.com
* @date 2014-6-10 上午11:33:15
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/weixin/{brandId}")
@SessionAttributes({"cardInfos","openid"}) //券信息和用户openid放入session
public class WeixinRechargeCenterController {
	private static final Log log = LogFactory.getLog("wxpay");
	private static final Log logAna = LogFactory.getLog("payanalysis");//日志记录分析
	@Value("#{payPropertiesReader['xld.key']}")
	private String xldKey;
	@Value("#{payPropertiesReader['xld.activeId']}")
	private String activeCode;
	@Value("#{payPropertiesReader['xld.appId']}")
	private String appid;
	@Value("#{payPropertiesReader['xld.appKey']}")
	private String appkey;
	@Value("#{payPropertiesReader['xld.appSecret']}")
	private String appSecret;
	@Value("#{payPropertiesReader['xld.partnerId']}")
	private String productid;
	@Value("#{payPropertiesReader['xld.partnerKey']}")
	private String partnerkey;
	@Value("#{payPropertiesReader['xld.optainTicketsUrl']}")
	private String optainTicketsUrl;
	@Value("#{payPropertiesReader['xld.optainTicketInfoUrl']}")
	private String optainTicketInfoUrl;
	@Value("#{payPropertiesReader['xld.mailUrl']}")
	private String mailUrl;
	@Value("#{payPropertiesReader['smsUrl']}")
	private String smsUrl;
	@Value("#{payPropertiesReader['xld.mails']}")
	private String mails;
	@Value("#{payPropertiesReader['xld.subject']}")
	private String subject;
	@Value("#{payPropertiesReader['xld.error']}")
	private String error;
	
	
	@Autowired
	private WeixinPayService weixinPayService;
	
	/**
	  * 跳转新辣道优惠券页面
	 * @param request
	 * @param brandId
	 * @param activeId 活动id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="goRecharge", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String goRecharge(HttpServletRequest request,
			@PathVariable(value = "brandId") String brandId,
			@RequestParam(value = "activeId", required = false) String activeId,
			@RequestParam(value = "code", required = false) String code,
			ModelMap model
			){
		List<WxCardInfo> hglist = new ArrayList<WxCardInfo>();
		Map<String,WxCardInfo> cardInfos = new HashMap<String,WxCardInfo>();
		activeId = StringUtils.isNotBlank(activeId)?activeId:activeCode;
		log.info("activeId:"+activeId+"\tcode:"+code);
		logAna.info(";"+DateUtil.getDate()+";goRecharge;");
		try {
			JSONObject res = weixinPayService.queryCardInfos(optainTicketInfoUrl, brandId,activeId, xldKey);//查询券信息
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("result message:"+message);
				if (success) {// 成功
					JSONArray array = res.getJSONArray("couponList");
					for(Iterator iter = array.iterator();iter.hasNext();){
						JSONObject jsonObject = (JSONObject) iter.next();
						WxCardInfo cardInfo = (WxCardInfo) JSONObject.toBean(jsonObject, WxCardInfo.class);
						hglist.add(cardInfo);
						cardInfos.put(cardInfo.getCouponId(), cardInfo);
					}
				}
			}
		} catch (Exception e) {
			log.error("code happen error.",e);
			return "wx-xldPayError";
		}
		model.addAttribute("hglist", hglist);
		model.addAttribute("cardInfos", cardInfos);//将券信息加入session<couponId,WxCardInfo>
		return "wx-huoguoList"; //跳转火锅列表
	}
	/**
	 * 跳转每一个套餐详情页面
	 * @param request
	 * @param brandId
	 * @param cardCode 优惠券id
	 * @param activeId 活动id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="goEachPage", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String goEachPage(HttpServletRequest request,
			@PathVariable(value = "brandId") String brandId,
			@RequestParam(value = "cardCode", required = false) String cardCode,
			@RequestParam(value = "activeId", required = false) String activeId,
			ModelMap model
			){
		logAna.info(";"+DateUtil.getDate()+";goEachPage;"+cardCode+";");
		try {
			obtainCardInfos(model,brandId,activeId,request,cardCode);
		} catch (WeixinRuntimeException e) {
			log.error("code happen error.",e);
			return "wx-xldPayError";
		}
		return "wx-xldProductDetail";//跳转商品详情页面
	}
	
	/**
	 * 跳转每一个购买页面,应该在此判断是否code失效，失效应跳转错误页面，不应该发起购买
	 * @param request
	 * @param brandId
	 * @param cardCode 优惠券id
	 * @param activeId 活动id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="goEachPayPage", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String goEachPayPage(HttpServletRequest request,
			@PathVariable(value = "brandId") String brandId,
			@RequestParam(value = "cardCode", required = false) String cardCode,
			@RequestParam(value = "activeId", required = false) String activeId,
			ModelMap model
			){
		logAna.info(";"+DateUtil.getDate()+";goEachPayPage;"+cardCode+";");
		try {
			obtainCardInfos(model,brandId,activeId,request,cardCode);//获取券信息
		} catch (Exception e) {
			log.error("code happen error.",e);
			return "wx-xldPayError";
		}
		return "wx-xldProductBuy";//跳转购买页面
	}
	
	/**
	 * 跳转成功页面
	 * @param request
	 * @param brandId
	 * @param num 购买数量
	 * @param totalFee 总价
	 * @param cardCode 优惠券id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{num}/{totalFee}/{cardCode}/{outTradeNo}/{mobile}/goPaySuccessPage", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String goPaySuccessPage(HttpServletRequest request,
			@ModelAttribute("brandId") @PathVariable(value = "brandId") String brandId,
			@ModelAttribute("num") @PathVariable(value = "num") String num,
			@ModelAttribute("totalFee") @PathVariable(value = "totalFee") String totalFee,
			@PathVariable(value = "cardCode") String cardCode,
			@PathVariable(value = "outTradeNo") String outTradeNo,
			@PathVariable(value = "mobile") String mobile,
			@RequestParam(value = "activeId", required = false) String activeId,
			@RequestParam(value = "code", required = false) String code,
			ModelMap model
			){
		logAna.info(";"+DateUtil.getDate()+";goPaySuccessPage;"+mobile+";"+outTradeNo+";"+cardCode+";"+totalFee+";"+num+";");
		log.info("num:"+num+"\ttotalFee:"+totalFee+"\tcardCode:"+cardCode+"\tcode:"+code+"\toutTradeNo:"+outTradeNo+"\tmobile:"+mobile);
		try {
			//获取券信息
			obtainCardInfos(model,brandId,activeId,request,cardCode);
			model.addAttribute("appid", appid);
			
			String openid = "";
			openid = (String) request.getSession().getAttribute("openid");
			log.info("获取session-openid:"+openid);
			if(StringUtils.isEmpty(openid)){
				JSONObject json = weixinPayService.getWeiXinId(code,"", appid, appSecret);
				log.info("获取auth2-openid:"+json.getString("openid"));
				openid = json.getString("openid");
			}
			model.addAttribute("openid", openid);
			
		} catch (Exception e) {
			log.error("code happen error.",e);
			try {
				String open_id= weixinPayService.queryOpenId(mobile, brandId);
				log.info("数据库-openid:"+open_id);
				model.addAttribute("openid", open_id);
			} catch (Exception e1) {
				log.error("code happen error.",e1);
				return "wx-xldPayError";
			}
		}
		return "wx-xldPaySuccess";//跳转购买页面
	}
	/**
	 * 私有方法，获取券信息
	 * @param model
	 * @param brandId
	 * @param activeId
	 * @param request
	 * @param cardCode
	 */
	private void obtainCardInfos(ModelMap model,String brandId,String activeId,HttpServletRequest request,String cardCode)throws WeixinRuntimeException{
		Map<String,WxCardInfo> cardInfos =null;
		
		cardInfos = (Map<String, WxCardInfo>) request.getSession().getAttribute("cardInfos");//session中取出套餐对象
		
		if(cardInfos==null){
			cardInfos = new HashMap<String,WxCardInfo>();//初始化
			activeId = StringUtils.isNotBlank(activeId)?activeId:activeCode;
			try {
				JSONObject res = weixinPayService.queryCardInfos(optainTicketInfoUrl, brandId,activeId, xldKey);//查询券信息
				if (res != null) { 
					Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
					String message = res.getString("message"); // 返回信息
					log.info("result message:"+message);
					if (success) {// 成功
						JSONArray array = res.getJSONArray("couponList");
						for(Iterator iter = array.iterator();iter.hasNext();){
							JSONObject jsonObject = (JSONObject) iter.next();
							WxCardInfo cardInfo = (WxCardInfo) JSONObject.toBean(jsonObject, WxCardInfo.class);
							cardInfos.put(cardInfo.getCouponId(), cardInfo);
						}
					}
				}
			} catch (Exception e) {
				log.error("code happen error.",e);
				throw new WeixinRuntimeException("code happen error.", e);
			}
		}
		WxCardInfo obj = cardInfos.get(cardCode);
		String couponDesc = obj.getCouponDesc();
		String[] descs = couponDesc.split("\\|");
		model.addAttribute("descs", descs);//套餐描述加入request
		model.addAttribute("cardCode", cardCode);//cardCode 加入request
		model.addAttribute("cardInfo", cardInfos.get(cardCode));//套餐详情加入request
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
	@RequestMapping(value="/{num}/{mobile}/payResult",method = { RequestMethod.POST,
			RequestMethod.GET })
	public void payResult(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId,
			@PathVariable(value = "num") int num,
			@PathVariable(value = "mobile") String mobile,
			@RequestParam(value = "cardId", required = false) String cardId,
			ModelMap model){
			WxOrder order = new WxOrder();
			String messageIn = "";
			try{
				Map<String,String> map = CommonUtil.convertString(request);
				BeanUtils.copyProperties(order,map);
				messageIn = IOUtil.inputStream2String(request.getInputStream());
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
			WxPayCouponState pcsvo = new WxPayCouponState();//记录券状态
			pcsvo.setMobile(mobile);
			pcsvo.setTotalFee(order.getTotal_fee());
			pcsvo.setCounter(new Long(num));
			pcsvo.setCardId(cardId);
			pcsvo.setTradeState(tradeState);
			pcsvo.setTransactionId(order.getTransaction_id());
			pcsvo.setPartnerId(brandId);
			pcsvo.setCardState("0");//先暂时插入0失败状态
			try {
				log.info("读取tx xml信息 start.");
				is = new ByteArrayInputStream(messageIn.getBytes());
				Document doc = new SAXReader().read(is);
				String OpenId=doc.selectSingleNode("/xml/OpenId").getText();
				String AppId=doc.selectSingleNode("/xml/AppId").getText();
				String IsSubscribe=doc.selectSingleNode("/xml/IsSubscribe").getText();
				String TimeStamp=doc.selectSingleNode("/xml/TimeStamp").getText();
				String NonceStr=doc.selectSingleNode("/xml/NonceStr").getText();
				String AppSignature=doc.selectSingleNode("/xml/AppSignature").getText();
				String SignMethod=doc.selectSingleNode("/xml/SignMethod").getText();
				model.addAttribute("openid", OpenId);//加入session
				log.info("读取tx xml信息 end.");
				logAna.info(";"+DateUtil.getDate()+";payResult;"+mobile+";"+order.getOut_trade_no()+";"+cardId+";"+order.getTotal_fee()+";"+num+";"+order.getTrade_state()+";"+OpenId+";");
				/*将支付结果加入bean*/
				order.setAppId(AppId);
				order.setOpenId(OpenId);
				order.setIsSubscribe(IsSubscribe);
				order.setNonceTr(NonceStr);
				order.setAppSignature(AppSignature);
				
				pcsvo.setOpenId(OpenId);
				/*生成券信息，入库，调用李斌接口获取券的信息*/
				String outTradeNO=order.getOut_trade_no();
				
				pcsvo.setOutTradeNo(outTradeNO);
				
				if("0".equals(tradeState)){
					order.setDeliverState("0");//发货状态
					try{
						String token = CommonUtil.getToken(appid,appSecret);//获取access_token
						String rs = deliverState(token,order,appkey);//立即发货
						String errmsg = JSONObject.fromObject(rs).getString("errmsg");
						log.info("发货:"+errmsg);
						if("ok".equals(errmsg)){
							order.setDeliverState("1");//发货状态 1 成功 0 失败
						}
					}catch(Exception ex){
						log.error("发货异常:",ex);
					}
				}
				int update = weixinPayService.updatePayResultInfo(order);//更新数据库支付信息
				log.info("更新数据库支付信息num:"+update);
				
				response.getWriter().write("success");//返回给腾讯success后，腾讯不再继续通知支付结果
				log.info("通知腾讯success");
				
				
				boolean isHas = weixinPayService.queryHasTicketsCard(outTradeNO);
				List<WxPayCard> cardList = new ArrayList<WxPayCard>();
				log.info("是否有券了:"+isHas);
				log.info("生成券信息，入库，调用李斌接口获取券的信息.");
				log.info("outTradeNo:"+order.getOut_trade_no()+"\ttransId:"+order.getTransaction_id()+"\tmobile:"+mobile+"\topenid:"+OpenId+"\tcardId:"+cardId+"\tnum:"+num+"\t fee:"+order.getTotal_fee());
				if(StringUtils.isNotBlank(mobile)&& !isHas && "0".equals(tradeState)){ //无券信息才执行
					Map<String,Object> jsonMap = new HashMap<String,Object>();
					jsonMap.put("count", num);
					jsonMap.put("merchantId", brandId);
					jsonMap.put("mobile", mobile);
					jsonMap.put("couponId", cardId);//券id
					String input = JSONObject.fromObject(jsonMap).toString();
					log.info("payResult.json:"+input);
					
					String result = CommonUtil.postSendMessage(optainTicketsUrl, input, xldKey);
					JSONObject requestObject = JSONObject.fromObject(result);
					String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
					JSONObject res=JSONObject.fromObject(data);
					if (res != null) { 
						Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
						String message = res.getString("message"); // 返回信息
						log.info("result message:"+message+"\t outTradeNo:"+order.getOut_trade_no());
						if (success) {// 成功
							pcsvo.setCardState("1");
							StringBuffer sb = new StringBuffer();
							JSONArray array = res.getJSONArray("couponList");
							for(Iterator iter = array.iterator();iter.hasNext();){
								JSONObject jsonObject = (JSONObject) iter.next();
								WxPayCard card = new WxPayCard();
								card.setBrandId(brandId);
								String resId = jsonObject.getString("id");
								sb.append(resId).append(",");
								card.setRes_card_no(resId);
								card.setCardId(cardId);
								card.setMobile(mobile);
								card.setOpenId(OpenId);
								card.setOutTradeNo(outTradeNO);
								card.setPartnerId(order.getPartner());
								cardList.add(card);
							}
							log.info("返回card_no:"+sb);
							log.info("生成券数量:"+cardList.size());
							//生成券信息入库
							weixinPayService.inserCardInfo(cardList);
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
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
						log.error("code happen error.",e);
						e.printStackTrace();
					}
				}
				try {
					boolean hasCouponState = weixinPayService.queryHasBindCoupon(order.getOut_trade_no());
					if(!hasCouponState){
						int a =	weixinPayService.insertCouponState(pcsvo);//插入券状态
						log.info("插入券状态成功:"+(a>0));
						if("0".equals(pcsvo.getCardState())){
							String content = "用户手机:"+pcsvo.getMobile()+"\n"+
									"购买券Id:"+pcsvo.getCardId()+"\n"+
									"购买数量:"+pcsvo.getCounter()+"\n"+
									"购买金额:"+pcsvo.getTotalFee()+"\n"+
									"商户订单号:"+pcsvo.getOutTradeNo()+"\n"+
									"银行订单号:"+pcsvo.getTransactionId()+"\n"+
									"商户brandId:"+pcsvo.getPartnerId()+"\n"+
									"用户wxId:"+pcsvo.getOpenId()+"\n";
							String[] targetMails = mails.split(",");
							//失败发送邮件
							MailUtil.sendMail(Arrays.asList(targetMails),subject,content);
							//发送短信内容
							String jsonpar = "{'mobile':'" + pcsvo.getMobile() + "','content':'" + error
					                 + "','merchant_id':'"+pcsvo.getPartnerId()+"','source':'8'}";
							
							CommonUtil.postSendMessage(smsUrl,jsonpar,xldKey);//发送短信
						}
					}else{
						if("1".equals(pcsvo.getCardState())){
							weixinPayService.updateWeixin_pay_coupon_state(pcsvo);
						}
					}
				} catch (Exception e) {
					log.error("插入券状态 code happen error.",e);
					e.printStackTrace();
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
	@RequestMapping(value="checkAndGet",method = { RequestMethod.POST,
			RequestMethod.GET })
	public void checkUserAndGetProductInfo(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "productInfo", required = false) String productInfo,
			@RequestParam(value = "totalFee", required = false) String totalFee,
			@RequestParam(value = "nonceTr", required = false) String nonceTr,
			@RequestParam(value = "cardId", required = false) String cardId,
			@RequestParam(value = "num", required = false) String num){
		log.info("mobile:"+mobile+"\t productInfo:"+productInfo+"\t totalFee:"+totalFee+"\tnonceTr："+nonceTr+"\tcardId:"+cardId+"\tnum:"+num);

		//返回package json
		String spbill_create_ip=request.getRemoteAddr();
		String outTradeNo = CommonUtil.CreateNoncestr();//生成订单号
		log.info("spbill_create_ip:"+spbill_create_ip);
		Map<String,String> map = new HashMap<String,String>();
		map.put("outTradeNo", outTradeNo);
		map.put("appId", appid);
		map.put("partnerId", productid);
		map.put("paternerKey", partnerkey);
		map.put("appKey", appkey);
		map.put("spbillCreateIp", spbill_create_ip);
		map.put("nonceTr", nonceTr);
		logAna.info(";"+DateUtil.getDate()+";checkAndGet;"+mobile+";"+outTradeNo+";"+cardId+";"+totalFee+";"+num+";");
		WxOrder wxorder  = new WxOrder();
		wxorder.setAppId(appid);
		wxorder.setMobile(mobile);
		wxorder.setPartner(productid);
		wxorder.setTotal_fee(Long.parseLong(totalFee));
		wxorder.setCount(Long.parseLong(num));
		try {
			wxorder.setOut_trade_no(outTradeNo);
			wxorder.setNonceTr(nonceTr);
			wxorder.setFee_type("1");
			wxorder.setTrade_mode("1");
			wxorder.setSign_type("MD5");
			wxorder.setBank_type("WX");
			wxorder.setProductInfo(productInfo);
			wxorder.setCardId(cardId);
			wxorder.setBrandId(brandId);
			weixinPayService.inserUserPayInfo(wxorder);//生成支付流水信息
			
		}catch(Exception ex){
			log.error("weixinPayService.inserUserPayInfo error.", ex);
		}
		try {
			log.info(JSONObject.fromObject(map).toString());
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (IOException e) {
			log.error("response date error.",e);
		} 
	}
	@RequestMapping(value="goErrorPage",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String goErrorPage(){
		logAna.info(";"+DateUtil.getDate()+";goErrorPage;");
		return "wx-xldPayError";
	}
	
	/**
	 * 发货通知
	 * @param token
	 * @return
	 * @throws WeixinRuntimeException 
	 */
	public String deliverState(String token,WxOrder order,String appkey) throws WeixinRuntimeException{
		String url="https://api.weixin.qq.com/pay/delivernotify?access_token="+token;
		HashMap<String,String> mp = new HashMap<String,String>();
		mp.put("appid",order.getAppId());
		mp.put("openid",order.getOpenId());
		mp.put("transid",order.getTransaction_id());
		mp.put("out_trade_no",order.getOut_trade_no());
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
