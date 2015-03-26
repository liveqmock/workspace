/**
 * @Description 运营报表接口实现类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;

import com.yazuo.erp.fes.controller.FesReportController;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.util.HttpClientUtil;

/**
 * @Description 工作计划接口实现类
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.erp.fes.service.FesReportService;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.util.DateUtil;

@Service
public class FesReportServiceImpl implements FesReportService {

	private static final Log log = LogFactory.getLog(FesReportController.class);

	@Resource
	private SynMerchantDao synMerchantDao;

	/**
	 * CRM查询商户报表信息
	 */
	@Value("${crm.erpstatUrl}")
	private String erpstatUrl;

	/**
	 * @Title exportReport
	 * @Description 导出某月的运营报表，并下载
	 * @param paramMap
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see com.yazuo.erp.fes.service.FesReportService#exportReport(java.util.Map,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean exportReport(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 校验入参，并取得查询月份
		Date d = this.getDate(paramMap);
		String yearMonth = DateUtil.format(d, "yyyy-MM");
		String yearMonthName = DateUtil.format(d, "yyyy年MM月");
		String sheetName = yearMonthName + "运营报告";

		// 查询CRM商户统计数据
		Map<String, Object> crmMap = queryStatisticsOfCrm(yearMonth);

		// 查询已上线商户基本信息
		List<Map<String, Object>> synMerchantList = synMerchantDao.getSynMerchantReport();

		// 整理报表数据
		this.organizingData(crmMap, synMerchantList);

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);

		// 生成Excel标题行
		this.generateFirstRow(workbook, sheet);

		// 生成Excel内容行
		this.generateContentRows(synMerchantList, workbook, sheet);

		// 导出excel文档
		this.exportExcel(request, response, sheetName, workbook);
		return true;
	}

	/**
	 * @Description 校验入参，并取得查询月份
	 * @param paramMap
	 * @return
	 * @return Date
	 * @throws
	 */
	private Date getDate(Map<String, Object> paramMap) {
		Object timeObj = paramMap.get("date");
		if (null == timeObj) {
			throw new FesBizException("请输入查询月份");
		}
		Date d = DateUtil.toDateFromMillisecond(timeObj);
		return d;
	}

	/**
	 * @Description 查询CRM商户统计数据
	 * @param yearMonth
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	private Map<String, Object> queryStatisticsOfCrm(String yearMonth) {
		// 调用CRM接口，查询报表数据
		Map<String, Object> crmMap = new HashMap<String, Object>();
		try {
			// 调用CRM查询商户报表信息接口，查询数据
			String url = erpstatUrl + "/m/" + yearMonth;
			String result = HttpClientUtil.execRequestAndGetResponse(url);

			// 校验返回数据是否为空
			JSONObject resultObj = JSONObject.fromObject(result);
			if (null == resultObj) {
				throw new FesBizException("[CRM查询商户报表信息]服务无返回");
			}
			log.info("resultJSON:" + resultObj.toString());

			// 校验服务是否调用成功
			String flag = resultObj.getString("flag");
			if ("0".equals(flag)) {
				String message = resultObj.getString("message");
				throw new FesBizException("[CRM查询商户报表信息]服务调用失败，" + message);
			}

			// 校验数据集
			crmMap = resultObj.getJSONObject("data");
			int size = crmMap.size();
			if (0 == size) {
				throw new FesBizException("[CRM查询商户报表信息]服务调用成功，列表条数0");
			}
		} catch (Exception e) {
			log.error("[CRM查询商户报表信息]服务调用失败");
			log.error(e.getMessage());
			e.printStackTrace();
		}

		if (null == crmMap || 0 == crmMap.size()) {
			throw new FesBizException("CRM数据异常");
		}
		return crmMap;
	}

	/**
	 * @Description 整理报表数据
	 * @param crmMap
	 * @param synMerchantList
	 * @return void
	 * @throws
	 */
	private void organizingData(Map<String, Object> crmMap, List<Map<String, Object>> synMerchantList) {
		for (Map<String, Object> map : synMerchantList) {
			Integer merchantId = (Integer) map.get("merchant_id");
			String serviceStartTime = DateUtil.format((java.util.Date) map.get("service_start_time"), "yyyyMMdd");
			String now = DateUtil.format(new Date(), "yyyyMMdd");

			// 计算从上线时间（即服务开始时间）到现在差距总共几年，总共几月，总共几日
			int ret[] = this.getDateLength(serviceStartTime, now);
			int yearSize = ret[0];// 总年数
			int monthSize = ret[1];// 总月数
			StringBuffer sb = new StringBuffer();// 服务时间，y年m月
			int y = 0;// 服务年
			int m = 0;// 服务月
			if (0 > yearSize || 0 > monthSize) {
				log.error("商户" + merchantId + "的服务开始时间异常[" + serviceStartTime + "]");
				y = 0;
				m = 0;
			}
			if (0 > yearSize) {
				y = 0;
			} else if (0 == yearSize) {
				y = 0;
				m = monthSize;
			} else if (0 < yearSize) {
				y = yearSize;
				m = monthSize - y * 12;
			}
			sb.append(y).append("年").append(m).append("月");

			JSONObject obj = (JSONObject) crmMap.get(String.valueOf(merchantId));
			if (null == obj) {
				map.put("flag", "false");// 标记，excel中不展示
				log.error("[CRM查询商户报表信息]未查询到商户" + merchantId + "的统计数据");
			} else {
				map.put("flag", "true");// 标记，excel中展示
				map.put("service_time", sb.toString());
				map.put("province", obj.getString("province"));// 省
				map.put("city", obj.getString("city"));// 市
				map.put("desk_num", obj.getInt("desk_num"));// 开台数
				map.put("membership_num", obj.getInt("membership_num"));// 会员总量
				map.put("store_balance", obj.getString("store_balance"));// 储值总额
				map.put("old_trans_quantity", obj.getInt("old_trans_quantity"));// 老会员交易笔数
				map.put("month_store_pay", obj.getString("month_store_pay"));// 当月储值
				map.put("integral_member", obj.getInt("integral_member"));// 新增积分会员
				map.put("store_member", obj.getInt("store_member"));// 新增储值会员
				map.put("member_consume_radio", obj.getString("member_consume_radio"));// 会员消费占比
				map.put("integral_member_radio", obj.getString("integral_member_radio"));// 新增积分会员占比
				map.put("store_member_radio", obj.getString("store_member_radio"));// 新增储值会员占比
				map.put("new_member_radio", obj.getString("new_member_radio"));// 新增会员占比
			}
		}
	}

	/**
	 * @Description 生成Excel标题行
	 * @param workbook
	 * @param sheet
	 * @return void
	 * @throws
	 */
	private void generateFirstRow(HSSFWorkbook workbook, HSSFSheet sheet) {
		// 首行字体
		HSSFFont fontOfFirstRow = workbook.createFont();
		fontOfFirstRow.setColor(HSSFColor.WHITE.index);
		fontOfFirstRow.setFontHeightInPoints((short) 10);
		fontOfFirstRow.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// 设置首行样式1-金色
		HSSFCellStyle firstRowStyle1 = workbook.createCellStyle();
		firstRowStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置填充样式
		firstRowStyle1.setFillForegroundColor(HSSFColor.GOLD.index);// 设置填充色-金色
		firstRowStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		firstRowStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		firstRowStyle1.setFont(fontOfFirstRow);

		// 设置首行样式1-蓝灰色
		HSSFCellStyle firstRowStyle2 = workbook.createCellStyle();
		firstRowStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置填充样式
		firstRowStyle2.setFillForegroundColor(HSSFColor.BLUE_GREY.index);// 设置填充色-蓝灰色
		firstRowStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		firstRowStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		firstRowStyle2.setFont(fontOfFirstRow);

		String[] headers = { "客户", "管理公司", "品牌ID", "前端", "组别", "地点", "上线时间", "年限", "开台数", "会员总量", "储值总额", "老会员交易笔数", "会员消费占比",
				"当月储值", "新增积分会员", "新增积分会员占比", "新增储值会员", "新增储值会员占比", "新增会员占比" };

		// 生成Excel标题行
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) (15.625 * 30));// 25为行高的像素数
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			if (i < 7) {
				cell.setCellStyle(firstRowStyle1);
			} else {
				cell.setCellStyle(firstRowStyle2);
			}
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
			sheet.setColumnWidth(i, headers[i].getBytes().length * 256);
		}
	}

	/**
	 * @Description 生成Excel内容行
	 * @param synMerchantList
	 * @param workbook
	 * @param sheet
	 * @return void
	 * @throws
	 */
	private void generateContentRows(List<Map<String, Object>> synMerchantList, HSSFWorkbook workbook, HSSFSheet sheet) {
		// 设置内容字体
		HSSFFont dataFont = workbook.createFont();
		dataFont.setColor(HSSFColor.BLACK.index);
		dataFont.setFontHeightInPoints((short) 10);

		// 设置内容样式-居左
		HSSFCellStyle dataStyle1 = workbook.createCellStyle();
		dataStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居左
		dataStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		dataStyle1.setFont(dataFont);

		// 设置内容样式-居右
		HSSFCellStyle dataStyle2 = workbook.createCellStyle();
		dataStyle2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 左右居右
		dataStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		dataStyle2.setFont(dataFont);

		// 设置内容样式-居中
		HSSFCellStyle dataStyle3 = workbook.createCellStyle();
		dataStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		dataStyle3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		dataStyle3.setFont(dataFont);

		int i = 1;
		for (Map<String, Object> map : synMerchantList) {

			// 由于没有查询到CRM统计数据，则不在excel中展示
			if ("false".equals(String.valueOf(map.get("flag")))) {
				continue;
			}

			HSSFRow dataRow = sheet.createRow(i++);
			HSSFCell cell0 = dataRow.createCell(0); // 客户
			HSSFCell cell1 = dataRow.createCell(1); // 管理公司
			HSSFCell cell2 = dataRow.createCell(2); // 品牌ID
			HSSFCell cell3 = dataRow.createCell(3); // 前端
			HSSFCell cell4 = dataRow.createCell(4); // 组别
			HSSFCell cell5 = dataRow.createCell(5); // 地点
			HSSFCell cell6 = dataRow.createCell(6); // 上线时间
			HSSFCell cell7 = dataRow.createCell(7); // 年限
			HSSFCell cell8 = dataRow.createCell(8); // 开台数
			HSSFCell cell9 = dataRow.createCell(9); // 会员总量
			HSSFCell cell10 = dataRow.createCell(10); // 储值总额
			HSSFCell cell11 = dataRow.createCell(11); // 老会员交易笔数
			HSSFCell cell12 = dataRow.createCell(12); // 会员消费占比
			HSSFCell cell13 = dataRow.createCell(13); // 当月储值
			HSSFCell cell14 = dataRow.createCell(14); // 新增积分会员
			HSSFCell cell15 = dataRow.createCell(15); // 新增积分会员占比
			HSSFCell cell16 = dataRow.createCell(16); // 新增储值会员
			HSSFCell cell17 = dataRow.createCell(17); // 新增储值会员占比
			HSSFCell cell18 = dataRow.createCell(18); // 新增会员占比

			cell0.setCellStyle(dataStyle1);
			cell1.setCellStyle(dataStyle1);
			cell2.setCellStyle(dataStyle2);
			cell3.setCellStyle(dataStyle3);
			cell4.setCellStyle(dataStyle3);
			cell5.setCellStyle(dataStyle3);
			cell6.setCellStyle(dataStyle1);
			cell7.setCellStyle(dataStyle1);
			cell8.setCellStyle(dataStyle2);
			cell9.setCellStyle(dataStyle2);
			cell10.setCellStyle(dataStyle2);
			cell11.setCellStyle(dataStyle2);
			cell12.setCellStyle(dataStyle2);
			cell13.setCellStyle(dataStyle2);
			cell14.setCellStyle(dataStyle2);
			cell15.setCellStyle(dataStyle2);
			cell16.setCellStyle(dataStyle2);
			cell17.setCellStyle(dataStyle2);
			cell18.setCellStyle(dataStyle2);

			String brandShortPinyin = String.valueOf(map.get("brand_short_pinyin"));
			String brand = String.valueOf(map.get("brand"));
			String merchantId = String.valueOf(map.get("merchant_id"));
			String frontName = String.valueOf(map.get("front_name"));
			String groupName = String.valueOf(map.get("group_name"));
			String city = String.valueOf(map.get("city"));
			String serviceStartTime = DateUtil.format((java.util.Date) map.get("service_start_time"), "yyyy/MM/dd");
			String serviceTime = String.valueOf(map.get("service_time"));
			String deskNum = String.valueOf(map.get("desk_num"));
			String membershipNum = String.valueOf(map.get("membership_num"));
			String storeBalance = String.valueOf(map.get("store_balance"));
			String oldTransQuantity = String.valueOf(map.get("old_trans_quantity"));
			String memberConsumeRadio = String.valueOf(map.get("member_consume_radio")) + " %";
			String monthStorePay = String.valueOf(map.get("month_store_pay"));
			String integralMember = String.valueOf(map.get("integral_member"));
			String integralMemberRadio = String.valueOf(map.get("integral_member_radio")) + " %";
			String storeMember = String.valueOf(map.get("store_member"));
			String storeMemberRadio = String.valueOf(map.get("store_member_radio")) + " %";
			String newMemberRadio = String.valueOf(map.get("new_member_radio")) + " %";

			HSSFRichTextString text0 = new HSSFRichTextString(brandShortPinyin); // 客户
			HSSFRichTextString text1 = new HSSFRichTextString(brand); // 管理公司
			HSSFRichTextString text2 = new HSSFRichTextString(merchantId); // 品牌ID
			HSSFRichTextString text3 = new HSSFRichTextString(frontName); // 前端
			HSSFRichTextString text4 = new HSSFRichTextString(groupName); // 组别
			HSSFRichTextString text5 = new HSSFRichTextString(city); // 地点
			HSSFRichTextString text6 = new HSSFRichTextString(serviceStartTime); // 上线时间
			HSSFRichTextString text7 = new HSSFRichTextString(serviceTime); // 年限
			HSSFRichTextString text8 = new HSSFRichTextString(deskNum); // 开台数
			HSSFRichTextString text9 = new HSSFRichTextString(membershipNum); // 会员总量
			HSSFRichTextString text10 = new HSSFRichTextString(storeBalance); // 储值总额
			HSSFRichTextString text11 = new HSSFRichTextString(oldTransQuantity); // 老会员交易笔数
			HSSFRichTextString text12 = new HSSFRichTextString(memberConsumeRadio); // 会员消费占比
			HSSFRichTextString text13 = new HSSFRichTextString(monthStorePay); // 当月储值
			HSSFRichTextString text14 = new HSSFRichTextString(integralMember); // 新增积分会员
			HSSFRichTextString text15 = new HSSFRichTextString(integralMemberRadio); // 新增积分会员占比
			HSSFRichTextString text16 = new HSSFRichTextString(storeMember); // 新增储值会员
			HSSFRichTextString text17 = new HSSFRichTextString(storeMemberRadio); // 新增储值会员占比
			HSSFRichTextString text18 = new HSSFRichTextString(newMemberRadio); // 新增会员占比

			cell0.setCellValue(text0);
			cell1.setCellValue(text1);
			cell2.setCellValue(text2);
			cell3.setCellValue(text3);
			cell4.setCellValue(text4);
			cell5.setCellValue(text5);
			cell6.setCellValue(text6);
			cell7.setCellValue(text7);
			cell8.setCellValue(text8);
			cell9.setCellValue(text9);
			cell10.setCellValue(text10);
			cell11.setCellValue(text11);
			cell12.setCellValue(text12);
			cell13.setCellValue(text13);
			cell14.setCellValue(text14);
			cell15.setCellValue(text15);
			cell16.setCellValue(text16);
			cell17.setCellValue(text17);
			cell18.setCellValue(text18);

			// 调整个别列的宽度
			sheet.setColumnWidth(0, 30 * 256);
			sheet.setColumnWidth(1, 60 * 256);
			sheet.setColumnWidth(3, 20 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			sheet.setColumnWidth(5, 10 * 256);
			sheet.setColumnWidth(7, serviceTime.getBytes().length * 256);
		}

		log.info("共导出" + (i - 1) + "条数据");
	}

	/**
	 * @Description 导出excel文档
	 * @param request
	 * @param response
	 * @param sheetName
	 * @param workbook
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @return void
	 * @throws
	 */
	private void exportExcel(HttpServletRequest request, HttpServletResponse response, String sheetName, HSSFWorkbook workbook)
			throws IOException, UnsupportedEncodingException {
		String fileName = sheetName + ".xls";
		OutputStream os = response.getOutputStream();
		try {
			response.reset();
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE")) { // IE
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) { // Firefox
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.setContentType("application/octet-stream; charset=utf-8");
			workbook.write(os);
			os.flush();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	/**
	 * @Description 计算开始时间到结束时间差距的总年数，总月数，总天数，
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @return int[]
	 * @throws
	 */
	private int[] getDateLength(String fromDate, String toDate) {
		Calendar c1 = getCal(fromDate);
		Calendar c2 = getCal(toDate);
		int[] p1 = { c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH) };
		int[] p2 = { c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH) };
		return new int[] { p2[0] - p1[0], p2[0] * 12 + p2[1] - p1[0] * 12 - p1[1],
				(int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600 * 1000)) };
	}

	private Calendar getCal(String date) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));
		return cal;
	}
}
