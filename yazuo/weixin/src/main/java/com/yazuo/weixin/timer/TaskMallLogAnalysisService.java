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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yazuo.weixin.dao.WeixinLogDao;

/**
* @ClassName TaskMallLogAnalysisService
* @Description  微信商城定时执行日志分析任务
* @author sundongfeng@yazuo.com
* @date 2014-11-3 上午11:36:00
* @version 1.0
*/
@Component("TaskMallLogAnalysisService")
public class TaskMallLogAnalysisService {
	private static final Log log = LogFactory.getLog("wxpay");
	@Autowired
	private WeixinLogDao WeixinLogDao;
	
	public void timeExe(String date){
		log.info("执行日志分析定时任务开始");
		
		try {
			String tomcatDir = System.getProperty("catalina.base");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(cal.DATE, -1);
			if(StringUtils.isEmpty(date)){
			  date = sdf.format(cal.getTime());
			}
			log.info("date:"+date);
			String filePath=tomcatDir+"/logs/martlog/martlog.log."+date+".log";
			log.info(tomcatDir);
			
			log.info(filePath);
			FileInputStream fin = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fin,"utf-8"));
			String subStr ="";
			
			Map<String,Map<String,?>> allMap = new HashMap<String,Map<String,?>>();
			while((subStr=br.readLine())!=null){
				String[] sub=subStr.split(";");
				if(allMap.containsKey(sub[0])){
					Map<String,Object> map=(Map<String, Object>) allMap.get(sub[0]);
					getMap(map,sub,date);
				}else{
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("a",0);
					map.put("b",0);
					map.put("c",0);
					map.put("d",0);
					map.put("e",0);
					map.put("f",0);
					map.put("g",0);
					map.put("h",0);
					getMap(map,sub,date);
					allMap.put(sub[0], map);
				}
			}
			if(allMap.size()>0){
				for(Map<String,?>obj:allMap.values()){
					WeixinLogDao.insertOrUpdateMallLog(obj);
				}
			}
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
	
	/**
	 * 定时任务
	 */
//	@Scheduled(cron="0 0 5 * * ?")
	public void logAnalysis()
	{
		timeExe("");
	} 
	
	public void getMap(Map<String,Object> map,String[] sub,String date){
		int a = (Integer) map.get("a");
		int b =(Integer) map.get("b");
		int c = (Integer) map.get("c");
		int d = (Integer) map.get("d");
		int e = (Integer) map.get("e");
		int f = (Integer) map.get("f");
		int g =(Integer) map.get("g");
		int h = (Integer) map.get("h");
		if("mallMartIndex".equals(sub[2])){
			a++;
		}else if("goodInfoPage".equals(sub[2])){
			b++;
		}else if("buyGood".equals(sub[2])){
			c++;
		}else if("integralPay".equals(sub[2])){
			d++;
		}else if("jiFenXiaoFei".equals(sub[2])){
			e++;
		}else if("obtainGoodInfo".equals(sub[2])){
			f++;
		}else if("mallPayResult".equals(sub[2])){
			g++;
		}else if("goMallSuccessPage".equals(sub[2])){
			h++;
		}
		map.put("a",a);
		map.put("b",b);
		map.put("c",c);
		map.put("d",d);
		map.put("e",e);
		map.put("f",f);
		map.put("g",g);
		map.put("h",h);
		map.put("j",Integer.parseInt(sub[0]));
		map.put("m",date);
	}
	
}
