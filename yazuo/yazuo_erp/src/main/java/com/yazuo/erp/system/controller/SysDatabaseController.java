/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
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

package com.yazuo.erp.system.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;
import com.yazuo.erp.system.exception.SystemBizException;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;

import com.yazuo.erp.system.service.SysDatabaseService;
import com.yazuo.erp.system.controller.SysRoleController;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 资料库 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("sysDatabase")
@SessionAttributes(Constant.SESSION_USER)
public class SysDatabaseController {

	private static final Log log = LogFactory.getLog(SysDatabaseController.class);

	@Resource
	private SysDatabaseService sysDatabaseService;

	@Resource
	private SysAttachmentUserDao sysAttachmentUserDao;

	/**
	 * @Description 上传资料库附件
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping(value = "uploadFiles", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object uploadPlanFiles(@RequestParam(value = "myfiles", required = false) MultipartFile[] myfiles,
			HttpServletRequest request) throws IOException {
		return sysDatabaseService.uploadFiles(myfiles, request);
	}

	/**
	 * 保存资料库
	 */
	@RequestMapping(value = "saveSysDatabase", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveSysDatabase(@RequestBody SysDatabaseVO sysDatabase, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysDatabaseService.saveSysDatabase(sysDatabase, user, request);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}

	/**
	 * 修改资料库
	 */
	@RequestMapping(value = "updateSysDatabase", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult updateSysDatabase(@RequestBody SysDatabaseVO sysDatabase, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysDatabaseService.updateSysDatabase(sysDatabase, user, request);
		return new JsonResult(count > 0).setMessage(count > 0 ? "修改成功!" : "修改失败!");
	}

	/**
	 * 列表显示 资料库
	 */
	@RequestMapping(value = "querySysDatabase", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult listSysDatabases(@RequestBody Map<String, Object> paramMap,@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		Page<Map<String, Object>> pageList = sysDatabaseService.querySysDatabase(paramMap);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		dataMap.put(Constant.ROWS, pageList.getResult());

		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * 根据ID查询资料信息
	 */
	@RequestMapping(value = "querySysDatabaseById", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult querySysDatabaseById(@RequestBody Map<String, Object> paramMap) {
		Map<String, Object> dataMap = sysDatabaseService.querySysDatabaseById(paramMap);
		return new JsonResult().setFlag(true).setData(dataMap);
	}

	/**
	 * 删除 资料库
	 */
	@RequestMapping(value = "deleteSysDatabase", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult deleteSysDatabase(@RequestBody SysDatabaseVO sysDatabase, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = sysDatabaseService.deleteSysDatabaseById(sysDatabase, request, user);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}

	/**
	 * @Description 文件下载并保存下载记录
	 * @param paramMap
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws
	 */
	@RequestMapping(value = "download", method = { RequestMethod.POST, RequestMethod.GET })
	public void download(@RequestParam(required = true) String relPath, @RequestParam(required = true) Integer userId,
			@RequestParam(required = true) Integer attachmentId, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		int count = sysDatabaseService.handleDownload(relPath, userId, attachmentId, response, request);
	}
}
