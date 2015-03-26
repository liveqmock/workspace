/*
 * 文件名：ImportData.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2015年1月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package network;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baidu.ueditor.ConfigManager;
import com.yazuo.superwifi.device.service.DeviceService;
import com.yazuo.superwifi.device.vo.DeviceInfo;
import com.yazuo.superwifi.exception.ExceptionResolver;
import com.yazuo.superwifi.member.vo.Member;
import com.yazuo.superwifi.merchant.controller.Ssid;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.util.HttpUtil;
import com.yazuo.superwifi.util.PropertyUtil;
import com.yazuo.superwifi.util.UploadFileUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-superwifi-*.xml"})
public class ImportData
{
    private Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    @Resource(name = "deviceServiceImpl")
    private DeviceService deviceService;
    
    @Resource(name = "merchantServiceImpl")
    private MerchantService merchantService;

    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;
    
    @Resource(name="supwerwifijdbcTemplate")
    private JdbcTemplate supwerwifijdbcTemplate;
    
    
    private String picDfsServer=null;
    private Integer picDfsPort=null;
    private String dfsTrackerHttpPort=null;
    
    private static final Logger log = Logger.getLogger(ExceptionResolver.class);

    public ImportData ( ) {
        
        try
        {
            this.picDfsServer=PropertyUtil.getInstance().getProperty("fdfs_yazuo.conf","dfs.server.ip");
            this.picDfsPort=Integer.valueOf(PropertyUtil.getInstance().getProperty("fdfs_yazuo.conf","dfs.server.port"));
            this.dfsTrackerHttpPort=PropertyUtil.getInstance().getProperty("fdfs_yazuo.conf","dfs.tracker.http.port");
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            log.error(e);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            log.error(e);
        }
        
    }
    @Test
    public void importMember(){
        List<Map<String,Object>> memberList=getMembers();
        Member member=null;
        for(Map<String,Object> map:memberList){
            member=new Member();
            member.setBrandId((Integer)map.get("brand_id"));
            member.setInsertTime((Timestamp)map.get("insert_time"));
            member.setMac((String)map.get("device_mac"));
            member.setMerchantId((Integer)map.get("merchant_id"));
            member.setPhoneNumber((String)map.get("mobile_number"));
            member.setStatus(1);
            member.setLastUpdateTime((Timestamp)map.get("insert_time"));
            mongoTemplate.insert(member);
        }
    }
    private List<Map<String,Object>> getMembers(){
        String sql="select * from superwifi.member";
        List<Map<String,Object>> memberList=supwerwifijdbcTemplate.queryForList(sql);
        return memberList;
    }
    
    @Test
    public void importMerchant() throws Exception{
        List<Map<String,Object>> merchantList=getMerchant();
        MerchantInfo merchantInfo=null;
        for(Map<String,Object> map:merchantList){
            Integer merchantId=(Integer)map.get("merchant_id");
            JSONObject merchant=merchantService.getMerchantByMerchantId(merchantId);
            String merchantName=merchant.getString("merchantName");
            Integer brandId=merchant.getInt("brandId");
            
            DeviceInfo di=new DeviceInfo();
            di.setBrandId(brandId);
            di.setInsertTime((Timestamp)map.get("insert_time"));
            di.setLastUpdateTime((Timestamp)map.get("insert_time"));
            di.setMac(((String)map.get("device_mac")).replace(":", ""));
            di.setMerchantId(merchantId);
            di.setStatus(DeviceInfo.STATUS_NORMAL);
            mongoTemplate.insert(di);
            
            if(mongoTemplate.findOne(new Query(Criteria.where("merchantId").is(merchantId)), MerchantInfo.class)!=null){continue;}
            
            merchantInfo=new MerchantInfo();
            merchantInfo.setMerchantId(merchantId);
            merchantInfo.setMerchantName(merchantName);
            merchantInfo.setIsFaceShop(true);
            merchantInfo.setStatus(MerchantInfo.STATUS_NORMAL);
            merchantInfo.setBrandId(brandId);
            merchantInfo.setInsertTime((Timestamp)map.get("insert_time"));
            merchantInfo.setLastUpdateTime(new Date());
            merchantInfo.setSource(MerchantInfo.SOURCE_CRM);
            String portalPic="http://static.yazuo.com/crm/superwifi/merchant-superwifi-"+merchantInfo.getBrandId()+".jpg";
            InputStream is=httpDownload(portalPic);
            byte[] b=input2byte(is);
            String id=UploadFileUtil.upLoadFiles(b, ".jpg", picDfsServer, dfsTrackerHttpPort, picDfsPort);
            merchantInfo.setPortalPic(id);
            mongoTemplate.insert(merchantInfo);
        }
    }
    private List<Map<String,Object>> getMerchant(){
        String sql="SELECT  aa.device_mac,aa.insert_time,   aa.zhima_id,    bb.brand_id,bb.merchant_id FROM superwifi.device_info AS aa LEFT JOIN (SELECT count(*),merchant_id,brand_id,zhima_id from superwifi.merchant_zhima where status=1 and brand_id<>15 and brand_id<>11302 and zhima_id<>'0' GROUP BY merchant_id,brand_id,zhima_id  ORDER BY zhima_id) AS bb ON aa.zhima_id = bb.zhima_id WHERE   aa.status = 1 and aa.zhima_id<>'15' and bb.zhima_id<>'32011476'ORDER BY bb.merchant_id desc";
        List<Map<String,Object>> merchantList=supwerwifijdbcTemplate.queryForList(sql);
        return merchantList;
    }
    
    
    /**http下载*/
    public InputStream httpDownload(String httpUrl){

        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            return inStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static final byte[] input2byte(InputStream inStream)
        throws IOException
    {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0)
        {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
    @Test
    public void test9(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());
        String token = md5PasswordEncoder.encodePassword("ea1ff591ff578ec8749286bb7f494ab8" + day + time + "c3f574a45e8fj816jfwefd337aee26b1", null);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", "ea1ff591ff578ec8749286bb7f494ab8" ));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("day", day));
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("deviceMac", "048d38584a28"));
        JSONObject result=null;
        try
        {
            result = HttpUtil.httpPostForJson(nvps, "http://open.zhimatech.com/api/v2/yazuo/query");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info(result.toString()); ;
    }
    
}
