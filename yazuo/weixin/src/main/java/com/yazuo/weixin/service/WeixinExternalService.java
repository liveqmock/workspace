package com.yazuo.weixin.service;

/**
* @ClassName WeixinExternalService
* @Description  微信对外接口
* @author sundongfeng@yazuo.com
* @date 2014-6-30 上午11:28:27
* @version 1.0
*/
public interface WeixinExternalService {
	public String queryOpenId(String mobile,String brandId)throws Exception;
}
