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
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.dao.SysWebkitDao;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.WebkitManagerService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.system.vo.SysWebkitVO;
import com.yazuo.util.StringUtil;

/**
 * webkit版本管理
 * @author kyy
 * @date 2014-9-15 下午1:52:12
 */

@Service
public class WebkitManagerServiceImpl implements WebkitManagerService {

	private static final Log log = LogFactory.getLog(WebkitManagerServiceImpl.class);
	
	@Resource
	private SysWebkitDao sysWebkitDao;
	@Resource
	private SysAttachmentService sysAttachmentService;
	@Value("${webkitFileTempPath}")
	private String webkitTempPath;
	@Value("${webkitFilePath}")
	private String webkitRealPath;
	
	public Object uploadFile(MultipartFile[] myfile, HttpServletRequest request) throws IOException {
		long maxFileSize = 0;//20 *1024*1024;
		return FileUploaderUtil.uploadFile(myfile, "webkitFileTempPath", "", maxFileSize, request);
	}

	@Override
	public JsonResult saveSysWebkit(SysWebkitVO sysWebkit, SysUserVO user, HttpServletRequest request) {
		JsonResult result = new JsonResult();
		SysAttachmentVO attachment = sysWebkit.getAttachmentVO();
		// 判断此版本号是否已存在
		SysWebkitVO webkit = new SysWebkitVO();
		webkit.setVersionNum(sysWebkit.getVersionNum());
		long num = sysWebkitDao.getWebKitManagerByVersion(webkit); // 根据版本号查询
		if (num >0) { // 已存在此版本
			result.setFlag(false).setMessage("此版本号已存在，请重新上传!");
			return result;
		}
		// 将临时里面的文件放到实际目录下
		String orignPath = request.getSession().getServletContext().getRealPath(webkitTempPath) + "/" + attachment.getAttachmentName() ;// 原路径
		String destPath = request.getSession().getServletContext().getRealPath(webkitRealPath);// 目标文件夹

		File resFile = new File(orignPath);
		File distFile = new File(destPath);
		if (resFile.exists()) {
			if (!distFile.exists()){
				distFile.mkdirs();
			}
			boolean success = resFile.renameTo(new File(distFile, resFile.getName()));
			log.info(success ? "移动文件成功!" : "移动文件失败!");
		} else {
			result.setFlag(false);
			result.setMessage("原文件不存在!");
			log.info("要移动的文件不存在");
			return result;
		}
		
		//先向附件表插入数据
		Integer attachmentId = 0;
		if (attachment !=null) {
			attachment.setIsEnable("1");
			attachment.setIsDownloadable("1");
			attachment.setInsertBy(user.getId());
			attachment.setUpdateBy(user.getId());
			attachment.setInsertTime(new Date());
			attachment.setUpdateTime(new Date());
			attachment.setModuleType("sys");
			attachment.setAttachmentType("3");
			String fileName = attachment.getAttachmentName();
			String suffix = !StringUtil.isNullOrEmpty(fileName) ? fileName.substring(fileName.lastIndexOf(".")+1):"";
			attachment.setAttachmentSuffix(suffix);
			// 保存附件
			sysAttachmentService.saveSysAttachment(attachment);
			attachmentId = attachment.getId();
		}
		// 再webkit版本表插入数据
		sysWebkit.setAttachmentId(attachmentId);
		sysWebkit.setIsEnable("1");
		sysWebkit.setInsertBy(user.getId());
		sysWebkit.setUpdateBy(user.getId());
		sysWebkit.setInsertTime(new Date());
		sysWebkit.setUpdateTime(new Date());
		int count = sysWebkitDao.saveSysWebkit(sysWebkit);
		result.setFlag(count>0).setMessage(count>0?"保存成功!":"保存失败!");
		return result;
	}

	@Override
	public JsonResult deleteSysWebkitById(Integer id, HttpServletRequest request) {
		JsonResult result = new JsonResult();
		SysWebkitVO webkit = sysWebkitDao.getSysWebkitById(id);
		SysAttachmentVO attachment = sysAttachmentService.getSysAttachmentById(webkit.getAttachmentId());
		// 删除文件目录下的文件
		String filePath = request.getSession().getServletContext().getRealPath(webkitRealPath) + "/" + attachment.getAttachmentName();
		File file = new File(filePath);
		if (file.exists()) { // 是否存在
			file.delete();
		} else {
			log.info(attachment + "文件不存在!");
		}
		// 在删除webkit版本信息表
		int count = sysWebkitDao.deleteSysWebkit(id);

		// 删除附件表中的webkit相关信息
		Integer attachmentId = webkit.getAttachmentId();
		sysAttachmentService.deleteSysAttachmentById(attachmentId);
		return result.setFlag(count>0).setMessage(count>0?"删除成功!":"删除失败!");
	}

	@Override
	public Page<SysWebkitVO> getSysWebkits(SysWebkitVO sysWebkit, HttpServletRequest request) {
		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort() + "/"
				+ request.getContextPath()+"/"+webkitRealPath;
		PageHelper.startPage(sysWebkit.getPageNumber(), sysWebkit.getPageSize(), true);
		Page<SysWebkitVO> list = sysWebkitDao.getWebKitManager(sysWebkit);
		for (SysWebkitVO webkit : list.getResult()) {
			String downloadLink =path +"/"+webkit.getFileName();
			webkit.setDownloadLink(downloadLink);
		}
		return list;
	}

	@Override
	public SysWebkitVO getWebKitMaxVersion() {
		List<SysWebkitVO> list = sysWebkitDao.getWebKitMaxVersion();
		if (list !=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
