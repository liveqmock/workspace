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

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;

import com.yazuo.erp.system.service.SysAttachmentUserService;
import com.yazuo.erp.system.controller.SysRoleController;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 附件-用户关系表（下载记录表） 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("sysAttachmentUser")
@SessionAttributes(Constant.SESSION_USER)
public class SysAttachmentUserController {

	private static final Log LOG = LogFactory.getLog(SysAttachmentUserController.class);
	@Resource
	private SysAttachmentUserService sysAttachmentUserService;

	/**
	 * 保存附件-用户关系表（下载记录表）
	 */
	@RequestMapping(value = "saveSysAttachmentUser", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveSysAttachmentUser(@RequestBody SysAttachmentUserVO sysAttachmentUser,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Integer userId = user.getId();
		sysAttachmentUser.setInsertBy(userId);
		sysAttachmentUser.setInsertTime(new Date());
		int count = sysAttachmentUserService.saveSysAttachmentUser(sysAttachmentUser);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}

	/**
	 * 列表显示 附件-用户关系表（下载记录表）
	 */
	@RequestMapping(value = "listSysAttachmentUsers/{pageNumber}/{pageSize}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult listSysAttachmentUsers(@PathVariable int pageNumber, @PathVariable int pageSize) {
		Map<String, Object> paramerMap = new HashMap<String, Object>();
		paramerMap.put("pageNumber", pageNumber);
		paramerMap.put("pageSize", pageSize);
		// Page<Map<String, Object>> pageList =
		// sysAttachmentUserService.getSysAttachmentUsers(paramerMap);
		// Map<String, Object> dataMap = new HashMap<String, Object>();
		// dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		// dataMap.put(Constant.ROWS, pageList.getResult());
		// return new JsonResult(true).setData(dataMap);
		return null;
	}

	/**
	 * 删除 附件-用户关系表（下载记录表）
	 */
	@RequestMapping(value = "deleteSysAttachmentUser/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult deleteSysAttachmentUser(@PathVariable int id) {
		int count = sysAttachmentUserService.deleteSysAttachmentUserById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
