/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.*;

import bsh.StringUtil;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl;
import com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.StepNum;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.fes.vo.FesOnlineProgramVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;
import com.yazuo.erp.system.exception.SystemBizException;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.testng.Assert;

import com.yazuo.erp.system.service.SysDocumentService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.util.BeanUtils;

@Service
public class SysDocumentServiceImpl implements SysDocumentService {

	@Resource
	private SysDocumentDao sysDocumentDao;

	@Resource
	private SysAttachmentDao sysAttachmentDao;

	@Resource
	private SysQuestionDao sysQuestionDao;

	@Resource
	private SysDocumentDtlDao sysDocumentDtlDao;

	@Resource
	private SysDocumentDtlOptionDao sysDocumentDtlOptionDao;
	
	@Resource private FesOnlineProcessService fesOnlineProcessService;
	@Resource private FesOnlineProgramService fesOnlineProgramService;
	@Resource private SysOperationLogService sysOperationLogService;

	/**
	 * 培训文件的路径
	 */
	@Value("${trainFilePath}")
	private String trainFilePath;
	
	/**
	 * 上传的填单相关附件(真正用的文件)
	 */
	@Value("${documentFilePath}")
	private String documentFilePath;

	/**
	 * 上传的填单相关附件(临时文件)
	 */
	@Value("${documentFileTempPath}")
	private String documentFileTempPath;

	private static final Log LOG = LogFactory.getLog(SysDocumentServiceImpl.class);
	/**
	 * @Description 上传填单附件
	 */
	@Override
	@Deprecated  
	public Object uploadFiles(MultipartFile[] myfiles, HttpServletRequest request) {
//		return FileUploaderUtil.uploadFile(myfiles, documentFileTempPath, null, 0, request);
		String basePath = request.getSession().getServletContext().getRealPath("/");
		try {
			return FileUploaderUtil.uploadFile(myfiles[0], basePath+"/"+documentFileTempPath, null, 0l);
		} catch (IOException e) {
			throw new SystemBizException("上传文件错误");
		}
	}
	/**
	 * @Description 真正用到的上传填单附件方法
	 */
	public Object uploadFiles(MultipartFile[] myfiles, HttpServletRequest request, String modlePath) {
//		return FileUploaderUtil.uploadFile(myfiles, documentFileTempPath, null, 0, request);
		String basePath = request.getSession().getServletContext().getRealPath("/");
		try {
			return FileUploaderUtil.uploadFile(myfiles[0], basePath+"/"+modlePath, null, 0l);
		} catch (IOException e) {
			throw new SystemBizException("上传文件错误");
		}
	}
	
	/**
	 * @Description 保存多个填单信息
	 */
	@Override
	public int saveSysDocuments(SysDocumentVO[] sysDocuments, HttpServletRequest request, SysUserVO user) {
		for (SysDocumentVO sysDocument : sysDocuments) {
			this.saveSysDocument(sysDocument, request, user);
		}
		return 1;
	}
	
	/**
	 * @Title saveSysDocument
	 * @Description 保存填单信息
	 * @param sysDocument
	 * @param request
	 * @param user
	 * @return
	 * @see com.yazuo.erp.system.service.SysDocumentService#saveSysDocument(com.yazuo.erp.system.vo.SysDocumentVO,
	 *      javax.servlet.http.HttpServletRequest,
	 *      com.yazuo.erp.system.vo.SysUserVO)
	 */
	public int saveSysDocument(SysDocumentVO sysDocument, HttpServletRequest request, SysUserVO user) {
		Integer userId = user.getId();

		// 添加附件信息
		SysAttachmentVO sysAttachment = this.saveSysAttachment(sysDocument, request, userId);

		// 添加填单信息
		this.saveSysDocument(sysDocument, userId, sysAttachment);
		Integer documentId = sysDocument.getId();

		List<SysDocumentDtlVO> sysDocumentDtlList = sysDocument.getSysDocumentDtlList();
		if(sysDocumentDtlList!=null&&sysDocumentDtlList.size()>0){  //只有修改文件上传的时候存在填单明细为空
			// 添加填单明细信息和填单明细选项
			this.saveSysDocumentDtlAndOption(sysDocument, userId, documentId);
		}

		return 1;
	}
	
	/**
	 * 判断是否实施培训回访和录音同时存在，存在返回状态"3"
	 */
	@Override
	public String getStatusForSysDocument(Integer merchantId, String docummentType) {
		String flag = "4";// 无录音，无内容
		List<SysDocumentVO> sysDocuments = this.getDocumentByMerchantAndType(merchantId, docummentType);
		if(sysDocuments!=null&&sysDocuments.size()>0){
			Assert.assertEquals(sysDocuments.size(), 1, "通过商户id和文档类型查询到的个数错误");
			SysDocumentVO sysDocumentDB = sysDocuments.get(0); //查询到了填单信息
			Integer attachmentId = sysDocumentDB.getAttachmentId();
			//查找是否存在填单明细
			SysDocumentDtlVO documentDtlVO = new SysDocumentDtlVO();
			documentDtlVO.setDocumentId(sysDocumentDB.getId());
			List<SysDocumentDtlVO> sysDocumentDtls = sysDocumentDtlDao.getSysDocumentDtls(documentDtlVO);
			
			if (null == attachmentId && (sysDocumentDtls != null && 1 <= sysDocumentDtls.size())) {
				flag = "1";// 无录音，有内容
			} else if (null != attachmentId && (sysDocumentDtls == null || 0 == sysDocumentDtls.size())) {
				flag = "2";// 有录音，无内容
			} else if (null != attachmentId && (sysDocumentDtls != null && 1 <= sysDocumentDtls.size())) {
				flag = "3";// 有录音，有内容
			}
		}
		
		return flag;
	}

	/**
	 * @Description 通过商户id和文档类型查找对应的 回访单
	 */
	@Override
	public List<SysDocumentVO> getDocumentByMerchantAndType(Integer merchantId, String docummentType) {
		List<SysDocumentVO> sysDocuments = new ArrayList<SysDocumentVO>();
		if(merchantId==null||StringUtils.isEmpty(docummentType)){//参数为空认为前台没有传过来或者错误， 直接返回空list
			return sysDocuments;
		}
		SysDocumentVO sysDocument = new SysDocumentVO();
		sysDocument.setMerchantId(merchantId);
		sysDocument.setDocumentType(docummentType);
		sysDocuments = this.getSysDocuments(sysDocument);
		return sysDocuments;
	}
	
	/**
	 * 上传录音并添加关联表记录
	 */
	@Override
	public JsonResult uploadRecordAndSaveSysDocumentForStep9(MultipartFile[] myfiles, 
			   SysDocumentVO sysDocument, HttpServletRequest request, SysUserVO user) {
		
		JsonResult uploadFile = (JsonResult)this.uploadFiles(myfiles, request, trainFilePath);
		@SuppressWarnings("unchecked")
		Map<String, Object> map  = (Map<String, Object>)uploadFile.getData();
		
		SysAttachmentVO sysAttachmentVO = new SysAttachmentVO();
		
		this.encaulateMapToSysAttachment(map, sysAttachmentVO);

		sysDocument.setSysAttachment(sysAttachmentVO);
		JsonResult jsonResult =  this.saveDocumentAndUpdateStatusForStep9(sysDocument, request, user, true);
		
	    return jsonResult;
	}

	/**
	 * 添加流水
	 * flag:0   上线前 第九步
	 * flag:1  上线后
	 * 
	 */
	private void addOperationLog(SysDocumentVO sysDocument, SysUserVO user, int flag) {
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		sysOperationLog.setInsertBy(user.getId());
		sysOperationLog.setMerchantId(sysDocument.getMerchantId());
		sysOperationLog.setInsertTime(new Date());
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setModuleType(Constant._FES);
		sysOperationLog.setDescription("上传了回访录音。");
		if(flag == 0){
			sysOperationLog.setFesLogType("11");
		}else{
			sysOperationLog.setFesLogType("13");
		}
		sysOperationLogService.saveSysOperationLog(sysOperationLog);
	}
	
	/**
	 * 上线后回访上传录音
	 * @flag ture: 上传录音, false: 培训回访单
	 */
	@Override
	public JsonResult uploadRecordAndSaveSysDocumentAfterOnline(MultipartFile[] myfiles, 
			SysDocumentVO[] sysDocuments, HttpServletRequest request, SysUserVO user, Boolean flag) {

		JsonResult uploadFile = (JsonResult)this.uploadFiles(myfiles, request, documentFilePath);
		@SuppressWarnings("unchecked")
		Map<String, Object> map  = (Map<String, Object>)uploadFile.getData();
		SysAttachmentVO sysAttachmentVO = new SysAttachmentVO();

		this.encaulateMapToSysAttachment(map, sysAttachmentVO);

		for (SysDocumentVO sysDocument : sysDocuments) {
			sysDocument.setSysAttachment(sysAttachmentVO);
			uploadFile = this.saveDocumentAndUpdateStatusForStep9(sysDocument, request, user, flag);//更改引用，可假定只要一个保存成功即为成功
		}
		return uploadFile;
	}

	/**
	 * @Description 封装map文件类型到 SysAttachmentVO对象
	 */
	private void encaulateMapToSysAttachment(Map<String, Object> map, SysAttachmentVO sysAttachmentVO) {
		sysAttachmentVO.setAttachmentType("2");// 音频
		sysAttachmentVO.setAttachmentSuffix(map.get("fileSuffix").toString());//音频文件
		sysAttachmentVO.setOriginalFileName(map.get("originalFileName").toString());
		sysAttachmentVO.setAttachmentName(map.get("attachmentName").toString());
		sysAttachmentVO.setAttachmentSize(map.get("fileSize").toString());
		sysAttachmentVO.setOriginalFileName(map.get("originalFileName").toString());
		sysAttachmentVO.setModuleType("fes");
		LOG.info(sysAttachmentVO);
	}
	
	
	/**
	 * 保存填单信息 , 如果录音和调研单都存在则更新状态
	 * 
	 * 第9步，新增/修改上线实施培训回访单
	 */
	@Override
	public JsonResult saveDocumentAndUpdateStatusForStep9(SysDocumentVO sysDocument,
			  HttpServletRequest request, SysUserVO user, Boolean isUploadFlag) {
		JsonResult jsonResult = new JsonResult();
		Integer merchantId = sysDocument.getMerchantId();

		//查询上线流程的状态
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setMerchantId(merchantId);
		fesOnlineProcessVO.setStepNum(StepNum.train.toString());
		FesOnlineProcessVO fesOnlineProcessDB = this.fesOnlineProcessService.getFesOnlineProcessByMerchantAndStep(fesOnlineProcessVO);
		sysDocument.setOnlineProcessStatus(fesOnlineProcessDB==null? "": fesOnlineProcessDB.getOnlineProcessStatus());
		
		Integer sysDocumentId = null;
		//能查到记录说明是更新操作
		List<SysDocumentVO> sysDocuments = this.getDocumentByMerchantAndType(merchantId, sysDocument.getDocumentType());
		if(sysDocuments.size()>0){ //修改操作
			SysDocumentVO sysDocumentVODB = sysDocuments.get(0);
			sysDocument.setId(sysDocumentVODB.getId());
			this.updateSysDocument(sysDocument, request, user);
			sysDocumentId = sysDocumentVODB.getId();
		}else{
		    sysDocumentId = this.saveSysDocument(sysDocument, request, user);
			jsonResult.setFlag(sysDocumentId>0);
		}
		//如果是第九步需要并且未上线需要更新状态
		FesOnlineProcessVO dbFesOnlineProcessVO = new FesOnlineProcessVO();
		//只要有内容则更改状态
		if(Arrays.asList("1","3").contains(this.getStatusForSysDocument(merchantId, "1"))){
			if("04"!=fesOnlineProcessDB.getOnlineProcessStatus()){
				dbFesOnlineProcessVO = this.fesOnlineProcessService.updateStepProcessStatus(fesOnlineProcessDB, "17", user.getId());//17:待回访确认
				sysDocument.setOnlineProcessStatus(dbFesOnlineProcessVO.getOnlineProcessStatus());
			}
		}
		if(isUploadFlag){//上传录音
			this.addOperationLog(sysDocument, user, 0);
		}else{
			this.addOperationLog(user, merchantId, "11");
		}
		
		sysDocument.setId(sysDocumentId);
		//添加路径信息
		this.addAttachmentPathToSysDocument(sysDocument);

		jsonResult.setFlag(true).setData(sysDocument);
		return jsonResult;
	}
	/**
	 * @param user
	 * @param merchantId
	 */
	private void addOperationLog(SysUserVO user, Integer merchantId, String fesLogType) {
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		sysOperationLog.setInsertBy(user.getId());
		sysOperationLog.setInsertTime(new Date());
		sysOperationLog.setMerchantId(merchantId);
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setModuleType(Constant._FES);
		sysOperationLog.setFesLogType(fesLogType);
		sysOperationLog.setDescription("填写了回访表单。" + " [操作人: "+user.getUserName() +"]");
		sysOperationLogService.saveSysOperationLog(sysOperationLog);
	}

	/**
	 * 添加路径信息
	 */
	@Override
	public void addAttachmentPathToSysDocument(SysDocumentVO sysDocument) {
		Integer sysDocumentId = sysDocument.getId();
		if(sysDocumentId!=null){
			SysDocumentVO sysDocumentById = this.sysDocumentDao.getSysDocumentById(sysDocumentId);
			if(sysDocumentById!=null){
				Integer attachmentId = sysDocumentById.getAttachmentId();
				SysAttachmentVO sysAttachement = this.sysAttachmentDao.getSysAttachmentById(attachmentId);
				if(sysAttachement!=null){
					String dbAttachmentPath =StringUtils.isEmpty(sysAttachement.getAttachmentPath())?"": sysAttachement.getAttachmentPath();
					String filePath = documentFilePath;
					if("1".equals(sysDocument.getDocumentType())){//培训步骤要存单独的路径
						filePath = trainFilePath;
					}
					String fileFullPath = filePath +"/"+ sysAttachement.getAttachmentName();
					sysDocument.setDocumentFilePath(fileFullPath);
					sysDocument.setProcessAttachmentType("14");
					sysDocument.setOriginalFileName(sysAttachement.getOriginalFileName());
				}
			}
		}
	}
	
	/**
	 * 保存填单信息 , 如果录音和调研单都存在则更新状态
	 * 
	 *  第10步结束以后由后端客服处理.上线后回访单
	 */
	@Override
	public JsonResult saveDocumentAndUpdateStatusAfterStep10(SysDocumentVO[] sysDocuments, HttpServletRequest request, SysUserVO user) {
		JsonResult jsonResult = null;
		Integer merchantId = null;
		for (SysDocumentVO sysDocument : sysDocuments) {
			jsonResult = this.saveDocumentAndUpdateStatusAfterStep10(sysDocument, request, user);
		    merchantId = sysDocument.getMerchantId();
		}
		//添加流水
		this.addOperationLog(user, merchantId, "32");
		return jsonResult;
	}
	/**
	 * 保存填单信息 , 如果录音和调研单都存在则更新状态
	 * 
	 *  第10步结束以后由后端客服处理.上线后回访单
	 */
	@Override
	public JsonResult saveDocumentAndUpdateStatusAfterStep10(SysDocumentVO sysDocument, HttpServletRequest request, SysUserVO user) {
		JsonResult jsonResult = new JsonResult();
		Integer merchantId = sysDocument.getMerchantId();
		//能查到记录说明是更新操作
		List<SysDocumentVO> sysDocuments = this.getDocumentByMerchantAndType(merchantId, sysDocument.getDocumentType());
		if(sysDocuments.size()>0){ //修改操作
			SysDocumentVO sysDocumentVODB = sysDocuments.get(0);
			sysDocument.setId(sysDocumentVODB.getId());
			this.updateSysDocument(sysDocument, request, user);
			jsonResult.setFlag(true).setMessage("修改成功");
		}else{
			int saveDocumentResult = this.saveSysDocument(sysDocument, request, user);
			jsonResult.setFlag(saveDocumentResult>0).setMessage("新增成功");
		}
		return jsonResult;
	}

	/**
	 * @Description 添加填单明细信息和填单明细选项
	 * @param sysDocument
	 * @param userId
	 * @param documentId
	 * @throws SystemBizException
	 * @throws
	 */
	private void saveSysDocumentDtlAndOption(SysDocumentVO sysDocument, Integer userId, Integer documentId)
			throws SystemBizException {
		List<SysDocumentDtlVO> sysDocumentDtlList = sysDocument.getSysDocumentDtlList();
		for (SysDocumentDtlVO sysDocumentDtlVO : sysDocumentDtlList) {
			// 添加填单明细信息
			this.saveSysDocumentDtl(userId, documentId, sysDocumentDtlVO);

			Integer documentDtlId = sysDocumentDtlVO.getId();
			String questionType = sysDocumentDtlVO.getQuestionType();
			if ("1".equals(questionType) || "2".equals(questionType)) {
				// 添加填单明细选项
				this.saveSysDocumentDtlOption(userId, sysDocumentDtlVO, documentDtlId);
			}
		}
	}

	/**
	 * @Description 添加填单明细选项
	 * @param userId
	 * @param sysDocumentDtlVO
	 * @param documentDtlId
	 * @throws SystemBizException
	 * @throws
	 */
	private void saveSysDocumentDtlOption(Integer userId, SysDocumentDtlVO sysDocumentDtlVO, Integer documentDtlId)
			throws SystemBizException {
		List<SysDocumentDtlOptionVO> sysDocumentDtlOptionList = sysDocumentDtlVO.getSysDocumentDtlOptionList();
		if (null == sysDocumentDtlOptionList) {
			throw new SystemBizException("填单选项为空异常");
		}
		for (SysDocumentDtlOptionVO sysDocumentDtlOptionVO : sysDocumentDtlOptionList) {
			// 选项ID
			sysDocumentDtlOptionVO.setDocumentDtlId(documentDtlId);// 填单明细ID
			// 选项内容
			// 是否选择
			// 是否开启意见框
			// 提示语
			// 意见
			sysDocumentDtlOptionVO.setRemark(null);// 备注
			sysDocumentDtlOptionVO.setUpdateBy(userId);// 最后修改人
			sysDocumentDtlOptionVO.setUpdateTime(new Date());// 最后修改时间

			int count = sysDocumentDtlOptionDao.saveSysDocumentDtlOption(sysDocumentDtlOptionVO);
			if (1 > count) {
				throw new SystemBizException("填单明细选项添加失败");
			}
		}
	}

	/**
	 * @Description 添加填单明细信息
	 * @param userId
	 * @param documentId
	 * @param sysDocumentDtlVO
	 * @throws SystemBizException
	 * @throws
	 */
	private void saveSysDocumentDtl(Integer userId, Integer documentId, SysDocumentDtlVO sysDocumentDtlVO)
			throws SystemBizException {
		Integer questionId = sysDocumentDtlVO.getQuestionId();
		SysQuestionVO sysQuestion = sysQuestionDao.getSysQuestionById(questionId);
		if (null == sysQuestion) {
			throw new SystemBizException("未查询到填单题目信息");
		}
		sysDocumentDtlVO.setDocumentId(documentId);// 填单ID
		// 题目ID
		sysDocumentDtlVO.setQuestionType(sysQuestion.getQuestionType());// 问题类型
		sysDocumentDtlVO.setTitle(sysQuestion.getTitle());// 题干
		sysDocumentDtlVO.setTip(sysQuestion.getTip());//提示语
		// 意见
		sysDocumentDtlVO.setRemark(null);// 备注
		sysDocumentDtlVO.setUpdateBy(userId);// 最后修改人
		sysDocumentDtlVO.setUpdateTime(new Date());// 最后修改时间

		// 添加填单明细信息
		int count = sysDocumentDtlDao.saveSysDocumentDtl(sysDocumentDtlVO);
		if (1 > count) {
			throw new SystemBizException("填单明细添加失败");
		}
	}

	/**
	 * @Description 添加填单信息
	 * @param sysDocument
	 * @param userId
	 * @param sysAttachment
	 * @throws SystemBizException
	 * @throws
	 */
	private int saveSysDocument(SysDocumentVO sysDocument, Integer userId, SysAttachmentVO sysAttachment)
			throws SystemBizException {
		Integer attachmentId = null;
		if (null != sysAttachment) {
			attachmentId = sysAttachment.getId();
		}
		sysDocument.setAttachmentId(attachmentId);// 附件ID
		sysDocument.setIsEnable("1");// 是否有效
		sysDocument.setRemark(null);// 备注
		sysDocument.setInsertBy(userId);// 创建人
		sysDocument.setInsertTime(new Date());// 创建时间
		sysDocument.setUpdateBy(userId);// 最后修改人
		sysDocument.setUpdateTime(new Date());// 最后修改时间
		return sysDocumentDao.saveSysDocument(sysDocument);
	}

	/**
	 * @Description 添加填单附件信息
	 * @param sysDocument
	 * @param request
	 * @param userId
	 * @return
	 * @throws SystemBizException
	 * @throws
	 */
	private SysAttachmentVO saveSysAttachment(SysDocumentVO sysDocument, HttpServletRequest request, Integer userId)
			throws SystemBizException {
		SysAttachmentVO sysAttachment = sysDocument.getSysAttachment();
		if (null != sysAttachment) {
			// 添加附件
			sysAttachment.setAttachmentPath(null);
			sysAttachment.setIsEnable("1");
			sysAttachment.setIsDownloadable("1");
			sysAttachment.setHours(null);
			sysAttachment.setRemark(null);
			sysAttachment.setInsertBy(userId);
			sysAttachment.setInsertTime(new Date());
			sysAttachment.setUpdateBy(userId);
			sysAttachment.setUpdateTime(new Date());
			int count = sysAttachmentDao.saveSysAttachment(sysAttachment);
			if (1 > count) {
				throw new SystemBizException("附件添加失败");
			}
			Integer attachmentId = sysAttachment.getId();

			// 相对路径为附件ID，更新相对路径
			sysAttachment.setAttachmentPath(String.valueOf(attachmentId));
			count = sysAttachmentDao.updateSysAttachment(sysAttachment);
			if (1 > count) {
				throw new SystemBizException("附件相对路径更新失败");
			}

			//2014-9-26 12:13:02更改不 不需要临时文件的方式
			/*// 文件从临时文件夹移动到实际位置
			String attachmentName = sysAttachment.getAttachmentName();
			this.moveFile(attachmentName, request, sysAttachment.getAttachmentPath());*/
		}
		return sysAttachment;
	}

	/**
	 * @Description 填单相关附件从临时文件夹移动到目标文件夹
	 * @param fileName
	 * @param request
	 * @return
	 * @throws
	 */
	private boolean moveFile(String fileName, HttpServletRequest request, String attachmentPath) {
		String orignPath = request.getSession().getServletContext().getRealPath(documentFileTempPath) + "/" + fileName;
		String destPath = request.getSession().getServletContext().getRealPath(documentFilePath + "/" + attachmentPath);
		File orignFile = new File(orignPath); // 源文件
		File destFile = new File(destPath); // 目标文件
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		return orignFile.renameTo(new File(destFile, orignFile.getName()));
	}

	public int batchInsertSysDocuments(Map<String, Object> map) {
		return sysDocumentDao.batchInsertSysDocuments(map);
	}

	/**
	 * @Title updateSysDocument
	 * @Description 修改填单信息
	 * @param sysDocument
	 * @param request
	 * @param user
	 * @return
	 * @see com.yazuo.erp.system.service.SysDocumentService#updateSysDocument(com.yazuo.erp.system.vo.SysDocumentVO,
	 *      javax.servlet.http.HttpServletRequest,
	 *      com.yazuo.erp.system.vo.SysUserVO)
	 */
	public int updateSysDocument(SysDocumentVO sysDocument, HttpServletRequest request, SysUserVO user) {
		Integer userId = user.getId();

		// 根据填单ID查询原填单信息
		Integer documentId = sysDocument.getId();
		SysDocumentVO oldDocument = sysDocumentDao.getSysDocumentById(documentId);
		if (null == oldDocument) {
			throw new SystemBizException("未查询到原填单信息");
		}

		if(sysDocument.getSysAttachment()!=null){
			// 修改附件信息
			Integer newAttachmentId = this.updateSysAttachment(sysDocument, request, userId, oldDocument);
			// 修改填单信息
			this.updateSysDocument(sysDocument, userId, newAttachmentId);
		}
		List<SysDocumentDtlVO> sysDocumentDtlList = sysDocument.getSysDocumentDtlList();
		if(sysDocumentDtlList!=null&&sysDocumentDtlList.size()>0){  //只有修改文件上传的时候存在填单明细为空
			// 修改填单明细信息和填单选项信息
			this.updateSysDocumentDtlAndOption(sysDocument, userId, documentId);
		}

		return 1;
	}

	/**
	 * @Description 修改填单明细信息和填单选项信息
	 * @param sysDocument
	 * @param userId
	 * @param documentId
	 * @throws SystemBizException
	 * @throws
	 */
	private void updateSysDocumentDtlAndOption(SysDocumentVO sysDocument, Integer userId, Integer documentId)
			throws SystemBizException {
		// 根据填单ID删除原有填单选项信息
		int count = sysDocumentDtlOptionDao.deleteSysDocumentDtlOptionByDocumentId(documentId);

		// 根据填单ID删除原有填单明细信息
		count = sysDocumentDtlDao.deleteSysDocumentDtlByDocumentId(documentId);

		// 添加填单明细信息和填单选项信息
		this.saveSysDocumentDtlAndOption(sysDocument, userId, documentId);
	}

	/**
	 * @Description 修改填单信息
	 * @param sysDocument
	 * @param userId
	 * @param newAttachmentId
	 * @throws SystemBizException
	 * @throws
	 */
	private void updateSysDocument(SysDocumentVO sysDocument, Integer userId, Integer newAttachmentId) throws SystemBizException {
		sysDocument.setAttachmentId(newAttachmentId); // 附件ID
		sysDocument.setInsertBy(userId); // 最后修改人
		sysDocument.setInsertTime(new Date()); // 最后修改时间
		int count = sysDocumentDao.updateSysDocument(sysDocument);
		if (1 > count) {
			throw new SystemBizException("修改填单信息失败");
		}
	}

	/**
	 * @Description 修改附件信息
	 * @param sysDocument
	 * @param request
	 * @param userId
	 * @param oldDocument
	 * @return
	 * @throws SystemBizException
	 * @throws
	 */
	private Integer updateSysAttachment(SysDocumentVO sysDocument, HttpServletRequest request, Integer userId,
			SysDocumentVO oldDocument) throws SystemBizException {
		Integer oldAttachmentId = oldDocument.getAttachmentId();// 原附件ID
		SysAttachmentVO newAttachment = sysDocument.getSysAttachment(); // 新附件信息
		SysAttachmentVO attachment = null;
		Integer newAttachmentId = oldAttachmentId;
		if (null == oldAttachmentId) {// 原附件为空
			// 如果原附件为空，新附件不为空，则添加附件
			// 如果原附件为空，新附件也为空，则无变化，不更新
			if (null != newAttachment) {// 添加新附件
				attachment = this.saveSysAttachment(sysDocument, request, userId);
				newAttachmentId = attachment.getId();
			}
		} else {// 原附件不为空
			// 根据附件ID查询附件信息
			SysAttachmentVO oldAttachment = sysAttachmentDao.getSysAttachmentById(oldAttachmentId);
			if (null == oldAttachment) {
				throw new SystemBizException("未查询到原附件信息");
			}

			// 如果原附件不为空，新附件不为空，则对比文件名称是否一致，一致时无变化，不一致则进行修改
			// 如果原附件不为空，新附件为空，则删除附件
			String oldAttachmentName = oldAttachment.getAttachmentName();
			if (null != newAttachment) {
				String newAttachmentName = newAttachment.getAttachmentName();
				if (!oldAttachmentName.equals(newAttachmentName)) {
					// 删除原附件文件
					this.deleteFile(oldAttachmentName, oldAttachment.getAttachmentPath(), request);
					// 删除原附件信息
					this.deleteSysAttachmentById(oldAttachment);
					// 添加新的附件信息
					attachment = this.saveSysAttachment(sysDocument, request, userId);
					newAttachmentId = attachment.getId();
				}
			} else {
				// 删除原附件文件
				this.deleteFile(oldAttachmentName, oldAttachment.getAttachmentPath(), request);
				// 删除原附件信息
				this.deleteSysAttachmentById(oldAttachment);
				newAttachmentId = null;
			}
		}
		return newAttachmentId;
	}

	/**
	 * @Description 删除原附件信息
	 * @param oldAttachmentId
	 * @throws SystemBizException
	 * @throws
	 */
	private void deleteSysAttachmentById(SysAttachmentVO oldAttachment) throws SystemBizException {
		//int count = sysAttachmentDao.deleteSysAttachmentById(oldAttachmentId);
		oldAttachment.setIsEnable("0");
		int count = sysAttachmentDao.updateSysAttachment(oldAttachment);
		if (1 > count) {
			throw new SystemBizException("删除原附件信息失败");
		}
	}

	/**
	 * @Description 删除填单附件
	 * @param fileName
	 * @param attachmentPath
	 * @param request
	 * @return
	 * @throws
	 */
	public boolean deleteFile(String fileName, String attachmentPath, HttpServletRequest request) {
		String destPath = request.getSession().getServletContext().getRealPath(documentFilePath) + "/" + attachmentPath;
		File destFile = new File(destPath + "/" + fileName); // 目标文件夹
		return destFile.delete();
	}

	public int batchUpdateSysDocumentsToDiffVals(Map<String, Object> map) {
		return sysDocumentDao.batchUpdateSysDocumentsToDiffVals(map);
	}

	public int batchUpdateSysDocumentsToSameVals(Map<String, Object> map) {
		return sysDocumentDao.batchUpdateSysDocumentsToSameVals(map);
	}

	public int deleteSysDocumentById(Integer id) {
		return sysDocumentDao.deleteSysDocumentById(id);
	}

	public int batchDeleteSysDocumentByIds(List<Integer> ids) {
		return sysDocumentDao.batchDeleteSysDocumentByIds(ids);
	}

	public SysDocumentVO getSysDocumentById(Integer id) {
		return sysDocumentDao.getSysDocumentById(id);
	}

	public List<SysDocumentVO> getSysDocuments(SysDocumentVO sysDocument) {
		return sysDocumentDao.getSysDocuments(sysDocument);
	}

	public List<Map<String, Object>> getSysDocumentsMap(SysDocumentVO sysDocument) {
		return sysDocumentDao.getSysDocumentsMap(sysDocument);
	}

	/**
	 * 通过商户ID查询回访单信息
	 */
	@Override
	public List<SysDocumentVO> getSysDocumentByMerchantId(SysDocumentVO sysDocument){
		Integer merchantId = sysDocument.getMerchantId();
		List<SysDocumentVO> documentByMerchantAndType2 = this.getDocumentByMerchantAndType(merchantId, "2");
		List<SysDocumentVO> documentByMerchantAndType3 = this.getDocumentByMerchantAndType(merchantId, "3");
		SysDocumentVO sysDocumentVO2 = (SysDocumentVO)documentByMerchantAndType2.get(0);
		SysDocumentVO sysDocumentVO3 = (SysDocumentVO)documentByMerchantAndType3.get(0);
		List<SysDocumentVO> listSysDocumentVO = new LinkedList<SysDocumentVO>();
		listSysDocumentVO.add(this.querySysDocumentById(sysDocumentVO2));
		listSysDocumentVO.add(this.querySysDocumentById(sysDocumentVO3));
		return listSysDocumentVO;
	}
	/**
	 * 通过商户Id 和类型查找对应的回访信息
	 */
	@Override
	public List<SysDocumentVO> getSysDocumentsByMerchantAndType(SysDocumentVO[] sysDocuments) {
		List<SysDocumentVO> sysDocumentsResult = new LinkedList<SysDocumentVO>();
		for (SysDocumentVO sysDocument : sysDocuments) {
			List<SysDocumentVO> sysDocumentsDB = sysDocumentDao.getSysDocuments(sysDocument);
			Assert.assertTrue(sysDocumentsDB.size()==1, "通过类型和商户只能得到唯一的回访单");
			SysDocumentVO sysDocumentVO = sysDocumentsDB.get(0);
			sysDocumentsResult.add(this.querySysDocumentById(sysDocumentVO));
		}
		return sysDocumentsResult;
	}

	/**
	 * @Title querySysDocumentById
	 * @Description 根据ID查询填单信息
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.system.service.SysDocumentService#querySysDocumentById(java.util.Map)
	 */
	@Override
	public SysDocumentVO querySysDocumentById(SysDocumentVO sysDocumentVO) {
		Integer documentId = sysDocumentVO.getId();
		if (null == documentId) {
			throw new SystemBizException("填单ID为空异常");
		}

		SysDocumentVO sysDocument = sysDocumentDao.getSysDocumentById(documentId);
		if (null == sysDocument) {
			throw new SystemBizException("未查询到填单信息");
		}
		Integer attachmentId = sysDocument.getAttachmentId();
		if (null != attachmentId) {
			SysAttachmentVO sysAttachment = sysAttachmentDao.getSysAttachmentById(attachmentId);
			sysDocument.setSysAttachment(sysAttachment);
			sysDocument.setDocumentFilePath(documentFilePath);
		}

		SysDocumentDtlVO sysDocumentDtl = new SysDocumentDtlVO();
		sysDocumentDtl.setDocumentId(documentId);
		List<SysDocumentDtlVO> sysDocumentDtlList = sysDocumentDtlDao.getSysDocumentDtls(sysDocumentDtl);
		for (SysDocumentDtlVO sysDocumentDtlVO : sysDocumentDtlList) {
			Integer documentDtlId = sysDocumentDtlVO.getId();

			SysDocumentDtlOptionVO option = new SysDocumentDtlOptionVO();
			option.setDocumentDtlId(documentDtlId);
			List<SysDocumentDtlOptionVO> sysDocumentDtlOptionList = sysDocumentDtlOptionDao.getSysDocumentDtlOptions(option);

			sysDocumentDtlVO.setSysDocumentDtlOptionList(sysDocumentDtlOptionList);
		}

		sysDocument.setSysDocumentDtlList(sysDocumentDtlList);
		return sysDocument;
	}

}
