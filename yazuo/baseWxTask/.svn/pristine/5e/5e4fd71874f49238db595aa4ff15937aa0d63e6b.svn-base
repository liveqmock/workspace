/**
 * @Description 
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.schedule;

import java.util.Map;

import javax.annotation.Resource;

import com.yazuo.erp.statistics.service.MerchantCardCountStatisticsService;
import com.yazuo.task.BaseTask;

/**
 * @Description
 * @author zhaohuaqin
 * @date 2014-8-7 下午3:19:03
 */
public class MerchantCardCountRemind extends BaseTask {
	@Resource
	private MerchantCardCountStatisticsService merchantCardCountStatisticsService;

	@Override
	public void execute1(Map params) {

		int type = 3;
		try {
			type = Integer.valueOf(params.get("type").toString());
		} catch (Exception e) {
			type = 3;
		}

		int count = 1000;
		try {
			count = Integer.valueOf(params.get("count").toString());
		} catch (Exception e) {
			count = 1000;
		}

		int quantity = 800;
		try {
			quantity = Integer.valueOf(params.get("quantity").toString());
		} catch (Exception e) {
			quantity = 800;
		}
		merchantCardCountStatisticsService.merchantCardCountStatistics(type, count, quantity);
	}

}
