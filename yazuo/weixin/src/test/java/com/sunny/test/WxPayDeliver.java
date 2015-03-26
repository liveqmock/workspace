package com.sunny.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.HttpClientCommonSSL;
import com.yazuo.weixin.util.MD5Util;
import com.yazuo.weixin.vo.WxOrder;
import com.yazuo.weixin.vo.WxPayDeliverStateVo;

public class WxPayDeliver {
	private static final Log log = LogFactory.getLog(WxPayDeliver.class);
	JdbcTemplate jdbcTemplate=null;
	private ApplicationContext cxt;
	//@Before
	public void setUp(){
//		cxt = new FileSystemXmlApplicationContext(new String[]{"target/classes/spring-weixin-datasource.xml","target/classes/spring-weixin-servlet.xml"});
//		jdbcTemplate = (JdbcTemplate) cxt.getBean("jdbcTemplate");
	}
	/**
	 * 查询待发货
	 * @return
	 */
	public List<WxPayDeliverStateVo> queryDeliver(){
		String sql ="select wo.mobile,wo.app_id,wo.transaction_id,wo.out_trade_no,wo.open_id "+
				"from weixin.weixin_order wo "+
				"where EXISTS (select 1 from weixin.weixin_pay_coupon_state wpcs   "+
				"where wo.out_trade_no = wpcs.out_trade_no    "+
				"and wo.mobile=wpcs.mobile   "+
				"and wpcs.trade_state='0'   "+
				"and wpcs.card_state='1' and wpcs.partner_id=?)    "+
				"and wo.trade_state='0'  "+
				"and wo.deliver_state='0'  ";
		
		List<WxPayDeliverStateVo> list = jdbcTemplate.query(sql,new Object[]{"1119"}, new BeanPropertyRowMapper<WxPayDeliverStateVo>(WxPayDeliverStateVo.class));
		return list;
	}
	
	//查询待发货
	@Test
	public void testQuery(){
		queryDeliver();
	}
	/**
	 * 订单状态查询
	 * @throws Exception 
	 */
	@Test
	public void orderQuery() throws Exception{
		String outTradeNo = "150226081317fqwRxdDwvuXvyjW8";
		String appid="wxf4c931a2bb7d40ef";
		String partner ="1219692001";
		String appscr = "7c53fe4ae3fce91ae716bd5b71629910";
		String partnetkey ="0d9be069458c0a9a390e710514f7f1bf";
		String appkey = "PKMuRBm4BRvqnmGv4GuoK9j8omu7Q1kvSWZjnd889I71Gyr4Ws1YFfTisUlgjGgU37Jh7yo4s6F5WkKUfrlH1NXcobl4OEZsBPjbEi0EO2BpSdgrYVyIxIikN84WhRYk";
		String token = CommonUtil.getToken(appid,appscr);
		String url = "https://api.weixin.qq.com/pay/orderquery?access_token="+token;
//		String url ="https://api.weixin.qq.com/pay/orderquery?access_token=ZhULNNVYjFCGIb0bYh4N_JJeggqU9F4CX6vQAHkXoJMzR_lisbFj1HEy85ni70XpiCB96ZiiSTAdJ0IydTz08w";
		HashMap<String,String> mp = new HashMap<String,String>();
		mp.put("appid",appid);
		String pack = "out_trade_no="+outTradeNo+"&partner="+partner+"&sign="+MD5Util.MD5("out_trade_no="+outTradeNo+"&partner="+partner+"&key="+partnetkey).toUpperCase();
		mp.put("package",pack);
		mp.put("timestamp", String.valueOf(new Date().getTime()/1000));
		mp.put("app_signature",CommonUtil.createSign(mp, appkey));
		mp.put("sign_method", "sha1");
		log.info("发货json:"+JSONObject.fromObject(mp).toString());
		String content = JSONObject.fromObject(mp).toString();
		
		String result = HttpClientCommonSSL.commonPostStream(url, content);
		log.info("查询订单返回:"+result);
		String trade_state =((JSONObject) JSONObject.fromObject(result).get("order_info")).getString("trade_state");
		log.info("trade_state："+trade_state	);
	}
	
	/**
	 * 发货测试
	 * @throws Exception 
	 */
	@Test
	public void test1() throws Exception{
		String appid="wxc23ca14780a032d9";
		String appscr = "0bd2790ed4da40371960d5d994805488";
		String appkey = "iKgdlPEpUUblDG4HYRnT32qSq8T9JNg1wYFPLNGE0lWKtuqVPMhkhGGEKweXisVhUz4EPdgVyGCvCZTWf8dthQ52lgV6HiQ6PncBApt636xEySxLtJbdydbls4UDlIF8";
		String token = CommonUtil.getToken(appid,appscr);
		
		String url="https://api.weixin.qq.com/pay/delivernotify?access_token="+token;
		HashMap<String,String> mp = new HashMap<String,String>();
		mp.put("appid",appid);
		mp.put("openid","o8-68jpCOA4XXlixsQbwmV2SowBA");
		mp.put("transid","1218431801201406183234380229");
		mp.put("out_trade_no","s6n5sFtOTpv6v88NhgdPJ2gxXsHshbAB");
		mp.put("deliver_timestamp",String.valueOf(new Date().getTime()/1000));
		mp.put("deliver_status","1");
		mp.put("deliver_msg","ok");
		mp.put("app_signature",CommonUtil.createSign(mp, appkey));
		mp.put("sign_method", "sha1");
		log.info("发货json:"+JSONObject.fromObject(mp).toString());
		String content = JSONObject.fromObject(mp).toString();
		
		String result = HttpClientCommonSSL.commonPostStream(url, content);
		log.info("发货返回:"+result);
	}
	
	/**
	 * 获取用户token
	 * @param appid
	 * @param appsecret
	 * @return
	 * @throws WeixinRuntimeException 
	 */
	public String getToken(String appid,String appsecret) throws WeixinRuntimeException{
		String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
		url+="&appid="+appid+"&secret="+appsecret;
		String token = HttpClientCommonSSL.commonPostStream(url, "");
		
		System.out.println(token);
		JSONObject json = JSONObject.fromObject(token);
		token = json.getString("access_token");
		return token;
	}
	/**
	 * 发货通知
	 * @param token
	 * @return
	 * @throws WeixinRuntimeException 
	 */
	public static String deliverState(String token,WxOrder order) throws WeixinRuntimeException{
		String url="https://api.weixin.qq.com/pay/delivernotify?access_token="+token;
		HashMap<String,String> mp = new HashMap<String,String>();
		mp.put("appid",order.getAppId());
		mp.put("openid",order.getOpenId());
		mp.put("transid",order.getTransaction_id());
		mp.put("out_trade_no",order.getOut_trade_no());
		mp.put("deliver_timestamp",String.valueOf(new Date().getTime()/1000));
		mp.put("deliver_status","1");
		mp.put("deliver_msg","ok");
		mp.put("app_signature",CommonUtil.FormatBizQueryParaMap(mp, false));
		mp.put("sign_method", "sha1");
		log.info("发货json:"+JSONObject.fromObject(mp).toString());
		String content = JSONObject.fromObject(mp).toString();
		String result = CommonUtil.postMessage(url, content);
		log.info("发货返回:"+result);
		return result;
	}
	
	@Test
	public void test_1000() throws Exception{
		Long time1 = System.currentTimeMillis();
		Thread.sleep(3534);
		Long time2 = System.currentTimeMillis();
		System.out.println((time2-time1)/1000);
		
		
	}
	
	@Test
	public void test_new_orderquery(){
		SortedMap<String, String> packageMap = new TreeMap<String,String>();
		packageMap.put("appid", "");
		packageMap.put("mch_id", "");
		packageMap.put("", "");
		packageMap.put("", "");
		packageMap.put("", "");
		packageMap.put("", "");
	}
}
