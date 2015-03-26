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

import com.yazuo.erp.statistics.service.MerchantSMSCountStatisticsService;
import com.yazuo.task.BaseTask;

/**
 * @Description
 * @author zhaohuaqin
 * @date 2014-8-7 上午11:36:06
 */
public class MerchantSMSCountRemind extends BaseTask {

	@Resource
	private MerchantSMSCountStatisticsService merchantSMSCountStatisticsService;

	@Override
	public void execute1(Map params) {
		int count = 2000;
		try {
			count = Integer.valueOf(params.get("count").toString());
		} catch (Exception e) {
			count = 2000;
		}

		merchantSMSCountStatisticsService.merchantSMSCount(count);
	}

}
