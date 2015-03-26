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

import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.task.BaseTask;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-5 上午10:24:56
 */
public class OnlineRemind extends BaseTask {

	@Resource
	private FesOnlineProgramService fesOnlineProgramService;

	@Override
	public void execute1(Map params) {
		try {
			fesOnlineProgramService.onlineRemind();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
