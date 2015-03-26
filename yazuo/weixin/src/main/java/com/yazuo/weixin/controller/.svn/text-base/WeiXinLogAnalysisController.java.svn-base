package com.yazuo.weixin.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yazuo.weixin.service.WeixinLogAnalysisService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.timer.TaskMallLogAnalysisService;
import com.yazuo.weixin.vo.BusinessVO;

/**
* @ClassName WeiXinLogAnalysisController
* @Description 日志分析
* @author sundongfeng@yazuo.com
* @date 2014-7-2 上午11:50:40
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/analysis/")
public class WeiXinLogAnalysisController {
	private static final Log log = LogFactory.getLog(WeiXinLogAnalysisController.class);
	@Autowired
	private WeixinLogAnalysisService weixinLogAnalysisService;
	@Autowired
	private WeixinManageService weixinManageService;
	@Autowired
	private TaskMallLogAnalysisService analyService;	
	/**
	 * 生成日志分析
	 * http://ip:port/yazuo-weixin/analysis/1119/logAnalysis.do?beginDate=2014-07-03
	 * @param brandId
	 * @param model
	 * @param date 日期
	 * @return
	 */
	@RequestMapping(value="/{brandId}/logAnalysis", method = { RequestMethod.POST,RequestMethod.GET })
	public void logAnalysis(@PathVariable(value = "brandId") String brandId,HttpServletResponse response,
			@RequestParam(value = "beginDate", required = false)String beginDate,ModelMap model){
			try {
				String tomcatDir = System.getProperty("catalina.base");
				String filePath=tomcatDir+"/logs/payanalysis/payanalysis.log";
				log.info(tomcatDir);
				if(StringUtils.isNotBlank(beginDate)){
					boolean sameFlag = DateUtils.isSameDay(DateUtil.parseYYYYMMDDDate(beginDate), new Date());
					if(!sameFlag){
						filePath = tomcatDir+"/logs/payanalysis/payanalysis.log."+beginDate.trim()+".log";
					}
				}
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
				map.put("g",beginDate);
				weixinLogAnalysisService.insertOrUpdateLog(map);
				br.close();
				fin.close();
			response.getWriter().print("goRecharge="+a+" goEachPage="+b+" goEachPayPage="+c+" checkAndGet="+d+" payResult="+e+" goPaySuccessPage="+f);
			} catch (FileNotFoundException e) {
				log.error("没找到日志文件.",e);
				try {
					response.getWriter().print("没有日志文件");
				} catch (IOException e1) {
					log.error("code happen error.",e1);
				}
			} catch (UnsupportedEncodingException e) {
				log.error("code happen error.",e);
			} catch (IOException e) {
				log.error("code happen error.",e);
			} catch (Exception e1) {
				log.error("code happen error.",e1);
			}
	}
	/**
	 * 
	 * @param brandId
	 * @param beginDate
	 * @param endDate
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{brandId}/queryLog", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryLog(@PathVariable(value = "brandId") String brandId,
			@RequestParam(value = "beginDate", required = false)String beginDate,
			@RequestParam(value = "endDate", required = false)String endDate,
			ModelMap model){
		try {
			List<Map<String,Object>> list =  weixinLogAnalysisService.queryLog(beginDate, endDate);
			model.addAttribute("logList", list);
			model.addAttribute("beginDate", beginDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {
			log.error("查询日志 error.",e);
			return "wx-xldPayError.jsp";
		}
		return "wx-xldLogAnalysis";
	}
	@RequestMapping(value="mallLogAnalysis", method = { RequestMethod.POST,RequestMethod.GET })
	public void mallLogAnalysis(@RequestParam(value = "beginDate", required = false)String beginDate,HttpServletResponse response) throws Exception{
		analyService.timeExe(beginDate);
		response.getWriter().print("成功");
		response.getWriter().flush();
		response.getWriter().close();
	}
	@RequestMapping(value="queryMallLog", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryMallLog(@RequestParam(value = "brandId",required = false) String brandId,
			@RequestParam(value = "beginDate", required = false)String beginDate,
			@RequestParam(value = "endDate", required = false)String endDate,
			ModelMap model){
		try {
			if(StringUtils.isNotBlank(brandId)){
				BusinessVO business = weixinManageService.getBusinessByBrandId(Integer.parseInt(brandId));
				model.addAttribute("business", business);
				model.addAttribute("brandId", brandId);
			}
			List<Map<String,Object>> list =  weixinLogAnalysisService.queryMallLog(beginDate, endDate,brandId);
			List<Map<String,Object>> brandList =  weixinLogAnalysisService.queryMallBrand();
			model.addAttribute("logList", list);
			model.addAttribute("brandList", brandList);
			model.addAttribute("beginDate", beginDate);
			model.addAttribute("endDate", endDate);
		} catch (Exception e) {
			log.error("查询日志 error.",e);
			return "wx-xldPayError.jsp";
		}
		return "/mall/wx-mallLogAnalysis";
	}
	/**
	 * 导出excel
	 * @param response
	 */
	@RequestMapping(value="/{brandId}/exportExcel", method = { RequestMethod.POST,RequestMethod.GET })
	public void exportExcel(HttpServletResponse response,
			@RequestParam(value = "beginDate", required = false)String beginDate,
			@RequestParam(value = "endDate", required = false)String endDate){
		response.setCharacterEncoding("utf-8");
		String fileName="log.xls";
		response.reset();
		response.setContentType("application/x-msdownload");
//		response.setContentType("application/octet-stream;charset=utf8");
//		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment; filename=" + fileName + "");
		try {
			List exportData = new ArrayList();
			OutputStream os = response.getOutputStream();
			Map<Integer,Object> titleMap = new HashMap<Integer,Object>();
			titleMap.put(1, "日期");
			titleMap.put(2, "套餐列表页");
			titleMap.put(3, "火锅页");
			titleMap.put(4, "购买页");
			titleMap.put(5, "发起支付次数");
			titleMap.put(6, "腾讯通知支付次数（有误差）");
			exportData.add(titleMap);
			
			List<Map<String,Object>> list =  weixinLogAnalysisService.queryLog(beginDate, endDate);
			if(list!=null&&list.size()>0){
				for(Map<String,Object> mp:list){
					Map<Integer,Object> contentMap = new HashMap<Integer,Object>();
					contentMap.put(1, mp.get("logdate"));
					contentMap.put(2, mp.get("gorecharge"));
					contentMap.put(3, mp.get("goeachpage"));
					contentMap.put(4, mp.get("goeachpaypage"));
					contentMap.put(5, mp.get("checkandget"));
					contentMap.put(6, mp.get("payresult"));
					exportData.add(contentMap);
				}
			}
			exportExcelByList(exportData, os);
			os.close();
			response.flushBuffer();
		} catch (IOException e) {
			log.error("code happen error.",e);
		} catch (Exception e) {
			log.error("code happen error.",e);
		}
	}
	
	/**
	 * 导出，读取List，返回输出流
	 * 
	 * @param list
	 * @param cols
	 * @param response
	 */
	public static void exportExcelByList(List list, OutputStream response) {
		try {
			if (list == null || list.size() < 1) {
				return;
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			sheet.setDefaultRowHeight((short) (20*20));
			// 字体加粗
			HSSFFont font = workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFont(font);
			// 总记录数
			int index = 0;
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				HSSFRow row = sheet.createRow(i);
				index = 0;
				// 设置每行内容
				Iterator it = map.keySet().iterator();
				while (it.hasNext()) {
					HSSFCell cell = row.createCell(index);
					Object obj = map.get((Integer) it.next());
					if (obj == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(obj.toString());
					}
					// 第一行为表头，设置了字体加粗
					if (i == 0) {
						cell.setCellStyle(style);
					}
					index = index + 1;
				}
			}
			for(int j=0; j<index;j++){
				sheet.autoSizeColumn((short) j);
			}
			workbook.write(response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
