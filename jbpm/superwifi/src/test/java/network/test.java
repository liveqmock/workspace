package network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yazuo.superwifi.device.service.DeviceService;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.networkmanagement.service.NetworkManagementService;
import com.yazuo.superwifi.util.JsonResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-superwifi-*.xml"})
public class test {
	
	@Resource(name = "networkManagementServiceImpl")
    private NetworkManagementService networkManagementService;
	
	 @Resource(name = "deviceServiceImpl")
	    private DeviceService deviceService;
	@Resource(name="mongoTemplate")
    private MongoTemplate mongoTemplate; 
	private static final Logger log = Logger.getLogger(test.class);
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
	@Test
	public void addAccessRestrictions(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> urlList = new ArrayList<String>();
		String url = "www.baidu.com";
		urlList.add(url);
		paramMap.put("brandId", 15);
		paramMap.put("merchantId", 2);
		paramMap.put("type", 1);
		paramMap.put("url", urlList);
		paramMap.put("managerPsd", "123");
		
		try{
			JsonResult jsoninfo  = networkManagementService.addAccessRestrictions(paramMap);
			log.info(jsoninfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getAccessRestrictionsList(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("brandId", 15);
		paramMap.put("merchantId", 2);
		paramMap.put("type", 1);
		paramMap.put("pageSize", 10);
		paramMap.put("pageNumber", 1);
		try{
			JsonResult jsoninfo  = networkManagementService.getAccessRestrictionsList(paramMap);
			log.info(jsoninfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void addOrUpdateBlackWhiteList(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> phonenum = new ArrayList<String>();
		String a ="1231241";
		String b ="1231242";
		String c ="1231243";
		String d ="1231244";
		phonenum.add(a);
		phonenum.add(b);
		phonenum.add(c);
		phonenum.add(d);
		paramMap.put("brandId", 15);
		paramMap.put("merchantId", 2);
		paramMap.put("type", 1);
		paramMap.put("phoneNum", phonenum);
		paramMap.put("managerPsd", "123");
		paramMap.put("actionType", 1);
		try{
			JsonResult jsoninfo  = networkManagementService.addOrUpdateBlackWhiteList(paramMap);
			log.info(jsoninfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getBlackWhiteList(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("brandId", 15);
		paramMap.put("merchantId", 1);
		paramMap.put("type", 1);
		paramMap.put("phoneNum", "1");
		paramMap.put("pageSize", 10);
		paramMap.put("pageNumber", 1);
		try{
			JsonResult jsoninfo  = networkManagementService.getBlackWhiteList(paramMap);
			log.info(jsoninfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Test
	public void getBlackWhiteCountNum(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("brandId", 15);
		paramMap.put("merchantId", 1);
		paramMap.put("type", 1);
		try{
			JsonResult jsoninfo  = networkManagementService.getBlackWhiteCountNum(paramMap);
			log.info(jsoninfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAccessRestrictionsNum(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("brandId", 15);
		paramMap.put("merchantId", 1);
		try{
			JsonResult jsoninfo  = networkManagementService.getAccessRestrictionsNum(paramMap);
			log.info(jsoninfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void getDeviceDetail(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("brandId", 15);
		paramMap.put("merchantId", 2251);
		try{
			JsonResult jsoninfo  = deviceService.getDeviceDetail(paramMap);
			log.info(jsoninfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateDeviceDetail(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceId", "548965f1dc595477865c0343");
		paramMap.put("deviceName", "夏广洋测试");
		paramMap.put("managerPsd", "sdfasd");
		try{
			JsonResult jsoninfo  = deviceService.updateDeviceDetail(paramMap);
			log.info(jsoninfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void addSSID(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceName", "yazuo");
		paramMap.put("isMain", true);
		try{
			deviceService.addSSID(paramMap);
//			log.info(jsoninfo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
    public void addC(){
	    mongoTemplate.updateFirst(new Query(Criteria.where("status").is(1)), Update.update("portassslPicss", "fawefa"), MerchantInfo.class);
    }
	
}
