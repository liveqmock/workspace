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
package com.yazuo.weixin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yazuo.weixin.timer.TaskDeliverService;
import com.yazuo.weixin.timer.TaskLogAnalysisService;


/**
* @ClassName TimerTaskController
* @Description  定时任务
* @author sundongfeng@yazuo.com
* @date 2014-7-24 上午11:30:23
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/timer/task")
public class TimerTaskController {
	private static final Log log = LogFactory.getLog("wxpay");
	@Autowired
	private TaskDeliverService taskDeliverService;
	@Autowired
	private TaskLogAnalysisService taskLogAnalysisService;
	
	@RequestMapping(value="deliver", method = { RequestMethod.POST,	RequestMethod.GET })
	public void deliver(HttpServletRequest request,HttpServletResponse response){
		taskDeliverService.deliver();
		try {
			response.getWriter().print("success");
		} catch (IOException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}

	}
	@RequestMapping(value="logAnalysis", method = { RequestMethod.POST,	RequestMethod.GET })
	public void logAnalysis(HttpServletRequest request,HttpServletResponse response){
		taskLogAnalysisService.logAnalysis();
		try {
			response.getWriter().print("success");
		} catch (IOException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
	}
	
	
}
