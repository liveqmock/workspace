package com.sunny.test;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.EncryptUtil;
import com.yazuo.weixin.util.MailUtil;
import com.yazuo.weixin.vo.WxcoffeeCzFlag;

/**
* @ClassName WxInterfaceApiTest
* @Description 测试接口
* @author sundongfeng@yazuo.com
* @date 2014-7-7 下午6:05:30
* @version 1.0
*/
public class WxInterfaceApiTest {
	Logger log = Logger.getLogger(this.getClass());
	
	private ApplicationContext cxt;
	JdbcTemplate jdbcTemplate=null;
	@Before
	public void setUp(){
		cxt = new FileSystemXmlApplicationContext(new String[]{"target/classes/spring-weixin-datasource.xml","target/classes/spring-weixin-servlet.xml"});
		jdbcTemplate = (JdbcTemplate) cxt.getBean("jdbcTemplate");
	}
	/**
	 * 查询储值卡信息
	 */
	@Test
	public  void queryCardList(){
		String optainTicketInfoUrl="http://192.168.236.41:8083/crmapi/cardnew/getCardListByMobileAndmerchantId.do";
		String xldKey="700018";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", 21);
		jsonMap.put("mobile", "13400000003");
		jsonMap.put("isStore", true);
		String input = JSONObject.fromObject(jsonMap).toString();
		
		String result="";
		try {
			result = CommonUtil.postSendMessage(optainTicketInfoUrl, input, xldKey);
		} catch (WeixinRuntimeException e1) {
			log.error("code happen error.",e1);
			e1.printStackTrace();
		}
	}
	
	/**
	 * 查询充值
	 */
	@Test
	public void chongzhi(){
		String chongzhiUrl="http://192.168.236.41:8083/crmapi/cardnew/storedCash2Card.do";
		String secretKey="HKpaL3cAOIGggxnq6moKVFI9";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", "21");
		jsonMap.put("mobile", "18697765517");
		jsonMap.put("cardNo", "6201300270551858");
		jsonMap.put("amount", "0.01");
		jsonMap.put("source", 5);
		jsonMap.put("remark", "微信充值");
		WxcoffeeCzFlag czflag = new WxcoffeeCzFlag();
		czflag.setOutTradeNo("qweurwqioruoq");
		czflag.setMobile("18697765517");
		czflag.setCardNo("6201300270551858");
		czflag.setBrandId("21");
		czflag.setAmount(1D);
		czflag.setStatus("0");//先插入0，如果充值成功，将状态更改为1
		czflag.setRemark("微信咖啡陪你充值");
		try {
			String encodeInput = JSONObject.fromObject(jsonMap).toString();
			String input = EncryptUtil.encrypt3DesObject(secretKey, encodeInput);
			input = URLEncoder.encode(input, "UTF-8");
			String  result = CommonUtil.postSendMessage(chongzhiUrl, input, Constant.COFFEEKEY);
//			String encodeInput = JSONObject.fromObject(jsonMap).toString();
//			log.info(encodeInput);
//			String  result = CommonUtil.postSendMessage(chongzhiUrl, encodeInput, "700018");
			JSONObject requestObject = JSONObject.fromObject(result);
			Boolean flag = requestObject.getBoolean("flag");
			String data = "";
			if(flag){
				data = EncryptUtil.dncrypt3DesString(secretKey,  URLEncoder.encode(((JSONObject) requestObject.get("data")).get("result").toString(),"UTF-8"));
			}else{
				data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			}
			JSONObject res=JSONObject.fromObject(data);	
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("result message:"+message);
				if (success) {// 成功
					czflag.setStatus("1");//将充值状态更改为成功状态1
				}
			}
		} catch ( Exception e1) {
			log.error("code happen error.",e1);
			e1.printStackTrace();
		}finally{
			boolean flag = queryHasCzflag(czflag.getOutTradeNo());
			log.info("flag:"+flag);
			if(flag){
				if("1".equals(czflag.getStatus())){
					updateCzflag(czflag);
				}
			}else{
				int a = insertCzflag(czflag);
				log.info("插入券状态成功:"+(a>0));
				if("0".equals(czflag.getStatus())){
					log.info("发送失败短信");
				}
			}
		}
		
	}
	
	/**
	 * 查询充值状态
	 * @param outTradeNo
	 * @return
	 */
	public boolean queryHasCzflag(String outTradeNo){
		String sql = "select count(*) cn from weixin.weixin_coffee_czflag where out_trade_no=?";
		int cn = jdbcTemplate.queryForInt(sql,outTradeNo);
		log.info("查询充值状态sql:"+sql+" outtradeno:"+outTradeNo+" cn:"+cn);
		return cn>0?true:false;
	}
	
	/**
	 * 插入充值状态信息
	 * @return
	 */
	public int insertCzflag(WxcoffeeCzFlag czflag){
		log.info(czflag.toString());
		String sql = "insert into weixin.weixin_coffee_czflag (out_trade_no, mobile,"+ 
				    " card_no, brand_id, amount,status, remark,create_time) "+
				    " values (?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
		return jdbcTemplate.update(sql, czflag.getOutTradeNo(),czflag.getMobile(),czflag.getCardNo(),
				czflag.getBrandId(),czflag.getAmount(),czflag.getStatus(),czflag.getRemark());
	}
	
	/**
	 * 更新充值状态信息
	 * @return
	 */
	public int updateCzflag(WxcoffeeCzFlag czflag){
		String sql = "update weixin.weixin_coffee_czflag set status =?,amount=? where out_trade_no=?";
		log.info("更新充值状态信息sql:"+sql+" status:"+czflag.getStatus()+" outtradeno:"+czflag.getOutTradeNo());
		return jdbcTemplate.update(sql, czflag.getStatus(),czflag.getAmount(),czflag.getOutTradeNo());
	}
}
