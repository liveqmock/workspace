/*
 * 文件名：ZhimaAPITest.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2015年1月21日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package network;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yazuo.superwifi.util.HttpUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-superwifi-*.xml"})
public class ZhimaAPITest
{
    
    private static final String APPKEY = "ea1ff591ff578ec8749286bb7f494ab8";

    private static final String SECRETKEY = "c3f574a45e8fj816jfwefd337aee26b1";

    private static final String SYNCHRODATABRAND = "http://open.zhimatech.com/api/v2/yazuo/synchrodataBrand";

    private static final String MACRELEASE = "http://open.zhimatech.com/api/v2/yazuo/accept";

    private static final String CONFIG = "http://open.zhimatech.com/api/v2/yazuo/config";

    private static final String QUERY = "http://open.zhimatech.com/api/v2/yazuo/query";
    private static final Logger log = Logger.getLogger(test.class);
    private Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
    @Test
    public void queryTest(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());
        String token = md5PasswordEncoder.encodePassword(APPKEY + day + time + SECRETKEY, null);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", APPKEY ));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("day", day));
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("deviceMac", "048d38584ea0"));
        JSONObject result=null;
        try
        {
            result = HttpUtil.httpPostForJson(nvps, QUERY);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info(result.toString()); ;
    }
    
    @Test
    public void configTest(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());
        String token = md5PasswordEncoder.encodePassword(APPKEY + day + time + SECRETKEY, null);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", APPKEY ));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("day", day));
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("deviceMac", "048d38584ea0"));
        JSONObject result=null;
        try
        {
            result = HttpUtil.httpPostForJson(nvps, CONFIG);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info(result.toString()); ;
    }
    
    @Test
    public void synchrodatabrandTest(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());
        String token = md5PasswordEncoder.encodePassword(APPKEY + day + time + SECRETKEY, null);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", APPKEY ));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("day", day));
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("deviceMac", "048d38584ea0"));
        JSONObject result=null;
        try
        {
            result = HttpUtil.httpPostForJson(nvps, SYNCHRODATABRAND);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info(result.toString()); ;
    }
    
    @Test
    public void macReleaseTest(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());
        String token = md5PasswordEncoder.encodePassword(APPKEY + day + time + SECRETKEY, null);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", APPKEY ));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("day", day));
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("deviceMac", "048d38584ea0"));
        JSONObject result=null;
        try
        {
            result = HttpUtil.httpPostForJson(nvps, MACRELEASE);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info(result.toString()); ;
    }
}
