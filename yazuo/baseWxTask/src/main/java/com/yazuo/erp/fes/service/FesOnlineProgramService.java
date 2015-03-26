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
package com.yazuo.erp.fes.service;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-4 上午11:20:11
 */
public interface FesOnlineProgramService {
	void cardMakeRemind() throws Exception;

	void materielMakeRemind() throws Exception;

	void cardDeliveryRemind() throws Exception;

	void equipmentDeliveryRemind() throws Exception;

	void draftProgramRemind() throws Exception;

	void onlineBefore5daysRemind() throws Exception;

	void onlineRemind() throws Exception;
}
