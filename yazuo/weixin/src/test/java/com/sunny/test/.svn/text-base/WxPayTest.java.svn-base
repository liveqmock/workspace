package com.sunny.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.MailUtil;
import com.yazuo.weixin.vo.WxOrder;
import com.yazuo.weixin.vo.WxPayCard;
import com.yazuo.weixin.vo.WxPayCouponState;


/**
* @ClassName WxPayTest
* @Description 
* @author sundongfeng@yazuo.com
* @date 2014-6-11 上午8:41:53
* @version 1.0
*/
public class WxPayTest {
	private ApplicationContext cxt;
	private WxOrder wxorder=null;
	JdbcTemplate jdbc=null;
	Logger log = Logger.getLogger(this.getClass());
	@Before
	public void setUp(){
		 wxorder = new WxOrder();
		cxt = new FileSystemXmlApplicationContext(new String[]{"target/classes/spring-weixin-datasource.xml","target/classes/spring-weixin-servlet.xml"});
		jdbc = (JdbcTemplate) cxt.getBean("jdbcTemplate");
	}
	
	@Test
	public void testSTr(){
		String aa = "wo | ni";
		aa = aa.replace("|", "，");
		System.out.println(aa);
	}
	
	@Test
	public  void queryOpenid(){
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxc23ca14780a032d9&secret=0bd2790ed4da40371960d5d994805488&code=0017aa0e1bc3aa5f32479e3e251e3c44&grant_type=authorization_code";
		try {
			System.out.println(CommonUtil.postMessage(url));
			System.out.println(CommonUtil.postMessage(url,""));
		} catch (WeixinRuntimeException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryOpenId()throws WeixinRuntimeException{
		String sql = "select open_id from weixin.weixin_pay_card_bind where mobile=? and brand_id=? limit 1";
		String open_id = jdbc.queryForObject(sql, String.class, new Object[]{"18510335354","1119"});
		System.out.println("openId:"+open_id);
	}
	

	@Test
	public void testWxUpdate(){
		main1();
		
		String sql =  "update weixin.weixin_order "+
			    "set open_id = ?,notify_id = ?,bank_billno =?,sign = ?,transaction_id = ?,product_fee = ?,   "+
				"transport_fee = ?,discount = ?,total_fee = ?,time_end =?,trade_state = ?,input_charset = ?, "+
				"pay_info = ?,is_subscribe = ?,app_signature = ?                                             "+
			    "where  app_id = ? and partner =? and out_trade_no =?                      ";
		int num = jdbc.update(sql, wxorder.getOpenId(),wxorder.getNotify_id(),wxorder.getBank_billno(),
				wxorder.getSign(),wxorder.getTransaction_id(),wxorder.getProduct_fee(),
				wxorder.getTransport_fee(),wxorder.getDiscount(),wxorder.getTotal_fee(),wxorder.getTime_end(),wxorder.getTrade_state(),
				wxorder.getInput_charset(),wxorder.getPayInfo(),wxorder.getIsSubscribe(),wxorder.getAppSignature(),
				wxorder.getAppId(),wxorder.getPartner(),wxorder.getOut_trade_no());
		
	}
	
	public  void main1( ) {
		InputStream is = null;
		String xml = "<xml><OpenId><![CDATA[o8-68jmtej4mqj8-PGaV3dJwaUEk]]></OpenId><AppId><![CDATA[wxc23ca14780a032d9]]></AppId><IsSubscribe>1</IsSubscribe><TimeStamp>1402048967</TimeStamp><NonceStr><![CDATA[uByG7vfhXtIKeovb]]></NonceStr><AppSignature><![CDATA[430ab4eb3794b358ba29c26bf0965ed4156ef903]]></AppSignature><SignMethod><![CDATA[sha1]]></SignMethod></xml>";
		try {
			is = new ByteArrayInputStream(xml.getBytes());
			Document doc = new SAXReader().read(is);
			String OpenId=doc.selectSingleNode("/xml/OpenId").getText();
			String AppId=doc.selectSingleNode("/xml/AppId").getText();
			String IsSubscribe=doc.selectSingleNode("/xml/IsSubscribe").getText();
			String NonceStr=doc.selectSingleNode("/xml/NonceStr").getText();
			String AppSignature=doc.selectSingleNode("/xml/AppSignature").getText();
			wxorder.setOut_trade_no("YA2vnOTl3fg62Cjd");
			wxorder.setPartner("1218431801");
			wxorder.setAppId(AppId);
			wxorder.setOpenId(OpenId);
			wxorder.setIsSubscribe(IsSubscribe);
			wxorder.setNonceTr(NonceStr);
			wxorder.setAppSignature(AppSignature);
		} catch (DocumentException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Test
	public void testCopy() {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("bank_billno", "201406062171388");
		map.put("total_fee", "1");
		map.put("out_trade_no", "kBrJUEVQtaWHpgZ8");
		map.put("transaction_id", "1218431801201406063402760891");
		WxOrder wxorder = new WxOrder();
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(wxorder,map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		System.out.println(wxorder.getTotal_fee());
		System.out.println(wxorder.getTransaction_id());
	}
	
	@Test
	public void testJson(){
		String a = "{'list':['123','456']}";
		JSONObject json = JSONObject.fromObject(a);
		JSONArray list = json.getJSONArray("list");
		Object[] aList = list.toArray();
		for(Object ab:aList){
			System.out.println(ab);
		}
	}
	/**
	 * 获取券信息
	 */
	@Test
	public void testInterfaceLb1(){
		String optainTicketInfoUrl="http://192.168.236.201:8083/crmapi/couponnew/queryCouponTemplateListByActiveId.do";
		String activeId="4447";
		String xldKey="700030";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", "1119");
		jsonMap.put("activeId", activeId);
		String input = JSONObject.fromObject(jsonMap).toString();
		
		String result="";
		try {
			result = CommonUtil.postSendMessage(optainTicketInfoUrl, input, xldKey);
		} catch (WeixinRuntimeException e1) {
			log.error("code happen error.",e1);
			e1.printStackTrace();
		}
		log.info("获取券信息结果:"+result);
		
		JSONObject requestObject = JSONObject.fromObject(result);
		try {
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("result message:"+message);
				if (success) {// 成功
					JSONArray array = res.getJSONArray("couponList");
					JSONObject jsonObject = (JSONObject) array.get(0);
//					WxCardInfo cardInfo = (WxCardInfo) JSONObject.toBean(jsonObject, WxCardInfo.class);
					String a=jsonObject.getString("couponName");
					log.info(a.toString());
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取券code
	 * @throws WeixinRuntimeException 
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testInterfaceLb2() throws WeixinRuntimeException, UnsupportedEncodingException{
		int num=1; ////////////////////////////数量
		String cardId="18077";//券id
		String mobile = "13622058980";
		String outTradeNO="OBmyNl8j6ALla7ZB";
		String tranid = "1218431801201407013229710531";
		String OpenId="o8-68jv_xDMiC2vLVKrxh_5M06xY";
		
		String optainTicketsUrl="http://58.83.233.47:8081/crmapi/couponnew/buyCouponByWeixin.do";
//		String optainTicketsUrl="http://crmapi.yazuoyw.com:8081/crmapi/couponnew/buyCouponByWeixin.do";
		String brandId="1119";
		String xldKey="700030";
		String partner="1218431801";
		boolean isHas = queryHasTicketsCard(outTradeNO);
		log.info("是否有券了:"+isHas);
		log.info("生成券信息，入库，调用李斌接口获取券的信息.");
		List<WxPayCard> cardList = new ArrayList<WxPayCard>();
		if(StringUtils.isNotBlank(mobile)&& !isHas){ //无券信息才执行
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
				log.info("result message:"+message);
				if (success) {// 成功
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
						card.setPartnerId(partner);
						cardList.add(card);
					}
					log.info("返回card_no:"+sb);
					log.info("生成券数量:"+cardList.size());
					//生成券信息入库
					inserCardInfo(cardList);//插入绑定卡信息
					WxPayCouponState pcsvo = new WxPayCouponState();//记录券状态
					pcsvo.setMobile(mobile);
					pcsvo.setTotalFee(159L);
					pcsvo.setCounter(new Long(num));
					pcsvo.setCardId(cardId);
					pcsvo.setTradeState("0");
					pcsvo.setTransactionId(tranid);
					pcsvo.setPartnerId(brandId);
					pcsvo.setOpenId(OpenId);
					pcsvo.setOutTradeNo(outTradeNO);
					pcsvo.setCardState("1");
					insertCouponState(pcsvo);//插入券状态
				}
			}
		}
	}
	
	public boolean queryHasTicketsCard(String outTradeNo){
		String sql = "select count(*) cn from weixin.weixin_pay_card_bind a where a.out_trade_no=? and a.create_time>CURRENT_DATE";
		int cn = jdbc.queryForInt(sql,outTradeNo);
		return cn>0?true:false;
	}
	public void inserCardInfo(final List<WxPayCard> list)throws WeixinRuntimeException{
		String sql = "insert into weixin.weixin_pay_card_bind"+
				" ( mobile, open_id,                  "+
			    "  card_id, res_card_no, out_trade_no,  "+
			    "  create_time, partner_id, brand_id  "+
			    "  ) values (?,?,?,?,?,CURRENT_TIMESTAMP,?,?)         ";
		 jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				WxPayCard bean =  list.get(i);
				ps.setString(1,bean.getMobile());
				ps.setString(2,bean.getOpenId());
				ps.setString(3,bean.getCardId());
				ps.setString(4,bean.getRes_card_no());
				ps.setString(5,bean.getOutTradeNo());
				ps.setString(6,bean.getPartnerId());
				ps.setString(7,bean.getBrandId());
			}
			
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	/**
	 * 插入券状态表
	 * @param state
	 * @return
	 * @throws WeixinRuntimeException
	 */
	public void insertCouponState(WxPayCouponState state)throws WeixinRuntimeException{
		String sql =   " insert into weixin.weixin_pay_coupon_state ( mobile, total_fee,"+ 
					   "   counter, card_id, open_id,                            "+
					   "   trade_state, card_state, create_time,                 "+
					   "   transaction_id, out_trade_no, partner_id  )           "+
					   " values ( ?,?, ?, ?,?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?) ";
		int cn = jdbc.update(sql, state.getMobile(),state.getTotalFee(),state.getCounter(),
				state.getCardId(),state.getOpenId(),state.getTradeState(),state.getCardState(),
				state.getTransactionId(),state.getOutTradeNo(),state.getPartnerId()	);
		System.out.println(cn);
	}
	
	/**
	 * 生成二级跳转
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testRedirectUrl() throws UnsupportedEncodingException{
//		String url = "http://124.42.38.70/yazuo-weixin/weixin/1119/goRecharge.do?showwxpaytitle=1";
		String url= "http://124.42.38.74/yazuo-weixin/weixin/1119/mallMartIndex.do?showwxpaytile=1";
		String result =" https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf4c931a2bb7d40ef&redirect_uri="+url+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		System.out.println(result);
	}
	/**
	 * 测试邮件
	 */
	@Test
	public void sendMail() {
		String mailUrl="http://192.168.236.201:8083/crmapi/mail/sendMail.do";
		String mail="sundongfeng@yazuo.com,zhaochuang@yazuo.com";
		String subject="券发送失败";
		String key="700030";
		WxPayCouponState obj=new WxPayCouponState();
		obj.setMobile("18510335354");
		obj.setCardId("6051");
		obj.setCounter(123L);
		obj.setTotalFee(123L);
		obj.setOutTradeNo("sdfasdfsdfasdfa");
		obj.setTransactionId("sdfassadffa123123");
		obj.setPartnerId("1119");
		obj.setOpenId("objjjjjjsdfasfajjjoke");
		String[] mails= mail.split(",");
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("mails", mails);
		jsonMap.put("subject", subject);
		String content = "\n用户手机:"+obj.getMobile()+"\n"+
						"购买券Id:"+obj.getCardId()+"\n"+
						"购买数量:"+obj.getCounter()+"\n"+
						"购买金额:"+obj.getTotalFee()+"\n"+
						"商户订单号:"+obj.getOutTradeNo()+"\n"+
						"银行订单号:"+obj.getTransactionId()+"\n"+
						"商户brandId:"+obj.getPartnerId()+"\n"+
						"用户wxId:"+obj.getOpenId()+"\n";
		jsonMap.put("content", content);
		jsonMap.put("multiple", true);
		
		String input = JSONObject.fromObject(jsonMap).toString();
		log.info("input:"+input);
		String result="";
		try {
			result = CommonUtil.postSendMessage(mailUrl, input, key);
		} catch (WeixinRuntimeException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
		log.info("获取券信息结果:"+result);
		
		JSONObject requestObject = JSONObject.fromObject(result);
		
		String data="";
		try {
			data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
		JSONObject res=JSONObject.fromObject(data);
		Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
		String message = res.getString("message"); // 返回信息
		log.info("邮件服务器返回结果  成功标识:"+success+"\t返回信息:"+message);
	}
	
	@Test
	public void sendMails() throws Exception{
		Properties prop = new Properties();
		InputStream is = WxPayTest.class.getClassLoader().getResourceAsStream("weixin-coffee.properties");
		prop.load(is);
		System.out.println(prop.get("coffee.subject"));
		WxPayCouponState obj=new WxPayCouponState();
		obj.setMobile("18510335354");
		obj.setCardId("6051");
		obj.setCounter(123L);
		obj.setTotalFee(123L);
		obj.setOutTradeNo("sdfasdfsdfasdfa");
		obj.setTransactionId("sdfassadffa123123");
		obj.setPartnerId("1119");
		obj.setOpenId("objjjjjjsdfasfajjjoke");
		String content = "\n用户手机:"+obj.getMobile()+"\n"+
				"购买券Id:"+obj.getCardId()+"\n"+
				"购买数量:"+obj.getCounter()+"\n"+
				"购买金额:"+obj.getTotalFee()+"\n"+
				"商户订单号:"+obj.getOutTradeNo()+"\n"+
				"银行订单号:"+obj.getTransactionId()+"\n"+
				"商户brandId:"+obj.getPartnerId()+"\n"+
				"用户wxId:"+obj.getOpenId()+"\n";
		String mails = "sundongfeng@yazuo.com";
		String[] targetMails = mails.split(",");
		//失败发送邮件
		MailUtil.sendMail(Arrays.asList(targetMails),""+prop.get("coffee.subject"),content);
		
		
		//发送短信内容
		String jsonpar = "{'mobile':'18510335354','content':'"+prop.get("coffee.error")+"','merchant_id':'9355','source':'8'}";
		String smsUrl="http://192.168.236.41:8083/crmapi/shortmessage/shortMessageSendJMS.do";
		CommonUtil.postSendMessage(smsUrl,jsonpar,Constant.ENTITYKEY.toString());//发送短信
	}
	/**
	 * 发送短信
	 * @throws Exception 
	 */
	@Test
	public void sendSMS() throws Exception{
		String mobileNumber="18510335354";
		String content="很抱歉您购买的新辣道小时代套餐券没有注入成功。烦请您致电客服进行人工注券。客服电话：#010-8888888#。给您带来不便敬请谅解。";
		 String jsonpar = "{'mobile':'" + mobileNumber + "','content':'" + content
                 + "','merchant_id':'1119','source':'8'}";
		 String smsUrl = "http://192.168.236.35:8085/crmapi/shortmessage/shortMessageSendJMS.do";
         String jsonre = CommonUtil.postSendMessage(smsUrl,jsonpar,"700030" ).toString();
	}
	@Test
	public void queryCard() throws WeixinRuntimeException{
		String url = "http://192.168.236.35:8085/crmapi/card/queryCard.do?ciphertext={'card':'6201200002739688'}&key=600039";
		String json = CommonUtil.postMessage(url, "");
		System.out.println(json);
	}
	/**
	 * 更新券状态测试
	 */
	@Test
	public void updateCouponState(){
		WxPayCouponState state = new WxPayCouponState();
		state.setOutTradeNo("sxeQRIteYVmj7jbn");
		state.setCardState("1");
		log.info(updateWeixin_pay_coupon_state(state));
	}
	
	
	public int updateWeixin_pay_coupon_state(WxPayCouponState state){
		String sql = "update weixin.weixin_pay_coupon_state set card_state=? where out_trade_no=?";
		log.info("更新券状态:"+sql+" card_state:"+state.getCardState()+" outTradeNo:"+state.getOutTradeNo());
		int update =  jdbc.update(sql, state.getCardState(),state.getOutTradeNo());
		return update;
	}
	
	@Test
	public void gestJPson(){
		JSONObject json = new JSONObject();
		List list = new ArrayList<Map<String,String>>();
		for(int i=0;i<3;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("name", "消费模版"+i);
			map.put("value", "BePL5xCZiPdx5o143nk2V_cztmSxvtBYbVgxeJ3xSoY");
			map.put("type",i+1+"");
			list.add(map);
		}
		json.put("templates", list);
		System.out.println(json.toString());
	}
	
	@Test
	public void getOUt() throws Exception{
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		long transtime = simpleFormate2.parse("2014-09-17 11:57:48").getTime();
		long nowtime = new Date().getTime();
		boolean out = (nowtime-transtime)/1000>((long)(30*24*60*60));
	}
}
