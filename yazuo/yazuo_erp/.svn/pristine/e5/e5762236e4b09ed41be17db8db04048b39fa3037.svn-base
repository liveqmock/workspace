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

package com.yazuo.erp.fes.service.impl;

import java.io.IOException;
import java.util.*;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.PathConfig;
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

import com.yazuo.erp.fes.service.FesOpenCardApplicationDtlService;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class FesOpenCardApplicationDtlServiceImpl implements FesOpenCardApplicationDtlService {
	
	@Resource
	private FesOpenCardApplicationDtlDao fesOpenCardApplicationDtlDao;
	
	public int saveFesOpenCardApplicationDtl (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl) {
		return fesOpenCardApplicationDtlDao.saveFesOpenCardApplicationDtl(fesOpenCardApplicationDtl);
	}
	public int batchInsertFesOpenCardApplicationDtls (Map<String, Object> map){
		return fesOpenCardApplicationDtlDao.batchInsertFesOpenCardApplicationDtls(map);
	}
	public int updateFesOpenCardApplicationDtl (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl){
		return fesOpenCardApplicationDtlDao.updateFesOpenCardApplicationDtl(fesOpenCardApplicationDtl);
	}
	public int batchUpdateFesOpenCardApplicationDtlsToDiffVals (Map<String, Object> map){
		return fesOpenCardApplicationDtlDao.batchUpdateFesOpenCardApplicationDtlsToDiffVals(map);
	}
	public int batchUpdateFesOpenCardApplicationDtlsToSameVals (Map<String, Object> map){
		return fesOpenCardApplicationDtlDao.batchUpdateFesOpenCardApplicationDtlsToSameVals(map);
	}
	public int deleteFesOpenCardApplicationDtlById (Integer id){
		return fesOpenCardApplicationDtlDao.deleteFesOpenCardApplicationDtlById(id);
	}
	public int batchDeleteFesOpenCardApplicationDtlByIds (List<Integer> ids){
		return fesOpenCardApplicationDtlDao.batchDeleteFesOpenCardApplicationDtlByIds(ids);
	}
	public FesOpenCardApplicationDtlVO getFesOpenCardApplicationDtlById(Integer id){
		return fesOpenCardApplicationDtlDao.getFesOpenCardApplicationDtlById(id);
	}
	public List<FesOpenCardApplicationDtlVO> getFesOpenCardApplicationDtls (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl){
		return fesOpenCardApplicationDtlDao.getFesOpenCardApplicationDtls(fesOpenCardApplicationDtl);
	}
	public List<Map<String, Object>>  getFesOpenCardApplicationDtlsMap (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl){
		return fesOpenCardApplicationDtlDao.getFesOpenCardApplicationDtlsMap(fesOpenCardApplicationDtl);
	}
	

	@Override
	public JsonResult uploadOpenCardAttachment( MultipartFile myfile, String realPath, Object object,
			SysUserVO sessionUser) {
		JsonResult fileInfo = null;
		try {
			String materialsDesignFilePath = PathConfig.APPLY_CARD_PATH;
			fileInfo = FileUploaderUtil.uploadFile(myfile, realPath + materialsDesignFilePath, null, 0L);
			Map<String, Object> file = (Map<String, Object>) fileInfo.getData();
			file.put("fileFullPath", materialsDesignFilePath + "/"+file.get("attachmentName"));
		} catch (IOException e) {
			throw new FesBizException("文件读写错误" + e);
		}
		if (fileInfo.getFlag() == 1) {// 上传成功
			//保存附件
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
		sysAttachment.setAttachmentType("4");// 文件类型
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
		sysAttachment.setFileFullPath(file.get("fileFullPath").toString());
		this.sysAttachmentService.saveSysAttachment(sysAttachment);
		return sysAttachment;
	}
}
