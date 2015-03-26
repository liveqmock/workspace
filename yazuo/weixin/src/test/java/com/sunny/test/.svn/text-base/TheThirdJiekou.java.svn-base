package com.sunny.test;

import java.net.URLDecoder;

import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yazuo.weixin.util.CommonUtil;

/**
* @ClassName TheThirdJiekou
* @Description  第三方接口
* @author sundongfeng@yazuo.com
* @date 2014-10-9 上午10:17:15
* @version 1.0
*/
public class TheThirdJiekou {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-weixin-datasource.xml");
        MemcachedClient memcachedClient = context.getBean(MemcachedClient.class);
        memcachedClient.set("hello", 0, "Hello,你好");
        String value = memcachedClient.get("hello");
        System.out.println("hello=" + value);
//        memcachedClient.delete("hello");
//        value = memcachedClient.get("hello");
//        System.out.println("hello=" + value);
        System.out.println(memcachedClient.get("15_1"));
        System.out.println(memcachedClient.get("15_2"));
        System.out.println(memcachedClient.get("15_3"));
      System.out.println(memcachedClient.get("15"));
        //close memcached client
        memcachedClient.shutdown();
    }
}
