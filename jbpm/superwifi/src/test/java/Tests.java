import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import network.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.WriteResult;
import com.yazuo.superwifi.device.vo.DeviceInfo;
import com.yazuo.superwifi.device.vo.DeviceSSID;
import com.yazuo.superwifi.login.vo.Identifyinginfo;
import com.yazuo.superwifi.member.vo.Member;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.networkmanagement.vo.AccessRestrictions;
import com.yazuo.superwifi.networkmanagement.vo.AppInfo;
 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-superwifi-*.xml"})
public class Tests {
	@Resource(name="mongoTemplate")
    private MongoTemplate mongoTemplate; 
	@Resource(name = "merchantServiceImpl")
    private MerchantService merchantService;
	private static final Logger log = Logger.getLogger(test.class);
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	@Test
	public void test1(){
//		Identifyinginfo  idinfo = new Identifyinginfo();
//		idinfo.setId("2");
//		idinfo.setIdentifyingCode("456");
//		idinfo.setMobileNumber("12345231");
//		mongoTemplate.insert(idinfo, "testCollection");
	    log.info(merchantService.getMerchantTypeByMerchantId(20));
	}
	@Test 
	public void test2(){
		Identifyinginfo dd= mongoTemplate.findOne(new Query(Criteria.where("identifyingCode").is("123")), Identifyinginfo.class, "testCollection");
		log.info(dd.getId());
		//修改mobileNumber并添加status字段，删除brandId字段
		//mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(dd.getId())),Update.update("mobileNumber", "13671212123").set("status", 1).unset("brandId"),"testCollection");
		WriteResult wr = mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(dd.getId())),Update.update("status", 0).set("brandId", 1).set("passIdentifyingTime", new Date()),"testCollection");
		log.info(wr.toString());
	}
	@Test 
	public void test3(){
		Identifyinginfo dd= mongoTemplate.findOne(new Query(Criteria.where("identifyingCode").is("123")), Identifyinginfo.class, "testCollection");
		log.info(dd.getIdentifyingCode());
	}
	
	@Test 
	public void test4(){
//		DeviceInfo  idinfo = new DeviceInfo();
//		idinfo.setBrandId(15);
//		idinfo.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo.setMac("ad-23-54-fs-33-ff");
//		idinfo.setMerchantId(444);
//		idinfo.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo, "deviceInfo");
//		
//		DeviceInfo  idinfo1 = new DeviceInfo();
//		idinfo1.setBrandId(15);
//		idinfo1.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo1.setMac("ga-41-15-8f-ed-tt");
//		idinfo1.setMerchantId(2251);
//		idinfo1.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo1, "deviceInfo");
//		
//		DeviceInfo  idinfo2 = new DeviceInfo();
//		idinfo2.setBrandId(15);
//		idinfo2.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo2.setMac("af-41-14-8f-ed-dd");
//		idinfo2.setMerchantId(2251);
//		idinfo2.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo2, "deviceInfo");
//		
//		DeviceInfo  idinfo3 = new DeviceInfo();
//		idinfo3.setBrandId(15);
//		idinfo3.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo3.setMac("ac-81-12-8f-ed-dd");
//		idinfo3.setMerchantId(2170);
//		idinfo3.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo3, "deviceInfo");
//		
//		DeviceInfo  idinfo4 = new DeviceInfo();
//		idinfo4.setId(null);
//		idinfo4.setBrandId(15);
//		idinfo4.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo4.setMac("hs-78-tj-u7-87-fd");
//		idinfo4.setMerchantId(2170);
//		idinfo4.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo4, "deviceInfo");
//		
//		DeviceInfo  idinfo5 = new DeviceInfo();
//		idinfo5.setId(null);
//		idinfo5.setBrandId(15);
//		idinfo5.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo5.setMac("ys-78-yu-56-mb-jf");
//		idinfo5.setMerchantId(20);
//		idinfo5.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo5, "deviceInfo");
//		
//		DeviceInfo  idinfo6 = new DeviceInfo();
//		idinfo6.setId(null);
//		idinfo6.setBrandId(15);
//		idinfo6.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo6.setMac("rt-kg-56-wr-tr-45");
//		idinfo6.setMerchantId(20);
//		idinfo6.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo6, "deviceInfo");
//		
//		DeviceInfo  idinfo7 = new DeviceInfo();
//		idinfo7.setId(null);
//		idinfo7.setBrandId(15);
//		idinfo7.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo7.setMac("44-hd-gs-34-s7-sd");
//		idinfo7.setMerchantId(21);
//		idinfo7.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo7, "deviceInfo");
		
//		DeviceInfo  idinfo8 = new DeviceInfo();
//		idinfo8.setId(null);
//		idinfo8.setBrandId(15);
//		idinfo8.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo8.setMac("34-gs-sj-km-yr-ku");
//		idinfo8.setMerchantId(22);
//		idinfo8.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo8, "deviceInfo");
//		
//		DeviceInfo  idinfo9 = new DeviceInfo();
//		idinfo9.setId(null);
//		idinfo9.setBrandId(15);
//		idinfo9.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo9.setMac("46-fs-hs-4f-4a-gs");
//		idinfo9.setMerchantId(22);
//		idinfo9.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo9, "deviceInfo");
//		
//		DeviceInfo  idinfo10 = new DeviceInfo();
//		idinfo10.setId(null);
//		idinfo10.setBrandId(15);
//		idinfo10.setInsertTime(new Timestamp(System.currentTimeMillis()));
//		idinfo10.setMac("em-yd-34-jf-96-jd");
//		idinfo10.setMerchantId(23);
//		idinfo10.setStatus(DeviceInfo.STATUS_NORMAL);
//		mongoTemplate.insert(idinfo10, "deviceInfo");
//		
		DeviceInfo  idinfo11 = new DeviceInfo();
		idinfo11.setId(null);
		idinfo11.setBrandId(15);
		idinfo11.setInsertTime(new Timestamp(System.currentTimeMillis()));
		idinfo11.setMac("54-45-65-jf-7g-kf");
		idinfo11.setMerchantId(2251);
		idinfo11.setStatus(DeviceInfo.STATUS_NORMAL);
		
		List<DeviceSSID> dss = new ArrayList<DeviceSSID>();
		DeviceSSID ds = new DeviceSSID();
	//	ds.setDeviceName("liaoliaoliao");
		ds.setIsMain(true);
		mongoTemplate.insert(ds);
		dss.add(ds);
		idinfo11.setDevSSID(dss);
		mongoTemplate.insert(idinfo11, "deviceInfo");
		
//		List<DeviceSSID> dss = new ArrayList<DeviceSSID>();
//		DeviceSSID ds = new DeviceSSID();
//		ds.setSsid("liaoliaoliao");
//		ds.setIsMain(true);
//		mongoTemplate.insert(ds);
	}
	
	@Test
	public void Test5(){
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();  
        int pageSize = 45;  
        long count = mongoTemplate.count(new Query(Criteria.where("status").is(1).and("brandId").is(15)), DeviceInfo.class);
        long pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;  
        Integer pc = (int) (count<pageSize?count:pageSize+1);
        List<DeviceInfo> videoEncodeList = mongoTemplate.find(new Query(Criteria.where("status").is(1).and("brandId").is(15)), DeviceInfo.class).subList(0, pc);  
           log.info(pc+"  "+videoEncodeList.size());
	}
	
	@Test
	public void Test6(){
		MerchantInfo mer = new MerchantInfo();
		mer.setBrandId(15);
		mer.setMerchantId(2251);
		mer.setConnectType(3);
		mer.setIsFaceShop(true);
		mer.setStatus(1);
		mongoTemplate.insert(mer);
	}
	
	@Test
	public void Test7(){
//		Member mem = new Member();
//		mem.setBrandId(15);
//		mem.setMerchantId(2251);
//		mem.setPoneNumber("123612111111");
//		mem.setMac("sd-as-32-dg-ew-ww");
//		mem.setInsertTime(new Date());
//		mem.setStatus(1);
//		mongoTemplate.insert(mem);
		
//		Member mem1 = new Member();
//		mem1.setBrandId(15);
//		mem1.setMerchantId(2251);
//		mem1.setPoneNumber("123612222222");
//		mem1.setMac("fs-wu-lg-hd-74-08");
//		mem1.setInsertTime(new Date());
//		mem1.setStatus(1);
//		mongoTemplate.insert(mem1);
		
		Member mem2 = new Member();
		mem2.setBrandId(15);
		mem2.setMerchantId(850);
		mem2.setPhoneNumber("1236133333");
		mem2.setMac("et-34-3g-74-ey-7s");
		mem2.setInsertTime(new Date());
		mem2.setStatus(1);
		mongoTemplate.insert(mem2);
		
		Member mem3 = new Member();
		mem3.setBrandId(15);
		mem3.setMerchantId(850);
		mem3.setPhoneNumber("12361444444");
		mem3.setMac("4s-7e-5h-54-jd-3g");
		mem3.setInsertTime(new Date());
		mem3.setStatus(1);
		mongoTemplate.insert(mem3);
		
		Member mem4 = new Member();
		mem4.setBrandId(15);
		mem4.setMerchantId(850);
		mem4.setPhoneNumber("12361666666");
		mem4.setMac("4s-7e-5h-54-jd-3g");
		mem4.setInsertTime(new Date());
		mem4.setStatus(1);
		mongoTemplate.insert(mem4);
		
		Member mem5 = new Member();
		mem5.setBrandId(15);
		mem5.setMerchantId(850);
		mem5.setPhoneNumber("12361555555");
		mem5.setMac("er-57-jk-zd-0t-45");
		mem5.setInsertTime(new Date());
		mem5.setStatus(1);
		mongoTemplate.insert(mem5);
	}
	
	@Test
	public void Test8(){
		GroupByResults<AccessRestrictions> BW = mongoTemplate.group(Criteria.where("type").is(1).and("status").is(AccessRestrictions.STATUS_NORMAL), "accessRestrictions", GroupBy.key("url")
                .initialDocument("{count:0,sum:0}").reduceFunction("function(doc,prev){prev.count++;prev.sum+=doc._id}").finalizeFunction("function(prev){prev.sum=prev.sum/prev.count}"), AccessRestrictions.class);
		//List<AccessRestrictions> as = mongoTemplate.find(new Query(Criteria.where("type").is(1).and("status").is(AccessRestrictions.STATUS_NORMAL)), AccessRestrictions.class);
		BasicDBList list = (BasicDBList)BW.getRawResults().get("retval");
		 for (int i = 0; i < list.size(); i ++) {  
	            BasicDBObject obj = (BasicDBObject)list.get(i);  
	            log.info(obj.get("count")+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");  
	        }  
	}
	 @Test  
	    public void distinct() {  
	        CommandResult result = mongoTemplate.executeCommand("{distinct:'accessRestrictions', key:'merchantId'}");  
	        BasicDBList list = (BasicDBList)result.get("values");  
	        for (int i = 0; i < list.size(); i ++) {  
	            log.info(list.get(i));  
	        }  
	    }  
	@Test
	public void Test9(){
		AppInfo app = new AppInfo();
		app.setName("大众点评");
		List<String> urls = new ArrayList<String>();
		urls.add("http://t.dianping.com");
		urls.add("http://12344.com");
		app.setUrlList(urls);
		app.setInsertTime(new Date());
		app.setStatus(1);
		mongoTemplate.insert(app);
		
		AppInfo app1 = new AppInfo();
		app1.setName("美团");
		List<String> urls1 = new ArrayList<String>();
		urls1.add("http://t.dianping.com");
		urls1.add("http://12344.com");
		app1.setUrlList(urls1);
		app1.setInsertTime(new Date());
		app1.setStatus(1);
		mongoTemplate.insert(app1);
		
//		AccessRestrictions ar = new AccessRestrictions();
//		ar.setBrandId(15);
//		ar.setMerchantId(2251);
//		ar.setUrl("www.baidu.com");
//		ar.setInsertTime(new Date());
//		ar.setStatus(AccessRestrictions.STATUS_NORMAL);
//		ar.setType(AccessRestrictions.TYPE_WEB);
//		mongoTemplate.insert(ar);
	}
	
	@Test
	public void inTest(){
		List<String> as = new ArrayList<String>();
		as.add("54-45-65-jf-7g-kf");
		as.add("ga-41-15-8f-ed-tt");
		List<DeviceInfo> list = mongoTemplate.find(new Query(Criteria.where("mac").in(as)),  DeviceInfo.class);
		for(int i =0;i<list.size();i++){
			log.info(list.get(i).getId());
		}
	}
}
