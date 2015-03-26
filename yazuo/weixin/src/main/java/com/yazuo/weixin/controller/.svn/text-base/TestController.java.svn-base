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

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.service.Test;
import com.yazuo.weixin.util.CommonUtil;

/**
 * @Description TODO
 * @author yazuo
 * @date 2014-7-23 下午7:46:20
 */
@Controller
@RequestMapping("/weixin/test")
public class TestController {
	private static final Log log = LogFactory.getLog("mall");
	@Resource
	private Test test;
	
	@RequestMapping(value = "importData", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void importData() {
//		test.savePersonData();
		for(int i=0;i<10000;i++){
			String url = "http://124.42.38.70/yazuo-weixin/imageCoffee/coffee_card.jpg";//?r="+i;
			try {
				CommonUtil.postMessage(url);
			} catch (WeixinRuntimeException e) {
				log.error("code happen error.",e);
				e.printStackTrace();
			}
		}
	}
}
