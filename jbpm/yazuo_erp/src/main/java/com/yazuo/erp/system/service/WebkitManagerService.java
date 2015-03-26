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

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.system.vo.SysWebkitVO;

/**
 * webkit版本管理
 * @author kyy
 * @date 2014-9-15 下午1:49:09
 */
public interface WebkitManagerService {

	/**上传文件*/
	public Object uploadFile(MultipartFile[] myfile, HttpServletRequest request) throws IOException;
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	JsonResult saveSysWebkit (SysWebkitVO sysWebkit, SysUserVO user, HttpServletRequest request);

	/**
	 * 按ID删除对象
	 */
	JsonResult deleteSysWebkitById (Integer id, HttpServletRequest request);
	
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	Page<SysWebkitVO> getSysWebkits (SysWebkitVO sysWebkit, HttpServletRequest request);
	
	SysWebkitVO getWebKitMaxVersion();
}
