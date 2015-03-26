package com.yazuo.weixin.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yazuo.weixin.service.WeixinManageService;

@Controller
@RequestMapping(value = "/weixin/data")
public class BusinessAutoreplyLoaderController {
	@Autowired
	private WeixinManageService weixinManageService;
	private static boolean init = false;

	@PostConstruct
	public void init() {
		if (!init) {
			init = true;
			System.out.println("加载所有自定义回复");
			weixinManageService.loadAutoreplyData();
			System.out.println("加载所有自定义回复完成");
		}
	}
}
