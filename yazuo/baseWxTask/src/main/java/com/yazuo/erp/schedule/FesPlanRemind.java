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

import com.yazuo.erp.fes.service.FesPlanService;
import com.yazuo.task.BaseTask;

/**
 * @Description
 * @author zhaohuaqin
 * @date 2014-8-22 下午2:56:48
 */
public class FesPlanRemind extends BaseTask {

	@Resource
	private FesPlanService fesPlanService;

	@Override
	public void execute1(Map params) {
		fesPlanService.batchSaveFesPlan();
	}

}
