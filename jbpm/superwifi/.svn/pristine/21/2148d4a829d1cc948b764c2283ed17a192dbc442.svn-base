/*
 * 文件名：DeviceServiceImpl.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年9月2日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.device.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.yazuo.superwifi.device.service.DeviceService;
import com.yazuo.superwifi.device.vo.DeviceInfo;
import com.yazuo.superwifi.device.vo.DeviceSSID;
import com.yazuo.superwifi.device.vo.UnactivatedDevice;
import com.yazuo.superwifi.exception.BussinessException;
import com.yazuo.superwifi.member.vo.Member;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.util.HttpUtil;
import com.yazuo.superwifi.util.JsonResult;


@Service("deviceServiceImpl")
public class DeviceServiceImpl implements DeviceService
{

	@Value("#{propertiesReader['yazuoToken']}")
    String yazuoToken;
	
	@Value("#{propertiesReader['deviceMemCount']}")
    String deviceMemCount;
	
    @Value("#{propertiesReader['getMerchantChildListByMerchantId']}")
    String getMerchantChildListByMerchantId;
    
    @Value("#{propertiesReader['oneMax']}")
    Integer oneMax;//单个设备最多可连接人数
    
    @Value("#{propertiesReader['crmApiKey']}")
    private String crmApiKey;
    
	@Resource(name = "merchantServiceImpl")
	private MerchantService merchantService;
    
    @Resource(name="mongoTemplate")
    private MongoTemplate mongoTemplate; 
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
    Logger log = Logger.getLogger(this.getClass());
    
    @Override
   public void saveOrUpdateMac(Map<String,Object> map)throws Exception{
    	//逻辑删除该商户原有的mac码
    	//WriteResult wr = mongoTemplate.updateFirst(new Query(Criteria.where("status").is(DeviceInfo.STATUS_NORMAL).and("merchantId").is(merchantId)), Update.update("status", DeviceInfo.STATUS_DELETE),DeviceInfo.class);
    	Integer merchantId = (Integer) map.get("merchantId");
    	Integer brandId = (Integer) map.get("brandId");
    	List<Map<String,Object>> devList =  (List<Map<String, Object>>) map.get("mac");
        for (int i = 0; i < devList.size(); i++ ){
        	Map<String,Object> mp = devList.get(i);
        	String id = mp.get("id").toString();
        	String mac = mp.get("mac").toString();
        	
        	DeviceInfo dev = new DeviceInfo();
        	if(id != null && !"".equals(id)){
        		//修改mac码
        		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), Update.update("mac", mac).set("lastUpdateTime", new Date()), DeviceInfo.class);
        	}else{
        		dev.setId(null);
        		dev.setMac(mac);
        		dev.setInsertTime(new Date());
        		dev.setLastUpdateTime(new Date());
        		dev.setStatus(DeviceInfo.STATUS_NORMAL);
        		dev.setMerchantId(merchantId);
        		dev.setBrandId(brandId);
            	mongoTemplate.insert(dev);
        	}
        }
    }
    
    @Override
    public void deteleMacById(Map<String,Object> map) throws Exception{
    	String id = map.get("id").toString();
    	mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), Update.update("status", DeviceInfo.STATUS_DELETE).set("lastUpdateTime", new Date()), DeviceInfo.class);
    	//mongoTemplate.findAndRemove(new Query(Criteria.where("_id").is(id)), DeviceInfo.class);
    }
    @Override
	public Map<String,Object> getMacByMerchants(Map<String,Object> map)throws Exception{
    	Integer brandId = (Integer) map.get("brandId");
    	String merchantId = map.get("merchantId")==null?"":map.get("merchantId").toString();
    	Boolean isFaceShop = (Boolean)map.get("isFaceShop");
    	String merchantName = map.get("merchantName")==null?"":map.get("merchantName").toString();
    	Integer pageSize = (Integer)map.get("pageSize");
    	Integer pageNumber = (Integer)map.get("pageNumber");
    	List<MerchantInfo> merchantsList = jsonMerchant(brandId );
    	//需要分页的总条数
    	int totalSize = 0;
    	
        List<DeviceInfo> dList = mongoTemplate.find(new Query(Criteria.where("status").is(1).and("brandId").is(brandId)), DeviceInfo.class); 
    	//处理后的商户信息，集团下面的所有门店，包括mac码和上级商户名称
    	List<MerchantInfo>newMerList = new ArrayList<MerchantInfo>();
    	//遍历集团树形结构，得到商户对应的mac码，同时得到门店数量，作为totalSize输出
    	for(MerchantInfo m : merchantsList){
    		List<DeviceInfo>merMacList = new ArrayList<DeviceInfo>();
    		//得到商户对应的mac码
    		for(DeviceInfo dev : dList){
    			if(m.getIsFaceShop() && m.getMerchantId().intValue() == dev.getMerchantId()){
    				merMacList.add(dev);
    			}
    		}
    		//循环商户结构，取得上级名称
			for(MerchantInfo m1 : merchantsList){
				if(m.getParent().intValue() == m1.getMerchantId()){
					m.setParentName(m1.getMerchantName());
				}
			}
    		
    		if(m.getIsFaceShop()){
        		m.setDeviceInfoList(merMacList);
        		MerchantInfo mer = (MerchantInfo) m.clone();
        		newMerList.add(mer);
    		}
    	}
    	
    	//判断商户的级别，集团，分公司，门店
    	//新的结果集，存放分好的数据,得到门店数量，作为totalSize输出
    	List<MerchantInfo> newMers = new ArrayList<MerchantInfo>();
    	if(merchantName != null && !"".equals(merchantName)){
    		//如果是按照商户名称查询的
    		for(MerchantInfo m:newMerList){//遍历结果集，取出该门店的数据
    			if(m.getMerchantName().indexOf(merchantName) != -1){
    				newMers.add(m);

            		totalSize++;
    			}
    		}
    	}
    	//如果是按照id查询的
    	else if(merchantId != null && !"".equals(merchantId)){
    		Integer merId =Integer.parseInt(merchantId);
	    	if(brandId.intValue() == merId.intValue()){//如果是集团，newMerList不用做改动
	    		newMers = newMerList;
	
	    		totalSize = newMerList.size();
	    	}else if(!brandId.equals(merchantId) && !isFaceShop){//是分公司
	    		for(MerchantInfo m:newMerList){//遍历结果集，取出某分公司下的所有门店的数据
	    			if(m.getParent().intValue() == merId.intValue()){
	    				newMers.add(m);
	
	            		totalSize++;
	    			}
	    		}
	    	}else if(isFaceShop){//是门店
	    		for(MerchantInfo m:newMerList){//遍历结果集，取出该门店的数据
	    			if(m.getMerchantId().intValue() == merId.intValue()){
	    				newMers.add(m);
	
	            		totalSize++;
	    			}
	    		}
	    	}
    	}
    	//实行分页，对处理完成的结果集进行分页
    	map.clear();
    	
    	Integer fromIndex = (pageNumber-1) * pageSize;
    	Integer toIndex = (int) (pageNumber * pageSize>totalSize?totalSize:pageNumber*pageSize);
    	if(fromIndex<=toIndex){
    		newMers = newMers.subList(fromIndex, toIndex);  
    	}else{
    		newMers = new ArrayList<MerchantInfo>();
    	}
    	map.put("totalSize", totalSize);
    	map.put("newMerList", newMers);
    	return map;
    }
    /**
     * 把从crm获取的信息放到一个集合里
     * @return
     * @throws Exception
     */
    public List<MerchantInfo> jsonMerchant(Integer brandId)
        throws Exception
    {
    	List<MerchantInfo> merchantsList = new ArrayList<MerchantInfo>();

        // 获取CRM的商户信息
        String json = getMerchantChildListByMerchantId(brandId);
        json=json.replace("\\t", "");

        // 解析json
        JSONObject ja = JSONObject.fromObject(json);
        JSONObject jo = JSONObject.fromObject(ja.get("data"));
        JSONObject jo11 = JSONObject.fromObject(jo.get("result"));
        String jo111 = jo11.get("merchantList").toString();
        if (jo111.isEmpty() || jo111.toString().equals(""))
        {
            log.error("CRM没有查到id为：" + brandId + "的商户信息，请检查crmMerchantId是否正确");
            return null;
        }

        JSONArray jo1 = JSONArray.fromObject(jo11.get("merchantList"));

        if (jo1 != null && jo1.size() > 0)
        {
        	for(int i=0;i<jo1.size();i++){
            	MerchantInfo m = new MerchantInfo();
            	
	            String merchantName = jo1.getJSONObject(i).get("merchantName").toString();
	            Integer newMerchantid = jo1.getJSONObject(i).getInt("merchantId");
	            Integer parentMerchantid = jo1.getJSONObject(i).getInt("parent");
	            Boolean isFaceShop = jo1.getJSONObject(i).getBoolean("isFaceShop");
	            
	            m.setIsFaceShop(isFaceShop);
	            m.setMerchantId(newMerchantid);
	            m.setMerchantName(merchantName);
	            m.setParent(parentMerchantid);
	            merchantsList.add(m);
        	}
        }
        return merchantsList;
    }
    public String getMerchantChildListByMerchantId(Integer merchantId)
            throws Exception
        {
            String json = "";

            JSONObject jo = new JSONObject();
            jo.accumulate("merchantId", merchantId);
            String ciphertext = jo.toString();

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("ciphertext", ciphertext));
            nvps.add(new BasicNameValuePair("key", crmApiKey));

            log.info("访问参数地址：" + getMerchantChildListByMerchantId);
            json = HttpUtil.httpPostForString(nvps, getMerchantChildListByMerchantId).toString();

            return json;
        }
    
    @Override
	public JsonResult getConnectNum(Integer merchantId) throws Exception {
		JsonResult jsoninfo = new JsonResult();
        List< DeviceInfo> dclist= mongoTemplate.find( new Query(Criteria.where("merchantId" ).is(merchantId)), DeviceInfo. class);
        String deviceId = "" ;
        List<DeviceInfo> macs = new ArrayList<DeviceInfo>();//存储过滤掉“-”以后的mac码
         for (DeviceInfo dc:dclist ){
        	 dc.setMac(dc.getMac().replaceAll("-", ""));
             deviceId += "deviceId=" +dc.getMac()+"&";
             macs.add(dc);
        }
        log.info(deviceId);
//		  List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		  nvps.add( new BasicNameValuePair("token" , yazuoToken));
//		  nvps.add( new BasicNameValuePair("deviceId" , deviceId));
//		  nvps.add( new BasicNameValuePair("data" , null));              
//		  String connectnum = HttpUtil.httpPostForString(nvps, deviceMemCount);
//		 
//		  JSONObject node = JSONObject.fromObject(connectnum);
         
		String connectnum = "{\"code\":\"1000\",\"data\":[{\"deviceId\":\"ac81128feddd\",\"count\":\"123\"},{\"deviceId\":\"af41148feddd\",\"count\":\"42\"},{\"deviceId\":\"ga41158fedtt\",\"count\":\"97\"},{\"deviceId\":\"544565jf7gkf\",\"count\":\"56\"}]}";
        JSONObject node = JSONObject.fromObject(connectnum);
     	String code = node.getString("code");
		if ("1000" .equals(code)){
			//如果查询终端连接数成功，计算设备连接人数，设备使用率等情况
			JSONArray json = JSONArray.fromObject(node.get("data"));
			Integer total = 0;//门店设备连接数(各个设备的连接总和)
			Integer totalMax = json.size() * oneMax;//总的设备可连接人数
			List<Object> oneUserInfoList = new ArrayList<Object>();
			for(int i = 0;i<json.size();i++){
				Map<String,Object> oneUseInfo = new HashMap<String, Object>();//存放单个设备使用情况
				String mac = json.getJSONObject(i).getString("deviceId");
				Integer count = json.getJSONObject(i).getInt("count");
				total += count;
				
				for(DeviceInfo dev:macs ){
					if(mac.toLowerCase().equals(dev.getMac().toLowerCase())){
						//单个设备使用率 = 单个设备可连接人数/单个设备已连接人数*100
						BigDecimal onePercent = new BigDecimal(count).divide(new BigDecimal(oneMax),2, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100));
						oneUseInfo.put("count", count);//单个设备当前使用数
						oneUseInfo.put("percent", onePercent+"%");//单个设备使用率
						
						Integer macSize = dev.getDevSSID() == null?0:dev.getDevSSID().size();//商户ssid名称数
						String devName = "设备名称";
						if(macSize >0){
							for(int j = 0;j<macSize;j++){
								if(dev.getDevSSID().get(j).getIsMain()){
									devName = dev.getDevSSID().get(j).getSsid();//设备名称
								}
							}
						}
						oneUseInfo.put("devName", devName);
						oneUserInfoList.add(oneUseInfo);
					}
				}
			}
			
			//计算总的设备使用率,总的设备使用率=总的设备可连接人数/总的设备连接人数*100
			BigDecimal totalPercent = new BigDecimal(total).divide(new BigDecimal(totalMax),2, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100));
			
			Map<String,Object> totalUseInfo = new HashMap<String, Object>();//存放总的设备使用情况
			totalUseInfo.put("totalPercent", totalPercent+"%");
			totalUseInfo.put("total", total);
			
			jsoninfo.setFlag( true ).setMessage("查询终端数成功" );
			jsoninfo.putData("totalUseInfo", totalUseInfo);
			jsoninfo.putData("oneUseInfo", oneUserInfoList);
		} 
		return jsoninfo;
	}
    
    @Override
    public List<Map<String,Object>> getSpeedByMerchant(Integer merchantId)throws Exception{
    	List<Map<String,Object>> lastList = new ArrayList<Map<String,Object>>();
    	
    	List< DeviceInfo> dclist= mongoTemplate.find( new Query(Criteria.where("merchantId" ).is(merchantId)), DeviceInfo. class);
        String deviceId = "" ;
        List<DeviceInfo> macs = new ArrayList<DeviceInfo>();//存储过滤掉“-”以后的mac码
         for (DeviceInfo dc:dclist ){
        	 dc.setMac(dc.getMac().replaceAll("-", ""));
             deviceId += "deviceId=" +dc.getMac()+"&";
             macs.add(dc);
        }
         log.info(deviceId);
//		  List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		  nvps.add( new BasicNameValuePair("token" , yazuoToken));
//		  nvps.add( new BasicNameValuePair("deviceId" , deviceId));
//		  nvps.add( new BasicNameValuePair("data" , null));              
//		  String connectnum = HttpUtil.httpPostForString(nvps, deviceMemCount);
//		 
//		  JSONObject node = JSONObject.fromObject(connectnum);
		  
         String connectnum ="{\"code\":\"1000\",\"data\":[" +
         		"{\"deviceId\":\"ac81128feddd\",\"staList\":[" +
         		"		{\"staMac\":\"sdas32dgewww\",\"upFlow\":\"23\",\"downFlow\":\"12\"}," +
         		"		{\"staMac\":\"wewrwq\",\"upFlow\":\"56\",\"downFlow\":\"53\"}," +
         		"		{\"staMac\":\"352dsfsf\",\"upFlow\":\"567\",\"downFlow\":\"57\"}]}," +
         		"{\"deviceId\":\"af41148feddd\",\"staList\":[" +
         		"		{\"staMac\":\"fswulghd7408\",\"upFlow\":\"42\",\"downFlow\":\"75\"}," +
         		"		{\"staMac\":\"423fds\",\"upFlow\":\"35\",\"downFlow\":\"56\"}]}," +
         		"{\"deviceId\":\"544565jf7gkf\",\"staList\":[" +
         		"		{\"staMac\":\"sdgfs322\",\"upFlow\":\"64\",\"downFlow\":\"41\"}," +
         		"		{\"staMac\":\"234sdfa\",\"upFlow\":\"58\",\"downFlow\":\"23\"}]}" +
         		"]}";
         
         JSONObject node = JSONObject.fromObject(connectnum);
         String code = node.getString("code");
         if("1000".equals(code)){
        	 JSONArray json = JSONArray.fromObject(node.get("data"));
        	 
        	 List<Map<String,Object>> memList = new ArrayList<Map<String,Object>>();
        	 for(int i =0;i<json.size();i++){//循环店内WiFi设备
        		 String mac = json.getJSONObject(i).getString("deviceId");
        		 JSONArray memberList =  json.getJSONObject(i).getJSONArray("staList");
        		 for(int j=0;j<memberList.size();j++){//循环member的流量信息
        			 String phoneMac = memberList.getJSONObject(j).getString("staMac");//手机mac码
        			 Integer upFlow = memberList.getJSONObject(j).getInt("upFlow");//上行流量
        			 Integer downFlow = memberList.getJSONObject(j).getInt("downFlow");//下行流量
        			 
        			 Map<String,Object> mFlow = new HashMap<String, Object>();
        			 mFlow.put("phoneMac", phoneMac);
//        			 mFlow.put("upFlow", upFlow);
//        			 mFlow.put("downFlow", downFlow);
        			 mFlow.put("flow", upFlow+downFlow);
        			 mFlow.put("deviceId", mac);
        			 
        			 memList.add(mFlow);
        		 }
        	 }
        	 //获取流量对应的手机号,需要会员表配合
        	 //查询会员信息
        	 List<Member> members = mongoTemplate.find(new Query(Criteria.where("status").is(Member.STATUS_NORMAL)), Member.class);
        	 for(Member me : members){
        		 for(int i = 0;i<memList.size();i++){
        			 String phoneMac = memList.get(i).get("phoneMac").toString();
        			 if(me.getMac().replace("-", "").equals(phoneMac)){
        				 memList.get(i).put("phoneNumber", me.getPhoneNumber());
        				 lastList.add(memList.get(i));
        			 }
        		 }
        	 }
        	 //冒泡排序
	        Map<String,Object> temp = null ;
	        for (int i = 0 ; i < lastList.size() - 1 ; i++){
		        for (int j = i + 1 ; j < lastList.size() ; j++){
		        	Integer a = (Integer) lastList.get(i).get("flow") ;
		        	Integer b = (Integer) lastList.get(j).get("flow") ;
    		        if (a < b){
        		        temp = lastList.get(i) ;
        		        lastList.set(i, lastList.get(j));
        		        lastList.set(j,temp);
    		        }
		        }
	        }
         }
    	return lastList;
    }

	@Override
	public JsonResult getDeviceDetail(Map<String, Object> paramMap) throws Exception {
		JsonResult jsonInfo = new JsonResult();
		Integer merchantId = (Integer)paramMap.get("merchantId");
		Integer brandId = (Integer) paramMap.get("brandId");
		if(merchantId!=0&&merchantId!=null){
			//查询到商户的设备信息
			List<DeviceInfo> deviceList = mongoTemplate.find(new Query(Criteria.where("merchantId").is(merchantId)
					.and("brandId").is(brandId)),DeviceInfo.class);
			
			jsonInfo.setFlag(true).setMessage("查询成功");
			jsonInfo.putData("totalSize", deviceList.size());
			jsonInfo.putData("rows", deviceList);
		}else{
			jsonInfo.setFlag(false).setMessage("查询失败，或无此商户");
		}
		return jsonInfo;
	}

	@Override
	public JsonResult updateDeviceDetail(Map<String, Object> paramMap)
			throws Exception {
		JsonResult jsonInfo = new JsonResult();
		String deviceId = (String) paramMap.get("deviceId");
		String devSSIDid = paramMap.get("devSSIDid").toString();
		String deviceName = (String) paramMap.get("deviceName");
		String managerPsd = (String)paramMap.get("managerPsd");
		Integer brandId = (Integer) paramMap.get("brandId");
		Integer merchantId = (Integer)paramMap.get("merchantId");
		if(!"".equals(deviceName)&&!"".equals(deviceId)&&!"".equals(managerPsd)&&managerPsd!=null){
			//判断主管密码
			Boolean flag = merchantService.checkAdminPassWord(merchantId, brandId, managerPsd);
			if(flag){
				//在mongo中，直接更新子表的数据，附表一般不会跟着变，要从父表来更新子表的数据
				DeviceInfo dev = mongoTemplate.findOne(new Query(Criteria.where("id").is(deviceId)), DeviceInfo.class);
				for(DeviceSSID ds:dev.getDevSSID()){
					if(ds.getId().equals(devSSIDid)){
						//查询到该ssid，跟新该ssid的name，然后更新设备表的ssid即可
						mongoTemplate.updateFirst(new Query(Criteria.where("id").is(devSSIDid)),Update.update("ssid", deviceName), DeviceSSID.class);
						DeviceSSID d = mongoTemplate.findOne(new Query(Criteria.where("id").is(devSSIDid)), DeviceSSID.class);
						//更新父表的数据
						mongoTemplate.updateFirst(new Query(Criteria.where("id").is(devSSIDid)),Update.update("devSSID", d), DeviceInfo.class);
						//如果能够确定用的是dbref引用，也可只更新子表的name,此种方法省略
					}
				}
				jsonInfo.setFlag(true).setMessage("修改成功");
			}else{
				jsonInfo.setFlag(false).setMessage("主管密码错误");
			}			
		}else{
			jsonInfo.setFlag(false).setMessage("请正确输入参数");
		}
		return jsonInfo;
	}
	
	/**
	 * 增加ssid
	 */
	@Override
	public void addSSID(Map<String, Object> paramMap){
		List<DeviceInfo> di = mongoTemplate.find(new Query(Criteria.where("status").is(1)), DeviceInfo.class);
		for(DeviceInfo d:di){
			if(d.getDevSSID()==null){
				DeviceSSID DS = new DeviceSSID();
				String deviceName = (String) paramMap.get("deviceName");
				Boolean isMain = (Boolean) paramMap.get("isMain");
				DS.setSsid(deviceName);
				DS.setIsMain(isMain);
				mongoTemplate.insert(DS);
				List<DeviceSSID> dlist =mongoTemplate.find(new Query(Criteria.where("ssid").is(deviceName)), DeviceSSID.class);
				mongoTemplate.updateFirst(new Query(Criteria.where("status").is(1)), Update.update("devSSID", dlist), DeviceInfo.class);
			}
		}
	}
	
	@Override
	public Long getCurrentDeviceCount(Map<String,Object> map)throws Exception{
		Long count = (long) 0;
		Integer merchantId = (Integer) map.get("merchantId");
		count = mongoTemplate.count(new Query(Criteria.where("merchantId").is(merchantId).and("status").is(DeviceInfo.STATUS_NORMAL)), DeviceInfo.class);
		return count;
	}

    @Override
    public DeviceInfo getDeviceInfoByMac(String mac) throws Exception
    {
        List<DeviceInfo> deviceInfoList = mongoTemplate.find(new Query(
            Criteria.where("mac").is(mac.replace(":", "")).and("status").is(DeviceInfo.STATUS_NORMAL)), DeviceInfo.class);
        if (null != deviceInfoList && deviceInfoList.size() > 0)
        {
            return deviceInfoList.get(0);
        }else{
            throw new BussinessException("设备不存在或状态不正常");
        }
    }
    
    @Override
    public List<String> getMacList(Integer merchantId) throws Exception{
        List<String> macList=new ArrayList<String>();
        List<UnactivatedDevice> unactivatedDeviceList = mongoTemplate.find(new Query(
            Criteria.where("status").is(UnactivatedDevice.STATUS_RELEASE)), UnactivatedDevice.class);
        
        for(UnactivatedDevice ud:unactivatedDeviceList){
            String mac=ud.getMac().toLowerCase();
            mac = mac.substring(0, 2) + "-" + mac.substring(2, 4) + "-" + mac.substring(4, 6) + "-"
                + mac.substring(6, 8) + "-" + mac.substring(8, 10) + "-" + mac.substring(10, 12);
            macList.add(mac);
        }
        if(null!=merchantId){
            List<DeviceInfo> merchantDeviceList = mongoTemplate.find(new Query(
                Criteria.where("merchantId").is(merchantId)), DeviceInfo.class);
            for(DeviceInfo md:merchantDeviceList){
                String mac=md.getMac().toLowerCase();
                mac = mac.substring(0, 2) + "-" + mac.substring(2, 4) + "-" + mac.substring(4, 6) + "-"
                    + mac.substring(6, 8) + "-" + mac.substring(8, 10) + "-" + mac.substring(10, 12);
                macList.add(mac);
            }
        }
        return macList;
    }
    @Override
    public void importUnactivatedDevice(String mac) throws Exception{
        UnactivatedDevice ud=new UnactivatedDevice();
        ud.setInsertTime(new Date());
        ud.setLastUpdateTime(new Date());
        ud.setMac(mac);
        ud.setStatus(UnactivatedDevice.STATUS_RELEASE);
        mongoTemplate.insert(ud);
    }
    
    @Override
    public void activatedDevice(String mac) throws Exception{
        Update up = new Update();
        up.set("status", UnactivatedDevice.STATUS_ACTIVATED);
        mongoTemplate.updateMulti(
            new Query(Criteria.where("mac").is(mac)),up
            , UnactivatedDevice.class);
    }
    
    @Override
    public void releaseDevice(String mac) throws Exception{
        Update up = new Update();
        up.set("status", UnactivatedDevice.STATUS_RELEASE);
        mongoTemplate.updateMulti(
            new Query(Criteria.where("mac").is(mac)),up
            , UnactivatedDevice.class);
    }
    
    @Override
    public void releaseDevice(Integer merchantId) throws Exception{
        Query query = new Query(Criteria.where("merchantId").is(merchantId));
        List<DeviceInfo> deviceInfoList= mongoTemplate.find(query, DeviceInfo.class);
        for(DeviceInfo di:deviceInfoList){
            mongoTemplate.updateMulti(
                new Query(Criteria.where("mac").is(di.getMac())),
                Update.update("status", UnactivatedDevice.STATUS_RELEASE), UnactivatedDevice.class);
        }
    }
}
