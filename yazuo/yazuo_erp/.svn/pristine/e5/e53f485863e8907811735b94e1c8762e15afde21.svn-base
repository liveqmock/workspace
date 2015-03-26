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
package com.yazuo.erp.superReport.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yazuo.erp.superReport.service.NewSuperReportService;
import com.yazuo.util.StringUtil;

/**
 * 新数据监测
 * @author kyy
 * @date 2014-7-9 上午10:55:29
 */
@Controller
@RequestMapping("/new/superReport")
public class NewSuperReportController {

	
	@Resource
	private NewSuperReportService newSuperReportService;
	
	@RequestMapping(value = "userTableData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object userTableData(String beginDate, String endDate) {
		// 用户数据统计及表格数据
	   JSONObject json = newSuperReportService.getUserInfo(fromatDate(beginDate), fromatDate(endDate));
	   JSONObject result = new JSONObject();
	   result.put("data", json);
	   result.put("flag", 1);
	   result.put("message", "");
	   return result;
	}
	
	@RequestMapping(value = "userChartData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object userChartData(String beginDate, String endDate,@RequestParam(value="queryType", required=false) String queryType
    		,@RequestParam(value="queryFlag", required=false) String queryFlag, @RequestParam(value="isNew", required=false) String isNew) {
		// 用户数据统计及表格数据  
		JSONObject result = new JSONObject();
		if (StringUtil.isNullOrEmpty(isNew)) {
			//1）某个时间段
			Object obj = newSuperReportService.getUserChartByDatePeriod(fromatDate(beginDate), fromatDate(endDate), queryType, queryFlag);	
			result.put("flag", 1);
			result.put("message", "");
			result.put("data", obj);
			return result;
		}
		//2）某个时间点
		JSONArray obj = newSuperReportService.getUserInfoByDate(Boolean.parseBoolean(isNew), fromatDate(beginDate));
		result.put("flag", 1);
		result.put("message", "");
		result.put("data", obj);
		return result;
	}
	
	@RequestMapping(value = "brandTableData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JSONObject brandTableData(String beginDate, String endDate) {
		// 用户数据统计及表格数据
	   JSONObject json = newSuperReportService.getBrandInfo(fromatDate(beginDate), fromatDate(endDate));
	   JSONObject result = new JSONObject();
	   result.put("data", json);
	   result.put("flag", 1);
	   result.put("message", "");
	   return result;
	}
	
	@RequestMapping(value = "brandChartData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object brandChartData(String beginDate, String endDate, @RequestParam(value="isNew", required=false) String isNew) {
		JSONObject result = new JSONObject();
		// 用户数据统计及表格数据  
		if (!StringUtil.isNullOrEmpty(isNew)) {
			//1）某个时间点
			JSONObject obj = newSuperReportService.getBrandInfoByDate(Boolean.parseBoolean(isNew), fromatDate(endDate));//getUserChartByDatePeriod(fromatDate(beginDate), fromatDate(endDate), queryType, queryFlag);
			result.put("flag", 1);
			result.put("message", "");
			result.put("data", obj);
			return result;
		}
		//2）某个时间段
		JSONObject obj = newSuperReportService.getBrandByDatePeriod(fromatDate(beginDate), fromatDate(endDate));
		result.put("flag", 1);
		result.put("message", "");
		result.put("data", obj);
		return result;
	}
	
	@RequestMapping(value = "functionTableData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    // 功能统计表格数据
    public Object functionTableData(String beginDate, String endDate , @RequestParam(value="type") String type) {
		JSONObject json = new JSONObject();
		Object obj = null;
		if (type.equals("personCount")) {
			obj = newSuperReportService.getUserCountInfo(fromatDate(beginDate), fromatDate(endDate));
		} else if (type.equals("count")) {
			obj = newSuperReportService.getUserTimesInfo(fromatDate(beginDate), fromatDate(endDate));
		} else if (type.equals("clickCount")) {
			obj = newSuperReportService.getPicTableCount(fromatDate(beginDate), fromatDate(endDate));
		} else if (type.equals("sendCount")) {
			obj = newSuperReportService.getSendCount(fromatDate(beginDate), fromatDate(endDate));
		}
		json.put("flag", 1);
		json.put("message", "");
		json.put("data", obj);
		return json;
	}
	
	@RequestMapping(value = "functionChartData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    // 功能统计图形数据
    public Object functionChartData(String beginDate, String endDate, @RequestParam(value="type") String type, 
    		@RequestParam(value="path", required=false) String path, @RequestParam(value="queryFlag", required=false) String queryFlag) {
		JSONObject obj = null;
		if (type.equals("personCount")) {
			obj = newSuperReportService.getUserCountByFunc(path, fromatDate(beginDate), fromatDate(endDate), queryFlag);
		} else if (type.equals("count")) {
			obj = newSuperReportService.getUserTimesByFunc(path, fromatDate(beginDate), fromatDate(endDate), queryFlag);
		} else if (type.equals("clickCount")) {
			obj = newSuperReportService.getPicCountByFunc(path, fromatDate(beginDate), fromatDate(endDate), queryFlag);
		} else if (type.equals("sendCount")) {
			obj = newSuperReportService.getSendAdviceByFunc(path, fromatDate(beginDate), fromatDate(endDate), queryFlag);
		}
		JSONObject result = new JSONObject();
		if (obj == null) {
			result.put("data", "");
			result.put("flag", 1);
			result.put("message", "");
			return result;
		}else if (obj !=null && queryFlag.equals("date")){
			result.put("data", obj);
			result.put("flag", 1);
			result.put("message", "");
			return result;
		} else {
			obj.put("flag", 1);
			obj.put("message", "");
			return obj;
		}
	}
	
	// 格式化数据
	private String fromatDate (String time) {
		long second = StringUtil.isNullOrEmpty(time) ? 0 : Long.parseLong(time);
		Date date = new Date();
		date.setTime(second * 1000); // 将秒转换成毫秒数
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date); // 格式化
	}
}
