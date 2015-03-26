/**
 * @Description 填单信息相关接口
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description 填单信息相关接口
 * @author erp team
 * @date
 */
public interface SysDocumentService {

	/**
	 * @Description 新增对象 
	 * @param sysDocument
	 * @param request
	 * @param user
	 * @return
	 * @throws
	 */
	int saveSysDocument(SysDocumentVO sysDocument, HttpServletRequest request, SysUserVO user);

	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertSysDocuments(Map<String, Object> map);

	/**
	 * @Description 修改对象
	 * @param sysDocument
	 * @param request
	 * @param user
	 * @return
	 * @throws
	 */
	int updateSysDocument(SysDocumentVO sysDocument, HttpServletRequest request, SysUserVO user);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysDocumentsToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysDocumentsToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteSysDocumentById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysDocumentByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	SysDocumentVO getSysDocumentById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysDocumentVO> getSysDocuments(SysDocumentVO sysDocument);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getSysDocumentsMap(SysDocumentVO sysDocument);

	/**
	 * @Description 上传填单附件
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws
	 */
	Object uploadFiles(MultipartFile[] myfiles, HttpServletRequest request) throws IOException;

	/**
	 * @Description 根据ID查询填单信息
	 * @param sysDocument
	 * @return
	 * @throws
	 */
	SysDocumentVO querySysDocumentById(SysDocumentVO sysDocument);

	/**
	 * 通过商户Id 和类型查找对应的回访信息
	 */
	List<SysDocumentVO> getSysDocumentsByMerchantAndType(SysDocumentVO[] sysDocuments);

	/**
	 * @Description 保存多个填单信息
	 */
	int saveSysDocuments(SysDocumentVO[] sysDocuments, HttpServletRequest request, SysUserVO user);

	/**
	 * 保存填单信息 , 如果录音和调研单都存在则更新状态
	 * 
	 * 第9步，新增/修改上线实施培训回访单的时候调用
	 */
	JsonResult saveDocumentAndUpdateStatusForStep9( SysDocumentVO sysDocument, HttpServletRequest request,
			SysUserVO user, Boolean isUploadFlag);

	/**
	 * 上传录音并添加关联表记录
	 */
	JsonResult uploadRecordAndSaveSysDocumentForStep9(MultipartFile[] myfiles, 
			SysDocumentVO sysDocument, HttpServletRequest request, SysUserVO user);

	/**
	 * 保存填单信息 , 如果录音和调研单都存在则更新状态
	 * 
	 *  第10步结束以后由后端客服处理.上线后回访单
	 */
	JsonResult saveDocumentAndUpdateStatusAfterStep10(SysDocumentVO sysDocument, HttpServletRequest request, SysUserVO user);

	/**
	 * 上传录音并添加关联表记录
	 * 
	 * 第10步结束以后由后端客服处理.上线后回访单
	 */
	JsonResult uploadRecordAndSaveSysDocumentAfterOnline(MultipartFile[] myfiles, SysDocumentVO[] sysDocuments,
			HttpServletRequest request, SysUserVO user, Boolean flag);

	/**
	 * @Description 通过商户id和文档类型查找对应的 回访单
	 */
	List<SysDocumentVO> getDocumentByMerchantAndType(Integer merchantId, String docummentType);

	/**
	 * 添加路径信息
	 */
	void addAttachmentPathToSysDocument(SysDocumentVO sysDocument);

	/**
	 * 判断是否实施培训回访和录音同时存在，存在返回状态"3"
	 */
	String getStatusForSysDocument(Integer merchantId, String docummentType);

	/**
	 * 通过商户ID查询回访单信息
	 */
	List<SysDocumentVO> getSysDocumentByMerchantId(SysDocumentVO sysDocument);

	JsonResult saveDocumentAndUpdateStatusAfterStep10(SysDocumentVO[] sysDocuments, HttpServletRequest request, SysUserVO user);

}
