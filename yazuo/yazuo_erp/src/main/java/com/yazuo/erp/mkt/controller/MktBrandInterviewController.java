/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.mkt.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.mkt.dao.MktBrandInterviewDao;
import com.yazuo.erp.mkt.service.MktBrandInterviewService;
import com.yazuo.erp.mkt.vo.MktBrandInterviewVO;
import com.yazuo.erp.mkt.vo.MktShopSurveyVO;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 品牌访谈 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("mktBrandInterview")
public class MktBrandInterviewController {
	
	private static final Log LOG = LogFactory.getLog(MktBrandInterviewController.class);
	@Resource
	private MktBrandInterviewService mktBrandInterviewService;
	@Resource
	private SysDictionaryService sysDictionaryService;
	@Resource
	private SysOperationLogService sysOperationLogService;
	@Resource
	private MktBrandInterviewDao mktBrandInterviewDao;
	@Value("${merchantLogoPath}")
	private String merchantLogoPath;
	
	/**
	 * 修改品牌访谈
	 */
	@RequestMapping(value = "saveMktBrandInterview", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveMktBrandInterview(@RequestBody MktBrandInterviewVO mktBrandInterview, HttpSession session, HttpServletRequest request) {
		int count = 0; 
		SysUserVO user = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		mktBrandInterview.setUpdateBy(user.getId());
		mktBrandInterview.setUpdateTime(new Date());
		mktBrandInterview.setIsEnable("1");
		
//		BigDecimal whiteBig = new BigDecimal(mktBrandInterview.getWhiteListNumberStr());
//		mktBrandInterview.setWhiteListNumber(whiteBig);
		
//		BigDecimal sendBig = new BigDecimal(mktBrandInterview.getSendCardNumberStr());
//		mktBrandInterview.setSendCardNumber(sendBig);

			
		String description = "";
		Integer merchantId = mktBrandInterview.getMerchantId();
		MktBrandInterviewVO mktBrandInterviewVOP = new MktBrandInterviewVO();
		mktBrandInterviewVOP.setMerchantId(merchantId);
		mktBrandInterviewVOP.setIsEnable(Constant.IS_ENABLE);
		List<MktBrandInterviewVO> listMktBrandInterviewVO = this.mktBrandInterviewDao.getMktBrandInterviews(mktBrandInterviewVOP);
		if(listMktBrandInterviewVO.size()>0){ //编辑
			MktBrandInterviewVO dbEntity = listMktBrandInterviewVO.get(0);
			mktBrandInterview.setId(dbEntity.getId());
			count = mktBrandInterviewService.updateMktBrandInterview(mktBrandInterview, request);
			description = "更新了";
		} else {
			mktBrandInterview.setInsertBy(user.getId());
			mktBrandInterview.setInsertTime(new Date());
			count = mktBrandInterviewService.saveMktBrandInterview(mktBrandInterview, request);
			description = "增加了";
		}
		
		// 添加操作日志
		String fesLogType = ""; // 日志类型
		if (mktBrandInterview.getModuleType().equals("mkt")) {
			fesLogType = "1";
		} else if (mktBrandInterview.getModuleType().equals("fes")) {
			fesLogType = "3";
		}
		SysOperationLogVO operateLog = new SysOperationLogVO();
		operateLog.setInsertBy(user.getId());
		operateLog.setInsertTime(new Date());
		operateLog.setDescription(description+"商户访谈单");
		operateLog.setFesLogType(fesLogType);
		operateLog.setMerchantId(mktBrandInterview.getMerchantId());
		operateLog.setModuleType(mktBrandInterview.getModuleType());
		operateLog.setOperatingTime(new Date());
		sysOperationLogService.saveSysOperationLog(operateLog);
		
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!").setData(mktBrandInterview);
	}
	
	
	// 初始化门店标签中的基本信息
	@RequestMapping(value = "initMerchantMsg", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult initMerchantMsg() {
		List<Map<String, Object>> list1 = sysDictionaryService.querySysDictionaryByTypeStd("00000066");
		List<Map<String, Object>> list2 = sysDictionaryService.querySysDictionaryByTypeStd("00000067");
		List<Map<String, Object>> list3 = sysDictionaryService.querySysDictionaryByTypeStd("00000068");
		JsonResult result = new JsonResult(true);
		result.putData("joinList", list1);
		result.putData("nearList", list2);
		result.putData("coustomerList", list3);
		result.setMessage("查询成功!");
		return result;
	}
	
	// 初始化会员信息
	@RequestMapping(value = "initCardMsg", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult initCardMsg() {
		List<Map<String, Object>> list1 = sysDictionaryService.querySysDictionaryByTypeStd("00000069");
		List<Map<String, Object>> list2 = sysDictionaryService.querySysDictionaryByTypeStd("00000070");
		List<Map<String, Object>> list3 = sysDictionaryService.querySysDictionaryByTypeStd("00000071");
		JsonResult result = new JsonResult(true);
		result.putData("cardList", list1);
		result.putData("systemList", list2);
		result.putData("memberRightList", list3);
		result.setMessage("查询成功!");
		return result;
	}
	
	// 初始化门店基础设备信息
	@RequestMapping(value = "initSettingMsg", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult initSettingMsg() {
		List<Map<String, Object>> list1 = sysDictionaryService.querySysDictionaryByTypeStd("00000073");
		List<Map<String, Object>> list2 = sysDictionaryService.querySysDictionaryByTypeStd("00000074");
		JsonResult result = new JsonResult(true);
		Collections.sort(list1, new BeanComparator("value"));
		result.putData("networkList", list1);
		result.putData("storeTrainingList", list2);
		result.setMessage("查询成功!");
		return result;
	}
	
	// 编辑
	@RequestMapping(value = "edit", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	@Deprecated 
	public JsonResult edit(Integer merchantId) {
		MktBrandInterviewVO brandInterview = new MktBrandInterviewVO();
		brandInterview.setMerchantId(merchantId);
		
		List<MktBrandInterviewVO> list = mktBrandInterviewService.getMktBrandInterviews(brandInterview);
		if (list !=null && list.size() > 0) {
			brandInterview = list.get(0);
		}
		brandInterview.setRelativePath(merchantLogoPath+"/"+brandInterview.getAttachmentPath() + "/" + brandInterview.getFileName());
		BigDecimal sendBig = brandInterview.getSendCardNumber();
//		BigDecimal whiteBig = brandInterview.getWhiteListNumber();
		
		brandInterview.setSendCardNumberStr(String.valueOf(sendBig));
//		brandInterview.setWhiteListNumberStr(String.valueOf(whiteBig));
		return new JsonResult(true).setData(brandInterview);
	}
	

	/**
	 * 编辑或新增调研单接口 
	 * 有id是编辑， 否则新增
	 * @param mktShopSurveyId 编辑的时候有值
	 * @return merchantId 必填 ： 查找负责人的时候用到
	 */
	@RequestMapping(value = "loadMktBrandInterview", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult loadMktBrandInterview(@RequestBody MktBrandInterviewVO mktBrandInterview) {

		MktBrandInterviewVO mktBrandInterviewVO = mktBrandInterviewService.loadMktBrandInterview(mktBrandInterview);
		return new JsonResult(true).setData(mktBrandInterviewVO);
	}
	
	/**
	 * 上传电子文档
	 */
	@RequestMapping(value = "uploadAttachment", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult uploadAttachment(@RequestParam MultipartFile myfile, HttpServletRequest request) throws IOException {
		String basePath = request.getSession().getServletContext().getRealPath("/");
		SysUserVO user = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		if(user==null){
			user = (SysUserVO)request.getAttribute(Constant.SESSION_USER);
		}
		return new JsonResult(true).setData(this.mktBrandInterviewService.uploadAttachment(myfile, basePath, user));
	}
	
}
