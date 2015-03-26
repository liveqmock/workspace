/**
 * @Description 客户投诉相关接口
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

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.*;

/**
 * @Description 客户投诉相关接口
 * @author gaoshan
 * @date
 */
public interface SysCustomerComplaintService {
	
	/**
	 * @Description 上传客户投诉附件
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws 
	 */
	Object uploadFiles(MultipartFile[] myfiles, HttpServletRequest request) throws IOException;
	
	/**
	 * @Description 新增客户投诉
	 * @param sysCustomerComplaint
	 * @param user
	 * @param request 
	 * @return
	 * @throws
	 */
	int saveSysCustomerComplaint(SysCustomerComplaintVO sysCustomerComplaint, SysUserVO user, HttpServletRequest request);

	/**
	 * @Description 修改客户投诉 
	 * @param sysCustomerComplaint
	 * @param user
	 * @return
	 * @throws
	 */
	int updateSysCustomerComplaint(SysCustomerComplaintVO sysCustomerComplaint, SysUserVO user);
	
	/**
	 * @Description 根据ID查询客户投诉信息
	 * @param id
	 * @return
	 * @throws 
	 */
	Map<String, Object> querySysCustomerComplaintById(Integer id);

	/**
	 * @Description 按ID删除客户投诉
	 * @param sysCustomerComplaint
	 * @return
	 * @throws
	 */
	int deleteSysCustomerComplaintById(SysCustomerComplaintVO sysCustomerComplaint);

	/**
	 * @Description 返回所有返回所有满足条件的Map客户投诉的List
	 * @param sysCustomerComplaint
	 * @return
	 * @throws
	 */
	Page<Map<String, Object>> querySysCustomerComplaintList(Map<String, Object> paramerMap);

}
