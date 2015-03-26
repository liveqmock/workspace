package com.sunny.test;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;

/**
* @ClassName WxQueryTest
* @Description 
* @author sundongfeng@yazuo.com
* @date 2014-7-17 上午9:16:30
* @version 1.0
*/
public class WxQueryTest {
	
	/**
	 * 查询交易流水
	 */
	@Test
	public void getTransWaterSumByCardNo(){
		String url="http://192.168.232.202:8083/crmapi/transwaternew/getTransWaterSumByCardNo.do";
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("cardNo","6201300008977188");
		map.put("merchantId","21");
		map.put("page",	 "1");
		map.put("size",	 "10");
		String input = JSONObject.fromObject(map).toString();
		
		try {
			String result = CommonUtil.postSendMessage(url, input, Constant.ENTITYKEY.toString());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				if (success) {// 成功
					JSONObject page = res.getJSONObject("transWaterPage");
					int total= page.getInt("total");
					int page1= page.getInt("page");
					int records= page.getInt("records");
					JSONArray array = page.getJSONArray("rows");
					for(Iterator iter = array.iterator();iter.hasNext();){
						JSONObject jsonObject = (JSONObject) iter.next();
						Map<String,Object> mapeve =  (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
						System.out.println(mapeve.get("membershipId"));
						System.out.println(mapeve.get("id"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testLength(){
		String b ="CB"+ DateUtil.getDate()+CommonUtil.CreateNoncestr();
		System.out.println(b+";"+b.length());
	}
	
	@Test
	public void getBeforeDate() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -1);
		String date = sdf.format(cal.getTime());
		System.out.println("date:"+date);
		Date date1 = sdf.parse("2015-01-30");
		Date date2 = sdf.parse("2015-03-15");
		System.out.println(date1.getTime()/1000);
		System.out.println(date2.getTime()/1000);
	}
}
