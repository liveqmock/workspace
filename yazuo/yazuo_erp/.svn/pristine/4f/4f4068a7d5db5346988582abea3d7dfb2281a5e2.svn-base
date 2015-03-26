/**
 * @Description 工作计划导出接口实现类
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
import com.yazuo.erp.fes.dao.FesPlanDao;
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

import com.yazuo.erp.fes.service.FesPlanReportService;
import com.yazuo.erp.fes.service.FesReportService;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.util.DateUtil;

@Service
public class FesPlanReportServiceImpl implements FesPlanReportService {

	private static final Log log = LogFactory.getLog(FesReportController.class);

	@Resource
	private FesPlanDao fesPlanDao;

	/**
	 * @Title exportReport
	 * @Description 导出工作计划，并下载
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
		// 校验入参
		Object startTimeObj = paramMap.get("startTime");
		if (null == startTimeObj) {
			throw new FesBizException("请选择开始时间");
		}
		Date startDate = DateUtil.toDateFromMillisecond(startTimeObj);

		Object endTimeObj = paramMap.get("endTime");
		if (null == endTimeObj) {
			throw new FesBizException("请选择结束时间");
		}
		Date endDate = DateUtil.toDateFromMillisecond(endTimeObj);

		// 按照开始时间和结束时间查询工作计划列表
		List<Map<String, Object>> list = fesPlanDao.getFesPlansList(startDate, endDate);

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		String sheetName = "工作计划";
		HSSFSheet sheet = workbook.createSheet(sheetName);

		// 生成Excel标题行
		this.generateFirstRow(workbook, sheet);

		// 生成Excel内容行
		this.generateContentRows(list, workbook, sheet);

		// 导出excel文档
		this.exportExcel(request, response, sheetName, workbook);
		return true;
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

		// 设置首行样式-金色
		HSSFCellStyle firstRowStyle = workbook.createCellStyle();
		firstRowStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置填充样式
		firstRowStyle.setFillForegroundColor(HSSFColor.GOLD.index);// 设置填充色-金色
		firstRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		firstRowStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		firstRowStyle.setFont(fontOfFirstRow);

		String[] headers = { "工作计划id", "用户id", "用户", "商户id", "商户名称", "商户简称", "标题", "事项类型", "沟通方式", "联系人", "说明", "开始时间", "完成时间",
				"延时和放弃时情况说明", "来源", "状态", "创建人", "创建时间", "最后修改人", "修改时间" };

		// 生成Excel标题行
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) (15.625 * 30));// 25为行高的像素数
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(firstRowStyle);
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
			HSSFRow dataRow = sheet.createRow(i++);
			HSSFCell cell0 = dataRow.createCell(0); // 工作计划id
			HSSFCell cell1 = dataRow.createCell(1); // 用户id
			HSSFCell cell2 = dataRow.createCell(2); // 用户
			HSSFCell cell3 = dataRow.createCell(3); // 商户id
			HSSFCell cell4 = dataRow.createCell(4); // 商户名称
			HSSFCell cell5 = dataRow.createCell(5); // 商户简称
			HSSFCell cell6 = dataRow.createCell(6); // 标题
			HSSFCell cell7 = dataRow.createCell(7); // 事项类型
			HSSFCell cell8 = dataRow.createCell(8); // 沟通方式
			HSSFCell cell9 = dataRow.createCell(9); // 联系人
			HSSFCell cell10 = dataRow.createCell(10); // 说明
			HSSFCell cell11 = dataRow.createCell(11); // 开始时间
			HSSFCell cell12 = dataRow.createCell(12); // 完成时间
			HSSFCell cell13 = dataRow.createCell(13); // 延时和放弃时情况说明
			HSSFCell cell14 = dataRow.createCell(14); // 来源
			HSSFCell cell15 = dataRow.createCell(15); // 状态
			HSSFCell cell16 = dataRow.createCell(16); // 创建人
			HSSFCell cell17 = dataRow.createCell(17); // 创建时间
			HSSFCell cell18 = dataRow.createCell(18); // 最后修改人
			HSSFCell cell19 = dataRow.createCell(19); // 修改时间

			cell0.setCellStyle(dataStyle2);
			cell1.setCellStyle(dataStyle2);
			cell2.setCellStyle(dataStyle1);
			cell3.setCellStyle(dataStyle2);
			cell4.setCellStyle(dataStyle1);
			cell5.setCellStyle(dataStyle1);
			cell6.setCellStyle(dataStyle1);
			cell7.setCellStyle(dataStyle3);
			cell8.setCellStyle(dataStyle3);
			cell9.setCellStyle(dataStyle1);
			cell10.setCellStyle(dataStyle1);
			cell11.setCellStyle(dataStyle1);
			cell12.setCellStyle(dataStyle1);
			cell13.setCellStyle(dataStyle1);
			cell14.setCellStyle(dataStyle3);
			cell15.setCellStyle(dataStyle3);
			cell16.setCellStyle(dataStyle1);
			cell17.setCellStyle(dataStyle1);
			cell18.setCellStyle(dataStyle1);
			cell19.setCellStyle(dataStyle1);

			String planId = String.valueOf(map.get("plan_id")); // 工作计划id
			String userId = String.valueOf(map.get("user_id")); // 用户id
			String userName = String.valueOf(map.get("user_name")); // 用户
			String merchantId = String.valueOf(map.get("merchant_id")); // 商户id
			String merchantName = String.valueOf(map.get("merchant_name")); // 商户名称
			String brandShortPinyin = String.valueOf(map.get("brand_short_pinyin")); // 商户简称
			String title = String.valueOf(map.get("title")); // 标题
			String planItemTypeName = String.valueOf(map.get("plan_item_type_name")); // 事项类型
			String communicationFormTypeName = String.valueOf(map.get("communication_form_type_name")); // 沟通方式
			String contactName = String.valueOf(map.get("contact_name")); // 联系人
			String description = String.valueOf(map.get("description")); // 说明
			String startTime = String.valueOf(map.get("start_time")); // 开始时间
			String endTime = String.valueOf(map.get("end_time")); // 完成时间
			String explanation = String.valueOf(map.get("explanation")); // 延时和放弃时情况说明
			String plansSourceName = String.valueOf(map.get("plans_source_name")); // 来源
			String statusName = String.valueOf(map.get("status_name")); // 状态
			String insertByName = String.valueOf(map.get("insert_by_name")); // 创建人
			String insertTime = String.valueOf(map.get("insert_time")); // 创建时间
			String updateByName = String.valueOf(map.get("update_by_name")); // 最后修改人
			String updateTime = String.valueOf(map.get("update_time")); // 修改时间

			HSSFRichTextString text0 = new HSSFRichTextString(planId); // 工作计划id
			HSSFRichTextString text1 = new HSSFRichTextString(userId); // 用户id
			HSSFRichTextString text2 = new HSSFRichTextString(userName); // 用户
			HSSFRichTextString text3 = new HSSFRichTextString("0".equals(merchantId) ? "" : merchantId); // 商户id
			HSSFRichTextString text4 = new HSSFRichTextString(merchantName); // 商户名称
			HSSFRichTextString text5 = new HSSFRichTextString(brandShortPinyin); // 商户简称
			HSSFRichTextString text6 = new HSSFRichTextString(title); // 标题
			HSSFRichTextString text7 = new HSSFRichTextString(planItemTypeName); // 事项类型
			HSSFRichTextString text8 = new HSSFRichTextString(communicationFormTypeName); // 沟通方式
			HSSFRichTextString text9 = new HSSFRichTextString(contactName); // 联系人
			HSSFRichTextString text10 = new HSSFRichTextString(description); // 说明
			HSSFRichTextString text11 = new HSSFRichTextString(startTime); // 开始时间
			HSSFRichTextString text12 = new HSSFRichTextString(endTime); // 完成时间
			HSSFRichTextString text13 = new HSSFRichTextString(explanation); // 延时和放弃时情况说明
			HSSFRichTextString text14 = new HSSFRichTextString(plansSourceName); // 来源
			HSSFRichTextString text15 = new HSSFRichTextString(statusName); // 状态
			HSSFRichTextString text16 = new HSSFRichTextString(insertByName); // 创建人
			HSSFRichTextString text17 = new HSSFRichTextString(insertTime); // 创建时间
			HSSFRichTextString text18 = new HSSFRichTextString(updateByName); // 最后修改人
			HSSFRichTextString text19 = new HSSFRichTextString(updateTime); // 修改时间

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
			cell19.setCellValue(text19);

			// 调整个别列的宽度
			// sheet.setColumnWidth(0, 30 * 256); // 工作计划id
			// sheet.setColumnWidth(1, 30 * 256); // 用户id
			sheet.setColumnWidth(2, 10 * 256); // 用户
			// sheet.setColumnWidth(3, 30 * 256); // 商户id
			sheet.setColumnWidth(4, 40 * 256); // 商户名称
			sheet.setColumnWidth(5, 20 * 256); // 商户简称
			sheet.setColumnWidth(6, 50 * 256); // 标题
			// sheet.setColumnWidth(7, 20 * 256); // 事项类型
			// sheet.setColumnWidth(8, 30 * 256); // 沟通方式
			sheet.setColumnWidth(9, 10 * 256); // 联系人
			sheet.setColumnWidth(10, 40 * 256); // 说明
			// sheet.setColumnWidth(11, 30 * 256); // 开始时间
			sheet.setColumnWidth(12, 20 * 256); // 完成时间
			sheet.setColumnWidth(13, 40 * 256); // 延时和放弃时情况说明
			sheet.setColumnWidth(14, 10 * 256); // 来源
			sheet.setColumnWidth(15, 10 * 256); // 状态
			sheet.setColumnWidth(16, 10 * 256); // 创建人
			sheet.setColumnWidth(17, 20 * 256); // 创建时间
			// sheet.setColumnWidth(18, 30 * 256); // 最后修改人
			sheet.setColumnWidth(19, 20 * 256); // 修改时间
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
}
