package com.sunny.test;

import java.io.File;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.yazuo.weixin.refund.ClientResponseHandler;
import com.yazuo.weixin.refund.RequestHandler;
import com.yazuo.weixin.refund.TenpayHttpClient;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.HttpClientCommonSSL;

public class TestClass {
	@Test
	public void testTuikuan() throws Exception{
		//商户号 
	    String partner = "1219692001";
	    //密钥 
	    String key = "0d9be069458c0a9a390e710514f7f1bf";
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
	    reqHandler.setKey(key);
	    reqHandler.setGateUrl("https://mch.tenpay.com/refundapi/gateway/refund.xml");
	    
	    //-----------------------------
	    //设置接口参数
	    //-----------------------------
	    reqHandler.setParameter("partner", partner);	
	    reqHandler.setParameter("out_trade_no", " ");	
	    reqHandler.setParameter("transaction_id", "1219692001201409013226114037");
	    reqHandler.setParameter("out_refund_no", "qwteqwyeqywe12312393");	
	    reqHandler.setParameter("total_fee", "1");	
	    reqHandler.setParameter("refund_fee", "1");
	    reqHandler.setParameter("op_user_id", partner);	
	    //操作员密码,MD5处理
	    reqHandler.setParameter("op_user_passwd", "111111");	
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
	    //设置个人(商户)证书
	    httpClient.setCertInfo(new File("e:/1219692001.pfx"), "1219692001");
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
	    	resHandler.setKey(key);
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
	    	
	    	System.out.println("商户退款单号"+out_refund_no+"的退款状态是："+refund_status);
	    		

	    	} else {
	    		//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
	    		System.out.println("验证签名失败或业务错误");
	    		System.out.println("retcode:" + resHandler.getParameter("retcode")+
	    	    	                    " retmsg:" + resHandler.getParameter("retmsg"));
	    	}	
	    } else {
	    	System.out.println("后台调用通信失败");   	
	    	System.out.println(httpClient.getResponseCode());
	    	System.out.println(httpClient.getErrInfo());
	    	//有可能因为网络原因，请求已经处理，但未收到应答。
	    }
	    
	    //获取debug信息,建议把请求、应答内容、debug信息，通信返回码写入日志，方便定位问题
	    System.out.println("http res:" + httpClient.getResponseCode() + "," + httpClient.getErrInfo());
	    System.out.println("req url:" + requestUrl);
	    System.out.println("req debug:" + reqHandler.getDebugInfo());
	    System.out.println("res content:" + rescontent);
	    System.out.println("res debug:" + resHandler.getDebugInfo());
	    
	}
	
	@Test
	public void testTuikuanyazuo() throws Exception{
		//商户号 
	    String partner = "1220627401";
	    //密钥 
	    String key = "623a33e9235c95034b12309b8efe99fe";
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
	    reqHandler.setKey(key);
	    reqHandler.setGateUrl("https://mch.tenpay.com/refundapi/gateway/refund.xml");
	    
	    //-----------------------------
	    //设置接口参数
	    //-----------------------------
	    reqHandler.setParameter("partner", partner);	
	    reqHandler.setParameter("out_trade_no", " ");	
	    reqHandler.setParameter("transaction_id", "1220627401201410246061821908");
	    reqHandler.setParameter("out_refund_no", "1220627401201410246061821908");	
	    reqHandler.setParameter("total_fee", "100");	
	    reqHandler.setParameter("refund_fee", "100");
	    reqHandler.setParameter("op_user_id", partner);	
	    //操作员密码,MD5处理
	    reqHandler.setParameter("op_user_passwd", "yazuo@zhao");	
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
	    //设置个人(商户)证书
	    httpClient.setCertInfo(new File("e:/1220627401.pfx"), "1220627401");
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
	    	resHandler.setKey(key);
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
	    	
	    	System.out.println("商户退款单号"+out_refund_no+"的退款状态是："+refund_status);
	    		

	    	} else {
	    		//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
	    		System.out.println("验证签名失败或业务错误");
	    		System.out.println("retcode:" + resHandler.getParameter("retcode")+
	    	    	                    " retmsg:" + resHandler.getParameter("retmsg"));
	    	}	
	    } else {
	    	System.out.println("后台调用通信失败");   	
	    	System.out.println(httpClient.getResponseCode());
	    	System.out.println(httpClient.getErrInfo());
	    	//有可能因为网络原因，请求已经处理，但未收到应答。
	    }
	    
	    //获取debug信息,建议把请求、应答内容、debug信息，通信返回码写入日志，方便定位问题
	    System.out.println("http res:" + httpClient.getResponseCode() + "," + httpClient.getErrInfo());
	    System.out.println("req url:" + requestUrl);
	    System.out.println("req debug:" + reqHandler.getDebugInfo());
	    System.out.println("res content:" + rescontent);
	    System.out.println("res debug:" + resHandler.getDebugInfo());
	    
	}
	
	@Test
	public void testRefundQuery() throws Exception{
		//商户号 
		String partner = "1220627401";
	    //密钥 
	    String key = "623a33e9235c95034b12309b8efe99fe";
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
	    reqHandler.setKey(key);
	    reqHandler.setGateUrl("https://gw.tenpay.com/gateway/normalrefundquery.xml");
	    
	    //-----------------------------
	    //设置接口参数
	    //-----------------------------
	    reqHandler.setParameter("partner", partner);	
	    reqHandler.setParameter("out_trade_no", "");	
	    reqHandler.setParameter("transaction_id", "1220627401201409163221408994");
	    reqHandler.setParameter("out_refund_no", "tk1409161057174ikSBwcBFm6yKsSL");	
	    //reqHandler.setParameter("refund_id", "1111900000109201102240360176");

	    //-----------------------------
	    //设置通信参数
	    //-----------------------------
	    //设置请求返回的等待时间
	    httpClient.setTimeOut(5);	
	    
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
	    	resHandler.setKey(key);
	    	   	
	    	//获取返回参数
	    	String retcode = resHandler.getParameter("retcode");
	    	
	    	//判断签名及结果
	    	//只有签名正确并且retcode为0才是请求成功
	    	if(resHandler.isTenpaySign()&& "0".equals(retcode)) {
	    		//取结果参数做业务处理
			//退款笔数
			String refund_count = resHandler.getParameter("refund_count");
			
			System.out.println("退款笔数:" + refund_count);
			int count = Integer.parseInt(refund_count);
			//每笔退款详情
			/*退款状态	refund_status	
				4，10：退款成功。
				3，5，6：退款失败。
				8，9，11:退款处理中。
				1，2: 未确定，需要商户原退款单号重新发起。
				7：转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
				*/
			for(int i=0; i<count; i++){ 
			    String refund_state_n = "refund_state_" + Integer.toString(i);
			    String out_refund_no_n = "out_refund_no_" + Integer.toString(i);
			    String refund_fee_n = "refund_fee_" + Integer.toString(i);
			    
			    System.out.println("第" + Integer.toString(i) + "笔：" + refund_state_n + "=" + resHandler.getParameter(refund_state_n) 
			        + "," + out_refund_no_n + "=" + resHandler.getParameter(out_refund_no_n) 
			        + "," + refund_fee_n + "=" + resHandler.getParameter(refund_fee_n));
			}	
	    	} else {
	    		//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
	    		System.out.println("验证签名失败或业务错误");
	    		System.out.println("retcode:" + resHandler.getParameter("retcode")+
	    	    	                    " retmsg:" + resHandler.getParameter("retmsg"));
	    		System.out.println("retcode:" + resHandler.getParameter("retcode")+
	    	    	                    " retmsg:" + resHandler.getParameter("retmsg"));
	    	}	
	    } else {
	    	System.out.println("后台调用通信失败");   	
	    	System.out.println(httpClient.getResponseCode());
	    	System.out.println(httpClient.getErrInfo());
	    	//有可能因为网络原因，请求已经处理，但未收到应答。
	    }
	    
	    //获取debug信息,建议把请求、应答内容、debug信息，通信返回码写入日志，方便定位问题
	    System.out.println("http res:" + httpClient.getResponseCode() + "," + httpClient.getErrInfo());
	    System.out.println("req url:" + requestUrl);
	    System.out.println("req debug:" + reqHandler.getDebugInfo());
	    System.out.println("res content:" + rescontent);
	    System.out.println("res debug:" + resHandler.getDebugInfo());
	}
	
	
	@Test
	public void testLength(){
		String a="fy7oUrbi3iEOKNxa";
//		System.out.println(a.length());
		JSONObject obj = new JSONObject();
		obj.put("partner", "1219692001");
		obj.put("appKey", "0d9be069458c0a9a390e710514f7f1bf");
		obj.put("outTradeNo", "CB201408211131462G8VnLHHOqXlKLFJ");
		obj.put("transId", "1219692001201408213360861547");
		obj.put("refundNo", "Ct201408211131462G8VnLHHOqXlKLFJ");
		obj.put("totalFee", "1");
		obj.put("refundFee", "1");
		obj.put("password", "111111");
		System.out.println(obj.toString());
		
		System.out.println(DateUtil.get12Date());
		System.out.println(1/100);
	}
	/***
	 * 发送消息模版
	 * @throws Exception
	 */
	@Test
	public void testTemplate() throws Exception{
		String token = CommonUtil.getToken("wx3da045bb2bedd556","f12d805f3717b52ec9c840ff5571cd9d");
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
		JSONObject obj = new JSONObject();
		JSONObject sobj = new JSONObject();
		obj.put("touser", "o9futjtAqOIFkZbczIJ67Q1vCdHg");
		obj.put("template_id", "BePL5xCZiPdx5o143nk2V_cztmSxvtBYbVgxeJ3xSoY");
		obj.put("url", "http://124.42.38.70/yazuo-weixin/weixin/phonePage/membershipCard.do?"+"brandId="+15+"&weixinId=o9futjtAqOIFkZbczIJ67Q1vCdHg");
		sobj.put("productType","{'value':'商品名','color':'#173177'}");
		sobj.put("name","{'value':'充值卡','color':'#173177'}");
		sobj.put("number","{'value':'1','color':'#173177'}");
		sobj.put("expDate","{'value':'2014-08-29','color':'#173177'}");
		sobj.put("remark","{'value':'花费50元，50积分','color':'#173177'}");
		obj.put("data", sobj);
		System.out.println(obj.toString());
		HttpClientCommonSSL.commonPostStream(url, obj.toString());
	}
	/***
	 * 消费通知
	 * @throws Exception
	 */
	@Test
	public void testTemplate1() throws Exception{
		String token = CommonUtil.getToken("wxf4c931a2bb7d40ef","7c53fe4ae3fce91ae716bd5b71629910");
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
		JSONObject obj = new JSONObject();
		JSONObject sobj = new JSONObject();
		obj.put("touser", "o9futjtAqOIFkZbczIJ67Q1vCdHg");
		obj.put("template_id", "BePL5xCZiPdx5o143nk2V_cztmSxvtBYbVgxeJ3xSoY");
		obj.put("url", "http://124.42.38.70/yazuo-weixin/weixin/phonePage/membershipCard.do?"+"brandId="+15+"&weixinId=o9futjtAqOIFkZbczIJ67Q1vCdHg");
		sobj.put("pay","{'value':'50元，积分消费50积分，优惠券一张','color':'#173177'}");
		sobj.put("address","{'value':'新辣道上地店','color':'#173177'}");
		sobj.put("time","{'value':'2014-08-29 15:15:15','color':'#173177'}");
		sobj.put("remark","{'value':'如有疑问，请咨询13912345678。','color':'#173177'}");
		obj.put("data", sobj);
		System.out.println(obj.toString());
		HttpClientCommonSSL.commonPostStream(url, obj.toString());
	}
	/***
	 * 优惠券到账通知
	 * @throws Exception
	 */
	@Test
	public void testTemplate2() throws Exception{
		String token = CommonUtil.getToken("wxf4c931a2bb7d40ef","7c53fe4ae3fce91ae716bd5b71629910");
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
		JSONObject obj = new JSONObject();
		JSONObject sobj = new JSONObject();
		obj.put("touser", "oUDOTjsh5yRe2WWOXaAplrX7qXf4");
		obj.put("template_id", "g5MEB8ws9cTiMSaIC3nkmIQPu1Vb9ChGqIOtV73kpdg");
		obj.put("url", "www.baidu.com");
		sobj.put("first","{'value':'50元，积分消费50积分，优惠券一张','color':'#173177'}");
		sobj.put("orderTicketStore","{'value':'新辣道上地店','color':'#173177'}");
		sobj.put("orderTicketRule","{'value':'2014-08-29 15:15:15','color':'#173177'}");
		sobj.put("remark","{'value':'如有疑问，请咨询13912345678。','color':'#173177'}");
		obj.put("data", sobj);
		System.out.println(obj.toString());
		HttpClientCommonSSL.commonPostStream(url, obj.toString());
	}
	@Test
	public void testTemplate22() throws Exception{
		String token = CommonUtil.getToken("wxf4c931a2bb7d40ef","7c53fe4ae3fce91ae716bd5b71629910");
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
		JSONObject obj = new JSONObject();
		JSONObject sobj = new JSONObject();
		obj.put("touser", "oUDOTjsh5yRe2WWOXaAplrX7qXf4");
		obj.put("template_id", "lsVK83WvzYGh_JQHMM8XNjheZZ8xbZDtHO4gUMadfRA");
		obj.put("url", "http://acc.yazuosoft.com/evaluation.php?action=index&merchantId=9355&transwaterid=305507152");
		obj.put("topcolor", "#ff6600");
		sobj.put("productType","{'value':'消费信息','color':'#173177'}");
		sobj.put("name","{'value':'现金消费30.00元，获得3.00积分，剩余12.30积分。','color':'#173177'}");
		sobj.put("accountType","{'value':'会员卡号','color':'#173177'}");
		sobj.put("account","{'value':'6201200202123018','color':'#173177'}");
		sobj.put("time","{'value':'2014-10-15 16:43:04','color':'#173177'}");
		sobj.put("remark","{'value':'','color':'#173177'}");
		obj.put("data", sobj);
		System.out.println(obj.toString());
		System.out.println(HttpClientCommonSSL.commonPostStream(url, obj.toString()));
	}
}
