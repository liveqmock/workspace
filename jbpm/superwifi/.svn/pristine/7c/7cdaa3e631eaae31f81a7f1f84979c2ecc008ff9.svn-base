/*
 * 文件名：DeviceService.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年9月2日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.device.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.yazuo.superwifi.device.vo.DeviceInfo;
import com.yazuo.superwifi.device.vo.UnactivatedDevice;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.util.JsonResult;


public interface DeviceService
{
	void saveOrUpdateMac(Map<String,Object> map)throws Exception;
	Map<String,Object> getMacByMerchants(Map<String,Object> map)throws Exception;
	void deteleMacById(Map<String,Object> map) throws Exception;

	JsonResult getConnectNum(Integer MerchantId) throws Exception;
	
	List<Map<String,Object>> getSpeedByMerchant(Integer merchantId)throws Exception;
	
	JsonResult getDeviceDetail(Map<String,Object> map) throws Exception;
	
	JsonResult updateDeviceDetail(Map<String,Object> map) throws Exception;
	void addSSID(Map<String,Object> map) throws Exception;
	Long getCurrentDeviceCount(Map<String,Object> map)throws Exception;
	
	DeviceInfo getDeviceInfoByMac(String mac) throws Exception;
	/**
	 * 
	 * Description: 获取未设备mac地址，merchantId为空时获取所有未激活的mac列表，不为空时获取未激活的mac列表以及本商户对应的mac列表
	 * Implement: <br>
	 * 1、…<br>
	 * 2、…<br>
	 * 
	 * @param merchantId
	 * @return
	 * @throws Exception 
	 * @see
	 */
	List<String> getMacList(Integer merchantId) throws Exception;
	
	void importUnactivatedDevice(String mac) throws Exception;
	
	void activatedDevice(String mac) throws Exception;
    
	void releaseDevice(String mac) throws Exception;
	
	void releaseDevice(Integer merchantId) throws Exception;
}
