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
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.controller.SysDatabaseController;
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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.system.service.SysDatabaseService;
import com.yazuo.util.DateUtil;

@Service
public class SysDatabaseServiceImpl implements SysDatabaseService {

	private static final Log log = LogFactory.getLog("database");
	
	@Resource
	private SysDatabaseDao sysDatabaseDao;

	@Resource
	private SysAttachmentDao sysAttachmentDao;

	@Resource
	private SysDictionaryDao sysDictionaryDao;

	@Resource
	private SysAttachmentUserDao sysAttachmentUserDao;

	@Resource
	private SysUserDao sysUserDao;

	/**
	 * 资料库相关附件(真正用的文件)
	 */
	@Value("${databaseFilePath}")
	private String databaseFilePath;

	/**
	 * 资料库相关附件(临时文件)
	 */
	@Value("${databaseFileTempPath}")
	private String databaseFileTempPath;

	/**
	 * 上传的用户头像(真正用的文件)
	 */
	@Value("${userPhotoPath}")
	private String userPhotoPath;

	public int saveSysDatabase(SysDatabaseVO sysDatabase, SysUserVO user, HttpServletRequest request) {
		Integer userId = user.getId();// 当前用户ID

		// 处理新增附件，移动文件（临时文件夹到目标文件夹），添加附件信息
		if (null == sysDatabase) {
			throw new SystemBizException("入参异常");
		}

		// 新增资料库信息
		this.saveSysDatabase(sysDatabase, userId);
		Integer id = sysDatabase.getId();

		// 添加附件
		SysAttachmentVO sysAttachment = sysDatabase.getSysAttachment();
		Integer attachmentId = null;
		if (null != sysAttachment) {
			attachmentId = this.handleAddAttachmentList(request, userId, sysAttachment, sysDatabase);
		}

		// 更新附件ID
		sysDatabase.setAttachmentId(attachmentId);
		int count = sysDatabaseDao.updateSysDatabase(sysDatabase);
		if (1 > count) {
			throw new SystemBizException("更新附件ID失败");
		}

		return 1;
	}

	/**
	 * @Description 新增资料库信息
	 * @param sysDatabase
	 * @param userId
	 * @throws SystemBizException
	 * @throws
	 */
	private void saveSysDatabase(SysDatabaseVO sysDatabase, Integer userId) throws SystemBizException {
		// 标题
		// 描述
		// 适用人群
		// 附件ID
		sysDatabase.setIsEnable("1");// 是否有效
		sysDatabase.setRemark(null);// 备注
		sysDatabase.setInsertBy(userId);// 创建人
		sysDatabase.setInsertTime(new Date());// 创建时间
		sysDatabase.setUpdateBy(userId);// 最后修改人
		sysDatabase.setUpdateTime(new Date());// 最后修改时间
		int count = sysDatabaseDao.saveSysDatabase(sysDatabase);
		if (1 > count) {
			throw new SystemBizException("新增资料库信息失败");
		}
	}

	/**
	 * @param sysDatabase
	 * @Description 处理新增附件，移动文件（临时文件夹到目标文件夹），添加附件信息
	 * @param request
	 * @param userId
	 * @param addList
	 * @param id
	 * @throws
	 */
	private Integer handleAddAttachmentList(HttpServletRequest request, Integer userId, SysAttachmentVO sysAttachment,
			SysDatabaseVO sysDatabase) {
		// 文件从临时文件夹移动到实际位置
		String attachmentName = sysAttachment.getAttachmentName();
		Integer id = sysDatabase.getId();
		this.moveFile(attachmentName, request, id);

		// 添加附件
		Integer attachmentId = this.saveAttachment(sysAttachment, userId, id);

		// 登记资料库上传日志
		this.addDatabaseLog(userId, sysAttachment, sysDatabase, "upload");

		return attachmentId;
	}

	/**
	 * @Description 记录上传或者下载记录
	 * @param userId
	 * @param sysAttachment
	 * @param sysDatabase
	 * @param flag
	 * @return void
	 * @throws
	 */
	private void addDatabaseLog(Integer userId, SysAttachmentVO sysAttachment, SysDatabaseVO sysDatabase, String flag) {
		Map<String, Object> userInfo = sysUserDao.getUserInfoById(userId);
		if (null == userInfo) {
			throw new SystemBizException("未查询到用户信息");
		}
		String userName = (String) userInfo.get("user_name");
		String tel = (String) userInfo.get("tel");
		String positionName = (String) userInfo.get("position_name");
		String groupName = (String) userInfo.get("group_name");
		String originalFileName = sysAttachment.getOriginalFileName();
		String date = DateUtil.format(new Date());

		StringBuffer sb = new StringBuffer(64);
		if ("upload".equals(flag)) {
			sb.append("Database upload log : ");
		} else if ("download".equals(flag)) {
			sb.append("Database download log : ");
		} else {
			return;
		}

		sb.append("[");
		sb.append(sysDatabase.getId()).append("|");
		sb.append(sysDatabase.getTitle()).append("|");
		sb.append(userId).append("|");
		sb.append(userName).append("|");
		sb.append(tel).append("|");
		sb.append(groupName).append("|");
		sb.append(positionName).append("|");
		sb.append(date).append("|");
		sb.append(originalFileName).append("|");
		sb.append(sysAttachment.getId());
		sb.append("]");
		log.info(sb.toString());
	}

	/**
	 * @Description 客户投诉相关附件从临时文件夹移动到目标文件夹
	 * @param fileName
	 * @param request
	 * @param id
	 * @return
	 * @throws
	 */
	private boolean moveFile(String fileName, HttpServletRequest request, Integer id) {
		String orignPath = request.getSession().getServletContext().getRealPath(databaseFileTempPath) + "/" + fileName;
		String destPath = request.getSession().getServletContext().getRealPath(databaseFilePath) + "/" + id;
		File orignFile = new File(orignPath); // 源文件
		File destFile = new File(destPath); // 目标文件
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		return orignFile.renameTo(new File(destFile, orignFile.getName()));
	}

	/**
	 * @Description 添加资料库的附件
	 * @param sysAttachment
	 * @param userId
	 * @param id
	 * @return
	 * @throws
	 */
	private Integer saveAttachment(SysAttachmentVO sysAttachment, Integer userId, Integer id) {
		sysAttachment.setAttachmentType("3");
		sysAttachment.setAttachmentPath(String.valueOf(id));
		sysAttachment.setModuleType("sys");
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

	public int batchInsertSysDatabases(Map<String, Object> map) {
		return sysDatabaseDao.batchInsertSysDatabases(map);
	}

	public int updateSysDatabase(SysDatabaseVO sysDatabase, SysUserVO user, HttpServletRequest request) {
		Integer userId = user.getId();

		// 处理新增附件，移动文件（临时文件夹到目标文件夹），添加附件信息
		if (null == sysDatabase) {
			throw new SystemBizException("入参异常");
		}

		// 查询原记录
		SysDatabaseVO sysDatabaseDB = sysDatabaseDao.getSysDatabaseById(sysDatabase.getId());
		if (null == sysDatabaseDB) {
			throw new SystemBizException("未查询到资料信息");
		}

		// 修改附件
		SysAttachmentVO sysAttachment = sysDatabase.getSysAttachment();
		Integer attachmentId = sysDatabaseDB.getAttachmentId();
		if (null != sysAttachment) {
			attachmentId = this.handleUpdateAttachmentList(request, userId, sysAttachment, sysDatabase, sysDatabaseDB);
		}

		// 修改资料库信息
		sysDatabase.setAttachmentId(attachmentId);
		sysDatabase.setUpdateBy(userId);// 最后修改人
		sysDatabase.setUpdateTime(new Date());// 最后修改时间
		int count = sysDatabaseDao.updateSysDatabase(sysDatabase);
		if (1 > count) {
			throw new SystemBizException("更新附件ID失败");
		}

		return 1;
	}

	/**
	 * @Description 修改附件信息
	 * @param request
	 * @param userId
	 * @param sysAttachment
	 * @param sysDatabase
	 * @param sysDatabaseDB
	 * @return
	 * @throws
	 */
	private Integer handleUpdateAttachmentList(HttpServletRequest request, Integer userId, SysAttachmentVO sysAttachment,
			SysDatabaseVO sysDatabase, SysDatabaseVO sysDatabaseDB) {
		Integer attachmentId = sysDatabaseDB.getAttachmentId();
		if (null != attachmentId) {
			// 查询原附件信息
			SysAttachmentVO sysAttachmentDB = sysAttachmentDao.getSysAttachmentById(attachmentId);
			if (null == sysAttachmentDB) {
				throw new SystemBizException("未查询到原附件信息");
			}

			// 删除原附件文件
			this.deleteFile(sysAttachmentDB.getAttachmentName(), sysAttachmentDB.getAttachmentPath(), request);

			// 删除原附件信息
			sysAttachmentDB.setIsDownloadable("0");
			int count = sysAttachmentDao.updateSysAttachment(sysAttachmentDB);
			if (1 > count) {
				throw new SystemBizException("删除原附件信息失败");
			}
		}

		// 添加附件信息
		attachmentId = this.handleAddAttachmentList(request, userId, sysAttachment, sysDatabase);

		return attachmentId;
	}

	public boolean deleteFile(String fileName, String id, HttpServletRequest request) {
		String destPath = request.getSession().getServletContext().getRealPath(databaseFilePath) + "/" + id + "/" + fileName;
		File destFile = new File(destPath + "/" + fileName); // 目标文件夹
		return destFile.delete();
	}

	public int batchUpdateSysDatabasesToDiffVals(Map<String, Object> map) {
		return sysDatabaseDao.batchUpdateSysDatabasesToDiffVals(map);
	}

	public int batchUpdateSysDatabasesToSameVals(Map<String, Object> map) {
		return sysDatabaseDao.batchUpdateSysDatabasesToSameVals(map);
	}

	public int deleteSysDatabaseById(SysDatabaseVO sysDatabase, HttpServletRequest request, SysUserVO user) {
		if (null == sysDatabase) {
			throw new SystemBizException("ID不能为空");
		}

		Integer id = sysDatabase.getId();
		SysDatabaseVO sysDatabaseDB = sysDatabaseDao.getSysDatabaseById(id);
		if (null == sysDatabaseDB) {
			throw new SystemBizException("未查询到资料信息");
		}

		// 删除资料信息
		sysDatabaseDB.setIsEnable("0");
		sysDatabaseDB.setUpdateBy(user.getId());
		sysDatabaseDB.setUpdateTime(new Date());
		int count = sysDatabaseDao.updateSysDatabase(sysDatabaseDB);
		if (1 > count) {
			throw new SystemBizException("删除资料信息失败");
		}

		// 查询原附件信息
		Integer attachmentId = sysDatabaseDB.getAttachmentId();
		SysAttachmentVO sysAttachmentDB = sysAttachmentDao.getSysAttachmentById(attachmentId);

		if (null != sysAttachmentDB) {
			// 删除原附件文件
			this.deleteFile(sysAttachmentDB.getAttachmentName(), sysAttachmentDB.getAttachmentPath(), request);

			// 删除原附件信息
			sysAttachmentDB.setIsEnable("0");
			sysAttachmentDB.setUpdateBy(user.getId());
			sysAttachmentDB.setUpdateTime(new Date());
			count = sysAttachmentDao.updateSysAttachment(sysAttachmentDB);
			if (1 > count) {
				throw new SystemBizException("删除原附件信息失败");
			}
		}

		return 1;
	}

	public int batchDeleteSysDatabaseByIds(List<Integer> ids) {
		return sysDatabaseDao.batchDeleteSysDatabaseByIds(ids);
	}

	public SysDatabaseVO getSysDatabaseById(Integer id) {
		return sysDatabaseDao.getSysDatabaseById(id);
	}

	public List<SysDatabaseVO> getSysDatabases(SysDatabaseVO sysDatabase) {
		return sysDatabaseDao.getSysDatabases(sysDatabase);
	}

	public List<Map<String, Object>> getSysDatabasesMap(SysDatabaseVO sysDatabase) {
		return sysDatabaseDao.getSysDatabasesMap(sysDatabase);
	}

	/**
	 * @Title uploadFiles
	 * @Description 上传资料库附件
	 * @param myfiles
	 * @param request
	 * @return
	 * @see com.yazuo.erp.system.service.SysDatabaseService#uploadFiles(org.springframework.web.multipart.MultipartFile[],
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Object uploadFiles(MultipartFile[] myfiles, HttpServletRequest request) throws IOException {
		return FileUploaderUtil.uploadFile(myfiles, "databaseFileTempPath", null, 0, request);
	}

	/**
	 * @Title querySysCustomerComplaintList
	 * @Description 列表显示 资料库
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.system.service.SysDatabaseService#querySysCustomerComplaintList(java.util.Map)
	 */
	@Override
	public Page<Map<String, Object>> querySysDatabase(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		if (null == user) {
			throw new SystemBizException("请重新登录");
		}
		Integer userId = user.getId();

		// 判断用户是否有前端权限
		int isFes = this.sysUserDao.checkPermission(userId, "fes_my_main_page");

		// 判断用户是否有销售权限
		int isSale = this.sysUserDao.checkPermission(userId, "sales_service");

		if (0 < isFes && 0 < isSale) {// 前端、销售权限
			paramMap.put("crowdType", "3");
		} else if (0 < isFes) {// 前端权限
			paramMap.put("crowdType", "1");
		} else if (0 < isSale) {// 销售权限
			paramMap.put("crowdType", "2");
		} else {// 无权限，无查询记录
			paramMap.put("crowdType", "0");
		}

		Integer pageSize = (Integer) paramMap.get("pageSize");
		Integer pageNumber = (Integer) paramMap.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页
		Page<Map<String, Object>> list = sysDatabaseDao.querySysDatabase(paramMap);
		for (Map<String, Object> vo : list) {
			String attachmentSize = (String) vo.get("attachment_size");
			if (null != attachmentSize) {
				BigDecimal attachmentSizeB = new BigDecimal(attachmentSize);
				BigDecimal attachmentSizeKB = attachmentSizeB.divide(BigDecimal.valueOf(1024)).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				vo.put("attachment_size", attachmentSizeKB);
			}

			// 处理投诉类型的中文显示，拼接字符串
			String[] types = (String[]) vo.get("apply_crowd_type");
			List applyCrowdTypeNameList = this.getApplyCrowdTypeName(types);
			vo.put("apply_crowd_type_name", applyCrowdTypeNameList);
			vo.put("databaseFilePath", databaseFilePath);// 附件配置路径
		}
		return list;
	}

	/**
	 * @Description 处理适用人群的中文显示，拼接字符串
	 * @param type
	 * @return
	 * @throws
	 */
	private List getApplyCrowdTypeName(String[] type) {
		List<Map<String, Object>> typeList = sysDictionaryDao.querySysDictionaryByType("00000094");
		Map<String, String> m = new HashMap<String, String>();
		for (Map<String, Object> t : typeList) {
			m.put((String) t.get("dictionary_key"), (String) t.get("dictionary_value"));
		}

		List list = new ArrayList<String>();
		for (String str : type) {
			String typeName = m.get(str);
			list.add(typeName);
		}
		return list;
	}

	/**
	 * @Title querySysDatabaseById
	 * @Description 根据ID查询资料信息
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.system.service.SysDatabaseService#querySysDatabaseById(java.util.Map)
	 */
	@Override
	public Map<String, Object> querySysDatabaseById(Map<String, Object> paramMap) {
		Integer id = (Integer) paramMap.get("id");
		if (null == id) {
			throw new SystemBizException("资料ID不能为空");
		}

		SysDatabaseVO sysDatabase = new SysDatabaseVO();
		sysDatabase.setId(id);
		Map<String, Object> sysDatabasesMap = this.sysDatabaseDao.getSysDatabasesMapById(sysDatabase);
		if (null == sysDatabasesMap || 0 == sysDatabasesMap.size()) {
			throw new SystemBizException("未查询到资料信息");
		}

		Integer attachmentId = (Integer) sysDatabasesMap.get("attachment_id");
		SysAttachmentVO sysAttachment = null;
		Map<String, Object> dataMap = null;
		if (null != attachmentId) {
			// 查询附件信息
			sysAttachment = this.sysAttachmentDao.getSysAttachmentById(attachmentId);
			if (null == sysAttachment) {
				throw new SystemBizException("系统异常，未查询到附件信息");
			}

			String attachmentSize = sysAttachment.getAttachmentSize();
			if (null != attachmentSize) {
				BigDecimal attachmentSizeB = new BigDecimal(attachmentSize);
				BigDecimal attachmentSizeKB = attachmentSizeB.divide(BigDecimal.valueOf(1024)).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				sysAttachment.setAttachmentSizeKB(attachmentSizeKB);
			}

			// 分页查询下载记录
			Integer pageSize = (Integer) paramMap.get("pageSize");
			Integer pageNumber = (Integer) paramMap.get("pageNumber");
			PageHelper.startPage(pageNumber, pageSize, true);// 分页

			SysAttachmentUserVO sysAttachmentUser = new SysAttachmentUserVO();
			sysAttachmentUser.setAttachmentId(attachmentId);
			Page<Map<String, Object>> pageList = this.sysAttachmentUserDao.getSysAttachmentUserList(sysAttachmentUser);

			dataMap = new HashMap<String, Object>();
			dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
			dataMap.put(Constant.ROWS, pageList.getResult());
		}

		// 处理投诉类型的中文显示，拼接字符串
		String[] types = (String[]) sysDatabasesMap.get("apply_crowd_type");
		List applyCrowdTypeNameList = this.getApplyCrowdTypeName(types);

		sysDatabasesMap.put("sysAttachment", sysAttachment);// 附件信息
		sysDatabasesMap.put("databaseFilePath", databaseFilePath);// 附件配置路径
		sysDatabasesMap.put("apply_crowd_type_name", applyCrowdTypeNameList);// 适用人群中文名
		sysDatabasesMap.put("sysAttachmentUserList", dataMap);// 下载记录列表
		sysDatabasesMap.put("userPhotoPath", userPhotoPath);// 用户头像配置路径
		return sysDatabasesMap;
	}

	/**
	 * @Title handleDownload
	 * @Description 文件下载并保存下载记录
	 * @param relPath
	 * @param userId
	 * @param attachmentId
	 * @param response
	 * @param request
	 * @return
	 * @throws SystemBizException
	 * @throws FesBizException
	 * @throws IOException
	 * @see com.yazuo.erp.system.service.SysDatabaseService#handleDownload(java.lang.String,
	 *      java.lang.Integer, java.lang.Integer,
	 *      javax.servlet.http.HttpServletResponse,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public int handleDownload(String relPath, Integer userId, Integer attachmentId, HttpServletResponse response,
			HttpServletRequest request) throws SystemBizException, FesBizException, IOException {
		if (null == relPath) {
			throw new SystemBizException("路径不能为空");
		}
		String prefixPath = request.getSession().getServletContext().getRealPath("/");
		String fileFullPath = prefixPath + relPath;
		FileSystemResource resource = new FileSystemResource(fileFullPath);
		int count = 0;
		if (!resource.exists()) {
			throw new FesBizException("文件不存在！");
		} else {
			OutputStream os = response.getOutputStream();
			try {
				response.reset();
				String fileName = resource.getFilename();
				SysAttachmentVO sysAttachment = sysAttachmentDao.getSysAttachmentById(attachmentId);
				if (null == sysAttachment) {
					throw new SystemBizException("未查询到附件信息");
				}
				String originalFileName = sysAttachment.getOriginalFileName();
				if (null != originalFileName) {
					fileName = originalFileName;
				}
				String agent = request.getHeader("USER-AGENT");
				if (null != agent && -1 != agent.indexOf("MSIE")) { // IE
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				} else if (null != agent && -1 != agent.indexOf("Mozilla")) { // Firefox
					fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
				} else {
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				}
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				response.setContentType("application/octet-stream; charset=utf-8");
				IOUtils.copyLarge(FileUtils.openInputStream(new File(fileFullPath)), os);
				os.flush();

				// 添加下载记录
				SysAttachmentUserVO sysAttachmentUserVO = new SysAttachmentUserVO();
				sysAttachmentUserVO.setAttachmentId(attachmentId);
				sysAttachmentUserVO.setUserId(userId);
				sysAttachmentUserVO.setInsertBy(userId);
				sysAttachmentUserVO.setInsertTime(new Date());
				count = sysAttachmentUserDao.saveSysAttachmentUser(sysAttachmentUserVO);

				// 登记下载记录
				SysDatabaseVO sysDatabase = sysDatabaseDao
						.getSysDatabaseById(Integer.parseInt(sysAttachment.getAttachmentPath()));
				if (null == sysDatabase) {
					throw new SystemBizException("未查询到资料信息");
				}
				this.addDatabaseLog(userId, sysAttachment, sysDatabase, "download");
			} finally {
				if (os != null) {
					os.close();
				}
			}
		}
		return count;
	}
}
