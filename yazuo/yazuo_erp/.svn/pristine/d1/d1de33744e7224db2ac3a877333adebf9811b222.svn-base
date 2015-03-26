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

package com.yazuo.erp.system.service;

import java.io.IOException;
import java.util.Map;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;
import com.yazuo.erp.system.exception.SystemBizException;

/**
 * @Description TODO
 * @author erp team
 * @date
 */
public interface SysDatabaseService {

	/**
	 * 新增对象 @return : 新增加的主键id
	 * 
	 * @param request
	 * @param user
	 */
	int saveSysDatabase(SysDatabaseVO sysDatabase, SysUserVO user, HttpServletRequest request);

	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertSysDatabases(Map<String, Object> map);

	/**
	 * @Description 修改资料库
	 * @param sysDatabase
	 * @param user
	 * @param request
	 * @return
	 * @throws
	 */
	int updateSysDatabase(SysDatabaseVO sysDatabase, SysUserVO user, HttpServletRequest request);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysDatabasesToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysDatabasesToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 * 
	 * @param request
	 * @param user 
	 */
	int deleteSysDatabaseById(SysDatabaseVO sysDatabase, HttpServletRequest request, SysUserVO user);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysDatabaseByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	SysDatabaseVO getSysDatabaseById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysDatabaseVO> getSysDatabases(SysDatabaseVO sysDatabase);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getSysDatabasesMap(SysDatabaseVO sysDatabase);

	/**
	 * @Description 上传资料库附件
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws
	 */
	Object uploadFiles(MultipartFile[] myfiles, HttpServletRequest request) throws IOException;

	/**
	 * @Description 列表显示 资料库
	 * @param paramMap
	 * @return
	 * @throws
	 */
	Page<Map<String, Object>> querySysDatabase(Map<String, Object> paramMap);

	/**
	 * @Description 根据ID查询资料信息
	 * @param paramMap
	 * @return
	 * @throws
	 */
	Map<String, Object> querySysDatabaseById(Map<String, Object> paramMap);

	/**
	 * @Description 文件下载并保存下载记录
	 * @param relPath
	 * @param userId
	 * @param attachmentId
	 * @param response
	 * @param request
	 * @return
	 * @throws
	 */
	int handleDownload(String relPath, Integer userId, Integer attachmentId, HttpServletResponse response,
			HttpServletRequest request) throws SystemBizException, FesBizException, IOException;

}
