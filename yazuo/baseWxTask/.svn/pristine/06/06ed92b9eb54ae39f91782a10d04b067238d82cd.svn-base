/**
 * @Description 月报检查
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.bes.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.erp.bes.dao.BesMonthlyCheckDao;
import com.yazuo.erp.bes.service.BesMonthlyCheckService;
import com.yazuo.erp.util.HttpClientUtil;
import com.yazuo.erp.util.SqlUtil;

/**
 * @Description 月报检查
 * @author gaoshan
 * @date 2014-10-29 下午7:31:10
 */
@Service
public class BesMonthlyCheckServiceImpl implements BesMonthlyCheckService {

	Log log = LogFactory.getLog(this.getClass());

	@Resource
	private BesMonthlyCheckDao besMonthlyCheckDao;

	@Value("#{configProperties['crm.mreportUrl'] }")
	String mreportUrl;

	/**
	 * @throws Exception
	 * @Title saveOrUpdateMonthlyCheck
	 * @Description
	 * @see com.yazuo.erp.bes.service.BesMonthlyCheckService#saveOrUpdateMonthlyCheck()
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdateMonthlyCheck() throws Exception {
		String yearMonth = null;// 年月，例如2014-10
		String pattern = null;// 年月日，例如2014-10-01
		SimpleDateFormat sdf = null;

		// crm查询结果
		List<Map<String, Object>> crmList = new ArrayList<Map<String, Object>>();
		try {
			// 调用crm月报检查接口，查询数据
			String result = HttpClientUtil.execRequestAndGetResponse(mreportUrl);

			// 校验返回数据是否为空
			JSONObject resultObj = JSONObject.fromObject(result);
			if (null == resultObj) {
				log.error("[CRM月报检查]服务无返回");
				return;
			}
			log.info("resultJSON:" + resultObj.toString());

			// 校验服务是否调用成功
			String flag = resultObj.getString("flag");
			if ("0".equals(flag)) {
				String message = resultObj.getString("message");
				log.error("[CRM月报检查]服务调用失败，" + message);
				return;
			}

			// 校验数据集
			JSONArray dataObj = resultObj.getJSONArray("data");
			int size = dataObj.size();
			if (0 == size) {
				log.error("[CRM月报检查]服务调用成功，列表条数0");
				return;
			}

			for (int i = 0; i < size; i++) {
				JSONObject obj = (JSONObject) dataObj.get(i);
				int brandId = obj.getInt("brand_id");
				String brand = obj.getString("brand");
				String month = obj.getString("month");
				int deskNum = obj.getInt("desknum");
				int overview = obj.getInt("overview");
				int effect = obj.getInt("effect");
				int development = obj.getInt("development");
				int consumption = obj.getInt("consumption");
				int storage = obj.getInt("storage");
				int marketing = obj.getInt("marketing");
				int quality = obj.getInt("quality");
				int status = obj.getInt("status");
				pattern = month + "-01";

				if (null == sdf) {
					sdf = new SimpleDateFormat(pattern);
					yearMonth = month;
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tableName", "bes.bes_monthly_check");// 表名
				map.put("merchant_id", brandId);
				map.put("report_time", pattern);
				map.put("open_number", String.valueOf(deskNum));
				map.put("job_overview", String.valueOf(overview));
				map.put("effect_and_suggestion", String.valueOf(effect));
				map.put("membership_development", String.valueOf(development));
				map.put("member_consumption", String.valueOf(consumption));
				map.put("membership_value", String.valueOf(storage));
				map.put("affiliate_marketing", String.valueOf(marketing));
				map.put("population_quality", quality);
				map.put("monthly_check_state", status);
				map.put("update_by", 1);
				map.put("update_time", new Date());

				crmList.add(map);
			}
		} catch (Exception e) {
			log.error("[CRM月报检查]服务调用失败");
			log.error(e.getMessage());
			e.printStackTrace();
		}

		if (null != crmList && 0 < crmList.size() && null != sdf) {
			// 根据月份删除ERP月报检查数据
			this.besMonthlyCheckDao.deleteBesMonthlyCheckByMonth(yearMonth);
			log.info("[" + yearMonth + "]月报检查，批量删除成功");

			// 批量添加
			if (crmList != null && crmList.size() > 0) {
				String addSqls = SqlUtil.generateBatchInsertSql(crmList);
				besMonthlyCheckDao.batchExecuteSql(addSqls);
				log.info("[" + yearMonth + "]月报检查，批量添加" + crmList.size() + "条记录");
			}
		}
	}

}
