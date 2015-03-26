package com.yazuo.weixin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallGoods;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.minimart.vo.WxMallOrder;
import com.yazuo.weixin.service.WeixinPayService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.IOUtil;
import com.yazuo.weixin.util.WxPayUtil;

 
/**
* @ClassName WeixinCoffeeSellCardController
* @Description 咖啡陪你在线售卡
* @author sundongfeng@yazuo.com
* @date 2014-8-21 下午5:02:32
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/weixin/{brandId}")
@SessionAttributes({"dict","openid","brandId"}) 
public class WeixinCoffeeSellCardController {
	private static final Log log = LogFactory.getLog("coffee");
	private static final Log logAna = LogFactory.getLog("coffeeLogAnalysis");//日志记录分析
	@Value("#{coffeePropertiesReader['smsUrl']}")
	private String smsUrl;
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Value("#{propertiesReader['activeCard']}")
	private String activeCardUrl;
	
	@Autowired
	private WeixinMallMartService weixinMallMartService;
	@Autowired
	private WeixinPayService weixinPayService;
	@Autowired
	private WxPayUtil wxpayUtil;
	/**
	 * 私有方法，取出openid
	 * @param request
	 * @param code
	 * @param appid
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	private String queryWeixinId(HttpServletRequest request,String code,String appid,String appSecret)throws Exception{
		String openid = (String) request.getSession().getAttribute("openid");
		log.info("获取session-openid:"+openid);
		if(StringUtils.isEmpty(openid)){
			JSONObject json = weixinPayService.getWeiXinId(code,"", appid, appSecret);
			log.info("获取auth2-openid:"+json.getString("openid"));
			openid = json.getString("openid");
		}
		return openid;
	}
	/**
	 * 微信在线售卡主页面
	 * @param request
	 * @param brandId
	 * @param code 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="sellCardIndex", method = { RequestMethod.POST,RequestMethod.GET })
	public String sellCardIndex(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			ModelMap model
			){
		logAna.info(";"+DateUtil.getDate()+";sellCardIndex;");
		try{
			WxMallMerchantDict dict = weixinMallMartService.queryMerchantParam(brandId);
			model.addAttribute("dict", dict);
			String openid = weixinId;
			if(StringUtils.isEmpty(openid)){
				try {
					JSONObject json = weixinPayService.getWeiXinId(code,"", dict.getAppId(), dict.getAppSecret());
					log.info("获取auth2-openid:"+json.getString("openid"));
					openid = json.getString("openid");
				} catch (Exception e) {
					log.error("code happen error.",e);
				}
			}
			model.addAttribute("openid", openid);
			
			List<WxMallGoods>  mallGoods = weixinMallMartService.queryMallGoods(brandId,2);//2 实体卡
			// 设置图片路径之后的返回结果
			mallGoods = weixinMallMartService.getCardPictureUrlByCardType(mallGoods);
			
			model.addAttribute("brandId", brandId);
			model.addAttribute("mallGoods", mallGoods);
			return "/coffee/wx-coffeeCardIndex"; //跳转微信商城主页
		}catch(Exception ex){
			log.error("code happen error.",ex);
			return "wx-xldPayError";
		}
	}
	/**
	 * 我的申请记录
	 * @param brandId
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value="myCardList", method = { RequestMethod.POST,RequestMethod.GET })
	public String myCardList( HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId", required = false) String openid,
			ModelMap model){
		logAna.info(";"+DateUtil.getDate()+";myCardList;");
		List<Map<String,Object>>  orderList=new ArrayList<Map<String,Object>>();
		try {
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			model.addAttribute("dict", dict);
			orderList = weixinMallMartService.queryPaySuccessOrders(openid,brandId,5); //5 在线售卡订单
		} catch (Exception e) {
			log.error("code happen error.",e);
		}
		model.addAttribute("orderList", orderList);
		model.addAttribute("brandId", brandId);
		model.addAttribute("weixinId", openid);
		return "/coffee/wx-coffeeMyCardList";
	}
	
	
	/**
	 * 商品详情页面
	 * @param request
	 * @param brandId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="cardInfoPage", method = { RequestMethod.POST,RequestMethod.GET })
	public String cardInfoPage(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "goodCode", required = false) Integer goodCode,
			ModelMap model
			){
		logAna.info(";"+DateUtil.getDate()+";cardInfoPage;");
		WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
		if(dict==null){
			 dict=weixinMallMartService.queryMerchantParam(brandId) ;
		}
		model.addAttribute("dict", dict);
		/**
		 * 查询单个商品列表，加入request
		 */
		WxMallGoods vo =  weixinMallMartService.queryEachGood( brandId, goodCode);
		// 设置图片路径之后的返回结果 
		List<WxMallGoods> mallList = new ArrayList<WxMallGoods>();
		mallList.add(vo);
		mallList = weixinMallMartService.getCardPictureUrlByCardType(mallList);
		vo = mallList.get(0);
		
		model.addAttribute("goodvo", vo);
		model.addAttribute("brandId", brandId);
		return "/coffee/wx-coffeeCardGoodInfo";//跳转商品详情页面
	}
	
	/**
	 * 跳转每一个购买页面
	 * @param request
	 * @param brandId
	 * @param cardCode 优惠券id
	 * @param activeId 活动id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="buyCard", method = { RequestMethod.POST, RequestMethod.GET })
	public String buyCard(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "goodCode", required = false) Integer goodCode,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "state", required = false) String state,
			ModelMap model
			){
		try {
			logAna.info(";"+DateUtil.getDate()+";buyCard;");
			String openid="";
			String accesstoken="";
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			model.addAttribute("dict", dict);
			try {
				JSONObject json = weixinPayService.getWeiXinId(code,"", dict.getAppId(), dict.getAppSecret());
				log.info("获取auth2-openid:"+json.getString("openid"));
				log.info("获取auth2-access_token:"+json.getString("access_token"));
				openid = json.getString("openid");
				accesstoken = json.getString("access_token");
				model.addAttribute("brandId", brandId);
				model.addAttribute("openid", openid);
				model.addAttribute("code", code);
				model.addAttribute("state", state);
				model.addAttribute("accesstoken", accesstoken);
			} catch (Exception e) {
				log.error("buyCard 获取access_token error.",e);
			}
			/**
			 * 查询单个商品列表，加入request
			 */
			WxMallGoods vo =  weixinMallMartService.queryEachGood( brandId, goodCode);
			// 设置图片路径之后的返回结果 
			List<WxMallGoods> mallList = new ArrayList<WxMallGoods>();
			mallList.add(vo);
			mallList = weixinMallMartService.getCardPictureUrlByCardType(mallList);
			vo = mallList.get(0);
			
			model.addAttribute("goodvo", vo);
			return "/coffee/wx-coffeeCardBuy";//跳转购买页面
		
		} catch (Exception e) {
			log.error("code happen error.",e);
			return "wx-xldPayError";
		}
	}
	/**
	 * 购买前，检查购买规则
	 * @param request
	 * @param response
	 * @param brandId 商户id
	 * @param weixinId 微信id
	 * @param goodId 商品id
	 * @param num 购买个数
	 * @param payType 支付类型
	 * @throws Exception 
	 */
	@RequestMapping(value="checkBeforeBuy",method = { RequestMethod.POST, RequestMethod.GET })
	public void checkBeforeBuy(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "goodId", required = false) Integer goodId,
			@RequestParam(value = "num", required = false) Integer num,
			@RequestParam(value = "buy_limit_is", required = false) String buy_limit_is,
			@RequestParam(value = "buy_limit_type", required = false) String buy_limit_type,
			@RequestParam(value = "buy_limit_num", required = false) Integer buy_limit_num
			) throws Exception{
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		if("1".equals(buy_limit_is)){//1 限购
			boolean flag = false;
			if("1".equals(buy_limit_type)){ //1 每天限购
				flag = true; //每天限制
			}
			int buyNum = weixinMallMartService.queryUserBuyCount(weixinId, brandId, flag, goodId);
			if((num+buyNum)>buy_limit_num){
				json.put("flag", "1");
				json.put("message", "超过购买限制");
			}
		}
		//查商品库存量
		int remainNum = weixinMallMartService.selectRemainNum(goodId);
		if(remainNum<=0){
			json.put("flag", "1");
			json.put("message", "商品已售罄");
		}else if(num>remainNum){
			json.put("flag", "1");
			json.put("message", "商品库存不足了");
		}
		log.info("brandId:"+brandId+" weixinId:"+weixinId+" goodId:"+goodId+" num:"+num+" json:"+json.toString());
		response.getWriter().print(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 获取用户购买信息，组装商品信息
	 * @param request
	 * @param response
	 * @param brandId 商户id
	 * @param totalFee 总价
	 * @param goodInfo 商品详情
	 * @param goodId 商品id
	 * @param num 购买数量
	 * @param receiver 收件人
	 * @param firstStageName 省
	 * @param secondstageName 市
	 * @param thirdStageName 县
	 * @param addressDetail 地址
	 * @param mobile 手机
	 * @param zipcode 邮编
	 * @param openId 微信id
	 */
	@RequestMapping(value="obtainCardInfo",method = {RequestMethod.POST,RequestMethod.GET})
	public void obtainCardInfo(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "totalFee", required = false) Double totalFee,
			@RequestParam(value = "goodInfo", required = false) String goodInfo,
			@RequestParam(value = "goodId", required = false) Long goodId,
			@RequestParam(value = "num", required = false) Long num,
			@RequestParam(value = "receiver", required = false) String receiver,
			@RequestParam(value = "firstStageName", required = false) String firstStageName,
			@RequestParam(value = "secondstageName", required = false) String secondstageName,
			@RequestParam(value = "thirdStageName", required = false) String thirdStageName,
			@RequestParam(value = "addressDetail", required = false) String addressDetail,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "zipcode", required = false) String zipcode,
			@RequestParam(value = "openId", required = false) String openId,
			@RequestParam(value = "productType", required = false) Integer productType,
			@RequestParam(value = "batchId", required = false) Integer batchId,
			@RequestParam(value = "cardTypeId", required = false) Integer cardTypeId
			){
		try {
			log.info("brandId:"+brandId+"\t goodInfo:"+goodInfo+"\t totalFee:"+totalFee+"\t goodId:"+goodId+"\t num:"+num);
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			//返回package json
			String spbill_create_ip=request.getRemoteAddr();
			String outTradeNo = DateUtil.get12Date()+CommonUtil.CreateNoncestr();//生成订单号，前14位时间，后面16位随机数
			String nonceTr = CommonUtil.CreateNoncestr();
			String timeStamp=Long.toString(new Date().getTime()/1000);
			logAna.info(";"+DateUtil.getDate()+";obtainCardInfo;");
			WxMallOrder wxorder  = new WxMallOrder();
			wxorder.setAppId(dict.getAppId());
			wxorder.setMobile(mobile);
			wxorder.setPartner(dict.getPartnerId());
			wxorder.setTotal_fee(totalFee);//
			wxorder.setBuyNum(num);
			wxorder.setOut_trade_no(outTradeNo);
			wxorder.setNonceTr(nonceTr);
			wxorder.setFee_type(1L);
			wxorder.setTrade_mode(1L);
			wxorder.setSign_type("MD5");
			wxorder.setBank_type("WX");
			wxorder.setPayType(1L);//支付方式
			wxorder.setProductInfo(goodInfo);
			wxorder.setGoodsId(goodId);
			wxorder.setBrandId(brandId.longValue());
			wxorder.setSource(5L);//在线售卡为5
			wxorder.setReceiver(receiver);
			wxorder.setFirstAddr(firstStageName);
			wxorder.setSecondAddr(secondstageName);
			wxorder.setThirdAddr(thirdStageName);
			wxorder.setDetailAddr(addressDetail);
			wxorder.setZipcode(zipcode);
			wxorder.setOpenId(openId);
			wxorder.setProductType(productType);	//卡类型
			weixinMallMartService.insertMallOrder(wxorder);//生成订单支付流水信息
			
			//生成微信支付信息
			String path = request.getContextPath();
			String notify_url = serverIp+path+"/weixin/"+brandId+"/"+num+"/"+mobile+"/"+goodId+"/"+productType+"/"+batchId+"/"+cardTypeId+"/cardPayResult.do"; 
			log.info("notify_url:"+notify_url);
			wxpayUtil.SetParameter("bank_type", "WX");
			wxpayUtil.SetParameter("body", goodInfo);
			wxpayUtil.SetParameter("partner", dict.getPartnerId());
			wxpayUtil.SetParameter("out_trade_no",outTradeNo );
			DecimalFormat df = new DecimalFormat("#.0");
			String totalfee100=df.format(totalFee*100);
			wxpayUtil.SetParameter("total_fee", totalfee100.substring(0, totalfee100.length()-2));
			wxpayUtil.SetParameter("fee_type", "1");
			wxpayUtil.SetParameter("notify_url", notify_url); //notify_url
			wxpayUtil.SetParameter("spbill_create_ip", spbill_create_ip);
			wxpayUtil.SetParameter("input_charset", "UTF-8");
			wxpayUtil.setAppid(dict.getAppId());
			wxpayUtil.setAppkey(dict.getAppKey());
			wxpayUtil.setNoncestr(nonceTr);
			wxpayUtil.setPartnerkey(dict.getPartnerKey());
			wxpayUtil.setProductid(dict.getPartnerId());
			wxpayUtil.setTimeStamp(timeStamp);
			String packageInfo = wxpayUtil.CreateBizPackage();
			log.info( packageInfo);
			JSONObject jsonRes = new JSONObject();
			jsonRes.put("outTradeNo", outTradeNo);
			jsonRes.put("order", packageInfo);
			response.getWriter().write(jsonRes.toString());
			response.getWriter().flush();
			response.getWriter().close();
		
		} catch (WeixinRuntimeException e) {
			log.error("code happen error.",e);
		} catch (IOException e) {
			log.error("response code happen error.",e);
		} catch (Exception e) {
			log.error("code happen error.",e);
		}
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
	@RequestMapping(value="/{num}/{mobile}/{goodId}/{productType}/{batchId}/{cardTypeId}/cardPayResult",method = { RequestMethod.POST,RequestMethod.GET })
	public void cardPayResult(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Long brandId,
			@PathVariable(value = "num") Integer num,
			@PathVariable(value = "mobile") String mobile,
			@PathVariable(value = "goodId") Integer goodId,
			@PathVariable(value = "productType") String productType,
			@PathVariable(value = "batchId") Integer batchId,
			@PathVariable(value = "cardTypeId") Integer cardTypeId,
			ModelMap model){
			WxMallOrder order = new WxMallOrder();
			String messageIn = "";
			String tradeState="";
			InputStream is = null;
			try{
				Map<String,String> map = CommonUtil.convertString(request);
				BeanUtils.copyProperties(order,map);
				messageIn = IOUtil.inputStream2String(request.getInputStream());
				log.info("腾讯返回支付信息xml："+messageIn);
			
				tradeState = order.getTrade_state();//支付成功状态,0成功
				log.info("读取tx xml信息 start.");
				is = new ByteArrayInputStream(messageIn.getBytes());
				Document doc = new SAXReader().read(is);
				String OpenId=doc.selectSingleNode("/xml/OpenId").getText();
				String AppId=doc.selectSingleNode("/xml/AppId").getText();
				String IsSubscribe=doc.selectSingleNode("/xml/IsSubscribe").getText();
				String NonceStr=doc.selectSingleNode("/xml/NonceStr").getText();
				String AppSignature=doc.selectSingleNode("/xml/AppSignature").getText();
				model.addAttribute("openid", OpenId);//加入session
				log.info("读取tx xml信息 end.");
				logAna.info(";"+DateUtil.getDate()+";cardPayResult;"+mobile+";"+order.getOut_trade_no()+";"+goodId+";"+order.getTotal_fee()+";"+num+";"+order.getTrade_state()+";"+OpenId+";");
				//将支付结果加入bean
				order.setAppId(AppId);
				order.setOpenId(OpenId);
				order.setIsSubscribe("1".equals(IsSubscribe)?true:false);
				order.setNonceTr(NonceStr);
				order.setAppSignature(AppSignature);
				order.setBrandId(brandId);
				order.setOrderState(1L);
				
				response.getWriter().write("success");//返回给腾讯success后，腾讯不再继续通知支付结果
				log.info("通知腾讯success");
			} catch (DocumentException e) {
				log.error("解析支付结果 error.", e);
			}catch(IOException ex){
				log.error("支付结果通知异常.", ex);
			} catch (IllegalAccessException e1) {
				log.error("复制信息异常.",e1);
			} catch (InvocationTargetException e1) {
				log.error("复制信息异常.",e1);
			} catch (Exception e) {
				log.error("code error.", e);
			}finally{
				
				/**2是虚拟卡下发，发货*/
				if("2".equals(productType)){
					order.setDeliverState(0L);
					try{
						WxMallMerchantDict dict = weixinMallMartService.queryMerchantParam(brandId.intValue());
						String token = weixinMallMartService.getAcessTokenByDB(dict);//获取token
						Map<String,String> ma = new HashMap<String,String>();
						ma.put("appid",order.getAppId() );
						ma.put("openid",order.getOpenId() );
						ma.put("transid",order.getTransaction_id() );
						ma.put("out_trade_no", order.getOut_trade_no());
						String rs = CommonUtil.deliverState(token,ma,dict.getAppKey());//立即发货
						String errmsg = JSONObject.fromObject(rs).getString("errmsg");
						log.info("发货:"+errmsg);
						if("ok".equals(errmsg)){
							order.setDeliverState(1L);//发货状态 1 成功 0 失败
							order.setOrderState(2L);//修改订单发货状态
						}
						/**生成虚拟卡*/
						int has = weixinMallMartService.queryMallOrderCard(order.getOut_trade_no());
						if(has==0){
							String cardNo = activeCard(mobile,brandId.toString(),batchId,cardTypeId);
							if(StringUtils.isNotEmpty(cardNo)){
								int inum = weixinMallMartService.insertMallOrderCard(order.getOut_trade_no(), cardNo);
								log.info("虚拟卡绑定卡："+(inum>0));
							}else{
								//绑定不成功要发短信
							}
						}
					} catch (Exception e) {
						log.error("code happen error.",e);
					}
					
				}
				
				/**
				 * 更新订单信息，生成券信息，生成券状态信息，扣除商品库存
				 */
				try {
					buyBusiness(tradeState,order,num,goodId);
				} catch (Exception e) {
					log.error("更新订单信息，生成券信息，生成券状态信息，扣除商品库存 code happen error.",e);
				}
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
						log.error("code happen error.",e);
						e.printStackTrace();
					}
				}
			}
	}
	/**
	 * 减少商品库存 
	 * @param order
	 * @param inflag true 积分消费
	 * @throws Exception 
	 */
	public void buyBusiness(String tradeState,WxMallOrder order,Integer num,Integer goodId) throws Exception{
		if("0".equals(tradeState)){
			//查询成功支付的订单 
			WxMallOrder successOrder =weixinMallMartService.queryIntegralMallOrder(order.getOut_trade_no(),order.getBrandId().intValue());
			int update = weixinMallMartService.updatePayResultInfo(order);//更新数据库支付信息
			log.info("更新订单信息:"+(update>0));
			if( update>0 &&successOrder==null ){
				/**
				 * 减少库存代码
				 */
				boolean kcflag = weixinMallMartService.updateOrderGoodRemain(num,goodId);
				log.info("更新商品库存："+kcflag);
				/**
				 * 发预警短信
				 */
				if(kcflag){
					Map<String,Object> ma = weixinMallMartService.queryGoodInventory(goodId);
					Integer sendsmsnum = (Integer) ma.get("send_sms_num");
					Integer remainnum = (Integer)ma.get("remain_num");
					Integer totalnum = (Integer)ma.get("total_num");
					Integer warningtype = (Integer)ma.get("warning_type");
					Float warningvalue = (Float)ma.get("warning_value");
					if("1".equals(warningtype.toString())&& warningvalue>0){
						warningvalue= totalnum*warningvalue/100;
					}
					String warnmobile = (String)ma.get("warning_mobile");
					String warncontent = (String)ma.get("warning_content");
					if((sendsmsnum==null || sendsmsnum<1)&& warningvalue>0 &&(remainnum<=warningvalue)){
						//发送短信内容
						String jsonpar = "{'mobile':'" + warnmobile + "','content':'" + warncontent
				                 + "','merchant_id':'"+order.getBrandId()+"','source':'8'}";
						
						try {
							CommonUtil.postSendMessage(smsUrl,jsonpar,Constant.KEY.toString());//发送短信
							weixinMallMartService.updateSendSmsNum(goodId);//更新短信次数
						} catch (Exception e) {
							log.error("code happen error.",e);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 在线售卡跳转成功页面
	 * @param request
	 * @param brandId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{weixinId}/{outTradeNo}/goCardSuccessPage", method = { RequestMethod.POST, RequestMethod.GET })
	public String goCardSuccessPage(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@PathVariable(value = "weixinId") String weixinId,
			@PathVariable(value = "outTradeNo") String outTradeNo,
			ModelMap model
			){
		logAna.info(";"+DateUtil.getDate()+";goCardSuccessPage;"+outTradeNo+";");
		log.info("outTradeNo:"+outTradeNo);
		try {
			
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			model.addAttribute("dict", dict);
			WxMallOrder order = weixinMallMartService.queryCardMallOrder(outTradeNo,brandId);
			model.addAttribute("order", order);
			model.addAttribute("brandId", brandId);
			model.addAttribute("weixinId", weixinId);
		} catch (Exception e) {
			log.error("code happen error.",e);
			return "wx-xldPayError";
		}
		return "/coffee/wx-coffeeCardPaySuccess";//跳转购买页面
	}
	/**
	 * 生成虚拟卡，绑定卡关系
	 * @param mobile
	 * @param brandid
	 * @param batchId
	 * @param cardTypeId
	 * @throws Exception 
	 */
	public String activeCard(String mobile,String brandId,Integer batchId,Integer cardTypeId) throws Exception{
		String cardNo ="";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", brandId);
		jsonMap.put("mobile", mobile);
		jsonMap.put("batchId", batchId);//券id
		jsonMap.put("cardtypeId", cardTypeId);
		String input = JSONObject.fromObject(jsonMap).toString();
		log.info("activeCardUrl.json:"+input);
		
		String result = CommonUtil.postSendMessage(activeCardUrl, input, Constant.COUPONKEY);
		JSONObject requestObject = JSONObject.fromObject(result);
		String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
		JSONObject res=JSONObject.fromObject(data);
		if (res != null) { 
			Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			if (success) {// 成功
				cardNo = res.getString("cardNo"); // 返回信息
			}
			log.info("result message:"+message+"\t cardNo:"+cardNo);
		}
		return cardNo;
	}
}
