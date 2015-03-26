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

import com.yazuo.erp.syn.service.SynHealthDegreeService;
import com.yazuo.task.BaseTask;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-12 下午2:08:22
 */
public class SynHealthDegreeInsertTask extends BaseTask {
	@Resource
	private SynHealthDegreeService synHealthDegreeService;

	@Override
	public void execute1(Map params) {

		int indexId = 14;
		try {
			indexId = Integer.valueOf(params.get("indexId").toString());
		} catch (Exception e) {
			indexId = 14;
		}

		try {
			synHealthDegreeService.batchInsertSynHealthDegreeByIndexId(indexId);
			synHealthDegreeService.batchInsertCurrentMonthSynHealthDegreeByIndexId(indexId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
