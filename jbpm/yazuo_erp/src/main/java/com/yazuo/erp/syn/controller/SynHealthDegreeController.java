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
package com.yazuo.erp.syn.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.syn.service.SynHealthDegreeService;
import com.yazuo.erp.syn.vo.SynHealthDegreeVO;
import com.yazuo.erp.system.service.SysDictionaryService;

/**
 * 健康度相关业务
 * @author kyy
 * @date 2014-8-14 下午5:51:07
 */
@Controller
@RequestMapping("health")
public class SynHealthDegreeController {
	private static final Log LOG = LogFactory.getLog(SynHealthDegreeController.class);
	
	@Resource
	private SynHealthDegreeService synHealthDegreeService;
	@Resource
	private SysDictionaryService sysDictionaryService;
	
	//展现某个商家的某个月的健康度
	@RequestMapping(value = "getHealthDegreeByMerchantId", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult getHealthDegreeByMerchantId(@RequestBody Map inputMap, HttpServletRequest request) {
		Integer merchantId = Integer.parseInt(inputMap.get("merchantId").toString());
		String date = inputMap.get("date").toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date reportDate = null;
		if (StringUtils.isNotEmpty(date)) {
			try {
				reportDate = sdf.parse(date);
			} catch (ParseException e) {
				LOG.info("时间转换错误");
			}
		} else {
			reportDate = new Date();
		}
		 //获取某个月第一天：
        Calendar c = Calendar.getInstance();    
        c.setTime(reportDate);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        Date startDate = c.getTime();
        
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.setTime(reportDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        Date endDate = ca.getTime();
        LOG.info("开始时间：" + startDate + ";结束时间：" + endDate);
		
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("merchantId", merchantId);
        map.put("startTime", startDate);
        map.put("endTime", endDate);
        map.put("sortColumns", "target_type");
		
        List<SynHealthDegreeVO> list = synHealthDegreeService.getSynHealthDegrees(map);
        JsonResult result = new JsonResult(true);
		if (list !=null && list.size() > 0) { // 该月份有关
			result.setData(list);
		} else { // 没有数据该月补0
			List<Map<String, Object>> dicList = sysDictionaryService.querySysDictionaryByType("00000061");
			list = new ArrayList<SynHealthDegreeVO>();
			for (Map<String, Object> m : dicList) {
				SynHealthDegreeVO health = new SynHealthDegreeVO();
				health.setMerchantId(merchantId);
				health.setReportTime(reportDate);
				health.setTargetType(String.valueOf(m.get("dictionary_key")));
				health.setTargetTypeText(String.valueOf(m.get("dictionary_value")));
				BigDecimal bd = new BigDecimal(0);
				health.setTargetValue(bd);
				health.setHealthDegree(bd);
				health.setActualValue(bd);
				list.add(health);
			}
			result.setData(list);
		}
        result.setMessage("健康度查询成功!");
		return result;
	}
	
	
}
