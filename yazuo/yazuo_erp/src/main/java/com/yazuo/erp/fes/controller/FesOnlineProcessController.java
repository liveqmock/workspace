/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yazuo.erp.system.service.*;
import com.yazuo.erp.system.vo.SysSmsTemplateVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.EmailVO;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.SendMessageVoid;
import com.yazuo.erp.bes.service.BesMonthlyCheckService;
import com.yazuo.erp.bes.service.BesMonthlyReportService;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.fes.service.FesMaterialRequestDtlService;
import com.yazuo.erp.fes.service.FesMaterialRequestService;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.erp.fes.service.FesOpenCardApplicationService;
import com.yazuo.erp.fes.service.FesRemarkService;
import com.yazuo.erp.fes.service.FesTrainPlanService;
import com.yazuo.erp.fes.vo.FesMaterialRequestDtlVO;
import com.yazuo.erp.fes.vo.FesMaterialRequestVO;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.fes.vo.FesOpenCardApplicationVO;
import com.yazuo.erp.fes.vo.FesTrainPlanVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.vo.SysAssistantMerchantVO;
import com.yazuo.erp.system.vo.SysMailTemplateVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.CalendarService;
import com.yazuo.util.EmailUtil;

/**
 * 上线流程 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("fesOnlineProcess")
@SuppressWarnings("unused")
public class FesOnlineProcessController {
	
	private static final Log LOG = LogFactory.getLog(FesOnlineProcessController.class);
	@Resource
	private FesOnlineProcessService fesOnlineProcessService;	
	@Resource
	private FesRemarkService fesRemarkService;
	@Resource
	private FesTrainPlanService fesTrainPlanService;
	@Resource
	private CalendarService calendarService;
	@Resource
	private SysAttachmentService sysAttachmentService;
	@Resource
	private SysOperationLogService sysOperationLogService;

	@Resource
	private SysSmsTemplateService sysSmsTemplateService;

	@Resource
	private FesOnlineProgramService fesOnlineProgramService;
	@Resource private FesOpenCardApplicationService fesonOpenCardApplicationService;
	@Resource private SysAssistantMerchantService sysAssistantMerchantService;
	@Resource private EmailUtil emailUtil;
	@Resource private SysMailTemplateService sysMailTemplateService;
	@Resource private FesMaterialRequestService fesMaterialRequestService;
	@Resource private BesMonthlyReportService besMonthlyReportService ;  //
	@Resource private SysDictionaryService sysDictionaryService;

	/**  
	 * 列表显示 上线流程 
	 * 
	 * 查询所有当前商户的上线流程及其他相关信息
	 */
	@RequestMapping(value = "listFesOnlineProcesss", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listFesOnlineProcesss(@RequestBody FesOnlineProcessVO fesOnlineProcessVO) {
   
		List<FesOnlineProcessVO> fesOnlineProcesss = fesOnlineProcessService.getComplexFesOnlineProcesss(fesOnlineProcessVO);
		return new JsonResult(true).setData(fesOnlineProcesss);
	}
	/**
	 * 列表显示 上线后流程 
	 */
	@RequestMapping(value = "listFesOnlineProcesssAfterOnline", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listFesOnlineProcesssAfterOnline(@RequestBody FesOnlineProcessVO fesOnlineProcessVO) {
		
		List<FesOnlineProcessVO> fesOnlineProcesss = fesOnlineProcessService.getComplexFesOnlineProcesssAfterOnline(fesOnlineProcessVO);
		return new JsonResult(true).setData(fesOnlineProcesss);
	}
	
	/**
	 * 更新 上线流程  状态
	 * @param fesOnlineProcess  [id, onlineProcessStatus]
	 * @param sessionUser
	 */
	@RequestMapping(value = "updateFesOnlineProcessStatus", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult updateFesOnlineProcessStatus(@RequestBody FesOnlineProcessVO fesOnlineProcess, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		JsonResult json = fesOnlineProcessService.updateFesOnlineProcessStatus(fesOnlineProcess, sessionUser);
		return json;
	}
	
	/**
	 * 申请开卡 驳回操作
	 * 
	 * @param fesOnlineProcess  [id, onlineProcessStatus]
	 * @param sessionUser
	 */
	@RequestMapping(value = "updateFesOnlineProcessStatusForReject", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult updateFesOnlineProcessStatusForReject(@RequestBody FesOnlineProcessVO fesOnlineProcess, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		JsonResult json = fesOnlineProcessService.updateFesOnlineProcessStatusForReject(fesOnlineProcess, sessionUser);
		return json;
	}

	@Resource
	private SendMessageVoid sendMessageVoid;
	/**
	 * 更新 第十步上线流程  状态 并发送邮件 
	 * @param fesOnlineProcess  [id, onlineProcessStatus]
	 * @author sendAddress  发件人地址
	 * @author contacts   联系人邮件地址
	 * @author context 邮件正文 
	 */
	@RequestMapping(value = "updateOnlineStatusAndSendEmail", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult updateOnlineStatusAndSendEmail(@RequestBody FesOnlineProcessVO fesOnlineProcess, HttpSession session) {

		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		JsonResult jsonResult = new JsonResult(true);
		try { 
			EmailVO email = fesOnlineProcess.getEmail();
			//发送邮件， 有异常不需要回滚
			emailUtil.sendEmailAfterMerchantOnline(email);
			Integer mailTemplateId = email.getMailTemplateId();
			if(mailTemplateId!=null && mailTemplateId.intValue()!=0) {
				SysMailTemplateVO sysMailTemplateById = this.sysMailTemplateService.getSysMailTemplateById(mailTemplateId);
				if("1".equals(sysMailTemplateById.getIsSendSMS())){//发送短息
					sendMessageVoid.sendMessage(email.getSmsContent(), sessionUser.getTel(),  LogFactory.getLog("sms"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setFlag(false).setMessage("发送邮件失败！");
			LOG.info("发送邮件失败！"+e);
			e.printStackTrace();
			return jsonResult;
		}
		jsonResult = fesOnlineProcessService.updateFesOnlineProcessStatus(fesOnlineProcess, sessionUser);
		return jsonResult;
	}
	
	/**
	 * 发送邮件 
	 */
	@RequestMapping(value = "sendEmail", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	@Deprecated  //not user
	public JsonResult sendEmail(@RequestBody EmailVO email) {
		JsonResult jsonResult = new JsonResult(true);
		try {
			//发送邮件， 有异常不需要回滚
			boolean flag = emailUtil.sendEmailAfterMerchantOnline(email);
			jsonResult.setFlag(true).setMessage("发送成功");
		} catch (Exception e) {
			jsonResult.setFlag(false).setMessage("发送失败"+e);
			e.printStackTrace();
		}
		return jsonResult;
	}
	@Resource BesMonthlyCheckService besMonthlyCheckService;
	/**
	 * 发送月报
	 */
	@RequestMapping(value = "sendMonthlyReprot", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult sendMonthlyReprot(@RequestBody EmailVO email, HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUserVO sessionUser = (SysUserVO) session.getAttribute(Constant.SESSION_USER);
		JsonResult jsonResult = new JsonResult(true);
		try {
			//根据商户校验，八大项填写未完成，则给提示、且不发邮件；
			boolean isCheckup = besMonthlyCheckService.monthlyReportUncompleted(email.getMerchantId());
			if (!isCheckup) {
				return new JsonResult(false, "月报检查八大项包含未完成的，不能发送邮件！");
			}
			//保存已发送月报信息 
			boolean saveSendMonthlyRpt = besMonthlyReportService.saveSendMonthlyRpt(email.getMerchantId(), sessionUser);
			if (!saveSendMonthlyRpt) {
				return new JsonResult(false, "请先创建商户该月的月报讲解工作计划，再发送月报!");
			}

			//发送邮件， 有异常不需要回滚
			emailUtil.sendEmailAfterMerchantOnline(email);
			Integer mailTemplateId = email.getMailTemplateId();
			if (mailTemplateId != null && mailTemplateId.intValue() != 0) {
				SysMailTemplateVO sysMailTemplateById = this.sysMailTemplateService.getSysMailTemplateById(mailTemplateId);
				if ("1".equals(sysMailTemplateById.getIsSendSMS())) {//发送短息
					sendMessageVoid.sendMessage(email.getSmsContent(), sessionUser.getTel(), LogFactory.getLog("sms"));
				}
			}

			//发送月报短信
			String appRoot = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			this.sysSmsTemplateService.sendMonthlySMS(email.getMerchantId(), SysSmsTemplateVO.TPL_TYPE.MONTHLY, appRoot, DateUtils.addMonths(new Date(), -1));

			if (saveSendMonthlyRpt) {
				return new JsonResult(true, "发送成功");
			}
		} catch (Exception e) {
			jsonResult.setFlag(false).setMessage("发送失败");
			LOG.info("发送失败！" + e);
			e.printStackTrace();
			return jsonResult;
		}
		return jsonResult;
	}
	
	/**
	 * 查找邮件模板
	 * @mailTemplateType 001 上线完成时邮件模板  002 月报模板
	 */
	@RequestMapping(value = "getEmailTemplates", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getEmailTemplates(@RequestBody SysMailTemplateVO sysMailTemplate) {
		PageHelper.startPage(1, 1000, true);
		Page<SysMailTemplateVO> sysMailTemplates = sysMailTemplateService.getSysMailTemplates(sysMailTemplate);
		JsonResult result = new JsonResult(true);
		result.putData("rows", sysMailTemplates);
		result.putData("totalSize", sysMailTemplates.getTotal());
		return result;
	}
    /*********************************************************************
     *                   每一个上线前步骤对应的接口                               *
     **********************************************************************/
	/**
	 * @Description 上传方案洽谈，实体卡制作卡样文件等对应的接口
	 * @param myfile
	 * @param typeId: 为了区分
	 *  1.流程6实体卡制作中的 卡样源文件和卡样确认单 依次为 1,2,3……
	 *  2.流程7物料设计中的 物料设计源文件和物料设计缩略图 依次为 6,7,8,9,10,11
	 * @param request
	 */
	@RequestMapping(value = "uploadFiles", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult uploadFiles(@RequestParam MultipartFile myfile, Integer processId, 
			@RequestParam (required = false) Integer typeId, Map<String, String> fileMap, HttpServletRequest request) throws IOException {
		
		String realPath = request.getSession().getServletContext().getRealPath("/");
		//在拦截器中调用request存储sessionUser
		SysUserVO sessionUser = (SysUserVO)request.getAttribute(Constant.SESSION_USER);//falsh 方式上传
		if (sessionUser == null) {
			sessionUser = (SysUserVO) request.getSession().getAttribute(Constant.SESSION_USER);
		}
		return fesOnlineProcessService.saveUploadFiles(myfile, processId, typeId, realPath, null, sessionUser);
	}
	
	/**
	 * 保存设计需求单附件
	 * @param myfile
	 * @param materialId
	 */
	@RequestMapping(value = "uploadFesmaterialAttr", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult uploadFesmaterialAttr(@RequestParam MultipartFile myfile, 
				 HttpServletRequest request) throws IOException {
		
		String realPath = request.getSession().getServletContext().getRealPath("/");
		//在拦截器中调用request存储sessionUser
		SysUserVO sessionUser = (SysUserVO)request.getAttribute(Constant.SESSION_USER);//falsh 方式上传
		if (sessionUser == null) {
			sessionUser = (SysUserVO) request.getSession().getAttribute(Constant.SESSION_USER);
		}
		return fesMaterialRequestService.uploadFesmaterialAttr(myfile, realPath, null, sessionUser);
	}
	/**
	 * @Description 点击确定按钮的时候对应的接口，步骤物料中用到
	 * @param myfile null
	 * @param fileMap  方法saveUploadFilesForReview 返回的临时文件信息
	 * @param typeId: 为了区分
	 *  1.流程6实体卡制作中的 卡样源文件和卡样确认单 依次为 1,2,3……
	 *  2.流程7物料设计中的 物料设计源文件和物料设计缩略图 依次为 6,7,8,9,10,11,13
	 */
	@RequestMapping(value = "uploadFilesForCommit", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult uploadFilesForCommit(@RequestBody Map<String, Object> fileMap, HttpServletRequest request) throws IOException {
		
		Integer processId = NumberUtils.toInt(fileMap.get("processId").toString());
		Integer typeId = NumberUtils.toInt(fileMap.get("typeId").toString());
		/*//为了兼容老数据和配合前端重构, 需要手动转换'物料设计缩略图'的状态值
		Map<Integer, Integer> typeConvertor = new HashMap<Integer, Integer>() {
			{	
				put(2, 7);
				put(3, 8);
				put(4, 9);
				put(5, 10);
				put(6, 11);
				put(7, 13);
			}
		};
		Map<Integer, Integer> revertTypeConvertor = new HashMap<Integer, Integer>() {
			{	
				put( 7 ,2 );
				put( 8 ,3 );
				put( 9 ,4 );
				put( 10,5 );
				put( 11,6);
				put( 13,7);
			}
		};
		if(typeConvertor.containsKey(typeId)){
			typeId = typeConvertor.get(typeId);
		}*/
		 
		String realPath = request.getSession().getServletContext().getRealPath("/");
		//在拦截器中调用request存储sessionUser
		SysUserVO sessionUser = (SysUserVO)request.getAttribute(Constant.SESSION_USER);//falsh 方式上传
		if (sessionUser == null) {
			sessionUser = (SysUserVO) request.getSession().getAttribute(Constant.SESSION_USER);
		}
	    JsonResult fileInfo = fesOnlineProcessService.saveUploadFiles(null, processId, typeId, realPath, fileMap, sessionUser);
		/*Map<String, Object> file = (Map<String, Object>)fileInfo.getData();
		Object object = file.get("processAttachmentType");
		if(object!=null){
			Integer processAttachmentType = NumberUtils.toInt(object.toString());
			file.put("processAttachmentType", (Integer)revertTypeConvertor.get(processAttachmentType));
		}
		fileInfo.setData(file);*/
		return fileInfo;
	}
	
	/**
	 * 物料设计的时候上传临时缩略图, 也可作为通用的预览文件接口
	 */
	@RequestMapping(value = "uploadTempFiles", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult uploadTempFiles(@RequestParam MultipartFile myfile, HttpServletRequest request) throws IOException {
		String basePath = request.getSession().getServletContext().getRealPath("/");
		return fesOnlineProcessService.saveUploadFilesForReview(myfile, basePath);
	}
	
	/**
	 * 删除 附件 
	 */
	@RequestMapping(value = "deleteAttachment/{processId}/{attachmentId}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteAttachment(@PathVariable int processId, @PathVariable int attachmentId, HttpSession session) {
		String prefixPath = session.getServletContext().getRealPath("/");
		//删除关联表fes_process_attachement 和 sys_attachment
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		JsonResult jsonResult = this.fesOnlineProcessService.deleteSysAttachmentById(processId, attachmentId, prefixPath, sessionUser);
		return jsonResult.setMessage(jsonResult.getFlag() > 0 ? "删除成功!" : "删除失败!");
	}
	
	/**
	 * 文件下载通用接口
	 * @param relPath: 文件相对路径从表里边取， 与文件名拼接
	 */
	@RequestMapping(value = "download", method = { RequestMethod.POST, RequestMethod.GET })
	public void download(@RequestParam(required=true) String relPath, 
				HttpServletResponse response, HttpServletRequest request) throws IOException {  
		String prefixPath = request.getSession().getServletContext().getRealPath("/");
        String fileFullPath = prefixPath+relPath;
		FileSystemResource resource = new FileSystemResource(fileFullPath);  
		if(!resource.exists()){
			throw new FesBizException("文件不存在！");
		}else{
			OutputStream os = response.getOutputStream();  
			try {  
				response.reset();  
				String fileName = resource.getFilename();
				//user real filename as download file name.
			    fileName = this.sysAttachmentService.getOriginalFileName(fileName);
//			    fileName = new String(fileName.getBytes("UTF-8"));
			    
			    String agent = request.getHeader("USER-AGENT");
				if (null != agent && -1 != agent.indexOf("MSIE")) { // IE
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				} else if (null != agent && -1 != agent.indexOf("Mozilla")) { // Firefox
					fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
				} else {
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				}
			    
				response.setHeader("Content-Disposition", "attachment; filename="+fileName); 
//				response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''"+fileName); 
				response.setContentType("application/octet-stream; charset=utf-8");  
//				os.write(FileUtils.readFileToByteArray(new File(fileFullPath)));
				IOUtils.copyLarge(FileUtils.openInputStream(new File(fileFullPath)), os);
				os.flush();  
			} finally {  
				if (os != null) {  
					os.close();  
				}  
			}  
		}
	}  
	
	/**
	 * 保存或修改客服商户关系
	 */
	@RequestMapping(value = "saveSysAssistantMerchant", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveSysAssistantMerchant(@RequestBody SysAssistantMerchantVO sysAssistantMerchant, HttpServletRequest request) {
		SysUserVO sessionUser = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		int row = sysAssistantMerchantService.saveOrUpdateSysAssistantMerchant(sysAssistantMerchant,sessionUser );
		return new JsonResult(row>0).setMessage(row>0? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 保存后台设置内容
	 * @param {"processId":1, "remark": "test"}
	 */
	@RequestMapping(value = "saveBackGroundContent", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveBackGroundContent(@RequestBody FesOnlineProcessVO fesOnlineProcessVO, HttpServletRequest request) {
		SysUserVO sessionUser = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		int row = this.fesOnlineProcessService.saveBackGroundContent(fesOnlineProcessVO, sessionUser);
		return new JsonResult(row>0).setMessage(row>0? "保存成功!" : "保存失败!");
	}
	/**
	 * 保存后台设置内容
	 * @param {"processId":1, "id": 1}  //id 为流水ID
	 */
	@RequestMapping(value = "deleteBackGroundContent", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult deleteBackGroundContent(@RequestBody FesOnlineProcessVO fesOnlineProcessVO, HttpServletRequest request) {
		SysUserVO sessionUser = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		int row = this.fesOnlineProcessService.deleteBackGroundContent(fesOnlineProcessVO, sessionUser);
		return new JsonResult("02",row>0);
	}

	/**
	 * 保存修改培训计划
	 */
	@RequestMapping(value = "saveFesTrainPlan", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveFesTrainPlan(@RequestBody FesTrainPlanVO fesTrainPlan, HttpServletRequest request) {
		SysUserVO sessionUser = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		FesTrainPlanVO fesTrainPlanVO = fesTrainPlanService.saveFesTrainPlan(fesTrainPlan, sessionUser);
		return new JsonResult(fesTrainPlanVO!=null).setData(fesTrainPlanVO).setMessage(fesTrainPlanVO!=null ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 删除 培训计划 
	 */
	@RequestMapping(value = "deleteFesTrainPlan/{id}", method = { RequestMethod.GET })		
	@ResponseBody
	public JsonResult deleteFesTrainPlan(@PathVariable int id) {
		int count = fesTrainPlanService.deleteFesTrainPlanById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
	
	/**
	 * 查询开卡申请单
	 */
	@RequestMapping(value = "getFesOpenCardApplicationsAndDtls", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult getFesOpenCardApplicationsAndDtls(@RequestBody FesOpenCardApplicationVO fesOpenCardApplication){
		List<FesOpenCardApplicationVO> fesOpenCardApplications = 
				this.fesonOpenCardApplicationService.getFesOpenCardApplicationsAndDtls(fesOpenCardApplication);
		//会员等级数据字典  2014-11-21 17:49:21 改为由前端 处理
//		List<Map<String, Object>> querySysDictionaryByTypeStd = this.sysDictionaryService.querySysDictionaryByTypeStd("00000114");
//		return new JsonResult(true).setData(new Object[]{fesOpenCardApplications,querySysDictionaryByTypeStd });
		return new JsonResult(true).setData(fesOpenCardApplications);
	}
	
	
	/**
	 * 保存修改设计需求单
	 */
	@RequestMapping(value = "saveFesMaterialRequestAndDtls", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveFesMaterialRequestAndDtls(@RequestBody FesMaterialRequestVO fesMaterialRequest, HttpServletRequest request) {

		SysUserVO sessionUser = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		FesOnlineProcessVO fesOnlineProcessVO = fesMaterialRequestService.saveFesMaterialRequestAndDtls(fesMaterialRequest, sessionUser);
		return new JsonResult(fesOnlineProcessVO!=null).setData(fesOnlineProcessVO);
	}
	/**
	 * 查询修改设计需求单
	 */
	@RequestMapping(value = "getFesMaterialRequestById", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getFesMaterialRequestById(@RequestBody FesMaterialRequestVO fesMaterialRequest) {
		FesMaterialRequestVO fesOnlineProcessVO = fesMaterialRequestService.getFesMaterialRequestById(fesMaterialRequest.getId());
		return new JsonResult(fesOnlineProcessVO!=null).setData(fesOnlineProcessVO);
	}
	@Resource FesMaterialRequestDtlService fesMaterialRequestDtlService;
	/**
	 * 查询设计需求单选择类别[不包含类型为'无'的]
	 */
	@RequestMapping(value = "getFesMaterialTypeById", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getFesMaterialTypeById(@RequestBody FesMaterialRequestVO fesMaterialRequest) {
		final Integer id = fesMaterialRequest.getId();
		final List<FesMaterialRequestDtlVO> list =
			fesMaterialRequestDtlService.getFesMaterialRequestDtls(new FesMaterialRequestDtlVO(){{setMaterialRequestId(id);}});
		
		List<Map<String, Object>> dics = this.sysDictionaryService.querySysDictionaryByTypeStd("00000086");
		CollectionUtils.filter(dics, new Predicate() {
			public boolean evaluate(Object object) {
				Map<String, Object> map = (Map<String, Object>)object;
				for (FesMaterialRequestDtlVO fesMaterialRequestDtlVO : list) {
					if(map.get("value").toString().equals(fesMaterialRequestDtlVO.getMaterialRequestType())){
						if(fesMaterialRequestDtlVO.getSpecificationType().equals("1")){//过滤掉选项为无的
							return false; // If the predicate returns false, remove the element. 
						}
					}
				}
				return true;
			}
		});
		return new JsonResult(dics, true);
	}
	
	/**
	 * 公用接口，计算n个工作日之后的日期
	 * @param date: 从哪一天开始算起
	 * @param days: 多少天以后
	 */
	@RequestMapping(value = "afterDays", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult afterDays(@RequestParam String date, @RequestParam Integer days) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(format.parse(date));
		Date resultDate = this.calendarService.afterDays(calendar, days);
		return new JsonResult(resultDate != null).setData(resultDate);
	}
	
}
