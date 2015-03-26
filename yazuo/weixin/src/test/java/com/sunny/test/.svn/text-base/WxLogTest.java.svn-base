package com.sunny.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
* @ClassName WxLogTest
* @Description 
* @author sundongfeng@yazuo.com
* @date 2014-7-2 下午1:45:33
* @version 1.0
*/
public class WxLogTest {
	private static final Log log = LogFactory.getLog(WxLogTest.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private ApplicationContext cxt;
	JdbcTemplate jdbcTemplate=null;
/*	@Before
	public void setUp(){
		cxt = new FileSystemXmlApplicationContext(new String[]{"target/classes/spring-weixin-datasource.xml","target/classes/spring-weixin-servlet.xml"});
		jdbcTemplate = (JdbcTemplate) cxt.getBean("jdbcTemplate");
	}*/
	
	@Test
	public void queryLog(){
		try {
			String tomcatDir = System.getProperty("catalina.base");
			String filePath=tomcatDir+"/logs/payanalysis/payanalysis.log";
			System.out.println(tomcatDir);
			FileInputStream fin = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fin,"utf-8"));
			String subStr ="";
			int a = 0;
			int b = 0;
			int c = 0;
			int d = 0;
			int e = 0;
			int f = 0;
			
			while((subStr=br.readLine())!=null){
				String[] sub=subStr.split(";");
				if("goRecharge".equals(sub[2])){
					a++;
				}else if("goEachPage".equals(sub[2])){
					b++;
				}else if("goEachPayPage".equals(sub[2])){
					c++;
				}else if("checkAndGet".equals(sub[2])){
					d++;
				}else if("payResult".equals(sub[2])){
					e++;
				}else if("goPaySuccessPage".equals(sub[2])){
					f++;
				}
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("a",a);
			map.put("b",b);
			map.put("c",c);
			map.put("d",d);
			map.put("e",e);
			map.put("f",f);
			map.put("g","20140702001122");
			insertLog(map);
			br.close();
			fin.close();
		} catch (FileNotFoundException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		} catch (Exception e1) {
			log.error("code happen error.",e1);
			e1.printStackTrace();
		}
	}
	
	public int insertLog(Map<String,?> map) throws Exception{
		String sql =   "insert into weixin_log_analysis ( gorecharge, goeachpage,  "+
				   "  goeachpaypage, checkandget, payresult, gopaysuccesspage, logdate) "+
				   "values (?,?, ?,?, ?, ?, ?) ";
		return jdbcTemplate.update(sql, map.get("a"),map.get("b"),map.get("c"),map.get("d"),map.get("e"),map.get("f"),sdf.parse((String)map.get("g")));
		
	}
	
	@Test
	public void modify() throws Exception{
//		String json = "{\"id\":\"" + 1
//				+ "\",\"birthday\":\"" + 20140708 + "\",\"birthType\":\""+2+"\"}";
//		System.out.println(json);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date  date = sdf.parse("20150228120156");
		System.out.println();
	}
	
	@Test
	public void testUrl() throws Exception{
		String url = "http://image.yazuo.com/group1/M00/01/71/wKimq1TxHhWAB9dNAAALPIsgIps285.p12";
        URL	httpurl= new URL(url);
	    String projectPath = this.getClass().getResource("/").getPath();
	    String endend = url.substring(url.lastIndexOf("."), url.length());
	    File fw = new File(projectPath+"tmp/ca_"+8826+endend);   
	    InputStream instream= null;
	    if(!fw.exists()){
	    	try{
	    		FileUtils.copyURLToFile(httpurl, fw);
	    		instream = new FileInputStream(fw);
	    	}catch(Exception ex){
	    		instream = httpurl.openStream();
	    		log.error("copy file error.",ex);
	    	}
	    }else{
	    	instream = new FileInputStream(fw);
	    }
	    System.out.println(projectPath);
	    System.out.println(projectPath+"tmp/ca_"+8826+endend);
	}
}
