package com.yazuo.superwifi.networkmanagement.service;

import java.util.List;
import java.util.Map;

import com.yazuo.superwifi.networkmanagement.vo.AppInfo;
import com.yazuo.superwifi.networkmanagement.vo.BlackWhiteList;
import com.yazuo.superwifi.util.JsonResult;


public interface NetworkManagementService {
	
	
	JsonResult addOrUpdateBlackWhiteList(Map<String, Object> paramMap) throws Exception;
	
	JsonResult getBlackWhiteList(Map<String, Object> paramMap) throws Exception;
	
	JsonResult getAccessRestrictionsList(Map<String, Object> paramMap) throws Exception;
	
	JsonResult getAccessRestrictionsNum(Map<String, Object> paramMap) throws Exception;
	
	JsonResult addAccessRestrictions(Map<String, Object> paramMap) throws Exception;
	
	JsonResult deleteAccessRestrictions(Map<String, Object> paramMap) throws Exception;
	
	JsonResult getBlackWhiteCountNum(Map<String, Object> paramMap) throws Exception;
	
	JsonResult synchronousDate(Map<String, Object> paramMap) throws Exception;
	
	JsonResult getTopAccessRestrictionsList(Map<String,Object> map) throws Exception;
	
	List<AppInfo> getAppInfoList()throws Exception;
	
	BlackWhiteList getBlackWhiteListByMacAndBrandId(Integer merchantId,String mac)throws Exception;
}
