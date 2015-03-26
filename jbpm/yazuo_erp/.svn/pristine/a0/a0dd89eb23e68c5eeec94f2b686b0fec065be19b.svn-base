/**
 * @Description 客户投诉相关接口实现
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

import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;
import com.yazuo.erp.system.exception.SystemBizException;

/**
 * @Description 客户投诉相关接口实现
 * @author gaoshan
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.system.service.SysCustomerComplaintService;

@Service
public class SysCustomerComplaintServiceImpl implements SysCustomerComplaintService {

	@Resource
	private SysCustomerComplaintDao sysCustomerComplaintDao;

	@Resource
	private SysAttachmentDao sysAttachmentDao;

	@Resource
	private SysDictionaryDao sysDictionaryDao;

	@Resource
	private SysUserMerchantDao sysUserMerchantDao;

	@Resource
	private SysToDoListDao sysToDoListDao;

	/**
	 * 上传的客户投诉相关附件(真正用的文件)
	 */
	@Value("${customerComplaintFilePath}")
	private String customerComplaintFilePath;

	/**
	 * 上传的客户投诉相关附件(临时文件)
	 */
	@Value("${customerComplaintFileTempPath}")
	private String customerComplaintFileTempPath;

	/**
	 * @Title uploadFiles
	 * @Description 上传客户投诉附件
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws IOException
	 * @see com.yazuo.erp.system.service.SysCustomerComplaintService#uploadFiles(org.springframework.web.multipart.MultipartFile[],
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Object uploadFiles(MultipartFile[] myfiles, HttpServletRequest request) throws IOException {
		return FileUploaderUtil.uploadFile(myfiles, "customerComplaintFileTempPath", null, 0, request);
	}

	/**
	 * @Title saveSysCustomerComplaint
	 * @Description 新增客户投诉
	 * @param sysCustomerComplaint
	 * @param user
	 * @return
	 * @see com.yazuo.erp.system.service.SysCustomerComplaintService#saveSysCustomerComplaint(com.yazuo.erp.system.vo.SysCustomerComplaintVO,
	 *      com.yazuo.erp.system.vo.SysUserVO)
	 */
	@Override
	public int saveSysCustomerComplaint(SysCustomerComplaintVO sysCustomerComplaint, SysUserVO user, HttpServletRequest request) {
		Integer userId = user.getId();// 当前用户ID

		// 处理新增附件，移动文件（临时文件夹到目标文件夹），添加附件信息
		if (null == sysCustomerComplaint) {
			throw new SystemBizException("入参异常");
		}

		SysAttachmentVO sysAttachment = sysCustomerComplaint.getSysAttachment();
		Integer attachmentId = null;
		if (null != sysAttachment) {
			attachmentId = this.handleAddAttachmentList(request, userId, sysAttachment);
		}

		// 添加客户投诉
		this.saveSysCustomerComplaint(sysCustomerComplaint, userId, attachmentId);
		Integer id = sysCustomerComplaint.getId();

		// 查询商户的前端负责人
		SysUserMerchantVO sysUserMerchant = new SysUserMerchantVO();
		sysUserMerchant.setMerchantId(sysCustomerComplaint.getMerchantId());
		List<SysUserMerchantVO> users = sysUserMerchantDao.getSysUserMerchants(sysUserMerchant);
		for (SysUserMerchantVO u : users) {
			SysToDoListVO toDoListVO = new SysToDoListVO();
			toDoListVO.setUserId(u.getUserId());// 用户ID
			toDoListVO.setMerchantId(u.getMerchantId());// 商户ID
			toDoListVO.setPriorityLevelType("01");// 优先级
			toDoListVO.setItemType("02");// 待办事项类型
			toDoListVO.setItemContent(sysCustomerComplaint.getComplaintTitle());// 待办事项内容
			toDoListVO.setItemStatus("0");// 待办事项状态
			toDoListVO.setBusinessType("2");// 业务场景
			toDoListVO.setRelatedType("3");// 相关类型
			toDoListVO.setRelatedId(id);// 相关ID
			toDoListVO.setRemark(null);// 备注
			toDoListVO.setIsEnable("1");// 是否有效
			toDoListVO.setInsertBy(userId);// 创建人
			toDoListVO.setInsertTime(new Date());// 创建时间
			toDoListVO.setUpdateBy(userId);// 最后修改人
			toDoListVO.setUpdateTime(new Date());// 最后修改时间

			// 给商户的前端负责人发送待办事项
			sysToDoListDao.saveSysToDoList(toDoListVO);
		}

		return 1;

	}

	/**
	 * @Description 新增客户投诉
	 * @param sysCustomerComplaint
	 * @param userId
	 * @param attachmentId
	 * @throws SystemBizException
	 * @throws
	 */
	private void saveSysCustomerComplaint(SysCustomerComplaintVO sysCustomerComplaint, Integer userId, Integer attachmentId)
			throws SystemBizException {
		sysCustomerComplaint.setAttachmentId(attachmentId);// 附件ID
		sysCustomerComplaint.setCustomerComplaintStatus("0");// 客户投诉状态
		sysCustomerComplaint.setIsEnable("1");// 是否有效
		sysCustomerComplaint.setInsertBy(userId);// 创建人
		sysCustomerComplaint.setInsertTime(new Date());// 创建时间
		sysCustomerComplaint.setUpdateBy(userId);// 最后修改人
		sysCustomerComplaint.setUpdateTime(new Date());// 最后修改时间
		int count = sysCustomerComplaintDao.saveSysCustomerComplaint(sysCustomerComplaint);
		if (1 > count) {
			throw new SystemBizException("新增客户投诉失败");
		}
	}

	/**
	 * @Description 处理新增附件，移动文件（临时文件夹到目标文件夹），添加附件信息
	 * @param request
	 * @param userId
	 * @param addList
	 * @throws
	 */
	private Integer handleAddAttachmentList(HttpServletRequest request, Integer userId, SysAttachmentVO sysAttachment) {
		// 文件从临时文件夹移动到实际位置
		String attachmentName = sysAttachment.getAttachmentName();
		this.moveFile(attachmentName, request);

		// 添加附件
		Integer attachmentId = this.saveAttachment(sysAttachment, userId);

		return attachmentId;
	}

	/**
	 * @Description 添加客户投诉的附件
	 * @param sysAttachment
	 * @param userId
	 * @return
	 * @throws
	 */
	private Integer saveAttachment(SysAttachmentVO sysAttachment, Integer userId) {
		sysAttachment.setAttachmentType("2");
		sysAttachment.setAttachmentPath(null);
		sysAttachment.setModuleType("fes");
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
		return sysAttachment.getId();
	}

	/**
	 * @Description 客户投诉相关附件从临时文件夹移动到目标文件夹
	 * @param fileName
	 * @param request
	 * @return
	 * @throws
	 */
	private boolean moveFile(String fileName, HttpServletRequest request) {
		String orignPath = request.getSession().getServletContext().getRealPath(customerComplaintFileTempPath) + "/" + fileName;
		String destPath = request.getSession().getServletContext().getRealPath(customerComplaintFilePath);
		File orignFile = new File(orignPath); // 源文件
		File destFile = new File(destPath); // 目标文件
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		return orignFile.renameTo(new File(destFile, orignFile.getName()));
	}

	/**
	 * @Title updateSysCustomerComplaint
	 * @Description 修改客户投诉
	 * @param sysCustomerComplaint
	 * @param user
	 * @return
	 * @see com.yazuo.erp.system.service.SysCustomerComplaintService#updateSysCustomerComplaint(com.yazuo.erp.system.vo.SysCustomerComplaintVO,
	 *      com.yazuo.erp.system.vo.SysUserVO)
	 */
	@Override
	public int updateSysCustomerComplaint(SysCustomerComplaintVO sysCustomerComplaint, SysUserVO user) {
		sysCustomerComplaint.setUpdateBy(user.getId());// 最后修改人
		sysCustomerComplaint.setUpdateTime(new Date());// 最后修改时间

		// 修改客户投诉信息
		sysCustomerComplaintDao.updateSysCustomerComplaint(sysCustomerComplaint);
		Integer id = sysCustomerComplaint.getId();

		// 根据客户投诉ID查询待办事项，并关闭
		SysToDoListVO sysToDoList = new SysToDoListVO();
		sysToDoList.setRelatedType("3");
		sysToDoList.setRelatedId(id);
		List<SysToDoListVO> sysToDoLists = sysToDoListDao.getSysToDoLists(sysToDoList);
		for (SysToDoListVO vo : sysToDoLists) {
			vo.setItemStatus("1");
			sysToDoListDao.updateSysToDoList(vo);
		}
		return 1;
	}

	/**
	 * @Title querySysCustomerComplaintById
	 * @Description 通过主键查找客户投诉
	 * @param id
	 * @return
	 * @see com.yazuo.erp.system.service.SysCustomerComplaintService#querySysCustomerComplaintById(java.lang.Integer)
	 */
	@Override
	public Map<String, Object> querySysCustomerComplaintById(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> vo = sysCustomerComplaintDao.querySysCustomerComplaintById(id);
		if (null == vo) {
			throw new SystemBizException("未查询到该客户投诉信息");
		}

		// 处理投诉类型的中文显示，拼接字符串
		String[] types = (String[]) vo.get("customer_complaint_type");
		String customerComplaintTypeName = this.getCustomerComplaintTypeName(types);
		vo.put("customer_complaint_type_name", customerComplaintTypeName);

		// 查询附件信息
		Integer attachmentId = (Integer) vo.get("attachment_id");
		if (null != attachmentId && 0 != attachmentId) {
			SysAttachmentVO sysAttachment = sysAttachmentDao.getSysAttachmentById(attachmentId);
			map.put("customerComplaintFilePath", customerComplaintFilePath);
			map.put("sysAttachment", sysAttachment);
		} else {
			map.put("customerComplaintFilePath", "");
			map.put("sysAttachment", "");
		}

		map.put("sysCustomerComplaint", vo);
		return map;
	}

	/**
	 * @Description 处理投诉类型的中文显示，拼接字符串
	 * @param type
	 * @return
	 * @throws
	 */
	private String getCustomerComplaintTypeName(String[] type) {
		List<Map<String, Object>> typeList = sysDictionaryDao.querySysDictionaryByType("00000049");
		Map<String, String> m = new HashMap<String, String>();
		for (Map<String, Object> t : typeList) {
			m.put((String) t.get("dictionary_key"), (String) t.get("dictionary_value"));
		}

		StringBuffer sb = new StringBuffer(64);
		for (String str : type) {
			String typeName = m.get(str);
			sb.append(typeName).append(",");
		}

		String customerComplaintTypeName = "";
		if (null != sb && sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
			customerComplaintTypeName = sb.toString();
		}
		return customerComplaintTypeName;
	}

	/**
	 * @Title 返回所有返回所有满足条件的Map客户投诉的List
	 * @Description
	 * @param paramerMap
	 * @return
	 * @see com.yazuo.erp.system.service.SysCustomerComplaintService#querySysCustomerComplaintList(java.util.Map)
	 */
	@Override
	public Page<Map<String, Object>> querySysCustomerComplaintList(Map<String, Object> paramerMap) {
		Page<Map<String, Object>> list = sysCustomerComplaintDao.querySysCustomerComplaintList(paramerMap);
		for (Map<String, Object> vo : list) {
			// 处理投诉类型的中文显示，拼接字符串
			String[] types = (String[]) vo.get("customer_complaint_type");
			String customerComplaintTypeName = this.getCustomerComplaintTypeName(types);
			vo.put("customer_complaint_type_name", customerComplaintTypeName);

			// 查询附件信息
			Integer attachmentId = (Integer) vo.get("attachment_id");
			if (null != attachmentId && 0 != attachmentId) {
				SysAttachmentVO sysAttachment = sysAttachmentDao.getSysAttachmentById(attachmentId);
				vo.put("customerComplaintFilePath", customerComplaintFilePath);
				vo.put("sysAttachment", sysAttachment);
			} else {
				vo.put("customerComplaintFilePath", "");
				vo.put("sysAttachment", "");
			}
		}
		return list;
	}

	/**
	 * @Title deleteSysCustomerComplaintById
	 * @Description 按ID删除客户投诉
	 * @param sysCustomerComplaint
	 * @return
	 * @see com.yazuo.erp.system.service.SysCustomerComplaintService#deleteSysCustomerComplaintById(com.yazuo.erp.system.vo.SysCustomerComplaintVO)
	 */
	@Override
	public int deleteSysCustomerComplaintById(SysCustomerComplaintVO sysCustomerComplaint) {
		return sysCustomerComplaintDao.deleteSysCustomerComplaintById(sysCustomerComplaint.getId());
	}

}
