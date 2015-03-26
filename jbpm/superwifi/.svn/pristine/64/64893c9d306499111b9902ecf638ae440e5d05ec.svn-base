/*
 * 文件名：DeviceController.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年9月3日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.device.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.superwifi.device.service.DeviceService;
import com.yazuo.superwifi.device.vo.DeviceInfo;
import com.yazuo.superwifi.util.JsonResult;

@Controller
@RequestMapping("/controller/device")
public class DeviceController
{
    @Resource(name = "deviceServiceImpl")
    private DeviceService deviceService;
    
    @RequestMapping(value = "addDeviceInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object addDeviceInfo(DeviceInfo di)
        throws Exception
    {
        return null;
    }
    
    @RequestMapping(value = "deleteDeviceInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object deleteDeviceInfo(DeviceInfo di)
        throws Exception
    {
        return null;
    }
    
    /**
     * 添加或者修改mac码
     */
    @RequestMapping(value = "saveOrUpdateMac", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object saveOrUpdateMac(@RequestBody Map<String,Object> map)
        throws Exception
    {
    	JsonResult  result = new JsonResult();
    	deviceService.saveOrUpdateMac(map);
    	result.setFlag(true);
    	result.setMessage("商户对应的mac码添加/修改成功");
        return result;
    }
    /**
     * 逻辑删除不需要的mac码
     */
    @RequestMapping(value = "deteleMacById", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object deteleMacById(@RequestBody Map<String,Object> map)
        throws Exception
    {
    	JsonResult  result = new JsonResult();
    	deviceService.deteleMacById(map);
    	result.setFlag(true);
    	result.setMessage("商户对应的mac码删除成功");
        return result;
    }
    /**
     * 运营平台点击mac码管理，首先通过接口从crm得到商户的组织架构
     * 然后获取mongo中该集团的所有mac码信息放入map
     * 遍历集团架构，与map中的mac码信息对比，得到最终结果返回给前端
     */
    @RequestMapping(value = "manageMac", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object manageMac(@RequestBody Map<String,Object> map)
        throws Exception
    {
    	Map<String,Object> merMap = deviceService.getMacByMerchants(map);
    	
    	JsonResult  result = new JsonResult();
    	result.putData("totalSize", merMap.get("totalSize"));
    	result.putData("rows", merMap.get("newMerList"));
    	result.setFlag(true);
    	result.setMessage("商户对应的mac码查询成功");
        return result;
    }
    
    /**
     * 获取设备连接数量
     * @param paramMap
     * Integer merchantId 
     * @return JsonResult
     * @throws Exception
     * @author xiaguangyang@yazuo.com
     */
    @RequestMapping(value = "getConnetNum", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getConnetNum(@RequestBody Map<String, Object> paramMap)throws Exception {
    	JsonResult jsonInfo = new JsonResult();
    	Integer merchantId = 0; 	
    	if (null != paramMap){
    		merchantId=(Integer)paramMap.get("merchantId");
    	}
    	jsonInfo = deviceService.getConnectNum(merchantId);
    	
		return jsonInfo;		
	}
    
    /**
     * 获取设备信息
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getDeviceDetail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getDeviceDetail(@RequestBody
    	    Map<String, Object> paramMap)throws Exception {
    	JsonResult jsonInfo = new JsonResult();
    	if (null != paramMap){
    		jsonInfo = deviceService.getDeviceDetail(paramMap);	
    	}else{
    		jsonInfo.setFlag(false).setMessage("未传入参数");
    	}
		return jsonInfo;
		
    }
    /**
     * 获取店内WiFi连接速度排行
     * @param paramMap
     * @return
     * @throws Exception
     */
	    @ResponseBody
	    @RequestMapping(value = "getSpeedByMerchant", method = {RequestMethod.GET, RequestMethod.POST})
	    public JsonResult getSpeedByMerchant(@RequestBody Map<String, Object> paramMap)throws Exception {
	    	JsonResult res = new JsonResult(); 
	    	Integer merchantId = (Integer) paramMap.get("merchantId");
	    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			list = deviceService.getSpeedByMerchant(merchantId);
			
	    	res.setFlag(true).setMessage("流量排行榜查询成功！");
	    	res.putData("data", list);
	    	return res;
	    }
    
    /**
     * 更改设备信息
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateDeviceDetail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult updateDeviceDetail(@RequestBody
    	    Map<String, Object> paramMap)throws Exception {
    	JsonResult jsonInfo = new JsonResult();
    	if (null != paramMap){
    		jsonInfo = deviceService.updateDeviceDetail(paramMap);	
    	}else{
    		jsonInfo.setFlag(false).setMessage("未传入参数");
    	}
		return jsonInfo;
    }
    
    /**
     * 网络首页，显示设备个数
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getCurrentDeviceCount", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getCurrentDeviceCount(@RequestBody Map<String, Object> paramMap)throws Exception {
    	JsonResult jsonInfo = new JsonResult();
    	if (null != paramMap){
    		Long count = deviceService.getCurrentDeviceCount(paramMap);	
    		jsonInfo.setFlag(true).setMessage("设备个数查询成功").setData(count);
    	}else{
    		jsonInfo.setFlag(false).setMessage("未传入参数");
    	}
		return jsonInfo;
    	
    }
    
    
    /**
     * 获取所有设备的
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getMacList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getMacList(Integer merchantId)throws Exception {
        
        JsonResult jsonInfo = new JsonResult();
        
        List<String> macList=deviceService.getMacList(merchantId);
        
        jsonInfo.setFlag(true).setMessage("设备个数查询成功").setData(macList);
        return jsonInfo;
    }
    
    /**
     * 获取所有设备的
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "importUnactivatedDevice", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult importUnactivatedDevice(String mac)throws Exception {
        
        
        deviceService.importUnactivatedDevice(mac);
        
        JsonResult jsonInfo = new JsonResult();
        jsonInfo.setFlag(true).setMessage("设备个数查询成功");
        return jsonInfo;
        
    }
}
