package com.yazuo.weixin.external;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.refund.ClientResponseHandler;
import com.yazuo.weixin.refund.RequestHandler;
import com.yazuo.weixin.refund.TenpayHttpClient;
import com.yazuo.weixin.service.WeixinExternalService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.HttpClientCommonSSL;

/**
* @ClassName WeixinExternalController
* @Description 对外接口
* @author sundongfeng@yazuo.com
* @date 2014-6-30 上午10:54:48
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/external")
public class WeixinExternalController {
	private static final Log log = LogFactory.getLog("external");
	@Value("#{propertiesReader['caFileIp']}")
	private String caFileIp;
	@Value("#{propertiesReader['certificateKeyUrl']}")
	private String certificateKeyUrl;
	@Value("#{propertiesReader['wxrefundUrlv2']}")
	private String wxrefundUrlv2;
	@Value("#{propertiesReader['wxrefundUrlv3']}")
	private String wxrefundUrlv3;
	@Value("#{propertiesReader['wxrefundquery']}")
	private String wxrefundquery;
	@Autowired
	private WeixinExternalService weixinExternalService;
	@Autowired
	private WeixinMallMartService weixinMallMartService;
	/**
	 * 获取openid，对接咖啡陪你app接口
	 * @param request
	 * @param response
	 * @param brandId
	 * @param mobile
	 * @param key
	 */
	@RequestMapping(value="/queryWeixinId",method = { RequestMethod.POST,RequestMethod.GET })
	public void queryWeixinId(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "brandId") String brandId,
			@RequestParam(value = "mobile") String mobile,
			@RequestParam(value = "key") String key){
		try {
			response.setCharacterEncoding("utf-8");
			String json="";
			if("10000".equals(key)){
				String weixinId = weixinExternalService.queryOpenId(mobile, brandId);
				if(StringUtils.isNotBlank(weixinId)){
					json = "{'success':true,'message':'查询成功','openid':'"+weixinId+"'}";
				}else{
					json="{'success':false,'message':'无此商户对应的手机号会员'}";
				}
			}else{
				json= "{'success':false,'message':'key值不匹配'}";
			}
			response.getWriter().write(json);
		
		} catch (Exception e) {
			log.error("code happen error.",e);
			String error= "{'success':false,'message':'接口异常'}";
			try {
				response.getWriter().write(error);
			} catch (IOException e1) {
				log.error("code happen error.",e1);
			}
		}finally{
			try {
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				log.error("code happen error.",e);
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 微信商城退款接口
	 * @param request
	 * @param response
	 * @param data
	 * @param key
	 */
	@RequestMapping(value="/refundFromShop",method = { RequestMethod.POST,RequestMethod.GET })
	public void refundFromShop(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "data") String data,
			@RequestParam(value = "key") String key
			){
		try {
			response.setCharacterEncoding("utf-8");
			JSONObject resJson = new JSONObject();
			JSONObject obj = JSONObject.fromObject(data);
			String brandId = obj.getString("brandId");
			WxMallMerchantDict dict = weixinMallMartService.queryMerchantParam(Integer.parseInt(brandId));
			if("10000".equals(key)){
				if("2".equals(String.valueOf(dict.getV2_v3()))){
				//商户号 
			    String partner = obj.getString("partner");
			    //密钥 
			    String appKey = dict.getPartnerKey(); //"0d9be069458c0a9a390e710514f7f1bf"
			    //创建查询请求对象
			    RequestHandler reqHandler = new RequestHandler(null, null);
			    //通信对象
			    TenpayHttpClient httpClient = new TenpayHttpClient();
			    //应答对象
			    ClientResponseHandler resHandler = new ClientResponseHandler();
			    
			    //-----------------------------
			    //设置请求参数
			    //-----------------------------
			    reqHandler.init();
			    reqHandler.setKey(appKey);
			    reqHandler.setGateUrl(wxrefundUrlv2.trim());
			    //-----------------------------
			    //设置接口参数
			    //-----------------------------
			    reqHandler.setParameter("partner", partner);	
			    reqHandler.setParameter("out_trade_no", obj.getString("outTradeNo"));	
			    reqHandler.setParameter("transaction_id", obj.getString("transId"));
			    reqHandler.setParameter("out_refund_no", obj.getString("refundNo"));	
			    reqHandler.setParameter("total_fee", obj.getString("totalFee"));	
			    reqHandler.setParameter("refund_fee", obj.getString("refundFee"));
			    reqHandler.setParameter("op_user_id", partner);	
			    //操作员密码,MD5处理
			    reqHandler.setParameter("op_user_passwd", dict.getPartnerPass());	
			    reqHandler.setParameter("recv_user_id", "");	
			    reqHandler.setParameter("reccv_user_name", "");
			    
			    //-----------------------------
			    //设置通信参数
			    //-----------------------------
			    //设置请求返回的等待时间
			    httpClient.setTimeOut(5);	
			    
			    String cafile = this.getClass().getClassLoader().getResource("cacert.pem").getPath();
			    //设置ca证书
			    httpClient.setCaInfo(new File(cafile));
			    URL httpurl=null;
			    if(dict.getCertificateUrl().startsWith("M00/")){
			    	httpurl= new URL(certificateKeyUrl.trim()+dict.getCertificateUrl());
			    }else{
			    	httpurl= new URL(caFileIp+dict.getCertificateUrl());
			    }
			    String projectPath = this.getClass().getResource("/").getPath();
			    File fw = new File(projectPath+"tmp/ca_"+brandId+".pfx");   
			    if(!fw.exists()){
			    	FileUtils.copyURLToFile(httpurl, fw);
			    }
			    //设置个人(商户)证书
			    httpClient.setCertInfo(fw, partner);//caFileIp+dict.getCertificateUrl() 
			    //"e:/"+partner+".pfx"
			    //设置发送类型POST
			    httpClient.setMethod("POST");     
			    
			    //设置请求内容
			    String requestUrl = reqHandler.getRequestURL();
			    httpClient.setReqContent(requestUrl);
			    String rescontent = "null";

			    //后台调用
			    if(httpClient.call()) {
			    	//设置结果参数
			    	rescontent = httpClient.getResContent();
			    	resHandler.setContent(rescontent);
			    	resHandler.setKey(appKey);
			    	//获取返回参数
			    	String retcode = resHandler.getParameter("retcode");
			    	
			    	//判断签名及结果
			    	if(resHandler.isTenpaySign()&& "0".equals(retcode)) {
			    	/*退款状态	refund_status	
						4，10：退款成功。
						3，5，6：退款失败。
						8，9，11:退款处理中。
						1，2: 未确定，需要商户原退款单号重新发起。
						7：转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
						*/
				    	String refund_status=resHandler.getParameter("refund_status");
				    	String out_refund_no=resHandler.getParameter("out_refund_no");
				    	String refund_id=resHandler.getParameter("refund_id");
				    	
				    	log.info("商户退款单号"+out_refund_no+"的退款状态是："+refund_status+"银行退款单号:"+refund_id);
			    		log.info("res content:" + rescontent);
			    		resJson.put("success", true);
			    		resJson.put("state", refund_status);
			    		resJson.put("refundNo", out_refund_no);
			    		
			    	} else {
			    		log.info("验证签名失败或业务错误");
			    		log.info("retcode:" + resHandler.getParameter("retcode")+
			    				" retmsg:" + resHandler.getParameter("retmsg"));
			    		String retmsg = resHandler.getParameter("retmsg");
			    		resJson.put("success", false);
						resJson.put("message", retmsg);
			    		//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
			    	}	
			    } else {
			    	resJson.put("success", false);
					resJson.put("message", "后台调用通信失败");
			    	log.info("后台调用通信失败");   	
			    	log.info(httpClient.getResponseCode());
			    	log.info(httpClient.getErrInfo());
			    	//有可能因为网络原因，请求已经处理，但未收到应答。
			    }
				
				}else if("3".equals(String.valueOf(dict.getV2_v3()))){
					String appid =dict.getAppId();
					String mch_id=obj.getString("partner");
					String nonce_str =CommonUtil.CreateNoncestr();
					String transaction_id =obj.getString("transId");
					String out_refund_no =obj.getString("refundNo");
					String out_trade_no =obj.getString("outTradeNo");
					String total_fee =obj.getString("totalFee");
					String refund_fee = obj.getString("refundFee");
					String op_user_id =mch_id;
					SortedMap<String, String> map = new TreeMap<String,String>();
					map.put("appid", appid);
					map.put("mch_id", mch_id);
					map.put("nonce_str", nonce_str);
					map.put("transaction_id", transaction_id);
					map.put("out_trade_no", out_trade_no);
					map.put("out_refund_no", out_refund_no);
					map.put("total_fee", total_fee);
					map.put("refund_fee", refund_fee);
					map.put("op_user_id", op_user_id);
					String sign = weixinMallMartService.createSign(map,dict.getPartnerKey());
					map.put("sign", sign);
					String xml=CommonUtil.ArrayToXml(map);
					resJson = weixinMallMartService.refundRequest(wxrefundUrlv3.trim(),mch_id,xml,dict);
				}
			}else{
				resJson.put("success", false);
				resJson.put("message", "key值不匹配");
			}
			log.info(resJson.toString());
			response.getWriter().print(resJson.toString());
		
		} catch (Exception e) {
			log.error("code happen error.",e);
			JSONObject errorResJson =new JSONObject();
			errorResJson.put("success", false);
			errorResJson.put("message", "接口异常");
			try {
				response.getWriter().print(errorResJson.toString());
			} catch (IOException e1) {
				log.error("code happen error.",e1);
			}
		}finally{
			try {
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				log.error("code happen error.",e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 微信商城退款接口
	 * @param request
	 * @param response
	 * @param data
	 * @param key
	 */
	@RequestMapping(value="/refundQuery",method = { RequestMethod.POST,RequestMethod.GET })
	public void refundQuery(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "data") String data,
			@RequestParam(value = "key") String key
			){
		try {
			response.setCharacterEncoding("utf-8");
			JSONObject resJson = new JSONObject();
			if("10000".equals(key)){
				JSONObject obj = JSONObject.fromObject(data);
				resJson = weixinMallMartService.queryRefundState(obj);
			}else{
				resJson.put("success", false);
				resJson.put("message", "key值不匹配");
			}
			log.info(resJson.toString());
			response.getWriter().print(resJson.toString());
		
		} catch (Exception e) {
			log.error("code happen error.",e);
			JSONObject errorResJson =new JSONObject();
			errorResJson.put("success", false);
			errorResJson.put("message", "接口异常");
			try {
				response.getWriter().print(errorResJson.toString());
			} catch (IOException e1) {
				log.error("code happen error.",e1);
			}
		}finally{
			try {
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				log.error("code happen error.",e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 咖啡陪你发货接口
	 * @param request
	 * @param response
	 * @param data
	 * @param key
	 */
	@RequestMapping(value="/deliverShop",method = { RequestMethod.POST,RequestMethod.GET })
	public void deliverShop(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "data") String data,
			@RequestParam(value = "key") String key
			){
			try {
				if("10000".equals(key)){
					JSONObject obj = JSONObject.fromObject(data);
					String brandId = obj.getString("brandId");
					WxMallMerchantDict dict = weixinMallMartService.queryMerchantParam(Integer.parseInt(brandId));
					String token = weixinMallMartService.getAcessTokenByDB(dict);//获取access_token
					Map<String,String> ma = new HashMap<String,String>();
					ma.put("appid",obj.getString("appid"));
					ma.put("openid",obj.getString("openid"));
					ma.put("transid",obj.getString("transid"));
					ma.put("out_trade_no", obj.getString("orderno"));
					String rs = deliverState(token,ma,obj.getString("appkey"));//立即发货
					response.getWriter().print(rs);
				}else{
					JSONObject resJson = new JSONObject();
					resJson.put("success", false);
					resJson.put("message", "key值不匹配");
					log.info(resJson.toString());
					response.getWriter().print(resJson.toString());
				}
			} catch (Exception e) {
				log.error("code happen error.",e);
				JSONObject errorResJson =new JSONObject();
				errorResJson.put("success", false);
				errorResJson.put("message", "接口异常");
				try {
					response.getWriter().print(errorResJson.toString());
				} catch (IOException e1) {
					log.error("code happen error.",e1);
				}
			}finally{
				try {
					response.getWriter().flush();
					response.getWriter().close();
				} catch (IOException e) {
					log.error("code happen error.",e);
					e.printStackTrace();
				}
			}
		
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
