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
package com.yazuo.erp.schedule;

import java.util.Map;

import javax.annotation.Resource;

import com.yazuo.erp.bes.service.BesMonthlyCheckService;
import com.yazuo.erp.syn.service.SynHealthDegreeService;
import com.yazuo.task.BaseTask;

/**
 * @Description 月报检查
 * @author gaoshan
 * @date 2014-10-29 下午7:31:10
 */
public class BesMonthlyCheckTask extends BaseTask {
	@Resource
	private BesMonthlyCheckService besMonthlyCheckService;

	@Override
	public void execute1(Map params) {
		try {
			besMonthlyCheckService.saveOrUpdateMonthlyCheck();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
