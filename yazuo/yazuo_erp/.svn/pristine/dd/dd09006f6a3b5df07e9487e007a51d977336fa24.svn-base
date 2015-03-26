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

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.service.WebkitManagerService;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.system.vo.SysWebkitVO;

/**
 * webkit版本管理业务
 * @author kyy
 * @date 2014-9-15 下午2:03:26
 */
@Controller
@RequestMapping("webkit")
@SessionAttributes(Constant.SESSION_USER)
public class WebkitManagerController {

	@Resource
	private WebkitManagerService webkitManagerService;
	
	@RequestMapping(value = "uploadImage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object uploadImage(@RequestParam MultipartFile[] myfiles, HttpServletRequest request) throws IOException {
		return webkitManagerService.uploadFile(myfiles, request);
	}
	
	@RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult list(Integer pageSize, Integer pageNumber, HttpServletRequest request){
		SysWebkitVO sysWebkit = new SysWebkitVO();
		sysWebkit.setPageNumber(pageNumber);
		sysWebkit.setPageSize(pageSize);
		
		Page<SysWebkitVO> pageList = webkitManagerService.getSysWebkits(sysWebkit, request);
		
		JsonResult result = new JsonResult(true);
		result.setMessage("查询成功!");
		result.putData("totalSize", pageList.getTotal());
		result.putData("rows", pageList.getResult());
		return result;
	}
	
	@RequestMapping(value = "saveWebkit", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveWebkit(@RequestBody SysWebkitVO sysWebkitVO, @ModelAttribute(Constant.SESSION_USER) SysUserVO user,
			HttpServletRequest request){
		return webkitManagerService.saveSysWebkit(sysWebkitVO, user, request);
	}
	
	@RequestMapping(value = "deleteWebkit", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult deleteWebkit(Integer id, HttpServletRequest request){
		return webkitManagerService.deleteSysWebkitById(id, request);
	}

}
