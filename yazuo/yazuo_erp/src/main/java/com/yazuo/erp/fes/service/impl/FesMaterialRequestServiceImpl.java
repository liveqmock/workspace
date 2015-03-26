/**
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
import java.util.*;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;
import com.yazuo.erp.fes.exception.FesBizException;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.fes.service.FesMaterialRequestService;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesProcessAttachmentService;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysDictionaryVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class FesMaterialRequestServiceImpl implements FesMaterialRequestService {
	
	@Resource private FesMaterialRequestDao fesMaterialRequestDao;
	@Resource private FesMaterialRequestDtlDao fesMaterialRequestDtlDao;
	@Resource private FesProcessAttachmentDao fesProcessAttachmentDao;
	@Resource private FesOnlineProcessService fesOnlineProcessService;
	@Resource private SysDictionaryService sysDictionaryService;
	
	/**
	 * 保存或者修改
	 */
	@Override
	public int saveOrUpdateFesMaterialRequest (FesMaterialRequestVO fesMaterialRequest, Integer userId) {
		if(fesMaterialRequest.getId()!=null){
			fesMaterialRequest.setUpdateBy(userId);
			fesMaterialRequest.setUpdateTime(new Date());
			fesMaterialRequest.setIsEnable("1");
			fesMaterialRequestDao.updateFesMaterialRequest(fesMaterialRequest);
		}else{
			fesMaterialRequest.setInsertBy(userId);
			fesMaterialRequest.setInsertTime(new Date());
			fesMaterialRequest.setUpdateBy(userId);
			fesMaterialRequest.setUpdateTime(new Date());
			fesMaterialRequest.setIsEnable("1");
		    fesMaterialRequestDao.saveFesMaterialRequest(fesMaterialRequest);
		}
		return 1;
	}
	
	/**
	 * 保存物料需求单和明细
	 */
	@Override
	public FesOnlineProcessVO saveFesMaterialRequestAndDtls(FesMaterialRequestVO fesMaterialRequest, SysUserVO sessionUser) {
		Integer requestId = fesMaterialRequest.getId();
		if(requestId!=null){
			List<FesMaterialRequestDtlVO> fesMaterialRequestDtlVOs = fesMaterialRequest.getFesMaterialRequestDtlVOs();
			for (FesMaterialRequestDtlVO fesMaterialRequestDtlVO : fesMaterialRequestDtlVOs) {
				this.fesMaterialRequestDtlDao.deleteFesMaterialRequestDtlById(fesMaterialRequestDtlVO.getId());
			}
			this.deleteFesMaterialRequestById(requestId);
		}
		//添加
		Integer userId = sessionUser.getId();
		fesMaterialRequest.setInsertBy(userId);
		fesMaterialRequest.setInsertTime(new Date());
		fesMaterialRequest.setUpdateBy(userId);
		fesMaterialRequest.setUpdateTime(new Date());
		fesMaterialRequest.setIsEnable("1");
		fesMaterialRequest.setRemark(fesMaterialRequest.getDescription());
		this.saveFesMaterialRequest(fesMaterialRequest);
		List<FesMaterialRequestDtlVO> fesMaterialRequestDtlVOs = fesMaterialRequest.getFesMaterialRequestDtlVOs();
		for (FesMaterialRequestDtlVO fesMaterialRequestDtlVO : fesMaterialRequestDtlVOs) {
			fesMaterialRequestDtlVO.setMaterialRequestId(fesMaterialRequest.getId());
			fesMaterialRequestDtlVO.setRemark(fesMaterialRequestDtlVO.getDescription());
			fesMaterialRequestDtlVO.setInsertBy(userId);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(FesMaterialRequestDtlVO.COLUMN_MATERIAL_REQUEST_ID, Constant.NOT_NULL);
		map.put(FesMaterialRequestDtlVO.COLUMN_MATERIAL_REQUEST_TYPE, Constant.NOT_NULL);
		map.put(FesMaterialRequestDtlVO.COLUMN_SPECIFICATION_TYPE, Constant.NOT_NULL);
		map.put(FesMaterialRequestDtlVO.COLUMN_INSERT_BY, Constant.NOT_NULL);
		map.put(FesMaterialRequestDtlVO.COLUMN_INSERT_TIME, Constant.NOT_NULL);
		map.put(FesMaterialRequestDtlVO.COLUMN_REMARK, Constant.NOT_NULL);
		map.put("list", fesMaterialRequestDtlVOs);
		this.fesMaterialRequestDtlDao.batchInsertFesMaterialRequestDtls(map);
		/*
		 * 变更状态
		 */
		Integer processId = fesMaterialRequest.getProcessId();
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setId(processId);
		fesOnlineProcessVO.setProcessId(processId);
		// 是否表单和 物料设计素材都存在
		if(this.checkHasBothRequirementAndAttachment(fesOnlineProcessVO)){
			FesOnlineProcessVO fesOnlineProcessById = this.fesOnlineProcessService.getFesOnlineProcessById(processId);
			if(fesOnlineProcessById.getOnlineProcessStatus().equals("18")){
				this.fesOnlineProcessService.updateStepProcessStatus(fesOnlineProcessVO, "19", userId);
			}
		}
		//查找新增加的信息
		return (FesOnlineProcessVO)this.fesOnlineProcessService.getComplexFesOnlineProcesss(fesOnlineProcessVO).get(0);
//		FesMaterialRequestVO complexFesMaterialRequestVO = this.getFesMaterialRequestById(fesMaterialRequest.getId());
//		return complexFesMaterialRequestVO;
	}
	/**
	 * 判断是否存在 表单和附件
	 */
	@Override
	public boolean checkHasBothRequirementAndAttachment(FesOnlineProcessVO fesOnlineProcess){
		Integer processId = fesOnlineProcess.getId();
		FesMaterialRequestVO fesMaterialRequest = new FesMaterialRequestVO();
		fesMaterialRequest.setProcessId(processId);
		List<FesMaterialRequestVO> fesMaterialRequests = this.fesMaterialRequestDao.getFesMaterialRequests(fesMaterialRequest);
		
		FesProcessAttachmentVO fesProcessAttachment = new FesProcessAttachmentVO();
		fesProcessAttachment.setProcessId(processId);
		fesProcessAttachment.setProcessAttachmentTypes(new String[]{"15"});
		List<FesProcessAttachmentVO> fesProcessAttachments = this.fesProcessAttachmentDao.getFesProcessAttachments(fesProcessAttachment);
		if(fesMaterialRequests.size()>0&&fesProcessAttachments.size()>0){
			return true;
		}
		return false;
	}
	//类型和规格说明匹配
	public static final Map<String, String> typeAndSpecMatcher = new HashMap<String, String>();
	static {
		typeAndSpecMatcher.put("2", "00000087");
		typeAndSpecMatcher.put("3", "00000088");
		typeAndSpecMatcher.put("4", "00000089");
		typeAndSpecMatcher.put("5", "00000090");
		typeAndSpecMatcher.put("6", "00000091");
		typeAndSpecMatcher.put("7", "00000092");
	}
	/**
	 * 返回对象和明细
	 */
	public FesMaterialRequestVO getFesMaterialRequestById(Integer id){
		FesMaterialRequestVO fesMaterialRequestVO = fesMaterialRequestDao.getFesMaterialRequestById(id);
		fesMaterialRequestVO.setDescription(fesMaterialRequestVO.getRemark()==null? "": fesMaterialRequestVO.getRemark());
		FesMaterialRequestDtlVO fesMaterialRequestDtl = new FesMaterialRequestDtlVO();
		fesMaterialRequestDtl.setMaterialRequestId(id);
		List<FesMaterialRequestDtlVO> fesMaterisalRequestDtlVOs =
				this.fesMaterialRequestDtlDao.getFesMaterialRequestDtls(fesMaterialRequestDtl);
		for (FesMaterialRequestDtlVO fesMaterialRequestDtlVO : fesMaterisalRequestDtlVOs) {
			String materialRequestType = fesMaterialRequestDtlVO.getMaterialRequestType();
			String specificationType = fesMaterialRequestDtlVO.getSpecificationType();
			String code = typeAndSpecMatcher.get(materialRequestType);
			if(code!=null){
				SysDictionaryVO sysDictionary = 
						this.sysDictionaryService.querySysDictionaryByTypeAndKey(code, specificationType);
				String specificationText = sysDictionary.getDictionaryValue();
				fesMaterialRequestDtlVO.setSpecificationText(specificationText);
			}
			fesMaterialRequestDtlVO.setDescription(fesMaterialRequestDtlVO.getRemark()==null? "": fesMaterialRequestDtlVO.getRemark());
		}
		fesMaterialRequestVO.setFesMaterialRequestDtlVOs(fesMaterisalRequestDtlVOs);
		//查询附件相关信息
		Integer attachmentId = fesMaterialRequestVO.getAttachmentId();
		if(attachmentId!=null && attachmentId.intValue()!=0){
			fesMaterialRequestVO.setSysAttachmentVO(this.sysAttachmentService.getSysAttachmentById(attachmentId));
		}else{
			fesMaterialRequestVO.setSysAttachmentVO(new SysAttachmentVO());
		}
		return fesMaterialRequestVO;
	}
	public int saveFesMaterialRequest (FesMaterialRequestVO fesMaterialRequest) {
		return fesMaterialRequestDao.saveFesMaterialRequest(fesMaterialRequest);
	}
	public int batchInsertFesMaterialRequests (Map<String, Object> map){
		return fesMaterialRequestDao.batchInsertFesMaterialRequests(map);
	}
	public int updateFesMaterialRequest (FesMaterialRequestVO fesMaterialRequest){
		return fesMaterialRequestDao.updateFesMaterialRequest(fesMaterialRequest);
	}
	public int batchUpdateFesMaterialRequestsToDiffVals (Map<String, Object> map){
		return fesMaterialRequestDao.batchUpdateFesMaterialRequestsToDiffVals(map);
	}
	public int batchUpdateFesMaterialRequestsToSameVals (Map<String, Object> map){
		return fesMaterialRequestDao.batchUpdateFesMaterialRequestsToSameVals(map);
	}
	public int deleteFesMaterialRequestById (Integer id){
		return fesMaterialRequestDao.deleteFesMaterialRequestById(id);
	}
	public int batchDeleteFesMaterialRequestByIds (List<Integer> ids){
		return fesMaterialRequestDao.batchDeleteFesMaterialRequestByIds(ids);
	}
	public List<FesMaterialRequestVO> getFesMaterialRequests (FesMaterialRequestVO fesMaterialRequest){
		return fesMaterialRequestDao.getFesMaterialRequests(fesMaterialRequest);
	}
	public List<Map<String, Object>>  getFesMaterialRequestsMap (FesMaterialRequestVO fesMaterialRequest){
		return fesMaterialRequestDao.getFesMaterialRequestsMap(fesMaterialRequest);
	}

	@Value("${materialsDesignFilePath}")
	private String materialsDesignFilePath;
	@Override
	public JsonResult uploadFesmaterialAttr(MultipartFile myfile, String realPath, Object object,
			SysUserVO sessionUser) {
//		FesMaterialRequestVO fesMaterialRequestById = this.getFesMaterialRequestById(materialId);
		JsonResult fileInfo = null;
		try {
			// 文件路径加入到map中: note: 为了解决static 类型不能用@value的问题
			fileInfo = FileUploaderUtil.uploadFile(myfile, realPath + materialsDesignFilePath, null, 0L);
			Map<String, Object> file = (Map<String, Object>) fileInfo.getData();
			file.put("fileFullPath", materialsDesignFilePath + "/"+file.get("attachmentName"));
		} catch (IOException e) {
			throw new FesBizException("文件读写错误" + e);
		}

		if (fileInfo.getFlag() == 1) {// 上传成功
			return new JsonResult(this.saveAttachement( sessionUser.getId(), fileInfo), true);
		}
		return null;
	}
	@Resource SysAttachmentService sysAttachmentService;
	private SysAttachmentVO saveAttachement( Integer userId, JsonResult fileInfo) {
		Map<String, Object> file = (Map<String, Object>)fileInfo.getData();
		//上传附件每次保存一个附件，对应表中插入一条记录
		SysAttachmentVO sysAttachment = new SysAttachmentVO();	
		sysAttachment.setAttachmentName(file.get("attachmentName").toString());
		sysAttachment.setOriginalFileName(file.get("originalFileName").toString());
		sysAttachment.setAttachmentType("3");// 文件类型
		sysAttachment.setAttachmentSuffix(file.get("fileSuffix").toString());
		sysAttachment.setModuleType(Constant._FES);
		sysAttachment.setIsEnable(Constant.IS_ENABLE);
		sysAttachment.setIsDownloadable("1");
		Object fileSizeObject = file.get("fileSize");
		if(fileSizeObject instanceof Long){
			Long fileSize = (Long)fileSizeObject;
			sysAttachment.setAttachmentSize(Long.toString(fileSize));
		}else{
			Integer fileSize = (Integer)fileSizeObject;
			sysAttachment.setAttachmentSize(fileSize.toString());
		}
		Object attachmentPath = file.get("attachmentPath");
		sysAttachment.setAttachmentPath(attachmentPath==null? null: attachmentPath.toString());
		sysAttachment.setInsertBy(userId);
		sysAttachment.setUpdateBy(userId);
		sysAttachment.setInsertTime(new Date());
		sysAttachment.setUpdateTime(new Date());
		this.sysAttachmentService.saveSysAttachment(sysAttachment);
		return sysAttachment;
	}
}
