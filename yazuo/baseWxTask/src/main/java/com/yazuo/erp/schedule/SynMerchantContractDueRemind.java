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

import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.task.BaseTask;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-19 下午3:10:23
 */
public class SynMerchantContractDueRemind extends BaseTask {
	@Resource
	private SynMerchantService synMerchantService;

	@Override
	public void execute1(Map params) {
		try {
			synMerchantService.querySynMerchantList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
