package com.yazuo.weixin.timer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yazuo.weixin.dao.WeixinLogDao;
import com.yazuo.weixin.util.DateUtil;


/**
* @ClassName TaskLogAnalysisService
* @Description 定时执行日志分析任务
* @author sundongfeng@yazuo.com
* @date 2014-7-3 下午1:51:40
* @version 1.0
*/
@Component("TaskLogAnalysisService")
public class TaskLogAnalysisService {
	private static final Log log = LogFactory.getLog("wxpay");
	@Autowired
	private WeixinLogDao WeixinLogDao;
	/**
	 * 定时任务
	 */
	//@Scheduled(cron="0 0 5 * * ?")
	public void logAnalysis()
	{
		log.info("执行日志分析定时任务开始");
		
		try {
			String tomcatDir = System.getProperty("catalina.base");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(cal.DATE, -1);
			String date = sdf.format(cal.getTime());
			log.info("date:"+date);
			String filePath=tomcatDir+"/logs/payanalysis/payanalysis.log."+date+".log";
			log.info(tomcatDir);
			
			log.info(filePath);
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
			log.info("goRecharge="+a+" goEachPage="+b+" goEachPayPage="+c+" checkAndGet="+d+" payResult="+e+" goPaySuccessPage="+f);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("a",a);
			map.put("b",b);
			map.put("c",c);
			map.put("d",d);
			map.put("e",e);
			map.put("f",f);
			map.put("g",date);
			WeixinLogDao.insertOrUpdateLog(map);
			br.close();
			fin.close();
		} catch (FileNotFoundException e) {
			log.error("没找到日志文件.",e);
		} catch (UnsupportedEncodingException e) {
			log.error("code happen error.",e);
		} catch (IOException e) {
			log.error("code happen error.",e);
		} catch (Exception e1) {
			log.error("code happen error.",e1);
		}
		log.info("执行日志分析定时任务结束");
	} 
}
