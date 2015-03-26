/**
 * @Description 填单信息控制类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.controller;

import java.io.IOException;
import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.testng.Assert;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;

import com.yazuo.erp.system.service.SysDocumentService;
import com.yazuo.erp.system.controller.SysRoleController;
import com.yazuo.erp.system.vo.SysDocumentVO;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 填单信息 相关业务操作
 * 
 * @author
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("sysDocument")
public class SysDocumentController {

	private static final Log LOG = LogFactory.getLog(SysDocumentController.class);
	@Resource private SysDocumentService sysDocumentService;
	@Resource private FesOnlineProcessService fesOnlineProcessService;

	/**
	 * @Description 上传填单附件
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping(value = "uploadFiles", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	@Deprecated
	public Object uploadPlanFiles(@RequestParam(value = "myfiles", required = false) MultipartFile[] myfiles,
			HttpServletRequest request) throws IOException {
		return sysDocumentService.uploadFiles(myfiles, request);
	}
	
	/**
	 * @Description 上线流程 培训 上传录音
	 */
	@RequestMapping(value = "uploadRecordAndSaveSysDocumentForStep9", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object uploadRecordAndSaveSysDocumentForStep9(@RequestParam(value = "myfile", required = false) MultipartFile myfile,
			String inputSysDocument, HttpServletRequest request) {
		SysDocumentVO sysDocument= (SysDocumentVO)JsonResult.readValueToVO(inputSysDocument, SysDocumentVO.class);
		Assert.assertTrue(sysDocument!=null, "参数错误！");
		MultipartFile[] myfiles = new MultipartFile[1];
		myfiles[0] = myfile; 
		SysUserVO user = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		if(user ==null){
			user = (SysUserVO)request.getAttribute(Constant.SESSION_USER);
		}
		return sysDocumentService.uploadRecordAndSaveSysDocumentForStep9(myfiles, sysDocument, request, user);
	}
	/**
	 * @Description 上线后 回访上传录音 
	 */
	@RequestMapping(value = "uploadRecordAndSaveSysDocumentAfterOnline", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object uploadRecordAndSaveSysDocumentAfterOnline(@RequestParam(value = "myfile", required = false) MultipartFile myfile,
			String inputSysDocuments, HttpServletRequest request) {
		System.out.println("ddddddddd");
		SysDocumentVO[] sysDocuments= (SysDocumentVO[])JsonResult.readValueToVO(inputSysDocuments, SysDocumentVO[].class);
		Assert.assertTrue(sysDocuments!=null, "参数错误！");
		MultipartFile[] myfiles = new MultipartFile[1];
		myfiles[0] = myfile; 
		SysUserVO user = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		if(user ==null){
			user = (SysUserVO)request.getAttribute(Constant.SESSION_USER);
		}
		return sysDocumentService.uploadRecordAndSaveSysDocumentAfterOnline(myfiles, sysDocuments, request, user, true);
	}
	
	/**
	 * 新增(多个)填单信息
	 * 
	 * 第9步，新增/修改上线实施培训回访单的时候调用
	 */
	@RequestMapping(value = "saveDocumentAndUpdateStatusForStep9", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveDocumentAndUpdateStatusForStep9(@RequestBody SysDocumentVO sysDocument, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		JsonResult json  = sysDocumentService.saveDocumentAndUpdateStatusForStep9(sysDocument, request, user, false);
		return new JsonResult(json!=null).setData(json);
	}
	

	/**
 	 *  保存填单信息 , 如果录音和调研单都存在则更新状态
	 * 
	 *  第10步结束以后由后端客服处理.上线后回访单
	 */
	@RequestMapping(value = "saveDocumentAndUpdateStatusAfterStep10", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveDocumentAndUpdateStatusAfterStep10(@RequestBody SysDocumentVO[] sysDocuments, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		return sysDocumentService.saveDocumentAndUpdateStatusAfterStep10(sysDocuments, request, user);
	}
	
	/**
	 *  上线后确认已回访后关闭待办事项
	 */
	@RequestMapping(value = "closeToDoListsAfterViste", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult closeToDoListsAfterViste(Integer merchantId) {
		JsonResult jsonResult = new JsonResult();
		this.fesOnlineProcessService.closeToDoListsAfterViste(merchantId);
		return jsonResult.setFlag(true);
	}
	/**
	 *  上线后确认已回访后关闭待办事项
	 */
	@RequestMapping(value = "getDocumentByMerchantAndType", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getDocumentByMerchantAndType(Integer merchantId, String documentType) {
		JsonResult jsonResult = new JsonResult();
		List<SysDocumentVO> listSysDocumentVO = this.sysDocumentService.getDocumentByMerchantAndType(merchantId, documentType);
		return jsonResult.setFlag(true).setData(listSysDocumentVO);
	}
	
	/**
	 * 保存填单信息
	 */
	@RequestMapping(value = "saveSysDocument", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	@Deprecated
	public JsonResult saveSysDocument(@RequestBody SysDocumentVO sysDocument, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysDocumentService.saveSysDocument(sysDocument, request, user);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}

	/**
	 * 修改填单信息
	 */
	@RequestMapping(value = "updateSysDocument", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	@Deprecated
	public JsonResult updateSysDocument(@RequestBody SysDocumentVO sysDocument, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysDocumentService.updateSysDocument(sysDocument, request, user);
		return new JsonResult(count > 0).setMessage(count > 0 ? "修改成功!" : "修改失败!");
	}

	/**
	 * 根据ID查询填单信息
	 */
	@RequestMapping(value = "querySysDocumentById", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult querySysDocumentById(@RequestBody SysDocumentVO sysDocument) {
		SysDocumentVO sysDocumentVO = sysDocumentService.querySysDocumentById(sysDocument);
		return new JsonResult().setFlag(true).setData(sysDocumentVO);
	}
	
	/**
	 * 根据商户ID查询填单信息
	 */
	@RequestMapping(value = "getSysDocumentByMerchantId", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getSysDocumentByMerchantId(@RequestBody SysDocumentVO sysDocument) {
		List<SysDocumentVO> listSysDocumentVO = sysDocumentService.getSysDocumentByMerchantId(sysDocument);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setFlag(true).setData(listSysDocumentVO);
		LOG.info(jsonResult.getJsonString(jsonResult));
		return jsonResult;
	}
	
	/**
	 *  通过商户Id 和类型查找对应的回访信息
	 *  @param merchantId
	 *  @param documentType
	 */
	@RequestMapping(value = "getSysDocumentsByMerchantAndType", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getSysDocumentsByMerchantAndType(@RequestBody SysDocumentVO[] sysDocuments) {
		List<SysDocumentVO> lists = sysDocumentService.getSysDocumentsByMerchantAndType(sysDocuments);
		return new JsonResult().setFlag(true).setData(lists);
	}

	/**
	 * 删除 填单信息
	 */
	@RequestMapping(value = "deleteSysDocument/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult deleteSysDocument(@PathVariable int id) {
		int count = sysDocumentService.deleteSysDocumentById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
