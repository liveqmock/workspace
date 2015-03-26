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

package com.yazuo.erp.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.erp.fes.dao.FesProcessAttachmentDao;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.dao.SysAttachmentDao;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.util.DeviceTokenUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */

@Service
public class SysAttachmentServiceImpl implements SysAttachmentService {

	private static final Log LOG = LogFactory.getLog(SysAttachmentServiceImpl.class);
	@Resource
	private SysAttachmentDao sysAttachmentDao;

	@Value("${attachmentRoot}")
	private String attachmentRoot;

	@Resource
	private FesProcessAttachmentDao fesProcessAttachmentDao;
	
	@Override
	public String getOriginalFileName(String attachmentName){
		SysAttachmentVO sysAttachment =  new SysAttachmentVO();
		sysAttachment.setAttachmentName(attachmentName);
		List<SysAttachmentVO> list = this.getSysAttachments(sysAttachment);
		Assert.assertTrue("文件不存在", list!=null && list.size()== 1);
		SysAttachmentVO sysAttachmentDB = (SysAttachmentVO)list.get(0);
		return sysAttachmentDB.getOriginalFileName();
	}

	@Override
	public SysAttachmentVO saveAttachmentAndFile(MultipartFile file, String module, String type, String attachmentType, Integer userId, String uploadRoot) {
		try {
			String originalName = file.getOriginalFilename();
			String suffix = originalName.replaceAll("^[^.]*\\.", "");
			String newFilename = UUID.randomUUID() + "." + suffix;
			String path = this.attachmentRoot + File.separator + module + File.separator + type;
			String absoluteDir = uploadRoot + File.separator + path;
			File dir = new File(uploadRoot + File.separator + path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(absoluteDir + File.separator + newFilename));
			SysAttachmentVO attachmentVO = new SysAttachmentVO();
			attachmentVO.setOriginalFileName(originalName);
			attachmentVO.setAttachmentName(newFilename);
			attachmentVO.setAttachmentSize(String.valueOf(file.getSize()));
			attachmentVO.setAttachmentType(attachmentType);
			attachmentVO.setAttachmentSuffix(suffix.toLowerCase());
			attachmentVO.setAttachmentPath(path);
			attachmentVO.setModuleType(module);
			attachmentVO.setIsEnable("1");
			attachmentVO.setIsDownloadable("1");
			attachmentVO.setInsertBy(userId);
			attachmentVO.setInsertTime(new Date());
			attachmentVO.setUpdateBy(userId);
			attachmentVO.setUpdateTime(new Date());
			this.sysAttachmentDao.saveSysAttachment(attachmentVO);
			return attachmentVO;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteSysAttachmentAndFile(String downloadRoot,Integer id) {
		SysAttachmentVO attachmentVO = this.sysAttachmentDao.getSysAttachmentById(id);
		if (attachmentVO != null) {
			String absoluteFilename = downloadRoot + attachmentVO.getAttachmentPath() + File.separator + attachmentVO.getFileFullPath();
			this.sysAttachmentDao.deleteSysAttachmentById(id);
			File file = new File(absoluteFilename);
			if (file.exists() && file.isDirectory()) { //删除文件
				FileUtils.deleteQuietly(file);
			}
		}
	}

	public int saveSysAttachment (SysAttachmentVO sysAttachment) {
		return sysAttachmentDao.saveSysAttachment(sysAttachment);
	}
	public int batchInsertSysAttachments (Map<String, Object> map){
		return sysAttachmentDao.batchInsertSysAttachments(map);
	}
	public int updateSysAttachment (SysAttachmentVO sysAttachment){
		return sysAttachmentDao.updateSysAttachment(sysAttachment);
	}
	public int batchUpdateSysAttachmentsToDiffVals (Map<String, Object> map){
		return sysAttachmentDao.batchUpdateSysAttachmentsToDiffVals(map);
	}
	public int batchUpdateSysAttachmentsToSameVals (Map<String, Object> map){
		return sysAttachmentDao.batchUpdateSysAttachmentsToSameVals(map);
	}
	public int batchDeleteSysAttachmentByIds (List<Integer> ids){
		return sysAttachmentDao.batchDeleteSysAttachmentByIds(ids);
	}
	public SysAttachmentVO getSysAttachmentById(Integer id){
		return sysAttachmentDao.getSysAttachmentById(id);
	}
	public List<SysAttachmentVO> getSysAttachments (SysAttachmentVO sysAttachment){
		return sysAttachmentDao.getSysAttachments(sysAttachment);
	}
	public List<Map<String, Object>>  getSysAttachmentsMap (SysAttachmentVO sysAttachment){
		return sysAttachmentDao.getSysAttachmentsMap(sysAttachment);
	}
	@Override
	public Page<SysAttachmentVO> queryPlanAttachment(Map<String, Object> paramMap) {
		Page<SysAttachmentVO> list = sysAttachmentDao.queryPlanAttachment(paramMap);
		String filePath = DeviceTokenUtil.getPropertiesValue("planFilePath");
		for (SysAttachmentVO att : list.getResult()) {
			String path = StringUtils.isNotEmpty(att.getAttachmentPath()) ? filePath+"\\"+att.getAttachmentPath() :filePath;
			att.setAttachmentPath(path);
		}
		return list;
	}
	
	/**
	 * 根据上线计划附件关系表fes_process_attachment 的类型确认 文件路径
	 */
	@Override
	public Page<SysAttachmentVO> queryOnlineAttachment(Map<String, Object> paramMap) {
		Page<SysAttachmentVO> list = sysAttachmentDao.queryOnlineAttachment(paramMap);
		for (SysAttachmentVO att : list.getResult()) {
			String processAttachmentType = att.getProcessAttachmentType();
			String filePath = "";
			if (processAttachmentType.equals("1")) { //会员方案洽谈
				filePath = DeviceTokenUtil.getPropertiesValue("draftProgramFilePath");
			} else if (processAttachmentType.equals("2")) {//会员方案确认
				filePath = DeviceTokenUtil.getPropertiesValue("finalProgramFilePath");
			} else if (Arrays.asList("3","4","12").contains(processAttachmentType)) {//实体卡制作
				filePath = DeviceTokenUtil.getPropertiesValue("cardFilePath");
			} else if (Arrays.asList("6","7","8","9","10","11","13","15").contains(processAttachmentType)) {//物料设计
				filePath = DeviceTokenUtil.getPropertiesValue("materialsDesignFilePath");
			} else if (Arrays.asList("5","14").contains(processAttachmentType)) {//实施培训回访
				filePath = DeviceTokenUtil.getPropertiesValue("trainFilePath");
			}else if(Arrays.asList("00").contains(processAttachmentType)){ // 判断是否是访谈单电子文档
				filePath = DeviceTokenUtil.getPropertiesValue("mktAttachmentDocumentPath");
			}
			String path = StringUtils.isNotEmpty(att.getAttachmentPath()) ? filePath+"\\"+att.getAttachmentPath() :filePath;
			att.setAttachmentPath(path);
			LOG.info("附件类型："+processAttachmentType +"   对应路径："+filePath);
		}
		return list;
	}
	@Override
	public Page<SysAttachmentVO> queryActivityAttachment(Map<String, Object> paramMap) {
		Page<SysAttachmentVO> list = sysAttachmentDao.queryActivityAttachment(paramMap);
		String filePath = DeviceTokenUtil.getPropertiesValue("activityFileUrl");
		for (SysAttachmentVO att : list.getResult()) {
			String path = StringUtils.isNotEmpty(att.getAttachmentPath()) ? filePath+"\\"+att.getAttachmentPath() :filePath;
			att.setAttachmentPath(path);
		}
		return list;
	}
	@Override
	public int deleteSysAttachmentById(Integer id) {
		return sysAttachmentDao.deleteSysAttachmentById(id);
	}
}
