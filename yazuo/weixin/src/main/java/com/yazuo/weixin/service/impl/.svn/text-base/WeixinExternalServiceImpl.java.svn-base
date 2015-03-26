package com.yazuo.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.WeixinExternalDao;
import com.yazuo.weixin.service.WeixinExternalService;
@Service
@Scope("prototype")
public class WeixinExternalServiceImpl implements WeixinExternalService{
	@Autowired
	private WeixinExternalDao weixinExternalDao;

	public String queryOpenId(String mobile, String brandId) throws Exception {
		return weixinExternalDao.queryOpenId(mobile, brandId);
	}

}
