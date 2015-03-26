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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.mkt.service.MktShopSurveyService;
import com.yazuo.erp.mkt.vo.MktShopSurveyVO;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * 门店调研 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("mktShopSurvey")
public class MktShopSurveyController {
	
	private static final Log LOG = LogFactory.getLog(MktShopSurveyController.class);
	@Resource
	private MktShopSurveyService mktShopSurveyService;
	@Resource
	private SysDictionaryService sysDictionaryService;
	@Resource
	private SysOperationLogService sysOperationLogService;
	
	/**
	 * 保存修改门店调研
	 */
	@RequestMapping(value = "saveMktShopSurvey", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveMktShopSurvey(@RequestBody MktShopSurveyVO mktShopSurvey, HttpSession session) {
		SysUserVO user = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		if (mktShopSurvey.getId() !=null && mktShopSurvey.getId()!=0) { // 修改
			return mktShopSurveyService.updateMktShopSurvey(mktShopSurvey, user);
		} else {
			// 添加
			return mktShopSurveyService.saveMktShopSurvey(mktShopSurvey, user);
		}
	}
	
	/**
	 * 销售前端查看 门店调研列表 
	 */
	@RequestMapping(value = "listMktShopSurveys", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listMktShopSurveys(@RequestBody Map<String, Object> paramMap) {
		Integer merchantId = Integer.parseInt(paramMap.get("merchantId").toString());
		MktShopSurveyVO mktShopSurvey = new MktShopSurveyVO();
		mktShopSurvey.setMerchantId(merchantId);
		mktShopSurvey.setIsEnable("1");
		//调研列表
		List<MktShopSurveyVO> list = mktShopSurveyService.getMktShopSurveys(mktShopSurvey);
		// 操作流水
		SysOperationLogVO operatLog = new SysOperationLogVO();
		operatLog.setMerchantId(merchantId);
		operatLog.setModuleType("mkt");
		List<SysOperationLogVO> operationList = sysOperationLogService.getSysOperationLogs(operatLog);
		// 判断确认完成按钮是否可用
		String btnEnbleStatus = mktShopSurveyService.judgeSalesConfirmBtnEnble(merchantId);
		// 门店访谈单是否存在
		boolean isInterview = mktShopSurveyService.brandInterviewExists(merchantId);
		
		JsonResult result = new JsonResult(true);
		result.putData("shopSurveyList", list);
		result.putData("operationLogList", operationList);
		result.putData("brandInterviewName", isInterview ? "《商户访谈单》" : "");
		result.putData("isDisabled", btnEnbleStatus);
		return result;
	}
	
	/**
	 * 删除 门店调研 
	 */
	@RequestMapping(value = "deleteMktShopSurvey", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteMktShopSurvey(@RequestBody MktShopSurveyVO mktShopSurveyVO, HttpSession session) {
		SysUserVO user = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		return mktShopSurveyService.deleteMktShopSurveyById(mktShopSurveyVO, user);
	}
	
	// 初始化业态信息
	@RequestMapping(value = "initFormatType", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult initFormatType() {
		List<Map<String, Object>> list = sysDictionaryService.querySysDictionaryByTypeStd("00000001");
		return new JsonResult(true).setData(list).setMessage("查询成功!");
	}
	
	// 现有宣传物料信息
	@RequestMapping(value = "initMaterialType", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult initMaterialType() {
		List<Map<String, Object>> list = sysDictionaryService.querySysDictionaryByTypeStd("00000076");
		return new JsonResult(true).setData(list).setMessage("查询成功!");
	}
	
	// 编辑
	@RequestMapping(value = "edit", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	@Deprecated //recommand user method 
	public JsonResult edit(Integer id) {
		MktShopSurveyVO shop = mktShopSurveyService.getMktShopSurveyById(id);
		return new JsonResult(true).setData(shop);
	}
	
	/**
	 * 编辑或新增调研单接口 
	 * 有id是编辑， 否则新增
	 * @param mktShopSurveyId 编辑的时候有值
	 * @return merchantId 必填 ： 查找负责人的时候用到
	 */
	@RequestMapping(value = "loadMktShopSurvey", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult loadMktShopSurvey(@RequestBody MktShopSurveyVO mktShopSurvey) {
		MktShopSurveyVO	mktShopSurveyVO = mktShopSurveyService.loadMktShopSurvey(mktShopSurvey);
		return new JsonResult(true).setData(mktShopSurveyVO);
	}
	
	
	
	// 销售模块点完成创建
	@RequestMapping(value = "salesComfirm", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult salesComfirm(Integer merchantId, HttpSession session) {
		SysUserVO user = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		return mktShopSurveyService.salesConfirm(merchantId, user);
	}
	
}
