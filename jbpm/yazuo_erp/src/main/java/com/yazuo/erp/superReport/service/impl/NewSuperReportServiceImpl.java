/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.superReport.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.erp.superReport.service.NewSuperReportService;
import com.yazuo.util.StringUtil;

/**
 *超级报表监控服务类
 * @author kyy
 * @date 2014-7-8 下午2:54:08
 */
@Service
public class NewSuperReportServiceImpl implements NewSuperReportService {
	
	Log log = LogFactory.getLog(this.getClass());
	@Value("${superReport.url.prefix}")
	private String superReportUrlPrefix;
	private String controllerUrlPrefix;
	
	// 商户统计项
	public static Map<String, String> brandItem = new HashMap<String, String>();
	// 功能统计项
	public static Map<String, String> reportTypeItem = new HashMap<String, String>();
	
	static {
		reportTypeItem.put("VIEW_MONTHREPORT", "查看月报");
		reportTypeItem.put("EXPORT_REPORT", "月报导出");
		reportTypeItem.put("SEND_NOTICE", "发送通知");
		reportTypeItem.put("SEND_REPORT", "发送日报");
		reportTypeItem.put("VIEW_REPORT", "查看日报");
		reportTypeItem.put("VIEW_STATISTICS", "数据统计");
		reportTypeItem.put("SEND_COMMENT", "发送评论");
		reportTypeItem.put("VIEW_COMMENT", "查看评论");
		reportTypeItem.put("VIEW_NOTICE", "查看通知");
		reportTypeItem.put("VIEW_MARK", "标注");
		reportTypeItem.put("CHART_DAY", "日环比图");
		reportTypeItem.put("CHART_MONTH", "月同比图");
		
		brandItem.put("brandUser", "品牌用户");
		brandItem.put("branchUser", "管理公司");
		brandItem.put("manager", "管理员");
		brandItem.put("boss", "老板");
		brandItem.put("faceshopUser", "门店用户");
		brandItem.put("otherUser", "其他");
		brandItem.put("sendReportFaceShop", "使用过的门店数");
		brandItem.put("notSendReportFaceShop", "未用过的门店数");
	}
	
	@Override
	public JSONObject getUserInfo(String beginDate, String endDate) {
		JSONObject userData = this.actionToJsonObject("/getUserInfo.do?startTime="+beginDate+"&endTime=" + endDate);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total",this.actionToJsonObject("/getUserAndMerchantTitleInfo.do?startTime="+beginDate+"&endTime=" + endDate));
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (!beginDate.equals(endDate)) { // 时间段
			JSONObject obj = userData.getJSONObject("data");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cateName", beginDate+"至" + endDate);
			map.put("newNum", obj.get("newUserCount"));
			map.put("loginNum", obj.get("loginUserCount"));
			map.put("loginTimes", obj.get("loginTimes"));
			list.add(map);
			jsonObject.put("statistics", list);
			return jsonObject;
		}
		// 今天（时间点）
		Iterator it = userData.keySet().iterator();
		while (it.hasNext()) {
			String kt = String.valueOf(it.next());
			JSONObject json = userData.getJSONObject(kt);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cate", kt);
			if (kt.equals("today")) map.put("cateName", "今天");
			if (kt.equals("yesterday")) map.put("cateName", "昨天");
			if (kt.equals("average")) map.put("cateName", "近90天平均 ");
			if (kt.equals("hisMax")) map.put("cateName", "历史最高");
			Iterator iter = json.keySet().iterator();
			while (iter.hasNext()) {
				String key = String.valueOf(iter.next());
				Object value = json.get(key);
				if (key.equals("totalUserCount"))map.put("totalNum", value);
				if (key.equals("newUserCount"))map.put("newNum", value);
				if (key.equals("loginUserCount"))map.put("loginNum", value);
				if (key.equals("loginTimes"))map.put("loginTimes", value);
			}
			if (!StringUtil.isNullOrEmpty(kt) && kt.equals("hisMax")) {
				String newDate = String.valueOf(json.get("newUserCountDate"));
				String loginDate = String.valueOf(json.get("loginUserCountDate"));
				String timeDate = String.valueOf(json.get("loginTimesDate"));
				map.put("newNumHisMaxDate", formatDate(newDate));
				map.put("loginNumHisMaxDate", formatDate(loginDate));
				map.put("loginTimesHisMaxDate", formatDate(timeDate));
			}
			if (StringUtil.isNullOrEmpty(String.valueOf(json.get("totalUserCount")))) {
				map.put("activeRatio", 0);
			} else {
				double total = Double.parseDouble(String.valueOf(json.get("totalUserCount")).trim());
				double loginCount = StringUtil.isNullOrEmpty(json.getString("loginUserCount")) ? 0 : json.getDouble("loginUserCount");
				double rate = total != 0 ? loginCount/total * 100 : 0;
				map.put("activeRatio", formatDouble(rate)+"%"); // 占比
			}
			// 将历史最高和近90天平均的总用户和活跃比例置为/
			if (kt.equals("hisMax") || kt.equals("average")) {
				map.put("totalNum", "/");
				map.put("activeRatio", "/");
			}
			list.add(map);
		}
		
	    jsonObject.put("statistics", list);
	    return jsonObject;
	}
	
	@Override
	public JSONArray getUserInfoByDate(boolean isNew, String date) {
	    JSONArray array = this.actionToJsonArray("/getUserChart.do?isNew=" + isNew + "&time=" + date);
	    getPercent(array);
	    return array;
	}

	// 各占比(饼图)
	private void getPercent(JSONArray array) {
		double total = 0;
	    for (int i=0; i < array.size(); i++) {
	    	JSONObject json = array.getJSONObject(i);
	    	total +=!StringUtil.isNullOrEmpty(String.valueOf(json.get("userCount"))) ? json.getDouble("userCount") : 0;
	    }
	    for (int i=0; i < array.size(); i++) {
	    	JSONObject json = array.getJSONObject(i);
	    	double count = !StringUtil.isNullOrEmpty(String.valueOf(json.get("userCount"))) ? json.getDouble("userCount") : 0;
	    	double rate = total !=0 ? count/total * 100 : 0; //比例
	    	json.put("ratio", formatDouble(rate));
	    }
	}
	
	@Override
	public JSONArray getUserInfoByDate(boolean isNew, String fromDate, String endDate) {
	    return this.actionToJsonArray("/getUserChart.do?isNew=" + isNew + "&time=" + fromDate);
	}
	
	@Override
	public JSONObject getBrandInfo(String beginDate, String endDate) {
		JSONObject obj = this.actionToJsonObject("/getMerchantInfo.do?startTime="+beginDate+"&endTime=" + endDate);
		// 最终结果集
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("total", this.actionToJsonObject("/getUserAndMerchantTitleInfo.do?startTime="+beginDate+"&endTime=" + endDate));
	    
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (!beginDate.equals(endDate)) { // 时间段
			JSONObject json = obj.getJSONObject("data");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("cateName", beginDate+"至" + endDate);
			result.put("newMerchant", json.get("newFaceShopCount"));
			result.put("activeMerchantNum", json.get("activeFaceShopCount"));
			result.put("newBrand", json.get("newBrandCount"));
			result.put("activeBrandNum", json.get("activeBrandCount"));
			list.add(result);
			
			jsonObject.put("statistics", list);
			return jsonObject;
		}
		Iterator it = obj.keySet().iterator();
		while (it.hasNext()) {
			String kt = String.valueOf(it.next());
			JSONObject json = obj.getJSONObject(kt);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cate", kt);
			if (kt.equals("today")) map.put("cateName", "今天");
			if (kt.equals("yesterday")) map.put("cateName", "昨天");
			if (kt.equals("average")) map.put("cateName", "近90天平均 ");
			if (kt.equals("hisMax")) map.put("cateName", "历史最高");
			Iterator iter = json.keySet().iterator();
			while (iter.hasNext()) {
				String key = String.valueOf(iter.next());
				Object value = json.get(key);
				if (key.equals("totalBrandCount"))map.put("totalBrand", value);
				if (key.equals("newBrandCount"))map.put("newBrand", value);
				if (key.equals("activeBrandCount"))map.put("activeBrandNum", value);
				if (key.equals("totalFaceShopCount"))map.put("totalMerchant", value);
				if (key.equals("newFaceShopCount"))map.put("newMerchant", value);
				if (key.equals("activeFaceShopCount"))map.put("activeMerchantNum", value);
			}
			if (StringUtil.isNullOrEmpty(String.valueOf(json.get("totalBrandCount")))) {
				map.put("activeBrandRatio", 0); // 活跃品牌占比
				map.put("activeMerchantRatio", 0); //活跃门店占比
			} else {
				double total = Double.parseDouble(String.valueOf(json.get("totalBrandCount")).trim());
				double activeBrandCount = json.getDouble("activeBrandCount");
				double rate = total!=0 ? activeBrandCount/total * 100 : 0;
				map.put("activeBrandRatio", formatDouble(rate)+"%"); // 活跃品牌占比
				
				double activeFaceShopCount = json.getDouble("activeFaceShopCount");
				double rate2 = total !=0 ? activeFaceShopCount/total * 100 : 0;
				map.put("activeMerchantRatio", formatDouble(rate2)+"%"); //活跃门店占比
			}
			// 历史最高才有数据
			if (!StringUtil.isNullOrEmpty(kt) && kt.equals("hisMax")) {
				String newDate = String.valueOf(json.get("newBrandCountDate"));
				String activeBrandDate = String.valueOf(json.get("activeBrandCountDate"));
				String newFaceDate = String.valueOf(json.get("newFaceShopCountDate"));
				String activeDate = String.valueOf(json.get("activeFaceShopCountDate"));
				map.put("newBrandHisMaxDate", formatDate(newDate));
				map.put("activeBrandNumHisMaxDate", formatDate(activeBrandDate));
				map.put("newMerchantHisMaxDate", formatDate(newFaceDate));
				map.put("activeMerchantNumHisMaxDate", formatDate(activeDate));
			}
			// 将历史最高和近90天平均的总用户和活跃比例置为/
			if (kt.equals("hisMax") || kt.equals("average")) {
				map.put("totalBrand", "/");
				map.put("activeBrandRatio", "/");
				map.put("totalMerchant", "/");
				map.put("activeMerchantRatio", "/");
			}
			if (kt.equals("average")) {
				map.put("activeBrandNum", "/");
				map.put("activeMerchantNum", "/");
			}
			list.add(map);
		}
		    		
	    jsonObject.put("statistics", list);
	    return jsonObject;
	}
	
	@Override
	public JSONObject getBrandInfoByDate(boolean isNew, String date) {
		JSONObject dataJsonObject = new JSONObject();
		// 返回数据
		JSONObject jsonObject =  this.actionToJsonObject("/getMerchantChart.do?isNew=" + isNew + "&time=" + date);
	    Iterator it = jsonObject.keySet().iterator();
	    
	    List<String> keyList = new ArrayList<String>();
	    List<String> valList = new ArrayList<String>();
	    while (it.hasNext()) {
	    	String key = String.valueOf(it.next());
	    	if (key.equals("brand")) {
	    		keyList.add("品牌");
	    	} else if (key.equals("faceShop")) {
	    		keyList.add("门店");
	    	} else if (key.equals("manageCompany")) {
	    		keyList.add("分公司");
	    	}
	    	valList.add(key);
	    }
	    List<String> dateList = new ArrayList<String>();
        JSONArray array = jsonObject !=null ? (JSONArray) jsonObject.get("brand") : new JSONArray();
        Iterator<JSONObject> objectIterator = array.iterator();
        while (objectIterator.hasNext()) {
            dateList.add(objectIterator.next().getString("time"));
        }

        JSONArray jsonArray = new JSONArray();
        Iterator<String> dateIterator = dateList.iterator();
        while (dateIterator.hasNext()) {
            String dateStr = dateIterator.next();
            List<Integer> nums = new ArrayList<Integer>();
            JSONObject listItem = new JSONObject();
            for (String name : valList) {
                for (Object item : jsonObject.getJSONArray(name)) {
                    JSONObject ite = (JSONObject) item;
                    if (ite.getString("time").equals(dateStr)) {
                        nums.add(ite.getInt("count"));
                    }
                }
            }
            listItem.put("name", dateStr);
            listItem.put("data", nums);
            jsonArray.add(listItem);
        }
        dataJsonObject.put("series", jsonArray);
        dataJsonObject.put("categories", keyList);
        return dataJsonObject;
	}
	
	@Override
	public Object getUserChartByDatePeriod(String startTime, String endTime, String queryType, String queryFlag) {
		if (queryFlag.equals("role")) { // role 角色占比
			JSONArray jsonArray = actionToJsonArray("/getUserChartByTime.do?startTime="+ startTime +"&endTime="+endTime+"&queryType="+queryType+"&queryFlag="+queryFlag);
			getPercent(jsonArray);
			return jsonArray;
		} else if (queryFlag.equals("date")){ // date 时间分布
			JSONObject jsonObject = actionToJsonObject("/getUserChartByTime.do?startTime="+ startTime +"&endTime="+endTime+"&queryType="+queryType+"&queryFlag="+queryFlag);
			return getChartOrderByDate(jsonObject, brandItem, "userCount");
		}
		return null;
	}

	@Override
	public JSONObject getBrandByDatePeriod(String startTime, String endTime) {
		JSONObject jsonObject = actionToJsonObject("/getMerchantChartByTime.do?startTime="+startTime+"&endTime=" + endTime);
         Map<String, String> map = new HashMap<String, String>();
         map.put("activeBrandTimeCount", "活跃品牌数");
         map.put("activeFaceShopTime", "活跃门店数");
         map.put("newBrandChartCount", "新增品牌");
         map.put("newFaceShopChartCount", "新增门店");
		return getChartOrderByDate(jsonObject, map, "count");
	}

	/** 按<时间>查询出的数据格式化*/
	private JSONObject getChartOrderByDate(JSONObject jsonObject, Map<String, String> tkeyMap, String countFileds) {
		JSONObject finalResult = new JSONObject(); 
		// 时间集合
		List<String> dateList = new ArrayList<String>();
		jsonObject = !StringUtil.isNullOrEmpty(jsonObject.toString()) ? jsonObject : new JSONObject();
		Set keySet = jsonObject.keySet();
		int m = 0; 
		for (Iterator pIt = keySet.iterator();pIt.hasNext();) {
		  if(m==0){
			  //取其中一个列表
			  JSONArray array = jsonObject.getJSONArray(String.valueOf(pIt.next()));
			  Iterator<JSONObject> objectIterator = array.iterator();
			  while (objectIterator.hasNext()) {
				  String time = objectIterator.next().getString("time");
				  dateList.add(String.valueOf(formatDate(time)));
			  }
		  } else {
			  break;
		  }
		  m++;
		}
        JSONArray jsonArray = new JSONArray();
            
        for (Object s : keySet) {
        	String tkey = String.valueOf(s);
        	JSONObject listItem = new JSONObject();
        	if (StringUtil.isNullOrEmpty(tkeyMap.get(tkey))) {
        		listItem.put("name", tkey);
        	} else {
        		listItem.put("name", tkeyMap.get(tkey));
        	}
        	if ((tkeyMap.get(tkey)!= null && tkeyMap.get(tkey).equals("未用过的门店数")) || tkey.equals("未用过的门店数")) {
        		listItem.put("visible", false);
        	} else {
        		listItem.put("visible", true);
        	}
            // 时间对应的值
             List<Integer> nums = new ArrayList<Integer>();
        	for (int i=0; i<dateList.size(); i++) {
                String dateStr = dateList.get(i);
                for (Object item : jsonObject.getJSONArray(tkey)) {
                    JSONObject ite = (JSONObject) item;
                    String resultTime = String.valueOf(formatDate(ite.getString("time")));
                    if (resultTime.equals(dateStr)) {
                        nums.add(ite.getInt(countFileds));
                    }
                }
            }
        	listItem.put("data", nums);
        	jsonArray.add(listItem);
        }
        finalResult.put("categories", dateList);
        finalResult.put("series", jsonArray);
		return finalResult;
	}

	@Override
	public Object getUserCountInfo(String startTime, String endTime) {
	    JSONObject jsonObject = this.actionToJsonObject("/getAccessUrlUserCountInfo.do?startTime="+startTime+"&endTime=" + endTime);
	    return formatFunctionData(jsonObject, startTime+"至" + endTime);
	}
	
	@Override
	public Object getPicTableCount(String startTime, String endTime) {
		JSONObject obj = actionToJsonObject("/getChartStatisticsCountInfo.do?startTime="+startTime+"&endTime="+ endTime);
		return formatFunctionData(obj, startTime+"至" + endTime);
	}

	@Override
	public Object getSendCount(String startTime, String endTime) {
		JSONObject obj = actionToJsonObject("/getSendDataCountInfo.do?startTime="+startTime+"&endTime=" + endTime);
		return formatFunctionData(obj, startTime+"至" + endTime);
	}

	@Override
	public Object getUserTimesInfo(String startTime, String endTime) {
	    JSONObject obj = this.actionToJsonObject("/getAccessUrlTimesInfo.do?startTime="+startTime+"&endTime=" + endTime);
	    return formatFunctionData(obj, startTime+"至" + endTime);
	}
	
	@Override
	public JSONObject getUserCountByFunc(String funcName, String beginDate, String endDate, String queryFlag) {
	    String url = "/getAccessUrlUserCountChart.do?startTime="+beginDate+"&endTime="+endDate+"&queryFlag="+ queryFlag;
	    if (!StringUtil.isNullOrEmpty(funcName)) {
	        url = url + "&path=" + funcName;
	    }
	    JSONObject jsonObject = this.actionToJsonObject(url); 
	    
	    return formatFunctionChartData(funcName, queryFlag, jsonObject, "personCount");
	}
	

	@Override
	public JSONObject getUserTimesByFunc(String funcName, String beginDate, String endDate, String queryFlag) {
	    String url = "/getAccessUrlTimesChart.do?startTime="+beginDate+"&endTime="+endDate+"&queryFlag="+ queryFlag;
	    if (!StringUtil.isNullOrEmpty(funcName)) {
	        url = url + "&path=" + funcName;
	    }
	    JSONObject jsonObject = this.actionToJsonObject(url);
	    return formatFunctionChartData(funcName, queryFlag, jsonObject, "count");
	}
	
	@Override
	public JSONObject getPicCountByFunc(String funcName, String beginDate, String endDate, String queryFlag) {
		String url = "/getChartStatisticsCountChart.do?startTime="+beginDate+"&endTime="+endDate+"&queryFlag=" + queryFlag;
		 if (!StringUtil.isNullOrEmpty(funcName)) {
		        url = url + "&path=" + funcName;
		    }
	    JSONObject jsonObject = this.actionToJsonObject(url);
	    return formatFunctionChartData(funcName, queryFlag, jsonObject, "clickCount");
	}

	@Override
	public JSONObject getSendAdviceByFunc(String funcName, String beginDate, String endDate, String queryFlag) {
		String url = "/getSendDataCountChart.do?startTime="+beginDate+"&endTime="+endDate+"&queryFlag=" + queryFlag;
		 if (!StringUtil.isNullOrEmpty(funcName)) {
		        url = url + "&path=" + funcName;
		    }
	    JSONObject jsonObject = this.actionToJsonObject(url);
	    return formatFunctionChartData(funcName, queryFlag, jsonObject, "sendCount");
	}


	/**功能统计数据格式化（时间和占比）*/
	private JSONObject formatFunctionChartData(String funcName, String queryFlag, JSONObject jsonObject, String type) {
		// 按时间点统计占比，按时间段统计占比和时间分布
	    if (StringUtil.isNullOrEmpty(queryFlag) || (!StringUtil.isNullOrEmpty(queryFlag) && queryFlag.equals("function"))) {
	    	JSONArray array = !StringUtil.isNullOrEmpty(jsonObject.toString()) ? jsonObject.getJSONArray("data") : new JSONArray();
	    	double total = 0;
		    for (int i=0; i < array.size(); i++) {
		    	JSONObject json = array.getJSONObject(i);
		    	total +=!StringUtil.isNullOrEmpty(String.valueOf(json.get("count"))) ? json.getDouble("count") : 0;
		    }
		    JSONArray fianlArray = new JSONArray();
		    for (int i=0; i < array.size(); i++) {
		    	JSONObject finalObject = new JSONObject();
		    	JSONObject json = array.getJSONObject(i);
		    	  if (StringUtil.isNullOrEmpty(funcName)) {
		    		  finalObject.put("name", reportTypeItem.get(json.getString("name"))); // 名字转换为中文
		    	  } else if(!StringUtil.isNullOrEmpty(funcName) || type.equals("sendCount")){
		    		  if (!StringUtil.isNullOrEmpty(brandItem.get(json.getString("name")))) {
		    			  finalObject.put("name", brandItem.get(json.getString("name")));
		    		  } else {
		    			  finalObject.put("name", json.getString("name"));
		    		  }
		    	  }
		    	finalObject.put("count", json.get("count"));
		    	double count = !StringUtil.isNullOrEmpty(String.valueOf(json.get("count"))) ? json.getDouble("count") : 0;
		    	double rate = total !=0 ? count/total * 100 : 0; //比例
		    	finalObject.put("ratio", formatDouble(rate));
		    	fianlArray.add(finalObject);
		    }
		    jsonObject = new JSONObject();
		    jsonObject.put("data", fianlArray);
		    return jsonObject;
	    } else if (!StringUtil.isNullOrEmpty(queryFlag) && queryFlag.equals("date")) {
	    	Map<String, String> map = new HashMap<String, String>();
	    	if (StringUtil.isNullOrEmpty(funcName)) {
	    		map = reportTypeItem;
	    	} else {
		    	map = brandItem;
	    	}
	    	return getChartOrderByDate(jsonObject, map, "count");
	    }
	    return null;
	}
	
	/**
	 * URL的帮助方法，消除冗余代码
	 * @param actionUrl
	 * @return
	 */
	private JSONObject actionToJsonObject(String actionUrl) {
	    String url = this.getUrl(actionUrl);
	    return this.getJsonObject(url);
	}
	
	/**
	 * 将指定URL的响应体转换为JSON对象
	 * @param url
	 * @return
	 */
	private JSONObject getJsonObject(String url) {
	    String jsonStr = this.getResponseBody(url);
	    JSONObject jsonObject = null;
	    try {
	        jsonObject = JSONObject.fromObject(jsonStr);
	    } catch (JSONException jsonException) {
	        log.error("url:" + url + ",data:" + jsonStr);
	        jsonException.printStackTrace();
	    }
	    return jsonObject;
	}
	/**
	 * URL的帮助方法，消除冗余代码
	 * @param actionUrl
	 * @return
	 */
	private JSONArray actionToJsonArray(String actionUrl) {
	    String url = this.getUrl(actionUrl);
	    return this.getJsonArray(url);
	}
	
	/**
	 * 将指定URL的响应体转换为JSON数组对象
	 * @param url
	 * @return
	 */
	private JSONArray getJsonArray(String url) {
	    String jsonStr = this.getResponseBody(url);
	    JSONArray jsonObject = null;
	    try {
	        jsonObject = JSONArray.fromObject(jsonStr);
	    } catch (JSONException jsonException) {
	        log.error("url:" + url + ",data:" + jsonStr);
	        jsonException.printStackTrace();
	    }
	    return jsonObject;
	}
	
	/**
	 * 返回响应体的字符串
	 *
	 * @param url 待请求的URL
	 * @return 返回响应的字符串
	 */
	private String getResponseBody(String url) {
	    HttpGet getRequest = new HttpGet(url);
	    HttpClient httpclient = new DefaultHttpClient();
	    String resultStr = null;
	    try {
	        long beginning = System.currentTimeMillis();
	        HttpResponse httpResponse = httpclient.execute(getRequest);
	        long end = System.currentTimeMillis();
	        log.debug("url:" + url + ",用时:" +(end-beginning)+"ms");
	        if (httpResponse.getStatusLine().getStatusCode() == 200) {
	            resultStr = EntityUtils.toString(httpResponse.getEntity());
	        } else {
	            log.error("返回状态码不是200: 状态码:" + httpResponse.getStatusLine().getStatusCode() + ",URL:" + url);
	        }
	    } catch (IOException e) {
	        log.error("请求错误，请求URL：" + url);
	        e.printStackTrace();
	        getRequest.abort();
	    }finally {
	        getRequest.releaseConnection();
	    }
	    return resultStr;
	}
	
	/**
	 * 容错: 配置URL时以/为后缀
	 *
	 * @return 超级报表的URL
	 */
	private String getSuperReportUrlPrefix() {
	    if (superReportUrlPrefix.endsWith("/")) {
	        this.superReportUrlPrefix = this.superReportUrlPrefix.replaceFirst("\\/$", "");
	    }
	    return this.superReportUrlPrefix;
	}
	
	/**
	 * 得到Controller前缀,包含superReport报表应用的URL前缀
	 * @return 超级报表的URL
	 */
	private String getControllerUrlPrefix() {
	    if (this.controllerUrlPrefix == null) {
	        this.controllerUrlPrefix = this.getSuperReportUrlPrefix()+ "/controller/statistics";
	    }
	    return this.controllerUrlPrefix;
	}
	
	/**
	 * 为actionUrl添加controller与应用前缀
	 * @param actionUrl
	 * @return
	 */
	private String getUrl(String actionUrl) {
	    return this.getControllerUrlPrefix() + actionUrl;
	}
	
	// 转换时间
	private long formatDate (String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (!StringUtil.isNullOrEmpty(time)) {
			Date date = null;
			try {
				date = sdf.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date !=null ? date.getTime()/1000 : 0;
		}
		return 0;
	}

	// 保留一位小数
	private double formatDouble(double data) {
		if (data !=0) {
			BigDecimal bg = new BigDecimal(data);
	        return bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return 0;
	}
	
	// 功能统计表格数据格式化
	private Object formatFunctionData (JSONObject obj, String title) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Iterator it = obj.keySet().iterator();
		while (it.hasNext()) {
			String kt = String.valueOf(it.next());
			Map<String, Object> map = new HashMap<String, Object>();
			if (kt.equals("data")) { // 按时间段查询出来的数据
				map.put("cateName", title);
			} else { // 今天或某个时间点查询
				map.put("cate", kt);
			}
			if (kt.equals("today")) map.put("cateName", "今天");
			if (kt.equals("yesterday")) map.put("cateName", "昨天");
			if (kt.equals("average")) map.put("cateName", "近90天平均 ");
			if (kt.equals("hisMax")) map.put("cateName", "历史最高");
			
			JSONArray array = obj.getJSONArray(kt);
			for (int i=0; i < array.size(); i++) {
				JSONObject temp = array.getJSONObject(i);
				String name = temp.getString("name");
				double count = StringUtil.isNullOrEmpty(String.valueOf(temp.get("count"))) ? 0 : temp.getDouble("count");
				if (name.equals("VIEW_MONTHREPORT")) {
					map.put("viewMonthReportCount", count);
					// 历史最高才有数据
					if (!StringUtil.isNullOrEmpty(kt) && kt.equals("hisMax")) {
						long date = temp.get("date")!=null ? formatDate(String.valueOf((temp.get("date")))) : 0;
						map.put("viewMonthReportCountHisMaxDate", date);
					}
				} else {
					// 名称组合
					String[] arrayStr = name.toLowerCase().split("_");
					String str1 = arrayStr[0];
					String str2 = arrayStr[1].substring(0,1).toUpperCase()+arrayStr[1].substring(1)+"Count";
					
					String finalKey = str1 + str2;
					map.put(finalKey, count);
					// 历史最高才有数据
					if (!StringUtil.isNullOrEmpty(kt) && kt.equals("hisMax")) {
						long date = temp.get("date")!=null ? formatDate(String.valueOf((temp.get("date")))) : 0;
						map.put(finalKey+"HisMaxDate", date);
					}
				}
			}
			list.add(map);
		}
		return list;
	}
}
