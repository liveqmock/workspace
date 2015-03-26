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

import com.yazuo.erp.train.service.CourseStateCheckService;
import com.yazuo.task.BaseTask;

/**
 * @Description 定时检查课程是否超时
 * @author gaoshan
 * @date 2014-10-29 下午7:31:10
 */
public class CourseStateCheckTask extends BaseTask {
	@Resource
	private CourseStateCheckService courseStateCheckService;

	@Override
	public void execute1(Map params) {
		try {
			courseStateCheckService.courseStateCheck();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
