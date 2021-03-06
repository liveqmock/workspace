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

import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.task.BaseTask;

/**
 * @Description
 * @author zhaohuaqin
 * @date 2014-8-4 上午11:51:40
 */
public class CardMakeRemind extends BaseTask {

	@Resource
	private FesOnlineProgramService fesOnlineProgramService;

	@Override
	public void execute1(Map params) {
		try {
			fesOnlineProgramService.cardMakeRemind();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
